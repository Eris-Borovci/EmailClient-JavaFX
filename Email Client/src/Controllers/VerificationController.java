package Controllers;

import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import hooks.DBConnection;
import hooks.RefreshInbox;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VerificationController {
	@FXML
	private Label Title;
	
	private Connection conn;
	private int emailId;

	// Event Listener on Button.onAction
	@FXML
	public void cancel(ActionEvent event) {
		Stage currentStage = (Stage)Title.getScene().getWindow();
		currentStage.close();
	}
	// Event Listener on Button.onAction
	@FXML
	public void delete(ActionEvent event) throws SQLException, IOException, ParseException {
		cancel(event);
		
		String query = "DELETE FROM emails WHERE email_id = ?";
		
		conn = DBConnection.Connect();
		
		PreparedStatement prepEmails = conn.prepareStatement(query);
		prepEmails.setInt(1, emailId);
		prepEmails.execute();
		
		query = "DELETE FROM files WHERE email_id = ?";
		PreparedStatement prepFiles = conn.prepareStatement(query);
		prepFiles.setInt(1, emailId);
		prepEmails.execute();
		
		RefreshInbox.refreshInbox();
	}
	
	public void setId(int emailId) {
		this.emailId = emailId;
	}
}
