package com.islora.lock.wx.server.netty;

import io.netty.channel.ChannelHandlerContext;

public class ChannelHandlerContextExt {
	
	private String message_id;
	
	private ChannelHandlerContext ctx;
	
	public ChannelHandlerContextExt(){}
	
	public ChannelHandlerContextExt(String message_id,ChannelHandlerContext ctx){
		this.message_id = message_id;
		this.ctx = ctx;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
	
}
