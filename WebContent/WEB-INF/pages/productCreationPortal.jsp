<!--  This file handles the registration form when a user signs up for the site -->

<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<section class="clean-block clean-form dark" style="background-color: #444444; padding-top:10px; height: 90%;">
    <div class="container">
        <div class="block-heading">
            <h2 class="text-warning">Create a Product</h2>
        </div>
        <%--@elvariable id="product" type="com.gcu.model.ProductModel"--%>
        <form:form method="post" modelAttribute="product" action="doCreate" style="background-color: #888888;">
            <div class="form-group">
                <form:label path="name">Name:</form:label>
                <form:input path="name" class="form-control item"/><form:errors path="name"/>
            </div>

            <div class="form-group">
                <form:label path="description">Description:</form:label>
                <form:textarea path="description" class="form-control item"/><form:errors path="description"/>
            </div>

            <div class="form-group">
                <form:label path="genre">Genre:</form:label>
                <form:input path="genre" class="form-control item"/><form:errors path="genre"/>
            </div>

            <button class="btn btn-dark btn-block" type="submit">Create Product</button>
        </form:form>
    </div>
</section>

