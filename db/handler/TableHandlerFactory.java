package eg.edu.alexu.csd.oop.db.handler;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.interpreter.ColumnsChecker;
import eg.edu.alexu.csd.oop.db.interpreter.InformationChecker;
import eg.edu.alexu.csd.oop.db.interpreter.SetChecker;
import eg.edu.alexu.csd.oop.db.interpreter.ValuesChecker;
import eg.edu.alexu.csd.oop.db.interpreter.ValuesSyntaxChecker;
import eg.edu.alexu.csd.oop.db.interpreter.WhereChecker;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;



public class TableHandlerFactory {

	/**
	 * 
	 */
	private QueryBuilder mBuilder;
	
	/**
	 * 
	 */
	private String theNextCheck;
	
	/**
	 * 
	 */
	private String substringToBeChecked;
	
	/**
	 * 
	 */
	private boolean columns;
	
	/**
	 * 
	 */
	private boolean values;
	
	/**
	 * 
	 */
	private boolean createTable;

	/**
	 * @param queryBuilder
	 */
	public TableHandlerFactory(QueryBuilder queryBuilder, String nextCheck) {
		this.mBuilder = queryBuilder;
		this.theNextCheck = nextCheck;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public QueryBuilder handleQuery() throws SQLException {
		if(theNextCheck == null) {
			throw new SQLException("Syntax error: Incomplete query.");
		}if (theNextCheck.equalsIgnoreCase("WHERE")) {
			WhereChecker whereChecker = new WhereChecker(mBuilder);
			whereChecker.validateRule();
		
		} else if (theNextCheck.equalsIgnoreCase("SET")) {			
			SetChecker setChecker = new SetChecker(mBuilder);
			setChecker.validateRule();
		
		} else if(this.theNextCheck.equalsIgnoreCase("VALUES")) {
			String newCommand = mBuilder.getCommand() + " ALL";
			mBuilder.setCommand(newCommand);
			ValuesChecker valuesChecker = new ValuesChecker(mBuilder);
			valuesChecker.validateRule();
			
		} else if (mBuilder.getQuery().trim().contains("(")) {	
			if (bracketIsClosed()) {
				//removing the brackets.				
				String newQuery = mBuilder.getQuery().replace(";", "").trim();
				newQuery = newQuery.replace(substringToBeChecked, "");
				substringToBeChecked = substringToBeChecked.substring(1, substringToBeChecked.length() - 1);
				newQuery = substringToBeChecked + newQuery;

				mBuilder.setQuery(newQuery.trim());
				if(columns) {
					ColumnsChecker columnsChecker = new ColumnsChecker(mBuilder);
					columnsChecker.validateRule();	
				} else if(values) {
					ValuesSyntaxChecker valuesSCh = new ValuesSyntaxChecker(mBuilder);
					valuesSCh.validateRule();
				} else if(createTable) {
					InformationChecker infoChecker = new InformationChecker(mBuilder);
					infoChecker.validateRule();
				}
					
			} else {
				throw new SQLException("Syntax error: wrong number of brackets");
			}
			
		} else {
			throw new SQLException("Syntax error near to " + theNextCheck);
		}
		return mBuilder;
	}
	
	/**
	 * @return
	 * @throws SQLException 
	 */
	private boolean bracketIsClosed() throws SQLException {
		int openNum = 0;
		int closeNum = 0;
		String dummyString = mBuilder.getQuery().toLowerCase();
		int valuesStartIndex = -1;
			
		if(columns) {
			valuesStartIndex = dummyString.indexOf("values");
			if(valuesStartIndex != -1) {
				substringToBeChecked = mBuilder.getQuery().substring(0, valuesStartIndex - 1);
				for(int i = 0; i < substringToBeChecked.length(); i++) {
					if(substringToBeChecked.charAt(i) == '(') {
						openNum++;
					} else if(substringToBeChecked.charAt(i) == ')') {
						closeNum++;
					}
				}
			} else {
				throw new SQLException("Syntax error: VALUES is missing");
			}
		
		} else if(values || createTable) {
			
			substringToBeChecked = mBuilder.getQuery().replace(";", "").trim();
			for(int i = 0; i < substringToBeChecked.length(); i++) {
				if(substringToBeChecked.charAt(i) == '(') {
					openNum++;
				} else if(substringToBeChecked.charAt(i) == ')') {
					closeNum++;
				}
			}
			
		}
		
		return (openNum == closeNum);
	}
	
	/**
	 * 
	 */
	public void setReasonOfBracketsToValues() {
		this.values = true;
	}
	
	/**
	 * 
	 */
	public void setReasonOfBracketsToColumns() {
		this.columns = true;
	}
	
	/**
	 * 
	 */
	public void setReasonOfBracketsToCreateTable() {
		this.createTable = true;
	}
}
