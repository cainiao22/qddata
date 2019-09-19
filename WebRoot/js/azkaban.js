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
            data['params[owner]'] = $('#owner').val();
            data['params[jobId]'] = $('#jobId').val();
            data['params[status]'] = $("input[name='status']:checked").val();
            data['params[startTime]'] = $('#dpd1').val();
            data['params[endTime]'] = $('#dpd2').val();
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
                "url" : dataAPIUrl + proxy("dataApiQuery/azkaban_logs"),
                "type": "POST",
            },
            "columns": [
                { "data": "execId" },
                { "data": "jobId" },
                { "data": "flowId" },
                { "data": "project" },
                { "data": "startTime" },
                { "data": function(obj){
                        if(obj.status == 50){
                            return '<i style="width:16px;height:16px;border-radius:50%;background-color:green;display: inline-block"></i>';
                        }else{
                            return '<i style="width:16px;height:16px;border-radius:50%;background-color:red;display: inline-block"></i>';
                        }
                } },
                { "data": "owner" }
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
        var title1	= ['<option value="">全部</option>'];
        $.getJSON(dataAPIUrl + proxy("dataApiQuery/azkaban_logs_owner"),{},function (data) {
            if(data.code!=0){
//                swal("提示",data.errorMsg, "success");
            }else{
                var owners = data.data,HTML=title1[0];
                owners.forEach(function (c,index) {
                    HTML += "<option value='"+c.name+"'>"+c.name+"</option>";
                })
                $('#owner').empty().append(HTML);
            }
        })
        $('#owner').select2({
            placeholder: "全部",
            allowClear:true,
            language: "zh-CN"
        });

    }(window.jQuery);

function getData() {
    var table = $("#datatable-buttons").DataTable();
    table.ajax.reload();
    table.columns.adjust();
}
