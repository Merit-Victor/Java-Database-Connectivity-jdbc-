package eg.edu.alexu.csd.oop.jdbc.resultSetMetaData;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.db.backend.DTD;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;
import eg.edu.alexu.csd.oop.jdbc.MyLogger;

public class ResultSetMetaData implements java.sql.ResultSetMetaData {
	private static Logger logger = MyLogger.getInstance();
	private Object[][] selectedRows;
	private QueryBuilder mBuilder;
	private DTD myDtd;
	private ArrayList<String> types;
	
	public ResultSetMetaData(Object[][] selected, QueryBuilder builder) {
		this.selectedRows = selected; 
		this.mBuilder = builder;
		this.myDtd = new DTD(mBuilder.getTable(), mBuilder.getDatabase());
		this.types = myDtd.ReadDTD()[0][0];
	}

	@Override
	public int getColumnCount() throws SQLException {
		// Returns the number of columns in this ResultSet object.
		logger.info("Counting...");
		if (selectedRows != null) {
			return selectedRows[0].length;
		} else {
			return 0;
		}
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		// Gets the designated column's suggested title for use in printouts and
		// displays.
		logger.info("Searching...");
		if (column < selectedRows[0].length && column > 1) {
			return mBuilder.getSelectedColumnsNames()[column - 1];
		} else {
			throw new SQLException("Invalid column index");
		}
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		// Get the designated column's name.
		return getColumnLabel(column);
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		// Retrieves the designated column's SQL type.
		logger.info("Searching...");
		if (column < selectedRows[0].length && column > 1) {
			if (types.get(column - 1).equalsIgnoreCase("varchar")) {
				return Types.VARCHAR;

			} else if (types.get(column - 1).equalsIgnoreCase("int")) {
				return Types.INTEGER;
			}
		} else {
			throw new SQLException("Invalid column index");
		}
		return 0;
	}

	@Override
	public String getTableName(int column) throws SQLException {
		// Gets the designated column's table name.
		logger.info("Searching...");
		return mBuilder.getTable();
	}
	////////////////////////////// unused////////////////////////////////////

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public String getCatalogName(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public String getColumnTypeName(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public int getPrecision(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public int getScale(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public int isNullable(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		throw new UnsupportedOperationException("");

	}
}
