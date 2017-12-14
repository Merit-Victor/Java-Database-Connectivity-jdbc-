package eg.edu.alexu.csd.oop.db.semanticCheck;

import java.util.List;

import eg.edu.alexu.csd.oop.db.backend.Table;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class TableSC extends SemanticChecker {

	private List<String> databases;
	/**
	 * The constructor.
	 * 
	 * @param builder
	 */
	public TableSC(QueryBuilder builder, List<String> list) {
		super(builder);
		databases  = list;
	}

	@Override
	public boolean validate() {
		String tableName = this.mQueryBuilder.getTable();
		String databaseName = databases.get(databases.size() - 1);
		Table tableToBeChecked = new Table(tableName, databaseName);
		return tableToBeChecked.isExist();
	}

}
