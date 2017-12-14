package eg.edu.alexu.csd.oop.jdbc.driver;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.jdbc.Handler;
import eg.edu.alexu.csd.oop.jdbc.MyLogger;

public class DriverHandler implements Handler {

	private Driver mDriver;
	private String mURL = "";
	private Properties mInfo;
	private DriverPropertyInfo[] info;
	private Connection mConnection;
	private static Logger logger = MyLogger.getInstance();
	
	public DriverHandler(Driver driver) {
		mDriver = driver;
	}

	@Override
	public boolean doChoice(int choice) throws SQLException {
		boolean result = false;
		switch(choice) {
		case 1:
			if(mURL.isEmpty()) {
				System.out.println("Must enter a url first.");
			} else {
				mDriver.acceptsURL(mURL); 
				result = true;
			}
			break;
		case 2:
			if(mInfo == null) {
				System.out.println("Must enter a property first.");
			} else {
				mConnection = mDriver.connect(mURL, mInfo);
				if(mConnection != null) {
					logger.info("\nConnected.");
				} else {
					throw new SQLException("Failed to connect.");
				}
				result = true;
			}
			break;
		case 3:
			if(mInfo == null) {
				System.out.println("Must enter a property first.");
			} else if(mURL.isEmpty()) {
				System.out.println("Must enter a url first.");
			} else {
				info = mDriver.getPropertyInfo(mURL, mInfo);
				if(info != null) {
					for(DriverPropertyInfo inf: info) {
						System.out.println(inf);
					}
				} else {
					throw new SQLException("Failed to get driver info.");
				}
				result = true;
			}
			break;
		default:
			System.out.println("Invalid Input");
			break;
		}
		return result;
	}

	@Override
	public void printOptions() {
		System.out.println("1: Verify URL.\n2: Connect.\n3: Get property info.");

	}

	public void setURL(String url) {
		this.mURL = url;
	}

	public void setProperties(Properties info) {
		this.mInfo = info;
	}
	
	public Connection getConnection() {
		return this.mConnection;
	}

}
