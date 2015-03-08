package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URI = "jdbc:mysql://localhost:3306/";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";
	private static Connection connection;

	public static Connection getConnection() {
		//openConnection();
		return connection;
	}

	/**
	 * Open connection.
	 * @return true if successfully
	 */
	public static boolean openConnection() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URI, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Error");
			return false;
		}
		return true;
	}
	
	/**
	 * Open connection for creating table
	 * @param databaseName the name of the database
	 * @return true if open successfully
	 */
	public static boolean openConnectionForCreatingTable(String databaseName) {
		try {
			String newURI = URI + databaseName;
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(newURI, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Driver not found");
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Error");
			return false;
		}
		return true;
	}
}
