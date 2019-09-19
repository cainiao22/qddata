/**
 * 观测台--事件分析
 */
var startdate = '',enddate = '';

var businessType = $(".tabControl > a > .active").attr("data-type");//千丁 or 丁管家

var param = {
    "queryDate": getCurrentDay(),
}

var sTimes=0,lastTimes=0,time_hb=0; //启动次数
var sUser=0,lastUser=0,user_hb=0;   //启动人数
$(document).ready(function () {
    //埋点
    var obj = {
        "eventType": ["eventAnalyse"],
    }
    if(businessType == 0){
        obj.eventType.push('qding');
    }else{
        obj.eventType.push('dgj');
    }
    getPointParameter(obj);
    seteventname(businessType);

    $('.c-datepicker-data-input').val(getCurrentDay());
    //年月日范围
    $('.setdate-1,.setdate-2').datePicker({
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
        },{
            name: '过去7天',
            day: '-7,-1'
        },{
            name: '过去30天',
            day: '-30,-1'
        }],
        hide: function (type) {
            this.$input.eq(0).val(this.$input.eq(0).val().substr(0, 10));
            this.$input.eq(1).val(this.$input.eq(1).val().substr(0, 10));
//                this.$input.eq(2).val(this.$input.eq(2).val().substr(0, 10));
//                this.$input.eq(3).val(this.$input.eq(3).val().substr(0, 10));
            startdate = this.$input.eq(0).val();
            enddate = this.$input.eq(1).val();
            $(".tab>div:first-child,.dgj_qst>div:first-child,.tab_2>div:first-child,.dgj_tab_2>div:first-child").addClass("bg_active").siblings("div").removeClass("bg_active");

            //埋点
            var obj = {
                "eventType": ["dateSelect"],
            }
            getPointParameter(obj);

            evevtnamechoose();
        }
    });
    startdate = getCurrentDay();
    enddate = getCurrentDay();

    startupfrequency(businessType);
    startupUser(businessType);
    rendmapshow('renshu', 1);
    tableShow("huizong", 1);
});
//日历显示时
function calendarshow(){
    $('.c-datepicker-picker').css({'left':'initial','right':'25px'});
    $('.c-datepicker-date-range-picker__editor-wrap').eq(1).css('display', 'none');
    $('.c-datepicker-date-range-picker__editor-wrap').eq(3).css('display', 'none');
}

//创建event事件名称的选择项
function seteventname(type){
    $('#eventname,#dgj_eventname').html("");
    var param = {
        "businessType":type
    }
    $.ajax({
        url: "/buriedEvent/getEventInfoByBusinessType",
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
                    str += '<option value="'+list[i].eventId+'">'+list[i].eventName+'</option>';
                }
                $('#eventname,#dgj_eventname').append(str);
                $("#eventname,#dgj_eventname").select2({
                    placeholder: '全部',
                    width:'250px',
                    allowClear: true,
                    multiple:false
                });
            }
        }
    });
}
//event事件名称的选择
function evevtnamechoose(){
    //埋点
    var obj = {
        "eventType": ["eventAnalyse"],
    }
    getPointParameter(obj);
    if($(".qst>.bg_active,.dgj_qst>.bg_active").text() == '事件发生人数'){
        rendmapshow('renshu');
    }else{
        rendmapshow('cishu');
    }
    if($(".tab_2>.bg_active,.dgj_tab_2>.bg_active").text() == '汇总统计'){
        tableShow("huizong");
    }else{
        tableShow("detail");
    }
}

//发生人数
function startupfrequency(businessType) {
    var appType="",userType="";
    if(businessType=="0"){
        appType = $("#appType option:selected").val();
        userType = $("#userType option:selected").val();
    }else{
        appType = $("#dgj_appType option:selected").val();
    }
    param.source = appType?appType:'';
    param.deviceStatus = userType?userType:'';
    param.businessType = businessType;
    $.ajax({
        url: "./getEventStartupUsersInfo",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(param),
        success: function (res) {
            if (res) {
                sUser = res.current;
                lastUser = res.last;
                user_hb = res.increasement;
                if(businessType=="0"){
                    pageShow(res,"#startUser");
                }else{
                    pageShow(res,"#dgj_startUser");
                }
            }
        }
    });
}

