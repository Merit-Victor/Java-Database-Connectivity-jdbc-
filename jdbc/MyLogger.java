package eg.edu.alexu.csd.oop.jdbc;

import java.util.logging.Logger;

public class MyLogger {

	private static Logger logger = null;

	private MyLogger() {
	}
	

	public static Logger getInstance() {
		if (logger == null) {
			logger = Logger.getLogger(MyLogger.class.getName());
		}
		return logger;
	}
}
