package hooks;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ValidationStyle {
	public static void setStyle(TextField input, boolean isError) {
		if(isError) {
			input.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
		} else {
			input.setStyle("-fx-text-box-border: #fff; -fx-focus-color: #fff;");
		}
	}
	
	public static void messageStyle(Label message, String textColor) {
		message.setStyle("-fx-text-fill: " + textColor);
	}
}
