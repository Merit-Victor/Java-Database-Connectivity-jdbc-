package eg.edu.alexu.csd.oop.jdbc.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.db.cs68.DatabaseImp;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;
import eg.edu.alexu.csd.oop.db.queryParser.QueryFactory;
import eg.edu.alexu.csd.oop.db.queryParser.QueryValidator;
import eg.edu.alexu.csd.oop.jdbc.MyLogger;

/**
 * @author Merit Victor
 *
 */
public class Statement implements java.sql.Statement {

	private static Logger logger;
	private Connection mConnection;
	private ArrayList<String> batch;
	private boolean statementIsClosed = false;
	private QueryValidator validator;
	private QueryBuilder mBuilder;
	private int queryTimeOut = 0;
	private QueryFactory factory;
	private DatabaseImp mDatabaseImpl;
	private ResultSet resultSet;
	private String databaseName;
	
	/**
	 * @param connection
	 * @param path
	 */
	public Statement(Connection connection, String path) {
		String[] parts;
		if (path.contains("/")) {
			parts = path.split("/");
		} else {
			parts = path.split("\\\\");
		}
		this.databaseName = parts[parts.length -1];
		Statement.logger = MyLogger.getInstance();
		this.mConnection = connection;
		this.factory = new QueryFactory();
		this.mDatabaseImpl = new DatabaseImp();
		this.mDatabaseImpl.createDatabase(databaseName, false);
		this.batch = new ArrayList<>();
	}
	 
	
	@Override
	public void addBatch(String arg0) throws SQLException {
		logger.info("Adding to batch...");
		if(statementIsClosed) {
			throw new SQLException("Cannot add command to the batch, Statement is closed.");
		}
		this.batch.add(arg0);
		logger.info("Success.");

	}

	@Override
	public void clearBatch() throws SQLException {
		logger.info("Clearing the batch...");
		if(statementIsClosed) {
			throw new SQLException("Cannot clear the batch, Statement is closed.");
		}
		this.batch.clear();
		logger.info("Success.");

	}

	@Override
	public void close() throws SQLException {
		logger.info("Closing Statement.");
		this.statementIsClosed = true;
		this.mConnection = null;
		this.batch.clear();
		logger.info("Success.");

	}

	@Override
	public boolean execute(String arg0) throws SQLException {
		logger.info("Executing...");
		if(statementIsClosed) {
			throw new SQLException("Statement is closed.");
		}
		try {
			this.validator = new QueryValidator(arg0, mDatabaseImpl.getDatabase());
			this.mBuilder = validator.getQueryBuilder();
		} catch (Exception e) {
			throw new SQLException("Invalid Query with messege: " + e.getMessage());
		}
		int queryIndex = factory.getQueryIndex(mBuilder.getCommand());
		boolean isDone = false;
		switch (queryIndex) {
		//Create, drop table, database.
		case 8:
		case 9:
		case 10:
		case 11:
			isDone = mDatabaseImpl.executeStructureQuery(arg0);
			break;
		//Select returns result set.
		case 1:
		case 2:
		case 12:
		case 13:
			executeQuery(arg0);
			break;
		//Delete, Insert or update.
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 14:
			executeUpdate(arg0);
			break;
		default:
			break;
		}
		return isDone;
	}

	@Override
	public int[] executeBatch() throws SQLException {
		int[] effectedRowsNumber = new int[this.batch.size()];
		logger.info("Executing batch...");
		if(this.batch == null) {
			throw new SQLException("Batch is null");
		}
		for(int i = 0; i < this.batch.size(); i++) {
				effectedRowsNumber[i] = this.executeUpdate(batch.get(i));
		}
		return effectedRowsNumber;
	}

	@Override
	public ResultSet executeQuery(String arg0) throws SQLException {
		//Select
		Object[][] array = mDatabaseImpl.executeQuery(arg0);
		if(array == null) {
			throw new SQLException("NULL ARRAY");
		} else {
			mBuilder = mDatabaseImpl.getQueryBuilder();
			mBuilder.setDatabase(databaseName);
			this.resultSet = new eg.edu.alexu.csd.oop.jdbc.resultSet.ResultSet(
					array,this, mBuilder);
			return resultSet;	
		}
	}

	@Override
	public int executeUpdate(String arg0) throws SQLException {
		//Insert, Delete or Update.
		logger.info("Executing query...");
		int result = 0;
		try {
			result = this.mDatabaseImpl.executeUpdateQuery(arg0);
			logger.info("Result = " + result);
		} catch(Exception e) {
			throw new SQLException("Executing query failed with messege: " + e.getMessage());
		}
		return result;
	}

	@Override
	public Connection getConnection() throws SQLException {
		logger.info("Getting Connection...");
		if(statementIsClosed) {
			throw new SQLException("Cannot get Connection from closed Statement.");
		}
		logger.info("Success.");
		return this.mConnection;
	}



	@Override
	public int getQueryTimeout() throws SQLException {
		logger.info("Query timeout: ");
		if(statementIsClosed) {
			throw new SQLException("Cannot get timeout of closed statement.");
		}
		return this.queryTimeOut;
	}
	

	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		logger.info("Setting timout...");
		if(statementIsClosed) {
			throw new SQLException("Cannot set query timeout for closed statement.");
		} else if(arg0 < 0) {
			throw new SQLException("Cannot set negative valued timeout.");
		} else {
			this.queryTimeOut = arg0;
			logger.info("Success.");
		}
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int getResultSetType() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int getUpdateCount() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean isPoolable() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		throw new UnsupportedOperationException(); 

	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		throw new UnsupportedOperationException(); 

	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		throw new UnsupportedOperationException(); 

	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		throw new UnsupportedOperationException(); 

	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		throw new UnsupportedOperationException(); 

	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		throw new UnsupportedOperationException(); 

	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		throw new UnsupportedOperationException(); 

	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException(); 
	}


	@Override
	public void cancel() throws SQLException {
		throw new UnsupportedOperationException(); 

	}
	
	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		throw new UnsupportedOperationException(); 

	}
	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		throw new UnsupportedOperationException(); 
	}
	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int getMaxRows() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		throw new UnsupportedOperationException(); 
	}
}
