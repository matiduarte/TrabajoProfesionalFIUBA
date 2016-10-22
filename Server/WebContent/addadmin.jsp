<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en-us">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="bootstrap/img/icono.ico">
    
    <!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Material Design -->
    <link href="bootstrap/css/bootstrap-material-design.css" rel="stylesheet">
    <link href="bootstrap/css/ripples.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
    <link href="bootstrap/css/addStyle.css" rel="stylesheet">
 	
 </head>
  <body>

<jsp:include page="admin.jsp">
		<jsp:param name="title" value="Alta Administradores"/>
 </jsp:include>
<%

		if(request.getAttribute("errormsg") != null){
%>

	<div class="alerta alert alert-danger">
  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  		<strong>Error!</strong> Este DNI ya se ha utilizado. Por favor, introduzca otro.
	</div>
 
 <%

		}
 %> 
 
<form id="identicalForm" class="register" method="post" action="addadmin">
<%if(request.getAttribute("id") != null) {%>
  <input type="hidden" name="id" id="id" value="${id}">
<%} else{%>
	<input type="hidden" name="id" id="id" value="">
<%} %>
  <div class="form-group label-floating">
    <label class="control-label" for="name">Nombre</label>
    <c:choose>
    	<c:when test="${name != NULL}">
 <input class="form-control" id="name" name="name" value="${name}" type="text" required>
  </c:when>
          <c:otherwise>
           <input class="form-control" id="name" name="name" type="text" required>
          </c:otherwise>
   </c:choose>
  
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="lastName">Apellido</label>
    <c:choose>
    	<c:when test="${lastName != NULL}">
  <input class="form-control" id="lastName" name="lastName" type="text" value="${lastName}" required>
  </c:when>
          <c:otherwise>
            <input class="form-control" id="lastName" name="lastName" type="text" required>
            </c:otherwise>
   </c:choose>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="usuario">Nombre de Usuario</label>
    <c:choose>
    	<c:when test="${user != NULL}">
  <input class="form-control" id="user" name="user" value="${user}" type="text" required>
  </c:when>
          <c:otherwise>
            <input class="form-control" id="user" name="user" type="text" required>
            </c:otherwise>
   </c:choose>
  
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="password">Contrase�a</label>
  <input class="pepito form-control" id="password" name="password" oninput="checkLenghtPass(this)" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="passwordconf">Repita Contrase�a</label>
  <input class="form-control" id="passwordconf" name="passwordconf" oninput="checkSamePass(this)" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="lastName">DNI</label>
    <c:choose>
    	<c:when test="${dni != NULL}">
  <input class="form-control" id="dni" name="dni" type="number" value="${dni}" required>
  </c:when>
          <c:otherwise>
            <input class="form-control" id="dni" name="dni" type="number" required>
            </c:otherwise>
   </c:choose>
  </div>
 
  <button class="btn btn-raised btn-danger pull-right" name="finalizar" type="submit">Registrar</button>
  <button class="btn-back btn btn-danger pull-left" onclick="volver()" type="button">Volver</button>
	</form>

	
	
	<script type='text/javascript'>

	function volver(){	
			window.location.href = "/Server/listaMedicos";
	}
	
	function checkLenghtPass(input) {
	    if (input.value.length < 6) {
	        input.setCustomValidity('La contrase�a debe tener al menos 6 caracteres.');
	    } else {
	        input.setCustomValidity('');
	   }
	}
	
	function checkSamePass(input) {
	    if (input.value != document.getElementById('password').value) {
	        input.setCustomValidity('Las contrase�as deben coincidir.');
	    } else {
	        input.setCustomValidity('');
	   }
	}
	
	
	</script>


  </body>
</html>