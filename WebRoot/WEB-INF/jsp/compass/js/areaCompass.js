/*
 *  罗盘二级数据展示 2018-05-18
 */
$(document).ready(function () {
  getLuopanCityData(getUrlParam("dataType"), getUrlParam("dateValue"), getUrlParam("topic_type"), getUrlParam("city"));
});

var barJSONData="";

//获取二、三级页面数据--接口
function getLuopanCityData(dataType, dateValue, topic_type, city) {
  $(".loading").css("display", "table");
  $(".errorTips").html("").hide();
  if (dataType && dateValue && topic_type && city) {
    checkTopic(topic_type);

    $.ajax({
      type: "get",
      url: GUrl + "/dataApiQuery/luopan_city_data?date_type=" + dataType + "&date_value=" + dateValue + "&topic_type=" + topic_type + "&city=" + city,
      dataType: "json",
      success: function (res) {
        //console.log("页面数据：" + JSON.stringify(res));
        $(".loading").hide();
        $(".twoLevelTime").text(dateValue); //日期
        $(".twoLevelArea").text(getCityName(city)).attr("data-type",city);//区域
        if (res.code == 0) {
          showLuoPanDate(city, res.data);
        } else {
          $(".errorTips").text("暂无数据～").show();
        }
      }, error: function (XMLHttpRequest, textStatus) {
        console.log(textStatus);
        if (textStatus == "error") {
          $(".errorTips").text("请稍后重试～").show();
        }
      }
    });
  } else {
    $(".errorTips").text("系统异常，请稍后重试～").show();
  }
}

//展示html
function showLuoPanDate(city, data) {
  var curStr = "", cityStr = "", titleStr = "";
  if (!checkEmptyOrNull(data) && data.length > 0) {
    var maps = new Map();
    var titleMap = new Map();
    var map = null;
    barJSONData = data;
    for (var i = 0; i < data.length; i++) {
      var region_id = data[i].region_id;    //城市类型 string
      var region_name = data[i].region_name;//城市名称 string
      var topic_type = data[i].topic_type;  //维度 string
      var key = data[i].key;                //业务指标 string 唯一
      var value = data[i].value;            //数值 number
      var huanbi = data[i].huanbi;          //环比 string
      if (city == region_id) {//当前区域数据
        curStr += pianquCommonFun(getValueByKey(key), key, value, huanbi);
      } else {//片区、城市列表数据
        if (maps.has(region_name)) {
          map = maps.get(region_name);
        } else {
          map = new Map();
          map.set("region_id", region_id);
          map.set("region_name", region_name);
        }
        map.set(key, value);
        maps.set(region_name, map);
        if (!titleMap.has(key)) {
          titleMap.set(key,getValueByKey(key));
        }
      }
    }
    initReport();//放在这里调用

    titleStr += '<div class="item flex" style="line-height: 22px;">';
    titleStr += '<div class="tabTitle">片区</div>';
    titleMap.forEach(function (val, k) {
      titleStr += '<div class="tabTitle">' + val + '</div>';
    });
    titleStr += '</div>';

    //k 区域名称，val  map
    maps.forEach(function (val, k) {
      if(k=="华北片区"){
        cityStr +='<div class="item flex" city="huabei">';
      }else if(k=="东南片区"){
        cityStr +='<div class="item flex" city="dongnan">';
      }else if(k=="西南片区"){
        cityStr +='<div class="item flex" city="xinan">';
      }else if(k=="其它"){
        cityStr +='<div class="item flex" city="qita">';
      }else if(k=="北京市"){
        cityStr +='<div class="item flex" city="bj">';
      }else if(k=="重庆市"){
        cityStr +='<div class="item flex" city="cq">';
      }else if(k=="成都市"){
        cityStr +='<div class="item flex" city="cd">';
      }else if(k=="上海市"){
        cityStr +='<div class="item flex" city="sh">';
      }else if(k=="西安市"){
        cityStr +='<div class="item flex" city="xa">';
      }else if(k=="杭州市"){
        cityStr +='<div class="item flex" city="hz">';
      }else if(k=="广州市"){
        cityStr +='<div class="item flex" city="gz">';
      }else{
        cityStr +='<div class="item flex">';
      }

      cityStr += '<div class="value">' + k + '</div>';
      //kTitle  指标id， valTitle 指标名称
      titleMap.forEach(function (valTitle, kTitle) {
        if (val) {
          var cValue = val.get(kTitle);
          if (cValue) {
              if(kTitle!="region_id" && kTitle!="region_name"){
                  if (kTitle == "pduan_atc_lv" || kTitle == "gg_shequ_zhanbi" || kTitle == "jf_shequ_rate" || kTitle == "bind_task_rate" || kTitle == "bind_time_rate") {
                    cityStr += '<div class="value">' + cValue + '%</div>';
                  }else{
                    cityStr += '<div class="value">' + cValue + '</div>';
                  }
              }
          } else {
            cityStr += '<div class="value">-</div>';
          }
        } else {
          cityStr += '<div class="value">-</div>';
        }
      });
      cityStr += '</div>';
    });
  }
  $("#topData").append(curStr);
  $("#cityData").append(titleStr + cityStr);
}

