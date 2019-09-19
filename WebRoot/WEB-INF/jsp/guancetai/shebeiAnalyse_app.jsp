<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!--观测台-终端分析-设备分析-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="/WEB-INF/jsp/common/importCss2.jsp"></c:import>
    <link rel="stylesheet" type="text/css" href="./css/datepicker.css">
    <link rel="stylesheet" type="text/css" href="./assets/plugins/datatables/datatables.min.css"/>
    <link rel="stylesheet" type="text/css" href="./css/guancetaiCommon.css">
    <style>
        table thead th, table thead td {
            border-right: 1px solid #dee2e6;
            border-bottom: 0;
            text-align: center;
        }

        table.dataTable thead td, table.dataTable tfoot th {
            font-weight: normal;
            border-top: 1px solid #dee2e6;
            border-right: 1px solid #dee2e6;
            border-bottom: 1px solid #dee2e6;
        }

        table.dataTable.no-footer, .dataTables_wrapper.no-footer .dataTables_scrollBody {
            border-bottom: 1px solid #dee2e6;
        }

        .tableInfo .dataTables_length {
            display: none;
        }

        .tableInfo .form-control {
            background-color: #F5F6FA;
            color: #8B93A7;
            min-height: 24px;
        }

        .table thead {
            background: #F5F6FA;
            border-radius: 2px;
        }

        .dataTables_scroll .dataTables_scrollBody table thead {
            visibility: hidden;
            height: 0;
        }

        .dataTables_scroll .dataTables_scrollHead {
            margin-bottom: -22px
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            color: white !important;
            background: #fff;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            color: white;
            border: 0;
            background-color: transparent;
        }
    </style>
</head>
<body>
<!--header-->
<header>
    <nav>
        <div class="logo"><img src="./images/logo.png"/></div>
        <div class="tabControl">
            <a href="./shebeiAnalyse_app">
                <div data-type="0" class="active"><i class="qdLogo"></i>千丁App</div>
            </a>
            <a href="./guancetai_dgj">
                <div data-type="1"><i class="dgjLogo_n"></i>丁管家</div>
            </a>
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
                        <a href="./guancetai">
                            <div class="level">总览</div>
                        </a>
                    </li>
                    <li class="chose">
                        <div class="iconClass" id="fxIcon">
                            <i class="fxIcon_n"></i><span>内容分析</span><em class="arrowdown_n"></em>
                        </div>
                        <a href="./pageAnalyse">
                            <div class="level">页面分析</div>
                        </a>
                        <a href="./eventAnalyse">
                            <div class="level">事件分析</div>
                        </a>
                        <a href="./pathAnalyse">
                            <div class="level active">行为路径</div>
                        </a>
                        <a href="./louDouAnalyse">
                            <div class="level">漏斗分析</div>
                        </a>
                    </li>
                    <li>
                        <div class="iconClass" id="lcIcon">
                            <i class="lcIcon_n"></i><span>活跃&留存</span><em class="arrowdown_n"></em>
                        </div>
                        <a href="./activeAnalyse">
                            <div class="level">活跃分析</div>
                        </a>
                        <a href="./lcAnalyse">
                            <div class="level">留存分析</div>
                        </a>
                    </li>
                    <li>
                        <div class="iconClass" id="bbIcon">
                            <i class="bbIcon_n"></i><span>版本分析</span><em class="arrowdown_n"></em>
                        </div>
                        <a href="./versionAnalyse">
                            <div class="level">版本分析</div>
                        </a>
                    </li>
                    <li>
                        <div class="iconClass" id="zdIcon">
                            <i class="zdIcon_n"></i><span>终端分析</span><em class="arrowdown_n"></em>
                        </div>
                        <a href="./shebeiAnalyse_app">
                            <div class="level active">设备分析</div>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</aside>
