<%--suppress XmlDuplicatedId --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${message != null}">
    <tiles:insertTemplate template="error.jsp" flush="true"></tiles:insertTemplate>
</c:if>

<div class="container bg-dark" style="min-height: 600px; margin-top:50px;">
    <div class="row" style="min-height: 450px;">
        <div class="col-lg-4" style="width: 285px;">
            <img style="width: 300px;height: 300px;margin-top: 40px;" src="${pageContext.request.contextPath}/resources/images/alb.jpg">
            <h6 style="color: white;width: 225px; margin-left: -50px; margin-top: 10px;">
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
                    <div class="col-lg-1" style="padding-top: 5px; padding-bottom: 5px; margin-left: -15px;">
                        <nobr>
                            <a href="#"><i class="fas fa-plus" style="color: white" data-toggle="tooltip" data-placement="left" title="Add to Playlist"></i></a>
                            <a href="#editSong${track.ID}" data-toggle="modal" data-target="#editSong${track.ID}"><i class="fas fa-cog" style="color: white; margin-top:3px;"></i></a>
                            <a href="#deleteSong${track.ID}" data-toggle="modal" data-target="#deleteSong${track.ID}"><i class="far fa-times-circle" style="color:white;"></i></a>
                        </nobr>
                    </div>
                </div>

                <!-- Edit Song Modal -->
                <div class="modal fade" id="editSong${track.ID}" tabindex="-1" role="dialog" aria-labelledby="editSong" aria-hidden="true" style="color: white;">
                    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background-color: rgb(40, 40, 40)">
                                <h5 class="modal-title" id="ModalLabel">Edit Song</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: white; opacity: 0.6;">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" style="background-color: rgb(40, 40, 40)">
                                <div class="card" style="background-color: rgb(40, 40, 40)">
                                        <%--@elvariable id="song" type="com.gcu.model.song"--%>
                                    <form:form method="post" id="editSongForm${track.ID}" modelAttribute="song" action="editSong">
                                        <form:hidden path="ID" value="${track.ID}"/>
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
                                <button form="editSongForm${track.ID}" class="btn btn-primary" type="submit">Edit Song</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Confirm Song Deletion Modal -->
                <div class="modal fade" id="deleteSong${track.ID}" tabindex="-1" role="dialog" aria-labelledby="deleteSong" aria-hidden="true" style="color: white;">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background-color: rgb(40, 40, 40)">
                                <h5 class="modal-title" id="ModalLabel">Warning!</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: white; opacity: 0.6;">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" style="background-color: rgb(40,40,40);">
                                <p>
                                    Are you sure that you want to delete this song? This is a permanent action.
                                </p>
                                <form:form method="post" id="deleteSongForm${track.ID}" modelAttribute="song" action="deleteSong">
                                    <form:hidden path="ID" value="${track.ID}"/>
                                    <form:hidden path="albumID" value="${album.ID}"/>
                                </form:form>
                            </div>
                            <div class="modal-footer" style="background-color: rgb(40, 40,40)">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <button form="deleteSongForm${track.ID}" type="submit" class="btn btn-primary">Remove</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Add Song to Playlist Modal -->
                <div class="modal fade" id="addToPlaylist${track.ID}" tabindex="-1" role="dialog" aria-labelledby="addToPlaylist${track.ID}" aria-hidden="true" style="color: white;">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background-color: rgb(40, 40, 40)">
                                <h5 class="modal-title" id="ModalLabel">Select a Playlist</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: white; opacity: 0.6;">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" style="background-color: rgb(40,40,40);">
                                <p>
                                    <!-- Put dropdown menu of available playlists here -->
                                </p>
                            </div>
                            <div class="modal-footer" style="background-color: rgb(40, 40,40)">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <button form="deleteSongForm${track.ID}" type="submit" class="btn btn-primary">Add</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<!-- Add Song Modal -->
<div class="modal fade" id="addSong" tabindex="-1" role="dialog" aria-labelledby="addSong" aria-hidden="true" style="color: white;">
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: rgb(40, 40, 40)">
                <h5 class="modal-title" id="ModalLabel">Add Song</h5>
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
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: rgb(40, 40, 40)">
                <h5 class="modal-title" id="ModalLabel">Edit Album</h5>
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

<!-- Confirm Album Deletion Modal -->
<div class="modal fade" id="deleteAlbum" tabindex="-1" role="dialog" aria-labelledby="deleteAlbum" aria-hidden="true" style="color: white;">
    <div class="modal-dialog modal-dialog-centered" role="document">
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

<script src="https://kit.fontawesome.com/b9eea00489.js" crossorigin="anonymous"></script>
<c:if test="${error != null}">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha256-pasqAKBDmFT4eHoN2ndd6lN370kFiGUFyTiUHWhU7k8=" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function() {
            $('#${error}').modal('toggle')
        })
    </script>
</c:if>