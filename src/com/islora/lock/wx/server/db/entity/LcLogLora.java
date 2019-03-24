package com.islora.lock.wx.server.db.entity;


public class LcLogLora {
	private int id;
	private String lockSn;
	private String type;
	private String content;
	private String systemSn;
	private String gatewaySn;
	private String rssi;
	private String snr;
	private String uptime;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSystemSn() {
		return systemSn;
	}
	public void setSystemSn(String systemSn) {
		this.systemSn = systemSn;
	}
	public String getGatewaySn() {
		return gatewaySn;
	}
	public void setGatewaySn(String gatewaySn) {
		this.gatewaySn = gatewaySn;
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
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	
}
