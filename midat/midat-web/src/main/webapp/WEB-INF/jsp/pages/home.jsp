<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>

<div id="home" class="jumbotron" style="max-width: 800px; margin-left: auto; margin-right: auto;">
    <div class=text-center>

        <h1>
            <fmt:message key="home.welcome"/>
        </h1>

        <h4>
            <fmt:message key="home.subtitle" />
        </h4>

        <div id="home-carousel" class="carousel slide" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#home-carousel" data-slide-to="0" class="active"></li>
                <li data-target="#home-carousel" data-slide-to="1"></li>
                <li data-target="#home-carousel" data-slide-to="2"></li>
                <li data-target="#home-carousel" data-slide-to="3"></li>
                <li data-target="#home-carousel" data-slide-to="4"></li>
                <li data-target="#home-carousel" data-slide-to="5"></li>
                <li data-target="#home-carousel" data-slide-to="6"></li>
                <li data-target="#home-carousel" data-slide-to="7"></li>
                <li data-target="#home-carousel" data-slide-to="8"></li>
                <li data-target="#home-carousel" data-slide-to="9"></li>
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner">
                <div class="item active">
                    <img src="${appRoot}/resources/images/gallery/01.JPG" alt="MIDAT">
                </div>
                <div class="item">
                    <img src="${appRoot}/resources/images/gallery/02.JPG" alt="MIDAT">
                </div>
                <div class="item">
                    <img src="${appRoot}/resources/images/gallery/03.JPG" alt="MIDAT">
                </div>
                <div class="item">
                    <img src="${appRoot}/resources/images/gallery/04.JPG" alt="MIDAT">
                </div>
                <div class="item">
                    <img src="${appRoot}/resources/images/gallery/05.JPG" alt="MIDAT">
                </div>
                <div class="item">
                    <img src="${appRoot}/resources/images/gallery/06.JPG" alt="MIDAT">
                </div>
                <div class="item">
                    <img src="${appRoot}/resources/images/gallery/07.JPG" alt="MIDAT">
                </div>
                <div class="item">
                    <img src="${appRoot}/resources/images/gallery/08.JPG" alt="MIDAT">
                </div>
                <div class="item">
                    <img src="${appRoot}/resources/images/gallery/09.JPG" alt="MIDAT">
                </div>

            </div>

            <!-- Controls -->
            <a class="left carousel-control" href="#home-carousel" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left"></span>
            </a>
            <a class="right carousel-control" href="#home-carousel" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right"></span>
            </a>
        </div>
    </div>

    <p style="margin-top: 50px; font-size: inherit;">
        <fmt:message key="home.intro" />
    </p>

</div>