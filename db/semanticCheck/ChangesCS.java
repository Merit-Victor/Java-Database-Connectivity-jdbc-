package eg.edu.alexu.csd.oop.db.semanticCheck;

import java.util.ArrayList;



import java.util.List;

import eg.edu.alexu.csd.oop.db.backend.DTD;
import eg.edu.alexu.csd.oop.db.interpreter.TypeFinder;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class ChangesCS extends SemanticChecker {

	/**
	 * The DTD used for semantic Check.
	 */
	private DTD myDTD;
	
	/**
	 * 
	 */
	private String[][] changes;
	
	/**
	 * @param builder
	 */
	public ChangesCS(QueryBuilder builder, List<String> list) {
		super(builder);
		String databaseName = list.get(list.size() - 1);
		myDTD = new DTD(mQueryBuilder.getTable(), databaseName); 
		changes = mQueryBuilder.getChanges();
	}
	
	public ChangesCS(QueryBuilder builder, String[] columns,
			String[] values , List<String> list) {
		super(builder);
		String databaseName = list.get(list.size() - 1);
		myDTD = new DTD(mQueryBuilder.getTable(), databaseName); 
		changes = new String[2][columns.length];
		changes[0] = columns;
		changes[1] = values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validate() {
		ArrayList<String> columns = myDTD.ReadDTD()[0][1];
		ArrayList<String> dataTypes = myDTD.ReadDTD()[0][0];
		int counter  = 0;
		for(int i = 0; i < changes[0].length; i++) {
			if(columns.contains(changes[0][i])) {
				counter ++;
				String type = new TypeFinder().findType(changes[1][i]);
				if(!type.equals(dataTypes.get(dataTypes.size() - 1 - i))) {
					return false;
				}
			}
		}
		return counter == changes[0].length;
	}

}
