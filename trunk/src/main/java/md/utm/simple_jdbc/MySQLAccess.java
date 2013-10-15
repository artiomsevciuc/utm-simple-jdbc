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
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private static Logger logger = null;
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

	public void insertRow(String name, String surname, int yearOfBirth,
			boolean isStudent) {
		try {
			// PreparedStatements can use variables and are more efficient
			preparedStatement = getConnection()
					.prepareStatement(
							"insert into  university_jdbc.person values (default, ?, ?, ?, ? )");
			// Parameters start with 1
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, surname);
			preparedStatement.setInt(3, yearOfBirth);
			preparedStatement.setBoolean(4, isStudent);
			preparedStatement.executeUpdate();
			logger.info(name + " " + surname + " was inserted successfully");
		} catch (Exception e) {
			logger.error("Statement was not executed: ", e);
		}
	}

	public void removeFromDataBase(String name, String surname) {
		try {
			preparedStatement = getConnection()
					.prepareStatement(
							"delete from university_jdbc.person where person_name= ? and person_surname= ?; ");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, surname);
			preparedStatement.executeUpdate();
			logger.info(name + " " + surname + " was deleted successfully");
		} catch (SQLException e) {
			logger.error("Removin from table failed: ", e);
		}

	}

	public void populateDatabase() {
		try {
			// Setup the connection with the DB

			// Statements allow to issue SQL queries to the database

			insertRow("Ion", "Malai", 1992, true);
			insertRow("Andre", "Stanchevici", 1981, false);
			insertRow("Dan", "Bejenar", 1993, true);

			readFromDataBase();

			resultSet = getStatement().executeQuery(
					"select * from university_jdbc.person");
			writeMetaData(resultSet);
		} catch (Exception e) {
			logger.error("Statement was not executed dut following error: ", e);
		} finally {
			close();
		}

	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// Now get some meta data from the database
		// Result set get the result of the SQL query

		logger.info("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " "
					+ resultSet.getMetaData().getColumnName(i));
		}
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
		List<Person> persons = new ArrayList<Person>();
		System.out.println();
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
			System.out.print("Name: " + name);
			System.out.print(", Surname: " + surname);
			System.out.print(", Year of birth: " + yearOfBirth);
			System.out.print(", Is student: " + isStudent);
			person = new Person(name, surname, yearOfBirth, isStudent);
			persons.add(person);
		}
		System.out.println();
		return persons;
	}

	public static void main(String[] args) {
		MySQLAccess mySQLAccess = new MySQLAccess();
		// mySQLAccess.readDataBase();
		mySQLAccess.readFromDataBase();
		mySQLAccess.removeFromDataBase("Andre", "Stanchevici");
		mySQLAccess.readFromDataBase();

		mySQLAccess.close();
	}

	// You need to close the resultSet
	private void close() {
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
