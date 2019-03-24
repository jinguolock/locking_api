package com.islora.lock.wx.server.db.entity;

public class LcReader {
	private int id;
	private String readerNo;
	private String password;
	private int ownerId;
	private String rtype;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getRtype() {
		return rtype;
	}
	public void setRtype(String rtype) {
		this.rtype = rtype;
	}
	public String getReaderNo() {
		return readerNo;
	}
	public void setReaderNo(String readerNo) {
		this.readerNo = readerNo;
	}
	
}
