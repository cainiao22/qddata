<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- All Jquery -->
<script src="./assets/plugins/jquery.min.js"></script>
<!-- Bootstrap tether Core JavaScript -->
<script src="./assets/plugins/bootstrap/js/popper.min.js"></script>
<script src="./assets/plugins/bootstrap/js/bootstrap.min.js"></script>
<!-- slimscrollbar scrollbar JavaScript -->
<script src="./assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!--Wave Effects -->
<script src="./assets/plugins/waves.js"></script>
<!--Menu sidebar -->
<script src="./assets/js/sidebarmenu.js"></script>
<!--stickey kit -->
<script src="./assets/plugins/sticky-kit-master/dist/sticky-kit.min.js"></script>
<!--Custom JavaScript -->
<script src="./assets/js/custom.min.js"></script>
<!-- Style switcher -->
<script src="./assets/plugins/styleswitcher/jQuery.style.switcher.js"></script>


<script src="./assets/plugins/bootstrap-sweetalert/sweetalert.min.js" type="text/javascript"></script>
<script src="./assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="./assets/plugins/jquery-cookie/jquery.cookie.js" type="text/javascript"></script>
<script type='text/javascript'>
    $(function(){
        var realname = decodeURI(Cookies.get("realname"));
       $("#welcome").text("欢迎您," + realname + "!");
        //在这儿利用userName去后台发送请求，，如果能获取到则让其展示
        var realname = decodeURI(Cookies.get("username"));
        //console.log(realname);
        if(""!=realname && null!=realname && undefined!=realname && "undefined"!=realname ){
            $.ajax({
                url: "./getUserByName?userName=" +realname,
                type: "post",
                success: function (data) {
                    //用户来源(0.compass/1.boss/2.mis)
                    if (1 != data.userSource || (null!=data.userName && null==data.userSource)) {
                       // console.log(data)
                        $("#updatePasswordBtn").show();
                        /*$("#logOutBtn").css("bottom","5px");*/
                        $("#logOutBtn").css("bottom","17px");
                    }
                },
                error: function (e) {
                }
            });
        }
    });
</script>