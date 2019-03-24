<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcApartmentDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcBuildingDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcCommunityDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcLockDao"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcApartment"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcBuilding"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcCommunity"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcLock"%>
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
  var allLock;
  var allCommunity;
  var allBuild;
  var allApartment;
  var communityMap=new Object();
  var buildMap=new Object();
  var apartmentMap=new Object();
  var editLockId;
  function lockAdd(){
	  var sn=$("#add_lock_sn").val();
	  var expire=$("#add_lock_expire").val();
	  var type=$("#add_lock_type").val();
	  var status=$("#add_lock_status").val();
	  var apartmentId=$("#add_lock_apartment").val();
	  if(sn==""){
			alert("序列号不能为空！");
			return;
		}
	  if(expire==""){
		  expire="0";
		}
	  if(isNaN(expire)){
		  alert("密钥更新周期需要为数字");
		  return;
	  }
	  var obj=new Object();
		obj.ltype=type;
		obj.sn=sn;
		obj.apartmentId= apartmentId;
		obj.expire=expire;
		obj.status=status;
		
		obj.c="addlock";
	  $.get("<%=path %>/lock",obj, function(result){
			alert("添加锁信息完成");
			location.reload();
		  },"json");
  }
  function lockEdit(lockId){
	  editLockId=lockId;
	  for(var i=0;i<allLock.length;i++){
		  var lock=allLock[i];
		  if(lock.id==lockId){
			  var ap=apartmentMap[lock.apartmentId];
			  var bu=buildMap[ap.buildingId];
			  var com=communityMap[bu.communityId];
			  $("#edit_lock_sn").val(lock.sn);
			  $("#edit_lock_expire").val(lock.keyExpireInterval);
			  $("#edit_lock_type").val(lock.ltype);
			  $("#edit_lock_status").val(lock.status+"");
			  
			  
			  $("#edit_lock_community").val(com.id);
			  editLockCommunityChange();
			  $("#edit_lock_build").val(bu.id);
			  edditLockBuildChange();
			  $("#edit_lock_apartment").val(ap.id);
			  return;
		  }
	  }
  }
  function lockEditSubmit(){
	  var expire=$("#edit_lock_expire").val();
	  var type=$("#edit_lock_type").val();
	  var status=$("#edit_lock_status").val();
	  var key=$("#edit_lock_key").val();
	  var apartmentId=$("#edit_lock_apartment").val();
	  if(expire==""){
		  expire="0";
		}
	  if(isNaN(expire)){
		  alert("密钥更新周期需要为数字");
		  return;
	  }
	  var obj=new Object();
		obj.ltype=type;
		obj.apartmentId= apartmentId;
		obj.expire=expire;
		obj.lockId=editLockId;
		obj.status=status;
		obj.key=key;
		obj.c="editlock";
	  $.get("<%=path %>/lock",obj, function(result){
			alert("修改锁信息完成");
			location.reload();
		  },"json");
  }
  function lockDelete(lockId){
	  $.get("<%=path %>/lock",{c:"deletelock",lockId:lockId}, function(result){
			alert("已删除！");
			location.reload();
		  },"json");
  }
  
  function footer(){
	  
	  
	  $('#menu_lock').addClass("active");
	  $('#menu_f_device').addClass("active");
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
					$('#add_lock_community').append("<option value='"+com.id+"'>"+com.name+"</option>"); 
					communityMap[com.id]=com;
					$('#edit_lock_community').append("<option value='"+com.id+"'>"+com.name+"</option>"); 
				}
				if(allBuild!=null&&allBuild.length>0){
					var co=allCommunity[0];
					for(var i=0;i<allBuild.length;i++){
						var bu=allBuild[i];
						bu.name=decodeURI(bu.name);
						bu.description=decodeURI(bu.description);
						//var v=bu.communityId+"";
						if(bu.communityId==co.id){
							$('#add_lock_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
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
								$('#add_lock_apartment').append("<option value='"+ap.id+"'>"+ap.name+"</option>"); 
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
			
		  },"json");
	    
  }
  function addLockCommunityChange(){
	  $('#add_lock_build').empty();
	  $('#add_lock_apartment').empty();
	  if(allBuild!=null&&allBuild.length>0){
		  var communityId=$("#add_lock_community").val();
		  for(var i=0;i<allBuild.length;i++){
				var bu=allBuild[i];
				var v=bu.communityId;
				if(v==communityId){
					$('#add_lock_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
				}
				
			}
	  }
  }
  function addLockBuildChange(){
	  $('#add_lock_apartment').empty();
	  if(allApartment!=null&&allApartment.length>0){
		  var buildId=$("#add_lock_build").val();
		  for(var i=0;i<allApartment.length;i++){
				var ap=allApartment[i];
				var v=ap.buildingId;
				if(v==buildId){
					$('#add_lock_apartment').append("<option value='"+ap.id+"'>"+ap.name+"</option>"); 
				}
				
			}
	  }
  }
  function editLockCommunityChange(){
	  $('#edit_lock_build').empty();
	  $('#edit_lock_apartment').empty();
	  if(allBuild!=null&&allBuild.length>0){
		  var communityId=$("#edit_lock_community").val();
		  for(var i=0;i<allBuild.length;i++){
				var bu=allBuild[i];
				var v=bu.communityId;
				if(v==communityId){
					$('#edit_lock_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
				}
				
			}
	  }
  }
  function edditLockBuildChange(){
	  $('#edit_lock_apartment').empty();
	  if(allApartment!=null&&allApartment.length>0){
		  var buildId=$("#edit_lock_build").val();
		  for(var i=0;i<allApartment.length;i++){
				var ap=allApartment[i];
				var v=ap.buildingId;
				if(v==buildId){
					$('#edit_lock_apartment').append("<option value='"+ap.id+"'>"+ap.name+"</option>"); 
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
        	设备管理
        <small>门锁管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
        <li><a href="#">设备管理</a></li>
        <li class="active">门锁管理</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title"><%=user.getUsername() %>的门锁列表</h3>
              <div class="box-tools">
              <div class="btn-group">
                  <button type="button"   data-toggle="modal" data-target="#modal-add-lock" class="btn btn-block btn-info">添加门锁</button>
                  
                </div>
                
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example1" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>锁SN</th>
                  <th>类型</th>
                  <th>状态</th>
                  <th>密钥生产日期</th>
                  <th>密钥更新周期</th>
                  <th>小区</th>
                  <th>公寓楼</th>
                  <th>公寓屋</th>
                  <th  width="100px">操作</th>
                </tr>
                </thead>
                
                <tbody>
                <%
                List<LcLock> locks=new LcLockDao().findByOwnerId(user.getId()); 
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
                if(locks!=null&&locks.size()>0){
                	for(LcLock lock:locks){
                		String tt="";
                		String status="正常运行";
                		LcApartment ap=apartmentMap.get(lock.getApartmentId());
                		LcBuilding build=buildMap.get(ap.getBuildingId());
                		LcCommunity community=communityMap.get(build.getCommunityId());
                		if("card".equals(lock.getLtype())){
                			tt="刷M1卡型门锁";
                		}else if("sncard".equals(lock.getLtype())){
                			tt="刷身份证型门锁";
                		}
                		if(lock.getStatus()==2){
                			status="禁止使用";
                		}else if(lock.getStatus()==3){
                			status="蓝牙模式";
                		}else if(lock.getStatus()==4){
                			status="刷卡模式";
                		}
                %>
                <tr>
                  <td><%=lock.getSn() %></td>
                  <td><%=tt %></td>
                  <td><%=status %></td>
                  <td><%=lock.getKeyMakeTime() %></td>
                  <td><%=lock.getKeyExpireInterval() %>天</td>
                  
                  <td><%=community!=null?community.getName():"" %></td>
                  <td><%=build!=null?build.getName():"" %></td>
                  <td><%=ap!=null?ap.getName():"" %></td>
                  <td>
                  <table>
                  <tr>
                  <td> <button onclick="lockEdit('<%=lock.getId() %>')"  data-toggle="modal" data-target="#modal-edit-lock" type="button" class="btn btn-block btn-info">修改</button></td>
                  <td> <button onclick="lockDelete('<%=lock.getId() %>')" type="button" style="margin-left:10px" class="btn btn-block btn-default">删除</button></td>
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
            
            
          <div class="modal fade" id="modal-add-lock">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加门锁</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>锁SN：</td>
                	<td><input type="text" id="add_lock_sn" name="sn" class="form-control" placeholder="输入门锁序列号"></td>
                </tr>
                <tr>
                	<td>密钥更新周期：</td>
                	<td><input type="text" id="add_lock_expire"  name="expire" class="form-control" placeholder="输入多少天"></td>
                </tr>
                <tr>
                	<td>类型：</td>
                	<td> <div class="form-group">
                  <select id="add_lock_type" class="form-control">
                    <option value="card">刷卡门锁</option>
                    <option value="sncard">身份证门锁</option>
                  </select>
                </div></td>
                </tr>
                
                <tr>
                	<td>状态：</td>
                	<td> <div class="form-group">
                  <select id="add_lock_status" class="form-control">
                    <option value="1">正常运行</option>
                    <option value="2">禁止使用</option>
                    <option value="3">蓝牙模式</option>
                    <option value="4">刷卡模式</option>
                  </select>
                </div></td>
                </tr>
                
                <tr>
                	<td>小区：</td>
                	<td> <div class="form-group">
                  <select onchange="addLockCommunityChange()" id="add_lock_community" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                 <tr>
                	<td>公寓楼：</td>
                	<td> <div class="form-group">
                  <select onchange="addLockBuildChange()"  id="add_lock_build" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                <tr>
                	<td>公寓房间：</td>
                	<td> <div class="form-group">
                  <select id="add_lock_apartment" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="lockAdd()" class="btn btn-primary" data-dismiss="modal">添加门锁</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        
        
       
        
        
        <!-------------------------------edit --------------------------------------------------------->
        
        <div class="modal fade" id="modal-edit-lock">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改门锁信息</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>锁SN：</td>
                	<td><input type="text" id="edit_lock_sn" name="sn" class="form-control" placeholder="" disabled></td>
                </tr>
                <tr>
                	<td>密钥更新周期：</td>
                	<td><input type="text" id="edit_lock_expire"  name="expire" class="form-control" placeholder="输入多少天"></td>
                </tr>
                <tr>
                	<td>类型：</td>
                	<td> <div class="form-group">
                  <select id="edit_lock_type" class="form-control">
                    <option value="card">刷卡门锁</option>
                  </select>
                </div></td>
                </tr>
                <tr>
                	<td>状态：</td>
                	<td> <div class="form-group">
                  <select id="edit_lock_status" class="form-control">
                    <option value="1">正常运行</option>
                    <option value="2">禁止使用</option>
                    <option value="3">蓝牙模式</option>
                    <option value="4">刷卡模式</option>
                  </select>
                </div></td>
                </tr>
                <tr>
                	<td>更新密钥：</td>
                	<td> <div class="form-group">
                  <select id="edit_lock_key" class="form-control">
                    <option value="0">不更新密钥</option>
                    <option value="1">更新密钥</option>
                  </select>
                </div></td>
                </tr>
                
                <tr>
                	<td>小区：</td>
                	<td> <div class="form-group">
                  <select onchange="editLockCommunityChange()"  id="edit_lock_community" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                 <tr>
                	<td>公寓楼：</td>
                	<td> <div class="form-group">
                  <select onchange="editLockBuildChange()" id="edit_lock_build" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                <tr>
                	<td>公寓房间：</td>
                	<td> <div class="form-group">
                  <select id="edit_lock_apartment" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="lockEditSubmit()" class="btn btn-primary" data-dismiss="modal">修改门锁</button>
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