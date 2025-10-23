<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Home Page</title>

	<style>
		h1 {
			color: blue;
			font-size: 24px;
			font-family: verdana;
			text-align: center;
		}

		img {
			max-width: 70%;
			height: auto;
			padding: 20px;
			display: block; /* Images are inline elements by default, convert to block */
		  	margin-left: auto;
		  	margin-right: auto;
		  	margin-bottom : auto;
		}

		h2 {
			text-align: center;
		}
	</style>
</head>
<body>
	<h1>Spring MVC Configuration Demo</h1>

	<h2>
		Login to continue to menu.
		<br>
		<a href='userLogin'>User Login</a>
	</h2>

	<img src="images/spring-mvc.jpg" alt="Spring MVC"/>
</body>
</html>