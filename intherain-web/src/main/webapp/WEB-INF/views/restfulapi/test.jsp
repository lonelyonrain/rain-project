<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>restfulapi</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="static/bootstrap3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="static/bootstrap3.3.7/css/bootstrap.min.css.map">
</head>
<body>
<!-- 	<h1 style="text-align:center">Hello, world! bootstrap!</h1> -->
	<!-- 标准的按钮 -->
	<!-- <button type="button" class="btn btn-default">默认按钮</button> -->
	<!-- 提供额外的视觉效果，标识一组按钮中的原始动作 -->
	<!-- <button type="button" class="btn btn-primary">原始按钮</button> -->
	<!-- 表示一个成功的或积极的动作 -->
	<!-- <button type="button" class="btn btn-success">成功按钮</button> -->
	<!-- 信息警告消息的上下文按钮 -->
	<!-- <button type="button" class="btn btn-info">信息按钮</button> -->
	<!-- 表示应谨慎采取的动作 -->
	<!-- <button type="button" class="btn btn-warning">警告按钮</button> -->
	<!-- 表示一个危险的或潜在的负面动作 -->
	<!-- <button type="button" class="btn btn-danger">危险按钮</button> -->
	<!-- 并不强调是一个按钮，看起来像一个链接，但同时保持按钮的行为 -->
	<!-- <button type="button" class="btn btn-link">链接按钮</button> -->

	<div style="width: 800px; margin-top: 10px; margin-left: auto; margin-right: auto; text-align: center;">
		<h2>restfulapi</h2>
	</div>
	<div style="width: 800px; margin-left: auto; margin-right: auto;">
		<fieldset>
			<legend>restfulapi-test表单测试</legend>
			<form class="bs-example bs-example-form" role="form">
				<div class="input-group">
					<input type="text"	class="form-control" placeholder="id">
				</div>
				<br>
				<div class="input-group">
					<input type="text" class="form-control" placeholder="姓名">
				</div>
				<br>
				<div class="input-group">
				 	<input type="text"	class="form-control" placeholder="年龄">
				</div>
				<br>
				<div class="input-group">
				 	<input type="text"	class="form-control" placeholder="备注">
				</div>
			</form>
		</fieldset>
		<br>
		<fieldset>
			<legend>基于Restful架构风格的资源请求测试</legend>
			<button id="btnGet" type="button" class="btn btn-info">获取人员GET</button>
			<button id="btnAdd" type="button" class="btn btn-info">添加人员POST</button>
			<button id="btnUpdate" type="button" class="btn btn-info">更新人员PUT</button>
			<button id="btnDel" type="button" class="btn btn-info">删除人员DELETE</button>
			<button id="btnList" type="button" class="btn btn-info">查询列表PATCH</button>
		</fieldset>
	</div>

	<script type="text/javascript"
		src="static/jquery3.2.1/jquery-3.2.1.min.js"></script>
	<script type="text/javascript"
		src="static/bootstrap3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript">  
	//
	$(function() { 
		//获取人员信息
		$("#btnGet").click(function(){
			$.ajax({
				type : "GET",
				contentType : "application/json",
				data : {
					random :new Date().getTime()
				},
				url : 'restfulapi/v1/users/user/1.do',
				dataType : 'json',
				async: true,
				success : function(data) {
					alert(data);
				},
				error: function (data){
					alert("a失败")
				}
				});
		});
	}); 
	$(function() { 
		//添加人员post
		$("#btnAdd").click(function(){
			var paramJson = JSON.stringify({
				id :"1",
				name :"1",
				age:"1",
				others:"1"
			});
			$.ajax({
				type : "POST",
//				contentType : "application/json; charset=utf-8",
				contentType : "application/x-www-form-urlencoded",
				data : {
					random :new Date().getTime(),
					paramJson :paramJson
				},
				url : 'restfulapi/v1/users/user.do',
				dataType : 'json',
				async: true,
				success : function(data) {
					alert(data);
				},
				error: function (data){
					alert("b失败")
				}
				});
		});
	}); 
	$(function() { 
		//更新人员
		$("#btnUpdate").click(function(){
			$.ajax({
				type : "PUT",
				contentType : "application/x-www-form-urlencoded",
				data : {
					random :new Date().getTime()
				},
				url : 'restfulapi/v1/users/user.do',
				dataType : 'json',
				async: true,
				success : function(data) {
					alert(data);
				},
				error: function (data){
					alert("c失败")
				}
				});
		});
	}); 
	$(function() { 
		//删除人员
		$("#btnDel").click(function(){
			$.ajax({
				type : "DELETE",
				contentType : "application/json",
				data : {
					random :new Date().getTime()
				},
				url : 'restfulapi/v1/users/user/1.do',
				dataType : 'json',
				async: true,
				success : function(data) {
					alert(data);
				},
				error: function (data){
					alert("d失败")
				}
				});
		});
	}); 
	$(function() { 
		//查询列表
		$("#btnList").click(function(){
			$.ajax({
				type : "PATCH",
				contentType : "application/json",
				data : {
					random :new Date().getTime()
				},
				url : 'restfulapi/v1/users/user.do',
				dataType : 'json',
				async: true,
				success : function(data) {
					alert(data);
				},
				error: function (data){
					alert("e失败")
				}
				});
		});
	}); 
	</script>
</body>
</html>
