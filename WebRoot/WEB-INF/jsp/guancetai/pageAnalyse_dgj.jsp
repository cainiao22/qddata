<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<!--观测台-内容分析-页面分析-->
<html>
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
				<a href="./pageAnalyse_dgj"><div class="level active">页面分析</div></a>
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
	<!--leftBar end-->

	<div class="wrapper">
		<div><i class="close_n" id="closeIcon" onclick="closeLeftPanel()"></i></div>
		<div class="controller">
			<div class="main" id="qdingPanel">
				<div class="titlePanel">
					<div class="title">
						<div>数据统计(今天/昨天)</div>
						<div>
							<label>端:</label>
							<select style="margin-right: 20px;" id="appType" onchange="dataStatisticSelect()">
								<option value="all">全部端</option>
								<option value="Android">Android</option>
								<option value="iOS">IOS</option>
							</select>
							<label class="userText">用户类型:</label>
							<select id="userType" onchange="dataStatisticSelect()">
								<option value="all">全部用户</option>
								<option value="0">未登录</option>
								<option value="1">已登录</option>
							</select>
						</div>
					</div>
				</div>
				<div class="section">
					<div class="dataDetail">
						<div class="dataTitle">页面访问用户数</div>
						<div class="dataInfo" id="startUser">
							<div class="left">
								<p>本期：0</p>
								<p>上期：0</p>
							</div>
							<div class="right">
								<p>0</p>
								<p>环比</p>
							</div>
						</div>
					</div>
					<div class="dataDetail">
						<div class="dataTitle">页面人均停留时长(s)</div>
						<div class="dataInfo" id="avg_startTime">
							<div class="left">
								<p>本期：0</p>
								<p>上期：0</p>
							</div>
							<div class="right">
								<p>0</p>
								<p>环比</p>
							</div>
						</div>
					</div>
					<div class="dataDetail">
						<div class="dataTitle">页面访问次数</div>
						<div class="dataInfo" id="startTimes">
							<div class="left">
								<p>本期：0</p>
								<p>上期：0</p>
							</div>
							<div class="right">
								<p>0</p>
								<p>环比</p>
							</div>
						</div>
					</div>
					<div class="dataDetail">
						<div class="dataTitle">页面次均停留时长(s)</div>
						<div class="dataInfo" id="onlineTime">
							<div class="left">
								<p>本期：0</p>
								<p>上期：0</p>
							</div>
							<div class="right">
								<p>0</p>
								<p>环比</p>
							</div>
						</div>
					</div>
				</div>
				<div class="chartInfo">
					<div class="title">
						<div>数据趋势图</div>
						<div style="float: left;margin-left: 10px">
							<select style="margin-right: 20px;min-width: 120px" id="pageName" onchange="pageNameChoose()">
								<option value="all" selected>全部</option>
							</select>
						</div>
						<div>
							<label>端:</label>
							<select style="margin-right: 20px;" id="appType_qst" onchange="dataStatisticSelect('qst')">
								<option value="all">全部端</option>
								<option value="Android">Android</option>
								<option value="iOS">IOS</option>
							</select>
							<label class="userText">用户类型:</label>
							<select style="margin-right: 20px;" id="userType_qst" onchange="dataStatisticSelect('qst')">
								<option value="all">全部用户</option>
								<option value="0">未登录</option>
								<option value="1">已登录</option>
							</select>
							<label>时间:</label>
							<div class="c-datepicker-date-editor  J-datepicker-range-day">
								<i class="c-datepicker-range__icon kxiconfont icon-clock"></i>
								<input placeholder="开始日期" name="" class="c-datepicker-data-input only-date" value="">
								<span class="c-datepicker-range-separator">-</span>
								<input placeholder="结束日期" name="" class="c-datepicker-data-input only-date" value="">
							</div>
						</div>
					</div>
					<div style="position: relative">
						<div class="tab tab_1">
							<div class="bg_active">页面访问用户数</div><div>页面人均停留时长(s)</div><div>页面访问次数</div><div>页面次均停留时长(s)</div>
						</div>
						<div id="trendEchart">

						</div>
					</div>
				</div>
				<div class="tableInfo">
					<div class="tableTyple">
						<div class="tab tab_2">
							<div class="bg_active">汇总统计</div><div>详情统计</div>
						</div>
						<div class="download" onclick="getExportDate()">下载</div>
					</div>
					<div class="col-md-12" >

						<table id="example" class="table cell-border" style="white-space: nowrap;">
							<thead>
								<tr><td style="border-left: 1px solid #dee2e6">页面ID</td><td>页面名称</td><td>端(IOS/Android)</td><td>访问次数</td><td>访问用户数(日均)</td><td>次均停留时长(s)</td></tr>
							</thead>
							<tbody id="detailInfo">
							</tbody>
						</table>

						<table id="example_detail" class="table cell-border" style="white-space: nowrap;display: none">
							<thead>
							<tr><td style="border-left: 1px solid #dee2e6">日期</td><td>页面ID</td><td>页面名称</td><td>端(IOS/Android)</td><td>访问次数</td><td>访问用户数</td><td>次均停留时长(s)</td></tr>
							</thead>
							<tbody id="detail">
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
<script type="text/javascript" src="./thirdlib/Select2/select2.min.js"></script>
<script type="text/javascript" src="./js/guancetai/guancetaiCommon.js"></script>
<script type="text/javascript" src="./js/guancetai/dataPoint.js"></script>
<script type="text/javascript" src="./js/guancetai/pageAnalyse.js"></script>
<script>

</script>
</body>
</html>
