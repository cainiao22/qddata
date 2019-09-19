/*
 *  罗盘二、三、四级页面数据展示 2018-05-18
 */
$(document).ready(function () {
    cityList();
    getLuopanCityData(getUrlParam("dataType"), getUrlParam("dateValue"), getUrlParam("topic_type"), getUrlParam("city"));
});

var barJSONData="",cityJSONData="";

//获取二、三级页面数据--接口
function getLuopanCityData(dataType, dateValue, topic_type, city) {
    $(".loading").css("display", "table");
    $(".twoLevelTime").text(dateValue); //日期
    $(".twoLevelArea").text(getCityName(city)).attr("data-type",city);//区域
    $("#busTitle").text(checkTopic(topic_type));
    $(".errorTips").html("").hide();
  if (dataType && dateValue && topic_type && city) {
    $.ajax({
      type: "get",
      url: GUrl + proxy("/dataApiQuery/luopan_city_data?date_type=" + dataType + "&date_value=" + dateValue + "&topic_type=" + topic_type + "&city=" + city),
      dataType: "json",
      success: function (res) {
        //console.log("页面数据：" + JSON.stringify(res));
        $(".loading").hide();
        if (res.code == 0) {
          showLuoPanDate(city, res.data);
        } else {
          $(".errorTips").text("暂无数据～").show();
        }
      }, error: function () {
          $(".loading").hide();
          $(".errorTips").text("请稍后重试～").show();
      }
    });
  } else {
    $(".errorTips").text("系统异常，请稍后重试～").show();
  }
}

/**
 * 四级页面接口
 * 物业-社区接口
 */
var currentPage=1; //当前页
var pageSize = 20; //每页记录
var totalCount = 0;//总条数
var footPage = 0;  //总页数
var number=1;//序号
function getPropertyList() {
    var city = getUrlParam("city");
    var keyword = $(".sbox").val().trim();
    if (currentPage != 1) {
        if (currentPage > footPage) {
            currentPage = null;
            $(".nomore").text("没有更多了～").show();
        }
    }
    if (currentPage != null) {
        $.ajax({
            url: GUrl+proxy('/dataApiQuery/luopan_city_project_data?city='+city+'&pageSize='+pageSize+'&currentPage='+currentPage+'&keyword='+keyword+'&calTotal=true'),
            type: 'get',
            dataType: 'json',
            success: function (res) {
                //console.log("物业公司列表："+JSON.stringify(res));
                if (res.code == 0 && res.data != null && res.data != "") {
                    showCommunity(res);
                } else { //错误
                    $(".nomore").text("暂无数据～").show();
                }
            },
            error: function () {
                $(".loading").hide();
                $(".nomore").text("请稍后重试～").show();
            }
        });
    }else{
        if (totalCount < 20) {
            $(".nomore").hide();
        } else {
            $(".nomore").text("没有更多了～").show();
        }
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
                if (!checkEmptyOrNull(res.data) && res.data.length > 0) {
                    cityJSONData = res.data;
                } else { //空
                }
            } else { //错误
            }
        },
        error: function (XMLHttpRequest, textStatus) {
        }
    });
}

//根据城市id 判断城市名称
function getCityName(id) {
    var cityName = "";
    var citylist = cityJSONData;
    if(!checkEmptyOrNull(citylist)){
        for(var i=0;i<citylist.length;i++){
            var region_id = citylist[i].region_id;
            var region_name = citylist[i].region_name;
            if(id==region_id){
                cityName = region_name;
            }
        }
    }else{
        if(id==GAreaType.ALL){ cityName="全国"}
        if(id==GAreaType.HUABEI){ cityName="华北片区"}
        if(id==GAreaType.DONGNAN){ cityName="东南片区"}
        if(id==GAreaType.XINAN){ cityName="西南片区"}
        if(id==GAreaType.OTHER){ cityName="其它"}
        if(id==GAreaType.BJ){ cityName="北京市"}
        if(id==GAreaType.CQ){ cityName="重庆市"}
        if(id==GAreaType.CD){ cityName="成都市"}
        if(id==GAreaType.SH){ cityName="西安市"}
        if(id==GAreaType.XA){ cityName=="西安市"}
        if(id==GAreaType.HZ){ cityName="杭州市"}
        if(id==GAreaType.GZ){ cityName="广州市"}
    }
    return cityName;
}

