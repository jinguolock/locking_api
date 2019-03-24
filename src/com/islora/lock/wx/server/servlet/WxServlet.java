package com.islora.lock.wx.server.servlet;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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
import com.islora.lock.wx.server.core.MyHttpClient;
import com.islora.lock.wx.server.db.dao.LcApartmentDao;
import com.islora.lock.wx.server.db.dao.LcAuthAppDao;
import com.islora.lock.wx.server.db.dao.LcAuthCardDao;
import com.islora.lock.wx.server.db.dao.LcBuildingDao;
import com.islora.lock.wx.server.db.dao.LcCommunityDao;
import com.islora.lock.wx.server.db.dao.LcLockDao;
import com.islora.lock.wx.server.db.dao.LcLogBleDao;
import com.islora.lock.wx.server.db.dao.LcReaderDao;
import com.islora.lock.wx.server.db.dao.LcUserDao;
import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.entity.LcAuthApp;
import com.islora.lock.wx.server.db.entity.LcAuthCard;
import com.islora.lock.wx.server.db.entity.LcBuilding;
import com.islora.lock.wx.server.db.entity.LcCommunity;
import com.islora.lock.wx.server.db.entity.LcLock;
import com.islora.lock.wx.server.db.entity.LcLogBle;
import com.islora.lock.wx.server.db.entity.LcReader;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.utils.CustomException;



/**
 * Servlet implementation class StartServlet
 */
