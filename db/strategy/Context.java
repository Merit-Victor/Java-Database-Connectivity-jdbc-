package eg.edu.alexu.csd.oop.db.strategy;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.backend.MyDatabase;

public class Context {

	Strategy strategy;
	MyDatabase databaseList;
	private String databaseName;

	public Context(Strategy strategy, MyDatabase database) {
		this.strategy = strategy;
		databaseList = database;
		databaseName = databaseList.getDatabaseList().get(databaseList.getDatabaseList().size() - 1);
	}

	public Result executeStrategy() throws SQLException {
		return strategy.doOperation(databaseName);
	}
}
