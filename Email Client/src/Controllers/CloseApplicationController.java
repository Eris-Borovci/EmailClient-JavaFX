package Controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class CloseApplicationController {
	@FXML
	private Button noBtn;

	// Event Listener on Button[#yesBtn].onAction
	@FXML
	public void closeApp(ActionEvent event) {	
		Platform.exit();
		
		closeModal(event);
	}
	// Event Listener on Button[#noBtn].onAction
	@FXML
	public void closeModal(ActionEvent event) {
		Stage modal = (Stage)noBtn.getScene().getWindow();
		modal.close();
	}
	
}
