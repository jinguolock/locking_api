<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcApartmentDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcBuildingDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcCommunityDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcLockDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcLogPwdDao"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcApartment"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcBuilding"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcCommunity"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcLock"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcLogPwd"%>
<%@page import="com.islora.lock.wx.server.core.MyProp"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="../header.jsp"%>
  <script type="text/javascript">
	
  function logClear(){
	  var obj=new Object();
		obj.c="deletepwd";
	  $.get("<%=path %>/log",obj, function(result){
		  location.reload();
		  },"json");
  }
  function footer(){
	  
	  
	  $('#menu_f_log').addClass("active");
	  $('#menu_log_ble').addClass("active");
	  $('#example1').DataTable({
	      'paging'      : true,
	      'lengthChange': false,
	      'searching'   : false,
	      'ordering'    : true,
	      'info'        : true,
	      'autoWidth'   : false
	    });
	    
  }
  
  </script>
</head>

<jsp:include page="../top.jsp" />
  <!-- Left side column. contains the logo and sidebar -->
  <jsp:include page="../left.jsp" />
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        	日志查看
        <small>密码日志</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
        <li><a href="#">日志查看</a></li>
        <li class="active">密码日志</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title"><%=user.getUsername() %>的密码开锁日志</h3>
              <div class="box-tools">
              <div class="btn-group">
                  <button type="button" onclick="logClear()" class="btn btn-block btn-info">清理日志</button>
                  
                </div>
                
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example1" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>开锁密码</th>
                  <th>类型</th>
                  <th>结果</th>
                  <th>所在小区</th>
                  <th>公寓楼</th>
                  <th>房间</th>
                  <th>操作时间</th>
                </tr>
                </thead>
                
                <tbody>
                <%
                List<LcLogPwd> logs=new LcLogPwdDao().findByOwnerId(user.getId()); 
                List<LcLock> locks=new LcLockDao().findByOwnerId(user.getId()); 
                List<LcUser> users=new LcUserDao().findAll();
                Map<Integer,LcUser> userMap=new HashMap<Integer,LcUser>();
                DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(users!=null&&users.size()>0){
                	for(LcUser com:users){
                		userMap.put(com.getId(), com);
                	}
                }               
                
                Map<String,LcLock> lockMap=new HashMap<String,LcLock>();
                if(locks!=null&&locks.size()>0){
                	for(LcLock com:locks){
                		lockMap.put(com.getSn(), com);
                	}
                }               
                
                List<LcCommunity> communitys=new LcCommunityDao().findByOwnerId(user.getId()); 
                Map<Integer,LcCommunity> communityMap=new HashMap<Integer,LcCommunity>();
                if(communitys!=null&&communitys.size()>0){
                	for(LcCommunity com:communitys){
                		communityMap.put(com.getId(), com);
                	}
                }
                
                List<LcBuilding> builds=new LcBuildingDao().findByOwnerId(user.getId()); 
                Map<Integer,LcBuilding> buildMap=new HashMap<Integer,LcBuilding>();
                if(builds!=null&&builds.size()>0){
                	for(LcBuilding build:builds){
                		buildMap.put(build.getId(),build);
                	}
                }
                List<LcApartment> apartments=new LcApartmentDao().findByOwnerId(user.getId()); 
                Map<Integer,LcApartment> apartmentMap=new HashMap<Integer,LcApartment>();
                if(apartments!=null&&apartments.size()>0){
                	for(LcApartment apartment:apartments){
                		apartmentMap.put(apartment.getId(),apartment);
                	}
                }
                
                if(logs!=null&&logs.size()>0){
                	for(LcLogPwd log:logs){
                		String tt=log.getPtype();
                		//LcUser client=userMap.get(log.getClientId());
                		LcLock lock=lockMap.get(log.getLockSn());
                		LcApartment ap=apartmentMap.get(lock.getApartmentId());
                		LcBuilding build=buildMap.get(ap.getBuildingId());
                		LcCommunity community=communityMap.get(build.getCommunityId());
                		
                		
                %>
                <tr>
                  <td><%=log.getPwd()%></td>
                  <td><%=log.getPtype() %></td>
                  <td><%=log.getLockResult() %></td>
                  <td><%=community!=null?community.getName():"" %></td>
                  <td><%=build!=null?build.getName():"" %></td>
                  <td><%=ap!=null?ap.getName():"" %></td>
                  <td><%=log.getUpTime()%></td>
                  
                </tr>
                <%	}
                	} %>
                
               
                
                </tbody>
                
              </table>
            </div>
            <!-- /.box-body -->
          </div>
         
          
          

         
            <!-- /.box-header -->
            
   
        

       
        
        
        <!-------------------------------edit --------------------------------------------------------->
        
        
        
        
        
        <!-- /.modal -->

        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>

 <jsp:include page="../foot.jsp" />