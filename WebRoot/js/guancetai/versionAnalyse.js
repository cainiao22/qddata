/**
 * 版本分析
 */
var startdate = '',enddate = '';
var businessType = $(".tabControl > a > .active").attr("data-type");//千丁 or 丁管家

$(document).ready(function () {

    if(businessType=='0'){
        $('.c-datepicker-data-input').eq(0).val(dayHandle(getCurrentDay(), -7));
        $('.c-datepicker-data-input').eq(1).val(dayHandle(getCurrentDay(), -1));
        //年月日范围
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

                tableShow(businessType);
            }
        });
        startdate = dayHandle(getCurrentDay(), -7);
        enddate = dayHandle(getCurrentDay(), -1);
    }else if(businessType=='1') {
        getProperty();
    }
    tableShow(businessType, 1);
});
//日历显示时
function calendarshow(){
    $('.c-datepicker-picker').css({'left':'initial','right':'25px'});
    $('.c-datepicker-date-range-picker__editor-wrap').eq(1).css('display', 'none');
    $('.c-datepicker-date-range-picker__editor-wrap').eq(3).css('display', 'none');
}

//物业公司列表
function getProperty() {
    $("#property").html("");
    $.ajax({
        url: getLocation() + proxy("/dataApiQuery/gct_dim_lessee_name"),
        type: "get",
        dataType: "json",
        success: function (res) {
            if (res && res.data && res.data.length>0) {
                var list = res.data;
                var str ='';
                for(var i=0; i<list.length; i++){
                    str += '<option value="'+list[i].lessee_id+'">'+list[i].lessee_name+'</option>';
                }
                $('#property').append(str);
                $("#property").select2({
                    placeholder: '全部',
                    allowClear: true,
                    multiple:false
                });
            }
        }
    });
}
//表格列表
function tableShow(type, mai) {
    var obj = {
        "eventType": [],
    }
    if(mai == 1){
        if(businessType == 0){
            obj.eventType.push("versionAnalyse");
        }else{
            obj.eventType.push("versionAnalyse2");
        }
    }else if(mai == 2){
        obj.eventType.push("appType");
    }else{
        if(type=="0"){
            obj.eventType.push("userType");
        }else{
            obj.eventType.push("propertySelect");
        }
    }

    getPointParameter(obj);
    if(type=="0"){
        $("#addUser>tbody,#appStartup>tbody").html("");
        var userType = $("#userType option:selected").val();
        var appType = $("#appType option:selected").val();
        var serviceType ='',str='';
        var t = $(".tab_2>.bg_active").text();
        if(t=="新增用户"){
            serviceType = 'version_user_add';
            str='#addUser';
        }else{
            serviceType = 'version_start_add';
            str='#appStartup';
        }
        var param={
            "source":appType,
            "service_type":serviceType,
            "device_status":userType,
            "start_day":startdate,
            "end_day":enddate,
            "limit":100
         }
       var urls = getLocation() + proxy("/dataApiQuery/gct_app_version_device",param);

        $(str).DataTable({
            dom: "lrtip",
            language: i18nZH,
            responsive: !0,
            "lengthChange": true,//开启显示条数
            // "lengthMenu": [ 15, 50, 75, 100 ],
            "ordering": true,//禁止排序
            "processing": true,//刷新的那个对话框
            "deferRender": false,//延迟渲染
//		     "scrollX": true,//启用水平滚动
//		     "autoWidth": true,//自动列宽
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
            columns: [{data: 'dt'},{data: 'app_version_device'},{data: 'count_nums'}]
        });
    }else{
        $("#dgj_fenxi>tbody").html("");
        var property = $("#property option:selected").val();
        var appType = $("#appType option:selected").val();
        var param={
            "source":appType,
            "lessee_id":property?property:'-1'
        }
        var urls = getLocation() + proxy("/dataApiQuery/gct_table_device_app_version_1",param);

        $('#dgj_fenxi').DataTable({
            dom: "lrtip",
            language: i18nZH,
            responsive: !0,
            "lengthChange": true,//开启显示条数
            // "lengthMenu": [ 15, 50, 75, 100 ],
            "ordering": true,//禁止排序
            "processing": true,//刷新的那个对话框
            "deferRender": false,//延迟渲染
//		     "scrollX": true,//启用水平滚动
//		     "autoWidth": true,//自动列宽
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
                {data: 'application_version'},
                {data: 'use_user_count'},
                {data: 'per_use_count'},
            ],
            "aaSorting": [
                [ 2, "desc"]
            ],//第二列倒序
        });
    }
}

//下载
function getExportData() {
    //埋点
    var obj = {
        "eventType": ["tableDownload"],
    }
    getPointParameter(obj);

    if(businessType=="0") {
        var userType = $("#userType option:selected").val();
        var appType = $("#appType option:selected").val();
        var serviceType = '',title ={};
        var t = $(".tab_2>.bg_active").text();
        if (t == "新增用户") {
            serviceType = 'version_user_add';
            title = {
                "dt":'日期',
                "app_version_device": '版本号',
                "count_nums": '新增用户'
            };
        } else {
            serviceType = 'version_start_add';
            title = {
                "dt":'日期',
                "app_version_device": '版本号',
                "count_nums": '启动次数'
            };
        }

        var param = {
            "source": appType,
            "service_type": serviceType,
            "device_status": userType,
            "start_day": startdate,
            "end_day": enddate,
            "limit":999999999
        }
        postDownLoadFile({
            url: './dataApiExport/gct_app_version_device',
            data: {
                "params":param,
                "titles":title
            },
            method: 'post'
        });
    }else{
        var property = $("#property option:selected").val();
        var appType = $("#appType option:selected").val();
        var param={
            "source":appType,
            "lessee_name":property?property:'-1'
        }
        title = {
            "application_version": '软件版本',
            "use_user_count": '使用人数',
            "per_use_count":'占比',
        };
        postDownLoadFile({
            url:"./dataApiExport/gct_table_device_app_version_1",
            data: {
                "params":param,
                "titles":title
            },
            method: 'post'
        });
    }
}

/*********前端交互*********/
//表格切换
$(".tab_2>div").click(function () {
    //埋点
    var obj = {
        "eventType": ["tableSwitch"],
    }
    getPointParameter(obj);

    $(this).addClass("bg_active").siblings("div").removeClass("bg_active");
    var i = $(this).index();
    if(i==0){
        $("#addUser,#addUser_info,#addUser_paginate").show();
        $("#appStartup,#appStartup_info,#appStartup_paginate").hide();
    }else if(i==1){
        $("#addUser,#addUser_info,#addUser_paginate").hide();
        $("#appStartup,#appStartup_info,#appStartup_paginate").show();
    }
    tableShow('0');
});
