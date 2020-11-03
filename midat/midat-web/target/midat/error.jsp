<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>
<jsp:useBean id="date" class="java.util.Date" />

<!doctype html>

<html lang="fr">

<head>
    <meta charset="utf-8">
    <title>Tango - Erreur !</title>

    <link rel="stylesheet" media="screen, projection"   href="${appRoot}resources/css/blueprint/screen.css">
    <link rel="stylesheet" media="screen, projection"   href="${appRoot}/resources/css/blueprint/fancy.css">

</head>

<body class="error">
    <h1 >Erreur</h1>
    <p>
        Toutes nos excuses: une erreur inattendue s'est produite sur le serveur. Si cette erreur persiste, veuillez contacter votre administrateur système au plus vite, en incluant les détails ci-dessous.
    </p>

    <dl>
        <dt>URI de la requête :</dt>
        <dd><c:out value="${requestScope['javax.servlet.forward.request_uri']}" /></dd>
        <dt>Date/heure :</dt>
        <dd><fmt:formatDate value="${date}" pattern="dd.MM.yyyy à HH:mm:ss" /></dd>
        <dt>Type d'erreur :</dt>
        <dd><c:out value="${requestScope['javax.servlet.error.exception']}" /></dd>
        <dt>Message :</dt>
        <dd><c:out value="${requestScope['javax.servlet.error.message']}" /></dd>
        <dt>HTTP status code :</dt>
        <dd><c:out value="${requestScope['javax.servlet.error.status_code']}" /></dd>
    </dl>

</body>

</html>