<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>zookeeper展示</title>
<!-- 引入CSS与JS文件 -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>
<script src="js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
<script src="js/default.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
var page=1;
var totalPage=1;

//当前目录
var path="/";
$(function(){               
	//xxxxpage = 13&absolutePath = xxx/xx
	 //                                 0            1               2     
	//http://127.0.0.1:8089/sys_am/list.jsp?page = 1&absolutePath = %2F哈哈哈%2Frgjgrdj
	var url=window.location.href;
	var urlx = decodeURI(url);
	var urls=urlx.split("=")
	var tmpPath = "/";
	if(urls[1]!=null && urls[1]!=0){
		var pageBack = urls[1].split("&")[0];
		if(urls[2]!=null && urls[2]!=""){
			tmpPath = urls[2];
		}
	}
	//%2F哈哈哈%2Ffgag
	if(pageBack==null){	
		findPage(page,tmpPath);
	}else{
		//,哈哈哈,afdgw
		var spilts = tmpPath.split("%2F");
		 var arraylen = spilts.length
	//	var nowSpilts=spilts.slice(1,2);
	//	 alert(nowSplits);
		var nowPath = "";
		for(var j=1;j<arraylen-1;j++){
			nowPath = nowPath +"/"+spilts[j];
		}
	
		var x;
		if(nowPath==""){
			nowPath="/";
		}
		findPage(pageBack,nowPath);
	}
})
function findUpPage(){
	if(page-1>=1){
	findPage(page-1,path);
	}else{
		alert("已经是第一页了")
	}
}
function findDownPage(){
	if(page+1<=totalPage){
		findPage(page+1,path);
	}else{
		alert("没有下一页了")
	}
}
function goStartPage(){
	findPage(1,path);
}
function findEndPage(){
	findPage(totalPage,path);
}
function goDelete(index){
	var p=$("#"+index).val();
	$.post("${pageContext.request.contextPath}/delete",{"bid":p},function(data){
		findPage(page);
	})
}
function goUpdate(index){
	//拿到节点值
	var p1=$("#"+index).val();
	if(p1 != "no"){
		if(path == "/"){
			var absolutePath = p1;
		}else{
			var absolutePath = path +p1;
		}	
	}
	//																0      1       2
	location.href="${pageContext.request.contextPath}/updateEntry?absolutePath="+absolutePath+"&page="+page;
}
function goAddNode(){
	location.href="${pageContext.request.contextPath}/addEntry?path="+path+"&page="+page;
}
function goAddDown(index){
	//拿到节点值
	var p1=$("#"+index).val();
	if(p1 != "no"){
		if(path == "/"){
			var absolutePath = p1;
		}else{
			var absolutePath = path +p1;
		}	
	}
	location.href="${pageContext.request.contextPath}/addDownEntry?path="+absolutePath+"&page="+page;
}
function goDeleteAll(){
	var chks=$("input:checked");
	var q = new Array();
	for(k=0;k<chks.length;k++){
		//将节点都放到数组里面去 格式为 /xxx/xxx 这样的
		q.push($($($(chks[k]).parent()).parent()).val())
	}
	//q=JSON.stringify(q);
	for(m=0;m<q.length;m++){
		$.post("${pageContext.request.contextPath}/delete",{"path":path,"nodePath":q[m]},function(data){
			
		})
	}
	
}

