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
    <link href="./assets/plugins/select2/css/select2.css" rel="stylesheet" type="text/css"/>
    <link href="../../assets/plugins/compass/collectEventInfo.css" rel="stylesheet" type="text/css"/>
    <link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>

    <style>
    </style>

</head>
<!-- END HEAD -->
<body class="fix-header fix-sidebar card-no-border">

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
                    <%--<h3 class="text-themecolor m-b-0 m-t-0">${ sidebar_menu.parentName}</h3>--%>
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
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="portlet light bordered">
                                        <div class="portlet-title">

                                            <div class="row">
                                                <div class="form-group col-md-4">
                                                    <label class="control-label">数据周期：</label>
                                                    <div class="input-daterange input-group" >
                                                        <input <%--readonly="readonly"--%> type="text" class="form-control" name="start_date" id="dpd1"/>
                                                        &nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;
                                                        <input readonly="readonly" type="text" class="form-control input-inline input-medium" name="end_date" id="dpd2"/>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="form-group col-md-4">
                                                    <label class="control-label showstyle">点击位置：</label>
                                                    <select id="source" class="collecteventinfoselect">
                                                        <option value=""></option>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-4">
                                                    <label class="control-label showstyle">点击区域所属页面：</label>
                                                    <select id="source_event_page" class="collecteventinfoselect">
                                                        <option value=""></option>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-4">
                                                    <label class="control-label showstyle">点击位页面所属区域：</label>
                                                    <select id="source_event_page_region" class="collecteventinfoselect">
                                                        <option value=""></option>
                                                    </select>
                                                </div>

                                            </div>

                                            <div class="row">
                                                <div class="form-group col-md-4">
                                                    <label class="control-label showstyle">&nbsp;事&nbsp;件&nbsp;ID：&nbsp;</label>
                                                    <select id="event_id" class="collecteventinfoselect">
                                                        <option value=""></option>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-4" style="padding-left: 47px;">
                                                    <label class="control-label showstyle">事件中文名称：</label>
                                                    <select id="event_name" class="collecteventinfoselect">
                                                        <option value=""></option>
                                                    </select>
                                                </div>
                                               <%-- <div class="col-md-4" style="float: right">
                                                    <button type="button" class="btn btn-info collecteventinfosearchbutton"
                                                            onclick="getData()">查询
                                                    </button>
                                                </div>--%>

                                                <%-- <div class="form-group col-md-5" >
                                                     <button type="button" class="btn btn-info"
                                                             onclick="getData()" style="float: right;padding-right: 15px;">查询
                                                     </button>
                                                 </div>
 --%>
                                            </div>
                                        </div>

                                        <div class="portlet-body">
                                            <div class="row">
                                                <div class="col-md-12"  style="background: RGB(29,44,51);border-radius: 8px;padding: 10px">
                                                    <div class="col-md-2" style="float: right">
                                                        <button type="button" class="btn btn-info"
                                                                onclick="getData()">查询
                                                        </button>
                                                        <button class="btn btn-info" onclick="exportExcel(1)">
                                                            <span>导出</span>
                                                        </button>
                                                    </div>
                                                    <table id="datatable" class="table table-striped table-bordered" style="background-color: #2a323c;width: 100%">
                                                        <thead>
                                                        <tr>
                                                            <th>日期</th>
                                                            <th>PV</th>
                                                            <th>UV</th>
                                                            <th>sourceEventPage</th>
                                                            <th>sourceEventPageRegion</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody></tbody>
                                                    </table>
                                                </div>
                                                <div  class="col-md-12" style="background: RGB(29,44,51);border-radius: 8px;padding: 10px;top: 8px;">
                                                    <%-- <hr style="border: 1px solid #c0c0c0">--%>
                                                    <%--<hr class="hrLine" style="width:46%;"/> 事件分析 <hr class="hrLine" style="width:46%;"/>--%>
                                                    <div class="col-md-1" style="float: right">
                                                        <button class="btn btn-info" onclick="exportExcel(2)">
                                                            <span>导出</span>
                                                        </button>
                                                    </div>
                                                    <table id="datatable2" class="table table-striped table-bordered" style="background-color: #2a323c;width: 100%">
                                                        <thead>
                                                        <tr>
                                                            <th>日期</th>
                                                            <th>PV</th>
                                                            <th>UV</th>
                                                            <th>eventId</th>
                                                            <th>eventName</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody></tbody>
                                                    </table>
                                                </div>
                                            </div>
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