@WebServlet( value = "/wx")
public class WxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static char[] cc=new char[128];
    public static DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WxServlet() {
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
		String userPhone=request.getParameter("u");
		String pwd=request.getParameter("p");
		LcUser lu=null;
		try {
			lu=new LcUserDao().getUserByPhone(userPhone);
		} catch (CustomException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//LcUser lu=(LcUser)request.getSession().getAttribute("user");
		if("login".equals(s)){
			try {
				if(lu!=null&&lu.getPassword().equals(pwd)) {
					JSONObject jo=new JSONObject();
					//new String(java.net.URLEncoder.encode(lc.getName(),"UTF-8"))
					jo.put("name", new String(java.net.URLEncoder.encode(lu.getName(),"UTF-8")));
					jo.put("address", new String(java.net.URLEncoder.encode(lu.getAddress(),"UTF-8")));
					jo.put("email", lu.getEmail());
					jo.put("id", lu.getId());
					jo.put("idsn", lu.getIdsn());
					jo.put("phone", lu.getPhone());
					jo.put("type", lu.getType());
					jo.put("username", new String(java.net.URLEncoder.encode(lu.getUsername(),"UTF-8")));
					jo.put("result", "success");
					back(response,jo.toJSONString());
					return;
				}else {
					backresult(response,"fail");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("webmainRequest".equals(s)){
			//http://127.0.0.1:8080/islocking/wx?c=webmainRequest&name=datalist&cmd=lock_node
			try {
				String requestName=request.getParameter("webmain_name");
				Enumeration<String> keys=request.getParameterNames();
				Map<String,String> map=new HashMap<String,String>();
				while(keys.hasMoreElements()) {
					String key=keys.nextElement();
					if("name".equals(key)) {
						continue;
					}
					map.put(key, request.getParameter(key));
				}
				back(response,MyHttpClient.getWebmainRequest(requestName, map));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("register".equals(s)){
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("getallcommunity".equals(s)){
			try {
				JSONObject cmap=new JSONObject();
				JSONObject bmap=new JSONObject();
				JSONObject amap=new JSONObject();
				LcCommunityDao comdao=new LcCommunityDao();
				LcBuildingDao builddao=new LcBuildingDao();
				LcApartmentDao apartdao=new LcApartmentDao();
				List<LcCommunity> clist=comdao.findByOwnerId(lu.getId());
				if(clist!=null) {
					for(LcCommunity lc:clist) {
						lc.setName(new String(java.net.URLEncoder.encode(lc.getName(),"UTF-8")));
						lc.setAddress(new String(java.net.URLEncoder.encode(lc.getAddress(),"UTF-8")));
						cmap.put(String.valueOf(lc.getId()), lc);
					}
				}
				//map.put("community", clist);
				List<LcBuilding> blist=builddao.findByOwnerId(lu.getId());
				if(blist!=null) {
					for(LcBuilding lc:blist) {
						lc.setName(new String(java.net.URLEncoder.encode(lc.getName(),"UTF-8")));
						lc.setDescription(new String(java.net.URLEncoder.encode(lc.getDescription(),"UTF-8")));
						bmap.put(String.valueOf(lc.getId()), lc);
					}
				}
				List<LcApartment> alist=apartdao.findByOwnerId(lu.getId());
				if(alist!=null) {
					for(LcApartment lc:alist) {
						lc.setName(new String(java.net.URLEncoder.encode(lc.getName(),"UTF-8")));
						lc.setDescription(new String(java.net.URLEncoder.encode(lc.getDescription(),"UTF-8")));
						amap.put(String.valueOf(lc.getId()), lc);
					}
				}
				
				
				JSONObject map=new JSONObject();
				map.put("community", clist);
				map.put("building", blist);
				map.put("apartment", alist);
				back(response,map.toJSONString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("getallauthapp".equals(s)){
			try {

				LcCommunityDao comdao=new LcCommunityDao();
				LcBuildingDao builddao=new LcBuildingDao();
				LcApartmentDao apartdao=new LcApartmentDao();
				
				List<LcAuthApp> authList=new LcAuthAppDao().getAuthByClient(lu.getId());
				JSONArray authsend=new JSONArray();
				if(authList!=null) {
					
					LcLockDao lld=new LcLockDao();
					for(LcAuthApp auth:authList) {
						LcLock ll=lld.findById(auth.getLockId());
						if(ll==null) continue;
						LcApartment ap=apartdao.findById(ll.getApartmentId());
						LcBuilding build=builddao.findById(ap.getBuildingId());
						LcCommunity com=comdao.findById(build.getCommunityId());
						
						JSONObject jo=new JSONObject();
						jo.put("start", auth.getStartTime());
						jo.put("end", auth.getEndTime());
						jo.put("apartmentId", ap.getId());
						jo.put("id", auth.getId());
						jo.put("local", new String(java.net.URLEncoder.encode(com.getName()+"："+build.getName()+"："+ap.getName(),"UTF-8")));
						authsend.add(jo);
					}
				}

				back(response,authsend.toJSONString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("addapp".equals(s)){
			try {
				String authId=request.getParameter("authid");
				String phone=request.getParameter("phone");
				String starttime=request.getParameter("starttime");
				String endtime=request.getParameter("endtime");
				String same=request.getParameter("same");
				
				LcAuthApp laa=new LcAuthAppDao().findById(Integer.parseInt(authId));
				
				if(laa==null) {
					//没有授权
					JSONObject jo=new JSONObject();
					//jo.put("sn", ll.getSn());
					jo.put("result", "noauth");
					//jo.put("lockinfo", ll.getWxLockInfo(5));
					back(response,jo.toJSONString());
					return;
				}
				if(!"can_auth".equals(laa.getAtype())) {
					JSONObject jo=new JSONObject();
					//jo.put("sn", ll.getSn());
					jo.put("result", "authtypewrong");
					//jo.put("lockinfo", ll.getWxLockInfo(5));
					back(response,jo.toJSONString());
					return;
				}
				
				if("1".equals(same)) {
					endtime=laa.getEndTime();
					starttime=laa.getStartTime();
				}
				try{
					df.format(endtime);
					df.format(starttime);
				}catch(Exception ex){
					ex.printStackTrace();
					backresult(response,"datewrong");
					return;
				}
				LcUser l=new LcUserDao().getUserByPhone(phone);
				if(l==null) {
					backresult(response,"nophone");
					return;
				}
				
				LcAuthApp la=new LcAuthApp();
				la.setAtype("no_auth");
				la.setClientId(l.getId());
				la.setEndTime(endtime);
				la.setLockId(laa.getLockId());
				la.setOpenTimes(-1);
				la.setOwnerId(laa.getOwnerId());
				la.setStartTime(starttime);
				
				new LcAuthAppDao().save(la);
				backresult(response,"success");
				return;
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