<div>
    <div class="mouseShow mouseShow1">
        <div>基本统计</div>
        <div class="twoLevel"><a href="./guancetai">
            <div class="menu">总览</div>
        </a></div>
    </div>
    <div class="mouseShow mouseShow2">
        <div>内容分析</div>
        <div class="twoLevel" style="margin-left: 0">
            <a href="./pageAnalyse">
                <div class="menu">页面分析</div>
            </a>
            <a href="./eventAnalyse">
                <div class="menu">事件分析</div>
            </a>
            <a href="./pathAnalyse">
                <div class="menu">行为路径</div>
            </a>
            <a href="./louDouAnalyse">
                <div class="menu">漏斗分析</div>
            </a>
        </div>
    </div>
    <div class="mouseShow mouseShow3">
        <div>活跃&留存</div>
        <div class="twoLevel" style="margin-left: 0">
            <a href="./activeAnalyse">
                <div class="menu">活跃分析</div>
            </a>
            <a href="./lcAnalyse">
                <div class="menu">留存分析</div>
            </a>

        </div>
    </div>
    <div class="mouseShow mouseShow4">
        <div>版本分析</div>
        <div class="twoLevel" style="margin-left: 0">
            <a href="./versionAnalyse">
                <div class="menu">版本分析</div>
            </a>
        </div>
    </div>
    <div class="mouseShow mouseShow5">
        <div>终端分析</div>
        <div class="twoLevel" style="margin-left: 0">
            <a href="./shebeiAnalyse_app">
                <div class="menu">设备分析</div>
            </a>
        </div>
    </div>
