package com.example.itake;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AlarmDatabase extends SQLiteOpenHelper
{
    class Row extends Object 
    {
        public long alarm_Id;
        public String time_data;
    }
    
    private static final String DATABASE_NAME = "iTake.db";

    private static final String DATABASE_TABLE = "ALARMDATA";

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE = "CREATE TABLE ALARMDATA ("
    	+ "alarm_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ "time_data TEXT NOT NULL);";

    private SQLiteDatabase db;

    public AlarmDatabase(Context ctx) 
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
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
        db.close();
	}

    public void createRow(String time) 
    {
    	db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("time_data", time);
        db.insert(DATABASE_TABLE, null, initialValues);
        db.close();
    }

    public void deleteRow(long rowId) 
    {
    	db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, "alarm_id=" + rowId, null);
        db.close();
    }

    public List<Row> fetchAllRows() 
    {
        ArrayList<Row> ret = new ArrayList<Row>();
        try 
        {
            Cursor c = db.query(DATABASE_TABLE, new String[] {"alarm_id", "time_data"}, null, null, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) 
            {
                Row row = new Row();
                row.alarm_Id = c.getLong(0);
                row.time_data = c.getString(1);
                ret.add(row);
                c.moveToNext();
            }
        } 
        catch (SQLException e) 
        {
            Log.e("Exception on query", e.toString());
        }
        return ret;
    }

    public Row fetchRow(long rowId) 
    {
        Row row = new Row();
        Cursor c = db.query(true, DATABASE_TABLE, new String[] {"alarm_id", "time_data"},
        		"alarm_id=" + rowId, null, null, null, null, null);
        if (c.getCount() > 0) 
        {
            c.isFirst();
            row.alarm_Id = c.getLong(0);
            row.time_data = c.getString(1);
            return row;
        } 
        else 
        {
            row.alarm_Id = -1;
            row.time_data = null;
        }
        return row;
    }

    public void updateRow(long rowId, String time) 
    {
    	db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put("time_data", time);
        db.update(DATABASE_TABLE, args, "alarm_id=" + rowId, null);
        db.close();
    }
 
    public Cursor GetAllRows() 
    {
        try 
        {
            return db.query(DATABASE_TABLE, new String[] {"alarm_id", "time_data"}, null, null, null, null, null);
        } 
        catch (SQLException e) 
        {
            Log.e("Exception on query", e.toString());
            return null;
        }
    }
}