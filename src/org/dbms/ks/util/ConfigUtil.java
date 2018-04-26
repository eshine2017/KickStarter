package org.dbms.ks.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

public class ConfigUtil {
	
	private static Properties props = new Properties();
	
	public static void loadConfig(ServletContext context) {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(new File(context.getRealPath("/WEB-INF/config/config.props")));
			props.load(fs);
			System.out.println("props loaded" +  props.toString());
		} catch(Exception e) {
			//TODO Log
		} finally {
			DBUtil._safeClose(fs);
		}
	}
	
	public static String get(String name) {
		return get(name, null);
	}
	
	public static String get(String name, String defaultValue) {
		return props.getProperty(name, defaultValue);
	}
}
