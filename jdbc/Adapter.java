package eg.edu.alexu.csd.oop.jdbc;

import java.util.ArrayList;
import java.util.HashMap;

import eg.edu.alexu.csd.oop.db.strategy.Result;

public class Adapter extends Result implements IResultSetAdapter {
	private HashMap<Integer, ArrayList<String>> myRows;

	public Adapter() {
		super();
		myRows = new HashMap<Integer, ArrayList<String>>();
	}

	@Override
	public void setSelectedRows(Object[][] selectedRows) {
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < selectedRows.length; i++) {
			for (int j = 0; j < selectedRows[0].length; j++) {
				temp.add((String) selectedRows[i][j]);
			}
			myRows.put(i, temp);
			temp.clear();
		}

	}
	public HashMap<Integer, ArrayList<String>> getMyRows() {
		return myRows;
	}
	

}
