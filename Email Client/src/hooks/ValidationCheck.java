package hooks;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ValidationCheck {
	private static Label message;
	
	public static void setMessageComponent(Label messageComponent) {
		message = messageComponent;
	}
	
	public static void toggleMessage(String messageText, boolean visible) {
		message.setVisible(visible);
		message.setText(messageText);
	}
	
	public static boolean LogInCheck(TextField username, TextField password) {
		boolean usernameCorrect, passwordCorrect;
		
		if(username.getText().isBlank() || password.getText().isBlank() || username.getText().length() < 5 || password.getText().length() < 8) {
			if(username.getText().isBlank() || username.getText().length() < 5) {
				ValidationStyle.setStyle(username, true);
				usernameCorrect = false;
			} else {
				ValidationStyle.setStyle(username, false);
				usernameCorrect = true;
			}
			
			if(password.getText().isBlank() || password.getText().length() < 8) {
				ValidationStyle.setStyle(password, true);
				passwordCorrect = false;
			} else {
				ValidationStyle.setStyle(password, false);
				passwordCorrect = true;
			}
			
			if(!usernameCorrect) {
				toggleMessage("Username should be longer than 4 characters", true);
			} else if(!passwordCorrect) {
				toggleMessage("Password should be longer than 7 characters", true);
			} else {				
				toggleMessage("Please fill all information", true);
			}
			
			ValidationStyle.messageStyle(message, "red");
			
			return false;
		} else {
			ValidationStyle.setStyle(username, false);
			ValidationStyle.setStyle(password, false);
			
			message.setVisible(false);
			return true;
		}		
	}
	
	public static boolean SignUpCheck(TextField fname, TextField lname, TextField username, PasswordField password, PasswordField repeatPassword) {
		if(fname.getText().isBlank() || lname.getText().isBlank() || username.getText().isBlank() || password.getText().isBlank() || username.getText().length() < 5 || password.getText().length() < 8 || repeatPassword.getText().isBlank() || !(repeatPassword.getText() != password.getText())) {
			boolean usernameCorrect, passwordCorrect, repeatPasswordCorrect;
			
			if(fname.getText().isBlank()) ValidationStyle.setStyle(fname, true); else ValidationStyle.setStyle(fname, false);
			if(lname.getText().isBlank()) ValidationStyle.setStyle(lname, true); else ValidationStyle.setStyle(lname, false);
			
			
			if(username.getText().isBlank() || username.getText().length() < 5) {
				ValidationStyle.setStyle(username, true);
				usernameCorrect = false;
			} else {
				ValidationStyle.setStyle(username, false);
				usernameCorrect = true;
			}
			
			if(password.getText().isBlank() || password.getText().length() < 8) {
				ValidationStyle.setStyle(password, true);
				passwordCorrect = false;
			} else {
				ValidationStyle.setStyle(password, false);
				passwordCorrect = true;
			}
			
			if(!(repeatPassword.getText() != password.getText()) || repeatPassword.getText().isBlank()) {
				ValidationStyle.setStyle(repeatPassword, true);
				repeatPasswordCorrect = false;
			} else {
				ValidationStyle.setStyle(repeatPassword, false);
				repeatPasswordCorrect = true;
			}
			
			if(usernameCorrect || passwordCorrect || repeatPasswordCorrect) {
				toggleMessage("Please fill all information", true);
			} else if(!usernameCorrect) {
				toggleMessage("Username should be longer than 4 characters", true);
			} else if(!passwordCorrect) {
				toggleMessage("Password should be longer than 7 characters", true);
			} else if(!repeatPasswordCorrect) {
				toggleMessage("Repeat password correctly", true);
			}
			
			ValidationStyle.messageStyle(message, "red");
			
			return false;
		} else {
			ValidationStyle.setStyle(fname, false);
			ValidationStyle.setStyle(lname, false);
			ValidationStyle.setStyle(username, false);
			ValidationStyle.setStyle(password, false);
			ValidationStyle.setStyle(repeatPassword, false);
			
			message.setVisible(false);
			
			return true;
		}
	}
}
