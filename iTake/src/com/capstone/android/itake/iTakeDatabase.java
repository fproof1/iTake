package com.capstone.android.itake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class iTakeDatabase extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "iTake.db";

    private static final String ALARM_DATABASE_TABLE = "ALARMDATA";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE ALARMDATA ("
    	+ "drug_id LONGTEXT NOT NULL, " // Drug ID Number
    	+ "time_data TEXT NOT NULL, " // Time for Alarm to begin
    	+ "alarm_interval TEXT NOT NULL, " // Alarm Interval
    	+ "alarm_id INTEGER);"; // Alarm ID Number

    private SQLiteDatabase db;

    public iTakeDatabase(Context ctx) 
    {
    	super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub	
		db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + ALARM_DATABASE_TABLE);
        onCreate(db);
        db.close();
	}

    public void alarm_createRow(String Id, String time, String Interval, int AlarmID) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("drug_id", Id);
        initialValues.put("time_data", time);
        initialValues.put("alarm_interval", Interval);
        initialValues.put("alarm_id", AlarmID);
        db = this.getWritableDatabase();
        db.insert(ALARM_DATABASE_TABLE, null, initialValues);
        db.close();
    }

    public void alarm_deleteRow(String rowId) 
    {
    	db = this.getWritableDatabase();
        db.delete(ALARM_DATABASE_TABLE, "drug_id="+ "\"" + rowId + "\"", null);
        db.close();
    }
 
    public void alarm_updateRow(String rowId, String time, String Interval) 
    {
        ContentValues args = new ContentValues();
        args.put("time_data", time);
        args.put("alarm_interval", Interval);
        db = this.getWritableDatabase();
        db.update(ALARM_DATABASE_TABLE, args, "drug_id=" + "\"" + rowId + "\"", null);
        db.close();
    }
    
    public Cursor alarm_GetRow(String rowId) 
    {
        try 
        {
        	db = this.getReadableDatabase();
        	Cursor c = db.query(ALARM_DATABASE_TABLE, new String[] {"time_data", "alarm_interval", "alarm_id", "drug_id"}, 
        			"drug_id=" + "\"" + rowId + "\"", null, null, null, null);
        	return c;
        } 
        catch (SQLException e) 
        {
            Log.e("Exception on query", e.toString());
            return null;
        }
    }
 
    public Cursor alarm_GetAllRows() 
    {
        try 
        {
        	db = this.getReadableDatabase();
        	Cursor c = db.query(ALARM_DATABASE_TABLE, new String[] {"time_data", "alarm_interval", "alarm_id", "drug_id"}, 
            		null, null, null, null, null);
        	return c;
        } 
        catch (SQLException e) 
        {
            Log.e("Exception on query", e.toString());
            return null;
        }
    }
}