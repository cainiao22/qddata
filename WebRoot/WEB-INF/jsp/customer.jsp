<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta HTTP-EQUIV="pragma" content="no-cache">
    <meta HTTP-EQUIV="Cache-Control" content="no-cache, must-revalidate">
    <meta HTTP-EQUIV="expires" content="0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>

    <style>
        .main{background: RGB(29,44,51);padding: 20px 2%;overflow: auto;}
        .condition{position: relative;width: 100%;margin-bottom: 25px;}
        .condition>div{display: inline-block}
        .condition>div>select{margin-right:15px;padding:0 5px;height:40px;border: 1px solid #747d8a;border-radius: .25rem;background-color:#2a323c;color:#fff;text-decoration:none;font-weight:400;font-size:16px;line-height:40px}
        .condition .fileClass{display:inline-block;width:300px;font-size:14px}
        .condition>div>button{margin-left:15px;width:80px;height:40px;color:#fff;text-align:center;font-size:16px;line-height:26px}
        table{margin-bottom:15px;text-align:center}
        table tr{line-height:30px}
        table tr td{max-width: 300px;max-height: 120px;overflow: auto}
        .rowPanel{max-height: 120px;overflow: auto;text-align: left}
        #riskEarlyWarning{display: none}

        .item {position: relative;overflow: hidden;-webkit-box-align: center;align-items: center;text-align: left;padding: 10px 15px;}
        .fenye{float:right;font-size: 14px;text-align: center;}
        .fenye div{margin: 0 10px; display: inline-block;float: left;line-height: 30px;}
        #curPage{width: 60px;line-height: 20px;text-align: center;}

        /*公共弹窗*/
        .pubMask { display: none;width: 100%;height: 100%;position: absolute;top: 0;left: 0;z-index: 8;}
        .pubPopup {display: none;width: 100%;z-index: 999;position: fixed;top: 40%;left: 0;opacity: 1;}
        .pubPopup .popCon {width:200px; max-width: 100%;background: #fff;border-radius: 8px;color: #000;font-size: 14px;margin: 0 auto; padding: 1em;text-align: center; }
    </style>
</head>
<!-- END HEAD -->
<body class="fix-header fix-sidebar card-no-border">
    <div class="preloader">
        <svg class="circular" viewBox="25 25 50 50">
            <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"/>
        </svg>
    </div>

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

            <div class="row main">
                <div class="condition">
                    <div>
                        <span>类型:</span>
                        <select id="dataType" onchange="checkType()">
                            <option value="customer" selected>客户分类</option>
                            <option value="riskwarning">高风险预警</option>
                        </select>
                    </div>
                    <div>
                        <input id="articleImageFile" name="excelFile" type="file" class="form-control fileClass">
                        <button class="btn btn-info" onclick="importFile()">导入</button>
                    </div>
                </div>

                <div style="padding-right: 20px;">
                    <table border="1px" width="1110px" bordercolor="#ccc" id="customerType">
                        <thead><tr><td>所属城市</td><td>物业公司名称</td><td>定级</td><td>描述</td></tr></thead>
                        <tbody></tbody>
                    </table>

                    <table border="1px" width="3500px" bordercolor="#ccc" id="riskEarlyWarning">
                        <thead>
                            <tr>
                                <td>客户等级</td>
                                <td>客户名称</td>
                                <td>项目所在地</td>
                                <td>归属城市</td>

                                <td>成功经理</td>
                                <td>风险描述</td>
                                <td>提出时间</td>
                                <td>解决方案</td>

                                <td>解决时间</td>
                                <td>状态</td>
                                <td>实施顾问</td>
                                <td>实施备注</td>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>

                    <div class="item fenye">
                        <div style="cursor: pointer;" onclick="prevPage()">上一页</div>
                        <div>第&nbsp;<%--<input type="text" id="curPage">--%>
                            <span id="curPage"></span> / <span id="allPage"></span>
                            &nbsp;页</div>
                        <div style="cursor: pointer;" onclick="nextPage()">下一页</div>
                    </div>
                </div>
            </div>

            <!-- footer -->
            <c:import url="common/footer.jsp"></c:import>
            <!-- End footer -->
        </div>
        <!-- End Page wrapper  -->
    </div>
    <!--公共弹窗 toast提示-->
    <div class="pubMask"></div>
    <div class="pubPopup">
        <div class="popCon"></div>
    </div>
    <!-- End Wrapper -->
    <c:import url="common/importJs2.jsp"></c:import>

    <script type="text/javascript">
    var currUrl =  "http://"+window.location.host;
    $(document).ready(function () {
        checkType();
    });

    var currentPage = 1; //当前页
    var pageSize = 10; //每页记录
    var totalCount = 0;  //总条数
    var footPage = 0;    //总页数
    var number = 0;     //开始基数
    //切换业务类型
    function checkType() {
        $("#articleImageFile").val("");
        sessionStorage.removeItem("customerList");
        sessionStorage.removeItem("riskEarlyWarning");
        currentPage = 1;
        totalCount = 0 ;
        footPage = 0;
        number = 0;
        $("#curPage").html("").text(currentPage);
        var type = $("#dataType option:selected").val();
        if(type=='customer'){
            $("#customerType tbody").html("");
            $("#customerType").show();
            $("#riskEarlyWarning").hide();
            //查客户分类
            $.ajax({
                url: currUrl+"/propertylevelselect",
                type: "get",
                dataType: "json",
                success: function (res) {
//                    console.log(res);
                    if(res && res.length>0){
                        sessionStorage.setItem("customerList",JSON.stringify(res));
                        customerListShow(res);
                    }else{
                        $("#customerType tbody").append('<tr><td colspan="4">暂无数据~</td></tr>');
                    }
                },
                error: function () {
                    console.log("接口调用失败");
                    $("#customerType tbody").append('<tr><td colspan="4">暂无数据~</td></tr>');
                }
            });
        }else{
            $("#riskEarlyWarning tbody").html("");
            $("#riskEarlyWarning").show();
            $("#customerType").hide();
            //高风险预警
            $.ajax({
                url: "./csmRisksselect",
                type: "get",
                dataType: "json",
                success: function (res) {
//                console.log(res);
                    if(res && res.length>0){
                        sessionStorage.setItem("riskEarlyWarning",JSON.stringify(res));
                        riskEarlyWarningShow(res);
                    }else{
                        $("#riskEarlyWarning tbody").append('<tr><td colspan="12">暂无数据~</td></tr>');
                    }
                },
                error: function () {
                    console.log("接口调用失败");
                    $("#riskEarlyWarning tbody").append('<tr><td colspan="12">暂无数据~</td></tr>');
                }
            });
        }
    }

    //导入excel文件
    function importFile(){
        var formData = new FormData();
        var name = $("#articleImageFile").val();
        var date = $("#startDate").val();
        formData.append("file",$("#articleImageFile")[0].files[0]);
        formData.append("dt",date);
        formData.append("name",name);//这个地方可以传递多个参数
        var type = $("#dataType option:selected").val();
        if(type=='customer') {
            if($("#articleImageFile")[0].files[0]){
                $.ajax({
                    url : currUrl+ "/propertylevelimport",
                    type : 'post',
                    async : false,
                    data :formData,
                    // 告诉jQuery不要去处理发送的数据
                    processData : false,
                    // 告诉jQuery不要去设置Content-Type请求头
                    contentType : false,
                    beforeSend:function(){
                        console.log("正在进行，请稍候");
                    },
                    success : function(res) {
//                      console.log(res);
                        if(res && res.length>0){
                            alert("导入成功");
                            $("#customerType tbody,#curPage,#allPage").html("");
                            sessionStorage.removeItem("customerList");
                            sessionStorage.setItem("customerList",JSON.stringify(res));
                            customerListShow(res);
                        }else{
                            alert("导入失败，请检查文档格式");
                        }
                    },
                    error:function() {
                        console.log("接口调用失败");
                        alert("导入失败~");
                    }
                });
            }else{
                alert("请选择要导入的文件~");
            }
        }else{
            if($("#articleImageFile")[0].files[0]){
                $.ajax({
                    url : currUrl+ "/csmRisksimport",
                    type : 'post',
                    async : false,
                    data :formData,
                    // 告诉jQuery不要去处理发送的数据
                    processData : false,
                    // 告诉jQuery不要去设置Content-Type请求头
                    contentType : false,
                    beforeSend:function(){
                        console.log("正在进行，请稍候");
                    },
                    success : function(res) {
//                        console.log(res);
                        if(res && res.length>0){
                            alert("导入成功");
                            $("#riskEarlyWarning tbody,#curPage,#allPage").html("");
                            sessionStorage.removeItem("riskEarlyWarning");
                            sessionStorage.setItem("riskEarlyWarning",JSON.stringify(res));
                            riskEarlyWarningShow(res);
                        }else{
                            alert("导入失败，请检查文档格式");
                        }
                    },
                    error:function() {
                        console.log("接口调用失败");
                        alert("导入失败~");
                    }
                });
            }else{
                alert("请选择要导入的文件~");
            }
        }
    }

    //客户分类列表展示
    function customerListShow(res) {
        var str = "";

        var listLen = 0;
        totalCount = res.length;
        footPage = Math.ceil(totalCount / pageSize);
        $("#curPage").html("").text(currentPage);
        $("#allPage").html("").text(footPage);
        if (currentPage != 1) {
            if (currentPage > footPage) {
                currentPage = null;
            }
        }
        if (currentPage != null) {
            if (totalCount <= pageSize) {
                listLen = totalCount;
            } else {
                if (currentPage < footPage) {
                    listLen = currentPage * pageSize;
                } else {
                    listLen = totalCount;
                }
            }
            for (var i = number; i < listLen; i++) {
                str += '<tr>';
                str += '<td>';
                str += res[i].city ? res[i].city : "";
                str += '<td>';
                str += res[i].property_name ? res[i].property_name : "";
                str += '</td>';
                str += '<td>';
                str += res[i].level ? res[i].level : "";
                str += '</td>';
                str += '<td>';
                str += res[i].remarks ? res[i].remarks : "";
                str += '</td>';
                str += '</tr>';
            }
            $("#customerType tbody").append(str);
        }
    }

    //风险预警列表展示
    function riskEarlyWarningShow(res) {
        var str = "";

        var listLen = 0;
        totalCount = res.length;
        footPage = Math.ceil(totalCount / pageSize);
        $("#curPage").html("").text(currentPage);
        $("#allPage").html("").text(footPage);
        if (currentPage != 1) {
            if (currentPage > footPage) {
                currentPage = null;
            }
        }
        if (currentPage != null) {
            if (totalCount <= pageSize) {
                listLen = totalCount;
            } else {
                if (currentPage < footPage) {
                    listLen = currentPage * pageSize;
                } else {
                    listLen = totalCount;
                }
            }
            for (var i = number; i < listLen; i++) {
                str += '<tr>';
                str += '<td>';
                str += res[i].client_level ? res[i].client_level : "";
                str += '</td>';
                str += '<td>';
                str += res[i].client_name ? res[i].client_name : "";
                str += '</td>';
                str += '<td>';
                str += res[i].site ? res[i].site : "";
                str += '</td>';
                str += '<td>';
                str += res[i].city ? res[i].city : "";
                str += '</td>';

                str += '<td>';
                str += res[i].manager ? res[i].manager : "";
                str += '</td>';
                str += '<td><div class="rowPanel">';
                str += res[i].risk_description ? res[i].risk_description : "";
                str += '</div></td>';
                str += '<td>';
                str += res[i].propose_time ? res[i].propose_time : "";
                str += '</td>';
                str += '<td><div class="rowPanel">';
                str += res[i].programme ? res[i].programme : "";
                str += '</div></td>';
                str += '<td>';
                str += res[i].end_time ? res[i].end_time : "";
                str += '</td>';

                str += '<td>';
                str += res[i].csm_status ? res[i].csm_status : "";
                str += '</td>';
                str += '<td>';
                str += res[i].adviser ? res[i].adviser : "";
                str += '</td>';
                str += '<td>';
                str += res[i].remarks ? res[i].remarks : "";
                str += '</td>';
                str += '</tr>';
            }
            $("#riskEarlyWarning tbody").append(str);
        }
    }

    //上一页
    function prevPage() {
        if (currentPage > 1) {
            currentPage = currentPage - 1;
            number = parseInt((currentPage - 1) * pageSize);
            var type = $("#dataType option:selected").val();
            if(type=='customer'){
               var list = JSON.parse(sessionStorage.getItem("customerList"));
               if(list){
                   $("#customerType tbody").html("");
                   customerListShow(list);
               }
            }else{
                var list = JSON.parse(sessionStorage.getItem("riskEarlyWarning"));
                if(list){
                    $("#riskEarlyWarning tbody").html("");
                    riskEarlyWarningShow(list);
                }
            }
        } else {
            popEffect("已经是第一页");
        }
    }
    //下一页
    function nextPage() {
        if (currentPage < footPage) {
            currentPage = currentPage + 1;
            number = parseInt((currentPage - 1) * pageSize);
            var type = $("#dataType option:selected").val();
            if(type=='customer'){
                var list = JSON.parse(sessionStorage.getItem("customerList"));
                if(list){
                    $("#customerType tbody").html("");
                    customerListShow(list);
                }
            }else{
                var list = JSON.parse(sessionStorage.getItem("riskEarlyWarning"));
                if(list){
                    $("#riskEarlyWarning tbody").html("");
                    riskEarlyWarningShow(list);
                }
            }
        } else {
            popEffect("已经是最后一页");
        }
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
