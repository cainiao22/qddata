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

    <style>
        .table .table {
            background-color: #747d8a;
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
                    <h3 class="text-themecolor m-b-0 m-t-0">${ sidebar_menu.parentName} </h3>
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
                                    <!-- BEGIN SAMPLE TABLE PORTLET-->
                                    <div class="portlet light bordered">
                                        <div class="portlet-title">


                                            <div class="form-group">
                                                <a href="roleadd">
                                                    <button type="button" class="btn btn-success">添加角色</button>
                                                </a>
                                                <a href="roleLevelAdd">
                                                    <button type="button" class="btn btn-success">添加角色层级</button>
                                                </a>

                                            </div>

                                        </div>
                                        <div class="portlet-body" style="color: #fdfdfebd">
                                            <div class="table-scrollable">
                                                <table class="table table-hover table-light">
                                                    <thead>
                                                    <tr>
                                                        <th width="33%">中文名称</th>
                                                        <th width="33%">英文名称</th>
                                                        <th width="33%">管理</th>

                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${roleList}" var="role">
                                                        <tr>
                                                            <td>${role.cnName}</td>
                                                            <td>${role.enName}</td>
                                                            <td><a id="role_${role.id}" href="javascript:void(0)">展开</a>
                                                                | <a href="./rolemanage?id=${role.id}">修改</a> | <a
                                                                        href="./roledelete?id=${role.id}" onclick="return deleteConfirm()"
                                                                >删除</a></td>
                                                        </tr>
                                                        <tr >
                                                            <td colspan="3" >
                                                                <table
                                                                        class="table table-striped table-bordered table-condensed table-hover"
                                                                        id="roleLevelTab_${role.id}" style="display: none;">
                                                                    <c:forEach items="${role.subRoleLevels}" var="roleLevel">
                                                                        <tr style="background-color: #383f48;">
                                                                            <td width="33%">层级：${roleLevel.level}
                                                                                :${roleLevel.cnName}</td>
                                                                            <td width="33%">${roleLevel.enName}</td>
                                                                            <td width="33%"><a
                                                                                    href="./roleLevelManage?id=${roleLevel.id}">修改</a>
                                                                                | <a
                                                                                        href="./roleLevelDelete?id=${roleLevel.id}" onclick="return deleteConfirm()">
                                                                                    删除</a></td>
                                                                        </tr>
                                                                    </c:forEach>

                                                                </table>
                                                            </td>


                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>

                                            </div>

                                            <div>


                                            </div>
                                        </div>
                                    </div>
                                    <!-- END SAMPLE TABLE PORTLET-->
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
<script language="JavaScript">
    function deleteConfirm(){
        var msg = "您真的确定要删除吗？\n\n请确认！";
        if (confirm(msg)==true){
            return true;
        }else{
            return false;
        }
    }
</script>
</body>
</html>
