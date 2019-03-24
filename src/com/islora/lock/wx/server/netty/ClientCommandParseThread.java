package com.islora.lock.wx.server.netty;

import io.netty.channel.ChannelHandlerContext;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.islora.lock.wx.server.core.MyProp;
import com.islora.lock.wx.server.db.dao.LcAuthCardDao;
import com.islora.lock.wx.server.db.dao.LcLockDao;
import com.islora.lock.wx.server.db.dao.LcLockStatusDao;
import com.islora.lock.wx.server.db.dao.LcLogCardDao;
import com.islora.lock.wx.server.db.dao.LcLogLoraDao;
import com.islora.lock.wx.server.db.entity.LcAuthCard;
import com.islora.lock.wx.server.db.entity.LcLock;
import com.islora.lock.wx.server.db.entity.LcLockStatus;
import com.islora.lock.wx.server.db.entity.LcLogCard;
import com.islora.lock.wx.server.db.entity.LcLogLora;


public class ClientCommandParseThread extends Thread{
	private Logger logger = LogManager.getLogger(ClientCommandParseThread.class.getName());
	public static DateFormat dft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String msg;
	
	private DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	
	private ChannelHandlerContext ctx;
	
	public ClientCommandParseThread(ChannelHandlerContext ctx,String msg){
		this.msg=msg;
		this.ctx=ctx;
	}
	