//去第三级页面
function gotoThreeLevelPage() {
  $('body').on('click', '#cityData div',function () {
    var city = $(this).attr("city");
    if(city){
      window.location.href="city.html?dataType="+getUrlParam("dataType")+"&dateValue="+getUrlParam("dateValue")+"&topic_type="+ getUrlParam("topic_type")+"&city="+city;
    }
  });
}

//柱状图--空
function initReport() {
  var myChart = echarts.init(document.getElementById('main'));
  window.onresize = myChart.resize;
  myChart.showLoading();
  var option = {
     title : {
       x : "center",
       y : "top"
     },
     // label: {
     //   show: true,
     //   position:'top',
     //   // formatter: '文字' // 标签的文字
     // },
     tooltip: {},
     legend: {
       show : true,
       data:[],//'签约企业数量','合同归档社区数量'
       x : 'center',
       y : 'bottom'
     },
     xAxis: {
       type : 'category',
       data: [] //"华北片区","东南片区","西南片区","其它"
     },
     yAxis: {},
     series: []
  };
  myChart.setOption(option);
  showBarData(myChart,option);
}

//柱状图--数据
function showBarData(myChart,option) {
  // 设置加载等待隐藏
  myChart.hideLoading();
  //区域列表
  var areaArr = [];
  //指标列表 例如： ['签约企业数量','合同归档社区数量']
  var legendArr = [],zhibiao = [];
  //series 列表 例如： [15, 20, 26,10]
  var seriesArr = [],valueArr = [];

  let serData = barJSONData;
  if(!checkEmptyOrNull(serData)){
    let maps = new Map();
    let titleMap = new Map();
    let map = null;
    for(let j=0;j<serData.length;j++){
      let region_id = serData[j].region_id;    //城市类型 string
      let region_name = serData[j].region_name;//城市名称 string
      let key = serData[j].key;                //业务指标 string 唯一
      let value = serData[j].value;            //数值 number
      if(region_id != $(".twoLevelArea").attr("data-type")){
        if (maps.has(key)) {
          map = maps.get(key);
        } else {
          map = new Map();
        }
        map.set(region_name, value);
        maps.set(key, map);
        if (!titleMap.has(region_name)) {
          titleMap.set(region_name,region_name);
        }
      }
    }
    //区域
    titleMap.forEach(function (valTitle, kTitle) {
      areaArr.push(valTitle);
    });

    //指标
    maps.forEach(function (val, k) {
      legendArr.push(getValueByKey(k));
      valueArr=[];
      titleMap.forEach(function (valTitle, kTitle) {
        if (val) {
          var cValue = val.get(kTitle);
          valueArr.push(cValue);
        } else {
          valueArr.push(0);
        }
      });
      seriesArr.push(valueArr);
    });
  }

  option.title.text = $("#busTitle").text();
  option.legend.data= legendArr;
  option.xAxis.data = areaArr;

  for (i = 0; i < seriesArr.length; i++) {
    var item = {
      name : legendArr[i],
      type : 'bar',
      data : seriesArr[i]
    };
    //option.label.formatter = seriesArr[i].data;//数字太大，展示不下
    option.series.push(item);
  }

  myChart.setOption(option);
}
