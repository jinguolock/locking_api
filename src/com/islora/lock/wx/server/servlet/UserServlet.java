package com.islora.lock.wx.server.servlet;


import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.islora.lock.wx.server.db.dao.LcUserDao;
import com.islora.lock.wx.server.db.entity.LcUser;



/**
 * Servlet implementation class StartServlet
 */
@WebServlet( value = "/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static char[] cc=new char[128];
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
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
		// TODO Auto-generated method stub
		/**/
		String s=request.getParameter("c");
		
		if("login".equals(s)){
			try {
				String username=request.getParameter("username");
				String pwd=request.getParameter("password");
				LcUserDao udao=new LcUserDao();
				LcUser user= udao.getUser(username);
				if(user!=null){
					if(user.getPassword().equals(pwd)) {
						request.getSession().setAttribute("user", user);
						backresult(response,true);
						return;
					}else {
						JSONObject js=new JSONObject ();
						js.put("result", "error_pwd");
						back(response,js.toJSONString());
						return;
					}
					
				}
				else {
					JSONObject js=new JSONObject ();
					js.put("result", "error_nouser");
					back(response,js.toJSONString());
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("register".equals(s)){
			try {
				String phone=request.getParameter("phone");
				String password=request.getParameter("password");
				String address=request.getParameter("address");
				String email=request.getParameter("email");
				String idsn=request.getParameter("idsn");
				String name=request.getParameter("name");
				String type=request.getParameter("type");
				String username=request.getParameter("username");
				LcUser user=new LcUser();
				user.setAddress(address);
				user.setEmail(email);
				user.setIdsn(idsn);
				user.setName(name);
				user.setPassword(password);
				user.setPhone(phone);
				user.setType(type);
				user.setUsername(username);
				
				String r=new LcUserDao().register(user);
				JSONObject js=new JSONObject ();
				js.put("result", r);
				back(response,js.toJSONString());
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if("edituser".equals(s)){
			try {
				String phone=request.getParameter("phone");
				String password=request.getParameter("password");
				String address=request.getParameter("address");
				String email=request.getParameter("email");
				String idsn=request.getParameter("idsn");
				String name=request.getParameter("name");
				String type=request.getParameter("type");
				String username=request.getParameter("username");
				LcUser user=(LcUser)request.getSession().getAttribute("user");
				user.setAddress(address);
				user.setEmail(email);
				user.setIdsn(idsn);
				user.setName(name);
				user.setPassword(password);
				user.setPhone(phone);
				user.setType(type);
				user.setUsername(username);
				
				new LcUserDao().update(user);
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
}