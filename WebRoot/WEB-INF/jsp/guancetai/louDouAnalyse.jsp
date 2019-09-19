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
            margin: 0 auto;
            text-align: center;
            color: #333;
            font-family: 'Arial Normal', 'Arial';
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
            padding-bottom: 20px;
            display: flex;
            justify-content: space-around;
            margin-top: 60px;
            list-style: none;
        }

        .wrapper .controller .pageTree ul li {
            cursor: pointer;
            position: relative;
            display: inline-block;
            width: 127px;
            height: 41px;
            line-height: 41px;
            background-color: rgb(243, 243, 243);
            margin: 0 auto;
            text-align: center;
            border-radius: 10px;
        }

        .wrapper .controller .pageTree ul li.select {
            display: block;
            background-color: rgba(0, 204, 255, 1);
        }

        .wrapper .controller .pageTree ul li .select2 {
            position: absolute;
            bottom: -30px;
            left: 30px;
            width: 60px;
            height: 30px;
        }

        .wrapper .controller .pageTree .select2 .top {
            position: absolute;
            top: 20px;
            width: 0;
            height: 0;
            border-style: solid;
            border-width: 15px 30px 0 30px;
            border-color: rgba(0, 204, 255, 1) transparent transparent transparent;
        }

        .wrapper .controller .pageTree .select2 .bot {
            position: absolute;
            top: 16px;
            width: 0;
            height: 0;
            border-style: solid;
            border-width: 15px 30px 0 30px;
            border-color: #fff transparent transparent transparent;
        }

        #louDouEdit {
            margin-left: 50px;
        }

        #addLouDouPage {
            margin-left: 120px;
            width: 86px;
            height: 25px;
            line-height: 22px;
            text-align: center;
            color: white;
            border-radius: 2px;
            cursor: pointer;
            background-color: #00a4fe;

        }

        #addLouDou {
            display: none;
            z-index: 100;
            position: fixed;
            width: 600px;
            height: 500px;
            top: 50%;
            left: 50%;
            border: 1px solid #dcdcdc;
            background-color: white;
            margin-left: -300px;
            margin-top: -250px;
        }

        #addLouDou:after {
            content: "";
            display: block;
            clear: both;
        }

        /*关闭图标*/

        #addLouDou .close {
            position: absolute;
            right: -15px;
            top: -15px;
            width: 30px;
            height: 30px;
            background: silver;
            border-radius: 15px;
            box-shadow: 2px 2px 5px 0px black;
            cursor: pointer;
        }

        #addLouDou .close:hover {
            background: red;
        }

        #addLouDou .close:before {
            position: absolute;
            content: '';
            width: 15px;
            height: 5px;
            background: white;
            transform: rotate(45deg);
            top: 12.5px;
            left: 8px;
        }

        #addLouDou .close:after {
            content: '';
            position: absolute;
            width: 15px;
            height: 5px;
            background: white;
            transform: rotate(-45deg);
            top: 12.5px;
            left: 8px;
        }

        #addLouDou .addLouDouTop {
            padding: 10px;
            border-bottom: solid 1px red;
            background-color: #dcdcdc;
        }

        #addLouDou .ol {
            height: 350px;
            overflow: auto;
            padding-top: 20px;
            text-align: center;
        }

        #addLouDou .ol li {
            position: relative;
        }

        #addLouDou .ol li span.stars {
            margin-right: 3px;
            color: red;
        }

        #addLouDou .addLouDouStyle {
            margin-top: 5px;
            width: 222px;
            height: 30px;
            background-color: white;
        }

        #addLouDou .delete {
            cursor: pointer;
            position: relative;
            top: 18px;
            left: 15px
        }

        #addLouDou .delete1:before {
            position: absolute;
            content: '';
            width: 20px;
            height: 3px;
            background: #ff7580;
            transform: rotate(45deg);
        }

        #addLouDou .delete1:after {
            content: '';
            position: absolute;
            width: 20px;
            height: 3px;
            background: #ff6d5a;
            transform: rotate(-45deg);
        }

        #addLouDou .addSteps {
            padding-left: 20px;
            width: 120px;
            height: 30px;
            line-height: 30px;
            text-align: center;
            color: white;
            background-color: rgba(0, 204, 255, 1);
            float: right;
            margin-right: 160px;
            cursor: pointer;
            border-radius: 2px;
            border: 1px solid #ccc;
        }

        #addLouDou .addSteps span {
            position: relative;
            top: 14px;
            right: 20px;
        }

        #addLouDou .addSteps span:after {
            position: absolute;
            content: "";
            width: 16px;
            height: 3px;
            transform: rotate(90deg);
            background: white;
        }

        #addLouDou .addSteps span:before {
            position: absolute;
            content: "";
            width: 16px;
            height: 3px;
            background: white;

        }

        #addLouDou .addLouDouBot {
            position: absolute;
            right: 20px;
            bottom: 10px
        }

        #addLouDou .addLouDouBot span {
            display: inline-block;
            width: 50px;
            height: 30px;
            line-height: 30px;
            text-align: center;
            margin-right: 10px;
            border-radius: 3px;
            border: .5px solid #ccc;
            cursor: pointer;
        }

        #louDouMap {
            width: 100%;
        }

        #louDouMap:after {
            content: "";
            display: block;
            clear: both;
        }

        #louDouMap .left ol li {
            position: relative;
            margin: 0;
            padding: 0;
            color: #000;
        }

        #louDouMap .left ol li span {
            color: white;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        #louDouMap .left ol li:nth-of-type(3n+1) {
            width: 336px;
            height: 43px;
            line-height: 43px;
            text-align: center;
            border: 1px solid rgba(204, 204, 204, 1);
        }

        #louDouMap .left ol li:nth-of-type(3n+2) {
            width: 336px;
            height: 33px;
            line-height: 33px;
            text-align: center;
            background-color: rgba(242, 242, 242, 1);
        }

        #louDouMap .left ol li:nth-of-type(3n) {
            width: 336px;
        }

        #louDouMap .left .leftTop {
            width: 184px;
            height: 24px;
            background-color: rgb(0, 160, 217);
            margin-left: 71px;
        }

        #louDouMap .left .leftBot {
            width: 0;
            height: 0;
            border-style: solid;
            border-width: 33px 92px 0 92px;
            border-color: rgb(0, 160, 217) transparent transparent;
            margin-left: 71px;
        }
        #louDouMap .left {
            float: left;
        }

        #louDouMap .right {
            float: right;
            height: auto;
        }

        #louDouMap .right table th {
        text-align: center;
            width: auto;
            padding: 0 30px 0 30px;
            height: 36px;
            line-height: 36px;
            background-color: rgba(204, 204, 204, 0.27);
        }

        #louDouMap .right table td {
            color: #000;
            width: auto;
            padding: 20px;
            height: 36px;
            left: 36px;
        }

        #editLouDou {
            display: none;
            z-index: 100;
            position: fixed;
            width: 600px;
            height: 500px;
            top: 50%;
            left: 50%;
            border: 1px solid #dcdcdc;
            background-color: white;
            margin-left: -300px;
            margin-top: -250px;
        }

        #editLouDou:after {
            content: "";
            display: block;
            clear: both;
        }

        /*关闭图标*/

        #editLouDou .close {
            position: absolute;
            right: -15px;
            top: -15px;
            width: 30px;
            height: 30px;
            background: silver;
            border-radius: 15px;
            box-shadow: 2px 2px 5px 0px black;
            cursor: pointer;
        }

        #editLouDou .close:hover {
            background: red;
        }

        #editLouDou .close:before {
            position: absolute;
            content: '';
            width: 15px;
            height: 5px;
            background: white;
            transform: rotate(45deg);
            top: 12.5px;
            left: 8px;
        }

        #editLouDou .close:after {
            content: '';
            position: absolute;
            width: 15px;
            height: 5px;
            background: white;
            transform: rotate(-45deg);
            top: 12.5px;
            left: 8px;
        }

        #editLouDou .addLouDouTop {
            padding: 10px;
            border-bottom: solid 1px red;
            background-color: #dcdcdc;
        }

        #editLouDou .ol {
            height: 350px;
            overflow: auto;
            padding-top: 20px;
            text-align: center;
        }

        #editLouDou .ol li {
            position: relative;
        }

        #editLouDou .ol li span.stars {
            margin-right: 3px;
            color: red;
        }

        #editLouDou .addLouDouStyle {
            margin-top: 5px;
            width: 222px;
            height: 30px;
            background-color: white;
        }

        #editLouDou .delete {
            cursor: pointer;
            position: relative;
            top: 18px;
            left: 15px
        }

        #editLouDou .delete1:before {
            position: absolute;
            content: '';
            width: 20px;
            height: 3px;
            background: #ff7580;
            transform: rotate(45deg);
        }

        #editLouDou .delete1:after {
            content: '';
            position: absolute;
            width: 20px;
            height: 3px;
            background: #ff6d5a;
            transform: rotate(-45deg);
        }

        #editLouDou .addSteps {
            padding-left: 20px;
            width: 120px;
            height: 30px;
            line-height: 30px;
            text-align: center;
            color: white;
            background-color: rgba(0, 204, 255, 1);
            float: right;
            margin-right: 160px;
            cursor: pointer;
            border-radius: 2px;
            border: 1px solid #ccc;
        }

        #editLouDou .addSteps span {
            position: relative;
            top: 14px;
            right: 20px;
        }

        #editLouDou .addSteps span:after {
            position: absolute;
            content: "";
            width: 16px;
            height: 3px;
            transform: rotate(90deg);
            background: white;
        }

        #editLouDou .addSteps span:before {
            position: absolute;
            content: "";
            width: 16px;
            height: 3px;
            background: white;

        }

        #editLouDou .addLouDouBot {
            position: absolute;
            right: 20px;
            bottom: 10px
        }

        #editLouDou .addLouDouBot span {
            display: inline-block;
            width: 50px;
            height: 30px;
            line-height: 30px;
            text-align: center;
            margin-right: 10px;
            border-radius: 3px;
            border: .5px solid #ccc;
            cursor: pointer;
        }



    </style>
