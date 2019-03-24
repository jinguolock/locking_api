package com.islora.lock.wx.server.netty;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class MyChannelHandler extends  ChannelInboundHandlerAdapter {
	private Logger logger = LogManager.getLogger(MyChannelHandler.class.getName());
	 @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
	        String ss=msg.toString();
	        System.out.println(ss);
	        ClientCommandParseThread th=new ClientCommandParseThread(ctx,ss);
	        th.start();
	    }
	      @Override
		public void channelRegistered(ChannelHandlerContext ctx)throws Exception {
			super.channelRegistered(ctx);
			InetSocketAddress  sa=(InetSocketAddress)(ctx.channel().remoteAddress());
			String ip=sa.getAddress().getHostAddress();
			int port=sa.getPort();
		}
		
		@Override
		public void channelUnregistered(ChannelHandlerContext ctx)throws Exception {
			super.channelUnregistered(ctx);
			InetSocketAddress  sa=(InetSocketAddress)(ctx.channel().remoteAddress());
	    	String ip=sa.getAddress().getHostAddress();
			int port=sa.getPort();
			MainServer.getInstance().removeClient(ip,port);
			ChannelHandlerContextExt ctxExt = new ChannelHandlerContextExt();
			ctxExt.setCtx(ctx);
			MainServer.getInstance().removeSubClient(ctxExt, null);
		}
		@Override
	   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	        logger.catching(cause);
			InetSocketAddress  sa=(InetSocketAddress)(ctx.channel().remoteAddress());
	    	String ip=sa.getAddress().getHostAddress();
			int port=sa.getPort();
			MainServer.getInstance().removeClient(ip,port);
			ChannelHandlerContextExt ctxExt = new ChannelHandlerContextExt();
			ctxExt.setCtx(ctx);
			MainServer.getInstance().removeSubClient(ctxExt, null);
	        ctx.close();
	    }

		@Override
	    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	        ctx.flush();
	    }

		@Override
	    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
	            throws Exception {

	        if (evt instanceof IdleStateEvent) {
	            IdleStateEvent event = (IdleStateEvent) evt;
	            if (event.state() == IdleState.READER_IDLE) {
	                System.out.println("READER_IDLE 读超时");
	                ctx.disconnect();
	            } else if (event.state() == IdleState.WRITER_IDLE) {  
	                System.out.println("WRITER_IDLE 写超时");
	            } else if (event.state() == IdleState.ALL_IDLE) {
	                System.out.println("ALL_IDLE 总超时");
	            }
	        }
	    }
}
