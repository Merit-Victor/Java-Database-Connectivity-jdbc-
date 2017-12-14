package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class DatabaseChecker extends Interpreter {

	/**
	 * Whether it's create or drop database or table. 
	 */
	private boolean databaseRequired = false;
	
	/**
	 * @param builder
	 */
	public DatabaseChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if(isDatabaseRequired()) {
			String newCommand = this.getQueryBuilder().getCommand() + " DATABASE";
			this.getQueryBuilder().setCommand(newCommand);
			toNextStep();
		} else {
			throw new SQLException("Syntax error near to " + this.getCurrentCheck());
		}
		
	}

	@Override
	public void toNextStep() throws SQLException {
		prepareQueryforNextStep();
		String newQuery = getQueryBuilder().getQuery().replace("database", "").trim();
		getQueryBuilder().setQuery(newQuery);
		if(this.getNextCheck() == null || this.getNextCheck() == ";") {
			throw new SQLException("Syntax error: database's name is missed.");
		} else if(this.getQueryBuilder().getQuery().trim().contains(" ")) {
			throw new SQLException("Syntax error: database's name should not contain spaces.");
		} else if(this.getQueryBuilder().getQuery().trim().contains(",")) {
			throw new SQLException("Syntax error: only one database is allowed per time.");
		} else {
			String newCommand = this.getQueryBuilder().getCommand() + " database";
			this.getQueryBuilder().setCommand(newCommand);
			this.getQueryBuilder().setDatabase(
					this.getNextCheck().toLowerCase().replace(";", "").trim());
		}
	}

	/**
	 * @return
	 */
	public boolean isDatabaseRequired() {
		if(this.getCurrentCheck().equalsIgnoreCase("DATABASE")) {
			databaseRequired = true;
		}
		return this.databaseRequired;
	}
}
