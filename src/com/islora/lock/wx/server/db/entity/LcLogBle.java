package com.islora.lock.wx.server.db.entity;


public class LcLogBle {
	private int id;
	private int clientId;
	private int apartmentId;
	private String resultMsg;
	private String happenTime;
	private String lockInfo;
	private String lockSN;
	private String type;
	private int ownerId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getApartmentId() {
		return apartmentId;
	}
	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getHappenTime() {
		return happenTime;
	}
	public void setHappenTime(String happenTime) {
		this.happenTime = happenTime;
	}
	public String getLockInfo() {
		return lockInfo;
	}
	public void setLockInfo(String lockInfo) {
		this.lockInfo = lockInfo;
	}
	public String getLockSN() {
		return lockSN;
	}
	public void setLockSN(String lockSN) {
		this.lockSN = lockSN;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
