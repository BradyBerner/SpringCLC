<!--  This file handles the registration form when a user signs up for the site -->

<%@ page contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<section class="clean-block clean-form dark" style="background-color: #444444; padding-top:10px;">
	<div class="container">
		<div class="block-heading">
			<h2 class="text-warning">Registration</h2>
		</div>
		<%--@elvariable id="user" type="com.gcu.model.UserModel"--%>
		<form:form method="POST" modelAttribute = "user" action="doRegistration" style="background-color: #888888;">
			<div class="form-group">
				<form:label path= "firstName">First Name</form:label>
				<form:input path = "firstName" class="form-control item"/><form:errors path ="firstName"/>
			</div>

			<div class="form-group">
				<form:label path= "lastName">Last Name</form:label>
				<form:input path = "lastName" class="form-control item"/><form:errors path ="lastName"/>
			</div>

			<div class="form-group">
				<form:label path= "credentials.username">Username</form:label>
				<form:input path = "credentials.username" class="form-control item"/><form:errors path ="credentials.username"/>
			</div>

			<div class="form-group">
				<form:label path= "credentials.password">Password</form:label>
				<form:password path = "credentials.password" class="form-control item"/><form:errors path ="credentials.password"/>
			</div>

			<div class="form-group">
				<form:label path= "email">Email</form:label>
				<form:input type = "?email?" path = "email" class="form-control item"/><form:errors path ="email"/>
			</div>

			<div class="form-group">
				<form:label path= "phoneNumber">Phone Number</form:label>
				<form:input path = "phoneNumber" class="form-control item"/><form:errors path ="phoneNumber"/>
			</div>

			<div class="form-group">
				<a href="/SpringCLC/login/portal" class="text-warning">Already a member?</a>
			</div>
				<button class="btn btn-dark btn-block" type="submit">Sign Up</button>
		</form:form>
	</div>
</section>
                    
        