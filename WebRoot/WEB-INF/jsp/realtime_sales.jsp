<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<!-- BEGIN HEAD -->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>千丁大数据平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width"/>

    <link href="./assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN THEME GLOBAL STYLES -->
    <link href="./assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css"/>
    <link href="./assets/global/css/plugins.min.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME GLOBAL STYLES -->
    <!-- BEGIN THEME LAYOUT STYLES -->
    <link href="./assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css"/>
    <link href="./assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="./assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css"/>
    <style>
    .label-info {
        background-color: green;
    }
    </style>
</head>
<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
<div class="page-wrapper">
    <!-- BEGIN CONTAINER -->
    <div class="page-container" style="margin-left:0;padding:5x;margin:0;">
        <!-- BEGIN CONTENT -->
        <div class="page-content-wrapper">
            <!-- BEGIN CONTENT BODY -->
            <div class="page-content" style="margin:0;padding:0px 0px 0px;">

                <div class="row" style="padding: 0;">
                    <div class="col-md-12" style="padding: 0;">
                        <div class="portlet box blue">
                            <div class="portlet-title">
                                <div class="caption">
                                    <h1>今日实时数据</h1>
                                    <h3 id="maxRealtime"></h3>
                                    <h6>对比值为近30日的日均值</h6>
                                </div>
                            </div>
                            <div class="portlet-body" style="padding: 0;">

                                <div class="tabbable-custom  ">
                                    <ul class="nav nav nav-tabs   ">
                                        <li class="active" style="width:40%;text-align: center;">
                                            <a href="#tab_1" data-toggle="tab"> 订单金额</br>
                                                <div id="total_price"></div>
                                                <div id="total_price_rate" class="label label-sm"
                                                     style="line-height:2"></div>
                                            </a>
                                        </li>
                                        <li style="width:28%;text-align: center;">
                                            <a href="#tab_2" data-toggle="tab"> 订单量</br>
                                                <div id="order_num"></div>
                                                <div id="order_num_rate" class="label label-sm"
                                                     style="line-height:2"></div>
                                            </a>
                                        </li>
                                        <li style="width:28%;text-align: center;">
                                            <a href="#tab_3" data-toggle="tab">购买人数</br>
                                                <div id="member_num"></div>
                                                <div id="member_num_rate" class="label label-sm"
                                                     style="line-height:2"></div>
                                            </a>
                                        </li>

                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="tab_1">
                                            <div id="c1"></div>
                                            <h4>实时销售额TOP10</h4>
                                            <div class="tabbable-line tabs-below">
                                                <ul class="nav nav-tabs">
                                                    <li class="active" style="width:33%;text-align: center;">
                                                        <a href="#tab_1_1" data-toggle="tab"> 城市 </a>
                                                    </li>
                                                    <li style="width:33%;text-align: center;">
                                                        <a href="#tab_1_2" data-toggle="tab"> 业态 </a>
                                                    </li>
                                                    <li style="width:33%;text-align: center;">
                                                        <a href="#tab_1_3" data-toggle="tab"> 商品 </a>
                                                    </li>
                                                </ul>


                                                <div class="tab-content">
                                                    <div class="tab-pane active" id="tab_1_1">
                                                        <div id="c4"></div>
                                                    </div>
                                                    <div class="tab-pane" id="tab_1_2">
                                                        <div id="c5"></div>
                                                    </div>
                                                    <div class="tab-pane" id="tab_1_3">
                                                        <div class="table-scrollable table-scrollable-borderless">
                                                            <table class="table table-hover table-light">
                                                                <thead>
                                                                <tr class="uppercase">
                                                                    <th>商品</th>
                                                                    <th>销售额</th>
                                                                    <th>排名</th>
                                                                </tr>
                                                                </thead>
                                                                <tbody id="c6">

                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="tab-pane" id="tab_2">
                                            <div id="c2"></div>
                                            <h4>实时订单量TOP10</h4>
                                            <div class="tabbable-line tabs-below">
                                                <ul class="nav nav-tabs">
                                                    <li class="active" style="width:33%;text-align: center;">
                                                        <a href="#tab_2_1" data-toggle="tab"> 城市 </a>
                                                    </li>
                                                    <li style="width:33%;text-align: center;">
                                                        <a href="#tab_2_2" data-toggle="tab"> 业态 </a>
                                                    </li>
                                                    <li style="width:33%;text-align: center;">
                                                        <a href="#tab_2_3" data-toggle="tab"> 商品 </a>
                                                    </li>
                                                </ul>


                                                <div class="tab-content">
                                                    <div class="tab-pane active" id="tab_2_1">
                                                        <div id="c7"></div>
                                                    </div>
                                                    <div class="tab-pane" id="tab_2_2">
                                                        <div id="c8"></div>
                                                    </div>
                                                    <div class="tab-pane" id="tab_2_3">
                                                        <div class="table-scrollable table-scrollable-borderless">
                                                            <table class="table table-hover table-light">
                                                                <thead>
                                                                <tr class="uppercase">
                                                                    <th>商品</th>
                                                                    <th>订单量</th>
                                                                    <th>排名</th>
                                                                </tr>
                                                                </thead>
                                                                <tbody id="c9">
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="tab-pane" id="tab_3">
                                            <div id="c3"></div>
                                            <h4>实时购买人数TOP10</h4>
                                            <div class="tabbable-line tabs-below">
                                                <ul class="nav nav-tabs">
                                                    <li class="active" style="width:33%;text-align: center;">
                                                        <a href="#tab_3_1" data-toggle="tab"> 城市 </a>
                                                    </li>
                                                    <li style="width:33%;text-align: center;">
                                                        <a href="#tab_3_2" data-toggle="tab"> 业态 </a>
                                                    </li>
                                                    <li style="width:33%;text-align: center;">
                                                        <a href="#tab_3_3" data-toggle="tab"> 商品 </a>
                                                    </li>
                                                </ul>


                                                <div class="tab-content">
                                                    <div class="tab-pane active" id="tab_3_1">
                                                        <div id="c10"></div>
                                                    </div>
                                                    <div class="tab-pane" id="tab_3_2">
                                                        <div id="c11"></div>
                                                    </div>
                                                    <div class="tab-pane" id="tab_3_3">
                                                        <div class="table-scrollable table-scrollable-borderless">
                                                            <table class="table table-hover table-light">
                                                                <thead>
                                                                <tr class="uppercase">
                                                                    <th>商品</th>
                                                                    <th>购买人数</th>
                                                                    <th>排名</th>
                                                                </tr>
                                                                </thead>
                                                                <tbody id="c12">
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <!-- END CONTENT BODY -->
        </div>
        <!-- END CONTENT -->
    </div>
    <!-- END CONTAINER -->
