package com.islora.lock.wx.server.db;

import java.sql.Connection;
import java.sql.DriverManager;




public class LogDBManager {
	private static LogDBManager instance;
	Connection connect = null;
	private LogDBManager(){
	}
	public static LogDBManager getInstance(){
		if(instance==null)
			instance=new LogDBManager();
		return instance;
	}
	public void init(){
		try {
			Class.forName("org.sqlite.JDBC");
			connect = DriverManager.getConnection("jdbc:sqlite:log0.db");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
