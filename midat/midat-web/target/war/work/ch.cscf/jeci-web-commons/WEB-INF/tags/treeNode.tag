<%@tag description="Recursively renders a node tag and its subtree. Use on root of tree to display the whole tree. Objects must have 'name', 'children' and 'id' properties."
       pageEncoding="UTF-8"
       body-content="empty"
%>

<%@attribute name="node" required="true" rtexprvalue="true"  type="java.lang.Object" %>
<%@attribute name="nodeIdPrefix" required="false" %>
<%@attribute name="hrefPrefix" required="false" %>
<%@attribute name="linkClasses" required="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>

<c:if test="${empty nodeIdPrefix}" >
    <c:set var="nodeIdPrefix" value="treeNode" />
</c:if>

<li id="${nodeIdPrefix}${node.id}" data-node-id="${node.id}" data-node-label="${node.name}">
    <a href="${hrefPrefix}${node.id}" class="${linkClasses}">
        ${node.name}
    </a>
    <c:if test="${not empty node.children}">
        <ul>
            <c:forEach var="child" items="${node.children}">
                <my:treeNode node="${child}" nodeIdPrefix="${nodeIdPrefix}" hrefPrefix="${hrefPrefix}"/>
            </c:forEach>
        </ul>
    </c:if>
</li>