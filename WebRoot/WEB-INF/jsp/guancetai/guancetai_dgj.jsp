<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<!--观测台-基本统计-总览-->
<html>
<head>
	<meta charset="utf-8"/>
	<title>北斗星-大数据中心</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
	<c:import url="/WEB-INF/jsp/common/importCss2.jsp"></c:import>
	<link rel="stylesheet" type="text/css" href="./css/datepicker.css">
	<link rel="stylesheet" type="text/css" href="./css/guancetaiCommon.css">

</head>
<body>
<header>
    <nav>
        <div class="logo"><img src="./images/logo.png" /></div>
        <div class="tabControl">
            <a href="./guancetai"><div data-type="0"><i class="qdLogo_n"></i>千丁App</div></a>
            <a href="./guancetai_dgj"><div data-type="1" class="active"><i class="dgjLogo"></i>丁管家</div></a>
        </div>
    </nav>
</header>
	<!--leftBar begin-->
<aside id="sidenav">
    <div class="scrollnav">
        <div class="leftPanel">
            <nav>
		        <ul id="sidebar">
			<li class="chose">
				<div class="iconClass" id="tjIcon">
					<i class="tjIcon_n"></i><span>基本统计</span><em class="arrowdown_n"></em>
				</div>
				<a href="./guancetai_dgj"><div class="level active">总览</div></a>
			</li>
			<li>
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
			<div class="main" id="dgjPanel">
				<div class="titlePanel">
					<div class="title">
						<div>数据统计(今天/昨天)</div>
						<div>
							<label>端</label>
							<select style="margin-right: 20px;" id="dgj_appType" onchange="dataStatisticSelect(1)">
								<option value="">全部端</option>
								<option value="Android">Android</option>
								<option value="iOS">IOS</option>
							</select>
						</div>
					</div>
				</div>
				<div class="section">

                    <div class="dataDetail" style="margin-left: 2%;">
                        <div class="dataTitle">累计注册用户</div>
                        <div class="dataInfo" id="dgj_totalUser">
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
						<div class="dataTitle">启动次数</div>
						<div class="dataInfo" id="dgj_startTimes">
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
						<div class="dataTitle">次均停留时长(s)</div>
						<div class="dataInfo" id="dgj_onlineTime">
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
						<div class="dataTitle">人均启动次数</div>
						<div class="dataInfo" id="dgj_avg_startTime">
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
					<div class="dataDetail" style="margin-left: 2%;">
						<div class="dataTitle">启动用户数</div>
						<div class="dataInfo" id="dgj_startUser">
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
						<div>
							<label>端</label>
							<select style="margin-right: 20px;" id="dgj_appType_qst" onchange="qushituSelect(1)">
								<option value="">全部端</option>
								<option value="Android">Android</option>
								<option value="iOS">IOS</option>
							</select>
							<label>时间</label>
							<div class="c-datepicker-date-editor  J-datepicker-range-day setdate-2">
								<i class="c-datepicker-range__icon kxiconfont icon-clock"></i>
								<input placeholder="开始日期" name="" class="c-datepicker-data-input only-date" value="">
								<span class="c-datepicker-range-separator">-</span>
								<input placeholder="结束日期" name="" class="c-datepicker-data-input only-date" value="">
								<em style="display: none">2</em>
							</div>
						</div>
					</div>
					<div style="position: relative">
						<div class="dgj_tab">
							<div class="bg_active">启动次数</div><div>次均停留时长(s)</div><div>启动用户数</div><div>人均启动次数</div>
						</div>
						<div id="dgj_trendEchart">

						</div>
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
<script type="text/javascript" src="./assets/plugins/datatables/datatables.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="./js/guancetai/guancetaiCommon.js"></script>
<script type="text/javascript" src="./js/guancetai/dataPoint.js"></script>
<script type="text/javascript" src="./js/guancetai/zonglan.js"></script>
<script>

</script>
</body>
</html>
