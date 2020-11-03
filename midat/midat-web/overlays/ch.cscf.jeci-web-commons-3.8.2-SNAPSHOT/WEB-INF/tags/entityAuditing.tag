<%@tag description=""
       pageEncoding="UTF-8"
       body-content="empty"
        %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="entity" required="true" type="ch.cscf.jeci.domain.entities.base.BaseEntity" %>

<c:set var="dbCreationDate">
    <fmt:formatDate value="${entity.dbCreationDate}" pattern="dd.MM.yyyy HH:mm:ss" />
</c:set>
<c:set var="dbUpdateDate">
    <fmt:formatDate value="${entity.dbUpdateDate}" pattern="dd.MM.yyyy HH:mm:ss" />
</c:set>
<c:set var="appCreationDate">
    <fmt:formatDate value="${entity.dbCreationDate}" pattern="dd.MM.yyyy HH:mm:ss" />
</c:set>
<c:set var="appUpdateDate">
    <fmt:formatDate value="${entity.dbUpdateDate}" pattern="dd.MM.yyyy HH:mm:ss" />
</c:set>

<c:choose>
    <c:when test="${not empty entity.id}">

        <h3>
            <fmt:message key="global.auditing.header" />
            <fmt:message key="global.auditing.header.${entityType}" />
        </h3>
        <div class="auditing">
            <table class="table table-condensed table-bordered table-striped">
                <thead>
                    <tr>
                        <th></th>
                        <th>Date et heure</th>
                        <th>User</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th colspan="3" style="text-align: center">
                            <fmt:message key="global.auditing.db.header" />
                        </th>
                    </tr>

                    <tr>
                        <th width="30%">
                            <fmt:message key="global.auditing.creation"/>
                        </th>
                        <td>
                            ${dbCreationDate}
                        </td>
                        <td>
                            ${entity.dbCreationUser}
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <fmt:message key="global.auditing.update"/>
                        </th>
                        <td>
                            ${dbUpdateDate}
                        </td>
                        <td>
                            ${entity.dbUpdateUser}
                        </td>
                    </tr>
                    <tr>
                        <th colspan="3" style="text-align: center">
                            <fmt:message key="global.auditing.app.header" />
                        </th>
                    </tr>

                    <tr>
                        <th>
                            <fmt:message key="global.auditing.creation"/>
                        </th>
                        <td>
                                ${appCreationDate}
                        </td>
                        <td>
                                ${entity.creationUser.username}
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <fmt:message key="global.auditing.update"/>
                        </th>
                        <td>
                                ${appUpdateDate}
                        </td>
                        <td>
                                ${entity.updateUser.username}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

    </c:when>
    <c:otherwise>
        <div class="auditing">
            This entity has not been persisted to the database yet.
        </div>
    </c:otherwise>
</c:choose>

