<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Import JSTL core library for looping and conditionals --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Departments With At Least A Given Number Of Employees</title>
	
		<link rel='stylesheet' 
			href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
		<script 
			src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
		<script 
			src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>
		<style>
			table th, td { text-align:center; }
		</style>
	</head>
	<body>
		<br>
		<div class="container">
		  	<div class="pull-right">
		    	<a href='optionMenu' class='btn btn-info'>
		    		<span class="glyphicon glyphicon-menu-left"></span>
		    		Go Back to Menu
		    	</a>
		    	<a href='create-new-department' class='btn btn-primary'>
		    		<span class="glyphicon glyphicon-add-button"></span>
		    		Add New Department
		    	</a>
		    	<a href='logout' class='btn btn-danger'>
		    		<span class="glyphicon glyphicon-log-out"></span>
		    		Logout
		    	</a>
		  	</div>
		</div>
		<div class='container'>
			<br>
			<%-- Display the information type using EL --%>
			<h2 style="text-align: center">${informationType}</h2> 
			
			<table  class='table table-bordered table-striped table-hover'>
				<tr>
					<%-- Loop over the columnLabel list using JSTL's <c:forEach> --%>
					<c:forEach var="label" items="${columnLabel}">
						<th>${label}</th>
					</c:forEach>
				</tr>
				
				
				<%-- Loop over the Map entries using JSTL's <c:forEach> --%>
				<%-- The 'var' attribute creates an Entry object, which has 'key' (Department) and 'value' (Integer count) properties --%>
				<c:forEach var="entry" items="${departmentList}">
				<tr>
					<%-- Access the Department ID via entry.key.id --%>
					<td>${entry.key.id}</td> 
					
					<%-- Access the Department Name via entry.key.name --%>
					<td>${entry.key.name}</td>
					
					<%-- Access the Employee Count via entry.value --%>
					<td>${entry.value}</td>
				</tr>					
				</c:forEach>
			</table>
		</div>
		<hr>
	</body>
</html>