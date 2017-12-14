package eg.edu.alexu.csd.oop.db.handler;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public abstract class Handler {

	/**
	 * 
	 */
	private QueryBuilder mQueryBuilder;

	/**
	 * @param qBuilder
	 */
	public Handler(QueryBuilder qBuilder) {
		this.mQueryBuilder = qBuilder;
	}

	/**
	 * @return
	 */
	public QueryBuilder getQueryBuilder() {
		return this.mQueryBuilder;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public abstract void handle() throws SQLException;
}
