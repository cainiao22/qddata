<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<!--观测台-内容分析-事件分析-->
<html>
<head>
	<meta charset="utf-8"/>
	<title>北斗星-大数据中心</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
	<c:import url="common/importCss2.jsp"></c:import>
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
		.select2-container--default .select2-selection--single {
			border: 1px solid #D8DFE5;
		}
		.select2-container--default .select2-selection--single .select2-selection__rendered{
			background:#F5F6FA;
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

<div id="main-wrapper">
	<!--header-->
	<header>
		<div class="logo"><img src="../../images/logo.png" /></div>
		<div class="tabControl">
			<div data-type="0"><i class="qdLogo"></i>千丁App</div><div data-type="1"><i class="dgjLogo_n"></i>丁管家</div>
		</div>
	</header>
	<!--左侧栏-->
	<div class="leftPanel">
		<ul id="sidebar">
			<li>
				<div class="iconClass" id="tjIcon">
					<i class="tjIcon_n"></i><span>基本统计</span><em class="arrowdown_n"></em>
				</div>
				<a href="./guancetai"><div class="level">总览</div></a>
			</li>
			<li class="chose">
				<div class="iconClass" id="fxIcon">
					<i class="fxIcon_n"></i><span>内容分析</span><em class="arrowdown_n"></em>
				</div>
				<a href="./pageAnalyse"><div class="level">页面分析</div></a>
				<a href="./eventAnalyse"><div class="level active">事件分析</div></a>
			</li>
			<li>
				<div class="iconClass" id="lcIcon">
					<i class="lcIcon_n"></i><span>活跃&留存</span><em class="arrowdown_n"></em>
				</div>
				<a href="./activeAnalyse"><div class="level">活跃分析</div></a>
				<a href="./lcAnalyse"><div class="level">留存分析</div></a>
			</li>
		</ul>
	</div>
	<div class="mouseShow mouseShow1">
		<div>基本统计</div><div class="twoLevel"><a href="./guancetai"><div class="menu menu1">总览</div></a></div>
	</div>
	<div class="mouseShow mouseShow2">
		<div>内容分析</div>
		<div class="twoLevel" style="margin-left: 0">
			<a href="./pageAnalyse"><div class="menu menu2">页面分析</div></a>
			<a href="./eventAnalyse"><div class="menu menu2">事件分析</div></a>
		</div>
	</div>
	<div class="mouseShow mouseShow3">
		<div>活跃&留存</div>
		<div class="twoLevel" style="margin-left: 0">
			<a href="./activeAnalyse"><div class="menu menu3">活跃分析</div></a>
			<a href="./lcAnalyse"><div class="menu menu3">留存分析</div></a>
		</div>
	</div>
	<!--右侧-->
	<div class="wrapper">
		<div><i class="close_n" id="closeIcon" onclick="closeLeftPanel()"></i></div>
		<div class="controller">
			<div class="main" id="qdingPanel">
				<div class="titlePanel">
					<div class="title">
						<div>数据统计(今天/昨天)</div>
						<div>
							<label>端</label>
							<select style="margin-right: 20px;" id="appType" onchange="dataStatisticSelect(0)">
								<option value="">全部端</option>
								<option value="Android">Android</option>
								<option value="iOS">IOS</option>
							</select>
							<label class="userText">用户类型</label>
							<select id="userType" onchange="dataStatisticSelect(0)">
								<option value="">全部用户</option>
								<option value="0">未登录</option>
								<option value="1">已登录</option>
							</select>
						</div>
					</div>
				</div>
				<div class="section">
					<div class="dataDetail" style="width:24%;">
						<div class="dataTitle">事件发生人数</div>
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
					<div class="dataDetail" style="width:24%;">
						<div class="dataTitle">事件发生次数</div>
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
				</div>
				<div class="chartInfo">
					<div class="title">
						<div>数据走势图</div>
						<div style="float: left;margin-left: 10px">
							<select style="margin-right: 20px;" id="eventname" class="select2" onchange="evevtnamechoose()">
								<option value="all" selected>全部</option>
							</select>
						</div>
						<div>
							<label>端</label>
							<select style="margin-right: 20px;" id="appType_qst" onchange="dataStatisticSelect(1)">
								<option value="all">全部端</option>
								<option value="Android">Android</option>
								<option value="iOS">IOS</option>
							</select>
							<label class="userText">用户类型</label>
							<select style="margin-right: 20px;" id="userType_qst" onchange="dataStatisticSelect(1)">
								<option value="all">全部用户</option>
								<option value="0">未登录</option>
								<option value="1">已登录</option>
							</select>
							<label>时间</label>
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
							<div class="bg_active">事件发生人数</div><div>事件发生次数</div>
						</div>
						<div id="trendEchart">
							<div class="trandmap"></div>
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
					<div class="col-md-12">
						<table id="example" class="table cell-border" style="white-space: nowrap;">
							<thead>
								<tr>
									<td style="border-left: 1px solid #dee2e6">事件ID</td><td>事件名称</td><td>页面名称</td>
									<td>类型</td><td>端(IOS/Android)</td><td>事件发生次数</td><td>事件发生人数(日均)</td>
								</tr>
							</thead>
							<tbody id="detailInfo">

							</tbody>
						</table>

						<table id="example_detail" class="table cell-border" style="white-space: nowrap;display: none">
							<thead>
							<tr>
								<td style="border-left: 1px solid #dee2e6">日期</td><td>事件ID</td><td>事件名称</td><td>页面名称</td>
								<td>类型</td><td>端(IOS/Android)</td><td>事件发生次数</td><td>事件发生人数</td>
							</tr>
							</thead>
							<tbody id="detail">

							</tbody>
						</table>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<c:import url="common/importJs2.jsp"></c:import>
<script type="text/javascript" src="./js/moment.min.js"></script>
<script type="text/javascript" src="./js/datepicker.all.min.js"></script>
<script type="text/javascript" src="./thirdlib/echarts/echarts.min.js"></script>
<script type="text/javascript" src="./js/guancetaiOption.js"></script>
<%--<script type="text/javascript" src="./assets/plugins/datatables/datatables.min.js"></script>--%>
<script type="text/javascript" src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./thirdlib/Select2/select2.min.js"></script>
<script type="text/javascript" src="./js/guancetaiCommon.js"></script>

<script>
    var startdate = '',
        enddate = '';

    if(sessionStorage.getItem('dingvalue')){
        var val = sessionStorage.getItem('dingvalue');
        $(".tabControl > div").eq(val).addClass("active").siblings("div").removeClass("active");
        if(val == 0){
            $(".userText,#userType,#userType_qst").show();
        }else{
            $(".userText,#userType,#userType_qst").hide();
        }
    }else{
        sessionStorage.setItem('dingvalue','0');
        $(".tabControl > div").eq(0).addClass("active").siblings("div").removeClass("active");
        $(".userText,#userType,#userType_qst").show();
    }

	var businessType = $(".tabControl > .active").attr("data-type");//千丁 or 丁管家

	var param = {
		"queryDate": getCurrentDay(),
	}

	var sTimes=0,lastTimes=0,time_hb=0; //启动次数
	var sUser=0,lastUser=0,user_hb=0;   //启动人数
	$(document).ready(function () {
        var val = sessionStorage.getItem('dingvalue');
        seteventname(val);

        $('.c-datepicker-data-input').val(getCurrentDay());
        //年月日范围
        $('.J-datepicker-range-day').datePicker({
            hasShortcut: true,
            isRange: true,
            show: calendarshow,
	        min:"2018-11-01",
			max: dayHandle(getCurrentDay(),-1),
            shortcutOptions: [{
                name: '今天',
                day: '0,0'
            },{
                name: '昨天',
                day: '-1,-1'
            }, {
                name: '过去7天',
                day: '-7,-1'
            }, {
                name: '过去30天',
                day: '-30,-1'
            }],
            hide: function (type) {
                this.$input.eq(0).val(this.$input.eq(0).val().substr(0, 10));
                this.$input.eq(1).val(this.$input.eq(1).val().substr(0, 10));
                startdate = this.$input.eq(0).val();
                enddate = this.$input.eq(1).val();
                $(".tab_1 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
                $(".tab_2 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
                if($(".tab_1 .bg_active").text() == '事件发生人数'){
                    rendmapshow('renshu');
                }else{
                    rendmapshow('cishu');
                }
                if($(".tab_2 .bg_active").text() == '汇总统计'){
                    tableShow("huizong");
                }else{
                    tableShow("detail");
                }
            }
        });
        startdate = getCurrentDay();
        enddate = getCurrentDay();

        startupfrequency(val);
		startupUser(val);
        rendmapshow('renshu');

        tableShow("huizong");
	});
	//日历显示时
    function calendarshow(){
	    $('.c-datepicker-picker').css({'left':'initial','right':'25px'});
        $('.c-datepicker-date-range-picker__editor-wrap').eq(1).css('display', 'none');
        $('.c-datepicker-date-range-picker__editor-wrap').eq(3).css('display', 'none');
	}


    //创建event事件名称的选择项
    function seteventname(type){
	    $('#eventname').html("");
    	var param = {
		   "businessType":type
	    }
	    $.ajax({
		    url: GUrl + proxy("/dataApiQuery/gct_collect_log_pi_ei",param),
		    type: "post",
		    dataType: "json",
		    headers: {
			    "Accept": "application/json",
			    "Content-Type": "application/json"
		    },
		    success: function (res) {
			    if (res) {
				    var list = res.data;
				    var str ='<option value="all" selected>全部</option>';
				    for(var i=0; i<list.length; i++){
					    str += '<option value="'+list[i].eventId+'">'+list[i].eventName+'</option>';
				    }
				    $('#eventname').append(str);
				    $("#eventname").select2({
					    placeholder: '全部',
					    width:'250px',
					    allowClear: true,
					    multiple:false
				    });
			    }
		    }
	    });
    }
    //event事件名称的选择
    function evevtnamechoose(){
	    $(".tab_1 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
	    $(".tab_2 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
	    if($(".tab .bg_active")[0].innerHTML == '事件发生人数'){
		    rendmapshow('renshu');
	    }else{
		    rendmapshow('cishu');
	    }
	    if($(".tab_2 .bg_active")[0].innerHTML == '汇总统计'){
		    tableShow("huizong");
	    }else{
		    tableShow("detail");
	    }
    }

    //发生人数
	function startupfrequency(businessType) {
		var appType="",userType="",
			appType = $("#appType option:selected").val(),
			userType = $("#userType option:selected").val();

		if(appType){
			param.source = appType;
		}else{
            param.source = '';
		}
		if(userType){
			param.deviceStatus = userType;
		}else{
            param.deviceStatus = '';
		}
        param.businessType = businessType;
		$.ajax({
			url: "./getEventStartupUsersInfo",
			type: "post",
			dataType: "json",
			headers: {
				"Accept": "application/json",
				"Content-Type": "application/json"
			},
			data: JSON.stringify(param),
			success: function (res) {
				if (res) {
					sUser = res.current;
					lastUser = res.last;
					user_hb = res.increasement;

					pageShow(res,"#startUser");
				}
			}
		});
	}

	//发生次数
	function startupUser(businessType) {
		var appType="",userType="",
			appType = $("#appType option:selected").val(),
			userType = $("#userType option:selected").val();

		if(appType){
			param.source = appType;
		}else{
            param.source = '';
		}
		if(userType){
			param.deviceStatus = userType;
		}else{
            param.deviceStatus = '';
		}
        param.businessType = businessType;
		$.ajax({
			url: "./getEventStartupTimesInfo",
			type: "post",
			dataType: "json",
			headers: {
				"Accept": "application/json",
				"Content-Type": "application/json"
			},
			data: JSON.stringify(param),
			success: function (res) {
				if (res) {
					sTimes = res.current;
					lastTimes = res.last;
					time_hb = res.increasement;
					pageShow(res, "#startTimes");
				}
			}
		});
	}

	//趋势图展示
    function rendmapshow(type){
        $('.trandmap').css({
			height: '300px',
			width: '100%'
		})
        var myChart = echarts.init(document.getElementsByClassName('trandmap')[0]);
        var opt = line_option;
        myChart.showLoading({
            text: ' ',
            effect: 'whirling',
            color: '#6F7E95',
            maskColor: '#FFFFFF',
        });
        myChart.clear();
		var appType = $("#appType_qst option:selected").val(),
            userType = $("#userType_qst option:selected").val(),
            enentid = $("#eventname option:selected").val();
		var params1 = {
          	is_current: 1,
			business_type: businessType,
			event_id: enentid,
			source: appType == ''?'all':appType,
			device_status: userType == ''?'all':userType,
			start_day: startdate,
			end_day: enddate,
		}
		var params_2 = {
            businessType: businessType,
			deviceStatus: (userType == 'all'?'':userType),
			eventId: (enentid == 'all'?'':enentid),
			queryDate: enddate,
			source: (appType == 'all'?'':appType),
		}
		var urls = '';
		if(startdate == enddate && startdate == getCurrentDay()){
            urls = (type == 'renshu'?'./getEventStartupUsersInfoTrend':'./getEventStartupTimesInfoTrend');
		}else{
            urls = GUrl + proxy("/dataApiQuery/gct_event_data_trend", params1);
		}
		//本期
		var prestartdate = '', preenddate = '';
        $.ajax({
            url: urls,
            type: "post",
            dataType: "json",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
			data: JSON.stringify((startdate == enddate && startdate == getCurrentDay())?params_2:''),
            success: function (res) {
	            var xdata = [];
	            if(startdate == enddate && startdate == getCurrentDay()){
                    if(res){
                        var serises = [];
                        var canzhao = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
                        var list = datassort(canzhao, res, 'key');
                        if(list && list.length > 0){
	                        for(var i=0; i<list.length; i++){
		                        xdata.push(i+"点");
		                        serises.push(list[i]?(list[i].value?list[i].value:'0'):'')
	                        }
	                        opt.series[0].data = serises;
	                        opt.xAxis.data = xdata;
	                        opt.xAxis.axisLabel.interval = 0;
	                        opt.xAxis.axisLabel.formatter = function(params){
		                        return params;
	                        }
                        }else{
	                        opt.series[0].data = [];
                        }
                        opt.tooltip.formatter= '';
                        myChart.hideLoading();
	                    myChart.setOption(opt,true);
                    }else{
                        myChart.hideLoading();
	                    myChart.setOption(opt,true);
                    }
				}else{
					if(res && res.data){
                        var serises = [];
                        var list = res.data;
                        if(list && list.length>0){
	                        prestartdate = list[0].pre_start_date;
	                        preenddate = list[0].pre_end_date;
	                        for(var i=0; i<list.length; i++){
		                        xdata.push(list[i]?(list[i].dt?list[i].dt:''):'');
		                        serises.push(list[i]?(list[i][type == 'renshu'?'event_user_num':'event_num']?list[i][type == 'renshu'?'event_user_num':'event_num']:'0'):'')
	                        }
	                        opt.series[0].data = serises;
	                        opt.xAxis.data = xdata;
	                        if(xdata.length >= 10){
		                        var n = (xdata.length / 10);
		                        opt.xAxis.axisLabel.interval = Math.ceil(n);
	                        }else{
		                        opt.xAxis.axisLabel.interval =0;
	                        }
	                        opt.tooltip.formatter= function(params){
		                        var content = '';
		                        for(var i=0; i<params.length; i++){
			                        if(params[i].value && params[i].value!=''){
				                        content += '<p style="margin: 0;float:left;">'+ params[i].name +'  <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:'+params[i].color+'"></div><span style="margin-left: 5px">' + params[i].seriesName + '</span> : ' + params[i].value +  '</p>';
			                        }
		                        }
		                        return '<div>'+ content + '</div>'
	                        };
                        }else{
	                        opt.series[0].data = [];
	                        opt.tooltip.formatter='';
                        }

                        myChart.hideLoading();
						myChart.setOption(opt,true);
					}else{
                        myChart.hideLoading();
						myChart.setOption(opt,true);
					}
				}
                //上期
                if(startdate == enddate && startdate == getCurrentDay()){
                    params_2.queryDate = dayHandle(enddate, -1);
                    urls2 = (type == 'renshu'?'./getEventStartupUsersInfoTrend':'./getEventStartupTimesInfoTrend');
                }else{
                    params1.is_current = 0;
                    params1.start_day = prestartdate;
                    params1.end_day = preenddate;
                    urls2 = GUrl + proxy("/dataApiQuery/gct_event_data_trend", params1);
                }
                $.ajax({
                    url: urls2,
                    type: "post",
                    dataType: "json",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    data: JSON.stringify((startdate == enddate && startdate == getCurrentDay())?params_2:''),
                    success: function (res) {
                        if(startdate == enddate && startdate == getCurrentDay()){
                            if (res) {
                                var serises2 = [];
                                var canzhao = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
                                var list2 = datassort(canzhao, res, 'key');
	                            if(list2 && list2.length > 0){
		                            for(var i=0; i<list2.length; i++){
			                            serises2.push(list2[i]?(list2[i].value?list2[i].value:'0'):'0')
		                            }
		                            opt.series[1].data = serises2;
	                            }else{
		                            opt.series[1].data = [];
	                            }
                                opt.tooltip.formatter= '';
                                myChart.hideLoading();
	                            myChart.setOption(opt,true);
                            }else{
                                myChart.hideLoading();
	                            myChart.setOption(opt,true);
							}
						}else{
                            if(res && res.data){
                                var serises2 = [];
                                var realdt = [];
	                            var list = datassort(xdata, res.data,'dt');
                                if(list && list.length > 0){
	                                for(var i=0; i<list.length; i++){
                                        if(type == 'renshu'){
			                                serises2.push(list[i]?(list[i]['event_user_num']?list[i]['event_user_num']:'0'):'0');
		                                }else{
			                                serises2.push(list[i]?(list[i]['event_num']?list[i]['event_num']:'0'):'0');
		                                }
		                                realdt.push(list[i].real_dt?list[i].real_dt:"");
	                                }
	                                opt.series[1].data = serises2;
	                                opt.tooltip.formatter= function(params){
		                                var content = '';
		                                for(var i=0; i<params.length; i++){
			                                if(params[i].value && params[i].value!=''){
				                                if(i == 0){
					                                content += '<div>'+ params[i].name +'  <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:'+params[i].color+'"></div><span style="margin-left: 5px">' + params[i].seriesName + '</span> : ' + params[i].value +  '</div>';
				                                }else{
				                                	if(realdt[params[i].dataIndex]){
						                                content += '<div>'+realdt[params[i].dataIndex]+' <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:'+params[i].color+'"></div><span style="margin-left: 5px;">' + params[i].seriesName + '</span> : ' + params[i].value +  '</div>';
					                                }else{
						                                content += '<div>&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:'+params[i].color+'"></div><span style="margin-left: 5px;">' + params[i].seriesName + '</span> : ' + params[i].value +  '</div>';
					                                }
				                                }
			                                }
		                                }
		                                return '<div>'+ content + '</div>'
	                                };
                                }else{
	                                opt.series[1].data = [];
	                                opt.tooltip.formatter='';
                                }
                                myChart.hideLoading();
                                myChart.setOption(opt,true);
                            }else{
                                myChart.hideLoading();
                                myChart.setOption(opt,true);
                            }
						}
                    }
                })
            }
        })
	}

    //表格列表
    function tableShow(type) {
	    $("#detailInfo,#detail").html("");
        var appType = $("#appType_qst option:selected").val(),
            userType = $("#userType_qst option:selected").val(),
            enentid = $("#eventname option:selected").val();
        var params1 = {
            is_detail: type == 'huizong'?0:1,
            business_type: businessType,
            event_id: enentid,
            source: appType == ''?'all':appType,
            device_status: userType == ''?'all':userType,
            start_day: startdate,
            end_day: enddate,
	        limit:100
        }
        var params_2 = {
            businessType: businessType,
            deviceStatus: (userType == 'all'?'':userType),
            eventId: (enentid == 'all'?'':enentid),
            queryDate: enddate,
            source: (appType == 'all'?'':appType),
	        limit:100
        }
        var urls = '';
        if(startdate == enddate && startdate == getCurrentDay()){
            urls = (type == 'huizong')?'./getGuancetaiEventDetailHuiZongList':'./getGuancetaiEventDetailList';
        }else{
            urls = GUrl + proxy("/dataApiQuery/gct_event_data_table", params1);
        }

	    //汇总
	    if(type == 'huizong'){
        	$("#example_detail,#example_detail_wrapper").css("display","none");
		    $('#example').show();
		    if(startdate == enddate && startdate == getCurrentDay()){
			    $('#example').DataTable({
				    // l - length changing input control
				    // f - filtering input
				    // t - The table
				    // i - Table information summary
				    // p - pagination control
				    // r - processing display element
				    dom: "lrtip",
				    language: i18nZH,
				    responsive: !0,
				    "lengthChange": false,//开启显示条数
				    "lengthMenu": [ 15, 50, 75, 100 ],
				    "ordering": true,//禁止排序
				    "processing": true,//刷新的那个对话框
				    "deferRender": false,//延迟渲染
				     "scrollX": true,//启用水平滚动
				     "autoWidth": true,//自动列宽
				    "destroy": true,
				    "ajax": {
					    "url":urls,
					    "type": "POST",
					    "async" : true,
					    dataType: "json",
					    contentType: "application/json;charset=utf-8",
					    data:function (d) {
						    return JSON.stringify($.extend({},d,params_2));
					    },
					    "dataFilter": function (res) {//res是服务器端返回的数据
						    var json = JSON.parse(res);
						    if(json.code == '0'){
//								console.log("data",json);
							    var returnData = {};
							    returnData.recordsTotal = json.total;//返回数据全部记录
							    returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
							    returnData.data =json.data;//返回的数据列表
							    return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
						    }else{
							    console.log("errorMsg",json.errorMsg,json.errInfo);
						    }
					    }
				    },
				    /*给数据添加列*/
				    columns: [
					    {data: 'eventId'},
					    {data: 'eventName'},
					    {data: 'pageName'},
					    {data: 'eventType'},
					    {data: 'source'},
					    {data: 'visitCount'},
					    {data: 'visitUsers'}
				    ],
			    });
		    }else{
			    $('#example').DataTable({
				    dom: "lrtip",
				    language: i18nZH,
				    responsive: !0,
				    "lengthChange": true,//开启显示条数
				    "lengthMenu": [ 15, 50, 75, 100 ],
				    "ordering": true,//禁止排序
				    "processing": true,//刷新的那个对话框
				    "deferRender": false,//延迟渲染
				     "scrollX": true,//启用水平滚动
				     "autoWidth": true,//自动列宽
				    "destroy": true,
				    "ajax": {
					    "url":urls,
					    "type": "GET",
					    "async" : true,
					    "dataType": "json",
					    "dataFilter": function (res) {//res是服务器端返回的数据
						    var json = JSON.parse(res);
						    if(json.code == '0'){
							    var tableList=[],tableObj={},returnData = {};
                                // var n = countDays($('.c-datepicker-data-input').eq(0).val(),$('.c-datepicker-data-input').eq(1).val());
								// console.log(n);
//								console.log("data",json);
// 							    if(n>0){
// 								    var list = json.data;
// 								    if(list && list.length>0){
// 									    for(var i=0;i<list.length;i++){
// 										    if(list[i].event_user_num){
// 											    var avgNum = Math.round((list[i].event_user_num)/n);
// 											    tableObj={
// 												    "event_id":list[i].event_id?list[i].event_id:'',
// 												    "event_name":list[i].event_name?list[i].event_name:'',
// 												    "page_name":list[i].page_name?list[i].page_name:'',
// 												    "event_type":list[i].event_type?list[i].event_type:'',
// 												    "source":list[i].source?list[i].source:'',
// 												    "event_num":list[i].event_num?list[i].event_num:'0',
// 												    "event_user_num":avgNum?avgNum:'0',
// 											    }
// 										    }
// 										    tableList.push(tableObj);
// 									    }
// 									    returnData.data =tableList;//返回的数据列表
// 								    }
// 							    }else{
								    returnData.data =json.data;//返回的数据列表
							    // }
							    returnData.recordsTotal = json.total;//返回数据全部记录
							    returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
							    return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
						    }else{
							    console.log("errorMsg",json.errorMsg,json.errInfo);
						    }
					    }
				    },
				    /*给数据添加列*/
				    columns: [
					    {data: 'event_id'},
					    {data: 'event_name'},
					    {data: 'page_name'},
					    {data: 'event_type'},
					    {data: 'source'},
					    {data: 'event_num'},
					    {data: 'event_user_num'}
				    ],
			    });
		    }
	    }else{//详情
		    $("#example,#example_wrapper").css("display","none");
		    $("#example_detai").show();
		    if(startdate == enddate && startdate == getCurrentDay()){
			    $('#example_detail').DataTable({
				    dom: "lrtip",
				    language: i18nZH,
				    responsive: !0,
				    "lengthChange": true,//开启显示条数
				    "lengthMenu": [ 15, 50, 75, 100 ],
				    "ordering": true,//禁止排序
				    "processing": true,//刷新的那个对话框
				    "deferRender": false,//延迟渲染
				     "scrollX": true,//启用水平滚动
				     "autoWidth": true,//自动列宽
				    "destroy": true,
				    "ajax": {
					    "url":urls,
					    "type": "POST",
					    "async" : true,
					    dataType: "json",
					    contentType: "application/json;charset=utf-8",
					    data:function (d) {
						    return JSON.stringify($.extend({},d,params_2));
					    },
					    "dataFilter": function (res) {//res是服务器端返回的数据
						    var json = JSON.parse(res);
						    if(json.code == '0'){
//								console.log("data",json);
							    var returnData = {};
							    returnData.recordsTotal = json.total;//返回数据全部记录
							    returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
							    returnData.data =json.data;//返回的数据列表
							    return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
						    }else{
							    console.log("errorMsg",json.errorMsg,json.errInfo);
						    }
					    }
				    },
				    /*给数据添加列*/
				    columns: [
					    {data: 'time'},
					    {data: 'eventId'},
					    {data: 'eventName'},
					    {data: 'pageName'},
					    {data: 'eventType'},
					    {data: 'source'},
					    {data: 'visitCount'},
					    {data: 'visitUsers'}
				    ],
			    });
		    }else{
			    $('#example_detail').DataTable({
				    dom: "lrtip",
				    language: i18nZH,
				    responsive: !0,
				    "lengthChange": true,//开启显示条数
				    "lengthMenu": [ 15, 50, 75, 100 ],
				    "ordering": true,//禁止排序
				    "processing": true,//刷新的那个对话框
				    "deferRender": false,//延迟渲染
				     "scrollX": true,//启用水平滚动
				     "autoWidth": true,//自动列宽
				    "destroy": true,
				    "ajax": {
					    "url":urls,
					    "type": "GET",
					    "async" : true,
					    "dataType": "json",
					    "dataFilter": function (res) {//res是服务器端返回的数据
						    var json = JSON.parse(res);
						    if(json.code == '0'){
							    var returnData = {};
								returnData.data =json.data;//返回的数据列表
							    returnData.recordsTotal = json.total;//返回数据全部记录
							    returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
							    return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
						    }else{
							    console.log("errorMsg",json.errorMsg,json.errInfo);
						    }
					    }
				    },
				    /*给数据添加列*/
				    columns: [
					    {data: 'dt'},
					    {data: 'event_id'},
					    {data: 'event_name'},
					    {data: 'page_name'},
					    {data: 'event_type'},
					    {data: 'source'},
					    {data: 'event_num'},
					    {data: 'event_user_num'}
				    ],
			    });
		    }
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

    //下载
    function getExportDate() {
	    var userType = $("#userType_qst option:selected").val();
	    var eventid = $("#eventname option:selected").val();
	    var appType = $("#appType_qst option:selected").val();
	    var realTime_mapper = {
		    "eventId":' 事件ID',
		    "eventName": '事件名称',
		    "pageName": '页面名称',
		    "eventType": '类型',
		    "source": '端(IOS/Android)',
		    "visitCount": '事件发生次数',
		    "visitUsers": '事件发生人数'
	    };

	    var realTime_param ={
		    "businessType": businessType,
		    "deviceStatus": userType == 'all'?'':userType,
		    "eventId": eventid == 'all' ? '': eventid,
		    "source": appType == 'all'?'':appType,
		    "queryDate":getCurrentDay()
	    };

	    var mapper = {
		    "event_id":' 事件ID',
		    "event_name": '事件名称',
		    "page_name": '页面名称',
		    "event_type": '类型',
		    "source": '端(IOS/Android)',
		    "event_num": '事件发生次数',
		    "event_user_num": '事件发生人数'
	    };
		//离线的参数
	    var param = {
		    "business_type": businessType,
		    "device_status": userType == ''?'all':userType,
		    "event_id": eventid,
		    "source": appType == ''?'all':appType,
		    "start_day": startdate,
		    "end_day": enddate,
		    "limit":999999999
	    };
		//汇总
	    if($(".tab > div:first-child").attr("class")=="bg_active"){
		    if(startdate == getCurrentDay()){//实时数据(今天)

			    postDownLoadFile({
				    url: './exportGuancetaiEventDetailHuiZongList',
				    data: {
				    	"params":realTime_param,
					    "titles":realTime_mapper
				    },
				    method: 'post'
			    });
		    }else{
		    	param.is_detail= '0';
			    postDownLoadFile({
				    url: './dataApiExport/gct_event_data_table',
				    data: {
					    "params": param,
					    "titles": mapper
				    },
				    method: 'post'
			    });
		    }
	    }else{//详情
		    if(startdate == getCurrentDay()) {
			    postDownLoadFile({
				    url: '/exportGuancetaiEventDetailList',
				    data: {
					    "params":realTime_param,
					    "titles":realTime_mapper
				    },
				    method: 'post'
			    });
		    }else{
			    param.is_detail= '1';
			    postDownLoadFile({
				    url: './dataApiExport/gct_event_data_table',
				    data: {
					    "params": param,
					    "titles": mapper
				    },
				    method: 'post'
			    });
		    }
	    }
    }

	/*********前端交互*********/
	//业务切换
	$(".tabControl > div").click(function () {
		$(this).addClass("active").siblings("div").removeClass("active");
		var i = $(this).index();
		$(".tab>div").removeClass("bg_active");
		$(".tab>div:nth-child(1)").addClass("bg_active");
        businessType = i;
        sessionStorage.setItem('dingvalue',i);
		startdate = getCurrentDay();
		enddate = getCurrentDay();

        $('.c-datepicker-data-input').val(getCurrentDay());
		$('#appType option:first-child,#userType option:first-child,#eventname option:first-child').attr("selected","selected");
		$('#appType_qst option:first-child,#userType_qst option:first-child').attr("selected","selected");
		$(".tab_1 > div,.tab_2 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
		if(i==0){
			$(this).children("i").removeClass().addClass("qdLogo");
			$(this).siblings("div").children("i").removeClass().addClass("dgjLogo_n");
			$(".userText,#userType,#userType_qst").show();
		}else{
			$(this).children("i").removeClass().addClass("dgjLogo");
			$(this).siblings("div").children("i").removeClass().addClass("qdLogo_n");
			$(".userText,#userType,#userType_qst").hide();
		}
		seteventname(i);
        startupfrequency(i);
        startupUser(i);

        rendmapshow('renshu');
        tableShow('huizong');
	});

	//趋势图切换
	$(".tab_1 > div").click(function () {
		$(this).addClass("bg_active").siblings("div").removeClass("bg_active");
		var i = $(this).index();
		if(i==0){
            rendmapshow("renshu");
		}else if(i==1){
            rendmapshow("cishu");
		}
	});
    //表格详情切换
    $(".tab_2 > div").click(function () {
        $(this).addClass("bg_active").siblings("div").removeClass("bg_active");
        var i = $(this).index();
        if(i==0){
	        $("#example_detail,#example_detail_info,#example_detail_paginate").hide();
	        $("#example,#example_info,#example_paginate").show();
            tableShow("huizong");
        }else if(i==1){
	        $("#example,#example_info,#example_paginate").hide();
	        $("#example_detail,#example_detail_info,#example_detail_paginate").show();
            tableShow("detail");
        }
    });

	//切换条件查询统计数据
	function dataStatisticSelect(businessType) {
	    if(businessType == 0){
            startupfrequency(businessType);
            startupUser(businessType);
		}else{
            $(".tab_1 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
            $(".tab_2 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
            rendmapshow("renshu");
            tableShow('huizong');
		}

	}


</script>
</body>
</html>
