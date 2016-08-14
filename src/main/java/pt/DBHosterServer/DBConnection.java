package pt.DBHosterServer;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

public class DBConnection {
	private String url = null;
	private String user = null;
	private String pass = null;
	private String jdbcPath = null;
	private Connection conn = null;

	public DBConnection(String jdbcPath, String url, String user, String pass) {
		this.jdbcPath = jdbcPath;
		this.url = url;
		this.user = user;
		this.pass = pass;
	}

	public Connection createConnect() {
		// Register JDBC driver
		try {
			Class.forName(jdbcPath);
		}

		catch (ClassNotFoundException e) {
			System.err.println("Cannot register JDBC Driver" + e.getMessage());
			e.printStackTrace();
		}

		// Open connection
		System.out.println("Connecting to a selected database...");

		try {
			conn = (Connection) DriverManager.getConnection(url, user, pass);
		}

		catch (SQLException e) {
			System.err.println("Cannot open connection" + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("Connected database successfully...");

		return conn;
	}
	
	public Connection getConnection() {
		return conn;
	}

}
