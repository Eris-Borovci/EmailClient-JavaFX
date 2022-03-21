package Controllers;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import modules.SelectedFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import context.UserInfo;
import hooks.CRUDuser;
import hooks.DBConnection;
import hooks.ValidationStyle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

public class ComposeController {
	@FXML
	private TextField toInp;
	@FXML
	private TextField titleInp;
	@FXML
	private TextArea subjectInp;
	@FXML
	private Label messageLabel;
	@FXML
	private ListView<String> listFiles;
	@FXML
	private Button removeItem;
	
	public Connection conn = null;
	private List<File> files;
	private List<String> selected;
	private ArrayList<SelectedFiles> selectedFiles = new ArrayList<SelectedFiles>();
	
	@FXML
	public void initialize() {
		listFiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		listFiles.setOnMouseClicked(e->{
			selected = listFiles.getSelectionModel().getSelectedItems();
			
			if(selected.size() > 0) {
				removeItem.setVisible(true);
			} else {
				removeItem.setVisible(false);
			}
		});
	}

	// Event Listener on Button.onAction
	@FXML
	public void Send(ActionEvent event) throws SQLException, FileNotFoundException {
		String query;
		if(validate()) {
			if(!CRUDuser.checkExistence(toInp.getText())) {
				messageLabel.setVisible(true);
				messageLabel.setText("User does not exist");
				ValidationStyle.messageStyle(messageLabel, "red");
				return;
			}
			
			conn = DBConnection.Connect();
			
//			Getting another `to user` id
			query = "SELECT id FROM users WHERE username = ?";
			PreparedStatement toId = conn.prepareStatement(query);
			toId.setString(1, toInp.getText());
			ResultSet toIdData = toId.executeQuery();
			int theId = 0;
			if(toIdData.next()) {
				theId = toIdData.getInt("id");
			}
			
//			Inserting to email
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			String nowDateTime = LocalDateTime.now().format(dateFormatter);
			
			query = "INSERT INTO emails(from_id, to_id, title, subject, date_time) VALUES(?,?,?,?,?)";
			PreparedStatement emailStm = conn.prepareStatement(query);
			emailStm.setInt(1, UserInfo.getId());
			emailStm.setInt(2, theId);
			emailStm.setString(3, titleInp.getText());
			emailStm.setString(4, subjectInp.getText());
			emailStm.setString(5, nowDateTime);
			
			emailStm.execute();
			
//			Getting the email id

			query = "SELECT email_id FROM emails WHERE email_id = (SELECT MAX(email_id) FROM emails) AND from_id = ?";
			PreparedStatement prepId = conn.prepareStatement(query);
			prepId.setInt(1, UserInfo.getId());
			ResultSet latestIdData = prepId.executeQuery();
			int latestId = 0;
			if(latestIdData.next()) {
				latestId = latestIdData.getInt("email_id");
			}
			
//			Adding the files
			if(listFiles.getItems().size() > 0) {
				query = "INSERT INTO files(email_id, filename, file) VALUES(?,?,?)";
								
				for(int i = 0; i<selectedFiles.size(); i++) {
					PreparedStatement filesStm = conn.prepareStatement(query);
					filesStm.setInt(1, latestId);
					filesStm.setString(2, selectedFiles.get(i).getFileName());
					
					InputStream in = new FileInputStream(selectedFiles.get(i).getAbsoultePath());
					filesStm.setBlob(3, in);
					
					filesStm.execute();
				}
			}
			
			toInp.setText("");
			titleInp.setText("");
			subjectInp.setText("");
			
			listFiles.getItems().clear();
			selectedFiles.clear();
			
			messageLabel.setText("Email Sended");
			messageLabel.setVisible(true);
			ValidationStyle.messageStyle(messageLabel, "blue");
		}
	}
	
	@FXML
	public void openFileChooser(ActionEvent event) {
		FileChooser fc = new FileChooser();
		
		files = fc.showOpenMultipleDialog(null);
		
		if(files !=null) {
			for(int i = 0; i<files.size(); i++) {
				listFiles.getItems().add(files.get(i).getName());
				selectedFiles.add(new SelectedFiles(files.get(i).getName(), files.get(i).getAbsolutePath()));
			}
		}
	}
	
	@FXML
	public void removeSelectedItem(ActionEvent event) {
		selected.forEach(s -> selectedFiles.removeIf(f -> f.getFileName().equals(s)));
	
		listFiles.getItems().removeAll(selected);
		
		if(listFiles.getItems().size() == 0) {
			removeItem.setVisible(false);
		}
	}
	
	public boolean validate() {
		if(toInp.getText().isEmpty() || titleInp.getText().isEmpty()) {
			if(toInp.getText().isEmpty()) {
				ValidationStyle.setStyle(toInp, true);
			} else ValidationStyle.setStyle(toInp, false);
			
			if(titleInp.getText().isEmpty()) {
				ValidationStyle.setStyle(titleInp, true);
			} else ValidationStyle.setStyle(titleInp, false);
			
			messageLabel.setText("The first two text fields should be filled!");
			messageLabel.setVisible(true);
			ValidationStyle.messageStyle(messageLabel, "red");
			
			return false;
		}
		
		messageLabel.setVisible(false);
		return true;
	}
}
