package eg.edu.alexu.csd.oop.db.semanticCheck;

import java.util.List;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class DatabaseSC extends SemanticChecker {
	
	private List<String> databases;
	
	/**
	 * The constructor.
	 * @param builder
	 */
	public DatabaseSC(QueryBuilder builder, List<String> list) {
		super(builder);
		databases = list;
	}

	@Override
	public boolean validate() {
		String databaseName = this.mQueryBuilder.getDatabase();
		for(String database : databases) {
			if(databaseName.equals(database)) {
				return true;
			}
		}
		return false;
	}

}
