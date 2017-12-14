package eg.edu.alexu.csd.oop.db.queryParser;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.backend.MyDatabase;
import eg.edu.alexu.csd.oop.db.interpreter.SyntaxInterpreter;
import eg.edu.alexu.csd.oop.db.semanticCheck.SemanticCheckerFactory;

/**
 * @author Merit Victor
 *
 */
public class QueryValidator {

	/**
	 * 
	 */
	private QueryBuilder queryBuilder;

	/**
	 * 
	 */
	private SyntaxInterpreter mSyntaxInterpreter;
	
	/**
	 * @param query
	 * @param database 
	 * @throws SQLException 
	 */
	public QueryValidator(String query, MyDatabase database) throws SQLException {
		this.queryBuilder = new QueryBuilder(query.trim());
		this.mSyntaxInterpreter = new SyntaxInterpreter(queryBuilder);
		mSyntaxInterpreter.checkSyntax();
//		SemanticCheckerFactory factory = new SemanticCheckerFactory(queryBuilder,database);
//		if(!factory.getResult()) {
//			throw new SQLException("Semantic check failed. " + query);
//		}
	}
	
	/**
	 * @return
	 */
	public QueryBuilder getQueryBuilder() {
		return this.queryBuilder;
	}
	
}
