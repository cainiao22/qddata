/**
 * 观测台公共js
 */
function getLocation(){
	if(window.location.host == 'qddata.qdingnet.com'){
		return  window.location.origin+"/getHttpData?url=";
	}else if(window.location.host == 'qabigdata.qdingnet.com'){
        return  window.location.origin + '/getHttpData?url=';
	}else if(window.location.host == 'devbigdata.qdingnet.com'){
        return  window.location.origin + '/getHttpData?url=';
	}else{
        return  window.location.origin + '/getHttpData?url=';
	}
}

//语言样式设置
var i18nZH = {
	"sProcessing":   "",
	"sLengthMenu":   "显示 _MENU_ 项结果",
	"sZeroRecords":  "没有匹配结果",
	"sInfo":         "共 _TOTAL_ 条",
	"sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
	"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	"sInfoPostFix":  "",
	"sSearch":       "搜索:",
	"sUrl":          "",
	"sEmptyTable":     "表中数据为空",
	"sLoadingRecords": "载入中...",
	"sInfoThousands":  ",",
	"oPaginate": {
		"sFirst":    "首页",
		"sPrevious": "<",
		"sNext":     ">",
		"sLast":     "尾页"
	},
	"oAria": {
		"sSortAscending":  ": 以升序排列此列",
		"sSortDescending": ": 以降序排列此列"
	}
};

//代理
function proxy(str, param){
    var paramStr ='';
    if(param){
        paramStr = '?';
        for (var i in param) {
            paramStr += (i + '=' + param[i] + '&')
        }
    }

	if(location.host ==="qddata.qdingnet.com"){
		return encodeURIComponent("http://yushanfang.bigdata.qdingnet.com"+str+paramStr);
	}else if(location.host ==="qabigdata.qdingnet.com"){
		return encodeURIComponent("http://qa-yushanfang.bigdata.qdingnet.com"+str+paramStr);
	}else{
		return encodeURIComponent("http://dev-yushanfang.bigdata.qdingnet.com"+str+paramStr);
	}
}
function proxy_2(str){
	if(location.host ==="qddata.qdingnet.com"){
		return encodeURIComponent("http://yushanfang.bigdata.qdingnet.com"+str);
	}else if(location.host ==="qabigdata.qdingnet.com"){
		return encodeURIComponent("http://qa-yushanfang.bigdata.qdingnet.com"+str);
	}else{
		return encodeURIComponent("http://dev-yushanfang.bigdata.qdingnet.com"+str);
	}
}

//得到当天日期
function getCurrentDay() {
	var date = new Date();
	var curYear = date.getFullYear();
	var curMonth = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
	var curDay = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();

	var currentDay = curYear + "-" + curMonth + "-" + curDay;
	return currentDay;
}
/*
 * 日期加减
 * @param date 传入的日期，"2019-01-14"
 * @param number 加减数量，前一天 -1，后一天 +1
 */
function dayHandle(date,num){
	var date=new Date(date);
	date.setDate(date.getDate()+num);
	var m=(date.getMonth()+1) < 10 ? '0'+(date.getMonth()+1) : (date.getMonth()+1);
	var d=date.getDate() < 10 ? '0'+date.getDate() : date.getDate();
	return date.getFullYear()+'-'+m+'-'+d;
}
//计算两日期相隔多少天
function countDays(d1,d2) {
	var format = 0;
	if(d1!=d2){
		var customerMill =new Date(d1).getTime();
		var currentDate = new Date(dayHandle(d2,1)).getTime();
		var diff = currentDate - customerMill;
		//计算天数
		var format =  Math.floor(diff/(24*3600*1000));
	}
	return format;
}
//数组的最大值
function getMaxValue(arr){
	var max = Math.max.apply(null,arr);
	return max;
}
//数据排序
function datassort(canzhao, res, ming2){
	var arr = [], judge = 0;
	for(var i=0; i<canzhao.length; i++){
		for(var j=0; j<res.length; j++){
			if(canzhao[i] == res[j][ming2]){
				arr.push(res[j]);
				judge++;
			}
		}
		if(judge == 0){
            arr.push('');
		}else{
            judge = 0;
		}
	}
	return arr;
}

