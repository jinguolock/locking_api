package com.islora.lock.wx.server.db.entity;


public class LcLogFinger {
	private int id;
	private String lockSn;
	private String lockResult;
	private String hwid;
	private String upTime;
	private int ownerId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLockSn() {
		return lockSn;
	}
	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}
	public String getLockResult() {
		return lockResult;
	}
	public void setLockResult(String lockResult) {
		this.lockResult = lockResult;
	}
	
	public String getUpTime() {
		return upTime;
	}
	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getHwid() {
		return hwid;
	}
	public void setHwid(String hwid) {
		this.hwid = hwid;
	}
	
	
}
