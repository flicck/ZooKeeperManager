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
<script type="text/javascript">
function gofindlist(){
	location.href="${pageContext.request.contextPath}/list.jsp";
}
function goAdd(){
	location.href="${pageContext.request.contextPath}/add.jsp";
}
</script>
</head>
<body class="jumbotron">
	<div >
		<div class="container">
			<h1>欢迎使用zookeeper管理系统！</h1>
			<p>版本:1.1.0 开发者:HanWang1995 <span class="glyphicon glyphicon-qrcode"></span></p>
			<p>
				<a onclick="javascript:gofindlist();" class="btn btn-primary btn-lg" role="button"><span class="glyphicon glyphicon-search"></span>进入</a>
			</p>
		
		</div>
	</div>
</body>
</html>