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
  <title>islocking | Registration</title>
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
  	function isEmail(strEmail) {
  		if(strEmail==""){
  			return false;
  		}
	  if (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1){
		  return true;
	  }
	  else{
		  return false;
	  }
	}
  	function isTel(Tel){
  		if(Tel==""){
  			return false;
  		}
        var re=new RegExp(/^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/);
        var retu=Tel.match(re);
        if(retu){
            return true;
        }else{
            return false;
        }
    }
  	function register(){
  		$("#success_div").addClass("hidden");
  		var check=$("#check_agree").get(0).checked;
  		var user=$("#txt_user").val();
  		var pwd=$("#txt_pwd").val();
  		var repwd=$("#txt_repwd").val();
  		var email=$("#txt_email").val();
  		var phone=$("#txt_phone").val();
  		var name=$("#txt_name").val();
  		var id=$("#txt_id").val();
  		var addr=$("#txt_addr").val();
  		if(!(check)){
  			$("#info_content").html("需要同意系统协议！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		if(user==""||user.length<4){
  			$("#info_content").html("用户名不能为空或小于4个字符！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		if(pwd==""||pwd.length<4){
  			$("#info_content").html("密码不能为空或小于6个字符！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		if(pwd!=repwd){
  			$("#info_content").html("两次密码输入不一致！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		if(!isEmail(email)){
  			$("#info_content").html("email格式不对！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		if(!isTel(phone)){
  			$("#info_content").html("手机号码格式不对！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		if(name==""){
  			$("#info_content").html("姓名不能为空！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		if(id==""){
  			$("#info_content").html("身份证不能为空！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		if(addr==""){
  			$("#info_content").html("用户地址不能为空！");
  			$("#info_div").removeClass("hidden");
  			return;
  		}
  		
  		//$("#info_content").html("用户地址不能为空！");
		
		
		var obj=new Object();
		
		
		obj.phone=phone;
		obj.password=pwd;
		obj.address=addr;
		obj.email=email;
		obj.idsn=id;
		obj.name=name;
		obj.type="common";
		obj.username=user;
		obj.c="register";
		$.get("<%=path %>/user",obj, function(result){
			var r=result.result
			if("error_username"==r){
				$("#info_content").html("用户名重复注册！");
	  			$("#info_div").removeClass("hidden");
	  			return;
			}
			if("error_email"==r){
				$("#info_content").html("email重复注册！");
	  			$("#info_div").removeClass("hidden");
	  			return;
			}
			if("error_phone"==r){
				$("#info_content").html("手机号重复注册！");
	  			$("#info_div").removeClass("hidden");
	  			return;
			}
			if("error_id"==r){
				$("#info_content").html("身份证号重复注册！");
	  			$("#info_div").removeClass("hidden");
	  			return;
			}
			$("#info_div").addClass("hidden");
			$("#success_div").removeClass("hidden");
			setTimeout("window.location.href='login.jsp';",2000);
		  },"json");
  	}
  </script>
</head>
<body class="hold-transition register-page">



<div class="register-box">
  <div class="register-logo">
    <a href="<%=path %>/ad/index2.html">is<b>LOCKING</b></a>
  </div>
<div id="info_div" class="alert alert-danger alert-dismissible hidden">
                <h4><i class="icon fa fa-ban"></i><span>无法注册用户</span></h4>
                <span id="info_content">失败 </span>
</div>
<div id="success_div" class="alert alert-success alert-dismissible hidden">
                <h4><i class="icon fa fa-check"></i>注册成功</h4>
                即将跳转登录页面！
              </div>

  <div class="register-box-body">
    <p class="login-box-msg">注册用户</p>

    <!--  form action="<%=path %>/ad/index.html" method="post"-->
      <div class="form-group has-feedback">
        <input id="txt_user" type="text" class="form-control" placeholder="用户名">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="txt_pwd" type="password" class="form-control" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="txt_repwd" type="password" class="form-control" placeholder="重新输入密码">
        <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="txt_email" type="text" class="form-control" placeholder="Email">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="txt_phone" type="text" class="form-control" placeholder="电话号码">
        <span class="glyphicon glyphicon-phone form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="txt_name" type="text" class="form-control" placeholder="姓名">
        <span class="glyphicon glyphicon-file form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="txt_id" type="text" class="form-control" placeholder="身份证号码">
        <span class="glyphicon glyphicon-info-sign form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="txt_addr" type="text" class="form-control" placeholder="地址">
        <span class="glyphicon glyphicon-home form-control-feedback"></span>
      </div>
      
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input id="check_agree" type="checkbox"> 我同意 <a href="#">条款</a>
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button onclick="register()" class="btn btn-primary btn-block btn-flat">注册</button>
        </div>
        <!-- /.col -->
      </div>
    <!-- /form-->

    

    <a href="login.jsp" class="text-center">我已经是用户</a>
  </div>
  
  <!-- /.form-box -->
</div>


<!-- /.register-box -->

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
