/**
 * Created by 五道杠 on 2019/4/29.
 */
/**
 * 观测台--活跃分析
 */
var flagProperTyId;
var businessType = $(".tabControl > a > .active").attr("data-type");//千丁 or 丁管家

var startdate = '', enddate = '', startdate_2 = '', enddate_2 = '';

$(document).ready(function () {
    //埋点
    var obj = {
        "eventType": ["activeAnalyse"],
    }
    if (businessType == 0) {
        obj.eventType.push('qding');
    } else {
        obj.eventType.push('dgj');
    }

    getPointParameter(obj);

    $('.c-datepicker-data-input').eq(0).val(getCurrentDay());
    $('.c-datepicker-data-input').eq(1).val(getCurrentDay());
    $('.c-datepicker-data-input').eq(2).val(dayHandle(getCurrentDay(), -7));
    $('.c-datepicker-data-input').eq(3).val(dayHandle(getCurrentDay(), -1));

    //趋势图年月日范围
    startdate = getCurrentDay();
    enddate = getCurrentDay();
    $('.setdate-1').datePicker({
        hasShortcut: true,
        isRange: true,
        show: calendarshow,
        min: "2018-11-01",
        max: dayHandle(getCurrentDay(), -1),
        shortcutOptions: [{
            name: '今天',
            day: '0,0'
        }, {
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

            //-----------下面是时间统一一起 别问为啥  我也不知道---
            function getNowFormatDate() {
                var date = new Date();
                var seperator1 = "-";
                var year = date.getFullYear();
                var month = date.getMonth() + 1;
                var strDate = date.getDate();
                if (month >= 1 && month <= 9) {
                    month = "0" + month;
                }
                if (strDate >= 0 && strDate <= 9) {
                    strDate = "0" + strDate;
                }
                var currentdate = year + seperator1 + month + seperator1 + strDate;
                return currentdate;
            }

            startdate_2 = startdate;
            enddate_2 = enddate;

            /* (function () {
             -------------------这些代码是活跃分析里面的当天日期，获取当天日期后把原来的DOM隐藏，创建DOM添加进去  js看懂得话是可以用jq操作的----------------------------------------------------
             if (startdate == enddate) {
             if (startdate==getNowFormatDate()){
             (function () {
             var params = {
             "businessType": 1,
             "deviceStatus": "",
             "lesseeId": "1099100700000006108",
             "limit": 100,
             "queryDate": getNowFormatDate(),//当天日期
             "source": ""
             };
             var activeUsers;//全局定义全部页面
             (function () {
             $.ajax({
             url: "./getActiveUsersInfoTrend",
             type: "post",
             dataType: "json",
             headers: {
             "Accept": "application/json",
             "Content-Type": "application/json"
             },
             async: false,
             data: JSON.stringify(params),
             success: function (res) {
             activeUsers = res;
             }
             })
             })()
             })()
             ~(function () {
             var addTable=document.getElementsByClassName("addTable")[0];
             addTable.innerHTML=`<table id="addT" class="table cell-border;">
             <thead>
             <tr>
             <th style="border-bottom: 1px solid #ddd;border-right: 1px solid #ddd">时间</th>
             <th style="border-bottom: 1px solid #ddd">活跃用户</th>
             <th style="border-bottom: 1px solid #ddd;border-left: 1px solid #ddd">活跃率</th>
             </tr>
             </thead>`
             var tbody=document.createElement('tbody');
             tbody.style.width="500px";
             tbody.style.border="1px solid #ddd"
             addTable.appendChild(tbody);
             for (var i = 0; i < activeUsers.length; i++) {
             var o = activeUsers[i];
             var tr=document.createElement("tr");
             tr.innerHTML=`<td class="sorting_1" style="width: 294px;height:37px;text-align: center;border: 1px solid #ddd;padding: 8px;font-size: 20px;">`+o['key']+`:00</td><td style="width: 419px;height: 37px;text-align: center;padding: 8px;font-size: 20px;border: 1px solid #ddd">`+o['value']+`</td><td></td>`
             tbody.appendChild(tr)
             }
             })()


             }
             }
             })()*/

            tableShow();

            //---------------------------


            //埋点
            var obj = {
                "eventType": ["dateSelect"]
            }
            getPointParameter(obj);
            rendmapshow();
        }
    });

    startdate_2 = (getCurrentDay(), -7);
    enddate_2 = (getCurrentDay(), -1);
    //表格的年月日范围
    $('.setdate-2').datePicker({
        hasShortcut: true,
        isRange: true,
        show: calendarshow,
        min: "2018-11-01",
        max: dayHandle(getCurrentDay(), -1),
        shortcutOptions: [{
            name: '今天',
            day: '0,0'
        }, {
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
            startdate_2 = this.$input.eq(0).val();
            enddate_2 = this.$input.eq(1).val();
            //埋点
            var obj = {
                "eventType": ["dateSelect"]
            }
            getPointParameter(obj);
            tableShow();
        }
    });
    rendmapshow(1);
    tableShow();
});
//日历显示时
function calendarshow() {
    $('.c-datepicker-picker').css({'left': 'initial', 'right': '25px'});
    $('.c-datepicker-date-range-picker__editor-wrap').eq(1).css('display', 'none');
    $('.c-datepicker-date-range-picker__editor-wrap').eq(3).css('display', 'none');
    $('.c-datepicker-date-range-picker__editor-wrap').eq(5).css('display', 'none');
    $('.c-datepicker-date-range-picker__editor-wrap').eq(7).css('display', 'none');
}

//趋势图展示
function rendmapshow() {
    $('.trandmap').css({
        height: '300px',
        width: '100%'
    })
    myChart = echarts.init(document.getElementsByClassName('trandmap')[0]);
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
        dateType = $("#dateType_qst option:selected").val();
    var params1 = {
        is_current: 1,
        business_type: businessType,
        date_type: 'day',
        source: appType == '' ? 'all' : appType,
        device_status: userType == '' ? '2' : userType,
        start_day: startdate,
        end_day: enddate,
    }
    var params2 = {
        is_current: 1,
        business_type: businessType,
        date_type: 'week',
        source: appType == '' ? 'all' : appType,
        device_status: userType == '' ? '2' : userType,
        start_day: (new Date()).getFullYear() + '-' + '01',
        end_day: (new Date()).getFullYear() + '-' + (moment().week() < 10 ? "0" + moment().week() : moment().week()),
    }
    var params3 = {
        is_current: 1,
        business_type: businessType,
        date_type: 'month',
        source: appType == '' ? 'all' : appType,
        device_status: userType == '' ? '2' : userType,
        start_day: (new Date()).getFullYear() + '-' + '01',
        end_day: (new Date()).getFullYear() + '-' + ((new Date()).getMonth() + 1 < 10 ? ('0' + ((new Date()).getMonth() + 1)) : ((new Date()).getMonth() + 1)),
    }
    var params_2 = {
        businessType: businessType,
        deviceStatus: (userType == '2' ? '' : userType),
        queryDate: enddate,
        source: (appType == 'all' ? '' : appType),
    }
    var urls = '', urls2 = '';
    if (dateType == '' || dateType == 0) {
        if (startdate == enddate && startdate == getCurrentDay()) {
            urls = './getActiveUsersInfoTrend';
        } else {
            urls = getLocation() + proxy("/dataApiQuery/gct_user_active_trend", dateType == 2 ? params3 : (dateType == 1 ? params2 : params1));
        }
    } else {
        urls = getLocation() + proxy("/dataApiQuery/gct_user_active_trend", dateType == 2 ? params3 : (dateType == 1 ? params2 : params1));
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
        data: JSON.stringify((dateType == '' || dateType == 0) ? ((startdate == enddate && startdate == getCurrentDay()) ? params_2 : '') : ''),
        success: function (res) {
            var xdata = [];
            if ((dateType == '' || dateType == 0) && (startdate == enddate && startdate == getCurrentDay())) {
                if (res) {
                    var serises = [];
                    var canzhao = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23];
                    var list = datassort(canzhao, res, 'key');
                    if (list && list.length > 0) {
                        for (var i = 0; i < list.length; i++) {
                            xdata.push(i + "点");
                            serises.push(list[i] ? (list[i].value ? list[i].value : '0') : '')
                        }
                        opt.series[0].data = serises;
                        opt.xAxis.data = xdata;
                        opt.xAxis.axisLabel.formatter = function (params) {
                            return params;
                        };
                        opt.xAxis.axisLabel.interval = 0;
                    } else {
                        opt.series[0].data = [];
                    }
                    opt.tooltip.formatter = '';
                    myChart.hideLoading();
                    myChart.setOption(opt, true);
                } else {
                    myChart.hideLoading();
                    myChart.setOption(opt, true);
                }
            } else {
                if (res && res.data) {
                    var serises = [];
                    var list = res.data;
                    if (list && list.length > 0) {
                        prestartdate = list[0].pre_start_date;
                        preenddate = list[0].pre_end_date;
                        if (dateType == '' || dateType == 0) {
                            for (var i = 0; i < list.length; i++) {
                                xdata.push(list[i] ? (list[i].date ? list[i].date : '') : '');
                                serises.push(list[i] ? (list[i].active_users ? list[i].active_users : '0') : '')
                            }
                            opt.series[0].data = serises;
                            opt.xAxis.data = xdata;
                            if (xdata.length >= 10) {
                                var n = (xdata.length / 10);
                                opt.xAxis.axisLabel.interval = Math.ceil(n);
                            } else {
                                opt.xAxis.axisLabel.interval = 0;
                            }
                        } else {
                            var legs = dateType == 1 ? 53 : 12, judge = 0;
                            for (var j = 0; j < legs; j++) {
                                xdata.push('第' + (j + 1) + (dateType == 1 ? '周' : (dateType == 2 ? '月' : '')));
                                for (var z = 0; z < list.length; z++) {
                                    var datas = list[z].date ? (list[z].date.substr(5, 2)) : '';
                                    if ((j + 1) == datas) {
                                        serises.push(list[z] ? (list[z].active_users ? list[z].active_users : '0') : '');
                                        judge++;
                                    }
                                }
                                if (judge == 0) {
                                    serises.push('');
                                } else {
                                    judge = 0;
                                }
                            }
                            opt.xAxis.axisLabel.formatter = toXdata;
                            opt.series[0].data = serises;
                            opt.xAxis.data = xdata;
                            if (xdata.length >= 10) {
                                var n = (xdata.length / 10);
                                opt.xAxis.axisLabel.interval = Math.ceil(n);
                            } else {
                                opt.xAxis.axisLabel.interval = 0;
                            }
                        }
                        opt.tooltip.formatter = function (params) {
                            var content = '';
                            for (var i = 0; i < params.length; i++) {
                                if (params[i].value && params[i].value != '') {
                                    content += '<p style="margin: 0;float:left;">' + params[i].name + '  <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:' + params[i].color + '"></div><span style="margin-left: 5px">' + params[i].seriesName + '</span> : ' + params[i].value + '</p>';
                                }
                            }
                            return '<div>' + content + '</div>'
                        };
                    } else {
                        opt.series[0].data = [];
                        opt.tooltip.formatter = '';
                    }
                    myChart.hideLoading();
                    myChart.setOption(opt, true);
                } else {
                    myChart.hideLoading();
                    myChart.setOption(opt, true);
                }
            }
            //上期
            if (dateType == '' || dateType == 0) {
                if (startdate == enddate && startdate == getCurrentDay()) {
                    params_2.queryDate = dayHandle(enddate, -1);
                    urls2 = './getActiveUsersInfoTrend';
                } else {
                    params1.is_current = 0;
                    params1.start_day = prestartdate;
                    params1.end_day = preenddate;
                    urls2 = getLocation() + proxy("/dataApiQuery/gct_user_active_trend", params1);
                }
            } else {
                params1.is_current = 0;
                params1.start_day = prestartdate;
                params1.end_day = preenddate;
                params2.is_current = 0;
                params2.start_day = ((new Date()).getFullYear() - 1) + '-' + '01';
                params2.end_day = ((new Date()).getFullYear() - 1) + '-' + '53';
                params3.is_current = 0;
                params3.start_day = ((new Date()).getFullYear() - 1) + '-' + '01';
                params3.end_day = ((new Date()).getFullYear() - 1) + '-' + '12';
                urls2 = getLocation() + proxy("/dataApiQuery/gct_user_active_trend", dateType == 2 ? params3 : (dateType == 1 ? params2 : params1));
            }
            $.ajax({
                url: urls2,
                type: "post",
                dataType: "json",
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                data: JSON.stringify((dateType == '' || dateType == 0) ? ((startdate == enddate && startdate == getCurrentDay()) ? params_2 : '') : ''),
                success: function (res) {
                    if ((dateType == '' || dateType == 0) && (startdate == enddate && startdate == getCurrentDay())) {
                        if (res) {
                            var serises2 = [];
                            var canzhao = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23];
                            var list2 = datassort(canzhao, res, 'key');
                            if (list2 && list2.length > 0) {
                                for (var i = 0; i < list2.length; i++) {
                                    serises2.push(list2[i] ? (list2[i].value ? list2[i].value : '0') : '0')
                                }
                                opt.series[1].data = serises2;
                            } else {
                                opt.series[1].data = [];
                            }
                            opt.tooltip.formatter = '';
                            myChart.hideLoading();
                            myChart.setOption(opt, true);
                        } else {
                            myChart.hideLoading();
                            myChart.setOption(opt, true);
                        }
                    } else {
                        if (res && res.data) {
                            var serises2 = [];
                            var realdt = [];
                            var list = res.data;
                            if (list && list.length > 0) {
                                if (dateType == '' || dateType == 0) {
                                    for (var i = 0; i < list.length; i++) {
                                        serises2.push(list[i] ? (list[i].active_users ? list[i].active_users : '0') : '0');
                                        realdt.push(list[i].real_dt ? list[i].real_dt : "");
                                    }
                                    opt.series[1].data = serises2;
                                } else {
                                    var legs = dateType == 1 ? 53 : 12, judge = 0;
                                    for (var j = 0; j < legs; j++) {
                                        for (var z = 0; z < list.length; z++) {
                                            var datas = list[z].date ? (list[z].date.substr(5, 2)) : '';
                                            if ((j + 1) == datas) {
                                                realdt.push(list[z] ? (list[z].real_dt ? list[z].real_dt : 0) : 0);
                                                serises2.push(list[z] ? (list[z].active_users ? list[z].active_users : '0') : '0');
                                                judge++;
                                            }
                                        }
                                        if (judge == 0) {
                                            serises2.push('');
                                            realdt.push('')
                                        } else {
                                            judge = 0;
                                        }
                                    }
                                    opt.series[1].data = serises2;
                                }

                                opt.tooltip.formatter = function (params) {
                                    var content = '';
                                    for (var i = 0; i < params.length; i++) {
                                        if (params[i].value && params[i].value != '') {
                                            if (i == 0) {
                                                content += '<div>' + params[i].name + '  <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:' + params[i].color + '"></div><span style="margin-left: 5px">' + params[i].seriesName + '</span> : ' + params[i].value + '</div>';
                                            } else {
                                                if (realdt[params[i].dataIndex]) {
                                                    content += '<div>' + realdt[params[i].dataIndex] + ' <div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:' + params[i].color + '"></div><span style="margin-left: 5px;">' + params[i].seriesName + '</span> : ' + params[i].value + '</div>';
                                                } else {
                                                    content += '<div>&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<div style="width: 8px;height: 8px;border-radius: 4px;margin-left: 5px;display: inline-block;background:' + params[i].color + '"></div><span style="margin-left: 5px;">' + params[i].seriesName + '</span> : ' + params[i].value + '</div>';
                                                }
                                            }
                                        }
                                    }
                                    return '<div>' + content + '</div>'
                                };
                            } else {
                                opt.series[1].data = [];
                                opt.tooltip.formatter = '';
                            }
                            myChart.hideLoading();
                            myChart.setOption(opt, true);
                        } else {
                            myChart.hideLoading();
                            myChart.setOption(opt, true);
                        }
                    }
                }
            })
        }
    })
}

