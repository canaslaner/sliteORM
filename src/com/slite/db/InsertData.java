package com.slite.db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class InsertData {

	public static void insertData(Context context) {
		String[] fileNameList = null;
		try {
			fileNameList = context.getApplicationContext().getAssets().list("sql");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (fileNameList != null) {
			for (String fileName : fileNameList) {
				if (!fileName.endsWith(".sql")) {
					break;
				}
				Log.i("File Name : ", fileName);
				List<String> sqlList = new ArrayList<String>();
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new InputStreamReader(context.getApplicationContext().getAssets()
							.open("sql/" + fileName)));
					String mLine = reader.readLine();
					while (mLine != null) {
						sqlList.add(mLine);
						mLine = reader.readLine();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							// log the exception
						}
					}
				}

				execSQL(sqlList, context);
			}
		}
	}

	private static void execSQL(List<String> sqlList, Context context) {
		DbHelper dbHelper = DbHelper.getInstance(context);
		SQLiteDatabase sqLiteDatabase = null;

		try {
			sqLiteDatabase = dbHelper.getWritableDatabase();
			for (String sql : sqlList) {
				sqLiteDatabase.execSQL(sql);
			}
			Log.d("okk", "toplam insert : " + sqlList.size());
		} catch (Exception e) {
			Log.e("rawQuery Method", "exception in rawQuery method");
			e.printStackTrace();
		} finally {
			if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
				sqLiteDatabase.close();
			}
		}
	}
}
