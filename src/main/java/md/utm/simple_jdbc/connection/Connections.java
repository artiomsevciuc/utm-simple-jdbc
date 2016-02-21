package md.utm.simple_jdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;

public enum Connections {
	ORACLE_CONNECTION(new OracleConnection()), MY_SQL_CONNECTION(new MySqlConnection());
	private LocalConnection connection;

	private Connections(LocalConnection connection) {
		this.connection = connection;
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		return connection.getConnection();
	}
}
