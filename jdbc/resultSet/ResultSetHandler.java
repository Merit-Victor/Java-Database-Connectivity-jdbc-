package eg.edu.alexu.csd.oop.jdbc.resultSet;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.jdbc.Handler;
import eg.edu.alexu.csd.oop.jdbc.MyLogger;

public class ResultSetHandler implements Handler {

	private ResultSet mResultSet;
	private Scanner scan = new Scanner(System.in);
	private ResultSetMetaData mResultSetMetaData;
	private Object mObject;
	private static Logger logger = MyLogger.getInstance();
	
	public ResultSetHandler(ResultSet resultSet) {
		this.mResultSet = resultSet;
	}
	
	
	@Override
	public boolean doChoice(int choice) throws SQLException {
		boolean result = false;
		switch(choice) {
		case 1:
			System.out.println("Please enter row numebr:");
			System.out.println(mResultSet.absolute(scan.nextInt()));
			result = true;
			break;
			
		case 2:
			mResultSet.afterLast();
			result = true;
			break;
			
		case 3:
			mResultSet.beforeFirst();
			result = true;
			break;
			
		case 4:
			mResultSet.close();
			result = true;
			break;
			
		case 5:
			System.out.println("Please enter Column label:");
			System.out.println(mResultSet.findColumn(scan.nextLine()));
			result = true;
			break;
			
		case 6:
			System.out.println(mResultSet.first());
			result = true;
			break;
			
		case 7:
			System.out.println("Please enter column index:");
			System.out.println(mResultSet.getInt(scan.nextInt()));
			result = true;
			break;
			
		case 8:
			System.out.println("Please enter column label:");
			System.out.println(mResultSet.getInt(scan.nextLine()));
			result = true;
			break;
			
		case 9:
			mResultSetMetaData = mResultSet.getMetaData();
			if(mResultSetMetaData == null) {
				throw new SQLException("Failed to retrieve Result set metadata.");
			} else {
				logger.info("\nSuccess");
			}
			result = true;
			break;
			
		case 10:
			System.out.println("Please enter column index:");
			mObject = mResultSet.getObject(scan.nextInt());
			System.out.println(String.valueOf(mObject));
			result = true;
			break;
			
		case 11:
			mResultSet.getStatement();
			result = true;
			break;
			
		case 12: 
			System.out.println("Please enter column index:");
			System.out.println(mResultSet.getString(scan.nextLine()));
			result = true;
			break;
			
		case 13:
			System.out.println("Please enter column label:");
			System.out.println(mResultSet.getString(scan.nextLine()));
			result = true;
			break;
			
		case 14:
			System.out.println(mResultSet.isAfterLast());
			result = true;
			break;
			
		case 15:
			System.out.println(mResultSet.isBeforeFirst());
			result = true;
			break;
			
		case 16:
			System.out.println(mResultSet.isClosed());
			result = true;
			break;
			
		case 17:
			System.out.println(mResultSet.isFirst());
			result = true;
			break;
			
		case 18:
			System.out.println(mResultSet.isLast());
			result = true;
			break;
			
		case 19:
			System.out.println(mResultSet.last());
			result = true;
			break;
			
		case 20:
			System.out.println(mResultSet.previous());
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
		 System.out.println("1: Absolute.\n2: After Last.\n3: Before First.\n4: Close.\n"
		 		+ "5: Find Column\n6: First.\n7: Get Int with integer.\n"
		 		+ "8: Get Int with string.\n9: Get Meta Data.\10: Get Object.\n"
		 		+ "11: Get Statement.\n12: Get String with integer.\n"
		 		+ "13: Get String with string.\n14: Is After Last.\n15: Is Before First.\n"
		 		+ "16: Is Closed.\n17: Is First.\n18: Is Last.\n19: last.\n20: next.\n21: previous."); 

	}
	
	public ResultSetMetaData getResultSetMetaData() {
		return this.mResultSetMetaData;
	}
	
	public Object getObject() {
		return this.mObject;
	}

}
