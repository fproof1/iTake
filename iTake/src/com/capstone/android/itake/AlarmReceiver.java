/**


 * 
 */
package com.capstone.android.itake;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @author Wolfpack16 (R. Alex Kane)
 *
 */
public class AlarmReceiver extends Activity 
{
	public static String ALARMID;
	public static int REQ_CODE;
	
	private MediaPlayer mMediaPlayer; 
	private Vibrator phoneVibrate;
	private AudioManager audioManager;
	
	iTakeDatabase DBhelper;

	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);  
    }
	
	@Override
    protected void onStart() 
    {
        super.onStart();
        // The activity is about to become visible.
        
        //  Intent intent = getIntent();
        // ALARMID = intent.getStringExtra("ID");
        ALARMID = this.getIntent().getStringExtra("ID");
        REQ_CODE = this.getIntent().getIntExtra("RQS", 0);
        DBhelper = new iTakeDatabase(this);
        
        if(checkAlarm(ALARMID))
        {
        
	        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AlarmReceiver.this);
	        alertDialogBuilder.setTitle("Medicine Alert")
	        				  .setMessage("Insert Medication(s) Name Here")
	        				  .setCancelable(false); // User has to respond to dialog call
	        
	        alertDialogBuilder.setNegativeButton("Taken", new DialogInterface.OnClickListener() 
	        {                   
	        	public void onClick(DialogInterface dialog, int which) 
	        	{
	        		if(phoneVibrate != null)
	        		{
	        			phoneVibrate.cancel();
	        		}
	        		else
	        		{
		        		mMediaPlayer.stop();
	        		}
	        		alarmUpdate(ALARMID, 1);
	                AlarmReceiver.this.finish();
	            } //end onClick.
	        }); // end alertDialog.setButton.
	        
	        alertDialogBuilder.setPositiveButton("Remind Me Later", new DialogInterface.OnClickListener() 
	        {               	
	        	public void onClick(DialogInterface dialog, int which) 
	        	{
	        		if(phoneVibrate != null) // If phoneVibrate was set
	        		{
	        			phoneVibrate.cancel();
	        		}
	        		else // Else, Assume Alarm Sound was set
	        		{
		        		mMediaPlayer.stop();
	        		}
	        		alarmUpdate(ALARMID, 0);
	                AlarmReceiver.this.finish();
	            } //end onClick.
	        }); // end alertDialog.setButton.
	        alertDialogBuilder.show();  
	        playSound(AlarmReceiver.this, getAlarmUri());
        }
        else
        {
        	AlarmReceiver.this.finish();
        }
    }
    @Override
    protected void onResume() 
    {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        playSound(AlarmReceiver.this, getAlarmUri());
    }
    @Override
    protected void onPause() 
    {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        
        if(phoneVibrate != null) // If phoneVibrate was set
		{
			phoneVibrate.cancel();
		}
		else // Else, Assume Alarm Sound was set
		{
    		mMediaPlayer.stop();
		}
    }
    @Override
    protected void onStop() 
    {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        
        if(phoneVibrate != null) // If phoneVibrate was set
		{
			phoneVibrate.cancel();
		}
		else // Else, Assume Alarm Sound was set
		{
    		mMediaPlayer.stop();
		}
    }
    @Override
    protected void onDestroy() 
    {
        super.onDestroy();
        // The activity is about to be destroyed.
        
        if(phoneVibrate != null) // If phoneVibrate was set
		{
			phoneVibrate.cancel();
		}
		else // Else, Assume Alarm Sound was set
		{
    		mMediaPlayer.stop();
		}
    }

	private void playSound(AlarmReceiver alarmReceiver, Uri alert) 
    {
        try // Attempt Alarm Play
        {
            audioManager = (AudioManager) alarmReceiver.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0) // If Volume isn't 0
            {
            	mMediaPlayer = new MediaPlayer();
            	mMediaPlayer.setDataSource(getBaseContext(), alert);
            	
            	mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            	mMediaPlayer.setVolume(audioManager.getStreamVolume(AudioManager.STREAM_RING), 
            			audioManager.getStreamVolume(AudioManager.STREAM_RING));
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
            else // If Volume is 0, vibrate
            {
            	// Get instance of Vibrator from current Context
            	phoneVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            	// Start without a delay
            	// Vibrate for 1000 milliseconds
            	// Sleep for 1000 milliseconds
            	long[] pattern = {0, 1000, 1000};

            	// The '0' here means to repeat indefinitely
            	// '-1' would play the vibration once
            	phoneVibrate.vibrate(pattern, 0);
            }
        } 
        catch (IOException e) 
        {
        	Toast.makeText(getBaseContext(), "Sound/Vibrate Playing Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        	System.out.println("Sound/Vibrate Playing Error: " + e.getLocalizedMessage());
        }
    }

    //Get an alarm sound. Try for an alarm. If none set, try notification, 
    //Otherwise, ringtone.
    private Uri getAlarmUri() 
    {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) 
        {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) 
            {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }
    
    public boolean checkAlarm(String Id)
    {
    	Cursor c = DBhelper.alarm_GetRow(Id);
    	try
        {
    		// If Drug Doesn't Exist within Database, Remove Alarm
    		if(c.getCount() <= 0 || c == null)
    		{
    			AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    			
    			PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), REQ_CODE, 
    					this.getIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
    			
    			alarm.cancel(pendingIntent);
    			
    			Toast.makeText(getBaseContext(), "Drug Not Found! Alarm Removed!", Toast.LENGTH_SHORT).show();
    			
    			c.close();
    			
    			return false;
    		}
    		else // Drug Exists, Alarm needs to occur and update
    		{
    			c.close();
    			return true;
    		}
        }
		catch(Exception e)
        {
        	Toast.makeText(getBaseContext(), "Alarm Check Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("Alarm Check Error: " + e.getLocalizedMessage());
            c.close();
            return false;
        }
    }
    
    // Update Alarm Time Data in Database
    public void alarmUpdate(String Id, int Compliance)
    {
    	Cursor c = DBhelper.alarm_GetRow(Id);
    	try
        {    
    		if(c.getCount() > 0 && c != null)
    		{
    			c.moveToFirst();
	        	
	        	Calendar calNow = Calendar.getInstance();
    			Calendar calset = (Calendar) calNow.clone();
    			
    			long past_alarms = Long.parseLong(c.getString(0)) + Long.parseLong(c.getString(1));
				
    			// If Reminder was Continuously Going, which doesn't allow
    			// other alarms to be set or occur, then set
    			// those as non-compliant
				while(calset.getTimeInMillis() > past_alarms)
				{
					DBhelper.comply_createRow(c.getString(3), String.valueOf(past_alarms), 0);
					past_alarms += Long.parseLong(c.getString(1));
				}
    			
    			// Check for Alarm Update Occurred already
    			// based on 30 Minute Reminder already been set
    			// which accounts for continuous Reminders
    			if(Long.parseLong(c.getString(0)) < calset.getTimeInMillis() + 1800000)
    			{
    				// Update variables into DB
    				DBhelper.alarm_updateRow(Id, String.valueOf(past_alarms), c.getString(1));
    				
    				Intent intent = new Intent(getBaseContext(), AlarmOnReceive.class);
        			intent.putExtra("UID", c.getString(3));
    				
    				PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), c.getInt(2), 
    	        			intent, PendingIntent.FLAG_UPDATE_CURRENT);
    				
    				AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    				
    				// Set Regular Alarm
    				alarmManager.set(AlarmManager.RTC_WAKEUP, past_alarms, pendingIntent);
    				
    				Toast.makeText(getBaseContext(), "Regular Alarm Updated!", Toast.LENGTH_SHORT).show();
    			}
    			
    			if(Compliance == 1) // Taken
    			{
    				// Update Compliance into DB
        			DBhelper.comply_createRow(c.getString(3), c.getString(0), Compliance);
        			
        			Toast.makeText(getBaseContext(), "Compliance Updated!", Toast.LENGTH_SHORT).show();
    			}
    			else if(Compliance == 0) // Remind Me Later
    			{
    				// Do not store alarm update within Database
        			// but account for Compliance instead
    				
    				// Update Compliance into DB
        			DBhelper.comply_createRow(c.getString(3), String.valueOf(calset.getTimeInMillis()), Compliance);
        			
        			Intent intent_reminder = new Intent(getBaseContext(), AlarmOnReceive.class);
        			intent_reminder.putExtra("UID", c.getString(3));
        			
        			PendingIntent pendingIntent_Reminder = PendingIntent.getBroadcast(getBaseContext(), c.getInt(2), 
    	        			intent_reminder, PendingIntent.FLAG_UPDATE_CURRENT);
    				
        			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        			
        			// Alarm Reminder in 30 Minutes
    				alarmManager.set(AlarmManager.RTC_WAKEUP, Long.parseLong(c.getString(0)) + 1800000, pendingIntent_Reminder); 
    				// 1800000 = 30 Minutes in MilliSeconds
    				
    				Toast.makeText(getBaseContext(), "Alarm Reminder in 30 Minutes!", Toast.LENGTH_SHORT).show();
    			}
    			else // What the Hell Happened~
    			{
    				Toast.makeText(getBaseContext(), "Compliance Storage Error! Alarm Update Aborted!", Toast.LENGTH_SHORT).show();
    			}
    		}
    		else // Drug Doesn't Exist? Remove Alarm
    		{
    			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    			
    			PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), REQ_CODE, 
	        			this.getIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
    			
    			alarmManager.cancel(pendingIntent);
    			
    			Toast.makeText(getBaseContext(), "Drug Not Found! Alarm Removed!", Toast.LENGTH_SHORT).show();
    		}
        }
        catch(Exception e)
        {
        	Toast.makeText(getBaseContext(), "Database Update Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("Database Update Error: " + e.getLocalizedMessage());
        }
    	c.close();
    }
}