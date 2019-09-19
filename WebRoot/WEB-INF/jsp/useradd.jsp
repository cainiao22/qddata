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
                                    <form class="form-horizontal" action="./usersave" method="post" id="form">
                                        <div class="panel panel-primary">
                                            <div class="panel-body">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <h4 class="page-header m-t-0" style="margin-bottom: 15px;margin-left: 10px">基本信息</h4>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <input type="hidden" name="rolesStr" id="rolesStr"/> <input
                                                        type="hidden" name="id" id="id" value="${user.id }"/>
                                                    <div class="form-body" style="color: #6c757d">
                                                        <div class="form-group row">
                                                            <label class="col-md-3 control-label" style="
                                                                   padding-left: 12px;">用户名: </label>
                                                            <div class="col-md-9">
                                                                <c:choose>
                                                                    <c:when test="${ !empty  user}">
                                                                       <%-- <p class="form-control">${user.userName }</p>--%>
                                                                        <input type="text" name="userName" id="userName"
                                                                               class="form-control" value="${user.userName }">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input type="text" name="userName" id="userName"
                                                                               class="form-control" value="${user.userName }">
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-md-3 control-label" >姓名: </label>
                                                            <div class="col-md-9">
                                                                <input type="text"
                                                                       name="realName" class="form-control"
                                                                       value="${user.realName }" style="color: #fdfdfebd"/>
                                                            </div>
                                                        </div>

                                                        <div class="form-group row">
                                                            <label class="col-md-3 control-label">密码: </label>
                                                            <div class="col-md-9">
                                                                <c:choose>
                                                                    <c:when test="${ !empty  user}">
                                                                        <input type="text" name="password" class="form-control"
                                                                               value="">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input type="text" name="password" id="password"
                                                                               class="form-control" value="123456" style="color: #6c757d">
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-md-3 control-label">状态: </label>
                                                            <div class="col-md-9">
                                                                <select class="form-control" data-placeholder="请选择状态" name="status" style="color: #fdfdfebd">
                                                                    <option value="0" <c:if test="${0 eq user.status  }"> selected=" selected"</c:if>  >
                                                                        正常
                                                                    </option>
                                                                    <option value="1" <c:if test="${not (0  eq  user.status) }"> selected=" selected"</c:if> >
                                                                        禁用
                                                                    </option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <h4 class="page-header m-t-0">角色信息</h4>
                                                    </div>
                                                </div>
                                                <div class="row" style="color: #c7c9cc;">
                                                    <table
                                                            class="table table-striped table-bordered table-condensed table-hover">
                                                        <thead>
                                                        <tr>
                                                            <th width="33%">中文名称</th>
                                                            <th width="33%">英文名称</th>
                                                            <th width="33%">配置权限</th>
                                                        </tr>
                                                        </thead>

                                                        <c:forEach items="${roleList}" var="role">
                                                            <tr>
                                                                <td><span style="color: #01c0c8">角色：</span>${role.cnName}</td>
                                                                <td>${role.enName}</td>
                                                                <td><a id="role_${role.id}" href="javascript:void(0)">折叠</a>
                                                                    <a id="NoSelect_${role.id}" href="javascript:void(0)">不选</a> <%-- <input type="checkbox" id="role_${role.id}"
                                        name="roles" value="${role.id}"
                                        <c:if test="${userRolesSet.contains(role.id)}"> checked=true </c:if>> --%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="3">
                                                                    <table
                                                                            class="table table-striped table-bordered table-condensed table-hover"
                                                                            id="roleLevelTab_${role.id}">
                                                                        <c:forEach items="${role.subRoleLevels}" var="roleLevel">
                                                                            <tr style="background-color:#383f48">
                                                                                <td width="33%"><span
                                                                                        style="color: #01c0c8 ">角色层级：</span>${roleLevel.level}
                                                                                        ${roleLevel.cnName}</td>
                                                                                <td width="33%">${roleLevel.enName}</td>
                                                                                <td width="33%"><input type="radio"
                                                                                                       id="roleLevel_${roleLevel.id}"
                                                                                                       name="roleLevels_${role.id}"
                                                                                <c:if test="${userRoleLevelsSet.contains(roleLevel.id)}">
                                                                                                       checked=true </c:if>
                                                                                                       value="${roleLevel.id}"></td>
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
                                                            <div class="col-md-offset-3 col-md-4 ">
                                                                <button type="button" class="btn btn-success" id="submitBtn">确定</button>
                                                                <a href="./userlist">
                                                                    <button type="button" class="btn btn-warning" >取消</button>
                                                                </a>
                                                            </div>
                                                     <%--   </div>
                                                    </div>--%>
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
<%--D:\IdeaProjects\trunk\WebRoot\assets\js\systemManagement\roleTableManage.js--%>
<script type='text/javascript' src="js/systemManagement/roleTableManage.js"></script>
<script type='text/javascript' src="js/systemManagement/usermanage.js"></script>
</body>
</html>