<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<!--画像分析详情-->
<html>
<head>
	<meta charset="utf-8"/>
	<title>北斗星-大数据中心</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
	<c:import url="common/importCss2.jsp"></c:import>
	<link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>
	<style>
		body{font-family:PingFangSC-Regular;font-size:14px;color:#DEDEDE}
		.userIcon{display:inline-block;margin-bottom:-5px;width:22px;height:22px;background:url(../../images/user.png) no-repeat;background-size:22px 22px;cursor:pointer}
		.leftPanel{float:left;padding:10px 15px 10px 0;height:100%;border-right:1px solid #333;color:#939393}
		.leftPanel .userInfo{position:relative;margin-right:15px;margin-bottom:15px}
		.leftPanel .userInfo span{height:30px;color:#dedede;font-size:1pc}
		.leftPanel .userInfo input{margin-top:15px;padding-left:10px;width:100%;height:30px;border:0;border-radius:2px;background:#191919;color:#dedede;font-size:14px;line-height:30px}
		#phoneList{overflow-y:scroll;height:324px}
		#phoneList::-webkit-scrollbar-track{border-radius:5px;-webkit-box-shadow:inset 0 0 6px rgba(0,0,0,.3)}
		#phoneList::-webkit-scrollbar{width:10px}
		#phoneList::-webkit-scrollbar-thumb{border-radius:5px;background-color:#4e4e4e;-webkit-box-shadow:inset 0 0 6px rgba(0,0,0,.3)}
		.leftPanel .phoneNumber{position:relative;display:inline-flex;width:100%;height:36px;border-bottom:1px solid #333;line-height:36px;cursor:pointer}
		.leftPanel .phoneNumber label{display:inline-block;overflow:hidden;margin:0;width:75%;text-overflow:ellipsis;cursor:pointer}
		.leftPanel .active{color:#fda413}
		.leftPanel .phoneNumber span{float:right}
		.leftPanel .phoneNumber>div{display:inline-block;overflow:hidden;margin-left:10px;text-overflow:ellipsis}
		.rightPanel{display:inline-flex;padding:10px 0 10px 15px}
		.rightPanel .condition{overflow:hidden;width:100%}
		#detailList>div{display:none}
		.information{margin-bottom:10px;color:#dedede;line-height:30px}
		.information .detailInfo{display:flex;padding:0 8px;border-radius:2px;background:#191919;line-height:40px}
		.information .detailInfo span{flex:1}
		.but{display:inline-block;margin:5px 0;width:70px;border-radius:2px;text-align:center;line-height:30px}
		.cancel{border:1px solid #939393;font-size:14px}
		.cancel a{color:#dedede}

	</style>
</head>
<!-- END HEAD -->
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
	<!-- Page wrapper  -->
	<div class="page-wrapper">
		<!-- Container fluid  -->
		<div class="container-fluid">
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
			<!-- Row -->
			<div class="row">
				<div class="col-md-12" style="background: #232323;">
					<div class="col-md-3 leftPanel">
						<div class="userInfo">
							<i class="userIcon"></i>
							<span id="totalHits"></span>个人
							<div><input type="text" placeholder="请输入手机号或用户ID" id="searchBox" onkeyup="searchPhone()"></div>
						</div>
						<div id="phoneList"></div>


					</div>

					<div class="col-md-9 rightPanel">
						<div class="condition">
							<div id="detailList"></div>
							<p class="but cancel"><a href="javascript:history.go(-1)">返回</a></p>
						</div>
					</div>
				</div>
			</div>
			<!-- Row -->
			<!-- End PAge Content -->
		</div>
		<!-- End Container fluid  -->
		<!-- footer -->
		<c:import url="common/footer.jsp"></c:import>
		<!-- End footer -->
	</div>
	<!-- End Page wrapper  -->
</div>
<!-- End Wrapper -->

<c:import url="common/importJs2.jsp"></c:import>
<script type="text/javascript" src="./js/userProfileCommon.js"></script>
<script>
	var firstParam = JSON.parse(sessionStorage.getItem("firstParam"));
	var lpdObj = JSON.parse(sessionStorage.getItem("lpdObj"));
	var flag = true; //控制搜索 键盘事件调多次
	var tagList=[];
	$(document).ready(function () {
		$("#phoneList,#detailList").html("");
		profileDetailList();

	});

	//查询列表
	var currentPage = 1; //当前页
	var pageSize = 10;   //每页记录
	//搜索的参数
	var fromPage = 1;
	function profileDetailList(type) {
		var conditionList = JSON.parse(sessionStorage.getItem("conditionList"));
		var startCount = pageSize*(currentPage-1);

		var param = {
			"from":startCount.toString(),
			"size":pageSize.toString()
		}
		if(conditionList && conditionList.length>0){
			param.conditions = conditionList;
		}
		if(type=="search"){
			param.from = pageSize*(fromPage-1).toString();
			param.wildcardParam = $("#searchBox").val();
		}
		$.ajax({
			url: "./getUserDetailsByPagination",
			type: "post",
			data:{
				"query":JSON.stringify(param)
			},
			success: function (res) {
				if(type=="search"){
					if (res) {
						var details = res.details;
						var totalHits = res.totalHits;
						$("#totalHits").html("").text(totalHits.toLocaleString());
						if(details && details.length>0){
							detailShow(details);
						}else{
							$("#phoneList").append("<div style='text-align: center'>没有更多了~</div>");
						}
					}else{
						$(".condition").text("没有查到数据");
					}
					flag = true;
				}else{
					if (res) {
						var details = res.details;
						var totalHits = res.totalHits;
						$("#totalHits").html("").text(totalHits.toLocaleString());
						if(details && details.length>0){
							detailShow(details);
						}else{
							$("#phoneList").append("<div style='text-align: center'>没有更多了~</div>");
						}
					}else{
						$(".condition").text("没有查到数据");
					}
				}

			}
		});
	}

	function detailShow(details) {
		var lpd = lpdObj;
		var phoneStr="",detailStr="";
		for(var i=0;i<details.length;i++){
			var obj = JSON.parse(details[i]);
//			console.log(obj);

			var pid = obj.z_id;
			var phone = obj.phone;
			var username = obj.username;
			var sex = compareFun(lpd["sex"],obj.sex);

			//只取5大类的数据
			var city = compareFun(lpd["city"],obj.city);
			var age =compareFun(lpd["age"],obj.age);
			var communityvalue = compareFun(lpd["communityvalue"],obj.communityvalue);
			var management = compareFun(lpd["management"],obj.management);

			var consumption = compareFun(lpd["consumption"],obj.consumption);
			var houseasset = compareFun(lpd["houseasset"],obj.houseasset);

			var xls_preference = compareFun(lpd["xls_preference"],obj.xls_preference);
			var service_preference = compareFun(lpd["service_preference"],obj.service_preference);
			var field_preference = compareFun(lpd["field_preference"],obj.field_preference);

			var active_rate = compareFun(lpd["active_rate"],obj.active_rate);
			var active_type = compareFun(lpd["active_type"],obj.active_type);
			var entrance = compareFun(lpd["entrance"],obj.entrance);

			var wypay_will = compareFun(lpd["wypay_will"],obj.wypay_will);
			var posttype = compareFun(lpd["posttype"],obj.posttype);
			var wypay_type_preference = compareFun(lpd["wypay_type_preference"],obj.wypay_type_preference);

			phoneStr +='<div class="phoneNumber">';
			phoneStr +='<label>';
			if(phone){
				phoneStr +=phone;
			}
			phoneStr +='</label>';
			if(pid){
				phoneStr +='<div>ID:'+pid+'</div>';
			}
			phoneStr +='</div>';

			detailStr += '<div>';
			detailStr += '<div class="information">';
			detailStr += '<label>用户信息</label>';
			detailStr += '<div class="detailInfo">';
			detailStr += '<span>姓名：'+username+'</span>';
			detailStr += '<span>性别：'+sex+'</span>';
			detailStr += '<span>年龄段：'+age+'</span>';
			detailStr += '<span>常住城市：'+city+'</span>';
			detailStr += '</div>';
			detailStr += '<div class="detailInfo">';
			detailStr += '<span>社区价值：'+communityvalue+'</span>';
			detailStr += '<span>社区管理水平：'+management+'</span>';
			detailStr += '<span></span>';
			detailStr += '<span></span>';
			detailStr += '</div>';
			detailStr += '</div>';
			detailStr += '<div class="information">';
			detailStr += '<label>商业属性</label>';
			detailStr += '<div class="detailInfo">';
			detailStr += '<span>消费能力：'+consumption+'</span>';
			detailStr += '<span>房屋资产：'+houseasset+'</span>';
			detailStr += '<span></span>';
			detailStr += '<span></span>';
			detailStr += '</div>';
			detailStr += '</div>';
			detailStr += '<div class="information">';
			detailStr += '<label>习惯偏好</label>';
			detailStr += '<div class="detailInfo">';
			detailStr += '<span>新零售偏好：'+xls_preference+'</span>';
			detailStr += '<span>服务偏好：'+service_preference+'</span>';
			detailStr += '<span>邻域偏好：'+field_preference+'</span>';
			detailStr += '<span></span>';
			detailStr += '</div>';
			detailStr += '</div>';
			detailStr += '<div class="information">';
			detailStr += '<label>活跃及出行</label>';
			detailStr += '<div class="detailInfo">';
			detailStr += '<span>app活跃频率：'+active_rate+'</span>';
			detailStr += '<span>app活跃类型：'+active_type+'</span>';
			detailStr += '<span>出行规律：'+entrance+'</span>';
			detailStr += '<span></span>';
			detailStr += '</div>';
			detailStr += '</div>';
			detailStr += '<div class="information">';
			detailStr += '<label>物业属性</label>';
			detailStr += '<div class="detailInfo">';
			detailStr += '<span>物业费缴纳：'+wypay_will+'</span>';
			detailStr += '<span>报事渠道：'+posttype+'</span>';
			detailStr += '<span>物业缴费渠道：'+wypay_type_preference+'</span>';
			detailStr += '<span></span>';
			detailStr += '</div>';
			detailStr += '</div>';
			detailStr += '</div>';

//			var tag="",tagName="",tagvalueId="",tagvalue=""; //标签值中文 未成年
//			for(var m=0;m<tagList.length;m++){
//				tag = tagList[m].key;       //标签英文 age
//				tagName = tagList[m].value; //标签中文 年龄
//				tagvalueId = obj[tag];       //标签值枚举 age_01
//
//				var lpdArray = lpd[tag];
//				if(lpdArray && lpdArray.length>0){
//					for(var n=0;n<lpdArray.length;n++){
//						var id = lpdArray[n].example_id;
//						if(tagvalueId==id){
//							tagvalue = lpdArray[n].example_name;
//						}
//					}
//					resultArr.push({id:i,tag:tag,name:tagName,valueId:tagvalueId,value:tagvalue});
//				}else{
//					resultArr.push({id:i,tag:tag,name:tagName,valueId:tagvalueId,value:""});
//				}
//			}
		}
		$("#phoneList").append(phoneStr);
		$("#phoneList > div:first").addClass("active");
		$("#detailList").append(detailStr);
		$("#detailList > div:first").show();

		//点击事件
		$("#phoneList .phoneNumber").click(function () {
			$(this).addClass("active").siblings(".phoneNumber").removeClass("active");
			var i = $(this).index();
			$("#detailList > div").eq(i).show().siblings().hide();
		});

		//页面滚到底部异步加载下一页数据
		$("#phoneList").scroll(function () {
			//已经滚动到上面的页面高度
			var scrollTop = parseFloat($(this).scrollTop()),
				//div窗口高度
				windowHeight = parseFloat($(this).height()),
				scrollHeight = parseFloat(currentPage*pageSize*36);
//			console.log("当前页："+currentPage+"距离顶部："+scrollTop+",当前窗口高度："+windowHeight+",滚动高度："+scrollHeight);
			//此处是滚动条到底部时候触发的事件，或者是拉动滚动条的操作
			if (scrollTop + windowHeight >= scrollHeight) {
				currentPage +=1;
				profileDetailList();
			}
		});
	}

	function searchPhone() {
		$("#phoneList,#detailList").html("");
		if(flag){
			flag=false;
			profileDetailList('search');
		}
	}
	//循环得到标签
	function compareFun(valueList,val) {
		if(!val || val=="-1"){
			return "无标签";
		}
		if(valueList && valueList.length > 0){
			for(var m=0;m<valueList.length;m++){
				var m_obj = valueList[m];
				if(val == m_obj.example_id){
					return m_obj.example_name;
				}
			}
		}
	}

</script>
</body>
</html>
