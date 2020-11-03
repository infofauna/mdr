<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles"   uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="shiro"   uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="my"      tagdir="/WEB-INF/tags" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>

<tiles:importAttribute name="selectedMenuItem" />
<tiles:importAttribute name="pageCode" />



<div id="menu" class="collapse navbar-collapse">

    <ul class="nav navbar-nav">
        <c:forEach items="${menu.rootMenuElement.children}" var="child">
            <my:menuElement element="${child}"/>
        </c:forEach>

        <li class="pull-right">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                <i class="fa fa-user"></i>
                <shiro:principal property="username"/>
                <b class="caret"></b>
            </a>
            <ul role="menu" class="dropdown-menu">
                <li>
                    <a href="${appRoot}/authentication/logout">Logout</a>
                </li>
            </ul>
        </li>
    </ul>

</div>










