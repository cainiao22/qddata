<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss.jsp"></c:import>
    <style type="text/css">
        html{background:#ffffff;}
        .alert {border-width: 1px;}
        .display-hide, .display-none {display: none;}
        .alert-danger {background-color: #fbe1e3;border-color: #fbe1e3;color: #e73d4a;  }
    </style>
    <script src="../../assets/statics/compassEntrance/common/js/lxjsSdk.js"></script>
    <script src="../../assets/statics/compassEntrance/common/js/jquery-3.2.1.js"></script>
    <script>
        var lxbridge = null;
        // 建立bridge连接 demo
        setupWebViewJavascriptBridge(function(bridge) {
            //获取浏览器信息
            var u = navigator.userAgent, app = navigator.appVersion;
            var isAndroid = null;
            var isiOS = null;
            if (isAndroid == null) {
                isAndroid = u.indexOf("Android") > -1 || u.indexOf("Linux") > -1
            }
            if (isiOS == null) {
                isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)
            }
            if (isAndroid) {
                bridge.init(function(message, responseCallback) {
                    var data = {
                        'Javascript Responds' : 'Wee!'
                    }
                    responseCallback(data)
                })
            }

            /* 在这里拿到bridge */
            lxbridge=bridge;


        })
    </script>
</head>
<!-- END HEAD -->

<body class=" login" id="loginclass">
<div class="accountbg"></div>
<!-- BEGIN LOGIN -->
<div class="wrapper-page" style="display: none">
    <div class="panel panel-color panel-primary panel-pages">
        <div class="panel-body" >
            <h3 class="text-center m-t-0 m-b-15">
                <a  href="index.html">
                    <b>
                        <img src="./assets/images/logo-light-icon.png" alt="homepage" class="light-logo"/>
                    </b>
                    <span>
                    <img src="./assets/images/logo-light-text2.png" class="light-logo" alt="homepage"/>
                </span>
                </a>
            </h3>
            <!-- BEGIN LOGIN FORM -->
            <form class="login-form form-horizontal m-t-20" method="post">
                <div class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span>请输入用户名密码！ </span>
                </div>
                <div class="form-group">
                    <div class="input-icon col-xs-12">
                        <input class="form-control" type="text" required="" placeholder="用户名" autocomplete="off"
                               name="username" id="username">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-icon col-xs-12">
                        <input class="form-control" type="password" placeholder="密码" autocomplete="off" name="password"
                               id="password" required=""/>
                    </div>
                </div>
                <div class="form-group text-center m-t-40">
                    <div class="col-xs-12">
                        <button class="btn btn-primary btn-block btn-lg waves-effect waves-light" type="button"
                                id="loginBtn">登 录
                        </button>
                    </div>
                </div>
            </form>
            <input type="hidden" id="target" value="${target}" />
        </div>
    </div>
    <!-- END LOGIN FORM -->
</div>
<!-- END LOGIN -->
<c:import url="common/importJs.jsp"></c:import>
<script src="./assets/js/login/login.js" type="text/javascript"></script>
<script src="./assets/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="./assets/plugins/jquery-validation/js/localization/messages_zh.min.js" type="text/javascript"></script>
<script type="text/javascript">


    var url = "qddata.qdingnet.com";
    var k_host = window.document.location.hostname; //返回 web 主机的域名
    //  location.pathname //返回当前页面的路径和文件名
    var port = window.document.location.port;//返回 web 主机的端口 （80 或 443）
    var Scheme = window.document.location.protocol; //返回所使用的 web 协议（http:// 或 https://）http:
    //获取路径
    var pathName = window.document.location.pathname;
    //截取，得到项目名称
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    function getCurrentUserInfo() {
        try {
            lxbridge.callHandler('getCurrentUserInfo', {}, function (response) {
                if (response) {
                    if (typeof (response) == "object") {
                        lxUser = response;
                    } else {
                        lxUser = JSON.parse(response);
                    }
                    var userName = lxUser.usercode.substring(3);
                    //$(".login").style.display="block";
                    if (userName) {
                        getLongxinUrl(userName,"","");
                    } else {
                        $("html").css("background", "#2a323c");
                        $(".panel").css("background", "#323c48");
                        $(".wrapper-page").show();
                    }
                    //window.location.href="http://qabigdata.qdingnet.com/assets/pages/compass/index.html";
                } else {
                    $("html").css("background", "#2a323c");
                    $(".panel").css("background", "#323c48");
                    $(".wrapper-page").show();
                }
            });
        } catch (e) {
            var code = getQueryString("code");
            var state = getQueryString("state");
            if(state && code){
                getLongxinUrl("",code,state)
            }else{
                $("html").css("background", "#2a323c");
                $(".panel").css("background", "#323c48");
                $(".wrapper-page").show();
            }
        }
    }
    //根据参数名获取对应的url参数
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
    //获取所有的参数（通过对象的形式返回）
    function getQueryStrings(url) {
        var theRequest = new Object();
        var strs = url.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
        return theRequest;
    }
    function getLongxinUrl(name,code,state) {
        $.ajax({
            type: "GET",
            url: "./longxinLogin?userName=" + name + "&code=" + code + "&state="+state,
            dataType: "json",
            success: function (data) {
                if (data.result) {
                    if ($("#target").val() != '') {
                        if (k_host != url) {
                            window.location.href = Scheme + "//" + k_host + ":" + port + projectName + $("#target").val();
                        } else {
                            window.location.href = Scheme + "//" + k_host + $("#target").val();
                        }
                    } else {
                        window.location.href = data.url;
                    }
                }else{
                    $("html").css("background", "#2a323c");
                    $(".panel").css("background", "#323c48");
                    $(".wrapper-page").show();
                }
            }
        });
    }

    window.setTimeout("getCurrentUserInfo()",1000)
$(function () {
//  $('#userClick').click();
})
</script>
</body>
</html>