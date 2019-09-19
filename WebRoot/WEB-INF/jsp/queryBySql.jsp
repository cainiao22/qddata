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
    <link href="./assets/plugins/codemirror/lib/codemirror.css" rel="stylesheet" type="text/css"/>
    <link href="./assets/plugins/codemirror/addon/hint/show-hint.css" rel="stylesheet"  type="text/css"/>
    <style>
        .CodeMirror {
            background: #455262;
            color: #eee;
        }
        .CodeMirror-gutters {
            border-right: 1px solid #65778d;
            background-color: #3f4b59;
        }
        .portlet .portlet-body {
            background: #323c48;
        }
        .cm-s-default .cm-number {
            color: #9E9E9E;
        }
        .cm-s-default .cm-keyword {
            color: #ffb600;
        }
        .cm-s-default .cm-variable-2 {
            color: #2196F3;
        }
        .portlet .portlet-body {
            -moz-border-radius-bottomleft: 0px;
            -moz-border-radius-bottomright: 0px;
            -webkit-border-bottom-left-radius: 0px;
            -webkit-border-bottom-right-radius: 0px;
            border-bottom-left-radius: 0px;
            border-bottom-right-radius: 0px;
        }
        .portlet {
            background: inherit;
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
                                <h4 class="card-title">SQL查询(输入时，按Alt键有提示)</h4>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="panel panel-primary">
                                        <div class="panel-body">
                                            <!-- BEGIN FORM-->
                                            <form id="form" action="queryBySqlExport" target="_blank" method="post">
                                                <input type="hidden" name="dataSource" value="1"/>
                                                <div class="portlet light  ">
                                                    <div class="portlet-body form">
                                                        <div class="form-body">
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label class="col-md-1 control-label">SQL: </label>
                                                                        <div class="col-md-9">
                                                                            <textarea id="sql" name="sql" class="form-control" rows="5" cols="26" style="margin: 10px; width: 600px; height: 180px;" ></textarea>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-body">
                                                                <div class="row">
                                                                    <div class="form-group">
                                                                        <div class="col-md-offset-3 col-md-6 ">
                                                                    <span class="col-md-6">
                                                                        <button id="submitBtn" type="button" class="btn btn-info waves-effect waves-light">执行查询</button>
                                                                    </span>
                                                                        </div>
                                                                    </div>
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
                            <div class="row">
                                <div class="col-md-12   portlet light ">
                                    <div class="portlet-body" id="table_container">

                                    </div>
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
<script src="./assets/plugins/nicescroll/jquery.nicescroll.js" type="text/javascript"></script>
<script src="./assets/plugins/codemirror/lib/codemirror.js" type="text/javascript"></script>
<script src="./assets/plugins/codemirror/mode/sql/sql.js" type="text/javascript"></script>
<script src="./assets/plugins/codemirror/addon/hint/show-hint.js"></script>
<script src="./assets/plugins/codemirror/addon/hint/sql-hint.js"></script>
<script type="text/javascript">
    $(function () {
        $("html").niceScroll({cursorborder: "1px solid #9E9E9E",cursorcolor: "#222830",});
        var mime = 'text/x-pgsql';
        window.editor = CodeMirror.fromTextArea(document.getElementById('sql'), {
            mode: mime,
            indentWithTabs: true,
            smartIndent: true,
            lineNumbers: true,
            matchBrackets: true,
            autofocus: true,
            extraKeys: {"Alt": "autocomplete"}

        });
        window.hint_tables = {};
        $.post('../dataApiQuery/getDWMeta', {}, function (res) {
                if (res.code == 0) {
                    for (var i = 0; i < res.data.length; i++) {
                        var table_name = res.data[i].type + '.' + res.data[i].table_name;
                        if (typeof(hint_tables[table_name]) == "undefined") {
                            hint_tables[table_name] = [];
                        }
                        hint_tables[table_name].push(res.data[i].field_name);
                    }
                }
                window.editor.setOption("hintOptions", {
                        tables: hint_tables

                    }
                );
            }
        );


        $(document).on('click', '#exportBtn', function () {
            $('#form').submit();
        });
        $('#submitBtn').click(function () {
            $('#table_container').empty();
            $('.portlet :first').block({message: '数据查询中,请稍候...'});
            $.post('../queryBySqlSubmit', {sql: window.editor.getValue(), dataSource: '1'}, function (data) {
                $('#table_container').html(data);
//                $('.table-scrollable').slimScroll({ height: '300px' ,width: '100%',size: '15px'});
                $(".table-scrollable").niceScroll({
                    autohidemode:false,
                    cursorwidth:'10px',
                    cursorborder: "1px solid #9E9E9E",
                    cursorcolor: "#222830",

                });
//                当在同一页面中使用多个nicescroll插件时，要及时隐藏用完的nicescroll对象，加载时，需要先show,再resize。
                $("html").getNiceScroll().show().resize();
                $('.portlet :first').unblock();
            });
        });
    });
</script>
</body>
</html>