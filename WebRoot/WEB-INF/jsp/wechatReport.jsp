<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<!-- BEGIN HEAD -->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>千丁大数据平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>

    <c:import url="common/global_style.jsp"></c:import>
</head>
<!-- END HEAD -->
<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
<div class="page-wrapper">
    <c:import url="common/header.jsp"></c:import>

    <!-- BEGIN CONTAINER -->
    <div class="page-container">
        <!-- BEGIN SIDEBAR -->
        <div class="page-sidebar-wrapper">
            <!-- BEGIN SIDEBAR -->
            <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
            <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
            <div class="page-sidebar navbar-collapse collapse">
                <!-- BEGIN SIDEBAR MENU -->
                <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
                <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
                <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
                <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
                <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
                <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
                <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true"
                    data-slide-speed="200">
                    <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
                    <!-- BEGIN SIDEBAR TOGGLER BUTTON -->

                    <c:import url="common/sidebar.jsp"></c:import>


                </ul>
                <!-- END SIDEBAR MENU -->
                <!-- END SIDEBAR MENU -->
            </div>
            <!-- END SIDEBAR -->
        </div>
        <!-- END SIDEBAR -->
        <!-- BEGIN CONTENT -->
        <div class="page-content-wrapper">
            <!-- BEGIN CONTENT BODY -->
            <div class="page-content">
                <!-- BEGIN PAGE HEADER-->

                <!-- BEGIN PAGE BAR -->
                <div class="page-bar">
                    <ul class="page-breadcrumb">
                        <li>
                            <a href="index">首页</a>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li>
                            <span>元数据</span>
                            <i class="fa fa-circle"></i>
                        </li>
                        <li>
                            <span>微信报表</span>
                        </li>
                    </ul>

                </div>
                <!-- END PAGE BAR -->
                <!-- BEGIN PAGE TITLE-->
                <h1 class="page-title"> 微信报表
                    <small></small>
                </h1>
                <!-- END PAGE TITLE-->
                <!-- END PAGE HEADER-->
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN SAMPLE TABLE PORTLET-->
                        <div class="portlet light bordered">
                            <div class="portlet-title">

                                <form action="#" method="get" class="form-inline">
                                    <div class="form-group">
                                        <label class=" control-label">状态:</label>
                                        <select id="status" class="form-control">
                                            <option value="">请选择</option>
                                            <option value="SUCCESS">成功</option>
                                            <option value="FAILED">失败</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class=" control-label">手机号:</label>
                                        <input type="text" class="form-control input-inline input-medium"
                                               placeholder="请输入手机号" id="mobile">

                                    </div>
                                    <div class="form-group">
                                        <label class=" control-label">发送日期:</label>
                                        <div class="input-group input-large date-picker input-daterange col-md-10"   data-date-format="yyyymmdd">
                                            <input type="text" class="form-control"  id="sendDate" value="${sendDate }">
                                         </div>
                                    </div>
                                    <div class="form-group">
                                        <button id="queryBtn" type="button" class="btn btn-info">搜索</button>
                                        <%--<a href="bizSourceMetaAdd">	<button id="addBtn" type="button" class="btn btn-success" >添加</button></a>--%>
                                    </div>
                                </form>
                            </div>

                        </div>
                        <div class="portlet-body">
                            <div class="table-scrollable">
                                <table class="table table-hover table-light" id="t">

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
        <!-- END CONTENT BODY -->
    </div>
    <!-- END CONTENT -->

</div>
<!-- END CONTAINER -->
<c:import url="common/footer.jsp"></c:import>
</div>
<c:import url="common/global_js.jsp"></c:import>
<script type="text/javascript">

    $(function () {
        $('#t').bootstrapTable({
            //请求方法
            method: 'get',
            //是否显示行间隔色
            striped: true,
            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            cache: false,
            //是否显示分页（*）
            pagination: true,
            //是否启用排序
            sortable: false,
            //排序方式
            //初始化加载第一页，默认第一页
            //我设置了这一项，但是貌似没起作用，而且我这默认是0,- -
            //pageNumber:1,
            //每页的记录行数（*）
            pageSize: 10,
            //可供选择的每页的行数（*）
            pageList: [10, 25, 50, 100],
            //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
            url: "queryWechatReport",
            //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
            //queryParamsType:'',
            ////查询参数,每次调用是会带上这个参数，可自定义
            queryParams: function (params) {
                return {
                    page: Math.floor(params.offset / params.limit) + 1,
                    pageCount: params.limit,
                    mobile: $('#mobile').val(),
                    sendStatus: $('#status').val(),
                    dt:$('#sendDate').val()
                };
            },
            //分页方式：client客户端分页，server服务端分页（*）
            sidePagination: "server",
            //是否显示搜索
            search: false,
            //Enable the strict search.
            strictSearch: true,
            //Indicate which field is an identity field.
            idField: "id",
            columns: [
                {
                    field: 'mobile',
                    title: '手机号',
                    align: 'center',
                    /*formatter:function(value,row,index){
                     return '<a href="bizSourceMetaManage?id='+row.id+'" target="_blank">'+value+'</a>';
                     }*/
                },
                {
                    field: 'openId',
                    title: 'openId',
                    align: 'center',
                    /*formatter:function(value,row,index){
                     return '<a href="bizSourceMetaManage?id='+row.id+'" target="_blank">'+value+'</a>';
                     }*/
                },
                {
                    field: 'sendStatus',
                    title: '发送状态',
                    align: 'center'
                },
                {
                    field: 'dt',
                    title: '发送日期',
                    align: 'center'
                },

                /*{
                    title: '更新时间',
                    field: 'updateTime',
                    align: 'center',
                    formatter: function (value, row, index) {
                        return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                    }

                },*/
                {
                    field: 'updateType',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,
                    formatter: function (value, row, index) {
                        return '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">重发</button>';
                    }
                }

            ],
            pagination: true
        });

        $('#queryBtn').click(function () {
            $('#t').bootstrapTable('removeAll');
            $('#t').bootstrapTable('refresh', {});
        });

    });
    window.operateEvents = {
        'click .RoleOfA': function (e, value, row, index) {
            $.ajax({
                type: "GET",
                url: "./sendWechatReport?openId=" + value,
                success: function (data) {
                    if (data.errcode == 0) {
                        alert("发送成功");
                    } else {
                        alert("发送失败,错误信息:" + data.errmsg + ",errorocde:" + data.errcode);
                    }
                }
            });
        },
        'click .RoleOfB': function (e, value, row, index) {

        },
        'click .RoleOfC': function (e, value, row, index) {
            alert("C");
        },
        'click .RoleOfEdit': function (e, value, row, index) {
        }
    };

</script>
</body>
</html>