<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>钦天监-大数据中心</title>
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
                    <span>年份:</span>
                    <select name="" id="yearsdate">

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

            <div style="width: 100%;overflow: auto;">
                <table border="1px" width="120%" bordercolor="#ccc" id="yetaiTable">
                    <thead>
                        <tr>
                            <td>城市</td>
                            <td>销售员</td>
                            <td>1月</td>
                            <td>2月</td>
                            <td>3月</td>
                            <td>4月</td>
                            <td>5月</td>
                            <td>6月</td>
                            <td>7月</td>
                            <td>8月</td>
                            <td>9月</td>
                            <td>10月</td>
                            <td>11月</td>
                            <td>12月</td>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
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
        obtainyears();
        //查询数据
        querydata();
    });
    //年的数据
    function obtainyears(){
        var start_year = 2019,
            end_year = (new Date()).getFullYear(),
            legs = end_year - start_year,
            ele = '<option>' + end_year + '</option>';
        for(var i=0; i<legs; i++){
            end_year--;
            ele += '<option>' + end_year + '</option>';
        }
        $('#yearsdate').append(ele);
    }

    //查询数据
    function querydata() {
        var type = $("#yearsdate option:selected").val();
        $.ajax({
            url : currUrl+ "/salestaskselectbydt",
            type : 'get',
            dataType: "json",
            data: {dt: type},
            success : function(res) {
                 yetaiListShow(res);

            },
            error:function() {

            }
        });
    }
    //导入excel文件
    function importFile(){
        var formData = new FormData();
        var type = $("#yearsdate option:selected").val();
        formData.append("dt",type);
        formData.append("file",$("#articleImageFile")[0].files[0]);
        if($("#articleImageFile")[0].files[0]){
            $.ajax({
                url : currUrl+ "/salestaskimport",
                type : 'post',
                async : false,
                data : formData,
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
                        yetaiListShow(res);
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

    /**
     * 业态计划公共列表展示
     * @param res 数据列表
     * @param string 最后拼接成的table
     * @param name 区分哪个方法调用
     */
    function yetaiListShow(res) {
        var str = "";
        for(var i=0;i<res.length;i++){
            str +='<tr>';
            str += '<td>';
            str +=res[i].city?res[i].city:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].persion?res[i].persion:"";
            str += '</td>';

            str += '<td>';
            str +=res[i].jan?res[i].jan:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].feb?res[i].feb:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].mar?res[i].mar:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].april?res[i].april:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].may?res[i].may:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].june?res[i].june:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].jule?res[i].jule:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].aug?res[i].aug:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].sept?res[i].sept:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].oct?res[i].oct:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].nov?res[i].nov:"";
            str += '</td>';
            str += '<td>';
            str +=res[i].dec?res[i].dec:"";
            str += '</td>';
            str +='</tr>';
        }
        $("#yetaiTable tbody").append(str);
    }

    function exportFile(){
        var dt=$("#yearsdate option:selected").val();
        window.location.href = currUrl+"/salestaskexport?dt="+dt;
    }

    //导出
    function exportFile_old() {
        var res = tableToExcelData;
        //Worksheet名
        var worksheet = '',str = '';

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
        if(res && res.length>0){
            str += yetaiListShow(res);
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