//x轴上标签的位置
function toXdata(params) {
    var newParamsName = "";// 最终拼接成的字符串
    var paramsNameNumber = params ? params.length : 0;// 实际标签的个数
    if ((params ? params.length : 5) > 8) {
        var provideNumber = 5;
    } else {
        var provideNumber = 4;
    }
    // 每行能显示的字的个数
    var rowNumber = Math.ceil(paramsNameNumber / provideNumber);// 换行的话，需要显示几行，向上取整
    /**
     * 判断标签的个数是否大于规定的个数， 如果大于，则进行换行处理 如果不大于，即等于或小于，就返回原标签
     */
    // 条件等同于rowNumber>1
    if (paramsNameNumber > provideNumber) {
        /** 循环每一行,p表示行 */
        for (var p = 0; p < rowNumber; p++) {
            var tempStr = "";// 表示每一次截取的字符串
            var start = p * provideNumber;// 开始截取的位置
            var end = start + provideNumber;// 结束截取的位置
            // 此处特殊处理最后一行的索引值
            if (p == rowNumber - 1) {
                // 最后一次不换行
                tempStr = params.substring(start, paramsNameNumber);
            } else {
                // 每一次拼接字符串并换行
                tempStr = params.substring(start, end) + "\n";
            }
            newParamsName += tempStr;// 最终拼成的字符串
        }

    } else {
        // 将旧标签的值赋给新标签
        newParamsName = params;
    }
    //将最终的字符串返回
    return newParamsName
}
function getData() {
    var table = $("#example").DataTable();
    table.ajax.reload();
    table.columns.adjust();
}

