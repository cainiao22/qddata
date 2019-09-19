<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!--观测台--丁管家-终端分析-终端型号-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="/WEB-INF/jsp/common/importCss2.jsp"></c:import>
    <link rel="stylesheet" type="text/css" href="./assets/plugins/datatables/datatables.min.css" />
    <link rel="stylesheet" type="text/css" href="./css/guancetaiCommon.css">
    <style>
        table thead th, table thead td{
            border-right:1px solid #dee2e6;
            border-bottom: 0;
            text-align: center;
        }
        table.dataTable thead td, table.dataTable tfoot th{
            font-weight: normal;
            border-top: 1px solid #dee2e6;
            border-right: 1px solid #dee2e6;
            border-bottom:1px solid #dee2e6;
        }
        table.dataTable.no-footer,.dataTables_wrapper.no-footer .dataTables_scrollBody{
            border-bottom:1px solid #dee2e6;
        }
        .tableInfo .dataTables_length{
            display: none;
        }
        .tableInfo .form-control{
            background-color: #F5F6FA;
            color: #8B93A7;
            min-height: 24px;
        }
        .table thead{
            background: #F5F6FA;
            border-radius: 2px;
        }
        .dataTables_scroll .dataTables_scrollBody table thead{visibility: hidden;height: 0;}
        .dataTables_scroll .dataTables_scrollHead{margin-bottom: -22px}
        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            color: white !important;
            background: #fff;
        }
        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            color: white;
            border:0;
            background-color: transparent;
        }
    </style>
</head>
<body>
    <!--header-->
<header>
    <nav>
        <div class="logo"><img src="./images/logo.png" /></div>
        <div class="tabControl">
            <a href="./guancetai"><div data-type="0"><i class="qdLogo_n"></i>千丁App</div></a>
            <a href="./clientType_dgj"><div data-type="1" class="active"><i class="dgjLogo"></i>丁管家</div></a>
        </div>
    </nav>
</header>
    <!--leftBar begin-->
<aside id="sidenav">
    <div class="scrollnav">
        <div class="leftPanel">
            <nav>
                <ul id="sidebar">
            <li>
                <div class="iconClass" id="tjIcon">
                    <i class="tjIcon_n"></i><span>基本统计</span><em class="arrowdown_n"></em>
                </div>
                <a href="./guancetai_dgj"><div class="level">总览</div></a>
            </li>
            <li class="chose">
                <div class="iconClass" id="fxIcon">
                    <i class="fxIcon_n"></i><span>内容分析</span><em class="arrowdown_n"></em>
                </div>
                <a href="./pageAnalyse_dgj"><div class="level">页面分析</div></a>
                <a href="./eventAnalyse_dgj"><div class="level">事件分析</div></a>
                <a href="./pathAnalyse_dgj"><div class="level">行为路径</div></a>
                <a href="./louDouAnalyse_dgj"><div class="level">漏斗分析</div></a>
            </li>
            <li>
                <div class="iconClass" id="lcIcon">
                    <i class="lcIcon_n"></i><span>活跃&留存</span><em class="arrowdown_n"></em>
                </div>
                <a href="./activeAnalyse_dgj"><div class="level">活跃分析</div></a>
                <a href="./lcAnalyse_dgj"><div class="level">留存分析</div></a>
            </li>
            <li>
                <div class="iconClass" id="bbIcon">
                    <i class="bbIcon_n"></i><span>版本分析</span><em class="arrowdown_n"></em>
                </div>
                <a href="./versionAnalyse_dgj"><div class="level">版本分析</div></a>
            </li>
            <li>
                <div class="iconClass" id="zdIcon">
                    <i class="zdIcon_n"></i><span>终端分析</span><em class="arrowdown_n"></em>
                </div>
                <a href="./osAnalyse_dgj"><div class="level">操作系统</div></a>
                <a href="./clientType_dgj"><div class="level active">终端型号</div></a>
            </li>
        </ul>
            </nav>
        </div>
    </div>
