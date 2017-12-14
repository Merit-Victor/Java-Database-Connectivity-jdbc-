package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class ColumnsChecker extends Interpreter {
	
	/**
	 * 
	 */
	private String[] columnsNames;
	
	/**
	 * 
	 */
	private int wordStartIndex;
	
	/**
	 * @param builder
	 */
	public ColumnsChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if(this.getCurrentCheck().equalsIgnoreCase("from") 
				|| this.getCurrentCheck().equalsIgnoreCase("values")) {
			throw new SQLException("Syntax error: at least one column name is required");
		} else {
			columnsNames = getRegion().split(",");
			for (int i = 0; i < columnsNames.length; i++) {
				columnsNames[i] = columnsNames[i].trim();
				if (columnsNames[i].contains(" ")) {
					throw new SQLException("Syntax error: column's name should not contain spaces");
				}
			}
			this.getQueryBuilder().setSelectedColumnsNames(columnsNames.clone());
			String newCommand = this.getQueryBuilder().getCommand() + " columns";
			this.getQueryBuilder().setCommand(newCommand);
			toNextStep();
		}
	}

	@Override
	public void toNextStep() throws SQLException {
		String toBeRemovedFromQuery = this.getQueryBuilder().getQuery().substring(0, wordStartIndex);
		String restOfQuery = this.getQueryBuilder().getQuery().replace(toBeRemovedFromQuery, "").trim();
		getQueryBuilder().setQuery(restOfQuery);
		ValuesChecker vCheck = new ValuesChecker(getQueryBuilder());
		if(vCheck.isInsertValues()) {
			vCheck.validateRule();
		} else {
			FromChecker fromChecker = new FromChecker(getQueryBuilder());
			fromChecker.validateRule();
		}
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	private String getRegion() throws SQLException {
		String dummy = this.getQueryBuilder().getQuery().toLowerCase();
		wordStartIndex = -1;
		wordStartIndex = dummy.indexOf("values");
		if(wordStartIndex != -1) {
			dummy = this.getQueryBuilder().getQuery().toLowerCase()
					.substring(0, wordStartIndex -1).trim();
		} else {
			wordStartIndex = dummy.indexOf("from");
			if(wordStartIndex != -1) {
				dummy = this.getQueryBuilder().getQuery().toLowerCase()
						.substring(0, wordStartIndex -1).trim();
			}
		}
		if(wordStartIndex != -1) {
			return dummy;
		} else {
			throw new SQLException("Syntax error: missing rest of the statement.");
		}
	}

}
