<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>智器航行舱</title>

  <meta name="format-detection" content="telephone=no,email=no,address=no">
  <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1,minimum-scale=1">
</head>
<body style="margin: 0;padding: 0;">
    <iframe allowfullscreen
            src="https://sc.qdingnet.com/#/bj_ufo/RhnNt0TYa0hbakjJUL8XBKu6m6ik1slEO5uEjKe1WCewkGGKloIfTNUA_Ur8DWylNrLzug0ZOJ3jUoYxJMC2WA==" frameborder="0" width="100%" height="100%" id="main" name="main" class="myIframe">
    </iframe>
</body>
<script>
    // 计算页面的实际高度，iframe自适应会用到
    function calcPageHeight(doc) {
        var cHeight = Math.max(doc.body.clientHeight, doc.documentElement.clientHeight);
        var sHeight = Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight);
        var height  = Math.max(cHeight, sHeight);
        return height;
    }
    //根据ID获取iframe对象
    var ifr = document.getElementById('main');
    ifr.onload = function() {

        var height = calcPageHeight(document);
        if(height < 850){
            height = 850;
        }
        ifr.style.height = height + 'px';
    }
</script>
</html>

