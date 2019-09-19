<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
  
                                     
<c:if test="${sqlQueryResult.resCode ne 0 }">
<div>
<script type="text/javascript">
$(function(){
	  swal("提示",'出错了！ ', "warning");  
	
});
</script>
${sqlQueryResult.resMsg}
 </div>
</c:if>
<c:if test="${sqlQueryResult.resCode eq 0 }">

<h6>共计行数:<span class="label label-info">${sqlQueryResult.totalCnt }</span>,页面最大显示<span class="label label-info">100</span>行,用时:<span class="label label-info">${sqlQueryResult.timeUsed}</span>秒</h6>
 <p class="col-md-offset-11"><button class="btn btn-success" id="exportBtn">导出全部</button></p>
 <div class="table-scrollable" style="height: 300px;overflow: scroll;">
<c:if test="${not empty sqlQueryResult.resultList }">
 <table class="table  table-bordered  table-hover  " id="t">
	<thead>
	<tr>
	<th>
RowNo.
	</th>
	<c:forEach items="${sqlQueryResult.resultList.get(0) }" var="cell">
	<th>
	${cell }
	</th>
	</c:forEach>

</tr>
	</thead>
	<tbody>
	<c:forEach items="${sqlQueryResult.resultList }" var ="row" begin="1" varStatus="varStatus">
	<tr>
	<td>${varStatus.index }
	</td>
	<c:forEach items="${row }" var="cell"> 
		<td>
	${cell }
	</td>
	</c:forEach>
	
	</tr>
	
	</c:forEach>

	</tbody>
</table> 
</c:if>
</c:if>
       </div>