//展示html
function showLuoPanDate(city, data) {
  var curStr = "", cityStr = "", titleStr = "";
  if (!checkEmptyOrNull(data) && data.length > 0) {
    var citlist = cityJSONData;

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
                if (!titleMap.has(key)) {
                  titleMap.set(key,getValueByKey(key));
                }
                if (maps.has(region_name)) {
                  map = maps.get(region_name);
                } else {
                  map = new Map();
                  map.set("region_id", region_id);
                  map.set("region_name", region_name);
                }
                map.set(key, value);
                maps.set(region_name, map);
          }
    }

    if(titleMap.size > 0){
        if(getUrlParam("topic_type")==="jiaofei"){
            initNumber();
            initAmount();
            initZhanbi();
        }else{
            initReport();//放在这里调用
        }
        titleStr += '<div class="item flex" style="line-height: 22px;">';
        titleStr += '<div class="tabTitle">片区</div>';
        titleMap.forEach(function (val, k) {
            titleStr += '<div class="tabTitle">' + val + '</div>';
        });
        titleStr += '</div>';
    }

    //k 区域名称，val  map
    if(maps.size > 0){
        maps.forEach(function (val, k) {
            if(!checkEmptyOrNull(citlist)){
                for(var i=0;i<citlist.length;i++){
                    var region_id = citlist[i].region_id;
                    var region_name = citlist[i].region_name;
                    if(k == region_name){
                        cityStr +='<div class="item flex" city="'+region_id+'">';
                    }
                }
            }else{
                cityStr +='<div class="item flex">';
            }
            cityStr += '<div class="value">' + k + '</div>';
            //kTitle  指标id， valTitle 指标名称
            titleMap.forEach(function (valTitle, kTitle) {
                if (val) {
                    var cValue = val.get(kTitle);
                    if (!checkEmptyOrNull(value)) {
                        if(kTitle!="region_id" && kTitle!="region_name"){
                            if (kTitle == "pduan_atc_lv" || kTitle == "gg_shequ_zhanbi" || kTitle == "jf_shequ_rate" || kTitle == "bind_task_rate" || kTitle == "bind_time_rate") {
                                cityStr += '<div class="value">' + cValue + '%</div>';
                            }else{
                                cityStr += '<div class="value">' + cValue + '</div>';
                            }
                        }
                    } else {
                        cityStr += '<div class="value">0</div>';
                    }
                } else {
                    cityStr += '<div class="value">-</div>';
                }
            });
            cityStr += '</div>';
        });
    }
  }
  $("#topData").append(curStr);
  $("#cityData").append(titleStr + cityStr);
}

