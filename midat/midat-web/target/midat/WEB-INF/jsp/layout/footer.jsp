<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="footer" class="container-fluid">
    <div class="row">
        <div class="col-md-3 col-sm-6 text-center">
            ${currentJeciApplication.code}
            v.<fmt:message key="build.version" />
        </div>
        <div class="col-md-6 col-sm-6 text-center">
            <fmt:message key="global.browserCompatibility"/>
        </div>
        <div class="col-md-3 col-sm-12 text-center" >
            &copy; 2014-<script type="text/javascript">document.write(new Date().getFullYear());</script>  CSCF - Database and web app powered by SITEL (UniNE)
        </div>
    </div>
</div>
