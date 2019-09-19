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
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="icon" type="image/png" sizes="16x16" href="./assets/images/icon.png">
    <c:import url="common/importCss2.jsp"></c:import>
    <style>
        .pagination > li > a,
        .pagination > li > span {
            position: relative;
            float: left;
            padding: 6px 12px;
            line-height: 1.42857;
            text-decoration: none;
            color: #6c757d;
            background-color: #272c33;
            border: 1px solid #6a6f76;
            margin-left: -1px;
        }
        .pagination > .active > a,
        .pagination > .active > span,
        .pagination > .active > a:hover,
        .pagination > .active > span:hover,
        .pagination > .active > a:focus,
        .pagination > .active > span:focus {
            background-color: #383f48;
            border-color: #6a6f76;
        }

        th,td {
            color: #ffffff;
        }
        .float_right_res {
            min-height: 20px !important;
            position:absolute;
            top:150px;  //设置相对于父级的高度,以便于插件可以调整高度
        bottom:20px;
            padding-left: 65%;
        }
     /*   #myTable_filter{
            right:0;
            position:absolute;
        }*/
        /*#myTable_wrapper{
            left:0;
            position:absolute;
        }*/

      /*  #myTable_info{
            position:absolute;
        }*/
        .float_right_display {
            min-height: 20px !important;
            position:absolute;  //固定控件,防止随着滚动条一起滚动
        padding-left: 58%;//占据屏幕的58%
        padding-right: 15px;
            padding-top: 10px;
        }

        .block{
            /* padding-top: 0%;
             padding-right: 84%;*/
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
        <div class="container-fluid" style="padding: 15px;">
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
                <div class="col-md-12" style="padding: 0">
                    <div class="card">
                        <div class="card-body">
                            <div class="form-group">
                                <h4 class="card-title">${ sidebar_menu.name}</h4>
                            </div>
                            <div class="row">
                                <div class="col-md-12" style="padding: 0">
                                    <!-- BEGIN SAMPLE TABLE PORTLET-->
                                    <div class="portlet light bordered">
<%--                                        <div class="portlet-title">
                                            <form action="./midluopanrengongdatalist" method="post" class="form-inline" style="margin-bottom: 15px" id="querymidluopandata">
                                                <div class="col-xs-4">
                                                    <div class="form-group row" style="margin-left: 20px;color: #6c757d">
                                                        <label class=" control-label">指标类型:</label>
                                                        <select id="queryByKey"  class="form-control " style="width:180px;" onchange="keyChange()" >
                                                            <option value='任务-绑定户数'>任务-绑定户数</option>
                                                            <option value='取消或暂停项目'>取消或暂停项目</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-4">
                                                    <div class="form-group row " style="margin-left: 20px;color: #6c757d">
                                                        <label class=" control-label">具体日期:</label>
                                                        <input class="form-control input-inline input-medium" placeholder="请选择所属月份" value="${midluopanrengongdata.dateValue}"
                                                               type="text"  id="queryByDateValueMonth" align="right" style="width:180px;" autocomplete="off"/>
                                                        <input class="form-control input-inline input-medium" placeholder="请选择日期" value="${midluopanrengongdata.dateValue}"
                                                               type="text"  id="queryByDateValueDay" align="right" style="display:none;width:180px;" autocomplete="off"/>
                                                    </div>
                                                </div>
                                                <div class="col-xs-4">
                                                    <div class="form-group row"  style="margin-left: 20px;color: #6c757d">
                                                        <label class=" control-label " >所属城市:</label>
                                                        <select id="queryByRegionId" class="form-control input-small" align="center" style="width:180px;">
                                                            <option value='' >全部城市</option>
                                                            <option value ="1">北京</option>
                                                            <option value ="3">重庆</option>
                                                            <option value="5">成都</option>
                                                            <option value="9">上海</option>
                                                            <option value ="11">西安</option>
                                                            <option value ="14">杭州</option>
                                                            <option value="31">广州</option>
                                                            <option value="qita">其他</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                &lt;%&ndash;<div class="col-xs-3">&ndash;%&gt;
                                                    <div class="form-group" style="margin:0 auto;color: #6c757d">
                                                        <button id="queryBtn" type="submit" class="btn btn-info"  style="margin-left: 0px;">搜索</button>
                                                        <a  id="addmidluopanrengongdata">
                                                            <button  type="button" class="btn btn-success" style="margin-left: 5px" onclick = "midrengongdataadd()">添加</button>
                                                        </a>
                                                    </div>
                                                &lt;%&ndash;</div>&ndash;%&gt;
                                            </form>
                                        </div>--%>
                                        <div class="table-responsive m-t-40">
                                            <table id="myTable" class="table table-bordered table-striped" style="background-color: #212529; color: #6c757d;">
                                                <thead>
                                                <tr>
                                                    <th>日期</th>
                                                    <th>城市名称</th>
                                                    <th>社区名称</th>
                                                    <th>业务指标名称</th>
                                                    <th>指标值</th>
                                                    <th>创建时间</th>
                                                    <th>创建人</th>
                                                    <th>更新时间</th>
                                                    <th>更新人</th>
                                                    <%--<th>操作</th>--%>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${midluopanrengongdatalists}" var="midluopanrengongdata">
                                                    <tr>
                                                        <td>${midluopanrengongdata.dateValue}</td>
                                                        <td>${midluopanrengongdata.regionName}</td>
                                                        <td>${midluopanrengongdata.projectName}</td>
                                                        <td>${midluopanrengongdata.key}</td>
                                                        <td>${midluopanrengongdata.value}</td>
                                                        <td><fmt:formatDate value="${midluopanrengongdata.createTime}"
                                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        <td>${midluopanrengongdata.createUser}</td>
                                                        <td><fmt:formatDate value="${midluopanrengongdata.updateTime}"
                                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                        <td>${midluopanrengongdata.updateUser}</td>
                                                        <%--<td><div><a href="./midluopanrengongdatamanager?id=${midluopanrengongdata.id}" class="btn-success">修改</a> </div><div> <a
                                                                href="midluopanrengongdatadelete?id=${midluopanrengongdata.id}&key=${midluopanrengongdata.key}" class="btn-danger" >删除</a></div>
                                                        </td>--%>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <!-- END SAMPLE TABLE PORTLET-->
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
</div>
    <c:import url="common/importJs2.jsp"></c:import>

    <link rel="stylesheet" type="text/css" href="./js/datetimepicker/bootstrap-datetimepicker.css" />
    <script src="./js/datetimepicker/bootstrap-datetimepicker.js"></script>
    <script src="./js/datetimepicker/bootstrap-datetimepicker.min.js"></script>
    <script src="./js/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
    <script language="JavaScript">
        $(document).ready(function() {
            $('#myTable').DataTable({
                "order": [
                    [5, 'desc']
                ],
            });
        });
        //初始化月类型搜索框
        $('#queryByDateValueMonth').datetimepicker({
            format: 'yyyy-mm',
            autoclose: true,
            todayBtn: true,
            startView: 'year',
            minView:'year',
            maxView:'decade',
            language:  'zh-CN',
        });
        //初始化日类型初始框
        $('#queryByDateValueDay').datetimepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            minView:'2',
            maxView:'decade',
            language:  'zh-CN',

        });
        //日期框初始化
        if("取消或暂停项目"==$("#queryByKey").val()){
            $("#queryByDateValueMonth").hide();
        }else{
            $("#queryByDateValueDay").hide();
        }
        //指标类型发生变化
        function keyChange(){
            var key = document.getElementById("queryByKey");
            var keyValue=key.options[key.selectedIndex].value;
            checkDateValue(keyValue,true);
        }

        /**
         * 控制日期控件展示那个,并清空所选值
         * @param value
         */
        function checkDateValue(value,is_clear){
            //判断dateValue是否有值,如果有值,赋值
            if("取消或暂停项目"==value){
                $("#queryByDateValueDay").show();
                $("#queryByDateValueMonth").hide();
                if(is_clear){
                    $("#queryByDateValueDay").val(null);
                }
            }else if("任务-绑定户数"==value){
                $("#queryByDateValueMonth").show();
                $("#queryByDateValueDay").hide();
                if(is_clear){
                    $("#queryByDateValueMonth").val(null);
                }
            }
        }
        //添加时,将数据类型带入
        function midrengongdataadd() {
            var key = document.getElementById("queryByKey");
            var keyValue =  key.options[key.selectedIndex].value;
            /*<option value='bind_task_hushu'>任务-绑定户数</option>
            <option value='wyy_cancel_project_num'>取消或暂停项目</option>*/
            //添加时将日期传过去,如果日期不为空,则让其选中
            if("任务-绑定户数"==keyValue){
                var monthTemp =  $("#queryByDateValueMonth").val();
                if(monthTemp==null||monthTemp==undefined||monthTemp==""){
                    $("#addmidluopanrengongdata").attr("href","./midluopanrengongdataadd?key=bind_task_hushu&keyText="+keyValue);
                }else{
                    $("#addmidluopanrengongdata").attr("href","./midluopanrengongdataadd?key=bind_task_hushu&keyText="+keyValue+"&dateValue="+monthTemp);
                }
            }else{
                var dateTemp =  $("#queryByDateValueDay").val();
                if(dateTemp==null||dateTemp==undefined||dateTemp==""){
                    $("#addmidluopanrengongdata").attr("href","./midluopanrengongdataadd?key=wyy_cancel_project_num&keyText="+keyValue);
                }else{
                    $("#addmidluopanrengongdata").attr("href","./midluopanrengongdataadd?key=wyy_cancel_project_num&keyText="+keyValue+"&dateValue="+dateTemp);
                }
            }
        }
        $(function(){
            //查询按钮点击时
            $("#queryBtn").click(function(){
                var midluopanrengongdata = {};
                //如果月份不为空.拿到月份,如果城市不为空,拿到城市,并且还有key的值
                var key = document.getElementById("queryByKey");
                var keyText=key.options[key.selectedIndex].text;
                var keyValue=key.options[key.selectedIndex].value;
                midluopanrengongdata.key = keyText;
                var region=document.getElementById("queryByRegionId");
                //select标签获取的值其实是一个数组--a.options[]; 然后，选定项的下标是--a.selectedIndex
                var regionId=region.options[region.selectedIndex].value;
                if(''!=regionId){
                    midluopanrengongdata.regionId = regionId;
                }
                //给日期赋值
                var month = $("#queryByDateValueMonth").val();
                if(month==null||month==undefined||month==""){
                    var day = $("#queryByDateValueDay").val();
                    if(day==null||day==undefined||day==""){
                        midluopanrengongdata.dateValue = null;
                    }else{
                        midluopanrengongdata.dateValue = day;
                    }
                }else{
                    midluopanrengongdata.dateValue = month;
                }
                if(undefined==midluopanrengongdata.dateValue){
                    midluopanrengongdata.dateValue = null;
                }
                if(undefined==regionId){
                    midluopanrengongdata.regionId = null;
                }
                $("#queryByKey").attr({name:"key",value:keyText});
                $("#queryByRegionId").attr({name:"regionId",value:midluopanrengongdata.regionId});
                //$("#queryByRegionId").attr({name:"regionName",value:region.options[region.selectedIndex].text});
                if("wyy_cancel_project_num"==keyValue ||"取消或暂停项目"==keyValue){
                    $("#queryByDateValueDay").attr({name:"dateValue",value:midluopanrengongdata.dateValue});
                }else{
                    $("#queryByDateValueMonth").attr({name:"dateValue",value:midluopanrengongdata.dateValue});
                }
            });
            //点击搜索之后上一次选择的城市和操作类型保持选中状态
            var keyTemp = "${midluopanrengongdata.key}";
            var regionIdTemp = "${midluopanrengongdata.regionId}";
            if(""!=keyTemp){
                //设置日期框样式
                checkDateValue(keyTemp,false);
                $("#queryByKey").val(keyTemp);
            }
            if(""!=regionIdTemp){
                //设置选中城市id
                $("#queryByRegionId").val(regionIdTemp);
            }
        })
    </script>
</body>
</html>
