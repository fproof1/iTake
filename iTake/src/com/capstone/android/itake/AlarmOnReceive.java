package com.capstone.android.itake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmOnReceive extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
	{
	    Intent i = new Intent(context, AlarmReceiver.class);
	    AlarmReceiver.ALARM_ID = intent.getStringExtra("UID");
	    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    context.startActivity(i);
	}
}
