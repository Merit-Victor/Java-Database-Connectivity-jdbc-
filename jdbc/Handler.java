package eg.edu.alexu.csd.oop.jdbc;

import java.sql.SQLException;

public interface Handler  {

	public boolean doChoice(int choice) throws SQLException;
	
	public void printOptions();
}
