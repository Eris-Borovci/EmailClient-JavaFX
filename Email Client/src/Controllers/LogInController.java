package Controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import hooks.CloseApplication;
import hooks.DragStage;
import hooks.ValidationCheck;
import hooks.ValidationStyle;
import hooks.CRUDuser;
import hooks.ChangeScene;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LogInController {
	@FXML
	private TextField usernameInp;
	@FXML
	private PasswordField passwordInp;
	@FXML
	private HBox screenDraggable;
    @FXML
    private AnchorPane MainStage;
    @FXML
    private Label messageLabel;
    @FXML
    private CheckBox remember;
    
	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void closeApplication(MouseEvent e) throws IOException {
		CloseApplication closeApp = new CloseApplication();
		closeApp.Open();
	}

	@FXML
	public void ChangeScene() throws IOException {
		ChangeScene newScene = new ChangeScene(usernameInp, "SignUp", false, 651, 413);
		newScene.change();
	}
	
	@FXML
	public void Drag(MouseEvent e) {
		DragStage.setStage(screenDraggable);
		DragStage.start(e);
	}
	
	@FXML
	public void logIn(MouseEvent e) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		ValidationCheck.setMessageComponent(messageLabel);
		
		if(ValidationCheck.LogInCheck(usernameInp, passwordInp)) {
			if(CRUDuser.checkExistence(usernameInp.getText())) {
				if(CRUDuser.checkPassword(usernameInp.getText(), passwordInp.getText())) {
					CRUDuser.LoggedIn(passwordInp, usernameInp.getText(), remember.isSelected());
				} else {
					ValidationStyle. messageStyle(messageLabel,"red");
					messageLabel.setText("Incorrect password");
					messageLabel.setVisible(true);
				}
			} else {
				ValidationStyle.messageStyle(messageLabel, "red");
				messageLabel.setText("User does not exist");
				messageLabel.setVisible(true);
			}
		}
	}
}
