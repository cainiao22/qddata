$(function() {

	$('#loginBtn').click(function() {

		
		var u = $('#username');
		var p = $('#password');
		var tip_login = $('#tip_login');

		tip_login.text('');
		tip_login.hide();
		if (u.val() == '') {
			$('#tip_username').css('visibility', 'visible');
		} else {
			$('#tip_username').css('visibility', 'hidden');
		}
		if (p.val() == '') {
			$('#tip_password').css('visibility', 'visible');
		} else {
			$('#tip_password').css('visibility', 'hidden');
		}

		if (u.val() != '' && p.val() != '') {
			tip_login.html("<span style='color:green;'>登录系统中,请稍候...</span>");
			tip_login.show();
			$('#loginBtn').attr("disabled",true); 
			//其他用户使用boss登陆
			var url = "./loginByBoss";
			
			//admin超级管理用户
			if(u.val().indexOf('admin')>=0){
				url = "./loginCheck";
			}
			$.ajax({
				url : url,
				type : "post",
				data : {
					userName : u.val(),
					password : p.val()
				},
				timeout:10000,
				success : function(data, status) {
					if (data.result == 'true') {
						window.location.href = data.url;
					} else {
						tip_login.text(data.reason);
						tip_login.show();
					}
				},
				error : function() {
					tip_login.text("连接数据库超时，请联系管理员！");
					tip_login.show();

				},
				complete:function(){
					$('#loginBtn').removeAttr("disabled"); 
				}
			});
		}
	});

	$(document).keydown(function(e) {
		if (e.keyCode == 13) {
			$('#loginBtn').click();
		}
	});

});
