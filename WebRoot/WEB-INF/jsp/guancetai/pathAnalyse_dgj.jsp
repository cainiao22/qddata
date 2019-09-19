<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<!--观测台-内容分析-路径分析-->
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
    <link rel="stylesheet" type="text/css" href="./thirdlib/Select2/select2.min.css"/>
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

        .wrapper .controller .pageTable {
            margin-top: 20px;
        }

        .wrapper .controller .pageTree {
            overflow-x: auto;
            width: 100%;
            height: auto;
            padding: 0 auto;
        }

        .wrapper .controller .pageTree .pageTreeStart {
            position: relative;
            width: 190px;
            height: 47px;
            line-height: 47px;
            background-color: rgba(0, 204, 255, 1);
            font-size: 13px;
            margin: 0 auto 30px;
            text-align: center;
            color: #333;
            border-radius: 10px;
        }

        .wrapper .controller .pageTree .pageTreeStart .select1 {
            position: absolute;
            bottom: -30px;
            left: 65px;
            width: 60px;
            height: 30px;

        }

        .wrapper .controller .pageTree .select1 .top {
            position: absolute;
            top: 20px;
            width: 0;
            height: 0;
            border-style: solid;
            border-width: 15px 30px 0 30px;
            border-color: rgba(0, 204, 255, 1) transparent transparent transparent;
        }

        .wrapper .controller .pageTree .select1 .bot {
            position: absolute;
            top: 16px;
            width: 0;
            height: 0;
            border-style: solid;
            border-width: 15px 30px 0 30px;
            border-color: #fff transparent transparent transparent;
        }
        .wrapper .controller .pageTree ul {
            display: block;
            justify-content: space-around;
            width: auto;
            white-space: nowrap;
            /*padding-bottom: 20px;*/
            height: 115px;
            padding-top: 25px;
            list-style: none;
            text-align: center;
            border-bottom: 1px solid #ccc;
        }

        .wrapper .controller .pageTree ul li {
            cursor: pointer;
            position: relative;
            display: inline-block;
            width: 127px;
            height: 41px;
            line-height: 41px;
            background-color: rgba(242, 242, 242, 1);
            margin: 0 5px;
            text-align: center;
            padding: 0 20px;
            border-radius: 10px;
        }

        .wrapper .controller .pageTree ul li.select {
            display: inline-block;
            background-color: rgba(0, 204, 255, 1);
        }

        .wrapper .controller .pageTree ul li .select3 {
            position: absolute;
            bottom: -30px;
            left: 70px;
            width: 60px;
            height: 30px;
        }

        .wrapper .controller .pageTree .select3 .top {
            position: absolute;
            top: 20px;
            width: 0;
            height: 0;
            border-style: solid;
            border-width: 15px 30px 0 30px;
            border-color: rgba(0, 204, 255, 1) transparent transparent transparent;
        }

        .wrapper .controller .pageTree .select3 .bot {
            position: absolute;
            top: 16px;
            width: 0;
            height: 0;
            border-style: solid;
            border-width: 15px 30px 0 30px;
            border-color: #fff transparent transparent transparent;
        }
        #ul {
            display: block;
            padding-top: 45px;
            height: 130px;
        }
        #ul li h5 {
            font-size: 13px;
            width: 50px;
            height: 20px;
            position: absolute;
            top: -25px;
            left: 10px;
        }
        ul li h5 {
            font-size: 13px;
            width: 50px;
            height: 20px;
            position: absolute;
            top: -25px;
            left: 10px;
        }


    </style>
    <head>
        <meta charset="utf-8"/>
        <title>北斗星-大数据中心</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
        <c:import url="/WEB-INF/jsp/common/importCss2.jsp"></c:import>
        <link rel="stylesheet" type="text/css" href="./css/datepicker.css">
        <link rel="stylesheet" type="text/css" href="./assets/plugins/datatables/datatables.min.css" />
        <link rel="stylesheet" type="text/css" href="./thirdlib/Select2/select2.min.css"/>
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
<header>
    <nav>
        <div class="logo"><img src="./images/logo.png" /></div>
        <div class="tabControl">
            <div class="tabControl">
                <a href="./pageAnalyse"><div data-type="0"><i class="qdLogo_n"></i>千丁App</div></a>
                <a href="./pageAnalyse_dgj"><div data-type="1" class="active"><i class="dgjLogo"></i>丁管家</div></a>
            </div>
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
                        <a href="./pathAnalyse_dgj"><div class="level active">行为路径</div></a>
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
                        <a href="./clientType_dgj"><div class="level">终端型号</div></a>
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

<div class="wrapper">
    <div><i class="close_n" id="closeIcon" onclick="closeLeftPanel()"></i></div>
    <div class="controller">
        <div class="main" id="qdingPanel">
            <div class="titlePanel">
                <div class="title">
                    <div>
                        <label style="margin-left: 40px">端:</label>
                        <select style="margin-right: 20px;" id="appType" onchange="equipmentChange();equipmentChange2()">
                            <option value="all">全部端</option>
                            <option value="Android">Android</option>
                            <option value="iOS">IOS</option>
                        </select>
                        <label style="margin-left: 300px">时间:</label>
                        <div class="c-datepicker-date-editor J-datepicker-range-day" id="timeSelect">
                            <i class="c-datepicker-range__icon kxiconfont icon-clock"></i>
                            <input placeholder="开始日期" name="" class="c-datepicker-data-input only-date" value="">
                            <span class="c-datepicker-range-separator">-</span>
                            <input placeholder="结束日期" name="" class="c-datepicker-data-input only-date" value="">
                        </div>

                        <div style="display: inline-block; margin-left: 100px" id="input">
                            <input type="radio" name="analysis" value="pageAnalysis" checked>页面分析
                            <input type="radio" name="analysis" value="eventAnalysis">事件分析

                        </div>

                    </div>

                </div>
            </div>

            <%--指定页面--%>
            <div class="pageTable" id="pageTable">
                <label>指定页面:</label>
                <select name="" id="sgmlPage" onchange="sgmlPageFn()">
                    <%--通过js动态添加--%>
                    <option value="all">无指定</option>
                </select>
            </div>





            <%--页面展示树形图--%>
            <div class="pageTree" id="pageTree">
                <div class="pageTreeStart">
                    <span> 开始</span>
                    <%--i标签存下拉箭头三角--%>
                    <div class="startArrow select1">
                        <div class="top"></div>
                        <div class="bot"></div>
                    </div>
                </div>
                <%--各个树形图二级页面--%>
                <ul id="ul">
                    <%--通过js动态添加  不过下面这个是暂时留着--%>
                    <%--  <li class="select">页面一
                          <div class="">
                              <div class="top"></div>
                              <div class="bot"></div>
                          </div>
                          <h5>20%/00</h5>
                      </li>
                          <li class="select">页面二
                          <div class="">
                              <div class="top"></div>
                              <div class="bot"></div>
                          </div>
                          <h5>20%/00</h5>
                      </li>--%>

                </ul>


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
    <script type="text/javascript" src="./thirdlib/Select2/select2.min.js"></script>
    <script type="text/javascript" src="./js/guancetai/guancetaiCommon.js"></script>
    <script type="text/javascript" src="./js/guancetai/dataPoint.js"></script>
    <script type="text/javascript" src="./js/guancetai/pathAnalyse_dgj.js"></script>


    <script>


    </script>
</body>
</html>