//表格列表
function tableShow() {
    $("#detailInfo").html("");
    var appType = $("#appType_tab option:selected").val(),
        userType = $("#userType_tab option:selected").val(),
        dateType = $("#dateType_tab option:selected").val();
    var params1 = {
        is_current: 1,
        business_type: businessType,
        date_type: 'day',
        source: appType == '' ? 'all' : appType,
        device_status: userType == '' ? '2' : userType,
        start_day: startdate_2,
        end_day: enddate_2,
        limit: 100
    }
    var params2 = {
        is_current: 1,
        business_type: businessType,
        date_type: 'week',
        source: appType == '' ? 'all' : appType,
        device_status: userType == '' ? '2' : userType,
        start_day: (new Date()).getFullYear() + '-' + '01',
        end_day: (new Date()).getFullYear() + '-' + (moment().week() < 10 ? "0" + moment().week() : moment().week()),
        limit: 100
    }
    var params3 = {
        is_current: 1,
        business_type: businessType,
        date_type: 'month',
        source: appType == '' ? 'all' : appType,
        device_status: userType == '' ? '2' : userType,
        start_day: (new Date()).getFullYear() + '-' + '01',
        end_day: (new Date()).getFullYear() + '-' + ((new Date()).getMonth() + 1 < 10 ? ('0' + ((new Date()).getMonth() + 1)) : ((new Date()).getMonth() + 1)),
        limit: 100
    };
    var urls = getLocation() + proxy("/dataApiQuery/gct_user_active_table", dateType == 2 ? params3 : (dateType == 1 ? params2 : params1));
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
        "lengthMenu": [15, 50, 75, 100],
        "ordering": true,//禁止排序
        "processing": true,//刷新的那个对话框
        "deferRender": false,//延迟渲染
//		     "scrollX": true,//启用水平滚动
//		     "autoWidth": true,//自动列宽
        "destroy": true,
        "ajax": {
            "url": urls,
            "type": "GET",
            "async": true,
            "dataType": "json",
            "dataFilter": function (res) {//res是服务器端返回的数据
                var json = JSON.parse(res);
                var returnData = {};
                if (json.code == '0' && json.data) {
                    returnData.recordsTotal = json.total;//返回数据全部记录
                    returnData.recordsFiltered = json.total;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = json.data;//返回的数据列表
                    return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
                } else {
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
            {data: 'date'},
            {data: 'active_users'},
            {data: 'per_active_users'},
        ],
    });
}