//去第三、四级页面
function gotoThreeLevelPage(pageName) {
  $('body').on('click touchstart', '#cityData div',function () {
    var city = $(this).attr("city");
    if(city){
      window.location.href=pageName+".html?dataType="+getUrlParam("dataType")+"&dateValue="+getUrlParam("dateValue")+"&topic_type="+ getUrlParam("topic_type")+"&city="+city;
    }
  });
}
//去社区指标详情页
function gotoProjectPage() {
    var dateList = document.getElementById("communityData");
    dateList.addEventListener('click',function(ev){
        var target = ev.target;
        while(target !== dateList ){
            if(target.parentNode.className == 'item flex-div2'){
                var projectId = target.parentNode.id;
                if(projectId){
                    window.location.href="project.html?projectId="+projectId+"&dataType="+getUrlParam("dataType")+"&dateValue="+getUrlParam("dateValue");
                }
            }
            target = target.parentNode;
        }
    });
}
//柱状图--空
function initReport() {
    $("#main").css({
        "height":"300px",
        "padding":"0px 10px 15px 10px"
    });
  var myChart = echarts.init(document.getElementById('main'));
  window.onresize = myChart.resize;
  myChart.showLoading();
  var option = {
     // title : {
     //   x : "center",
     //   y : "top"
     // },
     // label: {
     //   show: true,
     //   position:'top',
     //   // formatter: '文字' // 标签的文字
     // },
      grid:{
          left: 40,
          bottom: 80,
          right: 40
      },
     tooltip: {},
     legend: {
         show : true,
         data:[],//'签约企业数量','合同归档社区数量'
         x : 'center',
         y : 'bottom',
         itemWidth:20,
         itemHeight:12,
         padding:[10,0, 0,0]
     },
     xAxis: {
         type : 'category',
         axisLabel: {
             interval: 0,
             formatter:function(value,index){
                 if(value.length % 2!=0){
                     return value;
                 }else{
                     return value;
                 }
             }
         },
         data: [] //"华北片区","东南片区","西南片区","其它"
     },
     yAxis: [{
         type: 'value',
         name: '(个)',
         axisTick: {
             inside: true
         },
         scale: true,
         axisLabel: {
             formatter: function (value, index) {
                 if (value >= 10000 && value < 10000000) {
                     value = value / 10000 + "万";
                 } else if (value >= 10000000) {
                     value = value / 10000000 + "千万";
                 }
                 return value;
             }
         }
     },
     {
         type : 'value',
         name : '占比(%)',
         splitLine: { show: false },//去除网格线
         axisLabel : {
             formatter: '{value}'
         }
     }
     ],
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
  var legendArr = [],zhibiaoKey = [];
  //series 列表 例如： [15, 20, 26,10]
  var seriesArr = [],valueArr = [];

  var serData = barJSONData;
  if(!checkEmptyOrNull(serData)){
    var maps = new Map();
    var titleMap = new Map();
    var map = null;
    for(var j=0;j<serData.length;j++){
        var region_id = serData[j].region_id;     //城市类型 string
        var region_name = serData[j].region_name; //城市名称 string
        var key = serData[j].key;                //业务指标 string 唯一
        var value = serData[j].value;            //数值 number
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
        zhibiaoKey.push(k);//存指标
        legendArr.push(getValueByKey(k));
        valueArr=[];
        titleMap.forEach(function (valTitle, kTitle) {
            if (val) {
              valueArr.push(val.get(kTitle));
            } else {
              valueArr.push(0);
            }
        });
        seriesArr.push(valueArr);
    });
  }

  //option.title.text = $("#busTitle").text();
  option.legend.data= legendArr;
  option.xAxis.data = areaArr;

  for (i = 0; i < seriesArr.length; i++) {
      var item="";
      if(zhibiaoKey[i]=="pduan_atc_lv" || zhibiaoKey[i]=="gg_shequ_zhanbi" || zhibiaoKey[i] =="mj_use_rate" || zhibiaoKey[i]=="jf_shequ_rate" || zhibiaoKey[i]=="bind_task_rate" || zhibiaoKey[i]=="bind_time_rate"){
            item = {
                name : legendArr[i],
                type : 'line',
                yAxisIndex: 1,
                // itemStyle : {//折线图每个折点显示数值
                //     normal: {
                //         label : {show: true}
                //     }
                // },
                data : seriesArr[i]
            }
      }else{
            item = {
              name : legendArr[i],
              type : 'bar',
              barGap :0,//设置同一组柱子的间距
              data : seriesArr[i]
            };
      }
    //option.label.formatter = seriesArr[i].data;//数字太大，展示不下
    option.series.push(item);
  }

  myChart.setOption(option);
}

