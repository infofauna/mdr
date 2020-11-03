<%@tag description="Renders application messages of type success, error, notice and info. An instance of ch.cscf.jeci.web.tools.Messages must be present in the page context."
       pageEncoding="UTF-8"
       body-content="empty"
        %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="jeciMessages" style="display: none">

    <c:if test="${not empty messages}">

        <c:if test="${not empty messages.success}">
            <div class="message success"><ul>
                <c:forEach items="${messages.success}" var="successMessage">
                    <li>
                        <fmt:message key="${successMessage}" />
                    </li>
                </c:forEach>
            </ul></div>
        </c:if>

        <c:if test="${not empty messages.error}">
            <div class="message error"><ul>
                <c:forEach items="${messages.error}" var="errorMessage">
                    <li>
                        <fmt:message key="${errorMessage}" />
                    </li>
                </c:forEach>
            </ul></div>
        </c:if>

        <c:if test="${not empty messages.notice}">
            <div class="message notice"><ul>
                <c:forEach items="${messages.notice}" var="noticeMessage">
                    <li>
                        <fmt:message key="${noticeMessage}" />
                    </li>
                </c:forEach>
            </ul></div>
        </c:if>

        <c:if test="${not empty messages.info}">
            <div class="message info"><ul>
                <c:forEach items="${messages.info}" var="infoMessage">
                    <li>
                        <fmt:message key="${infoMessage}" />
                    </li>
                </c:forEach>
            </ul></div>
        </c:if>

    </c:if>

</div>
