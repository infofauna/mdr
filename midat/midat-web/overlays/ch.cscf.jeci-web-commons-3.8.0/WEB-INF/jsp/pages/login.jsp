<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>


<div class="col-lg-offset-3 col-lg-6 col-md-6 col-lg-offset-3 col-sm-offset-2 col-sm-8">
    <c:if test="${param['loggedOut']}">
        <div class="callout-info">
            <spring:message code="login.justloggedout" />
        </div>
    </c:if>

    <form:form action="${appRoot}/authentication/login" method="POST" modelAttribute="loginCommand">
        <fieldset>
            <legend>Login</legend>

            <form:errors path="*" element="div" cssClass="errors"/>

            <div class="form-group">
                <label for="username">
                    <spring:message code="login.username"/>
                </label>
                <form:input id="username" path="username" cssClass="form-control input-lg"/>
            </div>

            <div class="form-group">
                <label for="password">
                    <spring:message code="login.password"/>
                </label>
                <form:password id="password" path="password" cssClass="form-control input-lg"/>
            </div>

            <button class="btn btn-primary pull-right" >
                <i class="fa fa-key"></i>
                <span class="text">
                    <spring:message code='login.go'/>
                </span>
            </button>

        </fieldset>

    </form:form>
</div>

<script type="application/javascript">
    window.onload = function() {
        document.getElementById("username").focus();
    };
</script>