//发生次数
function startupUser(businessType) {
    var appType="",userType="";
    if(businessType=="0"){
        appType = $("#appType option:selected").val();
        userType = $("#userType option:selected").val();
    }else{
        appType = $("#dgj_appType option:selected").val();
    }
    param.source = appType?appType:'';
    param.deviceStatus = userType?userType:'';
    param.businessType = businessType;
    $.ajax({
        url: "./getEventStartupTimesInfo",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(param),
        success: function (res) {
            if (res) {
                sTimes = res.current;
                lastTimes = res.last;
                time_hb = res.increasement;
                if(businessType=="0") {
                    pageShow(res, "#startTimes");
                }else{
                    pageShow(res, "#dgj_startTimes");
                }
            }
        }
    });
}

//趋势图展示
function rendmapshow(type){


    var myChart=null;
    if(businessType=="0") {
        myChart = echarts.init(document.getElementById('trendEchart'));
    }else{
        myChart = echarts.init(document.getElementById('dgj_trendEchart'));
    }
    var opt = line_option;
    myChart.showLoading({
        text: ' ',
        effect: 'whirling',
        color: '#6F7E95',
        maskColor: '#FFFFFF',
    });
    myChart.clear();

    var appType="",userType="",enentid="";
    if(businessType=="0"){
        appType = $("#appType_qst option:selected").val();
        userType = $("#userType_qst option:selected").val();
        enentid = $("#eventname option:selected").val();
    }else{
        appType = $("#dgj_appType_qst option:selected").val();
        enentid = $("#dgj_eventname option:selected").val();
    }
    param.source = appType?appType:'';
    param.deviceStatus = userType?userType:'';

    var params1 = {
        is_current: 1,
        business_type: businessType,
        event_id: enentid,
        source: appType == ''?'all':appType,
        device_status: userType == ''?'all':userType,
        start_day: startdate,
        end_day: enddate,
    }
    var params_2 = {
        businessType: businessType,
        deviceStatus: (userType == 'all'?'':userType),
        eventId: (enentid == 'all'?'':enentid),
        queryDate: enddate,
        source: (appType == 'all'?'':appType),
    }
    var urls = '';
    if(startdate == enddate && startdate == getCurrentDay()){
        urls = (type == 'renshu'?'./getEventStartupUsersInfoTrend':'./getEventStartupTimesInfoTrend');
    }else{
        urls = getLocation() + proxy("/dataApiQuery/gct_event_data_trend", params1);
    }
    //本期
    var prestartdate = '', preenddate = '';
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
            var xdata = [];
            if(startdate == enddate && startdate == getCurrentDay()){
                if(res){
                    var serises = [];
                    var canzhao = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
                    var list = datassort(canzhao, res, 'key');
                    if(list && list.length > 0){
                        for(var i=0; i<list.length; i++){
                            xdata.push(i+"点");
                            serises.push(list[i]?(list[i].value?list[i].value:'0'):'')
                        }
                        opt.series[0].data = serises;
                        opt.xAxis.data = xdata;
                        opt.xAxis.axisLabel.interval = 0;
                        opt.xAxis.axisLabel.formatter = function(params){
                            return params;
                        }
                    }else{
                        opt.series[0].data = [];
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
                            xdata.push(list[i]?(list[i].dt?list[i].dt:''):'');
                            serises.push(list[i]?(list[i][type == 'renshu'?'event_user_num':'event_num']?list[i][type == 'renshu'?'event_user_num':'event_num']:'0'):'')
                        }
                        opt.series[0].data = serises;
                        opt.xAxis.data = xdata;
                        if(xdata.length >= 10){
                            var n = (xdata.length / 10);
                            opt.xAxis.axisLabel.interval = Math.ceil(n);
                        }else{
                            opt.xAxis.axisLabel.interval =0;
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
                    }else{
                        opt.series[0].data = [];
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
                urls2 = (type == 'renshu'?'./getEventStartupUsersInfoTrend':'./getEventStartupTimesInfoTrend');
            }else{
                params1.is_current = 0;
                params1.start_day = prestartdate;
                params1.end_day = preenddate;
                urls2 = getLocation() + proxy("/dataApiQuery/gct_event_data_trend", params1);
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
                                    serises2.push(list2[i]?(list2[i].value?list2[i].value:'0'):'0')
                                }
                                opt.series[1].data = serises2;
                            }else{
                                opt.series[1].data = [];
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
                            if(list && list.length > 0){
                                for(var i=0; i<list.length; i++){
                                    if(type == 'renshu'){
                                        serises2.push(list[i]?(list[i]['event_user_num']?list[i]['event_user_num']:'0'):'0');
                                    }else{
                                        serises2.push(list[i]?(list[i]['event_num']?list[i]['event_num']:'0'):'0');
                                    }
                                    realdt.push(list[i].real_dt?list[i].real_dt:"");
                                }
                                opt.series[1].data = serises2;
                                opt.tooltip.formatter= function(params){
                                    var content = '';
                                    for(var i=0; i<params.length; i++){
                                        if(params[i].value && params[i].value!=''){
                                            if(i == 0){
                                                content += '<div>'+ params[i].name +'  <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:'+params[i].color+'"></div><span style="margin-left: 5px">' + params[i].seriesName + '</span> : ' + params[i].value +  '</div>';
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
                                opt.series[1].data = [];
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

var oTable;
//表格列表
function tableShow(type) {
    $("#example>tbody,#example_detail>tbody,#dgj_example>tbody,#dgj_example_detail>tbody").html("");
    var appType="",userType="",enentid="";
    if(businessType=="0"){
        appType = $("#appType_qst option:selected").val();
        userType = $("#userType_qst option:selected").val();
        enentid = $("#eventname option:selected").val();
    }else{
        appType = $("#dgj_appType_qst option:selected").val();
        enentid = $("#dgj_eventname option:selected").val();
    }

    var params1 = {
        is_detail: type == 'huizong'?0:1,
        business_type: businessType,
        event_id: enentid,
        source: appType == ''?'all':appType,
        device_status: userType == ''?'all':userType,
        start_day: startdate,
        end_day: enddate,
        limit:100
    }
    var params_2 = {
        businessType: businessType,
        deviceStatus: (userType == 'all'?'':userType),
        eventId: (enentid == 'all'?'':enentid),
        queryDate: enddate,
        source: (appType == 'all'?'':appType),
        limit:100
    }
    var urls = '',column = [];
    if(startdate == enddate && startdate == getCurrentDay()){
        urls = (type == 'huizong')?'./getGuancetaiEventDetailHuiZongList':'./getGuancetaiEventDetailList';

        column =[
            {className:'details-control', data: 'eventId'},
            {data: 'eventName'},{data: 'pageName'},{data: 'eventType'},{data: 'source'},{data: 'visitCount'},{data: 'visitUsers'}
        ];
        if(type != 'huizong'){
            column.unshift({data: 'time'});
        }
    }else{
        urls = getLocation() + proxy("/dataApiQuery/gct_event_data_table", params1);

        column = [
            {className:'details-control', data: 'event_id'},
            {data: 'event_name'},{data: 'page_name'},{data: 'event_type'},{data: 'source'},{data: 'event_num'},{data: 'event_user_num'}
        ];
        if(type != 'huizong'){
            column.unshift({data: 'dt'});
        }
    }

    if(businessType=="0") {
        //汇总
        if(type == 'huizong'){
            $("#example_detail,#example_detail_wrapper").css("display","none");
            $('#example').show();

            if(startdate == enddate && startdate == getCurrentDay()){
                oTable = $('#example').DataTable({
                    dom: "lrtip",
                    language: i18nZH,
                    responsive: !0,
                    "lengthChange": false,//开启显示条数
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
                                returnData.data =json.data;//返回的数据列表
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
                    columns: column,
                });
            }else{
                oTable = $('#example').DataTable({
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
                    columns: column,
                });
            }

        }else{//详情
            $("#example,#example_wrapper").css("display","none");
            $("#example_detail").show();
            if(startdate == enddate && startdate == getCurrentDay()){
                oTable = $('#example_detail').DataTable({
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
                    columns:column,
                });
            }else{
                oTable = $('#example_detail').DataTable({
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
                    columns: column,
                });
            }
        }
    }else{
        //汇总
        if(type == 'huizong'){
            $("#dgj_example_detail,#dgj_example_detail_wrapper").css("display","none");
            $('#dgj_example').show();
            // var oTable;
            if(startdate == enddate && startdate == getCurrentDay()){
                $('#dgj_example').DataTable({
                    // l - length changing input control
                    // f - filtering input
                    // t - The table
                    // i - Table information summary
                    // p - pagination control
                    // r - processing display element
                    dom: "lrtip",
                    language: i18nZH,
                    responsive: !0,
                    "lengthChange": false,//开启显示条数
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
                    columns: column,
                });
            }else{
                $('#dgj_example').DataTable({
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
                    columns: column,
                });
            }

        }else{//详情
            $("#dgj_example,#dgj_example_wrapper").css("display","none");
            $("#dgj_example_detail").show();
            // var oTable;
            if(startdate == enddate && startdate == getCurrentDay()){
                $('#dgj_example_detail').DataTable({
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
                    columns: column,
                });
            }else{
               $('#dgj_example_detail').DataTable({
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
                    columns: column,
                });
            }
        }
    }
}

//点击展开参数列表
$('#example tbody').on('click', 'td.details-control',function (){
    $("#example tbody tr").removeClass("shown");
    $(".paramText").parent().parent().remove();
    var tr = $(this).closest('tr');
    var row = oTable.row(tr);
    if (row.child.isShown()) {
        row.child.hide();
        tr.removeClass('shown');
    }else {
        var eid = $(this).text().trim();
        row.child(paramDataShow(eid)).show();
        $(".paramText").parent('td').css("text-align","left");
        tr.addClass('shown');
        paramDataList();
        paramDataTable(eid);
    }
});
//点击展开参数列表
$('#example_detail tbody').on('click', 'td.details-control',function (){
    $("#example_detail tbody tr").removeClass("shown");
    $(".paramText").parent().parent().remove();
    var tr = $(this).closest('tr');
    var row = oTable.row(tr);
    if (row.child.isShown()) {
        row.child.hide();
        tr.removeClass('shown');
    }else {
        var eid = $(this).text().trim();
        row.child(paramDataShow(eid)).show();
        $(".paramText").parent('td').css("text-align","left");
        tr.addClass('shown');
        paramDataList();
        paramDataTable(eid);
    }
});

//下载
function getExportDate() {
    //埋点
    var obj = {
        "eventType": ["tableDownload"],
    }
    getPointParameter(obj);

    var appType="",userType="",eventid="";
    if(businessType=="0"){
        appType = $("#appType_qst option:selected").val();
        userType = $("#userType_qst option:selected").val();
        eventid = $("#eventname option:selected").val();
    }else{
        appType = $("#dgj_appType_qst option:selected").val();
        eventid = $("#dgj_eventname option:selected").val();
    }
    var realTime_mapper = {
        "eventId":' 事件ID',
        "eventName": '事件名称',
        "pageName": '页面名称',
        "eventType": '类型',
        "source": '端(IOS/Android)',
        "visitCount": '事件发生次数',
        "visitUsers": '事件发生人数'
    };

    var realTime_param ={
        "businessType": businessType,
        "deviceStatus": userType == 'all'?'':userType,
        "eventId": eventid == 'all' ? '': eventid,
        "source": appType == 'all'?'':appType,
        "queryDate":getCurrentDay()
    };

    var mapper = {
        "event_id":' 事件ID',
        "event_name": '事件名称',
        "page_name": '页面名称',
        "event_type": '类型',
        "source": '端(IOS/Android)',
        "event_num": '事件发生次数',
        "event_user_num": '事件发生人数'
    };
    //离线的参数
    var param = {
        "business_type": businessType,
        "device_status": userType == ''?'all':userType,
        "event_id": eventid,
        "source": appType == ''?'all':appType,
        "start_day": startdate,
        "end_day": enddate,
        "limit":999999999
    };
    if(businessType=="0"){
        //汇总
        if($(".tab_2>div:first-child").attr("class")=="bg_active"){
            if(startdate == getCurrentDay()){//实时数据(今天)
                postDownLoadFile({
                    url: './exportGuancetaiEventDetailHuiZongList',
                    data: {
                        "params":realTime_param,
                        "titles":realTime_mapper
                    },
                    method: 'post'
                });
            }else{
                param.is_detail= '0';
                postDownLoadFile({
                    url: './dataApiExport/gct_event_data_table',
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
                    url: '/exportGuancetaiEventDetailList',
                    data: {
                        "params":realTime_param,
                        "titles":realTime_mapper
                    },
                    method: 'post'
                });
            }else{
                param.is_detail= '1';
                postDownLoadFile({
                    url: './dataApiExport/gct_event_data_table',
                    data: {
                        "params": param,
                        "titles": mapper
                    },
                    method: 'post'
                });
            }
        }
    }else{
        //汇总
        if($(".dgj_tab_2>div:first-child").attr("class")=="bg_active"){
            if(startdate == getCurrentDay()){//实时数据(今天)
                postDownLoadFile({
                    url: './exportGuancetaiEventDetailHuiZongList',
                    data: {
                        "params":realTime_param,
                        "titles":realTime_mapper
                    },
                    method: 'post'
                });
            }else{
                param.is_detail= '0';
                postDownLoadFile({
                    url: './dataApiExport/gct_event_data_table',
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
                    url: '/exportGuancetaiEventDetailList',
                    data: {
                        "params":realTime_param,
                        "titles":realTime_mapper
                    },
                    method: 'post'
                });
            }else{
                param.is_detail= '1';
                postDownLoadFile({
                    url: './dataApiExport/gct_event_data_table',
                    data: {
                        "params": param,
                        "titles": mapper
                    },
                    method: 'post'
                });
            }
        }
    }

}

//展开行
function paramDataShow(eid){
    var str='';
    str +='<span class="paramText">参数名称:</span>';
    str +='<select class="select2" id="paramList" onchange="paramDataTable(\''+eid+'\')">';
    str +='<option value="all">全部</option>';
    str +='</select>';
    str +='<div style="max-height: 300px;overflow: auto;"><table id="rowDetailTable"><thead style="visibility: visible;height: auto;"><tr style="background: #F5F6FA;border-radius: 2px;"><td>参数ID</td><td>参数名称</td><td>参数值</td><td>触发次数</td></tr></thead><tbody></tbody></table></div>';
    return str;
}

//参数列表
function paramDataList() {
    $("#paramList").html("");
    $.ajax({
        url: getLocation() + proxy("/dataApiQuery/gct_event_key_param_list"),
        type: "get",
        dataType: "json",
        success: function (res) {
            if (res) {
                var list = res.data;
                var str ='<option value="all" selected>全部</option>';
                for(var i=0; i<list.length; i++){
                    if(list[i].ai_param_name){
                        str += '<option value="'+list[i].ai_param_id+'">'+list[i].ai_param_id +'('+list[i].ai_param_name+')</option>';
                    }
                }
                $('#paramList').append(str);
            }
        }
    });
}

//参数表格
function paramDataTable(eventid) {
    $('#rowDetailTable>tbody').html("");
    var appType = $("#appType_qst option:selected").val();
    var userType = $("#userType_qst option:selected").val();
    var eventParamId = $("#paramList option:selected").val();
    var param = {
        "source": appType,
        "device_status":userType,
        "event_id":eventid,
        "ai_param_key":eventParamId?eventParamId:"all",
        "start_day":startdate,
        "end_day":enddate,
        "limit":100
    }

    $.ajax({
        url: getLocation() + proxy("/dataApiQuery/gct_event_key_data_table",param),
        type: "get",
        dataType: "json",
        success: function (res) {
            if (res && res.data && res.data.length>0) {
                var list = res.data;
                var str ='';
                for(var i=0; i<list.length; i++){
                    str += '<tr>';
                    str += '<td>'+list[i].ai_param_key+'</td>';
                    str += '<td>'+list[i].ai_param_name+'</td>';
                    str += '<td>'+list[i].ai_param_value+'</td>';
                    str += '<td>'+list[i].ai_param_key_nums+'</td>';
                    str += '</tr>';
                }
                $('#rowDetailTable>tbody').append(str);
            }else{
                $('#rowDetailTable>tbody').append("<tr><td colspan='4'>暂无数据</td></tr>");
            }
        }
    });
}
/*********前端交互*********/
//趋势图切换
$(".qst>div,.dgj_qst>div").click(function () {
    //埋点
    var obj = {
        "eventType": ["qstSwitch"],
    }
    getPointParameter(obj);
    $(this).addClass("bg_active").siblings("div").removeClass("bg_active");
    var i = $(this).index();
    if(i==0){
        rendmapshow("renshu");
    }else if(i==1){
        rendmapshow("cishu");
    }
});
//表格详情切换
$(".tab_2>div,.dgj_tab_2>div").click(function () {
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
function dataStatisticSelect(type) {
    //埋点
    var pointArr=[];
    pointArr.push("appType");
    if(type=="0"){
        pointArr.push("userType");
    }
    var obj = {
        "eventType": pointArr,
    }
    getPointParameter(obj);

    startupfrequency(type);
    startupUser(type);
}
//切换条件查询趋势图和表格
function qushituSelect() {
    //埋点
    var pointArr=[];
    pointArr.push("appType");
    if(businessType=="0"){
        pointArr.push("userType");
    }
    var obj = {
        "eventType": pointArr,
    }
    getPointParameter(obj);
    rendmapshow("renshu");
    tableShow('huizong');
}

