package md.utm.simple_jdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface LocalConnection {
	Connection getConnection() throws SQLException, ClassNotFoundException;
}
