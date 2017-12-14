package eg.edu.alexu.csd.oop.db.strategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.backend.Condition;

public class DeleteOperation implements Strategy {

	private String tableName;
	private Condition condition = null;
	private int counter;
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;

	public DeleteOperation(String tableName, Condition condition) {
		this.tableName = tableName;
		this.condition = condition;
		// this.dataBaseName = dataBaseName;

	}

	public DeleteOperation(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public Result doOperation(String databaseName) {
		Result myMap = new Result();
		InputStream inputStream;
		Reader reader = null;
		try {
			inputStream = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName + System.getProperty("file.separator") + tableName + ".xml");
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

			
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
//			docFactory.setValidating(true);
			doc = docBuilder.parse(is);
			doc.getDocumentElement().normalize();
		NodeList rows = doc.getElementsByTagName("Row");
		if (condition != null) {

			getColum(rows);
		} else {
			for (int i = 0; i < rows.getLength(); i++) {
				Node root = doc.getDocumentElement();
				root.removeChild(rows.item(i));
				i--;
				counter++;
			}
		}
		myMap.setNumber(counter);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = null;
		transformer = transformerFactory.newTransformer();
	//	transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, databaseName + "/" + tableName + ".dtd");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"Database"+System.getProperty("file.separator")+databaseName +System.getProperty("file.separator")
				+ tableName + ".xml"));
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return myMap;
	}

	private void getColum(NodeList rows) {

		for (int i = 0; i < rows.getLength(); i++) {

			Node currentRow = rows.item(i);
			Element rowElement = (Element) currentRow;
			String key = rowElement
					.getElementsByTagName(condition.getFirstOperand().replace("\'", " ").replace("\"", " ").trim()).item(0)
					.getTextContent();
			if (key != null) {
				Boolean doOperation = CheckOprant(key);
				if (doOperation) {
					Node root = doc.getDocumentElement();
					root.removeChild(rows.item(i));
					i--;
				}
			}
		}
	}

	private Boolean CheckOprant(String value) {
		char operant = condition.getOperator();
		String comparedValue = condition.getSecondOperand().replace("\'", " ").replace("\"", " ").trim();
		if (operant == '=') {
			if (comparedValue.compareTo(value) == 0) {
				counter++;
				return true;

			}

		} else if (operant == '<') {
			if (comparedValue.compareTo(value) > 0) {
				counter++;
				return true;

			}
		} else if (operant == '>') {
			if (comparedValue.compareTo(value) < 0) {
				counter++;
				return true;
			}
		}
		return false;
	}
}