/*********前端交互*********/
/**
 * 切换条件查询统计数据
 * type 1 趋势图，2 表格
 */

function dataStatisticSelect(type) {
    //埋点
    var obj = {
        "eventType": ["appType", "userType", "dateType"]
    }
    getPointParameter(obj);

    var dateType = $("#dateType_qst option:selected").val();
    var dateType_2 = $("#dateType_tab option:selected").val();

    if (type == 1) {
        if (dateType == 1) {
            $('.setdate-1,.setdate-1 input').css({
                'background': '#ccc',
                'cursor': 'not-allowed'
            });
            $("#example thead th:nth-child(2)").html("周活跃用户");
            $('.setdate-1 input').attr('disabled', 'disabled');
        } else if (dateType == 2) {
            $('.setdate-1,.setdate-1 input').css({
                'background': '#ccc',
                'cursor': 'not-allowed'
            });
            $("#example thead th:nth-child(2)").html("月活跃用户");
            $('.setdate-1 input').attr('disabled', 'disabled');
        } else {
            $('.setdate-1').css({
                'background': '#fff',
                'cursor': 'default'
            });
            $('.setdate-1 input').css({
                'background': '#fff',
                'cursor': 'text'
            });
            $('.setdate-1 input').removeAttr('disabled');
        }
        rendmapshow();
    } else {
        if (dateType_2 == 1) {
            $('.setdate-2,.setdate-2 input').css({
                'background': '#ccc',
                'cursor': 'not-allowed'
            });
            $("#example thead th:nth-child(2)").html("周活跃用户");
            $('.setdate-2 input').attr('disabled', 'disabled');
        } else if (dateType_2 == 2) {
            $('.setdate-2,.setdate-2 input').css({
                'background': '#ccc',
                'cursor': 'not-allowed'
            });
            $("#example thead th:nth-child(2)").html("月活跃用户");
            $('.setdate-2 input').attr('disabled', 'disabled');
        } else {
            $('.setdate-2').css({
                'background': '#fff',
                'cursor': 'default'
            });
            $('.setdate-2 input').css({
                'background': '#fff',
                'cursor': 'text'
            });
            $('.setdate-2 input').removeAttr('disabled');
        }
        tableShow();
    }
}

