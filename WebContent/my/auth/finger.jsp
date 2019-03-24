<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcApartmentDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcBuildingDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcCommunityDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcLockDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcAuthFingerDao"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcApartment"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcBuilding"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcCommunity"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcLock"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcAuthFinger"%>
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
	  var number=$("#add_auth_number").val();
	  var name=$("#add_auth_name").val();
	  var opentimes=$("#add_auth_opentimes").val();
	  var type=$("#add_auth_type").val();
	  var start=$("#add_auth_start").val();
	  var end=$("#add_auth_end").val();
	  var apartmentId=$("#add_auth_apartment").val();
	  if(pwd==""){
			alert("密码不能为空！");
			return;
		}
	  if(start==""||end==""){
		  alert("开始或结束时间不能为空！");
			return;
		}
	
	  var obj=new Object();
		obj.type=type;
		obj.number=number;
		obj.opentimes=opentimes;
		obj.apartmentid= apartmentId;
		obj.starttime=start;
		obj.endtime=end;
		obj.name=name;
		obj.c="addfinger";
	  $.get("<%=path %>/auth",obj, function(result){
		  if(result.result=="true"){
			  alert("添加授权信息完成");
			  location.reload();
		  }else if(result.result=="nolock"){
			  alert("此房间没有绑定锁");
		  }else if(result.result=="datewrong"){
			  alert("开始或结束时间格式错误");
		  }else if(result.result=="noself"){
			  alert("不能给自己授权");
		  }
		  },"json");
  }
  function authEdit(authId){
	  editAuthId=authId;
	  for(var i=0;i<allAuth.length;i++){
		  var auth=allAuth[i];
		  if(auth.id==authId){
			  var lock=lockMap[auth.lockId];
			  var ap=apartmentMap[lock.apartmentId];
			  var bu=buildMap[ap.buildingId];
			  var com=communityMap[bu.communityId];
			  
			  $("#edit_auth_number").val(auth.hwid);
			  $("#edit_auth_name").val(auth.name);
			  $("#edit_auth_opentimes").val(auth.openTimes);
			  $("#edit_auth_type").val(auth.ptype);
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
	  var number=$("#edit_auth_number").val();
	  var name=$("#edit_auth_name").val();
	  var opentimes=$("#edit_auth_opentimes").val();
	  var type=$("#edit_auth_type").val();
	  var start=$("#edit_auth_start").val();
	  var end=$("#edit_auth_end").val();
	  var apartmentId=$("#edit_auth_apartment").val();
	  if(pwd==""){
			alert("密码不能为空！");
			return;
		}
	  if(start==""||end==""){
		  alert("开始或结束时间不能为空！");
			return;
		}
	
	  var obj=new Object();
	  	obj.pwdId=editAuthId;
		obj.type=type;
		obj.number=number;
		obj.name=name;
		obj.apartmentid= apartmentId;
		obj.starttime=start;
		obj.endtime=end;
		obj.opentimes=opentimes;
		obj.c="editpwd";
	  $.get("<%=path %>/auth",obj, function(result){
		  if(result.result=="true"){
			  alert("添加授权信息完成");
			  location.reload();
		  }else if(result.result=="nolock"){
			  alert("此房间没有绑定锁");
		  }else if(result.result=="datewrong"){
			  alert("开始或结束时间格式错误");
		  }else if(result.result=="noself"){
			  alert("不能给自己授权");
		  }
		  },"json");
  }
  function authDelete(authId){
	  $.get("<%=path %>/auth",{c:"deletefinger",fingerId:authId}, function(result){
			alert("已删除！");
			location.reload();
		  },"json");
  }
  
  function footer(){
	  
	  
	  $('#menu_pwd').addClass("active");
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
	  $.get("<%=path %>/auth",{c:"getallfinger"}, function(result){
		  allAuth=result;
		  for(var i=0;i<allAuth.length;i++){
				var com=allAuth[i];
				com.name=decodeURI(com.name);
			}
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
        <small>密码管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
        <li><a href="#">授权管理</a></li>
        <li class="active">密码管理</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title"><%=user.getUsername() %>的密码列表</h3>
              <div class="box-tools">
              <div class="btn-group">
                  <button type="button"   data-toggle="modal" data-target="#modal-add-auth" class="btn btn-block btn-info">添加指纹</button>
                  
                </div>
                
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example1" class="table table-bordered table-hover">
                <thead>
                <tr>
                	<th width="130px">起始时间</th>
                  <th width="130px">结束时间</th>
                  <th width="60px">模型序号</th>
                  <th>描述</th>
                  <th>状态</th>
                  <th>类型</th>
                  <th>所在小区</th>
                  <th>公寓楼</th>
                  <th>房间</th>
                  
                  <th width="70px">开锁次数</th>
                  <th  width="100px">操作</th>
                </tr>
                </thead>
                
                <tbody>
                <%
                List<LcAuthFinger> auths=new LcAuthFingerDao().findByOwnerId(user.getId()); 
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
                	for(LcAuthFinger auth:auths){
                		String tt="";
                		LcLock lock=lockMap.get(auth.getLockId());
                		LcApartment ap=apartmentMap.get(lock.getApartmentId());
                		LcBuilding build=buildMap.get(ap.getBuildingId());
                		LcCommunity community=communityMap.get(build.getCommunityId());
                		
                %>
                <tr>
                  <td><%=auth.getStartTime() %></td>
                  <td><%=auth.getEndTime() %></td>
                  <td><%=auth.getHwid()%></td>
                  <td><%=auth.getName()%></td>
                  <td><%=auth.getStatus()%></td>
                  <td><%=auth.getFtype() %></td>
                  <td><%=community!=null?community.getName():"" %></td>
                  <td><%=build!=null?build.getName():"" %></td>
                  <td><%=ap!=null?ap.getName():"" %></td>
                  
                  <td><%=auth.getOpenTimes() %></td>
                  <td>
                  <table>
                  <tr>
                  <td> <button onclick="authEdit('<%=auth.getId() %>')"  data-toggle="modal" data-target="#modal-edit-auth" type="button" class="btn btn-block btn-info">修改</button></td>
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
                <h4 class="modal-title">添加指纹</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
            	<tr>
                	<td>输入序号：</td>
                	<td><input type="text" id="add_auth_number" name="number" class="form-control" placeholder="输入序号"></td>
                </tr>
                <tr>
                	<td>输入描述：</td>
                	<td><input type="text" id="add_auth_name" name="name" class="form-control" placeholder="输入描述"></td>
                </tr>
                <tr>
                	<td>开门次数：</td>
                	<td><input type="text" id="add_auth_opentimes" name="opentimes" class="form-control" placeholder="开门次数"></td>
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
                	<td>指纹类型：</td>
                	<td> <div class="form-group">
                  <select id="add_auth_type" class="form-control">
                  	<option value="server">普通指纹</option>
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
                <button type="button" onclick="authAdd()" class="btn btn-primary" data-dismiss="modal">添加指纹</button>
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
                <h4 class="modal-title">修改授权指纹</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                
                <tr>
                	<td>描述：</td>
                	<td><input type="text" id="edit_auth_name" name="name" class="form-control" placeholder="输入描述"></td>
                </tr>
                <tr>
                	<td>开门次数：</td>
                	<td><input type="text" id="edit_auth_opentimes" name="opentimes" class="form-control" placeholder="开门次数"></td>
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
                  <select id="edit_auth_type" class="form-control">
                    <option value="server">普通</option>
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
                <button type="button" onclick="authEditSubmit()" class="btn btn-primary" data-dismiss="modal">修改密码</button>
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