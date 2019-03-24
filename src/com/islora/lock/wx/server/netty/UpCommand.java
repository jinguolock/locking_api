package com.islora.lock.wx.server.netty;

import java.io.Serializable;
//第一个都是 低电量
//{"Content":"01,12,34,56,78,91,72,3C,5B,00,00,02","Gateway":"13010003","NodeId":"10000002","Rssi":"96.0000","Snr":"7.2500","SystemId":"10990143","Type":"lock_heart"}
//第二个状态  1是成功 2是密码错误 3是卡无效 4是没有卡  5是时间过期了
//{"Content":"01,01,F4,06,CB,26,12,34,56,78,80,87,60,5B,00,66,89,5B,00,00,00,00","Gateway":"13010003","NodeId":"10000002","Rssi":"95.0000","Snr":"6.7500","SystemId":"10990143","Type":"lock_open_card"}
//第二个是状态 1是成功 2是授权时间错误 3是密码错误
//{"Content":"01,01","Gateway":"13010003","NodeId":"10000002","Rssi":"95.0000","Snr":"6.2500","SystemId":"10990143","Type":"lock_open_ble"}


public class UpCommand implements Serializable{
	private String NodeId;
	private String Type;
	private String Content;
	private String SystemId;
	private String Gateway;
	private String Rssi;
	private String Snr;
	public String getNodeId() {
		return NodeId;
	}
	public void setNodeId(String nodeId) {
		NodeId = nodeId;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getSystemId() {
		return SystemId;
	}
	public void setSystemId(String systemId) {
		SystemId = systemId;
	}
	public String getGateway() {
		return Gateway;
	}
	public void setGateway(String gateway) {
		Gateway = gateway;
	}
	public String getRssi() {
		return Rssi;
	}
	public void setRssi(String rssi) {
		Rssi = rssi;
	}
	public String getSnr() {
		return Snr;
	}
	public void setSnr(String snr) {
		Snr = snr;
	}
	
	
}
