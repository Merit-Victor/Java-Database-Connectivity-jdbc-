package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;



/**
 * @author Merit Victor
 *
 */
public class FromChecker extends Interpreter {

	/**
	 * @param builder
	 */
	public FromChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if (this.getCurrentCheck().equalsIgnoreCase("FROM")) {
			String newCommand = this.getQueryBuilder().getCommand() + " FROM";
			this.getQueryBuilder().setCommand(newCommand);
			if(this.getNextCheck() == null || this.getNextCheck() == ";") {
				throw new SQLException("Syntax error: table name is required.");
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
		String restOfQuery = this.getQueryBuilder().getQuery().replace("from", "").trim();
		getQueryBuilder().setQuery(restOfQuery);
		TableSyntaxChecker tableChecker = new TableSyntaxChecker(getQueryBuilder());
		tableChecker.validateRule();
	}

}
