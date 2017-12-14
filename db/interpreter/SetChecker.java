package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;



public class SetChecker extends Interpreter{

	public SetChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if (this.getCurrentCheck().equalsIgnoreCase("SET")) {
			String newCommand = this.getQueryBuilder().getCommand() + " SET";
			this.getQueryBuilder().setCommand(newCommand);
			toNextStep();
		} else {
			throw new SQLException("Syntax error near to " + this.getCurrentCheck());
		}
		
	}

	@Override
	public void toNextStep() throws SQLException {
		prepareQueryforNextStep();
		String restOfQuery = this.getQueryBuilder().getQuery().replace("set", "").trim();
		getQueryBuilder().setQuery(restOfQuery);
		ChangesChecker changesChecker = new ChangesChecker(getQueryBuilder());
		changesChecker.validateRule();
	}

}
