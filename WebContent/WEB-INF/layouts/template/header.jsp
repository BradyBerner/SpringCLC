<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="center">
	<nav class="navbar navbar-light navbar-expand-lg fixed-top text-warning bg-dark border-dark clean-navbar">
        <div class="container"><a class="navbar-brand text-warning logo" href="/SpringCLC/">CLC Title</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div
                class="collapse navbar-collapse" id="navcol-1">
                <ul class="nav navbar-nav text-warning ml-auto">
                    <%--@elvariable id="credentials" type="com.gcu.model.CredentialsModel"--%>
                    <!--TODO: Temporary way for checking if someone is logged in should be replaced by session var-->
                    <c:choose>
                        <c:when test="${sessionScope.principal != null}">
                            <li class="nav-item">
                                <a class="nav-link text-warning" href="/SpringCLC/product/create">Create Product</a>
                            </li>
                            <li class="nav-item" role="presentation"><a href="/SpringCLC/login/signOut" class="btn btn-primary text-warning bg-dark border-dark">Log Out</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item" role="presentation"><a href="/SpringCLC/login/portal" class="btn btn-primary text-warning bg-dark border-dark">Log In</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
</div>