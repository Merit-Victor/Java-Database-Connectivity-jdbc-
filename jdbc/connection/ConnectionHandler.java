package eg.edu.alexu.csd.oop.jdbc.connection;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.jdbc.Handler;
import eg.edu.alexu.csd.oop.jdbc.MyLogger;

import java.sql.Connection;

public class ConnectionHandler implements Handler{

	private Connection mConnection;
	private Statement mStatement;
	private static Logger logger = MyLogger.getInstance();
	
	public ConnectionHandler(Connection mConnetion) {
		this.mConnection = mConnetion;
	}
	
	@Override
	public boolean doChoice(int choice) throws SQLException {
		boolean result = false;
		switch(choice) {
		case 1:
			this.mStatement = mConnection.createStatement();
			if(mStatement == null) {
				throw new SQLException("Failed to create statement");
			} else {
				logger.info("\nStatement created successfully.");
			}
			result = true;
			break;
		case 2:
			mConnection.close();
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
		System.out.println("1: Create Statement.\n2: Close.");
	}
	
	public Statement getStatement() {
		return this.mStatement;
	}

}
