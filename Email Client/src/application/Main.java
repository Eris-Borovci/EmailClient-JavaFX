package application;
import java.io.IOException;

import context.UserInfo;
import context.UserSession;
import hooks.CloseApplication;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			if(UserSession.getSession() != null) {
				UserInfo.setUsername(UserSession.getSession());
				
				Parent root = FXMLLoader.load(getClass().getResource("/screens/Home.fxml"));
				Scene scene = new Scene(root,800,472);
				
				primaryStage.setScene(scene);
				primaryStage.setMinWidth(800);
				primaryStage.setMinHeight(472);
				primaryStage.show();
			} else {
				Parent root = FXMLLoader.load(getClass().getResource("/screens/LogIn.fxml"));
				Scene scene = new Scene(root,651,413);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.UNDECORATED);
				primaryStage.setResizable(false);
				primaryStage.show();	
			}
			
			primaryStage.setTitle("Email Client");
			primaryStage.getIcons().add(new Image("/images/email_logo.png"));
			primaryStage.setOnCloseRequest(e->{
				e.consume();
				
				CloseApplication ca = new CloseApplication();
				try {
					ca.Open();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
