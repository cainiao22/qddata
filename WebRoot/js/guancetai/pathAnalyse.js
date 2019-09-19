/**
 * Created by 五道杠 on 2019/4/22.
 */
var flagDeviceType = "all";//端标识默认
var flagStartDate = getNowDay_7();//默认起始日期标识；
var flagEndDate = getNowDay();//默认最后一天标识
var flagAnalysis = "pageAnalysis";//默认页面
var flagSelect;//指定页面的所有数据
var flagEndPi//指定页面的下拉框里面的页面
var flagIds = "";//发送请求的ids拼接

//-------------以下是复制过来的，仅用于日期查找-------------
var businessType = $(".tabControl > a > .active").attr("data-type");//千丁 or 丁管家
var startdate = '' || getNowDay(), enddate = '' || getNowDay();
var param = {
    "queryDate": getCurrentDay(),
}
$(document).ready(function () {
    //埋点
    var obj = {
        "eventType": ["pageAnalyse"],
    }
    if (businessType == 0) {
        obj.eventType.push('qding');
    } else {
        obj.eventType.push('dgj');
    }
    getPointParameter(obj);
    setPageName(businessType);
    function minTime() {
        //只能选择当月的日期
        var datetime = new Date();
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        return year + "-" + month + "-01"
    }

    $('.c-datepicker-data-input').eq(0).val(dayHandle(getCurrentDay(), -7));//备注
    $('.c-datepicker-data-input').eq(1).val(dayHandle(getCurrentDay(), -1));
    //年月日范围     触发时间选择器
    $('#timeSelect').datePicker({
        hasShortcut: true,
        isRange: true,
        show: calendarshow,
        min: minTime(),//只能选择当月
        max: dayHandle(getCurrentDay(), -1),
        shortcutOptions: [{
            name: '过去7天',
            day: '-7,-1'
        }, {
            name: '过去15天',
            day: '-15,-1'
        }, {
            name: '过去30天',
            day: '-30,-1'
        }, {
            name: '过去60天',
            day: '-60,-1'
        }, {
            name: '过去90天',
            day: '-90,-1'
        }],
        hide: function (type) {
            flagIds = '';
            while (ul.nextElementSibling) {
                ul.parentElement.removeChild(ul.nextElementSibling)
            }
            this.$input.eq(0).val(this.$input.eq(0).val().substr(0, 10));
            this.$input.eq(1).val(this.$input.eq(1).val().substr(0, 10));
            startdate = this.$input.eq(0).val();
            enddate = this.$input.eq(1).val();
            $(".tab_1 > div,.tab_2 > div").eq(0).addClass("bg_active").siblings("div").removeClass("bg_active");
            //埋点
            var obj = {
                "eventType": ["dateSelect"]
            };
            getPointParameter(obj);

            //---------时间选择请求数据-------------
            flagEndDate = enddate;
            flagStartDate = startdate;
            var appType = document.getElementById("appType");
            flagDeviceType = deviceType = appType[appType.selectedIndex].value;
            // 会发送两次请求
            if (flagAnalysis == "eventAnalysis") {
                var params1 = {
                    "analysisType": flagAnalysis,//全局定义事件标识
                    "deviceType": deviceType,
                    "endDate": flagEndDate,
                    "endPi": flagEndPi,
                    "ids": flagIds,
                    "startDate": flagStartDate,
                    "productId": 0,
                    "status": 0
                };
                var allPage1;
                // 页面获取
                $.ajax({
                    url: "./getBehaviorAnalysis",
                    type: "post",
                    dataType: "json",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    async: false,
                    data: JSON.stringify(params1),
                    success: function (res) {
                        allPage1 = res;
                    }
                });
                allPage = [];
                //过滤掉没用的name
                for (var i = 0; i < allPage1.length; i++) {
                    var obj = allPage1[i];
                    if (obj['name']) {
                        allPage.push(obj)
                    }
                }
                effectSwitching();
                newTreeMenu();
                return
            }
            //-----------------------
            // effectSwitching();
            sgmlPageFn();
            function effectSwitching() {//效果切换
                ul.innerHTML = null;//把ul前面的结构清空  不然会一直添加
                for (var i = 0; i < allPage.length; i++) {
                    var addValueNumber = addNumber(allPage, allPage[i]['number']);
                    var ap = allPage[i];
                    var addNum = addValueNumber.slice(0, addValueNumber.indexOf("%"));
                    if (addNum == 0) {
                        continue
                    }
                    var li = document.createElement("li");
                    li.setAttribute("ids", ap["id"]);

                    if (ap["name"].length > 14) {
                        li.style.width = "240px"
                    } else if (ap['name'].length > 4) {
                        li.style.width = "200px";
                    } else {
                        li.style.width = "137px"
                    }
                    var a = "left:64px";
                    var b = "left:40px";
                    li.innerHTML = ap['name'] + `<div class="" style="` + (ap['name'].length > 4 ? a : b) + `"><div class="top"></div><div class="bot"></div></div><h5>` + addValueNumber + `</h5>`
                    ul.appendChild(li)


                }
                // newTreeMenu();
//            绑定点击效果
                var li = ul.getElementsByTagName('li');
                for (var i = 0; i < li.length; i++) {
                    function pageJumpFun() {
//   树形菜单跳转页面使用
                        for (var j = 0; j < li.length; j++) {
                            var o = li[j];
                            li[j].className = '';
                            li[j].firstElementChild.style.display = "none"
                        }
                        this.className = "select";
                        this.firstElementChild.className = "select3";
                        this.firstElementChild.style.display = "block";
                    }

                    li[i].addEventListener('click', pageJumpFun, true)
                }
            }


            //------------end-----------
        }
    });
    startdate = getCurrentDay();
    enddate = getCurrentDay();

});

