<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Jogador</title>
</head>
<body>
<jsp:include page="menu.jsp"/>
	<h1>Jogador:</h1>

	<form action="jogador" method="post">
		<label>ID:</label> 			 	  <input name="id"> <br> 
		<label>Nome:</label> 			  <input name="nome"> <br> 
		<label>Data	de nascimento:</label> <input name="dataNasc"> <br> 
		<label>Altura:</label>			  <input name="altura"> <br> 
		<label>Peso:</label> 			  <input name="peso"> <br>
		
		 <select id="timeSection"  name="time_id">
		 <option name="time_id" disabled="disabled" selected="selected">	Selecione um time</option>
		 <c:if test="${not empty time }">
		 	<c:forEach var="t" items="${time }">
			 	<option name="time_id" value="${t.getCodigo() }">
			 		${t.getNome() } - ${t.getCodigo() }
			 	</option>
		 	</c:forEach>
		 </c:if>
		</select> <br>


		<button type="submit" name="button" value="Insert">Inserir</button>
		<button type="submit" name="button" value="Delete">Deletar</button>
		<button type="submit" name="button" value="Update">Atualizar</button>
		<button type="submit" name="button" value="List">Listar</button>
		<button type="submit" name="button" value="Find">Encontrar</button>
	</form>
	
	 <c:if test="${not empty erro }">
	 	<p>${erro }</p>
	 </c:if>
	 
	  <c:if test="${not empty jogador }">
	 	<p>${jogador }</p>
	 </c:if>

 	<c:if test="${not empty jogadores }">
 	
 	<c:forEach var="jogador" items="${jogadores }">
			 <p>${jogador }</p>
		 	</c:forEach>
	 </c:if>

</body>
</html>