//缴费次数
function initNumber() {
    $("#numberChar").css({
        "height":"300px",
        "padding":"0 10px"
    });
    var myChart = echarts.init(document.getElementById('numberChar'));
    window.onresize = myChart.resize;
    var option = {
        grid:{
            left: 40,
            right: 10
        },
        tooltip: {},
        legend: {
            show : true,
            data:[],//'签约企业数量','合同归档社区数量'
            x : 'center',
            y : 'bottom',
            itemWidth:20,
            itemHeight:12
        },
        xAxis: {
            type : 'category',
            axisLabel: {
                interval: 0,
                formatter:function(value,index){
                    if(value.length % 2!=0){
                        return value;
                    }else{
                        return value;
                    }
                }
            },
            data: [] //"华北片区","东南片区","西南片区","其它"
        },
        yAxis: [{
            type: 'value',
            name: '(次数)',
            axisTick: {
                inside: true
            },
            scale: true,
            axisLabel: {
                formatter: '{value}'
            }
        }
        ],
        series: []
    };
    myChart.setOption(option);
    showNumberData(myChart,option);
}
//缴费次数--数据
function showNumberData(myChart,option) {
    //区域列表
    var areaArr = [];
    //指标列表 例如： ['签约企业数量','合同归档社区数量']
    var legendArr = [],zhibiaoKey = [];
    //series 列表 例如： [15, 20, 26,10]
    var seriesArr = [],valueArr = [];

    var serData = barJSONData;
    if(!checkEmptyOrNull(serData)){
        var maps = new Map();//['jf_shequ_rate':map]
        var titleMap = new Map();
        var map = null;//['华北片区':20]
        for(var j=0;j<serData.length;j++){
            var region_id = serData[j].region_id;     //城市类型 string
            var region_name = serData[j].region_name; //城市名称 string
            var key = serData[j].key;                //业务指标 string 唯一
            var value = serData[j].value;            //数值 number
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
            if(k=="jf_online_times"){
                zhibiaoKey.push(k);//存指标
                legendArr.push(getValueByKey(k));
                valueArr=[];
                titleMap.forEach(function (valTitle, kTitle) {
                    if (val) {
                        valueArr.push(val.get(kTitle));
                    } else {
                        valueArr.push(0);
                    }
                });
                seriesArr.push(valueArr);
            }
        });
    }
    option.legend.data= legendArr;
    option.xAxis.data = areaArr;

    for (i = 0; i < seriesArr.length; i++) {
        var item = {
            name : legendArr[i],
            type : 'bar',
            barGap :0,//设置同一组柱子的间距
            data : seriesArr[i]
        };
        option.series.push(item);
    }
    myChart.setOption(option);
}

//缴费金额
function initAmount() {
    $("#amountChar").css({
        "height":"300px",
        "padding":"0 10px"
    });
    var myChart = echarts.init(document.getElementById('amountChar'));
    window.onresize = myChart.resize;
    var option = {
        color: ["#7356FF"],
        grid:{
            left: 40,
            right: 10
        },
        tooltip: {},
        legend: {
            show : true,
            data:[],//'签约企业数量','合同归档社区数量'
            x : 'center',
            y : 'bottom',
            itemWidth:20,
            itemHeight:12
        },
        xAxis: {
            type : 'category',
            axisLabel: {
                interval: 0,
                formatter:function(value,index){
                    if(value.length % 2!=0){
                        return value;
                    }else{
                        return value;
                    }
                }
            },
            data: [] //"华北片区","东南片区","西南片区","其它"
        },
        yAxis: [{
            type: 'value',
            name: '(RMB)',
            axisTick: {
                inside: true
            },
            scale: true,
            axisLabel: {
                formatter: function (value, index) {
                    if (value >= 10000 && value < 10000000) {
                        value = value / 10000 + "万";
                    } else if (value >= 10000000) {
                        value = value / 10000000 + "千万";
                    }
                    return value;
                }
            }
        }
        ],
        series: []
    };
    myChart.setOption(option);
    showAmountData(myChart,option);
}
//缴费金额--数据
function showAmountData(myChart,option) {
    //区域列表
    var areaArr = [];
    //指标列表 例如： ['签约企业数量','合同归档社区数量']
    var legendArr = [],zhibiaoKey = [];
    //series 列表 例如： [15, 20, 26,10]
    var seriesArr = [],valueArr = [];

    var serData = barJSONData;
    if(!checkEmptyOrNull(serData)){
        var maps = new Map();
        var titleMap = new Map();
        var map = null;
        for(var j=0;j<serData.length;j++){
            var region_id = serData[j].region_id;     //城市类型 string
            var region_name = serData[j].region_name; //城市名称 string
            var key = serData[j].key;                //业务指标 string 唯一
            var value = serData[j].value;            //数值 number
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
            if(k=="jf_online_jine"){
                zhibiaoKey.push(k);//存指标
                legendArr.push(getValueByKey(k));
                valueArr=[];
                titleMap.forEach(function (valTitle, kTitle) {
                    if (val) {
                        valueArr.push(val.get(kTitle));
                    } else {
                        valueArr.push(0);
                    }
                });
                seriesArr.push(valueArr);
            }
        });
    }
    option.legend.data= legendArr;
    option.xAxis.data = areaArr;

    for (i = 0; i < seriesArr.length; i++) {
        var item = {
            name : legendArr[i],
            type : 'bar',
            barGap :0,//设置同一组柱子的间距
            data : seriesArr[i]
        };
        option.series.push(item);
    }
    myChart.setOption(option);
}

