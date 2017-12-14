package eg.edu.alexu.csd.oop.db.handler;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.interpreter.ALLChecker;
import eg.edu.alexu.csd.oop.db.interpreter.ColumnsChecker;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;


/**
 * @author Merit Victor
 *
 */
public class SelectHandler extends Handler {

	/**
	 * @param qBuilder
	 */
	public SelectHandler(QueryBuilder qBuilder) {
		super(qBuilder);
	}

	@Override
	public void handle() throws SQLException {
		ALLChecker allChecker = new ALLChecker(this.getQueryBuilder());
		if (allChecker.allAreRequired()) {
			allChecker.validateRule();
		} else {
			ColumnsChecker colChecker = new ColumnsChecker(this.getQueryBuilder());
			colChecker.validateRule();
		}
	}

}
