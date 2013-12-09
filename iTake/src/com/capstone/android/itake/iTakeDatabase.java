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
    
    private static final String COMPLY_DATABASE_TABLE = "COMPLYDATA";

    private static final int DATABASE_VERSION = 1;

    private static final String ALARM_CREATE = "CREATE TABLE ALARMDATA ("
    	+ "drug_id LONGTEXT NOT NULL, " // Drug ID Number
    	+ "time_data TEXT NOT NULL, " // Time for Alarm to begin
    	+ "alarm_interval TEXT NOT NULL, " // Alarm Interval
    	+ "alarm_id INTEGER);"; // Alarm ID Number
    
    private static final String COMPLIANCE_CREATE = "CREATE TABLE COMPLYDATA ("
        + "drug_id LONGTEXT NOT NULL, " // Unique Drug ID Number
        + "time_data TEXT NOT NULL, " // Time that Alarm happened
        + "comply_id INTEGER);"; // Compliance Number (0 for False, 1 for True)

    private SQLiteDatabase db;

    public iTakeDatabase(Context ctx) 
    {
    	super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL(ALARM_CREATE);
		db.execSQL(COMPLIANCE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub	
		db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + ALARM_DATABASE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + COMPLY_DATABASE_TABLE);
        onCreate(db);
        db.close();
	}
	
	public int alarm_tableSize()
	{
		db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT COUNT(*) AS NumberOfOrders FROM " + ALARM_DATABASE_TABLE, null);
		
		int entry_Size = 0;
		
		if (c.moveToFirst() && c != null)
		{
		    entry_Size = c.getInt(0);
		}
		
		c.close();
		
		return entry_Size;
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
    // Creates Compliance Data based on unique Drug ID
    public void comply_createRow(String Id, String time, int Compliance) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("drug_id", Id); // Pass Unique Drug ID
        initialValues.put("time_data", time); // Pass Alarm Time
        initialValues.put("comply_id", Compliance); // Pass Alarm ID Request Code
        db = this.getWritableDatabase();
        db.insert(COMPLY_DATABASE_TABLE, null, initialValues); // Insert Row Data
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
    
    // Obtain Comply data based on unique Drug ID and return Cursor
    public Cursor comply_GetRows(String rowId) 
    {
        try 
        {
        	db = this.getReadableDatabase();
        	Cursor c = db.query(COMPLY_DATABASE_TABLE, new String[] {"time_data", "comply_id", "drug_id"}, 
        			"drug_id=" + "\"" + rowId + "\"", null, null, null, null);
        	// Resulting Query closes Database connection and returns
        	// 4 columns based on one Drug ID
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
    
    // Obtain All Comply data and return Cursor
    public Cursor comply_GetAllRows() 
    {
        try 
        {
        	db = this.getReadableDatabase();
        	Cursor c = db.query(COMPLY_DATABASE_TABLE, new String[] {"time_data","comply_id", "drug_id"}, 
            		null, null, null, null, null);
        	// Resulting Query closes Database connection and returns
        	// 4 columns based on all Drug IDs
        	return c;
        } 
        catch (SQLException e) 
        {
            Log.e("Exception on query", e.toString());
            return null;
        }
    }
}