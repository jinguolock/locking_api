package com.islora.lock.wx.server.db.entity;

public class LcLockStatus {
	private int id;
	private int lockId;
	private String version;
	private int lowPower;
	private String currentKey;
	private String currentTime;
	private String lastMsgTime;
	private String rssi;
	private String snr;
	private int ownerId;
	private String sourceType;
	public int getId() {
		return id;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLockId() {
		return lockId;
	}
	public void setLockId(int lockId) {
		this.lockId = lockId;
	}
	public int getLowPower() {
		return lowPower;
	}
	public void setLowPower(int lowPower) {
		this.lowPower = lowPower;
	}
	public String getCurrentKey() {
		return currentKey;
	}
	public void setCurrentKey(String currentKey) {
		this.currentKey = currentKey;
	}
	public String getLastMsgTime() {
		return lastMsgTime;
	}
	public void setLastMsgTime(String lastMsgTime) {
		this.lastMsgTime = lastMsgTime;
	}

	public String getRssi() {
		return rssi;
	}
	public void setRssi(String rssi) {
		this.rssi = rssi;
	}
	public String getSnr() {
		return snr;
	}
	public void setSnr(String snr) {
		this.snr = snr;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
}
