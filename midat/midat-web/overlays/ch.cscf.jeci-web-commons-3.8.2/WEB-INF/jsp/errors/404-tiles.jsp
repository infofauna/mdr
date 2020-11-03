<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="CommonGUIMessages"  scope="request" />
<fmt:setBundle basename="${currentJeciApplication.code}-GUIMessages"  scope="request" />

<tiles:insertDefinition name="404" />