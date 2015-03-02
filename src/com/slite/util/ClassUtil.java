package com.slite.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.util.Log;
import dalvik.system.DexFile;

public class ClassUtil {
	private static String className = ClassUtil.class.getName();

	public static List<Class<?>> getAllClasses(Context context) {
		ArrayList<Class<?>> classList = new ArrayList<Class<?>>();

		try {
			DexFile dexFile = new DexFile(context.getPackageCodePath());
			Enumeration<String> iter = dexFile.entries();

			while (iter.hasMoreElements()) {
				classList.add(Class.forName(iter.nextElement()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(className, e.getMessage());
		}
		return classList;
	}

	@SuppressWarnings("unused")
	private static List<String> getAllPackages(Context context) {
		return null;
	}

	@SuppressWarnings("unused")
	private static List<Class<?>> findClassesInDirectory(File directory, String packageName) {
		return null;
	}

	@SuppressWarnings("unused")
	private static List<Class<?>> getClassesInPackage(String packageName, Context context) {
		return null;
	}
}
