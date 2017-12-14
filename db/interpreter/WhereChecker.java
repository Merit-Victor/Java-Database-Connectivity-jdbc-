package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

public class WhereChecker extends Interpreter{

	public WhereChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if (this.getCurrentCheck().equalsIgnoreCase("WHERE")) {
			String newCommand = this.getQueryBuilder().getCommand() + " WHERE";
			this.getQueryBuilder().setCommand(newCommand);
			if(this.getNextCheck() == null || this.getNextCheck().equals(";")) {
				throw new SQLException("Syntax error: condition is required.");
			} else {
				toNextStep();	
			}
		} else {
			throw new SQLException("Syntax error near to " + this.getCurrentCheck());
		}
		
	}

	@Override
	public void toNextStep() throws SQLException {
		prepareQueryforNextStep();
		String restOfQuery = this.getQueryBuilder().getQuery().replace("where", "").trim();
		getQueryBuilder().setQuery(restOfQuery);
		ConditionChecker conChecker = new ConditionChecker(getQueryBuilder());
		conChecker.validateRule();	
	}

}
