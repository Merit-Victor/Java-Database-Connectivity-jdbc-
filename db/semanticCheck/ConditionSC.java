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
public class ConditionSC  extends SemanticChecker {

	/**
	 * The DTD used for semantic Check.
	 */
	private DTD myDTD;
	
	/**
	 * @param builder
	 */
	public ConditionSC(QueryBuilder builder, List<String> list) {
		super(builder);
		String databaseName = list.get(list.size() - 1); 
		myDTD = new DTD(mQueryBuilder.getTable(), databaseName); 
	}

	@Override
	public boolean validate() {
		@SuppressWarnings("unchecked")
		ArrayList<String>[][] columnsAndValues = myDTD.ReadDTD();
		for(int i = 0; i < columnsAndValues[0][1].size(); i++) {
			String column = columnsAndValues[0][1].get(i);
			if(column.equals(mQueryBuilder.getColumnOfCondition())) {
				String type = new TypeFinder().findType(mQueryBuilder.getValue());
				if(type.equals(columnsAndValues[0][0].get(i))) {
					return true;
				}
			}
		}
		return false;
	}

}
