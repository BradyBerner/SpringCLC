<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="features-boxed" style="background-color: rgb(44, 47, 49); padding-top:20px; min-height: 90%;">
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
                                <div id="result" class="box" style="width:350px; height:350px;">
                                    <div class="image"><a href="#"><img class="img-fluid d-block mx-auto" src="${pageContext.request.contextPath}/resources/images/20190920_223758.jpg"></a></div>
                                    <div class="product-name"><a href="#" class="text">${current.name}</a></div>
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

    <div class="modal fade" id="addAlbum" tabindex="-1" role="dialog" aria-labelledby="addAlbum" aria-hidden="true" style="color: white;">
        <div class="modal-dialog modal-md" role="document">
            <div class="modal-content">
                <div class="modal-header" style="background-color: rgb(40, 40, 40)">
                    <h5 class="modal-title" id="ModalLabel">Add an Album</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: white; opacity: 0.6;">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" style="background-color: rgb(40, 40, 40)">
                    <div class="card" style="background-color: rgb(40, 40, 40)">
                        <%--@elvariable id="album" type="com.gcu.model.AlbumModel"--%>
                        <form:form method="post" id="addAlbumForm" modelAttribute="album" action="add">
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
                        </form:form>
                    </div>
                </div>
                <div class="modal-footer" style="background-color: rgb(40,40,40)">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button form="addAlbumForm" class="btn btn-primary" type="submit">Create Album</button>
                </div>
            </div>
        </div>
    </div>
</section>

<%--@elvariable id="error" type="java.lang.String"--%>
<c:if test="${error != null}">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#addAlbum').modal('toggle')
        });
    </script>
</c:if>