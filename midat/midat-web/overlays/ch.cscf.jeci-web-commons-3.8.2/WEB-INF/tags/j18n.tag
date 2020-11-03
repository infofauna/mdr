<%@tag description="Generates a JS script that sets some i18n values to be used by JS."
       pageEncoding="UTF-8"
       body-content="empty"
%>

<%@attribute name="keysPrefix" required="false" rtexprvalue="false"  type="java.lang.String" %>
<%@taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="my"    tagdir="/WEB-INF/tags" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>

<c:set var="currentLang" value="${pageContext.response.locale.language}"/>

<script>
    //Locale : ${currentLang}
    JECI.i18nlang="${currentLang}";

    //create the object that will hold the messages
    JECI.i18nCurrentPageMessages = {};

    //always load the "global" messages
    <c:forEach items="${bundleSource.getMessagesStartingWith('global.', currentLang)}" var="entry">
        $j18n.i18nCurrentPageMessages["${entry.key}"]="${entry.value}";
    </c:forEach>

    //load the messages that starts with the given prefix from the bundle source and write it to javascript
    <c:forEach items="${bundleSource.getMessagesStartingWith(keysPrefix, currentLang)}" var="entry">
        $j18n.i18nCurrentPageMessages["${entry.key}"]="${entry.value}";
    </c:forEach>

</script>