//日历显示时
function calendarshow() {
    $('.c-datepicker-picker').css({'left': 'initial', 'right': '25px'});
    $('.c-datepicker-date-range-picker__editor-wrap').eq(1).css('display', 'none');
    $('.c-datepicker-date-range-picker__editor-wrap').eq(3).css('display', 'none');
}
function setPageName(type) {
    $('#pageName').html("");
    var param = {
        "businessType": type
    };
    $.ajax({
        url: getLocation() + proxy("/dataApiQuery/gct_collect_log_page_info", param),
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (res) {
            if (res) {
                var list = res.data;
                var str = '<option value="all" selected>全部</option>';
                for (var i = 0; i < list.length; i++) {
                    str += '<option value="' + list[i].pageId + '">' + list[i].pageName + '</option>';
                }
                $('#pageName').append(str);
                $("#pageName").select2({
                    placeholder: '全部',
                    allowClear: true,
                    multiple: false
                });
            }
        }
    });
}
//------------------------------------------end---------------
var pageParams = {
    "productId": 0,
    "status": 0
};
$.ajax({//指定页面接口
    url: "./getMaidianPage",
    type: "post",
    dataType: "json",
    headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
    },
    async: false,
    data: JSON.stringify(pageParams),
    success: function (res) {
        flagSelect = res;
        var sgmlPage = document.getElementById("sgmlPage");
        //   获取指定页面的下拉菜单列表页
        //          动态添加下拉列表的数据
        for (var da in flagSelect) {
            var op = document.createElement("option");
            op.value = da;
            op.innerHTML = flagSelect[da];
            sgmlPage.appendChild(op)
        }
    }
});


