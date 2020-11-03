<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<% response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>

<div class="errorPage">
    <br/>
    <h1 class="alt span-22 prepend-1">Erreur inattendue !</h1>

    <p class="span-20 prepend-2">
        Toutes nos excuses: une erreur inattendue s'est produite sur le serveur. Si cette erreur persiste, veuillez contacter votre administrateur système au plus vite, en incluant les détails ci-dessous.
    </p>

    <h2 class="alt span-22 prepend-1">Détails de l'erreur :</h2>

    <dl class="span-20 prepend-2">
        <dt>Date/heure :</dt>
        <dd><fmt:formatDate value="${exceptionTime}" pattern="dd.MM.yyyy à HH:mm:ss" /></dd>
        <dt>Type d'erreur :</dt>
        <dd>${exception.getClass().getName()}</dd>
        <dt>Message :</dt>
        <dd>${exception.message}</dd>
    </dl>

    <h2 class="alt span-22 prepend-1">Stack trace :</h2>

    <div class="span-22 prepend-1 stackTrace clearfix">
        <pre>
            ${stackTrace}
        </pre>
    </div>
</div>