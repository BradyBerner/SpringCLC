<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

	<h2>User Successfully logged in</h2>
	<table>
		<tr>
			<th><label>Username</label>
			<th><label>Password</label>
		</tr>
		<tr>
			<td><label>${user.username}</label></td>
			<td><label>${user.password}</label></td>
		</tr>
		<tr>
		<td><a href="../registration/portal">Register a New User</a></td>
		</tr>
	</table>
