package com.islora.lock.wx.server.db.entity;


public class LcLock {
	private int id;
	private String ltype;
	private String sn;
	private String securekey;
	private int version;
	private int status;
	private String keyMakeTime;
	private int keyExpireInterval;
	private int apartmentId;
	private int ownerId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSecurekey() {
		return securekey;
	}
	public void setSecurekey(String securekey) {
		this.securekey = securekey;
	}
	public int getApartmentId() {
		return apartmentId;
	}
	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}
	public String getKeyMakeTime() {
		return keyMakeTime;
	}
	public void setKeyMakeTime(String keyMakeTime) {
		this.keyMakeTime = keyMakeTime;
	}
	public String getLtype() {
		return ltype;
	}
	public void setLtype(String ltype) {
		this.ltype = ltype;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public int getKeyExpireInterval() {
		return keyExpireInterval;
	}
	public void setKeyExpireInterval(int keyExpireInterval) {
		this.keyExpireInterval = keyExpireInterval;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
