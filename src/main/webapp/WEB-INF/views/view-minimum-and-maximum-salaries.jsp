<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Import JSTL formatting library for currency --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Minimum And Maximum Salaries</title>
		
		<link rel='stylesheet' 
			 href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css' />
		<script 
			src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js' >
		</script>
		<script 
			src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'>
		</script>
	</head>
	<body>
		<br>
		<div class="container">
		  	<div class="pull-right">
		    	<a href='optionMenu' class='btn btn-info'>
		    		<span class="glyphicon glyphicon-menu-left"></span>
		    		Go Back to Menu
		    	</a>
		    	<a href='logout' class='btn btn-danger'>
		    		<span class="glyphicon glyphicon-log-out">
		    		</span>
		    		Logout
		    	</a>
		  	</div>
		</div>
		<br>
		<div class='container'>		
			<br>
			<%-- Display the information type using EL (Expression Language) --%>
			<h2 style="text-align: center">${informationType}</h2>		
			<table class='table table-bordered table-striped table-hover'>
				<tr>
					<th style='text-align:center'>Minimum Salary</th>
					<th style='text-align:center'>Maximum Salary</th>
				</tr>
				<tr>
					<td style='text-align:center'>
						<%-- Use JSTL fmt:formatNumber to format the salary as currency --%>
						<fmt:formatNumber value="${minSalary}" type="currency" currencySymbol="$" />
					</td>
					<td style='text-align:center'>
						<%-- Use JSTL fmt:formatNumber to format the salary as currency --%>
						<fmt:formatNumber value="${maxSalary}" type="currency" currencySymbol="$" />
					</td>
				</tr>		
			</table>
		</div>
	</body>
</html>