package context;

public class EmailId {
	private static int email_id;
	
	public static void setEmailId(int id) {
		email_id = id;
	}
	
	public static int getEmailId() {
		return email_id;
	}
}
