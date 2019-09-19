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
    <link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>

    <style>
        .main{background: RGB(29,44,51);padding: 20px 2%;overflow: auto;}
        .condition{position: relative;width: 100%;margin-bottom: 25px;}
        .condition>div{display: inline-block}
        .condition>div>.date {width: 100px;}
        .condition>div>.date,.condition>div>select{margin-right:15px;padding:0 5px;height:40px;border: 1px solid #747d8a;border-radius: .25rem;background-color:#2a323c;color:#fff;text-decoration:none;font-weight:400;font-size:16px;line-height:40px}
        .condition .fileClass{display:inline-block;width:300px;font-size:14px}
        .condition>div>button{margin-left:15px;width:80px;height:40px;color:#fff;text-align:center;font-size:16px;line-height:26px}
        table{margin-bottom:15px;text-align:center}
        table tr{line-height:30px}
        #cityTable{display: none}

        .datepicker table tr td span.disabled, .datepicker table tr td span.disabled:hover{
            color: #666;
            cursor: not-allowed;
        }

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
                        <span>月份:</span>
                        <input type="text" class="date" name="start_date" id="startDate" />
                    </div>
                    <div>
                        <span>类型:</span>
                        <select id="dataType" onchange="checkType()">
                            <option value="yetai_plan" selected>业态计划</option>
                            <option value="city_plan">城市计划</option>
                        </select>
                    </div>
                    <div>
                        <input id="articleImageFile" name="excelFile" type="file" class="form-control fileClass">
                        <button class="btn btn-info" onclick="importFile()">导入</button>
                    </div>
                    <div>
                        <button class="btn btn-info" onclick="exportFile()">导出</button>
                    </div>
                </div>

                <div style="padding-right: 20px;">
                    <table border="1px" width="3500px" bordercolor="#ccc" id="yetaiTable">
                        <thead>
                            <tr>
                                <td rowspan="2">业态</td>
                                <td rowspan="2">业态英文</td>
                                <td rowspan="2">交易类型</td>
                                <td rowspan="2">交易类型英文</td>
                                <td rowspan="2">责任人</td>

                                <td colspan="4">合计</td>

                                <td colspan="4">week1</td>
                                <td colspan="4">week2</td>
                                <td colspan="4">week3</td>
                                <td colspan="4">week4</td>
                                <td colspan="4">week5</td>
                                <td colspan="4">week6</td>
                            </tr>
                            <tr>
                                <td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>
                                <td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>
                                <td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>
                                <td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>
                                <td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>
                                <td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>
                                <td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>

                    <table border="1px" width="1110px" bordercolor="#ccc" id="cityTable">
                        <thead>
                            <tr><td>城市</td><td>城市英文</td><td>业态</td><td>业态英文</td><td>收入</td><td>边际利润</td><td>出货量</td><td>回款</td><td>GSV</td></tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>

            <!-- footer -->
            <c:import url="common/footer.jsp"></c:import>
            <!-- End footer -->
        </div>
        <!-- End Page wrapper  -->
    </div>

    <!-- End Wrapper -->
    <c:import url="common/importJs2.jsp"></c:import>

    <script src="./assets/plugins/datepicker/js/bootstrap-datepicker.js"></script>
    <script src="./assets/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    <script type="text/javascript" src="./assets/plugins/moment-with-locales.min.js"></script>
    <%--<script type="text/javascript" src="./js/jquery.table2excel.js"></script>--%>
    <script type="text/javascript">

    var currUrl =  "http://"+window.location.host;
    var tableToExcelData=[];//存放要导出的数据

    $(document).ready(function () {
        $('#startDate').val(moment().format('YYYY-MM'));
        checkType();
    });

    //日期
    $('#startDate').datepicker({
        language: "zh-CN",
        autoclose: true,
        format: 'yyyy-mm',
        todayHighlight: true,
        startView: 'months', //开始视图层，为月视图层
        maxViewMode:'years', //最大视图层，为年视图层
        minViewMode:'months', //最小视图层，为月视图层
//        endDate: moment().toDate(), //控制可选的最晚月份，为当前月
    }).on('changeDate', function (e) {
        checkType();
    });

    //切换业务类型
    function checkType() {
        $("#articleImageFile").val("");
        tableToExcelData =[]; //切换条件，清空数据
        var dt='';
        if($("#startDate").val()){
            dt = $("#startDate").val();
        }else{
            var date = new Date();
            var curMonth = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
            dt = date.getFullYear()+curMonth;
        }

        var type = $("#dataType option:selected").val();

        if(type=='city_plan'){
            $("#cityTable tbody").html("");
            $("#cityTable").show();
            $("#yetaiTable").hide();
            //查询城市计划数据
            $.ajax({
                url: currUrl+"/cityplanselectbydt?dt="+dt,
                type: "get",
                dataType: "json",
                success: function (res) {
//                    console.log(res);
                    if(res && res.length>0){
                        cityListShow(res,"cityList");
                        tableToExcelData = res;
                    }else{
                        $("#cityTable tbody").append('<tr><td colspan="9">暂无数据~</td></tr>');
                    }
                },
                error: function () {
                    console.log("接口调用失败");
                    $("#cityTable tbody").append('<tr><td colspan="9">暂无数据~</td></tr>');
                }
            });
        }else{
            $("#yetaiTable tbody").html("");
            $("#yetaiTable").show();
            $("#cityTable").hide();
            //查询业态计划数据
            $.ajax({
                url: "./yusuanluruselectbydt?dt="+dt,
                type: "get",
                dataType: "json",
                success: function (res) {
//                console.log(res);
                    if(res && res.length>0){
                        yetaiListShow(res,"yetaiList");
                        tableToExcelData = res;
                    }else{
                        $("#yetaiTable tbody").append('<tr><td colspan="33">暂无数据~</td></tr>');
                    }
                },
                error: function () {
                    console.log("接口调用失败");
                    $("#yetaiTable tbody").append('<tr><td colspan="33">暂无数据~</td></tr>');
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
        if(type=='city_plan') {
            if($("#articleImageFile")[0].files[0]){
                $.ajax({
                    url : currUrl+ "/cityplanimport",
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
                            $("#cityTable tbody").html("");
                            cityListShow(res,"cityList");
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
                    url : currUrl+ "/jiashicangyusuanimport",
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
                            $("#yetaiTable tbody").html("");
                            yetaiListShow(res,"yetaiList");
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

    /**
     * 业态计划公共列表展示
     * @param res 数据列表
     * @param string 最后拼接成的table
     * @param name 区分哪个方法调用
     */
    function yetaiListShow(res,name) {
        var str = "";
        for(var i=0;i<res.length;i++){
            if(name=="yetaiList" && res[i].dim_name=="合计"){
                str +='<tr style="background:#717171">';
            }else{
                str +='<tr>';
            }
            str += '<td>';
            str +=res[i].yewuxian_name?res[i].yewuxian_name:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].yewuxian_id?res[i].yewuxian_id:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].dim_name?res[i].dim_name:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].dim_type?res[i].dim_type:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].person_name?res[i].person_name:"";
            str += '</td>';

            str += '<td>';
            str +=res[i].all_shouru?res[i].all_shouru:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].all_lirun?res[i].all_lirun:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].all_chuhuo?res[i].all_chuhuo:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].all_huikuan?res[i].all_huikuan:"";
            str += '</td>';

            str += '<td>';
            str +=res[i].week1_shouru?res[i].week1_shouru:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week1_lirun?res[i].week1_lirun:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week1_chuhuo?res[i].week1_chuhuo:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week1_huikuan?res[i].week1_huikuan:"";
            str += '</td>';

            str += '<td>';
            str +=res[i].week2_shouru?res[i].week2_shouru:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week2_lirun?res[i].week2_lirun:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week2_chuhuo?res[i].week2_chuhuo:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week2_huikuan?res[i].week2_huikuan:"";
            str += '</td>';

            str += '<td>';
            str +=res[i].week3_shouru?res[i].week3_shouru:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week3_lirun?res[i].week3_lirun:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week3_chuhuo?res[i].week3_chuhuo:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week3_huikuan?res[i].week3_huikuan:"";
            str += '</td>';

            str += '<td>';
            str +=res[i].week4_shouru?res[i].week4_shouru:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week4_lirun?res[i].week4_lirun:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week4_chuhuo?res[i].week4_chuhuo:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week4_huikuan?res[i].week4_huikuan:"";
            str += '</td>';

            str += '<td>';
            str +=res[i].week5_shouru?res[i].week5_shouru:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week5_lirun?res[i].week5_lirun:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week5_chuhuo?res[i].week5_chuhuo:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week5_huikuan?res[i].week5_huikuan:"";
            str += '</td>';

            str += '<td>';
            str +=res[i].week6_shouru?res[i].week6_shouru:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week6_lirun?res[i].week6_lirun:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week6_chuhuo?res[i].week6_chuhuo:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].week6_huikuan?res[i].week6_huikuan:"";
            str += '</td>';

            str +='</tr>';
        }
        if(name=="yetaiList"){
            $("#yetaiTable tbody").append(str);
        }else{
            return str;
        }
    }

    /**
     * 城市计划公共列表展示
     * @param res 数据列表
     * @param string 最后拼接成的table
     * @param name 区分哪个方法调用
     */
    function cityListShow(res,name) {
        var str = "";
        for(var i=0;i<res.length;i++) {
            if(name=="cityList" && res[i].yewuxian_name=="合计"){//做展示
                str +='<tr style="background:#717171">';
            }else{//下载
                str +='<tr>';
            }
            str += '<td>';
            str += res[i].region_name?res[i].region_name:"";
            str += '<td>';
            str += res[i].region_id?res[i].region_id:"";
            str += '</td>';
            str += '<td>';
            str += res[i].yewuxian_id?res[i].yewuxian_id:"";
            str += '</td>';
            str += '<td>';
            str += res[i].yewuxian_name?res[i].yewuxian_name:"";
            str += '</td>';
            str += '<td>';
            str += res[i].all_shouru?res[i].all_shouru:"";
            str += '</td>';
            str += '<td>';
            str += res[i].all_lirun?res[i].all_lirun:"";
            str += '</td>';
            str += '<td>';
            str += res[i].all_chuhuo?res[i].all_chuhuo:"";
            str += '</td>';
            str += '<td>';
            str += res[i].all_huikuan?res[i].all_huikuan:"";
            str += '</td>';
            str += '<td>';
            str += res[i].all_gsv?res[i].all_gsv:"";
            str += '</td>';
            str += '</tr>';
        }
        if(name=="cityList"){//做展示
            $("#cityTable tbody").append(str);
        }else {//下载
           return str;
        }
    }

    function exportFile(){
        var dt='';
        if($("#startDate").val()){
            dt = $("#startDate").val();
        }else{
            var date = new Date();
            var curMonth = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
            dt = date.getFullYear()+curMonth;
        }
        var type = $("#dataType option:selected").val();
        if(type=='city_plan'){
            window.location.href = currUrl+"/cityplanexport?dt="+dt;
        }else{
            window.location.href = currUrl+"/jiashicangyusuanexport?dt="+dt;
        }
    }

    //导出
    function exportFile_old() {
        var res = tableToExcelData;
        var type = $("#dataType option:selected").val();
        //Worksheet名
        var worksheet = '',str = '';
        if(type=='city_plan'){
            worksheet="城市计划";
            str =  '<tr><td colspan="9">目标录入模板</td></tr>'+
                   '<tr><td>城市</td><td>城市英文</td><td>业态</td><td>业态英文</td><td>收入</td><td>边际利润</td><td>出货量</td><td>回款</td><td>GSV</td></tr>';
        }else{
            worksheet="业态计划";

            str = '<tr><td colspan="33">目标录入模板</td></tr>'+
                '<tr><td rowspan="2">业态</td><td rowspan="2">业态英文</td><td rowspan="2">交易类型</td><td rowspan="2">交易类型英文</td><td rowspan="2">责任人</td>' +
                ' <td colspan="4">合计</td><td colspan="4">week1</td><td colspan="4">week2</td><td colspan="4">week3</td><td colspan="4">week4</td><td colspan="4">week5</td><td colspan="4">week6</td>'+
                '</tr><tr>'+
                '<td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>'+
                '<td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>'+
                '<td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>'+
                '<td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>'+
                '<td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>'+
                '<td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>'+
                '<td>归档收入</td><td>边际利润</td><td>本月出货</td><td>本月回款</td>'+
                '</tr>';
        }

        if(res && res.length>0){
            if(type=='city_plan') {
                str += cityListShow(res);
            }else{
               str += yetaiListShow(res);
            }
            var uri = 'data:application/vnd.ms-excel;base64,';

            //下载的表格模板数据
            var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">'+
                '<head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>'+
                '<x:Name>'+worksheet+'</x:Name>'+
                '<x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->'+
                '</head>'+
                '<body><table>'+str+'</table></body></html>';
            //下载模板
            window.location.href = uri + base64(template);
        }else{
            alert("数据为空");
        }

    }
    //输出base64编码
    function base64 (s) {
        return window.btoa(unescape(encodeURIComponent(s))) ;
    }

    </script>

</body>
</html>
