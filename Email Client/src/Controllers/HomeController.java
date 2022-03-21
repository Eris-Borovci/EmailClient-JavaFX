package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import context.UserInfo;
import context.UserSession;
import javafx.event.ActionEvent;

public class HomeController {
	
	@FXML
	private StackPane MainShow;
	@FXML
	private Circle inboxIndicator, sentIndicator;
	@FXML
	private Menu accountName;

	@FXML
	public void initialize() throws IOException {
		openInbox(null);
		accountName.setText(UserInfo.getUsername());
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void openCompose(ActionEvent event) throws IOException {
		Parent fxml = (Parent)FXMLLoader.load(getClass().getResource("/screens/Compose.fxml"));
		MainShow.getChildren().clear();
		MainShow.getChildren().addAll(fxml);
		
		IndicatorToggle(inboxIndicator, false);
		IndicatorToggle(sentIndicator, false);
	}
	// Event Listener on Button.onAction
	@FXML
	public void openInbox(ActionEvent event) throws IOException {
		Parent fxml = (Parent)FXMLLoader.load(getClass().getResource("/screens/Inbox.fxml"));
		MainShow.getChildren().clear();
		MainShow.getChildren().addAll(fxml);
		
		IndicatorToggle(inboxIndicator, true);
		IndicatorToggle(sentIndicator, false);
	}
	// Event Listener on Button.onAction
	@FXML
	public void openSent(ActionEvent event) throws IOException {
		Parent fxml = (Parent)FXMLLoader.load(getClass().getResource("/screens/Sent.fxml"));
		MainShow.getChildren().clear();
		MainShow.getChildren().addAll(fxml);
		
		IndicatorToggle(inboxIndicator, false);
		IndicatorToggle(sentIndicator, true);
	}
	
	@FXML 
	public void LogOut(ActionEvent event) throws IOException {
		UserSession.removeSession();
//		Closing the current one
		Stage ms = (Stage)MainShow.getScene().getWindow();
		ms.close();
		
//		Load login page
		Parent root = FXMLLoader.load(getClass().getResource("/screens/LogIn.fxml"));
		Scene scene = new Scene(root,651,413);
		
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void IndicatorToggle(Circle indicator, Boolean show) {
		indicator.setVisible(show);
	}
}
