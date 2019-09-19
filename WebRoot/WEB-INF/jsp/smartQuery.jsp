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
    <link href="./assets/plugins/select2/css/select2.css" rel="stylesheet" type="text/css"/>
    <c:import url="common/importCss2.jsp"></c:import>
    <style>
        input {
            margin: 0;
            font-family: inherit;
            font-size: inherit;
            /* line-height: inherit; */
        }
        .select2-container--default .select2-selection--single .select2-selection__rendered {
            line-height: 35px;
        }
        .select2-container--default .select2-results__option[aria-selected=true] {
            background-color: #e4e3e83b;
        }
        .select2-container .select2-selection--single {
            box-sizing: border-box;
            cursor: pointer;
            display: block;
            /*    height: 28px;*/
            user-select: none;
            -webkit-user-select: none; }
        .select2-container .select2-selection--single .select2-selection__rendered {
            /*     display: block;*/
            padding-left: 8px;
            padding-right: 20px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap; }
        .btn.yellow:not(.btn-outline){
            color: #fff;
            background-color: #c49f47;
            border-color: #c49f47;
        }
        .btn.red:not(.btn-outline) {
            color: #fff;
            background-color: #e7505a;
            border-color: #e7505a;
        }
        .form-control {
          /*  color: #ffffff;*/
            min-height: 38px;
            /* display: initial; */
        }
        .close {
            float: right;
            /* font-size: 1.5rem; */
            font-weight: 700;
            line-height: 1;
            color: #000;
            /* text-shadow: 0 1px 0 #fff; */
            opacity: .5;
        }
        .tokenfield .token {
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
            border-radius: 3px;
            display: inline-block;
            border: 1px solid #6c757d;
            background-color: #383f48;
            white-space: nowrap;
            margin: -1px 5px 5px 0;
            height: 22px;
            vertical-align: top;
            cursor: default;
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
                <div class="col-md-6 col-4 align-self-center">
                    <button class="right-side-toggle waves-effect waves-light btn-info btn-circle btn-sm pull-right m-l-10">
                        <i class="ti-settings text-white"></i></button>
                </div>
            </div>
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- Start Page Content -->
            <!-- Row -->
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-body">
                            <div class="portlet light  ">
                                <div class="portlet-body">
                                    <div class="form-group">
                                        <h4 class="card-title">数据集:</h4>
                                        <div class="col-md-9">
                                            <select class="form-control select2" id="metaTableSelect">
                                                <option value="-1">请选择</option>
                                                <c:forEach items="${metaTableList}" var="metaTable">
                                                    <option value="${metaTable.id }" <c:if
                                                            test="${metaTableParam.id eq metaTable.id }"> selected=" selected"</c:if> >${metaTable.alias }(${metaTable.type}.${metaTable.name})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${not empty metaFieldList }">
                        <form id="form" action="smartQueryExport" target="_blank" method="post"
                              style="display: none;">
                            <input id="fullTableName" name="fullTableName"
                                   value="${metaTableParam.type}.${metaTableParam.name}" type="hidden"/>
                            <input id="tableId" name="tableId" value="${metaTableParam.id}" type="hidden"/>
                            <input id="groupBy" name="groupBy" value="" type="hidden"/>
                            <input id="metric" name="metric" value="" type="hidden"/>
                            <input id="filter" name="filter" value="" type="hidden"/>
                            <input id="limit" name="limit" value="" type="hidden"/>
                        </form>
                        <div class="portlet light  ">

                            <div class="portlet-body">
                                <div class="form-group">
                                    <label class="col-md-1 control-label">维度: </label>
                                    <div class="col-md-9">
                                        <select class="form-control select2"  id="metaFieldSelect1"
                                                multiple="multiple" style="visibility: hidden;">
                                            <c:forEach items="${metaFieldList}" var="metaField">
                                                <c:if test="${ metaField.isDim eq 1}">
                                                    <option value="${metaField.name }">${metaField.alias }</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="portlet light  ">
                            <div class="portlet-body">
                                <div class="form-group">
                                    <label class="col-md-1 control-label">指标: </label>
                                    <div class="col-md-9">
                                        <select class="form-control select2" id="metaFieldSelect2"
                                                multiple="multiple" style="visibility: hidden;">
                                            <c:forEach items="${metaFieldList}" var="metaField">
                                                <c:if test="${ metaField.isMetric eq 1}">
                                                    <c:choose>
                                                        <c:when test="${metaField.isVirtual eq 1}">
                                                            <option value="${metaField.name }">${metaField.alias }</option>
                                                        </c:when>
                                                        <c:when test="${metaField.isVirtual eq 0}">
                                                            <optgroup label="${metaField.alias }">
                                                                <c:if test="${metaField.dataType eq 0 }">
                                                                    <option value=" count(${metaField.name })">${metaField.alias }【计数】</option>
                                                                    <option value=" count( distinct ${metaField.name })">${metaField.alias }【去重计数】</option>
                                                                </c:if>
                                                                <c:if test="${metaField.dataType eq 1 }">
                                                                    <option value=" count(${metaField.name })">${metaField.alias }【计数】</option>
                                                                    <option value=" count( distinct ${metaField.name })">${metaField.alias }【去重计数】</option>
                                                                    <option value=" sum(${metaField.name })">${metaField.alias }【求和】</option>
                                                                    <option value=" agv(  ${metaField.name })">${metaField.alias }【平均数】</option>
                                                                    <option value=" max(   ${metaField.name })">${metaField.alias }【最大值】</option>
                                                                    <option value=" min(   ${metaField.name })">${metaField.alias }【最小值】</option>
                                                                </c:if>
                                                                <c:if test="${metaField.dataType eq 2 }">
                                                                    <option value=" count(${metaField.name })">${metaField.alias }【计数】</option>
                                                                    <option value=" count( distinct ${metaField.name })">${metaField.alias }【去重计数】</option>
                                                                </c:if>
                                                            </optgroup>
                                                        </c:when>
                                                    </c:choose>

                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="portlet light  ">
                            <div class="portlet-body">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">筛选: </label>
                                </div>
                            </div>
                            <div class="portlet-body " id="filterFiv">
                                <div class="form-group">
                                    <div class="col-md-9 col-md-offset-1">
                                        <button class="btn btn-info" id="addFilterBtn">添加</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                            <div id="alert" style="height: auto;width: auto"></div>

                        <div class="portlet light  ">
                            <div class="portlet-body">
                                <div class="form-group">
                                    <label class="col-md-1 control-label">限制: </label>
                                    <div class="col-md-9">
                                        <div class="col-md-3">
                                            <select class="form-control " id="limit_size">
                                                <option value="1000">1000行</option>
                                                <option value="5000">5000行</option>
                                                <option value="10000">10000行</option>
                                                <option value="50000">50000行</option>
                                                <option value="100000">100000行</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="portlet light  ">
                            <div class="portlet-body">
                                <div class="portlet-body ">
                                    <div class="form-group">
                                        <div class="col-md-9 col-md-offset-1">
                                            <button type="button" class="btn red  btn-xlg" id="queryBtn"> 执行查询
                                            </button>
                                        </div>
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
                    </c:if>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Row -->
            <!-- End PAge Content -->
            <!-- Right sidebar -->
            <div class="right-sidebar">
                <div class="slimscrollright">
                    <div class="rpanel-title"> Service Panel <span><i class="ti-close right-side-toggle"></i></span>
                    </div>
                    <div class="r-panel-body">
                        <ul id="themecolors" class="m-t-20">
                            <li><b>With Light sidebar</b></li>
                            <li><a href="javascript:void(0)" data-theme="default" class="default-theme">1</a></li>
                            <li><a href="javascript:void(0)" data-theme="green" class="green-theme">2</a></li>
                            <li><a href="javascript:void(0)" data-theme="red" class="red-theme">3</a></li>
                            <li><a href="javascript:void(0)" data-theme="blue" class="blue-theme">4</a></li>
                            <li><a href="javascript:void(0)" data-theme="purple" class="purple-theme">5</a></li>
                            <li><a href="javascript:void(0)" data-theme="megna" class="megna-theme">6</a></li>
                            <li class="d-block m-t-30"><b>With Dark sidebar</b></li>
                            <li><a href="javascript:void(0)" data-theme="default-dark" class="default-dark-theme">7</a>
                            </li>
                            <li><a href="javascript:void(0)" data-theme="green-dark" class="green-dark-theme">8</a></li>
                            <li><a href="javascript:void(0)" data-theme="red-dark" class="red-dark-theme">9</a></li>
                            <li><a href="javascript:void(0)" data-theme="blue-dark" class="blue-dark-theme">10</a></li>
                            <li><a href="javascript:void(0)" data-theme="purple-dark" class="purple-dark-theme">11</a>
                            </li>
                            <li><a href="javascript:void(0)" data-theme="megna-dark"
                                   class="megna-dark-theme working">12</a></li>
                        </ul>
                        <ul class="m-t-20 chatonline">
                            <li><b>Chat option</b></li>
                            <li>
                                <a href="javascript:void(0)"><img src="./assets/images/users/1.jpg" alt="user-img"
                                                                  class="img-circle"> <span>Varun Dhavan <small
                                        class="text-success">online</small></span></a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"><img src="./assets/images/users/2.jpg" alt="user-img"
                                                                  class="img-circle"> <span>Genelia Deshmukh <small
                                        class="text-warning">Away</small></span></a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"><img src="./assets/images/users/3.jpg" alt="user-img"
                                                                  class="img-circle"> <span>Ritesh Deshmukh <small
                                        class="text-danger">Busy</small></span></a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"><img src="./assets/images/users/4.jpg" alt="user-img"
                                                                  class="img-circle"> <span>Arijit Sinh <small
                                        class="text-muted">Offline</small></span></a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"><img src="./assets/images/users/5.jpg" alt="user-img"
                                                                  class="img-circle"> <span>Govinda Star <small
                                        class="text-success">online</small></span></a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"><img src="./assets/images/users/6.jpg" alt="user-img"
                                                                  class="img-circle"> <span>John Abraham<small
                                        class="text-success">online</small></span></a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"><img src="./assets/images/users/7.jpg" alt="user-img"
                                                                  class="img-circle"> <span>Hritik Roshan<small
                                        class="text-success">online</small></span></a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"><img src="./assets/images/users/8.jpg" alt="user-img"
                                                                  class="img-circle"> <span>Pwandeep rajan <small
                                        class="text-success">online</small></span></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- End Right sidebar -->
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
<script src="./assets/plugins/bootstrap-tokenfield/bootstrap-tokenfield.min.js" type="text/javascript"></script>
<script src="./assets/plugins/select2/js/select2.js"></script>
<script type='text/javascript'>
    $(function () {
        var filterHtml = ' <div class="portlet-body"><div class="form-group row"><div class="col-md-offset-1 col-md-3"><select name="filterSel" class="form-control select2" ><c:forEach items="${metaFieldList}" var="metaField">    <c:if test="${ metaField.isFilter eq 1}"><option value="${metaField.name }">${metaField.alias }</option></c:if></c:forEach></select></div><div class="col-md-2"><select class="form-control select2"><option value="0">包含</option><option value="1">不包含</option><option value="2">大于</option><option value="3">大于等于</option><option value="4">小于</option><option value="5">小于等于</option></select></div><div class="col-md-3"><input type="text"  name="filters"  class="form-control  input-medium "  /></div><div class="col-md-1">  <button type="button" class="btn yellow  btn" name="deleteBtn">删除</button></div></div></div></div>';
        $('.select2').select2({}
        );
        $('.select2').css('visibility', 'visible');
        $('#metaTableSelect').change(function () {
            if ($(this).val() != '-1') {
                window.location.href = 'smartQuery?id=' + $(this).val();
            }
        });

        $('body').on('click', 'button[name=deleteBtn]', function () {

            $(this).parent().parent().parent().remove();
        });
        $('#addFilterBtn').click(function () {
            $('#filterFiv').before(filterHtml);

            $('[name=filters]:last').tokenfield({
                createTokensOnBlur: true,
                delimiter: [","],
                showAutocompleteOnFocus: true
            });
            $('select[name=filterSel]:last').select2({}
            );
        });
        $(document).on('click', '#exportBtn', function () {
            $('#form').submit();
        });
        $('#queryBtn').click(function () {
            $('#alert').block({message: '数据查询中,请稍候...'});
            var groupBy = $("#metaFieldSelect1").select2("data");
            var groupByData = '';
            for (var i = 0; i < groupBy.length; i++) {
                groupByData = groupByData + groupBy[i].id + ":" + groupBy[i].text + "€";
            }
            var metric = $("#metaFieldSelect2").select2("data");
            var metricData = '';
            for (var i = 0; i < metric.length; i++) {
                metricData = metricData + metric[i].id + ":" + metric[i].text + "€";
            }
            if (groupByData == '' && metricData == '') {
                $('#alert').unblock();
                swal("提示!", "维度、指标至少选择一个.", "warning");
                return false;
            }
            var filterData = '';
            $('[name=filters]').each(function () {
                var curFilter = $(this).parent().parent().prev().prev().children().first().find("option:selected").val()
                curFilter += "#$#";
                curFilter += $(this).parent().parent().prev().children().first().val();
                curFilter += "#$#";
                curFilter += $(this).tokenfield('getTokensList', '^ ', false);
                filterData += '€' + curFilter;
            });
            $('#table_container').empty();
            var limit = $('#limit_size').val();
            $.post('../smartQuerySubmit', {
                fullTableName: '${metaTableParam.type}.${metaTableParam.name}',
                tableId: '${metaTableParam.id}',
                groupBy: groupByData,
                metric: metricData,
                filter: filterData,
                limit: limit
            }, function (data) {
                $('#table_container').html(data);

                $('#alert').unblock();

            });
            $('#groupBy').val(groupByData);
            $('#metric').val(metricData);
            $('#filter').val(filterData);
            $('#limit').val(limit);
        });
    });
</script>
</body>
</html>