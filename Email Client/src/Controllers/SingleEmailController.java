package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import context.EmailId;
import hooks.DBConnection;
import hooks.DeleteEmail;
import hooks.RefreshInbox;
import hooks.RefreshSentInbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SingleEmailController {
	@FXML
	private Button backButton;
	@FXML
	private Label title, from;
	@FXML
	private Text subject;
	@FXML
	private VBox filesContainer;
	
	private int email_id;
	private boolean isSent;
	private Connection conn;
	
	@FXML
	public void initialize() throws IOException, SQLException {
		setFiles();
	}
	
	@FXML
	public void deleteEmail(ActionEvent event) throws SQLException, IOException {
		DeleteEmail de = new DeleteEmail(email_id);
		de.deleteEmail();
	}
	
	@FXML
	public void goBack(ActionEvent event) throws SQLException, IOException, ParseException {
		if(isSent) {
			RefreshSentInbox.refresh();
			return;
		}
		
		RefreshInbox.refreshInbox();
	}
	
	public void setInfo(String titleInfo, String fromInfo, String subjectInfo, boolean is, int emailId) throws IOException, SQLException {
		title.setText(titleInfo);
		from.setText(fromInfo);
		subject.setText(subjectInfo);
		email_id = emailId;
		this.isSent = is;
	}
	
	public void setId(int id) {
		email_id = id;
	}
	
	public int getId() {
		return email_id;	
	}

	public ResultSet getFiles() throws SQLException {
		conn = DBConnection.Connect();
		String query = "SELECT * FROM files WHERE email_id = ?";
		
		PreparedStatement prepStm = conn.prepareStatement(query);
		prepStm.setInt(1, EmailId.getEmailId());
		
		ResultSet fileData = prepStm.executeQuery();

		return fileData;
	}
	
	public void setFiles() throws IOException, SQLException {
		ResultSet files = getFiles();
		
		if(!files.isBeforeFirst()) {
			Label label = new Label("No files attached");
			filesContainer.getChildren().add(label);
			return;
		}
		
		while(files.next()) {
			int file_id = files.getInt("file_id");
			
			FXMLLoader fxmlLoader = new FXMLLoader();
			AnchorPane fileComp = fxmlLoader.load(getClass().getResource("/components/SingleFileComp.fxml").openStream());
			SingleFileCompController con = (SingleFileCompController)fxmlLoader.getController();
			
			filesContainer.getChildren().add(fileComp);
			
			
			con.setInfo(file_id, files.getString("filename"));
		}
	}
}
