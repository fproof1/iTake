/**
 * 
 */
package com.capstone.android.itake;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;




/**
 * @author Wolfpack16 (Alex Kane)
 * 
 */
public class AlarmTimer extends Activity implements OnTimeSetListener
{
	public static String DRUG_ALARM_ID;
	public static String DRUG_NAME;
	public static String DRUG_DOSAGE;
	public static String DRUG_FREQUENCY;

	TimePickerDialog timePickerDialog;
	iTakeDatabase DBhelper;

	private static int AlarmID; // Alarm ID Code
	private boolean mIgnoreTimeSet;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		DBhelper = new iTakeDatabase(this);
		AlarmRecreate();
	}

	@Override
	protected void onStart() 
	{
		super.onStart();
		openTimePickerDialog(false);
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

	private void openTimePickerDialog(boolean is24r) 
	{
		Calendar calendar = Calendar.getInstance();
		
		mIgnoreTimeSet = false;

		timePickerDialog = new TimePickerDialog(AlarmTimer.this, AlarmTimer.this, 
				calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24r);
		
		timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() 
		{
		    public void onClick(DialogInterface dialog, int which) 
		    {
		        mIgnoreTimeSet = true;
		        timePickerDialog.onClick(dialog, which);
		    }
		});

		// Set the Cancel button
		timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Set Alarm", new DialogInterface.OnClickListener()
		{
		    public void onClick(DialogInterface dialog, int which) 
		    {
		    	mIgnoreTimeSet = false;
		        timePickerDialog.onClick(dialog, which);	        
		    }
		});
		
		timePickerDialog.setCancelable(false); // Has To Click Set Alarm or Cancel

		timePickerDialog.setTitle("Set Alarm Time");
		timePickerDialog.show();
	}
	
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) 
	{
		if (!mIgnoreTimeSet)
		{
			Calendar calNow = Calendar.getInstance();
			Calendar calset = (Calendar) calNow.clone();
	
			calset.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calset.set(Calendar.MINUTE, minute);
			calset.set(Calendar.SECOND, 0);
			calset.set(Calendar.MILLISECOND, 0);
	
			if (calset.compareTo(calNow) <= 0) 
			{
				// Today Set time passed, count to tomorrow
				calset.add(Calendar.DATE, 1);
			}
			AlarmSave(calset);
		}
		AlarmTimer.this.finish();
	};
	
	// Save Alarm Time Data in Database
	public void AlarmSave(Calendar alarmtime) 
	{
		Cursor c = DBhelper.alarm_GetRow(DRUG_ALARM_ID);
		try 
		{
			if (c.getCount() > 0 && c != null)
			{
				c.moveToFirst();
				// insert variables into DB
				DBhelper.alarm_updateRow(DRUG_ALARM_ID,	String.valueOf(alarmtime.getTimeInMillis()),
						c.getString(1));
				
				Intent intent = new Intent(getBaseContext(), AlarmOnReceive.class);
				intent.putExtra("UID", c.getString(3));
				intent.putExtra("REQUEST_NUMBER", c.getInt(2));
				
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), c.getInt(2), 
						intent, PendingIntent.FLAG_UPDATE_CURRENT);

				AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC_WAKEUP, alarmtime.getTimeInMillis(), pendingIntent);
				
				Toast.makeText(getBaseContext(), "Alarm Updated!", Toast.LENGTH_SHORT).show();
			}
			else
			{
				c.close();
				AlarmID = DBhelper.alarm_tableSize() + 1;
				
				// All New Alarms are given by default a daily frequency interval upon creation
				DBhelper.alarm_createRow(DRUG_ALARM_ID,	String.valueOf(alarmtime.getTimeInMillis()),
						String.valueOf(AlarmManager.INTERVAL_DAY), AlarmID);
				
				c = DBhelper.alarm_GetRow(DRUG_ALARM_ID);
				try
				{
					c.moveToFirst();
					
					Intent intent = new Intent(getBaseContext(), AlarmOnReceive.class);
					intent.putExtra("UID", c.getString(3));
					intent.putExtra("REQUEST_NUMBER", c.getInt(2));
					
					PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), c.getInt(2), 
							intent, PendingIntent.FLAG_UPDATE_CURRENT);
	
					AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
					alarmManager.set(AlarmManager.RTC_WAKEUP, alarmtime.getTimeInMillis(), pendingIntent);
					
					Toast.makeText(getBaseContext(), "Alarm Created!", Toast.LENGTH_SHORT).show();
				}
				catch (Exception f)
				{
					System.out.println("Created Alarm Lookup Error: " + f.getLocalizedMessage());
				}
			}
		} 
		catch (Exception e) 
		{
			Toast.makeText(getBaseContext(), "Database Save Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			System.out.println("Database Save Error: " + e.getLocalizedMessage());
		}
		c.close();
	}

	public void AlarmRecreate()
	{
		// Recreate Alarms if any exist
		Cursor c = DBhelper.alarm_GetAllRows();
		Calendar calNow = Calendar.getInstance();
		Calendar calset = (Calendar) calNow.clone();
		boolean missedmeds = false;
		
		try 
		{
			int numRows = c.getCount();
			c.moveToFirst();
			for (int i = 0; i < numRows; ++i) 
			{
				Intent intent = new Intent(getBaseContext(), AlarmOnReceive.class);
				intent.putExtra("UID", c.getString(3));
				intent.putExtra("REQUEST_NUMBER", c.getInt(2));
				
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), c.getInt(2), 
						intent, PendingIntent.FLAG_UPDATE_CURRENT);

				// Check to see if alarm time was passed
				if(calset.getTimeInMillis() < Long.parseLong(c.getString(0)))
				{
					// If not, compliance shall not be affected
					AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
					alarmManager.set(AlarmManager.RTC_WAKEUP, Long.parseLong(c.getString(0)), pendingIntent);
				}
				else
				{
					// If so, compliance is automatically set to 0
					// If it was missed multiple times, account for them
					missedmeds = true;
					long past_alarmtime = Long.parseLong(c.getString(0));
					
					while(calset.getTimeInMillis() > past_alarmtime)
					{
						DBhelper.comply_createRow(c.getString(3), String.valueOf(past_alarmtime), 0);
						past_alarmtime += Long.parseLong(c.getString(1));
					}
					
					// Update variables into DB
	    			DBhelper.alarm_updateRow(c.getString(3), String.valueOf(past_alarmtime), c.getString(1));
					
					// Set Alarm after compliance is accounted
					AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
					alarmManager.set(AlarmManager.RTC_WAKEUP, past_alarmtime, pendingIntent);
				}
				c.moveToNext();
			}
			
			if(missedmeds)
			{
				Toast.makeText(getBaseContext(), String.valueOf(numRows) + 
						" Alarms Recreated! Medication(s) were not taken on time!", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(getBaseContext(), String.valueOf(numRows) + " Alarms Recreated!", Toast.LENGTH_SHORT).show();
			}
		} 
		catch (Exception e) 
		{
			Toast.makeText(getBaseContext(), "Alarm Recreation Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			System.out.println("Alarm Recreation Error: " + e.getLocalizedMessage());
		}
		c.close();
	}
}
