package com.capstone.android.itake;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TimePicker;

/**
 * @author Wolfpack16 (Alex Kane)
 * 
 */
public class AlarmFrequency extends Activity implements OnTimeSetListener
{
	public static String DRUG_ALARM_ID;

	TimePickerDialog timePickerDialog;
	iTakeDatabase DBhelper;

	//private static int AlarmID; // Alarm ID Code
	//private boolean mIgnoreTimeSet;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		DBhelper = new iTakeDatabase(this);
		
		setContentView(R.layout.alarm_light);
		
		NumberPicker number = (NumberPicker) findViewById(R.id.numberPicker);
		NumberPicker interval = (NumberPicker) findViewById(R.id.intervalPicker);
		
        number.setMaxValue(8);
        number.setMinValue(1);
        number.setFocusable(true);
        number.setFocusableInTouchMode(true);
        
        interval.setMaxValue(4);
        interval.setMinValue(0);
        interval.setDisplayedValues( new String[] { "Hours", "Days", "Weeks", "Months", "Years" });
        interval.setFocusable(true);
        interval.setFocusableInTouchMode(true);
	}

	@Override
	protected void onStart() 
	{
		super.onStart();
		// The activity is about to become visible.
	}

	@Override
	protected void onRestart() 
	{
		super.onRestart();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		// The activity has become visible (it is now "resumed").
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		// Another activity is taking focus (this activity is about to be
		// "paused").
	}

	@Override
	protected void onStop() 
	{
		super.onStop();
		// The activity is no longer visible (it is now "stopped")
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		// The activity is about to be destroyed.
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		
	}
}