</head>
<body>
<header>
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
                            <div class="level">行为路径</div>
                        </a>
                        <a href="./louDouAnalyse">
                            <div class="level active">漏斗分析</div>
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
            <div class="titlePanel" style="margin-bottom: 20px">
                <div class="title">
                    <div>
                        <label style="margin-left: 40px">端:</label>
                        <%-- onchange="dataStatisticSelect()"--%>
                        <select style="margin-right: 20px;" id="appType" onchange="getDevType()">
                            <option value="all">全部端</option>
                            <option value="Android">Android</option>
                            <option value="iOS">IOS</option>
                        </select>
                        <label style="margin-left: 30px">时间:</label>
                        <div class="c-datepicker-date-editor J-datepicker-range-day" id="timeSelect">
                            <i class="c-datepicker-range__icon kxiconfont icon-clock"></i>
                            <input placeholder="开始日期" name="" class="c-datepicker-data-input only-date" value="">
                            <span class="c-datepicker-range-separator">-</span>
                            <input placeholder="结束日期" name="" class="c-datepicker-data-input only-date" value="">
                        </div>

                        <select  id="louDouEdit" onchange="louDouEditChange()">
                            <%--<option value="payLouDou">缴费页面漏斗</option>--%>
                            <%--<option value="pageLouDou">xxx页面漏斗</option>--%>
                        </select>
                        <%--新增漏斗与编辑漏斗触发通过一个函数，通过判断进行不同操作--%>
                        <a href="javascript:;" onclick="editLouDou()" style="margin-left: 10px" >编辑漏斗</a>
                        <%--编辑漏斗页面--%>
                        <div id="editLouDou" style="clear: both">
                            <div class="addLouDouTop"><span style="margin-left: 20px">添加漏斗</span><span
                                    class="close" onclick="closeEditLouDou()"></span></div>
                            <ol class="ol">

                            </ol>
                            <div class="addSteps"><span></span> 添加步骤</div>
                            <div class="addLouDouBot">
                                <span
                                        style="color: white; background-color: rgba(0, 204, 255, 1)" onclick="determine1()">确定</span>
                                <span
                                        onclick="closeEditLouDou()">取消</span></div>
                        </div>

                        <button onclick="addLouDouPageFn()" id="addLouDouPage">新增漏斗</button>

                        <%-----------------------事件让下面的新增页面addLouDow显示隐藏---------------------------------%>
                        <div id="addLouDou" style="clear: both">
                            <div class="addLouDouTop"><span style="margin-left: 20px">添加漏斗</span><span
                                    class="close" onclick="closeAddLouDou()"></span></div>
                            <ol class="ol">
                                <li><span class="stars">*</span>漏斗名称 <input type="text" placeholder="请输入漏斗名称"
                                                                            class="addLouDouStyle">
                                </li>

                            </ol>
                            <div class="addSteps"><span></span> 添加步骤</div>
                            <div class="addLouDouBot">
                                <span
                                    style="color: white; background-color: rgba(0, 204, 255, 1)" onclick="determine()">确定</span>
                                <span
                                        onclick="closeAddLouDou()">取消</span></div>
                        </div>
                    </div>

                </div>

            </div>
            <%------------------------------------------------新增漏斗页面END----------------------------------%>

            <%--漏斗示意图--%>
            <div id="louDouMap">
                <div class="left">
                    <ol>
                    </ol>
                </div>

                <%--------------------left是图标  right是表格-----------------------------------%>
                <div class="right">
                    <table border="1" id="louDouMapTable" style="display: none">
                        <tr>
                            <th>步骤</th>
                            <th>页面名称</th>
                            <th>UV</th>
                            <th>上一步UV转化率</th>
                            <th>总体UV转化率</th>
                        </tr>
                    </table>

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
    <script type="text/javascript" src="./thirdlib/Select2/select2.min.js"></script>
    <script type="text/javascript" src="./js/guancetai/guancetaiCommon.js"></script>
    <script type="text/javascript" src="./js/guancetai/dataPoint.js"></script>
    <script type="text/javascript" src="./js/guancetai/loudouAnalyse.js"></script>
    <script>
    </script>
</body>
</html>
