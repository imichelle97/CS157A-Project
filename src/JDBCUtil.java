import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.Connection;


public class JDBCUtil {
	
	private static final String dbPropertiesFile = "db.properties";
	private static Properties prop = new Properties();
	private static FileInputStream filesys;
	
	/**
	 * DriverManager Connection
	 */
	public static Connection getConnectionByDriverManager() {
		Connection conn = null;
		
		try {
			filesys = new FileInputStream(dbPropertiesFile);
			prop.load(filesys);
			
			/*
			 * Load JDBC driver
			 */
			Class.forName(prop.getProperty("MYSQL_DB_DRIVER_CLASS"));
			
			/*
			 * Get connection from driver manager
			 */
			conn = DriverManager.getConnection(prop.getProperty("MYSQL_DB_URL"),
					prop.getProperty("MYSQL_DB_USERNAME"),
					prop.getProperty("MYSQL_DB_PASSWORD"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/*
	 * Get statement from connection
	 */
	public static Statement getStatement(Connection conn) {
		Statement statement = null;
		
		try {
			statement = conn.createStatement();
			return statement;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}
	
	/*
	 * Close the connection
	 */
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Close the statement
	 */
	public static void closeStatement(Statement stmt) {
		try {
			stmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
