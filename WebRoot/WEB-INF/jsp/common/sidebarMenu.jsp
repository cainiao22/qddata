<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="my" uri="authority"%>
<li class="sidebar-toggler-wrapper hide">
	<div class="sidebar-toggler">
		<span></span>
	</div>
</li>
<c:forEach items="${sidebarMenuList }" var="sidebarMenu">
	<my:hasAuthority module="${sidebarMenu.key.path}">
		<li class="has_sub  <c:if test="${ sidebar_menu.parentName eq sidebarMenu.key.name}"> active open</c:if>">
			<a href="javascript:void(0);" class="waves-effect">
				<i class="mdi mdi-layers ${sidebarMenu.key.icon }"></i>
				<span>${sidebarMenu.key.name }</span><span class="pull-right"><i class="mdi mdi-plus"></i></span>
			</a>
			<ul class="list-unstyled">
				<c:forEach items="${sidebarMenu.value}" var="subMenu">
					<my:hasAuthority module="${subMenu.path}">
						<li>
							<a href="${subMenu.path }"> <i class="${subMenu.icon }"></i>${subMenu.name}</a>
						</li>
					</my:hasAuthority>
				</c:forEach>
			</ul>
		</li>
	</my:hasAuthority>
</c:forEach>