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
		<jsp:param name="title" value="Alta Turnos"/>
 </jsp:include>
<%
		if(request.getAttribute("errormsg") != null){
%>
	<div class="alerta alert alert-danger">
  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  		<strong>Error!</strong> Este turno ya existe. Por favor, introduzca otro.
	</div>
 <%
		}
 %> 
 
<form id="identicalForm" class="register" method="post" action="medicalShift">
	<%if(request.getAttribute("id") != null) {%>
		<input type="hidden" name="id" id="id" value="${id}">
	<%} else{%>
		<input type="hidden" name="id" id="id" value="">
	<%} %>
	
	<div class="label-floating">
		<label class="control-label" for="doctorId">Doctor</label>
		<br>
		<select class="opcion" id="doctorId" name="doctorId" required>
			<option value="">Ningun Doctor   </option>
			<c:forEach items="${doctors}" var="doctor">
				<c:choose>
					<c:when test="${doctorId != doctor.id}">
		 				<option value="${doctor.id}">
							<c:out value="${doctor.firstName} ${doctor.lastName}"></c:out>
						</option>
					</c:when>
					<c:otherwise>
						<option value="${doctor.id}" selected>
							<c:out value="${doctor.firstName} ${doctor.lastName}"></c:out>
						</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>  
	</div>
	<div class="label-floating">
		<label class="control-label" for="patientId">Paciente</label>
		<br>
		<select class="opcion" id="patientId" name="patientId" required>
			<option value="">Ningun Paciente</option>
			<c:forEach items="${patients}" var="patient">
				<c:choose>
					<c:when test="${patientId != patient.id}">
		 				<option value="${patient.id}">
							<c:out value="${patient.firstName} ${patient.lastName}"></c:out>
						</option>
					</c:when>
					<c:otherwise>
						<option value="${patient.id}" selected>
							<c:out value="${patient.firstName} ${patient.lastName}"></c:out>
						</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>  
	</div>
	
	<div class="form-group">
		<label class="control-label" for="lastName">Fecha</label>
		<c:choose>
			<c:when test="${date != NULL}">
				<input class="form-control" id="date" name="date" type="date" value="${date}" required>
			</c:when>
			<c:otherwise>
				<input class="form-control" id="date" name="date" type="date" required>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div class="form-group">
		<label class="control-label" for="usuario">Hora</label>
		<c:choose>
			<c:when test="${time != NULL}">
				<input class="form-control" id="time" name="time" value="${time}" type="time" required>
			</c:when>
			<c:otherwise>
				<input class="form-control" id="time" name="time" type="time" required>
			</c:otherwise>
		</c:choose>
	</div>
  
	<button class="btn btn-raised btn-danger pull-right" name="finalizar" type="submit">Registrar</button>
	<button class="btn-back btn btn-danger pull-left" onclick="volver()" type="button">Volver</button>
</form>

<script type='text/javascript'>

function volver(){	
		window.location.href = host + "/listaTurnos";
}

</script>

<script src="bootstrap/js/mySlider.js"></script>
</body>
</html>