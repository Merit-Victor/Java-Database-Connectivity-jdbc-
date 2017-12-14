package eg.edu.alexu.csd.oop.jdbc.driver;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.jdbc.MyLogger;
import eg.edu.alexu.csd.oop.jdbc.pool.ConnectionPool;

/**
 * @author Merit Victor
 *
 */
public class Driver implements java.sql.Driver {

	/**
	 * 
	 */
	private String urlRegex = "jdbc:xmldb://localhost";

	/**
	 * 
	 */
	private ConnectionPool pool;
	
	/**
	 * 
	 */
	private static Logger logger;
	

	public Driver() {
		Driver.logger = MyLogger.getInstance();
		this.pool = new ConnectionPool();
	}
	
	@Override
	public boolean acceptsURL(String url) throws SQLException {
		logger.info("\nVerifying URL...");
		if(url.equals(urlRegex)) {
			logger.info("Varified.");
			return true;
		}
		logger.warning("\nInvalid url");
		return false;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		if (this.acceptsURL(url)) {
			logger.info("\nConnecting...");
			File dir =  (File)info.get("path");
			String path = dir.getAbsolutePath();
			pool.setPath(path);
			return pool.checkOut();
		}
		return null;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		logger.info("\nGetting Driver info...");
		DriverPropertyInfo dpi = new DriverPropertyInfo("path", info.getProperty("path"));
		if (acceptsURL(url)) {
			return new DriverPropertyInfo[] { dpi };
		}
		return null;
	}

	@Override
	public int getMajorVersion() {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getMinorVersion() {
		throw new UnsupportedOperationException();

	}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean jdbcCompliant() {
		throw new UnsupportedOperationException();
	}

}
