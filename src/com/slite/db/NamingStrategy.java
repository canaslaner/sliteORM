package com.slite.db;

import java.util.Locale;

public class NamingStrategy {

	private static Locale enLocale = new Locale("EN");

	public static String rename(String base) {
		String[] r = base.split("(?<=[a-z])(?=[A-Z])");
		base = "";
		for (String str : r) {
			base += str.toUpperCase(enLocale) + "_";
		}
		return base.substring(0, base.length() - 1);
	}
}
