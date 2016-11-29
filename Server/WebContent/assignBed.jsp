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
	<jsp:param name="title" value="Asignar cama"/>
</jsp:include>

<form id="identicalForm" name="identicalForm" class="register" method="post" action="vistaPisos">
	<input type="hidden" name="id" id="id" value="${floorId}">
	<input type="hidden" name="bedId" id="bedId" value="${bedId}">
	<div class="">
		<label class="control-label" for="name">Paciente</label>
		<select id="patient" name="patient">
			<option value="0">Ningun Paciente</option>
			<c:forEach items="${users}" var="user">
				<option value="${user.id}">
					<c:out value="${user.firstName} ${user.lastName}"></c:out>
				</option>
			</c:forEach>
		</select>  
	</div>
	<button class="btn btn-raised btn-danger pull-right" name="finalizar" type="submit">Registrar</button>
	<button class="btn-back btn btn-danger pull-left" onclick="volver()" type="button">Volver</button>
</form>
<script type='text/javascript'>

function volver(){	
	window.location.href = "vistaPisos?id=" + '${floorId}';
}

</script>
</body>
</html>