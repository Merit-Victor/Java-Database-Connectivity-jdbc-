package eg.edu.alexu.csd.oop.jdbc.resultSet;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.db.backend.DTD;
import eg.edu.alexu.csd.oop.db.queryParser.QueryBuilder;
import eg.edu.alexu.csd.oop.jdbc.MyLogger;
import eg.edu.alexu.csd.oop.jdbc.resultSetMetaData.ResultSetMetaData;
import eg.edu.alexu.csd.oop.jdbc.statement.Statement;

public class ResultSet implements java.sql.ResultSet {
	// Statement return Result set when it execute query only.
	private Statement myStatment;
	private int index;
	private ResultSetMetaData resultSetMetaData;
	private Logger logger;
	private Object[][] selectedRows;
	private QueryBuilder mBuilder;
	private DTD myDTD;
	private ArrayList<String> types;

	public ResultSet(Object[][] selected, Statement myStatment, QueryBuilder builder) {
		this.myStatment = myStatment;
		this.selectedRows = selected;
		this.mBuilder = builder;
		this.myDTD = new DTD(this.mBuilder.getTable(), mBuilder.getDatabase());
		this.types = myDTD.ReadDTD()[0][0];
		if(mBuilder.getSelectedColumnsNames() == null) {
			ArrayList<String> columns = myDTD.ReadDTD()[0][1];
			String[] selectedColumnsNames = new String[columns.size()];
			int index1 = 0;
			for(String col: columns) {
				selectedColumnsNames[index1] = col;
				index1++;
			}
			mBuilder.setSelectedColumnsNames(selectedColumnsNames);
		}
		this.index = 0;
		this.logger = MyLogger.getInstance();

	}

	@Override
	public boolean absolute(int row) throws SQLException {
		// Moves the cursor to the given row number in this ResultSet object.
		// If the given row number is negative, the cursor moves to an absolute
		// row position with respect to the end of the result set. For example,
		// calling the method absolute(-1) positions the cursor on the last row;
		// calling the method absolute(-2) moves the cursor to the next-to-last
		// row, and so on.
		// If the row number specified is zero, the cursor is moved to before
		// the first row
		logger.info("\nMoving...");
		if (isClosed()) {
			throw new SQLException("The Statment is closed");
		}
		if (row == 0 || row < -(selectedRows.length)) {
			beforeFirst();
			logger.info("Before first row.");
			return false;
		} else if (row > selectedRows.length) {
			afterLast();
			logger.info("After last row.");
			return false;
		} else {
			if (row < 0) {
				index =  row + selectedRows.length + 1;
			} else {
				index = row;
			}
			logger.info("Success");
			return true;
		}
	}

