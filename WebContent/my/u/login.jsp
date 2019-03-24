<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String path=request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<link href="<%=path %>/my/img/lock.ico" rel="shortcut icon">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>islocking | Log in</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="<%=path %>/ad/bower_components/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=path %>/ad/bower_components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="<%=path %>/ad/bower_components/Ionicons/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=path %>/ad/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="<%=path %>/ad/plugins/iCheck/square/blue.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Google Font -->
  <link rel="stylesheet" href="<%=path %>/ad/googlefont.css">
  <script type="text/javascript">
  	function login(){
  		var user=$("#txt_user").val();
  		var pwd=$("#txt_pwd").val();
  		if(user==""){
  			$("#info_content").html("用户名不能为空！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		if(pwd==""){
  			$("#info_content").html("密码不能为空！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		
		var obj=new Object();
		obj.password=pwd;
		obj.username=user;
		obj.c="login";
		$.get("<%=path %>/user",obj, function(result){
			var r=result.result
			if("error_nouser"==r){
				$("#info_content").html("没有此用户，请先注册！");
	  			$("#info_div").removeClass("hidden");
	  			return;
			}
			if("error_pwd"==r){
				$("#info_content").html("用户名密码错误！");
	  			$("#info_div").removeClass("hidden");
	  			return;
			}
			window.location.href='<%=path %>/my/build/community.jsp';
		  },"json");
  	}
  </script>
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="<%=path %>/ad/index2.html">is<b>LOCKING</b></a>
  </div>
  
  <div id="info_div" class="alert alert-danger alert-dismissible hidden">
                <h4><i class="icon fa fa-ban"></i><span>无法注册用户</span></h4>
                <span id="info_content">失败 </span>
</div>

  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">登陆您的管理系统</p>


      <div class="form-group has-feedback">
        <input id="txt_user" type="text" class="form-control" placeholder="用户名">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="txt_pwd" type="password" class="form-control" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <!--  input type="checkbox"> 记住我-->
              
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button onclick="login()" class="btn btn-primary btn-block btn-flat">登陆系统</button>
        </div>
        <!-- /.col -->
      </div>


    <!-- /.social-auth-links -->

    <a href="#">密码找回</a><br>
    <a href="register.jsp" class="text-center">注册用户</a>

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 3 -->
<script src="<%=path %>/ad/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<%=path %>/ad/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="<%=path %>/ad/plugins/iCheck/icheck.min.js"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' /* optional */
    });
  });
</script>
</body>
</html>
