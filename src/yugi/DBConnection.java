package yugi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
	
	private final Connection connection;
	
	public DBConnection() throws SQLException {
		 String URL = "jdbc:mysql://localhost:3306/torneo_yugi";
		 String USER = "root";
		 String PASSWORD = "";
		 
		 this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
	public Connection getConnection() {
		return connection;
	}

	/*
	 * private static final String URL = "jdbc:mysql://localhost:3306/torneo_yugi";
	 * private static final String USER = "root";
	 * private static final String PASSWORD = "";
	 * 
	 * public static Connection getConnection() throws SQLException { return
	 * DriverManager.getConnection(URL, USER, PASSWORD); }
	 */

}