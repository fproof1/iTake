package com.capstone.android.itake;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView settingsLabel, historyLabel, drugLabel, cheatLabel;
	ImageButton drugimg, cheatimg, historyimg, settingsimg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		
		setupMain();
	
}

	private void setupMain() {
		drugimg = (ImageButton) findViewById(R.id.drugbutton);
		cheatimg = (ImageButton) findViewById(R.id.cheatsbutton);
		drugLabel = (TextView) findViewById(R.id.drug_label);
		cheatLabel = (TextView) findViewById(R.id.cheat_label);
		historyimg = (ImageButton) findViewById(R.id.historybutton);
		settingsimg = (ImageButton) findViewById(R.id.settingsbutton);
		historyLabel = (TextView) findViewById(R.id.history_label);
		settingsLabel = (TextView) findViewById(R.id.settings_label);
		
		
	}
}