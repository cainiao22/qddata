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
                                    <form class="form-horizontal" action="./modulesave" method="post" id="form">
                                        <div class="panel panel-primary">
                                            <div class="panel-body">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <h4 class="page-header m-t-0">基本信息</h4>
                                                    </div>
                                                </div>
                                                <div class="form-body">
                                                        <div class="form-group row">
                                                            <label class="col-md-3 control-label">父级模块:</label>
                                                            <div class="col-md-4">
                                                                <select id="p_module" name="pid" style="width: 300px;" class="form-control">
                                                                    <option value='-1'>系统顶级</option>
                                                                    <c:forEach items="${allTreeModules}" var="moduleTemp">

                                                                        <option value='${ moduleTemp.id}'
                                                                                <c:if test="${ moduleTemp.id eq  module.pid }"> selected=selected </c:if>>
                                                                            <c:forEach begin="1"
                                                                                       end="${moduleTemp.level}">------</c:forEach>
                                                                                ${moduleTemp.moduleName}
                                                                        </option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-md-3 control-label">模块名称: </label>
                                                            <div class="col-md-4">
                                                                <input type="text"
                                                                       name="moduleName" class="form-control"
                                                                       value="${module.moduleName }"/>
                                                            </div>
                                                        </div>

                                                        <div class="form-group row">
                                                            <label class="col-md-3 control-label">模块标识: </label>
                                                            <div class="col-md-4">
                                                                <input type="text"
                                                                       name="moduleUrl" class="form-control"
                                                                       value="${module.moduleUrl }"/>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-md-3 control-label">顺序编码: </label>
                                                            <div class="col-md-4">
                                                                <input type="text"
                                                                       name="sortNo" class="form-control"
                                                                       value="${module.sortNo }"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                <div>
                                                    <div class="col-sm-12">
                                                        <h4 class="page-header m-t-0">权限关联</h4>
                                                    </div>
                                                </div>
                                                <div >
                                                    <table class="table table-striped table-bordered table-condensed table-hover">
                                                        <thead>
                                                        <tr>
                                                            <th width="33%">中文名称</th>
                                                            <th width="33%">英文名称</th>
                                                            <th width="33%">配置权限</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach items="${roleList}" var="role">
                                                            <tr>
                                                                <td><span style="color: #01c0c8;">角色：</span>${role.cnName}</td>
                                                                <td>${role.enName}</td>
                                                                <td><a id="role_${role.id}" href="javascript:viod(0)">折叠</a>
                                                                    <a id="NoSelect_${role.id}"
                                                                       href="javascript:void(0)">不选</a>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="3">
                                                                    <table
                                                                            class="table table-striped table-bordered table-condensed table-hover"
                                                                            id="roleLevelTab_${role.id}">
                                                                        <c:forEach items="${role.subRoleLevels}"
                                                                                   var="roleLevel">
                                                                            <tr style="background-color:#383f48">
                                                                                <td width="33%"><span
                                                                                        style="color: #01c0c8;">角色层级：</span>${roleLevel.level}
                                                                                        ${roleLevel.cnName}</td>
                                                                                <td width="33%">${roleLevel.enName}</td>
                                                                                <td width="33%"><input type="radio"
                                                                                                       id="roleLevel_${roleLevel.id}"
                                                                                                       name="roleLevels_${role.id}"
                                                                                <c:if test="${moduleRoles.contains(roleLevel.id)}">
                                                                                                       checked=true </c:if>
                                                                                                       value="${roleLevel.id}">
                                                                                </td>
                                                                            </tr>
                                                                        </c:forEach>

                                                                    </table>
                                                                </td>


                                                            </tr>
                                                        </c:forEach>
                                                        </tbody>
                                                    </table>

                                                    <%--<div class="form-actions">
                                                        <div class="row">--%>
                                                    <div class="form-actions">
                                                        <div class="row">
                                                            <div class="col-md-offset-3 col-md-4">
                                                                <input name="id" value="${module.id }" type="hidden"/>
                                                                <input
                                                                        name="roleLevelsStr" id="roleLevelsStr"
                                                                        type="hidden"/>
                                                                <button type="button" class="btn btn-success" id="submitBtn">确定
                                                                </button>

                                                                <a href="moduledelete?id=${module.id}">
                                                                    <button id="delBtn" type="button"
                                                                            class="btn btn-warning">删除模块
                                                                    </button>
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div><%--<end form-actions">--%>
                                                </div>   <%--125行--%>
                                            </div> <%-- panel-body--%>
                                        </div>  <%-- panel panel-primary--%>
                                    </form>
                                    <!-- END FORM-->
                                </div> <%-- ENDcol-md-12--%>
                            </div><%--END row--%>
                        </div>   <%--END card-body--%>
                    </div>  <%--END card--%>
                 </div>
                </div>
            </div>
    <c:import url="common/footer.jsp"></c:import>
    </div>
</div>

<!-- End Wrapper -->
<c:import url="common/importJs2.jsp"></c:import>
<script type='text/javascript' src="./js/systemManagement/roleTableManage.js"></script>
<script type='text/javascript' src="./js/systemManagement/modulemanage.js"></script>
</body>
</html>