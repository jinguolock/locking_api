package com.islora.lock.wx.server.db.utils;

public class CustomException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5952195219121130600L;

	public CustomException() {
		super();
	}

	public CustomException(String msg) {
		super(msg);
	}

	public CustomException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}
}