function addNumber(obj, num) { // 百分比使用
    var newNum = 0;
    for (var i = 0; i < obj.length; i++) {
        newNum += parseInt(obj[i]['number'])
    }
    return parseInt((Math.round((num / newNum) * 100))) + "%" + "/" + num
}
function clickChangeColor() {
    // 绑定点击效果变蓝色的
    var ul = document.getElementById("ul");
    var li = ul.getElementsByTagName('li');
    for (var i = 0; i < li.length; i++) {
        function pageJumpFun() {
//   树形菜单跳转页面使用
            for (var j = 0; j < li.length; j++) {
                var o = li[j];
                li[j].className = '';
                li[j].firstElementChild.style.display = "none"
            }
            this.className = "select";
            this.firstElementChild.className = "select3";
            this.firstElementChild.style.display = "block";
        }
        li[i].addEventListener("click", pageJumpFun, true)

    }
}
function getNowDay_7() {
    //当天的前7天  此一次进入页面使用
    var datetime = new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date
}
function getNowDay() {
    //当天的前一天日期
    var datetime = new Date(new Date().getTime() - 24 * 60 * 60 * 1000)
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date
}

var allPage1;////全局定义全部页面  存储后端返回的数据
var allPage = [];//后端的数据去掉null

//首次进入的页面  页面默认日期是近七天
function timeSelectDate() {
    /*  console.log(flagAnalysis);
     console.log(flagDeviceType);
     console.log(flagEndDate);
     console.log(flagStartDate);*/

    var params1 = {//首次进入页面的默认前七天
        "analysisType": flagAnalysis,
        "deviceType": flagDeviceType || 'all',
        "endDate": flagEndDate,
        "endPi": flagEndPi,
        "ids": flagIds,
        "startDate": flagStartDate,
        "productId": 0,
        "status": 0
    };

    // 页面获取
    $.ajax({
        url: "./getBehaviorAnalysis",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        async: false,
        data: JSON.stringify(params1),
        success: function (res) {
            allPage1 = res;
        }
    });
    for (var i = 0; i < allPage1.length; i++) {
        var obj = allPage1[i];
        if (obj['name']) {
            allPage.push(obj)
        }
    }
}
timeSelectDate();
//树形菜单点击切换效果
function effectSwitching() {
    var ul = document.getElementById("ul");
    for (var i = 0; i < allPage.length; i++) {
        var ap = allPage[i];
        var addValueNumber = addNumber(allPage, ap['number']);
        var addNum = addValueNumber.slice(0, addValueNumber.indexOf("%"));
        var li = document.createElement("li");
        li.setAttribute("ids", ap["id"]);
        if (addNum == 0)  continue;
        if (ap["name"].length > 14) {
            li.style.width = "240px"
        } else if (ap['name'].length > 4) {
            li.style.width = "200px";
        } else {
            li.style.width = "137px"
        }
        var a = "left:64px";
        var b = "left:40px";
        li.innerHTML = ap['name'] + `<div class="" style = ` + (ap['name'].length > 4 ? a : b ) + `><div class="top"></div><div class="bot"></div></div><h5>` + addValueNumber + `</h5>`
        ul.appendChild(li);
        if (ap['id'] == flagEndPi) {
            li.style.pointerEvents = 'none';
            //限制点击
        }

    }
    clickChangeColor();
    newTreeMenu()
};
effectSwitching();
//下拉列表跳转到指定的页面
function sgmlPageFn() {
    flagIds = '';
//获取下拉列表的值
    var sgmlPage = document.getElementById('sgmlPage');
    var selectValue = sgmlPage[sgmlPage.selectedIndex].value;
    flagEndPi = selectValue == "all" ? '' : selectValue;
    ul.innerHTML = null;
    while (ul.nextElementSibling) {
        ul.parentElement.removeChild(ul.nextElementSibling)
    }
    var params = {
        "analysisType": flagAnalysis,
        "deviceType": flagDeviceType,
        "endDate": flagEndDate,
        "endPi": flagEndPi,
        "ids": flagIds,
        "startDate": flagStartDate,
        "productId": 0,
        "status": 0
    };
    $.ajax({
        url: "./getBehaviorAnalysis",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        async: false,
        data: JSON.stringify(params),
        success: function (res) {
            loopValue = res;
        }
    });
    allPage = [];
    //过滤掉没用的name
    for (var i = 0; i < loopValue.length; i++) {
        var obj = loopValue[i];
        if (obj['name']) {
            allPage.push(obj)
        }
    }
    effectSwitching()

}

