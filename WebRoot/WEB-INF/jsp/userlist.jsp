<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        .pagination > li > a,
        .pagination > li > span {
            position: relative;
            float: left;
            padding: 6px 12px;
            line-height: 1.42857;
            text-decoration: none;
            color:#c7c9cc;
            background-color: #272c33;
            border: 1px solid #6a6f76;
            margin-left: -1px;
        }
        .pagination > .active > a,
        .pagination > .active > span,
        .pagination > .active > a:hover,
        .pagination > .active > span:hover,
        .pagination > .active > a:focus,
        .pagination > .active > span:focus {
            background-color: #383f48;
            border-color: #6a6f76;
        }

        .block{
           /* padding-top: 0%;
            padding-right: 84%;*/
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
                                    <!-- BEGIN SAMPLE TABLE PORTLET-->
                                    <div class="portlet light bordered">
                                        <div class="portlet-title">


                                            <form action="./userlist" method="post" class="form-inline" style="margin-bottom: 15px">

                                                <div class="form-group" style="margin-left: 30px;color: #c7c9cc">
                                                    <label class=" control-label">用户名:</label>
                                                    <input class="form-control input-inline input-medium" placeholder="请输入用户名"
                                                           type="text" name="userName" id="queryByUserName"
                                                           value="${userParam.userName }"/>
                                                </div>
                                                <div class="form-group">
                                                    <label class=" control-label" style="margin-left: 66px;color:#fdfdfebd">姓名:</label>
                                                    <input class="form-control input-inline input-medium" placeholder="请输入姓名"
                                                           type="text" name="realName" id="queryByRealName"
                                                           value="${userParam.realName }"/>
                                                </div>

                                                <div class="form-group">
                                                    <button id="queryBtn" type="submit" class="btn btn-info" style="margin-left: 170px">搜索</button>
                                                    <a href="./useradd">
                                                        <button id="addBtn" type="button" class="btn btn-success" style="margin-left: 45px">添加</button>
                                                    </a>
                                                </div>
                                        </form>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="table-scrollable">
                                            <table class="table table-hover table-light" style="background-color: #212529; color: #6c757d;">
                                                <thead>
                                                <tr  style="color: #c7c9cc">
                                                    <th>用户名</th>
                                                    <th>姓名</th>
                                                    <th>创建时间</th>
                                                    <th>更新时间</th>
                                                    <th>状态</th>
                                                    <th>管理</th>

                                                </tr>
                                                </thead>
                                                <tbody style="color: #c7c9cc">
                                                <c:forEach items="${userList}" var="user">
                                                    <tr>
                                                        <td>${user.userName}</td>
                                                        <td>${user.realName}</td>
                                                        <td><fmt:formatDate value="${user.createTime}"
                                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        <td><fmt:formatDate value="${user.updateTime}"
                                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        <c:choose>
                                                            <c:when test="${user.status eq 0 }">
                                                                <td><span class="label label-success">正常</span></td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td><span class="label label-warning">禁用</span></td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <td><a href="./usermanage?id=${user.id}">修改</a> | <a
                                                                href="userdelete?id=${user.id}" onclick="return deleteConfirm()">删除</a></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>

                                        </div>

                                        <div>
                                            <c:import url="common/pagination.jsp">
                                                <c:param name="currentPage" value="${userParam.page}"/>
                                                <c:param name="totalPage" value="${totalPage }"/>
                                                <c:param name="totalCount" value="${totalCount }"/>
                                                <c:param name="url"
                                                         value="./userlist?username=${userParam.userName}&realname=${userParam.realName}&"/>
                                            </c:import>
                                        </div>
                                    </div>
                                </div>
                                <!-- END SAMPLE TABLE PORTLET-->
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
