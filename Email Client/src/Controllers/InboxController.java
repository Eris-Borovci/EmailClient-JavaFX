package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import context.UserInfo;
import hooks.ChangeToSingle;
import hooks.DBConnection;
import hooks.RefreshInbox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class InboxController {
	@FXML
	private VBox inboxBox;
	
	private Connection conn;
	
	@FXML
	public void initialize() throws SQLException, IOException, ParseException {
		setInboxElements();
		
		RefreshInbox.setController(this);
		ChangeToSingle.setController(this);
	}
	
	public void setInboxElements() throws SQLException, IOException, ParseException {
		ResultSet data = getInboxData();
		
		inboxBox.getChildren().clear();
		
		if(!data.isBeforeFirst()) {
			Label label = new Label("Your inbox is empty!");
			inboxBox.getChildren().add(label);
			
			return;
		}
		
		while(data.next()) {
			String fromUsername = getFromUsername(data.getInt("from_id"));
			String title = data.getString("title");
			String datetime = data.getString("date_time");
			
			try {
				FXMLLoader fxmlLoader = new FXMLLoader();
				AnchorPane item = fxmlLoader.load(getClass().getResource("/components/inboxItem.fxml").openStream());
				inboxItemController inboxItemCo = (inboxItemController) fxmlLoader.getController();
				
				item.getStylesheets().add(getClass().getResource("/style/ItemHover.css").toExternalForm());
				
				inboxBox.getChildren().add(item);
				
				inboxItemCo.changeTitle(title);
				inboxItemCo.changeFrom(fromUsername);
				inboxItemCo.changeDate(datetime);
				inboxItemCo.setId(data.getInt("email_id"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getFromUsername(int from_id) throws SQLException {
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
		
	
	public ResultSet getInboxData() throws SQLException {
		conn = DBConnection.Connect();
		String query;
		
		query = "SELECT * FROM emails WHERE to_id = ?";
		
		PreparedStatement prepStm = conn.prepareStatement(query);
		prepStm.setInt(1, UserInfo.getId());
		
		ResultSet data = prepStm.executeQuery();
		
		return data;
	}
	
	public void ViewItem(int emailId) throws SQLException, IOException {
		hooks.ViewItem VI = new hooks.ViewItem(inboxBox, emailId,true);
		
		VI.View();
	}
}
