package com.capstone.android.itake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
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
		
		drugimg.setOnClickListener(this);
		historyimg.setOnClickListener(this);
		settingsimg.setOnClickListener(this);
		cheatimg.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.drugbutton:
			openDrugScreen();
			break;
		case R.id.cheatsbutton:
			openCheatSheets();
			break;
		case R.id.historybutton:
			openHistory();
			break;
		case R.id.settingsbutton:
			openSettings();
			break;
		}
		
	}

	private void openSettings() {
		startActivity(new Intent(MainActivity.this, SettingsActivity.class));
		
	}

	private void openHistory() {
		// TODO Auto-generated method stub
		
	}

	private void openCheatSheets() {
		// TODO Auto-generated method stub
		
	}

	private void openDrugScreen() {
		startActivity(new Intent(MainActivity.this, DrugListActivity.class));
		
	}
}