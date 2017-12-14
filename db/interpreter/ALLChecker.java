package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;


/**
 * @author Merit Victor
 *
 */
public class ALLChecker extends Interpreter {

	/**
	 * 
	 */
	private boolean allAreRequired = false;

	/**
	 * @param builder
	 */
	public ALLChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if (allAreRequired()) {
			String newCommand = this.getQueryBuilder().getCommand() + " ALL";
			this.getQueryBuilder().setCommand(newCommand);
			toNextStep();
		} else if (this.getCurrentCheck().contains("*")) {
			throw new SQLException("Syntax error near to " + this.getCurrentCheck());
		}
	}

	@Override
	public void toNextStep() throws SQLException {
		String restOfQuery = this.getQueryBuilder().getQuery().replace("*", "").trim();
		getQueryBuilder().setQuery(restOfQuery);
		FromChecker fromChecker = new FromChecker(getQueryBuilder());
		fromChecker.validateRule();
	}

	/**
	 * This method must be called before the validateRule method.
	 * @return
	 */
	public boolean allAreRequired() {
		if (this.getCurrentCheck().equals("*")) {
			allAreRequired = true;
		}
		return this.allAreRequired;
	}

}
