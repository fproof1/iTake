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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * @author WhiteDivinity
 *
 */
public class AlarmReceiver extends Activity 
{
    private MediaPlayer mMediaPlayer; 

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.alarm);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Medicine Alert");
        alertDialogBuilder.setMessage("Insert Medication Name Here");
        
        alertDialogBuilder.setNegativeButton("Taken", new DialogInterface.OnClickListener() 
        {                   
        	public void onClick(DialogInterface dialog, int which) 
        	{
                mMediaPlayer.stop();
                finish();
            } //end onClick.
        }); // end alertDialog.setButton.
        
        alertDialogBuilder.setPositiveButton("Remind Me Later", new DialogInterface.OnClickListener() 
        {                   
        	public void onClick(DialogInterface dialog, int which) 
        	{
                mMediaPlayer.stop();
                finish();
            } //end onClick.
        }); // end alertDialog.setButton.
        alertDialogBuilder.show();  
  //      Button theButton = alertDialogBuilder.getButton(DialogInterface.BUTTON_POSITIVE);
   //     theButton.setOnClickListener(new OnClickListener(alertDialogBuilder));
     /*   Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
        stopAlarm.setOnTouchListener(new OnTouchListener() 
        {
            public boolean onTouch(View arg0, MotionEvent arg1) 
            {
                mMediaPlayer.stop();
                finish();
                return false;
            }
        });*/
        playSound(this, getAlarmUri());
    }

    private void playSound(Context context, Uri alert) 
    {
        mMediaPlayer = new MediaPlayer();
        try
        {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) 
            {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
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