package com.islora.lock.wx.server.servlet;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.islora.lock.wx.server.db.dao.LcApartmentDao;
import com.islora.lock.wx.server.db.dao.LcAuthAppDao;
import com.islora.lock.wx.server.db.dao.LcAuthCardDao;
import com.islora.lock.wx.server.db.dao.LcBuildingDao;
import com.islora.lock.wx.server.db.dao.LcCommunityDao;
import com.islora.lock.wx.server.db.dao.LcLockDao;
import com.islora.lock.wx.server.db.dao.LcReaderDao;
import com.islora.lock.wx.server.db.dao.LcUserDao;
import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.entity.LcAuthApp;
import com.islora.lock.wx.server.db.entity.LcAuthCard;
import com.islora.lock.wx.server.db.entity.LcBuilding;
import com.islora.lock.wx.server.db.entity.LcCommunity;
import com.islora.lock.wx.server.db.entity.LcLock;
import com.islora.lock.wx.server.db.entity.LcReader;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.utils.CustomException;



/**
 * Servlet implementation class StartServlet
 */
@WebServlet( value = "/reader")
public class ReaderApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static char[] cc=new char[128];
    public static DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat df2=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReaderApiServlet() {
        super();
    }

	@Override
	public void init() throws ServletException {
		super.init();
	}
		

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String s=request.getParameter("c");
		String sn=request.getParameter("sn");
		String pwd=request.getParameter("pwd");
		LcReader lr=null;
		try {
			lr = new LcReaderDao().getBySn(sn);
		} catch (CustomException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(lr==null) {
			backresult(response,"noreader");
			return;
		}
		if(!lr.getPassword().equals(pwd)) {
			backresult(response,"wrongpwd");
			return;
		}
		int userId=lr.getOwnerId();
		LcUser lu=new LcUserDao().findById(userId);
		if("getallapp".equals(s)){
			try {
				List<LcAuthApp> clist=new LcAuthAppDao().findByOwnerId(lu.getId());
				back(response,JSON.toJSONString(clist));
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("getallcommunity".equals(s)){
			try {
				JSONObject map=new JSONObject();
				List<LcCommunity> clist=new LcCommunityDao().findByOwnerId(lu.getId());
				if(clist!=null) {
					for(LcCommunity lc:clist) {
						lc.setName(new String(java.net.URLEncoder.encode(lc.getName(),"UTF-8")));
						lc.setAddress(new String(java.net.URLEncoder.encode(lc.getAddress(),"UTF-8")));
					}
				}
				map.put("community", clist);
				List<LcBuilding> blist=new LcBuildingDao().findByOwnerId(lu.getId());
				if(blist!=null) {
					for(LcBuilding lc:blist) {
						lc.setName(new String(java.net.URLEncoder.encode(lc.getName(),"UTF-8")));
						lc.setDescription(new String(java.net.URLEncoder.encode(lc.getDescription(),"UTF-8")));
					}
				}
				map.put("building", blist);
				List<LcApartment> alist=new LcApartmentDao().findByOwnerId(lu.getId());
				if(blist!=null) {
					for(LcApartment lc:alist) {
						lc.setName(new String(java.net.URLEncoder.encode(lc.getName(),"UTF-8")));
						lc.setDescription(new String(java.net.URLEncoder.encode(lc.getDescription(),"UTF-8")));
					}
				}
				map.put("apartment", alist);
				back(response,map.toJSONString());
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("login".equals(s)){
			try {
				JSONObject js=new JSONObject ();
				js.put("result", "success");
				js.put("type", lr.getRtype());
				back(response,js.toJSONString());
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("getauthbyid".equals(s)){
			try {
				String cardNo=request.getParameter("card");
				LcAuthCard lac=new LcAuthCardDao().getAuthByCardId(cardNo);
				if(lac==null) {
					backresult(response,"nocard");
					return;
				}
				
				int lockId=lac.getLockId();
				LcLock lock=new LcLockDao().findById(lockId);
				if(lock==null) {
					backresult(response,"nolock");
					return;
				}
				LcUser client=new LcUserDao().findById(lac.getClientId());
				if(client==null) {
					backresult(response,"noclient");
					return;
				}
				
				JSONObject jo=new JSONObject();
				jo.put("result", "success");
				jo.put("apartmentId", lock.getApartmentId());
				jo.put("phone",client.getPhone());
				jo.put("type", lac.getCtype());
				jo.put("start", lac.getStartTime());
				jo.put("end", lac.getEndTime());
				
				back(response,jo.toJSONString());
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("writeauth".equals(s)){
			try {
				String cardNo=request.getParameter("card");
				String phone=request.getParameter("phone");
				String type=request.getParameter("type");
				String apartmentid=request.getParameter("apartmentid");
				String opentimes=request.getParameter("open");
				String start=request.getParameter("start");
				String end=request.getParameter("end");
				LcAuthCard lac=new LcAuthCardDao().getAuthByCardId(cardNo);
				boolean noCard=lac==null;
				if(noCard) {
					lac=new LcAuthCard();
				}
				lac.setCardNo(cardNo);
				LcUser u=new LcUserDao().getUserByPhone(phone);
				if(u==null) {
					backresult(response,"noclient");
					return;
				}
				LcLock lo= new LcLockDao().getLockByApartment(Integer.parseInt(apartmentid));
				if(lo==null) {
					backresult(response,"nolock");
					return;
				}
				lac.setClientId(u.getId());
				lac.setCtype(type);
				lac.setEndTime(end);
				lac.setLockId(lo.getId());
				lac.setOpenTimes(Integer.parseInt(opentimes));
				lac.setOwnerId(lu.getId());
				lac.setReaderNo(sn);
				lac.setStartTime(start);
				if(noCard) {
					new LcAuthCardDao().save(lac);
				}else {
					new LcAuthCardDao().update(lac);
				}
				
				backresult(response,"success");
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("getlockinfo".equals(s)){
			try {

				String apartmentid=request.getParameter("apartmentid");
				String start=request.getParameter("start");
				String end=request.getParameter("end");
				long st=0L;
				long en=0L;
				try {
					st=df2.parse(start).getTime()/1000;
					en=df2.parse(end).getTime()/1000;
				}catch(Exception ex) {
					backresult(response,"notime");
					return;
				}
				LcLock lo= new LcLockDao().getLockByApartment(Integer.parseInt(apartmentid));
				if(lo==null) {
					backresult(response,"nolock");
					return;
				}
				byte[] bs=new byte[8];
				bs[0]=(byte)(st&0xff);
				bs[1]=(byte)(st>>8&0xff);
				bs[2]=(byte)(st>>16&0xff);
				bs[3]=(byte)(st>>24&0xff);
				bs[4]=(byte)(en&0xff);
				bs[5]=(byte)(en>>8&0xff);
				bs[6]=(byte)(en>>16&0xff);
				bs[7]=(byte)(en>>24&0xff);
				StringBuilder sb=new StringBuilder();
				for(byte b:bs) {
					sb.append(String.format("%02X", (int)(b&0xff)));
				}
				JSONObject obj=new JSONObject();
				obj.put("result", "success");
				obj.put("pwd", lo.getSecurekey()+sb.toString());
				
				back(response,obj.toJSONString());
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("addapp".equals(s)){
			try {
				String type=request.getParameter("type");
				String phone=request.getParameter("phone");
				String starttime=request.getParameter("starttime");
				String endtime=request.getParameter("endtime");
				String opentimes=request.getParameter("opentimes");
				String apartmentid=request.getParameter("apartmentid");
				
				LcUser l=new LcUserDao().getUserByPhone(phone);
				if(l==null) {
					backresult(response,"nophone");
					return;
				}
				LcLock lock=new LcLockDao().getLockByApartment(Integer.parseInt(apartmentid));
				if(lock==null) {
					backresult(response,"nolock");
					return;
				}
				if(l.getId()==lu.getId()) {
					backresult(response,"noself");
					return;
				}
				try {
					df.parse(starttime);
					df.parse(endtime);
				}catch(Exception ex) {
					backresult(response,"datewrong");
					return;
				}
				LcAuthApp laa=new LcAuthApp();
				laa.setAtype(type);
				laa.setClientId(l.getId());
				laa.setEndTime(endtime);
				laa.setLockId(lock.getId());
				laa.setOpenTimes(Integer.parseInt(opentimes));
				laa.setOwnerId(lu.getId());
				laa.setStartTime(starttime);
				
				new LcAuthAppDao().save(laa);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if("deleteapp".equals(s)){
			try {
				String appId=request.getParameter("appId");
				new LcAuthAppDao().deleteById(Integer.parseInt(appId));
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("editapp".equals(s)){
			try {
				String type=request.getParameter("type");
				String phone=request.getParameter("phone");
				String starttime=request.getParameter("starttime");
				String endtime=request.getParameter("endtime");
				String opentimes=request.getParameter("opentimes");
				String apartmentid=request.getParameter("apartmentid");
				String appId=request.getParameter("appId");
				
				LcUser l=new LcUserDao().getUserByPhone(phone);
				if(l==null) {
					backresult(response,"nophone");
					return;
				}
				if(l.getId()==lu.getId()) {
					backresult(response,"noself");
					return;
				}
				LcLock lock=new LcLockDao().getLockByApartment(Integer.parseInt(apartmentid));
				if(lock==null) {
					backresult(response,"nolock");
					return;
				}
				try {
					df.parse(starttime);
					df.parse(endtime);
				}catch(Exception ex) {
					backresult(response,"datewrong");
					return;
				}
				LcAuthApp laa=new LcAuthApp();
				laa.setId(Integer.parseInt(appId));
				laa.setAtype(type);
				laa.setClientId(l.getId());
				laa.setEndTime(endtime);
				laa.setLockId(lock.getId());
				laa.setOpenTimes(Integer.parseInt(opentimes));
				laa.setOwnerId(lu.getId());
				laa.setStartTime(starttime);
				
				new LcAuthAppDao().update(laa);
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("addcard".equals(s)){
			try {
				String type=request.getParameter("type");
				String phone=request.getParameter("phone");
				String starttime=request.getParameter("starttime");
				String endtime=request.getParameter("endtime");
				String opentimes=request.getParameter("opentimes");
				String apartmentid=request.getParameter("apartmentid");
				String cardno=request.getParameter("cardno");
				
				LcUser l=new LcUserDao().getUserByPhone(phone);
				if(l==null) {
					backresult(response,"nophone");
					return;
				}
				LcLock lock=new LcLockDao().getLockByApartment(Integer.parseInt(apartmentid));
				if(lock==null) {
					backresult(response,"nolock");
					return;
				}
				LcAuthCard laa=new LcAuthCard();
				laa.setCtype(type);
				laa.setClientId(l.getId());
				laa.setEndTime(endtime);
				laa.setLockId(lock.getId());
				laa.setOpenTimes(Integer.parseInt(opentimes));
				laa.setOwnerId(lu.getId());
				laa.setStartTime(starttime);
				laa.setCardNo(cardno);
				
				new LcAuthCardDao().save(laa);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if("deletecard".equals(s)){
			try {
				String cardId=request.getParameter("cardId");
				new LcAuthCardDao().deleteById(Integer.parseInt(cardId));
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("editcard".equals(s)){
			try {
				
				String type=request.getParameter("type");
				String phone=request.getParameter("phone");
				String starttime=request.getParameter("starttime");
				String endtime=request.getParameter("endtime");
				String opentimes=request.getParameter("opentimes");
				String apartmentid=request.getParameter("apartmentid");
				String cardno=request.getParameter("cardno");
				String cardid=request.getParameter("cardid");
				
				LcUser l=new LcUserDao().getUserByPhone(phone);
				if(l==null) {
					backresult(response,"nophone");
					return;
				}
				LcLock lock=new LcLockDao().getLockByApartment(Integer.parseInt(apartmentid));
				if(lock==null) {
					backresult(response,"nolock");
					return;
				}
				LcAuthCard laa=new LcAuthCard();
				laa.setCtype(type);
				laa.setClientId(l.getId());
				laa.setEndTime(endtime);
				laa.setLockId(lock.getId());
				laa.setOpenTimes(Integer.parseInt(opentimes));
				laa.setOwnerId(lu.getId());
				laa.setStartTime(starttime);
				laa.setCardNo(cardno);
				laa.setId(Integer.parseInt(cardid));
				
				new LcAuthCardDao().update(laa);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("updatelockkey".equals(s)){
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void back(HttpServletResponse response,String str) throws IOException{
		response.setContentType("text;charset=UTF-8");
		response.getWriter().print(str);
	}
	private void backresult(HttpServletResponse response,boolean b) throws IOException{
		response.setContentType("text;charset=UTF-8");
		JSONObject js=new JSONObject ();
		js.put("result", String.valueOf(b));
		response.getWriter().print(js.toJSONString());
	}
	private void backresult(HttpServletResponse response,String b) throws IOException{
		response.setContentType("text;charset=UTF-8");
		JSONObject js=new JSONObject ();
		js.put("result", b);
		response.getWriter().print(js.toJSONString());
	}
}