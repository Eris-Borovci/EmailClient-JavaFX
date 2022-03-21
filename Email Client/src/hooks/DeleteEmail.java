package hooks;

import java.io.IOException;
import java.sql.SQLException;

import Controllers.VerificationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DeleteEmail {
	private int emailId;
	
	public DeleteEmail(int emailId) {
		this.emailId = emailId;
	}
	
	public void deleteEmail() throws SQLException, IOException {
		Stage newStage = new Stage();
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		
		Parent pane = fxmlLoader.load(getClass().getResource("/components/Verification.fxml").openStream());
		
		Scene newScene = new Scene(pane, 384, 183);
		
//		Getting controller
		VerificationController con = (VerificationController)fxmlLoader.getController();
		con.setId(emailId);
		
		newStage.setScene(newScene);
		newStage.setResizable(false);
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.initStyle(StageStyle.UTILITY);
		newStage.showAndWait();
	}
}
