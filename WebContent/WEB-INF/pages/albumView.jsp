<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container bg-dark" style="min-height: 600px; margin-top:50px;">
    <div class="row" style="min-height: 450px;">
        <div class="col-lg-4" style="width: 285px;">
            <img style="width: 250px;height: 250px;margin-top: 50px;">
            <h6 style="color: white;width: 225px; margin-left: -20px; margin-top: 10px;">
                ${album.tracks.size()}
                <c:choose>
                    <c:when test="${album.tracks.size() > 1}">
                         Songs
                    </c:when>
                    <c:otherwise>
                         Song
                    </c:otherwise>
                </c:choose>
            </h6>
            <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#editAlbum" style="margin-top: 20px;">Edit Album</button><br>
            <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#deleteAlbum" style="margin-top:20px;">Delete Album</button>
        </div>
        <div class="col" style="margin-top: 20px; margin-right:20px;">
            <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addSong" style="float: right;margin-top: 20px;">Add Song</button>
            <%--@elvariable id="album" type="com.gcu.edu.model.AlbumModel"--%>
            <h2 style="margin-top: 10px;color: white;text-align: left; margin-left:-20px;">${album.name}</h2>
            <h2 style="color: white;margin-top: -10px; text-align: left; margin-left:-20px">${album.artist}</h2>
            <c:forEach var="track" items="${album.tracks}" varStatus="count">
                <div class="row" style="height: 35px; <c:if test="${count.count == 1}">margin-top: 25px; border-top:1px solid white;</c:if> border-bottom:1px solid white; text-align: left !important;">
                    <div class="col-lg-1" style="padding-top: 5px; padding-bottom: 5px;">
                        <p style="color: white;">${count.count}</p>
                    </div>
                    <div class="col-lg-10" style="padding-top: 5px; padding-bottom: 5px;">
                        <p style="color: white;">${track.name}</p>
                    </div>
                    <div class="col-lg-1" style="padding-top: 5px; padding-bottom: 5px;">
                        <p style="color: white;">4:17</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<!-- Add Song Modal -->
<div class="modal fade" id="addSong" tabindex="-1" role="dialog" aria-labelledby="addSong" aria-hidden="true" style="color: white;">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: rgb(40, 40, 40)">
                <h5 class="modal-title" id="ModalLabel">Add a Song</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: white; opacity: 0.6;">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" style="background-color: rgb(40, 40, 40)">
                <div class="card" style="background-color: rgb(40, 40, 40)">
                    <%--@elvariable id="song" type="com.gcu.model.song"--%>
                    <form:form method="post" id="addSongForm" modelAttribute="song" action="addSong">
                        <form:hidden path="albumID" value="${album.ID}"/>
                        <div class="form-group">
                            <form:label path="name">Name:</form:label>
                            <form:input path="name" class="form-control item"/><form:errors path="name"/>
                        </div>

                        <div class="form-group">
                            <form:label path="artist">Artist:</form:label>
                            <form:input path="artist" class="form-control item"/><form:errors path="artist"/>
                        </div>
                    </form:form>
                </div>
            </div>
            <div class="modal-footer" style="background-color: rgb(40,40,40)">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button form="addSongForm" class="btn btn-primary" type="submit">Add Song</button>
            </div>
        </div>
    </div>
</div>

<!-- Edit Album Modal -->
<div class="modal fade" id="editAlbum" tabindex="-1" role="dialog" aria-labelledby="editAlbum" aria-hidden="true" style="color: white;">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: rgb(40, 40, 40)">
                <h5 class="modal-title" id="ModalLabel">Edit an Album</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: white; opacity: 0.6;">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" style="background-color: rgb(40, 40, 40)">
                <div class="card" style="background-color: rgb(40, 40, 40)">
                    <%--@elvariable id="album" type="com.gcu.model.AlbumModel"--%>
                    <form:form method="post" id="editAlbumForm" modelAttribute="album" action="edit">
                        <form:hidden path="ID" value="${album.ID}"/>
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
                <button form="editAlbumForm" class="btn btn-primary" type="submit">Edit Album</button>
            </div>
        </div>
    </div>
</div>

<!-- Confirm Deletion Modal -->
<div class="modal fade" id="deleteAlbum" tabindex="-1" role="dialog" aria-labelledby="deleteAlbum" aria-hidden="true" style="color: white;">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: rgb(40, 40, 40)">
                <h5 class="modal-title" id="ModalLabel">Warning!</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: white; opacity: 0.6;">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" style="background-color: rgb(40,40,40);">
                <p>
                    Are you sure that you want to delete this album? This is a permanent action.
                </p>
                <form:form method="post" id="deleteAlbumForm" modelAttribute="album" action="delete">
                    <form:hidden path="ID" value="${album.ID}"/>
                </form:form>
            </div>
            <div class="modal-footer" style="background-color: rgb(40, 40,40)">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button form="deleteAlbumForm" type="submit" class="btn btn-primary">Remove</button>
            </div>
        </div>
    </div>
</div>