package eg.edu.alexu.csd.oop.db.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DTD {
	/**
	 * 
	 */
	private ArrayList<Column> columns;
	/**
	 * 
	 */
	private String tableName;
	/**
	 * 
	 */
	private String databaseName;
	/**
	 * 
	 */
	private String newLine = System.getProperty("line.separator");
	/**
	 * 
	 */
	private String slach = System.getProperty("file.separator");

	/**
	 * @param columns
	 * @param tableName
	 * @param databaseName
	 */
	public DTD(ArrayList<Column> columns, String tableName, String databaseName) {
		this.columns = columns;
		this.tableName = tableName;
		this.databaseName = databaseName;
	}

	public DTD(String tableName, String databaseName) {
		this.tableName = tableName;
		this.databaseName = databaseName;
	}

	/**
	 * 
	 */
	public void writeDTD() {
		FileWriter fr;
		try {
			fr = new FileWriter(
					System.getProperty("user.dir") + slach + "Database" + slach 
							+ databaseName + slach + tableName + ".dtd",
					false);
			BufferedWriter bw = new BufferedWriter(fr);
			String table = "<!ELEMENT " + tableName + " ( Columns , Row* )>" + newLine;
			String tableIdentifier = "<!ELEMENT Columns ( ";
			String tableElement = "";
			String row = "<!ELEMENT Row ( ";
			String rowElement = "";
			for (int i = 0; i < columns.size(); i++) {
				if (i != 0) {
					row += " , ";
					tableIdentifier += " , ";
				}
				row += columns.get(i).getName();
				String dataType = columns.get(i).getDataType();
				if (columns.get(i).getDataType().length() > 3) {
					dataType = dataType.substring(0, 7);
				}
				tableIdentifier += dataType;
				tableElement += "<!ELEMENT " + dataType + "(#PCDATA)>" + newLine;
				rowElement += "<!ELEMENT " + columns.get(i).getName() + "(#PCDATA)>" + newLine;
			}
			row += " )>" + newLine;
			tableIdentifier += " )>" + newLine;
			bw.write(table);
			bw.write(tableIdentifier);
			bw.write(tableElement);
			bw.write(row);
			bw.write(rowElement);
			bw.close();
			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String>[][] ReadDTD() {
		ArrayList<String>[][] myList = new ArrayList[1][2];
		myList[0][0] = new ArrayList<String>(); // this list will contain the
												// dataType
		myList[0][1] = new ArrayList<String>(); // this list contain name of
												// columns
		try {

			FileReader fr = new FileReader(System.getProperty("user.dir") + System.getProperty("file.separator")
					+ "Database" + System.getProperty("file.separator") + databaseName
					+ System.getProperty("file.separator") + tableName + ".dtd");
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("<!ELEMENT Columns (")) {
					String[] arr = line.split("\\s+");
					for (int i = 3; i < arr.length; i += 2) {
						myList[0][0].add(arr[i]);
					}
				}
				if (line.contains("<!ELEMENT Row (")) {
					String[] arr = line.split("\\s+");
					for (int i = 3; i < arr.length; i += 2) {
						myList[0][1].add(arr[i]);
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myList;
	}

}
