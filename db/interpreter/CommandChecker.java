package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.handler.CommandHandlerFactory;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class CommandChecker extends Interpreter {

	/**
	 * 
	 */
	private String[] commands = new String[] { "SELECT", "UPDATE", "DELETE", "INSERT", "CREATE", "DROP" };

	/**
	 * @param builder
	 */
	public CommandChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		boolean result = false;
		for (String command : commands) {
			if (command.equalsIgnoreCase(this.getCurrentCheck())) {
				this.getQueryBuilder().setCommand(command);
				result = true;
				break;
			}
		}
		if (result) {
			toNextStep();
		} else {
			throw new SQLException("Syntax error near to " + this.getCurrentCheck());
		}

	}

	@Override
	public void toNextStep() throws SQLException {
		prepareQueryforNextStep();
		int lenght = getCurrentCheck().length();
		String restOfQuery = this.getQueryBuilder().getQuery().substring(lenght).trim();
		getQueryBuilder().setQuery(restOfQuery);
		CommandHandlerFactory factory = new CommandHandlerFactory(getQueryBuilder());
		this.buildQuery(factory.handleQuery());
	}

}
