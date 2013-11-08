package com.example.itake;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service 
{	
	@Override
	public void onCreate() 
	{
		Intent i = new Intent(this, AlarmReceiver.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    startActivity(i);
	}

	@Override
	public IBinder onBind(Intent arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}
}