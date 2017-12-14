package eg.edu.alexu.csd.oop.db.cs68;

import java.sql.SQLException;

public class manager {
	DatabaseImp db;

	OperationFactory operationFactory;

	public manager(DatabaseImp db) {
		this.operationFactory = db.getOperationFactory();
		this.db = db;
	}

	public void executeAll(String query) {

		int operationIndex;
		try {
			operationIndex = db.getOperationIndex(query);
			switch (operationIndex) {
			case 1:
			case 2:
			case 12:
			case 13:
				try {
					System.out.println("Your selected columns");
					db.executeQuery(query);
				} catch (SQLException e) {
					System.err.println(e);
				}
				break;
			case 3:
			case 4:
				try {
					System.out.println("The number of columns updated");
					int count = db.executeUpdateQuery(query);
					if (count != 0) {
						System.out.println(count);
					}
				} catch (SQLException e) {
					System.err.println(e);
				}
				break;
			case 5:
			case 6:
				try {
					System.out.println("The number of columns deleted");
					int count = db.executeUpdateQuery(query);
					if (count != 0) {
						System.out.println(count);
					}
				} catch (SQLException e) {
					System.err.println(e);
				}
				break;
			case 7:
			case 14:
				try {
					System.out.println("The number of columns inserted");
					int count = db.executeUpdateQuery(query);
					if (count != 0) {
						System.out.println(count);
					}
				} catch (SQLException e) {
					System.err.println(e);
				}
				break;
			case 8:
				try {
					if (db.executeStructureQuery(query)) {
						System.out.println("Your database has been created succesfully");
					} else {
						System.out.println("Sorry,there was an error , please try again");
					}
				} catch (SQLException e) {
					System.err.println(e);
				}
				break;
			case 9:
				try {
					if (db.executeStructureQuery(query)) {
						System.out.println("Your database has been droped succesfully");
					} else {
						System.out.println("Sorry,there was an error , please try again");
					}
				} catch (SQLException e) {
					System.err.println(e);
				}
				break;
			case 10:
				try {
					if (db.executeStructureQuery(query)) {
						System.out.println("Your table has been droped succesfully");
					} else {
						System.out.println("Sorry,there was an error , please try again");
					}

				} catch (SQLException e) {
					System.err.println(e);
				}
				break;
			case 11:
				try {
					if (db.executeStructureQuery(query)) {
						System.out.println("Your table has been droped succesfully");
					} else {
						System.out.println("Sorry,there was an error , please try again");
					}
				} catch (SQLException e) {
					System.err.println(e);
				}
				break;
			}
		} catch (SQLException e1) {
			System.err.println(e1);
		}

	}

}