function equipmentChange2() {
    // 端改变以后第一行样式加不上，只能这样了
    var ul = document.getElementById("ul");
    var lis = ul.getElementsByTagName("li");
    for (let i = 0; i < lis.length; i++) {
        lis[i].addEventListener("click", function () {
            for (let j = 0; j < lis.length; j++) {
                lis[j].className = '';
                lis[j].firstElementChild.style.display = "none";
            }
            lis[i].className = "select";
            lis[i].firstElementChild.style.display = "block";
            lis[i].firstElementChild.className = "select3"
        }, true)
    }
}
var deviceType;
//下拉列表ios与android端 发送数据请求
function equipmentChange() {
    flagIds = "";
    while (ul.nextElementSibling) {
        ul.parentElement.removeChild(ul.nextElementSibling)
    }
    var appType = document.getElementById("appType");
    flagDeviceType = deviceType = appType[appType.selectedIndex].value;
    if (startdate == getNowDay() && enddate == getNowDay()) {//判断是不是当当天
        startdate = getNowDay_7();
    }
    var params = {
        "analysisType": flagAnalysis,
        "deviceType": deviceType,
        "endDate": flagEndDate,
        "endPi": flagEndPi,
        "ids": flagIds,
        "startDate": flagStartDate,
        "productId": 0,
        "status": 0
    };
    $.ajax({
        url: "./getBehaviorAnalysis",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        async: false,
        data: JSON.stringify(params),
        success: function (res) {
            allPage1 = res;
        }
    });
    allPage = [];
    //过滤掉没用的name
    for (var i = 0; i < allPage1.length; i++) {
        var obj = allPage1[i];
        if (obj['name']) {
            allPage.push(obj)
        }
    }
    for (var i = 0; i < allPage.length; i++) {
        var addValueNumber = addNumber(allPage, allPage[i]['number']);
        var ap = allPage[i];
        var addNum = addValueNumber.slice(0, addValueNumber.indexOf("%"));
        if (addNum == 0) {
            continue
        }
        var li = document.createElement("li");
        li.setAttribute("ids", ap["id"]);
        if (ap["name"].length > 14) {
            li.style.width = "240px"
        } else if (ap['name'].length > 4) {
            li.style.width = "200px";

        } else {
            li.style.width = "137px"
        }

        var a = "left:64px";
        var b = "left:40px";
        li.innerHTML = ap['name'] + `<div class="" style="` + (ap['name'].length > 4 ? a : b) + `"><div class="top"></div><div class="bot"></div></div><h5>` + addValueNumber + `</h5>`

        ul.appendChild(li);

    }
    // newTreeMenu();

    function effectSwitching() {//效果切换
        ul.innerHTML = null;//把ul前面的结构清空  不然会一直添加
        for (var i = 0; i < allPage.length; i++) {
            var addValueNumber = addNumber(allPage, allPage[i]['number']);
            var ap = allPage[i];
            var addNum = addValueNumber.slice(0, addValueNumber.indexOf("%"));
            if (addNum == 0) {//判断百分比前面是不是0
                continue
            }
            var li = document.createElement("li");
            li.setAttribute("ids", ap["id"]);
            if (ap["name"].length > 14) {
                li.style.width = "240px"
            } else if (ap['name'].length > 4) {
                li.style.width = "200px";
            } else {
                li.style.width = "137px"
            }
            var a = "left:64px";
            var b = "left:40px";
            li.innerHTML = ap['name'] + `<div class="" style="` + (ap['name'].length > 4 ? a : b) + `"><div class="top"></div><div class="bot"></div></div><h5>` + addValueNumber + `</h5>`;
            ul.appendChild(li)
            if (ap['id'] == flagEndPi) {
                li.style.pointerEvents = 'none';
                //限制点击
            }
        }
        newTreeMenu();
//            绑定点击效果
        var li = ul.getElementsByTagName('li');
//  下面三行是为了默认进入后样式显示第一个
        if (li.innerText) {
        } else {
            return
        }
        li[0].className = "select";
        li[0].firstElementChild.className = "select3";
        li[0].firstElementChild.style.display = "block";
        for (var i = 0; i < li.length; i++) {
            function pageJumpFun() {
//   树形菜单跳转页面使用
                for (var j = 0; j < li.length; j++) {
                    var o = li[j];
                    li[j].className = '';
                    li[j].firstElementChild.style.display = "none";
                    li[j].firstElementChild.className = "";
                }
                li[i].className = "select";
                li[i].firstElementChild.className = "select3";
                li[i].firstElementChild.style.display = "block";
            }

            li[i].addEventListener("click", pageJumpFun, true)
        }
    };
    effectSwitching();
    // sgmlPageFn()
}

