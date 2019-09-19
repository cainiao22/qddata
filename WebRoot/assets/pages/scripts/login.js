var Login = function() {

    var handleLogin = function() {

        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            },

            messages: {
                username: {
                    required: "请输入用户名!"
                },
                password: {
                    required: "请输入密码!"
                }
            },

            invalidHandler: function(event, validator) { //display error alert on form submit   
                $('.alert-danger', $('.login-form')).show();
            },

            highlight: function(element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function(form) {
            	loginCheck();
            }
        });

        function loginCheck(){
        	$('#loginBtn').attr("disabled",true); 
			//其他用户使用boss登陆
			var url = "./loginByBoss";
			
			//admin超级管理用户
			if($('#username').val().indexOf('admin')>=0){
				url = "./loginCheck";
			}
			$.ajax({
				url : url,
				type : "post",
				data : {
					userName : $('#username').val(),
					password :  $('#password').val()
				},
				timeout:10000,
				success : function(data, status) {
					if (data.result == 'true') {
						window.location.href = data.url;
					} else {
						swal({
							  title: "用户名或密码错误！",
							  type: "warning",
							  confirmButtonClass: "btn-danger",
							  closeOnConfirm: true
							});
					}
				},
				error : function() {
					swal({
						  title: "连接数据库超时，请联系管理员！",
						  type: "warning",
						  confirmButtonClass: "btn-danger",
						  closeOnConfirm: true
						});

				},
				complete:function(){
					$('#loginBtn').removeAttr("disabled"); 
				}
			});
        }
        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                	loginCheck();
                }
                return false;
            }
        });
    	$('#loginBtn').click(function() {
    		  if ($('.login-form').validate().form()) {
              	loginCheck();
              }
              return false;
        });
    }


    return {
        //main function to initiate the module
        init: function() {
            handleLogin();
        }

    };

}();

jQuery(document).ready(function() {
    Login.init();
});