<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.islora.lock.wx.server.db.dao.LcUserDao"%>
<%@page import="com.islora.lock.wx.server.db.entity.LcUser"%>
<% 
String path=request.getContextPath();

LcUser user=(LcUser)request.getSession().getAttribute("user");
if(user==null){
	return;
}
%>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="<%=path%>/ad/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p><%=user.getUsername() %></p>
          <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
        </div>
      </div>
      <!-- search form -->
      <!-- form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
          <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
        </div>
      </form-->
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header">功能列表</li>
       
        <li id="menu_f_build" class="treeview">
          <a href="#">
            <i class="fa fa-folder"></i> <span>公寓管理</span>
            <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
          </a>
          <ul class="treeview-menu">
            <li id="menu_community"><a href="<%=path%>/my/build/community.jsp"><i class="fa fa-building"></i>房产管理</a></li>
            
            
          </ul>
        </li>
      	<li id="menu_f_device" class="treeview">
          <a href="#">
            <i class="fa fa-folder"></i> <span>设备管理</span>
            <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
          </a>
          <ul class="treeview-menu">
            <li id="menu_lock" ><a href="<%=path%>/my/device/lock.jsp"><i class="fa fa-lock"></i>门锁管理</a></li>
            <li id="menu_lock_status" ><a href="<%=path%>/my/device/lockstatus.jsp"><i class="fa fa-lock"></i>门锁状态</a></li>
            <li id="menu_reader"><a href="<%=path%>/my/device/cardreader.jsp"><i class="fa  fa-credit-card"></i>发卡器管理</a></li>
            <!-- li id="menu_reader_status"><a href="<%=path%>/my/device/cardreader.jsp"><i class="fa fa-credit-card"></i>发卡器状态</a></li-->
          </ul>
        </li>
        <li id="menu_f_auth" class="treeview">
          <a href="#">
            <i class="fa fa-folder"></i> <span>授权管理</span>
            <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
          </a>
          <ul class="treeview-menu">
            <li id="menu_app"><a href="<%=path%>/my/auth/app.jsp"><i class="fa fa-weixin"></i>应用授权</a></li>
            <li id="menu_card"><a href="<%=path%>/my/auth/card.jsp"><i class="fa fa-hand-paper-o"></i>发卡授权</a></li>
            <li id="menu_pwd"><a href="<%=path%>/my/auth/pwd.jsp"><i class="fa fa-hand-paper-o"></i>密码授权</a></li>
            <li id="menu_finger"><a href="<%=path%>/my/auth/finger.jsp"><i class="fa fa-hand-paper-o"></i>指纹授权</a></li>
            <!-- li id="menu_client"><a href="<%=path%>/my/auth/client.jsp"><i class="fa fa-user"></i>我的客户</a></li-->
          </ul>
        </li>
        <li id="menu_f_log" class="treeview">
          <a href="#">
            <i class="fa fa-folder"></i> <span>日志查看</span>
            <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
          </a>
          <ul class="treeview-menu">
            <!-- li id="menu_log_lora"><a href="<%=path%>/my/log/lora.jsp"><i class="fa fa-file-text"></i>连接日志</a></li-->
            <li id="menu_log_card"><a href="<%=path%>/my/log/card.jsp"><i class="fa fa-file-text"></i>刷卡日志</a></li>
            <li id="menu_log_ble"><a href="<%=path%>/my/log/ble.jsp"><i class="fa fa-file-text"></i>应用日志</a></li>
            <li id="menu_log_pwd"><a href="<%=path%>/my/log/pwd.jsp"><i class="fa fa-file-text"></i>密码日志</a></li>
            <li id="menu_log_finger"><a href="<%=path%>/my/log/finger.jsp"><i class="fa fa-file-text"></i>指纹日志</a></li>
          </ul>
        </li>
        
        <li class="header">事件通知</li>
        <li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>重要事件</span></a></li>
        <li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>警告事件</span></a></li>
        <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>通知</span></a></li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>