//下载
function getExportDate() {
    //埋点
    var obj = {
        "eventType": ["tableDownload"],
    }
    getPointParameter(obj);

    var userType = $("#userType_tab option:selected").val();
    var appType = $("#appType_tab option:selected").val();
    var dateValue = $("#dateType_tab option:selected").val();
    var dateType = "", startDate = "", endDate = "";
    if (dateValue == 0) {
        dateType = "day";
        startDate = $('.c-datepicker-data-input').eq(2).val();
        endDate = $('.c-datepicker-data-input').eq(3).val();
    } else if (dateValue == 1) {
        dateType = "week";
        startDate = (new Date()).getFullYear() + "-01";
        var w = (moment().week() < 10 ? "0" + moment().week() : moment().week());
        endDate = (new Date()).getFullYear() + "-" + w;
    } else if (dateValue == 2) {
        dateType = "month";
        var eday = $('.c-datepicker-data-input').eq(3).val();
        startDate = (new Date()).getFullYear() + '-01';
        endDate = (new Date()).getFullYear() + '-' + eday.substr(5, 2);
    }
    var mapper = {
        "date": ' 日期',
        "active_users": '日活跃用户',
        "per_active_users": '活跃率'
    };
    var param = {
        "business_type": businessType,
        "date_type": dateType,
        "source": appType == '' ? 'all' : appType,
        "device_status": userType == '' ? 'all' : userType,
        "is_current": 1,
        "start_day": startDate,
        "end_day": endDate,
    };

    postDownLoadFile({
        url: './dataApiExport/gct_user_active_table',
        data: {
            "params": param,
            "titles": mapper
        },
        method: 'post'
    });
}


