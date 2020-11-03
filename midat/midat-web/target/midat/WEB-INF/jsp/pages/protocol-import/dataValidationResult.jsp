<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>

<div>

    <h3 class="alt span-15 ">
        <fmt:message key="import.protocol.validationResult.mainHeader"/>
    </h3>

    <p>
        <fmt:message key="import.protocol.validationResult.excelFileName"/>
        ${validationResult.excelFileName},
        <fmt:message key="import.protocol.validationResult.excelSheetName"/>
        ${validationResult.excelSheetName}
        <br/>
        <fmt:message key="import.protocol.validationResult.result"/>
        <c:choose>
            <c:when test="${validationResult.hasValidationPassed()}">
                <c:choose>
                    <c:when test="${validationResult.hasWarnings()}">
                        <fmt:message key="import.protocol.validationResult.resultWarnings"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="import.protocol.validationResult.resultPassed"/>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <fmt:message key="import.protocol.validationResult.resultFailed"/>
            </c:otherwise>
        </c:choose>
    </p>

    <h3>
        <fmt:message key="import.protocol.validationResult.messagesHeader"/>
    </h3>

    <div class="checkbox">
        <label>
            <input type="checkbox" id="show-only-warnings-errors-chk">
            <fmt:message key="import.protocol.validationResult.hideinfo"/>
        </label>
    </div>

    <table class="table table-striped" id="messages-table">
        <thead>
        <tr>
            <th></th>
            <th>
                <fmt:message key="import.protocol.validationResult.severity"/>
            </th>
            <th>
                <fmt:message key="import.protocol.validationResult.message"/>
            </th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${validationResult.messages}" var="message" varStatus="status">
            <c:choose>
                <c:when test="${message.severity.designation == 'error' || message.severity.designation == 'fatal'}">
                    <c:set var="rowStyle" value="danger"/>
                </c:when>
                <c:when test="${message.severity.designation == 'warning'}">
                    <c:set var="rowStyle" value="warning"/>
                </c:when>
                <c:otherwise>
                    <c:set var="rowStyle" value="info"/>
                </c:otherwise>

            </c:choose>
            <tr class="${rowStyle}">
                <td>
                        ${status.count}
                </td>
                <td>
                        ${message.severity.designation}
                </td>
                <td>
                        ${message.message}
                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>

    <c:choose>
        <c:when test="${validationResult.hasValidationPassed()}">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Importation définitive des données
                </div>
                <div class="panel-body">
                    <div class="row">
                    <form:form action="${appRoot}${validationResult.confirmPath}" method="post" id="confirmImportForm">

                        <c:if test="${validationResult.protocolType eq 'LABORATORY' or validationResult.protocolType eq 'MASS'}">
                            <div class="col-md-8 col-sm-12">
                                <div class="form-group">
                                    <select name="publish" class="form-control">
                                        <option value="false" selected >Ne pas encore publier les données (elles ne seront visibles que de vous pour le moment)</option>
                                        <option value="true">Publier les données directement</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <select name="makePublic" class="form-control">
                                        <option value="false" selected >Limiter la disponibilité de ces données au groupe cantonal</option>
                                        <option value="true">Ne pas limiter la disponibilité</option>
                                    </select>
                                </div>
                            </div>
                        </c:if>

                        <div class="col-md-4 col-sm-12 inline-form">

                                <button class="btn btn-primary btn-block pull-right" id="confirm-import-button">
                                    <i class="fa fa-check"></i>
                                    <span class="text">
                                        <fmt:message key="import.protocol.validationResult.confirm"/>
                                    </span>
                                </button>

                                <a href="${appRoot}${validationResult.cancelPath}" class="btn btn-default btn-block pull-right" id="cancel-button">
                                    <i class="fa fa-arrow-left"></i>
                                    <span class="text">
                                        <fmt:message key="import.protocol.validationResult.cancel"/>
                                    </span>
                                </a>
                        </div>
                    </form:form>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>

            <a href="${appRoot}${validationResult.cancelPath}" class="btn btn-default">
                <i class="fa fa-arrow-left"></i>
                <span class="text">
                    <fmt:message key="import.protocol.validationResult.cancel"/>
                </span>
            </a>

        </c:otherwise>
    </c:choose>


</div>