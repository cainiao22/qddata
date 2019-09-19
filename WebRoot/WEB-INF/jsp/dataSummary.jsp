<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
	<style>
		.main{position:relative;width:100%}
		.stateType{height:44px;line-height:44px;background:#333}
		.stateType div{float:left;display:inline-block;width:120px;text-align:center;cursor:pointer;color:#FFF}
		.stateType div.bg_active{background:#FDA413;font-family:PingFangSC-Medium;font-size:16px}
		.chartControl{width:100%;overflow:hidden;margin-bottom:10px}
		.chartControl .echart1,.chartControl .echart2{float:left;width:49.5%;height:300px;border-radius:4px;background:#232323}
		.chartControl .echart1{margin-right:1%}
	</style>
</head>

<body class="fix-header fix-sidebar card-no-border">
<div class="preloader">
	<svg class="circular" viewBox="25 25 50 50">
		<circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"/>
	</svg>
</div>

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
	<div class="page-wrapper">
		<!-- Container fluid  -->
		<div class="container-fluid" style="background: #191919">
			<!-- Bread crumb and right sidebar toggle -->
			<div class="row page-titles">
				<div class="col-md-6 col-8 align-self-center">
					<h3 class="text-themecolor m-b-0 m-t-0">${ sidebar_menu.parentName}</h3>
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a href="javascript:void(0)">${ sidebar_menu.parentName}</a></li>
						<li class="breadcrumb-item active">${ sidebar_menu.name} </li>
					</ol>
				</div>
			</div>
			<!-- End Bread crumb and right sidebar toggle -->
			<!-- Start Page Content -->
			<div class="row">
				<div class="main">
					<div class="stateType">
						<div class="bg_active">全部业主</div>
						<div>C端业主</div>
						<div>P端业主</div>
					</div>

					<div class="chartControl">
						<div class="echart1" id="userCityEchart"></div>
						<div class="echart2" id="userAgeEchart"></div>
					</div>
					<div class="chartControl">
						<div class="echart1" id="userEntranceEchart"></div>
						<div class="echart2" id="userQdLiveEchart"></div>
					</div>
					<div class="chartControl">
						<div class="echart1" id="userServiceEchart"></div>
						<div class="echart2" id="userRetailEchart"></div>
					</div>
					<div class="chartControl">
						<div class="echart1" id="userActiveRateEchart"></div>
						<div class="echart2" id="userActiveTypeEchart"></div>
					</div>
					<div class="chartControl">
						<div class="echart1" id="wuyeEchart"></div>
						<div class="echart2" id="baoshiEchart"></div>
					</div>
					<div class="chartControl">
						<div class="echart1" id="wuyeChannelEchart"></div>
					</div>

				</div>
			</div>
			<!-- End Page Content -->
		</div>
		<!-- footer -->
		<c:import url="common/footer.jsp"></c:import>
		<!-- End footer -->
	</div>
</div>
<c:import url="common/importJs2.jsp"></c:import>
<script type="text/javascript" src="./js/userProfileCommon.js"></script>
<script type="text/javascript" src="./thirdlib/echarts/echarts.all.min.js"></script>
<script type="text/javascript" src="./js/option.js"></script>

<script type="text/javascript">
	var lpdObj = JSON.parse(sessionStorage.getItem("lpdObj"));
	var cityArr=[],ageArr=[],entranceArr=[],fieldPreferenceArr=[],servicePreferenceArr=[],xlsPreferenceactiveRateArr=[],
		activeRateArr=[],activeTypeArr=[],wypayWillArr=[],postTypeArr=[],wypayTypeArr=[];

	(function () {
		getLpd();
	})();
	//获取缓存里的标签
	function getLpd() {
		var lpd = lpdObj;
		if(lpd) {
			cityArr = lpd["city"];
			ageArr = lpd["age"];
			entranceArr = lpd['entrance'];
			fieldPreferenceArr = lpd['field_preference'];
			servicePreferenceArr = lpd['service_preference'];
			xlsPreferenceactiveRateArr = lpd['xls_preference'];
			activeRateArr = lpd['active_rate'];
			activeTypeArr = lpd['active_type'];
			wypayWillArr = lpd['wypay_will'];
			postTypeArr = lpd['posttype'];
			wypayTypeArr = lpd['wypay_type_preference'];
		}
	}
	var titleList = [
		'用户城市分布',
		'用户年龄段分布',
		'出行规律',
		'千丁live领域偏好',
		'服务偏好',
		'新零售偏好',
		'app活跃频率',
		'app活跃类型',
		'物业费缴纳',
		'报事渠道',
		'物业缴费渠道',
	];
	var legendList = [
		['北京市','西安市','杭州市','重庆市','成都市','上海市','广州市','其它'],
		['未成年','青年','中青年','中年','老年'],
		['夜行人','朝九晚五','晚归','居家族','无规律'],
		['千丁live领域偏好'],
		['服务偏好'],
		['新零售偏好'],
		['连续活跃','高频用户','中频用户','低频用户','连续沉默'],
		['仅门禁活跃','仅物业服务活跃','仅千丁live活跃','其他'],
		['提前','准时','偶尔延期','经常延期','长期欠费'],
		['呼叫中心','物业代报','千丁报事'],
		['前台','银行代扣','千丁App','腾讯海纳'],
	];
	$(document).ready(function () {
		getAllUserInfo();
	});

	//选项卡切换
	$(".stateType > div").click(function () {
		$(this).addClass("bg_active").siblings("div").removeClass("bg_active");
		var i = $(this).index();
		$(".chartControl,.chartControl>div").show();
		if(i==0){
			getAllUserInfo();
		}else if(i==1){
			$("div.chartControl:nth-child(6),div.chartControl:nth-child(7)").hide();
			getCduanUserInfo();
		}else if(i==2){
			$("div.chartControl:nth-child(3),div.chartControl:nth-child(4),div.chartControl:nth-child(5)").hide();
			getPduanUserInfo();
		}
	});

	//用户城市分布
	function chartCityFun(obj) {
		var myChart = echarts.init(document.getElementById('userCityEchart'));
		var opt = pie_option;
		opt.title.text=titleList[0];
		opt.series[0].name=titleList[0];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],legendData=[];
		if(obj) {
			getData(obj, cityArr, legendData, seriesData, "city");
		}

		if(legendData.length>0){
			opt.legend[0].data=legendData.slice(0,4);
			opt.legend[1].data=legendData.slice(4);
		}else{
			opt.legend[0].data=legendList[0].slice(0,4);
			opt.legend[1].data=legendList[0].slice(4);
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"北京市"},{value:0, name:"西安市"},{value:0, name:"杭州市"},{value:0, name:"重庆市"},{value:0, name:"成都市"},{value:0, name:"上海市"},{value:0, name:"广州市"},{value:0, name:"其它"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'用户年龄段分布'
	function chartAgeFun(obj) {
		var myChart = echarts.init(document.getElementById('userAgeEchart'));
		var opt = pie_option;
		opt.title.text=titleList[1];
		opt.series[0].name=titleList[1];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],legendData=[];
		if(obj) {
			getData(obj, ageArr, legendData, seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}else{
			opt.legend.data= legendList[1];
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"未成年"},{value:0, name:"青年"},{value:0, name:"中青年"},{value:0, name:"中年"},{value:0, name:"老年"},];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'出行规律'
	function chartEntranceFun(obj) {
		var myChart = echarts.init(document.getElementById('userEntranceEchart'));
		var opt = stack_bar_option;
		opt.title.text=titleList[2];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var xAxisData=[],citys=[], seriesData1=[], seriesData2=[], seriesData3=[], seriesData4=[], seriesData5=[];
		if(obj){
			if(cityArr && cityArr.length > 0){
				for(var i=0;i<cityArr.length;i++){
					var id = cityArr[i].example_id;
					var name = cityArr[i].example_name;
					if(id != -1){
						//柱状图需要动态更新横坐标
						if(name=="北京市" || name=="重庆市" || name=="成都市" || name=="上海市" || name=="西安市" || name=="杭州市" || name=="广州市"){
							xAxisData.push(name);
							citys.push({cityId:id,cityName:name});
						}
					}
				}
			}

			for(var i=0;i<citys.length;i++){
				var id = citys[i].cityId;

				if(obj[id] && obj[id]["entrance_01"]){
					seriesData1.push(obj[id]["entrance_01"]);
				}else{
					seriesData1.push(0);
				}

				if(obj[id] && obj[id]["entrance_02"]){
					seriesData2.push(obj[id]["entrance_02"]);
				}else{
					seriesData2.push(0);
				}

				if(obj[id] && obj[id]["entrance_03"]){
					seriesData3.push(obj[id]["entrance_03"]);
				}else{
					seriesData3.push(0);
				}

				if(obj[id] && obj[id]["entrance_04"]){
					seriesData4.push(obj[id]["entrance_04"]);
				}else{
					seriesData4.push(0);
				}

				if(obj[id] && obj[id]["entrance_05"]){
					seriesData5.push(obj[id]["entrance_05"]);
				}else{
					seriesData5.push(0);
				}
			}
		}
		opt.xAxis.data=xAxisData;

		opt.series[0].data = seriesData1;
		opt.series[1].data = seriesData2;
		opt.series[2].data = seriesData3;
		opt.series[3].data=seriesData4;
		opt.series[4].data=seriesData5;

		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'千丁live领域偏好'
	function chartQdingLiveFun(obj) {
		var myChart = echarts.init(document.getElementById('userQdLiveEchart'));
		var opt = bar_option;
		opt.title.text=titleList[3];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
        myChart.clear();
        var seriesData=[],xAxisData=[],nameList=[];
        if(obj && obj.length > 0) {
            for(var i=0;i<obj.length;i++){
                if(i<10 && obj[i]){
                    var xname = obj[i].name;
                    if(xname) {
                        var name =xname.substr(0,6);
                        xAxisData.push(name);
                        nameList.push(xname);
                        seriesData.push(obj[i].personNum);
                    }
                }
            }
            opt.xAxis.data=xAxisData;
            opt.series[0].data = seriesData;
            opt.tooltip.formatter = function (params, ticket, callback) {
                var content = '';
                var j = params[0].dataIndex;
                if(params[0]){
                    for (var i = 0; i < params.length; i++) {
                        content += '<div style="color:' + params[i].color + '">' + params[i].marker + nameList[j] +':'+ params[i].value + '</div>'
                    }
                }
                return  '<div>' + content + '</div>';
            };

            var maxVal = getMaxValue(seriesData);
            if(maxVal > 1000){
                opt.yAxis[0].axisLabel.formatter = function(value, index) {
                    return value / 1000 + 'k';
                }
            }else{
                opt.yAxis[0].axisLabel.formatter = function(value, index) {
                    return value;
                }
            }
        }else{
            opt.xAxis.data=[];
            opt.series[0].data = [];
        }
		myChart.hideLoading();
		myChart.setOption(opt);
	}

	//'服务偏好'
	function chartServiceFun(obj) {
		var myChart = echarts.init(document.getElementById('userServiceEchart'));
		var opt = bar_option;
		opt.title.text=titleList[4];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],xAxisData=[],nameList=[];
		if(obj && obj.length > 0) {
			for(var i=0;i<obj.length;i++){
				if(i<10 && obj[i]){
					var xname = obj[i].name;
					if(xname) {
						var name =xname.substr(0,6);
						xAxisData.push(name);
						nameList.push(xname);
						seriesData.push(obj[i].personNum);
					}
				}
			}
			opt.xAxis.data=xAxisData;
			opt.series[0].data = seriesData;
			opt.tooltip.formatter = function (params, ticket, callback) {
				var content = '';
				var j = params[0].dataIndex;
				if(params[0]){
					for (var i = 0; i < params.length; i++) {
						content += '<div style="color:' + params[i].color + '">' + params[i].marker + nameList[j] +':'+ params[i].value + '</div>'
					}
				}
				return  '<div>' + content + '</div>';
			};

			var maxVal = getMaxValue(seriesData);
			if(maxVal > 1000){
				opt.yAxis[0].axisLabel.formatter = function(value, index) {
					return value / 1000 + 'k';
				}
			}else{
				opt.yAxis[0].axisLabel.formatter = function(value, index) {
					return value;
				}
			}
		}else{
			opt.xAxis.data=[];
			opt.series[0].data = [];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'新零售偏好'
	function chartXLSEchartFun(obj) {
		var myChart = echarts.init(document.getElementById('userRetailEchart'));
		var opt = bar_option;
		opt.title.text=titleList[5];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],xAxisData=[],nameList=[];
		if(obj && obj.length > 0) {
			for(var i=0;i<obj.length;i++){
				if(i<10 && obj[i]){
					var xname = obj[i].name;
					if(xname){
						var name =xname.substr(0,6);
						xAxisData.push(name);
						nameList.push(xname);
						seriesData.push(obj[i].personNum);
					}
				}
			}
			opt.xAxis.data=xAxisData;
			opt.series[0].data = seriesData;
			opt.tooltip.formatter = function (params, ticket, callback) {
				var content = '';
				var j = params[0].dataIndex;
				if(params[0]){
					for (var i = 0; i < params.length; i++) {
						content += '<div style="color:' + params[i].color + '">' + params[i].marker + nameList[j] +':'+ params[i].value + '</div>'
					}
				}
				return  '<div>' + content + '</div>';
			};
		}else{
			opt.xAxis.data=[];
			opt.series[0].data = [];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}

	//'app活跃频率'
	function chartActiveRateFun(obj) {
		var myChart = echarts.init(document.getElementById('userActiveRateEchart'));
		var opt = pie_option;
		opt.title.text=titleList[6];
		opt.series[0].name=titleList[6];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();

		var seriesData=[],legendData=[];
		if(obj) {
			getData(obj, activeRateArr, legendData, seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}else{
			opt.legend.data= legendList[6];
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"连续活跃"},{value:0, name:"高频用户"},{value:0, name:"中频用户"},{value:0, name:"低频用户"},{value:0, name:"连续沉默"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'app活跃类型'
	function chartActiveTypeFun(obj) {
		var myChart = echarts.init(document.getElementById('userActiveTypeEchart'));
		var opt = pie_option;
		opt.title.text=titleList[7];
		opt.series[0].name=titleList[7];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],legendData=[];
		if(obj){
			getData(obj,activeTypeArr,legendData,seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}else{
			opt.legend.data= legendList[7];
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"仅门禁活跃"},{value:0, name:"仅物业服务活跃"},{value:0, name:"仅千丁live活跃"},{value:0, name:"其他"}];
		}

		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'物业费缴纳'
	function chartWyPayFun(obj) {
		var myChart = echarts.init(document.getElementById('wuyeEchart'));
		var opt = pie_option;
		opt.title.text=titleList[8];
		opt.series[0].name=titleList[8];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();

		var seriesData=[],legendData=[];
		if(obj) {
			getData(obj, wypayWillArr, legendData, seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}else{
			opt.legend.data= legendList[8];
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"提前"},{value:0, name:"准时"},{value:0, name:"偶尔延期"},{value:0, name:"经常延期"},{value:0, name:"长期欠费"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'报事渠道'  没有值
	function chartPostTypeFun(obj) {
		var myChart = echarts.init(document.getElementById('baoshiEchart'));
		var opt = pie_option;
		opt.title.text=titleList[9];
		opt.series[0].name=titleList[9];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],legendData=[];
		if(obj){
			getData(obj,postTypeArr,legendData,seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}else{
			opt.legend.data= legendList[9];
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"呼叫中心"},{value:0, name:"物业代报"},{value:0, name:"千丁报事"}];
		}

		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'物业缴费渠道'
	function chartWypayTypeFun(obj) {
		var myChart = echarts.init(document.getElementById('wuyeChannelEchart'));
		var opt = pie_option;
		opt.title.text=titleList[10];
		opt.series[0].name=titleList[10];
		var seriesData=[],legendData=[];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		if(obj){
			getData(obj,wypayTypeArr,legendData,seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}else{
			opt.legend.data=legendList[10];
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"前台"},{value:0, name:"银行代扣"},{value:0, name:"千丁App"},{value:0, name:"腾讯海纳"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}

	//获取全部用户
	function getAllUserInfo() {
		$.ajax({
			url:"./queryAllUserOverview",
			type:"get",
			success:function(res){
//				console.log(res);
				if(res){
					//用户城市分布
					var userGroupCity = res.userGroupCity;
					//用户年龄分布
					var userGroupAge = res.userGroupAge;
					//出行规律
					var userGroupEntrance = res.userGroupEntrance;
					//服务偏好
					var userGroupServicePreference = res.userGroupServicePreference;
					//新零售偏好
					var userGroupXlsPreference = res.userGroupXlsPreference;
                    //千丁Live领域偏好
                    var userGroupFieldPreference = res.userGroupFieldPreference;
					//活跃频率
					var userGroupactiveRate = res.userGroupactiveRate;
					//活跃类型
					var userGroupActiveType = res.userGroupActiveType;
					//物业费缴纳
					var userGroupWyPayWill = res.userGroupWyPayWill;
					//报事渠道
					var userGroupPostType = res.userGroupPostType;
					// 物业缴费渠道
					var userGroupWypayTypePreference = res.userGroupWypayTypePreference;

					chartCityFun(userGroupCity);
					chartAgeFun(userGroupAge);
					chartEntranceFun(userGroupEntrance);
					chartQdingLiveFun(userGroupFieldPreference);
					chartServiceFun(userGroupServicePreference);
					chartXLSEchartFun(userGroupXlsPreference);
					chartActiveRateFun(userGroupactiveRate);
					chartActiveTypeFun(userGroupActiveType);
					chartWyPayFun(userGroupWyPayWill);
					chartPostTypeFun(userGroupPostType);
					chartWypayTypeFun(userGroupWypayTypePreference);

					if(userGroupServicePreference && userGroupServicePreference.length > 0){
						sessionStorage.setItem("serviceData",JSON.stringify(userGroupServicePreference));
					}
					if(userGroupXlsPreference && userGroupXlsPreference.length > 0) {
						sessionStorage.setItem("xlsData", JSON.stringify(userGroupXlsPreference));
					}
				}
			}
		});
	}

	//获取C端用户
	function getCduanUserInfo() {
		$.ajax({
			url:"./queryCUserOverview",
			type:"get",
			success:function(res){
//				console.log(res);
				if(res){
					//用户城市分布
					var userGroupCity = res.userGroupCity;
					//用户年龄分布
					var userGroupAge = res.userGroupAge;
					//出行规律
					var userGroupEntrance = res.userGroupEntrance;
					//服务偏好
					var userGroupServicePreference = res.userGroupServicePreference;
					//新零售偏好
					var userGroupXlsPreference = res.userGroupXlsPreference;
					//活跃频率
					var userGroupactiveRate = res.userGroupactiveRate;
					//活跃类型
					var userGroupActiveType = res.userGroupActiveType;

					chartCityFun(userGroupCity);
					chartAgeFun(userGroupAge);
					chartEntranceFun(userGroupEntrance);
//					chartQdingLiveFun(null);
					chartServiceFun(userGroupServicePreference);
					chartXLSEchartFun(userGroupXlsPreference);
					chartActiveRateFun(userGroupactiveRate);
					chartActiveTypeFun(userGroupActiveType);

				}
			}
		});
	}

	//获取P端用户
	function getPduanUserInfo() {
		$.ajax({
			url:"./queryPUserOverview",
			type:"get",
			success:function(res){
//				console.log(res);
				if(res){
					//用户城市分布
					var userGroupCity = res.userGroupCity;
					//用户年龄分布
					var userGroupAge = res.userGroupAge;
					//物业费缴纳
					var userGroupWyPayWill = res.userGroupWyPayWill;
					//报事渠道
					var userGroupPostType = res.userGroupPostType;
					// 物业缴费渠道
					var userGroupWypayTypePreference = res.userGroupWypayTypePreference;

					chartCityFun(userGroupCity);
					chartAgeFun(userGroupAge);
					chartWyPayFun(userGroupWyPayWill);
					chartPostTypeFun(userGroupPostType);
					chartWypayTypeFun(userGroupWypayTypePreference);
				}
			}
		});
	}


</script>
</body>
</html>
