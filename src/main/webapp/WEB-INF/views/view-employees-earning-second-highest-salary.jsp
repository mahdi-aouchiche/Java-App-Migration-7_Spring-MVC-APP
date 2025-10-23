<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Import JSTL core library --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Import JSTL formatting library for currency --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>View Employees</title>
		
		<link rel='stylesheet' 
			 href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css' />
		<script 
			src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js' >
		</script>
		<script 
			src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'>
		</script>
		
		<style>
			table th, td { text-align:center;
			}
		</style>
	</head>
	<body>
		<br>
		<div class="container">
		  	<div class="pull-right">
		    	<%-- Spring uses direct URL mapping for links --%>
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
		
		<div class='container'>
			<br>		
			<%-- Display the information type using EL (Expression Language) --%>
			<h2 style="text-align: center">${informationType}</h2>	
			<table class='table table-bordered table-striped table-hover'>
				<tr>
				<%-- Loop over the columnLabel list using JSTL's <c:forEach> --%>
				<c:forEach var="label" items="${columnLabel}">
					<th>${label}</th>
				</c:forEach>
				</tr>
				
				<%-- Loop over the records list using JSTL's <c:forEach> --%>
				<c:forEach var="employee" items="${records}">
				<tr>
					<%-- Access Employee properties directly using EL --%>
					<td>${employee.id}</td>
					<td>${employee.name}</td>
					<td>${employee.age}</td>	
					<%-- Use JSTL fmt:formatNumber to format the salary as currency --%>
					<td>
						<fmt:formatNumber value="${employee.salary}" type="currency" currencySymbol="$" />
					</td>
				</tr>					
				</c:forEach>
			</table>
		</div>
		<hr>
	</body>
</html>