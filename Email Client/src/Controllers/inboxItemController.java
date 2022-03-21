package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import hooks.ChangeToSingle;
import hooks.ChangeToSingleSent;
import hooks.DeleteEmail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class inboxItemController {
	@FXML
	private Label Title;
	@FXML
	private Label From;
	@FXML
	private Label Date;
	
	private int emailId;
	private Connection conn;
	private boolean isSent;
	
	@FXML
	public void changeTitle(String title) {
		Title.setText(title);
	}
	
	@FXML
	public void changeFrom(String from) {
		From.setText(from);
	}
	
	@FXML
	public void changeDate(String date) {
		Date.setText(date);
	}
	
	@FXML
	public void deleteEmail(ActionEvent event) throws SQLException, IOException {
		DeleteEmail de = new DeleteEmail(emailId);
		de.deleteEmail();
	}
	
	@FXML
	public void viewEmail(MouseEvent event) throws SQLException, IOException {
		if(event.getClickCount() == 2) {
			if(isSent) {
				ChangeToSingleSent.setItemId(emailId);
				ChangeToSingleSent.changeScreen();
				return;
			}
			
			ChangeToSingle.setItemId(emailId);
			ChangeToSingle.changeScreen();
		}
	}
	
	public void setId(int emailId) {
		this.emailId = emailId;
	}
	
	public void setIsSent(boolean is) {
		this.isSent = is;
	}
}
