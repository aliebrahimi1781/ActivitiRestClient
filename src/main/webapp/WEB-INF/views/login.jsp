<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>Login</title>


<link href="<c:url value="/resources/stylesheets/styles.css"/>"
	rel="stylesheet" />

<link href="<c:url value="/resources/stylesheets/reset.css" />"
	rel="stylesheet">

<link href="<c:url value="/resources/stylesheets/animate.css" />"
	rel="stylesheet">

</head>

<body>


	<div id="container">

		<form name='f' action="<c:url value='j_spring_security_check' />"
			method='POST'>

			<c:if test="${not empty error}">
				<div class="errorblock">
				Your login attempt was not successful, try again.<br /> Caused :
				${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
				</div>
			</c:if>


			<label for="name">Koriničko ime:</label> <input type="text"
				name='j_username' value=''> 
			<label for="password">Lozinka:</label>

			<input type="password" name='j_password'>

			<div class="upperLine">
				<input type="submit" class="login" value="Login">
			</div>


		</form>

	</div>



</body>

</html>






