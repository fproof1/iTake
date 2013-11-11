package com.capstone.android.itake;
//by Libby!
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnClickListener {
	
	TextView settingsHeader, soundHeader, soundToggle, userHeader, emailHeader, passwordHeader;
	Switch soundSwitch;
	Spinner soundSpinner;
	EditText editEmail, editPassword;
	Button selectEdit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		setupSettings();
	
}

	private void setupSettings() {
		
		settingsHeader = (TextView) findViewById(R.id.settingshead);
		soundHeader = (TextView) findViewById(R.id.textView1);
		soundSpinner = (Spinner) findViewById(R.id.spinner1);
		soundToggle = (TextView) findViewById(R.id.textView2);
		soundSwitch = (Switch) findViewById(R.id.switch1);
		userHeader = (TextView) findViewById(R.id.textView3);
		emailHeader = (TextView) findViewById(R.id.textView4);
		editEmail = (EditText) findViewById(R.id.editText1);
		passwordHeader = (TextView) findViewById(R.id.textView5);
		editPassword = (EditText) findViewById(R.id.editText2);
		selectEdit = (Button) findViewById(R.id.button1);
		
		selectEdit.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			gotoEdit();
			break;
		}
		// TODO Auto-generated method stub
		
	}

	private void gotoEdit() {
		// TODO Auto-generated method stub
		//do nothing
	}
}