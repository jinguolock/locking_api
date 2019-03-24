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
import com.islora.lock.wx.server.db.dao.LcAuthFingerDao;
import com.islora.lock.wx.server.db.dao.LcAuthPwdDao;
import com.islora.lock.wx.server.db.dao.LcBuildingDao;
import com.islora.lock.wx.server.db.dao.LcCommunityDao;
import com.islora.lock.wx.server.db.dao.LcLockDao;
import com.islora.lock.wx.server.db.dao.LcReaderDao;
import com.islora.lock.wx.server.db.dao.LcUserDao;
import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.entity.LcAuthApp;
import com.islora.lock.wx.server.db.entity.LcAuthCard;
import com.islora.lock.wx.server.db.entity.LcAuthFinger;
import com.islora.lock.wx.server.db.entity.LcAuthPwd;
import com.islora.lock.wx.server.db.entity.LcBuilding;
import com.islora.lock.wx.server.db.entity.LcCommunity;
import com.islora.lock.wx.server.db.entity.LcLock;
import com.islora.lock.wx.server.db.entity.LcReader;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.utils.CustomException;



/**
 * Servlet implementation class StartServlet
 */
@WebServlet( value = "/auth")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static char[] cc=new char[128];
    public static DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthServlet() {
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
		if("getallapp".equals(s)){
			try {
				List<LcAuthApp> clist=new LcAuthAppDao().findByOwnerId(lu.getId());
				back(response,JSON.toJSONString(clist));
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("getallcard".equals(s)){
			try {
				List<LcAuthCard> clist=new LcAuthCardDao().findByOwnerId(lu.getId());
				back(response,JSON.toJSONString(clist));
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}if("getallpwd".equals(s)){
			try {
				List<LcAuthPwd> clist=new LcAuthPwdDao().findByOwnerId(lu.getId());
				if(clist!=null) {
					for(LcAuthPwd lc:clist) {
						lc.setName(new String(java.net.URLEncoder.encode(lc.getName(),"UTF-8")));
					}
				}
				back(response,JSON.toJSONString(clist));
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}if("getallfinger".equals(s)){
			try {
				List<LcAuthFinger> clist=new LcAuthFingerDao().findByOwnerId(lu.getId());
				if(clist!=null) {
					for(LcAuthFinger lc:clist) {
						lc.setName(new String(java.net.URLEncoder.encode(lc.getName(),"UTF-8")));
					}
				}
				back(response,JSON.toJSONString(clist));
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
				//应该可以给自己授权
//				if(l.getId()==lu.getId()) {
//					backresult(response,"noself");
//					return;
//				}
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
				
				LcLock lock=new LcLockDao().getLockByApartment(Integer.parseInt(apartmentid));
				if(lock==null) {
					backresult(response,"nolock");
					return;
				}
				
				if("time".equals(type)){
					LcUser l=new LcUserDao().getUserByPhone(phone);
					if(l==null) {
						backresult(response,"nophone");
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
				}else if("sntime".equals(type)){
					if(!("sncard".equals(lock.getLtype()))){
						backresult(response,"lock_type_error");
						return;
					}
					String sn=phone;
					LcAuthCard laa=new LcAuthCard();
					laa.setCtype(type);
					laa.setClientId(0);
					laa.setEndTime(endtime);
					laa.setLockId(lock.getId());
					laa.setOpenTimes(Integer.parseInt(opentimes));
					laa.setOwnerId(lu.getId());
					laa.setStartTime(starttime);
					laa.setCardNo("");
					laa.setReaderNo(sn);
					new LcAuthCardDao().save(laa);
					backresult(response,true);
					return;
				}
				
				
				
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
		}else if("deleteexpirecard".equals(s)){
			try {
				new LcAuthCardDao().clearExpireAuth(lu.getId());
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
		}else if("addpwd".equals(s)){
			try {
				String type=request.getParameter("type");
				String pwd=request.getParameter("pwd");
				String name=request.getParameter("name");
				String starttime=request.getParameter("starttime");
				String endtime=request.getParameter("endtime");
				String opentimes=request.getParameter("opentimes");
				String apartmentid=request.getParameter("apartmentid");
				
				LcLock lock=new LcLockDao().getLockByApartment(Integer.parseInt(apartmentid));
				if(lock==null) {
					backresult(response,"nolock");
					return;
				}
				//应该可以给自己授权
//				if(l.getId()==lu.getId()) {
//					backresult(response,"noself");
//					return;
//				}
				try {
					df.parse(starttime);
					df.parse(endtime);
				}catch(Exception ex) {
					backresult(response,"datewrong");
					return;
				}
				LcAuthPwd laa=new LcAuthPwd();
				laa.setPtype(type);
				laa.setPwd(pwd);
				laa.setName(name);
				laa.setStatus("normal");
				laa.setEndTime(endtime);
				laa.setLockId(lock.getId());
				laa.setOpenTimes(Integer.parseInt(opentimes));
				laa.setOwnerId(lu.getId());
				laa.setStartTime(starttime);
				
				new LcAuthPwdDao().save(laa);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if("deletepwd".equals(s)){
			try {
				String pwdId=request.getParameter("pwdId");
				new LcAuthPwdDao().deleteById(Integer.parseInt(pwdId));
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("editpwd".equals(s)){
			try {
				String type=request.getParameter("type");
				String pwd=request.getParameter("pwd");
				String name=request.getParameter("name");
				String starttime=request.getParameter("starttime");
				String endtime=request.getParameter("endtime");
				String opentimes=request.getParameter("opentimes");
				String apartmentid=request.getParameter("apartmentid");
				String pwdId=request.getParameter("pwdId");
				
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
				LcAuthPwd laa=new LcAuthPwd();
				laa.setPwd(pwd);
				laa.setName(name);
				laa.setId(Integer.parseInt(pwdId));
				laa.setPtype(type);
				laa.setEndTime(endtime);
				laa.setLockId(lock.getId());
				laa.setOpenTimes(Integer.parseInt(opentimes));
				laa.setOwnerId(lu.getId());
				laa.setStartTime(starttime);
				
				new LcAuthPwdDao().update(laa);
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if("addfinger".equals(s)){
			try {
				String type=request.getParameter("type");
				String number=request.getParameter("number");
				String name=request.getParameter("name");
				String starttime=request.getParameter("starttime");
				String endtime=request.getParameter("endtime");
				String opentimes=request.getParameter("opentimes");
				String apartmentid=request.getParameter("apartmentid");
				
				LcLock lock=new LcLockDao().getLockByApartment(Integer.parseInt(apartmentid));
				if(lock==null) {
					backresult(response,"nolock");
					return;
				}
				//应该可以给自己授权
//				if(l.getId()==lu.getId()) {
//					backresult(response,"noself");
//					return;
//				}
				try {
					df.parse(starttime);
					df.parse(endtime);
				}catch(Exception ex) {
					backresult(response,"datewrong");
					return;
				}
				LcAuthFinger laa=new LcAuthFinger();
				laa.setFtype(type);
				laa.setHwid(Integer.parseInt(number));
				laa.setName(name);
				laa.setStatus("normal");
				laa.setEndTime(endtime);
				laa.setLockId(lock.getId());
				laa.setOpenTimes(Integer.parseInt(opentimes));
				laa.setOwnerId(lu.getId());
				laa.setStartTime(starttime);
				
				new LcAuthFingerDao().save(laa);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if("deletefinger".equals(s)){
			try {
				String fingerId=request.getParameter("fingerId");
				new LcAuthFingerDao().deleteById(Integer.parseInt(fingerId));
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("editfinger".equals(s)){
			try {
				String type=request.getParameter("type");
				String number=request.getParameter("number");
				String name=request.getParameter("name");
				String starttime=request.getParameter("starttime");
				String endtime=request.getParameter("endtime");
				String opentimes=request.getParameter("opentimes");
				String apartmentid=request.getParameter("apartmentid");
				String fingerId=request.getParameter("fingerId");
				
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
				LcAuthFinger laa=new LcAuthFinger();
				laa.setHwid(Integer.parseInt(number));
				laa.setName(name);
				laa.setId(Integer.parseInt(fingerId));
				laa.setFtype(type);
				laa.setEndTime(endtime);
				laa.setLockId(lock.getId());
				laa.setOpenTimes(Integer.parseInt(opentimes));
				laa.setOwnerId(lu.getId());
				laa.setStartTime(starttime);
				
				new LcAuthFingerDao().update(laa);
				backresult(response,true);
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