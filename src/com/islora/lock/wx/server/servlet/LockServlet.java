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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.islora.lock.wx.server.core.MyProp;
import com.islora.lock.wx.server.db.dao.LcApartmentDao;
import com.islora.lock.wx.server.db.dao.LcAuthCardDao;
import com.islora.lock.wx.server.db.dao.LcBuildingDao;
import com.islora.lock.wx.server.db.dao.LcCommunityDao;
import com.islora.lock.wx.server.db.dao.LcLockDao;
import com.islora.lock.wx.server.db.dao.LcReaderDao;
import com.islora.lock.wx.server.db.dao.LcUserDao;
import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.entity.LcAuthCard;
import com.islora.lock.wx.server.db.entity.LcBuilding;
import com.islora.lock.wx.server.db.entity.LcCommunity;
import com.islora.lock.wx.server.db.entity.LcLock;
import com.islora.lock.wx.server.db.entity.LcReader;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.utils.CustomException;
import com.islora.lock.wx.server.netty.DownCommand;
import com.islora.lock.wx.server.netty.MainServer;



/**
 * Servlet implementation class StartServlet
 */
@WebServlet( value = "/lock")
public class LockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static char[] cc=new char[128];
    public static DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LockServlet() {
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
		LcUser lu=(LcUser)request.getSession().getAttribute("user");
		if("getalllock".equals(s)){
			try {
				List<LcLock> clist=new LcLockDao().findByOwnerId(lu.getId());
				back(response,JSON.toJSONString(clist));
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("getallreader".equals(s)){
			try {
				List<LcReader> clist=new LcReaderDao().findByOwnerId(lu.getId());
				back(response,JSON.toJSONString(clist));
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("addlock".equals(s)){
			try {
				//back(response,JSON.toJSONString(new LockUserDao().findAll()));
				String type=request.getParameter("ltype");
				String sn=request.getParameter("sn");
				//String securekey=request.getParameter("securekey");
				//String keyMakeTime=request.getParameter("keyMakeTime");
				String apartmentId=request.getParameter("apartmentId");
				String keyExpireInterval=request.getParameter("expire");
				String status=request.getParameter("status");
				LcLock ll=new LcLock();
				ll.setApartmentId(Integer.parseInt(apartmentId));
				ll.setKeyMakeTime(df.format(new Date()));
				ll.setSecurekey(sn);
				ll.setKeyExpireInterval(Integer.parseInt(keyExpireInterval));
				ll.setLtype(type);
				ll.setSn(sn);
				ll.setVersion(1);
				ll.setStatus(Integer.parseInt(status));
				ll.setOwnerId(lu.getId());
				new LcLockDao().save(ll);
				backresult(response,true);
				JSONArray ja=new JSONArray();
				if("card".equals(type)){
					ja.add(MyProp.getAddLockJSON(sn, sn, ll.getStatus(),1,"",1));
				}else if("sncard".equals(type)){
					ja.add(MyProp.getAddLockJSON(sn, sn, ll.getStatus(),1,"",2));
				}
				MainServer.getInstance().writeToAll(ja.toJSONString());
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if("deletelock".equals(s)){
			try {
				String lockId=request.getParameter("lockId");
				new LcLockDao().deleteById(Integer.parseInt(lockId));
				backresult(response,true);
				JSONArray ja=new JSONArray();
				ja.add(MyProp.getDeleteLockJSON(lockId));
				MainServer.getInstance().writeToAll(ja.toJSONString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("editlock".equals(s)){
			try {
				String type=request.getParameter("ltype");
				String lockId=request.getParameter("lockId");
				String apartmentId=request.getParameter("apartmentId");
				String keyExpireInterval=request.getParameter("expire");
				String status=request.getParameter("status");
				String key=request.getParameter("key");
				LcLockDao dao=new LcLockDao();
				LcLock lock=dao.findById(Integer.parseInt(lockId));
				lock.setApartmentId( Integer.parseInt(apartmentId));
				lock.setKeyExpireInterval(Integer.parseInt(keyExpireInterval));
				lock.setLtype(type);
				lock.setStatus(Integer.parseInt(status));
				if("1".equals(key)) {
					lock.setSecurekey(MyProp.getNewLockKey());
				}
				//new LcLockDao().updateLockInfo(Integer.parseInt(lockId), Integer.parseInt(apartmentId), type,Integer.parseInt(keyExpireInterval));
				dao.update(lock);
				backresult(response,true);
				JSONArray ja=new JSONArray();
				if("card".equals(lock.getLtype())){
					ja.add(MyProp.getEditLockJSON(lock.getSn(), lock.getSecurekey(), lock.getStatus(),lock.getVersion(),"",1));
				}else if("sncard".equals(lock.getLtype())){
					List<LcAuthCard> list=new LcAuthCardDao().getAuthByLockId(lock.getId());
					StringBuilder sb=new StringBuilder();
					for(LcAuthCard card:list){
						String id=card.getCardNo();
						int leng=id.length()/2;
						for(int i=0;i<leng;i++){
							int ii=i*2;
							String ss=id.substring(ii,ii+2);
							sb.append(ss).append(",");
						}
					}
					sb.deleteCharAt(sb.length()-1);
					ja.add(MyProp.getEditLockJSON(lock.getSn(), lock.getSecurekey(), lock.getStatus(),lock.getVersion(),sb.toString(),2));
				}
				
				MainServer.getInstance().writeToAll(ja.toJSONString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("addreader".equals(s)){
			try {
				//back(response,JSON.toJSONString(new LockUserDao().findAll()));
				String type=request.getParameter("type");
				String sn=request.getParameter("sn");
				String password=request.getParameter("password");
				LcReader ll=new LcReader();
				ll.setPassword(password);
				ll.setReaderNo(sn);
				ll.setRtype(type);
				ll.setOwnerId(lu.getId());
				new LcReaderDao().save(ll);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if("deletereader".equals(s)){
			try {
				String readerId=request.getParameter("readerId");
				new LcReaderDao().deleteById(Integer.parseInt(readerId));
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("editreader".equals(s)){
			try {
				String type=request.getParameter("type");
				String password=request.getParameter("password");
				String readerId=request.getParameter("readerId");
				new LcReaderDao().updateReaderInfo(Integer.parseInt(readerId), password, type);
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("updateparameter".equals(s)){
			try {
				DownCommand.sendAllLock();
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("updatelockkey".equals(s)){
			try {
				DownCommand.sendAllLock();
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
}