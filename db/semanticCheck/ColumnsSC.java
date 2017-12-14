package eg.edu.alexu.csd.oop.db.semanticCheck;

import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.oop.db.backend.DTD;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class ColumnsSC extends SemanticChecker {

	/**
	 * The DTD used for semantic Check.
	 */
	private DTD myDTD;
	
	/**
	 * The constructor.
	 * @param builder
	 */
	public ColumnsSC(QueryBuilder builder, List<String> list) {
		super(builder);
		String databaseName = list.get(list.size() - 1);
		myDTD = new DTD(mQueryBuilder.getTable(), databaseName); 
	}

	@Override
	public boolean validate() {
		@SuppressWarnings("unchecked")
		ArrayList<String> columns = myDTD.ReadDTD()[0][1];
		int counter  = 0;
		for(String column : mQueryBuilder.getSelectedColumnsNames()) {
			if(columns.contains(column)) {
				counter ++;
			}
		}
		return (counter == mQueryBuilder.getSelectedColumnsNames().length);
	}

}
