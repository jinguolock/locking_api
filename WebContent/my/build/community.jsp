<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcApartmentDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcBuildingDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcCommunityDao"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcApartment"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcBuilding"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcCommunity"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="../header.jsp"%>
  <script type="text/javascript">
  var allCommunity;
  var allBuild;
  var allApartment;
  var editCommunityId;
  var editBuildId;
  var editApartmentId;
  function communityAdd(){
	  var name=$("#add_community_name").val();
	  var address=$("#add_community_address").val();
	  if(name==""||address==""){
			alert("名称和地址不能为空！");
			return;
		}
	  var obj=new Object();
		obj.lat="0";
		obj.lon="0";
		obj.type= $("#add_community_type").val();
		obj.name=name;
		obj.address=address;
		
		obj.c="addcommunity";
	  $.get("<%=path %>/community",obj, function(result){
			alert("添加小区信息完成");
			location.reload();
		  },"json");
  }
  function communityEdit(communityId){
	  editCommunityId=communityId;
	  for(var i=0;i<allCommunity.length;i++){
		  var comm=allCommunity[i];
		  if(comm.id==communityId){
			  $("#edit_community_name").val(comm.name);
			  $("#edit_community_address").val(comm.address);
			  $("#edit_community_type").val(comm.ctype);
			  return;
		  }
	  }
  }
  function editCommunitySubmit(){
	  var name=$("#edit_community_name").val();
	  var address=$("#edit_community_address").val();
	  if(name==""||address==""){
			alert("名称和地址不能为空！");
			return;
		}
	  var obj=new Object();
	  	obj.communityId=editCommunityId;
		obj.lat="0";
		obj.lon="0";
		obj.type= $("#edit_community_type").val();
		obj.name=name;
		obj.address=address;
		
		obj.c="editcommunity";
	  $.get("<%=path %>/community",obj, function(result){
			alert("修改小区信息完成");
			location.reload();
		  },"json");
  }
  function communityDelete(communityId){
	  $.get("<%=path %>/community",{c:"deletecommunity",communityId:communityId}, function(result){
			alert("已删除！");
			location.reload();
		  },"json");
  }
  function buildAdd(){
	  var name=$("#add_build_name").val();
	  var desc=$("#add_build_desc").val();
	  var floor=$("#add_build_floor").val();
	  var unit=$("#add_build_unit").val();
	  var comm=$("#add_build_community").val();
	  if(name==""){
		  alert("名称不能为空！");
		  return;
	  }
	  if(isNaN(floor)||isNaN(unit)){
		  alert("楼层和单元数需为数字！");
		  return;
	  }
	  var obj=new Object();
		obj.desc=desc;
		obj.floorNumber=floor;
		obj.unitNumber=unit;
		obj.name=name;
		obj.communityId=comm;
		
		obj.c="addbuilding";
	  $.get("<%=path %>/community",obj, function(result){
			alert("添加公寓楼完成!");
			location.reload();
		  },"json");
  }
  function buildEdit(buildId){
	  editBuildId=buildId;
	  for(var i=0;i<allBuild.length;i++){
		  var build=allBuild[i];
		  if(build.id==buildId){
			  $("#edit_build_name").val(build.name);
			  $("#edit_build_desc").val(build.description);
			  $("#edit_build_floor").val(build.floorNumber);
			  $("#edit_build_unit").val(build.uintNumber);
			  $("#edit_build_community").val(build.communityId);
			  return;
		  }
	  }
  }
  function buildEditSubmit(){
	  var name=$("#edit_build_name").val();
	  var desc=$("#edit_build_desc").val();
	  var floor=$("#edit_build_floor").val();
	  var unit=$("#edit_build_unit").val();
	  var comm=$("#edit_build_community").val();
	  if(name==""){
		  alert("名称不能为空！");
		  return;
	  }
	  if(isNaN(floor)||isNaN(unit)){
		  alert("楼层和单元数需为数字！");
		  return;
	  }
	  var obj=new Object();
		obj.desc=desc;
		obj.floorNumber=floor;
		obj.unitNumber=unit;
		obj.name=name;
		obj.communityId=comm;
		obj.buildingId=editBuildId;
		
		obj.c="editbuilding";
	  $.get("<%=path %>/community",obj, function(result){
			alert("修改公寓楼完成!");
			location.reload();
		  },"json");
  }
  function buildDelete(buildId){
	  $.get("<%=path %>/community",{c:"deletebuilding",buildingId:buildId}, function(result){
			alert("已删除！");
			location.reload();
		  },"json");
  }
  function apartmentAdd(){
	  var name=$("#add_apartment_name").val();
	  var desc=$("#add_apartment_desc").val();
	  var floor=$("#add_apartment_floor").val();
	  var unit=$("#add_apartment_unit").val();
	  var build=$("#add_apartment_build").val();
	  
	  if(name==""){
		  alert("名称不能为空！");
		  return;
	  }
	  if(isNaN(floor)||isNaN(unit)){
		  alert("楼层和单元数需为数字！");
		  return;
	  }
	  var obj=new Object();
		obj.desc=desc;
		obj.floor=floor;
		obj.uint=unit;
		obj.name=name;
		obj.buildingId=build;
		
		obj.c="addapartment";
	  $.get("<%=path %>/community",obj, function(result){
			alert("添加公寓房间完成!");
			location.reload();
		  },"json");
  }
  function apartmentEdit(apartmentId){
	  editApartmentId=apartmentId;
	
	  
	  for(var i=0;i<allApartment.length;i++){
		  var apartment=allApartment[i];
		  if(apartment.id==apartmentId){
			  $("#edit_apartment_name").val(apartment.name);
			  $("#edit_apartment_desc").val(apartment.description);
			  $("#edit_apartment_floor").val(apartment.floor);
			  $("#edit_apartment_unit").val(apartment.uint);
			  var commid;
			  for(var j=0;j<allBuild.length;j++){
				  var build=allBuild[j];
				  if(build.id==apartment.buildingId){
					  $("#edit_apartment_community").val(build.communityId);
					  commid=build.communityId;
					  break;
				  }
			  }
			  if(commid!=null){
				  editApartmentBuildFill();
			  }
			  $("#edit_apartment_build").val(apartment.buildingId);
			  return;
		  }
	  }
  }
  function apartmentEditSubmit(){
	  var name=$("#edit_apartment_name").val();
	  var desc=$("#edit_apartment_desc").val();
	  var floor=$("#edit_apartment_floor").val();
	  var unit=$("#edit_apartment_unit").val();
	  var build=$("#edit_apartment_build").val();
	  
	  if(name==""){
		  alert("名称不能为空！");
		  return;
	  }
	  if(isNaN(floor)||isNaN(unit)){
		  alert("楼层和单元数需为数字！");
		  return;
	  }
	  var obj=new Object();
		obj.desc=desc;
		obj.floor=floor;
		obj.uint=unit;
		obj.name=name;
		obj.buildingId=build;
		obj.apartmentId=editApartmentId;
		obj.c="editapartment";
	  $.get("<%=path %>/community",obj, function(result){
			alert("修改公寓房间完成!");
			location.reload();
		  },"json");
  }
  function apartmentDelete(apartmentId){
	  $.get("<%=path %>/community",{c:"deleteapartment",apartmentId:apartmentId}, function(result){
			alert("已删除！");
			location.reload();
		  },"json");
  }
  function footer(){
	  $('#menu_community').addClass("active");
	  $('#menu_f_build').addClass("active");
	  
	  $('#example1').DataTable({
	      'paging'      : true,
	      'lengthChange': false,
	      'searching'   : false,
	      'ordering'    : true,
	      'info'        : true,
	      'autoWidth'   : false
	    });
	    $('#example2').DataTable({
	      'paging'      : true,
	      'lengthChange': false,
	      'searching'   : false,
	      'ordering'    : true,
	      'info'        : true,
	      'autoWidth'   : false
	    });
	     $('#example3').DataTable({
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
						$('#add_build_community').append("<option value='"+com.id+"'>"+com.name+"</option>"); 
						$('#add_apartment_community').append("<option value='"+com.id+"'>"+com.name+"</option>"); 
						$('#edit_build_community').append("<option value='"+com.id+"'>"+com.name+"</option>"); 
						$('#edit_apartment_community').append("<option value='"+com.id+"'>"+com.name+"</option>"); 
					}
					if(allBuild!=null&&allBuild.length>0){
						var co=allCommunity[0];
						for(var i=0;i<allBuild.length;i++){
							var bu=allBuild[i];
							bu.name=decodeURI(bu.name);
							bu.description=decodeURI(bu.description);
							//var v=bu.communityId+"";
							if(bu.communityId==co.id){
								$('#add_apartment_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
							}
							
						}
					}
				}
				if(allApartment!=null&&allApartment.length>0){
					for(var i=0;i<allApartment.length;i++){
						var bu=allApartment[i];
						bu.name=decodeURI(bu.name);
						bu.description=decodeURI(bu.description);
					}
				}
				/* if(allBuild!=null&&allBuild.length>0){
					for(var i=0;i<allBuild.length;i++){
						var bu=allBuild[i];
						$('#add_apartment_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
					}
				} */
			  },"json");
	    //设置value为pxx的项选中
	   // $(".selector").val("pxx");
  }
  function addApartmentBuildFill(){
	  if(allBuild!=null&&allBuild.length>0){
		  $('#add_apartment_build').empty();
		  var communityId=$("#add_apartment_community").val();
		  for(var i=0;i<allBuild.length;i++){
				var bu=allBuild[i];
				var v=bu.communityId;
				if(v==communityId){
					$('#add_apartment_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
				}
				
			}
	  }
	  
  }
  function editApartmentBuildFill(){
	  if(allBuild!=null&&allBuild.length>0){
		  $('#edit_apartment_build').empty();
		  var communityId=$("#edit_apartment_community").val();
		  for(var i=0;i<allBuild.length;i++){
				var bu=allBuild[i];
				var v=bu.communityId;
				if(v==communityId){
					$('#edit_apartment_build').append("<option value='"+bu.id+"'>"+bu.name+"</option>"); 
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
        	公寓管理
        <small>房屋管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
        <li><a href="#">公寓管理</a></li>
        <li class="active">房产管理</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title"><%=user.getUsername() %>的小区列表</h3>
              <div class="box-tools">
              <div class="btn-group">
                  <button type="button"   data-toggle="modal" data-target="#modal-add-community" class="btn btn-block btn-info">添加小区</button>
                  
                </div>
                
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example1" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>名称</th>
                  <th>地址</th>
                  <th>类型</th>
                  <th  width="100px">操作</th>
                </tr>
                </thead>
                
                <tbody>
                <%
                List<LcCommunity> communitys=new LcCommunityDao().findByOwnerId(user.getId()); 
                Map<Integer,LcCommunity> communityMap=new HashMap<Integer,LcCommunity>();
                if(communitys!=null&&communitys.size()>0){
                	for(LcCommunity com:communitys){
                		communityMap.put(com.getId(), com);
                		String tt="普通公寓";
                		if("common".equals(com.getCtype())){
                			tt="普通公寓";
                		}
                %>
                <tr>
                  <td><%=com.getName() %></td>
                  <td><%=com.getAddress() %></td>
                  <td><%=tt %></td>
                  <td>
                  <table>
                  <tr>
                  <td> <button onclick="communityEdit('<%=com.getId() %>')"  data-toggle="modal" data-target="#modal-edit-community" type="button" class="btn btn-block btn-info">修改</button></td>
                  <td> <button onclick="communityDelete('<%=com.getId() %>')" type="button" style="margin-left:10px" class="btn btn-block btn-default">删除</button></td>
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
          <!-- /.box -->
			 <div class="box">
            <div class="box-header">
              <h3 class="box-title"><%=user.getUsername() %>的公寓楼列表</h3>
              <div class="box-tools">
              <div class="btn-group">
                  <button type="button" data-toggle="modal"  data-target="#modal-add-build"   class="btn btn-block btn-info">添加公寓</button>
                </div>
                
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>名称</th>
                  <th>描述</th>
                  <th>小区</th>
                  <th>楼层数</th>
                  <th>单元数</th>
                  <th width="100px">操作</th>
                </tr>
                </thead>
                <tbody>
                <%
                List<LcBuilding> builds=new LcBuildingDao().findByOwnerId(user.getId()); 
                Map<Integer,LcBuilding> buildMap=new HashMap<Integer,LcBuilding>();
                if(builds!=null&&builds.size()>0){
                	for(LcBuilding build:builds){
                		LcCommunity com=communityMap.get(build.getCommunityId());
                		buildMap.put(build.getId(),build);
                %>
                <tr>
                  <td><%=build.getName() %></td>
                  <td><%=build.getDescription() %></td>
                  <td><%=com.getName() %></td>
                  <td><%=build.getFloorNumber() %></td>
                  <td><%=build.getUintNumber()%></td>
                  <td>
                  <table>
                  <tr>
                  <td> <button onclick="buildEdit('<%=build.getId() %>')" data-toggle="modal"  data-target="#modal-edit-build" type="button" class="btn btn-block btn-info">修改</button></td>
                  <td> <button onclick="buildDelete('<%=build.getId() %>')" type="button" style="margin-left:10px" class="btn btn-block btn-default">删除</button></td>
                  </tr>
                  </table>
					</td>
                </tr>
                <%}} %>
                
                </tbody>
                
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <div class="box">
            <div class="box-header">
              <h3 class="box-title"><%=user.getUsername() %>的房间列表</h3>
              <div class="box-tools">
              <div class="btn-group">
                  <button type="button" data-toggle="modal"  data-target="#modal-add-apartment"   class="btn btn-block btn-info">添加房间</button>
                </div>
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example3" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>名称</th>
                  <th>描述</th>
                  <th>小区</th>
                  <th>楼名</th>
                  <th>楼层</th>
                  <th>单元</th>
                  <th width="100px">操作</th>
                </tr>
                </thead>
                <tbody>
                <%
                List<LcApartment> apartments=new LcApartmentDao().findByOwnerId(user.getId()); 
                if(apartments!=null&&apartments.size()>0){
                	for(LcApartment apartment:apartments){
                		LcBuilding build=buildMap.get(apartment.getBuildingId());
                		LcCommunity com=communityMap.get(build.getCommunityId());
                		
                %>
                <tr>
                  <td><%=apartment.getName() %></td>
                  <td><%=apartment.getDescription() %></td>
                  <td><%=com.getName() %></td>
                  <td><%=build.getName() %></td>
                  <td><%=apartment.getFloor()%></td>
                  <td><%=apartment.getUint()%></td>
                  <td><table>
                  <tr>
                  <td> <button onclick="apartmentEdit('<%=apartment.getId() %>')" data-toggle="modal"  data-target="#modal-edit-apartment"  type="button" class="btn btn-block btn-info">修改</button></td>
                  <td> <button onclick="apartmentDelete('<%=apartment.getId() %>')"  type="button" style="margin-left:10px" class="btn btn-block btn-default">删除</button></td>
                  </tr>
                  </table></td>
                </tr>
                <%}} %>
               
                
                </tbody>
                
              </table>
            </div>
            
          <div class="modal fade" id="modal-add-community">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加小区</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>名称：</td>
                	<td><input type="text" id="add_community_name" name="name" class="form-control" placeholder="输入小区名称"></td>
                </tr>
                <tr>
                	<td>地址：</td>
                	<td><input type="text" id="add_community_address"  name="address" class="form-control" placeholder="输入小区地址"></td>
                </tr>
                <tr>
                	<td>类型：</td>
                	<td> <div class="form-group">
                  <select id="add_community_type" class="form-control">
                    <option value="common">普通公寓</option>
                  </select>
                </div></td>
                </tr>
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="communityAdd()" class="btn btn-primary" data-dismiss="modal">添加小区</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        
        
        <div class="modal fade" id="modal-add-build">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加公寓楼</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>名称：</td>
                	<td><input type="text" id="add_build_name" name="name" class="form-control" placeholder="输入公寓楼名称"></td>
                </tr>
                <tr>
                	<td>描述：</td>
                	<td><input type="text"  id="add_build_desc" name="desc" class="form-control" placeholder="输入公寓描述"></td>
                </tr>
                <tr>
                	<td>小区：</td>
                	<td> <div class="form-group">
                  <select  id="add_build_community" class="form-control">
                    
                  </select>
                </div></td>
                </tr>
                
                <tr>
                	<td>总楼层：</td>
                	<td><input  id="add_build_floor" type="text" name="floor" class="form-control" placeholder="输入总楼层数字"></td>
                </tr>
                <tr>
                	<td>单元数：</td>
                	<td><input  id="add_build_unit" type="text" name="unit" class="form-control" placeholder="输入单元数"></td>
                </tr>
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="buildAdd()" class="btn btn-primary" data-dismiss="modal">添加单元楼</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        
        
        <div class="modal fade" id="modal-add-apartment">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加公寓房间</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>名称：</td>
                	<td><input  id="add_apartment_name" type="text" name="name" class="form-control" placeholder="输入房间名称"></td>
                </tr>
                <tr>
                	<td>描述：</td>
                	<td><input id="add_apartment_desc" type="text" name="desc" class="form-control" placeholder="输入房间描述"></td>
                </tr>
                <tr>
                	<td>小区：</td>
                	<td> <div class="form-group">
                  <select onchange="addApartmentBuildFill()" id="add_apartment_community" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                 <tr>
                	<td>公寓楼：</td>
                	<td> <div class="form-group">
                  <select id="add_apartment_build" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                <tr>
                	<td>楼层：</td>
                	<td><input id="add_apartment_floor" type="text" name="floor" class="form-control" placeholder="输入楼层数字"></td>
                </tr>
                <tr>
                	<td>单元：</td>
                	<td><input id="add_apartment_unit" type="text" name="unit" class="form-control" placeholder="输入单元数字"></td>
                </tr>
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="apartmentAdd()" class="btn btn-primary" data-dismiss="modal">添加公寓房间</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        
        
        <!-------------------------------edit --------------------------------------------------------->
        
        <div class="modal fade" id="modal-edit-community">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑小区信息</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>名称：</td>
                	<td><input type="text" id="edit_community_name" name="name" class="form-control" placeholder="输入小区名称"></td>
                </tr>
                <tr>
                	<td>地址：</td>
                	<td><input type="text" id="edit_community_address"  name="address" class="form-control" placeholder="输入小区地址"></td>
                </tr>
                <tr>
                	<td>类型：</td>
                	<td> <div class="form-group">
                  <select id="edit_community_type" class="form-control">
                    <option value="common">普通公寓</option>
                  </select>
                </div></td>
                </tr>
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="editCommunitySubmit()" class="btn btn-primary" data-dismiss="modal">修改小区</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        
        
        <div class="modal fade" id="modal-edit-build">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改公寓楼信息</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>名称：</td>
                	<td><input type="text" id="edit_build_name" name="name" class="form-control" placeholder="输入公寓楼名称"></td>
                </tr>
                <tr>
                	<td>描述：</td>
                	<td><input type="text"  id="edit_build_desc" name="desc" class="form-control" placeholder="输入公寓描述"></td>
                </tr>
                <tr>
                	<td>小区：</td>
                	<td> <div class="form-group">
                  <select  id="edit_build_community" class="form-control">
                    
                  </select>
                </div></td>
                </tr>
                
                <tr>
                	<td>总楼层：</td>
                	<td><input  id="edit_build_floor" type="text" name="floor" class="form-control" placeholder="输入总楼层数字"></td>
                </tr>
                <tr>
                	<td>单元数：</td>
                	<td><input  id="edit_build_unit" type="text" name="unit" class="form-control" placeholder="输入单元数"></td>
                </tr>
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="buildEditSubmit()" class="btn btn-primary" data-dismiss="modal">修改单元楼</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        
        
        <div class="modal fade" id="modal-edit-apartment">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改公寓房间信息</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>名称：</td>
                	<td><input  id="edit_apartment_name" type="text" name="name" class="form-control" placeholder="输入房间名称"></td>
                </tr>
                <tr>
                	<td>描述：</td>
                	<td><input id="edit_apartment_desc" type="text" name="desc" class="form-control" placeholder="输入房间描述"></td>
                </tr>
                <tr>
                	<td>小区：</td>
                	<td> <div class="form-group">
                  <select onchange="editApartmentBuildFill()" id="edit_apartment_community" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                 <tr>
                	<td>公寓楼：</td>
                	<td> <div class="form-group">
                  <select id="edit_apartment_build" class="form-control">
                   
                  </select>
                </div></td>
                </tr>
                <tr>
                	<td>楼层：</td>
                	<td><input id="edit_apartment_floor" type="text" name="floor" class="form-control" placeholder="输入楼层数字"></td>
                </tr>
                <tr>
                	<td>单元：</td>
                	<td><input id="edit_apartment_unit" type="text" name="unit" class="form-control" placeholder="输入单元数字"></td>
                </tr>
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="apartmentEditSubmit()" class="btn btn-primary" data-dismiss="modal">修改公寓房间</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        
        
        
        
        <!-- /.modal -->
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>

 <jsp:include page="../foot.jsp" />