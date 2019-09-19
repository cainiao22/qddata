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
    if(dataAPIDomain != ''){
        return encodeURIComponent(dataAPIDomain + str);
    }
    if(location.origin === 'http://qddata.qdingnet.com'){
        dataAPIDomain = "http://yushanfang.bigdata.qdingnet.com/";

    }else if(location.origin === 'http://qa-qddata.bigdata.qdingnet.com'){
        dataAPIDomain = "http://qa-yushanfang.bigdata.qdingnet.com/";
    }else{
        dataAPIDomain = "http://dev-yushanfang.bigdata.qdingnet.com/";
    }

    return encodeURIComponent(dataAPIDomain + str);
}


getlocation();




! function(t) {
    "use strict";
    var a = function() {
        this.$dataTableButtons = t("#datatable-buttons")
    };
    var i18nZH = {
        "sProcessing":   "处理中...",
        "sLengthMenu":   "显示  _MENU_  项结果",
        "sZeroRecords":  "没有匹配结果",
        "sInfo":         "共 _TOTAL_ 条记录",
        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 条记录",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix":  "",
        "sSearch":       "搜索:",
        "sUrl":          "",
        "sEmptyTable":     "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "首页",
            "sPrevious": "上页",
            "sNext":     "下页",
            "sLast":     "末页"
        },
        "oAria": {
            "sSortAscending":  ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    };
    a.prototype.createDataTableButtons = function() {
        /*      var param = {"start_date":$('#dpd1').val(),"end_date":$('#dpd2').val()};*/
        0 !== this.$dataTableButtons.length && this.$dataTableButtons.
        on('preXhr.dt', function ( e, settings, data ) {
            data['params[calTotal]'] = true;
            data['params[pageSize]'] = data.length;
            data['params[currentPage]'] = parseInt(data.start/data.length)+parseInt(1);
            data['params[region]'] = $('#city').val();
            data['params[project]'] = $('#community').val();
            data['params[merge]'] = $('#merge').val();
            data['params[start_date]'] = $('#dpd1').val();
            data['params[end_date]'] = $('#dpd2').val();
        } ).
        on('xhr.dt', function ( e, settings, json, xhr ) {
            json['recordsTotal'] = json.total;
            json['recordsFiltered'] = json.total;
        } ).
        DataTable({
            dom: "lrtip",
            language: i18nZH,
            responsive: !0,
            "lengthChange": true,//开启显示条数
            "lengthMenu": [ 15, 50, 75, 100 ],
            "ordering": false,//禁止排序
            "deferRender": true,
            "processing": true,
            "scrollX": true,
            "serverSide": true,
            "autoWidth": false,
            "ajax": {
                "url" : dataAPIUrl + proxy("dataApiQuery/daily_project_report_merge"),
                "type": "POST",
            },
            "columns": [
                { "data": "region_id" },
                { "data": "region_name" },
                { "data": "project_id" },
                { "data": "project_name" },
                { "data": "dt" },
                { "data": "person_number" },
                { "data": "live_number" },
                { "data": "gate_type" },
                { "data": "is_open_fee" },
                { "data": "is_fix" },
                { "data": "total_inc" },
                { "data": "total_bind_room" },
                { "data": "total_bind_member" },
                { "data": "current_inc" },
                { "data": "current_bind_room" },
                { "data": "current_bind_member" },
                { "data": "bind_rate" },
                { "data": "permeate_rate" },
                { "data": "current_act_rate" },
                { "data": "current_act_mem" },
                { "data": "current_act_num" },
                { "data": "one_stage_num" },
                { "data": "second_stage_num" },
                { "data": "current_gmv_num" },
                { "data": "current_gmv_price" },
                { "data": "current_wf_price" },
                { "data": "current_wf_pay_mem" },
                { "data": "current_pass_hk_mem" },
                { "data": "current_pass_hk_num" },
                { "data": "current_app_pass_hk_mem" },
                { "data": "current_app_pass_hk_num" },
                { "data": "current_getnotice_mem" },
                { "data": "current_apply_mem" },
                { "data": "current_apply_num" },
                { "data": "propertyinfo_name" },
                { "data": "developer_name" },
                { "data": "app_update" },
                { "data": "gate_update" },
                { "data": "is_wx" },
                { "data": "is_app" }

            ]
        })
    }, a.prototype.init = function() {
        this.createDataTableButtons()
    }, t.DataTable = new a, t.DataTable.Constructor = a
}(window.jQuery),
    function(t) {
        "use strict";
        moment.locale('zh-cn');
        $('#dpd1').val(moment().subtract(7,'days').format('YYYY-MM-DD'));
        $('#dpd2').val(moment().subtract(1,'days').format('YYYY-MM-DD'));

        //开始时间
        $('#dpd1').datepicker({
            language: "zh-CN",
            autoclose: true,
            format : 'yyyy-mm-dd',
            maxDate: "+1D",
        }).on('changeDate',function(e){

            var date1 = new Date(Date.parse($("#dpd1").val()));
            var date2 = new Date(Date.parse($("#dpd2").val()));

            if(date1 > date2){
                $("#dpd2").val($("#dpd1").val());
                var startTime = e.date;
                $('#dpd2').datepicker('setStartDate',startTime);
            }else{
                var startTime1 = e.date;
                $('#dpd2').datepicker('setStartDate',startTime1);
            }
        });
        //结束时间
        $('#dpd2').datepicker({
            language: "zh-CN",
            autoclose: true,
            format : 'yyyy-mm-dd',
            maxDate: "+1D",
        }).on('changeDate',function(e){

            var date1 = new Date(Date.parse($("#dpd1").val()));
            var date2 = new Date(Date.parse($("#dpd2").val()));

            if(date2 < date1){
                $("#dpd1").val($("#dpd2").val());
                var endTime = e.date;
                $('#dpd1').datepicker('setEndDate',endTime);
            }else{
                var endTime1 = e.date;
                $('#dpd1').datepicker('setEndDate',endTime1);
            }
        });
        /* $('.date').val(moment().subtract(1,'days').format('YYYY-MM-DD'));
         $('.input-append.date').datepicker({
         language: "zh-CN",
         autoclose: true,
         format : 'yyyy-mm-dd',
         maxDate: "+1D",

         })*/
        t.DataTable.init();
        //二级联动
        var title1	= ['<option value="">地域</option>' , '<option value="">社区</option>' ];
        $.getJSON(dataAPIUrl + proxy("dataApiQuery/shequribao_shaixuanweidu?dimension=city"),{},function (data) {
            if(data.code!=0){
//                swal("提示",data.errorMsg, "success");
            }else{
                var city = data.data,HTML=title1[0];
                city.forEach(function (c,index) {
                    //其他选项不显示
                    if(c.key && c.key != '9999'){
                        HTML += "<option value='"+c.key+"'>"+c.value+"</option>";
                    }
                })
                $('#city').empty().append(HTML);
            }
        })
        $('#community').select2({
            placeholder: "全部社区",
            allowClear:true,
            language: "zh-CN"

        });
        $('#city').select2({
            placeholder: "全部",
            allowClear:true,
            language: "zh-CN"
        });

        $('#city').change(function(e) {
            $('#community').empty().append(title1[1]);
            //重新填充社区option
            var cV = $(this).val();
            $.getJSON(dataAPIUrl + proxy("dataApiQuery/jiashicang_shaixuanweidu?dimension=project&condition="+cV),{},function (data) {
                if(data.code!=0){
//                    swal("提示",data.errorMsg, "success");
                }else{
                    var community = data.data,HTML=title1[1];
                    community.forEach(function (c,index) {
                        if(c.project_id){
                            HTML += "<option value='"+c.project_id+"'>"+c.project_name+"</option>";
                        }
                    })
                    $('#community').empty().append(HTML);
                }
            })
        })
        // getData();
    }(window.jQuery);

