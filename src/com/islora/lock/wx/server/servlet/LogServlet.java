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
import com.islora.lock.wx.server.db.dao.LcLogBleDao;
import com.islora.lock.wx.server.db.dao.LcLogCardDao;
import com.islora.lock.wx.server.db.dao.LcLogFingerDao;
import com.islora.lock.wx.server.db.dao.LcLogLoraDao;
import com.islora.lock.wx.server.db.dao.LcLogPwdDao;
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
@WebServlet( value = "/log")
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static char[] cc=new char[128];
    public static DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogServlet() {
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
		if("deleteble".equals(s)){
			try {
				new LcLogBleDao().deleteByOwnerId(lu.getId());
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("deletecard".equals(s)){
			try {
				new LcLogCardDao().deleteByOwnerId(lu.getId());
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("deletepwd".equals(s)){
			try {
				new LcLogPwdDao().deleteByOwnerId(lu.getId());
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("deletefinger".equals(s)){
			try {
				new LcLogFingerDao().deleteByOwnerId(lu.getId());
				backresult(response,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("deletelora".equals(s)){
			try {
				new LcLogLoraDao().deleteByOwnerId(lu.getId());
				backresult(response,true);
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