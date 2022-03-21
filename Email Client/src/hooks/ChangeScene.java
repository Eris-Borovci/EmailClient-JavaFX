package hooks;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ChangeScene {
	private TextField component;
	private String newScene;
	private boolean rebuild;
	private int width, height;
	
	public ChangeScene(TextField component, String newScene, boolean rebuild, int width, int height) {
		this.component = component;
		this.newScene = newScene; 
		this.rebuild = rebuild;
		this.width = width;
		this.height = height;
	}
	
	public void change() throws IOException {
		String filename = "/screens/" + newScene + ".fxml";
		Stage currentStage = (Stage)component.getScene().getWindow();
		
		Parent root = FXMLLoader.load(getClass().getResource(filename));
		Scene scene = new Scene(root, width, height);
		
		if(this.rebuild) {
			currentStage.close();
			
			Stage newStage = new Stage();
			newStage.setScene(scene);
			newStage.setMinWidth(746);
			newStage.setMinHeight(472);
			newStage.setTitle("Email Client");
			newStage.getIcons().add(new Image("/images/email_logo.png"));
			newStage.setOnCloseRequest(e->{
				e.consume();
				
				CloseApplication ca = new CloseApplication();
				try {
					ca.Open();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			newStage.show();
		} else {
			currentStage.setScene(scene);
		}
	}
}
