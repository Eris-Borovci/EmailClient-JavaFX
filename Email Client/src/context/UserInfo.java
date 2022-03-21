package context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hooks.DBConnection;

public class UserInfo {
	private static String username;
	private static int id;
	
	public static void setUsername(String newUsername) throws SQLException {
		username = newUsername;
		
		setId(newUsername);
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static void setId(String theUsername) throws SQLException {
		Connection conn = DBConnection.Connect();
		
		String query = "SELECT id FROM users WHERE username = ?";
		
		PreparedStatement prepStm = conn.prepareStatement(query);
		prepStm.setString(1, theUsername);
		
		ResultSet theId = prepStm.executeQuery();
		
		if(theId.next()) {
			id = theId.getInt("id");
		}
	}
	
	public static int getId() {
		return id;
	}
}
