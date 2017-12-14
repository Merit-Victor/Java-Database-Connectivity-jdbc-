package eg.edu.alexu.csd.oop.db.handler;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.interpreter.DatabaseChecker;
import eg.edu.alexu.csd.oop.db.interpreter.IntoChecker;
import eg.edu.alexu.csd.oop.db.interpreter.TableChecker;
import eg.edu.alexu.csd.oop.db.interpreter.TableSyntaxChecker;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;



/**
 * @author Merit Victor
 *
 */
public class CommandHandlerFactory {

	/**
	 * 
	 */
	private QueryBuilder mBuilder;
	
	/**
	 * @param queryBuilder
	 */
	public CommandHandlerFactory(QueryBuilder queryBuilder) {
		this.mBuilder = queryBuilder;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public QueryBuilder handleQuery() throws SQLException {
		String command = this.mBuilder.getCommand();

		if (command.equals("SELECT")) {
			new SelectHandler(mBuilder).handle();
		
		} else if (command.equals("UPDATE")) {
			TableSyntaxChecker tCheck = new TableSyntaxChecker(mBuilder);
			tCheck.validateRule();
		
		} else if (command.equals("DELETE")) {
			new DeleteHandler(mBuilder).handle();
		
		} else if (command.equals("INSERT")) {
			
			IntoChecker intoChecker = new IntoChecker(mBuilder);
			intoChecker.validateRule();
		
		} else if (command.equals("DROP")) {
			DatabaseChecker dbChecker = new DatabaseChecker(mBuilder);
			if(dbChecker.isDatabaseRequired()) {
				dbChecker.validateRule();
			} else {
				TableChecker tableChecker = new TableChecker(mBuilder);
				tableChecker.validateRule();
			}
			
		} else if (command.equals("CREATE")) {
			DatabaseChecker dbChecker = new DatabaseChecker(mBuilder);
			if(dbChecker.isDatabaseRequired()) {
				dbChecker.validateRule();
			} else {
				TableChecker tableChecker = new TableChecker(mBuilder);
				tableChecker.validateRule();
			}
		}
		
		return mBuilder;
	}
}
