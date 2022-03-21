package hooks;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import context.UserInfo;
import context.UserSession;
import javafx.scene.control.TextField;

public class CRUDuser {
	private static Connection conn;
	
	public static boolean checkExistence(String username) throws SQLException {
		if(getUserInfo(username).next()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkPassword(String username, String password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		ResultSet data = getUserInfo(username);
		String storedPassword = null;
		
		if(data.next()) {
			storedPassword = data.getString("password");
		}
		
		if(PasswordHash.validatePassword(password, storedPassword)) {
			return true;
		} else {			
			return false;
		}
	}
	
	public static void LoggedIn(TextField component, String username, Boolean remember) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
		if(remember) UserSession.setSession(username);
		
		UserInfo.setUsername(username);
		
		ChangeScene cs = new ChangeScene(component, "Home", true, 800, 472);
		cs.change();
	}
	
	public static int createUser(String fname, String lname, String username, String password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		conn = DBConnection.Connect();
		String query = "INSERT INTO users(firstname, lastname, username, password) VALUES (?,?,?,?)";
		
		PreparedStatement prepStm = conn.prepareStatement(query);
		prepStm.setString(1, fname);
		prepStm.setString(2, lname);
		prepStm.setString(3, username);
		prepStm.setString(4, PasswordHash.generateStorngPasswordHash(password));
		
		int rows = prepStm.executeUpdate();
		
		return rows;
	}
	
	public static ResultSet getUserInfo(String username) throws SQLException {
		conn = DBConnection.Connect();
		String query = "SELECT * FROM users WHERE username = ?";
		
		PreparedStatement prepStm = conn.prepareStatement(query);
		prepStm.setString(1, username);
		
		ResultSet data = prepStm.executeQuery();
		
		return data;
	}
}
