package md.utm.simple_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class MySQLAccess {
	private Connection connection = null;
	private Statement statement = null;
	private final PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private static Logger logger = null;
	private PreparedStatement insertPreparedStatement = null;
	private PreparedStatement deletePreparedStatement = null;
	static {
		// This will load the MySQL driver, each DB has its own driver
		logger = Logger.getLogger(MySQLAccess.class);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		logger.info("Driver was loaded successfully");
	}

	public Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager
						.getConnection("jdbc:mysql://localhost/university_jdbc?"
								+ "user=root&password=root");
			} catch (SQLException e) {
				logger.error("Failed to create connection", e);
			}
		}
		return connection;
	}

	private PreparedStatement getInsertPreparedStatement() throws SQLException {
		if (insertPreparedStatement == null) {
			// PreparedStatements can use variables and are more efficient
			insertPreparedStatement = getConnection()
					.prepareStatement(
							"insert into  university_jdbc.person values (default, ?, ?, ?, ? )");
		}
		return insertPreparedStatement;
	}

	private PreparedStatement getDeletePreparedStatement() throws SQLException {
		if (deletePreparedStatement == null) {
			// PreparedStatements can use variables and are more efficient
			deletePreparedStatement = getConnection()
					.prepareStatement(
							"delete from university_jdbc.person where person_name= ? and person_surname= ?; ");
		}
		return deletePreparedStatement;
	}

	public Statement getStatement() {
		if (statement == null) {
			try {
				statement = getConnection().createStatement();
			} catch (SQLException e) {
				logger.error("Failed to create statement", e);
			}
		}
		return statement;
	}

	/**
	 * Insert a row in the database
	 * 
	 * @param name
	 * @param surname
	 * @param yearOfBirth
	 * @param isStudent
	 * @return how many rows were updated
	 */
	public int insertRow(String name, String surname, int yearOfBirth,
			boolean isStudent) {
		int updatedRows = -1;
		try {
			// Parameters start with 1
			getInsertPreparedStatement().setString(1, name);
			getInsertPreparedStatement().setString(2, surname);
			getInsertPreparedStatement().setInt(3, yearOfBirth);
			getInsertPreparedStatement().setBoolean(4, isStudent);
			updatedRows = getInsertPreparedStatement().executeUpdate();
		} catch (Exception e) {
			logger.error("Statement was not executed: ", e);
		}
		return updatedRows;
	}

	/**
	 * Remove line from database in case of the found object
	 * 
	 * @param name
	 * @param surname
	 * @return number of updated rows
	 */
	public int removeFromDataBase(String name, String surname) {
		int updatetRows = -1;
		try {
			getDeletePreparedStatement().setString(1, name);
			getDeletePreparedStatement().setString(2, surname);
			updatetRows = getDeletePreparedStatement().executeUpdate();
		} catch (SQLException e) {
			logger.error("Removin from table failed: ", e);
		}
		return updatetRows;
	}

	/**
	 * Find a person but using simple statement, not prepared
	 * 
	 * @param personName
	 * @param personSurname
	 * @return list of persons
	 */
	public List<Person> findPerson(String personName, String personSurname) {
		// Result set get the result of the SQL query
		List<Person> persons = new ArrayList<Person>();
		String queryString = "select * from university_jdbc.person where person.person_name='%s' and person.person_surname='%s' ";
		try {

			resultSet = getStatement().executeQuery(
					String.format(queryString, personName, personSurname));
			persons = writeResultSet(resultSet);
		} catch (SQLException e) {
			logger.error("Reading all lines from table failed: ", e);
		}
		return persons;
	}

	public List<Person> readFromDataBase() {
		// Result set get the result of the SQL query
		List<Person> persons = new ArrayList<Person>();
		try {
			resultSet = getStatement().executeQuery(
					"select * from university_jdbc.person");
			persons = writeResultSet(resultSet);
		} catch (SQLException e) {
			logger.error("Reading all lines from table failed: ", e);
		}
		return persons;
	}

	private List<Person> writeResultSet(ResultSet resultSet)
			throws SQLException {
		// ResultSet is initially before the first data set
		String result = "Name: %s, Surname: %s, Year of birth: %s, Is student: %s";
		List<Person> persons = new ArrayList<Person>();
		Person person;
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String name = resultSet.getString("person_name");
			String surname = resultSet.getString("person_surname");
			int yearOfBirth = resultSet.getInt("person_year_of_birth");
			boolean isStudent = resultSet.getBoolean("person_is_student");
			person = new Person(name, surname, yearOfBirth, isStudent);
			persons.add(person);
			logger.info(String.format(result, name, surname, yearOfBirth,
					isStudent));
		}
		return persons;
	}

	/**
	 * Show the meta data of the table
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public void showResultSetMetadata(ResultSet info) throws SQLException {
		// Now get some meta data from the database
		// Result set get the result of the SQL query
		logger.info("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			logger.info("Column " + i + " "
					+ resultSet.getMetaData().getColumnName(i));
		}
	}

	/**
	 * You need to close the resultSet, call it after each end of the
	 * {@link MySQLAccess} class
	 * 
	 */
	public void close() {
		try {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}

			if (statement != null && !statement.isClosed()) {
				statement.close();
			}

			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
			logger.error("Closing all connections failed", e);
		}
	}
}
