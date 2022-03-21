package hooks;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import Controllers.InboxController;

public final class RefreshInbox {
	private static InboxController controller;
	
	public static void setController(InboxController con) {
		controller = con;
	}
	
	public static void refreshInbox () throws SQLException, IOException, ParseException {
		controller.setInboxElements();
	}
}
