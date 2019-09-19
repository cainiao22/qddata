<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>北斗星-大数据中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
</head>
<!-- END HEAD -->
<body style="margin: 0;padding: 0">
<c:import url="common/importJs2.jsp"></c:import>
<script type="text/javascript">
    $(document).ready(function () {
        window.location ="${dataArmorHost}/${requestDataArmorUrl}?username=${username }&qdUserId=${userId}&qdArmorToken=${qdArmorToken}&userId=1"
    });
</script>

</body>


</html>
