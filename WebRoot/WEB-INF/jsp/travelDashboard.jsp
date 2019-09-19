<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>旅游BU-驾驶舱</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta content="Admin Dashboard" name="description"/>
    <meta content="ThemeDesign" name="author"/>
    <link rel="stylesheet" href="./css/DataCenter/travelDashboard/morris.css"/>
    <link href="./css/DataCenter/travelDashboard/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="./assets/css/icons.css" rel="stylesheet" type="text/css"/>
    <link href="./css/DataCenter/travelDashboard/style.css" rel="stylesheet" type="text/css"/>
    <%--<link href="./assets/plugin/daterangepicker/daterangepicker.css" rel="stylesheet" type="text/css"/>--%>
    <link href="./assets/plugins/datepicker/css/bootstrap-datepicker.css" rel="stylesheet" type="text/css"/>
    <link href="./assets/plugins/select2/css/select2.css" rel="stylesheet" type="text/css"/>

    <style type="text/css">
        .link div{
            float: left ;padding-left: 10px;
        }
        select{
            width: 90%;
        }
        .tcenter{
            text-align: center;
            margin-bottom: 0px;
        }
        .tright{
            text-align: right;
            margin-top: 0px;
        }
        .tab-pane{
            padding: 10px 20px 30px 20px;
        }
        .nextform,.nextform option{
            background-color: #2a323c;
            border-radius: 4px;
            border: 1px solid rgba(255, 255, 255, 0.3);
            box-shadow: none;
            height: 28px;
            color: #ffffff;
            font-size: 14px;
            cursor: pointer;
        }
        .dropdown-menu{
            background-color: #323c48;
        }
        .datepicker {
            border: 1px solid #232a32;
            padding: 8px;
        }
        .form-control{
            background-color: #2a323c;
        }
        .form-control:focus {
            background: #2a323c;
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
        .datepicker table tr td.today, .datepicker table tr td.today:hover, .datepicker table tr td.today.disabled, .datepicker table tr td.today.disabled:hover{
            background-color: #3b4655;
            background-image: linear-gradient(to bottom, #3b4655, #3b4655);
        }
        .datepicker table tr td.range.today:hover, .datepicker table tr td.range.today:hover:hover, .datepicker table tr td.range.today.disabled:hover, .datepicker table tr td.range.today.disabled:hover:hover, .datepicker table tr td.range.today:active, .datepicker table tr td.range.today:hover:active, .datepicker table tr td.range.today.disabled:active, .datepicker table tr td.range.today.disabled:hover:active, .datepicker table tr td.range.today.active, .datepicker table tr td.range.today:hover.active, .datepicker table tr td.range.today.disabled.active, .datepicker table tr td.range.today.disabled:hover.active, .datepicker table tr td.range.today.disabled, .datepicker table tr td.range.today:hover.disabled, .datepicker table tr td.range.today.disabled.disabled, .datepicker table tr td.range.today.disabled:hover.disabled, .datepicker table tr td.range.today[disabled], .datepicker table tr td.range.today:hover[disabled], .datepicker table tr td.range.today.disabled[disabled], .datepicker table tr td.range.today.disabled:hover[disabled]{
            background-image: linear-gradient(to bottom, #3c4857, #3c4857);
        }
        .datepicker table tr td.day:hover,
        .datepicker table tr td.day.focused {
            background: #2a323c;
            cursor: pointer;
        }
        .datepicker table tr td.range, .datepicker table tr td.range:hover, .datepicker table tr td.range.disabled, .datepicker table tr td.range.disabled:hover{
            background: #3c4857;
        }
        .datepicker .datepicker-switch:hover,
        .datepicker .prev:hover,
        .datepicker .next:hover,
        .datepicker tfoot tr th:hover {
            background: #2a323c;
        }
        .input-group-addon {
            border-radius: 2px;
            background-color: #323c48;
            color: #ffffff;
            border: 1px solid #323c48;
        }
        .datepicker table tr td.active, .datepicker table tr td.active:hover, .datepicker table tr td.active.disabled, .datepicker table tr td.active.disabled:hover{
            background-color: #2a323c !important;
        }
        /*body{
            zoom: 80%;
        }*/
    </style>
</head>
<body class="fixed-left">
<div id="wrapper">


    <div class="content-page">
        <div class="content">
            <div class="">
                <div>
                    <ul class="nav navbar-nav navbar-right pull-right"><li class="hidden-xs"> <a href="#" id="btn-fullscreen" class="waves-effect waves-light notification-icon-box"><i class="mdi mdi-fullscreen"></i></a></li></ul>
                </div>
                <div class="page-header-title">
                    <h4 class="page-title">旅游BU-驾驶舱</h4>

                </div>
            </div>
            <div class="page-content-wrapper ">
                <div class="container">
                    <div class="row">
                        <div class="form-group col-lg-6">
                            <label class="col-lg-2 control-label">数据周期：</label>
                            <div class="col-lg-3 link">
                                <div class="col-lg-12" style="cursor: pointer;">
                                    <select id="cycle" class="nextform">

                                        <option value="昨天">昨天</option>
                                        <option value="最近7日" selected="selected">最近7日</option>
                                        <option value="本周">本周</option>
                                        <option value="上周">上周</option>
                                        <option value="最近30日">最近30日</option>
                                        <option value="本月">本月</option>
                                        <option value="上月">上月</option>
                                        <option value="自定义">自定义</option>
                                    </select>
                                </div>
                            </div>
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="input-sm form-control" name="start" id="dpd1"/>
                                <span class="input-group-addon">to</span>
                                <input type="text" class="input-sm form-control" name="end" id="dpd2"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-lg-4">
                            <div class="form-group">
                                <label class="col-lg-2 control-label">地域:</label>
                                <div class="col-lg-5">
                                    <select id="city">
                                        <option value="">地域</option>
                                    </select>
                                </div>
                                <div class="col-lg-5">
                                    <select id="community">
                                        <option value="">社区</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-lg-4">
                            <div class="form-group">
                                <label class="col-lg-2 control-label">品类:</label>
                                <div class="col-lg-5">
                                    <select id="first">
                                        <option value="">全部</option>
                                    </select>
                                </div>
                                <div class="col-lg-5">
                                    <select id="second">
                                        <option value="">全部</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-lg-2">
                            <button type="button" class="btn btn-info waves-effect waves-light fa fa-search" onclick="getData()">搜&nbsp;&nbsp;&nbsp;&nbsp; 索</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-3">
                            <div class="panel panel-primary">
                                <div class="tab-pane">
                                    <div style="height: 30px" >
                                        <h2 class="tcenter">
                                            <span id="saleNum">0</span>元
                                        </h2>
                                        <h4 class="tright">销售额</h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="panel panel-primary">
                                <div class="tab-pane">
                                    <div style="height: 30px">
                                        <h2 class="tcenter">
                                            <span id="orderNum">0</span>单
                                        </h2>
                                        <h4 class="tright">订单数</h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="panel panel-primary">
                                <div class="tab-pane">
                                    <div style="height: 30px">
                                        <h2 class="tcenter">
                                            <span id="buyNum">0</span>人
                                        </h2>
                                        <h4 class="tright">购买人数</h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="panel panel-primary">
                                <div class="tab-pane">
                                    <div style="height: 30px">
                                        <h2 class="tcenter">
                                            <span id="refundNum">0</span>元
                                        </h2>
                                        <h4 class="tright">退款金额</h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div>
                    <div>
                        <div class="col-lg-8">
                            <div class="row">

                                <div class="col-lg-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-body">
                                            <div id="echarts1" style="height: 300px"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-body">
                                            <div id="echartSell" style="height: 300px"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-body">
                                            <div id="echartsCustomer" style="height: 300px"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="panel panel-primary">
                                        <div class="panel-body">
                                            <div id="echartsPay" style="height: 300px"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <div class="col-lg-4">
                            <div class="panel panel-primary">
                                <div class="panel-body">
                                    <div style="float: left;padding: 7px 0 10px 0;" class="col-lg-4">
                                        <select id="groupKey" class="nextform js--triggerAnimation">
                                            <option value="城市">城市</option>
                                            <option value="社区">社区</option>
                                            <option value="品类">品类</option>
                                            <option value="商品">商品</option>
                                        </select>
                                    </div>
                                    <div style="float: left; padding-left: 10px">
                                        <h4 style="color:white;">TOP 10</h4>
                                    </div>
                                    <p>
                                    <div class="btn-group" data-toggle="buttons-radio" style="width: 100%">
                                        <button class="btn btn-primary fa fa-sort-amount-desc active" id="a1" onclick="resortTable(this)" value="sale_price" style="width: 25%">销售额</button>
                                        <button class="btn btn-primary fa fa-sort-amount-desc" onclick="resortTable(this)" value="pay_order_num" style="width: 25%">订单量</button>
                                        <button class="btn btn-primary fa fa-sort-amount-desc" onclick="resortTable(this)" value="refund_num" style="width: 25%">退单量</button>
                                        <button class="btn btn-primary fa fa-sort-amount-desc" onclick="resortTable(this)" value="refund_price" style="width: 25%">退款额</button>
                                    </div>
                                    </p>
                                    <div id="echartsTopTen" style="min-height: 577px">
                                        <table id="tblTopTen"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 60px;">
        <footer class="footer">
            千丁互联-大数据部
        </footer>
    </div>
</div>

<script src="./assets/plugins/jquery.min.js"></script>
<script src="./assets/global/plugins/bootstrap-table/bootstrap-table.min.js" type="text/javascript"></script>
<script src="./assets/global/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js" type="text/javascript"></script>

<script src="./assets/plugins/jquery.slimscroll.js"></script>
<script src="./assets/plugins/detect.js"></script>
<script src="./assets/plugins/modernizr.min.js"></script>
<script src="./assets/plugins/fastclick.js"></script>
<script src="./assets/plugins/wow.min.js"></script>
<script src="./js/DataCenter/travelDashboard/app.js"></script>

<script src="./assets/plugins/waves.js"></script>
<script src="./js/DataCenter/travelDashboard/echarts.common.min.js"></script>

<script src="./js/DataCenter/travelDashboard/dashboard.js"></script>

<script type="text/javascript" src="./assets/plugins/moment-with-locales.min.js"></script>
<script src="./assets/plugins/datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="./assets/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript" src="./assets/plugins/daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="./assets/plugins/select2/js/select2.js"></script>
<script type="text/javascript" src="./assets/plugins/jquery.animateNumber.min.js"></script>
<script type="text/javascript">
    $(function() {
        moment.locale('zh-cn');
        $('#dpd1').val(moment().subtract(7,'days').format('YYYY-MM-DD'));
        $('#dpd2').val(moment().subtract(1,'days').format('YYYY-MM-DD'));
        $('.input-daterange').datepicker({
            language: "zh-CN",
//            todayHighlight : true,
            autoclose: true,
            format : 'yyyy-mm-dd',
            maxDate: "+1D",

        }).on('changeDate', function(e) {
            $('#cycle').val('自定义');
        });

        $('#cycle').on('change',function () {
            var v = $(this).val();
            switch (v){
                case '昨天' :
                    $('#dpd1').val(moment().subtract(1,'days').format('YYYY-MM-DD'));
                    $('#dpd2').val(moment().subtract(1,'days').format('YYYY-MM-DD'));
                    break;
                case '最近7日' :
                    $('#dpd1').val(moment().subtract(7,'days').format('YYYY-MM-DD'));
                    $('#dpd2').val(moment().subtract(1,'days').format('YYYY-MM-DD'));
                    break;
                case '本周' :
                    $('#dpd1').val(moment().startOf('weeks').format('YYYY-MM-DD'));
                    $('#dpd2').val(moment().subtract(1,'days').format('YYYY-MM-DD'));
                    break;
                case '上周' :
                    $('#dpd1').val(moment().subtract(1,'weeks').startOf('weeks').format('YYYY-MM-DD'));
                    $('#dpd2').val(moment().subtract(1,'weeks').endOf('weeks').format('YYYY-MM-DD'));
                    break;
                case '最近30日' :
                    $('#dpd1').val(moment().subtract(30,'days').format('YYYY-MM-DD'));
                    $('#dpd2').val(moment().subtract(1,'days').format('YYYY-MM-DD'));
                    break;
                case '本月' :
                    $('#dpd1').val(moment().startOf('month').format('YYYY-MM-DD'));
                    $('#dpd2').val(moment().subtract(1,'days').format('YYYY-MM-DD'));
                    break;
                case '上月' :
                    $('#dpd1').val(moment().subtract(1,'month').startOf('month').format('YYYY-MM-DD'));
                    $('#dpd2').val(moment().subtract(1,'month').endOf('month').format('YYYY-MM-DD'));
                    break;
            }
        })
        //二级联动
        var title1	= ['<option value="">地域</option>' , '<option value="">社区</option>' ];
        var title2	= ['<option value="">全部</option>' , '<option value="">全部</option>' ];
        $.getJSON(dataAPIUrl+proxy("jiashicang_shaixuanweidu?dimension=city"),{},function (data) {
            if(data.code!=0){
//                swal("提示",data.errorMsg, "success");
            }else{
                var city = data.data,HTML=title1[0];
                city.forEach(function (c,index) {
                    //其他选项不显示
                    if(c.key && c.key != '9999'){
                        HTML += "<option value='"+c.key+"'>"+c.value+"</option>";
                    }
                })
                $('#city').empty().append(HTML);
            }
        })
        $.getJSON(dataAPIUrl+proxy("jiashicang_shaixuanweidu?dimension=c1"),{},function (data) {
            if(data.code!=0){
//                swal("提示",data.errorMsg, "success");
            }else{
                var first = data.data,HTML=title2[0];
                first.forEach(function (c,index) {
                    if(c.key){
                        HTML += "<option value='"+c.key+"'>"+c.value+"</option>";
                    }
                })
                $('#first').empty().append(HTML);
            }
        })
        $('#community').select2({

        });
        $('#city,#first,#second').select2({
            minimumResultsForSearch: -1
        })
        $('#city').change(function(e) {
            $('#community').empty().append(title1[1]);
            //重新填充社区option
            var cV = $(this).val();
            $.getJSON(dataAPIUrl+proxy("jiashicang_shaixuanweidu?dimension=project&condition="+cV),{},function (data) {
                if(data.code!=0){
//                    swal("提示",data.errorMsg, "success");
                }else{
                    var community = data.data,HTML=title1[1];
                    community.forEach(function (c,index) {
                        if(c.project_id){
                            HTML += "<option value='"+c.project_id+"'>"+c.project_name+"</option>";
                        }
                    })
                    $('#community').empty().append(HTML);
                }
            })
        })

        $('#first').change(function() {
            $('#second').empty().append(title2[1]);
            //重新填充二级品类option
            var fVal = $(this).val();
            $.getJSON(dataAPIUrl+proxy("jiashicang_shaixuanweidu?dimension=c2&condition="+fVal),{},function (data) {
                if(data.code!=0){
//                    swal("提示",data.errorMsg, "success");
                }else{
                    var second = data.data,HTML=title2[1];
                    second.forEach(function (c,index) {
                        if(c.key){
                            HTML += "<option value='"+c.key+"'>"+c.value+"</option>";
                        }
                    })
                    $('#second').empty().append(HTML);
                }
            })
        })


        //获取数据
        getData();

    });

    var datarange,city,community,first,second;
    function getFiltrateParam() {
        datarange = [];
        datarange.push($('#dpd1').val());
        datarange.push($('#dpd2').val());
        city = $('#city').val();
        community = $('#community').val();
        first = $('#first').val();
        second = $('#second').val();

        return {
            region: city,
            project: community,
            start_date: datarange[0],
            end_date: datarange[1],
            c1_name: first,
            c2_name: second
        }
    }
    function testAnim(x,$id) {
        $($id).removeClass(x + ' animated').addClass(x + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
            $(this).removeClass(x + ' animated');
        });
    };


</script>
</body>
</html>