<script src="./assets/plugins/modernizr.min.js"></script>
<script src="./assets/plugins/detect.js"></script>
<script src="./assets/plugins/fastclick.js"></script>
<script src="./assets/plugins/jquery.slimscroll.js"></script>
<script src="./assets/plugins/waves.js"></script>
<script src="./assets/plugins/wow.min.js"></script>
<script src="./assets/plugins/jquery.nicescroll.js"></script>
<script src="./assets/plugins/jquery.scrollTo.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="./assets/plugins/datepicker/js/bootstrap-datepicker.js"></script>
<script src="./assets/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="./assets/plugins/select2/js/select2.js"></script>
<script src="./assets/plugins/select2/js/zh-CN.js"></script>
<script type="text/javascript" src="./assets/plugins/moment-with-locales.min.js"></script>

<script>
    //页面加载下拉列表数据和时间数据
    $(function() {
        "use strict";
        moment.locale('zh-cn');
        $('#dpd1').val(moment().subtract(7, 'days').format('YYYY-MM-DD'));
        $('#dpd2').val(moment().subtract(1, 'days').format('YYYY-MM-DD'));

        //开始时间
        $('#dpd1').datepicker({
            language: "zh-CN",
            autoclose: true,
            format: 'yyyy-mm-dd',
            maxDate: "+1D",
        }).on('changeDate', function (e) {
            var date1 = new Date(Date.parse($("#dpd1").val()));
            var date2 = new Date(Date.parse($("#dpd2").val()));

            var data3=(date2-date1)/1000/60/60/24;//相差多少天

            //判断其实和结束时间相差是否大于7或者是小于0
            if(data3 > 7 || data3 < 0){
                //如果大于7或者是小于0就把结束时间设置成起始时间+7
                $('#dpd2').val(get7DaysBefore($('#dpd1').val()));
            }
        });
        //结束时间
        $('#dpd2').datepicker({
            language: "zh-CN",
            autoclose: true,
            format: 'yyyy-mm-dd',
            maxDate: "+1D",
        }).on('changeDate', function (e) {
            var date1 = new Date(Date.parse($("#dpd1").val()));
            var date2 = new Date(Date.parse($("#dpd2").val()));

            var data3=(date2-date1)/1000/60/60/24;//相差多少天

            //判断结束和开始时间相差是否大于7或者是小于0
            if(data3 > 7 || data3 < 0){
                //如果大于7或者是小于0就把开始时间设置成结束时间-7
                $('#dpd1').val(get7DaysBefore2($('#dpd2').val()));
            }

        });

        //获取7天后的日期
        function get7DaysBefore(date){
            var date = date || new Date(),
                timestamp, newDate;
            if(!(date instanceof Date)){
                date = new Date(date.replace(/-/g, '/'));
            }
            timestamp = date.getTime();

            newDate = new Date(timestamp + 7 * 24 * 3600 * 1000);

            return [[newDate.getFullYear(), zeroFill(newDate.getMonth() + 1), zeroFill(newDate.getDate())].join('-')];
        }

        //获取7天前的日期
        function get7DaysBefore2(date){
            var date = date || new Date(),
                timestamp, newDate;
            if(!(date instanceof Date)){
                date = new Date(date.replace(/-/g, '/'));
            }
            timestamp = date.getTime();

            newDate = new Date(timestamp - 7 * 24 * 3600 * 1000);

            return [[newDate.getFullYear(), zeroFill(newDate.getMonth() + 1), zeroFill(newDate.getDate())].join('-')];
        }

        //月,日 补0
        function zeroFill(i){
            if (i >= 0 && i <= 9) {
                return "0" + i;
            } else {
                return i;
            }
        }

        //加载下拉列表数据
        $.ajax({
            url: "../getCollectEventInfo",
            async:true,
            dataType: "json",
            success: function (data) {
                if(data.code == '0'){
                    console.log("data",data)
                    $("#event_id").select2({
                        data: data.event_id,
                        allowClear:true,
                        placeholder: "请选择",
                        language: "zh-CN"
                    });

                    $("#event_name").select2({
                        data: data.event_name,
                        allowClear:true,
                        placeholder: '请选择',
                        language: "zh-CN"
                    });

                    $("#source").select2({
                        data: data.source,
                        allowClear:true,
                        placeholder: '请选择',
                        language: "zh-CN"
                    });

                    $("#source_event_page").select2({
                        data: data.source_event_page,
                        allowClear:true,
                        placeholder: '请选择',
                        language: "zh-CN"
                    });

                    $("#source_event_page_region").select2({
                        data: data.source_event_page_region,
                        allowClear:true,
                        placeholder: '请选择',
                        language: "zh-CN"
                    });
                }else{
                    console.log("errorMsg",data.errorMsg);
                }
            }
        });
    });

    var postDownLoadFile = function (options) {
        var config = $.extend(true, { method: 'post' }, options);
        var $iframe = $('<iframe id="down-file-iframe" />');
        var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
        $form.attr('action', config.url);
        //?params[date]=2018-04-17&params[region]=1&titles[region_name]=城市&titles[project_id]=社区ID&titles[project_name]=社区名称
        var data = config['data'];
        for (var key in data['params']) {
            $form.append('<input type="hidden" name="params[' + key + ']" value="' + data['params'][key] + '" />');
        }
        var order = 0;
        for (var key in data['titles']) {
            $form.append('<input type="hidden" name="titles[' + key + '].name" value="' + data['titles'][key] + '" />');
            $form.append('<input type="hidden" name="titles[' + key + '].order" value="' + order + '" />');
            order += 1;
        }
        $iframe.append($form);
        $(document.body).append($iframe);
        $form[0].submit();
        $iframe.remove();
    }

    //导出数据
    function exportExcel(code) {
        var tdInfo;
        var http;
        if(code == 1){
            tdInfo = {
                "dt":'时间',
                "pv":'PV',
                "uv":'UV',
                "source":'source',
                "sourceEventPage":'sourceEventPage',
                "sourecEventPageRegion":'sourecEventPageRegion'
            };
            http = 'http://yushanfang.bigdata.qdingnet.com/dataApiExport/queryqueryCollectEventInfoBySource';
        }else{
            tdInfo =  {
                "dt":'日期',
                "pv":'PV',
                "uv":'UV',
                "eventId":'eventId',
                "eventName":'eventName',
            };
            http = 'http://yushanfang.bigdata.qdingnet.com/dataApiExport/queryCollectEventInfoByEvent';
        }

        var param = {"source":$('#source').val(),"targetEventPage":$('#source_event_page').val(),
            "sourceEventPageRegion":$('#source_event_page_region').val(),"eventId":$('#event_id').val(),
            "eventName":$('#event_name').val(),"startDate":$('#dpd1').val(),"endDate":$('#dpd2').val()};

        var mapper = tdInfo,

            param = {params:param, titles:mapper};
        postDownLoadFile({
            url:http,
            data:param,
            method:'post'
        });
        console.log(http+"?"+$.param(param));
    }

    //语言样式设置
    var i18nZH = {
        "sProcessing":   "处理中...",
        "sLengthMenu":   "显示 _MENU_ 项结果",
        "sZeroRecords":  "没有匹配结果",
        "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix":  "",
        "sSearch":       "搜索:",
        "sUrl":          "",
        "sEmptyTable":     "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "首页",
            "sPrevious": "上页",
            "sNext":     "下页",
            "sLast":     "末页"
        },
        "oAria": {
            "sSortAscending":  ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    };

    //页面加载调用查询
    $(function () {

        //后台调用接口

        $('#datatable').DataTable({
            dom: "lrtip",
            language: i18nZH,
            responsive: !0,
            "lengthChange": true,//开启显示条数
            "lengthMenu": [ 15, 50, 75, 100 ],
            "ordering": false,//禁止排序
            "deferRender": true,
            "processing": true,
            "serverSide": true,
            "scrollX": true,
            "autoWidth": true,
            "destroy": true,
            "ajax": {
                "url":"../collectEventInfoBySource",
                "type": "POST",
                "async" : true,
                "data": function (d) {
                    //删除多余请求参数
                    for(var key in d){
                        if(key.indexOf("columns")==0||key.indexOf("order")==0||key.indexOf("search")==0){ //以columns开头的参数删除
                            delete d[key];
                        }
                    }
                    var searchParams = {"source":$('#source').val(),"targetEventPage":$('#source_event_page').val(),
                        "sourceEventPageRegion":$('#source_event_page_region').val(),"eventId":$('#event_id').val(),
                        "eventName":$('#event_name').val(),"startDate":$('#dpd1').val(),"endDate":$('#dpd2').val(),
                        "calTotal":true,"pageSize":d.length,"currentPage":parseInt(d.start/d.length)+parseInt(1)};
                    //附加查询参数
                    if (searchParams) {
                        $.extend(d, searchParams); //给d扩展参数
                    }
                },
                "dataType": "json",
                "dataFilter": function (json) {//json是服务器端返回的数据
                    json = JSON.parse(json);
                    if(json.code == '0'){
                        console.log("data",json);
                        var returnData = {};
                        returnData.recordsTotal = json.total;//返回数据全部记录
                        returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = json.data;//返回的数据列表
                        return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                    }else{
                        console.log("errorMsg",json.errorMsg,json.errInfo);
                    }
                }
            },
            /*给数据添加列*/
            columns: [
                {data: 'dt'},
                {data: 'pv'},
                {data: 'uv'},
                {data: 'sourceEventPage'},
                {data: 'sourecEventPageRegion'},
            ],
        });

        $('#datatable2').DataTable({
            dom: "lrtip",
            language: i18nZH,
            responsive: !0,
            "lengthChange": true,//开启显示条数
            "lengthMenu": [ 15, 50, 75, 100 ],
            "ordering": false,//禁止排序
            "processing": true,//刷新的那个对话框
            "serverSide": true,//服务器端获取数据
            "deferRender": true,
            "scrollX": true,
            "autoWidth": true,
            "destroy": true,
            "ajax": {
                "url":"../collectEventInfoByEvent",
                "type": "POST",
                "async" : true,
                "data": function (d) {
                    //删除多余请求参数
                    for(var key in d){
                        if(key.indexOf("columns")==0||key.indexOf("order")==0||key.indexOf("search")==0){ //以columns开头的参数删除
                            delete d[key];
                        }
                    }
                    var searchParams = {"source":$('#source').val(),"targetEventPage":$('#source_event_page').val(),
                        "sourceEventPageRegion":$('#source_event_page_region').val(),"eventId":$('#event_id').val(),
                        "eventName":$('#event_name').val(),"startDate":$('#dpd1').val(),"endDate":$('#dpd2').val(),
                        "calTotal":true,"pageSize":d.length,"currentPage":parseInt(d.start/d.length)+parseInt(1)};
                    //附加查询参数
                    if (searchParams) {
                        $.extend(d, searchParams); //给d扩展参数
                    }
                },
                "dataType": "json",
                "dataFilter": function (json) {//json是服务器端返回的数据
                    json = JSON.parse(json);
                    if(json.code == '0'){
                        console.log("data",json);
                        var returnData = {};
                        returnData.recordsTotal = json.total;//返回数据全部记录
                        returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = json.data;//返回的数据列表
                        return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                    }else{
                        console.log("errorMsg",json.errorMsg,json.errInfo);
                    }
                }
            },
            /*给数据添加列*/
            columns: [
                {data: 'dt'},
                {data: 'pv'},
                {data: 'uv'},
                {data: 'eventId'},
                {data: 'eventName'},
            ],
        });
    })

    function getData() {
        var table = $("#datatable").DataTable();
        var table2 = $("#datatable2").DataTable();
        // table.destroy();
        /*  var merge=$('#merge').val();*/
        table.ajax.reload();
        table.columns.adjust();

        table2.ajax.reload();
        table2.columns.adjust();
    }

    $.fn.dataTable.ext.errMode = 'none'; //不显示任何错误信息
    //以下为发生错误时的事件处理，如不处理，可不管。
    $('#datatable').on( 'error.dt', function ( e, settings, techNote, message ){
        //这里可以接管错误处理，也可以不做任何处理
        console.log( 'An error has been reported by DataTables: ', message );
    }).DataTable();

    //以下为发生错误时的事件处理，如不处理，可不管。
    $('#datatable2').on( 'error.dt', function ( e, settings, techNote, message ){
        //这里可以接管错误处理，也可以不做任何处理
        console.log( 'An error has been reported by DataTables: ', message );
    }).DataTable();
</script>


</body>
</html>