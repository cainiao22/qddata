<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<!--标签列表页面-->
<html>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
    <link rel='stylesheet' type='text/css' href='./assets/plugins/zTree/zTreeStyle.css'/>
	<style>
		.title{padding-left: 10px;height: 50px;line-height: 50px;background: #333333;box-shadow: 0 0 3px 2px rgba(0,0,0,0.03);font-family: PingFangSC-Regular;font-size: 16px;color: #DEDEDE}
		#tagExample,#modifySubmitBtn,#delBtn{display: none}/**非管理员不能编辑**/
        .leftPanel{height:100%;float:left;padding:20px 0 10px 10px;border-right:1px solid #333}
        .leftPanel .addEmptyList {height: 30px;line-height: 30px;margin-right: 15px;margin-bottom: 15px; border: 1px solid #939393; border-radius: 2px;font-size:15px;color:#fff;text-align: center;cursor: pointer;display: none; }
        .rightPanel{display:inline-flex;padding:20px 15px 15px 15px;}
        .ztree{width: auto;height: 494px;overflow: auto}
        .ztree li span{font-size: 14px;}
        .form-group label{margin-left:20px;color: #DEDEDE;}
        .form-group select,.form-group input,.form-group textarea{width:100%;min-height: 38px;line-height: 38px;padding:0 10px; background: #191919;border-radius: 2px;border: 0; color: #DEDEDE;font-size: 17px;}
        .tag-instance{display: inline-flex; float: left;}
        .tag-instance input {width: 160px;margin:0 10px 10px 0;}

        .btn{height:38px;margin-right: 15px}
	</style>
	<script type="text/javascript">
		//获取cookie
		function getCookie(name) {
			var arrcookie = document.cookie.split("; ");//分割
			for ( var i = 0; i < arrcookie.length; i++) {
				var arr = arrcookie[i].split("=");
				if (arr[0] == name){
					return arr[1];
				}
			}
			return "";
		}

		var zNodes = [];

		<c:forEach items="${PortraittagList}" var="tag">
           //这里把选中的标签都push到数值，编辑的时候才能用
			zNodes.push({
				id: "${tag.id}",   //主键ID
                slevel:"${tag.level}",
				pId: "${tag.pid}",  //父级
				name: "${tag.name}", //名称
				tag:"${tag.tag}",   //标识
                <%--dataType:"${tag.dataType}",//类型--%>
                <%--sortNo:"${tag.sortNo}",    //排序--%>
                <%--desc:"${tag.description}", //描述--%>
				open: false,
				<%--url: "./portraitEditTag?id=${tag.id}&tag=${tag.tag}&level=${tag.level}",--%>
				target: "_self"
			});
		</c:forEach>
	</script>
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
            <div class="row">
                <div class="col-md-12" style="padding:0;background: #232323;">
					<div class="title">标签列表</div>
                    <div class="col-md-3 leftPanel">
                        <a href="#" id="add" onclick="addTag()"><div class="addEmptyList">添加标签</div></a>

                        <div class="zTreeDemoBackground">
                            <ul class="ztree">
                                <li tabindex="0" hidefocus="true">
                                    <span  class="button ico_open"></span>
                                    <span  class="node_name">系统顶级</span>
                                </li>
                                <li>
                                    <ul id="tagTree"></ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-9 rightPanel">
                        <form  class="form-horizontal" action="./savePortraitTag" method="post" id="form">
                            <input name="id" id="eidtTagId" type="hidden"/>
                            <input name="level" id="level" type="hidden"/>
                            <div class="form-group row">
                                <label>父级标签:</label>
                                <div class="col-md-7">
                                    <select id="p_module" name="pid" class="form-control">
                                        <option value='1'>系统顶级</option>
                                        <c:forEach items="${PortraittagList}" var="tagTemp">
                                            <option value='${tagTemp.id}' data-tag="${tagTemp.tag}" data-level="${tagTemp.level}">
                                                <c:forEach begin="1" end="${tagTemp.level}">------</c:forEach>
                                                    ${tagTemp.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label>标签名称: </label>
                                <div class="col-md-7">
                                    <input type="text" name="name" id="name" class="form-control" value=""/>
                                </div>
                            </div>
                            <!--标识必填-->
                            <div class="form-group row">
                                <label>标签标识: </label>
                                <div class="col-md-7">
                                    <input type="text" name="tag" id="tag" class="form-control" placeholder="标识必填" required/>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label>标签类型: </label>
                                <div class="col-md-7">
                                    <select id="dataType" class="form-control" name="dataType">
                                        <option value='1'>文本</option>
                                        <option value='2'>json</option>
                                        <option value='3'>列表</option>
                                        <option value='4'>日期</option>
                                        <option value='5'>数值</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row" id="tagExample">
                                <label>标签实例: </label>
                                <div class="col-md-7 example">

                                </div>
                            </div>
                            <div class="form-group row">
                                <label>排序数值: </label>
                                <div class="col-md-7">
                                    <input type="text" name="sortNo" id="sortNo" class="form-control" value="">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label>规则描述: </label>
                                <div class="col-md-7">
                                    <textarea name="description" id="desc" class="form-control" rows="5" cols="16"></textarea>
                                </div>
                            </div>
                            <div class="form-group row" style="margin-left:42%">
                                <button id="addSubmitBtn" type="submit" class="btn btn-success">添加</button>

                                <button id="modifySubmitBtn" type="submit" class="btn btn-success">确认修改</button>
                                <a href="#" id="deleteUrl">
                                    <button id="delBtn" type="button" class="btn btn-warning">删除标签</button>
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
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
<script type="text/javascript" src="./assets/plugins/zTree/jquery.ztree.core-3.5.min.js"></script>
<%--<script type="text/javascript" src="./assets/js/moduleTree.js"></script>--%>
<script>
	var setting = {
		view:{
			showLine:false   //是否显示节点之间的连线
		},
		data: {
			simpleData: {
				enable: true
			}
		},
        callback: {
            onClick: editTag,
        }
	};
	$(document).ready(function () {
		$.fn.zTree.init($("#tagTree"), setting, zNodes);
        if(getCookie("username")!="admin"){
            $("#p_module,#dataType").attr("disabled","true");
            $("#name,#tag,#sortNo,#desc").attr("readOnly","true");
            $(".tag-instance:last-child,.btn").hide();
        }else{
            $(".addEmptyList").show();
        }
	});
    //添加标签
    function addTag() {
        $("#dataType option:first-child").attr("selected","selected");
        $("#dataType option:first-child").text("text");

        $("#name,#tag,#sortNo,#desc").val("");
        $("#addSubmitBtn").show();
        $("#modifySubmitBtn,#modifySubmitBtn,#delBtn,#tagExample").hide();
        $("#tagExample>div").html("");
        $("#deleteUrl").attr("href","#");
    }
	//编辑标签
    function editTag(event, treeId, treeNode) {
        $("#tagExample>div").html("");
        if(getCookie("username")!="admin"){
            $("#tagExample,#addSubmitBtn,#modifySubmitBtn,#delBtn").hide();
        }else{
            $("#addSubmitBtn").hide();
            $("#tagExample,#modifySubmitBtn,#delBtn").css("display","inline-flex");
        }
//        console.log(treeNode);
	    $("#form").attr("action","./editPortraitTag");
	    $("#deleteUrl").attr("href","deletePortraitTag?tag="+treeNode.tag);

        var tagParam={
            "id":treeNode.id,
            "tag":treeNode.tag,
            "level":treeNode.slevel
        }
//        if(!treeNode.pId){
//            $("#p_module").val("1");
//        }else{
//            $("#p_module").val(treeNode.pId);
//        }
//        $("#eidtTagId").val(treeNode.id);
//        $("#name").val(treeNode.name);
//        $("#tag").val(treeNode.tag);
//        $("#dataType").val(treeNode.dataType);
//        $("#sortNo").val(treeNode.sortNo);
//        $("#desc").val(treeNode.desc);

        $.ajax({
            url: "./portraitEditTag",
            type: "post",
            data:tagParam,
            success: function (res) {
//                console.log(res);

                if(res){
                    var pdStr="";
                    if(res.portraitTag){
                        if(!res.portraitTag.pid){
                            $("#p_module").val("1");
                        }else{
                            $("#p_module").val(res.portraitTag.pid);
                        }
                        $("#eidtTagId").val(res.portraitTag.id);
                        $("#name").val(res.portraitTag.name);
                        $("#tag").val(res.portraitTag.tag);
                        $("#dataType").val(res.portraitTag.dataType);
                        $("#sortNo").val(res.portraitTag.sortNo);
                        $("#desc").val(res.portraitTag.description);

                        if(res.portraitTag.dataType == 3){
                            //实例列表
                            if(res.pdList && res.pdList.length > 0){
                                for(var i=0;i<res.pdList.length;i++){
                                    pdStr +='<input type="hidden" value="'+res.pdList[i].id+'">';
                                    pdStr +='<div class="tag-instance">';
                                    pdStr +='<input type="text" placeholder="实例标识" value="'+res.pdList[i].example_id+'" readonly>';
                                    pdStr +='<input type="text" placeholder="标签名称" value="'+res.pdList[i].example_name+'" readonly>';
                                    pdStr +='<input type="text" placeholder="排序编号" value="'+res.pdList[i].sortno+'" readonly>';
                                    pdStr +='<button  type="button" class="btn btn-warning" onclick="deleteDictionary('+res.pdList[i].id+')">删除</button>';
                                    pdStr +='</div>';
                                }
                            }
                            if(getCookie("username")=="admin"){
                                pdStr +='<div class="tag-instance">';
                                pdStr +='<input type="text" placeholder="实例标识">';
                                pdStr +='<input type="text" placeholder="标签名称">';
                                pdStr +='<input type="text" placeholder="排序编号">';
                                pdStr +='<button  type="button" class="btn btn-success" onclick="saveDictionary(this)">添加</button>';
                                pdStr +='<button  type="button" class="btn btn-warning" style="display: none">删除</button>';
                                pdStr +='</div>';
                            }
                            $("#tagExample>div").append(pdStr);
                        }else{
                            $("#tagExample").hide();
                        }
                    }
                }
            }
        });
    }

    //保存标签实例
    function saveDictionary(the) {
        //获取当前实例input 数组
        var sList = $(the).siblings();

        var tagParam={
            "example_id":"",
            "example_name":"",
            "sortno":"",
            "source":$("#tag").val().trim(),
        };

        if(sList[0].value.trim()){
            tagParam.example_id = sList[0].value.trim();
        }
        if(sList[1].value.trim()){
            tagParam.example_name = sList[1].value.trim();
        }
        if(sList[2].value.trim()){
            tagParam.sortno = sList[2].value.trim();
        }

        if(tagParam.source && tagParam.example_id && tagParam.example_name && tagParam.sortno) {
            $.ajax({
                url: "./saveDictionariesTag",
                type: "post",
                data:tagParam,
                success: function (res) {
                    if(res && res.id){
                        $(the).siblings('input').attr("readOnly","true");
                        $(the).siblings('.btn-warning').attr("onclick","deleteDictionary('"+ res.id +"')").show();
                        $(the).remove();

                        //添加一条新的空input
                        var inputStr = '<div class="tag-instance">'+
                            '<input type="text" placeholder="实例标识">'+
                            '<input type="text" placeholder="标签名称">'+
                            '<input type="text" placeholder="排序编号">'+
                            '<button  type="button" class="btn btn-success" onclick="saveDictionary(this)">添加</button>'+
                            '<button  type="button" class="btn btn-warning" style="display: none">删除</button>'+
                            '</div>';
                        $(".example").append(inputStr);
                    }
                }
            });
        }else{
            alert("请添加标签实例，3个框，一个都不能少");
        }

    }

    //删除标签实例
    function deleteDictionary(id) {
        var tagParam={
            "id":id,
            "source":$("#tag").val().trim()
        };
        $.ajax({
            url: "./dictionariesDelete",
            type: "get",
            data:tagParam,
            success: function (res) {
                var str="";
                for(var i=0;i<res.length;i++){
                    str+= '<input type="hidden" value="'+res[i].id+'">';
                    str += '<div class="tag-instance">';
                    str += '<input type="text" placeholder="实例标识" value="'+res[i].example_id+'" readonly>';
                    str += '<input type="text" placeholder="标签名称" value="'+res[i].example_name+'" readonly>';
                    str += '<input type="text" placeholder="排序编号" value="'+res[i].sortno+'" readonly>';

                    str += '<button  type="button" class="btn btn-warning" onclick="deleteDictionary(\''+res[i].id+'\')">删除</button>';
                    str += '</div>';
                }
                str+= '<div class="tag-instance">';
                str+= '<input type="text" placeholder="实例标识">';
                str+= '<input type="text" placeholder="标签名称">';
                str+= '<input type="text" placeholder="排序编号">';

                str+= '<button  type="button" class="btn btn-success" onclick="saveDictionary(this)">添加</button>';
                str+= '<button  type="button" class="btn btn-warning" style="display: none">删除</button>';
                str+= '</div>';
                $(".example").html(str);
            }
        });
    }

</script>
</body>
</html>