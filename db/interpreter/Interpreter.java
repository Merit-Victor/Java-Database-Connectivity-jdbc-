package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;



public abstract class Interpreter {

	/**
	 * 
	 */
	private String[] parts;
	
	/**
	 * 
	 */
	private String currentCheck;
	
	/**
	 * 
	 */
	private String nextCheck;
	
	/**
	 * 
	 */
	private String query;
	
	/**
	 * 
	 */
	private QueryBuilder mQueryBuilder;

	/**
	 * Constructor.
	 * 
	 * @param builder
	 *            query to be build.
	 */
	public Interpreter(QueryBuilder builder) {
		this.query = builder.getQuery();
		this.parts = query.split("\\s+");
		this.currentCheck = parts[0].trim();
		if (parts.length > 1) {
			this.nextCheck = parts[1].trim();
		} else {
			this.nextCheck = null;
		}
		this.mQueryBuilder = builder;
	}

	/**
	 * @return
	 */
	public String getCurrentCheck() {
		return currentCheck;
	}

	/**
	 * @return
	 */
	public String getNextCheck() {
		return nextCheck;
	}

	/**
	 * @return
	 */
	public QueryBuilder getQueryBuilder() {
		return this.mQueryBuilder;
	}

	/**
	 * @param qBuilder
	 */
	public void buildQuery(QueryBuilder qBuilder) {
		this.mQueryBuilder = qBuilder;
	}

	/**
	 * @throws SQLException
	 */
	public abstract void validateRule() throws SQLException;

	/**
	 * @throws SQLException
	 */
	public abstract void toNextStep() throws SQLException;
	
	/**
	 * 
	 */
	public void prepareQueryforNextStep() {
		parts[0] = parts[0].toLowerCase();
		String newQuery = "";
		for(String p: parts) {
			newQuery += p + " ";
		}
		newQuery = newQuery.trim();
		mQueryBuilder.setQuery(newQuery);
	}
}
