package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;



/**
 * @author Merit Victor
 *
 */
public class InformationChecker extends Interpreter{

	/**
	 * 
	 */
	private String[][] info;
	
	/**
	 * @param builder
	 */
	public InformationChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if(this.getQueryBuilder().getQuery().contains("=") 
				|| this.getQueryBuilder().getQuery().contains(">")
				|| this.getQueryBuilder().getQuery().contains("<")) {
			throw new SQLException("Syntax error: only spaces are allowed");
		} else {
			String newCommand = this.getQueryBuilder().getCommand() + " info";
			this.getQueryBuilder().setCommand(newCommand);
			analysInformation();
			this.getQueryBuilder().setTableInfo(info.clone());
		}
		
	}

	@Override
	public void toNextStep() throws SQLException {
		//end of statement.
		
	}
	
	/**
	 * @throws SQLException
	 */
	private void analysInformation() throws SQLException {
		String substringToBeChecked =  getQueryBuilder().getQuery().replace(";", "").trim(); 
		String[] columnsAndTypes = substringToBeChecked.split(",");
		info = new String[2][columnsAndTypes.length];
		String[] oneColumnAndType;
		
		for(int i = 0; i < columnsAndTypes.length; i++) {
			oneColumnAndType = columnsAndTypes[i].trim().split("\\s+");
	
			if(oneColumnAndType.length > 2) {
				throw new SQLException("Syntax error near to " + columnsAndTypes[i]);
			
			} else if(oneColumnAndType.length < 2) {
				throw new SQLException("Syntax error: each column requires a data type");
			
			} else {
			
				oneColumnAndType[0] = oneColumnAndType[0].trim().toLowerCase();
				oneColumnAndType[1] = oneColumnAndType[1].trim();
				if(oneColumnAndType[0].contains(" ")) {
					throw new SQLException("Syntax error: column name's should not contain spaces");
				} else { 
					info[0][i] = oneColumnAndType[0];
					info[1][i] = oneColumnAndType[1];
				}	
			}		
		}
	}

}
