/**
 *观测台--总览
 */
var startdate = '', enddate = '';

var businessType = $(".tabControl > a > .active").attr("data-type");//千丁 or 丁管家

var sTimes=0,lastTimes=0,time_hb=0; //启动次数
var sUser=0,lastUser=0,user_hb=0;   //启动人数
$(document).ready(function () {
    if(sessionStorage.getItem('firstland')){
        //埋点
        var obj = {
            "eventType": ["zl"],
        }
        if(businessType == 0){
            obj.eventType.push('qding');
        }else{
            obj.eventType.push('dgj');
        }

        getPointParameter(obj);
    }else{
        //埋点
        var obj = {
            "eventType": ["denglu"],
        }

        getPointParameter(obj, 1);
    }

    registUser();

    startupTimes(businessType);
    startupUser(businessType);
    onlineTime(businessType);
    window.setTimeout(function () {
        avgStartTimes(businessType);
    },300);

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
            if(businessType=="0"){
                qushituSelect(0);
            }else{
                qushituSelect(1);
            }
            //埋点
            var obj = {
                "eventType": ["dateSelect"],
            }
            getPointParameter(obj);
        }
    });
    startdate = getCurrentDay();
    enddate = getCurrentDay();

    //趋势图--默认查今天数据
    $("#appType_qst,#userType_qst").attr("disabled","true").css("cursor","not-allowed");
    if(businessType && businessType =="1"){
        qushituShow("count", 1);
    }else{
        allUserTrend("new", 1);
    }
});

//日历显示时
function calendarshow(){
    $('.c-datepicker-picker').css({'left':'initial','right':'25px'});
    $('.c-datepicker-date-range-picker__editor-wrap').eq(1).css('display', 'none');
    $('.c-datepicker-date-range-picker__editor-wrap').eq(3).css('display', 'none');
}

//累计/新增注册用户
function registUser() {
    var date = new Date();
    var hour = date.getHours()-1;
    if(hour<10){
        hour = "0"+(hour);
    }
    var param = {
        "dt":getCurrentDay(),
        "hour":hour
    }
    var urls='';
    if(businessType=='0'){
        urls = getLocation() + proxy("/dataApiQuery/gct_zonglan_reg_data",param);
    }else{
        urls = getLocation() + proxy("/dataApiQuery/gct_dgj_leiji_regist_num",param);
    }

    $.ajax({
        url: urls,
        type: "get",
        success: function (res) {
            if (res) {
                var result = JSON.parse(res);
                if(result && result.data.length > 0){
                    var totalResist = {
                        "current":result.data[0].regist_num,
                        "last":result.data[0].pre_regist_num,
                        "increasement":result.data[0].regist_num_huanbi
                    };
                    if(businessType=='0'){
                        pageShow(totalResist,"#totalUser");
                        var currentRegist = {
                            "current":result.data[0].current_regist_num,
                            "last":result.data[0].pre_current_regist_num,
                            "increasement":result.data[0].current_regist_num_huanbi
                        };
                        pageShow(currentRegist,"#newUser");
                    }else{
                        pageShow(totalResist,"#dgj_totalUser");
                    }
                }
            }
        }
    });
}
//启动次数
function startupTimes(businessType) {
    var appType="",userType="";
    if(businessType=="0"){
        appType = $("#appType option:selected").val();
        userType = $("#userType option:selected").val();
    }else{
        appType = $("#dgj_appType option:selected").val();
    }
    var param = {
        "businessType":businessType,
        "queryDate": getCurrentDay(),
        "source":appType,
        "deviceStatus":userType
    }
    $.ajax({
        url: "./getStartupTimesInfo",
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

                if(businessType=="1"){
                    pageShow(res,"#dgj_startTimes");
                }else {
                    pageShow(res,"#startTimes");
                }
            }
        }
    });
}
//启动用户数
function startupUser(businessType) {
    var appType="",userType="";
    if(businessType=="0"){
        appType = $("#appType option:selected").val();
        userType = $("#userType option:selected").val();
    }else{
        appType = $("#dgj_appType option:selected").val();
    }
    var param = {
        "businessType":businessType,
        "queryDate": getCurrentDay(),
        "source":appType,
        "deviceStatus":userType
    }
    $.ajax({
        url: "./getStartupUsersInfo",
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
                if(businessType=="1") {
                    pageShow(res,"#dgj_startUser");
                }else{
                    pageShow(res, "#startUser");
                }
            }
        }
    });
}
//次均停留时长
function onlineTime(businessType) {
    var appType="",userType="";
    if(businessType=="0"){
        appType = $("#appType option:selected").val();
        userType = $("#userType option:selected").val();
    }else{
        appType = $("#dgj_appType option:selected").val();
    }
    var param = {
        "businessType":businessType,
        "queryDate": getCurrentDay(),
        "source":appType,
        "deviceStatus":userType
    }
    $.ajax({
        url: "./getAvgOnlineTimeInfo",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(param),
        success: function (res) {
            if (res) {
                if(businessType=="1"){
                    pageShow(res,"#dgj_onlineTime","three");
                }else{
                    pageShow(res,"#onlineTime","three");
                }
            }
        }
    });
}
//人均启动次数 = 启动次数/启动用户数
function avgStartTimes(businessType) {
    var times = Number(sTimes);
    var user = Number(sUser);
    var lasttime = Number(lastTimes);
    var lastuser = Number(lastUser);
    var h_time = Number(time_hb);
    var h_user = Number(user_hb);
    var current=0,last = 0,increasement = 0;
    if(user){
        current = Number(times/user).toFixed(2);
    }
    if(lastuser){
        last = Number(lasttime/lastuser).toFixed(2);
    }
    if(h_user){
        increasement = Number(last == 0?100:((current - last)/last*100)).toFixed(2);
    }

    var res={};
    if(Number(current)){
        res.current = current;
    }else{
        res.current = 0;
    }
    if(Number(last)){
        res.last = last;
    }else{
        res.last = 0;
    }
    if(Number(increasement)){
        res.increasement = increasement;
    }else{
        res.increasement = 0;
    }
    if(businessType=="1"){
        pageShow(res,"#dgj_avg_startTime","two");
    }else{
        pageShow(res,"#avg_startTime","two");
    }
}

