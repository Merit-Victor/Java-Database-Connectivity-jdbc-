package eg.edu.alexu.csd.oop.db.semanticCheck;

import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.oop.db.backend.DTD;
import eg.edu.alexu.csd.oop.db.backend.MyDatabase;
import eg.edu.alexu.csd.oop.db.interpreter.TypeFinder;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class ValuesTypesSC extends SemanticChecker {

	
	/**
	 * 
	 */
	private DTD myDTD;
	
	/**
	 * The constructor.
	 * @param builder
	 */
	public ValuesTypesSC(QueryBuilder builder, List<String> databaseList) {
		super(builder);
		String databaseName = databaseList.get(databaseList.size() - 1);
		myDTD = new DTD(mQueryBuilder.getTable(), databaseName); 
	}

	@Override
	public boolean validate() {
		@SuppressWarnings("unchecked")
		ArrayList<String> dataTypes = myDTD.ReadDTD()[0][0];
		String[] values = mQueryBuilder.getValuesToBeInserted();
		if(values.length != dataTypes.size()) {
			return false;
		}
		for(int i = 0; i < values.length; i++) {
			String datatype = new TypeFinder().findType(values[i]);
			if(!datatype.equals(dataTypes.get(i))) {
				return false;
			}
		}
		return true; 
	}

}
