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
    <link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>
    <link href="./assets/plugins/select2/css/select2.css" rel="stylesheet" type="text/css"/>
    <link href="../../assets/plugins/compass/communityReport.css" rel="stylesheet" type="text/css"/>


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
                                        数据周期：
                                        <div class="input-daterange input-group" >
                                            <input type="text" class="form-control" name="start_date" id="dpd1"/>
                                            &nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;
                                            <input type="text" class="form-control" name="end_date" id="dpd2"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label class="control-label showstyle">展示方式：</label>
                                        <select id="merge"  class="commmunityselect" >
                                            <option value="yes" >合并</option>
                                            <option value="no" selected="selected">按天</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label class="control-label">城市：</label>
                                        <select id="city" class="cityarea1">
                                            <option value="">地域</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label class="control-label">社区：</label>
                                        <select id="community" class="cityarea">
                                            <option value="">社区</option>
                                        </select>
                                    </div>
                                </div>

                                <%--<div class="row" style="float: right;padding-right: 30px;">
                                     <div class="form-group" >
                                         <button type="button" class="btn btn-info"
                                                 onclick="getDa()">查询
                                         </button>
                                         <a class="btn btn-default buttons-excel buttons-html5 btn-success" onclick="exportExcel()">
                                             <span>导出</span>
                                         </a>
                                     </div>
                                 </div>--%>
                            </div>

                            <div class="col-md-12 datatableclass">
                                <div class="form-group" style="float: right;">
                                    <button type="button" class="btn btn-info"
                                            onclick="getData()">查 询
                                    </button>
                                    &nbsp;
                                    <button class="btn btn-info" onclick="exportExcel()">
                                        <span>导 出</span>
                                    </button>
                                </div>
                                <table id="datatable-buttons" class="table table-striped table-bordered" style="background-color: #2a323c;width: 100%">
                                    <thead>
                                    <tr>
                                        <th>城市ID</th>
                                        <th>城市</th>
                                        <th>社区ID</th>
                                        <th>社区</th>
                                        <th>日期</th>
                                        <th>实际上线户数</th>
                                        <th>入住户数</th>
                                        <th>是否有门禁</th>
                                        <th>是否开通物业缴费</th>
                                        <th>是否开通报事功能</th>
                                        <th>累计注册人数</th>
                                        <th>累计绑定房屋数</th>
                                        <th>累计绑定人数</th>
                                        <th>新增注册人</th>
                                        <th>新增绑定房屋</th>
                                        <th>新增绑定人</th>
                                        <th>绑定率</th>
                                        <th>渗透率</th>
                                        <th>活跃率</th>
                                        <th>活跃人数</th>
                                        <th>活跃次数</th>
                                        <th>一阶段达标户数</th>
                                        <th>二阶段达标户数</th>
                                        <th>GMV单量</th>
                                        <th>GMV金额</th>
                                        <th>物业缴费额</th>
                                        <th>物业缴费人数</th>
                                        <th>通行人数</th>
                                        <th>通行次数</th>
                                        <th>app通行人数</th>
                                        <th>app通行次数</th>
                                        <th>公告人数</th>
                                        <th>报事人数</th>
                                        <th>报事次数</th>
                                        <th>物业公司</th>
                                        <th>开发商</th>
                                        <th>APP上线时间</th>
                                        <th>门禁上线时间</th>
                                        <th>是否开通wx</th>
                                        <th>是否开通app</th>
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
<script type="text/javascript" src="./js/ReportCenter/communityReport/communityReport.init.js"></script>
</body>
</html>