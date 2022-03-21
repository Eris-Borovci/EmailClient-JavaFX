package Controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import hooks.CRUDuser;
import hooks.ChangeScene;
import hooks.CloseApplication;
import hooks.DragStage;
import hooks.ValidationCheck;
import hooks.ValidationStyle;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;

import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;

public class SignUpController {
	@FXML
	private HBox screenDraggable;
	@FXML
	private PasswordField passwordInp;
	@FXML
	private TextField firstnameInp;
	@FXML
	private Button signupBtn;
	@FXML
	private TextField lastnameInp;
	@FXML
	private TextField usernameInp;
	@FXML
	private PasswordField repeatInp;
	@FXML
	private Label messageLabel;

	// Event Listener on HBox[#screenDraggable].onMouseDragged
	@FXML
	public void Drag(MouseEvent event) {
		DragStage.setStage(screenDraggable);
		DragStage.start(event);
	}

	// Event Listener on ImageView.onMouseClicked
	@FXML
	public void closeApplication(MouseEvent event) throws IOException {
		CloseApplication c = new CloseApplication();
		c.Open();
	}
	// Event Listener on Label.onMouseClicked
	@FXML
	public void changeScreen(MouseEvent event) throws IOException {
		ChangeScene newScene = new ChangeScene(firstnameInp, "LogIn", false, 651, 413);
		newScene.change();
	}
	
	@FXML
	public void signUp(MouseEvent e) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		ValidationCheck.setMessageComponent(messageLabel);
		
		if(ValidationCheck.SignUpCheck(firstnameInp, lastnameInp, usernameInp, passwordInp, repeatInp)) {
			if(CRUDuser.checkExistence(usernameInp.getText())) {
				ValidationStyle.messageStyle(messageLabel, "red");
				messageLabel.setText("User already exists");
				messageLabel.setVisible(true);
			} else {
				if(CRUDuser.createUser(firstnameInp.getText(), lastnameInp.getText(), usernameInp.getText(), passwordInp.getText()) > 0) {
					CRUDuser.LoggedIn(firstnameInp, usernameInp.getText(), true);
				} else {
					ValidationStyle.messageStyle(messageLabel, "red");
					messageLabel.setText("Error");
					messageLabel.setVisible(true);
				}
			}
		}
	}
}
