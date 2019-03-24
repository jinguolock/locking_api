package com.islora.lock.wx.server.db.entity;

import java.util.Date;

public class LcAuthCard {
	private int id;
	private String ctype;
	private int clientId;
	private int lockId;
	private int ownerId;
	private int openTimes;
	private String startTime;
	private String endTime;
	private String cardNo;
	private String readerNo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
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
	public String getReaderNo() {
		return readerNo;
	}
	public void setReaderNo(String readerNo) {
		this.readerNo = readerNo;
	}
	
	
}
