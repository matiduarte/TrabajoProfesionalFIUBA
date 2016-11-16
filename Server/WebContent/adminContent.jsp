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
    <link href="bootstrap/css/home.css" rel="stylesheet">
 	
 	
 </head>
  <body>

<jsp:include page="admin.jsp">
		<jsp:param name="title" value="Home"/>
 </jsp:include>
	
	<div class="container">
		<div class="nameContainer">
			<div class="welcomMessage">
				Bienvenido, ${name}
			</div>
			<%if(System.getenv("DATABASE_URL") != null){%>
				<a class="btn btn-raised btn-danger" href="/listaPacientes">Ver Pacientes</a>
			<%}else{%>
				<a class="btn btn-raised btn-danger" href="/Server/listaPacientes">Ver Pacientes</a>
			<%}%>	
		</div>
		
		<div class="statisticsContainer">
			<div class ="statistic">
				Medicos
				<div class="numberContainer">
					${doctors}
				</div>
			</div> 
			
			<div class ="statistic">
				Enfermeras
				<div class="numberContainer">
					${nurses}
				</div>
			</div> 
			
			<div class ="statistic">
				Pacientes
				<div class="numberContainer">
					${patients}
				</div>
			</div> 
			
			<div class ="statistic">
				Tipos de estudio
				<div class="numberContainer">
					${studyType}
				</div>
			</div> 
		</div>

	</div>
		
	
	
	
	
	<script type='text/javascript'>

	
	
	</script>


  </body>
</html>