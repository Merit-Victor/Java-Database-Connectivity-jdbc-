package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs68.DatabaseImp;
import eg.edu.alexu.csd.oop.jdbc.driver.Driver;
import eg.edu.alexu.csd.oop.jdbc.resultSetMetaData.ResultSetMetaData;

public class Example {

	Driver driver = new Driver();
	Database db = new DatabaseImp();
//	@Test
//	public void testCreatTable() {
//		File dbDir = null;
//		dbDir = new File(db.createDatabase("TestDB", false));
//		Driver driver = new Driver();
//		Properties info = new Properties();
//		info.put("path", dbDir.getAbsoluteFile());
//		Connection conn;
//		Statement sttm;
//		try {
//			conn = driver.connect("jdbc:xmldb://localhost", info);
//			sttm = conn.createStatement();
//		
//			String query = "CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)";
//			sttm.execute(query);
//			query = "DROP Table table_name1";
//			boolean success = sttm.execute(query);
//			Assert.assertEquals(true, success);
////			query = "CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)";
////			boolean created = sttm.execute(query);
////			Assert.assertFalse("Create table succeed when table already exists", created);
////			query = "CREATE TABLE incomplete_table_name1";
////			sttm.execute(query);
////		
////			Assert.fail("Create invalid table succeed");
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//	}
	
	@Test
	public void testScenario3() {
		
		File dbDir = null;
		dbDir = new File(db.createDatabase("TestDB", false));

		Properties info = new Properties();
		info.put("path", dbDir.getAbsoluteFile());
		Connection conn;
		Statement sttm;
		String query;
			
			try {
				conn = driver.connect("jdbc:xmldb://localhost", info);
				sttm = conn.createStatement();
				query = "CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)";
				sttm.execute(query);
				int count1 = sttm.executeUpdate("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
				Assert.assertNotEquals("Insert returned zero rows", 0, count1);
				
//				int count2 = sttm.executeUpdate("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)");
//				Assert.assertNotEquals("Insert returned zero rows", 0, count2);
//				
//				int count3 = sttm.executeUpdate("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)");
//				Assert.assertNotEquals("Insert returned zero rows", 0, count3);
//				
//				int count4 = sttm.executeUpdate("DELETE From table_name1  WHERE coLUmn_NAME2=4");
//				Assert.assertEquals("Delete returned wrong number", 1, count4);
			
//				
//				ResultSet result = sttm.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 < 6");
//				Assert.assertEquals(1, result.findColumn("column_name2"));
//				if(result.absolute(1)){
//				Assert.assertEquals(4, result.getInt("column_name2")); }
//				java.sql.ResultSetMetaData rsmd = result.getMetaData();
				
//				Assert.assertNotEquals(null, result);
//				Assert.assertNotNull("Null result returned", result);
//				Assert.assertEquals("Wrong number of rows", 1, result.length);
//				Assert.assertEquals("Wrong number of columns", 3, result[0].length);
//				
//				int count5 = db.executeUpdateQuery("UPDATE table_name1 SET column_name1='11111111', COLUMN_NAME2=10, column_name3='333333333' WHERE coLUmn_NAME2=5");
//				Assert.assertEquals("Update returned wrong number", 1, count5);
//				
//				Object[][] result2 = db.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 > 4");
//				
//				Assert.assertNotNull("Null result returned", result2);
//				Assert.assertEquals("Wrong number of rows", 2, result2.length);
//				Assert.assertEquals("Wrong number of columns", 3, result2[0].length);
//				
//				Object column_2_object = result2[0][1];
//				
//				if (column_2_object instanceof String)
//					fail("This should be 'Integer', but found 'String'!");
//				else if (column_2_object instanceof Integer) {
//					int column_2 = (Integer) column_2_object;
//					Assert.assertEquals("Select did't return the updated record!", 10, column_2);
//					
//				}
//				else
//					fail("This should be 'Integer', but wh»at is found can't be identified!");
					
			} catch (Throwable e){
				e.printStackTrace();
				Assert.fail("Failed to complete scenario 3"+ e);
			
	}
	
	}
}
