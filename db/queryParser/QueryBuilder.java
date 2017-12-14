package eg.edu.alexu.csd.oop.db.queryParser;

/**
 * @author Merit Victor 
 * Just a set of getter and setter class.
 */
public class QueryBuilder {

	/**
	 * 
	 */
	private String mCommand;

	/**
	 * 
	 */
	private String mTable;

	/**
	 * 
	 */
	private String mDatabase;

	/**
	 * 
	 */
	private String mQuery;
	
	/**
	 * 
	 */
	private String value;

	/**
	 * 
	 */
	private String mColumnOfCondition;
	
	/**
	 * 
	 */
	private char operation;
	
	/**
	 * 
	 */
	private String[] selectedColumnsNames;
	
	/**
	 * 
	 */
	private String[][] changes;
	
	/**
	 * Try parsing to integer and catch exception if it's string.
	 */
	private String[] valuesToBeInserted;
	
	
	/**
	 * 
	 */
	private String[][] tableInformation; 

	/**
	 * @param query
	 */
	public QueryBuilder(String query) {
		this.mQuery = query;
	}

	/**
	 * @param command
	 */
	public void setCommand(String command) {
		this.mCommand = command;
	}

	/**
	 * @return
	 */
	public String getCommand() {
		return this.mCommand;
	}

	/**
	 * @param table
	 */
	public void setTable(String table) {
		this.mTable = table;
	}

	/**
	 * @return
	 */
	public String getTable() {
		return this.mTable;
	}

	/**
	 * @param database
	 */
	public void setDatabase(String database) {
		this.mDatabase = database;
	}

	/**
	 * @return
	 */
	public String getDatabase() {
		return this.mDatabase;
	}

	/**
	 * @param newQuery
	 */
	public void setQuery(String newQuery) {
		this.mQuery = newQuery;
	}

	/**
	 * @return
	 */
	public String getQuery() {
		return this.mQuery;
	}

	/**
	 * @return
	 */
	public String getColumnOfCondition() {
		return this.mColumnOfCondition;
	}

	/**
	 * @param columnOfCondition
	 */
	public void setColumnOfCondition(String columnOfCondition) {
		this.mColumnOfCondition = columnOfCondition;
	}
	
	/**
	 * @param v
	 */
	public void setValue(String val) {
		this.value = val;
	}
	
	/**
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * @return
	 */
	public char getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 */
	public void setOperation(char operation) {
		this.operation = operation;
	}

	/**
	 * @return
	 */
	public String[] getSelectedColumnsNames() {
		return selectedColumnsNames;
	}

	/**
	 * @param selectedColumnsNames
	 */
	public void setSelectedColumnsNames(String[] selectedColumnsNames) {
		this.selectedColumnsNames = selectedColumnsNames;
	}
	
	/**
	 * @return
	 */
	public String[][] getChanges() {
		return changes;
	}

	/**
	 * @param changes
	 */
	public void setChanges(String[][] changes) {
		this.changes = changes;
	}

	/**
	 * @return
	 */
	public String[] getValuesToBeInserted() {
		return valuesToBeInserted;
	}

	/**
	 * @param valuesToBeInserted
	 */
	public void setValuesToBeInserted(String[] valuesToBeInserted) {
		this.valuesToBeInserted = valuesToBeInserted;
	}
	
	/**
	 * @return
	 */
	public String[][] getTableInfo() {
		return tableInformation;
	}

	/**
	 * @param tableInfo
	 */
	public void setTableInfo(String[][] tableInfo) {
		this.tableInformation = tableInfo;
	}
}
