package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 * This class differs from the ValuesChecker class that the 
 * {@link ValuesChecker} checks the existence of the VALUES word, 
 * and this class checks the correct syntax of writing those values.  
 */
public class ValuesSyntaxChecker extends Interpreter{

	/**
	 * 
	 */
	private String[] values;
	
	/**
	 * @param builder
	 */
	public ValuesSyntaxChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		
		values = this.getQueryBuilder().getQuery().replace(";", "").trim().split(",");
//		String type;
//		
//		for (int i = 0; i < values.length; i++) {
//			values[i] = ((String) values[i]).trim();
//			
//			type = new TypeFinder().findType((String)values[i]);
//			
//			if(!type.equals("int")) {
//				String value = (String)values[i];
//				value = value.replace("\'", "").trim();
//				value = value.replace("\"", "").trim();
//				values[i] = value;
//			} else {
//				values[i] = Integer.parseInt(values[i]);
//			}
//		}
		
		if(getQueryBuilder().getSelectedColumnsNames() != null 
				&& values.length != getQueryBuilder().getSelectedColumnsNames().length) {
			throw new SQLException("Syntax error: each column requires a value.");
		} else {
			this.getQueryBuilder().setValuesToBeInserted(values.clone());
			String newCommand = this.getQueryBuilder().getCommand() + " values";	
			this.getQueryBuilder().setCommand(newCommand);	
		}
	}

	@Override
	public void toNextStep() throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