//---------------------
// <%---------------- 特么的直接强制value赋值，然后再执行就行le------------%>
function dataStatisticSelect1() {
    appType_tab.value = appType_qst.value;
    dataStatisticSelect(2)
}
function dataStatisticSelect2() {
    userType_tab.value = userType_qst.value;
    dataStatisticSelect(2)
}
function dataStatisticSelect3() {
    dateType_tab.value = dateType_qst.value;
    dataStatisticSelect(2)
}
//----------------------------------------------------
//    不需要的给隐藏掉
(function () {
    var title = document.getElementById('title');
    var l = title.getElementsByTagName('label');
    var s = title.getElementsByTagName("select");
    for (var i = 0; i < l.length; i++) {
        l[i].style.display = "none"
    }
    for (var i = 0; i < s.length; i++) {
        s[i].style.display = "none"
    }
})()


//    --------------------物业搜索---------------
//物业下拉列表
getProperty();
function getProperty() {
    $("#property").html("");
    $.ajax({
        url: getLocation() + proxy("/dataApiQuery/gct_dim_lessee_name"),
        type: "get",
        dataType: "json",
        success: function (res) {
            if (res && res.data && res.data.length > 0) {
                var list = res.data;
                var str = '';
                for (var i = 0; i < list.length; i++) {
                    str += '<option value="' + list[i].lessee_id + '">' + list[i].lessee_name + '</option>';
                }
                $('#property').append(str);
                $("#property").select2({
                    placeholder: '全部',
                    allowClear: true,
                    multiple: false
                });
            }
        }
    });
}

var property = document.getElementById('property');
function test() {
    flagProperTyId = property[property.selectedIndex].value || "all";

}
