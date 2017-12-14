package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;


/**
 * @author Merit Victor
 *
 */
public class TableChecker extends Interpreter {

	
	/**
	 * 
	 */
	private boolean tableIsRequired = false;
	
	/**
	 * @param builder
	 */
	public TableChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if(isTableRequired()) {
			String newCommand = this.getQueryBuilder().getCommand() + " TABLE";
			this.getQueryBuilder().setCommand(newCommand);
			toNextStep();
		} else {
			throw new SQLException("Syntax error near to " + this.getCurrentCheck());
		}
		
	}

	@Override
	public void toNextStep() throws SQLException {
		String restOfQuery = getQueryBuilder().getQuery().substring(5).trim();
		getQueryBuilder().setQuery(restOfQuery);
		if(this.getNextCheck() != null && this.getNextCheck() == ";") {
			throw new SQLException("Syntax error: table's name is missed.");
		} else if(this.getNextCheck().trim().contains(" ")) {
			throw new SQLException("Syntax error: table's name should not contain spaces.");
		} else {
			TableSyntaxChecker  tableSyntaxChecker = new TableSyntaxChecker(getQueryBuilder());
			tableSyntaxChecker.validateRule();
		}
	}
	
	/**
	 * @return
	 */
	public boolean isTableRequired() {
		if(this.getCurrentCheck().equalsIgnoreCase("TABLE")) {
			tableIsRequired = true;
		}
		return this.tableIsRequired;
	}
}


