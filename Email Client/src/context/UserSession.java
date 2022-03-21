package context;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.prefs.Preferences;


public class UserSession {
	public static void setSession(String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Preferences userPref = Preferences.userRoot();
		userPref.put("username", username);
	}
	
	public static String getSession() {
		Preferences userPref = Preferences.userRoot();
		String info = userPref.get("username", null);
		
		return info;
	}
	
	public static void removeSession() {
		Preferences userPref = Preferences.userRoot();
		userPref.remove("username");
	}
}
