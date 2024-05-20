<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Time</title>
</head>
<body>
<jsp:include page="menu.jsp"/>
<h1>Time:</h1>

	<form action="time" method="post">
	<label>Codigo:</label> <input name="codigo"> <br>
		<label>Nome:</label> <input name="nome"> <br>
		<label>Cidade</label> <input name="cidade"> <br> 

		

		<button type="submit" name="button" value="Insert">Inserir</button>
		<button type="submit" name="button" value="Delete"> Deletar</button>
		<button type="submit" name="button" value="Update">Atualizar</button>
		<button type="submit" name="button" value="List">Listar</button>
		<button type="submit" name="button" value="Find">Encontrar</button>
	</form>
	
	
	<c:if test="${not empty erro }">
	<p>${erro }</p>
	</c:if>
	
	
	  <c:if test="${not empty time }">
	 	<p>${time }</p>
	 </c:if>

 	<c:if test="${not empty times }">
 	
 	<c:forEach var="time" items="${times }">
			 <p>${time }</p><br>
		 	</c:forEach>
	 	
	 </c:if>

</body>
</html>