function goInfo(index){
	//拿到节点名	
	var p1 = $("#"+index).val();
	//拿到节点信息情况--如果有信息的话，就隐藏，如果没有信息的话就展示
	var tdx = $("#"+index).find("td");
	var tmp = $(tdx[1]).html();
	if(p1 != "no" && tmp==""){
		if(path == "/"){
			var absolutePath = p1;
		}else{
			var absolutePath = path +p1;
		}
		$.post("${pageContext.request.contextPath}/findNodeInfo",{"absolutePath":absolutePath},function(data){
			var tds = $("#"+index).find("td");
			$(tds[1]).html("value =>"+data['nodeValue']+"</br>"
					+"cZxid =>"+data['cZxid']+"</br>"
					+"ctime =>"+data['ctime']+"</br>"
					+"mZxid =>"+data['mZxid']+"</br>"
					+"mtime =>"+data['mtime']+"</br>"
					+"pZxid =>"+data['pZxid']+"</br>"
					+"cversion =>"+data['cversion']+"</br>"
					+"dataVersion =>"+data['dataVersion']+"</br>"
					+"aclVersion =>"+data['aclVersion']+"</br>"
					+"ephemeralOwner =>"+data['ephemeralOwner']+"</br>"
					+"dataLength =>"+data['dataLength']+"</br>"
					+"numOfChildren =>"+data['numOfChildren']);
		})
	}else{
		$(tdx[1]).html("");
	}
}
function goDelete(index){
	
	//拿到节点名	
	var p1 = $("#"+index).val();
	var flag = false;
	if(window.confirm('你确定要删除'+p1+'吗？')){
		if(window.confirm('你真的确定要删除'+p1+'吗？该节点下所有节点也会一并递归删除!')){
	        //alert("确定");
	        flag = true;
	     }
     }

	if(flag){
		if(p1 != "no"){
			if(path == "/"){
				var absolutePath = p1;
			}else{
				var absolutePath = path +p1;
			}
			$.post("${pageContext.request.contextPath}/delete",{"absolutePath":absolutePath},function(data){
				if(data!=path){
					//说明这级已经删完了，需要返回上一级
					findPage(1,data);
				}else{
					findPage(page,path);
				}
			
			})
			
		}
	}
}

