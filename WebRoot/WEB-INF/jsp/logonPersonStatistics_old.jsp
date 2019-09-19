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
                                    <div class="form-group col-md-7 ">
                                        <label>日期:</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" name="start_date" id="startDate"/>
                                            &nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
                                            <input type="text" class="form-control" name="end_date" id="endDate"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>项目:</label>
                                        <select id="projectNames" class="form-control" style="width: 110px">
                                            <%--<option value="jiashicang" selected="selected">驾驶舱</option>
                                            <option value="luopan">罗盘</option>--%>
                                            <c:choose>
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
                                            </c:choose>
                                        </select>

                                    </div>
                                    <div class="form-group col-md-2">
                                        <div class="btn btn-info fa fa-search" onclick="getUserCount()">查询</div>
                                    </div>
                                </div>

                            </div>
                            <div class="buttonCheck">
                                <div class="but">
                                    <div class="btn btn-info" id="tongji" onclick="javascript:swichBtn(this.id);">统计数据</div>
                                    <div class="btn btn-default" id="mingxi" onclick="javascript:swichBtn(this.id);">明细数据</div>
                                </div>
                                <div class="export"><div class="btn btn-info fa fa-file-excel-o" onclick="getExportDate()">导出</div></div>
                            </div>


                            <div class="section">
                                <div class="item flex">
                                    <div class="tabValue">序号</div>
                                    <div class="tabValue">姓名</div>
                                    <div class="tabValue">登录天数</div>
                                    <div class="tabValue">次数</div>
                                    <div class="tabValue">项目名称</div>
                                </div>
                                <div id="projectInfo"></div>
                                <div class="errorTips"></div>
                            </div>

                            <div class="detailTab">
                                <div class="item flex">
                                    <div class="tabValue">序号</div>
                                    <div class="tabValue">日期</div>
                                    <div class="tabValue">姓名</div>
                                    <div class="tabValue">次数</div>
                                    <div class="tabValue">项目名称</div>
                                </div>
                                <div id="detailInfo"></div>
                                <div class="errorTips"></div>
                            </div>

                            <div class="item fenye">
                                <div style="cursor: pointer;" onclick="prevPage()">上一页</div>
                                <div>第&nbsp;<%--<input type="text" id="curPage">--%>
                                    <span id="curPage"></span> / <span id="allPage"></span>
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
<script>
    $('#startDate').val(moment().subtract(6, 'days').format('YYYY-MM-DD'));
    $('#endDate').val(moment().subtract(0, 'days').format('YYYY-MM-DD'));
    $(document).ready(function () {
        queryPersonCount($('#startDate').val(), $('#endDate').val(), $("#projectNames option:selected").val());
    });
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

    //查询所有数据
    var currentPage = 1; //当前页
    var pageSize = 20; //每页记录
    var totalCount = 0;  //总条数
    var footPage = 0;    //总页数
    function queryPersonCount(startTime, endTime, source) {
        $("#projectInfo,.errorTips").html("");
        $("#curPage").text(currentPage);
        if (startTime && endTime && source) {
            $.ajax({
                url: "../queryPersonCount?startTime=" + startTime + "&endTime=" + endTime + "&source=" + source,
                type: "get",
                dataType: "json",
                success: function (res) {
                    //console.log(JSON.stringify(res));
                    if (res != null && res.code == 0) {
                        if (res.list != null && res.list.length > 0) {
                            pagination(res.list,"section");
                        } else {
                            $(".errorTips").text("暂无数据～").show();
                        }
                    } else {
                        $(".errorTips").text("暂无数据～").show();
                    }
                },
                error: function () {
                    $(".errorTips").text("暂无数据～").show();
                }
            });
        } else {
            $(".errorTips").text("系统异常，请稍后重试～").show();
        }
    }

    var number = 0;     //开始基数
    function pagination(list,name) {
        var str = "";
        var listLen = 0;
        totalCount = list.length;
        footPage = Math.ceil(totalCount / pageSize);
        $("#allPage").text(footPage);
        if (currentPage != 1) {
            if (currentPage > footPage) {
                currentPage = null;
            }
        }
        if (currentPage != null) {
            if (totalCount <= pageSize) {
                listLen = totalCount;
            } else {
                if (currentPage < footPage) {
                    listLen = currentPage * pageSize;
                } else {
                    listLen = totalCount;
                }
            }
            for (var i = number; i < listLen; i++) {
                var date = list[i].date;         //日期
                var user = list[i].user;         //唯一id
                var userName = list[i].userName; //中文名
                var source = list[i].source;      //来源
                var pv = list[i].pv;              //登录次数
                var dayNumber = list[i].dayNumber;//登录天数

                str += '<div class="item flex">';
                str += '<div class="tabValue">' + (i + 1) + '</div>';
                if(name === "detailTab"){
                    str += '<div class="tabValue">' + date + '</div>';
                }
                str += '<div class="tabValue">' + userName + '</div>';
                if(name === "section"){
                    str += '<div class="tabValue">' +  dayNumber + '</div>';
                }
                if(source ==="日报"){
                    str += '<div class="tabValue">' +Math.ceil(pv/10) + '</div>';
                }else {
                    str += '<div class="tabValue">' + pv + '</div>';
                }
                str += '<div class="tabValue">'+source+ '</div>';
                str += '</div>';
            }
            if(name === "detailTab") {
                $("#detailInfo").append(str);
            }else{
                $("#projectInfo").append(str);
            }
        }
    }
    //点击查询
    function getUserCount() {
        currentPage = 1;
        footPage = 0;
        totalCount = 0 ;
        number=0;
        if($(".detailTab").css("display")==="block"){
            queryPersonCountAll($('#startDate').val(), $('#endDate').val(), $("#projectNames option:selected").val());
        }else{
            queryPersonCount($('#startDate').val(), $('#endDate').val(), $("#projectNames option:selected").val());
        }
    }
    //上一页
    function prevPage() {
        if (currentPage > 1) {
            currentPage = currentPage - 1;
            number = parseInt((currentPage - 1) * pageSize);
            if($(".detailTab").css("display")==="block"){
                queryPersonCountAll($('#startDate').val(), $('#endDate').val(), $("#projectNames option:selected").val());
            }else{
                queryPersonCount($('#startDate').val(), $('#endDate').val(), $("#projectNames option:selected").val());
            }
        } else {
            popEffect("已经是第一页");
        }
    }
    //下一页
    function nextPage() {
        if (currentPage < footPage) {
            currentPage = currentPage + 1;
            number = parseInt((currentPage - 1) * pageSize);
            if($(".detailTab").css("display")==="block"){
                queryPersonCountAll($('#startDate').val(), $('#endDate').val(), $("#projectNames option:selected").val());
            }else{
                queryPersonCount($('#startDate').val(), $('#endDate').val(), $("#projectNames option:selected").val());
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
            "startTime": $('#startDate').val(),
            "endTime": $('#endDate').val(),
            "source": $("#projectNames option:selected").val()
        };
        /*  var title = {"region_id":'姓名',"region_name":'次数',"project_id":'项目名称'},*/

        if($(".detailTab").css("display")==="block"){//明细
            var mapper = {
                    "userName": '姓名',
                    "pv": '次数',
                    "source": '项目名称',
                    "date": '日期'
                },
                param = {params: param, titles: mapper};
            postDownLoadFile({
                url: '../exportAllPersonCount',
                data: param,
                method: 'post'
            });
        }else{//统计
            var mapper = {
                    "userName": '姓名',
                    "dayNumber": '登录天数',
                    "pv": '次数',
                    "source": '项目名称'
                },
                param = {params: param, titles: mapper};
            postDownLoadFile({
                url: '../exportPersonCount',
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

    //切换按钮
    function swichBtn(id) {
        currentPage = 1;
        number = 0;
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
    function queryPersonCountAll(startTime, endTime, source) {
        $("#detailInfo,.errorTips").html("");
        $("#curPage").text(currentPage);
        if (startTime && endTime && source) {
            $.ajax({
                url: "../queryPersonCountAll?startTime=" + startTime + "&endTime=" + endTime + "&source=" + source,
                type: "get",
                dataType: "json",
                success: function (res) {
                    //console.log(JSON.stringify(res));
                    if (res != null && res.code == 0) {
                        if (res.list != null && res.list.length > 0) {
                            pagination(res.list,"detailTab");
                        } else {
                            $(".errorTips").text("暂无数据～").show();
                        }
                    } else {
                        $(".errorTips").text("暂无数据～").show();
                    }
                },
                error: function () {
                    $(".errorTips").text("暂无数据～").show();
                }
            });
        } else {
            $(".errorTips").text("系统异常，请稍后重试～").show();
        }
    }


</script>
</body>
</html>