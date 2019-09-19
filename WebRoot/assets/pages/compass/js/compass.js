/*
 * 罗盘数据展示 2018-05-10
 */

//当前日期
var date = new Date();
var curYear = date.getFullYear();
var curMonth = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
var curDay = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();

var currentDay = curYear + "-" + curMonth + "-" + curDay;
console.log(currentDay);

$(document).ready(function () {
  //初始化日期-- 默认是前一天
  $("#date").val(dayHandle(currentDay,-1)).show();
  $("#month").val(curYear + "-" + curMonth);
  $("#year").val(curYear);
  //默认查询前一天、全国数据
  queryDataBydate(GDateType.DAY,dayHandle(currentDay,-1), GAreaType.ALL);
  cityList();
  switchTab();
  clickPrev();
  gotoTwoLevelPage();

//点击某个区域以外的地方关闭区域列表
  $(".calendar,.timeItem div").click(function (event) {
	if (!$(this).hasClass("areaPanel") && !$(this).hasClass("areaDiv")){
	  $("#selectList").hide();
	}
	event.stopPropagation();
  });
});
//上升、下降箭头
var raise = '<span>↑</span>';
var lower = '<span>↓</span>';

var zhibiaoKey = [
    "qy_qiye_num",
    "qy_guidang_shequ",
    "wyy_todo_project_num",
    "wyy_doing_project_num",
    "wyy_done_project_num",
    "wyy_cancel_project_num",
    "pduan_bind_mem",
    "pduan_act_mem",
    "pduan_atc_lv",
    "gg_shequ_num",
    "gg_shequ_zhanbi",
    "gg_shequ_avg",
    "mj_shequ_num",
    "mj_total_num",
    "mj_nopass_num",
    "mj_use_rate",
    "bs_total_num",
    "bs_yezhu_num",
    "bs_wuye_num",
    "jf_shequ_rate",
    "jf_online_times"	,
    "jf_online_jine",
    "bind_task_hushu",
    "bind_new_inc",
    "bind_act_num",
    "bind_new_hushu",
    "bind_task_rate",
    "bind_time_rate"
];
//按日期查询数据--接口
function queryDataBydate(dataType, dateValue, city) {
	$(".table").hide();
	$(".errorTips").html("").hide();
  $(".loading").css("display", "table");
  if(dataType && dateValue && city){
	  $.ajax({
		type: "get",
		url: GUrl + proxy("/dataApiQuery/luopan_home_data?date_type=" + dataType + "&date_value=" + dateValue + "&city=" + city),
		dataType: "json",
		success: function (res) {
		    //console.log("首页数据："+JSON.stringify(res));
			$(".loading").hide();
		  	if (res.code == 0) {
				var list = res.data;
				//查找后台有没有未返回的指标key
				for(var j=0;j<zhibiaoKey.length;j++){
					var zb = zhibiaoKey[j];
                    var pos = list.indexOf(zb);
                    if(pos==-1){
						if(zb=="qy_qiye_num" || zb=="wyy_todo_project_num" || zb=="pduan_bind_mem" || zb=="gg_shequ_num" || zb=="mj_shequ_num" || zb=="bs_total_num" || zb=="bind_task_hushu"){
                            $("."+zb+" td:nth-child(3)").text("0");
                            $("."+zb+" td:nth-child(4)").text("0%").removeClass();
						}else if(zb=="jf_shequ_rate"){
                            $("."+zb+" td:nth-child(3)").text("0%");
                            $("."+zb+" td:nth-child(4)").text("0%").removeClass();
						}else{
							if(zb=="pduan_atc_lv" || zb=="gg_shequ_zhanbi" || zb =="mj_use_rate" || zb=="bind_task_rate" || zb=="bind_time_rate" ){
                                $("."+zb+" td:nth-child(2)").text("0%");
                                $("."+zb+" td:nth-child(3)").text("0%").removeClass();
							}else{
                                $("."+zb+" td:nth-child(2)").text("0");
                                $("."+zb+" td:nth-child(3)").text("0%").removeClass();
							}
						}
                    }
				}

				if (!checkEmptyOrNull(list) && list.length > 0) {
			  		for (var i = 0; i < list.length; i++) {
						var topic_type = list[i].topic_type; //维度 string
						var key = list[i].key;               //业务指标 string 唯一
						var value = list[i].value;          //数值 number
						var huanbi = list[i].huanbi;        //环比 string

						if (key == "qy_qiye_num") {
						  check4Data(".qy_qiye_num ",value, huanbi,topic_type);
						  continue;
						}
						if (key == "qy_guidang_shequ") {
						  check3Data(".qy_guidang_shequ ",value,huanbi,topic_type);
						  continue;
						}
						if (key == "wyy_todo_project_num") {
						  check4Data(".wyy_todo_project_num ",value, huanbi,topic_type);
						  continue;
						}
						if (key == "wyy_doing_project_num") {
						  check3Data(".wyy_doing_project_num ",value,huanbi,topic_type);
						  continue;
						}
						if (key == "wyy_done_project_num") {
						  check3Data(".wyy_done_project_num ",value, huanbi,topic_type);
						  continue;
						}
						if (key == "wyy_cancel_project_num") {
						  check3Data(".wyy_cancel_project_num ",value, huanbi,topic_type);
						  continue;
						}
						if (key == "pduan_bind_mem") {
						  check4Data(".pduan_bind_mem ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "pduan_act_mem") {
						  check3Data(".pduan_act_mem ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "pduan_atc_lv") {//P端活跃员工占比
						  check3Data(".pduan_atc_lv ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "gg_shequ_num") {
						  check4Data(".gg_shequ_num ",value, huanbi,topic_type);
						  continue;
						}
						if (key == "gg_shequ_zhanbi") {//发公告社区占比
						  check3Data(".gg_shequ_zhanbi ",value, huanbi,topic_type);
						  continue;
						}
						if (key == "gg_shequ_avg") {
						  check3Data(".gg_shequ_avg ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "mj_shequ_num") {
						  check4Data(".mj_shequ_num ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "mj_total_num") {
						  check3Data(".mj_total_num ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "mj_nopass_num") {
						  check3Data(".mj_nopass_num ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "mj_use_rate") {
						  check3Data(".mj_use_rate ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "bs_total_num") {
						  check4Data(".bs_total_num ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "bs_yezhu_num") {
						  check3Data(".bs_yezhu_num ",value, huanbi,topic_type);
						  continue;
						}
						if (key == "bs_wuye_num") {
						  check3Data(".bs_wuye_num ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "jf_shequ_rate") {//开通缴费功能的项目占比
						  check4Data(".jf_shequ_rate ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "jf_online_times") {
						  check3Data(".jf_online_times ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "jf_online_jine") {
						  check3Data(".jf_online_jine ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "bind_task_hushu") {
						  check4Data(".bind_task_hushu ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "bind_new_inc") {
						  check3Data(".bind_new_inc ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "bind_act_num") {
						  check3Data(".bind_act_num ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "bind_new_hushu") {
						  check3Data(".bind_new_hushu ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "bind_task_rate") {//绑定任务完成占比
						  check3Data(".bind_task_rate ", value, huanbi,topic_type);
						  continue;
						}
						if (key == "bind_time_rate") {//时间进度比
						  check3Data(".bind_time_rate ",value, huanbi,topic_type);
						  continue;
						}
			  		}
					if(dataType == GDateType.MONTH){
						  var y = dateValue.substr(0,4);
						  var m = dateValue.substr(-2);
						  if(y==curYear && m==curMonth){//说明是当年当月
							$(".time p").text("截止至"+m+"月"+dayHandle(currentDay,-1).substr(-2)+"日").show();
						  }else{
							var dayCount = getDayCount(y,m);
							$(".time p").text("截止至"+dateValue.substr(-2)+"月"+dayCount+"日").show();
						  }
					}else{
						$(".time p").html("").hide();
					}
                    $(".table").css("display","table");
				} else { //空UI
				    $(".time p").html("").hide();
                    $(".errorTips").text("暂无数据～").show();
				}
			} else {//错误UI
				$(".time p").html("").hide();
                $(".errorTips").text("暂无数据～").show();
			}
		},
		error: function () {
			$(".loading").hide();
			$(".time p").html("").hide();
            $(".errorTips").text("请稍后重试～").show();
		}
	  });
  }else{
		$(".loading").hide();
		$(".time p").html("").hide();
        $(".errorTips").text("暂无数据～").show();
  }

}

//获取城市列表--接口
function cityList() {
  $.ajax({
	url: GUrl + proxy('/dataApiQuery/luopan_home_city'),
	type: 'get',
	dataType: 'json',
	success: function (res) {
	  //console.log("城市列表："+JSON.stringify(res));
	  if (res.code == 0) {
		var list = res.data;
		if (!checkEmptyOrNull(list) && list.length > 0) {
		  for (var i = 0; i < list.length; i++) {
			var region_id = list[i].region_id;
			var region_name = list[i].region_name;
			$("#selectList").append("<p region-id='" + region_id + "' onclick='selectArea(this)'>" + region_name + "</p>");
		  }
		} else { //空
		}
	  } else { //错误
	  }
	},
	error: function () {
	}
  })
}
//进入下级页面
function gotoTwoLevelPage() {
  var dateList = document.getElementById("dateList");
  dateList.addEventListener('click',function(ev){
      var city = $(".area").attr("data-type");
	  var target = ev.target;
	  while(target !== dateList ){
		  if(target.tagName.toLowerCase() == 'tr'){
			var cName = "."+target.className;
			var topic_type= $(cName).attr("topic_type");
			if(city==GAreaType.ALL){
                lowerPage("area",topic_type,city);
			}else if(city==GAreaType.DONGNAN || city==GAreaType.HUABEI || city==GAreaType.XINAN){
                lowerPage("city",topic_type,city);
			}else{
                lowerPage("community",topic_type,city);
			}
			break;
		  }
		  target = target.parentNode;
	  }
  });
}

function lowerPage(pageName,topic_type,city) {
    var dataType,riqi;
    var text= $(".prev").text().trim();
    if(text=="前一日"){
        dataType=GDateType.DAY;
        riqi = $("#date").val().trim();
    }else if(text=="前一月"){
        dataType=GDateType.MONTH;
        riqi = $("#month").val().trim();
    }else if(text=="前一年"){
        dataType=GDateType.YEAR;
        riqi = $("#year").val().trim();
    }
    window.location.href = pageName.trim()+".html?dataType="+dataType+"&dateValue="+riqi+"&topic_type="+topic_type+"&city="+city;
}

//塞值--每一个维度第一条数据
function check4Data(className,value, huanbi,topic_type) {
  $(className).attr("topic_type",topic_type);
  if (!checkEmptyOrNull(value)) {
	  var  cN = className.trim();
	  if(cN!=null && cN!=""){
		  if(cN==".pduan_atc_lv" || cN ==".mj_use_rate" || cN==".gg_shequ_zhanbi" || cN==".jf_shequ_rate" || cN==".bind_task_rate" || cN==".bind_time_rate"){
			$(className + " td:nth-child(3)").text(value+"%");
		  }else{
			$(className + " td:nth-child(3)").text(value);
		  }
	  }
  }else{
	$(className + " td:nth-child(3)").text("0");
  }
  if (Number(huanbi) > 0) {//以下 td前面的空格不要删除
	$(className + " td:nth-child(4)").text(huanbi + "%").removeClass("text-red").addClass("text-green").append(raise);
  } else if (Number(huanbi) < 0) {
	$(className + " td:nth-child(4)").text(huanbi + "%").removeClass("text-green").addClass("text-red").append(lower);
  }else if(Number(huanbi) == 0){
      $(className + " td:nth-child(4)").text("0%").removeClass();
	// if(value>0){
	//   $(className + " td:nth-child(4)").text(huanbi + "%").removeClass();
	// }else{
	//   $(className + " td:nth-child(4)").text("-").removeClass();
	// }
  }else{//正、负无穷的情况
	$(className + " td:nth-child(4)").text("-").removeClass();
  }
}
//塞值--每一个维度除了第一条数据外的数据
function check3Data(className,value, huanbi,topic_type) {
  $(className).attr("topic_type",topic_type);
    var text= $(".prev").text().trim();
    var cN = className.trim();
	// if (value==0 && Number(huanbi)==0) {
	// 	$(className + " td:nth-child(2)").text("-");
	// }else{ }
	if (!checkEmptyOrNull(value)) {
		if (cN != null && cN != "") {
			if((cN == ".mj_use_rate" || cN == ".mj_nopass_num") && text!="前一日"){
                $(className + " td:nth-child(2)").text("-");
			}else{
                if (cN == ".pduan_atc_lv" || cN == ".mj_use_rate" || cN == ".gg_shequ_zhanbi" || cN == ".jf_shequ_rate" || cN == ".bind_task_rate" || cN == ".bind_time_rate") {
                    $(className + " td:nth-child(2)").text(value+"%");
                } else {
                    $(className + " td:nth-child(2)").text(value);
                }
			}
		}
	}else{
        $(className + " td:nth-child(2)").text("0");
	}

  if (Number(huanbi) > 0) {//以下 td前面的空格不要删除
	$(className + " td:nth-child(3)").text(huanbi + "%").removeClass("text-red").addClass("text-green").append(raise);
  } else if (Number(huanbi) < 0) {
	$(className + " td:nth-child(3)").text(huanbi + "%").removeClass("text-green").addClass("text-red").append(lower);
  }else if(Number(huanbi) == 0){
      $(className + " td:nth-child(3)").text("0%").removeClass();
	// if(value>0){
	//   $(className + " td:nth-child(3)").text(huanbi + "%").removeClass();
	// }else{
	//   $(className + " td:nth-child(3)").text("-").removeClass();
	// }
  }else{//正无穷
	$(className + " td:nth-child(3)").text("-").removeClass();
  }
}
/*******************js交互效果*****************/
//选择区域
function clickArea() {
  if($("#selectList p").length > 0){
	$("#selectList").slideToggle();
  }
}
function selectArea(the) {
  var id = $(the).attr("region-id");
  var text = $(the).text();
  $(".area").attr("data-type",id).text(text);
  $("#selectList").hide();
  var text= $(".prev").text().trim();
  if(text=="前一日"){
	queryDataBydate(GDateType.DAY,$("#date").val().trim(),id);
  }else if(text=="前一月"){
	queryDataBydate(GDateType.MONTH,$("#month").val().trim(),id);
  }else if(text=="前一年"){
	queryDataBydate(GDateType.YEAR,$("#year").val().trim(),id);
  }
}

//点击日期切换
function switchTab() {
  $(".tabHead > .calendar").click(function () {
	$(".loading").css("display", "table");
	var i = $(this).index();
	$(this).addClass("active").siblings().removeClass("active");
	if (i == 0) {
	  $(".time p").html("").hide();//隐藏更新截止日期
	  $("#date").val(dayHandle(currentDay,-1)).show();
	  $("#month,#year").hide();
	  $(".timeItem .prev").text("前一日");
	  $(".timeItem .next").text("后一日");
	  queryDataBydate(i + 1,dayHandle(currentDay,-1),GAreaType.ALL);
	} else if (i == 1) {
	  $("#month").val(curYear + "-" + curMonth).show();
	  $("#date,#year").hide();
	  $(".datepicker,#yearPanel").hide();
	  $("#monthPanel").show();
	  $(".timeItem .prev").text("前一月");
	  $(".timeItem .next").text("后一月");
	  queryDataBydate(i + 1, curYear + "-" + curMonth, GAreaType.ALL);
	} else if (i == 2) {
	  $(".time p").html("").hide();//隐藏更新截止日期
	  $("#year").val(curYear).show();
	  $("#date,#month").hide();
	  $(".datepicker,#monthPanel").hide();
	  $("#yearPanel").show();
	  $(".timeItem .prev").text("前一年");
	  $(".timeItem .next").text("后一年");
	  queryDataBydate(i + 1, curYear, GAreaType.ALL);
	}
  });
}

//***************以下是 点击前一天、月、年******************
function clickPrev(){
  $(".prev").click(function () {
	var area = $(".area").attr("data-type").trim();
	var text= $(this).text().trim();
	if(text=="前一日"){
	  prevCommonFun(GDateType.DAY,"#date",area);
	}else if(text=="前一月"){
	  prevCommonFun(GDateType.MONTH,"#month",area);
	}else if(text=="前一年"){
	  prevCommonFun(GDateType.YEAR,"#year",area);
	}
  });
}

//点击后一天、月、年
function clickNext(the){
	var area = $(".area").attr("data-type").trim();
	var text= $(the).text().trim();
	if(text=="后一日"){
	  nextCommonFun("#date",curYear+"-"+curMonth+"-"+curDay,GDateType.DAY,area);
	}else if(text=="后一月"){
	  nextCommonFun("#month",curYear+"-"+curMonth,GDateType.MONTH,area);
	}else if(text=="后一年"){
	  nextCommonFun("#year",curYear,GDateType.YEAR,area);
	}
}

//************ 封装 *************
/*
 * 前一天、月、年 日期显示
 * @param type 日期类型
 */
function prevCommonFun(type,inputId,area) {
  var inputDate= $(inputId).val().trim();
  if(type==GDateType.DAY){
	$(inputId).val(dayHandle(inputDate,-1));
  }else if(type==GDateType.MONTH){
	$(inputId).val(monthHandle(inputDate,-1));
  }else{
	$(inputId).val(inputDate-1);
  }
  queryDataBydate(type, $(inputId).val().trim(), area);
}
/*
 * 点击后一天、月、年 校验日期是否小于“前一天”日期
 * @param inputId 日期文本框的值 "#year"
 * @param curD 当前日期 "2018"
 * @param type 日期类型 1 到日 2 到月 3 到年
 * @param area 区域 从页面获取
 */
function nextCommonFun(inputId,curD,type,area) {
  var curDT =new Date(curD).getTime();//当前日期毫秒值
  var inputDate=$(inputId).val().trim();//日期框的值
  var inputDT;
  if(type==GDateType.YEAR){
	inputDT = inputDate;
  }else{
	inputDT = new Date(inputDate).getTime();
  }
  //文本框的日期 小于 当前日期 才执行 后一天
  if(inputDT < curDT){
	if(type==GDateType.DAY){
        var nt = dayHandle(inputDate,1);
		if(new Date(nt).getTime()!=curDT){
            $(inputId).val(dayHandle(inputDate,1));
		}else{
			popEffect("请选择今天之前的日期");
		}
	}else if(type==GDateType.MONTH){
	  $(inputId).val(monthHandle(inputDate,1));
	}else if(type==GDateType.YEAR){
	  $(inputId).val(yearHandle(inputDate,1));
	}
	queryDataBydate(type, $(inputId).val().trim(), area);
  }
}

//*******************以上是 点击前一天、月、年******************

//*************工具 开始************
/*
 * 日期加减
 * @param date 传入的日期，"2018-05-13"
 * @param number 加减数量，前一天 -1，后一天 +1
 */
function dayHandle(date,num){
  var date=new Date(date);
  date.setDate(date.getDate()+num);
  var m=(date.getMonth()+1) < 10 ? '0'+(date.getMonth()+1) : (date.getMonth()+1);
  var d=date.getDate() < 10 ? '0'+date.getDate() : date.getDate();
  return date.getFullYear()+'-'+m+'-'+d;
}
/**
 * 月份加减
 * @param date "2018-05"
 * @param num 加减数量
 */
function monthHandle(date,num){
  var date = new Date(date);
  date.setMonth(date.getMonth()+num);
  var m=(date.getMonth()+1) < 10 ? '0'+(date.getMonth()+1) : (date.getMonth()+1);
  return date.getFullYear()+'-'+m;
}
/**
 * 年份加减
 * @param date
 * @param num
 */
function yearHandle(date,num){
  var date = new Date(date);
  date.setFullYear(date.getFullYear()+num);
  return date.getFullYear();
}
//*************工具 结束************

//*************插件部分************
// 日 插件
function showDate() {
  var inputDate=$("#date").val().trim();//日期框的值
  var input_time = new Date(inputDate).getTime();
  var cur_time = date.getTime();
  if(input_time <= cur_time){
	var area = $(".area").attr("data-type").trim();
	queryDataBydate(GDateType.DAY,$("#date").val().trim(),area);
  }else{
	//弹框提示
	popEffect("请选择今天之前的日期");
  }
}
/**
 * 年和月 插件
 */
function shijianHandle(idName,yearBool,monBool,startYear) {
  $(idName).shijian({
	showNowTime:false,//是否显示当前日期
	Year:yearBool,//当前年份
	Month:monBool,//是否显示月
	Day:false,
	Hour:false,//是否显示小时
	Minute:false,//是否显分钟
	startYear:startYear,
	endYear:new Date().getFullYear()
  });
}

