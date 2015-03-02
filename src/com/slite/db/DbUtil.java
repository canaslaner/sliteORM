package com.slite.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.util.Log;

public class DbUtil {

	public static String getDbFullPath(Context applicationContext){
		return applicationContext.getDatabasePath(DbHelper.databaseName).toString();
	}
	
	public static String getDbPath(Context applicationContext){
		File file = new File(getDbFullPath(applicationContext));
		return file.getParent();
	}
	
	public static boolean dbExists(Context applicationContext) {
		File database = new File(getDbFullPath(applicationContext));

		if (!database.exists()) {
			Log.i("Database", "Not Found");
			return false;
		} else {
			Log.i("Database", "Found");
			return true;
		}
	}

	public static void copyDataBase(Context applicationContext) throws IOException {
		if(!makeDirs(getDbPath(applicationContext))){
			new IOException("Path is not exist and can not be created!!");
		}
		
		InputStream inputStream = applicationContext.getAssets().open("db/" + DbHelper.databaseName);
		String outFileName = getDbFullPath(applicationContext);
		OutputStream outpuStream = new FileOutputStream(outFileName, false);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outpuStream.write(buffer, 0, length);
		}

		outpuStream.flush();
		outpuStream.close();
		inputStream.close();
	}
	
	/**
	 * 
	 * @param path
	 * @return true if path is exists 
	 */
	private static boolean makeDirs(String path){
		if(path != null){
			File file = new File(path);
			if(!file.isDirectory()){
				file = file.getParentFile();
			}
			file.mkdirs();
			return file.exists();
		}
		return false;
	}
}
