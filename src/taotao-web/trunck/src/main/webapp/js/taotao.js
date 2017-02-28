var TT = TAOTAO = {
	checkLogin : function(){
		// 获取当前域中的cookie，这里使用的第三方jquery.cookie.js插件
		var _token = $.cookie("TT_TOKEN");
		if(!_token){
			return ;
		}
		$.ajax({
			url : "http://sso.taotao.com/service/user/" + _token,
			dataType : "jsonp", // jsonp的本质是 通过spring标签的可以跨域请求资源，spring标签请求都是get请求，所以jsonp都是get请求
			type : "GET",
			success : function(data){
				var _data = data;
				var html =_data.username+"，欢迎来到淘淘！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
				$("#loginbar").html(html);
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});