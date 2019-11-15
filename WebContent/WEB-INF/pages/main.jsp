<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!-- Main landing page after a login, contains links to other parts of the site -->
<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<section class="clean-block about-us" style="background-color: rgb(35, 35, 35); height:90%; background-image: url(${pageContext.request.contextPath}/resources/images/music-notes-wallpaper-16213-16712-hd-wallpapers.jpg)">
    <div class="container">
        <div class="block-heading">
            <h2 class="text-white" style="color: #ffffff;">Welcome to Our CLC</h2>
                <%--@elvariable id="credentials" type="com.gcu.model.CredentialsModel"--%>
                <c:if test="${sessionScope.principal != null}">
                    <p class="text-white" style="color: #ffffff;">Welcome back, ${sessionScope.principal.getUsername()}!</p>
                </c:if>
                <c:if test="${message != null}">
                    <tiles:insertTemplate template="error.jsp" flush="true"></tiles:insertTemplate>
                </c:if>
        </div>
        <div class="row justify-content-center">
            <div class="col-sm-6 col-lg-4">
                <div class="card clean-card text-center"><img class="card-img-top w-100 d-block" src="${pageContext.request.contextPath}/resources/images/20190920_223758.jpg">
                    <div class="card-body info">
                        <a href="/SpringCLC/library/"><h4 class="card-title">Manage Music</h4></a>
                        <p class="card-text">"Soon" This card will link to a library where users can manage their music</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-lg-4">
                <div class="card clean-card text-center"><img class="card-img-top w-100 d-block" src="${pageContext.request.contextPath}/resources/images/20190920_225323.jpg">
                    <div class="card-body info">
                        <h4 class="card-title">Playlists</h4>
                        <p class="card-text">The real reason this is separate from "Manage Music" is because I wanted three cards here, but somebody will probably come up with a better reason at some point, then we'll go with that. </p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-lg-4">
                <div class="card clean-card text-center"><img class="card-img-top w-100 d-block" src="${pageContext.request.contextPath}/resources/images/20190920_225331.jpg">
                    <div class="card-body info">
                        <h4 class="card-title">Browse and Add Music</h4>
                        <p class="card-text">Like an e-commerce app, but without a shopping cart!</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
