<%@tag description="Recursively renders menu element and, if present, the sub-elements of this element."
       pageEncoding="UTF-8"
       body-content="empty"
        %>

<%@attribute name="element" required="true" rtexprvalue="true" type="ch.cscf.jeci.web.menu.MenuElement" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>

<shiro:hasPermission name="${element.permission}">
    <c:if test="${element.getClass().simpleName eq 'MenuGroup'}">
        <li>
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                <fmt:message key="${element.label}"/>
                <b class="caret"></b>
            </a>
            <ul role="menu" class="dropdown-menu">
                <c:forEach items="${element.children}" var="child">
                    <my:menuElement element="${child}"/>
                </c:forEach>
            </ul>
        </li>
    </c:if>
    <c:if test="${element.getClass().simpleName eq 'MenuItem'}">

        <li>
            <a href="${appRoot}/${element.url}">
                <fmt:message key="${element.label}"/>
            </a>
        </li>
    </c:if>
</shiro:hasPermission>
