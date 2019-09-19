<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
    <script src="js/jquery-1.7.2.min.js"></script>
    <style>
        .table .table {
            background-color: #747d8a;
        }
         .breadcrumb-item.active {
            color: white;
        }
         /*label调整*/
        .form-horizontal label {
            margin-bottom: 30px;
            width: 100px;
            text-align: right;
            margin-right: 20px;
            color: #fdfdfebd;
            font-weight: 400;
            font-family: "Rubik", sans-serif;
            padding-top: 7px;
        }

        .form-update-password{
            background-color: #2a323c;
            min-height: 38px;
            padding: .375rem .75rem;
            font-size: 1rem;
            line-height: 1.5;
            background-clip: padding-box;
            border: 1px solid #ced4da;
            border-radius: .25rem;
            transition: border-color .15s ease-in-out, box-shadow .15s ease-in-out;
            border-color: #747d8a;
        }

        .updatepasswordbtn{
            width: 100px;
            height: 30px;
            line-height: 18px;
        }
        .form-group {
            padding-top: 10px;
            margin-bottom: 25px;
            margin-left: 57px;
        }

        .row span{
            display: inline-block;
            font-size: 14px;
            height: 20px;
            line-height: 20px;
        }


    </style>
    <script>
        var pd;
        $(function () {
            $("#reset").click(function () {
                $("input[type=text]").val("");
                $("input[type=password]").val("");
                $("#passstrength3").html("");
                $("#passstrength2").html("");
                $("#passstrength").html("");
            });


            $("#oldpassword").blur (function (e) {
               /* var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\W).*$", "g");
                var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
                var enoughRegex = new RegExp("(?=.{6,}).*", "g");*/
                var password =   $("#oldpassword").val();
                if(null==password || undefined ==password ||''==password){
                    $("#passstrength").html("请输入原密码")
                }else if(password.length>0){
                    var arr = new Array();
                    arr = password.split(" ");
                    if(arr.length != 1){
                        $("#passstrength").html("密码不能包含空格")
                       // return false;
                    }else{
                        //这儿发送ajax请求，从后台判断密码是否一致
                        $.ajax({
                            url: "./checkUserPassword?password=" +password,
                            type: "post",
                            success: function (data) {
                                if (data.success ) {
                                    $("#passstrength").html('<img src="../../assets/images/user/true.png">');
                                } else {
                                    $("#passstrength").html('<img src="../../assets/images/user/chahao.png">'+data.msg)

                                   // $("#passstrength").html(data.msg);
                                }
                            },
                            error: function (e) {
                                // rj.error(e);
                            }
                        });
                    }
                }
                return true;
            });

            $("#newpassword").blur (function (e) {
                var newpassword =   $("#newpassword").val();
                if(null==newpassword || undefined ==newpassword ||''==newpassword){
                    $("#passstrength2").html("请输入新的密码");
                }else if(newpassword.length>0){
                    var arr = new Array();
                    arr = newpassword.split(" ");
                    if(arr.length != 1){
                        $("#passstrength2").html("密码不能包含空格")
                        // return false;
                    }else{
                        $("#passstrength2").html("");
                        checkPasswordAgain();
                    }
                    //$("#passstrength2").html("新密码长度不能为空");
                }else{
                    $("#passstrength2").html("");
                    checkPasswordAgain();
                }
                return true;
            });

           function checkPasswordAgain () {
               var newpassword2 =   $("#newpassword2").val();
               if(null==newpassword2 || undefined ==newpassword2 ||''==newpassword2){
                   $("#passstrength3").html("请再输入一遍新密码");
               }else if( newpassword2.length>0){
                   var arr = new Array();
                   arr = newpassword2.split(" ");
                   if(arr.length != 1){
                       $("#passstrength3").html("密码不能包含空格")
                       // return false;
                   }else if(newpassword2 !=$("#newpassword").val()){
                       $("#passstrength3").html('<img src="../../assets/images/user/chahao.png">输入的密码与新密码不一致')
                   }else{
                       $("#passstrength3").html("");
                   }
                   //$("#passstrength3").html("密码长度最低六位");
               }else if(newpassword2 !=$("#newpassword").val()){
                   $("#passstrength3").html("输入的密码与新密码不一致");
                   //$("#passstrength3").html("<img src='../../assets/images/user/chahao.png'>输入的密码与新密码不一致");
               }else{
                   $("#passstrength3").html("");
               }
               return true;
           }
            $("#newpassword2").blur (function (e) {
                var newpassword2 =   $("#newpassword2").val();
                if(null==newpassword2 || undefined ==newpassword2 ||''==newpassword2){
                    $("#passstrength3").html("请再输入一遍新密码");
                }else if( newpassword2.length>0){
                    var arr = new Array();
                    arr = newpassword2.split(" ");
                    if(arr.length != 1){
                        $("#passstrength3").html("密码不能包含空格")
                        // return false;
                    }else if(newpassword2 !=$("#newpassword").val()){
                        $("#passstrength3").html('<img src="../../assets/images/user/chahao.png">输入的密码与新密码不一致')
                    }else{
                        $("#passstrength3").html("");
                    }
                    //$("#passstrength3").html("密码长度最低六位");
                }else if(newpassword2 !=$("#newpassword").val()){
                    $("#passstrength3").html("输入的密码与新密码不一致");
                    //$("#passstrength3").html("<img src='../../assets/images/user/chahao.png'>输入的密码与新密码不一致");
                }else{
                    $("#passstrength3").html("");
                }
                return true;
            });


            //验证密码：
            $("#submit").click(function () {
                //在这儿校验，成功则让其提交
                var password =   $("#oldpassword").val();
                if(null==password || undefined ==password || ''==password){
                    return;
                }else if(password.length>0) {
                    var arr = new Array();
                    arr = password.split(" ");
                    if (arr.length != 1) {
                        return;
                    }
                }
                var newpassword =   $("#newpassword").val();
                if(null==newpassword || undefined ==newpassword ||  ''==newpassword){
                    return;
                }else if(newpassword.length>0) {
                    var arr = new Array();
                    arr = newpassword.split(" ");
                    if (arr.length != 1) {
                        return;
                    }
                }
                var newpassword2 =   $("#newpassword2").val();
                if(null==newpassword2 || undefined ==newpassword2 ||  ''==newpassword2){
                   return;
                }else if(newpassword2!=newpassword){
                    return;
                }else if(newpassword2.length>0) {
                    var arr = new Array();
                    arr = newpassword2.split(" ");
                    if (arr.length != 1) {
                        return;
                    }
                }

               $.ajax({
                    url: "./updateUserPassword?password=" + password+"&newPassword="+newpassword,
                    type: "post",
                    success: function (data) {
                        if (data.success) {
                            window.location.href ="login";
                        } else {
                            alert(data.msg);
                            rj.error(data.Msg);
                            return;
                        }
                    },
                    error: function (e) {
                        //rj.error(e);
                    }
                });
               /* $.ajax({
                    url: "/PersonCenter/SaveForm?UserId=" + '@ViewBag.CurrentUserId' + "&Password=" + $("#userPw1").val(),
                    type: "post",
                    success: function (data) {
                        if (data.Status == "1") {
                            rj.success(data.Msg);
                        } else {
                            rj.error(data.Msg);
                            return;
                        }
                    },
                    error: function (e) {
                        rj.error(e);
                    }
                });*/
            });
        });

    </script>

