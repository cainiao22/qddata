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
    <link href="./assets/plugins/select2/css/select2.css" rel="stylesheet" type="text/css"/>
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

        th {
            color: #93a2a9;
        }
        td {
            color: black;
        }
        body,.select2-container--default,.select2-selection--single,.select2-selection__rendered,.select2-container--default .select2-selection--single{
            background-color: white;
        }
        .form-control,.form-control:focus,.form-control[readonly]{
            background: white;
            color: black;
        }

        .control-label{
            color:black;
        }

        .dropdown-menu{
            /*日期框背景色调整*/
            background-color: #e1eaf5;
        }
        .block{
            /* padding-top: 0%;
             padding-right: 84%;*/
        }
        .select2-dropdown,.select2-container--default .select2-selection--single .select2-selection__rendered {
            background-color: #ffffff;
            color: black;
        }
        .select2-container .select2-selection--single{height: 38px;}
    </style>
</head>
<!-- END HEAD -->
<body class="fix-header fix-sidebar card-no-border">
<div class="preloader">
    <svg class="circular" viewBox="25 25 50 50">
        <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"/>
    </svg>
</div>
<div id="main-wrapper" style="overflow: hidden">
    <%--<c:import url="common/header2.jsp"></c:import>--%>
    <!-- End Topbar header -->
  <%--  <aside class="left-sidebar">
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
    </aside>--%>
    <!-- Page wrapper  -->
    <%--<div class="page-wrapper">--%>
        <!-- Container fluid  -->
        <div class="container-fluid" style="padding: 15px;">
            <!-- Bread crumb and right sidebar toggle -->
          <%--  <div class="row page-titles">
                <div class="col-md-6 col-8 align-self-center">
                    <h3 class="text-themecolor m-b-0 m-t-0">${ sidebar_menu.parentName}</h3>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="javascript:void(0)">${ sidebar_menu.parentName}</a></li>
                        <li class="breadcrumb-item active">${ sidebar_menu.name} </li>
                    </ol>
                </div>
            </div>--%>
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- Start Page Content -->
            <!-- Row -->
            <div class="row">
                <div class="col-md-12" style="padding: 0">
                    <div class="card">
                        <div class="card-body" style="background: white">
                            <div class="form-group">
                                <h4 class="card-title">${ sidebar_menu.name}</h4>
                            </div>
                            <div class="row">
                                <div class="col-md-12" style="padding: 0">
                                    <!-- BEGIN SAMPLE TABLE PORTLET-->
                                    <div class="portlet light bordered">
                                        <div class="portlet-title">
                                            <form action="./midrengongdataquery" method="post" class="form-inline" style="margin-bottom: 15px" id="querymidluopandata" onsubmit="return checkQuery()">
                                                <div class="col-md-4" style="margin: 0 15px;">
                                                    <div class="form-group row " style="color: #6c757d" id ="radiokey" >
                                                        <input type="radio" class="checkkey" id="keyradio1" name="key"  value="任务-绑定户数" style="margin-top: 4px;margin-right:1px;">
                                                        <label for="keyradio1" style="color: #6c757d">任务-绑定户数</label>
                                                        <input type="radio" class="checkkey" id="keyradio3" name="key" value="物业云实施状态" style="margin-top: 5px;margin-left: 20px;margin-right:1px;">
                                                        <label for="keyradio3" style="color: #6c757d">物业云实施状态</label>
                                                    </div>
                                                </div>
                                                <div class="col-md-6" style="margin:0 20px;">
                                                    <div class="form-group row " style="color: #6c757d" id="queryDate">
                                                    </div>
                                                </div>
                                                <div style="margin:0 auto;color: #6c757d;display: none">
                                                    <button id="queryBtn" type="submit" class="btn btn-info"  style="margin-left: 0px;"></button>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="alert alert-success" style="display:none;" id="message">
                                            <a href="#" class="close" data-dismiss="alert">
                                                &times;
                                            </a>
                                            <strong> <label id ="addResult"></label></strong>。
                                        </div>
                                        <div class="table-responsive m-t-40" id="addTable" style="overflow-x: hidden">
                                               <form action="./midrengongdataadd" id="addTableValue" method="post"  onsubmit="return check()" >
                                                   <div style="position: relative;margin: 0 auto;text-align: center;">
                                                       <input  type="submit" value="保存数据" class="btn btn-info">
                                                   </div>
<%--
                                                   <div class="alert alert-success" style="display:none;" id="message2"></div>
