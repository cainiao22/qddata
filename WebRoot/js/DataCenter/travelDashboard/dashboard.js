var CommonUtils = {

    /** 合并列，echarts图表使用 **/
    convertColumn : function(rows) {
            var map = {};
            if(rows == null){
                return map;
            }
            for(var key in rows[0]){
                map[key] = [];
            }
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                for(var key in row){
                    map[key].push(row[key]);
                }
            }
            return map;
        }
}

var groupKey = {'商品': 'ware_name',
                '品类': 'c2_name',
                '社区': 'project_name',
                '城市': 'region_name'};
var sortKey = 'sale_price';

var dataAPIUrl = '';
var dataAPIDomain = '';
var userAPI = "";
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
    if(location.origin === 'http://qddata.qdingnet.com'){
        dataAPIDomain = "http://yushanfang.bigdata.qdingnet.com/";

    }else if(location.origin === 'http://qa-qddata.bigdata.qdingnet.com'){
        dataAPIDomain = "http://qa-yushanfang.bigdata.qdingnet.com/";
    }else{
        dataAPIDomain = "http://dev-yushanfang.bigdata.qdingnet.com/";
    }

    return encodeURIComponent(dataAPIDomain + "dataApiQuery/" + str);
}
getlocation();

function setOrderGraph(rows) {
    var myChart = echarts.init(document.getElementById('echarts1'),'');
    /** 折线图 **/
    var option = {
        tooltip : {

            trigger: 'axis'

        },
        legend: {

            data:['订单数','退单数'],
            textStyle:{    //图例文字的样式
                color:'#fff'
            }
        },
        xAxis: {
            type: 'category',
            data: rows.dt,
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#fff'
                }
            }
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#fff'
                }
            }
        },
        series: [
                {
                    name: '订单数',
                    data: rows.pay_order_num,
                    type: 'line',
                    itemStyle: {
                        normal: {
                            "color": "rgba(0,191,183,1)"
                        }
                    },
                },
                {
                    name: '退单数',
                    data: rows.refund_num,
                    type: 'line',
                    itemStyle: {
                        normal: {
                            "color": "rgba(255,144,128,1)"
                        }
                    },
                }
            ]
    };
    myChart.setOption(option);
}

function setSellGraph(rows) {
    var myChart = echarts.init(document.getElementById('echartSell'),'');
    /** 堆积图 **/
    var option = {

        /*title : {

            text: '某楼盘销售情况',

            subtext: '纯属虚构'

        },*/
        grid: {
            left: '12%',
        },
        tooltip : {

            trigger: 'axis'

        },

        legend: {

            data:['销售额','退款额'],
            textStyle:{    //图例文字的样式
                color:'#fff'
            }

        },

        toolbox: {

            show : false,

            feature : {

                mark : {show: true},

                dataView : {show: true, readOnly: false},

                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},

                restore : {show: true},

                saveAsImage : {show: true}

            }

        },

        calculable : true,

        xAxis: {
            type : 'category',
            boundaryGap : false,
            data : rows.dt,
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#fff'
                }
            }
        }
        ,

        yAxis: {
                type : 'value',
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#fff'
                    }
                }
            },

        series : [
            {

                name: '销售额',

                type: 'line',

                smooth: true,

                itemStyle: {
                    normal: {
                        areaStyle: {"color": "rgba(0,191,183,1)"},
                        "color": "rgba(0,191,183,1)"
                    }
                },

                data: rows.sale_price

            },

            {

                name:'退款额',

                type:'line',

                smooth:true,

                itemStyle: {
                    normal: {
                        areaStyle: {"color": "rgba(255,144,128,1)"},
                        "color": "rgba(255,144,128,1)"
                    }
                },

                data: rows.refund_price

            }

        ]

    };
    myChart.setOption(option);
}

