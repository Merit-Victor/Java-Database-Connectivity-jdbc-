package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.handler.TableHandlerFactory;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;



/**
 * @author Merit Victor
 *
 */
public class TableSyntaxChecker extends Interpreter{
	
	/**
	 * 
	 */
	private String tableName;
	
	/**
	 * @param builder
	 */
	public TableSyntaxChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		tableName = this.getCurrentCheck().replace(";", "").trim();
		if(tableName.contains("(")) {
			tableName = tableName.substring(0, tableName.indexOf("("));
		}
		if(tableName.contains(" ") || tableName.contains(",")) {	
			throw new SQLException("Syntax error near to " + tableName);
		} else {
			String newCommand = this.getQueryBuilder().getCommand() + " table";
			this.getQueryBuilder().setCommand(newCommand);
			this.getQueryBuilder().setTable(tableName.toLowerCase());
			if(this.getNextCheck() != null && !this.getNextCheck().equals(";")) {
				toNextStep();
			}
		}
	}

	@Override
	public void toNextStep() throws SQLException {
		String restOfQuery = this.getQueryBuilder().getQuery().replace(tableName, "").trim();
		getQueryBuilder().setQuery(restOfQuery);
		TableHandlerFactory factory = new TableHandlerFactory(getQueryBuilder(), this.getNextCheck());
		if(getQueryBuilder().getCommand().contains("INSERT")) {
			factory.setReasonOfBracketsToColumns();
		} else if(getQueryBuilder().getCommand().contains("CREATE")) {
			factory.setReasonOfBracketsToCreateTable();
		}
		this.buildQuery(factory.handleQuery());
	}
}
