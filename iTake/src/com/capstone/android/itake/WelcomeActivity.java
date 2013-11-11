package com.capstone.android.itake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends Activity implements OnClickListener {
	
	TextView appTitle, loginLine;
	EditText email, password;
	Button logmein, createnew;
	
	String username;
	String userkey;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomsplash);
		
		setupWelcome();
	}

	private void setupWelcome() {
		
		email = (EditText) findViewById(R.id.emaillogin);
		password = (EditText) findViewById(R.id.passwordlogin);
		logmein = (Button) findViewById(R.id.loginbutton);
		createnew = (Button) findViewById(R.id.newuserbutton);
		appTitle = (TextView) findViewById(R.id.welcometv);
		loginLine = (TextView) findViewById(R.id.loginprompt);
		
		logmein.setOnClickListener(this);
		createnew.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		
		switch(v.getId()) {
		
		case R.id.loginbutton:
		
			loginToMain();
			break;
			
		case R.id.newuserbutton:
			goToRegister();
			
			break;
		}
	}

	private void goToRegister() {
		// TODO Auto-generated method stub
		startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
	}

	private void loginToMain() {
		// TODO Auto-generated method stub
		startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
		
	}
}