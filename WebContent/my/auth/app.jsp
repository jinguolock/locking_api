<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcApartmentDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcBuildingDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcCommunityDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcLockDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcAuthAppDao"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcApartment"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcBuilding"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcCommunity"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcLock"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcAuthApp"%>
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
  var allAuth;
  var allLock;
  var allCommunity;
  var allBuild;
  var allApartment;
  var lockMap=new Object();
  var communityMap=new Object();
  var buildMap=new Object();
  var apartmentMap=new Object();
  var editAuthId;
  function authAdd(){
	  var phone=$("#add_auth_phone").val();
	  var type=$("#add_auth_type").val();
	  var start=$("#add_auth_start").val();
	  var end=$("#add_auth_end").val();
	  var apartmentId=$("#add_auth_apartment").val();
	  if(phone==""){
			alert("电话不能为空！");
			return;
		}
	  if(start==""||end==""){
		  alert("开始或结束时间不能为空！");
			return;
		}
	
	  var obj=new Object();
		obj.type=type;
		obj.phone=phone;
		obj.apartmentid= apartmentId;
		obj.starttime=start;
		obj.endtime=end;
		obj.opentimes="-1";
		obj.c="addapp";
	  $.get("<%=path %>/auth",obj, function(result){
		  if(result.result=="true"){
			  alert("添加授权信息完成");
			  location.reload();
		  }else if(result.result=="nolock"){
			  alert("此房间没有绑定锁");
		  }else if(result.result=="nophone"){
			  alert("此电话没有注册用户");
		  }else if(result.result=="datewrong"){
			  alert("开始或结束时间格式错误");
		  }else if(result.result=="noself"){
			  alert("不能给自己授权");
		  }
		  },"json");
  }
  function authEdit(authId,phone){
	  editAuthId=authId;
	  for(var i=0;i<allAuth.length;i++){
		  var auth=allAuth[i];
		  if(auth.id==authId){
			  var lock=lockMap[auth.lockId];
			  var ap=apartmentMap[lock.apartmentId];
			  var bu=buildMap[ap.buildingId];
			  var com=communityMap[bu.communityId];
			  
			  $("#edit_auth_phone").val(phone);
			  $("#edit_auth_type").val(auth.atype);
			  $("#edit_auth_start").val(auth.startTime);
			  $("#edit_auth_end").val(auth.endTime);
			  
			  $("#edit_auth_community").val(com.id);
			  editAuthCommunityChange();
			  $("#edit_auth_build").val(bu.id);
			  edditAuthBuildChange();
			  $("#edit_auth_apartment").val(ap.id);
			  return;
		  }
	  }
  }
  function authEditSubmit(){
	  var phone=$("#edit_auth_phone").val();
	  var type=$("#edit_auth_type").val();
	  var start=$("#edit_auth_start").val();
	  var end=$("#edit_auth_end").val();
	  var apartmentId=$("#edit_auth_apartment").val();
	  if(phone==""){
			alert("电话不能为空！");
			return;
		}
	  if(start==""||end==""){
		  alert("开始或结束时间不能为空！");
			return;
		}
	
	  var obj=new Object();
	  	obj.appId=editAuthId;
		obj.type=type;
		obj.phone=phone;
		obj.apartmentid= apartmentId;
		obj.starttime=start;
		obj.endtime=end;
		obj.opentimes="-1";
		obj.c="editapp";
	  $.get("<%=path %>/auth",obj, function(result){
		  if(result.result=="true"){
			  alert("添加授权信息完成");
			  location.reload();
		  }else if(result.result=="nolock"){
			  alert("此房间没有绑定锁");
		  }else if(result.result=="nophone"){
			  alert("此电话没有注册用户");
		  }else if(result.result=="datewrong"){
			  alert("开始或结束时间格式错误");
		  }else if(result.result=="noself"){
			  alert("不能给自己授权");
		  }
		  },"json");
  }
  function authDelete(authId){
	  $.get("<%=path %>/auth",{c:"deleteapp",appId:authId}, function(result){
			alert("已删除！");
			location.reload();
		  },"json");
  }
  
  function footer(){
	  
	  
	  $('#menu_app').addClass("active");
	  $('#menu_f_auth').addClass("active");
	  $('#example1').DataTable({
	      'paging'      : true,
	      'lengthChange': false,
	      'searching'   : false,
	      'ordering'    : true,
	      'info'        : true,
	      'autoWidth'   : false
	    });
	   

	  $.get("<%=path %>/community",{c:"getall"}, function(result){
	    	
			allCommunity=result.community;
			allBuild=result.building;
			allApartment=result.apartment;
			
			
			
			if(allCommunity!=null&&allCommunity.length>0){
				for(var i=0;i<allCommunity.length;i++){
					var com=allCommunity[i];
					com.name=decodeURI(com.name);
					com.address=decodeURI(com.address);
					$('#add_auth_community').append("<option value='"+com.id+"'>"+com.name+"</option>"); 
					communityMap[com.id]=com;
					$('#edit_auth_community').append("<option value='"+com.id+"'>"+com.name+"</option>"); 
				}
				if(allBuild!=null&&allBuild.length>0){
					var co=allCommunity[0];
					for(var i=0;i<allBuild.length;i++){
						var bu=allBuild[i];
						bu.name=decodeURI(bu.name);
						bu.description=decodeURI(bu.description);
						//var v=bu.communityId+"";
						if(bu.communityId==co.id){
							$('#add_auth_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
						}
						buildMap[bu.id]=bu;
					}
					
					if(allApartment!=null&&allApartment.length>0){
						var bui=allBuild[0];
						for(var i=0;i<allApartment.length;i++){
							var ap=allApartment[i];
							ap.name=decodeURI(ap.name);
							ap.description=decodeURI(ap.description);
							if(bui.id==ap.buildingId){
								$('#add_auth_apartment').append("<option value='"+ap.id+"'>"+ap.name+"</option>"); 
							}
							apartmentMap[ap.id]=ap;
						}
					}
				}
			}
			
			/* if(allBuild!=null&&allBuild.length>0){
				for(var i=0;i<allBuild.length;i++){
					var bu=allBuild[i];
					$('#add_apartment_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
				}
			} */
		  },"json");
	  
	  
	  $.get("<%=path %>/lock",{c:"getalllock"}, function(result){
		  allLock=result;
		  for(var i=0;i<allLock.length;i++){
				var lock=allLock[i];
				lockMap[lock.id]=lock;
			}
		  },"json");
	  $.get("<%=path %>/auth",{c:"getallapp"}, function(result){
		  allAuth=result;
			
		  },"json");
	    
  }
  function addAuthCommunityChange(){
	  $('#add_auth_build').empty();
	  $('#add_auth_apartment').empty();
	  if(allBuild!=null&&allBuild.length>0){
		  var communityId=$("#add_auth_community").val();
		  for(var i=0;i<allBuild.length;i++){
				var bu=allBuild[i];
				var v=bu.communityId;
				if(v==communityId){
					$('#add_auth_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
				}
				
			}
	  }
  }
  function addAuthBuildChange(){
	  $('#add_auth_apartment').empty();
	  if(allApartment!=null&&allApartment.length>0){
		  var buildId=$("#add_auth_build").val();
		  for(var i=0;i<allApartment.length;i++){
				var ap=allApartment[i];
				var v=ap.buildingId;
				if(v==buildId){
					$('#add_auth_apartment').append("<option value='"+ap.id+"'>"+ap.name+"</option>"); 
				}
				
			}
	  }
  }
  function editAuthCommunityChange(){
	  $('#edit_auth_build').empty();
	  $('#edit_auth_apartment').empty();
	  if(allBuild!=null&&allBuild.length>0){
		  var communityId=$("#edit_auth_community").val();
		  for(var i=0;i<allBuild.length;i++){
				var bu=allBuild[i];
				var v=bu.communityId;
				if(v==communityId){
					$('#edit_auth_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
				}
				
			}
	  }
  }
  function edditAuthBuildChange(){
	  $('#edit_auth_apartment').empty();
	  if(allApartment!=null&&allApartment.length>0){
		  var buildId=$("#edit_auth_build").val();
		  for(var i=0;i<allApartment.length;i++){
				var ap=allApartment[i];
				var v=ap.buildingId;
				if(v==buildId){
					$('#edit_auth_apartment').append("<option value='"+ap.id+"'>"+ap.name+"</option>"); 
				}
				
			}
	  }
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
        	授权管理
        <small>应用管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
        <li><a href="#">授权管理</a></li>
        <li class="active">应用管理</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title"><%=user.getUsername() %>的授权列表</h3>
              <div class="box-tools">
              <div class="btn-group">
                  <button type="button"   data-toggle="modal" data-target="#modal-add-auth" class="btn btn-block btn-info">添加授权</button>
                  
                </div>
                
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example1" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>授权手机</th>
                  <th>类型</th>
                  <th>所在小区</th>
                  <th>公寓楼</th>
                  <th>房间</th>
                  <th>起始时间</th>
                  <th>结束时间</th>
                  <th  width="100px">操作</th>
                </tr>
                </thead>
                
                <tbody>
                <%
                List<LcAuthApp> auths=new LcAuthAppDao().findByOwnerId(user.getId()); 
                List<LcLock> locks=new LcLockDao().findByOwnerId(user.getId()); 
                List<LcUser> users=new LcUserDao().findAll();
                Map<Integer,LcUser> userMap=new HashMap<Integer,LcUser>();
                if(users!=null&&users.size()>0){
                	for(LcUser com:users){
                		userMap.put(com.getId(), com);
                	}
                }               
                
                Map<Integer,LcLock> lockMap=new HashMap<Integer,LcLock>();
                if(locks!=null&&locks.size()>0){
                	for(LcLock com:locks){
                		lockMap.put(com.getId(), com);
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
                
                if(auths!=null&&auths.size()>0){
                	for(LcAuthApp auth:auths){
                		String tt="";
                		LcUser client=userMap.get(auth.getClientId());
                		LcLock lock=lockMap.get(auth.getLockId());
                		LcApartment ap=apartmentMap.get(lock.getApartmentId());
                		LcBuilding build=buildMap.get(ap.getBuildingId());
                		LcCommunity community=communityMap.get(build.getCommunityId());
                		if("can_auth".equals(auth.getAtype())){
                			tt="可授权";
                		}else if("no_auth".equals(auth.getAtype())){
                			tt="不可授权";
                		}
                %>
                <tr>
                  <td><%=client.getPhone()%></td>
                  <td><%=tt %></td>
                  <td><%=community!=null?community.getName():"" %></td>
                  <td><%=build!=null?build.getName():"" %></td>
                  <td><%=ap!=null?ap.getName():"" %></td>
                  <td><%=auth.getStartTime() %></td>
                  <td><%=auth.getEndTime() %></td>
                  <td>
                  <table>
                  <tr>
                  <td> <button onclick="authEdit('<%=auth.getId() %>','<%=client.getPhone() %>')"  data-toggle="modal" data-target="#modal-edit-auth" type="button" class="btn btn-block btn-info">修改</button></td>
                  <td> <button onclick="authDelete('<%=auth.getId() %>')" type="button" style="margin-left:10px" class="btn btn-block btn-default">删除</button></td>
                  </tr>
                  </table>
                  
				</td>
                </tr>
                <%	}
                	} %>
                
               
                
                </tbody>
                
              </table>
            </div>
            <!-- /.box-body -->
          </div>
         
          
          

         
            <!-- /.box-header -->
            
            
          <div class="modal fade" id="modal-add-auth">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加授权</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>客户电话：</td>
                	<td><input type="text" id="add_auth_phone" name="phone" class="form-control" placeholder="输入客户电话"></td>
                </tr>
                <tr>
                	<td>起始时间：</td>
                	<td><input type="text" id="add_auth_start"  name="start" class="form-control" placeholder="输入格式:2018-07-09 01:07:00"></td>
                </tr>
                <tr>
                	<td>结束时间：</td>
                	<td><input type="text" id="add_auth_end"  name="end" class="form-control" placeholder="输入格式:2018-07-09 01:07:00"></td>
                </tr>
                <tr>
                	<td>授权类型：</td>
                	<td> <div class="form-group">
                  <select id="add_auth_type" class="form-control">
                    <option value="no_auth">不可授权</option>
                    <option value="can_auth">可授权</option>
                  </select>
                </div></td>
                </tr>
                
                <tr>
                	<td>小区：</td>
                	<td> <div class="form-group">
                  <select onchange="addAuthCommunityChange()" id="add_auth_community" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                 <tr>
                	<td>公寓楼：</td>
                	<td> <div class="form-group">
                  <select onchange="addAuthBuildChange()"  id="add_auth_build" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                <tr>
                	<td>公寓房间：</td>
                	<td> <div class="form-group">
                  <select id="add_auth_apartment" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="authAdd()" class="btn btn-primary" data-dismiss="modal">添加授权</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        
        
       
        
        
        <!-------------------------------edit --------------------------------------------------------->
        
        <div class="modal fade" id="modal-edit-auth">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改授权信息</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>客户电话：</td>
                	<td><input type="text" id="edit_auth_phone" name="phone" class="form-control" placeholder="输入客户电话"></td>
                </tr>
                <tr>
                	<td>起始时间：</td>
                	<td><input type="text" id="edit_auth_start"  name="start" class="form-control" placeholder="输入格式:2018-07-09 01:07:00"></td>
                </tr>
                <tr>
                	<td>结束时间：</td>
                	<td><input type="text" id="edit_auth_end"  name="end" class="form-control" placeholder="输入格式:2018-07-09 01:07:00"></td>
                </tr>
                <tr>
                	<td>授权类型：</td>
                	<td> <div class="form-group">
                  <select id="add_auth_type" class="form-control">
                    <option value="no_auth">不可授权</option>
                    <option value="can_auth">可授权</option>
                  </select>
                </div></td>
                </tr>
                
                <tr>
                	<td>小区：</td>
                	<td> <div class="form-group">
                  <select onchange="editAuthCommunityChange()"  id="edit_auth_community" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                 <tr>
                	<td>公寓楼：</td>
                	<td> <div class="form-group">
                  <select onchange="editAuthBuildChange()" id="edit_auth_build" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                <tr>
                	<td>公寓房间：</td>
                	<td> <div class="form-group">
                  <select id="edit_auth_apartment" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="authEditSubmit()" class="btn btn-primary" data-dismiss="modal">修改授权</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        
        
        
        
        
        <!-- /.modal -->

        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>

 <jsp:include page="../foot.jsp" />