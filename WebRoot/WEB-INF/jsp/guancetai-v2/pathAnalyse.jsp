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
            margin: 0 auto 15px;
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

        #pageTree {
            /*background-color: #dcdb4f;*/
        }
        .wrapper .controller .pageTree ul {
            display: block;
            border-bottom: 1px solid #ccc;
            justify-content: space-around;
            width: auto;
            white-space: nowrap;
            /*padding-bottom: 20px;*/
            height: 115px;
            padding-top: 25px;
            list-style: none;
            text-align: center;
            /*background-color: #ff78b7;*/
        }

        #pageTree ul:after {
            content: "";
            display: block;
            clear: both;
        }

        #ul {
            display: block;
            padding-top: 60px;
            height: 145px;
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
</head>
<body>
<%--<header>
    <nav>
        <div class="logo"><img src="./images/logo.png"/></div>
        <div class="tabControl">
            <a href="./pageAnalyse">
                <div data-type="0" class="active"><i class="qdLogo"></i>千丁App</div>
            </a>
            <a href="./pageAnalyse_dgj">
                <div data-type="1"><i class="dgjLogo_n"></i>丁管家</div>
            </a>
        </div>
    </nav>
</header>--%>
<!--leftBar begin-->

<!--leftBar end-->

<div class="wrapper" style="margin-top: 0; margin-left: 0;">
    <div class="controller" style="padding: 0;">
        <div class="main" id="qdingPanel">
            <div class="titlePanel">
                <div class="title">
                    <div>
                        <label style="margin-left: 40px">端:</label>
                        <select style="margin-right: 20px;" id="appType"
                                onchange="equipmentChange();equipmentChange2()">
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
                <select name="" id="sgmlPage" class="select2" onchange="sgmlPageFn()">
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
                    <%--      <li class="select">页面一
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
    <script type="text/javascript" src="./js/guancetai/pathAnalyse.js"></script>


    <script>


    </script>
</body>
</html>
