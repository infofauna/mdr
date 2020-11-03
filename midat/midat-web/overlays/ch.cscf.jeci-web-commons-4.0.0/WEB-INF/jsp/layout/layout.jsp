<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles"   uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="my"      tagdir="/WEB-INF/tags" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>
<c:set var="lang"    value="${pageContext.response.locale.language}"/>

<tiles:importAttribute name="baseHref" />
<tiles:importAttribute name="selectedMenuItem" />
<tiles:importAttribute name="pageId" />
<tiles:importAttribute name="pageCode" />
<tiles:importAttribute name="bodyClasses" />

<!doctype html>
<html lang="${lang}">
<head>
    <meta charset="utf-8">
    <title>
        <fmt:message key="global.htmltitle"/>
        - <tiles:insertAttribute name="title" ignore="true" />
    </title>

    <c:if test="${not empty baseHref}">
        <base href="${appRoot}${baseHref}">
    </c:if>

    <!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link rel="icon" type="image/x-icon" href="${appRoot}/resources/images/favicon.ico">

    <link rel="stylesheet" media="all" href="${appRoot}/resources/css/styles.css">
    <link rel="stylesheet" media="all" href="${appRoot}/resources/css/jquery/custom-theme-unine/jquery-ui-1.10.3.custom.min.css">
    <link rel="stylesheet" media="all" href="${appRoot}/resources/css/jqgrid/ui.jqgrid.css">
    <link rel="stylesheet" media="all" href="${appRoot}/resources/css/trNgGrid/trNgGrid-3.0.4.min.css">
    <link rel="stylesheet" media="all" href="${appRoot}/resources/font-awesome-4.7.0/css/font-awesome.min.css">

    <!-- add rzslider css -->
    <link rel="stylesheet" media="all" href="${appRoot}/resources/css/rzslider/rzslider.min.css">
    <link rel="stylesheet" media="all" href="${appRoot}/resources/css/rzslider/custom-style.css">


    <!-- jsGrid css-->
    <link rel="stylesheet" media="all" href="${appRoot}/resources/css/jsGrid/js-grid-1.5.3/jsgrid.min.css">
    <link rel="stylesheet" media="all" href="${appRoot}/resources/css/jsGrid/js-grid-1.5.3/jsgrid-theme.min.css">

    <!-- HTML 5 fix for IE -->
    <!--[if lt IE 9 ]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <link rel="stylesheet" media="screen, projection"   href="${appRoot}/resources/css/blueprint/ie.css">
    <![endif]-->

</head>

<body id="${pageId}" class="${bodyClasses}">

    <nav role="navigation" class="navbar navbar-default navbar-fixed-top" id="header">
        <tiles:insertAttribute name="banner" />

        <tiles:insertAttribute name="menu" >
            <tiles:putAttribute name="selectedMenuItem" value="${selectedMenuItem}"/>
            <tiles:putAttribute name="pageCode" value="${pageCode}"/>
        </tiles:insertAttribute>
    </nav>

    <my:messages />

    <tiles:insertAttribute name="breadcrumb" >
        <tiles:putAttribute name="selectedMenuItem" value="${selectedMenuItem}"/>
        <tiles:putAttribute name="pageCode" value="${pageCode}"/>
    </tiles:insertAttribute>

    <div id="body" class="container">
        <tiles:insertAttribute name="body" />
    </div>

    <tiles:insertAttribute name="footer" />

    <script src="${appRoot}/resources/js/jquery/jquery-1.11.1.min.js"></script>

    <script src="${appRoot}/resources/js/jquery/jquery-ui-1.10.3.custom.min.js"></script>
    <script src="${appRoot}/resources/js/jquery/jquery.ui.datepicker-fr-CH.min.js"></script>
    <script src="${appRoot}/resources/js/jquery/jquery.cookie.js"></script>
    <script src="${appRoot}/resources/js/jquery/jquery.hotkeys.js"></script>
    <script src="${appRoot}/resources/js/jquery/jquery.timers-1.2.js"></script>
    <script src="${appRoot}/resources/js/jquery/jquery.fileupload-9.12.5.js"></script>
    <script src="${appRoot}/resources/js/jquery/jquery.noty.js"></script>
    <script src="${appRoot}/resources/js/jquery/jquery.noty.top.js"></script>
    <script src="${appRoot}/resources/js/jquery/jquery.noty.topCenter.js"></script>
    <script src="${appRoot}/resources/js/jquery/jquery.noty.theme.default.js"></script>
    <script src="${appRoot}/resources/js/jquery/jquery.jqGrid-4.6.0.min.js"></script>
    <script src="${appRoot}/resources/js/jquery/i18n/grid.locale-${lang}.js"></script>

    <script src="${appRoot}/resources/js/jquery/jquery.mustache.js"></script>

    <script src="${appRoot}/resources/js/bootstrap.min.js"></script>
    <script src="${appRoot}/resources/js/mustache/mustache.js"></script>

    <script src="${appRoot}/resources/js/jeci.js"></script>

    <!-- add moment-with-locales + twix -->
    <script src="${appRoot}/resources/js/moment/moment-with-locales.min.js"></script>
    <script src="${appRoot}/resources/js/moment/twix.min.js"></script>



    <script>
        JECI.appRoot="${appRoot}";
        JECI.lang="${lang}";
    </script>

    <script src="${appRoot}/resources/js/lodash/lodash-3.10.1.min.js"></script>

    <script src="${appRoot}/resources/js/j18n.js"></script>
    <script src="${appRoot}/resources/js/jeci.dialog-form.js"></script>
    <script src="${appRoot}/resources/js/jeci.grid.js"></script>

    <script src="${appRoot}/resources/js/angular/angular-1.5.3/angular.min.js"></script>
    <c:if test="${lang == 'de' || lang == 'fr' || lang == 'it'}">
    <script src="${appRoot}/resources/js/angular/angular-1.5.3/i18n/angular-locale_${lang}-ch.js"></script>
    </c:if>
    <script src="${appRoot}/resources/js/angular/angular-1.5.3/angular-sanitize.min.js"></script>
    <script src="${appRoot}/resources/js/angular/angular-1.5.3/angular-route.min.js"></script>

    <script src="${appRoot}/resources/js/angular/trNgGrid/trNgGrid-3.1.4.min.js"></script>
    <script src="${appRoot}/resources/js/angular/trNgGrid/trNgGrid-i18n.js"></script>

    <script src="${appRoot}/resources/js/angular/ui-bootstrap/ui-bootstrap-tpls-1.3.1.min.js"></script>

    <script src="${appRoot}/resources/js/angular/angular-strap-2.2.1/angular-strap.js"></script>
    <script src="${appRoot}/resources/js/angular/angular-strap-2.2.1/angular-strap.tpl.min.js"></script>

    <script src="${appRoot}/resources/js/angular/angular-ui-router-0.2.18/angular-ui-router.min.js"></script>

    <script src="${appRoot}/resources/js/angular/jeci/jeci-filters.js"></script>
    <script src="${appRoot}/resources/js/geoadmin-map/geoadmin-map.js"></script>

    <script src="${appRoot}/resources/js/angular/chart-js-v-2.8.0/chart.js"></script>
    <script src="${appRoot}/resources/js/angular/chart-js-v-2.8.0/angular-chart.js"></script>

    <!-- jsGrid js-->
    <script src="${appRoot}/resources/js/jsGrid/js-grid-1.5.3/jsgrid.min.js"></script>

    <my:j18n keysPrefix="" />

    <tiles:insertAttribute name="appJS" />
</body>
</html>