function setCustomerGraph(rows) {
    var myChart = echarts.init(document.getElementById('echartsCustomer'),'');
    /** 柱状图 **/
    var option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function(datas)
            {
                var res = datas[0].name + '<br/>', sum = 0;
                for(var i = 0, length = datas.length; i < length; i++) {
                    sum += datas[i].value;
                    res += datas[i].seriesName + '：' + datas[i].value + '<br/>';
                }
                res += '购买人数: ' + sum + '<br/>';
                return res;
            }
        },
        legend: {
            data: [ '新客户','老客户'],
            textStyle:{    //图例文字的样式
                color:'#fff'
            }
        },
        /*grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },*/
        xAxis:  {
            type: 'category',
            data: rows.dt,
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#fff'
                }
            }
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#fff'
                }
            }
        },
        series: [
            {
                name: '老客户',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        //show: false,
                        position: 'insideRight'
                    }
                },
                data: rows.old_mem_num,
                itemStyle: {
                    normal: {
                        "color": "rgba(0,191,183,1)",
                    }
                },
            },
            {
                name: '新客户',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        //show: true,
                        position: 'insideRight'
                    }
                },
                data: rows.new_mem_num,
                itemStyle: {
                    normal: {
                        "color": "rgba(255,144,128,1)",
                    }
                },
            },
        ]
    };
    myChart.setOption(option);
}

function setPayGraph(rows) {
    /** 折线图 **/
    var myChart = echarts.init(document.getElementById('echartsPay'),'');
    /** 柱状图+折线图 **/
    var option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'cross'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['未支付订单数', '支付订单数','订单支付率'],
            textStyle:{    //图例文字的样式
                color:'#fff'
            }
        },
        grid: {
            right: '12%',
            left: '12%',
            /*bottom: '3%',
            containLabel: true*/
        },
        xAxis:  {
            type: 'category',
            data: rows.dt,
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#fff'
                }
            }
        },
        yAxis: [{
            type: 'value',
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#fff'
                },
            },
            splitLine:{
                show:false
            },
        },
        {
            type: 'value',
            min: 0,
            max: 100,
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#fff'
                },
                formatter: '{value} %'
            }
        }
        ],
        series: [
            {
                name: '支付订单数',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        //show: true,
                        position: 'insideRight'
                    }
                },
                yAxisIndex: 0,
                data: rows.pay_order_num,
                itemStyle: {
                    normal: {
                        "color": "rgba(0,191,183,1)",
                    }
                },
            },
            {
                name: '未支付订单数',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        //show: true,
                        position: 'insideRight'
                    }
                },
                yAxisIndex: 0,
                data: rows.unpay_order_num,
                itemStyle: {
                    normal: {
                        "color": "rgba(255,144,128,1)",
                    }
                },
            },
            {
                name: '订单支付率',
                type: 'line',
                label: {
                    normal: {
                        //show: true,
                        position: 'insideRight'
                    }
                },
                yAxisIndex: 1,
                data: rows.pay_rate,
                itemStyle: {
                    normal: {
                        "color": "rgba(252,230,48,1)",
                        label:{
                            formatter: '{c}%'
                        }
                    }
                },
            }

        ]
    };
    myChart.setOption(option);
}

