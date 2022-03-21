package hooks;

import java.io.IOException;
import java.sql.SQLException;

import Controllers.InboxController;

public class ChangeToSingle {
	private static InboxController controller;
	private static int itemId;
	
	public static void setController(InboxController con) {
		controller = con;
	}
	
	public static void setItemId(int id) {
		itemId = id;
	}
	
	public static int getId() {
		return itemId;
	}
	
	public static void changeScreen() throws SQLException, IOException {
		controller.ViewItem(itemId);
	}
}
