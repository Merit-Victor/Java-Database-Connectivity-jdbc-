package eg.edu.alexu.csd.oop.db.strategy;

import java.util.ArrayList;

public class Result {

	private int number = -1;
	private Object[][] array = new Object[0][0];
	protected ArrayList<String> columnsName;
	protected String tableName;
	protected ArrayList<String> columnsType;

	public void setColumnsName(ArrayList<String> columnsName) {
		this.columnsName = columnsName;
	}

	public ArrayList<String> getColumnsName() {
		return columnsName;
	}

	public void setColumnsType(ArrayList<String> columnsType) {
		this.columnsType = columnsType;
	}

	public ArrayList<String> getColumnsType() {
		return columnsType;
	}
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Object[][] getArray() {
		return array;
	}

	public void setArray(Object[][] array) {
		this.array = array;
	}

}
