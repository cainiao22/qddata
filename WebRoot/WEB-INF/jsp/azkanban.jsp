<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
    <link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css" />
    <link href="./assets/plugins/select2/css/select2.css" rel="stylesheet" type="text/css" />
    <link href="../../assets/plugins/compass/communityReport.css" rel="stylesheet" type="text/css" />


</head>
<!-- END HEAD -->

<body class="fix-header fix-sidebar card-no-border">

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
                    <%--<h3 class="text-themecolor m-b-0 m-t-0">${ sidebar_menu.parentName}</h3>--%>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="javascript:void(0)">${ sidebar_menu.parentName}</a></li>
                        <li class="breadcrumb-item active">${ sidebar_menu.name} </li>
                    </ol>
                </div>
            </div>
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- Start Page Content -->
            <!-- Row -->

            <div class="col-md-12">
                <div class="card">
                    <div class="card-body" style="padding:0;">
                        <div class="col-md-13">
                            <div class="portlet-title">
                                <div class="row">
                                    <div class="form-group col-md-4 ">
                                        时间：
                                        <div class="input-daterange input-group">
                                            <input type="text" class="form-control" name="startTime" id="dpd1" />
                                            &nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;
                                            <input type="text" class="form-control" name="endTime" id="dpd2" />
                                        </div>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label class="control-label showstyle">责任人：</label>
                                        <select id="owner" name="owner" class="commmunityselect">
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label class="control-label">任务：</label>
                                        <input type="text" class="jobinput" name="jobId" id="jobId" />
                                    </div>
                                    <div class="form-group col-md-2">
                                        <button type="button" class="btn btn-info" onclick="getData()">查 询
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="portlet-title">
                                <div class="row">
                                    <div class="form-group col-md-4 ">
                                    </div>
                                    <div class="form-group col-md-3">
                                    </div>
                                    <div class="form-group col-md-5">
                                        <label class="control-label"><input name="status" type="radio" checked="checked" value="" />全部</label>&nbsp;&nbsp;&nbsp;
                                        <label><input name="status" type="radio" value="50"/>正常&nbsp;<i style="width:16px;height:16px;border-radius:50%;background-color:green;display: inline-block"></i></label>&nbsp;&nbsp;&nbsp;
                                        <label><input name="status" type="radio" value="70"/>报警&nbsp;<i style="width:16px;height:16px;border-radius:50%;background-color:red;display: inline-block"></i></label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 datatableclass">

                                <table id="datatable-buttons" class="table table-striped table-bordered" style="background-color: #2a323c;width: 100%">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>任务名称</th>
                                        <th>所属flow</th>
                                        <th>所属项目</th>
                                        <th>执行时间</th>
                                        <th>状态</th>
                                        <th>责任人</th>
                                    </tr>
                                    </thead>
                                    <tbody></tbody>
                                </table>
                            </div>
                            <!-- END SAMPLE TABLE PORTLET-->
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
<script src="./assets/plugins/modernizr.min.js"></script>
<script src="./assets/plugins/detect.js"></script>
<script src="./assets/plugins/fastclick.js"></script>
<script src="./assets/plugins/jquery.slimscroll.js"></script>
<script src="./assets/plugins/waves.js"></script>
<script src="./assets/plugins/wow.min.js"></script>
<script src="./assets/plugins/jquery.nicescroll.js"></script>
<script src="./assets/plugins/jquery.scrollTo.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="./assets/plugins/datepicker/js/bootstrap-datepicker.js"></script>
<script src="./assets/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="./assets/plugins/select2/js/select2.js"></script>
<script src="./assets/plugins/select2/js/zh-CN.js"></script>
<script type="text/javascript" src="./assets/plugins/moment-with-locales.min.js"></script>
<script type="text/javascript" src="./js/azkaban.js"></script>
</body>

</html>