<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="banner" class="">
    <h1>
        <fmt:message key="global.headertitle" />
    </h1>
    <div id="languageMenu" class="dropdown">
        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" href="#">
            ${fn:toUpperCase(pageContext.response.locale.language)}
            <b class="caret"></b>
        </button>

        <ul class="dropdown-menu" role="menu">
            <li role="presentation">
                <a role="menuitem" href="?lang=fr"
                        <c:if test="${pageContext.response.locale.language eq 'fr'}" >
                            class="active"
                        </c:if>
                        >
                    FR
                </a>
            </li>
            <li role="presentation">
                <a role="menuitem" href="?lang=de"
                        <c:if test="${pageContext.response.locale.language eq 'de'}" >
                            class="active"
                        </c:if>
                        >
                    DE
                </a>
            </li>
            <li role="presentation">
                <a role="menuitem" href="?lang=it"
                        <c:if test="${pageContext.response.locale.language eq 'it'}" >
                            class="active"
                        </c:if>
                        >
                    IT
                </a>
            </li>
            <li role="presentation">
                <a role="menuitem" href="?lang=en"
                        <c:if test="${pageContext.response.locale.language eq 'en'}" >
                            class="active"
                        </c:if>
                        >
                    EN
                </a>
            </li>
        </ul>
    </div>

</div>

