<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<!--群组管理-->
<html>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
	<link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>
	<link href="./thirdlib/Select2/select2.min.css" rel="stylesheet" type="text/css"/>
	<link href="./css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <style>
	    body{font-family:PingFangSC-Regular;font-size:14px;color:#DEDEDE}
	    .deleteIcon{background:url(../../images/icon_operation_del.png) no-repeat;background-size:30px 30px;width:30px;height:30px;display:inline-block;cursor:pointer}
	    .addIcon{background:url(../../images/icon_operation_add.png) no-repeat;background-size:30px 30px;width:30px;height:30px;display:inline-block;margin-left:84px;margin-bottom:-9px;cursor:pointer}
	    .downIcon{position:absolute;right:15px;background:url(../../images/icon_down.png) no-repeat;background-size:10px 10px;width:10px;height:10px;display:inline-block;margin-top:3px;cursor:pointer}
	    .leftPanel{height:100%;float:left;padding:10px 0;border-right:1px solid #333}
	    .leftPanel .addEmptyList{height:30px;line-height:30px;margin-right:15px;margin-bottom:15px;border:1px solid #939393;border-radius:2px;text-align:center;cursor:pointer}
	    .rightPanel{display:inline-flex;padding:10px 0 10px 15px}
	    .rightPanel .condition{width:100%;}

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
		.select2-container .select2-selection--multiple,.select2-container--default .select2-selection--multiple .select2-selection__rendered{margin-bottom: -5px;border-radius: inherit;}
		.select2-container--default .select2-container--focus .select2-selection--multiple{border:0;}
		.select2-container--default .select2-selection--multiple .select2-selection__rendered{overflow-y: auto;max-height: 90px;}
		.select2-container--default .select2-selection--multiple .select2-selection__rendered li{line-height: 24px; margin: 2px;}
		.select2-container--default .select2-selection--multiple .select2-selection__choice{margin-top: 0;background-color: transparent;}

		.condition>div{margin-bottom:10px;}
		.condition>div:nth-of-type(1){height:30px;}
		.condition>div:nth-of-type(2){height:75px;}
		.condition>div:nth-of-type(3){height:20px;}
		.condition>div:nth-of-type(4).condPanel{margin-bottom:0px;}
		.condition>div:nth-of-type(5){height:30px;margin-bottom:10px;}
	    .condition>div>span{float:left;width:84px;text-align:right}
		.condition>div>input{margin-left:0!important;}
	    .confirmDialog p lable{display:inline-flex;line-height:30px}
	    .condition>div span.preview{float:none;margin-left:10px;padding:2px 15px;border:1px solid #939393;border-radius:2px;cursor:pointer}
	    .condition>div .radio-inline{height:12px;width:12px;border-radius:6px;border:1px solid #999;margin-bottom:0;margin-right:6px}
	    .condition>div .radio_active{border:1px solid #FDA413;margin-top:5px;}
	    .condition>div .radio-inline span{display:block;width:6px;height:6px;border-radius:3px;margin-left:2px;margin-top:2px}
	    .condition>div .radio-inline span.circle{background:#FDA413}
	    .condition>div input,.condition>div select,.condition>div textarea{background:#191919;border-radius:2px;border:0;padding-left:10px;color:#939393;font-size:14px}
	    .condition>div input,.condition>div select{height:30px;line-height:30px}
	    .condition>div select{min-width:94px;}
	    .condDiv select:first-child,.condDiv>label input,.condDiv>label select,.condDiv>label textarea{width: 235px}
	    .condDiv>label input{margin-right: 14px;}
	    .condition>div em{font-style:normal}
	    .userGroup .privateText{cursor:pointer}
	    .userGroup ul{max-height:190px;overflow-y:auto}
	    .userGroup ul li{list-style-type:none;color:#999;font-size:14px}
	    .userGroup ul li a{color:#999}
	    .userGroup ul li a:hover{color:#FFF}
	    .userGroup ul li span{position:absolute;right:15px;font-size:14px;color:#FDA413;cursor:pointer;display:none}
	    .condPanel>div{margin-left:84px;}
	    .condPanel select{margin-right:15px}
	    .confirmDialog{position:fixed;top:45%;left:50%;width:260px;height:auto;background:#232323;border-radius:4px;z-index:9;display:none}
	    .confirmDialog .confirmPanel .confirmTitle{height:50px;line-height:50px;background:#333;border-top-left-radius:4px;border-top-right-radius:4px;color:#FFF;text-align:center}
	    .confirmDialog .confirmPanel .button{width:100%;margin:0 auto;text-align:center}
	    .layer{position:fixed;top:0;width:100%;height:100%;z-index:1;background:#000;opacity:.8;display:none}
	    label>input.datePick{width:95px}
	    .but{width:70px;height:30px;line-height:30px;border-radius:2px;text-align:center;display:inline-block;cursor:pointer}
	    .confirmBtn{background-color:#FDA413}
	    .cancel{border:1px solid #939393}
	    .userGroup ul::-webkit-scrollbar-track{-webkit-box-shadow:inset 0 0 6px rgba(0,0,0,.3);border-radius:5px}
	    .userGroup ul::-webkit-scrollbar{width:10px}
	    .userGroup ul::-webkit-scrollbar-thumb{border-radius:5px;-webkit-box-shadow:inset 0 0 6px rgba(0,0,0,.3);background-color:#4e4e4e}
		.condition>div .add{line-height:30px;display:inline-block;margin-left:84px;margin-bottom:10px;height:26px;color:#FDA413;font-size:12px;cursor:pointer}
		.condition>div .add>span{font-size:20px;width: 20px;  text-align: center;}
		.paramsfir .dropsearch{width: 100%;background: #fff;color: #000;margin-left: 0;}
		.searchshow>h3{color: #FDA413;font-size:14px;padding-left: 10px;margin-top: 0;margin-bottom:0;line-height:30px;}
		.searchshow>p>span{cursor:pointer;line-height: 30px;display:block;width:100%;color: #DEDEDE;font-family: PingFangSC-Regular;font-size: 14px;padding-left:20px;}
		.secdrop{float:left;}
		.dropbox .ztree{width: 250px;overflow-y:inherit;}
		.dropbox .ztree li span{line-height: 30px;color: #fff;text-align:left;float:none;}
		.dropbox .ztree *{font-size: 14px;}
		.dropbox .ztree li a{height: 30px;padding-left: 5px;}
		.dropbox .ztree li span.button.switch{height: 18px;width: 18px;margin-top:6px;}
		.dropbox .ztree li a.curSelectedNode{padding-top: 1px;background-color: rgba(0,0,0,0);color: #FDA413;height: 30px;border: 0px #FFB951 solid;opacity: 1;}
		.dropbox .ztree li a.curSelectedNode span{line-height: 30px;color: #FDA413;}
		.paramsfir{position:relative;line-height:14px;}
		.dropshow{width: 230px;height: 30px;background:#191919;float: left;line-height: 28px;padding-left: 10px;}
		.dropshow>p{float: left;margin-bottom:0;}
		.dropshow .arrow{width: 18px;height: 28px;float: right;background: url(../../images/down2.png) no-repeat;background-size:8px 10px;background-position: 5px 9px;}
		.dropshow .arrow_2{cursor:pointer;width: 8px;height: 28px;float: right;background: url(../../images/close.png) no-repeat;background-size:7px 7px;background-position: 0 10px;}
		.dropbox{background: #191919;position:absolute;width: 230px;height: 250px;overflow:auto;bottom:40px;z-index: 99;}
		.dropbox .normalshow>div>h3{color: #FDA413;font-size:14px;padding-left: 10px;margin-top: 0;margin-bottom:0;line-height:30px;}

		.paramsfir .secdrop>select,input{min-height:28px;float:left!important;height: 28px;background: #191919;line-height: 28px;color:#fff;border: 0;margin-left:10px!important;min-width:100px;}
		.paramsfir .secdrop>select{margin-right: 0;padding-left:0;}
		.form-control{width: 20%;}
		.select2-container{margin-left:10px;}
		select.form-control:not([size]):not([multiple]){height: 28px;}
		.select2-container .select2-selection--multiple{min-height: 28px;line-height: 28px;}
		.phoneNumbr input{float: none;}
		.phoneNumbr span{float:left;line-height:30px;}
		.condition>div input{float:none;margin-left:0;}
		.form-control{padding: 0;}
		.select2-container--default .select2-results>.select2-results__options{margin-top:26px;}
		.select2-container--default .select2-search--dropdown .select2-search__field{margin-left: 0!important;}
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
        <div class="container-fluid" style="background: #191919;">
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
						<div class="addEmptyList" onclick="addUserGroup()">添加</div>
		                <div class="userGroup">
			                <div class="privateText" onclick="showAndHide('private')">个人私有<span class="downIcon"></span></div>
			                <ul id="privateList"></ul>
		                </div>
		                <div class="userGroup">
			                <div class="publicText" onclick="showAndHide('public')">公开共享<span class="downIcon"></span></div>
			                <ul id="publicList"></ul>
		                </div>
                    </div>

	                <div class="col-md-9 rightPanel">
		                <div class="condition">
			                <input type="hidden" id="groupId" value="">
			                <div>
				                <span>用户群名称：</span><input type="text" name="name" id="name" placeholder="请输入" style="width: 50%;">
			                </div>
			                <div>
				                <span>用户群描述：</span> <textarea name="description" id="description" class="form-control" placeholder="请输入" rows="3" style="width: 50%;"></textarea>
			                </div>
			                <div>
				                <span>是否共享：</span>
				                <label id="publicShare" class="radio-inline radio_active" data-value="0"><span class="circle"></span></label>公开共享
				                <label id="privateShare" class="radio-inline" style="margin-left: 20px;" data-value="1"><span></span></label>个人私有
			                </div>

			                <div class="condPanel" style="margin-bottom: 0">
				                <span>用户群体：</span>
				                <!--这里的 id 有规律且有用，不能随意更改-->
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
												<h3>标签</h3>
												<div id="treeDemo_0" class="ztree"></div>
											</div>
										</div>
										<div class="searchshow"></div>
									</div>
									<div class="secdrop" id="secdrop_0"></div>
									<i class="deleteIcon" onclick="javascript:cutCondition(this);" style="margin-left: 5px"></i>
								</div>
			                </div>
							<div>
								<label class="add" onclick="addCondition()"><span>+</span>添加条件</label>
							</div>
			                <div>
								<span>符合人数：</span><em id="showNumber">0</em>人
				                <span class="preview" onclick="yulan()">预览</span>
			                </div>
			                <div>
				                <div class="but confirmBtn" style="margin-right: 10px;" id="openSave" onclick="openDialog('save')">保存</div>
		                        <div class="but cancel" id="openDelete">删除</div>
			                </div>
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
<!--蒙层-->
<div class="layer"></div>

<!--确认框-->
<div id="confirmDialog" class="confirmDialog">
	<div class="confirmPanel">
		<div class="confirmTitle"></div>
		<div class="button">
			<div class="but confirmBtn" style="margin: 20px" id="confirmBtn">确认</div>
			<div class="but cancel" onclick="closeDialog()">取消</div>
		</div>
	</div>
</div>

<c:import url="common/importJs2.jsp"></c:import>
<script type="text/javascript" src="./js/zTreeJs/jquery.ztree.core.js"></script>
<script src="./assets/plugins/datepicker/js/bootstrap-datepicker.js"></script>
<script src="./assets/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript" src="./assets/plugins/moment-with-locales.min.js"></script>
<script type="text/javascript" src="./thirdlib/Select2/select2.full.min.js"></script>
<script type="text/javascript" src="./js/userProfileCommon.js"></script>
<script type="text/javascript">
	var userGroupList = JSON.parse(sessionStorage.getItem("userGroupList"));
	var firstParam = JSON.parse(sessionStorage.getItem("firstParam"));
	var lpdObj = JSON.parse(sessionStorage.getItem("lpdObj"));
    var treeele = JSON.parse(sessionStorage.getItem("treeEle"));

	$(document).ready(function(){
		getUserGroup2();
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
            $('#param_'+num+' .dropshow p').attr('data-select-type', 'group');
            $('#param_'+num+' .dropbox').css('display', 'none');
            $('#param_'+num+' .arrow_2').css('display', 'block');
            $('#param_'+num+' .secdrop').empty();
            treerender(num);
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
            selectStr += '<input type="number" id="thirdParam_'+num+'" class="phoneNumbr" style="width:80px" placeholder="请输入数字">';
        }
        $('#param_'+num+' .secdrop').empty();
        $('#param_'+num+' .secdrop').append(selectStr);
        if(userselectVal=="city" || userselectVal=="community" || userselectVal=="property"){
            $("#thirdParam_"+num).select2({
                placeholder: '',
                width:'235px',
				height: '89px',
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
                $("#param_"+ index +" >.secdrop").append('<span>&nbsp;&nbsp;至&nbsp;&nbsp;</span><input type="text" name="end_date" class="datePick" id="endDate_'+index+'" onfocus="selectTimes('+index+')"/>');
                $('#endDate_'+index).val(moment().subtract(0, 'days').format('YYYY-MM-DD'));
            }else{
                $("#param_"+ index +" >.secdrop .datePick").remove();
                $("#param_"+ index +" >.secdrop").append('<input type="text" name="start_date" class="datePick" id="startDate_'+index+'" onfocus="selectTimes('+index+')"/>');
                $('#startDate_'+index).val(moment().subtract(6, 'days').format('YYYY-MM-DD'));
            }
        }else if(dataType=="5"){
            if(rule=="bt"){
                $("#param_"+ index +" >.secdrop").append('<span class="phoneNumbr"><span>&nbsp;&nbsp;至&nbsp;&nbsp;</span><input type="number" style="width:130px" id="endNumber_'+index+'" placeholder="请输入数字"/></span>');
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
                $("#param_" + num).css('height', '89px');
                $("#thirdParam_"+num).select2({
                    placeholder: '请选择多个',
                    width:'235px',
					height: '89px',
                    multiple:true
                });
            }else if(list.length > 10){
                $("#thirdParam_"+num).select2({
                    placeholder: '请选择',
                    width:'235px',
                    multiple:false
                });
                $("#param_" + num).css('height', '32px');
            }else{
                $("#param_" + num).css('height', '32px');
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
                    var i2 = indexList[j];
                    var selectType = $('#param_'+i2+' .dropshow p').attr("data-select-type");
                    if(selectType=="group"){
                        $.ajax({
                            url: "./managePortraitUserGroup",
                            type: "post",
                            async:false,
                            data: {
                                id: $("#firstCondition_"+i2+" option:selected").val()
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
                        var tagName = $('#param_'+i2+' .dropshow p').text();
                        var obj = conditionsdata(tagName);
                        var tag = obj.tag;
                        var dataType = obj.dataType;
                        var rule = $("#ruleSelect_"+i2+" option:selected").val();
                        var value1="",value2="",tagObj={};

                        if(dataType=="1"){
                            value1 = $("#thirdParam_"+i2).val();
                        }else if(dataType=="2"){
                            //json 的包含 wildcard 和   exWildcard 不包含
                            if(rule=="wildcard" || rule=="exWildcard"){
                                //获取多选下来框选中的值
								var judgelegs = $("#thirdParam_"+i2+" .select2,.select2-container,.select2-container--default").parent().parent().attr('id');
                                var mValArray=[];
                                if(judgelegs != ('param_' + i2)){
                                    var newdatas = $("#thirdParam_"+i2+" option:selected").html();
                                    var idcorname = JSON.parse(sessionStorage.getItem('idcorname_'+i2));
                                    var newarr = newdatas.split(',');
                                    for(var x=0; x<idcorname.length; x++){
                                        for(var z=0; z<newarr.length; z++){
                                            if(idcorname[x].text == newarr[z]){
                                                var text2='"'+idcorname[x].id+'"';
                                                mValArray.push(text2);
                                            }
                                        }
                                    }
                                    value1 =mValArray.join(",");
								}else{
                                    var multipleVal = $("#thirdParam_"+i2).select2("val");
                                    if(multipleVal && multipleVal.length>0){
                                        for(var y=0;y<multipleVal.length;y++){
                                            if(Number(multipleVal[y])){
                                                var text='"'+multipleVal[y]+'"';
                                                mValArray.push(text);
                                            }else{
                                                var idcorname = JSON.parse(sessionStorage.getItem('idcorname'));
                                                var newarr = multipleVal[y].split(',');
                                                for(var x=0; x<idcorname.length; x++){
                                                    for(var z=0; z<newarr.length; z++){
                                                        if(idcorname[x].text == newarr[z]){
                                                            var text2='"'+idcorname[x].id+'"';
                                                            mValArray.push(text2);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        value1 =mValArray.join(",");
                                    }
								}
                            }
                        }else if(dataType=="3"){
                            var mValArray = [];
                            if(rule=="in" || rule=="ex"){
                                //获取多选下来框选中的值
                                var judgelegs = $("#thirdParam_"+i2+" .select2,.select2-container,.select2-container--default").parent().parent().attr('id');
								if(judgelegs == ('param_' + i2)){
                                    var multipleVal = $("#thirdParam_"+i2).select2("val");
                                    var mValArray = multipleVal;//多选value1 多个参数用逗号分隔以字符串的形式传给后台
                                    if(mValArray && mValArray.length>0){
                                        value1 =mValArray.join(",");
                                    }
								}else{
                                    value1 = $("#thirdParam_"+i2+" option:selected").attr('example_id');
								}
                            }else{
                                value1 = $("#thirdParam_"+i2+" option:selected").val();
                            }
                        }else if(dataType=="4"){
                            value1 = $('#startDate_'+i2).val();
                            value2 = $('#endDate_'+i2).val();
                        }else if(dataType=="5"){
                            value1 = $('#thirdParam_'+i2).val();
                            value2 = $('#endNumber_'+i2).val();
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
                    }
                }
            }
        }
        return conditions;
    }
    //提取数据
    function conditionsdata(name){
        var list = firstParam,
			obj = {};
        for(var i=0; i<list.length; i++){
            if(list[i].name == name){
                obj = list[i];
            }
        }
        return obj;
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

	//左侧用户群组列表
	function getUserGroup2() {
		var res = userGroupList;
		$("#privateList,#publicList").html("");
		if(res){
			var privateStr="",publicStr="";
			var privateList = res.private;
			var publicList = res.public;
			if(privateList && privateList.length > 0){
				for(var i=0;i<privateList.length;i++){
					privateStr += '<li onmouseover="javascript:mouseoverFun(this);" onmouseout="javascript:mouseoutFun(this);"><a href="#" onclick="javascript:getUserGroupById(this'+',\''+privateList[i].id+'\');">'+privateList[i].name+'</a><span onclick="javascript:copyGroup(this'+',\''+privateList[i].id+'\');">复制</span></li>';
				}
				$("#privateList").append(privateStr);
			}else{
				$("#privateList").append('<li>暂无数据</li>');
			}
			if(publicList && publicList.length > 0){
				for(var j=0;j<publicList.length;j++){
					publicStr += '<li onmouseover="javascript:mouseoverFun(this);" onmouseout="javascript:mouseoutFun(this);"><a href="#" onclick="javascript:getUserGroupById(this'+',\''+publicList[j].id+'\');">'+publicList[j].name+'</a><span onclick="javascript:copyGroup(this'+',\''+publicList[j].id+'\');">复制</span></li>';
				}
				$("#publicList").append(publicStr);
			}else{
				$("#publicList").append('<li>暂无数据</li>');
			}
		}
	}

	//复制群组
	function copyGroup(the,uid) {
		var userGroupId={
			id:uid
		}
		$.ajax({
			url:"./copyPortraitUserGroup",
			type:"post",
			data:userGroupId,
			success:function (res) {
				if(res && res.userGroup){
					$(the).parent().parent().prepend('<li><a href="#" onclick="javascript:getUserGroupById(this'+',\''+res.userGroup.id+'\');">'+res.userGroup.name+'</a></li>');
				}
			}
		});
	}

	//编辑群信息
	function getUserGroupById(the,uid) {
		$(".userGroup ul li a").css("color","#999");
		$(the).css("color","#FFFFFF");
		$("#openDelete").removeAttr("onclick");
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
                                idobtainname(arr[i].value1, obj.tag, num, selectStr, res);
//								selectStr += '<input type="text" id="thirdParam_'+num+'" onchange="javascript:jsonTypeSearch(this);">';
                            }
                            <!--3、列表-->
                            if(Number(userselectType)==3){
                                var list = obj.lpd;
								if(arr[i].rule == 'in' || arr[i].rule == 'ex'){
                                    var arr2 = [],
										rulearr = arr[i].value1.split(',');
                                    if(list && list.length > 0){
                                        for(var j=0;j<list.length;j++){
                                            for(var m=0; m<rulearr.length; m++){
                                                if(rulearr[m] == list[j].example_id){
                                                    arr2.push(list[j].example_name)
												}
											}
                                        }
                                    }
                                    var opt="<option example_id="+arr[i].value1+">"+arr2.join(',')+"</option>";
                                    if(userselectVal=="city" || userselectVal=="community" || userselectVal=="property"){
                                        selectStr += '<select class="select2" id="thirdParam_'+num+'">'+opt+'</select>';
                                    }else{
                                        selectStr += '<select id="thirdParam_'+num+'">'+opt+'</select>';
                                    }
								}else{
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
                            if(Number(userselectType)!=2){
                                $('#param_'+num+' .secdrop').empty();
                                $('#param_'+num+' .secdrop').append(selectStr);
							}
                            if(userselectVal=="city" || userselectVal=="community" || userselectVal=="property"){
                                $("#thirdParam_"+num).select2({
                                    placeholder: '',
                                    width:'235px',
									height: '89px',
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
	//预览
	function yulan() {
        var conditions =conditionsFun();
		var params ={
			"conditions":conditions
		}
		$.ajax({
			url:"./userProfileV2query",
			type:"get",
			data:{
				"query":JSON.stringify(params)
			},
			success:function (res) {
				if(res || res.totalHit){
					$("#showNumber").text(res.totalHit.toLocaleString());
				}
			}
		});
	}

	//保存
	function saveUserGroup(uid) {
		var name = $("#name").val();
		if(!name){
			alert("请输入用户群名称");
			closeDialog();
			return;
		}
		var des = $("#description").val();
		var isShare = $(".radio_active").attr("data-value");

        var conditions =conditionsFun();

		var params = {
			"name":name,
			"description":des,
			"isShare":isShare,
		}
		if(conditions.length>0){
			var con ={
				"conditions":conditions
			}
			params.conditions = JSON.stringify(con);
		}

		if(uid && uid!="undefined"){
			params.id = uid;
		}
        sessionStorage.setItem('params', JSON.stringify(params))
		$.ajax({
			url: "./addOrUpdatePortraitUserGroup",
			type: "POST",
			data:params,
			success: function (res) {
				if(res){
					closeDialog();
//					window.location.reload();
					window.location.href="./groupManagement";
				}
			}
		});
	}

	//删除群组
	function deleteUserGroup(uid) {
		closeDialog();
		var userGroup={
			id:uid
		}
		$.ajax({
			url: "./deletePortraitUserGroupnew",
			type: "get",
			data: userGroup,
			success: function () {
//				window.location.reload();
				window.location.href="./groupManagement";
			}
		});
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
	//鼠标移入移除
	function mouseoverFun(the) {
		$(the).css("color","#FFFFFF");
		$(the).find("span").css("display","inline-block");
	}
	function mouseoutFun(the) {
		$(the).css("color","#999999");
		$(the).find("span").hide();
	}

	//切换单选按钮
	$(".radio-inline").click(function () {
		$(this).addClass("radio_active").children("span").addClass("circle");
		$(this).siblings("label").removeClass("radio_active").children("span").removeClass("circle");
	});

	//添加群组
	function addUserGroup() {
		$("#groupId,#name,#description").val("");
		$(".condPanel > div").remove();
		$("#openSave").attr("onclick","openDialog('save')");
		getUserTagSelect();
	}
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
        emptyCon += '<h3>标签</h3>';
        emptyCon += '<div id="treeDemo_'+num+'" class="ztree"></div>';
        emptyCon += '</div>';
        emptyCon += '</div>';
        emptyCon += '<div class="searchshow"></div>';
        emptyCon += '</div>';
        emptyCon += '<div class="secdrop" id="secdrop_'+num+'"></div>';
        emptyCon += '<i class="deleteIcon" onclick="javascript:cutCondition(this);" style="margin-left: 5px"></i>';
        emptyCon += '</div>';
        $(".condPanel").append(emptyCon);
        $(".condPanel").removeAttr("style");
        getUserGroup(num);
    }

	//减少条件
    function cutCondition(the) {
        if($(".paramsfir").length>1){
            $(the).parent().remove();
        }
    }

	//打开弹窗
	function openDialog(type,uid) {
		if(type=="save"){//保存可以不要id
			$("#confirmDialog .confirmTitle").text("确认保存?");
			$("#confirmBtn").attr("onclick","saveUserGroup('"+uid+"')");
		}else if(type=="delete"){//删除必须要 id
			if(uid){
				$(".confirmTitle").text("确认删除?");
				$("#confirmBtn").attr("onclick","deleteUserGroup('"+uid+"')");
			}else{//3秒提示框
				alert("请选择要删除的群组！");
			}
		}
		$("#confirmDialog,.layer").show();
	}
	//关闭弹窗
	function closeDialog() {
		$("#confirmDialog,.layer").hide();
	}
	function showAndHide(str) {
		if(str=="private"){
			$("#privateList").toggle();
		}else{
			$("#publicList").toggle();
		}
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
        $(the).parent().parent().css('height', '89px');
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
			height: '89px',
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
	//根据id获取名称
	function idobtainname(type, tagval, index, selectStr, oldres){
        var arr = type.split('","'),
			arr2 = type.split('"'),
			arr3 = [],
			num = index;
		for(var i=0; i<arr.length; i++){
			if(i == 0){
			    if(Number(arr2[1])){
                    arr3.push(Number(arr2[1]));
				}
			}else{
			    if(parseInt(arr[i])){
                    arr3.push(parseInt(arr[i]));
				}
			}
		}
        var url = "";
        if(category.indexOf(tagval) > -1){
            url="searchCategory"
        }else if(classif.indexOf(tagval) > -1){
            url="searchClassif"
        }else if(SKU.indexOf(tagval) > -1){
            url="searchSKU"
        }
        $.ajax({
            url: "./" + url,
            type: "post",
            async:false,
            data: {
                searchKey: arr3.join(','),
                isMatch: false
            },
            success: function (res) {
                if (res) {
                    var paramsarr = [],
                        paramsstr = '',
						res = res.results;
                    sessionStorage.setItem('idcorname_'+num, JSON.stringify(res));
                    for(var i=0; i<res.length; i++){
                        paramsarr.push(res[i].text);
					}
                    selectStr += '<select class="form-control" id="thirdParam_'+num+'" onfocus="javascript:jsonTypeSearch(this,\''+tagval+'\');"><option>'+paramsarr.join(',')+'</option></select>';
					$('#param_'+num+' .secdrop').empty();
                    $('#param_'+num+' .secdrop').append(selectStr);
                    $('.secdrop').css('width', '53%');
                    $('#thirdParam_'+num).css('width', '63%');
                    $("#openSave").attr("onclick","openDialog('save','"+oldres.userGroup.id+"')");
                    $("#openDelete").attr("onclick","openDialog('delete','"+oldres.userGroup.id+"')");
                }
            },
			error: function(){
                selectStr += '<select class="form-control" id="thirdParam_'+num+'" onfocus="javascript:jsonTypeSearch(this,\''+tagval+'\');"><option></option></select>';
                $('#param_'+num+' .secdrop').empty();
                $('#param_'+num+' .secdrop').append(selectStr);
                $('.secdrop').css('width', '53%');
                $('#thirdParam_'+num).css('width', '63%');
                $("#openSave").attr("onclick","openDialog('save','"+oldres.userGroup.id+"')");
                $("#openDelete").attr("onclick","openDialog('delete','"+oldres.userGroup.id+"')");
			}
        });
	}
</script>
</body>
</html>
