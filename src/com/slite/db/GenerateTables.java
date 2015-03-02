package com.slite.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.slite.annotation.Table;
import com.slite.util.ClassUtil;
import com.slite.util.ColumnUtil;

public class GenerateTables {

	private static String className = GenerateTables.class.getName();

	private Locale enLocale = new Locale("EN");
	private DbHelper dbHelper = null;
	private Context context = null;

	public GenerateTables(Context context) {
		this.context = context;
		this.dbHelper = DbHelper.getInstance(context);
	}

	public List<String> generateCreateScript() {
		List<Class<?>> clazzList = getModelClasses();
		List<String> scriptList = new ArrayList<String>();

		for (Class<?> clazz : clazzList) {
			String tableName = clazz.getAnnotation(Table.class).name();
			if (tableName == null || tableName.equals("")) {
				tableName = NamingStrategy.rename(clazz.getSimpleName());
			}

			tableName = tableName.toUpperCase(enLocale);
			Field[] fields = clazz.getDeclaredFields();

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("CREATE TABLE '");
			stringBuilder.append(tableName);
			stringBuilder.append("' (");

			for (Field field : fields) {
				stringBuilder.append(ColumnUtil.getColumnScript(field));
				stringBuilder.append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			stringBuilder.append(");");
			scriptList.add(stringBuilder.toString());
			Log.d("-->", stringBuilder.toString());
		}

		SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
		for (String script : scriptList) {
			sqLiteDatabase.execSQL(script);
		}
		return scriptList;
	}

	private List<Class<?>> getModelClasses() {
		List<Class<?>> modelClassList = new ArrayList<Class<?>>();
		List<Class<?>> classList = null;
		try {
			classList = ClassUtil.getAllClasses(context);

		} catch (Exception e) {
			Log.e(className, e.getMessage());
		}
		if (classList != null) {
			for (Class<?> clazz : classList) {
				if (clazz.isAnnotationPresent(Table.class)) {
					modelClassList.add(clazz);
				}
			}
		}
		return modelClassList;
	}
}
