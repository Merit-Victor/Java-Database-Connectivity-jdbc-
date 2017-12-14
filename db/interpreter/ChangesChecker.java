package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;


/**
 * @author Merit Victor
 *
 */
public class ChangesChecker extends Interpreter{
	
	/**
	 * 
	 */
	private String[][] changes;
	
	/**
	 * @param builder
	 */
	public ChangesChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if(!this.getQueryBuilder().getQuery().contains("=")) {
			throw new SQLException("Syntax error: must write '=' between the column and the value");
		} else {
			String newCommand = this.getQueryBuilder().getCommand() + " changes";
			this.getQueryBuilder().setCommand(newCommand);
			analysChanges();
			this.getQueryBuilder().setChanges(changes.clone());
			toNextStep();
		}
		
	}

	@Override
	public void toNextStep() throws SQLException {
		String dummyString = getQueryBuilder().getQuery().toLowerCase();
		int whereStartIndex = dummyString.indexOf("where");
		if(whereStartIndex != -1) {
			String toBeRemovedFromQuery = getQueryBuilder().getQuery().substring(0, whereStartIndex - 1);
			String restOfQuery = this.getQueryBuilder().getQuery().replace(toBeRemovedFromQuery, "").trim();
			getQueryBuilder().setQuery(restOfQuery);
			WhereChecker whereChecker = new WhereChecker(getQueryBuilder());
			whereChecker.validateRule();
		}
		
	}

	/**
	 * @throws SQLException
	 */
	private void analysChanges() throws SQLException {
		String dummyString = getQueryBuilder().getQuery();
		int whereStartIndex = dummyString.toLowerCase().indexOf("where");
		String substringToBeChecked;
		if(whereStartIndex != -1) {
			substringToBeChecked = getQueryBuilder().getQuery().substring(0, whereStartIndex - 1);
			if(substringToBeChecked.contains(">") || substringToBeChecked.contains("<")) {
				throw new SQLException("Syntax error: changes are made using '=' only.");
			}
		} else {
			substringToBeChecked = dummyString.replace(";", "").trim();
		}
		String[] columnsAndValues = substringToBeChecked.split(",");
		changes = new String[2][columnsAndValues.length];
		String[] oneColumnAndValue;
		
		for(int i = 0; i < columnsAndValues.length; i++) {
			oneColumnAndValue = columnsAndValues[i].trim().split("=");
			
			if(oneColumnAndValue.length > 2) {
				throw new SQLException("Syntax error near to " + columnsAndValues[i]);
			
			} else if(oneColumnAndValue.length < 2) {
				throw new SQLException("Syntax error: each column requires a value");
			
			} else {
			
				oneColumnAndValue[0] = oneColumnAndValue[0].trim().toLowerCase();
				oneColumnAndValue[1] = oneColumnAndValue[1].trim();
				if(oneColumnAndValue[0].contains(" ")) {
					throw new SQLException("Syntax error: column name's should not contain spaces");
				} else { 
					changes[0][i] = oneColumnAndValue[0];
//					String type = new TypeFinder().findType(oneColumnAndValue[1]);
//					if(type.equals("int")) {
//						try {
//							changes[1][i] = Integer.parseInt(oneColumnAndValue[1]);	
//						} catch (NumberFormatException e) {
//							throw new SQLException(
//									"Syntax error: wrong type of value in " + oneColumnAndValue[1]);
//						}
//					} else {
//						oneColumnAndValue[1] = oneColumnAndValue[1].replace("\'", "").trim();
//						oneColumnAndValue[1] = oneColumnAndValue[1].replace("\"", "").trim();
						changes[1][i] = oneColumnAndValue[1];
				//	}
				}	
			}		
		}
	}
}

