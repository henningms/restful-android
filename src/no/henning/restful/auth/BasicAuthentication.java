package no.henning.restful.auth;

import android.util.Base64;

public class BasicAuthentication
{
	private static String username;
	private static String password;
	
	public static String to()
	{
		String authString = String.format("%s:%s", username, password);
		
		return Base64.encodeToString(authString.getBytes(), Base64.DEFAULT);
	}
	
	public static void setUsernameAndPassword(String user, String pass)
	{
		username = user;
		password = pass;
	}
}
