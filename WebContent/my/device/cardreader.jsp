<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcApartmentDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcBuildingDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcCommunityDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcLockDao"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcReaderDao"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcApartment"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcBuilding"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcCommunity"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcLock"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcReader"%>
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
  var allReader;
  
  var editReaderId;
  function readerAdd(){
	  var sn=$("#add_reader_sn").val();
	  var pwd=$("#add_reader_pwd").val();
	  var type=$("#add_reader_type").val();
	  if(sn==""||pwd==""){
			alert("序列号和密码不能为空！");
			return;
		}
	  
	  var obj=new Object();
	obj.type=type;
	obj.sn=sn;
	obj.password= pwd;
		
		obj.c="addreader";
	  $.get("<%=path %>/lock",obj, function(result){
			alert("添加读卡器信息完成");
			location.reload();
		  },"json");
  }
  function readerEdit(readerId){
	  editReaderId=readerId;
	  for(var i=0;i<allReader.length;i++){
		  var reader=allReader[i];
		  if(reader.id==readerId){
			  $("#edit_reader_sn").val(reader.readerNo);
			  $("#edit_reader_pwd").val(reader.password);
			  $("#edit_reader_type").val(reader.rtype);
			  return;
		  }
	  }
  }
  function readerEditSubmit(){
	  var pwd=$("#edit_reader_pwd").val();
	  var type=$("#edit_reader_type").val();
	  if(pwd==""){
		  alert("密码不能为空");
		  return;
		}
	  
	  var obj=new Object();
		obj.type=type;
		obj.readerId=editReaderId;
		obj.password= pwd;
			
			obj.c="editreader";
		  $.get("<%=path %>/lock",obj, function(result){
				alert("添加读卡器信息完成");
				location.reload();
			  },"json");
  }
  function readerDelete(readerId){
	  $.get("<%=path %>/lock",{c:"deletereader",readerId:readerId}, function(result){
			alert("已删除！");
			location.reload();
		  },"json");
  }
  
  function footer(){
	  
	  
	  $('#menu_reader').addClass("active");
	  $('#menu_f_device').addClass("active");
	  $('#example1').DataTable({
	      'paging'      : true,
	      'lengthChange': false,
	      'searching'   : false,
	      'ordering'    : true,
	      'info'        : true,
	      'autoWidth'   : false
	    });
	   

	  
	  
	  
	  $.get("<%=path %>/lock",{c:"getallreader"}, function(result){
		  allReader=result;
			
		  },"json");
	    
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
        <small>读卡器管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
        <li><a href="#">设备管理</a></li>
        <li class="active">读卡器管理</li>
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
                  <button type="button"   data-toggle="modal" data-target="#modal-add-reader" class="btn btn-block btn-info">添加读卡器</button>
                  
                </div>
                
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example1" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>刷卡器序列号</th>
                  <th>类型</th>
                  <th>密码</th>
                  <th  width="100px">操作</th>
                </tr>
                </thead>
                
                <tbody>
                <%
                List<LcReader> readers=new LcReaderDao().findByOwnerId(user.getId()); 
                if(readers!=null&&readers.size()>0){
                	for(LcReader reader:readers){
                		String tt="";
                		if("normal".equals(reader.getRtype())){
                			tt="普通刷卡器";
                		}
                %>
                <tr>
                  <td><%=reader.getReaderNo() %></td>
                  <td><%=tt %></td>
                  <td><%=reader.getPassword() %></td>
                  
                  <td>
                  <table>
                  <tr>
                  <td> <button onclick="readerEdit('<%=reader.getId() %>')"  data-toggle="modal" data-target="#modal-edit-reader" type="button" class="btn btn-block btn-info">修改</button></td>
                  <td> <button onclick="readerDelete('<%=reader.getId() %>')" type="button" style="margin-left:10px" class="btn btn-block btn-default">删除</button></td>
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
            
            
          <div class="modal fade" id="modal-add-reader">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加读卡器</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>读卡器SN：</td>
                	<td><input type="text" id="add_reader_sn" name="sn" class="form-control" placeholder="输入读卡器序列号"></td>
                </tr>
                <tr>
                	<td>读卡器密码：</td>
                	<td><input type="text" id="add_reader_pwd"  name="pwd" class="form-control" placeholder="输入密码"></td>
                </tr>
                <tr>
                	<td>类型：</td>
                	<td> <div class="form-group">
                  <select id="add_reader_type" class="form-control">
                    <option value="normal">普通刷卡器</option>
                  </select>
                </div></td>
                </tr>
                
                
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="readerAdd()" class="btn btn-primary" data-dismiss="modal">添加读卡器</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        
        
       
        
        
        <!-------------------------------edit --------------------------------------------------------->
        
        <div class="modal fade" id="modal-edit-reader">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改读卡器信息</h4>
              </div>
              <div class="modal-body">
                <table  style="border-collapse:separate; border-spacing:0px 10px;width:500px">
                <tr>
                	<td>读卡器SN：</td>
                	<td><input type="text" id="edit_reader_sn" name="sn" class="form-control" placeholder="" disabled></td>
                </tr>
                <tr>
                	<td>读卡器密码：</td>
                	<td><input type="text" id="edit_reader_pwd"  name="expire" class="form-control" placeholder="输入密码"></td>
                </tr>
                <tr>
                	<td>类型：</td>
                	<td> <div class="form-group">
                  <select id="edit_reader_type" class="form-control">
                    <option value="normal">普通刷卡器</option>
                  </select>
                </div></td>
                </tr>
                
                
                </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" onclick="readerEditSubmit()" class="btn btn-primary" data-dismiss="modal">修改读卡器</button>
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