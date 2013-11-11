package com.capstone.android.itake;

public class User {
	
	//private static final String JSON_USER= "username";
	//private static final String JSON_PASS = "password";
	
	private String mUser;
	private String mPass;
	
	public String getUser() {
		return mUser;
	}
	//reserved for testing
	public String getPass() {
		return mPass;
	}
	
	public void setUser(String name) {
		mUser = name;
	}
	
	public void setPass(String pword) {
		mPass = pword;
	}
}