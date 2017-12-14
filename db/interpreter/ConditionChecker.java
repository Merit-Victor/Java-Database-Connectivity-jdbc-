package eg.edu.alexu.csd.oop.db.interpreter;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;



/**
 * @author Merit Victor
 *
 */
public class ConditionChecker extends Interpreter {

	/**
	 * 
	 */
	private int condIndex;

	/**
	 * 
	 */
	private String query;

	/**
	 * 
	 */
	private String valueType;

	/**
	 * @param builder
	 */
	public ConditionChecker(QueryBuilder builder) {
		super(builder);
	}

	@Override
	public void validateRule() throws SQLException {
		if (!validNumberOfConditions()) {
			throw new SQLException("Syntax error: wrong condition statement.");
		} else {
			this.getQueryBuilder().setOperation(query.charAt(condIndex));
			String columnOfCondition = query.substring(0, condIndex).trim().toLowerCase();
			this.getQueryBuilder().setColumnOfCondition(columnOfCondition);
			String valueOfCondition = query.substring(condIndex + 1).replace(";", "").trim();
			this.getQueryBuilder().setValue(valueOfCondition);
			String newCommand = this.getQueryBuilder().getCommand() + " condition";
			this.getQueryBuilder().setCommand(newCommand);
//			valueType = new TypeFinder().findType(valueOfCondition);
//			String sub = valueOfCondition.replace(";", "").trim();
//			if (valueType == "int") {
//				try {
//					int value = Integer.parseInt(sub);
//					this.getQueryBuilder().setValue(value);
//					this.getQueryBuilder().setTypeOfValue(valueType);
//					String newCommand = this.getQueryBuilder().getCommand() + " condition";
//					this.getQueryBuilder().setCommand(newCommand);
//				} catch (NumberFormatException e) {
//					throw new SQLException("Syntax error: wrong type of value");
//				}
//
//			} else {
//				sub = sub.replace("\'", "").trim();
//				sub = sub.replace("\"", "").trim();
//				String value = sub;
//				this.getQueryBuilder().setValue(value);
//				this.getQueryBuilder().setTypeOfValue(valueType);
//				String newCommand = this.getQueryBuilder().getCommand() + " condition";
//				this.getQueryBuilder().setCommand(newCommand);
//			}

		}
	}

	@Override
	public void toNextStep() throws SQLException {
		// end of the checker.
	}

	/**
	 * @return
	 */
	private boolean validNumberOfConditions() {
		query = getQueryBuilder().getQuery();
		int condNum = 0;
		for (int i = 0; i < query.length(); i++) {
			if (query.charAt(i) == '>' || query.charAt(i) == '<' 
					|| query.charAt(i) == '=') {
				condNum++;
				condIndex = i;
			}
		}
		return (condNum == 1);
	}

}
