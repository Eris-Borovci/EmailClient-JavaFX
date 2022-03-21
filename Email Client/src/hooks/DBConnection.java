package hooks;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection Connect() {
		try {
			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/email", "root", "");
			
			return conn;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}
}
