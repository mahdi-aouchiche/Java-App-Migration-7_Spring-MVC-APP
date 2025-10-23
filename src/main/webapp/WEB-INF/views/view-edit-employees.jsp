<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>View And Edit Employees</title>
	
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
    </script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js">
    </script>
    
    <style type="text/css">
   		.action-icons img {
			margin: 0 5px;
			cursor: pointer;
			width: 30px;
			height: 25px;
		}
		
		.add-image {
			margin: 0 10px;
			cursor: pointer;
			width: 50px;
			height: 50px;
		}
		.action-icons {
			display: flex;
			justify-content: space-around;
			align-items: center;
		}
 		.edit-form-row {
			display: none;
		}
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
			<a href='add-new-employee' class='btn btn-primary'>
				<span class="glyphicon glyphicon-add-button"></span>
				Add New Employee
			</a>
	    	<a href='logout' class='btn btn-danger'>
	    		<span class="glyphicon glyphicon-log-out"></span>
	    		Logout
	    	</a>
	  	</div>
	</div>
	<div class="container">
		<br>
		
		<%-- Use Expression Language to display model attribute --%>
		<h2 style="text-align: center">${informationType}</h2>

		<%-- Display Success Message (Flash Attribute: 'message') --%>
		<c:if test="${not empty message}">
		    <div class="alert alert-success" role="alert" style="text-align: center;">
		        <c:out value="${message}"/>
		    </div>
		</c:if>

		<%-- Display Error Message (Flash Attribute: 'error') --%>
		<c:if test="${not empty error}">
		    <div class="alert alert-danger" role="alert" style="text-align: center;">
		        <c:out value="${error}"/>
		    </div>
		</c:if>
		
		<table class="table table-bordered table-striped table-hover">
			<%-- Table Header: Use JSTL <c:forEach> to iterate over tableHeader list --%>
			<thead>
				<tr>
					<c:forEach var="label" items="${tableHeader}">
						<th style='text-align: center'><c:out value="${label}"/></th>
					</c:forEach>
				</tr>
			</thead>

			<%-- Table Body: Use JSTL <c:forEach> to iterate over employeeList --%>
			<tbody>
				<c:forEach var="employee" items="${employeeList}">
				
				<%-- Row with Employee Data --%>
				<tr>
					<td style='text-align: center'>${employee.id}</td>
					<td style='text-indent: 25%'>${employee.name}</td>
					<td style='text-align: center'>${employee.age}</td>
					<%-- Use JSTL fmt:formatNumber for currency formatting --%>
					<td style='text-indent: 25%'>
					    <fmt:formatNumber value="${employee.salary}" type="currency"/>
					</td>
					<td>
						<div class="action-icons">
							<%-- Delete Icon: Use EL for URL construction --%>
							<c:url var="deleteURL" value="delete-an-employee">
							    <c:param name="employeeId" value="${employee.id}"/>
							    <c:param name="returnURL" value="${returnURL}"/>
							</c:url>
							<img src='images/delete.png' title='delete' 
								 onclick="window.location.href='${deleteURL}'">
	
							<%-- Edit Icon --%>
							<img src="images/edit.png" title="edit" 
								 onclick="showUpdateForm(${employee.id})">
						</div>
					</td>
				</tr>

				<%-- Hidden Row with Edit Form --%>
				<tr class="edit-form-row" id="edit-row-${employee.id}">
					
					<%-- Form Action URL: Use JSTL <c:url> for clean URL building --%>
					<c:url var="actionURL" value="update-employee">
					    <c:param name="id" value="${employee.id}"/>
					    <c:param name="returnURL" value="${returnURL}"/>
					</c:url>
					<form:form action="${actionURL}" method="post">
						<td style='text-align: center'><input type="hidden" name="id" 
							value="${employee.id}">${employee.id}</td>
						<td><input type="text" name="name" class="form-control"
							value="${employee.name}" placeholder="Full Name"
							style="text-indent: 25%" required></td>
						<td><input type="number" name="age" class="form-control"
							value="${employee.age}" placeholder="Age" min="18"
							max="100" style='text-align: center' required></td>
						<td><input type="number" name="salary" class="form-control"
							oninput='doubleValue()' min='0' step="0.01"
							value="<fmt:formatNumber value='${employee.salary}' pattern='0.00'/>"
							step="0.01" placeholder="Salary" style="text-indent: 25%"
							required></td>
						<td style='display: flex;
						justify-content: space-around; align-items: center;'>
							<button type="button" class="btn btn-warning"
								onclick="showUpdateForm(${employee.id})">Cancel</button>
							<button type="submit" class="btn btn-success">Update</button>	
						</td>
					</form:form>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<a href='add-new-employee'>
			<img class="add-image" src="images/add.png" 
				 alt="Add Employee" title = "add new employee"/>
		</a>
		
	</div>
	<hr>
		
	<script>
		function showUpdateForm(id) {
			var element = document.getElementById('edit-row-' + id);
			if (element.style.display === 'none' || element.style.display === '') {
				element.style.display = 'table-row';
			} else {
				element.style.display = 'none';
			}
		}
	</script>
</body>
</html>