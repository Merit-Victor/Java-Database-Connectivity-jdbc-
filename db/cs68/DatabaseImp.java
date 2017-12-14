package eg.edu.alexu.csd.oop.db.cs68;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.backend.Column;
import eg.edu.alexu.csd.oop.db.backend.MyDatabase;
import eg.edu.alexu.csd.oop.db.backend.Table;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;
import eg.edu.alexu.csd.oop.db.queryParser.QueryFactory;
import eg.edu.alexu.csd.oop.db.queryParser.QueryValidator;
import eg.edu.alexu.csd.oop.db.strategy.Context;
import eg.edu.alexu.csd.oop.db.strategy.Result;

public class DatabaseImp implements Database {
	Context context;
	OperationFactory operationFactory;

	public OperationFactory getOperationFactory() {
		return operationFactory;
	}

	MyDatabase database;
	QueryValidator validator;
	Table table;
	QueryBuilder queryBuilder;

	public DatabaseImp() {
		database = new MyDatabase();
		table = new Table();

	}

	public MyDatabase getDatabase() {
		return database;
	}

	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		try {
			if (dropIfExists) {
				this.executeStructureQuery("DROP DATABASE " + databaseName);
				this.executeStructureQuery("CREATE DATABASE " + databaseName);

			} else if (!dropIfExists) {

				this.executeStructureQuery("CREATE DATABASE " + databaseName);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return database.getPath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		ArrayList<Column> columns;
		boolean isDone = false;
		switch (getOperationIndex(query)) {
		case 8:
			isDone = true;
			database.createDatabase(queryBuilder.getDatabase());
			break;
		case 9:
			isDone = database.dropDatabase(queryBuilder.getDatabase());
			break;
		case 10:
			if (database.getDatabaseList().isEmpty()) {
				throw new NullPointerException(query);
			} else {
				columns = new ArrayList<>();
				String[][] changes = queryBuilder.getTableInfo();
				for (int i = 0; i < changes[0].length; i++) {
					Column column = new Column();
					column.setName(changes[0][i].toLowerCase());
					column.setDataType(changes[1][i].toLowerCase());
					columns.add(column);
				}

				isDone = table.createTable(database.getDatabaseList().get(database.getDatabaseList().size() - 1),
						queryBuilder.getTable(), columns);
			}
			break;
		case 11:
			isDone = table.dropTable(database.getDatabaseList().get(database.getDatabaseList().size() - 1),
					queryBuilder.getTable());
			break;
		}
		return isDone;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		int operationIndex = getOperationIndex(query);
		context = operationFactory.getOperation(operationIndex);
		Result result = context.executeStrategy();
		return result.getArray();
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		int operationIndex = getOperationIndex(query);
		context = operationFactory.getOperation(operationIndex);
		Result result = context.executeStrategy();
		return result.getNumber();
	}

	public int getOperationIndex(String query) throws SQLException {
		int operationIndex = 0;
		validator = new QueryValidator(query, database);
		QueryFactory factory = new QueryFactory();
		queryBuilder = validator.getQueryBuilder();
		operationFactory = new OperationFactory(queryBuilder, database);
		operationIndex = factory.getQueryIndex(queryBuilder.getCommand());
		return operationIndex;
	}

}