//---------------------end---------------------------


//---------------------事件分析start------------------------------

//        右上角切换分析事件
changeEvent();
function changeEvent() {
    flagEndPi = "";
    var inputs = document.getElementById("input");
    var pageTree = document.getElementById("pageTree");
    var input = inputs.getElementsByTagName("input");
    var pageTable = document.getElementById('pageTable');


//事件分析触发
    function testEvev() {
        flagEndPi='';
        pageTable.style.display = "none";
        pageTree.style.marginTop = "51px";
        // ul.style.display = "block";//预防与页面的display打架
        // getEvent_analysis()   //    数据请求
        var appType = document.getElementById("appType");
        deviceType = appType[appType.selectedIndex].value;
        // console.log(flagAnalysis);
        // console.log(flagEndDate);
        // console.log(flagStartDate);
        // console.log("端"+flagDeviceType);
        var params = {
            "analysisType": flagAnalysis,
            "deviceType": flagDeviceType,
            "endDate": flagEndDate,
            "endPi": "",
            "ids": flagIds,
            "startDate": flagStartDate,
            "productId": 0,
            "status": 0
        }
        var allPage1 = [];
        $.ajax({
            url: "./getBehaviorAnalysis",
            type: "post",
            dataType: "json",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            async: false,
            data: JSON.stringify(params),
            success: function (res) {
                allPage = res;
                //    事件分析
                var allPage1 = [];
                for (var i = 0; i < allPage.length; i++) {
                    var obj = allPage[i];
                    if (obj['name']) {
                        allPage1.push(obj)
                    }
                }
                ul.innerHTML = null;//清空原来的ul里面的子元素
                for (var i = 0; i < allPage1.length; i++) {
                    if (!allPage1) break;
                    var addValueNumber = addNumber(allPage1, allPage1[i]['number'])
                    var ap = allPage1[i];
                    var addNum = addValueNumber.slice(0, addValueNumber.indexOf("%"));
                    if (addNum == 0) {//判断百分比前面是不是0
                        continue
                    }
                    var li = document.createElement("li");
                    li.setAttribute("ids", ap["id"]);
                    var flag;
                    if (ap["name"].length > 4 && ap["name"].length <= 10) {
                        li.style.width = "200px";
                        flag = "left:70px"
                    } else if (ap["name"].length > 10 && ap["name"].length < 20) {
                        li.style.width = "220px"
                    } else if (ap["name"].length >= 14) {

                        li.style.width = "240px"
                    } else {
                        li.style.width = "137px";
                        flag = "left:40px"
                    }
                    li.innerHTML = ap['name'] + `<div class="" style="` + flag + `"><div class="top"></div><div class="bot"></div></div><h5>` + addValueNumber + `</h5>`;
                    ul.appendChild(li)

                }
                clickChangeColor();
                newTreeMenu();
            }
        })

    }

//    页面事件触发
    function testPage() {
        if(sgmlPage[sgmlPage.selectedIndex].value=="all"){
            flagEndPi='';
        }else {
            flagEndPi=sgmlPage[sgmlPage.selectedIndex].value;
        }

        firstPage();
        pageTable.style.display = "block";
        pageTree.style.marginTop = 0;
        // history.go(0)//回到页面分析刷新页面
        var params = {
            "analysisType": flagAnalysis,
            "deviceType": deviceType,
            "endDate": flagEndDate,
            "endPi": flagEndPi,
            "ids": flagIds,
            "startDate": flagStartDate,
            "productId": 0,
            "status": 0
        };
        $.ajax({
            url: "./getBehaviorAnalysis",
            type: "post",
            dataType: "json",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            async: false,
            data: JSON.stringify(params),
            success: function (res) {
                allPage1 = res;
            }
        })
        var allPage = [];
        for (var j = 0; j < allPage1.length; j++) {
            var ap = allPage1[j];
            if (ap["name"]) {
                allPage.push(ap)
            }
        }
        var ul = document.getElementById("ul");
        ul.innerHTML = null;
        for (var j = 0; j < allPage.length; j++) {
            var ap = allPage[j];
            var addValueNumber = addNumber(allPage, ap['number']);
            var addNum = addValueNumber.slice(0, addValueNumber.indexOf("%"));
            var li = document.createElement("li");
            li.setAttribute("ids", ap["id"]);
            if (addNum == 0) {
                continue
            }
            if (ap["name"].length > 14) {
                li.style.width = "240px"
            } else if (ap['name'].length > 4) {
                li.style.width = "200px";
            } else {
                li.style.width = "137px"
            }
            var a = "left:64px";
            var b = "left:40px";
            li.innerHTML = ap['name'] + `<div class="" style = ` + (ap['name'].length > 4 ? a : b ) + `><div class="top"></div><div class="bot"></div></div><h5>` + addValueNumber + `</h5>`
            ul.appendChild(li);
            if (ap['id'] == flagEndPi) {
                li.style.pointerEvents = 'none';
                //限制点击

            }
        }

        //绑定点击效果
        clickChangeColor();
        newTreeMenu()
    }

    //-------------------------监测右上角事件分析与页面分析的选项事件-----------------------------------
    for (var i = 0; i < input.length; i++) {
        if (input[i].value == "eventAnalysis") {//事件分析
            input[i].onchange = function () {
                flagIds = ''
                ulChange();
                flagAnalysis = "eventAnalysis";
                testEvev()
            }
        } else if (input[i].value == "pageAnalysis") {
            input[i].onchange = function () {
                // history.go(0);
                flagIds = '';
                ulChange();
                flagAnalysis = "pageAnalysis";
                testPage()

            }
        }
    }
}
//-----------------------------end----------------------------------------------------------------------

