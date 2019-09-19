<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<!--观测台-活跃&留存-活跃分析-->
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

        table.dataTable thead th, table.dataTable tfoot th {
            font-weight: normal;
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

        table.dataTable thead th {
            border-right: 1px solid #ddd
        }

        table.dataTable thead th, table.dataTable thead td {
            border-bottom: 1px solid #dee2e6;
        }

        /*table.dataTable thead .sorting:after{display: none}*/
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
            <a href="./activeAnalyse">
                <div data-type="0" class="active"><i class="qdLogo"></i>千丁App</div>
            </a>
            <a href="./activeAnalyse_dgj">
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
                    <li>
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
                            <div class="level">行为路径</div>
                        </a>
                        <a href="./louDouAnalyse">
                            <div class="level">漏斗分析</div>
                        </a>
                    </li>
                    <li class="chose">
                        <div class="iconClass" id="lcIcon">
                            <i class="lcIcon_n"></i><span>活跃&留存</span><em class="arrowdown_n"></em>
                        </div>
                        <a href="./activeAnalyse">
                            <div class="level active">活跃分析</div>
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
                            <div class="level">设备分析</div>
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
            <div class="chartInfo">
                <div class="title">
                    <div>用户活跃走势图</div>
                    <div>
                        <label>端:</label>
                        <select style="margin-right: 20px;" id="appType_qst" onchange="dataStatisticSelect(1)">
                            <option value="all">全部端</option>
                            <option value="Android">Android</option>
                            <option value="iOS">IOS</option>
                        </select>
                        <label class="userText">用户类型:</label>
                        <select style="margin-right: 20px;" id="userType_qst" onchange="dataStatisticSelect(1)">
                            <option value="2">全部用户</option>
                            <option value="0">未登录</option>
                            <option value="1">已登录</option>
                        </select>
                        <label>时间类型:</label>
                        <select style="margin-right: 20px;" id="dateType_qst" onchange="dataStatisticSelect(1)">
                            <option value="0">日</option>
                            <option value="1">周</option>
                            <option value="2">月</option>
                        </select>
                        <label>时间:</label>
                        <div class="c-datepicker-date-editor  J-datepicker-range-day setdate-1">
                            <i class="c-datepicker-range__icon kxiconfont icon-clock"></i>
                            <input placeholder="开始日期" name="" class="c-datepicker-data-input only-date" value="">
                            <span class="c-datepicker-range-separator">-</span>
                            <input placeholder="结束日期" name="" class="c-datepicker-data-input only-date" value="">
                            <em style="display: none">2</em>
                        </div>
                    </div>
                </div>
                <div style="position: relative">
                    <div id="trendEchart">
                        <div class="trandmap"></div>
                    </div>
                </div>
            </div>
            <div class="tableInfo">
                <div class="title" style="border-bottom: 1px solid #EBF1F5;">
                    <div>用户活跃详情</div>
                    <div>
                        <label>端:</label>
                        <select style="margin-right: 20px;" id="appType_tab" onchange="dataStatisticSelect(2)">
                            <option value="all">全部端</option>
                            <option value="Android">Android</option>
                            <option value="iOS">IOS</option>
                        </select>
                        <label class="userText">用户类型:</label>
                        <select style="margin-right: 20px;" id="userType_tab" onchange="dataStatisticSelect(2)">
                            <option value="2">全部用户</option>
                            <option value="0">未登录</option>
                            <option value="1">已登录</option>
                        </select>
                        <label>时间类型:</label>
                        <select style="margin-right: 20px;" id="dateType_tab" onchange="dataStatisticSelect(2)">
                            <option value="0">日</option>
                            <option value="1">周</option>
                            <option value="2">月</option>
                        </select>
                        <label>时间:</label>
                        <div class="c-datepicker-date-editor  J-datepicker-range-day setdate-2">
                            <i class="c-datepicker-range__icon kxiconfont icon-clock"></i>
                            <input placeholder="开始日期" name="" class="c-datepicker-data-input only-date" value="">
                            <span class="c-datepicker-range-separator">-</span>
                            <input placeholder="结束日期" name="" class="c-datepicker-data-input only-date" value="">
                            <em style="display: none">2</em>
                        </div>
                    </div>
                </div>
                <div class="tableTyple" style="position: relative;">
                    <div class="download" onclick="getExportDate()">下载</div>
                </div>
                <div class="col-md-12">
                    <table id="example" class="table cell-border">
                        <thead>
                        <tr>
                            <th>日期</th>
                            <th>日活跃用户</th>
                            <th>活跃率</th>
                        </tr>
                        </thead>
                        <tbody id="detailInfo">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="/WEB-INF/jsp/common/importJs2.jsp"></c:import>
<script type="text/javascript" src="./js/moment.min.js"></script>
<script type="text/javascript" src="./js/datepicker.all.min.js"></script>
<script type="text/javascript" src="./thirdlib/echarts/echarts.min.js"></script>
<script type="text/javascript" src="./js/guancetai/guancetaiOption.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="./js/guancetai/guancetaiCommon.js"></script>
<script type="text/javascript" src="./js/guancetai/dataPoint.js"></script>
<script type="text/javascript" src="./js/guancetai/activeAnalyse.js"></script>


<script>

</script>
</body>
</html>
