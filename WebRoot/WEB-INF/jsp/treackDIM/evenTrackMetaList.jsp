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
    <c:import url="../common/importCss2.jsp"></c:import>
    <link href="./assets/plugins/select2/css/select2.css" rel="stylesheet" type="text/css"/>
    <style>
        .dataTable th {
            white-space: nowrap !important;
        }
    </style>

</head>
<!-- END HEAD -->
<body class="fix-header fix-sidebar card-no-border">

<div id="main-wrapper">
    <c:import url="../common/header2.jsp"></c:import>
    <!-- End Topbar header -->
    <aside class="left-sidebar">
        <!-- Sidebar scroll-->
        <div class="scroll-sidebar">
            <!-- Sidebar navigation-->
            <nav class="sidebar-nav">
                <ul id="sidebarnav">
                    <c:import url="../common/sidebarMenu2.jsp"></c:import>
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
                    <%--<h3 class="text-themecolor m-b-0 m-t-0">${ sidebar_menu.parentName}</h3>--%>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="javascript:void(0)">${ sidebar_menu.parentName}</a></li>
                        <li class="breadcrumb-item active">${ sidebar_menu.name} </li>
                    </ol>
                </div>
            </div>
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- Start Page Content -->
            <!-- Row -->

            <div class="col-md-12">
                <div class="card">
                    <div class="card-body" style="padding:0;">
                            <div class="modal fade" id="optModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel1" style="display: none;" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title" id="mode">操 作</h4>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                                    </div>
                                    <div class="modal-body">
                                        <form id="form">
                                            <div class="form-group row">
                                                <label for="code" class="col-form-label col-3">事件id:</label>
                                                <input type="hidden" class="form-control col-8" id="id" name="id">
                                                <input type="text" class="form-control col-8" id="code" name="code">
                                            </div>
                                            <div class="form-group row">
                                                <label for="name" class="col-form-label col-3">事件名称:</label>
                                                <input type="text" class="form-control col-8" id="name" name="name">
                                            </div>
                                            <div class="form-group row">
                                                <label for="paramIds" class="col-form-label col-3">参数:</label>
                                                <select class="form-control col-8" data-placeholder="请选择参数" name="paramIds" id="paramIds" style="color: #fdfdfebd">
                                                    <c:forEach var="list" items="${allParamList}">
                                                        <option value="${list.id }">${list.name }</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group row">
                                                <label for="businessId" class="col-form-label col-3">所属业态:</label>
                                                <select class="form-control col-8" id="businessId" name="businessId">
                                                    <c:forEach items="${businessList }" var="list">
                                                        <option value="${list.id }">${list.name }</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group row">
                                                <label for="productId" class="col-form-label col-3">所属产品:</label>
                                                <select  class="form-control col-8" id="productId" name="productId" readonly="readonly">
                                                    <c:forEach items="${productList }" var="list">
                                                        <option value="${list.id }">${list.name }</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group row">
                                                <label for="status" class="col-form-label col-3">状态:</label>
                                                <select class="form-control col-8" data-placeholder="请选择状态" name="status" id="status" style="color: #fdfdfebd">
                                                    <option value="0" selected=" selected">
                                                        正常
                                                    </option>
                                                    <option value="1">
                                                        禁用
                                                    </option>
                                                </select>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="submit()">提 交</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="fileModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                            &times;
                                        </button>
                                        <h4 class="modal-title" id="myModalLabel">
                                            导入Excel
                                        </h4>
                                    </div>
                                    <div class="modal-body">
                                        <input id="excelFile" name="excelFile" type="file" class="form-control fileClass" accept=".xls, .xlsx" />
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                                        </button>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="importFile()">
                                            提交
                                        </button>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal -->
                        </div>

                        <div class="col-md-13">
                            <div class="portlet-title">
                                <div class="row">
                                    <div class="form-group col-md-3 row">
                                        <label class="col-form-label" for="event_productId">产品：</label>
                                        <div class="input-group col-md-5">
                                            <select id="event_productId" name="productId">
                                                <c:forEach items="${productList }" var="list">
                                                    <option value="${list.id }">${list.name }</option>
                                                </c:forEach>

                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-3 row">
                                        <label class="col-form-label" for="eventName">事件名称：</label>
                                        <div class="input-group col-md-5">
                                            <input type="text" class="form-control" name="name" id="eventName">
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <button type="button" class="btn btn-info" onclick="getData()">搜 索 </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 datatableclass" style="background: RGB(29,44,51);border-radius: 8px;padding: 10px">
                                <div class="form-group" style="float: left;">
                                    <button type="button" class="btn btn-info" data-toggle="modal" data-target="#optModal" data-whatever="@mdo" onclick="addMode()">添 加
                                    </button>
                                    &nbsp;
                                    <button id="btn_edit" class="btn btn-info" data-toggle="modal" data-target="#optModal" disabled="disabled">
                                        编 辑
                                    </button>
                                </div>
                                <div class="form-group" style="float: right;">
                                    <button type="button" class="btn btn-info" data-toggle="modal" data-target="#fileModal">导 入</button>
                                    &nbsp;
                                    <button class="btn btn-info" onclick="exportExcel()">
                                        <span>导 出</span>
                                    </button>
                                </div>
                                <div>
                                    <table id="datatable" class="table table-striped table-bordered" style="background-color: #2a323c;width: 100%">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>序号</th>
                                            <th>事件id</th>
                                            <th>事件名称</th>
                                            <th>参数</th>
                                            <th>所属业态</th>
                                            <th>所属产品</th>
                                            <th>是否删除</th>
                                        </tr>
                                        </thead>
                                        <tbody></tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- END SAMPLE TABLE PORTLET-->
                        </div>
                    </div>
                </div>
            </div>

            <!-- Row -->
            <!-- End PAge Content -->
        </div>
        <!-- End Container fluid  -->
        <!-- footer -->
        <c:import url="../common/footer.jsp"></c:import>
        <!-- End footer -->
    </div>
    <!-- End Page wrapper  -->