//------------------------事件分析end----------------------------


//首次进入页面下面一行菜单下面列表为空
function firstPage() {
    var ul = document.getElementById("ul");
    var lis = ul.getElementsByTagName('li');
    for (var i = 0; i < lis.length; i++) {
        lis[i].className = '';
        lis[i].firstElementChild.style.display = 'none';
    }
}
firstPage();
function ulChange() {
    var uls = pageTree.getElementsByTagName("ul");
    for (var j = 1; j < uls.length; j++) {
        pageTree.removeChild(uls[j]);
        j--;
    }
}
//-----------------------树形菜单继续往下走-------------------------
newTreeMenu();
function newTreeMenu() {
    var pageTree = document.getElementById("pageTree");
    var ul = document.getElementById("ul");
    var lis = ul.getElementsByTagName("li");
    for (let i = 0; i < lis.length; i++) {
        let cur = lis[i];
        cur.onclick = function () {
            var that = this;
            var newIds = cur.getAttribute("ids");
            flagIds = newIds;
            downTreeMenu(newIds, that);
        }
    }
}
function downTreeMenu(ids, that) {
    if (flagAnalysis == "eventAnalysis") {
        flagEndPi = "";
    }
    //经常要用的递归
    while (that.parentElement.nextElementSibling) {
        pageTree.removeChild(that.parentElement.nextElementSibling)//干掉父亲的弟弟节点
    }

    var loopValue;
    var loopValue2 = [];
    //通过获取li的宽度来设置下标箭头的left值

    if (that.style.width <= 127) {
        that.firstElementChild.style.left = "30px"
    }
    if (flagIds.split(",").length > 9) {
        //只能到第十级，把最底下一层样式清空
        that.className = '';
        that.firstElementChild.style.display = 'none';
        return
    }

    var params = {
        "analysisType": flagAnalysis,
        "deviceType": flagDeviceType,
        "endDate": flagEndDate,
        "endPi": flagEndPi,
        "ids": flagIds,
        "startDate": flagStartDate,
        "productId": 0,
        "status": 0
    };
    $.ajax({
        url: "./getBehaviorAnalysis",
        type: "post",
        dataType: "json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        async: false,
        data: JSON.stringify(params),
        success: function (res) {
            loopValue = res;
        }
    });
    for (var i = 0; i < loopValue.length; i++) {
        var obj = loopValue[i];
        if (obj["name"]) {
            loopValue2.push(obj)
        }
    }
    var ul = document.createElement("ul");
    if (flagAnalysis != "pageAnalysis") {
        ul.style.display = 'block';
    }
    if (!loopValue2.length) {
        //去掉null的值里面为空的话直接return
        //如果没有数据的话。把最底下一层样式清空
        that.className = '';
        that.firstElementChild.style.display = 'none';
        return
    }
    for (var i = 0; i < loopValue2.length; i++) {
        var loop = loopValue2[i];
        var addValueNumber = addNumber(loopValue2, loop['number']);
        var addNum = addValueNumber.slice(0, addValueNumber.indexOf("%"));

        if (addNum == 0) {
            continue
        }
        let li = document.createElement('li');
        li.setAttribute("ids", loop['id']);
        if (loop["name"].length >= 14) {
            li.style.width = "240px";

        } else if (loop["name"].length > 4 && loop["name"].length < 14) {
            li.style.width = "200px";
        }
        li.innerHTML = loop["name"] + `<div class=""><div class="top"></div><div class="bot"></div></div></div><h5>` + addValueNumber + `</h5>`
        ul.appendChild(li);
        if (loop['id'] == flagEndPi) {
            li.style.pointerEvents = 'none';
            //限制点击
        }
        var par;
        li.addEventListener('click', function getAll() {
            var lic = li.parentNode.childNodes;//获取所有
            for (var j = 0; j < lic.length; j++) {
                lic[j].className = "";
                lic[j].firstElementChild.style.display = "none"
            }
            li.className = "select";
            li.firstElementChild.className = "select3";
            li.firstElementChild.style.display = "block";
            var that = this;
            //如果点击同一个li标签，会一直往ids里面加同一个数据，
            if (this.parentElement == par) {
                //如果点击的当前元素与上次点击的元素是同一个级别的    把上一个截了
                if (this.parentElement = this) {
                    var uls = pageTree.getElementsByTagName("ul");
                    for (var j = 0; j < uls.length; j++) {
                        var ul = uls[j];
                        if (this.parentElement == ul) {
                            var a = flagIds.split(",");
                            a.splice(j)
                            a.push(this.getAttribute('ids'));
                            flagIds = a.toString();
                        }
                    }
                }
                flagIds = flagIds.slice(0, flagIds.lastIndexOf(","));
            }
            flagIds = flagIds + "," + this.getAttribute("ids");
            par = this.parentElement;
            downTreeMenu(li.getAttribute("ids"), that);
        }, true);
    }
    pageTree.appendChild(ul);


}


