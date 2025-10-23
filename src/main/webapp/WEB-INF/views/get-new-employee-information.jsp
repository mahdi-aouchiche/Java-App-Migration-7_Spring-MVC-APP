<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Get Employee Information</title>
	
	<link rel='stylesheet'
		href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
	<script
		src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'>
	</script>
	<script
		src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'>
	</script>
	
	<style type="text/css">
		body { 
  			display: grid;
  			place-items: center;
  			min-height: 100vh;
  			margin: 0;
  		}
		
		form {
			height: 400px;
			width: 500px;
			margin: 0 auto;
			padding: 20px;
		}

		table {
			border: 2px solid #ccc;
			width: 100%;
			height: 50%;
		}
		
		thead tr {
			background-color: #ff6347;
		}
		
		th {
			border: 2px solid #ccc;
			padding: 5px;
			text-align: center;
		}
				
		tr {
			height: 40px;
		}
		
		td {
			border: 2px solid #ccc;
			padding: 1px;
			text-align: center;
		}
		
		input {
			height: 40px;
			width: 100%;
			box-sizing: border-box;
			padding: 1px;
			text-align: center;
		}
		
		input:hover {
			background-color: #00fbb0;
		}
		
		.centered-div{
			width: 500px;
			height: 100%;
		}	
	</style>

</head>
<body>
	<div class="container">
		 <div class="pull-right">
		    <a href='optionMenu' class='btn btn-info'>
		    	<span class="glyphicon glyphicon-menu-left"></span>
		    	Go Back to Menu
		    </a>
		    <a href='logout' class='btn btn-danger'>
		    	<span class="glyphicon glyphicon-log-out"></span>
		    	Logout
		    </a>
		 </div>
	</div>
	<br>
	
	<div class="centered-div">
	
		<%-- Display Success Message (Flash Attribute: 'message') --%>
		<c:if test="${not empty successMessage}">
		    <div class="alert alert-success" role="alert" style="text-align: center;">
		        <c:out value="${successMessage}"/>
		    </div>
		</c:if>

		<%-- Display Error Message (Flash Attribute: 'error') --%>
		<c:if test="${not empty errorMessage}">
		    <div class="alert alert-danger" role="alert" style="text-align: center;">
		        <c:out value="${errorMessage}"/>
		    </div>
		</c:if>
		
		<form:form method='post' action='add-new-employee' modelAttribute='employeeData'>
			<table>
				<thead>
					<tr>
						<th>${message}</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><form:input type='text' path='firstname'
							placeholder='Firstname' required='true'/></td>
					</tr>
					<tr>
						<td><form:input type='text' path='lastname' 
							placeholder='Lastname' required='true'/></td>
					</tr>
					<tr>
						<td><form:input type='number' path='age'
							placeholder='Age (between 18 and 100)'
							min='18' max='100' required='true'/></td>
					</tr>
					<tr>
						<td><form:input type='number' path='salary' 
							placeholder='Salary' oninput='doubleValue()' 
							step='0.01' min='0' required='true'/></td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th><input type='submit' value='Add Employee'></th>
					</tr>
				</tfoot>
			</table>
		</form:form>
	</div>
</body>
</html>