</div>
<!--leftBar end-->
<div class="wrapper">
    <div><i class="close_n" id="closeIcon" onclick="closeLeftPanel()"></i></div>
    <div class="controller">
        <div class="main" id="qdingPanel">
            <div class="titlePanel">
                <div class="title">
                    <div>版本分析</div>
                    <div>
                        <label>端:</label>
                        <select style="margin-right: 20px;" id="appType" onchange="tableShow()">
                            <option value="all">全部端</option>
                            <option value="Android">Android</option>
                            <option value="iOS">IOS</option>
                        </select>
                        <label>用户类型:</label>
                        <select style="margin-right: 20px;" id="userType" onchange="tableShow()">
                            <option value="all">全部用户</option>
                            <option value="0">未登录</option>
                            <option value="1">已登录</option>
                        </select>
                        <label>时间:</label>
                        <div class="c-datepicker-date-editor  J-datepicker-range-day setdate-1">
                            <i class="c-datepicker-range__icon kxiconfont icon-clock"></i>
                            <input placeholder="开始日期" name="" class="c-datepicker-data-input only-date" value="">
                            <span class="c-datepicker-range-separator">-</span>
                            <input placeholder="结束日期" name="" class="c-datepicker-data-input only-date" value="">
                        </div>
                    </div>
                </div>
            </div>
            <div class="tableInfo">
                <div class="tableTyple">
                    <div class="tab tab_2">
                        <div class="bg_active">新增用户</div>
                        <div>应用启动</div>
                    </div>
                    <div class="download" onclick="getExportData()">下载</div>
                </div>
                <div class="col-md-12">

                    <table id="addUser" class="table cell-border" style="white-space: nowrap;">
                        <thead>
                        <tr>
                            <td style="border-left: 1px solid #dee2e6">日期</td>
                            <td>设备型号</td>
                            <td>新增用户</td>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>

                    <table id="appStartup" class="table cell-border" style="white-space: nowrap;display: none">
                        <thead>
                        <tr>
                            <td style="border-left: 1px solid #dee2e6">日期</td>
                            <td>设备型号</td>
                            <td>启动次数</td>
                        </tr>
                        </thead>
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
<script type="text/javascript" src="./thirdlib/Select2/select2.min.js"></script>
<script type="text/javascript" src="./js/guancetai/guancetaiCommon.js"></script>
<script type="text/javascript" src="./js/guancetai/dataPoint.js"></script>
<script>

    var startdate = '', enddate = '';
    var businessType = $(".tabControl > a > .active").attr("data-type");//千丁 or 丁管家

    $(document).ready(function () {
        //埋点
        var obj = {
            "eventType": ["shebeiAnalyse"],
        }
        if (businessType == 0) {
            obj.eventType.push('qding');
        } else {
            obj.eventType.push('dgj');
        }
        getPointParameter(obj);

        $('.c-datepicker-data-input').eq(0).val(dayHandle(getCurrentDay(), -7));
        $('.c-datepicker-data-input').eq(1).val(dayHandle(getCurrentDay(), -1));
        //年月日范围
        $('.setdate-1').datePicker({
            hasShortcut: true,
            isRange: true,
            show: calendarshow,
            min: "2018-11-01",
            max: dayHandle(getCurrentDay(), -1),
            shortcutOptions: [{
                name: '过去7天',
                day: '-7,-1'
            }],
            hide: function (type) {
                this.$input.eq(0).val(this.$input.eq(0).val().substr(0, 10));
                this.$input.eq(1).val(this.$input.eq(1).val().substr(0, 10));
                startdate = this.$input.eq(0).val();
                enddate = this.$input.eq(1).val();
                //埋点
                var obj = {
                    "eventType": ["dateSelect"],
                }
                getPointParameter(obj);

                tableShow();
            }
        });
        startdate = dayHandle(getCurrentDay(), -7);
        enddate = dayHandle(getCurrentDay(), -1);

        tableShow(1);
    });
    //日历显示时
    function calendarshow() {
        $('.c-datepicker-picker').css({'left': 'initial', 'right': '25px'});
        $('.c-datepicker-date-range-picker__editor-wrap').eq(1).css('display', 'none');
        $('.c-datepicker-date-range-picker__editor-wrap').eq(3).css('display', 'none');
    }

    //表格列表
    function tableShow(mai) {
        //埋点
        var obj = {
            "eventType": ["appType", "userType"],
        }
        if (mai != 1) {
            getPointParameter(obj);
        }

        $("#addUser>tbody,#appStartup>tbody").html("");
        var userType = $("#userType option:selected").val();
        var appType = $("#appType option:selected").val();
        var serviceType = '', str = '';
        var t = $(".tab_2>.bg_active").text();
        if (t == "新增用户") {
            serviceType = 'device_user_add';
            str = '#addUser';
        } else {
            serviceType = 'device_start_add';
            str = '#appStartup';
        }
        var param = {
            "source": appType,
            "service_type": serviceType,
            "device_status": userType,
            "start_day": startdate,
            "end_day": enddate,
            "limit": 100
        }
        var urls = getLocation() + proxy("/dataApiQuery/gct_app_version_device", param);

        $(str).DataTable({
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
                "url": urls,
                "type": "GET",
                "async": true,
                "dataType": "json",
                "dataFilter": function (res) {//res是服务器端返回的数据
                    var json = JSON.parse(res);
                    var returnData = {};
                    if (json.code == '0' && json.data) {
                        returnData.recordsTotal = json.total;//返回数据全部记录
                        returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = json.data;//返回的数据列表
                        return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                    } else {
                        //接口返回异常需要把数据置为空，否则datatable会弹窗报错
                        returnData.recordsTotal = 0;
                        returnData.recordsFiltered = 0;
                        returnData.data = [];
                        return JSON.stringify(returnData);
                    }
                }
            },
            /*给数据添加列*/
            columns: [{data: 'dt'}, {data: 'app_version_device'}, {data: 'count_nums'}]
        });
    }
    //下载
    function getExportData() {
        //埋点
        var obj = {
            "eventType": ["tableDownload"],
        }
        getPointParameter(obj);

        var userType = $("#userType option:selected").val();
        var appType = $("#appType option:selected").val();
        var serviceType = '', title = {};
        var t = $(".tab_2>.bg_active").text();
        if (t == "新增用户") {
            serviceType = 'device_user_add';
            title = {
                "dt": '日期',
                "app_version_device": '设备型号',
                "count_nums": '新增用户'
            };
        } else {
            serviceType = 'device_start_add';
            title = {
                "dt": '日期',
                "app_version_device": '设备型号',
                "count_nums": '启动次数'
            };
        }

        var param = {
            "source": appType,
            "service_type": serviceType,
            "device_status": userType,
            "start_day": startdate,
            "end_day": enddate,
            "limit": 999999999
        }
        postDownLoadFile({
            url: './dataApiExport/gct_app_version_device',
            data: {
                "params": param,
                "titles": title
            },
            method: 'post'
        });
    }

    /*********前端交互*********/
    //表格切换
    $(".tab_2>div").click(function () {
        //埋点
        var obj = {
            "eventType": ["tableSwitch"],
        }
        getPointParameter(obj);

        $(this).addClass("bg_active").siblings("div").removeClass("bg_active");
        var i = $(this).index();
        if (i == 0) {
            $("#addUser,#addUser_info,#addUser_paginate").show();
            $("#appStartup,#appStartup_info,#appStartup_paginate").hide();
        } else if (i == 1) {
            $("#addUser,#addUser_info,#addUser_paginate").hide();
            $("#appStartup,#appStartup_info,#appStartup_paginate").show();
        }
        tableShow();
    });

</script>
</html>