function setTopTenTable() {
    $('#tblTopTen').bootstrapTable('removeAll');
    var params = getFiltrateParam();
    params.group_key = groupKey[$("#groupKey").val()] || 'region_name';
    params.sort_key = sortKey || 'sale_price';
    var paramsStr = $.param(params);
    $('#tblTopTen').bootstrapTable({
        //请求方法
        method: 'get',
        //是否显示行间隔色
        striped: true,
        //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        cache: false,
        //是否显示分页（*）
        pagination: true,
        //是否启用排序
        sortable: false,
        //排序方式
        //初始化加载第一页，默认第一页
        //我设置了这一项，但是貌似没起作用，而且我这默认是0,- -
        //pageNumber:1,
        //每页的记录行数（*）
        pageSize: 10,
        //可供选择的每页的行数（*）
        pageList: [10, 25, 50, 100],
        //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
        url: dataAPIUrl+proxy("lvyou_jiashicang_topn?"+paramsStr),
        //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
        //queryParamsType:'',
        ////查询参数,每次调用是会带上这个参数，可自定义
        queryParams : function() {

            /*return {
                start_date: '2018-03-27',
                end_date: '2018-03-27',
                c1_name: null,
                c2_name: null,
                group_key: groupKey[$("#groupKey").val()] || 'region_name',
                sort_key: sortKey
            };*/
        },
        //分页方式：client客户端分页，server服务端分页（*）
        sidePagination: "server",
        //是否显示搜索
        search: false,
        //Enable the strict search.
        strictSearch: true,
        //Indicate which field is an identity field.
        idField : "group_key",
        responseHandler: function (json) {
            testAnim("bounceInRight", '#echartsTopTen');
            $("#tblTopTen ").find("tr:eq(0)").find("div:eq(0)").html($("#groupKey").val());
            return {rows: json.data,
                    total: 10}
        },
        columns: [
            {
                field: 'group_key',
                title: $("#groupKey").val(),
                align: 'center'
            },
            {
                field: 'sale_price',
                title: '销售额',
                align: 'center'
            },
            {
                field: 'pay_order_num',
                title: '订单量',
                align: 'center'
            },

            {
                field: 'refund_num',
                title: '退单量',
                align: 'center'
            },
            {
                field: 'refund_price',
                title: '退款额',
                align: 'center'
            }


        ],
        formatLoadingMessage: function(){
            return '';
        },
        pagination:false
    });
    $('#tblTopTen').bootstrapTable('refresh');
}

$(document).ready(function () {
    $("#groupKey").change(function () {
        var params = getFiltrateParam();
        params.group_key = groupKey[$("#groupKey").val()] || 'region_name';
        params.sort_key = sortKey || 'sale_price';
        var paramsStr = $.param(params);
        $('#tblTopTen').bootstrapTable("refresh",{
            url:dataAPIUrl+proxy("lvyou_jiashicang_topn?"+paramsStr)
        });
    });
});

function getData() {
    var param = getFiltrateParam();
    var url =  dataAPIUrl+proxy("lvyou_jiashicang_qushi?"+$.param(param));
    var bannerurl = dataAPIUrl+proxy("lvyou_jiashicang_banner?"+$.param(param));
    /*var param = {
     region: null,
     project: null,
     start_date: '2018-03-26',
     end_date: '2018-03-29',
     c1_name: null,
     c2_name: null
     };*/
    $.getJSON(url, function (json) {
        if(json.code == 0 || json.code == 100){
            var rows = CommonUtils.convertColumn(json.data);
            console.log(JSON.stringify(rows));
            setOrderGraph(rows);
            setSellGraph(rows);
            setCustomerGraph(rows);
            setPayGraph(rows);
        }
    });
    $.getJSON(bannerurl, function (json) {
        if(json.code == 0){
            var data = json.data[0];
            if(data.sale_price){
                $('#saleNum').animateNumber({ number: data.sale_price});
            }else{
                $('#saleNum').text("0");
            }
            if(data.pay_order_num){
                $('#orderNum').animateNumber({ number: data.pay_order_num});
            }else{
                $('#orderNum').text("0");
            }
            if(data.total_mem_num){
                $('#buyNum').animateNumber({ number: data.total_mem_num});
            }else{
                $('#buyNum').text("0");
            }
            if(data.refund_price){
                $('#refundNum').animateNumber({ number: data.refund_price});
            }else{
                $('#refundNum').text("0");
            }
        }
    });
    setTopTenTable();
}


function resortTable(elem) {
    sortKey = $(elem).val();
    if($("#a1").hasClass("active")){
        $("#a1").removeClass("active");
    }
    var params = getFiltrateParam();
    params.group_key = groupKey[$("#groupKey").val()] || 'region_name';
    params.sort_key = sortKey || 'sale_price';
    var paramsStr = $.param(params);
    $('#tblTopTen').bootstrapTable("refresh",{
        url:dataAPIUrl+proxy("lvyou_jiashicang_topn?"+paramsStr)
    });
}