</aside>
    <div>
        <div class="mouseShow mouseShow1">
            <div>基本统计</div><div class="twoLevel"><a href="./guancetai_dgj"><div class="menu">总览</div></a></div>
        </div>
        <div class="mouseShow mouseShow2">
            <div>内容分析</div>
            <div class="twoLevel" style="margin-left: 0">
                <a href="./pageAnalyse_dgj"><div class="menu">页面分析</div></a>
                <a href="./eventAnalyse_dgj"><div class="menu">事件分析</div></a>
                <a href="./pathAnalyse_dgj"><div class="menu">行为路径</div></a>
                <a href="./louDouAnalyse_dgj"><div class="menu">漏斗分析</div></a>
            </div>
        </div>
        <div class="mouseShow mouseShow3">
            <div>活跃&留存</div>
            <div class="twoLevel" style="margin-left: 0">
                <a href="./activeAnalyse_dgj"><div class="menu">活跃分析</div></a>
                <a href="./lcAnalyse_dgj"><div class="menu">留存分析</div></a>
            </div>
        </div>
        <div class="mouseShow mouseShow4">
            <div>版本分析</div>
            <div class="twoLevel" style="margin-left: 0">
                <a href="./versionAnalyse_dgj"><div class="menu">版本分析</div></a>
            </div>
        </div>
        <div class="mouseShow mouseShow5">
            <div>终端分析</div>
            <div class="twoLevel" style="margin-left: 0">
                <a href="./osAnalyse_dgj"><div class="menu">操作系统</div></a>
                <a href="./clientType_dgj"><div class="menu">终端型号</div></a>
            </div>
        </div>
    </div>
    <!--leftBar end-->
    <div class="wrapper">
        <div><i class="close_n" id="closeIcon" onclick="closeLeftPanel()"></i></div>
        <div class="controller">
            <div class="main" id="dgjPanel">
                <div class="titlePanel">
                    <div class="title">
                        <div>设备型号统计详情(截止昨日)</div>
                        <div>
                            <label>端:</label>
                            <select id="appType" onchange="tableShow()">
                                <option value="all">全部端</option>
                                <option value="Android">Android</option>
                                <option value="iOS">IOS</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="tableInfo">
                    <div class="tableTyple">
                        <div class="download" onclick="getExportData()">下载</div>
                    </div>
                    <div class="col-md-12" >
                        <table id="dgj_fenxi" class="table cell-border" style="white-space: nowrap;">
                            <thead><tr><td style="border-left: 1px solid #dee2e6">手机型号</td><td>使用人数</td><td>占比</td></tr></thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
<c:import url="/WEB-INF/jsp/common/importJs2.jsp"></c:import>
<script type="text/javascript" src="./js/moment.min.js"></script>

<script type="text/javascript" src="./js/datepicker.all.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="./js/guancetai/guancetaiCommon.js"></script>
<script type="text/javascript" src="./js/guancetai/dataPoint.js"></script>
<script>

    var businessType = $(".tabControl > a > .active").attr("data-type");//千丁 or 丁管家

    $(document).ready(function () {
        //埋点
        var obj = {
            "eventType": ["clientType"],
        }
        if(businessType == 0){
            obj.eventType.push('qding');
        }else{
            obj.eventType.push('dgj');
        }
        getPointParameter(obj);

        tableShow();
    });

    //表格列表
    function tableShow() {


        $("#dgj_fenxi>tbody").html("");
       var appType = $("#appType option:selected").val();
        var param={
            "source":appType
        }
       var urls = getLocation() + proxy("/dataApiQuery/gct_table_device_model_1",param);

        $('#dgj_fenxi').DataTable({
            dom: "lrtip",
            language: i18nZH,
            responsive: !0,
            "lengthChange": true,//开启显示条数
            // "lengthMenu": [ 15, 50, 75, 100 ],
            "ordering": true,//禁止排序
            "processing": true,//刷新的那个对话框
            "deferRender": false,//延迟渲染
//		     "scrollX": true,//启用水平滚动
//		     "autoWidth": true,//自动列宽
            "destroy": true,
            "ajax": {
                "url":urls,
                "type": "GET",
                "async" : true,
                "dataType": "json",
                "dataFilter": function (res) {//res是服务器端返回的数据
                    var json = JSON.parse(res);
                    var returnData = {};
                    if(json.code == '0' && json.data){
                        returnData.recordsTotal = json.total;//返回数据全部记录
                        returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = json.data;//返回的数据列表
                        return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                    }else{
                        //接口返回异常需要把数据置为空，否则datatable会弹窗报错
                        returnData.recordsTotal = 0;
                        returnData.recordsFiltered = 0;
                        returnData.data = [];
                        return JSON.stringify(returnData);
                    }
                }
            },
            /*给数据添加列*/
            columns: [
                {data: 'device_model'},
                {data: 'use_device_model_count'},
                {data: 'per_use_count'},
            ],
            "aaSorting": [
                [ 2, "desc"]
            ],//第二列倒序
        });
    }

    //下载
    function getExportData(){
        //埋点
        var obj = {
            "eventType": ["tableDownload"],
        }
        getPointParameter(obj);

        var appType = $("#appType option:selected").val();
        var param={
            "source":appType,
        }
        var title = {
            "device_model": '手机型号',
            "use_device_model_count": '使用人数',
            "per_use_count":'占比',
        };
        postDownLoadFile({
            url: './dataApiExport/gct_table_device_model_1',
            data: {
                "params":param,
                "titles":title
            },
            method: 'post'
        });
    }
</script>
</html>
