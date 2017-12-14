package eg.edu.alexu.csd.oop.db.strategy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import eg.edu.alexu.csd.oop.db.backend.Column;
import eg.edu.alexu.csd.oop.db.backend.Condition;

public class SelectOperation implements Strategy {
	private String tableName;
	private ArrayList<Column> columns;
	private boolean selectedAll = false;
	Condition condition;
	private ArrayList<String> columnsName = new ArrayList<String>();// -------------->
	private ArrayList<String> ColumnsType = new ArrayList<String>();// -------------->

	public SelectOperation(String tableName, ArrayList<Column> columns) {
		this.columns = columns;
		this.tableName = tableName;
	}

	public SelectOperation(String tableName2) {

		this.tableName = tableName2;
		selectedAll = true;
	}

	public SelectOperation(String tableName2, Condition condition) {
		// select all from table where condition
		this.condition = condition;
		this.tableName = tableName2;
		selectedAll = true;
	}

	public SelectOperation(String tableName, ArrayList<Column> columns, Condition condition) {
		// SELECT columns FROM table WHERE condition
		this.columns = columns;
		this.tableName = tableName;
		this.condition = condition;
	}

	@Override
	public Result doOperation(String databaseName) {
		Result resultMap = new Result();
		List<List<Object>> resultList = new ArrayList<List<Object>>();
		NodeList nList = null;
		NodeList nl = null;
		InputStream inputStream;
		Reader reader = null;
		try {
			inputStream = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator")
					+ "Database" + System.getProperty("file.separator") + databaseName
					+ System.getProperty("file.separator") + tableName + ".xml");
			try {
				reader = new InputStreamReader(inputStream, "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		InputSource is = new InputSource(reader);
		is.setEncoding("ISO-8859-1");
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(is);
			// dbFactory.setValidating(true);
			doc.getDocumentElement().normalize();

			nList = doc.getElementsByTagName("Row");
			List<Object> list = new ArrayList<Object>();
			if (selectedAll) {
				columns = new ArrayList<>();
				Node Col = doc.getElementsByTagName("Columns").item(0);
				Element eElement = (Element) Col;
				nl = eElement.getElementsByTagName("*");
				for (int i = 0; i < nl.getLength(); i++) {
					Column column = new Column();
					column.setName(nl.item(i).getTextContent());
					columns.add(column);
					ColumnsType.add((nl.item(i).getNodeName()));// ------------------>
					columnsName.add(nl.item(i).getTextContent());// ----------------->
				}
			}
			if (condition != null) {

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					Element eElement = (Element) nNode;

					String key = eElement.getElementsByTagName(condition.getFirstOperand()).item(0).getTextContent();

					switch (condition.getOperator()) {
					case '=':
						if (condition.getSecondOperand().replace("\'", " ").replace("\"", " ").trim()
								.compareTo(key) == 0) {
							for (int i = 0; i < columns.size(); i++) {
								String value = eElement.getElementsByTagName(columns.get(i).getName()).item(0)
										.getTextContent();
								list.add(value);

							}
							resultList.add(list);
							list = new ArrayList<Object>();

						}
						break;
					case '<':
						if (condition.getSecondOperand().replace("\'", " ").replace("\"", " ").trim()
								.compareTo(key) > 0) {
							for (int i = 0; i < columns.size(); i++) {
								String value = eElement.getElementsByTagName(columns.get(i).getName()).item(0)
										.getTextContent();
								list.add(value);
							}
							resultList.add(list);
							list = new ArrayList<Object>();

						}
						break;
					case '>':

						if (Integer.parseInt(
								condition.getSecondOperand().replace("\'", " ").replace("\"", " ").trim()) < Integer
										.parseInt(key)) {
							for (int i = 0; i < columns.size(); i++) {
								if (nl.item(i).getNodeName().equals("int")) {
									int value = Integer.parseInt(eElement.getElementsByTagName(columns.get(i).getName())
											.item(0).getTextContent());
									list.add(value);
								} else {
									String value = eElement.getElementsByTagName(columns.get(i).getName()).item(0)
											.getTextContent();
									list.add(value);
								}
							}
							resultList.add(list);
							list = new ArrayList<Object>();

						}
						break;
					default:
						break;

					}
				}

			} else {

				for (int temp = 0; temp < nList.getLength(); temp++) {
					for (int i = 0; i < columns.size(); i++) {
						Node nNode = nList.item(temp);
						Element eElement = (Element) nNode;
						String value = eElement.getElementsByTagName(columns.get(i).getName()).item(0).getTextContent();
						list.add(value);

					}
					resultList.add(list);
					list = new ArrayList<Object>();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object result[][] = new Object[resultList.size()][resultList.get(0).size()];
		for (int i = 0; i < resultList.size(); i++) {
			for (int j = 0; j < resultList.get(0).size(); j++) {

				result[i][j] = resultList.get(i).get(j);
			}
		}
		resultMap.setArray(result);
		resultMap.setTableName(tableName);// ---------------->
		resultMap.setColumnsName(columnsName);// ------------->
		resultMap.setColumnsType(ColumnsType);// -------------.
		return resultMap;

	}

}
