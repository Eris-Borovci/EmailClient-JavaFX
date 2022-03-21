package hooks;

import java.sql.SQLException;

import Controllers.SentController;

public final class RefreshSentInbox {
	private static SentController controller;
	
	public static void setController(SentController con) {
		controller = con;
	}
	
	public static void refresh() throws SQLException {
		controller.setSentElements();
	}
}
