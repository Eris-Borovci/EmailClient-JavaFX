package hooks;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CloseApplication {
	public void Open() throws IOException {
		Parent root = (Parent)FXMLLoader.load(getClass().getResource("/screens/CloseApplication.fxml"));
		
		Scene scene = new Scene(root, 500, 223);
		
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UTILITY);
		stage.showAndWait();
	}
}
