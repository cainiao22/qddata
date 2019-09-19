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
    <link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>
    <link href="./assets/plugins/select2/css/select2.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        body{color:#fdfdfe;}
        .dataTable td {
            white-space: nowrap !important;
            text-align: center;
        }

        .dataTable th {
            white-space: nowrap !important;
            text-align: center;
        }

        .paging_simple_numbers .pagination .paginate_button {
            padding: 0px;
            background: #2f363efc;
        }

        .datepicker th {
            font-size: 14px !important;
            color: #ffffff;
        }

        .datepicker tfoot tr th:hover {
            background: #2a323c;
        }

        .datepicker table tr td.day.focused {
            background: #2a323c;
            cursor: pointer;
        }

        .datepicker table tr td.today, .datepicker table tr td.today:hover, .datepicker table tr td.today.disabled, .datepicker table tr td.today.disabled:hover {
            background-color: #3b4655;
            background-image: linear-gradient(to bottom, #3b4655, #3b4655);
        }

        .datepicker table tr td.range.today:hover, .datepicker table tr td.range.today:hover:hover, .datepicker table tr td.range.today.disabled:hover, .datepicker table tr td.range.today.disabled:hover:hover, .datepicker table tr td.range.today:active, .datepicker table tr td.range.today:hover:active, .datepicker table tr td.range.today.disabled:active, .datepicker table tr td.range.today.disabled:hover:active, .datepicker table tr td.range.today.active, .datepicker table tr td.range.today:hover.active, .datepicker table tr td.range.today.disabled.active, .datepicker table tr td.range.today.disabled:hover.active, .datepicker table tr td.range.today.disabled, .datepicker table tr td.range.today:hover.disabled, .datepicker table tr td.range.today.disabled.disabled, .datepicker table tr td.range.today.disabled:hover.disabled, .datepicker table tr td.range.today[disabled], .datepicker table tr td.range.today:hover[disabled], .datepicker table tr td.range.today.disabled[disabled], .datepicker table tr td.range.today.disabled:hover[disabled] {
            background-image: linear-gradient(to bottom, #3c4857, #3c4857);
        }

        .datepicker table tr td.day:hover, .datepicker table tr td.day.focused {
            background: #2a323c;
            cursor: pointer;
        }

        .datepicker table tr td.range, .datepicker table tr td.range:hover, .datepicker table tr td.range.disabled, .datepicker table tr td.range.disabled:hover {
            background: #3c4857;
        }

        .datepicker .datepicker-switch:hover, .datepicker .prev:hover, .datepicker .next:hover, .datepicker tfoot tr th:hover {
            background: #2a323c;
        }

        .datepicker table tr td.active, .datepicker table tr td.active:hover, .datepicker table tr td.active.disabled, .datepicker table tr td.active.disabled:hover {
            background-color: #2a323c !important;
        }

        .row {
            position: relative;
        }

        .input-group {
            width: 86%;
            display: -webkit-inline-box;
            display: inline-box;
        }

        .input-group > .form-control {
            min-width: 120px;
            height: 38px;
            line-height: 38px;
            text-align: center;
            border: 1px solid #6a7077;
        }

        .table td, .table th {
            padding: 0.3rem 0.75rem 0.3rem;
            vertical-align: top;
        }

        .page-titles {
            padding-bottom: 0px;
        }

        .btn {
            width: 80px;
            height: 30px;
        }

        .item {
            position: relative;
            overflow: hidden;
            -webkit-box-align: center;
            align-items: center;
            text-align: left;
            padding: 10px 15px;
        }

        .flex,.buttonCheck {
            display: flex;
        }

        .flex div{
            flex: 1;
            text-align: center;
        }

        #projectNames {
            width: 100px;
            height: 38px;
            line-height: 38px;
            display: inline-block;
        }

        .tabValue {
            line-height: 30px;
            float: left;
            color: #fdfdfe;
        }

        #projectInfo .item:nth-child(odd),#detailInfo .item:nth-child(odd){
            background-color: #2a323c;
        }

        #projectInfo .item:nth-child(even),#detailInfo .item:nth-child(even){
            background-color: #383f48;
        }

        .form-group {
            line-height: 38px;
        }

        .form-group label {
            float: left;
            display: inline-block;
            margin-right: 5px;
        }

        .errorTips {
            text-align: center;
            padding: 10px;
            background-color: #2a323c;
            display: none;
        }

        #curPage{
            width: 60px;
            line-height: 20px;
            text-align: center;
        }

        /*公共弹窗*/
        .pubMask {
            display: none;
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
            z-index: 8;
        }

        .pubPopup {
            display: none;
            width: 100%;
            z-index: 999;
            position: fixed;
            top: 40%;
            left: 0;
            opacity: 1;
        }

        .pubPopup .popCon {
            width:200px;
            max-width: 100%;
            background: #fff;
            border-radius: 8px;
            color: #000;
            font-size: 14px;
            margin: 0 auto;
            padding: 1em;
            text-align: center;
        }
        .buttonCheck{
            margin-bottom: 20px;
        }
        .buttonCheck .but{float: left}
        .buttonCheck .export{position: absolute;right: 0;}
        .detailTab{display: none;}
        .btn-default{border: 1px solid #ccc;border-radius: 5px;}
        .but .btn-default,.but .btn-info{line-height: 17px;}
        .fenye{float:right;font-size: 14px;text-align: center;}
        .fenye div{margin: 0 10px; display: inline-block;float: left;line-height: 30px;}


        .dataTables_scrollHead {
            overflow: hidden;
            position: relative;
            border: 0px;
            width: 100%;
            height: 51px;
        }
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

            <div class="col-md-12">
                <div class="card">
                    <div class="card-body" style="padding:0;">
                        <div class="col-md-13">
                            <div class="portlet-title">

                                <div class="row">
                                    <div class="form-group col-md-6 ">
                                        <label>日期:</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" name="start_date" id="startDate"/>
                                            &nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
                                            <input type="text" class="form-control" name="end_date" id="endDate"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>产品名称:</label>
                                        <select id="projectNames" class="form-control" style="width: 150px">
                                           <option value=""></option>
                                           <%-- <c:choose>
                                                <c:when test="${not empty sources}">
                                                    <c:forEach items="${sources}" var="source">
                                                        <option value="${source.key}" ${source.key  eq  'jiashicang'?'selected' : '' }>${source.value}</option>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="jiashicang" selected="selected">驾驶舱</option>
                                                    <option value="luopan">罗盘</option>
                                                    <option value="ribao">日报</option>
                                                    <option value="yuebao">月报</option>
                                                    <option value="shujukuijia">数据盔甲</option>
                                                </c:otherwise>
                                            </c:choose>--%>
                                        </select>

                                    </div>
                                    <div class="form-group col-md-3">
                                        <div class="btn btn-info fa fa-search" onclick="getUserCount()">查询</div>
                                        &nbsp;&nbsp;
                                        <div class="btn btn-info fa fa-file-excel-o" onclick="getExportDate()">导出</div>
                                    </div>
                                </div>

                            </div>
                            <div class="buttonCheck">
                                <div class="but">
                                    <div class="btn btn-info" id="tongji" onclick="javascript:swichBtn(this.id);">统计数据</div>
                                    <div class="btn btn-default" id="mingxi" onclick="javascript:swichBtn(this.id);">明细数据</div>
                                </div>
                            </div>
                            <div class="section">
                                <table id="datatable-section" class="table table-striped table-bordered" style="background-color: #2a323c;width: 100%">
                                    <thead>
                                    <tr style="height: 51px;font-weight: 600;">
                                        <td>产品名称</td>
                                        <td>PV</td>
                                        <td>UV</td>
                                    </tr>
                                    </thead>
                                    <tbody id="projectInfo"></tbody>
                                </table>
                                <div class="errorTips"></div>
                            </div>
                            <div class="detailTab">
                                <table id="datatable-detailTab" class="table table-striped table-bordered" style="background-color: #2a323c;width: 100%">
                                    <thead>
                                    <tr style="height: 51px;font-weight: 600;">
                                        <td>用户名</td>
                                        <td>姓名</td>
                                        <td>登陆次数</td>
                                        <td>PV</td>
                                        <td>点击事件次数</td>
                                        <td>停留时间</td>
                                    </tr>
                                    </thead>
                                    <tbody id="detailInfo"></tbody>
                                </table>
                                <div class="errorTips"></div>
                            </div>





                            <div class="item fenye">
                                <div style="cursor: pointer;" onclick="prevPage()">上一页</div>
                                <div>第&nbsp;<%--<input type="text" id="curPage">--%>
                                    <span id="curPage"></span>
                                    &nbsp;页</div>
                                <div style="cursor: pointer;" onclick="nextPage()">下一页</div>
                            </div>
                            <!-- END SAMPLE TABLE PORTLET-->
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

<!--公共弹窗 toast提示-->
<div class="pubMask"></div>
<div class="pubPopup">
    <div class="popCon"></div>
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
<script type="text/javascript" src="./assets/plugins/moment-with-locales.min.js"></script>
<script src="./assets/plugins/select2/js/select2.js"></script>
<script src="./assets/plugins/select2/js/zh-CN.js"></script>
<script>
    $(function () {
//加载下拉列表数据
       $.ajax({
            url: "../getByEventType",
            async:true,
            dataType: "json",
            success: function (data) {
                console.log("data",data)
                if(data.length>0){
                    var HTML = "<option value=''>请选择</option>";
                    data.forEach(function (c,index) {
                            HTML += "<option value='"+c.eventId+"'>"+c.eventName+"</option>";
                    })
                    $('#projectNames').empty().append(HTML);

                }else{
                    console.log("errorMsg",data.errorMsg);
                }
            }
        });
    $('#startDate').val(moment().subtract(6, 'days').format('YYYY-MM-DD'));
    $('#endDate').val(moment().subtract(0, 'days').format('YYYY-MM-DD'));

    //开始时间
    $('#startDate').datepicker({
        language: "zh-CN",
        autoclose: true,
        format: 'yyyy-mm-dd',
        todayHighlight: true,
        endDate: '0d'
    }).on('changeDate', function (e) {

        var date1 = new Date(Date.parse($("#startDate").val()));
        var date2 = new Date(Date.parse($("#endDate").val()));

        if (date1 > date2) {
            $("#endDate").val($("#startDate").val());
            var startTime = e.date;
            $('#endDate').datepicker('setStartDate', startTime);
        } else {
            var startTime1 = e.date;
            $('#endDate').datepicker('setStartDate', startTime1);
        }
    });
    //结束时间
    $('#endDate').datepicker({
        language: "zh-CN",
        autoclose: true,
        format: 'yyyy-mm-dd',
        todayHighlight: true,
        endDate: '0d'
    }).on('changeDate', function (e) {

        var date1 = new Date(Date.parse($("#startDate").val()));
        var date2 = new Date(Date.parse($("#endDate").val()));

        if (date2 < date1) {
            $("#startDate").val($("#endDate").val());
            var endTime = e.date;
            $('#startDate').datepicker('setEndDate', endTime);
        } else {
            var endTime1 = e.date;
            $('#startDate').datepicker('setEndDate', endTime1);
        }
    });
    });
    //查询所有数据
  /*  var currentPage = 1; //当前页
    var pageSize = 20; //每页记录
    var totalCount = 0;  //总条数
    var footPage = 0;    //总页数*/
    var page = 1; //当前页
    var pageCount = 20; //每页记录
    function queryPersonCount() {
        $("#curPage").text(page);
        $('#datatable-section').DataTable({
            dom: "lrtip",
            language: i18nZH,
            responsive: !0,
            "destroy": true,
            bPaginate:false,
            bFilter:false,
            bInfo:false,
            "lengthChange": false,//开启显示条数
            "lengthMenu": [15, 50, 75, 100],
            "ordering": false,//禁止排序
            "deferRender": true,//延迟渲染
            "processing": true,//处理状态
            "scrollX": true,//控制在列过多过宽是,是否出现水平滚动条.注意使用这个参数时最好关闭响应式设计
            "serverSide": true,//当设为true时,列表的过滤,搜索和排序信息会传递到Server端进行处理,实现真翻页方案的必需属性.反之,所有的列表功能都在客户端计算并执行
            "autoWidth": false,//定义是否由控件自动控制列宽
            "ajax": {
                "url": "../queryTrackSummary",
                "type": "POST",
                "async": true,
                "data": function (d) {
                    //删除多余请求参数
                    for (var key in d) {
                        if (key.indexOf("columns") == 0 || key.indexOf("order") == 0 || key.indexOf("search") == 0) { //以columns开头的参数删除
                            delete d[key];
                        }
                    }
                    var searchParams = {
                        "startDate": $('#startDate').val(),
                        "endDate": $('#endDate').val(),
                        "secondSource": $("#projectNames option:selected").val(),
                        "calTotal": true,
                        "pageCount": pageCount,
                        "page": page
                    };
                    //附加查询参数
                    if (searchParams) {
                        $.extend(d, searchParams); //给d扩展参数
                    }
                },
                "dataType": "json",
                "dataFilter": function (json) {//json是服务器端返回的数据
                    json = JSON.parse(json);
                    if (json.code == '0') {
                        console.log("data", json);
                        var returnData = {};
                        //  returnData.recordsTotal = json.total;//返回数据全部记录
                        //  returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = json.data;//返回的数据列表
                        if(json.data.length==0){
                            paging=false;
                        }else{
                            paging=true;
                        }
                        return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                    } else {
                        console.log("errorMsg", json.errorMsg, json.errInfo);
                    }
                }
            },
            /*给数据添加列*/
            columns: [
                {data: 'secSource'},
                {data: 'pv'},
                {data: 'uv'}
            ],
        });
    }

    var paging = true;     //判断是否分页

    //点击查询
    function getUserCount() {
        page = 1;
        if($(".detailTab").css("display")==="block"){
            queryPersonCountAll();
        }else{
            queryPersonCount();
        }
    }
    //上一页
    function prevPage() {
        if (page > 1) {
            page = page - 1;
            if($(".detailTab").css("display")==="block"){
                queryPersonCountAll();
            }else{
                queryPersonCount();
            }
        } else {
            popEffect("已经是第一页");
        }
    }
    //下一页
    function nextPage() {

        if (paging) {
            page = page + 1;
            if($(".detailTab").css("display")==="block"){
                queryPersonCountAll();
            }else{
                queryPersonCount();
            }
         } else {
            popEffect("已经是最后一页");
        }
    }
    //3秒弹窗
    function popEffect(text) {
        var times = 2;
        $('.pubPopup,.pubMask').show();
        $('.pubPopup .popCon').text(text);
        var i = setInterval(function () {
            times--;
            if (times == 1) {
                $('.pubPopup,.pubMask').addClass('popFadeOut');
            }
            if (times == 0) {
                clearInterval(i);
                $('.pubPopup,.pubMask').hide().removeClass('popFadeOut');
                times = 2;
            }
        }, 1000);
    }
    //点击导出
    function getExportDate() {
       var param = {
            "startDate": $('#startDate').val(),
            "endDate": $('#endDate').val(),
            "secondSource": $("#projectNames option:selected").val()
        };
        if($(".detailTab").css("display")==="block"){//明细
            var mapper = {
                    "user": '用户名',
                    "userName": '姓名',
                    "loginCount": '登陆次数',
                    "pv": 'pv',
                    "eventCount": '点击事件次数',
                    "onlineTime": '停留时间'
                },
                param = {params: param, titles: mapper};
            postDownLoadFile({
                url: '../exportTrackDetails',
                data: param,
                method: 'post'
            });
        }else{//统计
            var mapper = {
                    "secSource": '产品名称',
                    "pv":'pv',
                    "uv": 'uv'
                },
                param = {params: param, titles: mapper};
            postDownLoadFile({
                url: '../exportTrackSummary',
                data: param,
                method: 'post'
            });
        }
    }

    var postDownLoadFile = function (options) {
        var config = $.extend(true, {method: 'post'}, options);
        var $iframe = $('<iframe id="down-file-iframe" />');
        var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
        $form.attr('action', config.url);
        var data = config['data'];
        for (var key in data['params']) {
            $form.append('<input type="hidden" name="params.' + key + '" value="' + data['params'][key] + '" />');
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

    //切换按钮
    function swichBtn(id) {
        page = 1;
        if(id==="tongji"){
            $("#tongji").removeClass("btn-default").addClass("btn-info");
            $("#mingxi").removeClass("btn-info").addClass("btn-default");
            $(".section").show().siblings(".detailTab").hide();
            queryPersonCount($('#startDate').val(), $('#endDate').val(), $("#projectNames option:selected").val());
        }else{
            $("#tongji").removeClass("btn-info").addClass("btn-default");
            $("#mingxi").removeClass("btn-default").addClass("btn-info");
            $(".detailTab").show().siblings(".section").hide();
            queryPersonCountAll($('#startDate').val(), $('#endDate').val(), $("#projectNames option:selected").val());
        }
    }

    //查询所有

    function queryPersonCountAll() {
        $("#curPage").text(page);
        $('#datatable-detailTab').DataTable({
            dom: "lrtip",
            language: i18nZH,
            responsive: !0,
            "destroy": true,
            bPaginate:false,
            bFilter:false,
            bInfo:false,
            "lengthChange": false,//开启显示条数
            "lengthMenu": [15, 50, 75, 100],
            "ordering": false,//禁止排序
            "deferRender": true,//延迟渲染
            "processing": true,//处理状态
            "scrollX": true,//控制在列过多过宽是,是否出现水平滚动条.注意使用这个参数时最好关闭响应式设计
            "serverSide": true,//当设为true时,列表的过滤,搜索和排序信息会传递到Server端进行处理,实现真翻页方案的必需属性.反之,所有的列表功能都在客户端计算并执行
            "autoWidth": false,//定义是否由控件自动控制列宽
            "ajax": {
                "url": "../queryTrackDetails",
                "type": "POST",
                "async": true,
                "data": function (d) {
                    //删除多余请求参数
                    for (var key in d) {
                        if (key.indexOf("columns") == 0 || key.indexOf("order") == 0 || key.indexOf("search") == 0) { //以columns开头的参数删除
                            delete d[key];
                        }
                    }
                    var searchParams = {
                        "startDate": $('#startDate').val(),
                        "endDate": $('#endDate').val(),
                        "secondSource": $("#projectNames option:selected").val(),
                        "calTotal": true,
                        "pageCount": pageCount,
                        "page": page
                    };
                    //附加查询参数
                    if (searchParams) {
                        $.extend(d, searchParams); //给d扩展参数
                    }
                },
                "dataType": "json",
                "dataFilter": function (json) {//json是服务器端返回的数据
                    json = JSON.parse(json);
                    if (json.code == '0') {
                        console.log("data", json);
                        var returnData = {};
                        //  returnData.recordsTotal = json.total;//返回数据全部记录
                        //  returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = json.data;//返回的数据列表
                        if(json.data.length==0){
                            paging=false;
                        }else{
                            paging=true;
                        }
                        return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                    } else {
                        console.log("errorMsg", json.errorMsg, json.errInfo);
                    }
                }
            },
            /*给数据添加列*/
            columns: [
                {data: 'user'},
                {data: 'userName'},
                {data: 'loginCount'},
                {data: 'pv'},
                {data: 'eventCount'},
                {data: 'onlineTime'},
            ],
        });
    }

    //语言样式设置
     var i18nZH = {
         "sProcessing":   "处理中...",
         "sLengthMenu":   "显示  _MENU_  项结果",
         "sZeroRecords":  "没有匹配结果",
         "sInfo":         "共 _TOTAL_ 条记录",
         "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 条记录",
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
        //queryPersonCount($('#startDate').val(), $('#endDate').val(), $("#projectNames option:selected").val());
        //后台调用接口
        queryPersonCount();
        queryPersonCountAll();

    });


</script>
</body>
</html>