--%>
                                               </form>
                                        </div>
                                    </div>
                                    <div id="showTable">
                                        <form action="./midrengongdataadd"  method="post">
                                            <div class='row'>
                                                <div class='col-md-12' style="position: relative;width: 90%;margin: 0 5%;">
                                                    <div class='form-group row' style="margin-bottom:10px;" >
                                                        <label class='control-label text-left col-md-2'>日期:</label>
                                                        <div class='col-md-9'>
                                                            <input type='text' class='form-control' placeholder='请选择日期'  id="dateValueadd" name='dateValue'  required='true' autocomplete='off' readonly value="${midluopanrengongdata.dateValue}">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <c:forEach items="${midluopanrengongdatalists}" var="midluopanrengongdata" varStatus="cou">
                                                <div class="row">
                                                    <div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">
                                                        <div class="form-group row" style="margin-bottom:10px;" >
                                                            <label class="control-label text-left col-md-2">${fn:substring(midluopanrengongdata.regionName,0,2)}:</label>
                                                            <div class="col-md-9">
                                                                <input type="hidden" name="mlrds[${cou.index}].id" value="${midluopanrengongdata.id}">
                                                                <input type="hidden" name="mlrds[${cou.index}].regionId" value="${midluopanrengongdata.regionId}"/>
                                                                <input type="hidden" name="mlrds[${cou.index}].regionName"  value="${midluopanrengongdata.regionName}"/>
                                                                <input type="hidden" class="key" name="mlrds[${cou.index}].key" value="${midluopanrengongdata.key}" />
                                                                <input type="hidden" name="mlrds[${cou.index}].dateValue" class="form-control monthValue" value="${midluopanrengongdata.dateValue}"/>
                                                                <input type="number" name="mlrds[${cou.index}].value" class="form-control" min="0" max="999999999999" required="true" value="${midluopanrengongdata.value}"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                            <div style="position: relative;margin: 0 auto;text-align: center;">
                                                <input type="submit" value="保存数据" class="btn btn-info" <%--style="margin-left: 45px"--%> />
                                            </div>
                                            <div class="alert alert-success" style="display:none;" id="message2"></div>
                                        </form>
                                    </div>
                                    <div id="showTable2">
                                        <form action="./midrengongdataaddwuyeyun"  method="post">
                                            <div class='row'>
                                                <div class='col-md-12' style="position: relative;width: 90%;margin: 0 5%;">
                                                    <div class='form-group row' style="margin-bottom:10px;" >
                                                        <label class='control-label text-left col-md-2'>日期:</label>
                                                        <div class='col-md-9'>
                                                            <input type='text' class='form-control' id='dateValuewuyeyun' name='dateValue'  required='true' autocomplete='off' readonly='true'>
                                                            <input type='hidden' name ='id' id='midluopanrengongdataid'/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class='row'>
                                                <div class='col-md-12' style="position: relative;width: 90%;margin: 0 5%;">
                                                    <div class='form-group row' style="margin-bottom:10px;">
                                                        <label class='control-label text-left col-md-2'>城市:</label>
                                                        <div class='col-md-9'>
                                                            <input type='text' id='regionNamewuyeyun' name ='regionName'  class='form-control' readonly='true'/>
                                                            <input type='hidden' id='regionIdwuyeyun' name ='regionId' />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class='row'>
                                                <div class='col-md-12' style="position: relative;width: 90%;margin: 0 5%;">
                                                    <div class='form-group row' style="margin-bottom:10px;">
                                                        <label class='control-label text-left col-md-2'>社区名称:</label>
                                                        <div class='col-md-9'>
                                                            <input type='text' id='projectNamewuyeyun' name ='projectName' class='form-control' readonly='true'/>
                                                            <input type='hidden' id='projectIdwuyeyun' name ='projectId' />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">
                                                    <div class="form-group row" style="margin-bottom:10px;">
                                                        <label class="control-label text-left col-md-2">物业公司:</label>
                                                        <div class="col-md-9">
                                                            <input type="text" id="propertyinfoNamewuyeyun" name="propertyinfoName"  class="form-control" readonly="true" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">
                                                    <div class="form-group row" style="margin-bottom:10px;">
                                                        <label class="control-label text-left col-md-2">物业云实施状态:</label>
                                                        <div class="col-md-9">
                                                            <select class="form-control " id="statevaluewuyeyun" name="mlrds[0].value" required="true" ><option value="0">正常</option><option value="-1">取消</option><option value="1">暂停</option></select>
                                                            <input type="hidden" name="mlrds[0].key" value="物业云实施状态" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">
                                                    <div class="form-group row" style="margin-bottom:10px;">
                                                        <label class="control-label text-left col-md-2">物业员工数量:</label>
                                                        <div class="col-md-9">
                                                            <input type="number" class="form-control" id="yuangongvaluewuyeyun" name="mlrds[1].value"  min="0" max="999999999999" required="true" />
                                                            <input type="hidden" name="mlrds[1].key" value="员工潜在使用系统的人员数量" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">
                                                    <div class="form-group row" style="margin-bottom:10px;">
                                                        <label class="control-label text-left col-md-2">注册员工数量:</label>
                                                        <div class="col-md-9">
                                                            <input type="number" class="form-control" id="zhucevaluewuyeyun" name="mlrds[2].value"  min="0" max="999999999999" required="true" />
                                                            <input type="hidden" name="mlrds[2].key" value="员工注册数量" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div style='position: relative;margin: 0 auto;text-align: center;'><input  type='submit' value='保存数据' class='btn btn-info' ></div><div class='alert alert-success' style='display:none;' id='message2'></div>
                                            <%--<div align="center">
                                                <input id="submitwuyeyun"  type="submit" value="修改数据" class="btn btn-success" style="margin-left: 45px">
                                                <a href="./midLuopanRengongData?key=物业云实施状态"  class="btn btn-info" style="margin-left: 45px">返&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;回</a>
                                            </div>--%>
                                        </form>
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
            <%--<c:import url="common/footer.jsp"></c:import>--%>
            <!-- End footer -->
        </div>
        <!-- End Page wrapper  -->
    <%--</div>--%>
    <!-- End Wrapper -->
