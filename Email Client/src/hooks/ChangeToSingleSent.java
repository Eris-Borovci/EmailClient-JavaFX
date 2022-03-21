package hooks;

import java.io.IOException;
import java.sql.SQLException;

import Controllers.SentController;

public class ChangeToSingleSent {
	private static SentController controller;
	private static int itemId;
	
	public static void setController(SentController con) {
		controller = con;
	}
	
	public static void setItemId(int id) {
		itemId = id;
	}
	
	public static void changeScreen() throws SQLException, IOException {
		controller.ViewItemNow(itemId);
	}
}