</head>
<!-- END HEAD -->
<body class="fix-header fix-sidebar card-no-border">
<div class="preloader">
    <svg class="circular" viewBox="25 25 50 50">
        <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"/>
    </svg>
</div>
<div id="main-wrapper">
    <c:import url="common/header2.jsp"></c:import>
    <!-- End Topbar header -->
    <aside class="left-sidebar">
        <!-- Sidebar scroll-->
        <div class="scroll-sidebar">
            <!-- Sidebar navigation-->
            <nav class="sidebar-nav">
                <ul id="sidebarnav">
                    <c:import url="common/sidebarMenu2.jsp"></c:import>
                </ul>
            </nav>
            <!-- End Sidebar navigation -->
        </div>
        <!-- End Sidebar scroll-->
    </aside>
    <!-- Page wrapper  -->
    <div class="page-wrapper">
        <!-- Container fluid  -->
        <div class="container-fluid">
            <!-- Bread crumb and right sidebar toggle -->
            <div class="row page-titles">
                <div class="col-md-6 col-8 align-self-center">
                    <h3 class="text-themecolor m-b-0 m-t-0">${ sidebar_menu.parentName}</h3>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="javascript:void(0)">${ sidebar_menu.parentName}</a></li>
                        <li class="breadcrumb-item active">${ sidebar_menu.name} </li>
                    </ol>
                </div>
            </div>
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- Start Page Content -->
            <!-- Row -->
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-body" style="background: RGB(29,44,51);border-radius: 8px;padding: 10px">
                            <div class="row">
                                <div class="col-md-12">
                                    <!-- BEGIN FORM-->
                                    <h4 style="width:94% ;text-align:center ;color: white">修改密码</h4>
                                    <form class="form-horizontal" action="./updateUserPassword" method="post" id="form"  >
                                        <div class="form-body" style="color: #6c757d;  margin: 0 auto;width:50%;">
                                           <div class=" row">
                                                <label class=" control-label">原密码: </label>
                                                <div class="">
                                                    <input type="password" name="password" id="oldpassword"
                                                           class="form-update-password form-controller" style="color: #6c757d;" placeholder="请输入原始密码">
                                                    <span id="passstrength" style="color:red"></span>
                                                </div>
                                            </div>
                                            <div class=" row">
                                                <label class=" control-label">新密码: </label>
                                                <div class="">
                                                    <input type="password" name="newpassword" id="newpassword"
                                                           class="form-update-password"  style="color: #6c757d" placeholder="请输入新密码">
                                                    <span id="passstrength2" style="color:red"></span>
                                                </div>
                                            </div>
                                            <div class=" row">
                                                <label class=" control-label">确认新密码: </label>
                                                <div class="">
                                                    <input type="password" name="newpassword2" id="newpassword2"
                                                           class="form-update-password"  style="color: #6c757d" placeholder="请再输入一遍新密码">
                                                    <span id="passstrength3" style="color:red;"></span>
                                                </div>
                                            </div>
                                            <div class="form-group" >
                                                <input type="button" id="submit" name="submit" value="提交" class="btn btn-info updatepasswordbtn" style="text-align:center;margin-left: 61px;" />
                                                &nbsp;&nbsp;&nbsp;<input type="button" id="reset" name="reset" value="重置" class="btn btn-info updatepasswordbtn" style="text-align:center" />
                                            </div>
                                        </div>
                                    </form>
                                    <!-- END FORM-->
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Row -->
            <!-- End PAge Content -->
        </div>
        <!-- End Container fluid  -->
        <!-- footer -->
        <c:import url="common/footer.jsp"></c:import>
        <!-- End footer -->
    </div>
    <!-- End Page wrapper  -->
</div>
<!-- End Wrapper -->
<c:import url="common/importJs2.jsp"></c:import>
<%--D:\IdeaProjects\trunk\WebRoot\assets\js\systemManagement\roleTableManage.js--%>
<script type='text/javascript' src="js/systemManagement/roleTableManage.js"></script>
<script type='text/javascript' src="js/systemManagement/usermanage.js"></script>
</body>
</html>