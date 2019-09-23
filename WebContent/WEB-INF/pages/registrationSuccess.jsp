<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- Temporary Page used to signify a user has successfully registered -->
<section class="clean-block clean-form dark" style="background-color: #444444;">
<div class="container">
<div class="block-heading">
    <h1>User Successfully Registered<br/></h1>
    <p>Created User with info:<br>
    First Name ${user.firstName}<br>
    Last Name ${user.lastName}<br>
    Username ${user.username}<br>
    Password $user.password}<br>
    Email ${user.email}<br>
    Phone Number ${user.phoneNumber}<br>
    </p>
    </div>
    </div>
    </section>
    <td><a href="../login/portal">Log In</a></td>