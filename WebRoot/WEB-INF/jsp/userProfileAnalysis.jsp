<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<!--画像分析-->
<html>
<head>
    <meta charset="utf-8"/>
    <title>钦天监-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
	<link rel="stylesheet" href="./assets/plugins/jquery-ui/jquery-ui.min.css">
	<link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>
	<link href="./thirdlib/Select2/select2.min.css" rel="stylesheet" type="text/css"/>
	<link href="./css/loading.css" rel="stylesheet" type="text/css"/>
	<link href="./css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>

	<style>
		body{font-family:PingFangSC-Regular;font-size:14px;color:#DEDEDE}
		.deleteIcon{background:url(../../images/icon_operation_del.png) no-repeat;background-size:30px 30px;width:30px;height:30px;float:left;cursor:pointer}
		.addIcon{background:url(../../images/icon_operation_add.png) no-repeat;background-size:30px 30px;width:30px;height:30px;display:inline-block;margin-bottom:-9px;cursor:pointer}
		.closeIcon{position:absolute;right:15px;top:16px;background:url(../../images/close.png) no-repeat;background-size:12px 12px;width:12px;height:12px;display:inline-block;z-index:10;cursor:pointer}
		.triangle-down{position:absolute;margin-left:-23px;top:23px;width:0;height:0;border-left:3px solid transparent;border-right:3px solid transparent;border-top:6px solid #9BA3AF;cursor:pointer;display:inline-block}
		.layer{position:fixed;top:0;width:100%;height:100%;z-index:1;background:#000;opacity:.8;display:none}
		#controllPanel .main,#controllPanel header,#controllPanel section{position:relative;width:100%;}
		#controllPanel header>div{float:left}
		.condition .paramsfir{position:relative;line-height:14px;height: 38px;}
		label>input.datePick{width:100px}
		.defaultBtn,.serachBtn{height:30px;line-height:30px;border-radius:2px;text-align:center;cursor:pointer}
		.serachBtn{width:70px;margin-right:15px;background:#FDA413}
		.defaultBtn{width:140px;border:1px solid #939393}
		section{background:#232323;padding-left:25px;border-bottom-right-radius:2px;border-bottom-left-radius:2px}
		section .titleInfo{margin:10px;color:#9BA3AF}
		section .titleInfo>span{font-family:PingFangSC-Medium;font-size:16px;font-weight:bolder;color:#FFF}
		.main ul{margin-top:10px;background:#333}
		.main ul li{width:120px;height:40px;line-height:40px;font-size:16px;text-align:center}
		.main ul li a{color:#FFF}
		.bg_active{background:#FDA413}
		.font_color{display:inline-block}
		.font_color a{color:#FDA413}
		.chartControl{width:100%;overflow:hidden;margin-bottom:10px}
		.chartControl .echart1,.chartControl .echart2{float:left;width:49.5%;height:300px;border-radius:4px;background:#232323}
		.chartControl .echart1{margin-right:1%}
		#commonPersonsDialog{position:fixed;top:26%;left:40%;width:500px;height:auto;background:#232323;border-radius:4px;z-index:9;display:none}
		#commonPersonsDialog .personsPanel .commonPersonsTitle{height:40px;line-height:40px;background:#333;border-top-left-radius:4px;border-top-right-radius:4px;color:#FFF;padding-left:20px}
		#commonPersonsDialog .personsPanel .commonPersonsInfo{position:relative;width:100%;padding:20px 20px 5px 20px;border-bottom:1px solid #333}
		#commonPersonsDialog .personsPanel .commonPersonsInfo input,#commonPersonsDialog .personsPanel .commonPersonsInfo textarea{width:84%;background:#191919;border-radius:2px;border:0;padding-left:10px;color:#939393;font-size:14px}
		#commonPersonsDialog .personsPanel .commonPersonsInfo input{margin-left:0;float: none;height:30px;line-height:30px}
		#commonPersonsDialog .personsPanel .commonPersonsInfo p span{float:left}
		#commonPersonsDialog .personsPanel .button{float:right;padding:20px;display:inline-flex}
		#commonPersonsDialog .personsPanel .button .bg_active{width:112px;height:30px;line-height:30px;text-align:center;color:#FFF;border-radius:2px;margin-right:10px;cursor:pointer}
		.cancelBtn{width:70px;height:28px;line-height:28px;text-align:center;color:#FFF;border:1px solid #939393;border-radius:2px;cursor:pointer}
		.radio-inline{height:12px;width:12px;border-radius:6px;border:1px solid #999;margin-bottom:0;margin-right:6px}
		.radio_active{border:1px solid #FDA413}
		.radio-inline span{width:6px;height:6px;border-radius:3px;margin-left:2px;margin-top:2px}
		.radio-inline span.circle{background:#FDA413}
		.firstCondition{width:230px;overflow:hidden;line-height:30px;margin-right:10px;padding-left:10px;background:#191919;border:0;color:#DEDEDE}
		.loading{position:fixed;top:50%;left:50%;margin-left:120px;z-index:99}
		.add{display:inline-block;height:26px;color:#FDA413;font-size:12px;cursor:pointer}
		.add>span{font-size:20px}

		.select2-results ul::-webkit-scrollbar-track{-webkit-box-shadow:inset 0 0 6px rgba(0,0,0,.3);border-radius:5px}
		.select2-results ul::-webkit-scrollbar{width:10px}
		.select2-results ul::-webkit-scrollbar-thumb{border-radius:5px;-webkit-box-shadow:inset 0 0 6px rgba(0,0,0,.3);background-color:#4e4e4e}
		.select2-container--default .select2-selection--single,
		.select2-dropdown,
		.form-control, .btn-secondary:hover, .form-control:focus, .btn-secondary.active,
		.card-outline-danger, .card-outline-info, .card-outline-warning, .card-outline-success,
		.card-outline-primary, .popover-title, .select2-container--default .select2-selection--single,
		.select2-container--default .select2-selection--multiple, .bootstrap-tagsinput, .footable .pagination li a,
		.select2-container--default .select2-selection--single .select2-selection__rendered,
		.ms-container .ms-selectable li.ms-elem-selectable,
		.ms-container .ms-selection li.ms-elem-selection, .jsgrid-row > .jsgrid-cell, .page-item .page-link,
		.page-item.disabled .page-link, .minimal-faq .card .card-header,
		.mega-dropdown .nav-accordion .card-header{background: #191919;color: #DEDEDE;border: 0;}
		.select2-container--default .select2-results__option--highlighted[aria-selected]{background: #232323;color:#FDA413 }
		.select2-container--default .select2-results>.select2-results__options{max-height: 330px}
		.select2-container--default .select2-results__option[aria-selected=true]{background: transparent;}
		.select2-container--default .select2-results__group{color: #FDA413;}
		.select2-container--default .select2-results__group:nth-child(1){border-top: 1px solid #333333;}

		/****详情****/
		#profileDetail{display: none}
		.userIcon{display:inline-block;margin-bottom:-5px;width:22px;height:22px;background:url(../../images/user.png) no-repeat;background-size:22px 22px;cursor:pointer}
		.leftPanel{float:left;padding:10px 15px 10px 0;height:100%;min-height:542px;border-right:1px solid #333;color:#939393}
		.leftPanel .userInfo{position:relative;margin-right:15px;margin-bottom:15px}
		.leftPanel .userInfo span{height:30px;color:#dedede;font-size:1pc}
		.leftPanel .userInfo input{float:none;margin-top:15px;padding-left:10px;width:100%;height:30px;border:0;border-radius:2px;background:#191919;color:#dedede;font-size:14px;line-height:30px}
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
        /*公共弹窗*/
        .pubMask{position:absolute;top:0;left:0;z-index:8;display:none;width:100%;height:100%}
        .pubPopup{position:fixed;top:40%;left:0;z-index:999;display:none;width:100%;opacity:1}
        .pubPopup .popCon{margin:0 auto;padding:1em;width:200px;max-width:100%;border-radius:8px;background:#fff;color:#000;text-align:center;font-size:14px}

        .select2-container .select2-selection--multiple,.select2-container--default .select2-selection--multiple .select2-selection__rendered{margin-bottom: -5px;border-radius: inherit;}
		.select2-container--default .select2-container--focus .select2-selection--multiple{border:0;}
		.select2-container--default .select2-selection--multiple .select2-selection__rendered{overflow-y: auto;max-height: 90px;}
		.select2-container--default .select2-selection--multiple .select2-selection__rendered li{line-height: 24px; margin: 2px;}
		.select2-container--default .select2-selection--multiple .select2-selection__choice{margin-top: 0;background-color: transparent;}
		list-group-item,node-tree{line-height: 20px!important;}
		.dropshow{width: 230px;height: 30px;background:#191919;float: left;line-height: 28px;padding-left: 10px;}
		.dropshow>p{float: left;}
		.dropshow .arrow{width: 18px;height: 28px;float: right;background: url(../../images/down2.png) no-repeat;background-size:8px 10px;background-position: 5px 9px;}
		.dropshow .arrow_2{cursor:pointer;width: 8px;height: 28px;float: right;background: url(../../images/close.png) no-repeat;background-size:7px 7px;background-position: 0 10px;}
		.dropbox{background: #191919;position:absolute;width: 230px;height: 350px;overflow:auto;top:30px;z-index: 99;}
		.dropbox .normalshow>div>h3{color: #FDA413;font-size:14px;padding-left: 10px;margin-top: 0;margin-bottom:0;line-height:30px;}
		.nav>li>a{padding:0;}
		.nav>li>a:hover{background: rgba(0,0,0,0);}
		.usergroup>p{width:100%;border-bottom:1px solid #dedede;margin-bottom:0;}
		.usergroup>p>span{cursor:pointer;line-height: 30px;display:block;width:100%;color: #DEDEDE;font-family: PingFangSC-Regular;font-size: 14px;padding-left:20px;}
		/*.usergroup>p>span:hover{color: #FDA413;}*/
		.ztree{width: 250px;overflow-y:inherit;}
		.ztree li span{line-height: 30px;color: #fff;}
		.ztree *{font-size: 14px;}
		.ztree li a{height: 30px;}
		.ztree li span.button.switch{height: 18px;width: 18px;margin-top:6px;}
		.ztree li a.curSelectedNode{padding-top: 1px;background-color: rgba(0,0,0,0);color: #FDA413;height: 30px;border: 0px #FFB951 solid;opacity: 1;}
		.ztree li a.curSelectedNode span{line-height: 30px;color: #FDA413;}
		/*.ztree li a:hover span{color: #FDA413;}*/
		.dropsearch{width: 100%;background: #fff;color: #000;margin-left: 0;}
		.searchshow>h3{color: #FDA413;font-size:14px;padding-left: 10px;margin-top: 0;margin-bottom:0;line-height:30px;}
		.searchshow>p>span{cursor:pointer;line-height: 30px;display:block;width:100%;color: #DEDEDE;font-family: PingFangSC-Regular;font-size: 14px;padding-left:20px;}
		.secdrop{float:left;}
		.secdrop>select,input{min-height:28px;float:left;height: 28px;background: #191919;line-height: 28px;color:#fff;border: 0;margin-left:10px;min-width:100px;}
		.form-control{width: 20%;}
		.select2-container{margin-left:10px;}
		select.form-control:not([size]):not([multiple]){height: 28px;}
		/*.select2-container .select2-selection--multiple, .select2-container--default .select2-selection--multiple .select2-selection__rendered{margin-bottom: -15px;}*/
		.select2-container .select2-selection--multiple{min-height: 28px;line-height: 28px;}
        .phoneNumbr input{float: none;}
		.select2-container--default .select2-results>.select2-results__options{margin-top:26px;}
		.select2-container--default .select2-search--dropdown .select2-search__field{margin-left: 0}
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
            <div class="row" id="controllPanel">

				<header class="col-md-12" style="background: #232323; border-top-left-radius: 2px;border-top-right-radius: 2px;padding-top: 15px;">
					<div class="col-md-9 condition">
						<div class="paramsfir" id="param_0">
							<div class="dropshow"  id="dropshow_0" onclick="dropboxshow(this)">
								<p>总体用户</p>
								<span class="arrow"></span>
								<span class="arrow_2" style="display: none;"></span>
							</div>
							<div class="dropbox" id="dropbox_0" style="display: none">
								<input type="text" class="dropsearch">
								<div class="normalshow">
									<div>
										<h3>用户群</h3>
										<div class="usergroup"></div>
									</div>
									<div>
										<h3>标签</h3>
										<div id="treeDemo_0" class="ztree"></div>
									</div>
								</div>
								<div class="searchshow"></div>
							</div>
							<div class="secdrop" id="secdrop_0"></div>
							<i class="deleteIcon" onclick="javascript:cutCondition(this);" style="margin-left: 5px"></i>
						</div>

						<label class="add" onclick="addCondition()"><span>+</span>添加条件</label>
					</div>
					<div class="col-md-3" style="display: inline-flex;padding-top: 11px;">
						<div class="serachBtn" onclick="serachData()">搜索</div>
						<div class="defaultBtn" onclick="openDialog()">保存为常用人群</div>
					</div>
				</header>

                <section>
					<div class="titleInfo">
						共<span id="hitCount">0</span>人符合条件,
						<div class="font_color">
                            <a href="#" onclick="showDetail()">查看用户详情</a> &nbsp;|&nbsp;
                            <a href="#" onclick="downloadData()">导出</a>
                        </div>
					</div>
                </section>

                <div class="main">
	                <!--loading-->
	                <div class="loading">
		                <div class="card" style="background: transparent;"><span class="throbber-loader">Loading&#8230;</span></div>
	                </div>

	                <ul id="myTab" class="nav">
		                <li class="bg_active"><a href="#" data-toggle="tab">人群属性</a></li>
		                <li><a href="#" data-toggle="tab">商业属性</a></li>
		                <li><a href="#">习惯偏好</a></li>
		                <li><a href="#">活跃及出行</a></li>
		                <li><a href="#">物业属性</a></li>
	                </ul>

	                <div class="chartControl">
		                <div class="echart1" id="firstEchart"></div>
		                <div class="echart2" id="secondEchart"></div>
	                </div>
	                <div class="chartControl">
		                <div class="echart1" id="thirdEchart"></div>
		                <div class="echart2" id="fourthEchart"></div>
	                </div>
                </div>

            </div>

	        <div class="row" id="profileDetail">
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
					        <p class="but cancel" onclick="goBack()"><a href="#">返回</a></p>
				        </div>
			        </div>
		        </div>
	        </div>

	        <!-- footer -->
	        <c:import url="common/footer.jsp"></c:import>
	        <!-- End footer -->
         </div>
         <!-- End Page wrapper  -->
	</div>
</div>
<div id="commonPersonsDialog">
	<div class="personsPanel">
		<div class="closeIcon" onclick="closeDialog()"></div>
		<div class="commonPersonsTitle">保存常用人群</div>
		<div class="commonPersonsInfo">
			<p>
				<span>分群名称：</span><input type="text" id="groupName" placeholder="请输入">
			</p>
			<p>
				<span>分群描述：</span> <textarea name="description" id="groupDes" class="form-control" placeholder="请输入" rows="3"></textarea>
			</p>
			<p>
				<span>是否共享：</span>
				<label id="publicShare" class="radio-inline radio_active" data-value="0"><span class="circle"></span></label>公开共享
				<label id="privateShare" class="radio-inline" style="margin-left: 20px;" data-value="1"><span></span></label>个人私有
			</p>

		</div>
		<div class="button">
			<div class="bg_active" onclick="saveUserGroup()">保存该人群</div>
			<div class="cancelBtn" onclick="closeDialog()">取消</div>
		</div>
	</div>
</div>

<!--蒙层-->
<div class="layer"></div>

<!--公共弹窗 toast提示-->
<div class="pubMask"></div>
<div class="pubPopup">
    <div class="popCon"></div>
</div>

<!-- End Wrapper -->
<c:import url="common/importJs2.jsp"></c:import>

<script src="./assets/plugins/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="./js/zTreeJs/jquery.ztree.core.js"></script>
<script src="./assets/plugins/datepicker/js/bootstrap-datepicker.js"></script>
<script src="./assets/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript" src="./assets/plugins/moment-with-locales.min.js"></script>
<script type="text/javascript" src="./thirdlib/echarts/echarts.all.min.js"></script>
<script type="text/javascript" src="./thirdlib/Select2/select2.full.min.js"></script>
<%--<script type="text/javascript" src="./thirdlib/Select2/select2.min.js"></script>--%>
<script type="text/javascript" src="./js/option.js"></script>
<script type="text/javascript" src="./js/userProfileCommon.js"></script>

<script>
	var userGroupList = JSON.parse(sessionStorage.getItem("userGroupList"));
	var firstParam = JSON.parse(sessionStorage.getItem("firstParam"));
	var lpdObj = JSON.parse(sessionStorage.getItem("lpdObj"));
	var treeele = JSON.parse(sessionStorage.getItem("treeEle"));

	var cityArr=[],ageArr=[],entranceArr=[],
		activeRateArr=[],activeTypeArr=[],wypayWillArr=[],postTypeArr=[],wypayTypeArr=[],
		communityvalueArr=[],managementArr=[],consumptionArr=[],houseassetArr=[],
		xlsPreferenceArr=[],servicePreferenceArr=[],fieldPreferenceArr=[];

	var currentPage = 1; //当前页
	var pageSize = 10;   //每页记录
	//搜索的参数
	var fromPage = 1;

	var titleList = [
		'用户城市分布',
		'用户年龄段分布',
		'社区价值',
		'社区管理水平',
		'消费能力',
		'房屋资产',
		'新零售偏好',
		'服务偏好',
		'千丁live领域偏好',
		'app活跃频率',
		'app活跃类型',
		'出行规律',
		'物业费缴纳',
		'报事渠道',
		'物业缴费渠道',
	];

	$(document).ready(function () {
		$(".container-fluid").css("background","#191919");
        getLpd();
        serachData();
        getUserGroup(0);
        drophide();
	});
	//点击空白下拉框隐藏
	function drophide(){
	    document.onclick = function(e){
	        var legs = $('.dropbox').length;
	        for(var i=0; i<legs; i++){
                if($('#param_'+i+' .dropshow p').html() == ''){
                    $('#param_'+i+' .dropshow p').html('总体用户');
                }
                $('#param_'+i+' .dropbox').css('display', 'none');
			}
		}
	}
    //树状图渲染
    function treerender(index){
        var num = index;
        var setting = {
            view: {
                showLine: false,
                showIcon: false,
                selectedMulti: false,
                dblClickExpand: false,
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                beforeClick: beforeClick,
                beforeCollapse: beforemousedown,
                beforeExpand: beforemouseup,
            }
        };
        zTreeObj = $.fn.zTree.init($("#param_"+num+" #treeDemo_"+num), setting, treeele);
        function beforeClick(treeId, treeNode) {
            var e = window.event;
            e.stopPropagation();
            if(treeNode.level >= 2){
                $('#param_'+num+' .usergroup p span').css('color', '#fff');
                $('#param_'+num+' .dropbox').css('display', 'none');
                $('#param_'+num+' .dropshow p').html(treeNode.name);
                $('#param_'+num+' .dropshow p').attr('data-select-type', 'tag');
                $('#param_'+num+' .arrow_2').css('display', 'block');
                var list = firstParam, obj={};
                for(var i=0; i<list.length; i++){
                    if(list[i].name == treeNode.name){
                        obj = list[i];
                    }
                }
                treerender(num);
            	ruleSelectShow(obj, num);
			}
        }
        function beforemousedown(){
            var e = window.event;
            e.stopPropagation();
            return true;
		}
		function beforemouseup(){
            var e = window.event;
            e.stopPropagation();
		}
    }
	//获取缓存里的标签
	function getLpd() {
		var lpd = lpdObj;
		if(lpd) {
			cityArr = lpd["city"];
			ageArr = lpd["age"];
			communityvalueArr = lpd["communityvalue"];
			managementArr = lpd["management"];
			consumptionArr = lpd["consumption"];
			houseassetArr = lpd["houseasset"];
			entranceArr = lpd['entrance'];
			xlsPreferenceArr = lpd["xls_preference"];
			servicePreferenceArr = lpd["service_preference"];
			fieldPreferenceArr = lpd["field_preference"];
			activeRateArr = lpd['active_rate'];
			activeTypeArr = lpd['active_type'];
			wypayWillArr = lpd['wypay_will'];
			postTypeArr = lpd['posttype'];
			wypayTypeArr = lpd['wypay_type_preference'];
		}
	}
	//群组和标签列表
	function getUserGroup(index) {
		var res = userGroupList;
		var tag = firstParam;
		var num = index;
		//用户群组列表
		var groupStr="",tagStr="";
		if(res){
			var privateList = res.private;
			var publicList = res.public;
			/*
			 * data-group-id  字段id，标签列表里代表标签对应的英文，例如：sex
			 * data-select-type 选中类型，用以判断标签列表是否展示
			 * data-type 标签类型  1、2、3、4、5 对应 文本、json、列表、日期、数字
			 */
			if(privateList){
				for(var i=0;i<privateList.length;i++){
					groupStr += '<span value="'+privateList[i].id+'">'+privateList[i].name+'</span>';
				}
			}
			if(publicList){
				for(var j=0;j<publicList.length;j++){
					groupStr += '<span value="'+publicList[j].id+'">'+publicList[j].name+'</span>';
				}
			}
		}

		$("#param_"+num+" .usergroup").append("<p>"+groupStr+"</p>");
        usergroupclick(num);
        treerender(num);
        emptycontant(num);
        dropboxsearch(num);
	}
	//用户群的点击事件
	function usergroupclick(index){
        var num = index;
        $('#param_'+num+' .usergroup p span').eq(0).css('color', '#FDA413');
        $('#param_'+num+' .usergroup p span').click(function(e){
            e.stopPropagation();
            $('#param_'+num+' .usergroup p span').css('color', '#fff');
			this.style.color = '#FDA413';
            $('#param_'+num+' .dropshow p').html(this.innerHTML);
            if(this.innerHTML == 'p端用户' || this.innerHTML == 'c端用户'){
                $('#param_'+num+' .dropshow p').attr('data-select-type', 'group');
			}else{
                $('#param_'+num+' .dropshow p').attr('data-select-type', 'tag');
			}
            $('#param_'+num+' .dropbox').css('display', 'none');
            $('#param_'+num+' .arrow_2').css('display', 'block');
            $('#param_'+num+' .secdrop').empty();
            treerender(num);
//            getUserGroupById(conditionsdata2(this.innerHTML).id);
		})
	}

	//下拉框的显示
	function dropboxshow(the){
	    var e = window.event;
	    e.stopPropagation();
	    var num = $(the).attr('id').substr(-1, 1);
	    if($('#param_'+num+' .dropbox').css('display') == 'none'){
            $('#param_'+num+' .dropbox').css('display', 'block');
		}else{
            treerender(num);
            if($('#param_'+num+' .dropshow p').html() == ''){
                $('#param_'+num+' .dropshow p').html('总体用户');
            }
            $('#param_'+num+' .dropbox').css('display', 'none');
		}
	}
	//下拉框的搜索
	function dropboxsearch(index) {
	    var num = index;
        $('#param_'+num+' .dropsearch').on('keyup', function (e){
            var evt = (e) ? e : window.event
			e.stopPropagation();
            var list = firstParam;
            if(this.value.length != 0){
                var elestr = '';
                for(var i=0; i<list.length; i++){
                    if(list[i].status == 0){
                        if(list[i].name.indexOf(this.value) >= 0){
                            elestr += '<span value="'+list[i].id+'" data-select-type="tag">'+list[i].name+'</span>';
                        }
					}
                }
                $("#param_"+num+" .searchshow").empty();
                $("#param_"+num+" .searchshow").append("<h3>标签</h3><p>"+elestr+"</p>");
                searchonlick(num);
                $('#param_'+num+' .searchshow').css('display', 'block');
                $('#param_'+num+' .normalshow').css('display', 'none');
            }else{
                $("#param_"+num+" .searchshow").empty();
                $('#param_'+num+' .searchshow').css('display', 'none');
                $('#param_'+num+' .normalshow').css('display', 'block');
            }
        })
        $('#param_'+num+' .dropsearch').on('click', function(e){
            e.stopPropagation();
		})
    }
	//搜索项的选择
	function searchonlick(index){
	    var num = index;
	    $('#param_'+num+' .searchshow p span').click(function(e){
	        e.stopPropagation();
            $('#param_'+num+' .searchshow p span').css('color', '#fff');
            this.style.color = '#FDA413';
            $('#param_'+num+' .dropshow p').html(this.innerHTML);
            $('#param_'+num+' .arrow_2').css('display', 'block');
            $('#param_'+num+' .dropbox').css('display', 'none');
            $('#param_'+num+' .dropshow p').attr('data-select-type', 'tag');
            var list = firstParam, obj={};
            for(var i=0; i<list.length; i++){
                if(list[i].name == this.innerHTML){
                    obj = list[i];
                }
            }
            ruleSelectShow(obj, num);
		})
	}
	//清空显示框
	function emptycontant(index){
	    var num = index;
		$('#param_'+num+' .arrow_2').click(function(e){
		    e.stopPropagation();
            $('#param_'+num+' .dropshow p').html('');
            $('#param_'+num+' .dropbox').css('display', 'block');
            $('#param_'+num+' .dropbox input').val('');
            $('#param_'+num+' .searchshow').empty();
            $('#param_'+num+' .searchshow').css('display', 'none');
            $('#param_'+num+' .normalshow').css('display', 'block');
            $('#param_'+num+' .arrow_2').css('display', 'none');
            $('#param_'+num+' .secdrop').empty();
            treerender(num);
		})
	}
	//选择标签时规则及列表展示
	function ruleSelectShow(obj, index) {
		var num = index;
		var userselectType = obj.dataType;
		var userselectVal = obj.tag;
		var selectStr = "";
		selectStr += '<select id="ruleSelect_'+num+'" tagname="'+userselectVal+'" datetype="'+userselectType+'" onchange="javascript:changeRule(this,'+num+');">';
		if(Number(userselectType)==1) {
			typeOneSelect();
			selectStr +=typeOne;
		}
		if(Number(userselectType)==2){
			typeTwoSelect();
			selectStr += typeTwo;
		}
		if(Number(userselectType)==3){
			typeThreeSelect();
			selectStr +=typeThree;
		}
		if(Number(userselectType)==4){
			typeFourSelect();
			selectStr +=typefour;
		}
		if(Number(userselectType)==5){
			typeFiveSelect();
			selectStr +=typefive;
		}
		selectStr += '</select>';

		<!--1、文本-->
		if(Number(userselectType)==1) {
			selectStr += '<input type="text" id="thirdParam_'+num+'" placeholder="多个值用英文逗号隔开">';
		}
		<!--2、json-->
		if(Number(userselectType)==2){
            selectStr += '<select class="form-control" id="thirdParam_'+num+'" onfocus="javascript:jsonTypeSearch(this,\''+userselectVal+'\');"></select>';
//			selectStr += '<input type="text" id="thirdParam_'+num+'" onchange="javascript:jsonTypeSearch(this);">';
		}
		<!--3、列表-->
		if(Number(userselectType)==3){
			var list = obj.lpd;
			var opt="<option>请选择</option>";
			if(list && list.length > 0){
				for(var i=0;i<list.length;i++){
					if(list[i].example_id != -1 && list[i].example_id != 0 && list[i].example_id !=null){
						opt +="<option value="+list[i].example_id+">"+list[i].example_name+"</option>";
					}
				}
			}
			if(userselectVal=="city" || userselectVal=="community" || userselectVal=="property"){
				selectStr += '<select class="select2" id="thirdParam_'+num+'">'+opt+'</select>';
			}else{
				selectStr += '<select id="thirdParam_'+num+'">'+opt+'</select>';
			}
		}
		<!--4、日期-->
		if(Number(userselectType)==4){
			selectStr += '<input type="text" name="start_date" class="datePick" id="startDate_'+num+'" onfocus="selectTimes('+num+')"/>';
			$('#startDate_'+index).val(moment().subtract(6, 'days').format('YYYY-MM-DD'));
		}
		<!--5、数值-->
		if(Number(userselectType)==5){
			selectStr += '<input type="number" id="thirdParam_'+num+'" class="phoneNumbr" style="width:130px" placeholder="请输入数字">';
		}
        $('#param_'+num+' .secdrop').empty();
		$('#param_'+num+' .secdrop').append(selectStr);
		if(userselectVal=="city" || userselectVal=="community" || userselectVal=="property"){
			$("#thirdParam_"+num).select2({
				placeholder: '',
				width:'235px',
				allowClear: true,
				multiple:false
			});
		}
	}

	function changeRule(the,index){
		var dataType = $(the).attr('datetype')
        var userselectVal = $(the).attr('tagname');
		var rule = $("#ruleSelect_"+index+" option:selected").val();
		if(dataType=="4"){
			if(rule=="bt"){
				$("#param_"+ index +" >.secdrop").append('&nbsp;&nbsp;至&nbsp;&nbsp;<input type="text" name="end_date" class="datePick" id="endDate_'+index+'" onfocus="selectTimes('+index+')"/>');
				$('#endDate_'+index).val(moment().subtract(0, 'days').format('YYYY-MM-DD'));
			}else{
				$("#param_"+ index +" >.secdrop .datePick").remove();
				$("#param_"+ index +" >.secdrop").append('<input type="text" name="start_date" class="datePick" id="startDate_'+index+'" onfocus="selectTimes('+index+')"/>');
				$('#startDate_'+index).val(moment().subtract(6, 'days').format('YYYY-MM-DD'));
			}
		}else if(dataType=="5"){
            if(rule=="bt"){
                $("#param_"+ index +" >.secdrop").append('<span class="phoneNumbr">&nbsp;&nbsp;至&nbsp;&nbsp;<input type="number" style="width:130px" id="endNumber_'+index+'" placeholder="请输入数字"/></span>');
            }else{
                $("#param_"+ index +" >.secdrop .phoneNumbr").remove();
                $("#param_"+ index +" >.secdrop").append('<input type="number" class="phoneNumbr" style="width:130px" id="thirdParam_'+index+'" placeholder="请输入数字"/>');
            }
        }else if(dataType=="3"){
            //包含、不包含 可以多选
			$(the).siblings().remove();

            var ids = $(the).parent().parent().attr("id");
            var num = ids.substr(6);
            var list = lpdObj[userselectVal];
            var opt="";
            if(list && list.length > 0){
                for(var i=0;i<list.length;i++){
                    if(list[i].example_id != -1 && list[i].example_id != 0 && list[i].example_id !=null){
                        opt +="<option value="+list[i].example_id+">"+list[i].example_name+"</option>";
                    }
                }
            }
            $(the).after('<select class="select2" id="thirdParam_'+num+'">'+opt+'</select>');
            if(rule=="in" || rule=="ex"){
				$("#thirdParam_"+num).select2({
					placeholder: '请选择多个',
					width:'235px',
					multiple:true
				});
            }else if(list.length > 10){
                $("#thirdParam_"+num).select2({
                    placeholder: '请选择',
                    width:'235px',
                    multiple:false
                });
			}
		}
	}
	//提取条件
	function conditionsFun() {
		var div = $(".paramsfir");
		var indexList=[], conditions = [];
		//取到列表标签的id 的数值
		if(div.length > 0) {
			for (var i = 0; i < div.length; i++) {
				var id = div[i].getAttribute("id");
				var ind = Number(id.substr(-1, 1));
				indexList.push(ind);
			}
			if(indexList.length > 0){
				for(var j=0;j<indexList.length;j++){
					var i = indexList[j];
					var selectType = $('#param_'+i+' .dropshow p').attr("data-select-type");
					if(selectType=="group"){
						$.ajax({
							url: "./managePortraitUserGroup",
							type: "post",
							async:false,
							data: {
								id: $("#firstCondition_"+i+" option:selected").val()
							},
							success: function (res) {
								if (res && res.userGroup) {
									var result = JSON.parse(res.userGroup.conditions);
									if(result){
										for(var m=0;m<result.conditions.length;m++){
											conditions.push(result.conditions[m]);
										}
									}
								}
							}
						});
					}else if(selectType=="tag"){
                        var tagName = $('#param_'+i+' .dropshow p').text();
                        var obj = conditionsdata(tagName);
                        if(obj){
                            var tag = obj.tag;
                            var dataType = obj.dataType;
                            var rule = $("#ruleSelect_"+i+" option:selected").val();

                            var value1="",value2="",tagObj={};

                            if(dataType=="1"){
                                value1 = $("#thirdParam_"+i).val();
                            }else if(dataType=="2"){
                                //json 的包含 wildcard 和   exWildcard 不包含
                                if(rule=="wildcard" || rule=="exWildcard"){
                                    //获取多选下来框选中的值
                                    var multipleVal = $("#thirdParam_"+i).select2("val");
                                    var mValArray=[];
                                    if(multipleVal && multipleVal.length>0){
                                        for(var i=0;i<multipleVal.length;i++){
                                            var text='"'+multipleVal[i]+'"';
                                            mValArray.push(text);
                                        }
                                        value1 =mValArray.join(",");
                                    }
                                }
                            }else if(dataType=="3"){
                                if(rule=="in" || rule=="ex"){
                                    //获取多选下来框选中的值
                                    var multipleVal = $("#thirdParam_"+i).select2("val");
                                    var mValArray = multipleVal;//多选value1 多个参数用逗号分隔以字符串的形式传给后台
                                    if(mValArray && mValArray.length>0){
                                        value1 =mValArray.join(",");
                                    }
                                }else{
                                    value1 = $("#thirdParam_"+i+" option:selected").val();
                                }
                            }else if(dataType=="4"){
                                value1 = $('#startDate_'+i).val();
                                value2 = $('#endDate_'+i).val();
                            }else if(dataType=="5"){
                                value1 = $('#thirdParam_'+i).val();
                                value2 = $('#endNumber_'+i).val();
                            }
                            tagObj.tag = tag;
                            tagObj.tagName = tagName;
                            tagObj.dataType = dataType;
                            tagObj.rule = rule;
                            tagObj.value1 = value1;
                            if(value2){
                                tagObj.value2 = value2;
                            }
                            conditions.push(tagObj);
						}else{
                            var obj = conditionsdata2(tagName);
                            var conarr = conditions.concat(JSON.parse(obj.conditions).conditions);
                            conditions = conarr;
						}
					}
				}
			}
		}
		return conditions;
	}
	//提取tag数据
	function conditionsdata(name){
        var list = firstParam;
        for(var i=0; i<list.length; i++){
            if(list[i].name == name){
                return list[i];
			}
		}
	}
    //提取用户群数据
    function conditionsdata2(name){
        var list1 = userGroupList.public,
			list2 = userGroupList.private,
			list = list1.concat(list2);
        for(var i=0; i<list.length; i++){
            if(list[i].name == name){
                return list[i];
            }
        }
    }
	//偏好查询
	function queryPreference() {
//		var conditions = [{"tag":"age","tagName":"年龄","dataType":1,"rule":"eq","value1":"age_01"},{"tag":"xls_preference","tagName":"偏好","dataType":1,"rule":"ex","value1":"-1"}];
		var conditions =conditionsFun();

		var serviceData = JSON.parse(sessionStorage.getItem("serviceData"));
		var xlsData = JSON.parse(sessionStorage.getItem("xlsData"));
		if((!conditions || conditions.length ==0) && (serviceData || xlsData)){
			$(".loading,.layer").hide();
			if(serviceData){
				chartServiceFun(serviceData,"storage");
			}
			if(xlsData){
				chartXLSEchartFun(xlsData,"storage");
			}
//			chartQdingLiveFun(null);
		}else{
			queryData("habit");
		}
	}
	//搜索
	function serachData() {
		$("#myTab > li").removeClass("bg_active").first().addClass("bg_active");
		queryData('crowd');
	}
    //编辑群信息
    function getUserGroupById(uid) {
        var userGroupParam = {
            id:uid
        }
        $.ajax({
            url: "./managePortraitUserGroup",
            type: "post",
            data: userGroupParam,
            success: function (res) {
//				console.log(res);
                if (res && res.userGroup) {
                    var lp = firstParam;

                    $(".condPanel > div").remove();
                    $("#groupId").val(res.userGroup.id);
                    $("#name").val(res.userGroup.name);
                    $("#description").val(res.userGroup.description);

                    if(res.userGroup.isShare==0){
                        $("#publicShare").addClass("radio_active");
                        $("#publicShare>span").addClass("circle");
                        $("#privateShare").removeClass("radio_active");
                        $("#privateShare>span").removeClass("circle");
                    }else{
                        $("#privateShare").addClass("radio_active");
                        $("#privateShare>span").addClass("circle");
                        $("#publicShare").removeClass("radio_active");
                        $("#publicShare>span").removeClass("circle");
                    }
                    if(res.userGroup.conditions){
                        //所有的条件 json 字符串 转 对象
                        var cond =JSON.parse(res.userGroup.conditions);

                        //条件存放列表
                        var condArr=[];
                        condArr.push(cond.conditions);

                        if(condArr && condArr[0].length > 0){
                            var str = "",arr = condArr[0];
                            for(var i=0;i < arr.length;i++){
                                var num=i;
                                if(arr[i]){
                                    str += '<div class="paramsfir" id="param_'+num+'">';
                                    str += '<div class="dropshow"  id="dropshow_'+num+'" onclick="dropboxshow(this)">';
                                    str += '<p data-select-type="tag">'+arr[i].tagName+'</p>';
                                    str += '<span class="arrow"></span>';
                                    str += '<span class="arrow_2" style="display: none;"></span>';
                                    str += '</div>';
                                    str += '<div class="dropbox" id="dropbox_'+num+'" style="display: none">';
                                    str += '<input type="text" class="dropsearch">';
                                    str += '<div class="normalshow">';
                                    str += '<div>';
                                    str += '<h3>标签</h3>';
                                    str += '<div id="treeDemo_'+num+'" class="ztree"></div>';
                                    str += '</div>';
                                    str += '</div>';
                                    str += '<div class="searchshow"></div>';
                                    str += '</div>';
                                    str += '<div class="secdrop" id="secdrop_'+num+'"></div>';
                                    str += '<i class="deleteIcon" onclick="javascript:cutCondition(this);" style="margin-left: 5px"></i>';
                                    str += '</div>';
                                }
                            }
                        }
                    }else{
                        $(".addIcon").css("margin-left","10px");
                    }

                    $(".condPanel").append(str);
                    if(condArr && condArr[0].length > 0){
                        for(var i=0;i < arr.length;i++){
                            treerender(i);
                            var num = i;
                            var index = i;
                            var userselectType = arr[i].dataType;
                            var userselectVal = arr[i].tag;
                            var defval = arr[i].rule;
                            var rule = arr[i].rule;
                            var selectStr = "";
                            var obj = conditionsdata(arr[i].tagName);
                            selectStr += '<select id="ruleSelect_'+num+'" tagname="'+userselectVal+'" datetype="'+userselectType+'" onchange="javascript:changeRule(this,'+num+');">';
                            if(Number(userselectType)==1) {
                                typeOneSelect(defval);
                                selectStr +=typeOne;
                            }
                            if(Number(userselectType)==2){
                                typeTwoSelect(defval);
                                selectStr += typeTwo;
                            }
                            if(Number(userselectType)==3){
                                typeThreeSelect(defval);
                                selectStr +=typeThree;
                            }
                            if(Number(userselectType)==4){
                                typeFourSelect(defval);
                                selectStr +=typefour;
                            }
                            if(Number(userselectType)==5){
                                typeFiveSelect(defval);
                                selectStr +=typefive;
                            }
                            selectStr += '</select>';

                            <!--1、文本-->
                            if(Number(userselectType)==1) {
                                selectStr += '<input type="text" id="thirdParam_'+num+'" value="'+arr[i].value1+'">';
                            }
                            <!--2、json-->
                            if(Number(userselectType)==2){
                                selectStr += '<select class="form-control" id="thirdParam_'+num+'" onfocus="javascript:jsonTypeSearch(this,\''+userselectVal+'\');"></select>';
//								selectStr += '<input type="text" id="thirdParam_'+num+'" onchange="javascript:jsonTypeSearch(this);">';
                            }
                            <!--3、列表-->
                            if(Number(userselectType)==3){
                                var list = obj.lpd;
                                var opt="<option>请选择</option>";
                                if(list && list.length > 0){
                                    for(var j=0;j<list.length;j++){
                                        if(list[j].example_id != -1 && list[j].example_id != 0 && list[j].example_id !=null){
                                            var val = (list[j].example_id == arr[i].value1)?'selected':'';
                                            opt +="<option value="+list[j].example_id+" "+val+">"+list[j].example_name+"</option>";
                                        }
                                    }
                                }
                                if(userselectVal=="city" || userselectVal=="community" || userselectVal=="property"){
                                    selectStr += '<select class="select2" id="thirdParam_'+num+'">'+opt+'</select>';
                                }else{
                                    selectStr += '<select id="thirdParam_'+num+'">'+opt+'</select>';
                                }
                            }
                            <!--4、日期-->
                            if(Number(userselectType)==4){
                                if(rule=="bt"){
                                    selectStr += '<span>&nbsp;&nbsp;至&nbsp;&nbsp;</span><input type="text" name="end_date" class="datePick" id="endDate_'+index+'" onfocus="selectTimes('+index+')"/>';
                                }else{
                                    selectStr += '<input type="text" name="start_date" class="datePick" id="startDate_'+index+'" onfocus="selectTimes('+index+')"/>';
                                }
                                $('#startDate_'+index).val(moment().subtract(6, 'days').format('YYYY-MM-DD'));
                            }
                            <!--5、数值-->
                            if(Number(userselectType)==5){
                                if(rule=="bt"){
                                    selectStr += '<input type="number" class="phoneNumbr" style="width:80px" id="thirdParam_'+index+'" value="'+arr[i].value1+'"/>';
                                    selectStr += '<span class="phoneNumbr" style="min-width:84px;width:135px;text-align:left;"><span style="width:20px;line-height:30px;"><span>&nbsp;&nbsp;至&nbsp;&nbsp;</span></span><span ><input type="number" style="width:80px" class="phoneNumbr2" id="endNumber_'+index+'" value="'+arr[i].value2+'"/></span></span>';
                                }else{
                                    selectStr += '<input type="number" class="phoneNumbr" style="width:80px" id="thirdParam_'+index+'" value="'+arr[i].value1+'"/>';
                                }
                            }
                            $('#param_'+num+' .secdrop').empty();
                            $('#param_'+num+' .secdrop').append(selectStr);
                            if(userselectVal=="city" || userselectVal=="community" || userselectVal=="property"){
                                $("#thirdParam_"+num).select2({
                                    placeholder: '',
                                    width:'235px',
                                    allowClear: true,
                                    multiple:false
                                });
                            }
                        }
                    }

                    $("#openSave").attr("onclick","openDialog('save','"+res.userGroup.id+"')");
                    $("#openDelete").attr("onclick","openDialog('delete','"+res.userGroup.id+"')");
                }
            }
        });
    }
	//crowd 人群属性
	//business 商业属性
	//habit 习惯偏好
	//activeAndTravel 活跃及出行
	//travel 物业属性
	function queryData(type) {
		var obj={
			"typeVal":type
		}
		var conditions =conditionsFun();
		if(conditions && conditions.length > 0){
			obj.conditions = conditions;
		}
		$.ajax({
			url: "./queryPortraitAnalysis",
			type: "post",
			data:{
				"query":JSON.stringify(obj),
			},
			success: function (res) {
				if(res){
//					console.log(res);
					//符合条件人数
                    if(res.hitCount){
                        $("#hitCount").html("").text(res.hitCount.toLocaleString());
                    }else {
                        $("#hitCount").html("").text("0");
                    }
					switch (type){
						case "crowd":
							chartCityFun(res.userGroupCity);
							chartAgeFun(res.userGroupAge);
							chartCommunityvalueFun(res.userGroupCommunityvalue);
							chartCommunityManagementFun(res.userGroupManagement);
							break;
						case "business":
							chartConsumptionFun(res.userGroupConsumption);
							chartHouseassetFun(res.userGroupHouseasset);
							break;
						case "habit":
							$(".loading,.layer").hide();
							if(res.userGroupPreferences){
								var xls = res.userGroupPreferences.retXls;
								var service = res.userGroupPreferences.retService;
								chartXLSEchartFun(xls);
								chartServiceFun(service);
//								chartQdingLiveFun(null);
							}

							break;
						case "activeAndTravel":
							chartActiveRateFun(res.userGroupactiveRate);
							chartActiveTypeFun(res.userGroupActiveType);
							chartEntranceFun(res.userGroupEntrance);
							break;
						case "travel":
							chartWyPayFun(res.userGroupWyPayWill);
							chartPostTypeFun(res.userGroupPostType);
							chartWypayTypeFun(res.userGroupWypayTypePreference);
							break;
						default:
							chartCityFun(res.userGroupCity);
							chartAgeFun(res.userGroupAge);
							chartCommunityvalueFun(res.userGroupCommunityvalue);
							chartCommunityManagementFun(res.userGroupManagement);
							break;
					}
				}
			}
		});
	}

	//保存为常用人群
	function saveUserGroup() {
		var name = $("#groupName").val();
		if(!name){
			alert("请输入分群名称");
			closeDialog();
			return;
		}
		var des = $("#groupDes").val();
		var isShare = $(".radio_active").attr("data-value");

		var params ={
			"name":name,
			"description":des,
			"isShare":isShare,
		};
		var conditions = conditionsFun();
		if(conditions && conditions.length > 0){
			var con ={
				"conditions":conditions
			}
			params.conditions = JSON.stringify(con);
		}
//		console.log(params);
		$.ajax({
			url: "./addOrUpdatePortraitUserGroup",
			type: "POST",
			data:params,
			success: function (res) {
				if(res){
					closeDialog();
					$("#groupName,#groupDes").val("");
					$("#privateShare").removeClass("radio_active");
					$("#privateShare span").removeClass("circle");
					$("#publicShare").addClass("radio_active");
					$("#publicShare span").addClass("circle");
				}
			}
		});
	}

	//查看用户详情
	function showDetail() {
		$("#controllPanel").hide();
		$("#profileDetail").show();
		$("#profileDetail>div").css("min-height","542px");
		var conditions = conditionsFun();
		sessionStorage.removeItem("conditionList");
		sessionStorage.setItem("conditionList", JSON.stringify(conditions));
//		window.location.href="./userProfileDetails";
		$("#phoneList,#detailList").html("");
		$("#searchBox").val("");
		$(".container-fluid").removeAttr("style");
		currentPage=1;
		fromPage = 1;
		profileDetailList();
	}

	function selectTimes(index) {
		var rule = $("#ruleSelect_"+index+" option:selected").val();
		if(rule=="bt"){
			//开始时间
			$('#startDate_'+index).datepicker({
				language: "zh-CN",
				autoclose: true,
				format: 'yyyy-mm-dd',
				todayHighlight: true,
				endDate: '0d'
			}).on('changeDate', function (e) {
				var date1 = new Date(Date.parse($('#startDate_'+index).val()));
				var date2 = new Date(Date.parse($('#endDate_'+index).val()));
				if (date1 > date2) {
					$('#endDate_'+index).val($('#startDate_'+index).val());
					$('#endDate'+index).datepicker('setStartDate', e.date);
				} else {
					$('#endDate'+index).datepicker('setStartDate', e.date);
				}
			});
			//结束时间
			$('#endDate_'+index).datepicker({
				language: "zh-CN",
				autoclose: true,
				format: 'yyyy-mm-dd',
				todayHighlight: true,
				endDate: '0d'
			}).on('changeDate', function (e) {
				var date1 = new Date(Date.parse($('#startDate_'+index).val()));
				var date2 = new Date(Date.parse($('#endDate_'+index).val()));
				if (date2 < date1) {
					$('#startDate_'+index).val($('#endDate_'+index).val());
					$('#startDate_'+index).datepicker('setEndDate', e.date);
				} else {
					$('#startDate_'+index).datepicker('setEndDate', e.date);
				}
			});
		}else{
			//开始时间
			$('#startDate_'+index).datepicker({
				language: "zh-CN",
				autoclose: true,
				format: 'yyyy-mm-dd',
				todayHighlight: true,
				endDate: '0d'
			}).on('changeDate', function (e) {});
		}
	}

	/**********前端交互*********/
	//添加条件
	function addCondition(){
		var num = 0;
		var id = $(".paramsfir:last").attr("id");

		if(id){
			num = Number(id.substr(-1, 1))+1;
		}else{
			num +=1;
		}
		var emptyCon ="";
		emptyCon += '<div class="paramsfir" id="param_'+num+'">';
		emptyCon += '<div class="dropshow"  id="dropshow_'+num+'" onclick="dropboxshow(this)">';
		emptyCon += '<p>总体用户</p>';
		emptyCon += '<span class="arrow"></span>';
        emptyCon += '<span class="arrow_2" style="display: none;"></span>';
        emptyCon += '</div>';
        emptyCon += '<div class="dropbox" id="dropbox_'+num+'" style="display: none">';
        emptyCon += '<input type="text" class="dropsearch">';
        emptyCon += '<div class="normalshow">';
        emptyCon += '<div>';
        emptyCon += '<h3>用户群</h3>';
		emptyCon += '<div class="usergroup"></div>';
        emptyCon += '</div>';
        emptyCon += '<div>';
        emptyCon += '<h3>标签</h3>';
        emptyCon += '<div id="treeDemo_'+num+'" class="ztree"></div>';
        emptyCon += '</div>';
        emptyCon += '</div>';
        emptyCon += '<div class="searchshow"></div>';
        emptyCon += '</div>';
        emptyCon += '<div class="secdrop" id="secdrop_'+num+'"></div>';
        emptyCon += '<i class="deleteIcon" onclick="javascript:cutCondition(this);" style="margin-left: 5px"></i>';
        emptyCon += '</div>';
		$(".add").before(emptyCon);
		$(".add").removeAttr("style");
		getUserGroup(num);
	}

	//条件减少
	function cutCondition(the) {
		if($(".paramsfir").length>1){
			$(the).parent().remove();
		}
	}

	//选项卡切换
	$("#myTab > li").click(function () {
		$(this).addClass("bg_active").siblings("li").removeClass("bg_active");
		var i = $(this).index();
		$("#firstEchart,#secondEchart,#thirdEchart,#fourthEchart").show();
		if(i==0){//人群属性
			queryData("crowd");
		}else if(i==1){//商业属性
			$("#thirdEchart,#fourthEchart").hide();
			queryData("business");
		}else if(i==2){//习惯偏好
			$("#thirdEchart,#fourthEchart").hide();
			$(".loading").css("display", "block");
			$(".layer").show();
			queryPreference();
		}else if(i==3){//活跃及出行
			$("#fourthEchart").hide();
			queryData("activeAndTravel");
		}else if(i==4){//物业属性
			$("#fourthEchart").hide();
			queryData("travel");
		}
	});

	//打开弹窗
	function openDialog() {
		$("#commonPersonsDialog,.layer").show();
	}
	//关闭弹窗
	function closeDialog() {
		$("#commonPersonsDialog,.layer").hide();
	}
	//切换单选按钮
	$(".radio-inline").click(function () {
		$(this).addClass("radio_active").children("span").addClass("circle");
		$(this).siblings("label").removeClass("radio_active").children("span").removeClass("circle");
	});

	//********************echarts图标展示********************
	//用户城市分布
	function chartCityFun(obj) {
		var myChart = echarts.init(document.getElementById('firstEchart'));
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
			opt.legend[0].data=[legendData[0],legendData[1],legendData[2],legendData[3]];
			opt.legend[1].data=[legendData[4],legendData[5],legendData[6],legendData[7]];
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"城市"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'用户年龄段分布'
	function chartAgeFun(obj) {
		var myChart = echarts.init(document.getElementById('secondEchart'));
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
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"年龄"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//社区价值
	function chartCommunityvalueFun(obj) {
		var myChart = echarts.init(document.getElementById('thirdEchart'));
		var opt = pie_option;
		opt.title.text=titleList[2];
		opt.series[0].name=titleList[2];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],legendData=[];
		if(obj) {
			getData(obj, communityvalueArr, legendData, seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"社区价值"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//社区管理水平
	function chartCommunityManagementFun(obj) {
		var myChart = echarts.init(document.getElementById('fourthEchart'));
		var opt = pie_option;
		opt.title.text=titleList[3];
		opt.series[0].name=titleList[3];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],legendData=[];
		if(obj) {
			getData(obj, managementArr, legendData, seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"社区管理水平"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}

	//消费能力
	function chartConsumptionFun(obj) {
		var myChart = echarts.init(document.getElementById('firstEchart'));
		var opt = bar_option;
		opt.title.text=titleList[4];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],xAxisData=[],legendData=[], seriesData1=[];
		if(obj) {
			getData(obj, consumptionArr, legendData, seriesData);
		}
//		console.log(seriesData);

		if(seriesData && seriesData.length>0){
			for(var i=0;i<seriesData.length;i++){
				xAxisData.push(seriesData[i].name);
				seriesData1.push(seriesData[i].value);
			}
			opt.xAxis.axisLabel.rotate='0';
			opt.xAxis.data=xAxisData;
			opt.series[0].name=titleList[4];
			opt.series[0].data = seriesData1;
			opt.tooltip.formatter = function (params, ticket, callback) {
				var content = '';
				var j = params[0].dataIndex;
				if(params[0]){
					for (var i = 0; i < params.length; i++) {
						content += '<div style="color:' + params[i].color + '">' + params[i].marker + xAxisData[j] +':'+ params[i].value + '</div>'
					}
				}
				return  '<div>' + content + '</div>';
			};
		}
		var maxVal = getMaxValue(seriesData1);
		if(maxVal > 1000){
			opt.yAxis[0].axisLabel.formatter = function(value, index) {
				return value / 1000 + 'k';
			}
		}else{
			opt.yAxis[0].axisLabel.formatter = function(value, index) {
				return value;
			}
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//房屋资产
	function chartHouseassetFun(obj) {
		var myChart = echarts.init(document.getElementById('secondEchart'));
		var opt = bar_option;
		opt.title.text=titleList[5];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],xAxisData=[],legendData=[], seriesData1=[];
		if(obj) {
			getData(obj, houseassetArr, legendData, seriesData);
		}
//		console.log(seriesData);
		if(seriesData && seriesData.length>0){
			for(var i=0;i<seriesData.length;i++){
				xAxisData.push(seriesData[i].name);
				seriesData1.push(seriesData[i].value);
			}
			opt.xAxis.axisLabel.rotate='0';
			opt.xAxis.data=xAxisData;
			opt.series[0].name=titleList[5];
			opt.series[0].data = seriesData1;
			opt.tooltip.formatter = function (params, ticket, callback) {
				var content = '';
				var j = params[0].dataIndex;
				if(params[0]){
					for (var i = 0; i < params.length; i++) {
						content += '<div style="color:' + params[i].color + '">' + params[i].marker + xAxisData[j] +':'+ params[i].value + '</div>'
					}
				}
				return  '<div>' + content + '</div>';
			};
		}
		var maxVal = getMaxValue(seriesData1);
		if(maxVal > 1000){
			opt.yAxis[0].axisLabel.formatter = function(value, index) {
				return value / 1000 + 'k';
			}
		}else{
			opt.yAxis[0].axisLabel.formatter = function(value, index) {
				return value;
			}
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}

	//'新零售偏好'
	function chartXLSEchartFun(obj,str) {
		var myChart = echarts.init(document.getElementById('firstEchart'));
		var opt = bar_option;
		opt.title.text=titleList[6];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var xAxisData=[],seriesData=[],nameList=[];
		if(obj && obj.length > 0){
			//取缓存中的值
			if(str && str=="storage"){
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
			}else{
				for(var i=0;i<obj.length;i++){
					if(i<10){
						var str = obj[i].indexOf("|");
						if(str != -1){
							var personNum = obj[i].substr(0,str);
							var perference = obj[i].substr(str+1);
							var name = perference.substr(0,6);

							xAxisData.push(name);
							nameList.push(perference);
							seriesData.push(personNum);
						}
					}
				}
			}
			opt.xAxis.data=xAxisData;
			opt.xAxis.axisLabel.rotate='30';
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
	function chartServiceFun(obj,str) {
		var myChart = echarts.init(document.getElementById('secondEchart'));
		var opt = bar_option;
		opt.title.text=titleList[7];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[], xAxisData=[],nameList=[];
		if(obj && obj.length > 0){
			//取缓存中的值
			if(str && str=="storage"){
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
			}else{
				for(var i=0;i<obj.length;i++){
					if(i<10){
						var str = obj[i].indexOf("|");
						if(str != -1){
							var personNum = obj[i].substr(0,str);
							var perference = obj[i].substr(str+1);
							var name = perference.substr(0,6);

							xAxisData.push(name);
							nameList.push(perference);
							seriesData.push(personNum);
						}
					}
				}
			}
			opt.xAxis.data=xAxisData;
			opt.xAxis.axisLabel.rotate='30';
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
	//'千丁live领域偏好'
	function chartQdingLiveFun(obj) {
		var myChart = echarts.init(document.getElementById('thirdEchart'));
		var opt = bar_option;
		opt.title.text=titleList[8];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		opt.xAxis.data=[];
		opt.series[0].data = [0];
		myChart.hideLoading();
		myChart.setOption(opt);
	}

	//'app活跃频率'
	function chartActiveRateFun(obj) {
		var myChart = echarts.init(document.getElementById('firstEchart'));
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
		if(obj) {
			getData(obj, activeRateArr, legendData, seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"活跃频率"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'app活跃类型'
	function chartActiveTypeFun(obj) {
		var myChart = echarts.init(document.getElementById('secondEchart'));
		var opt = pie_option;
		opt.title.text=titleList[10];
		opt.series[0].name=titleList[10];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],legendData=[];
		if(obj) {
			getData(obj, activeTypeArr, legendData, seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"活跃类型"}];
		}

		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'出行规律'
	function chartEntranceFun(obj) {
		var myChart = echarts.init(document.getElementById('thirdEchart'));
		var opt = stack_bar_option;
		opt.title.text=titleList[11];

		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var xAxisData=[],citys=[], seriesData1=[], seriesData2=[], seriesData3=[], seriesData4=[], seriesData5=[];
		if(obj) {
			if (cityArr && cityArr.length > 0) {
				for (var i = 0; i < cityArr.length; i++) {
					var id = cityArr[i].example_id;
					var name = cityArr[i].example_name;
					if (id != -1) {
						//柱状图需要动态更新横坐标
						if (name == "北京市" || name == "重庆市" || name == "成都市" || name == "上海市" || name == "西安市" || name == "杭州市" || name == "广州市") {
							xAxisData.push(name);
							citys.push({cityId: id, cityName: name});
						}
					}
				}
			}

			for (var i = 0; i < citys.length; i++) {
				var id = citys[i].cityId;
				if (obj[id] && obj[id]["entrance_01"]) {
					seriesData1.push(obj[id]["entrance_01"]);
				} else {
					seriesData1.push(0);
				}

				if (obj[id] && obj[id]["entrance_02"]) {
					seriesData2.push(obj[id]["entrance_02"]);
				} else {
					seriesData2.push(0);
				}

				if (obj[id] && obj[id]["entrance_03"]) {
					seriesData3.push(obj[id]["entrance_03"]);
				} else {
					seriesData3.push(0);
				}

				if (obj[id] && obj[id]["entrance_04"]) {
					seriesData4.push(obj[id]["entrance_04"]);
				} else {
					seriesData4.push(0);
				}

				if (obj[id] && obj[id]["entrance_05"]) {
					seriesData5.push(obj[id]["entrance_05"]);
				} else {
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

	//'物业费缴纳'
	function chartWyPayFun(obj) {
		var myChart = echarts.init(document.getElementById('firstEchart'));
		var opt = pie_option;
		opt.title.text=titleList[12];
		opt.series[0].name=titleList[12];
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
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"物业费缴费"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'报事渠道'
	function chartPostTypeFun(obj) {
		var myChart = echarts.init(document.getElementById('secondEchart'));
		var opt = pie_option;
		opt.title.text=titleList[13];
		opt.series[0].name=titleList[13];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],legendData=[];
		if(obj) {
			getData(obj, postTypeArr, legendData, seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"报事渠道"}];
		}

		myChart.hideLoading();
		myChart.setOption(opt);
	}
	//'物业缴费渠道'
	function chartWypayTypeFun(obj) {
		var myChart = echarts.init(document.getElementById('thirdEchart'));
		var opt = pie_option;
		opt.title.text=titleList[14];
		opt.series[0].name=titleList[14];
		myChart.showLoading({
			text: ' ',
			effect: 'whirling',
			color: '#fff',
			maskColor: '#232323',
		});
		myChart.clear();
		var seriesData=[],legendData=[];
		if(obj) {
			getData(obj, wypayTypeArr, legendData, seriesData);
		}
		if(legendData.length>0){
			opt.legend[0].data=legendData;
		}
		if(seriesData.length>0){
			opt.series[0].data=seriesData;
		}else{
			opt.series[0].data=[{value:0, name:"物业缴费渠道"}];
		}
		myChart.hideLoading();
		myChart.setOption(opt);
	}

	//================详情================
	var flag = true; //控制搜索 键盘事件调多次
	//查询列表
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

	function goBack() {
		$("#profileDetail").hide();
		$("#controllPanel").show();
	}

    /**
     * select2 ajax 数据格式
     * {"results":[{id:1,text:"aa"},...]}
     */
    var category = ["jiagou_12hour_goods","jiagou_7day_goods","jiagou_15day_goods","jiagou_30day_goods"];
    var classif = ["field_preference"];
    var SKU = ["xls_preference", "xls_preference_7day","xls_preference_15day","xls_preference_30day","xls_preference_60day","xls_preference_90day","service_preference",
        "service_preference_7day","service_preference_15day","service_preference_30day","service_preference_60day","service_preference_90day",
        "xls_7_buy_goods","xls_15_buy_goods","xls_30_buy_goods","xls_60_buy_goods","xls_90_buy_goods",
        "service_7_buy","service_15_buy","service_30_buy","service_60_buy","service_90_buy"];
	function jsonTypeSearch(the,userselectVal){
	    var url = "";
        if(category.indexOf(userselectVal) > -1){
            url="searchCategory"
		}else if(classif.indexOf(userselectVal) > -1){
            url="searchClassif"
		}else if(SKU.indexOf(userselectVal) > -1){
			url="searchSKU"
        }
        $(the).select2({
            ajax: {
                url: './'+url,
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        searchKey: params.term.trim(), //要查询的字段
                        isMatch: true
                    };
                },
                results: function (data, page) {//后台返回的select2 ajax的特定格式用这个，返回的其他的格式需要转换，用processResults
//                    console.log(data)
                    return { results: data };
                },
//                processResults: function (data) {
//                    //重命名字段名--如果后台返回的数据不是特定格式
//                    for (var i = 0; i < data.length; i++){
//                        data[i].id = data[i].Value;
//                        data[i].text = data[i].Name;
//                    }
//                    return { results: data };
//                },
//                cache: true //无缓存
            },
            language: "zh-CN",
            placeholder: {id:"-1", text:"请输入"},
            width:'235px',
            tags: false,//启用标记，允许手动添加标记
            multiple: true,
            minimumInputLength: 1,//最少输入多少个字符后开始查询
            escapeMarkup: function(markup) {// 自定义格式化防止xss注入
                return markup;
            },
            templateResult: function (userName) { // 函数用来渲染结果
                if (userName.loading) return userName.text;
                var markup = "<div class='select2-result-user clearfix'>" +userName.text+' | '+ userName.id+ "</div>";
                return markup;
            },
            formatSelection: function(data){// 函数用于呈现当前的选择
                return data.text;
            },
            templateSelection: function (userName) {  //选中某一个选项是执行
                return userName.text;
            }
        });
    }

//   $jsonQuery.trigger('change');//使用这个方法显示到select2上.

    //导出
    function downloadData(){
        var obj={};
	    var name = $("#myTab>.bg_active>a").text().trim();
	    if(name=="人群属性"){
            obj.typeVal = "crowd";
        }else if(name=="商业属性"){
            obj.typeVal = "business";
        }else if(name=="习惯偏好"){
            obj.typeVal = "habit";
        }else if(name=="活跃及出行"){
            obj.typeVal = "activeAndTravel";
        }else if(name=="物业属性"){
            obj.typeVal = "travel";
        }

        var conditions =conditionsFun();
        if(conditions && conditions.length > 0){
            obj.conditions = conditions;
            var param = {
                "query":JSON.stringify(obj)
            };
            var title = {
                "m_id":'m_id',
                "p_id":'p_id'
            };
            postDownLoadFile({
                url: './exportUserDatail',
                data: {
                    "params":param,
                    "titles":title
                },
                method: 'post'
            });
        }else{
            popEffect("请选择条件");
        }
    }

    var postDownLoadFile = function (options) {
        var config = $.extend(true, {method: 'post'}, options);
        var $iframe = $('<iframe id="down-file-iframe" />');
        var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
        $form.attr('action', config.url);
        var data = config['data'];
        for (var key in data['params']) {
            $form.append('<input type="hidden" name="params[' + key + ']" value=\'' + data['params'][key] + '\' />');
        }
        var order = 0;
        for (var key in data['titles']) {
            $form.append('<input type="hidden" name="titles[' + key + '].name" value=\'' + data['titles'][key] + '\' />');
            $form.append('<input type="hidden" name="titles[' + key + '].order" value="' + order + '" />');
            order += 1;
        }
        $iframe.append($form);
        $(document.body).append($iframe);
        $form[0].submit();
        $iframe.remove();
    }

    //3秒弹窗
    function popEffect(text) {
        var times = 2;
        $('.pubPopup,.pubMask').show();
        $('.pubPopup .popCon').text(text);
        var i = setInterval(function () {
            times--;
            if (times == 1) {
                $('.pubPopup,.pubMask').addClass('popFadeOut');
            }
            if (times == 0) {
                clearInterval(i);
                $('.pubPopup,.pubMask').hide().removeClass('popFadeOut');
                times = 2;
            }
        }, 1000);
    }
</script>
</body>
</html>