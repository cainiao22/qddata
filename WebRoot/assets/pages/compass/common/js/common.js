/**
 * 公共js
 * Created by juyao on 2018/5/11.
 */

//check empty or null or undefined
function checkEmptyOrNull(param) {
  return param === null || param === "null" || param === undefined || param === "undefined" || param === ""
}

//toast弹窗提示
function popEffect(text) {
    var times = 2;
    $('.pubPopup,.pubMask').show();
    $('.pubPopup .popCon').text(text);
    var i = setInterval(function () {
        times--;
        if (times == 1) {
            $('.pubPopup,.pubMask').addClass('popFadeOut');
        }
        if (times == 0) {
            clearInterval(i);
            $('.pubPopup,.pubMask').hide().removeClass('popFadeOut');
            times = 2;
        }
    }, 1000);
}

//关闭弹窗
function hideForm() {
    $(".toastWarp,.toast").hide();
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
//从地址栏获取参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}
//刷新页面
function reloadPage() {
    window.location.reload();
}

/**
 * 返回上一页
 */
function backLastPage() {
    history.back();
}
//返回历史记录上一页，不刷新
function goHistoryPage() {
    var ua = navigator.userAgent.toLowerCase();
    //系统判断
    if (/iphone|ipad|ipod/.test(ua)) {
        //alert('iphone');
        window.location.href = document.referrer;
    } else {
        //alert('android');
        //苹果手机不能识别下面if判断的内容，不会执行后退
        if (history.length == 0 || document.referrer == "") {
            history.go(-1);
        } else {
            history.back();
        }
    }
}

//获取某月有多少天
function getDayCount(year,month) {
  var day = new Date(Number(year),Number(month),0);
  return day.getDate();
}

//数组去重复
function removeDuplicatedItem(arr) {
  for(var i = 0; i < arr.length-1; i++){
    for(var j = i+1; j < arr.length; j++){
      if(arr[i]==arr[j]){
        arr.splice(j,1);//console.log(arr[j]);
        j--;
      }
    }
  }
  return arr;
}

function gohome() {
    window.location.href="index.html";
}

function proxy(str){
    if(location.origin ==='http://qddata.qdingnet.com'){
        return encodeURIComponent("http://yushanfang.bigdata.qdingnet.com"+str);
    }else if(location.origin ==='http://qabigdata.qdingnet.com'){
        return encodeURIComponent("http://qa-yushanfang.bigdata.qdingnet.com"+str);
    }else{
        return encodeURIComponent("http://dev-yushanfang.bigdata.qdingnet.com"+str);
    }
}