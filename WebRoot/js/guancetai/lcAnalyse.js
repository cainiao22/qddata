/**
 * 观测台--留存分析
 */

var businessType = $(".tabControl > a > .active").attr("data-type");//千丁 or 丁管家
var startdate = '', enddate = '';

$(document).ready(function () {
    //埋点
    var obj = {
        "eventType": ["lcAnalyse"],
    }
    if(businessType == 0){
        obj.eventType.push('qding');
    }else{
        obj.eventType.push('dgj');
    }
    getPointParameter(obj);

    $('.c-datepicker-data-input').eq(0).val(dayHandle(getCurrentDay(), -7));
    $('.c-datepicker-data-input').eq(1).val(dayHandle(getCurrentDay(), -1));
    //趋势图年月日范围
    $('.setdate-1').datePicker({
        hasShortcut: true,
        isRange: true,
        show: calendarshow,
        min:"2018-11-01",
        max: dayHandle(getCurrentDay(), -1),
        shortcutOptions: [{
            name: '过去7天',
            day: '-7,-1'
        }],
        hide: function (type) {
            this.$input.eq(0).val(this.$input.eq(0).val().substr(0, 10));
            this.$input.eq(1).val(this.$input.eq(1).val().substr(0, 10));
            startdate = this.$input.eq(0).val();
            enddate = this.$input.eq(1).val();
            //埋点
            var obj = {
                "eventType": ["dateSelect"],
            }
            getPointParameter(obj);

            tableShow();
        }
    });
    startdate = dayHandle(getCurrentDay(), -7);
    enddate = dayHandle(getCurrentDay(), -1);
    tableShow(businessType);
});
//日历显示时
function calendarshow(){
    $('.c-datepicker-picker').css({'left':'initial','right':'25px'});
    $('.c-datepicker-date-range-picker__editor-wrap').eq(1).css('display', 'none');
    $('.c-datepicker-date-range-picker__editor-wrap').eq(3).css('display', 'none');
}

//表格列表
function tableShow(type) {
    $("#detailInfo").html("");
    var dateType = $("#appType option:selected").val();
    var param = {
        "business_type": type,
        "source": dateType,
        "start_day": startdate,
        "end_day": enddate,
        "limit":100
    }
    var urls = getLocation() + proxy("/dataApiQuery/gct_user_retained_table", param);

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
//			 "scrollX": true,//启用水平滚动
//			 "autoWidth": true,//自动列宽
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
            {data: 'date'},
            {data: 'registed_user_num'},
            {data: 'day_2'},
            {data: 'day_3'},
            {data: 'day_4'},
            {data: 'day_5'},
            {data: 'day_6'},
            {data: 'day_7'},
            {data: 'day_30'}
        ],
    });
}

//下载
function getExportDate() {
    //埋点
    var obj = {
        "eventType": ["tableDownload"],
    }
    getPointParameter(obj);

    var appType = $("#appType option:selected").val();
    var startDate = $('.c-datepicker-data-input').eq(0).val();
    var endDate = $('.c-datepicker-data-input').eq(1).val();

    console.log(startDate,endDate);
    var mapper = {
        "date":' 日期',
        "registed_user_num": '新注册用户数',
        "day_2":"第二日留存",
        "day_3"	:"第三日留存",
        "day_4"	:"第四日留存",
        "day_5"	:"第五日留存",
        "day_6"	:"第六日留存",
        "day_7"	:"第七日留存",
        "day_30":"第三十日留存"
    };
    var param = {
        "business_type": businessType,
        "source": appType,
        "start_day": startDate,
        "end_day": endDate
    };

    postDownLoadFile({
        url: './dataApiExport/gct_user_retained_table',
        data: {
            "params": param,
            "titles": mapper
        },
        method: 'post'
    });
}

/*********前端交互*********/
//切换条件查询统计数据
function dataStatisticSelect() {
    //埋点
    var obj = {
        "appType": "appType",
    }
    getPointParameter(obj);
    var bType = $(".tabControl > .active").attr("data-type");
    tableShow(bType);
}

