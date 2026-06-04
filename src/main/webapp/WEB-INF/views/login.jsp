<%@page import="com.google.protobuf.Message"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login Page</title>
	
	<link rel='stylesheet'
		href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
	<script
		src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
	<script
		src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>
	
	<style>
		body {
			display: grid;
			place-items: center;
			min-height: 100vh;
			margin: 0;
		}
		
		table {
			border:none;
			width: 500px;
			margin: 0;
		}
		
		th {
			text-align: center;
		}
		
		tr {
			height: 40px;
		}
		
		input {
			width: 100%;
			height: 40px;
			box-sizing: border-box;
			padding: 1px;
			text-align: center;
		}
		
		input:hover {
			background-color: #00fbb0;
		}		
	</style>
</head>

<body class='bg-gray-100 flex items-center justify-center min-h-screen'>

	<!-- 
		method: post the user's input 
		action: path to map for the Controller
		modelAttribute: bind the input of this form using loginForm
	-->
	<form:form method='post' action='${pageContext.request.contextPath}/userLogin' modelAttribute="loginForm">
		<table>
			<thead>
				<tr style='border:none; '>
					<th><h2>Enter your login details</h2></th>
				</tr>
			</thead>
			<tbody>
				<tr>	
					<td> <form:input path='username' placeholder='Username'/> </td>
				</tr>
				<tr>
			
					<td> <form:password  path='password' placeholder='Password'/> </td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td>
						<input type='submit' value='Login' required>
					<td>
				</tr>
			</tfoot>
		</table>
		
		<p>${message}</p>
		
	</form:form>

</body>
</html>