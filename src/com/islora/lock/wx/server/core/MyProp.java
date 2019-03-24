package com.islora.lock.wx.server.core;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSONObject;


public class MyProp {
	private static Properties prop;
	public static String path=null;
	public static DateFormat format=new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");

	static{
		try {
			loadProperties();
		} catch (Exception e) {
		}
	}
	public static String getBleLogResult(String msg,String type) {
		if("open".equals(type)) {
			if("01".equals(msg)) {
				return "开锁成功";
			}else if("00".equals(msg)){
				return "验证失败";
			}else if("04".equals(msg)){
				return "数据错误";
			}else if("03".equals(msg)){
				return "密码错误";
			}else if("02".equals(msg)){
				return "时间错误";
			}
		}

		if("authcard".equals(type)) {
			if("01".equals(msg)) {
				return "已授权";
			}else if("02".equals(msg)){
				return "卡号不对，未授权";
			}else if("03".equals(msg)){
				return "卡授权失败，未授权";
			}else if("00".equals(msg)){
				return "没有卡，未授权";
			}else if("05".equals(msg)){
				return "时间问题，未授权";
			}else if("06".equals(msg)){
				return "密码问题，未授权";
			}else if("04".equals(msg)){
				return "写卡失败，未授权";
			}
		}
		
		return "unknown";
	}
	public static JSONObject getAddLockJSON(String sn,String pwd,int status,int version,String ids,int type) {
		JSONObject jo=new JSONObject();
		jo.put("target", "station");
		jo.put("method", "lclock");
		jo.put("cmd", "addlock");
		jo.put("id", sn);
		jo.put("ids", ids);
		jo.put("type", String.valueOf(type));
		jo.put("value", pwd);
		jo.put("update", String.valueOf(version));
		jo.put("status", String.valueOf(status));
		return jo;
	}
	public static JSONObject getEditLockJSON(String sn,String pwd,int status,int version,String ids,int type) {
		JSONObject jo=new JSONObject();
		jo.put("target", "station");
		jo.put("method", "lclock");
		jo.put("cmd", "editlock");
		jo.put("id", sn);
		jo.put("value", pwd);
		jo.put("ids", ids);
		jo.put("type", type);
		jo.put("value", pwd);
		jo.put("update", version);
		jo.put("status", String.valueOf(status));
		return jo;
	}
	public static JSONObject getDeleteLockJSON(String sn) {
		JSONObject jo=new JSONObject();
		jo.put("target", "station");
		jo.put("method", "lclock");
		jo.put("cmd", "deletelock");
		jo.put("id", sn);
		return jo;
	}
	public static String getNewLockKey() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<4;i++) {
			int a=(int)(Math.random()*255);
			byte b=(byte)(a&0xff);
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}
	public static String getCardLogResult(int msg) {
		if(msg==1) {
			return "开锁成功";
		}else if("04".equals(msg)){
			return "没有卡";
		}else if("05".equals(msg)){
			return "时间码错误";
		}else if("03".equals(msg)){
			return "卡授权错误";
		}else if("02".equals(msg)){
			return "密码错误";
		}
		return "unknown";
	}
	private static void loadProperties()throws Exception{
		if(prop==null){
			prop = new Properties();
			 InputStream in = MyProp.class.getClassLoader().getResourceAsStream("my.properties");
			 prop.load(in);
		}
	}
	public static String getPropStr(String key){
		return prop.getProperty(key);
	}
	public static void main(String[] args) throws Exception {
		
		//System.out.println(getNewLockKey());
		
	}
}