//数据展示
function pageShow(res,id,str) {
	if(res.current){
		if(str=="two"){//保留两位小数
			$(id+" > .left > p:nth-child(1)").html("").html("本期:"+Number(res.current).toFixed(2));
		}else if(str=="three"){//保留三位小数
			$(id+" > .left > p:nth-child(1)").html("").html("本期:"+Number(res.current).toFixed(3));
		}else{
			$(id+" > .left > p:nth-child(1)").html("").html("本期:"+res.current.toLocaleString());
		}
	}
	if(res.last){
		if(str=="two"){//保留两位小数
			$(id+" > .left > p:nth-child(2)").html("").html("上期:"+Number(res.last).toFixed(2));
		}else if(str=="three"){//保留三位小数
			$(id+" > .left > p:nth-child(2)").html("").html("上期:"+Number(res.last).toFixed(3));
		}else{
			$(id+" > .left > p:nth-child(2)").html("").html("上期:"+res.last.toLocaleString());
		}
	}
	if(res.increasement){
		if(res.increasement > 0){
			$(id+" > .right > p:nth-child(1)").removeClass().addClass("rise").html("").html(toDecimal(res.increasement)+"%<i class='riseIcon'></i>");
		}else{
			$(id+" > .right > p:nth-child(1)").removeClass().addClass("down").html("").html(toDecimal(res.increasement)+"%<i class='downIcon'></i>");
		}
	}else{
		$(id+" > .right > p:nth-child(1)").removeClass();
	}
}

/*******展开/关闭菜单******/
$("#sidebar > li").click(function () {
    $(this).find("a").toggle();
    var dis = $(this).find("a").css("display");
    if(dis=="none"){
        $(this).find("em").removeClass().addClass("arrowup_n");
    }else{
        $(this).find("em").removeClass().addClass("arrowdown_n");
    }
});

//鼠标移入菜单事件
$("#tjIcon").mouseover(function () {
    $("#tjIcon > i").removeClass().addClass("tjIcon");
    var cname = $("#tjIcon > em").attr("class");
    if(cname=="arrowup_n"){
        $("#tjIcon > em").removeClass().addClass("arrowup");
    }else if(cname=="arrowdown_n"){
        $("#tjIcon > em").removeClass().addClass("arrowdown");
    }
    if($("#closeIcon").attr("class")=="open" || $("#closeIcon").attr("class")=="open_n"){
        $(".mouseShow1").show();
        $(".mouseShow2,.mouseShow3,.mouseShow4,.mouseShow5").hide();
    }
});
$("#fxIcon").mouseover(function () {
    $("#fxIcon > i").removeClass().addClass("fxIcon");
    var cname = $("#fxIcon > em").attr("class");
    if(cname=="arrowup_n"){
        $("#fxIcon > em").removeClass().addClass("arrowup");
    }else if(cname=="arrowdown_n"){
        $("#fxIcon > em").removeClass().addClass("arrowdown");
    }
    if($("#closeIcon").attr("class")=="open" || $("#closeIcon").attr("class")=="open_n"){
        $(".mouseShow1,.mouseShow3,.mouseShow4,.mouseShow5").hide();
        $(".mouseShow2").show();
    }
});
$("#lcIcon").mouseover(function () {
    $("#lcIcon > i").removeClass().addClass("lcIcon");
    var cname = $("#lcIcon > em").attr("class");
    if(cname=="arrowup_n"){
        $("#lcIcon > em").removeClass().addClass("arrowup");
    }else if(cname=="arrowdown_n"){
        $("#lcIcon > em").removeClass().addClass("arrowdown");
    }
    if($("#closeIcon").attr("class")=="open" || $("#closeIcon").attr("class")=="open_n"){
        $(".mouseShow1,.mouseShow2,.mouseShow4,.mouseShow5").hide();
        $(".mouseShow3").show();
    }
});
$("#bbIcon").mouseover(function () {
    $("#bbIcon > i").removeClass().addClass("bbIcon");
    var cname = $("#bbIcon > em").attr("class");
    if(cname=="arrowup_n"){
        $("#bbIcon > em").removeClass().addClass("arrowup");
    }else if(cname=="arrowdown_n"){
        $("#bbIcon > em").removeClass().addClass("arrowdown");
    }
    if($("#closeIcon").attr("class")=="open" || $("#closeIcon").attr("class")=="open_n"){
        $(".mouseShow1,.mouseShow2,.mouseShow3,.mouseShow5").hide();
        $(".mouseShow4").show();
    }
});
$("#zdIcon").mouseover(function () {
    $("#zdIcon > i").removeClass().addClass("zdIcon");
    var cname = $("#zdIcon > em").attr("class");
    if(cname=="arrowup_n"){
        $("#zdIcon > em").removeClass().addClass("arrowup");
    }else if(cname=="arrowdown_n"){
        $("#zdIcon > em").removeClass().addClass("arrowdown");
    }
    if($("#closeIcon").attr("class")=="open" || $("#closeIcon").attr("class")=="open_n"){
        $(".mouseShow1,.mouseShow2,.mouseShow3,.mouseShow4").hide();
        $(".mouseShow5").show();
    }
});
//鼠标移除事件
$("#tjIcon").mouseout(function () {
    $("#tjIcon > i").removeClass().addClass("tjIcon_n");
    var cname = $("#tjIcon > em").attr("class");
    if(cname=="arrowup"){
        $("#tjIcon > em").removeClass().addClass("arrowup_n");
    }else if(cname=="arrowdown"){
        $("#tjIcon > em").removeClass().addClass("arrowdown_n");
    }
});
$("#fxIcon").mouseout(function () {
    $("#fxIcon > i").removeClass().addClass("fxIcon_n");
    var cname = $("#fxIcon > em").attr("class");
    if(cname=="arrowup"){
        $("#fxIcon > em").removeClass().addClass("arrowup_n");
    }else if(cname=="arrowdown"){
        $("#fxIcon > em").removeClass().addClass("arrowdown_n");
    }
});
$("#lcIcon").mouseout(function () {
    $("#lcIcon > i").removeClass().addClass("lcIcon_n");
    var cname = $("#lcIcon > em").attr("class");
    if(cname=="arrowup"){
        $("#lcIcon > em").removeClass().addClass("arrowup_n");
    }else if(cname=="arrowdown"){
        $("#lcIcon > em").removeClass().addClass("arrowdown_n");
    }
});
$("#bbIcon").mouseout(function () {
    $("#bbIcon > i").removeClass().addClass("bbIcon_n");
    var cname = $("#bbIcon > em").attr("class");
    if(cname=="arrowup"){
        $("#bbIcon > em").removeClass().addClass("arrowup_n");
    }else if(cname=="arrowdown"){
        $("#bbIcon > em").removeClass().addClass("arrowdown_n");
    }
});
$("#zdIcon").mouseout(function () {
    $("#zdIcon > i").removeClass().addClass("zdIcon_n");
    var cname = $("#zdIcon > em").attr("class");
    if(cname=="arrowup"){
        $("#zdIcon > em").removeClass().addClass("arrowup_n");
    }else if(cname=="arrowdown"){
        $("#zdIcon > em").removeClass().addClass("arrowdown_n");
    }
});