	@Override
	public void afterLast() throws SQLException {
		// Moves the cursor to the end of this ResultSet object, just after the
		// last row.
		logger.info("\nMoving...");
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}
		if (selectedRows.length != 0) {
			index = selectedRows.length + 1;
			logger.info("Success");
		}
	}

	@Override
	public void beforeFirst() throws SQLException {
		// Moves the cursor to the front of this ResultSet object, just before
		// the first row.
		logger.info("\nMoving...");
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}
		index = 0;
	}

	@Override
	public void close() throws SQLException {
		// Releases this ResultSet object's database and JDBC resources
		// immediately instead
		// of waiting for this to happen when it is automatically closed.
		logger.info("\nClosing ResultSet...");
		this.selectedRows = null;
		this.myStatment = null;
		logger.info("ResultSet closed successfully.");
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		// Maps the given ResultSet column label to its ResultSet column index.
		logger.info("\nSearching...");
		int ret = 0;
		for (String columnName : mBuilder.getSelectedColumnsNames()) {
			if (columnName.equalsIgnoreCase(columnLabel)) {
				return ret;
			}
			ret++;
		}
		throw new SQLException("Column label " + columnLabel + " doesn't exist.");
	}

	@Override
	public boolean first() throws SQLException {
		// Moves the cursor to the first row in this ResultSet object
		logger.info("\nMoving...");
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}
		if (selectedRows.length == 0) {
			return false;
		}
		index = 1;
		logger.info("Moved.");
		return true;

	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		// Retrieves the value of the designated column in the current row of
		// this ResultSet object as an int in the Java programming language
		logger.info("\nSearching...");
		if (!(isAfterLast()) && !(isBeforeFirst() && !(isClosed()))) {
			if (columnIndex > -1 && columnIndex < mBuilder.getSelectedColumnsNames().length) {
				Object temp = this.selectedRows[0][columnIndex];
				System.out.println(index + " " + String.valueOf(temp));
				if (temp == null) {
					return 0;
				} else if(types.get(columnIndex).equals("int")) {
					return Integer.parseInt(String.valueOf(temp));
				} else {
					return 0;
				}
			} else {
				throw new SQLException("Index Out Of Boundry");
			}
		} else {
			throw new SQLException(
					"After last: " + isAfterLast() + "\tBefore first: " + isBeforeFirst() + "\tClose: " + isClosed());
		}
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		// Retrieves the value of the designated column in the current row of
		// this ResultSet object as an int in the Java programming language.
		logger.info("\nSearching...");
		int columnIndex = findColumn(columnLabel);
		return getInt(columnIndex);
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		// Retrieves the value of the designated column in the current row of
		// this ResultSet object as a String in the Java programming language.
		logger.info("\nSearching...");
		if (!(isAfterLast()) && !(isBeforeFirst() && !(isClosed()))) {
			if (columnIndex > -1 && columnIndex < mBuilder.getSelectedColumnsNames().length) {
				Object temp = this.selectedRows[index - 1][columnIndex];
				String ret = null;
				if(types.get(columnIndex).equals("varchar")) {
					return String.valueOf(temp);
				} else {
					return ret;
				}
			} else {
				throw new SQLException("Index Out Of Boundry");
			}
		} else {
			throw new SQLException(
					"After last: " + isAfterLast() + "\tBefore first: " + isBeforeFirst() + "\tClose: " + isClosed());
		}
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		// Retrieves the value of the designated column in the current row of
		// this ResultSet object as a String in the Java programming language.
		logger.info("\nSearching...");
		int columnIndex = findColumn(columnLabel);
		return getString(columnIndex);
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		// Gets the value of the designated column in the current row of this
		// ResultSet object as an Object in the Java programming language
		logger.info("\nSearching...");
		if (!(isAfterLast()) && !(isBeforeFirst() && !(isClosed()))) {
			if (columnIndex > -1 && columnIndex < this.selectedRows.length) {
				return this.selectedRows[index - 1][columnIndex];
			} else {
				throw new SQLException("Index Out Of Boundry");
			}
		} else {
			throw new SQLException(
					"After last: " + isAfterLast() + "\tBefore first: " + isBeforeFirst() + "\tClose: " + isClosed());
		}

	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		// Retrieves the number, types and properties of this ResultSet object's
		// columns.
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}
		resultSetMetaData = new ResultSetMetaData(selectedRows, mBuilder);
		return this.resultSetMetaData;
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		// Retrieves whether the cursor is after the last row in this ResultSet
		// object.
		logger.info("\nChecking...");
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}

		if (index == this.selectedRows.length + 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		// Retrieves whether the cursor is before the first row in this
		// ResultSet object.
		logger.info("\nChecking...");
		if (index == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// Retrieves whether this ResultSet object has been closed.
		logger.info("\nChecking...");
		return (this.selectedRows == null) && (this.myStatment == null);

	}

	@Override
	public boolean isFirst() throws SQLException {
		// Retrieves whether the cursor is on the first row of this ResultSet
		// object.
		logger.info("\nChecking...");
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}

		if (index == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isLast() throws SQLException {
		// Retrieves whether the cursor is on the last row of this ResultSet
		// object.
		logger.info("\nChecking...");
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}

		if (index == this.selectedRows.length) {
			return true;
		}
		return false;
	}

	@Override
	public Statement getStatement() throws SQLException {
		// Retrieves the Statement object that produced this ResultSet object.
		logger.info("\nGetting statement...");
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}
		return this.myStatment;
	}

	@Override
	public boolean last() throws SQLException {
		// Moves the cursor to the last row in this ResultSet object.
		logger.info("\nMoving...");
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}
		if (this.selectedRows.length == 0) {
			return false;
		}

		index = this.selectedRows.length;
		return true;
	}

	@Override
	public boolean next() throws SQLException {
		// Moves the cursor froward one row from its current position.
		logger.info("\nMoving...");
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}
		if (index < this.selectedRows.length ) {
			index++;
			return true;
		}
		afterLast();
		return false;
	}

	@Override
	public boolean previous() throws SQLException {
		// Moves the cursor to the previous row in this ResultSet object.
		// true if the cursor is now positioned on a valid row; false if the
		// cursor is positioned before the first row
		logger.info("\nMoving...");
		if (isClosed()) {
			throw new SQLException("Statement is Closed");
		}
		if (index > 2) {
			index--;
			return true;
		}
		beforeFirst();
		return false;
	}

	///////////////////////////////////// unused////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////// ---->
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public void deleteRow() throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public int getConcurrency() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public String getCursorName() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public int getRow() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public int getType() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void insertRow() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void refreshRow() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean relative(int rows) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean rowUpdated() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateRow() throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException("");

	}

	@Override
	public boolean wasNull() throws SQLException {
		throw new UnsupportedOperationException("");
	}

}
