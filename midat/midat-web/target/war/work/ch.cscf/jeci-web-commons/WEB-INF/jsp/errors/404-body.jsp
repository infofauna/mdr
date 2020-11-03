<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>

<div id="home">
    <div class="span-22 prepend-1">
        <h3 >HTTP 404 Resource Not Found</h3>
    </div>



    <div  class="span-22 prepend-1">
        <hr/>
        <br>
        <p>
            The resource
            <pre>${requestScope['javax.servlet.forward.request_uri']}</pre>
            does not exist.
        </p>
        <br/>
        <p>
            If you reached this page from a link inside the application, please contact your administrator. Thank you.
        </p>
    </div>

</div>
