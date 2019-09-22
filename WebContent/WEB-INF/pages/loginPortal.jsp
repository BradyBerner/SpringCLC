<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
	<h2>Log In</h2>
	<h2 class="warning_label">${failed}</h2>
	<form:form method ="POST" modelAttribute = "user" action="doLogin">
		<table>
			<tr>
				<td><form:label path= "username">Username<br/></form:label></td>
				<td><form:input path = "username"/><form:errors path ="username"/></td>
			</tr>
			<tr>
				<td><form:label path= "password">Password<br/></form:label></td>
				<td><form:password path = "password"/><form:errors path ="password"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type = "submit" value= "Sign In"/>
				</td>
			</tr>
			<tr>
		<td><a href="../registration/portal">Not a member?</a></td>
		</tr>
		</table>
	</form:form>