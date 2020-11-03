<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="appRoot" value="${pageContext.request.contextPath}"/>

<div id="samples-app-root" ng-app="samplesApp">

    <ul id="breadcrumb" class="breadcrumb ng-cloak" ng-show="pageInfoLoaded" >

        <li>
            <a href="${appRoot}/app/home">
                MIDAT
            </a>
        </li>
        <li>
            <a href="#">
                {{"menu.consultation" | j18n}}
            </a>
        </li>
        <li >
            <a href="{{routeUrl}}">
                {{pageTitle | j18n}}
            </a>
        </li>

        <span id="page-code" class="pull-right">
            {{pageCode}}
        </span>

    </ul>

    <div ng-view></div>

</div>