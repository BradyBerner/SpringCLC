<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="message" type="com.gcu.model.MessageModel"--%>

<c:choose>
    <c:when test = "${message.messageType == 0}">
        <div id="dialog" title="Error">
            <div class="alert alert-danger" role="alert">${message.message}</div>
        </div>
    </c:when>
    <c:when test="${message.messageType == 1}">
        <div id="dialog" title="Success!">
            <div class="alert alert-success" role="alert">${message.message}</div>
        </div>
    </c:when>
    <c:when test="${message.messageType == 2}">
        <div id="dialog" title="Warning!">
            <div class="alert alert-warning" role="alert">${message.message}</div>
        </div>
    </c:when>
</c:choose>
<script>
    $(document).ready(function(){
        $("#dialog").dialog();
    })
</script>
