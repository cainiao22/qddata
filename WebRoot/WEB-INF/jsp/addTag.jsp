<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <!-- BEGIN SIDEBAR -->
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
    <!-- END SIDEBAR -->
    <!-- BEGIN page-wrapper -->
    <div class="page-wrapper">
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
            <div >
                <!-- BEGIN FORM-->
                <form  class="form-horizontal" action="./saveTag" method="post" id="form">
                    <div class="form-group row">
                        <label  class="col-md-3 col-form-label">父级标签:</label>
                        <div class="col-md-5">
                            <select id="p_module" name="pid" class="form-control">
                                <option value='-1'>系统顶级</option>
                                <c:forEach items="${allTreeTags}" var="tagTemp">
                                    <option value='${ tagTemp.id}'
                                            <c:if test="${ tagTemp.id eq  tag.pid }"> selected=selected </c:if>>
                                        <c:forEach begin="1" end="${tagTemp.level}">------</c:forEach>
                                            ${tagTemp.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">标签名称: </label>
                        <div class="col-md-5">
                            <input type="text" name="name" class="form-control" value="${tag.name }"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">标签标识: </label>
                        <div class="col-md-5">
                            <input type="text" name="tag" class="form-control" value="${tag.tag }"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">顺序编码: </label>
                        <div class="col-md-5">
                            <input type="text" name="sortNo" class="form-control" value="${tag.sortNo }">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">标签类型: </label>
                        <div class="col-md-5">
                            <select id="dataType" class="form-control" name="dataType">
                                <option value='0'
                                        <c:if test="${ tag.dataType eq 0 }"> selected=selected </c:if>>
                                    虚拟
                                </option>
                                <option value='1'
                                        <c:if test="${ tag.dataType eq  1 }"> selected=selected </c:if>>
                                    数值
                                </option>
                                <option value='2'
                                        <c:if test="${ tag.dataType eq 2 }"> selected=selected </c:if>>
                                    列表
                                </option>
                                <option value='3'
                                        <c:if test="${ tag.dataType eq 3 }"> selected=selected </c:if>>
                                    日期
                                </option>
                                <option value='4'
                                        <c:if test="${ tag.dataType eq 4 }"> selected=selected </c:if>>
                                    字典
                                </option>

                            </select>
                        </div>
                    </div>
                    <%--<div class="form-group row">
                        <label class="col-md-3 control-label">简单模式: </label>
                        <div class="col-md-5">
                            <select id="simpleModle" class="form-control" name="simpleModle">
                                <option value='0'
                                        <c:if test="${ tag.simpleModle eq 0 }"> selected=selected </c:if>>
                                    否
                                </option>
                                <option value='1'
                                        <c:if test="${ tag.simpleModle eq  1 }"> selected=selected </c:if>>
                                    是
                                </option>
                            </select>
                        </div>
                    </div>--%>
                    <%--<div class="form-group row">
                        <label class="col-md-3 control-label">常用标签: </label>
                        <div class="col-md-5">
                            <select id="commonConditon" class="form-control" name="commonConditon">
                                <option value='0'
                                        <c:if test="${ tag.commonConditon eq 0 }"> selected=selected </c:if>>
                                    否
                                </option>
                                <option value='1'
                                        <c:if test="${ tag.commonConditon eq  1 }"> selected=selected </c:if>>
                                    是
                                </option>
                            </select>
                        </div>
                    </div>--%>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">标签描述: </label>
                        <div class="col-md-5">
                                <textarea name="description" class="form-control" rows="5" cols="16" style="width: 435px; height: 59px;">
                                    ${tag.description}
                                </textarea>
                        </div>
                    </div>
                    <div class="form-group row" style="position: absolute;left: 50%; bottom: 40px;">
                        <input name="id" value="${tag.id }" type="hidden"/>
                        <button id="submitBtn" type="submit" class="btn btn-success">
                            提交
                        </button>
                        <c:if test="${ !empty  tag}">
                            <a href="deleteTag?id=${tag.id}">
                                <button id="delBtn" type="button" class="btn btn-warning">
                                    删除标签
                                </button>
                            </a>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
        <c:import url="common/footer.jsp"></c:import>
    </div><!-- END page wrapper -->
</div>
<!-- END main wrapper -->
<c:import url="common/importJs2.jsp"></c:import>
<script type='text/javascript' src="./js/systemManagement/roleTableManage.js"></script>
<script type='text/javascript' src="./js/systemManagement/usermanage.js"></script>
</body>
</html>