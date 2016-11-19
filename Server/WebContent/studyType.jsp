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
		<jsp:param name="title" value="Alta Estudios"/>
 </jsp:include>

 
<form id="identicalForm" class="register" method="post" action="studyType">
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
  
  <button class="btn btn-raised btn-danger pull-right" name="finalizar" type="submit">Registrar</button>
  <button class="btn-back btn btn-danger pull-left" onclick="volver()" type="button">Volver</button>
	</form>

	
	
	<script type='text/javascript'>

	function volver(){	
			window.location.href = host + "/listaEstudios";
	}
	
	
	</script>

<script src="bootstrap/js/mySlider.js"></script>
  </body>
</html>