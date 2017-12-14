package eg.edu.alexu.csd.oop.jdbc.cli;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import java.util.logging.*;


import eg.edu.alexu.csd.oop.jdbc.MyLogger;
import eg.edu.alexu.csd.oop.jdbc.connection.ConnectionHandler;
import eg.edu.alexu.csd.oop.jdbc.driver.Driver;
import eg.edu.alexu.csd.oop.jdbc.driver.DriverHandler;
import eg.edu.alexu.csd.oop.jdbc.resultSet.ResultSetHandler;
import eg.edu.alexu.csd.oop.jdbc.resultSetMetaData.ResultSetMetaDataHandler;
import eg.edu.alexu.csd.oop.jdbc.statement.StatementHandler;

public class Cli {
	
	private static Logger logger = MyLogger.getInstance();
	private static DriverHandler dHandler;
	private static Driver mDriver;
	private static Scanner scan; 
	private static String[] args1;
	private static Connection mConnetion;
	private static ConnectionHandler cHanlder;
	private static Statement mStatement;
	private static StatementHandler sHandler;
	private static ResultSet mResultSet;
	private static ResultSetHandler rsHandler;
	private static ResultSetMetaData mResultSetMetaData;
	private static ResultSetMetaDataHandler rsmdHandler;
	
	public static void main(String[] args){
		initialize();
		args1 = args;
		System.out.println("Please enter your desired database directory:");
		String databaseDirectory = readFullLine(scan);
		File file = new File(databaseDirectory);
		Properties info = new Properties();
		info.put("path", file.getAbsoluteFile());
		dHandler.setProperties(info);
		System.out.println("Please enter url:");
		String url = readFullLine(scan);
		dHandler.setURL(url);
		startDriver();
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
	
	private static void initialize() {
		mDriver = new Driver();
		scan = new Scanner(System.in);
		dHandler = new DriverHandler(mDriver);
	}
	
	private static void startDriver() {
		dHandler.printOptions();
		int choice = scan.nextInt();
		boolean result = false;
		try {
			result = dHandler.doChoice(choice);
			if(!result) {
				main(args1);
			} else {
				//Connect
				if(choice == 2) {
					mConnetion = dHandler.getConnection();
					if(mConnetion == null) {
						logger.warning("Failed to connect");
						startDriver();
					} else {
						startConnection();
					}
				} else {
					startDriver();
				}
			}
		} catch (SQLException e) {
			logger.warning("\n" + e.getMessage());
			main(args1);
		}
		
	}
	
	private static void startConnection() {
		cHanlder = new ConnectionHandler(mConnetion);
		cHanlder.printOptions();
		int choice = scan.nextInt();
		boolean result = false;
		try {
			result = cHanlder.doChoice(choice);
			if(!result) {
				startConnection();
			} else {
				//Create statement
				if(choice == 1) {
					mStatement = cHanlder.getStatement();
					startStatement();
				} else {
					System.out.println("New Connection? [Y/N]");
					if(scan.next().equalsIgnoreCase("y")) {
						startConnection();
					} else {
						System.out.println("BYE");
					}
				}
			}
		} catch (SQLException e) {
			logger.warning("\n" + e.getMessage());
			startDriver();
		}
		
	}
	
	private static void startStatement() {
		sHandler = new StatementHandler(mStatement);
		sHandler.printOptions();
		int choice = scan.nextInt();
		boolean result = false;
		try {
			result = sHandler.doChoice(choice);
			if(!result) {
				startStatement();
			} else {
				//Create statement
				if(choice != 6) {
					System.out.println("Again? [Y/N]");
					if(scan.next().equalsIgnoreCase("y")) {
						startStatement();
					} else {
						System.out.println("BYE");
					}
				} else {
					mResultSet = sHandler.getResultSet();
					startResultSet();
				}
			}
		} catch (SQLException e) {
			logger.warning("\n" + e.getMessage());
			startConnection();
		}
		
	}
	
	private static void startResultSet() {
		rsHandler = new ResultSetHandler(mResultSet);
		rsHandler.printOptions();
		int choice = scan.nextInt();
		boolean result = false;
		try {
			result = rsHandler.doChoice(choice);
			if(!result) {
				startResultSet();
			} else {
				if(choice != 9) {
					System.out.println("Again? [Y/N]");
					if(scan.next().equalsIgnoreCase("y")) {
						startResultSet();
					} else {
						System.out.println("BYE");
					}
				} else {
					mResultSetMetaData = rsHandler.getResultSetMetaData();
					startResultSetMetaData();
				}
			}
		} catch (SQLException e) {
			logger.warning("\n" + e.getMessage());
			startStatement();
		}
		
	}
	
	private static void startResultSetMetaData() throws SQLException {
		rsmdHandler = new ResultSetMetaDataHandler(mResultSetMetaData);
		rsmdHandler.printOptions();
		int choice = scan.nextInt();
		boolean result = false;
		result = rsmdHandler.doChoice(choice); 
		if(!result) {
			startResultSetMetaData();
		} else {
			System.out.println("1: Again.\n2: Exit.\n3: back.");
			choice = scan.nextInt();
			switch(choice) {
			case 1:
				startResultSetMetaData();
				break;
			case 2:
				System.out.println("BYE");
				System.exit(0);
				break;
			case 3:
				startResultSet();
				break;
			default:
				System.out.println("Invalid input, bye.");
				System.exit(0);
				break;
			}
		}
	}
}
