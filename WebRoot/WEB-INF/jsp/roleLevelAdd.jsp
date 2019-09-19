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
    <!-- END SIDEBAR -->
    <!-- BEGIN CONTENT -->
    <div class="page-wrapper">
        <div class="container-fluid">
            <div class="row page-titles">
                <div class="col-md-6 col-8 align-self-center">
                    <h3 class="text-themecolor m-b-0 m-t-0">${ sidebar_menu.parentName}</h3>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="javascript:void(0)">${ sidebar_menu.parentName}</a></li>
                        <li class="breadcrumb-item active">${ sidebar_menu.name} </li>
                    </ol>
                </div>
            </div>

            <%--<div class="">
                <div class="page-header-title"><h4 class="page-title">角色层级信息</h4></div>
            </div>--%>
            <%-- <div class="page-content-wrapper ">--%>
            <%-- <div class="container">--%>
            <div class="row portlet light bordered">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-body">
                            <div class="form-group">
                                <h4 class="card-title">角色层级信息</h4>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <!-- BEGIN FORM-->
                                    <form class="form-horizontal" action="./roleLevelSave" method="post" id="form">
                                        <div class="portlet light bordered">
                                            <div class="portlet-body form">
                                                <div class="form-body" style="color: #fdfdfebd">
                                                    <div class="form-group row">
                                                        <label class="col-md-3 control-label">中文名称: </label>
                                                        <div class="col-md-4">
                                                            <input style="color: #fdfdfebd" type="text" name="cnName"
                                                                   id="cnName"
                                                                   class="form-control" value="${roleLevel.cnName }">
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-md-3 control-label">英文名称: </label>
                                                        <div class="col-md-4">
                                                            <input style="color: #fdfdfebd" type="text" id="enName"
                                                                   name="enName" class="form-control"
                                                                   value="${roleLevel.enName}"/>
                                                        </div>
                                                    </div>

                                                    <div class="form-group row">
                                                        <label class="col-md-3 control-label">所属角色: </label>
                                                        <div class="col-md-4">
                                                            <select id="p_role" name="roleId" class="form-control"
                                                                    data-placeholder="请选择所属角色" style="color: #fdfdfebd">
                                                                <c:forEach items="${roleAllList}" var="role">

                                                                    <option value='${ role.id}'
                                                                            <c:if test="${ role.id eq  roleLevel.roleId }"> selected=selected </c:if>>
                                                                            ${role.cnName}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-md-3 control-label">层级序号: </label>
                                                        <div class="col-md-4">
                                                            <select id="level" name="level" class="form-control"
                                                                    data-placeholder="请选择层级序号" style="color: #fdfdfebd">
                                                                <c:forEach var="level" begin="1" end="5">

                                                                    <option value='${level}'
                                                                            <c:if test="${ roleLevel.level eq  level}"> selected=selected </c:if>>
                                                                            ${level}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <%--</div>--%>
                                                    <c:if test="${ !empty  roleLevel}">

                                                        <div class="portlet-title">
                                                            <div class="caption">
                                                                <i class="font-red-sunglo"></i>
                                                                <span class="caption-subject font-red-sunglo bold uppercase">关联用户</span>
                                                            </div>
                                                        </div>

                                                        <div class="portlet-body form">
                                                            <div class="form-body">
                                                                <div class="form-group row">
                                                                    <div class="col-md-3">
                                                                        <select id="itemall2" multipleOri="true"
                                                                                class="form-control"
                                                                                multiple="multiple" size="10"
                                                                                style="width: 220px">
                                                                            <c:forEach items="${userListAll }"
                                                                                       var="user">
                                                                                <option style="color: #fdfdfebd" value="${user.id }">${user.realName }</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <p><input type="button"
                                                                                  value="选择" id="selbtn2"
                                                                                  class="btn btn-info"/>
                                                                        </p>
                                                                        <p><input type="button" value="全选" id="allbtn2"
                                                                                  class="btn btn-info"/>
                                                                        </p>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <select id="itemsel2" multipleOri="true"
                                                                                class="form-control"
                                                                                multiple="multiple" size="10"
                                                                                style="width: 220px">
                                                                            <c:forEach items="${userListInRole }"
                                                                                       var="user">
                                                                                <option style="color: #fdfdfebd" value="${user.id }">${user.realName }</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <p>
                                                                            <input
                                                                                    type="button" value="删除"
                                                                                    id="delbtn2"
                                                                                    class="btn btn-info"/>
                                                                                <%--&lt;%&ndash; --%>
                                                                        </p><%--&ndash;%&gt;--%>
                                                                        </p><input
                                                                            type="button" value="清空" id="emptybtn2"
                                                                            class="btn btn-info"/></label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div >
                                                            <div class="portlet-title">
                                                                <div class="caption">
                                                                    <i class="font-red-sunglo"></i>
                                                                    <span class="caption-subject font-red-sunglo bold uppercase">关联权限</span>
                                                                    <br>
                                                                </div>
                                                            </div>
                                                            <div class="portlet-body form">
                                                                <div class="form-body" >
                                                                    <div class="zTreeDemoBackground"
                                                                         style="padding-left: 100px;">
                                                                        <ul id="tree" class="ztree" style="color: #fdfdfebd"></ul>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:if>

                                                    <%--</div>--%>
                                                    <%-- </div>--%>
                                                    <br>
                                                   <%-- <div class="col-md-offset-3 col-md-4">
                                                        <input type="hidden" name="id" id="id"
                                                               value="${roleLevel.id }"/>
                                                        <input type="hidden" name="modulesStr" id="modules"/> <input
                                                            type="hidden" name="usersStr" id="users"/>
                                                        <button type="button" class="btn green" id="submitBtn">确定
                                                        </button>
                                                        <a href="rolelist">
                                                            <button type="button" class="btn default">取消</button>
                                                        </a>
                                                    </div>--%>
                                                    <div class="portlet-body form">
                                                        <div class="form-actions">
                                                            <div class="row">
                                                                <div class="col-md-offset-3 col-md-4">
                                                                    <input type="hidden" name="id" id="id"
                                                                           value="${roleLevel.id  }"/>
                                                                    <input type="hidden" name="modulesStr" id="modules"/> <input
                                                                        type="hidden" name="usersStr" id="users"/>
                                                                    <button type="button" class="btn btn-success"
                                                                            id="submitBtn">确定
                                                                    </button>
                                                                    <a href="rolelist">
                                                                        <button type="button" class="btn btn-warning ">
                                                                            取消
                                                                        </button>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <%-- </div>--%>
                                            </div>
                                                <%--</div>--%>
                                    </form>
                                    <!-- END FORM-->
                                </div>

                            </div>
                        </div>
                    </div>
                    <!-- BEGIN FORM-->
                    <%--  <form class="form-horizontal" action="./roleLevelSave" method="post" id="form">
                          <div class="panel panel-primary portlet-body form">
                              <div class="panel-body">
                                  <div class="row">
                                      <div class="col-sm-12">
                                          <h4 class="page-header m-t-0">基本信息</h4>
                                      </div>
                                  </div>

                                  <div class="row">
                                      <div class="form-group">
                                          <label class="col-md-3 control-label">中文名称: </label>
                                          <div class="col-md-4">
                                              <input type="text" name="cnName" id="cnName"
                                                     class="form-control" value="${roleLevel.cnName }">
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="col-md-3 control-label">英文名称: </label>
                                          <div class="col-md-4">
                                              <input type="text" id="enName"
                                                     name="enName" class="form-control"
                                                     value="${roleLevel.enName}"/>
                                          </div>
                                      </div>

                                      <div class="form-group">
                                          <label class="col-md-3 control-label">所属角色: </label>
                                          <div class="col-md-4">
                                              <select id="p_role" name="roleId" class="form-control"
                                                      data-placeholder="请选择所属角色">
                                                  <c:forEach items="${roleAllList}" var="role">

                                                      <option value='${ role.id}'
                                                              <c:if test="${ role.id eq  roleLevel.roleId }"> selected=selected </c:if>>
                                                              ${role.cnName}</option>
                                                  </c:forEach>
                                              </select>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="col-md-3 control-label">层级序号: </label>
                                          <div class="col-md-4">
                                              <select id="level" name="level" class="form-control"
                                                      data-placeholder="请选择层级序号">
                                                  <c:forEach var="level" begin="1" end="5">

                                                      <option value='${level}'
                                                              <c:if test="${ roleLevel.level eq  level}"> selected=selected </c:if>>
                                                              ${level}</option>
                                                  </c:forEach>
                                              </select>
                                          </div>
                                      </div>
                                  </div>
                                  <c:if test="${ !empty  roleLevel}">

                                      <div class="portlet-title">
                                          <div class="caption">
                                              <i class="font-red-sunglo"></i>
                                              <span class="caption-subject font-red-sunglo bold uppercase">关联用户</span>
                                          </div>
                                      </div>

                                      <div class="portlet-body form">

                                          <div class="form-body">
                                              <div class="form-group">
                                                  <div class="col-md-3">
                                                      <select id="itemall2" multipleOri="true"
                                                              class="form-control"
                                                              multiple="multiple" size="10" style="width: 220px">
                                                          <c:forEach items="${userListAll }" var="user">
                                                              <option value="${user.id }">${user.realName }</option>
                                                          </c:forEach>
                                                      </select>
                                                  </div>
                                                  <div class="col-md-3">
                                                      <p><input type="button"
                                                                value="选择" id="selbtn2" class="btn btn-info"/></p>
                                                      <p><input
                                                              type="button" value="全选" id="allbtn2"
                                                              class="btn btn-info"/>
                                                      </p>
                                                  </div>
                                                  <div class="col-md-3">
                                                      <select id="itemsel2" multipleOri="true"
                                                              class="form-control"
                                                              multiple="multiple" size="10" style="width: 220px">
                                                          <c:forEach items="${userListInRole }" var="user">
                                                              <option value="${user.id }">${user.realName }</option>
                                                          </c:forEach>


                                                      </select>
                                                  </div>
                                                  <div class="col-md-3">
                                                      <p>
                                                          <input
                                                                  type="button" value="删除" id="delbtn2"
                                                                  class="btn btn-info"/>
                                                    &lt;%&ndash;  </p>&ndash;%&gt;
                                                      </p><input
                                                              type="button" value="清空" id="emptybtn2"
                                                              class="btn btn-info"/></label>
                                                  </div>
                                              </div>
                                          </div>
                                      </div>


                                      <div class="portlet-title">
                                          <div class="caption">
                                              <i class="font-red-sunglo"></i>
                                              <span class="caption-subject font-red-sunglo bold uppercase">关联权限</span>
                                          </div>
                                      </div>
                                      <div class="portlet-body form">

                                          <div class="form-body">

                                              <div class="zTreeDemoBackground" style="padding-left: 100px;">
                                                  <ul id="tree" class="ztree"></ul>
                                              </div>
                                          </div>
                                      </div>
                                  </c:if>
                                  <div class="portlet-body form">
                                      <div class="form-actions">
                                          <div class="row">
                                              <div class="col-md-offset-3 col-md-4">
                                                  <input type="hidden" name="id" id="id"
                                                         value="${roleLevel.id }"/>
                                                  <input type="hidden" name="modulesStr" id="modules"/> <input
                                                      type="hidden" name="usersStr" id="users"/>
                                                  <button type="button" class="btn green" id="submitBtn">确定
                                                  </button>
                                                  <a href="rolelist">
                                                      <button type="button" class="btn default">取消</button>
                                                  </a>
                                              </div>
                                          </div>
                                      </div>
                                  </div>
                              </div>
                          </div>
                      </form>--%>
                    <!-- END FORM-->
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
<link rel='stylesheet' type='text/css' href='./assets/plugins/zTree/zTreeStyle.css'/>

<c:import url="common/importJs2.jsp"></c:import>
<script type="text/javascript" src="./assets/plugins/zTree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="./assets/plugins/zTree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript">
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    setting.check.chkboxType = {"Y": "p", "N": "s"};
    var zNodes = [];
    <c:forEach items="${moduleList}" var="module">
    zNodes.push({
        id: "${module.id}", pId: "${module.pid}", name: "${module.moduleName}", open: true
        <c:if test="${roleModulesSet.contains(module.id)}">, checked: true </c:if>
    });
    </c:forEach>

    $(document).ready(function () {
        $.fn.zTree.init($("#tree"), setting, zNodes);
    });


</script>
<%--D:\IdeaProjects\trunk\WebRoot\js\rolelevelmanage.js--%>
<%--D:\IdeaProjects\trunk\WebRoot\js\systemManagement\rolelevelmanage.js--%>
<script type='text/javascript' src="js/systemManagement/rolelevelmanage.js"></script>

</body>
</html>