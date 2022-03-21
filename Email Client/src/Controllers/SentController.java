package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import context.UserInfo;
import hooks.ChangeToSingleSent;
import hooks.DBConnection;
import hooks.GetFromUsername;
import hooks.RefreshSentInbox;
import hooks.ViewItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SentController {
	@FXML
	private VBox sentBox;
	
	private Connection conn;
	
	public void initialize() throws SQLException {
		setSentElements();

		ChangeToSingleSent.setController(this);
		RefreshSentInbox.setController(this);
	}
	
	public void setSentElements() throws SQLException {
		ResultSet data = getSentData();

		sentBox.getChildren().clear();
	
		if(!data.isBeforeFirst()) {
			Label label = new Label("You haven't sent any emails yet!");
			
			sentBox.getChildren().add(label);
			
			return;
		}
		
		while(data.next()) {
			GetFromUsername gfu = new GetFromUsername(data.getInt("to_id"));
			
			String fromUsername = gfu.getUsername();
			String title = data.getString("title");
			String datetime = data.getString("date_time");
			
			try {
				FXMLLoader fxmlLoader = new FXMLLoader();
				AnchorPane item = fxmlLoader.load(getClass().getResource("/components/inboxItem.fxml").openStream());
				inboxItemController inboxItemCo = (inboxItemController) fxmlLoader.getController();
				
				item.getStylesheets().add(getClass().getResource("/style/ItemHover.css").toExternalForm());
				
				sentBox.getChildren().add(item);
				
				inboxItemCo.changeTitle(title);
				inboxItemCo.changeFrom(fromUsername);
				inboxItemCo.changeDate(datetime);
				inboxItemCo.setIsSent(true);
				inboxItemCo.setId(data.getInt("email_id"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ResultSet getSentData() throws SQLException {
		conn = DBConnection.Connect();
		String query;
		
		query = "SELECT * FROM emails WHERE from_id = ?";
		
		PreparedStatement prepStm = conn.prepareStatement(query);
		prepStm.setInt(1, UserInfo.getId());
		
		ResultSet data = prepStm.executeQuery();
		
		return data;
	}
	
	public void ViewItemNow(int emailId) throws SQLException, IOException {
		ViewItem VI = new ViewItem(sentBox, emailId, false);
		
		VI.View();
	}
}
