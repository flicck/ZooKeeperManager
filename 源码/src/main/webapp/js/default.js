/* 系统首页 - 显示账户信息 */
function showDatas(){
	// 发起一次请求 请求地址  get请求 --> doGet()方法 --> 类的对象 
	window.location.href = "findUserByPage?pageIndex=1";
}

/* 数据展示 - 分页方法 */
function goPage(pageIndex){
	// 发起一次请求 请求地址  get请求 --> doGet()方法 --> 类的对象 
	window.location.href = "findUserByPage?pageIndex="+pageIndex;
}



/* 系统首页 - 添加人员信息跳转 */
function goAdd(){
	window.location.href = "add.jsp";
}


/* 数据展示 - 删除人员信息 */
function goDelete(uid){
	var con = confirm("确定要删除这条数据么?");
	if(con == true){
		// 1 2 pid=100 1 2 
		// http get方式数据请求 ?key1=val1&key2=val2&ke3=val3.....
		window.location.href = "DeleteUser?uid="+uid;
	}
}


/* 数据显示 - 准备更新人员信息(反显) */
function goUpdate(uid){
	window.location.href = "findUserByID?uid="+uid;
}