function goDeep(index){
	//拿到节点名
	var p1 = $("#"+index).val();
	if(path == "/"){
		var absolutePath = p1;
	}else{
		var absolutePath = path + p1;
	}
	findPage(1,absolutePath);

}
function findRootNode(){
	findPage(1,'/');
}
function findPreNode(){
	var spilts = path.split("/");
	 var arraylen = spilts.length
	var nowSpilts=spilts.slice(1,arraylen-1);
	var nowPath = "";
	var x;
	if(nowSpilts.length!=0){
		for(x in nowSpilts){
			nowPath = nowPath + "/" + nowSpilts[x];
		}
	}else{
		nowPath = "/"
	}
	findPage(1,nowPath);
}
function findPage(pageIndex,tmppath){
	$.post("${pageContext.request.contextPath}/findByPage",{"pageIndex":pageIndex,"path":tmppath},function(data){
		if(data['totalPage']!=null){
	
		totalPage = data['totalPage'];
		path = data['path'];
		page=data['pageIndex'];
		$("#totalPage").html(data['totalPage']);
		$("#path").html(data['path']);
		$("#pageIndex").html(data['pageIndex']);
		$("#num").html(data['num']);
	
		var trs=$("#context").find("tr");
		var tds;
		
		for(i=2;i<12;i++){
			if(data['nodeList'][i-2] === undefined){				
				tds=$(trs[i]).find("td");
				$(tds[0]).html("");
				$(tds[1]).html("");
				$(trs[i]).val("no");
				$(trs[i]).hide();
			}else{				
				$(trs[i]).val("/"+data['nodeList'][i-2]);
				$(trs[i]).show();
				tds=$(trs[i]).find("td");
				$(tds[0]).html(data['nodeList'][i-2]);
				$(tds[1]).html("");
			}
	
			
		}
		
		}
		
	})
}
</script>
<!-- 引入CSS与JS文件 -->
</head>
<body>
	
	<%-- VIEW数据展示端是不允许出现java代码 --%>
	
	<div>
		<h1>zookeeper管理系统</h1>
		<h3>当前节点目录为：<span id="path"></span>&nbsp;&nbsp;当前目录节点数为：<span id="num"></span></h3>
		<h3>总页数：<span id="totalPage"></span></h3>
		<h3>当前为该节点的第<span id="pageIndex"></span>页</h3>
		<br />
		<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='index.jsp'"><span class="glyphicon glyphicon-home"></span> 返回首页</button>
		<button type="button" class="btn btn-info" onclick="javascript:findRootNode()"><span class="glyphicon glyphicon-home"></span> 返回根节点</button>
		<button type="button" class="btn btn-info" onclick="javascript:findPreNode()"><span class="glyphicon glyphicon-home"></span> 返回上一级</button>
		<button type="button" class="btn btn-warning" onclick="javascript:goAddNode()"><span class="glyphicon"></span> 新增节点</button>
		<br />
		<br />
		<br />
		<table id="context" class="table table-hover">
		
		<tr class="default">
				<td colspan="5" style="text-align: left;">
					当前目录为：<span id="totalCount" class="sp"></span>
					
				</td>
			</tr>		
				
			<tr class="info">
				<th>节点名</th>
				<th>节点信息(点击右边按钮以查看)</th>
				<th>操作</th>
				<th>删除?</th>
			<!--  <th><button class="btn btn-danger" onclick="javascript:goDeleteAll()">批量删除</button></th>-->	
			</tr>
			<tr id="1" value=""><td></td><td></td>
			<td><button type="button" class="btn btn-warning" onclick="javascript:goInfo(1);">查看/隐藏节点信息</button>
						<button type="button" class="btn btn-info" onclick="javascript:goUpdate(1);">修改节点</button>
						<button type="button" class="btn btn-info" onclick="javascript:goAddDown(1);">新增下级节点</button>
						<button type="button" class="btn btn-danger" onclick="javascript:goDeep(1);">进入下级</button>
						</td>
						<td><button type="button" class="btn btn-danger" onclick="javascript:goDelete(1);">删除</button></td>
						</tr>
						
			<tr id="2" value=""><td></td><td></td>
			<td><button type="button" class="btn btn-warning" onclick="javascript:goInfo(2);">查看/隐藏节点信息</button>
						<button type="button" class="btn btn-info" onclick="javascript:goUpdate(2);">修改节点</button>
						<button type="button" class="btn btn-info" onclick="javascript:goAddDown(2);">新增下级节点</button>
						<button type="button" class="btn btn-danger" onclick="javascript:goDeep(2);">进入下级</button></td>
						<td><button type="button" class="btn btn-danger" onclick="javascript:goDelete(2);">删除</button></td>
						</tr>
			<tr id="3" value=""><td></td><td></td>
			<td><button type="button" class="btn btn-warning" onclick="javascript:goInfo(3);">查看/隐藏节点信息</button>
						<button type="button" class="btn btn-info" onclick="javascript:goUpdate(3);">修改节点</button>
						<button type="button" class="btn btn-info" onclick="javascript:goAddDown(3);">新增下级节点</button>
						<button type="button" class="btn btn-danger" onclick="javascript:goDeep(3);">进入下级</button></td>
						<td><button type="button" class="btn btn-danger" onclick="javascript:goDelete(3);">删除</button></td>
						</tr>
			<tr id="4" value=""><td></td><td></td>
			<td><button type="button" class="btn btn-warning" onclick="javascript:goInfo(4);">查看/隐藏节点信息</button>
						<button type="button" class="btn btn-info" onclick="javascript:goUpdate(4);">修改节点</button>
						<button type="button" class="btn btn-info" onclick="javascript:goAddDown(4);">新增下级节点</button>
						<button type="button" class="btn btn-danger" onclick="javascript:goDeep(4);">进入下级</button></td>
						<td><button type="button" class="btn btn-danger" onclick="javascript:goDelete(4);">删除</button></td>
						</tr>
			<tr id="5" value=""><td></td><td></td>
			<td><button type="button" class="btn btn-warning" onclick="javascript:goInfo(5);">查看/隐藏节点信息</button>
						<button type="button" class="btn btn-info" onclick="javascript:goUpdate(5);">修改节点</button>
						<button type="button" class="btn btn-info" onclick="javascript:goAddDown(5);">新增下级节点</button>
						<button type="button" class="btn btn-danger" onclick="javascript:goDeep(5);">进入下级</button></td>
						<td><button type="button" class="btn btn-danger" onclick="javascript:goDelete(5);">删除</button></td>
						</tr>
			<tr id="6" value=""><td></td><td></td>
			<td><button type="button" class="btn btn-warning" onclick="javascript:goInfo(6);">查看/隐藏节点信息</button>
						<button type="button" class="btn btn-info" onclick="javascript:goUpdate(6);">修改节点</button>
						<button type="button" class="btn btn-info" onclick="javascript:goAddDown(6);">新增下级节点</button>
						<button type="button" class="btn btn-danger" onclick="javascript:goDeep(6);">进入下级</button></td>
						<td><button type="button" class="btn btn-danger" onclick="javascript:goDelete(6);">删除</button></td>
						</tr>
			<tr id="7" value=""><td></td><td></td>
			<td><button type="button" class="btn btn-warning" onclick="javascript:goInfo(7);">查看/隐藏节点信息</button>
						<button type="button" class="btn btn-info" onclick="javascript:goUpdate(7);">修改节点</button>
						<button type="button" class="btn btn-info" onclick="javascript:goAddDown(7);">新增下级节点</button>
						<button type="button" class="btn btn-danger" onclick="javascript:goDeep(7);">进入下级</button></td>
						<td><button type="button" class="btn btn-danger" onclick="javascript:goDelete(7);">删除</button></td>
						</tr>
			<tr id="8" value=""><td></td><td></td>
			<td><button type="button" class="btn btn-warning" onclick="javascript:goInfo(8);">查看/隐藏节点信息</button>
						<button type="button" class="btn btn-info" onclick="javascript:goUpdate(8);">修改节点</button>
						<button type="button" class="btn btn-info" onclick="javascript:goAddDown(8);">新增下级节点</button>
						<button type="button" class="btn btn-danger" onclick="javascript:goDeep(8);">进入下级</button></td>
						<td><button type="button" class="btn btn-danger" onclick="javascript:goDelete(8);">删除</button></td>
						</tr>
			<tr id="9" value=""><td></td><td></td>
			<td><button type="button" class="btn btn-warning" onclick="javascript:goInfo(9);">查看/隐藏节点信息</button>
						<button type="button" class="btn btn-info" onclick="javascript:goUpdate(9);">修改节点</button>
						<button type="button" class="btn btn-info" onclick="javascript:goAddDown(9);">新增下级节点</button>
						<button type="button" class="btn btn-danger" onclick="javascript:goDeep(9);">进入下级</button></td>
						<td><button type="button" class="btn btn-danger" onclick="javascript:goDelete(9);">删除</button></td>
						</tr>
			<tr id="10" value=""><td></td><td></td>
			<td><button type="button" class="btn btn-warning" onclick="javascript:goInfo(10);">查看/隐藏节点信息</button>
						<button type="button" class="btn btn-info" onclick="javascript:goUpdate(10);">修改节点</button>
						<button type="button" class="btn btn-info" onclick="javascript:goAddDown(10);">新增下级节点</button>
						<button type="button" class="btn btn-danger" onclick="javascript:goDeep(10);">进入下级</button></td>
						<td><button type="button" class="btn btn-danger" onclick="javascript:goDelete(10);">删除</button></td>
						</tr>
			
			
			<tr>
				<td colspan="5">
					<button type="button" class="btn btn-default" onclick="javascript:goStartPage();"><span class="glyphicon glyphicon-fast-backward"></span>当前节点首页</button>
							<button type="button" class="btn btn-default" onclick="javascript:findUpPage();"><span class="glyphicon glyphicon-backward"></span> 上一页</button>
						
						
							<button type="button" class="btn btn-default" onclick="javascript:findDownPage();">下一页 <span class="glyphicon glyphicon-forward"></span></button>
					<button type="button" class="btn btn-default" onclick="javascript:findEndPage();">当前节点末页 <span class="glyphicon glyphicon-fast-forward"></span></button>
				</td>
			</tr>
			
			
			
			
		</table>
		
		
	</div>
	
	
	
	
</body>
</html>