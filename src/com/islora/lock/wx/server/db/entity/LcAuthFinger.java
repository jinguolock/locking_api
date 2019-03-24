package com.islora.lock.wx.server.db.entity;


public class LcAuthFinger {
	private int id;
	private String name;
	private int hwid;
	private String ftype;
	private String status;
	private int lockId;
	private int ownerId;
	private int openTimes;
	private String startTime;
	private String endTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHwid() {
		return hwid;
	}
	public void setHwid(int hwid) {
		this.hwid = hwid;
	}
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getLockId() {
		return lockId;
	}
	public void setLockId(int lockId) {
		this.lockId = lockId;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public int getOpenTimes() {
		return openTimes;
	}
	public void setOpenTimes(int openTimes) {
		this.openTimes = openTimes;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
}