// $(".level").click(function () {
//     $("#sidebar > li").removeClass("chose");
//     $(".level").removeClass("active");
//     $(this).parents("li").addClass("chose");
//     $(this).addClass("active");
//     console.log();
//
// });
/*******子菜单鼠标移入/移除效果******/
$(".level").mouseover(function () {
    $(".level").removeAttr("style");
    $(this).css({
        "background":"#F5F6FA",
        "color":"#4C84FF"
    });
});
$(".level,.menu").mouseout(function () {
    $(this).removeAttr("style");
});

/********左侧收起之后鼠标移入效果*********/
$(".mouseShow").mouseover(function () {
    $(this).show();
});
$(".twoLevel>a>div").each(function (i) {
    $(this).mouseover(function () {
        $(this).css({
            "background": "#F5F6FA",
            "color": "#4C84FF"
        });
    });
    $(this).mouseout(function () {
        $(".mouseShow"+i).hide();
    });
});

//收齐左侧栏
function closeLeftPanel() {
    $("#sidenav").css("width","60px");
    $(".wrapper").css("margin-left","60px");
    $("#sidebar li span,#sidebar li .arrowdown_n,#sidebar li .level").hide();
    $("#closeIcon").removeClass().addClass("open_n").attr("onclick","openLeftPanel()");
}
//展开左侧栏
function openLeftPanel() {
    $(".mouseShow1,.mouseShow2,.mouseShow3").hide();

    $("#sidenav").css("width","200px");
    $(".wrapper").css("margin-left","200px");
    $("#sidebar li span,#sidebar li .arrowdown_n,#sidebar li .level").show();
    $("#closeIcon").removeClass().addClass("close_n").attr("onclick","closeLeftPanel()");
}

$("#closeIcon").mouseover(function () {
    var cname = $(this).attr("class");
    if(cname=="close_n"){
        $(this).removeClass().addClass("close");
    }else if(cname=="open_n"){
        $(this).removeClass().addClass("open");
    }
});
$("#closeIcon").mouseout(function () {
    var cname = $(this).attr("class");
    if(cname=="close"){
        $(this).removeClass().addClass("close_n");
    }else if(cname=="open"){
        $(this).removeClass().addClass("open_n");
    }
});

//x轴上标签的位置
function toXdata(params){
	var newParamsName = "";// 最终拼接成的字符串
	var paramsNameNumber = params?params.length:0;// 实际标签的个数
	if((params?params.length:5) > 8){
		var provideNumber = 5;
	}else{
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

//保留二位小数
function toDecimal(x) {
	var f = parseFloat(x);
	if (isNaN(f)) {
		return false;
	}
	var f = Math.round(x * 100) / 100;
	var s = f.toString();
	var rs = s.indexOf('.');
	if (rs < 0) {
		rs = s.length;
		s += '.';
	}
	while (s.length <= rs + 2) {
		s += '0';
	}
	return s;
}

var postDownLoadFile = function (options) {
    var config = $.extend(true, {method: 'post'}, options);
    var $iframe = $('<iframe id="down-file-iframe" />');
    var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
    $form.attr('action', config.url);
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