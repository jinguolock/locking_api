package com.islora.lock.wx.server.db.utils;

public class StrUtils {

	
	@SuppressWarnings("unused")
	public static String getStrKey(String... key){
		StringBuffer keybuff = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			keybuff.append(key[i]).append('.');
		}
		keybuff.deleteCharAt(keybuff.length()-1);
		return keybuff.toString();
	}
}
