package eg.edu.alexu.csd.oop.db.strategy;

import java.sql.SQLException;

public interface Strategy {

	public Result doOperation(String databaseName) throws SQLException;

}
