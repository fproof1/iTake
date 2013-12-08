package com.capstone.android.itake;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity implements OnClickListener {
	
	TextView userNew, regPrompt;
	EditText email, password, confirmPass;
	Button login;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_reg);
		
		setupRegister();
	
}

	private void setupRegister() {
		
		userNew = (TextView) findViewById(R.id.newuser_prompt);
		regPrompt = (TextView) findViewById(R.id.reg_prompt);
		email = (EditText) findViewById(R.id.emaillogin);
		password = (EditText) findViewById(R.id.passwordlogin);
		confirmPass = (EditText) findViewById(R.id.passwordconfirm);
		login = (Button) findViewById(R.id.loginbutton);
		
		login.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		
		switch(v.getId()) {
		case R.id.loginbutton:
			String user = email.getText().toString();
			String pass = password.getText().toString();
			String conf = confirmPass.getText().toString();
			if (pass != conf) {
				password.setText("Password mismatch, please enter again");
			}
			else {
				Password toStore = new Password(pass);
				try {
					String hashStore = Password.getSaltedHash(toStore);
					String pFile = "ITAKEP";
					String uFile = "ITAKEU";
					FileOutputStream pStream = openFileOutput(pFile, Context.MODE_PRIVATE);
					OutputStreamWriter toPFile = new OutputStreamWriter(pStream);
					toPFile.write(hashStore);
					toPFile.flush();
					toPFile.close();
					FileOutputStream uStream = openFileOutput(uFile, Context.MODE_PRIVATE);
					OutputStreamWriter toUFile = new OutputStreamWriter(uStream);
					toUFile.write(user);
					toUFile.flush();
					toUFile.close();
					goToMain();
				} catch (Exception e) {
					
					e.printStackTrace();
				}	
				
			}
		break;
			
		}
	}
	
	private void goToMain() {
		startActivity(new Intent(RegisterActivity.this, MainActivity.class));
	}
}