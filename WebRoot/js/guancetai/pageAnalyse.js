/**
 * 观测台--页面分析
 */
var businessType = $(".tabControl > a > .active").attr("data-type");//千丁 or 丁管家
var startdate = '',enddate = '';

var param = {
    "queryDate": getCurrentDay(),
}

$(document).ready(function () {
    //埋点
    var obj = {
        "eventType": ["pageAnalyse"],
    }
    if(businessType == 0){
        obj.eventType.push('qding');
    }else{
        obj.eventType.push('dgj');
    }
    getPointParameter(obj);

    startupTimes(businessType);
    startupUser(businessType);
    onlineTime(businessType);
    avgStartTimes(businessType);

    setPageName(businessType);
    $('.c-datepicker-data-input').val(getCurrentDay());
    //年月日范围
    $('.J-datepicker-range-day').datePicker({
        hasShortcut: true,
        isRange: true,
        show: calendarshow,
        min:"2018-11-01",
        max: dayHandle(getCurrentDay(),-1),
        shortcutOptions: [{
            name: '今天',
            day: '0,0'
        },{
            name: '昨天',
            day: '-1,-1'
        }, {
            name: '过去7天',
            day: '-7,-1'
        }, {
            name: '过去30天',
            day: '-30,-1'
        }],
        hide: function (type) {
            this.$input.eq(0).val(this.$input.eq(0).val().substr(0, 10));
            this.$input.eq(1).val(this.$input.eq(1).val().substr(0, 10));
            startdate = this.$input.eq(0).val();
            enddate = this.$input.eq(1).val();
            $(".tab_1 > div,.tab_2 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
            //埋点
            var obj = {
                "eventType": ["dateSelect"],
            }
            getPointParameter(obj);

            dataStatisticSelect("qst");
        }
    });
    startdate = getCurrentDay();
    enddate = getCurrentDay();

    rendmapshow("user", 1);
    tableShow("huizong", 1);
});

//日历显示时
function calendarshow(){
    $('.c-datepicker-picker').css({'left':'initial','right':'25px'});
    $('.c-datepicker-date-range-picker__editor-wrap').eq(1).css('display', 'none');
    $('.c-datepicker-date-range-picker__editor-wrap').eq(3).css('display', 'none');
}

//页面访问用户数
function startupUser(bType) {
    param.businessType=bType;
    param.source = ($("#appType option:selected").val()=="all") ? "" :$("#appType option:selected").val();
    param.deviceStatus =($("#userType option:selected").val()=="all") ? "" :$("#userType option:selected").val();
    $.ajax({
        url: "./getPageStartupUsersInfo",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(param),
        success: function (res) {
            if (res) {
                pageShow(res, "#startUser");
            }
        }
    });
}
//页面人均停留时长
function avgStartTimes(bType) {
    param.businessType=bType;
    param.source = ($("#appType option:selected").val()=="all") ? "" :$("#appType option:selected").val();
    param.deviceStatus =($("#userType option:selected").val()=="all") ? "" :$("#userType option:selected").val();
    $.ajax({
        url: "./getPageAvgUserOnlineTimeInfo",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(param),
        success: function (res) {
            if (res) {
                pageShow(res,"#avg_startTime","three");
            }
        }
    });
}
//页面访问次数
function startupTimes(bType) {
    param.businessType=bType;
    param.source = ($("#appType option:selected").val()=="all") ? "" :$("#appType option:selected").val();
    param.deviceStatus =($("#userType option:selected").val()=="all") ? "" :$("#userType option:selected").val();
    $.ajax({
        url: "./getPageStartupTimesInfo",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(param),
        success: function (res) {
            if (res) {
                pageShow(res,"#startTimes");
            }
        }
    });
}
//页面次均停留时长
function onlineTime(bType) {
    param.businessType=bType;
    param.source = ($("#appType option:selected").val()=="all") ? "" :$("#appType option:selected").val();
    param.deviceStatus =($("#userType option:selected").val()=="all") ? "" :$("#userType option:selected").val();
    $.ajax({
        url: "./getPageAvgOnlineTimeInfo",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(param),
        success: function (res) {
            if (res) {
                pageShow(res,"#onlineTime","three");
            }
        }
    });
}

//趋势图展示
function rendmapshow(type){

    var myChart = echarts.init(document.getElementById('trendEchart'));
    var opt = line_option;
    myChart.showLoading({
        text: ' ',
        effect: 'whirling',
        color: '#6F7E95',
        maskColor: '#FFFFFF',
    });
    myChart.clear();
    var appType = $("#appType_qst option:selected").val(),
        userType = $("#userType_qst option:selected").val(),
        pageId = $("#pageName option:selected").val();
    var params1 = {
        is_current: 1,
        business_type: businessType,
        pageid : pageId,
        source: appType,
        device_status:userType,
        start_day: startdate,
        end_day: enddate,
    }
    var params_2 = {
        businessType: businessType,
        deviceStatus: (userType == 'all'?'':userType),
        pageId: (pageId == 'all'?'':pageId),
        queryDate: enddate,
        source: (appType == 'all'?'':appType),
    }
    var urls = '',urls2='';
    //实时
    if(startdate == enddate && startdate == getCurrentDay()){
        if(type=="user"){
            urls = "./getPageStartupUsersInfoTrend";
        }else if(type=="count"){
            urls = "./getPageStartupTimesInfoTrend";
        }else if(type=="avg_user_time"){
            urls = "./getPageAvgUserOnlineTimeInfoTrend";
        }else if(type=="avg_time"){
            urls = "./getPageSetupTimeOnlineInfoTrend";
        }
    }else{//离线
        urls = getLocation() + proxy("/dataApiQuery/gct_page_data_trend", params1);
    }
    //本期
    var prestartdate = '', preenddate = '',xdata = [];
    $.ajax({
        url: urls,
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify((startdate == enddate && startdate == getCurrentDay())?params_2:''),
        success: function (res) {
            if(startdate == enddate && startdate == getCurrentDay()){
                if(res){
                    var serises = [];
                    var canzhao = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
                    var list = datassort(canzhao, res, 'key');
                    if(list && list.length > 0){
                        for(var i=0; i<list.length; i++){
                            xdata.push(i+"点");
                            if(list[i]){
                                if(list[i].value){
                                    if(type=="user" || type=="count"){//不保留小数点
                                        serises.push(Number(list[i].value));
                                    }else if(type=="avg_user_time"){//保留两位小数点
                                        serises.push(Number(list[i].value).toFixed(3));
                                    }else if(type=="avg_time"){//保留三位小数点
                                        serises.push(Number(list[i].value).toFixed(3));
                                    }
                                }else{
                                    serises.push('0');
                                }
                            }else{
                                serises.push('');
                            }
                        }
                        opt.series[0].data = serises;
                        opt.xAxis.data = xdata;
                        opt.xAxis.axisLabel.interval = 0;
                        opt.xAxis.axisLabel.formatter = function(params){
                            return params;
                        };
                    }else{
                        opt.series[0].data =[];
                    }
                    opt.tooltip.formatter= '';
                    myChart.hideLoading();
                    myChart.setOption(opt,true);
                }else{
                    myChart.hideLoading();
                    myChart.setOption(opt,true);
                }
            }else{
                if(res && res.data){
                    var serises = [];
                    var list = res.data;
                    if(list && list.length>0){
                        prestartdate = list[0].pre_start_date;
                        preenddate = list[0].pre_end_date;
                        for(var i=0; i<list.length; i++){
                            if(list[i]){
                                if(type=="user"){
                                    serises.push(list[i]['visit_user_num']?list[i]['visit_user_num']:'0');
                                }else if(type=="count"){
                                    serises.push(list[i]['visit_num']?list[i]['visit_num']:'0');
                                }else if(type=="avg_user_time"){
                                    var num = list[i]['per_capita_startup_time'];
                                    if(num){
                                        serises.push(num.toFixed(3));
                                    }else{
                                        serises.push('0');
                                    }
                                }else if(type=="avg_time"){
                                    var num = list[i]['average_stop_time'];
                                    if(num){
                                        serises.push(num.toFixed(3));
                                    }else{
                                        serises.push('0');
                                    }
                                }
                            }else{
                                serises.push('');
                            }
                            xdata.push(list[i].dt?list[i].dt:'');
                        }
                        opt.tooltip.formatter= function(params){
                            var content = '';
                            for(var i=0; i<params.length; i++){
                                if(params[i].value && params[i].value!=''){
                                    content += '<p style="margin: 0;float:left;">'+ params[i].name +'  <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:'+params[i].color+'"></div><span style="margin-left: 5px">' + params[i].seriesName + '</span> : ' + params[i].value +  '</p>';
                                }
                            }
                            return '<div>'+ content + '</div>'
                        };
                        opt.series[0].data = serises;
                        opt.xAxis.data = xdata;
                        if(xdata.length >=10){
                            var n = (xdata.length / 10);
                            opt.xAxis.axisLabel.interval = Math.ceil(n);
                        }else{
                            opt.xAxis.axisLabel.interval =0;
                        }
                    }else{
                        opt.series[0].data =[];
                        opt.tooltip.formatter='';
                    }
                    myChart.hideLoading();
                    myChart.setOption(opt,true);
                }else{
                    myChart.hideLoading();
                    myChart.setOption(opt,true);
                }
            }
            //上期
            if(startdate == enddate && startdate == getCurrentDay()){
                params_2.queryDate = dayHandle(enddate, -1);
                if(type=="user"){
                    urls2 = "./getPageStartupUsersInfoTrend";
                }else if(type=="count"){
                    urls2 = "./getPageStartupTimesInfoTrend";
                }else if(type=="avg_user_time"){
                    urls2 = "./getPageAvgUserOnlineTimeInfoTrend";
                }else if(type=="avg_time"){
                    urls2 = "./getPageSetupTimeOnlineInfoTrend";
                }
            }else{
                params1.is_current = 0;
                params1.start_day = prestartdate;
                params1.end_day = preenddate;
                urls2 = getLocation() + proxy("/dataApiQuery/gct_page_data_trend", params1);
            }
            $.ajax({
                url: urls2,
                type: "post",
                dataType: "json",
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                data: JSON.stringify((startdate == enddate && startdate == getCurrentDay())?params_2:''),
                success: function (res) {
                    if(startdate == enddate && startdate == getCurrentDay()){
                        if (res) {
                            var serises2 = [];
                            var canzhao = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
                            var list2 = datassort(canzhao, res, 'key');
                            if(list2 && list2.length > 0){
                                for(var i=0; i<list2.length; i++){
                                    if(list2[i]){
                                        if(list2[i].value){
                                            if(type=="user" ||type=="count"){//不保留小数点
                                                serises2.push(Number(list2[i].value));
                                            }else if(type=="avg_user_time"){//保留两位小数点
                                                serises2.push(Number(list2[i].value).toFixed(3));
                                            }else if(type=="avg_time"){//保留三位小数点
                                                serises2.push(Number(list2[i].value).toFixed(3));
                                            }
                                        }else{
                                            serises2.push('0');
                                        }
                                    }else{
                                        serises2.push('0');
                                    }
                                }
                                opt.series[1].data = serises2;
                            }else{
                                opt.series[1].data =[];
                            }
                            opt.tooltip.formatter= '';
                            myChart.hideLoading();
                            myChart.setOption(opt,true);
                        }else{
                            myChart.hideLoading();
                            myChart.setOption(opt,true);
                        }
                    }else{
                        if(res && res.data){
                            var serises2 = [];
                            var realdt = [];
                            var list = datassort(xdata, res.data,'dt');
                            if(list && list.length>0){
                                for(var i=0; i<list.length; i++){
                                    realdt.push(list[i].real_dt?list[i].real_dt:"");
                                    if(list[i]) {
                                        if(type=="user"){
                                            serises2.push(list[i].visit_user_num?list[i].visit_user_num:'0');
                                        }else if(type=="count"){
                                            serises2.push(list[i].visit_num?list[i].visit_num:'0');
                                        }else if(type=="avg_user_time"){
                                            serises2.push(list[i].per_capita_startup_time?Number(list[i].per_capita_startup_time).toFixed(3):'0');
                                        }else if(type=="avg_time"){
                                            serises2.push(list[i].average_stop_time?Number(list[i].average_stop_time).toFixed(3):'0');
                                        }
                                    }else{
                                        serises2.push('0');
                                    }
                                }
                                opt.series[1].data = serises2;
                                opt.tooltip.formatter= function(params){
                                    var content = '';
                                    for(var i=0; i<params.length; i++){
                                        if(params[i].value && params[i].value!=''){
                                            if(i == 0){
                                                if(params[i].name){
                                                    content += '<div>'+ params[i].name +'  <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:'+params[i].color+'"></div><span style="margin-left: 5px">' + params[i].seriesName + '</span> : ' + params[i].value +  '</div>';
                                                }else{
                                                    content += '<div>&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;  <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:'+params[i].color+'"></div><span style="margin-left: 5px">' + params[i].seriesName + '</span> : ' + params[i].value +  '</div>';
                                                }

                                            }else{
                                                if(realdt[params[i].dataIndex]){
                                                    content += '<div>'+realdt[params[i].dataIndex]+' <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:'+params[i].color+'"></div><span style="margin-left: 5px;">' + params[i].seriesName + '</span> : ' + params[i].value +  '</div>';
                                                }else{
                                                    content += '<div>&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:'+params[i].color+'"></div><span style="margin-left: 5px;">' + params[i].seriesName + '</span> : ' + params[i].value +  '</div>';
                                                }
                                            }
                                        }
                                    }
                                    return '<div>'+ content + '</div>'
                                };
                            }else{
                                opt.series[1].data =[];
                                opt.tooltip.formatter='';
                            }
                            myChart.hideLoading();
                            myChart.setOption(opt,true);
                        }else{
                            myChart.hideLoading();
                            myChart.setOption(opt,true);
                        }
                    }
                }
            })
        }
    })
}

//表格列表
function tableShow(type) {
    $("#detailInfo,#detail").html("");
    var appType = $("#appType_qst option:selected").val(),
        userType = $("#userType_qst option:selected").val(),
        pageId = $("#pageName option:selected").val();

    var params1 = {
        is_detail: type == 'huizong'?0:1,
        business_type: businessType,
        pageid: pageId,
        source: appType == ''?'all':appType,
        device_status: userType == ''?'all':userType,
        start_day: startdate,
        end_day: enddate,
        limit:100
    }
    var params_2 = {
        businessType: businessType,
        deviceStatus: (userType == 'all'?'':userType),
        pageId: (pageId == 'all'?'':pageId),
        queryDate: enddate,
        source: (appType == 'all'?'':appType),
        limit:100
    }
    var urls = '';
    if(startdate == enddate && startdate == getCurrentDay()){
        if(type == 'huizong'){
            urls = './getGuancetaiPageDetailHuiZongList';
        }else{
            urls = './getGuancetaiPageDetailList';
        }
    }else{
        urls = getLocation() + proxy("/dataApiQuery/gct_page_data_table", params1);
    }

    //汇总
    if(type == 'huizong'){
        $("#example_detail,#example_detail_wrapper").css("display","none");
        $('#example').show();
        if(startdate == enddate && startdate == getCurrentDay()){
            $('#example').DataTable({
                // l - length changing input control
                // f - filtering input
                // t - The table
                // i - Table information summary
                // p - pagination control
                // r - processing display element
                dom: "lrtip",
                language: i18nZH,
                responsive: !0,
                "lengthChange": true,//开启显示条数
                "lengthMenu": [ 15, 50, 75, 100 ],
                "ordering": true,//禁止排序
                "processing": true,//刷新的那个对话框
                "deferRender": false,//延迟渲染
                "scrollX": true,//启用水平滚动
                "autoWidth": true,//自动列宽
                "destroy": true,
                "ajax": {
                    "url":urls,
                    "type": "POST",
                    "async" : true,
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    data:function (d) {
                        return JSON.stringify($.extend({},d,params_2));
                    },
                    "dataFilter": function (res) {//res是服务器端返回的数据
                        var json = JSON.parse(res);
                        var returnData = {};
                        if(json.code == '0' && json.data){
                            returnData.recordsTotal = json.total;//返回数据全部记录
                            returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = json.data;//返回的数据列表
                            return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                        }else{
                            //接口返回异常需要把数据置为空，否则datatable会弹窗报错
                            returnData.recordsTotal = 0;
                            returnData.recordsFiltered = 0;
                            returnData.data = [];
                            return JSON.stringify(returnData);
                        }
                    }
                },
                /*给数据添加列*/
                columns: [
                    {data: 'pageId'},
                    {data: 'pageName'},
                    {data: 'source'},
                    {data: 'visitCount'},
                    {data: 'visitUsers'},
                    {data: 'onlineTimeEveryTime'},
                ],
            });
        }else{
            $('#example').DataTable({
                dom: "lrtip",
                language: i18nZH,
                responsive: !0,
                "lengthChange": true,//开启显示条数
                "lengthMenu": [ 15, 50, 75, 100 ],
                "ordering": true,//禁止排序
                "processing": true,//刷新的那个对话框
                "deferRender": false,//延迟渲染
                "scrollX": true,//启用水平滚动
                "autoWidth": true,//自动列宽
                "destroy": true,
                "ajax": {
                    "url":urls,
                    "type": "GET",
                    "async" : true,
                    "dataType": "json",
                    "dataFilter": function (res) {//res是服务器端返回的数据
                        var json = JSON.parse(res);
                        var returnData = {};
                        if(json.code == '0' && json.data){
                            returnData.recordsTotal = json.total;//返回数据全部记录
                            returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = json.data;//返回的数据列表
                            return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                        }else{
                            //接口返回异常需要把数据置为空，否则datatable会弹窗报错
                            returnData.recordsTotal = 0;
                            returnData.recordsFiltered = 0;
                            returnData.data = [];
                            return JSON.stringify(returnData);
                        }
                    }
                },
                /*给数据添加列*/
                columns: [
                    {data: 'pageid'},
                    {data: 'page_name'},
                    {data: 'source'},
                    {data: 'visit_num'},
                    {data: 'visit_user_num'},
                    {data: 'average_stop_time'},
                ],
            });
        }
    }else{//详情
        $("#example,#example_wrapper").css("display","none");
        $("#example_detail").show();
        if(startdate == enddate && startdate == getCurrentDay()){
            $('#example_detail').DataTable({
                dom: "lrtip",
                language: i18nZH,
                responsive: !0,
                "lengthChange": true,//开启显示条数
                "lengthMenu": [ 15, 50, 75, 100 ],
                "ordering": true,//禁止排序
                "processing": true,//刷新的那个对话框
                "deferRender": false,//延迟渲染
                "scrollX": true,//启用水平滚动
                "autoWidth": true,//自动列宽
                "destroy": true,
                "ajax": {
                    "url":urls,
                    "type": "POST",
                    "async" : true,
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    data:function (d) {
                        return JSON.stringify($.extend({},d,params_2));
                    },
                    "dataFilter": function (res) {//res是服务器端返回的数据
                        var json = JSON.parse(res);
                        var returnData = {};
                        if(json.code == '0' && json.data){
                            returnData.recordsTotal = json.total;//返回数据全部记录
                            returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = json.data;//返回的数据列表
                            return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                        }else{
                            //接口返回异常需要把数据置为空，否则datatable会弹窗报错
                            returnData.recordsTotal = 0;
                            returnData.recordsFiltered = 0;
                            returnData.data = [];
                            return JSON.stringify(returnData);
                        }
                    }
                },
                /*给数据添加列*/
                columns: [
                    {data: 'time'},
                    {data: 'pageId'},
                    {data: 'pageName'},
                    {data: 'source'},
                    {data: 'visitCount'},
                    {data: 'visitUsers'},
                    {data: 'onlineTimeEveryTime'},
                ],
            });
        }else{
            $('#example_detail').DataTable({
                dom: "lrtip",
                language: i18nZH,
                responsive: !0,
                "lengthChange": true,//开启显示条数
                "lengthMenu": [ 15, 50, 75, 100 ],
                "ordering": true,//禁止排序
                "processing": true,//刷新的那个对话框
                "deferRender": false,//延迟渲染
                "scrollX": true,//启用水平滚动
                "autoWidth": true,//自动列宽
                "destroy": true,
                "ajax": {
                    "url":urls,
                    "type": "GET",
                    "async" : true,
                    "dataType": "json",
                    "dataFilter": function (res) {//res是服务器端返回的数据
                        var json = JSON.parse(res);
                        var returnData = {};
                        if(json.code == '0' && json.data){
                            returnData.recordsTotal = json.total;//返回数据全部记录
                            returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = json.data;//返回的数据列表
                            return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                        }else{
                            //接口返回异常需要把数据置为空，否则datatable会弹窗报错
                            returnData.recordsTotal = 0;
                            returnData.recordsFiltered = 0;
                            returnData.data = [];
                            return JSON.stringify(returnData);
                        }
                    }
                },
                /*给数据添加列*/
                columns: [
                    {data: 'dt'},
                    {data: 'pageid'},
                    {data: 'page_name'},
                    {data: 'source'},
                    {data: 'visit_num'},
                    {data: 'visit_user_num'},
                    {data: 'average_stop_time'},
                ],
            });
        }
    }
}

function setPageName(type){
    $('#pageName').html("");
    var param = {
        "businessType":type
    }
    $.ajax({
        url: "/buriedPage/getPageInfoByBusinessType",
        data: param,
        type: "get",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (res) {
            if (res) {
                var list = res.data;
                var str ='<option value="all" selected>全部</option>';
                for(var i=0; i<list.length; i++){
                    str += '<option value="'+list[i].pageId+'">'+list[i].pageName+'</option>';
                }
                $('#pageName').append(str);
                $("#pageName").select2({
                    placeholder: '全部',
                    allowClear: true,
                    multiple:false
                });
            }
        }
    });
}

//页面名称的选择
function pageNameChoose(){
    //埋点
    var obj = {
        "eventType": ["tableDownload"],
    }
    getPointParameter(obj);
    $(".tab_1 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
    $(".tab_2 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
    rendmapshow('user');
    tableShow("huizong");
}

//下载
function getExportDate() {
    //埋点
    var obj = {
        "eventType": ["tableDownload"],
    }
    getPointParameter(obj);
    var userType = $("#userType_qst option:selected").val();
    var pageId = $("#pageName option:selected").val();
    var appType = $("#appType_qst option:selected").val();
    var realTime_mapper = {
        "pageId":' 页面ID',
        "pageName": '页面名称',
        "source": '端(IOS/Android)',
        "visitCount": '访问次数',
        "visitUsers": '访问用户数',
        "onlineTimeEveryTime": '次均停留时长(s)'
    };

    var realTime_param ={
        "businessType": businessType,
        "deviceStatus": userType == 'all'?'':userType,
        "pageId": pageId == 'all' ? '': pageId,
        "source": appType == 'all'?'':appType,
        "queryDate":getCurrentDay()
    };

    var mapper = {
        "pageid":' 页面ID',
        "page_name": '页面名称',
        "source": '端(IOS/Android)',
        "visit_num": '访问次数',
        "visit_user_num": '访问用户数',
        "average_stop_time": '次均停留时长(s)'
    };
    //离线的参数
    var param = {
        "business_type": businessType,
        "device_status": userType == ''?'all':userType,
        "pageid": pageId,
        "source": appType == ''?'all':appType,
        "start_day": startdate,
        "end_day": enddate,
        "limit":999999999
    };
    //汇总
    if($(".tab > div:first-child").attr("class")=="bg_active"){
        if(startdate == getCurrentDay()){//实时数据(今天)

            postDownLoadFile({
                url: './exportGuancetaiPageDetailHuiZongList',
                data: {
                    "params":realTime_param,
                    "titles":realTime_mapper
                },
                method: 'post'
            });
        }else{
            param.is_detail= '0';
            postDownLoadFile({
                url: './dataApiExport/gct_page_data_table',
                data: {
                    "params": param,
                    "titles": mapper
                },
                method: 'post'
            });
        }
    }else{//详情
        if(startdate == getCurrentDay()) {
            postDownLoadFile({
                url: '/exportGuancetaiPageDetailList',
                data: {
                    "params":realTime_param,
                    "titles":realTime_mapper
                },
                method: 'post'
            });
        }else{
            param.is_detail= '1';
            postDownLoadFile({
                url: './dataApiExport/gct_page_data_table',
                data: {
                    "params": param,
                    "titles": mapper
                },
                method: 'post'
            });
        }
    }
}

/*********前端交互*********/
//趋势图切换
$(".tab_1 > div").click(function () {
    //埋点
    var obj = {
        "eventType": ["qstSwitch"],
    }
    getPointParameter(obj);
    $(this).addClass("bg_active").siblings("div").removeClass("bg_active");
    var i = $(this).index();
    if(i==0){
        rendmapshow("user");
    }else if(i==1){
        rendmapshow("avg_user_time");
    }else if(i==2){
        rendmapshow("count");
    }else if(i==3){
        rendmapshow("avg_time");
    }
});

//表格详情切换
$(".tab_2 > div").click(function () {
    //埋点
    var obj = {
        "eventType": ["tableSwitch"],
    }
    getPointParameter(obj);

    $(this).addClass("bg_active").siblings("div").removeClass("bg_active");
    var i = $(this).index();
    if(i==0){
        $("#example_detail,#example_detail_info,#example_detail_paginate").hide();
        $("#example,#example_info,#example_paginate").show();
        tableShow("huizong");
    }else if(i==1){
        $("#example,#example_info,#example_paginate").hide();
        $("#example_detail,#example_detail_info,#example_detail_paginate").show();
        tableShow("detail");
    }
});

//切换条件查询统计数据
function dataStatisticSelect(str) {
    //埋点
    var obj = {
        "eventType": ["appType","userType"],
    }
    getPointParameter(obj);
    if(str=="qst"){
        var tab_text = $(".tab_1 .bg_active").text();
        if(tab_text=="页面访问用户数"){
            rendmapshow("user");
        }else if(tab_text=="页面人均停留时长(s)"){
            rendmapshow("avg_user_time");
        }else if(tab_text=="页面访问次数"){
            rendmapshow("count");
        }else if(tab_text=="页面次均停留时长(s)"){
            rendmapshow("avg_time");
        }
        var text = $(".tab_2 .bg_active").text();
        if(tab_text=="汇总统计"){
            tableShow("huizong");
        }else{
            tableShow("huizong");
        }
    }else{
        var bType = $(".tabControl .active").attr("data-type");
        startupTimes(bType);
        startupUser(bType);
        onlineTime(bType);
        avgStartTimes(bType);
    }
}