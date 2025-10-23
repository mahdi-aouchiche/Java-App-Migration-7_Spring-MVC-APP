<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Import JSTL core library for looping and conditionals --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Import JSTL formatting library for currency --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Average Salary Of Each Department</title>
		
		<link rel='stylesheet' 
			href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
		<script 
			src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'>
		</script>
		<script 
			src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'>
		</script>
		<style>
			th { text-align:center; }
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
			
			<table class='table table-bordered table-striped table-hover'>
				<tr>
					<%-- Loop over the columnLabel list using JSTL's <c:forEach> --%>
					<c:forEach var="label" items="${columnLabel}">
						<th>${label}</th>
					</c:forEach>
				</tr>
			
				<%-- Loop over the Map entries using JSTL's <c:forEach> --%>
				<%-- The 'var' attribute creates an Entry object, which has
				 'key' (Department) and 'value' (Double salary) properties --%>
				<c:forEach var="entry" items="${records}">
				<tr>
					<td style='text-align:center'>${entry.key.id}  </td>
					<td style='text-indent: 20%'> ${entry.key.name}</td>	
					<td style='text-indent: 40%'>
						<%-- Use JSTL fmt:formatNumber to format the salary as currency --%>
						<fmt:formatNumber value="${entry.value}" type="currency" currencySymbol="$" />
					</td>
				</tr>					
				</c:forEach>
			</table>
		</div>
		<hr>
	</body>
</html>