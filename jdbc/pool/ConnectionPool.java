package eg.edu.alexu.csd.oop.jdbc.pool;

import eg.edu.alexu.csd.oop.jdbc.connection.Connection;

/**
 * @author Merit Victor
 *
 */
public class ConnectionPool extends ObjectPool<Connection>{


	private String databasePath = "";
	private final int MAXIMUM = 10; 
	
	/**
	 * 
	 */
	public ConnectionPool() {
		initialize(MAXIMUM);
	}
	

	@Override
	protected Connection create() {
		return new Connection(databasePath, this);
	}
	
	public void setPath(String path) {
		this.databasePath = path;
		initialize(MAXIMUM);
	}
}
