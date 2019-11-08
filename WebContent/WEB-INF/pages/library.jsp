<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="features-boxed" style="background-color: #444444; padding-top:20px; height: 90%;">
        <div class="container">
            <div class="intro">
                <h2 class="text-center text-white" style="height: 5px; margin-bottom: 50px">Library:</h2>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addAlbum" style="float:right; margin-bottom: 10px;">Add Album</button>
            </div>
            <%--@elvariable id="message" type="com.gcu.model.MessageModel"--%>
            <div class="row justify-content-center features">
                <c:choose>
                    <%--@elvariable id="library" type="ArrayList<com.gcu.model.AlbumModel>"--%>
                    <c:when test="${library != null}">
                        <c:forEach var="current" items="${library}">
                            <div class="col-sm-6 col-md-5 col-lg-4 item">
                                <div id="result" class="box">
                                    <div class="image"><a href="#"><img class="img-fluid d-block mx-auto" src="${pageContext.request.contextPath}/resources/images/20190920_223758.jpg"></a></div>
                                    <div class="product-name"><a href="#">${current.name}</a></div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${message != null}">
                            <c:choose>
                                <c:when test = "${message.messageType == 0}">
                                    <div class="alert alert-danger" role="alert">${message.message}</div>
                                </c:when>
                                <c:when test="${message.messageType == 1}">
                                    <div class="alert alert-success" role="alert">${message.message}</div>
                                </c:when>
                                <c:when test="${message.messageType == 2}">
                                    <div class="alert alert-warning" role="alert">${message.message}</div>
                                </c:when>
                            </c:choose>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="modal fade" id="addAlbum" tabindex="-1" role="dialog" aria-labelledby="addAlbum" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header" id="darkerStyle">
                    <h5 class="modal-title" id="ModalLabel">Add an Album</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: white; opacity: 0.6;">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="darkerStyle">
                    <div class="card" id="darkerStyle">
                        <%--@elvariable id="album" type="com.gcu.model.AlbumModel"--%>
                        <form:form method="post" modelAttribute="album" action="add" style="background-color: #888888;">
                            <div class="form-group">
                                <form:label path="name">Name:</form:label>
                                <form:input path="name" class="form-control item"/><form:errors path="name"/>
                            </div>

                            <div class="form-group">
                                <form:label path="artist">Artist:</form:label>
                                <form:input path="artist" class="form-control item"/><form:errors path="artist"/>
                            </div>

                            <div class="form-group">
                                <form:label path="description">Description:</form:label>
                                <form:textarea path="description" class="form-control item"/><form:errors path="description"/>
                            </div>

                            <div class="form-group">
                                <form:label path="genre">Genre:</form:label>
                                <form:input path="genre" class="form-control item"/><form:errors path="genre"/>
                            </div>

                            <button class="btn btn-dark btn-block" type="submit">Create Album</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%--@elvariable id="error" type="java.lang.String"--%>
<c:if test="${error != null}">
    <script type="text/javascript">
        $(document).ready(function () {
            $('#addAlbum').modal('toggle')
        });
    </script>
</c:if>