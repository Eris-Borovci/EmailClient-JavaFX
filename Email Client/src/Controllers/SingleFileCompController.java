package Controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hooks.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class SingleFileCompController {
	@FXML
	private Text fileName;
	@FXML
	private Button downloadBtn;
	private int fileId;
	private Connection conn;
	
	@FXML
	public void download() throws SQLException, IOException {
		conn = DBConnection.Connect();
		String query;

		query = "SELECT * FROM files WHERE file_id = ?";
		
		PreparedStatement prepStm = conn.prepareStatement(query);
		prepStm.setInt(1, fileId);
		
		ResultSet file = prepStm.executeQuery();
		
		Blob fileData = null;
		
		if(file.next()) {
			fileData = file.getBlob("file");
		}
		
		int fileDataLength = (int) fileData.length();
		
		byte byteArray[] = fileData.getBytes(1, fileDataLength);
		
		FileOutputStream outPutStream = new FileOutputStream("C:\\Users\\OnLine\\Downloads\\" + file.getString("filename"));
		outPutStream.write(byteArray);
		outPutStream.close();
		
		downloadBtn.setText("Downloaded");
		downloadBtn.setStyle("-fx-background-color: #288C07");
	}
	
	public void setInfo(int fileId, String fname) {
		this.fileId = fileId;
		fileName.setText(fname);
	}
}
