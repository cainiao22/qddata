<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<!--编辑标签页面-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
	<style>
		.form-group label{
			line-height: 38px;
			text-align: center;
		}
		.tag-instance{
			display: inline-flex;
			float: left;
		}
		.tag-instance input {
			width: 160px;
			margin-right: 10px;
		}

		#example_id{width: 200px;margin-right: 10px}
		#example_name{width: 200px;margin-right: 10px}
		#submitBtn,.btn-success{margin-right: 10px}

	</style>
</head>

<!-- END HEAD -->
<body class="fix-header fix-sidebar card-no-border">
<div class="preloader">
    <svg class="circular" viewBox="25 25 50 50">
        <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"/>
    </svg>
</div>
<!-- BEGIN CONTAINER -->
<div id="main-wrapper">
    <c:import url="common/header2.jsp"></c:import>
    <!-- BEGIN SIDEBAR -->
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
    <!-- END SIDEBAR -->
    <!-- BEGIN page-wrapper -->
    <div class="page-wrapper">
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
            <div >
                <!-- BEGIN FORM-->
                <form  class="form-horizontal" action="./editPortraitTag" method="post" id="form">
	                <div class="form-group row">
		                <label  class="col-md-3 col-form-label">父级标签:</label>
		                <div class="col-md-5">
			                <select id="p_module" name="pid" class="form-control">
				                <option value='1'>系统顶级</option>
				                <c:forEach items="${treeList}" var="tagTemp">
					                <option value='${ tagTemp.id}'
						                 <c:if test="${ tagTemp.id eq  portraitTag.pid }"> selected=selected </c:if>>
						                 <c:forEach begin="1" end="${tagTemp.level}">------</c:forEach>
							                ${tagTemp.name}
					                </option>
				                </c:forEach>
			                </select>
		                </div>
	                </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">标签名称: </label>
                        <div class="col-md-5">
                            <input type="text" name="name" id="name" class="form-control" value="${portraitTag.name}"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">标签标识: </label>
                        <div class="col-md-5">
                            <input type="text" name="tag" id="tag" class="form-control" value="${portraitTag.tag}" readonly/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">标签类型: </label>
                        <div class="col-md-5">
	                        <select id="dataType" class="form-control" name="dataType">
		                        <option value='1'
				                        <c:if test="${ portraitTag.dataType eq  1 }"> selected=selected </c:if>>
			                        文本
		                        </option>
		                        <option value='2'
				                        <c:if test="${ portraitTag.dataType eq 2 }"> selected=selected </c:if>>
			                        json
		                        </option>
		                        <option value='3'
				                        <c:if test="${ portraitTag.dataType eq 3 }"> selected=selected </c:if>>
			                        列表
		                        </option>
		                        <option value='4'
				                        <c:if test="${ portraitTag.dataType eq 4 }"> selected=selected </c:if>>
			                        日期
		                        </option>
		                        <option value='5'
				                        <c:if test="${ portraitTag.dataType eq 5 }"> selected=selected </c:if>>
			                        数值
		                        </option>
	                        </select>
                        </div>
                    </div>

	                <div class="form-group row">
		                <label class="col-md-3 control-label">标签实例: </label>
		                <div class="col-md-9 example">
			                <c:if test="${not empty pdList}">
				                <c:forEach items="${pdList}" var="pd">
					                <input type="hidden" value="${pd.id}">
					                <div class="tag-instance">
						                <input type="text" class="form-control" placeholder="实例标识" value="${pd.example_id}" readonly>
						                <input type="text" class="form-control" placeholder="标签名称" value="${pd.example_name}" readonly>
						                <input type="text" class="form-control" placeholder="排序编号" value="${pd.sortno}" readonly>

						                <button  type="button" class="btn btn-warning" onclick="deleteDictionary('${pd.id}')">删除</button>
					                </div>
				                </c:forEach>
			                </c:if>

			                <div class="tag-instance">
				                <input type="text" class="form-control" placeholder="实例标识">
				                <input type="text" class="form-control" placeholder="标签名称">
				                <input type="text" class="form-control" placeholder="排序编号">

				                <button  type="button" class="btn btn-success" onclick="saveDictionary(this)">添加</button>
				                <button  type="button" class="btn btn-warning" style="display: none">删除</button>
			                </div>
		                </div>

	                </div>

	                <div class="form-group row">
		                <label class="col-md-3 control-label">排序数值: </label>
		                <div class="col-md-5">
			                <input type="text" name="sortNo" id="sortNo" class="form-control" value="${portraitTag.sortNo}">
		                </div>
	                </div>
                    <div class="form-group row">
                        <label class="col-md-3 control-label">规则描述: </label>
                        <div class="col-md-5">
                            <textarea name="description" id="desc" class="form-control" rows="5" cols="16" style=" height: 80px;">${portraitTag.description}</textarea>
                        </div>
                    </div>
                    <div class="form-group row" style="position: absolute;left: 50%; bottom: 40px;">
                        <input name="id" value="${portraitTag.id}" type="hidden"/>
                        <button id="submitBtn" type="submit" class="btn btn-success">确认修改</button>
                        <c:if test="${ not empty portraitTag && not empty portraitTag.tag}">
                            <a href="deletePortraitTag?tag=${portraitTag.tag}">
                                <button id="delBtn" type="button" class="btn btn-warning" >删除标签</button>
                            </a>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
        <c:import url="common/footer.jsp"></c:import>
    </div><!-- END page wrapper -->
</div>
<!-- END main wrapper -->
<c:import url="common/importJs2.jsp"></c:import>
<script>
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
	$(document).ready(function () {
		var track_user = getCookie("username");
		if(track_user!="admin"){
			$("#p_module,#dataType").attr("disabled","true");
			$("#name,#sortNo,#desc").attr("readOnly","true");
			$(".tag-instance:last-child,.btn").hide();
		}
	});


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
							'<input type="text" class="form-control" placeholder="实例标识">'+
							'<input type="text" class="form-control" placeholder="标签名称">'+
							'<input type="text" class="form-control" placeholder="排序编号">'+
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
					str += '<input type="text" class="form-control" placeholder="实例标识" value="'+res[i].example_id+'" readonly>';
					str += '<input type="text" class="form-control" placeholder="标签名称" value="'+res[i].example_name+'" readonly>';
					str += '<input type="text" class="form-control" placeholder="排序编号" value="'+res[i].sortno+'" readonly>';

					str += '<button  type="button" class="btn btn-warning" onclick="deleteDictionary(\''+res[i].id+'\')">删除</button>';
					str += '</div>';
				}
				str+= '<div class="tag-instance">';
				str+= '<input type="text" class="form-control" placeholder="实例标识">';
				str+= '<input type="text" class="form-control" placeholder="标签名称">';
				str+= '<input type="text" class="form-control" placeholder="排序编号">';

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