package com.slite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	
	public static String databaseName = "dsa.db";
	private static int databaseVersion = 1; 
	//TODO cann what the cursor factory, it is null in all examples, look for this
	private static CursorFactory cursorFactory = null;
	private static DbHelper dbHelper;


	public synchronized static DbHelper getInstance(Context context) {
		if (dbHelper == null) {
			dbHelper = new DbHelper(context.getApplicationContext());
		}
		return dbHelper;
	}

	
	private DbHelper(Context context) {
		super(context.getApplicationContext(), databaseName, cursorFactory, databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
