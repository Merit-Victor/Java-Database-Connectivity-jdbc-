package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class SyntaxInterpreter {
	
	/**
	 * 
	 */
	private QueryBuilder mQueryBuilder;
	
	/**
	 * @param builder
	 */
	public SyntaxInterpreter(QueryBuilder builder) {
		this.mQueryBuilder = builder;
	}

	/**
	 * @return
	 */
	public void checkSyntax() throws SQLException {
		CommandChecker commandChecker = new CommandChecker(mQueryBuilder);
		commandChecker.validateRule();
	}
}
