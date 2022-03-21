package hooks;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Controllers.SingleEmailController;
import context.EmailId;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ViewItem {
	private Connection conn;
	private VBox inboxBox;
	private int emailId;
	private boolean fromId;
	
	public ViewItem(VBox inbox_box, int email_id, boolean from_id) {
		this.inboxBox = inbox_box;
		this.emailId = email_id;
		this.fromId = from_id;
	}
	
	public void View() throws SQLException, IOException {
		
		conn = DBConnection.Connect();
		String query;
		
		query = "SELECT * FROM emails WHERE email_id = ?";
		
		PreparedStatement prepStm = conn.prepareStatement(query);
		prepStm.setInt(1, emailId);
		
		ResultSet emailData = prepStm.executeQuery();
		

		if(emailData.next()) {
			GetFromUsername gfu;
			
			if(fromId) {
				gfu = new GetFromUsername(emailData.getInt("from_id"));
			} else {
				gfu = new GetFromUsername(emailData.getInt("to_id"));
			}
			
			FXMLLoader fxmlLoader = new FXMLLoader();
			String title = emailData.getString("title");
			String from = gfu.getUsername();
			String subject = emailData.getString("subject");
			
			try {
				EmailId.setEmailId(emailId);
				
				AnchorPane SingleEmail = fxmlLoader.load(getClass().getResource("/components/SingleEmail.fxml").openStream());
				SingleEmail.getStylesheets().add(getClass().getResource("/style/ItemHover.css").toExternalForm());
				
				SingleEmailController ec = (SingleEmailController)fxmlLoader.getController();
				
				String fullTitle = "Title: " + title;
				String fullFrom = "From: " + from;
				
				inboxBox.getChildren().clear();
				inboxBox.getChildren().add(SingleEmail);

				ec.setInfo(fullTitle, fullFrom, subject, !fromId, emailId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
