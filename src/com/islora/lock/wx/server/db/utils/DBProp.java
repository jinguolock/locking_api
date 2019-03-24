package com.islora.lock.wx.server.db.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;


public class DBProp {
	private static Properties prop;
	public static String path=null;
	public static DateFormat format=new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
	static{
		try {
			loadProperties();
		} catch (Exception e) {
		}
	}
	
	private static void loadProperties()throws Exception{
		if(prop==null){
			prop = new Properties();
			 InputStream in = DBProp.class.getClassLoader().getResourceAsStream("db.properties");
			 prop.load(in);
		}
	}
	public static String getPropStr(String key){
		return prop.getProperty(key);
	}

}