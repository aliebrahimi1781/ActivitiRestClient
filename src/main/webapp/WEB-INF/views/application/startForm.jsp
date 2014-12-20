<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Pregled prijave</title>
<link href="<c:url value="/resources/stylesheets/styles.css"/>"
	rel="stylesheet" />

</head>
<body>

<h2>Pokretanje nove instance procesa</h2>

<c:if test="${fn:length(formProperties) > 0}">
<form name='f' action="<c:url value='/application/startNewInstance' />"method='POST'>

<fieldset>

<!-- 
Treba jos dodati validaciju (da li su uneta required obelezja, 
da li su uneta slova iako je tip long - ili obezbediti da se to onemoguci
Pokriti unos datuma
Pokusati smestiti u poseban jsp fajl forme, pa ukljuciti pomocu include
 -->
<c:forEach var="formProperty" items="${formProperties}">

	<c:if test="${formProperty.readable == true}">
	<label>${formProperty.name}</label>

	
	<c:if test="${formProperty['type'].equals('string') || formProperty['type'].equals('long')}">
	<input type="text" <c:if test="${formProperty['writable']==true}"> name="${formProperty['id']}"</c:if> 
	    <c:if test="${formProperty['writable']==false}"> disabled </c:if> }" />
	</c:if>
	
	<c:if test="${formProperty['type'].equals('boolean')}">
		<input type="checkbox" <c:if test="${formProperty['writable']==true}"> name="${formProperty['id']}"</c:if> 
		 <c:if test="${formProperty['writable']==false}"> disabled </c:if> />
	</c:if>
	
	<!--
	<c:if test="${formProperty['type'].equals('enum')}">
	<select <c:if test="${formProperty.writable==true}"> name="${formProperty.id}"</c:if>
	  <c:if test="${formProperty.writable==false}"> disabled </c:if> >
	  
	<c:forEach var="key" items="${formProperty.getType().getInformation('values').keySet()}">
		<option value="${key}">${formProperty.getType().getInformation('values').get(key)}</option>
	</c:forEach>
	
	
	
	</select>
	</c:if>
	-->
	
	</c:if>
	<c:if test="${formProperty['readable'] == false}">
	<input type="hidden"  name="${formProperty['id']}" />
	</c:if>
	<br/>
</c:forEach>
 <div class="submit">
	<input type="submit" value="Potvrda">
</div>

</fieldset>
</form>

</c:if>

<div class="home">
<a href="${pageContext.request.contextPath}/application/welcome">Početna</a>
</div>

	
</body>
</html>


