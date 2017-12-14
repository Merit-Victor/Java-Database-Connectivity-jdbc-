package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.handler.TableHandlerFactory;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class ValuesChecker extends Interpreter {

	private boolean isInsertValues = false;
	private String currentCheck;
	
	/**
	 * @param builder
	 */
	public ValuesChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if (isInsertValues()) {
			String newCommand = this.getQueryBuilder().getCommand() + " VALUES";
			this.getQueryBuilder().setCommand(newCommand);
			toNextStep();
		} else if ((this.getCurrentCheck().toUpperCase().contains("VALUES") 
				|| "VALUES".contains(getCurrentCheck().toUpperCase())) 
				&& !"VALUES".equalsIgnoreCase(getCurrentCheck())) {
			System.out.println("I'M HERE");
			throw new SQLException("Syntax error near to " + this.getCurrentCheck());
		}
		
	}

	@Override
	public void toNextStep() throws SQLException {
		prepareQueryforNextStep();
		String restOfQuery = this.getQueryBuilder().getQuery().replace("values", "").trim();
		getQueryBuilder().setQuery(restOfQuery);
		TableHandlerFactory thf = new TableHandlerFactory(getQueryBuilder(), 
				getCurrentCheck().replaceAll(currentCheck, ""));
		thf.setReasonOfBracketsToValues();
		thf.handleQuery();
	}
	
	/**
	 * This method must be called before the validateRule method.
	 * @return
	 */
	public boolean isInsertValues() {
		currentCheck = getCurrentCheck();
		if(currentCheck.contains("(")) {
			currentCheck = currentCheck.substring(0, currentCheck.indexOf("("));
		}
		if (currentCheck.equalsIgnoreCase("VALUES")) {
			isInsertValues = true;
		}
		return this.isInsertValues;
	}


}
