package hooks;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DragStage {
	@FXML
	private static HBox startPoint;
	private static double xOffset = 0;
	private static double yOffset = 0;


	public static void setStage(HBox window) {
		startPoint = window;
	}
	
	@FXML
	public static void MousePressStart(MouseEvent event) {
		xOffset = event.getSceneX();
		yOffset = event.getSceneY();
	}
	
	@FXML
	public static void MouseDragged(MouseEvent event) {
		Stage window = (Stage)startPoint.getScene().getWindow();
		
		window.setX(event.getScreenX() - xOffset);
		window.setY(event.getScreenY() - yOffset);
	}
	
	public static void start(MouseEvent e) {
		EventType<? extends MouseEvent> eventType = e.getEventType();
		String type = eventType.toString();
		
		if(type.equals("MOUSE_PRESSED")) {
			MousePressStart(e);
		} else if(type.equals("MOUSE_DRAGGED")) {
			MouseDragged(e);
		}
	}
}
