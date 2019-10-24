<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统首页</title>

<!-- 引入CSS与JS文件 -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>
<script src="js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
<script src="js/default.js" type="text/javascript" charset="utf-8"></script>
<!-- 引入CSS与JS文件 -->

</head>
<body>
	<div class="jumbotron">
		<div class="container" style="color: red;">
			<h1>哎呀~~~报错啦~~~~~☺</h1>
			<p>略略略~~~~~~~可能是因为以下原因报错!</p>
			<p>${msg}</p>
			<p>
				<a onclick="javascript:window.location.href='index.jsp'" class="btn btn-primary btn-lg" role="button"><span class="glyphicon glyphicon-home"></span> 返回系统首页</a>
			</p>
		</div>
	</div>
</body>
</html>