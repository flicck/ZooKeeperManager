<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改节点值</title>

<!-- 引入CSS与JS文件 -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>
<script src="js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
<script src="js/default.js" type="text/javascript" charset="utf-8"></script>
<!-- 引入CSS与JS文件 -->
<%
	request.getParameter("id");

%>
<script type="text/javascript">
$(function(){
	//var url=window.location.href;
	//var urls=url.split("=");
	//var urlss=urls[1].split("&");
	//var id=urlss[0];
	//var page=urls[2];
	
	//$.post("${pageContext.request.contextPath}/findByBid",{"bid":id},function(data1){
	//	$("#name").val(data1.bname);
	//	$("#author").val(data1.bname);
	//	alert(data1.bcreatedate);
	//	$("#time").val(data.bcreatedate);
	//	$("#price").val(data.bprice);
	})
})



</script>
</head>
<body>
	<div>
		<h1>修改节点值</h1>
		
		<br />
		
		
		<form action="updateC" method="post">
			<input type="hidden" name="absolutePath" value="${absolutePath}" />
			<input type="hidden" name="page" value="${page}" />
			<fieldset>
				<legend>修改节点值</legend>
				<table class="table table-hover">
					<tr class="default">
							<td>节点值 : </td>
							<td>
								<input id="nodeValue" name="nodeValue" value="${nodeInfo.nodeValue }" type="text" required="required" class="form-control" />
							</td>
					</tr>
					
					<tr>
						<th colspan="2">
							<button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> 确定修改</button>
							<button type="reset" class="btn btn-danger" onclick="javascript:history.go(-1);"><span class="glyphicon glyphicon-remove"></span> 放弃修改</button>
						</th>
					</tr>
				</table>
			</fieldset>
		</form>
		
		
	</div>
</body>
</html>