	public static Date getDateFromStr(String[] contentArr,int start) {
		long l1=Long.parseLong(contentArr[start], 16);
		long l2=Long.parseLong(contentArr[start+1], 16);
		long l3=Long.parseLong(contentArr[start+2], 16);
		long l4=Long.parseLong(contentArr[start+3], 16);
		long tb=(l4&0xff)<<24|(l3&0xff)<<16|(l2&0xff)<<8|(l1&0xff);
		return new Date(tb*1000);
	}
	public static void main(String[] args) {
		String[] a= {"00","66","89","5B"};
		String[] bs={"68","81","2F","5B"};
		String[] bs2={"34","ED","42","5B"};
		System.out.println(dft.format(getDateFromStr(bs,0)));
		System.out.println(dft.format(getDateFromStr(bs2,0)));
	}
	//[{target:"station",method:"lclock",cmd:"addlock",id:"10000002",value:"12345678",status:"1"}]
	//[{target:"station",method:"lclock",cmd:"deletelock",id:"10000002"}]
	//[{target:"station",method:"lclock",cmd:"editlock",id:"10000002",value:"12345678",status:"1"}]
	public void run(){
		logger.entry();
		try{
			if(msg.startsWith("i:")){
				String gateId=msg.substring(2);
				MainServer.getInstance().addClient(gateId, ctx);
				DownCommand.sendAllLock();
//				LcLockDao lockDao=new LcLockDao();
//				List<LcLock> locks=lockDao.findAll();
//				LcAuthCardDao cardDao=new LcAuthCardDao();
//				JSONArray ja=new JSONArray();
//				for(LcLock lock:locks) {
//					if("card".equals(lock.getLtype())){
//						ja.add(MyProp.getAddLockJSON(lock.getSn(), lock.getSecurekey(), lock.getStatus(),lock.getVersion(),"",1));
//					}else if("sncard".equals(lock.getLtype())){
//						List<LcAuthCard> list=cardDao.getAuthByLockId(lock.getId());
//						StringBuilder sb=new StringBuilder();
//						for(LcAuthCard card:list){
//							String id=card.getCardNo();
//							int leng=id.length()/2;
//							for(int i=0;i<leng;i++){
//								int ii=i*2;
//								String ss=id.substring(ii,ii+2);
//								sb.append(ss).append(",");
//							}
//						}
//						if(sb.length()>1){
//							sb.deleteCharAt(sb.length()-1);
//						}
//						
//						ja.add(MyProp.getAddLockJSON(lock.getSn(), lock.getSecurekey(), lock.getStatus(),lock.getVersion(),sb.toString(),2));
//					}
//					
//				}
//				try{
//					System.out.println(ja.toJSONString());
//		    		ctx.channel().writeAndFlush(ja.toJSONString());
//		    	}catch(Exception ex){
//		    		ex.printStackTrace();
//		    	}
			}if(msg.startsWith("{")){
				
				UpCommand up=JSON.parseObject(msg, UpCommand.class);
				
				if(!up.getType().startsWith("lock_")) {
					return;
				}
				LcLogLoraDao loraDao=new LcLogLoraDao();
				LcLockDao lockDao=new LcLockDao();
				LcLockStatusDao statusDao=new LcLockStatusDao();
				LcLogLora lll=new LcLogLora();
				
				lll.setContent(up.getContent());
				lll.setGatewaySn(up.getGateway());
				lll.setLockSn(up.getNodeId());
				lll.setRssi(up.getRssi());
				lll.setSnr(up.getSnr());
				lll.setSystemSn(up.getSystemId());
				lll.setType(up.getType());
				lll.setUptime(dft.format(new Date()));
				LcLock lock=lockDao.getLockBySn(up.getNodeId());
				if(lock!=null) {
					lll.setOwnerId(lock.getOwnerId());
				}
				loraDao.save(lll);
				boolean isnew=false;
				LcLockStatus lls=null;
				if(lock!=null) {
					lls=statusDao.getStatusByLockId(lock.getId());
				}
				if(lls==null) {
					isnew=true;
					lls=new LcLockStatus();
				}
				if(lock!=null) {
					lls.setLockId(lock.getId());
					lls.setOwnerId(lock.getOwnerId());
				}
				lls.setLastMsgTime(dft.format(new Date()));
				lls.setRssi(up.getRssi());
				lls.setSnr(up.getSnr());
				String[] contentArr=up.getContent().split(",");
				lls.setLowPower(Integer.parseInt(contentArr[0], 16));
				lls.setSourceType(up.getType());
				if("lock_heart".equals(up.getType())) {
					lls.setCurrentKey(contentArr[1]+contentArr[2]+contentArr[3]+contentArr[4]);
					/*long l1=Long.parseLong(contentArr[5], 16);
					long l2=Long.parseLong(contentArr[6], 16);
					long l3=Long.parseLong(contentArr[7], 16);
					long l4=Long.parseLong(contentArr[8], 16);
					long tb=(l4&0xff)<<24|(l3&0xff)<<16|(l2&0xff)<<8|(l1&0xff);
					Date d=new Date(tb*1000);*/
					lls.setCurrentTime(dft.format(getDateFromStr(contentArr,5)));
					lls.setVersion(contentArr[9]+"-"+contentArr[10]+"-"+contentArr[11]);
					
				}
			/*	else if("lock_open_card".equals(up.getType())) {
					
				}else if("lock_open_card".equals(up.getType())) {
					
				}*/
				if(isnew) {
					statusDao.save(lls);
				}else {
					statusDao.update(lls);
				}
				if("lock_open_card".equals(up.getType())) {
					LcLogCardDao cardDao=new LcLogCardDao();
					LcLogCard llc=new LcLogCard();
					//01,01,F4,06,CB,26,12,34,56,78,80,87,60,5B,00,66,89,5B,00,00,00,00
					llc.setCardPwd(contentArr[6]+contentArr[7]+contentArr[8]+contentArr[9]);
					llc.setCardResult(Integer.parseInt(contentArr[1],16));
					llc.setCardSn(contentArr[2]+contentArr[3]+contentArr[4]+contentArr[5]);
					llc.setEndTime(dft.format(getDateFromStr(contentArr,14)));
					llc.setLockSn(up.getNodeId());
					if(lock!=null) {
						llc.setOwnerId(lock.getOwnerId());
					}
					llc.setStartTime(dft.format(getDateFromStr(contentArr,10)));
					llc.setUpTime(dft.format(new Date()));
					cardDao.save(llc);
				}
				
			}if(msg.startsWith("nd")){
				String node_data=msg.substring(2);
				//MainServer.getInstance().addClient(gateId, ctx);
			}else{
				InetSocketAddress  sa=(InetSocketAddress)(ctx.channel().remoteAddress());
		        String ip=sa.getAddress().getHostAddress();
				int port=sa.getPort();
				String key=ip+":"+port;
				MainServer.getInstance().setIpCommandReturn(key, msg);
				
				if(msg.startsWith("{")){
					JSONObject jo=JSON.parseObject(msg);
					if("node_data".equals(jo.getString("Type"))){
						String[] sss=jo.getString("Content").split(",");
						byte[] bs=new byte[sss.length];
						for(int i=0;i<sss.length;i++){
							int b=Integer.parseInt(sss[i], 16);
							bs[i]=(byte)b;
						}
						
						DatagramSocket client = new DatagramSocket();
				        InetAddress addr = InetAddress.getByName("121.40.250.159");
				        DatagramPacket sendPacket = new DatagramPacket(bs ,bs.length , addr , 8893);
				        client.send(sendPacket);

				        
					}else if("node_event".equals(jo.getString("Type"))){
						
					}
				}
			}
		}catch(Exception ex){
			logger.catching(ex);  
		}
		logger.exit();
	}
}
