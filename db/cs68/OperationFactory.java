package eg.edu.alexu.csd.oop.db.cs68;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.backend.Column;
import eg.edu.alexu.csd.oop.db.backend.Condition;
import eg.edu.alexu.csd.oop.db.backend.MyDatabase;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;
import eg.edu.alexu.csd.oop.db.strategy.Context;
import eg.edu.alexu.csd.oop.db.strategy.DeleteOperation;
import eg.edu.alexu.csd.oop.db.strategy.InsertOperation;
import eg.edu.alexu.csd.oop.db.strategy.SelectOperation;
import eg.edu.alexu.csd.oop.db.strategy.UpdateOperation;

public class OperationFactory {

	Context context;
	QueryBuilder queryBuilder;
	ArrayList<Column> columns;
	Condition condition;
	MyDatabase database;

	public OperationFactory(QueryBuilder queryBuilder, MyDatabase database) {
		this.queryBuilder = queryBuilder;
		this.database = database;
	}

	public Context getOperation(int operationIndex) {
		if (operationIndex == 1) {
			// System.out.println(queryBuilder.getTable());
			context = new Context(new SelectOperation(queryBuilder.getTable()), database);
		} else if (operationIndex == 2) {
			columns = new ArrayList<>();
			String[] array = queryBuilder.getSelectedColumnsNames();
			for (int i = 0; i < array.length; i++) {
				Column column = new Column();
				column.setName(array[i].toLowerCase());
				columns.add(column);
			}
			context = new Context(new SelectOperation(queryBuilder.getTable(), columns), database);
		} else if (operationIndex == 3) {
			columns = new ArrayList<>();
			Object[][] changes = queryBuilder.getChanges();
			for (int i = 0; i < changes[0].length; i++) {
				Column column = new Column();
				column.setName(changes[0][i].toString().toLowerCase());
				column.setValue(changes[1][i].toString().toLowerCase());
				columns.add(column);
			}
			context = new Context(new UpdateOperation(queryBuilder.getTable(), columns), database);
		} else if (operationIndex == 4) {
			condition = new Condition();
			condition.setFirstOperand(queryBuilder.getColumnOfCondition().toLowerCase());
			condition.setOperator(queryBuilder.getOperation());
			condition.setSecondOperand(queryBuilder.getValue().toString().toLowerCase());
			columns = new ArrayList<>();
			Object[][] changes = queryBuilder.getChanges();
			for (int i = 0; i < changes[0].length; i++) {
				Column column = new Column();
				column.setName(changes[0][i].toString().toLowerCase());
				column.setValue(changes[1][i].toString().toLowerCase());
				columns.add(column);
			}
			context = new Context(new UpdateOperation(queryBuilder.getTable(), columns, condition), database);
		} else if (operationIndex == 5) {
			context = new Context(new DeleteOperation(queryBuilder.getTable()), database);
		} else if (operationIndex == 6) {
			condition = new Condition();
			condition.setFirstOperand(queryBuilder.getColumnOfCondition().toLowerCase());
			condition.setOperator(queryBuilder.getOperation());
			condition.setSecondOperand(queryBuilder.getValue().toString().toLowerCase());
			context = new Context(new DeleteOperation(queryBuilder.getTable(), condition), database);
		} else if (operationIndex == 7) {
			columns = new ArrayList<>();
			String[] array = queryBuilder.getSelectedColumnsNames();
			Object[] values = queryBuilder.getValuesToBeInserted();
			for (int i = 0; i < array.length; i++) {
				Column column = new Column();
				column.setName(array[i].toLowerCase());
				column.setValue(values[i].toString().toLowerCase());
				columns.add(column);
			}
			context = new Context(new InsertOperation(queryBuilder.getTable(), columns,false), database);
		} else if (operationIndex == 13) {
			condition = new Condition();
			condition.setFirstOperand(queryBuilder.getColumnOfCondition().toLowerCase());
			condition.setOperator(queryBuilder.getOperation());
			condition.setSecondOperand(queryBuilder.getValue().toString().toLowerCase());
			context = new Context(new SelectOperation(queryBuilder.getTable(), condition), database);
		}else if (operationIndex == 14) {
			columns = new ArrayList<>();
			Object[] values = queryBuilder.getValuesToBeInserted();
			for (int i = 0; i < values.length; i++) {
				Column column = new Column();
				column.setValue(values[i].toString().toLowerCase());
				columns.add(column);
			}
			context = new Context(new InsertOperation(queryBuilder.getTable(), columns,true), database);
		}else if (operationIndex==12){
			condition = new Condition();
			condition.setFirstOperand(queryBuilder.getColumnOfCondition().toLowerCase());
			condition.setOperator(queryBuilder.getOperation());
			condition.setSecondOperand(queryBuilder.getValue().toString().toLowerCase());
			columns = new ArrayList<>();
			String[] array = queryBuilder.getSelectedColumnsNames();
			for (int i = 0; i < array.length; i++) {
				Column column = new Column();
				column.setName(array[i].toLowerCase());
				columns.add(column);
			}
			context = new Context(new SelectOperation(queryBuilder.getTable(), columns,condition), database);
		}

		return context;
	}

}