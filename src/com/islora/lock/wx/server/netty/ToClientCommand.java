package com.islora.lock.wx.server.netty;



public class ToClientCommand {
	
	private String bs=null;
	private String iport;
	private String con;
	public ToClientCommand(String ipport,String content){
		this.iport=ipport;
		this.con=content;
		MainServer.getInstance().addIpCommand(this.iport, this);
	}
	public void setReturn(String bs){
		this.bs=bs;
	}
	public String getReturnBytes(){
		int i=0;
		MainServer.getInstance().writeToClient(iport, con);
		while(bs==null&&i<5000){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i=i+20;
		}
		if(i>=5000){
			System.out.println("no return from ip:"+iport);
		}
		MainServer.getInstance().removeIpCommand(iport);
		return bs;
	}
	
}
