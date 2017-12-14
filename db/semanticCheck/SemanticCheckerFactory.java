package eg.edu.alexu.csd.oop.db.semanticCheck;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.backend.MyDatabase;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;
import eg.edu.alexu.csd.oop.db.queryParser.QueryFactory;

/**
 * @author Merit Victor
 *
 */
public class SemanticCheckerFactory {

	/**
	 * 
	 */
	private boolean result = false;
	private MyDatabase database ;
	/**
	 * 
	 */
	private QueryBuilder builder;

	/**
	 * @param database 
	 * @param index
	 * @throws SQLException 
	 */
	public SemanticCheckerFactory(QueryBuilder qb, MyDatabase database) throws SQLException {
		this.builder = qb;
		this.database = database;
		checkSemanticValidation(new QueryFactory().getQueryIndex(builder.getCommand()));
		
	}

	/**
	 * Observe that the ValuesTypesSC calls the ColumnsSC first, Operations 8
	 * and 10 create database or table Thus they don't have a semantic check.
	 * 
	 * @param index
	 * @return
	 */
	private boolean checkSemanticValidation(int index) {
		switch (index) {
		// INSERT INTO table ALL VALUES values
		case 14:
			result = new TableSC(builder, database.getDatabaseList()).validate() 
						&& new ValuesTypesSC(builder, database.getDatabaseList()).validate();
			break;
		// SELECT ALL FROM table WHERE condition
		case 13:
		// DELETE FROM table WHERE condition
		case 6:
			result = new TableSC(builder, database.getDatabaseList()).validate() 
						&& new ConditionSC(builder, database.getDatabaseList()).validate();
			break;

		// SELECT ALL FROM table
		case 1:
		// DELETE ALL FROM table
		case 5:
			result = new TableSC(builder, database.getDatabaseList()).validate();
			break;

		// SELECT columns FROM table
		case 2:
			result = new TableSC(builder, database.getDatabaseList()).validate() 
						&& new ColumnsSC(builder, database.getDatabaseList()).validate();
			break;

		// UPDATE table SET changes
		case 3:
			result = new TableSC(builder, database.getDatabaseList()).validate()
						&& new ChangesCS(builder, database.getDatabaseList()).validate();
			break;
			
		// INSERT INTO table columns VALUES values
		case 7:
			result = new TableSC(builder, database.getDatabaseList()).validate()
						&& new ChangesCS(builder, builder.getSelectedColumnsNames(), 
								builder.getValuesToBeInserted(), database.getDatabaseList()).validate();
			break;

		// UPDATE table SET changes WHERE condition
		case 4:
			result = new TableSC(builder, database.getDatabaseList()).validate()
						&& new ChangesCS(builder, database.getDatabaseList()).validate()
						&& new ConditionSC(builder, database.getDatabaseList()).validate();
			break;

		// DROP DATABASE database
		case 9:
			result = new DatabaseSC(builder, database.getDatabaseList()).validate();
			break;

		// DROP TABLE table
		case 11:
			result = new TableSC(builder, database.getDatabaseList()).validate();
			break;
			
		// SELECT columns FROM table WHERE condition
		case 12:
			result = new TableSC(builder, database.getDatabaseList()).validate()
					 && new ColumnsSC(builder, database.getDatabaseList()).validate()
					 && new ConditionSC(builder, database.getDatabaseList()).validate();
			break;
		
		default:
			result = true;
			break;
		}
		return result;
	}

	/**
	 * Returns the result.
	 * 
	 * @return boolean.
	 */
	public boolean getResult() {
		return this.result;
	}

}
