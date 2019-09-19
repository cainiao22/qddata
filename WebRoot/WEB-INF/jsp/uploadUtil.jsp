<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <c:import url="common/importCss2.jsp"></c:import>
    <link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>
    <link href="./assets/plugins/select2/css/select2.css" rel="stylesheet" type="text/css"/>
   <%-- <link href="./assets/plugins/layui/css/layui.css" rel="stylesheet" type="text/css"/>--%>


    <style type="text/css">



        .dataTable td{
            white-space: nowrap !important;
            text-align: center;
        }

        .dataTable th {
            white-space: nowrap !important;
            text-align: center;
        }

        .paging_simple_numbers .pagination .paginate_button {
            padding: 0px;
            background: #2f363efc;
        }

        .datepicker th {
            font-size: 14px !important;
            color: #ffffff;
        }

        .datepicker tfoot tr th:hover {
            background: #2a323c;
        }

        .datepicker table tr td.day.focused {
            background: #2a323c;
            cursor: pointer;
        }

        .datepicker table tr td.today, .datepicker table tr td.today:hover, .datepicker table tr td.today.disabled, .datepicker table tr td.today.disabled:hover {
            background-color: #3b4655;
            background-image: linear-gradient(to bottom, #3b4655, #3b4655);
        }

        .datepicker table tr td.range.today:hover, .datepicker table tr td.range.today:hover:hover, .datepicker table tr td.range.today.disabled:hover, .datepicker table tr td.range.today.disabled:hover:hover, .datepicker table tr td.range.today:active, .datepicker table tr td.range.today:hover:active, .datepicker table tr td.range.today.disabled:active, .datepicker table tr td.range.today.disabled:hover:active, .datepicker table tr td.range.today.active, .datepicker table tr td.range.today:hover.active, .datepicker table tr td.range.today.disabled.active, .datepicker table tr td.range.today.disabled:hover.active, .datepicker table tr td.range.today.disabled, .datepicker table tr td.range.today:hover.disabled, .datepicker table tr td.range.today.disabled.disabled, .datepicker table tr td.range.today.disabled:hover.disabled, .datepicker table tr td.range.today[disabled], .datepicker table tr td.range.today:hover[disabled], .datepicker table tr td.range.today.disabled[disabled], .datepicker table tr td.range.today.disabled:hover[disabled] {
            background-image: linear-gradient(to bottom, #3c4857, #3c4857);
        }

        .datepicker table tr td.day:hover,
        .datepicker table tr td.day.focused {
            background: #2a323c;
            cursor: pointer;
        }

        .datepicker table tr td.range, .datepicker table tr td.range:hover, .datepicker table tr td.range.disabled, .datepicker table tr td.range.disabled:hover {
            background: #3c4857;
        }

        .datepicker .datepicker-switch:hover,
        .datepicker .prev:hover,
        .datepicker .next:hover,
        .datepicker tfoot tr th:hover {
            background: #2a323c;
        }


        .datepicker table tr td.active, .datepicker table tr td.active:hover, .datepicker table tr td.active.disabled, .datepicker table tr td.active.disabled:hover {
            background-color: #2a323c !important;
        }

        div.dataTables_processing {
            background: linear-gradient(to right, rgba(255, 255, 255, 0) 0%, rgb(42, 50, 60) 25%, rgb(42, 50, 60) 75%, rgba(255, 255, 255, 0) 100%);
            position: absolute;
            top: 75%;
            left: 50%;
            width: 200PX;
            height: 60px;
            margin-left: -100px;
            margin-top: -26px;
            padding-top: 20px;
            padding-bottom: 20px;
            text-align: center;
            font-size: 1.2em;
        }
        .cityarea1{
            width: 85px;
        }
        .cityarea{
            width: 170px;
        }
        .commmunityselect{
            width: 60%;
            height: 28px;
            background-color: #2a323c;
            color: #999;
            padding-left: 8px;
            cursor: pointer;
            border: 1px solid #6a7077;
            border-radius: 3px;
        }
        .row{
            position: relative;
        }
        .input-daterange{
            position: absolute;
            left: 65px;
            top: 0px;
        }
/*        .form-control{
            width:30px;
        }*/
        .showstyle{
            padding-left:50px;
        }
        .input-group{
            width: 60%;
        }
        .input-group > .form-control{
            /*position: relative;*/
            /*-webkit-box-flex: 1;*/
            /*-ms-flex: 1 1 auto;*/
            /*flex: 1 1 auto;*/
            /*width: 1%;*/
            /*margin-bottom: 0;*/
            width: 100px;
            height: 26px;
            min-height: 26px;
            border: 1px solid #6a7077;
        }
        .dataTables_scrollBody{
            height: auto;
            max-height: 350px;
            overflow-y: scroll;
        }
        .table td, .table th {
            padding: 0.3rem 0.75rem 0.3rem;
            vertical-align: top;
        }
        .page-titles {
            padding-bottom: 0px;
        }
        .form-group {
            margin-bottom: 10px;
        }
        .select2-container--default .select2-selection--single .select2-selection__rendered {
            font-size: 13px;
        }
        /* */
        .datatableclass{
            background:RGB(29,44,51);
            padding:10px;
            border-radius:8px;
        }
        .btn{
            padding: 3px 18px;
            font-size: 14px;
            cursor: pointer;
        }
        hr {
            margin-top: 0rem;
            margin-bottom: 2rem;
            border: 0;
            border-top: 1px solid rgb(76, 83, 91);
        }
        div.dataTables_processing {
            background: linear-gradient(to right, rgba(255, 255, 255, 0) 0%, rgb(42, 50, 60) 25%, rgb(42, 50, 60) 75%, rgba(255, 255, 255, 0) 100%);
            position: absolute;
            top: 75%;
            left: 50%;
            width: 200PX;
            height: 60px;
            margin-left: -100px;
            margin-top: -26px;
            padding-top: 20px;
            padding-bottom: 20px;
            text-align: center;
            font-size: 1.2em;
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
                    <!--删除 列表上边的 menu-title -->
                    <%--<li class="nav-small-cap">menu-title</li>--%>
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
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-body">
                                <div class="col-md-15">
                                        <div class="portlet-title">
                                            <form id="queryForm">
                                            <div class="row">
                                                <div class="form-group col-md-4 ">
                                                    <label class="control-label">日期：</label>
                                                    <div class="input-daterange input-group" >
                                                        <input type="text" class="form-control" name="createData" id="dpd1"/>
                                                    </div>
                                                </div>
                                                <div class="form-group col-md-4">
                                                    <label class="control-label">类型：</label>
                                                    <select id="merge" name="type"  class="commmunityselect" >
                                                        <option value="-1"  selected="selected">全部</option>
                                                        <option value="1">资产负债表</option>
                                                        <%--<option value="2">现金流量表</option>--%>
                                                        <option value="2">利润表</option>
                                                        <option value="3">大事记</option>
                                                        <option value="4">400</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-4" style="float: right">
                                                    <button type="button" class="btn btn-info"
                                                            onclick="getData()">搜索
                                                    </button>
                                                </div>
                                            </div>
                                            </form>
                                            <hr>
                                        </div>
                                    <div class="row">
                                        <div class="form-group" style="float: left;margin-top: -21px;margin-left: 51px;">
                                            <button class="btn btn-info" id="insertBt">
                                                <span>添加</span>
                                            </button>
                                        </div>
                                    </div>
                                        <div class="portlet-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <table id="datatable" class="table table-striped table-bordered" style="background-color: #2a323c">
                                                        <thead>
                                                            <tr>
                                                                <th>序号</th>
                                                                <th>日期</th>
                                                                <th>类型</th>
                                                                <th>上传时间</th>
                                                                <th>上传人</th>
                                                                <th>操作</th>
                                                            </tr>
                                                        </thead>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                <!-- END SAMPLE TABLE PORTLET-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Container fluid  -->
        <!-- footer -->
        <c:import url="common/footer.jsp"></c:import>
        <!-- End footer -->
    </div>
    <!-- End Page wrapper  -->
</div>
<!-- End Wrapper -->
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">
                    <font color="#f5f5dc">图片宽度请选择在375px以内的!&nbsp;&nbsp;&nbsp;&nbsp;图片大小请选择在15MB以内的!</font>
                </h4>
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    ×
                </button>
            </div>
            <div class="modal-body" >
                <form id="imgForm">
                <div class="row">
                    <div class="form-group col-md-4">
                        <input type="hidden" name="id" id="id"/>
                        <label class="control-label">类型：</label>
                        <select id="updataMerge" name="type" class="commmunityselect" >
                            <option value="-1"  selected="selected">全部</option>
                            <option value="1">资产负债表</option>
                           <%-- <option value="2">现金流量表</option>--%>
                            <option value="2">利润表</option>
                            <option value="3">大事记</option>
                            <option value="4">400</option>
                        </select>
                    </div>
                    <div class="form-group col-md-4 ">
                        <label class="control-label">日期：</label>
                        <div class="input-daterange input-group" >
                            <input type="text" class="form-control" name="createData" id="upDate"/>
                        </div>
                    </div>
                </div>

                <!-- 上传弹出框 -->
                <div id="uploadDialog" class="upload-dialog">
                    <ul id="ul_pics">
                    </ul>
                    <div class="form-group" style="float: left;">
                        <button class="btn btn-info" id="upImgBt">
                            <span>上传图片</span>
                        </button>
                    </div>
                </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" onclick="insertImgInfo()" class="btn btn-primary">确定
                </button>
                <button type="button" class="btn btn-default"  data-dismiss="modal" id="close">取消
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!--单个删除确认对话框-->
<div class="modal fade" id="deleteOneModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" > <!-- data-backdrop="static" 禁止点击弹框后面内容 -->
        <div class="modal-dialog modal-sm " > <!-- modal-sm 小的  modal-lg 大的 -->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        ×
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        提示信息
                    </h4>
                </div>
                <div class="modal-body" style="text-align: left;">
                    <h5>您确定要删除当前信息吗？</h5>
                </div>
                <div class="modal-footer">
                    <!--
                        <button type="button" class="btn btn-default"
                                data-dismiss="modal">取消
                        </button>
                         -->
                    <button type="button" class="btn btn-primary" id="delSubmit">
                        确认
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div>
</div>


<c:import url="common/importJs2.jsp"></c:import>
<script type="text/javascript" src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="./assets/plugins/datepicker/js/bootstrap-datepicker.js"></script>
<script src="./assets/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="./assets/plugins/select2/js/select2.js"></script>
<script src="./assets/plugins/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="./assets/plugins/moment-with-locales.min.js"></script>
<script>

    function insertImgInfo() {
       var sel =  $("#updataMerge").val();
       var date =  $("#upDate").val();
       if(sel == -1){
           swal("提示", "类型不能为空!");
           return;
       }else if(date == null || date == ""){
           swal("提示", "日期不能为空!");
           return;
       }
        $.ajax({
            url:"./insertEarningsReportInfo",
            type:"post",
            data:$("#imgForm").serialize(),
            dataType:"json",
            success:function(data){
                if(data.success) {
                    window.location.reload();
                }else{
                    console.log("msg",data.msg)
                    console.log("errMsg",data.errMsg)
                }
            }
        });
    }

    var width = 7500;
    var uploader = new plupload.Uploader({    //创建实例的构造方法
        runtimes : 'html5,flash,silverlight,html4',
        browse_button: 'upImgBt',           // 上传按钮
        url: "../upImgInfo?width="+width,        //远程上传地址
        flash_swf_url: '${pageContext.request.contextPath}/assets/plugins/plupload/Moxie.swf',    //flash文件地址
        silverlight_xap_url: '${pageContext.request.contextPath}/assets/plugins/plupload/Moxie.xap', //silverlight文件地址
        filters: {
            //max_file_size: '500kb', //最大上传文件大小（格式100b, 10kb, 10mb, 1gb）
             //title : "Image files", extensions : "jpg,jpeg,png,bmp,gif,pjpeg,x-png,GIF,JPG,PNG,JPEG,BMP,PJPEG,X-PNG" ,
             prevent_duplicates: false, //不允许选取重复文件
        },
        file_data_name:'file',
        unique_names:true,
        multi_selection: true, //true:ctrl多文件上传, false 单文件上传
        init: {
            FilesAdded: function(up, files) { //文件上传前
                var reader = new FileReader();
                reader.readAsDataURL(files[0].getNative());
                reader.onload = (function (e) {
                    var image = new Image();
                    image.src = e.target.result;
                    image.onload = function () {

                            var li = '';
                            plupload.each(files, function(file) { //遍历文件
                                li += "<li id='" + file['id'] + "'><div class='progress'><span class='bar'></span><span class='percent'>上传中 0%</span></div></li>";
                            });
                            $("#ul_pics").append(li);
                            uploader.start();

                    };
                });
            },
            UploadProgress: function(up, file) { //上传中，显示进度条
                var percent = file.percent;
                $("#" + file.id).find('.bar').css({"width": percent + "%"});
                $("#" + file.id).find(".percent").text("上传中 "+percent + "%");
            },
            FileUploaded: function(up, file, info) { //文件上传成功的时候触发
                var data = eval("(" + info.response + ")");
                console.log("data",data);
                if(data.success){
                    $("#" + file.id).html("<img src='" + data.imgPath + "' width='300px' height='300px' /><i onclick='delimg(this)'>x</i><input type='hidden' name='url' value='"+ data.imgPath +"'>");
                }else{
                    $("#" + file.id).remove();
                    swal("提示", data.msg);
                }
            },
            Error: function(up, err) { //上传出错的时候触发
                console.log("err",err);
            }
        }
    });
    uploader.init();

    function delimg(o){
        var src = $(o).prev().attr("src");
        $(o).parent().remove();
    /*    $.post("upimgs.php?get=delimg&imgurl="+src,function(data){
            if(data==1){
                $(o).parent().remove();
            }
        })*/
    }


    //模态窗口
    $('#insertBt').click(function () {
        $("#myModal1").modal("show");  //显示模态框
        $("#myModal").modal({ backdrop: "static", keyboard: false });
    });

    $(".close").click(function () {
        window.location.reload();
    })

    $("#close").click(function () {
        window.location.reload();
    })

    //时间
    $('#dpd1').datepicker({
        language: "zh-CN",
        autoclose: true,
        format: 'yyyy-mm',
        maxDate: "+1D",
        startView: 'months', //开始视图层，为月视图层
        maxViewMode:'years', //最大视图层，为年视图层
        minViewMode:'months', //最小视图层，为月视图层
    });

    //模态修改时间
    $('#upDate').datepicker({
        language: "zh-CN",
        autoclose: true,
        format: 'yyyy-mm',
        maxDate: "+1D",
        startView: 'months', //开始视图层，为月视图层
        maxViewMode:'years', //最大视图层，为年视图层
        minViewMode:'months', //最小视图层，为月视图层
    });

    //语言样式设置
    var i18nZH = {
        "sProcessing":   "处理中...",
        "sLengthMenu":   "显示 _MENU_ 项结果",
        "sZeroRecords":  "没有匹配结果",
        "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix":  "",
        "sSearch":       "搜索:",
        "sUrl":          "",
        "sEmptyTable":     "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "首页",
            "sPrevious": "上页",
            "sNext":     "下页",
            "sLast":     "末页"
        },
        "oAria": {
            "sSortAscending":  ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    };

    //页面加载调用查询
    $(function () {
        moment.locale('zh-cn');
        //后台调用接口

        $('#datatable').DataTable({
            dom: "lrtip",
            language: i18nZH,
            responsive: !0,
            "lengthChange": true,//开启显示条数
            "lengthMenu": [ 15, 50, 75, 100 ],
            "ordering": false,//禁止排序
            "deferRender": true,
            "processing": true,
            "serverSide": true,
            "scrollX": true,
            "autoWidth": false,
            "destroy": true,
            "ajax": {
                "url":"../queryImgInfo",
                "type": "POST",
                "async" : true,
                "data": function (d) {
                    //删除多余请求参数
                    for(var key in d){
                        if(key.indexOf("columns")==0||key.indexOf("order")==0||key.indexOf("search")==0){ //以columns开头的参数删除
                            delete d[key];
                        }
                    }
                    var searchParams = {"type":$('#merge').val(),"createData":$('#dpd1').val(), "pageSize":d.length,
                        "currentPage":parseInt(d.start/d.length)+parseInt(1)};
                    //附加查询参数
                    if (searchParams) {
                        $.extend(d, searchParams); //给d扩展参数
                    }
                },
                "dataType": "json",
                "dataFilter": function (json) {//json是服务器端返回的数据
                    json = JSON.parse(json);
                    if(json.success){
                        console.log("data",json);
                        var returnData = {};
                        returnData.recordsTotal = json.total;//返回数据全部记录
                        returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = json.data;//返回的数据列表
                        return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                    }else{
                        console.log("errInfo",json.errInfo);
                    }
                }
            },
            /*给数据添加列*/
            columns: [
                {
                    sTitle: '序号',
                    data: null,
                    className: 'text-center whiteSpace',
                    render:function(data,type,row,meta) {
                        return meta.row + 1 +
                            meta.settings._iDisplayStart;
                    }
                },
                {data: 'createData'},
                {data: 'type',
                    "render" : function(data, type, full, meta) {
                        if(data==1){//类型 1.资产负债表  2.利润表 3. 大事记
                            data ="资产负债表";
                        }else if(data == 2){
                            data ="利润表";
                        }else if(data == 3){
                            data ="大事记";
                        }else if(data == 4){
                            data ="400";
                        }
                        return	 data;
                    }
                },
                {data: 'updateTime',
                    "render" : function(data, type, full, meta) {
                        //时间格式化
                        return  moment(data).format("YYYY-MM-DD HH:mm:ss");
                    }
                },
                {data: 'upname'},
            ],
            "columnDefs" : [ {
                // 定义操作列,######以下是重点########
                "targets" : 5,//操作按钮目标列
                "data" : null,
                "render" : function(data, type,row) {
                    var id = '"' + row.id + '"';
                    var html ="";
                    html += "<a href='javascript:void(0);' onclick='queryThisRowInfoByid("+id+")'> 编辑</a>"
                    html += "&nbsp;丨"
                    html += "<a href='javascript:void(0);' onclick='deleteThisRowPapser("+ id+ ")'> 删除</a>"
                    return html;
                }
            } ],
        });
    })

    //回显信息
    function queryThisRowInfoByid(id) {
        $.ajax({
            url:"./queryThisRowInfoById",
            type:"post",
            data:{"id":id},
            dataType:"json",
            success:function (data) {
                if(data.success){
                    $("#myModal1").modal("show");  //显示模态框
                    $("#myModal").modal({ backdrop: "static", keyboard: false });
                    //id  updataMerge  upDate ul_pics
                    $("#id").val(data.earningsReport.id);
                    $("#updataMerge").val(data.earningsReport.type);
                    $("#upDate").val(data.earningsReport.createData);
                    var li = "";
                    var sid = "";
                    var index = "";
                    var liArr = new Array(); //定义一数组
                    var imgUrl = data.earningsReport.url;
                    liArr = imgUrl.split(","); //字符分割

                    for (i = 0;i < liArr.length ;i++ ) {
                        index = liArr[i];
                        index = index.lastIndexOf("/");
                        sid = liArr[i].substr(index +1);
                        li += "<li id='" + sid + "'></li>";
                    }
                    $("#ul_pics").append(li);

                    $('li').each(function(){
                        for (i = 0;i < liArr.length ;i++ ) {
                            index = liArr[i];
                            index = index.lastIndexOf("/");
                            sid = liArr[i].substr(index +1);
                            if($(this).attr('id') == sid){
                                $(this).html("<img src='" + liArr[i] + "' width='300px' height='300px' /><i onclick='delimg(this)'>x</i><input type='hidden' name='url' value='"+ liArr[i] +"'>");
                            }
                        }
                    });
                }else{

                }
            }
        })
    }


    //删除信息
    function deleteThisRowPapser(id) {
        $("#deleteOneModal").modal('show');
        console.log("id",id)
        /**
         * 点击确认删除按钮
         */
        $(document).delegate('#delSubmit','click',function(){
            $('#deleteOneModal').modal('hide');
            $.ajax({
                url:"./deleteImgInfoById",
                type:"post",
                data:{"id":id},
                dataType:"json",
                success:function(data){
                    if(data.success)
                    {

                        window.location.reload();
                    }
                    else
                    {
                        console.log("msg",data.msg)
                        console.log("errMsg",data.errMsg)
                    }
                }
            });
        });
    }

    //搜索
    function getData() {
        var table = $("#datatable").DataTable();
        // table.destroy();
        /*  var merge=$('#merge').val();*/
        table.ajax.reload();
        table.columns.adjust();
    }

    $.fn.dataTable.ext.errMode = 'none'; //不显示任何错误信息
    //以下为发生错误时的事件处理，如不处理，可不管。
    $('#datatable').on( 'error.dt', function ( e, settings, techNote, message ){
        //这里可以接管错误处理，也可以不做任何处理
        console.log( 'An error has been reported by DataTables: ', message );
    }).DataTable();


</script>
</body>
</html>