function getData() {
    var table = $("#datatable-buttons").DataTable();
    // table.destroy();
    /*  var merge=$('#merge').val();*/
    table.ajax.reload();
    table.columns.adjust();
}
var postDownLoadFile = function (options) {
    var config = $.extend(true, { method: 'post' }, options);
    var $iframe = $('<iframe id="down-file-iframe" />');
    var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
    $form.attr('action', config.url);
    //?params[date]=2018-04-17&params[region]=1&titles[region_name]=城市&titles[project_id]=社区ID&titles[project_name]=社区名称
    var data = config['data'];
    for (var key in data['params']) {
        $form.append('<input type="hidden" name="params[' + key + ']" value="' + data['params'][key] + '" />');
    }
    var order = 0;
    for (var key in data['titles']) {
        $form.append('<input type="hidden" name="titles[' + key + '].name" value="' + data['titles'][key] + '" />');
        $form.append('<input type="hidden" name="titles[' + key + '].order" value="' + order + '" />');
        order += 1;
    }
    $iframe.append($form);
    $(document.body).append($iframe);
    $form[0].submit();
    $iframe.remove();
}

//导出
function exportExcel() {
    /*var param = {"start_date":$('#dpd1').val(),"end_date":$('#dpd2').val()};*/
    var param = {"start_date":$('#dpd1').val(),"end_date":$('#dpd2').val(),"merge":$('#merge').val(),
        "region":$('#city').val(),"project":$('#community').val()};
    var mapper = {
            "region_id":'城市ID',
            "region_name":'城市',
            "project_id":'社区ID',
            "project_name":'社区',
            "dt":'日期',
            "person_number":'实际上线户数',
            "live_number":'入住户数',
            "gate_type":'是否有门禁',
            "is_open_fee":'是否开通物业缴费',
            "is_fix":'是否开通报事功能',
            "total_inc":'累计注册人数',
            "total_bind_room":'累计绑定房屋数',
            "total_bind_member":'累计绑定人数',
            "current_inc":'新增注册人',
            "current_bind_room":'新增绑定房屋',
            "current_bind_member":'新增绑定人',
            "bind_rate":'绑定率',
            "permeate_rate":'渗透率',
            "current_act_rate":'活跃率',
            "current_act_mem":'活跃人数',
            "current_act_num":'活跃次数',
            "one_stage_num" : '一阶段达标户数',
            "second_stage_num":'二阶段达标户数',
            "current_gmv_num":'GMV单量',
            "current_gmv_price":'GMV金额',
            "current_wf_price":'物业缴费额',
            "current_wf_pay_mem":'物业缴费人数',
            "current_pass_hk_mem":'通行人数',
            "current_pass_hk_num":'通行次数',
            "current_app_pass_hk_mem":'app通行人数',
            "current_app_pass_hk_num":'app通行次数',
            "current_getnotice_mem":'公告人数',
            "current_apply_mem":'报事人数',
            "current_apply_num":'报事次数',
            "propertyinfo_name":'物业公司',
            "developer_name":'开发商',
            "app_update":'APP上线时间',
            "gate_update":'门禁上线时间',
            "is_wx":'是否开通wx',
            "is_app" :'是否开通app'

        },
        param = {params:param, titles:mapper};
    postDownLoadFile({
        url: '/dataApiExport/daily_project_report_merge',
        data:param,
        method:'post'
    });
    console.log("../dataApiExport/daily_project_report_merge?"+$.param(param));
}
