/**
 * 
 */
package com.example.itake;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * @author Wolfpack16 (Alex Kane)
 *
 */
public class AlarmTimer extends Activity 
{
	TimePicker myTimePicker;
    Button buttonstartSetDialog;
    TextView textAlarmPrompt;
    TimePickerDialog timePickerDialog;

    final static int RQS_1 = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textAlarmPrompt = (TextView) findViewById(R.id.alarmprompt);
        
        buttonstartSetDialog = (Button) findViewById(R.id.startAlarm);
        buttonstartSetDialog.setOnClickListener(
        	new OnClickListener() 
        	{
	            @Override
	            public void onClick(View v) 
	            {
	                textAlarmPrompt.setText("");
	                openTimePickerDialog(false);
	            }
	        }
        );
    }

    private void openTimePickerDialog(boolean is24r) 
    {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(AlarmTimer.this,
        		onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    OnTimeSetListener onTimeSetListener = new OnTimeSetListener() 
    {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) 
        {
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) 
            {
                // Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }
            setAlarm(calSet);
        }
    };

    public void setAlarm(Calendar targetCal) 
    {
        textAlarmPrompt.setText("\n\n***\n" + "Alarm is set " + targetCal.getTime() + "\n" + "***\n");
        
        Intent intent = new Intent(getBaseContext(), AlarmOnReceive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), AlarmManager.INTERVAL_HOUR / 360, pendingIntent);
  //      alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }  
}
/*    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        //Create an offset from the current time in which the alarm will go off.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);

        //Create a new PendingIntent and add it to the AlarmManager
        textAlarmPrompt.setText("\n\n***\n" + "Alarm is set " + targetCal.getTime() + "\n" + "***\n");
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
        		12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
    
}*/