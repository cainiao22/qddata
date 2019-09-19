/**
 * 观测台埋点公共方法
 */
/**************埋点 begin*******************/

var points = {
    //事件参数
    "eventParams": [
        {
            "key": "appType",
            "value": "端选择",
            indexid: 'gct_index001'
        },
        {
            "key": "userType",
            "value": "用户类型选择",
            indexid: 'gct_index002'
        },
        {
            "key": "dateSelect",
            "value": "时间选择",
            indexid: 'gct_index003'
        },
        {
            "key": "qstSwitch",
            "value": "图形切换",
            indexid: 'gct_index004'
        },
        {
            "key": "qst",
            "value": "数据趋势图",
            indexid: 'gct_index005'
        },
        {
            "key": "tableSwitch",
            "value": "表切换",
            indexid: 'gct_index006'
        },
        {
            "key": "tableDownload",
            "value": "表下载",
            indexid: 'gct_index007'
        },
        {
            "key": "dateType",
            "value": "时间类型选择",
            indexid: 'gct_index008'
        },
        {
            "key": "tableExtension",
            "value": "表格扩展",
            indexid: 'gct_index009'
        },
        {
            "key": "tableExtensionParams",
            "value": "表格扩展参数选择",
            indexid: 'gct_index010'
        },
        {
            "key": "lastNodeSelect",
            "value": "末端节点选择",
            indexid: 'gct_index011'
        },
        {
            "key": "propertySelect",
            "value": "物业公司选择",
            indexid: 'gct_index012'
        },
        {
            "key": "zl",
            "value": "总览",
            indexid: 'gct_page001'
        },
        {
            "key": "pageAnalyse",
            "value": "页面分析",
            indexid: 'gct_page002'
        },
        {
            "key": "eventAnalyse",
            "value": "事件分析",
            indexid: 'gct_page003'
        },
        {
            "key": "activeAnalyse",
            "value": "活跃分析",
            indexid: 'gct_page004'
        },
        {
            "key": "lcAnalyse",
            "value": "留存分析",
            indexid: 'gct_page005'
        },
        {
            "key": "versionAnalyse",
            "value": "版本分析",
            indexid: 'gct_page006'
        },
        {
            "key": "shebeiAnalyse",
            "value": "设备分析",
            indexid: 'gct_page007'
        },
        {
            "key": "qding",
            "value": "千丁app",
            indexid: 'gct_page008'
        },
        {
            "key": "dgj",
            "value": "丁管家",
            indexid: 'gct_page009'
        },
        {
            "key": "operationType",
            "value": "操作系统",
            indexid: 'gct_page010'
        },
        {
            "key": "clientType",
            "value": "终端型号",
            indexid: 'gct_page011'
        },
        {
            "key": "pathAnalyse",
            "value": "路径分析",
            indexid: 'gct_page012'
        },
        {
            "key": "versionAnalyse2",
            "value": "版本分析",
            indexid: 'gct_page013'
        },
        {
            "key": "denglu",
            "value": "登陆",
            indexid: 'login001'
        },
        {
            "key": "tuichu",
            "value": "退出",
            indexid: 'exit001'
        }
    ]
};

//获取cookie
function getCookie(name) {
    var arrcookie = (document.cookie).split("; ");//分割
    for (var i = 0; i < arrcookie.length; i++) {
        var arr = arrcookie[i].split("=");
        if (arr[0] == name) {
            return arr[1];
        }
    }
    return "";
}

//埋点
function getPointParameter(obj, type){
    var paramspoint = {};
    paramspoint.user = getCookie("username")?getCookie("username"): '';
    var a ='';

    paramspoint.user_name = getCookie("realname")? decodeURI(getCookie("realname")): '';
    //获取一级来源及二级来源
    paramspoint.fir_source = 'bigdata';
    paramspoint.sec_source = 'event006';

    //获取在线时间及当前事件发生的时间
    var firsttime = new Date().getTime();
    paramspoint.online_time = sessionStorage.getItem('firstland')?(firsttime - sessionStorage.getItem('firstland')):0;

    sessionStorage.setItem('firstland', firsttime);
    paramspoint.time = firsttime;
    //获取上一次点击事件和本次的点击事件
    var now_event = JSON.stringify(getIndexId(points.eventParams, obj.eventType?obj.eventType:[], 'key'));

    if(sessionStorage.getItem('ref_event')){
        paramspoint.ref_event = JSON.parse(sessionStorage.getItem('ref_event'));
        sessionStorage.setItem('ref_event', JSON.stringify(now_event));
    }else{
        paramspoint.ref_event = {'event': 'login001'};
        sessionStorage.setItem('ref_event', JSON.stringify(now_event));
    }

    paramspoint.event = type == 1?{event: 'login001'}:now_event;
    paramspoint.ref_url = sessionStorage.getItem('ref_url')?sessionStorage.getItem('ref_url'):'';
    paramspoint.url = window.location.href;
    sessionStorage.setItem('ref_url', window.location.href);
    // console.log(paramspoint)
    $.ajax({
        url: getLocation()+ proxy_2('/pushTrackMsg?trackMsg=' + encodeURIComponent(JSON.stringify(paramspoint))),
        type: "get",
        success: function (res) {}
    });
}

//获取当前指标的事件
function getIndexId(res, arr, ming){
    var str = [];
    for(var i=0; i<arr.length; i++){
        for(var j=0; j<res.length; j++){
            if(res[j][ming] == arr[i]){
                str.push(res[j].indexid);
            }
        }
    }
    return str;
}

/**************埋点 end*******************/