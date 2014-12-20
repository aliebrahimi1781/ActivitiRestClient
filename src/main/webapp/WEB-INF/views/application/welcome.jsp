<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>Welcome</title>



<link href="<c:url value="/resources/stylesheets/styles.css"/>"
	rel="stylesheet" />


</head>

<body>
	<h3>Dobrodošli ${username}!</h3>
		
	<div class="message">${message}</div>		
		
	
	<a href="./tasksList">Pregled zadataka</a> <br/>
	<a href="./newInstance">Pokretanje nove instance</a> <br/>
	
	
	<br/>
	<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
	
</body>
</html>