package com.islora.lock.wx.server.servlet;


import java.io.IOException;

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
import com.islora.lock.wx.server.db.dao.LcBuildingDao;
import com.islora.lock.wx.server.db.dao.LcCommunityDao;
import com.islora.lock.wx.server.db.dao.LcLockDao;
import com.islora.lock.wx.server.db.dao.LcUserDao;
import com.islora.lock.wx.server.db.entity.LcApartment;
import com.islora.lock.wx.server.db.entity.LcBuilding;
import com.islora.lock.wx.server.db.entity.LcCommunity;
import com.islora.lock.wx.server.db.entity.LcLock;
import com.islora.lock.wx.server.db.entity.LcUser;
import com.islora.lock.wx.server.db.utils.CustomException;



/**
 * Servlet implementation class StartServlet
 */
@WebServlet( value = "/community")
public class CommunityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static char[] cc=new char[128];
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommunityServlet() {
        super();
    }

	@Override
	public void init() throws ServletException {
		super.init();
	}
		
	private static final String CODING = "ISO-8859-1";
    private static final String UTF8 = "UTF-8";
 
    public static String charsetString(String s) {
        String newString = null;
        try {
            newString = new String(s.getBytes(CODING), UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newString;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**/
		
		String s=request.getParameter("c");
		LcUser lu=(LcUser)request.getSession().getAttribute("user");
		if("getall".equals(s)){
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
		}
		else if("getcommunity".equals(s)){
			try {
				List<LcCommunity> list=new LcCommunityDao().findByOwnerId(lu.getId());
				back(response,JSON.toJSONString(list));
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("getbuilding".equals(s)){
			try {
				List<LcBuilding> list=new LcBuildingDao().findByOwnerId(lu.getId());
				back(response,JSON.toJSONString(list));
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("getapartment".equals(s)){
			try {
				List<LcApartment> list=new LcApartmentDao().findByOwnerId(lu.getId());
				back(response,JSON.toJSONString(list));
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if("addcommunity".equals(s)){
			try {
				String lat=request.getParameter("lat");
				String lon=request.getParameter("lon");
				String name=request.getParameter("name");
				String type=request.getParameter("type");
				String address=request.getParameter("address");
				LcCommunity lc=new LcCommunity();
				lc.setLat(lat);
				lc.setLon(lon);
				lc.setName(name);
				lc.setOwnerId(lu.getId());
				lc.setCtype(type);
				lc.setAddress(address);
				new LcCommunityDao().save(lc);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("editcommunity".equals(s)){
			try {
				String lat=request.getParameter("lat");
				String lon=request.getParameter("lon");
				String name=request.getParameter("name");
				String type=request.getParameter("type");
				String lcid=request.getParameter("communityId");
				String address=request.getParameter("address");
				LcCommunity lc=new LcCommunity();
				lc.setLat(lat);
				lc.setLon(lon);
				lc.setName(name);
				lc.setOwnerId(lu.getId());
				lc.setCtype(type);
				lc.setId(Integer.parseInt(lcid));
				lc.setAddress(address);
				new LcCommunityDao().update(lc);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if("deletecommunity".equals(s)){
			try {
				String lcid=request.getParameter("communityId");
				new LcCommunityDao().deleteById(Integer.parseInt(lcid));
				List<LcBuilding> builds=new LcBuildingDao().findByCommunityId(Integer.parseInt(lcid));
				LcApartmentDao adao=new LcApartmentDao();
				if(builds!=null) {
					for(LcBuilding b:builds) {
						adao.deleteByBuildId(b.getId());
					}
				}
				new LcBuildingDao().deleteByCommunityId(Integer.parseInt(lcid));
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("addbuilding".equals(s)){
			try {
				String communityId=request.getParameter("communityId");
				String desc=request.getParameter("desc");
				String floorNumber=request.getParameter("floorNumber");
				String name=request.getParameter("name");
				String uintNumber=request.getParameter("unitNumber");
				LcBuilding lb=new LcBuilding();
				lb.setCommunityId(Integer.parseInt(communityId));
				lb.setDescription(desc);
				lb.setFloorNumber(Integer.parseInt(floorNumber));
				lb.setName(name);
				lb.setOwnerId(lu.getId());
				lb.setUintNumber(Integer.parseInt(uintNumber));
				new LcBuildingDao().save(lb);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("editbuilding".equals(s)){
			try {
				String buildingId=request.getParameter("buildingId");
				String communityId=request.getParameter("communityId");
				String desc=request.getParameter("desc");
				String floorNumber=request.getParameter("floorNumber");
				String name=request.getParameter("name");
				String uintNumber=request.getParameter("unitNumber");
				LcBuilding lb=new LcBuilding();
				lb.setId(Integer.parseInt(buildingId));
				lb.setCommunityId(Integer.parseInt(communityId));
				lb.setDescription(desc);
				lb.setFloorNumber(Integer.parseInt(floorNumber));
				lb.setName(name);
				lb.setOwnerId(lu.getId());
				lb.setUintNumber(Integer.parseInt(uintNumber));
				new LcBuildingDao().update(lb);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("deletebuilding".equals(s)){
			try {
				String lcid=request.getParameter("buildingId");
				LcApartmentDao adao=new LcApartmentDao();
				new LcBuildingDao().deleteById(Integer.parseInt(lcid));
				adao.deleteByBuildId(Integer.parseInt(lcid));
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("addapartment".equals(s)){
			try {
				String buildingId=request.getParameter("buildingId");
				String desc=request.getParameter("desc");
				String floor=request.getParameter("floor");
				String name=request.getParameter("name");
				String uint=request.getParameter("uint");
				LcApartment la=new LcApartment();
				la.setBuildingId(Integer.parseInt(buildingId));
				la.setDescription(desc);
				la.setFloor(Integer.parseInt(floor));
				la.setName(name);
				la.setOwnerId(lu.getId());
				la.setUint(Integer.parseInt(uint));
				new LcApartmentDao().save(la);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("editapartment".equals(s)){
			try {
				String apartmentId=request.getParameter("apartmentId");
				String buildingId=request.getParameter("buildingId");
				String desc=request.getParameter("desc");
				String floor=request.getParameter("floor");
				String name=request.getParameter("name");
				String uint=request.getParameter("uint");
				LcApartment la=new LcApartment();
				la.setId(Integer.parseInt(apartmentId));
				la.setBuildingId(Integer.parseInt(buildingId));
				la.setDescription(desc);
				la.setFloor(Integer.parseInt(floor));
				la.setName(name);
				la.setOwnerId(lu.getId());
				la.setUint(Integer.parseInt(uint));
				new LcApartmentDao().update(la);
				backresult(response,true);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("deleteapartment".equals(s)){
			try {
				String lcid=request.getParameter("apartmentId");
				new LcApartmentDao().deleteById(Integer.parseInt(lcid));
				backresult(response,true);
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
}