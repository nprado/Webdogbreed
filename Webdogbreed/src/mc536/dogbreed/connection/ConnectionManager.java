package mc536.dogbreed.connection;
import java.sql.*;

import mc536.dogbreed.configuration.Configuration;

/**
 * Manages database connections.
 */
public class ConnectionManager {
	
	/**
	 * Database connection instance.
	 */
	private static Connection sConnection = null;
	
	/**
	 * Constructor.
	 * Prevents instantiation of this class.
	 */
	protected ConnectionManager() { }
	
	/**
	 * Returns a database connection.
	 * If a connection could not be established, returns null.
	 * 
	 * @return Database connection.
	 */
	public static Connection getConnection() {
		try {
			if (sConnection == null || sConnection.isClosed()) {
				sConnection = DriverManager.getConnection(Configuration.CONNECTION_URI, 
						Configuration.CONNECTION_USER, Configuration.CONNECTION_PASSWORD);
			}
		} catch (SQLException e) {
			sConnection = null;
			System.out.println("Could not connect to the database");
			e.printStackTrace(System.out);
		}
		
		return sConnection;
	}

}
