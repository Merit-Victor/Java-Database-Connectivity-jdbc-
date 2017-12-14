package eg.edu.alexu.csd.oop.jdbc.statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.jdbc.Handler;
import eg.edu.alexu.csd.oop.jdbc.MyLogger;

import java.sql.Statement;

public class StatementHandler implements Handler {

	private Statement mStatement;
	private ResultSet mResultSet;
	private static Logger logger = MyLogger.getInstance();
	
	public StatementHandler(Statement statement) {
		this.mStatement = statement;
	}

	@Override
	public boolean doChoice(int choice) throws SQLException {
		boolean result = false;
		Scanner scan = new Scanner(System.in);
		String userInput = "";
		switch (choice) {
		case 1:
			System.out.println("Please enter query:");
			userInput = readFullLine(scan);
			mStatement.addBatch(userInput);
			result = true;
			break;
			
		case 2:
			mStatement.clearBatch();
			result = true;
			break;
			
		case 3:
			mStatement.close();
			result = true;
			break;
			
		case 4:
			System.out.println("Please enter query:");
			userInput = readFullLine(scan);
			if(mStatement.execute(userInput)) {
				logger.info("\nQuery Executed successfully.");
			}
			result = true;
			break;
			
		case 5:
			int[] returnedValues;
			returnedValues = mStatement.executeBatch();
			if(returnedValues != null) {
				for(int value: returnedValues) {
					System.out.println(value);
				}
			}
			result = true;
			break;
		
		case 6:
			System.out.println("Please enter query:");
			userInput = readFullLine(scan);
			mResultSet = mStatement.executeQuery(userInput);
			result = true;
			break;
			
		case 7:
			int value;
			System.out.println("Please enter query:");
			userInput = readFullLine(scan);
			value = mStatement.executeUpdate(userInput);
			System.out.println(value);
			result = true;
			break;
			
		case 8:
			mStatement.getConnection();
			result = true;
			break;
		case 9:
			int timeout;
			timeout = mStatement.getQueryTimeout();
			System.out.println(timeout);
			result = true;
			break;
			
		case 10:
			int timeout2;
			System.out.println("Please enter Query timeout.");
			timeout2 = scan.nextInt();
			mStatement.setQueryTimeout(timeout2);
			result = true;
			break;
		default:
			System.out.println("Invalid input");
			break;
		}
		return result;
	}

	@Override
	public void printOptions() {
		System.out.println("1: Add to Batch.\n2: Clear Batch.\n3: Close.\n4: Execute.\n"
				+ "5: Execute Batch.\n6: Execute Query.\n7: Execute Update.\n"
				+ "8: Get the Connection.\n9: Get Query Timeout.\n" + "10: Set Query Timeout.");
	}

	/**
	 * This method to enable scanning the whole sentence.
	 * @param scan scanner
	 * @return line
	 */
	private static String readFullLine(final Scanner scan) {
		String line = "";
		line += scan.next();
		line += scan.nextLine();
		return line;
	}
	
	public ResultSet getResultSet() {
		return this.mResultSet;
	}
}
