package org.dbms.ks.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

public class QueryUtil {
	
	private static Properties props = new Properties();
	
	public static void loadQueries(ServletContext context) {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(new File(context.getRealPath("/WEB-INF/config/queries.props")));
			props.load(fs);
			System.out.println("queries loaded" +  props.toString());
		} catch(Exception e) {
			//TODO Log
		} finally {
			DBUtil._safeClose(fs);
		}
	}
	
	public static String get(String name) {
		return props.getProperty(name);
	}
}