//占比
function initZhanbi() {
    $("#zhanbiChar").css({
        "height":"300px",
        "padding":"0 10px"
    });
    var myChart = echarts.init(document.getElementById('zhanbiChar'));
    window.onresize = myChart.resize;
    var option = {
        grid:{
            left: 40,
            right: 10
        },
        tooltip: {},
        legend: {
            show : true,
            data:[],//'签约企业数量','合同归档社区数量'
            x : 'center',
            y : 'bottom',
            itemWidth:20,
            itemHeight:12
        },
        xAxis: {
            type : 'category',
            axisLabel: {
                interval: 0,
                formatter:function(value,index){
                    if(value.length % 2!=0){
                        return value;
                    }else{
                        return value;
                    }
                }
            },
            data: [] //"华北片区","东南片区","西南片区","其它"
        },
        yAxis: [{
            type : 'value',
            name : '占比(%)',
            splitLine: { show: false },//去除网格线
            axisLabel : {
                formatter: '{value}'
            }
        }],
        series: []
    };
    myChart.setOption(option);
    showZhanbiData(myChart,option);
}
//占比--数据
function showZhanbiData(myChart,option) {
    //区域列表
    var areaArr = [];
    //指标列表 例如： ['签约企业数量','合同归档社区数量']
    var legendArr = [],zhibiaoKey = [];
    //series 列表 例如： [15, 20, 26,10]
    var seriesArr = [],valueArr = [];

    var serData = barJSONData;
    if(!checkEmptyOrNull(serData)){
        var maps = new Map();
        var titleMap = new Map();
        var map = null;
        for(var j=0;j<serData.length;j++){
            var region_id = serData[j].region_id;     //城市类型 string
            var region_name = serData[j].region_name; //城市名称 string
            var key = serData[j].key;                //业务指标 string 唯一
            var value = serData[j].value;            //数值 number
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
            if(k=="jf_shequ_rate"){
                zhibiaoKey.push(k);//存指标
                legendArr.push(getValueByKey(k));
                valueArr=[];
                titleMap.forEach(function (valTitle, kTitle) {
                    if (val) {
                        valueArr.push(val.get(kTitle));
                    } else {
                        valueArr.push(0);
                    }
                });
                seriesArr.push(valueArr);
            }
        });
    }
    option.legend.data= legendArr;
    option.xAxis.data = areaArr;

    for (i = 0; i < seriesArr.length; i++) {
        item = {
            name : legendArr[i],
            type : 'line',
            data : seriesArr[i]
        }
        option.series.push(item);
    }
    myChart.setOption(option);
}


//四级页面展示
function showCommunity(data) {
    var communityStr ="";
    var list = data.data;
    number = parseInt((currentPage-1)*pageSize);
    if(list!=null && list!=undefined && list.length > 0){
         for (var i = 0; i < list.length; i++) {
            var project_id = list[i].project_id;              //社区ID
            var project_name = list[i].project_name;          //社区名称
            var propertyinfo_name = list[i].propertyinfo_name;//物业公司
            communityStr +='<div class="item flex-div2" id="'+project_id+'">';
            communityStr +='<div class="tabValue">'+(number+i+1)+'</div>';
            if(propertyinfo_name){
                communityStr +='<div class="tabValue">'+propertyinfo_name+'</div>';
            }else{
                communityStr +='<div class="tabValue">--</div>';
            }
            if(project_name){
                communityStr +='<div class="tabValue">'+project_name+'</div>';
            }else{
                communityStr +='<div class="tabValue">--</div>';
            }
            communityStr +='</div>';
        }
        $(".commText").show();
        $("#communityData").append(communityStr);
        $(".nomore").hide();
        totalCount = data.total;
        currentPage = data.currentPage + 1;
        if(data.totalPage){
            footPage = data.totalPage;
        }else{
            footPage = Math.ceil(totalCount / pageSize);
        }
    }else{
        $(".nomore").text("没有更多了～").show();
    }
}

//点击搜索
function search() {
    $(".commText").hide();
    $("#communityData").html("");
    currentPage = 1;
    getPropertyList();
}
