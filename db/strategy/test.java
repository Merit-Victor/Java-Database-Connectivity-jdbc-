package eg.edu.alexu.csd.oop.db.strategy;

import java.sql.SQLException;


import eg.edu.alexu.csd.oop.db.Database;

import eg.edu.alexu.csd.oop.db.cs68.DatabaseImp;

public class test {

	public static void main(String[] args) throws SQLException {
		
//		ArrayList<Column> columnUpdated = new ArrayList();
//		Column column = new Column();
//		column.setName("name");
//		column.setValue("lolo");
//		columnUpdated.add(column);
//		Column column1 = new Column();
//		column1.setName("ID");
//		column1.setValue(3);
//		columnUpdated.add(column1);
//		Condition condition = new Condition();
//		condition.setFirstOperand("ID");
//		condition.setOperator('>');
//		condition.setSecondOperand("26");
//		Context context= new Context(new UpdateOperation("table1", columnUpdated,condition));
		
//		ArrayList<Column> columnSelected= new ArrayList();
//		Column column2 = new Column();
//		column2.setName("name");
//		columnSelected.add(column2);
//		Column column3= new Column();
//		column3.setName("ID");
//		columnSelected.add(column3);
//		Condition condition = new Condition();
//		condition.setFirstOperand("ID");
//		condition.setOperator('=');
//		condition.setSecondOperand("20");
//		Context context= new Context(new SelectOperation("table1"));
//		
		
//		ArrayList<Column> columnsInserted= new ArrayList();
//		Column column4 = new Column();
//		column4.setName("name");
//		column4.setValue("salwa");
//		columnsInserted.add(column4);
//		Column column5= new Column();
//		column5.setName("ID");
//		column5.setValue(88);
//		columnsInserted.add(column5);
//		Column column6= new Column();
//		column6.setName("school");
//		column6.setValue("d");
//		//columnsInserted.add(column6);
//		Context context= new Context(new InsertOperation("table1",columnsInserted));
//	context.executeStrategy();
		DatabaseImp database = new DatabaseImp();
		System.out.println(database.createDatabase("test", false));
//		System.out.println(database.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar , column_name2 int, column_name3 varchar)"));
//     	System.out.println(database.executeUpdateQuery("INSERT INTO * table_name2(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//      System.out.println(database.executeUpdateQuery("INS ERT INTO table_name3(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//	    System.out.println(database.executeStructureQuery("CREATE TABLE table_name4(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name4(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//	    System.out.println(database.executeUpdateQuery("UPDATE table_name4 SET column_name1=='1111111111', COLUMN_NAME2=2222222, column_name3='333333333'"));
//	    System.out.println(database.executeStructureQuery("CREATE TABLE table_name5(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name5(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//	    System.out.println(database.executeUpdateQuery("UPDATE table_name5 SET column_name1='1111111111', COLUMN_NAME2='2222222', column_name3='333333333'"));
//	    System.out.println(database.executeStructureQuery("CREATE TABLE table_name6(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name6(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//	    System.out.println(database.executeUpdateQuery("UPDATE table_name6 SET column_name1='1111111111', COLUMN_NAME2=2222222, column_name3='333333333"));
//	    System.out.println(database.executeStructureQuery("CREATE TABLE table_name7(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name7(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4"));
//	    System.out.println(database.executeStructureQuery("CREATE TABLE table_name8(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name8(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 4, 'value2')"));
//		System.out.println(database.executeStructureQuery("CREATE TABLE table_name9(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//		System.out.println(database.executeStructureQuery("INSERT INTO ta.ble_name9(column_NAME1, COLUMN_name3, column_name.2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeStructureQuery("Create TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeUpdateQuery("INSERt INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VAlUES ('value2', 'value4', 5)"));	
//		System.out.println(database.executeUpdateQuery("UPDATE table_namE1 SET column_name1='1',COLUMN_NAME2=2,column_name3='3' WHERE coLUmn_NAME3='VALUE3'"));
//		String path = database.createDatabase("sample" + System.getProperty("file.separator") + "rita", false);
		
		
//		System.out.println(database.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar , column_name2 int, column_name3 varchar) "));
//		System.out.println(database.executeUpdateQuery("INSERT INTO * table_name2(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeUpdateQuery("INS ERT INTO table_name3(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeStructureQuery("CREATE TABLE table_name4(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name4(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeUpdateQuery("UPDATE table_name4 SET column_name1=='1111111111', COLUMN_NAME2=2222222, column_name3='333333333'"));
//		System.out.println(database.executeStructureQuery("CREATE TABLE table_name5(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name5(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeUpdateQuery("UPDATE table_name5 SET column_name1='1111111111', COLUMN_NAME2='2222222', column_name3='333333333'"));
//		System.out.println(database.executeStructureQuery("CREATE TABLE table_name6(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name6(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeUpdateQuery("UPDATE table_name6 SET column_name1='1111111111', COLUMN_NAME2=2222222, column_name3='333333333"));
//	    System.out.println(database.executeStructureQuery("CREATE TABLE table_name7(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name7(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4"));
//		System.out.println(database.executeStructureQuery("CREATE TABLE table_name8(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name8(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 4, 'value2')"));
//		System.out.println(database.executeStructureQuery("CREATE TABLE table_name9(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO ta.ble_name9(column_NAME1, COLUMN_name3, column_name.2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeStructureQuery("Create TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeUpdateQuery("INSERt INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VAlUES ('value2', 'value4', 5)"));
//	System.out.println(database.executeUpdateQuery("UPDATE table_namE1 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME3='VALUE3'"));
//		System.out.println(database.executeStructureQuery("CREATE DATABASE TestDB"));
//		System.out.println(database.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//		System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 5)"));
//		System.out.println(database.executeStructureQuery("DROP TABLE table_name1"));
// 		System.out.println(database.executeUpdateQuery("UPDATE table_name1 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME3='VALUE3'"));
//	    System.out.println(database.executeStructureQuery("CREATE DATABASE TestDB"));
//	    System.out.println(database.executeStructureQuery("DROP DATABASE TestDB"));
//	    System.out.println(database.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//	    System.out.println(database.executeStructureQuery("CREATE DATABASE TestDB"));
//	    System.out.println(database.executeStructureQuery("CREATE TABLE table_name2(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name2(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name2(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name2(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)"));
//	    System.out.println(database.executeUpdateQuery("DELETE From table_name2  WHERE coLUmn_NAME1='VAluE1'"));
	                                                    
//	    System.out.println(database.executeUpdateQuery("UPDATE table_name2 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME2=4"));
//        System.out.println(database.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)"));
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)"));
//	    System.out.println(database.executeUpdateQuery("DELETE From table_name1 WHERE coLUmn_NAME2=4"));
//	    System.out.println(database.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 < 6").length);
//	    System.out.println(database.executeUpdateQuery(" UPDATE table_name1 SET column_name1='value1', column_name2=15, column_name3='value2'"));
	    
//	    System.out.println(database.executeUpdateQuery("INSERT INTO table_name2 VALUES ('value1', 3,'value3')"));
	
	
//	System.out.println(database.executeStructureQuery("CREATE TABLE incomplete_table_name1"));
//		database.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
	
//	Database db = new DatabaseImp();
//		db.createDatabase("sample/76920.40168135888/TestDB_Select", true);
//		db.executeStructureQuery("CREATE DATABASE testdb_select;");
//		//db.executeStructureQuery("DROP DATABASE testdb_select ");
//		
//		System.out.println(db.executeStructureQuery(
//				"CREATE TABLE table_name13(column_name1 varchar, column_name2 int, column_name3 varchar)"));
//			
//		int t=db.executeUpdateQuery(
//			
//				"INSERT INTO table_name13(column_NAME1, column_name2, COLUMN_name3) VALUES ('value1', 4, 'value3')");
//		db.executeUpdateQuery(
//				"INSERT INTO table_name13(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 5)");
//		db.executeUpdateQuery(
//				"INSERT INTO table_name13(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value5', 'value6', 6)");
//		
//		
//		db.executeQuery("SELECT column_name1 FROM table_name13 WHERE coluMN_NAME2 < 5");
//		
//		db.createDatabase("sample/76920.40168135888/TestDB_Select", true);
//		db.executeStructureQuery("CREATE DATABASE testdb_select;");
//		db.executeStructureQuery(
//				"CREATE TABLE table_name13(column_name1 varchar, column_name2 int, column_name3 varchar)");
//		db.executeUpdateQuery(
//				"INSERT INTO table_name13(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//		db.executeUpdateQuery(
//				"INSERT INTO table_name13(column_NAME1, column_name2, COLUMN_name3) VALUES ('value1', 4, 'value3')");
//		db.executeUpdateQuery(
//				"INSERT INTO table_name13(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 5)");
//		db.executeUpdateQuery(
//				"INSERT INTO table_name13(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value5', 'value6', 6)");
//		Object[][]l=db.executeQuery("SELECT column_name1 FROM table_name13 WHERE coluMN_NAME2 < 5");
//		System.out.println(l[0][0]);
//		

	Database db = new DatabaseImp();
	db.executeStructureQuery("CREATE DATABASE testdb_select;");
	 
	db.executeStructureQuery(
			"CREATE TABLE table_name13(column_name1 varchar, column_name2 int, column_name3 varchar)");

	int t=db.executeUpdateQuery(

			"INSERT INTO table_name13 VALUES ('value1',5, 'value3')");
	db.executeUpdateQuery(
			"INSERT INTO table_name13 VALUES ('value2',5, 'value4')");
	db.executeUpdateQuery(
			"INSERT INTO table_name13(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value5', 'value6', 5)");
	db.createDatabase("sample/76920.40168135888/TestDB_Select", true);
	db.executeStructureQuery("CREATE DATABASE testdb_select;");
	db.executeStructureQuery(
			"CREATE TABLE table_name13(column_name1 varchar, column_name2 int, column_name3 varchar)");

	t=db.executeUpdateQuery("UPDATE table_name13 SET column_name1 = 'Alfred Schmidt' WHERE column_NAME2 = 5;");
	System.out.println(t);
//	System.out.println(database.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
	}
		//	INSERT INTO table_name13(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)
//	 UPDATE table_name9 SET column_name1='value1', column_name2=15, column_name3='value2'

}
