<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="my" uri="authority"%>

<c:forEach items="${sidebarMenuList }" var="sidebarMenu">
	<my:hasAuthority module="${sidebarMenu.key.path}">
		<li>
			<!--a 标签里的 href="#" 去掉了，跳转到其它页面的时候会把左侧列表全展开-->
			<a class="has-arrow" aria-expanded="false"><i class="mdi mdi-widgets">
			</i><span class="hide-menu">${sidebarMenu.key.name } </span></a>
			<ul aria-expanded="false" class="collapse">
				<c:forEach items="${sidebarMenu.value}" var="subMenu">
					<my:hasAuthority module="${subMenu.path}">
						<li>
							<a href=" ${subMenu.path } "
									<c:if test="${subMenu.target}"> target="_blank"</c:if>
							></i>${subMenu.name} </a>
						</li>
					</my:hasAuthority>
				</c:forEach>
			</ul>
		</li>
	</my:hasAuthority>
</c:forEach>
