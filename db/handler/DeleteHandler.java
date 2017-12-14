package eg.edu.alexu.csd.oop.db.handler;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.interpreter.ALLChecker;
import eg.edu.alexu.csd.oop.db.interpreter.FromChecker;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;

/**
 * @author Merit Victor
 *
 */
public class DeleteHandler extends Handler{

	/**
	 * @param qBuilder
	 */
	public DeleteHandler(QueryBuilder qBuilder) {
		super(qBuilder);
	}

	@Override
	public void handle() throws SQLException {
		ALLChecker allChecker = new ALLChecker(this.getQueryBuilder());
		allChecker.validateRule();
		if (!allChecker.allAreRequired()) {
			String dummy = this.getQueryBuilder().getQuery().toLowerCase();
			if(dummy.contains("from") && !dummy.contains("where")) {
				String newCommand = this.getQueryBuilder().getCommand() + " ALL";
				this.getQueryBuilder().setCommand(newCommand);
			}
			FromChecker fromCheck = new FromChecker(getQueryBuilder());
			fromCheck.validateRule();
		}
	}

}
