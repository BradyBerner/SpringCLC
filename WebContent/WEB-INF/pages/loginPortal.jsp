<!-- This page handles the login form when a user first tries to access the site -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<section class="clean-block clean-form dark" style="background-color: #444444; padding-top: 10px; height: 90%;">
	<div class="container">
        <div class="block-heading">
           <h2 class="text-warning">Log In</h2>
           <p class="text-warning">Welcome back! Please log in below</p>
        </div>
	<h2 class="warning_label">${failed}</h2>
	<%--@elvariable id="credentials" type="com.gcu.model.CredentialsModel"--%>
	<form:form method ="POST" modelAttribute = "credentials" action="doLogin" style="background-color: #888888;">
				<div class="form-group">
				<form:label path= "username">Username</form:label>
				<form:input path = "username" class="form-control item"/><form:errors path ="username"/>
				</div>
				
				<div class="form-group">
				<form:label path= "password">Password</form:label>
				<form:password path = "password" class="form-control"/><form:errors path ="password"/>
				</div>
				
				<div class="form-group">
				<a href="../registration/portal" class="text-warning">New to Spring CLC?</a>
				</div>
				
				<button class="btn btn-dark btn-block" type="submit">Log In</button>
	</form:form>
	</div>
</section>
               
