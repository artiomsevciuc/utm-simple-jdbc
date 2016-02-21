package md.utm.simple_jdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class OracleConnection implements LocalConnection {
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String connString = "jdbc:oracle:thin:@192.168.56.102:1521:ORCL";
		OracleDataSource ods = new OracleDataSource();
		ods.setURL(connString);
		ods.setUser("dummy_user");
		ods.setPassword("dummy_user");
		return ods.getConnection();
	}
}
