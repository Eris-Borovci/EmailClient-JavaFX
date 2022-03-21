package hooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetFromUsername {
	private Connection conn;
	private int from_id;
	
	public GetFromUsername(int from) {
		this.from_id = from;
	}
	
	public String getUsername() throws SQLException {
		conn = DBConnection.Connect();
		String query;
		
		query = "SELECT username FROM users WHERE id = ?";
		
		PreparedStatement prepStm = conn.prepareStatement(query);
		prepStm.setInt(1, from_id);
		
		ResultSet usernameData = prepStm.executeQuery();
		
		if(usernameData.next()) {
			return usernameData.getString("username");
		}
		
		return null;
	}
}