</div>
    <c:import url="common/importJs2.jsp"></c:import>
    <link rel="stylesheet" type="text/css" href="./js/datetimepicker/bootstrap-datetimepicker.css" />
    <script src="./js/datetimepicker/bootstrap-datetimepicker.js"></script>
    <script src="./js/datetimepicker/bootstrap-datetimepicker.min.js"></script>
    <script src="./js/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="./assets/plugins/select2/js/select2.js"></script>
    <!--引入select2中文包-->
    <script src="./assets/plugins/select2/js/zh-CN.js"></script>
    <script language="JavaScript">
        $("tr:odd").css("background-color", "white");
        $("tr:even").css("background-color", "white");

     var tablemonth =   '<div class="row"> '+
            '<div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">'+
            '<div class="form-group row" style="margin-bottom:10px;" >'+
         '<label class="control-label text-left col-md-2">日期:</label>'+
         '<div class="col-md-9">'+
         ' <input type="text" class="form-control dateValue" id="dateValueadd" name="dateValue"  required="true" autocomplete="off" readonly="true">'+
         '</div>'+
         '</div>'+
         ' </div>'+
         ' </div>'+
         '<div class="row">'+
         '<div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">'+
         '<div class="form-group row" style="margin-bottom:10px;" >'+
         ' <label class="control-label text-left col-md-2">北京:</label>'+
         '<div class="col-md-9">'+
         '<input type="hidden" name="mlrds[0].regionId" value="1" />'+
         '<input type="hidden" name="mlrds[0].regionName" value="北京市" />'+
         '<input type="hidden" class="key" name="mlrds[0].key" />'+
         '<input type="hidden" name="mlrds[0].dateValue" class="form-control monthValue"/>'+
         '<input type="number" name="mlrds[0].value" class="form-control" min="0" max="999999999999" required="true" />'+
         '</div>'+
         '</div>'+
         '</div>'+
         '</div>'+
         '<div class="row">'+
         '<div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">'+
         '<div class="form-group row" style="margin-bottom:10px;" >'+
         '<label class="control-label text-left col-md-2">重庆:</label>'+
         '<div class="col-md-9">'+
         '<input type="hidden" name="mlrds[1].regionId" value="3" />'+
         '<input type="hidden" name="mlrds[1].regionName" value="重庆市" />'+
         '<input type="hidden" class="key" name="mlrds[1].key" />'+
         '<input type="hidden" name="mlrds[1].dateValue" class="form-control monthValue"/>'+
         '<input type="number" name="mlrds[1].value" class="form-control" min="0" max="999999999999" required="true" />'+
         '</div>'+
         '</div>'+
         '</div>'+
         '</div>'+
         '<div class="row">'+
         '<div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">'+
         '<div class="form-group row" style="margin-bottom:10px;" >'+
         '<label class="control-label text-left col-md-2">成都:</label>'+
         '<div class="col-md-9">'+
         '<input type="hidden" name="mlrds[2].regionId" value="5" />'+
         '<input type="hidden" name="mlrds[2].regionName" value="成都市" />'+
         '<input type="hidden" class="key" name="mlrds[2].key" />'+
         '<input type="hidden" name="mlrds[2].dateValue" class="form-control monthValue"/>'+
         '<input type="number" name="mlrds[2].value" class="form-control" min="0" max="999999999999" required="true" />'+
         '</div>'+
         '</div>'+
         '</div>'+
         '</div>'+
         '<div class="row">'+
         '<div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">'+
         '<div class="form-group row" style="margin-bottom:10px;" >'+
         '<label class="control-label text-left col-md-2">上海:</label>'+
         '<div class="col-md-9">'+
         '<input type="hidden" name="mlrds[3].regionId" value="9" />'+
         '<input type="hidden" name="mlrds[3].regionName" value="上海市" />'+
         '<input type="hidden" class="key" name="mlrds[3].key" />'+
         '<input type="hidden" name="mlrds[3].dateValue" class="form-control monthValue"/>'+
         '<input type="number" name="mlrds[3].value" class="form-control" min="0" max="999999999999" required="true" />'+
         '</div>'+
         '</div>'+
         '</div>'+
         '</div>'+
         '<div class="row">'+
         '<div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">'+
         '<div class="form-group row" style="margin-bottom:10px;" >'+
         '<label class="control-label text-left col-md-2">西安:</label>'+
         '<div class="col-md-9">'+
         '<input type="hidden" name="mlrds[4].regionId" value="11" />'+
         '<input type="hidden" name="mlrds[4].regionName" value="西安市" />'+
         '<input type="hidden" class="key" name="mlrds[4].key" />'+
         '<input type="hidden" name="mlrds[4].dateValue" class="form-control monthValue"/>'+
         '<input type="number" name="mlrds[4].value" class="form-control" min="0" max="999999999999" required="true" />'+
         '</div>'+
         '</div>'+
         '</div>'+
        '</div>'+
         '<div class="row">'+
         '<div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">'+
         '<div class="form-group row" style="margin-bottom:10px;" >'+
         '<label class="control-label text-left col-md-2">杭州:</label>'+
         '<div class="col-md-9">'+
         '<input type="hidden" name="mlrds[5].regionId" value="14" />'+
         '<input type="hidden" name="mlrds[5].regionName" value="杭州市" />'+
         '<input type="hidden" class="key" name="mlrds[5].key" />'+
         '<input type="hidden" name="mlrds[5].dateValue" class="form-control monthValue"/>'+
         '<input type="number" name="mlrds[5].value" class="form-control" min="0" max="999999999999" required="true" />'+
         '</div>'+
         '</div>'+
         '</div>'+
         '</div>'+
         '<div class="row">'+
         '<div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">'+
         '<div class="form-group row" style="margin-bottom:10px;" >'+
         '<label class="control-label text-left col-md-2">广州:</label>'+
         '<div class="col-md-9">'+
         '<input type="hidden" name="mlrds[6].regionId" value="31" />'+
         '<input type="hidden" name="mlrds[6].regionName" value="广州市" />'+
         '<input type="hidden" class="key" name="mlrds[6].key" />'+
         '<input type="hidden" name="mlrds[6].dateValue" class="form-control monthValue"/>'+
         '<input type="number" name="mlrds[6].value" class="form-control" min="0" max="999999999999" required="true" />'+
         '</div>'+
         '</div>'+
         '</div>'+
         '</div>'+
         '<div class="row">'+
         '<div class="col-md-12" style="position: relative;width: 90%;margin: 0 5%;">'+
         '<div class="form-group row" style="margin-bottom:10px;" >'+
         '<label class="control-label text-left col-md-2">其它:</label>'+
         '<div class="col-md-9">'+
         '<input type="hidden" name="mlrds[7].regionId" value="qita" />'+
            '<input type="hidden" name="mlrds[7].regionName" value="其它" />'+
        '<input type="hidden" class="key" name="mlrds[7].key" />'+
        '<input type="hidden" name="mlrds[7].dateValue" class="form-control monthValue"/>'+
        '<input type="number" name="mlrds[7].value" class="form-control" min="0" max="999999999999" required="true" />'+
        '</div>'+
            '</div>'+
        '</div>'+
         '</div>'+
         '<div style="position: relative;margin: 0 auto;text-align: center;">'+
         ' <input type="submit" value="保存数据" class="btn btn-info" style="margin-left: 45px" />'+
         ' </div>'+
         '<div class="alert alert-success" style="display:none;" id="message2"></div> ';


        var tableday =  "<table  class='col-md-12 table table-bordered table-striped ' style='background-color: white;text-align:center'><thead> <tr><th>城市</th><th><span>取消或暂停项目数量(个)</span></th><th>日期</th></tr> </thead> <tbody> <tr> <td> <input type='hidden' name='mlrds[0].regionId' value='1'/> <input type='hidden' name='mlrds[0].regionName' value='北京市'/>北京 <input class='key' name='mlrds[0].key' type='hidden'/> </td> <td> <input type='number' name='mlrds[0].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td> <input type='text' name='mlrds[0].dateValue' class='form-control dateValue' placeholder='请选择日期' required='true' autocomplete='off' readonly> </td> </tr> <tr> <td><input type='hidden' name='mlrds[1].regionId' value='3'/> <input type='hidden' name='mlrds[1].regionName' value='重庆市'/>重庆 <input class='key' name='mlrds[1].key' type='hidden'/> </td> <td> <input type='number' name='mlrds[1].value' class='form-control'  min='0' max='999999999999' required='true'> </td> <td> <input type='text' name='mlrds[1].dateValue' class='form-control dateValue' placeholder='请选择日期' required='true' autocomplete='off' readonly> </td> </tr> <tr> <td><input type='hidden' name='mlrds[2].regionId' value='5'/> <input type='hidden' name='mlrds[2].regionName' value='成都市'/>成都 <input class='key' name='mlrds[2].key' type='hidden'/> </td> <td><input type='number' name='mlrds[2].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[2].dateValue' class='form-control dateValue' placeholder='请选择日期' required='true' autocomplete='off' readonly></td> </tr> <tr> <td><input type='hidden' name='mlrds[3].regionId' value='9'/> <input type='hidden' name='mlrds[3].regionName' value='上海市'/>上海 <input class='key' name='mlrds[3].key' type='hidden'/> </td> <td><input type='number' name='mlrds[3].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[3].dateValue' class='form-control dateValue' placeholder='请选择日期' required='true' autocomplete='off' readonly></td> </tr> <tr> <td><input type='hidden' name='mlrds[4].regionId' value='11'/> <input type='hidden' name='mlrds[4].regionName' value='西安市'/>西安 <input class='key' name='mlrds[4].key' type='hidden'/> </td> <td><input type='number' name='mlrds[4].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[4].dateValue' class='form-control dateValue' placeholder='请选择日期' required='true' autocomplete='off' readonly></td> </tr> <tr> <td><input type='hidden' name='mlrds[5].regionId' value='14'/> <input type='hidden' name='mlrds[5].regionName' value='杭州市'/>杭州 <input class='key' name='mlrds[5].key' type='hidden'/> </td> <td><input type='number' name='mlrds[5].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[5].dateValue' class='form-control dateValue' placeholder='请选择日期' required='true' autocomplete='off' readonly></td> </tr> <tr> <td><input type='hidden' name='mlrds[6].regionId' value='31'/> <input type='hidden' name='mlrds[6].regionName' value='广州市'/>广州 <input class='key' name='mlrds[6].key' type='hidden'/> </td> <td><input type='number' name='mlrds[6].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[6].dateValue' class='form-control dateValue' placeholder='请选择日期' required='true' autocomplete='off' readonly></td> </tr> <tr> <td><input type='hidden' name='mlrds[7].regionId' value='qita'/> <input type='hidden' name='mlrds[7].regionName' value='其它'/>其它 <input class='key' name='mlrds[7].key' type='hidden'/> </td> <td><input type='number' name='mlrds[7].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[7].dateValue' class='form-control dateValue' placeholder='请选择日期' required='true' autocomplete='off' readonly></td> </tr> </tbody></table><div style='position: relative;margin: 0 auto;text-align: center;'><input  type='submit' value='保存数据' class='btn btn-info'></div><div class='alert alert-success' style='display:none;' id='message2'></div>";
        //var tablemonth =  "<table  class='col-md-12 table table-bordered table-striped ' style='background-color: white;text-align:center'><thead> <tr><th>城市</th><th><span>月绑定任务数量(户)</span></th><th>月份</th></tr> </thead> <tbody> <tr> <td> <input type='hidden' name='mlrds[0].regionId' value='1'/> <input type='hidden' name='mlrds[0].regionName' value='北京市'/> 北京 <input class='key' name='mlrds[0].key' type='hidden'/> </td> <td> <input type='number' name='mlrds[0].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td> <input type='text' name='mlrds[0].dateValue' class='form-control monthValue' placeholder='请选择月份' required='true' autocomplete='off' readonly> </td> </tr> <tr> <td><input type='hidden' name='mlrds[1].regionId' value='3'/> <input type='hidden' name='mlrds[1].regionName' value='重庆市'/> 重庆 <input class='key' name='mlrds[1].key' type='hidden'/> </td> <td> <input type='number' name='mlrds[1].value' class='form-control'  min='0' max='999999999999' required='true'> </td> <td> <input type='text' name='mlrds[1].dateValue' class='form-control monthValue' placeholder='请选择月份' required='true' autocomplete='off' readonly> </td> </tr> <tr> <td><input type='hidden' name='mlrds[2].regionId' value='5'/> <input type='hidden' name='mlrds[2].regionName' value='成都市'/> 成都 <input class='key' name='mlrds[2].key' type='hidden'/> </td> <td><input type='number' name='mlrds[2].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[2].dateValue' class='form-control monthValue' placeholder='请选择月份' required='true' autocomplete='off' readonly></td> </tr> <tr> <td><input type='hidden' name='mlrds[3].regionId' value='9'/> <input type='hidden' name='mlrds[3].regionName' value='上海市'/> 上海 <input class='key' name='mlrds[3].key' type='hidden'/> </td> <td><input type='number' name='mlrds[3].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[3].dateValue' class='form-control monthValue' placeholder='请选择月份' required='true' autocomplete='off' readonly></td> </tr> <tr> <td><input type='hidden' name='mlrds[4].regionId' value='11'/> <input type='hidden' name='mlrds[4].regionName' value='西安市'/> 西安 <input class='key' name='mlrds[4].key' type='hidden'/> </td> <td><input type='number' name='mlrds[4].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[4].dateValue' class='form-control monthValue' placeholder='请选择月份' required='true' autocomplete='off' readonly></td> </tr> <tr> <td><input type='hidden' name='mlrds[5].regionId' value='14'/> <input type='hidden' name='mlrds[5].regionName' value='杭州市'/> 杭州 <input class='key' name='mlrds[5].key' type='hidden'/> </td> <td><input type='number' name='mlrds[5].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[5].dateValue' class='form-control monthValue' placeholder='请选择月份' required='true' autocomplete='off' readonly></td> </tr> <tr> <td><input type='hidden' name='mlrds[6].regionId' value='31'/> <input type='hidden' name='mlrds[6].regionName' value='广州市'/> 广州 <input class='key' name='mlrds[6].key' type='hidden'/> </td> <td><input type='number' name='mlrds[6].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[6].dateValue' class='form-control monthValue' placeholder='请选择月份' required='true' autocomplete='off' readonly></td> </tr> <tr> <td><input type='hidden' name='mlrds[7].regionId' value='qita'/> <input type='hidden' name='mlrds[7].regionName' value='其它'/> 其它 <input class='key' name='mlrds[7].key' type='hidden'/> </td> <td><input type='number' name='mlrds[7].value' class='form-control'  min='0' max='999999999999' required='true'></td> <td><input type='text' name='mlrds[7].dateValue' class='form-control monthValue' placeholder='请选择月份' required='true' autocomplete='off' readonly></td> </tr> </tbody></table><div style='position: relative;margin: 0 auto;text-align: center;'><input  type='submit' value='保存数据' class='btn btn-info' style='margin-left: 45px'></div><div class='alert alert-success' style='display:none;' id='message2'></div>";
        var tableqita = "<div class='row'> <div class='col-md-12 ' style='position: relative;width: 90%;margin: 0 5%;'> <div class='form-group row' style='margin-bottom:10px;'> <label class='control-label text-left col-md-2'>日期:</label> <div class='col-md-9'> <input type='text' class='form-control dateValue' placeholder='请选择日期' id='adddateValuewuyeyun' name='dateValue' autocomplete='off' readonly /> <input type='hidden' name ='id' id='midluopanrengongdataid'/> </div> </div> </div> </div> <div class='row'> <div class='col-md-12 ' style='position: relative;width: 90%;margin: 0 5%;'> <div class='form-group row' style='margin-bottom:10px;'> <label class='control-label text-left col-md-2'>城市:</label> <div class='col-md-9'> <input type='text' id='regionName' name ='regionName' class='form-control' readonly='true' required='true' placeholder='请从社区下拉列表选择城市'/> <input type='hidden' id='regionId' name ='regionId'/> </div> </div> </div> </div><div class='row'> <div class='col-md-12' style='position: relative;width: 90%;margin: 0 5%;'> <div class='form-group row' style='margin-bottom:10px;'> <label class='control-label text-left col-md-2'>社区名称:</label> <div class='col-md-9'> <input type='text' id='projectName' name ='projectName' class='form-control' readonly='true' required='true' placeholder='请从社区下拉列表选择社区'/> <input type='hidden' id='projectId' name ='projectId'/> </div> </div> </div> </div> <div class='row'> <div class='col-md-12' style='position: relative;width: 90%;margin: 0 5%;'> <div class='form-group row' style='margin-bottom:10px;'> <label class='control-label text-left col-md-2'>物业公司:</label> <div class='col-md-9'> <input type='text' id='propertyinfoName' name ='propertyinfoName' class='form-control' readonly='true' required='true' placeholder='请从社区下拉列表选择物业公司'/> </div> </div> </div> </div><div class='row'> <div class='col-md-12' style='position: relative;width: 90%;margin: 0 5%;'> <div class='form-group row' style='margin-bottom:10px;'> <label class='control-label text-left col-md-2' >物业云实施状态:</label> <div class='col-md-9'> <select  class='form-control ' name='mlrds[0].value' ><option value='0'>正常</option><option value='-1'>取消</option><option value='1'>暂停</option></select> <input type='hidden'  name ='mlrds[0].key' value='物业云实施状态'/></div> </div> </div> </div><div class='row'> <div class='col-md-12' style='position: relative;width: 90%;margin: 0 5%;'> <div class='form-group row' style='margin-bottom:10px;'> <label class='control-label text-left col-md-2' >物业员工数量:</label> <div class='col-md-9' > <input type='number' class='form-control'  name='mlrds[1].value' min='0' max='999999999999' required='true' placeholder='请输入物业员工数量'><input type='hidden'  name ='mlrds[1].key' value='员工潜在使用系统的人员数量'/> </div> </div> </div> </div><div class='row'> <div class='col-md-12' style='position: relative;width: 90%;margin: 0 5%;'> <div class='form-group row' style='margin-bottom:10px;'> <label class='control-label text-left col-md-2' >注册员工数量:</label> <div class='col-md-9' > <input type='number' class='form-control'  name='mlrds[2].value' min='0' max='999999999999' required='true' placeholder='请输入注册员工数量'><input type='hidden'  name ='mlrds[2].key' value='员工注册数量'/> </div> </div> </div> </div> <div style='position: relative;margin: 0 auto;text-align: center;'><input  type='submit' value='保存数据' class='btn btn-info' ></div><div class='alert alert-success' style='display:none;' id='message2'></div>";
        var queryByDateValueMonth = "<label class=' control-label' style='color: #6c757d;margin-top: 6px;margin-right: 5px;'>日期:</label><input class='form-control input-inline input-medium' id='queryByDateValueMonth' name='dateValue' placeholder='请选择所属月份' value='${midluopanrengongdata.dateValue}'type='text' align='right' style='width:180px;' autocomplete='off' readonly />";
        var queryByDateValueDay = "<div style='display: inline-flex;'><label class=' control-label' style='color: #6c757d;margin-top: 6px;margin-right: 5px;'>日期:</label><input class='form-control input-inline input-medium' id='queryByDateValueDay' name='dateValue' placeholder='请选择搜索日期' value='${midluopanrengongdata.dateValue}'type='text' align='right' style='width:180px;' autocomplete='off' readonly /></div>";
        //社区搜索框
        var queryByProjectName = "<div style='margin:10px 0;display: inline-flex;'><label style='font-family:\"Rubik\", sans-serif; color: #6c757d;margin-top: 6px;'>社区:&nbsp;</label> <select id='project_name' class='form-control input-inline input-medium' style='width: 180px;'></select><input type='hidden' name='projectId' id='selectProjectId'/></div>";
        //将搜索结果列表隐藏
        $("#showTable").show();
        $("#showTable2").hide();
        $('#addTableValue').hide();
        $('#queryDate').html(queryByDateValueMonth);
        initializationMonth();
        initializationDay();
        //初始化月类型搜索框
        function initializationMonth() {
            $('#queryByDateValueMonth').datetimepicker({
                format: 'yyyy-mm',
                autoclose: true,
                todayBtn: true,
                startView: 'year',
                minView:'year',
                maxView:'decade',
                language:  'zh-CN',
            }).on('changeDate',function(ev){
                //触发搜索按钮
                $("#queryBtn").click();
            });
        }
        //初始化日类型初始框
        function initializationDay() {
            $('#queryByDateValueDay').datetimepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                minView:'2',
                maxView:'decade',
                language:  'zh-CN',

            }).on('changeDate',function(ev){
                var project_name =   $("#project_name").val();
                if(null==project_name||""==project_name||undefined==project_name){
                    var project_id = $("#selectProjectId").val();
                    if(null!=project_id&&""!=project_id&&undefined!=project_id){
                        $("#queryBtn").click();
                    }
                }else{
                    $("#queryBtn").click();
                }
            });
        }

        //指标类型单选框改变之后
        $(".checkkey").click(function (){
            var key = $("input:radio[name='key']:checked ").val();
            if("任务-绑定户数"==key){
                $('#queryDate').html(queryByDateValueMonth);
                $('#addTableValue').html(tablemonth);
                $("tr:odd").css("background-color", "white");
                initializationMonth();
                checkDateValue(key,false);
                //触发搜索按钮
                $("#queryBtn").click();

            }else if("取消或暂停项目"==key){
                $('#queryDate').html(queryByDateValueDay);
                $('#addTableValue').html(tableday);
                $("tr:odd").css("background-color", "white");
                initializationDay();
            }else if("物业云实施状态"==key || "员工注册数量"==key || "员工潜在使用系统的人员数量"==key){
                $('#queryDate').html(queryByDateValueDay+queryByProjectName);
                $('#addTableValue').html(tableqita);
                //社区下拉列表加载
                initiationproject();
                initializationDay();
                checkDateValue(key,true);
                //将昨天日期和第一个社区设置进去
                var today = new Date();
                today.setTime(today.getTime()-24*60*60*1000);
                var year = today.getFullYear();
                var month = today.getMonth() + 1;
                if(month<10){
                    month ="0"+month;
                }
                var day = today.getDate();
                if(day<10){
                    day = "0"+day;
                }
                $("#queryByDateValueDay").val(year+"-"+month+"-"+day);
                //先选择一个社区 暂时固定
                var selectProjectId ="${selectProjectId.uuid}";
                //依据message的值,判断是添加还是查询
                if(selectProjectId!=""){
                    $("#selectProjectId").val(selectProjectId);
                    //将其选中
                    $("#project_name").select2("data",{"id":"${selectProjectId.uuid}","text":"${selectProjectId.project_name}"});
                }
                $("#queryBtn").click();
            }
        });
        /**
         * 控制日期控件展示那个,并清空所选值
         * @param value
         */
        function checkDateValue(value,is_clear){
            var today = new Date();
            var year = today.getFullYear();
            var month = today.getMonth() + 1;
            if(month<10){
                month ="0"+month;
            }
            var day = today.getDate();
            if(day<10){
                day = "0"+day;
            }
            //判断dateValue是否有值,如果有值,赋值
            if("取消或暂停项目"==value){
                if(is_clear){
                    $("#queryByDateValueDay").val(null);
                    $("#queryByDateValueMonth").val(null);
                }
                $('.dateValue').val(year+"-"+month+"-"+day);
                $('.key').val("取消或暂停项目");
            }else if("任务-绑定户数"==value || "物业云实施状态"==value){
                if(is_clear){
                    $("#queryByDateValueMonth").val(null);
                    $("#queryByDateValueDay").val(null);
                }
                $("#queryByDateValueMonth").val(year+"-"+month)
                $('.monthValue').val(year+"-"+month);
                $('.key').val("任务-绑定户数");
            }
        };

      //查询按钮点击之前进行数据校验
      function checkQuery(){
          var key = $("#radiokey input[name='key']:checked ").val();
          if(key=="任务-绑定户数"||key=="取消或暂停项目" || key=="物业云实施状态"){
          }else{
              $("#message").attr("class", "alert alert-warning");
              $("#message").html("搜索数据时,项目类型必须选择!").show(300).delay(3000).hide(300);
              return false;
          }
          if(key=="物业云实施状态"){
              //判断是否选择了社区
            var project_name =   $("#project_name").val();
            if(null==project_name||""==project_name||undefined==project_name){
                var project_id = $("#selectProjectId").val();
                if(null!=project_id&""!=project_id&undefined!=project_id){
                    return true;
                }
                $("#message").attr("class", "alert alert-warning");
                $("#message").html("物业云实施状态,搜索数据时,社区必须选择!").show(300).delay(3000).hide(300);
                return false;
            }
          }
          var month = $("#queryByDateValueMonth").val();
          if(month==null||month==undefined||month==""){
              var day = $("#queryByDateValueDay").val();
              if(day==null||day==undefined||day==""){
              }else{
                  return true;
              }
          }else{
              return true;
          }
          $("#message").attr("class", "alert alert-warning");
          $("#message").html("搜索条件下,日期不能为空!").show(300).delay(3000).hide(300);
          return false;
      };
        //保存数据按钮提交
        function check(){
            var key = $("#radiokey input[name='key']:checked ").val();
            if(key=="任务-绑定户数"||key=="取消或暂停项目"){
                var month = $("#queryByDateValueMonth").val();
                if(month==null||month==undefined||month==""){
                    var day = $("#queryByDateValueDay").val();
                    if(day==null||day==undefined||day==""){
                    }else{
                        //给key赋值
                        $('.key').val(key);
                        return true;
                    }
                }else{
                    //在这儿给日期赋值
                    $('.monthValue').val($('#dateValueadd').val());
                    $('.key').val(key);
                    return true;
                }
                $("#message2").attr("class", "alert alert-warning");
                $("#message2").html("日期不能为空,请从上方选择!").show(300).delay(3000).hide(300);
                return false;
            }else if("物业云实施状态"==key){
                var adddateValuewuyeyun = $("#adddateValuewuyeyun").val()
                if(null==adddateValuewuyeyun||""==adddateValuewuyeyun||undefined==adddateValuewuyeyun){
                    $("#message2").attr("class", "alert alert-warning");
                    $("#message2").html("物业云实施状态,保存数据时,日期不能为空!").show(300).delay(3000).hide(300);
                    return false;
                }
                //社区下拉框是否有值
                var projectsearch = $("#project_name").val();
                if(null==projectsearch||""==projectsearch||undefined==projectsearch){
                    $("#message2").attr("class", "alert alert-warning");
                    $("#message2").html("物业云实施状态,保存数据时,社区必须选择!").show(300).delay(3000).hide(300);
                    return false;
                }else{
                    //在这儿将action替换掉
                    $("#addTableValue").attr('action',"./midrengongdataaddwuyeyun");
                    return true;
                }
            }else{
                $("#message2").attr("class", "alert alert-warning");
                $("#message2").html("保存数据时,录入类型必须选择!").show(300).delay(3000).hide(300);
                return false;
            }
        };
        $(function(){
            //查询按钮点击时
            $("#queryBtn").click(function(){
                var midluopanrengongdata = {};
                var key = $("#radiokey input[name='key']:checked ").val();
                midluopanrengongdata.key = key;
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
                var project_name =   $("#project_name").val();
                if(null!=project_name){
                    midluopanrengongdata.projectId = project_name;
                    $("#selectProjectId").val(project_name);
                }
                return midluopanrengongdata;
            });
            //点击搜索之后上一次选择的城市和操作类型保持选中状态
            var keyTemp = "${midluopanrengongdata.key}";
            if(""!=keyTemp){
                //设置日期框样式
                checkDateValue(keyTemp,false);
                //搜索之后,让radio保持选中状态
                if("取消或暂停项目"==keyTemp){
                    //取消或暂停项目单选按钮选中
                    $("#keyradio2").attr("checked","checked");
                    //设置日期下拉列表
                    $('#queryDate').html(queryByDateValueDay);
                    initializationDay();
                    $("#typeText").html("取消或暂停项目数量(个)")
                }else if("物业云实施状态"==keyTemp){
                    $("#keyradio3").attr("checked","checked");
                    //并且将日期等设置进去
                    $('#queryDate').html(queryByDateValueDay+"&nbsp;"+queryByProjectName);
                    $('#addTableValue').html(tableqita);
                    $("#dateValue").val("${midluopanrengongdata.dateValue}");
                    //初始化日期和社区搜索列表
                    initiationproject();
                    initializationDay();

                }else{
                    $("#keyradio1").attr("checked","checked");
                    //设置搜索框月份
                    $("#queryByDateValueMonth").val("${midluopanrengongdata.dateValue}");
                    $(".monthValue").val("${midluopanrengongdata.dateValue}");

                }
            }else{
                $("#keyradio1").attr("checked","checked");
            }
            var message ="${result}";
            //依据message的值,判断是添加还是查询
            if(message==null||message == undefined||message==""){
            }else{
                var keyresult = "${key}";
                if(keyresult==null||keyresult == undefined||keyresult==""){
                    if("查询物业云状态成功"==message){
                        //把表头给固定
                        $('#queryDate').html(queryByDateValueDay+"&nbsp;"+queryByProjectName);
                        initiationproject();
                        initializationDay();
                        $("#addTable").hide();
                        $("#showTable").hide();
                        $("#showTable2").show();
                        $("#regionNamewuyeyun").val("${midluopanrengongdata.regionName}");
                        $("#regionIdwuyeyun").val("${midluopanrengongdata.regionId}");
                        $("#projectNamewuyeyun").val("${midluopanrengongdata.projectName}");
                        $("#projectIdwuyeyun").val("${midluopanrengongdata.projectId}");
                        $("#propertyinfoNamewuyeyun").val("${midluopanrengongdata.propertyinfoName}");
                        $("#dateValuewuyeyun").val("${midluopanrengongdata.dateValue}");
                        if("1"=="${statu}"){
                            $("#statevaluewuyeyun").val("${midluopanrengongdata.mlrds[0].value}");
                            $("#yuangongvaluewuyeyun").val("${midluopanrengongdata.mlrds[1].value}");
                            $("#zhucevaluewuyeyun").val("${midluopanrengongdata.mlrds[2].value}");
                        }else{
                            $("#statevaluewuyeyun").val(0);
                            $("#submitwuyeyun").val("保存数据");
                        }
                        $("#project_name").append('<option value="${queryprojectid}">${midluopanrengongdata.projectName}</option>');
                    }else{
                        if("查询成功"!=message){
                            /*如果不是查询成功,则是添加 */
                            $("#message").attr("class", "alert alert-success");
                            $("#message").html(message).show(300).delay(3000).hide(300);
                        }
                        $("#addTable").hide();
                        $("#showTable2").hide();
                        $("#showTable").show();
                    }

                }else{
                    if("物业云实施状态"==keyresult){
                        $("#keyradio3").attr("checked","checked");
                        //并且将日期等设置进去
                        $('#queryDate').html(queryByDateValueDay+"&nbsp;"+queryByProjectName);
                        $("#dateValue").val("${midluopanrengongdata.dateValue}");
                        initiationproject();
                        initializationDay();
                        //在这儿将以前数据选中
                        $("#message").attr("class", "alert alert-success");
                        if("{success=操作成功}"==message){
                            $("#message").html("操作成功").show(300).delay(3000).hide(300);
                        }else{
                            $("#message").html(message).show(300).delay(3000).hide(300);
                        }
                        $("#addTable").hide();
                        $("#showTable").hide();
                        $("#showTable2").show();
                        $("#regionNamewuyeyun").val("${midluopanrengongdata.regionName}");
                        $("#regionIdwuyeyun").val("${midluopanrengongdata.regionId}");
                        $("#projectNamewuyeyun").val("${midluopanrengongdata.projectName}");
                        $("#projectIdwuyeyun").val("${midluopanrengongdata.projectId}");
                        $("#propertyinfoNamewuyeyun").val("${midluopanrengongdata.propertyinfoName}");
                        $("#dateValuewuyeyun").val("${midluopanrengongdata.dateValue}");
                        $("#statevaluewuyeyun").val("${midluopanrengongdata.mlrds[0].value}");
                        $("#yuangongvaluewuyeyun").val("${midluopanrengongdata.mlrds[1].value}");
                        $("#zhucevaluewuyeyun").val("${midluopanrengongdata.mlrds[2].value}");


                        $("#project_name").append('<option value="${queryprojectid}">${midluopanrengongdata.projectName}</option>');

                    }else{
                        if("查询成功"!=message){
                            /*如果不是查询成功,则是添加 */
                            $("#message").attr("class", "alert alert-success");
                            $("#message").html(message).show(300).delay(3000).hide(300);
                        }
                        $("#addTable").hide();
                        $("#showTable2").hide();
                        $("#showTable").show();
                    }
                }
            }
        })
        //社区下拉列表初始化
        function initiationproject(){
            $("#project_name").select2({
                placeholder:'请选择',
                allowClear:true,
                ajax: {
                    url: "./queryprojectbyq",
                    dataType: 'json',
                    delay: 250,
                    data: function (params) {
                        return {
                            q: params.term,
                        };
                    },
                    processResults: function (data) {
                        return {
                            results: data
                        };
                    },
                    cache: true
                },
                escapeMarkup: function (markup) { return markup; },
                minimumInputLength: 1,
                templateResult: formatRepo,
                templateSelection: formatRepoSelection,
                language: "zh-CN"
            });
        }
        //templateResult返回结果回调
       function formatRepo(repo){
           if(undefined==repo.project_name){
               return repo;
           }
            return "<label style='color:blue'>"+repo.region_name+"</label><br/>"+"<label style='color:red'>&nbsp;&nbsp;"+repo.project_name+"</label><br/>";
        }
       //templateSelection选中项回调function
       function formatRepoSelection(repo){
           var reporesult = repo.text;
           if(null!=repo.region_id && undefined != repo.region_id && ""!=repo.region_id){
               $("#regionName").val(repo.region_name);
               $("#regionId").val(repo.region_id);
               $("#projectName").val(repo.project_name);
               $("#projectId").val(repo.project_id);
               $("#propertyinfoName").val(repo.propertyinfo_name);
               reporesult = repo.project_name;
               //
               var day = $("#queryByDateValueDay").val();
               if(day==null||day==undefined||day==""){
               }else{
                   $("#queryBtn").click();
               }
           }
           return "<label style='color:black'>"+ reporesult+"</label>";
       }
    </script>
</body>
</html>
