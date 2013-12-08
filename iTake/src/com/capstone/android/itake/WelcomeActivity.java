package com.capstone.android.itake;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
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
			username = email.getText().toString();
			userkey = password.getText().toString();
			boolean pwordSuccess;
			boolean uSuccess;
			
			if (username == null) {
				email.setText("You must enter a username");
;			}
			else if (userkey == null) {
				password.setText("You must enter a password");
			}
			else {
				Password entered = new Password(userkey);
				try {
					String retrieved = "";
					String pReader = "ITAKEP";
					String uReader = "ITAKEU";
					StringBuilder pBuilder = new StringBuilder();
					
					FileInputStream pIn = openFileInput(pReader); 
					InputStreamReader pInReader = new InputStreamReader(pIn);
					BufferedReader buffP = new BufferedReader(pInReader);
					String incoming = "";
					
					while ((incoming = buffP.readLine()) != null) {
						pBuilder.append(incoming);
					}
					pIn.close();
					retrieved = pBuilder.toString();
					pwordSuccess = Password.check(entered, retrieved);
					
					
					if (pwordSuccess == true) {
						String retUser = "";
						StringBuilder uBuilder = new StringBuilder();
						
						FileInputStream uIn = openFileInput(uReader);
						InputStreamReader uInReader = new InputStreamReader(uIn);
						BufferedReader buffU = new BufferedReader(uInReader);
						String incomingU = "";
						
						while ((incomingU = buffU.readLine()) != null) {
							uBuilder.append(incomingU);
						}
						uIn.close();
						retUser = uBuilder.toString();
						uSuccess = retUser.equals(username);
						
						if (uSuccess == true) {
							loginToMain();
						}
						else {
							email.setText("Incorrect username");
						}	
					}
					else {
						password.setText("Incorrect password! Please try again");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
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