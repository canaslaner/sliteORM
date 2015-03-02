package com.slite.util;

import java.lang.reflect.Field;
import java.math.BigInteger;

import com.slite.annotation.Column;
import com.slite.annotation.Id;
import com.slite.annotation.Unique;
import com.slite.db.NamingStrategy;

public class ColumnUtil {

	private final static int INTEGER_DEFAULT_LENGTH = 5;
	private final static int REAL_DEFAULT_LENGTH = 5;
	private final static int NUMERIC_DEFAULT_LENGTH = 5;
	private final static int TEXT_DEFAULT_LENGTH = 255;
	private final static int NONE_DEFAULT_LENGTH = 5;

	public static boolean isColumnId(Field field) {
		if (field.isAnnotationPresent(Id.class)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static boolean isColumnUnique(Field field) {
		if (field.isAnnotationPresent(Unique.class)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static String getColumnName(Field field) {
		String columnName = null;
		Column column = getColumnAnnotation(field);

		if (column != null && column.name() != null && !column.name().equals("")) {
			columnName = column.name();
		} else {
			columnName = field.getName();
		}

		columnName = NamingStrategy.rename(columnName);
		return columnName;
	}

	public static String getColumnType(Field field) {
		Column column = getColumnAnnotation(field);

		String type = null;
		Class<?> fieldType = field.getType();
		int defaultlength = 0;

		if (int.class == fieldType || Integer.class == fieldType || long.class == fieldType
				|| Long.class == fieldType || short.class == fieldType || Short.class == fieldType
				|| BigInteger.class == fieldType) {
			type = "INTEGER";
			defaultlength = INTEGER_DEFAULT_LENGTH;
		} else if (Float.class == fieldType || float.class == fieldType || double.class == fieldType
				|| Double.class == fieldType) {
			type = "REAL";
			defaultlength = REAL_DEFAULT_LENGTH;
		} else if (boolean.class == fieldType || Boolean.class == fieldType) {
			type = "NUMERIC";
			defaultlength = NUMERIC_DEFAULT_LENGTH;
		} else if (char.class == fieldType || Character.class == fieldType || String.class == fieldType) {
			type = "TEXT";
			defaultlength = TEXT_DEFAULT_LENGTH;
		} else if (byte.class == fieldType || Byte.class == fieldType) {
			type = "NONE";
			defaultlength = NONE_DEFAULT_LENGTH;
		}

		if (!isColumnId(field)) {
			if (column != null && column.length() > 0) {
				type = type + "(" + column.length() + ")";
			} else {
				type = type + "(" + defaultlength + ")";
			}
		}

		return type;
	}

	public static Column getColumnAnnotation(Field field) {
		Column column = null;
		if (field.isAnnotationPresent(Column.class)) {
			field.getAnnotation(Column.class);
			column = field.getAnnotation(Column.class);
		}
		return column;
	}

	public static String getColumnScript(Field field) {
		Column column = getColumnAnnotation(field);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("'");
		stringBuilder.append(getColumnName(field));
		stringBuilder.append("' ");
		stringBuilder.append(getColumnType(field));
		if (column != null && !column.nullable()) {
			stringBuilder.append(" NOT NULL");
		}
		if (isColumnUnique(field)) {
			stringBuilder.append(" UNIQUE");
		}
		if (isColumnId(field)) {
			stringBuilder.append(" PRIMARY KEY AUTOINCREMENT");
		}

		return stringBuilder.toString();
	}
}
