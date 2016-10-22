<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="bootstrap/img/boratti.ico">

    <title>Sanatorio Boratti</title>

   	<!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Material Design -->
    <link href="bootstrap/css/bootstrap-material-design.css" rel="stylesheet">
    <link href="bootstrap/css/ripples.min.css" rel="stylesheet">

	<link href="//fezvrasta.github.io/snackbarjs/dist/snackbar.min.css" rel="stylesheet">
	<link href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet">
 	<meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Custom styles for this template -->
	<link href="bootstrap/css/dashboard.css" rel="stylesheet">
	
	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<!-- <script src="bootstrap/js/bootstrap.min.js"></script> -->
	
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>
	<script src="//fezvrasta.github.io/snackbarjs/dist/snackbar.min.js"></script>
	
	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

  </head>

  <body>
	<div class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <c:choose>
    	<c:when test="${param.title != NULL}">
      <a class="navbar-brand" href="javascript:void(0)"><c:out value="${param.title}"/></a>
      </c:when>
          <c:otherwise>
          <a class="navbar-brand"><strong>Administración</strong></a>
          </c:otherwise>
   </c:choose>
    </div>
    <div class="navbar-collapse collapse navbar-responsive-collapse">
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a href="bootstrap-elements.html" data-target="#" class="dropdown-toggle" data-toggle="dropdown">Listar
            <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="/Server/listaAdministradores">Administradores</a></li>
            <li><a href="/Server/listaMedicos">Médicos</a></li>
            <li><a href="/Server/listaPacientes">Pacientes</a></li>
            <li><a href="/Server/listaEnfermeras">Enfermeras</a></li>
            <li><a href="/Server/listaSecretarias">Secretarias</a></li>
            <li><a href="/Server/listaMedicamentos">Medicamentos</a></li>
            <li><a href="/Server/listaEstudios">Estudios</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="bootstrap-elements.html" data-target="#" class="dropdown-toggle" data-toggle="dropdown">Agregar
            <b class="caret"></b></a>
          <ul class="dropdown-menu">
          <li><a href="#" onclick="addAdmin();return false;">Administradores</a></li>
          <li><a href="#" onclick="addNurse();return false;">Enfermeras</a></li>
          <li><a href="#" onclick="addStudyType();return false;">Estudios</a></li>
          <li><a href="#" onclick="addMedicine();return false;">Medicamentos</a></li>
            <li><a href="#" onclick="addDoctor();return false;">Médicos</a></li>
            <li><a href="#" onclick="addPatient();return false;">Pacientes</a></li>
            <li><a href="#" onclick="addSecretary();return false;">Secretaria</a></li>
          </ul>
        </li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#" onclick="logOut();return false;">Cerrar Sesión</a></li>
      </ul>
    </div>
  </div>
</div>

<script type="text/javascript">
	
	function addAdmin(){
		window.location.href = "/Server/addadmin";
	}
	
	function addDoctor(){
		window.location.href = "/Server/doctor";
	}
	
	function addPatient(){
		window.location.href = "/Server/patient";
	}

	function addNurse(){
		window.location.href = "/Server/nurse";
	}
	
	function addMedicine(){
		window.location.href = "/Server/medicine";
	}
	
	function addStudyType(){
		window.location.href = "/Server/studyType";
	}
	
	function logOut(){
		window.location.href = "/Server/signin";
	}
	
	function addSecretary(){
		window.location.href = "/Server/secretary";
	}
</script>

<script>
  $(function () {
    $.material.init();
    $(".shor").noUiSlider({
      start: 40,
      connect: "lower",
      range: {
        min: 0,
        max: 100
      }
    });

    $(".svert").noUiSlider({
      orientation: "vertical",
      start: 40,
      connect: "lower",
      range: {
        min: 0,
        max: 100
      }
    });
  });
</script> </body>
</html>
