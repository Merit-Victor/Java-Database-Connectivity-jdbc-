package eg.edu.alexu.csd.oop.jdbc.resultSetMetaData;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.jdbc.Handler;

public class ResultSetMetaDataHandler implements Handler {

	private ResultSetMetaData mResultSetMetaData;
	private Scanner scan = new Scanner(System.in);
	
	public ResultSetMetaDataHandler(ResultSetMetaData resultmetadata) {
		this.mResultSetMetaData = resultmetadata;
	}
	
	@Override
	public boolean doChoice(int choice) throws SQLException {
		boolean result = false;
		switch(choice) {
		case 1:
			System.out.println(mResultSetMetaData.getColumnCount());
			result = true;
			break;
		case 2:
			System.out.println("Please enter column index:");
			System.out.println(mResultSetMetaData.getColumnLabel(scan.nextInt()));
			result = true;
			break;
			
		case 3:
			System.out.println("Please enter column index:");
			System.out.println(mResultSetMetaData.getColumnType(scan.nextInt()));
			result = true;
			break;
			
		case 4:
			System.out.println("Please enter column index:");
			System.out.println(mResultSetMetaData.getTableName(scan.nextInt()));
			result = true;
			break;
			
		case 5:
			System.out.println("Please enter column index:");
			System.out.println(mResultSetMetaData.getColumnName(scan.nextInt()));
			result = true;
			break;
			
		default:
			System.out.println("Invalid input.");
			break;
		}
		return result;
	}

	@Override
	public void printOptions() {
		System.out.println("1: Get Column Count.\n2: Get Column Label.\n3: Get Column Type.\n"
				+ "4: Get Table Name.\n5: Get Column Name.");

	}

}
