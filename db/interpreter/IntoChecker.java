package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class IntoChecker extends Interpreter{

	/**
	 * @param builder
	 */
	public IntoChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if(this.getCurrentCheck().equalsIgnoreCase("INTO")) {
			String newCommand = this.getQueryBuilder().getCommand() + " INTO";
			this.getQueryBuilder().setCommand(newCommand);
			toNextStep();
		} else {
			throw new SQLException("Syntax error near to " + this.getCurrentCheck());
		}
		
	}

	@Override
	public void toNextStep() throws SQLException {
		prepareQueryforNextStep();
		String restOfQuery = this.getQueryBuilder().getQuery().replace("into", "").trim();
		getQueryBuilder().setQuery(restOfQuery);
		TableSyntaxChecker tableChecker = new TableSyntaxChecker(getQueryBuilder());
		tableChecker.validateRule();
	}

}