// 累计用户数趋势图 he 新增用户趋势图 同一接口
//2019-01-28 累计用户趋势图去掉，UI及调用已改，代码逻辑没动
function allUserTrend(type) {

    var myChart = echarts.init(document.getElementById('trendEchart'));
    var opt = line_option;
    myChart.showLoading({
        text: ' ',
        effect: 'whirling',
        color: '#6F7E95',
        maskColor: '#FFFFFF',
    });
    myChart.clear();

    var	appType = $("#appType_qst option:selected").val();
    var	userType = $("#userType_qst option:selected").val();
    var startdate = $('.c-datepicker-data-input').eq(0).val();
    var enddate = $('.c-datepicker-data-input').eq(1).val();

    var qst = {
        "dt": startdate
    }
    var qst2 = {
        "is_current":"1",
        "business_type":"0",
        "source":appType==''?'all':appType,
        "device_status":userType==''?'2':userType,
        "start_day":startdate,
        "end_day":enddate
    }

    var url = "",url2 = "";
    //实时接口
    if(startdate==enddate && startdate == getCurrentDay()){
        url = getLocation() + proxy("./dataApiQuery/gct_base_zonglan_reg_qushi_data",qst);
    }else{
        url = getLocation() + proxy("./dataApiQuery/gct_base_zonglan_qushi_data",qst2);
    }
    //本期
    var prestartdate = '', preenddate = '', xdata = [];
    $.ajax({
        url:url,
        type: "get",
        success: function (res) {
            if(startdate == enddate && startdate == getCurrentDay()){
                if(res){
                    var result = JSON.parse(res);
                    var serises = [];
                    var canzhao = ["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"];
                    var list = datassort(canzhao, result.data, 'hour');
                    if(list && list.length > 0){
                        for(var i=0; i<list.length; i++){
                            xdata.push(i+"点");
                            if(type=="leiji"){
                                serises.push(list[i]?(list[i].regist_num?list[i].regist_num:'0'):'');
                            }else{
                                serises.push(list[i]?(list[i].current_regist_num?list[i].current_regist_num:'0'):'');
                            }
                        }
                        opt.series[0].data = serises;
                        opt.xAxis.data = xdata;
                        opt.xAxis.axisLabel.interval = 0;
                        opt.xAxis.axisLabel.formatter = function(params){
                            return params;
                        };
                    }else{
                        opt.series[0].data = [];
                    }
                    myChart.hideLoading();
                    myChart.setOption(opt,true);
                }else{
                    myChart.hideLoading();
                    myChart.setOption(opt,true);
                }
            }else{
                if(res){
                    var serises = [];
                    var result = JSON.parse(res);
                    var list = result.data;
                    if(list && list.length>0){
                        prestartdate = list[0].pre_start_date;
                        preenddate = list[0].pre_end_date;
                        for(var i=0; i<list.length; i++){
                            if(list[i]){
                                if(type=="leiji"){
                                    serises.push(list[i].regist_num?list[i].regist_num:'0');
                                }else{
                                    serises.push(list[i].current_regist_num?list[i].current_regist_num:'0');
                                }
                            }else{
                                serises.push('');
                            }
                            xdata.push(list[i].dt?list[i].dt:'');
                        }
                        opt.series[0].data = serises;
                        opt.xAxis.data = xdata;
                        if(xdata.length >=10){
                            var n = (xdata.length / 10);
                            opt.xAxis.axisLabel.interval = Math.ceil(n);
                        }else{
                            opt.xAxis.axisLabel.interval = 0;
                        }
                    }else{
                        myChart.setOption({},true);
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
                qst.dt = dayHandle(startdate, -1);
                url2 =getLocation() + proxy("./dataApiQuery/gct_base_zonglan_reg_qushi_data",qst);
            }else{
                qst2.is_current = 0;
                qst2.start_day = prestartdate;
                qst2.end_day = preenddate;
                url2 = getLocation() + proxy("./dataApiQuery/gct_base_zonglan_qushi_data",qst2);
            }
            $.ajax({
                url:url2,
                type: "get",
                success: function (res) {
                    if(startdate == enddate && startdate == getCurrentDay()){
                        if(res){
                            var result = JSON.parse(res);
                            var serises2 = [];
                            var canzhao = ["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"];
                            var list = datassort(canzhao, result.data, 'hour');
                            if(list && list.length > 0){
                                for(var i=0; i<list.length; i++){
                                    if(type=="leiji"){
                                        serises2.push(list[i]?(list[i].regist_num?list[i].regist_num:'0'):'0');
                                    }else{
                                        serises2.push(list[i]?(list[i].current_regist_num?list[i].current_regist_num:'0'):'0');
                                    }
                                }
                                opt.series[1].data = serises2;
                            }else{
                                opt.series[1].data = [];
                                opt.tooltip.formatter= '';
                            }
                            myChart.hideLoading();
                            myChart.setOption(opt,true);
                        }else{
                            myChart.hideLoading();
                            myChart.setOption(opt,true);
                        }
                    }else{
                        if(res){
                            var serises2 = [];
                            var result = JSON.parse(res);
                            var list = datassort(xdata, result.data,'dt');
                            if(list && list.length>0){
                                for(var i=0; i<list.length; i++){
                                    if(type=="leiji"){
                                        serises2.push(list[i]?(list[i].regist_num?list[i].regist_num:'0'):'0');
                                    }else{
                                        serises2.push(list[i]?(list[i].current_regist_num?list[i].current_regist_num:"0"):'0');
                                    }
                                }
                                opt.series[1].data = serises2;
                            }else{
                                opt.series[1].data =[];
                                opt.tooltip.formatter= '';
                            }
                            myChart.hideLoading();
                            myChart.setOption(opt,true);
                        }else{
                            myChart.hideLoading();
                            myChart.setOption(opt,true);
                        }
                    }
                }
            });
        },
        error:function () {
            myChart.hideLoading();
            myChart.setOption(opt,true);
        }
    });

}

//后4个趋势图
function qushituShow(type) {


    var myChart=null;
    if(businessType=="0"){
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
    var appType="",userType="",startdate="",enddate="";
    if(businessType=="0"){
        appType = $("#appType_qst option:selected").val();
        userType = $("#userType_qst option:selected").val();
        startdate = $('.c-datepicker-data-input').eq(0).val();
        enddate = $('.c-datepicker-data-input').eq(1).val();
    }else{
        appType = $("#dgj_appType_qst option:selected").val();
        userType ='';
        startdate = $('.c-datepicker-data-input').eq(0).val();
        enddate = $('.c-datepicker-data-input').eq(1).val();
    }

    var qst = {
        "businessType":businessType,
        "source":appType,
        "deviceStatus":userType,
        "queryDate": startdate
    }
    var qst2 = {
        "is_current":"1",
        "business_type":businessType,
        "source":appType==''?'all':appType,
        "device_status":userType==''?'2':userType,
    }

    qst2.start_day = startdate;
    qst2.end_day = enddate;
    var urls = "",urls2="";
    //实时数据接口
    if(startdate == enddate && startdate == getCurrentDay()){
        if(type=="user"){
            urls = "./getStartupUsersInfoTrend";
        }else if(type=="count"){
            urls = "./getStartupTimesInfoTrend";
        }else if(type=="avg_user_time"){
            urls = "./getAvgStartupUsersInfoTrend";
        }else if(type=="avg_time"){
            urls = "./getSetupTimeOnlineInfoTrend";
        }
    }else{//离线
        urls = getLocation() + proxy("/dataApiQuery/gct_base_zonglan_qushi_data", qst2);
    }
    var prestartdate = '', preenddate = '';
    //本期
    $.ajax({
        url: urls,
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify((startdate == enddate && startdate == getCurrentDay())?qst:''),
        success: function (res) {
            var xdata = [];
            if(startdate == enddate && startdate == getCurrentDay()){
                if(res && res.length > 0){
                    var serises = [];
                    var canzhao = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
                    var list = datassort(canzhao, res, 'key');
                    for(var i=0; i<list.length; i++){
                        xdata.push(i+"点");
                        if(list[i]){
                            if(list[i].value){
                                if(type=="user" || type=="count"){//不保留小数点
                                    serises.push(Number(list[i].value));
                                }else if(type=="avg_user_time"){//保留两位小数点
                                    serises.push(toDecimal(Number(list[i].value)));
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
                    opt.series[0].data = [];
                }
                opt.tooltip.formatter= '';
                myChart.hideLoading();
                myChart.setOption(opt,true);
            }else{
                if(res && res.data){
                    var xdata = [], serises = [];
                    var list = res.data;
                    if(list && list.length>0){
                        prestartdate = list[0].pre_start_date;
                        preenddate = list[0].pre_end_date;
                        for(var i=0; i<list.length; i++){
                            if(list[i]){
                                if(type=="user"){
                                    serises.push(list[i]['startup_user_num']?list[i]['startup_user_num']:'0');
                                }else if(type=="count"){
                                    serises.push(list[i]['startup_app_num']?list[i]['startup_app_num']:'0');
                                }else if(type=="avg_user_time"){
                                    var num = list[i]['capita_startup_num'];
                                    if(num){
                                        serises.push(toDecimal(num));
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

                        opt.series[0].data = serises;
                        opt.xAxis.data = xdata;
                        if(xdata.length >= 10){
                            var n = (xdata.length / 10);
                            opt.xAxis.axisLabel.interval = Math.ceil(n);
                        }else{
                            opt.xAxis.axisLabel.interval =0;
                        }
                        opt.xAxis.axisLabel.formatter = toXdata;

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
                qst.queryDate = dayHandle(startdate, -1);
                if(type=="user"){
                    urls2 = "./getStartupUsersInfoTrend";
                }else if(type=="count"){
                    urls2 = "./getStartupTimesInfoTrend";
                }else if(type=="avg_user_time"){
                    urls2 = "./getAvgStartupUsersInfoTrend";
                }else if(type=="avg_time"){
                    urls2 = "./getSetupTimeOnlineInfoTrend ";
                }
            }else{
                qst2.is_current = 0;
                qst2.start_day = prestartdate;
                qst2.end_day = preenddate;
                urls2 = getLocation() + proxy("/dataApiQuery/gct_base_zonglan_qushi_data", qst2);
            }
            $.ajax({
                url: urls2,
                type: "post",
                dataType: "json",
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                data: JSON.stringify((startdate == enddate && startdate == getCurrentDay())?qst:''),
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
                                            if(type=="user" || type=="count"){//不保留小数点
                                                serises2.push(Number(list2[i].value));
                                            }else if(type=="avg_user_time"){//保留两位小数点
                                                serises2.push(toDecimal(Number(list2[i].value)));
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
                            if(list && list.length>0){
                                for(var i=0; i<list.length; i++){
                                    realdt.push(list[i].real_dt?list[i].real_dt:"");
                                    if(list[i]) {
                                        if(type=="user"){
                                            serises2.push(list[i].startup_user_num?list[i].startup_user_num:'0');
                                        }else if(type=="count"){
                                            serises2.push(list[i].startup_app_num?list[i].startup_app_num:'0');
                                        }else if(type=="avg_user_time"){
                                            serises2.push(list[i].capita_startup_num?toDecimal(list[i].capita_startup_num):'0');
                                        }else if(type=="avg_time"){
                                            serises2.push(list[i].average_stop_time?(Number(list[i].average_stop_time).toFixed(3)):'0');
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
        },
        error:function () {
            myChart.hideLoading();
            myChart.setOption(opt,true);
        }
    });
}

/*********前端交互*********/
//趋势图切换
$(".tab > div").click(function () {
    //埋点
    var obj = {
        "eventType": ["qstSwitch"],
    }
    getPointParameter(obj);

    $(this).addClass("bg_active").siblings("div").removeClass("bg_active");
    var i = $(this).index();
    // count 启动次数 user 启动用户数，avg_time 次均留长，avg_user_time人均启动次数
    if(i==0){
        $("#appType_qst,#userType_qst").attr("disabled","true").css("cursor","not-allowed");
        allUserTrend("new");
    }else if(i==1){
        $("#appType_qst,#userType_qst").removeAttr("disabled").css("cursor","pointer");
        qushituShow("count");
    }else if(i==2){
        $("#appType_qst,#userType_qst").removeAttr("disabled").css("cursor","pointer");
        qushituShow("avg_time");
    }else if(i==3){
        $("#appType_qst,#userType_qst").removeAttr("disabled").css("cursor","pointer");
        qushituShow("user");
    }else if(i==4){
        $("#appType_qst,#userType_qst").removeAttr("disabled").css("cursor","pointer");
        qushituShow("avg_user_time");
    }
});

//丁管家趋势图切换
$(".dgj_tab > div").click(function () {
    //埋点
    var obj = {
        "eventType": ["qstSwitch"],
    }
    getPointParameter(obj);
    $(this).addClass("bg_active").siblings("div").removeClass("bg_active");
    var i = $(this).index();
    if(i==0){
        qushituShow("count");
    }else if(i==1){
        qushituShow("avg_time");
    }else if(i==2){
        qushituShow("user");
    }else if(i==3){
        qushituShow("avg_user_time");
    }
});

//切换条件查询统计数据
function dataStatisticSelect(businessType) {
    //埋点
    var pointArr=[];
    pointArr.push("appType");

    if(businessType=="0"){
        registUser();
        pointArr.push("userType");
    }
    var obj = {
        "eventType": pointArr,
    }
    getPointParameter(obj);

    startupTimes(businessType);
    startupUser(businessType);
    onlineTime(businessType);
    window.setTimeout(function () {
        avgStartTimes(businessType);
    },300);
}

//切换条件查询趋势图
function qushituSelect(type) {
    //埋点
    var pointArr=[];
    pointArr.push("appType")

    if(businessType=="0"){
        registUser();
        pointArr.push("userType");
    }
    var obj = {
        "eventType": pointArr,
    }
    getPointParameter(obj);

    if(type=="0"){
        var tab_text = $(".tab .bg_active").text();
        if(tab_text=="新增注册用户数"){
            allUserTrend("new");
        }else if(tab_text=="启动次数"){
            qushituShow("count");
        }else if(tab_text=="次均停留时长(s)"){
            qushituShow("avg_time");
        }else if(tab_text=="启动用户数"){
            qushituShow("user");
        }else if(tab_text=="人均启动次数"){
            qushituShow("avg_user_time");
        }
    }else{
        var tab_text = $(".dgj_tab .bg_active").text();
        if(tab_text=="启动次数"){
            qushituShow("count");
        }else if(tab_text=="次均停留时长(s)"){
            qushituShow("avg_time");
        }else if(tab_text=="启动用户数"){
            qushituShow("user");
        }else if(tab_text=="人均启动次数"){
            qushituShow("avg_user_time");
        }
    }
}
