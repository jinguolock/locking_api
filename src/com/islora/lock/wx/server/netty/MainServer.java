package com.islora.lock.wx.server.netty;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.islora.lock.wx.server.core.MyProp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;



public class MainServer {
	private Logger logger = LogManager.getLogger(MainServer.class);
    private static int PORT = 10027;
    static{
		try {
			PORT=Integer.parseInt(MyProp.getPropStr("tcp.port"));
		} catch (Exception e) {
		}
	}
    private int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors()*2;
    
    private int BIZTHREADSIZE = 10;
    private EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
    private EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);
    public List<ChannelHandlerContext> clientList=Collections.synchronizedList(new ArrayList<ChannelHandlerContext>());
    public Map<String,ChannelHandlerContext> clientMap=new ConcurrentHashMap<String,ChannelHandlerContext>();
    public Map<String,HashSet<ChannelHandlerContextExt>> clientSubMap=new ConcurrentHashMap<String,HashSet<ChannelHandlerContextExt>>();
    public Map<String,String> gateIdIpMap=new HashMap<String,String>();
    public Map<String,ToClientCommand> ipportCommand=new HashMap<String,ToClientCommand>();
    private MainServer(){
    }
    
    private static class SingletonMainServer{
        private static MainServer instance = new MainServer();
    }
    public static MainServer getInstance(){
    	return SingletonMainServer.instance;
    }
    public void addIpCommand(String ipport,ToClientCommand client){
    	ipportCommand.put(ipport, client);
    }
    public void removeIpCommand(String ipport){
    	ipportCommand.remove(ipport);
    }
    public void setIpCommandReturn(String ipport,String bs){
    	if(ipportCommand.containsKey(ipport)){
    		ipportCommand.get(ipport).setReturn(bs);
    	}
    }
    public boolean hasIpCommand(String gateId){
    	String ipport=gateIdIpMap.get(gateId);
    	if(ipport==null) return false;
    	return ipportCommand.containsKey(ipport);
    }
    public String getIpPortByGateId(String gateId){
    	return gateIdIpMap.get(gateId);
    }
    public void addClient(String gateId,ChannelHandlerContext ctx){
    	InetSocketAddress  sa=(InetSocketAddress)(ctx.channel().remoteAddress());
        String ip=sa.getAddress().getHostAddress();
		int port=sa.getPort();
		String key=ip+":"+port;
		System.out.println("add client gateid:"+gateId+";ip:"+key);
		if(clientMap.containsKey(key)){
			return;
		}
		clientList.add(ctx);
		clientMap.put(key, ctx);
		gateIdIpMap.put(gateId, key);
		/*try {
			new LoraStationDao().updateStationOnline(gateId, 1);
		} catch (CustomException e) {
			e.printStackTrace();
		}*/
    }
    
    public void addSubClient(ChannelHandlerContextExt ctx,Object[] keys){
    	if(keys.length>0){
			for(Object keyObj : keys){
				String key = (String)keyObj;
				if (!clientSubMap.containsKey(key)) {
					clientSubMap.put(key, new HashSet<ChannelHandlerContextExt>());
				}
				clientSubMap.get(key).add(ctx);
			}
		}
    }
    
    public void removeSubClient(ChannelHandlerContextExt ctx,Object[] keys){
    	if(keys!=null){
    		if(keys.length>0){
    			for(Object keyObj : keys){
    				String key = (String)keyObj;
    				if (clientSubMap.containsKey(key)) {
    					Set<ChannelHandlerContextExt> values1 = clientSubMap.get(key);
    					Iterator<ChannelHandlerContextExt> iter1 = values1.iterator();
    					while(iter1.hasNext()){
    						ChannelHandlerContextExt ctxExt = (ChannelHandlerContextExt)iter1.next();
    						if(ctxExt.getMessage_id().equals(ctx.getMessage_id())){
    							iter1.remove();
    						}
    					}
    				}
    			}
    		}
    	}else{
			for (Set<ChannelHandlerContextExt> values : clientSubMap.values()) {
				Iterator<ChannelHandlerContextExt> iter = values.iterator();
				while(iter.hasNext()){
					ChannelHandlerContextExt ctxExt = (ChannelHandlerContextExt)iter.next();
					if(ctxExt.getMessage_id().equals(ctx.getMessage_id()) || ctx.getCtx()==ctxExt.getCtx()){
						iter.remove();
					}
				}
			}
		}
    }
    
    public void removeClient(String ip,int port){
    	String key=ip+":"+port;
    	removeClient(key);
    	
    }
    public void removeClient(String key){
    	System.out.println("remove client:"+key);
    	ChannelHandlerContext ctx=clientMap.get(key);
    	if(ctx==null){
    		return;
    	}
    	clientMap.remove(key);
    	clientList.remove(ctx);
    	Set<String> set=gateIdIpMap.keySet();
    	for(String k:set){
    		if(gateIdIpMap.get(k).equals(key)){
    			System.out.println("remove gate client:"+k);
    			/*try {
    				new LoraStationDao().updateStationOnline(k, 0);
    			} catch (CustomException e) {
    				e.printStackTrace();
    			}*/
    			gateIdIpMap.remove(k);
    			break;
    		}
    	}
    	try{
    		ctx.close();
    	}catch(Exception ex){
    		logger.catching(ex);
    	}
    	
    }
    public void writeToAll(String text){
    	Set<String> ips=clientMap.keySet();
    	if(ips==null||ips.size()==0){
    		return;
    	}
    	ips.forEach(ip->{
    		try{
    			ChannelHandlerContext ctx=clientMap.get(ip);
    			ctx.channel().writeAndFlush(text);
    		}catch(Exception ex){
    			logger.catching(ex);
    		}
    		
    	});
    }
    public void writeToClient(String ip,int port,String text){
    	logger.info("writeToClient name:"+ip+";text:"+text);
    	String key=ip+":"+port;
    	if(!clientMap.containsKey(key)){
    		logger.warn("writeToClient:no this client:"+key);
    		return;
    	}
    	ChannelHandlerContext ctx=clientMap.get(key);
    	try{
    		ctx.channel().writeAndFlush(text);
    	}catch(Exception ex){
    		try{
    			ctx.close();
    		}catch(Exception e){
    			logger.catching(e);  
    		}
    		removeClient(ip,port);
    	}
    }
    public void writeToGate(String gateId,String text){
    	if(!gateIdIpMap.containsKey(gateId)){
    		logger.warn("writeToClient:no this client:"+gateId);
    		return;
    	}
    	String key=gateIdIpMap.get(gateId);
    	writeToClient(key,text);
    }
    public void writeToClient(String key,String text){
    	if(!clientMap.containsKey(key)){
    		logger.warn("writeToClient:no this client:"+key);
    		return;
    	}
    	ChannelHandlerContext ctx=clientMap.get(key);
    	try{
    		ctx.channel().writeAndFlush(text);
    	}catch(Exception ex){
    		try{
    			ctx.close();
    		}catch(Exception e){
    			logger.catching(e);  
    		}
    		removeClient(key);
    	}
    }
    public void writeDataToNode(String key,String nodeId,String content){
    	if(!clientMap.containsKey(key)){
    		logger.warn("writeToClient:no this client:"+key);
    		return;
    	}
    	ChannelHandlerContext ctx=clientMap.get(key);
    	try{
    		
    		DownCommand dc=new DownCommand();
    		dc.setTarget("node");
    		dc.setNodeId(nodeId);
    		dc.setContentType("string");
    		dc.setContent(content);
    		dc.setCommand("10");
    		JSONArray ja=new JSONArray();
    		ja.add(dc);
    		
            String ss=ja.toJSONString();
    		
    		ctx.channel().writeAndFlush(ss);
    	}catch(Exception ex){
    		try{
    			ctx.close();
    		}catch(Exception e){
    			logger.catching(e);  
    		}
    		removeClient(key);
    	}
    }
    public void writeToClients(String[] ips,int[] ports,String text){
    	if(ips==null||ports==null||ips.length!=ports.length)
    		return;
    	for(int i=0;i<ips.length;i++){
    		String ip=ips[i];
    		int port=ports[i];
    		writeToClient(ip,port,text);
    	}
    }
    
	public Map<String, ChannelHandlerContext> getClientMap() {
		return clientMap;
	}
	public Map<String, HashSet<ChannelHandlerContextExt>> getClientSubMap() {
		return clientSubMap;
	}
	public void service() throws InterruptedException  {
    	try{
    		ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                     	pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new MyChannelHandler());
                }
                
            });
              ChannelFuture f = bootstrap.bind(PORT).sync();
              logger.info("TCP服务器已启动,端口为："+PORT);
              f.channel().closeFuture().sync();
              
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	finally{
    		bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
    	}
    }

        protected void shutdown() {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

        public static void main(String[] args) throws Exception {
            //logger.debug("开始启动TCP服务器...");
            /*new Thread(new Runnable(){

				@Override
				public void run() {
					while(true){
						if(MainServer.getInstance().clientList.size()>0){
							for(ChannelHandlerContext ctx:MainServer.getInstance().clientList){
								ctx.channel().writeAndFlush("abc");
							}
							
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					
				}
            	
            }).start();*/
        	System.out.println("starting");
        	Thread th2 = new Thread(new Runnable() {

    			@Override
    			public void run() {
    				try {
    					MainServer.getInstance().service();
    				} catch (Exception ex) {
    					if("java.net.BindException".equals(ex.getClass().getName()));{
    						System.exit(1);
    					}
    				}

    			}

    		});
    		th2.start();
//            HelloServer.shutdown();
        }
}

