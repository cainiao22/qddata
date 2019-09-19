<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
    <style>
        .dashboard-stat .visual {
            width: 80px;
            height: 80px;
            display: block;
            float: left;
            padding-top: 10px;
            padding-left: 15px;
            margin-bottom: 15px;
            font-size: 35px;
            line-height: 35px;
        }

        .icond {
            margin-left: -14px;
            font-size: 93px;
            line-height: 73px;
            color: #FFF;
            opacity: .1;
        }

       .panel {
            overflow: hidden;
        }
    </style>
</head>
<!-- END HEAD -->

<body class="fix-header fix-sidebar card-no-border">
<div class="preloader">
    <svg class="circular" viewBox="25 25 50 50">
        <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"/>
    </svg>
</div>
<!-- BEGIN CONTAINER -->
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

    <div class="page-wrapper">
        <!-- Container fluid  -->
        <div class="container-fluid">
            <div class="">
                <div class="page-header-title"><h3 class="page-title">&nbsp;&nbsp;&nbsp;大屏数据</h3></div>
            </div>
            <div class="page-content-wrapper ">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6 col-lg-4">
                            <div class="panel text-right dashboard-stat">
                                <div class="visual">
                                    <i class="fa fa fa-registered icond"></i>
                                </div>
                                <div class="panel-heading"></div>
                                <div class="panel-body p-t-10"><h2 class="m-t-0 m-b-15">
                                    <b id="data1">0</b></h2>
                                    <p class="text-muted m-b-0 m-t-20"><b>总注册用户数</b></p></div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-lg-4">
                            <div class="panel text-right dashboard-stat">
                                <div class="visual">
                                    <i class="fa fa-user-plus icond"></i>
                                </div>
                                <div class="panel-heading"></div>
                                <div class="panel-body p-t-10"><h2 class="m-t-0 m-b-15">
                                    <b id="data2">0</b></h2>
                                    <p class="text-muted m-b-0 m-t-20"><b>昨日注册用户数</b></p></div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-lg-4">
                            <div class="panel text-right dashboard-stat">
                                <div class="visual">
                                    <i class="fa fa-male icond"></i>
                                </div>
                                <div class="panel-heading"></div>
                                <div class="panel-body p-t-10"><h2 class="m-t-0 m-b-15">
                                    <b id="data3">0</b></h2>
                                    <p class="text-muted m-b-0 m-t-20"><b>昨日APP启动数</b></p></div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">

                            <iframe src="realtime_sales" width="100%" height="800px;" style="border-width: 0px;"></iframe>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <c:import url="common/footer.jsp"></c:import>
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<%--<c:import url="common/global_js.jsp"></c:import>--%>
<c:import url="common/importJs2.jsp"></c:import>

<script type="text/javascript" src="./assets/plugins/jquery.animateNumber.min.js"></script>

<script type='text/javascript'>
    $(function () {
        function getDay(day) {
            var today = new Date();
            var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24 * day;
            today.setTime(targetday_milliseconds); //注意，这行是关键代码
            var tYear = today.getFullYear();
            var tMonth = today.getMonth();
            var tDate = today.getDate();
            tMonth = doHandleMonth(tMonth + 1);
            tDate = doHandleMonth(tDate);
            return tYear + "-" + tMonth + "-" + tDate;
        }

        function doHandleMonth(month) {
            var m = month;
            if (month.toString().length == 1) {
                m = "0" + month;
            }
            return m;
        }
        $.post('../dataApiQuery/member_all_query', {}, function (data) {
            $('#data1').animateNumber({number: data.data[0].count});
        });
        $.post('../dataApiQuery/member_query_yesterday', {}, function (data) {
            $('#data2').animateNumber({number: data.data[0].count});
        });
        $.post('../dataApiQuery/app_start_query_yesterday', {
            dt: getDay(-1)
        }, function (data) {
            $('#data3').animateNumber({number: data.data[0].count});
        });
    });
</script>
</body>
</html>