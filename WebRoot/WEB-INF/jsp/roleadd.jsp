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
                        <div class="card-body">
                            <div class="form-group">
                                <h4 class="card-title">${ sidebar_menu.name}</h4>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <!-- BEGIN FORM-->
                                    <form class="form-horizontal" action="./rolesave" method="post"
                                          id="form">
                                        <div class="portlet light bordered">
                                            <div class="portlet-body form">
                                                <div class="form-body" style="color: #fdfdfebd">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">中文名称: </label>
                                                        <div class="col-md-4">
                                                            <input style="color: #fdfdfebd" type="text" name="cnName" id="cnName"
                                                                   class="form-control" value="${role.cnName }">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">英文名称: </label>
                                                        <div class="col-md-4">
                                                            <input style="color: #fdfdfebd" type="text" id="enName"
                                                                   name="enName" class="form-control"
                                                                   value="${role.enName}"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="portlet-body form">
                                                <div class="form-actions">
                                                    <div class="row">
                                                        <div class="col-md-offset-3 col-md-4">
                                                            <input type="hidden" name="id" id="id" value="${role.id }"/>
                                                            <button type="button" class="btn btn-success" id="submitBtn">确定</button>
                                                            <a href="rolelist">
                                                                <button type="button" class="btn btn-warning ">取消</button>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>

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
<script type='text/javascript' src="js/systemManagement/roleTableManage.js"></script>
<script type='text/javascript' src="js/systemManagement/usermanage.js"></script>
</body>
</html>