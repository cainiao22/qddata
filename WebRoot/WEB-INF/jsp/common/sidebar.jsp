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

<li class="nav-item  <c:if test="${ sidebar_menu.parentName eq sidebarMenu.key.name}"> active open</c:if>"><a href="javascript:;"
	class="nav-link nav-toggle"> <i class="${sidebarMenu.key.icon }"></i> <span
		class="title">${sidebarMenu.key.name }</span> 
		 <c:if test="${sidebar_menu.parentName eq sidebarMenu.key.name}">
		<span class="selected"></span> <span
		class="arrow open"></span>
		</c:if>
</a>
	<ul class="sub-menu">
	<c:forEach items="${sidebarMenu.value}" var="subMenu">
		<my:hasAuthority module="${subMenu.path}">
		<li class="nav-item  <c:if test="${ sidebar_menu.name eq subMenu.name}"> active open</c:if>"><a href="${subMenu.path }"
			class="nav-link "> <i class="${subMenu.icon }"></i> <span
				class="title">${subMenu.name }</span>
		</a></li>
		</my:hasAuthority>
		</c:forEach>

	</ul></li>
	</my:hasAuthority>
</c:forEach>





