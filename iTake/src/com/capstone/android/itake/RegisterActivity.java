package com.capstone.android.itake;

import android.app.Activity;
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
			goToMain();
		break;
			
		}
	}
	
	private void goToMain() {
		startActivity(new Intent(RegisterActivity.this, MainActivity.class));
	}
}