</div>
<!--[if lt IE 9]>
<script src="./assets/global/plugins/respond.min.js"></script>

<script src="./assets/global/plugins/ie8.fix.min.js"></script>
<![endif]-->
<!-- BEGIN CORE PLUGINS -->
<script src="./assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="./assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL SCRIPTS -->
<script src="./assets/global/scripts/app.min.js" type="text/javascript"></script>
<!-- END THEME GLOBAL SCRIPTS -->
<!-- BEGIN THEME LAYOUT SCRIPTS -->
<script src="./assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
<script src="./assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
<script src="./assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
<script src="./assets/layouts/global/scripts/quick-nav.min.js" type="text/javascript"></script>
<script src="./js/echarts.common.min.js" type="text/javascript"></script>
<!-- END THEME LAYOUT SCRIPTS -->

<script type="text/javascript">

    $(function () {
        function cutstr(str, len) {
            var str_length = 0;
            var str_len = 0;
            str_cut = new String();
            str_len = str.length;
            for (var i = 0; i < str_len; i++) {
                a = str.charAt(i);
                str_length++;
                if (escape(a).length > 4) {
                    //中文字符的长度经编码之后大于4
                    str_length++;
                }
                str_cut = str_cut.concat(a);
                if (str_length >= len) {
                    str_cut = str_cut.concat("...");
                    return str_cut;
                }
            }
            //如果给定字符串小于指定长度，则返回源字符串；
            if (str_length < len) {
                return str;
            }
        }

        function qfw(num) {
            num = num.toString().replace(/\$|\,/g, '');
            if (isNaN(num))
                num = "0";
            sign = (num == (num = Math.abs(num)));
            num = Math.floor(num * 100 + 0.50000000001);
            cents = num % 100;
            num = Math.floor(num / 100).toString();
            if (cents < 10)
                cents = "0" + cents;
            for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
                num = num.substring(0, num.length - (4 * i + 3)) + ',' +
                    num.substring(num.length - (4 * i + 3));
            return (((sign) ? '' : '-') + num + ( cents != '00' ? ('.' + cents) : ''));
        }

        var maxRealTime;

        $.ajax({
            type: "get",
            url: "../dataApiQuery/getMaxRealTime",
            async: false,
            success: function (res) {
                maxRealTime = res.data[0].dt;
            }
        });
        $('#maxRealtime').html(maxRealTime.substr(8, 2) + ":" + maxRealTime.substr(10) + '更新');
        var width = $('.portlet-body').width() * 0.9;
        // 基于准备好的dom，初始化echarts实例
        function initChart1(chartid, data1, data2, title, legend, seriesName) {
            $('#' + chartid).css('width', width);
            $('#' + chartid).css('height', '300px');
            var elm = document.getElementById(chartid)
            var c = echarts.init(elm);
            // 指定图表的配置项和数据
            var option = {
                title: {
                    show: true,
                    text: title
                },
                grid: {
                    top: "30",
                    right: "0",
                    left: '20%'
                },
                tooltip: {
                    trigger: 'axis'

                },
                legend: {
                    data: legend
                },
                xAxis: {
                    data: []
                },
                label: {
                    /*    	 emphasis:{
                     show:true,
                     formatter:"{b}时:{c}元"
                     } */
                },
                yAxis: {},
                series: [{
                    name: seriesName,
                    type: 'line',
                    data: []
                }, {
                    name: '历史均值',
                    type: 'line',
                    data: [],
                    lineStyle: {
                        normal: {
                            type: 'dotted'
                        }
                    }
                }]
            };
            for (var i = 0; i < 24; i++) {
                option.xAxis.data.push(i);
                if (i < data1.length) {
                    var row = data1[i];
                    option.series[0].data.push(row.value);
                }
                if (i < data2.length) {
                    var row = data2[i];
                    option.series[1].data.push(row.value);
                }
            }
            c.setOption(option);
        }

        function initChart2(chartid, data, title, legend, seriesName) {
            $('#' + chartid).css('width', width);
            $('#' + chartid).css('height', '300px');
            var elm = document.getElementById(chartid)
            var c = echarts.init(elm);
            // 指定图表的配置项和数据
            var option = {
                title: {
                    show: false,
                    text: title
                },
                grid: {
                    top: "10",
                    right: "0",
                    left: '20%'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: legend
                },
                xAxis: {
                    type: 'value',
                    name: '小时'
                },
                label: {
                    /*    	 emphasis:{
                     show:true,
                     formatter:"{b}时:{c}元"
                     } */
                },
                yAxis: {
                    type: 'category',
                    data: []
                },
                series: [{
                    name: seriesName,
                    type: 'bar',
                    data: [],
                    legendHoverLink: true
                }]
            };
            for (var i = data.length - 1; i >= 0; i--) {
                var row = data[i];
                option.yAxis.data.push(cutstr(row.key, 8));
                option.series[0].data.push(row.value);

            }
            c.setOption(option);
        }

        $.post('../dataApiQuery/realtimeOrderAggResult', {dt: maxRealTime}, function (res){

            if (null == res.data || res.data.length < 0) {
                return false;
            }
            var row = res.data[0];
            $('#total_price').html(qfw(row.total_price));
            $('#total_price_rate').html((row.total_price_rate > 0 ? "+" : '') + row.total_price_rate + "%");
            $('#total_price_rate').addClass(row.total_price_rate > 0 ? 'label-danger' : 'label-info')
            $('#order_num').html(qfw(row.order_num));
            $('#order_num_rate').html((row.order_num_rate > 0 ? "+" : '') + row.order_num_rate + "%");
            $('#order_num_rate').addClass(row.order_num_rate > 0 ? 'label-danger' : 'label-info')
            $('#member_num').html(qfw(row.member_num));
            $('#member_num_rate').html((row.order_num_rate > 0 ? "+" : '') + row.member_num_rate + "%");
            $('#member_num_rate').addClass(row.member_num_rate > 0 ? 'label-danger' : 'label-info')


        });
        $.post('../dataApiQuery/realTimeOrderTend', {
            dt: maxRealTime,
            value: 'sum(total_price )',
            table: 'rt_order_sub',
            product_no: 'productno'
        }, function (res1) {
            $.post('../dataApiQuery/realTimeOrderHistoryTend', {type: 'total_payment'}, function (res2) {
                initChart1('c1', res1.data, res2.data, '实时销售额趋势', '销售额', '销售额');

            });

        });
        $.post('../dataApiQuery/realTimeOrderTend', {
            dt: maxRealTime,
            value: 'count(distinct parent_code)',
            table: 'rt_order_sub',
            product_no: 'productno'
        }, function (res1) {
            $.post('../dataApiQuery/realTimeOrderHistoryTend', {type: 'order_num'}, function (res2) {
                initChart1('c2', res1.data, res2.data, '实时订单量趋势', '订单量', '订单量');
            });
        });
        $.post('../dataApiQuery/realTimeOrderTend', {
            dt: maxRealTime,
            value: 'count( DISTINCT mid)',
            table: 'rt_order_base',
            product_no: 'product_no'
        }, function (res1) {
            $.post('../dataApiQuery/realTimeOrderHistoryTend', {type: 'member_num'}, function (res2) {
                initChart1('c3', res1.data, res2.data, '实时购买人数趋势', '购买人数', '购买人数');
            });
        });

        $.post('../dataApiQuery/realTimeOrderCityRank', {
            dt: maxRealTime,
            value: 'sum(total_price )',
            table: 'rt_order_sub',
            product_no: 'productno'
        }, function (res) {
            initChart2('c4', res.data, '', '销售额', '销售额');
        });
        $.post('../dataApiQuery/realTimeOrderProductRank', {
            dt: maxRealTime,
            value: 'sum(total_price )',
            table: 'rt_order_sub',
            product_no: 'productno'
        }, function (res) {
            initChart2('c5', res.data, '', '销售额', '销售额');
        });
        $.post('../dataApiQuery/realTimeOrderWareRank', {
            dt: maxRealTime,
            value: 'sum(total_price )',
            table: 'rt_order_sub',
            product_no: 'productno'
        }, function (res) {
            $('#c6').empty();
            for (var i = 0; i < res.data.length; i++) {
                var row = res.data[i];
                $('#c6').append('<tr><td>' + row.key + '</td><td>' + qfw(row.value) + '</td><td><span class="label label-sm label-success">' + (parseInt(i) + 1) + '</span></td></tr>');

            }
        });


        $.post('../dataApiQuery/realTimeOrderCityRank', {
            dt: maxRealTime,
            value: 'count(distinct parent_code)',
            table: 'rt_order_sub',
            product_no: 'productno'
        }, function (res) {
            initChart2('c7', res.data, '', '订单量', '订单量');
        });
        $.post('../dataApiQuery/realTimeOrderProductRank', {
            dt: maxRealTime,
            value: 'count(distinct parent_code)',
            table: 'rt_order_sub',
            product_no: 'productno'
        }, function (res) {
            initChart2('c8', res.data, '', '订单量', '订单量');
        });
        $.post('../dataApiQuery/realTimeOrderWareRank', {
            dt: maxRealTime,
            value: 'count(distinct parent_code)',
            table: 'rt_order_sub',
            product_no: 'productno'
        }, function (res) {
            $('#c9').empty();
            for (var i = 0; i < res.data.length; i++) {
                var row = res.data[i];
                $('#c9').append('<tr><td>' + row.key + '</td><td>' + qfw(row.value) + '</td><td><span class="label label-sm label-success">' + (parseInt(i) + 1) + '</span></td></tr>');

            }
        });

        $.post('../dataApiQuery/realTimeOrderCityRank', {
            dt: maxRealTime,
            value: 'count( DISTINCT mid)',
            table: 'rt_order_base',
            product_no: 'product_no'
        }, function (res) {
            initChart2('c10', res.data, '', '购买人数', '购买人数');
        });
        $.post('../dataApiQuery/realTimeOrderMemNum', {dt: maxRealTime, key: 'product_name'}, function (res) {
            initChart2('c11', res.data, '', '购买人数', '购买人数');
        });
        $.post('../dataApiQuery/realTimeOrderMemNum', {dt: maxRealTime, key: 'ware_name'}, function (res) {
            $('#c12').empty();
            for (var i = 0; i < res.data.length; i++) {
                var row = res.data[i];
                $('#c12').append('<tr><td>' + row.key + '</td><td>' + qfw(row.value) + '</td><td><span class="label label-sm label-success">' + (parseInt(i) + 1) + '</span></td></tr>');

            }
        });
        $(window).resize(function () {
            $(charts).each(function (index, chart) {
                chart.resize();
            });
        });
    });

</script>
</body>
</html>