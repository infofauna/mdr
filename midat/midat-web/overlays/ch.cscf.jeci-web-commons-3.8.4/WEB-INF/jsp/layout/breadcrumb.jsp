<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles"   uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="shiro"   uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="my"      tagdir="/WEB-INF/tags" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>

<tiles:importAttribute name="selectedMenuItem" />
<tiles:importAttribute name="pageCode" />

<ul id="breadcrumb" class="breadcrumb">
    <c:forEach items="${menu.getMenuElementByName(selectedMenuItem).pathToElement}" var="pathElement" varStatus="s2">
        <li>
            <c:choose>
                <%-- First element is always home --%>
                <c:when test="${s2.first}">
                    <a href="${appRoot}/app/home">
                        <fmt:message key="menu.breadcrumb.root" />
                    </a>
                </c:when>

                <c:otherwise>
                    <%--<span class="separator">></span>--%>
                    <%-- Determine URL of <a> --%>
                    <c:choose >
                        <c:when test="${pathElement.getClass().simpleName eq 'MenuGroup'}">
                            <c:set var="url" value="#"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="url" value="${appRoot}/${pathElement.url}"/>
                        </c:otherwise>
                    </c:choose>

                    <%--Hyperlink --%>
                    <a href="${url}">
                        <fmt:message key="${pathElement.label}" />
                    </a>

                    <%-- Extra nav
                    <c:if test="${pathElement.parent.getClass().simpleName eq 'MenuGroup'}">
                        <ul>
                            <c:forEach items="${pathElement.parent.children}" var="child" >
                                <c:if test="${child ne pathElement and child.getClass().simpleName eq 'MenuItem'}">
                                    <li>
                                        <a href="${appRoot}/${child.url}">
                                            >&nbsp;&nbsp;&nbsp;&nbsp;
                                            <fmt:message key="${child.label}" />
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </c:if>   --%>
                </c:otherwise>
            </c:choose>
        </li>
    </c:forEach>

    <span id="page-code" class="pull-right">
        <tiles:insertAttribute name="pageCode" />
    </span>

</ul>