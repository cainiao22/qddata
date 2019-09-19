<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<!--添加标签页面-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
	<style>
		.form-group label{
			line-height: 38px;
			text-align: center;
		}
		.tag-instance{
			display: inline-flex;
		}
		#example_id{width: 200px;margin-right: 10px}
		#example_name{width: 200px;margin-right: 10px}


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
                <form  class="form-horizontal" action="./savePortraitTag" method="post" id="form">
	                <div class="form-group row">
		                <label  class="col-md-3 col-form-label">父级标签:</label>
		                <div class="col-md-5">
			                <select id="p_module" name="pid" class="form-control">
				                <option value='1'>系统顶级</option>
				                <c:forEach items="${PortraittagList}" var="tagTemp">
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
                            <input type="text" name="name" id="name" class="form-control" value=""/>
                        </div>
                    </div>
	                <!--标识必填-->
                    <div class="form-group row">
                        <label class="col-md-3 control-label">标签标识: </label>
                        <div class="col-md-5">
                            <input type="text" name="tag" id="tag" class="form-control" placeholder="标识必填" required/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">标签类型: </label>

                        <div class="col-md-5">
                            <select id="dataType" class="form-control" name="dataType">
                                <option value='1'>文本</option>
	                            <option value='2'>json</option>
	                            <option value='3'>列表</option>
	                            <option value='4'>日期</option>
	                            <option value='5'>数值</option>
                            </select>
                        </div>
                    </div>

	                <div class="form-group row">
		                <label class="col-md-3 control-label">排序数值: </label>
		                <div class="col-md-5">
			                <input type="text" name="sortNo" id="sortNo" class="form-control" value="">
		                </div>
	                </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">规则描述: </label>
                        <div class="col-md-5">
                            <textarea name="description" id="desc" class="form-control" rows="5" cols="16" style="height: 80px;"></textarea>
                        </div>
                    </div>
                    <div class="form-group row" style="position: absolute;left: 50%; bottom: 40px;">
                        <button id="submitBtn" type="submit" class="btn btn-success">添加</button>
                    </div>
                </form>
            </div>
        </div>
        <c:import url="common/footer.jsp"></c:import>
    </div><!-- END page wrapper -->
</div>
<!-- END main wrapper -->
<c:import url="common/importJs2.jsp"></c:import>

</body>
</html>