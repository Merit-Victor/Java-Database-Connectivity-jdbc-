package eg.edu.alexu.csd.oop.db.queryParser;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Merit Victor
 *
 */
public class QueryFactory {

	/**
	 * 
	 */
	private static Map<String, Integer> queries = new HashMap<>();
	
	/**
	 * 
	 */
	static  {
		
		queries.put("SELECT ALL FROM table", 1);
		queries.put("SELECT ALL FROM table WHERE condition", 13);
		queries.put("SELECT columns FROM table", 2);
		queries.put("SELECT columns FROM table WHERE condition", 12);
		queries.put("UPDATE table SET changes", 3);
		queries.put("UPDATE table SET changes WHERE condition", 4);
		queries.put("DELETE ALL FROM table", 5);
		queries.put("DELETE FROM table WHERE condition", 6);
		queries.put("INSERT INTO table columns VALUES values", 7);
		queries.put("INSERT INTO table ALL VALUES values", 14);
		queries.put("CREATE DATABASE database", 8);
		queries.put("DROP DATABASE database", 9);
		queries.put("CREATE TABLE table info", 10);
		queries.put("DROP TABLE table", 11);
	}
	
	/**
	 * @param queryCommand
	 * @return
	 * @throws SQLException 
	 */
	public int getQueryIndex(String queryCommand) throws SQLException {
		
		int index=-2;
		if(queryCommand.equals("CREATE TABLE table")){
			throw new SQLException();
		}else{
	 index = queries.get(queryCommand);
		}
		return index;
	}
}
