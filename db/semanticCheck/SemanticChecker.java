package eg.edu.alexu.csd.oop.db.semanticCheck;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public abstract class SemanticChecker {
	
	/**
	 * 
	 */
	protected QueryBuilder mQueryBuilder;
	
	/**
	 * @param builder
	 */
	public SemanticChecker(QueryBuilder builder) {
		this.mQueryBuilder = builder;
		
	}
	
	/**
	 * @return
	 */
	public abstract boolean validate();

}
