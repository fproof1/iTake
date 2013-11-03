/**
 * 
 */
package com.example.itake;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Wolfpack16 (R. Alex Kane)
 *
 */
public class AlarmReceiver extends Activity 
{
	private MediaPlayer mMediaPlayer; 
	private Vibrator phoneVibrate;
	private AudioManager audioManager;

	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
   //     setContentView(R.layout.main);

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
                AlarmReceiver.this.finish();
            } //end onClick.
        }); // end alertDialog.setButton.
        
        alertDialogBuilder.setPositiveButton("Remind Me Later", new DialogInterface.OnClickListener() 
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
                AlarmReceiver.this.finish();
            } //end onClick.
        }); // end alertDialog.setButton.
        alertDialogBuilder.show();  
        playSound(AlarmReceiver.this, getAlarmUri());
    }

	private void playSound(AlarmReceiver alarmReceiver, Uri alert) 
    {
        mMediaPlayer = new MediaPlayer();
        try // Attempt Alarm Play
        {
            mMediaPlayer.setDataSource(getBaseContext(), alert);
            audioManager = (AudioManager) alarmReceiver.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) // If Volume isn't 0
            {
            	mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
            else // If Volume is 0, vibrate
            {
            	// Get instance of Vibrator from current Context
            	phoneVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            	// Start without a delay
            	// Vibrate for 100 milliseconds
            	// Sleep for 1000 milliseconds
            	long[] pattern = {0, 1000, 1000};

            	// The '0' here means to repeat indefinitely
            	// '-1' would play the vibration once
            	phoneVibrate.vibrate(pattern, 0);
            }
        } 
        catch (IOException e) 
        {
            System.out.println("OOPS");
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
}