</div>
<!-- End Wrapper -->
<c:import url="../common/importJs2.jsp"></c:import>
<script src="./assets/plugins/modernizr.min.js"></script>
<script src="./assets/plugins/detect.js"></script>
<script src="./assets/plugins/fastclick.js"></script>
<script src="./assets/plugins/jquery.slimscroll.js"></script>
<script src="./assets/plugins/waves.js"></script>
<script src="./assets/plugins/wow.min.js"></script>
<script src="./assets/plugins/jquery.nicescroll.js"></script>
<script src="./assets/plugins/jquery.scrollTo.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./assets/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="./assets/plugins/select2/js/select2.js"></script>
<script src="./assets/plugins/select2/js/zh-CN.js"></script>
<script>
    var dataAPIUrl = '';
    var dataAPIDomain = '';
    var userAPI = "";
    var rowData = null;
    $.fn.serializeObject = function() {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
    function getlocation(){
        if(location.origin === 'http://qddata.qdingnet.com'){
            dataAPIUrl = "http://qddata.qdingnet.com/getHttpData?url=";
            dataAPIDomain = "http://yushanfang.bigdata.qdingnet.com/";
            userAPI = location.origin+"/";
        }else if(location.origin === 'http://qa-qddata.bigdata.qdingnet.com'){
            dataAPIUrl = 'http://qa-qddata.bigdata.qdingnet.com/getHttpData?url=';
            dataAPIDomain = "http://qa-yushanfang.bigdata.qdingnet.com/";
            userAPI = 'http://qabigdata.qdingnet.com/';
        }else{
            dataAPIUrl = 'http://dev-qddata.bigdata.qdingnet.com/getHttpData?url=';
            dataAPIDomain = "http://dev-yushanfang.bigdata.qdingnet.com/";
            userAPI = 'http://devbigdata.qdingnet.com/';
        }
    }


    function proxy(str){
        if(dataAPIDomain != ''){
            return encodeURIComponent(dataAPIDomain + str);
        }
        if(location.origin === 'http://qddata.qdingnet.com'){
            dataAPIDomain = "http://yushanfang.bigdata.qdingnet.com/";

        }else if(location.origin === 'http://qa-qddata.bigdata.qdingnet.com'){
            dataAPIDomain = "http://qa-yushanfang.bigdata.qdingnet.com/";
        }else{
            dataAPIDomain = "http://dev-yushanfang.bigdata.qdingnet.com/";
        }

        return encodeURIComponent(dataAPIDomain + str);
    }


    getlocation();



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
        //后台调用接口
        var productList = ${productListForJson};
        var businessList = ${businessListForJson};
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
                "url":"/buriedEvent/query",
                "type": "POST",
                "async" : true,
                "data": function (d) {
                    //删除多余请求参数
                    for(var key in d){
                        if(key.indexOf("columns")==0||key.indexOf("order")==0||key.indexOf("search")==0){ //以columns开头的参数删除
                            delete d[key];
                        }
                    }
                    var searchParams = {};
                    if($('#event_productId').val()){
                        searchParams['productId'] = $('#event_productId').val();
                    }

                    if($('#eventName').val()){
                        searchParams['name'] = $('#eventName').val();
                    }

                    //附加查询参数
                    if (searchParams) {
                        $.extend(d, searchParams); //给d扩展参数
                    }
                },
                "dataType": "json",
                "dataFilter": function (json) {//json是服务器端返回的数据
                    json = JSON.parse(json);
                    if(json.list){
                        var returnData = {};
                        returnData.recordsTotal = json.total;//返回数据全部记录
                        returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = json.list;//返回的数据列表
                        return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                    }else{
                        console.log("errorMsg",json.errorMsg,json.errInfo);
                    }
                }

            },
            /*给数据添加列*/
            columns: [
                {data: null},
                {data: null,
                    "render" : function(data, type, full, meta){
                        return meta.row + 1 + meta.settings._iDisplayStart;}},
                {data: 'code'},
                {data: 'name'},
                {data: null,
                    "render" : function(data){
                        var result = "";
                        for(var i=0; i<data.paramList.length; i++){
                            result += data.paramList[i].name;
                            result += ",";
                        }
                        return result.substring(0, result.length - 1);
                    }
                },
                {data: 'businessId'},
                {data: 'productId'},
                {data: 'status'}
            ],
            "fnDrawCallback": function() {
                $(this).find('thead input[type=checkbox]').removeAttr('checked');
                rowData = null;
            },
            "aoColumnDefs": [{
                'targets': 0,
                'searchable':false,
                'orderable':false,
                'className': 'dt-body-center',
                'render': function (data, type, row){
                    return '<input class="checkchild" type="radio" name="radio" onclick="currentID('+row.id+')"/>';

                }
            },
                {
                    'targets': 5,
                    'searchable':false,
                    'orderable':false,
                    'className': 'dt-body-center',
                    'render': function (data){
                        for(var i=0; i<businessList.length; i++){
                            if(businessList[i].id == data){
                                return businessList[i].name;
                            }
                        }
                    }
                },

                {
                    'targets': 6,
                    'searchable':false,
                    'orderable':false,
                    'className': 'dt-body-center',
                    'render': function (data){
                        for(var i=0; i<productList.length; i++){
                            if(productList[i].id == data){
                                return productList[i].name;
                            }
                        }
                    }
                },

                {
                    'targets': 7,
                    'searchable':false,
                    'orderable':false,
                    'className': 'dt-body-center',
                    'render': function (data, type, row){
                        return row.status==0?"启用":"禁用";
                    }
                }]
        });
    })

    function getData() {
        var table = $("#datatable").DataTable();
        table.ajax.reload();
        table.columns.adjust();
    }

    function currentID(id) {
        // $('.checkchild').removeAttr('checked');
        // $(this).attr('checked','checked');
        var data = $("#datatable").DataTable().data();
        for( i in data){
            if(data[i].id == id){
                rowData = data[i]
                break;
            }
        }
        edit();
    }
    function edit() {
        if(rowData){
            $('#btn_edit').removeAttr("disabled");
            $('#id').val(rowData.id);
            $('#code').val(rowData.code);
            $('#businessId').val(rowData.businessId);
            $('#name').val(rowData.name);
            $('#productId').val(rowData.productId);
            $('#status').val(rowData.status);
            var paramIds = [];
            for(var i=0; i<rowData.paramList.length; i++){
                paramIds.push((rowData.paramList)[i].id);
            }
            $('#paramIds').val(paramIds).trigger('change');
        }
    }
    function addMode() {
        $('#id').val("");
        $('#code').val("");
        $('#businessId').val("");
        $('#name').val("");
        $('#productId').val("");
        $('#status').val(0);
        $('#paramIds').val([]).trigger('change');
    }
    function submit() {
        $.ajax({
            url: "/buriedEvent/saveOrUpdate",
            type: "post",
            data:$('#form').serialize(),
            success: function (data) {
                if(data.code == 0){
                    getData();
                }
            },
            error: function (e) {
            }
        });
    }

    //导出
    function exportExcel() {
        var url = "/buriedEvent/export?";
        if($('#event_productId').val()){
            url += 'productId=' + $('#event_productId').val();
        }

        if($('#eventName').val()){
            url += '&name=' + $('#eventName').val();
        }
        window.open(url);
    }
    $('#paramIds').select2({
        placeholder: "SKIP",
        allowClear:true,
        language: "zh-CN",
        multiple:true,
        width:'330px',
    });
    
    function importFile() {
        if($('#excelFile').val() == '') {
            alert("请选择需要导入的文件");
        }else{
            var formData = new FormData();
            formData.append("excel", $("#excelFile")[0].files[0]);
            $.ajax({
                url: "buriedEvent/import",
                type: "POST",
                data: formData,
                /**
                 *必须false才会自动加上正确的Content-Type
                 */
                contentType: false,
                /**
                 * 必须false才会避开jQuery对 formdata 的默认处理
                 * XMLHttpRequest会对 formdata 进行正确的处理
                 */
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        alert("上传成功！");
                    }else{
                        alert(data.errorMsg);
                    }
                    //$("#imgWait").hide();
                },
                error: function () {
                    alert("上传失败！");
                    //$("#imgWait").hide();
                }
            });
        }
    }
</script>
</body>
</html>