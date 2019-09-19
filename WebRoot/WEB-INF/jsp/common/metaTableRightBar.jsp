<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

                            <c:if test="${ !empty  metaTable}">
                       <div class="page-toolbar">
                                <div class="btn-group pull-right">
                                    <button type="button" class="btn green btn-sm btn-outline dropdown-toggle" data-toggle="dropdown" aria-expanded="false">切换
                                        <i class="fa fa-angle-down"></i>
                                    </button>
                                    <ul class="dropdown-menu pull-right" role="menu">
                                        <li>
                                            <a href="metaTableManage?id=${metaTable.id }">
                                              基本信息</a>
                                        </li>
                                        <li>
                                            <a href="metaFieldList?tableId=${metaTable.id }">
                                            字段信息</a>
                                        </li>
                                        <li>
                                            <a href="metaTableMonitorList?id=${metaTable.id }">
                                              预警规则</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            </c:if>
                        