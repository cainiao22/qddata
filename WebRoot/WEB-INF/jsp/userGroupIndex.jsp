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
        .pagination > li > a,
        .pagination > li > span {
            position: relative;
            float: left;
            padding: 6px 12px;
            line-height: 1.42857;
            text-decoration: none;
            color: #fdfdfebd;
            background-color: #272c33;
            border: 1px solid #6a6f76;
            margin-left: -1px; }
        .pagination > .active > a,
        .pagination > .active > span,
        .pagination > .active > a:hover,
        .pagination > .active > span:hover,
        .pagination > .active > a:focus,
        .pagination > .active > span:focus {
            background-color: #383f48;
            border-color: #fdfdfebd;}
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
                        <div class="card-body" style="background-color: #272c33">
                            <%--<div class="form-group">
                                <h4 class="card-title">${ sidebar_menu.name}</h4>
                            </div>--%>
                            <div class="row">
                                <div class="col-md-12">
                                    <!-- BEGIN SAMPLE TABLE PORTLET-->
                                    <div class="portlet light bordered">
                                        <div class="portlet-title">

                                            <form action="#" method="get" class="form-inline">
                                                <div class="form-group">
                                                    <label class=" control-label">关键字:</label>
                                                    <input class="form-control input-inline input-medium" placeholder="请输入关键字"
                                                           type="text" id="keyword" value="${metaTableParam.keyword }"/>
                                                </div>
                                                <div class="form-group">
                                                    <label class=" control-label">负责人:</label>
                                                    <input type="text" class="form-control input-inline input-medium"
                                                           placeholder="请输入负责人" id="createUser"/>

                                                </div>
                                                <div class="form-group">
                                                    <button id="queryBtn" type="button" class="btn btn-info">搜索</button>
                                                    <%--<a href="#">	<button id="addBtn" type="button" class="btn btn-success" >添加</button></a>--%>
                                                </div>
                                            </form>
                                        </div>

                                    </div>
                                    <div class="portlet-body">
                                        <div class="table-scrollable">
                                            <table class="table table-hover table-light" id="t" style="width:100%; ">

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
<script src="./assets/plugins/bootstrap-table/bootstrap-table.min.js" type="text/javascript"></script>
<script src="./assets/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js" type="text/javascript"></script>
<script type="text/javascript" src="./js/userProfile/userGroup/config.js"></script>
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
            url: "listUserGroup",
            //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
            //queryParamsType:'',
            ////查询参数,每次调用是会带上这个参数，可自定义
            queryParams: function (params) {
                return {
                    page: Math.floor(params.offset / params.limit) + 1,
                    pageCount: params.limit,
                    createUser: $('#createUser').val(),
                    keyword: $('#keyword').val()
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
                    field: 'name',
                    title: '群组名称',
                    align: 'center',
                    valign:'middle',
                    width:'11%'
                    /*formatter:function(value,row,index){
                     return '<a href="dataApiDetail?commonDataSummaryId='+row.commonDataSummaryId+'">'+value+'</a>';
                     }*/
                },
                {
                    field: 'isShare',
                    title: '是否共享',
                    align: 'center',
                    valign:'middle',
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return "公开共享";
                        } else {
                            return "个人私有";
                        }
                    },
                    width:'9%'
                },
                {
                    field: 'modModel',
                    title: '模式',
                    align: 'center',
                    valign:'middle',
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return "简单模式";
                        } else {
                            return "高级模式";
                        }
                    },
                    width:'9%'
                },
                {
                    field: 'status',
                    title: '状态',
                    align: 'center',
                    valign:'middle',
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return "正常";
                        } else {
                            return "禁用";
                        }
                    },
                    width:'6%'
                },
                {
                    field: 'createUser',
                    title: '创建人',
                    align: 'center',
                    valign:'middle'
                },
                /*{
                 field: 'updateUser',
                 title: '更新人',
                 align: 'center'
                 },*/
                {
                    field: 'conditions',
                    title: '查询条件',
                    align: 'left',
                    formatter: function (value, row, index) {
                        var i = 0;
                        var result = '';
                        var step = 40;
                        if(value != null){
                            while (i < value.length) {
                                result += '<p>' + value.substring(i, i + step < value.length ? i + step : value.length) + '</p>';
                                i += step;
                            }
                        }
                        return result;
                    }
                },
                {
                    field: 'updateTime',
                    title: '更新时间',
                    align: 'center',
                    valign:'middle',
                    formatter: function (value, row, index) {
                        return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                    }
                },

                {
                    field: 'id',
                    title: '操作',
                    align: 'center',
                    valign:'middle',
                    events: operateEvents,
                    formatter: function (value, row, index) {
                        return '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">删除</button>';
                    }
                },

                /*{
                 title: '更新时间',
                 field: 'updateTime',
                 align: 'center',
                 formatter:
                 function(value,row, index) {
                 return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                 }

                 },*/

            ],
            //是否显示分页（*）
            pagination: true
        });

        $('#queryBtn').click(function () {
            $('#t').bootstrapTable('removeAll');
            $('#t').bootstrapTable('refresh', {});
        });

    });

    window.operateEvents = {
        'click .RoleOfA': function (e, value, row, index) {
            if (window.confirm("确认要删除这个群组吗?")) {
                window.location.href = "deleteUserGroup?id=" + value;
            }
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
