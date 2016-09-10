<%@ page import="java.lang.*" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="bootstrap/img/boratti.ico">

    <title>Iniciar Sesion</title>

    <!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Material Design -->
    <link href="bootstrap/css/bootstrap-material-design.css" rel="stylesheet">
    <link href="bootstrap/css/ripples.min.css" rel="stylesheet">

	<link href="//fezvrasta.github.io/snackbarjs/dist/snackbar.min.css" rel="stylesheet">
 	<meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Custom styles for this template -->
    <link href="bootstrap/css/signin.css" rel="stylesheet">


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>



  <body>
  
  <%

		if(request.getAttribute("errorUser") != null){
%>

	<div class="alerta-error alert alert-danger">
  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  		<strong>Error!</strong> El usuario no se encuentra registrado. Por favor, intente nuevamente.
	</div>
 
 <%
			
		}
%>

 <%

		if(request.getAttribute("errorPass") != null){
%>

	<div class="alerta-error alert alert-danger">
  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  		<strong>Error!</strong> Contrase�a incorrecta.Por favor, intente nuevamente.
	</div>
 
 <%
			
		}
%>
  
  
  	<img src="bootstrap/img/icono.png" class="logo" alt="Logo" style="width:250px;height:189px;"> 
    <div class="container">
    
		<form name="loginForm" method="post" action="signin" class="form-signin">
		<div class="form-group label-floating">
    	<label class="control-label" for="user">Usuario</label>
 		<input class="form-control" id="user" name="user" type="text" required>
  		</div>
 		 <div class="form-group label-floating">
    	<label class="control-label" for="password">Contrase�a</label>
  		<input class="form-control" id="password" name ="password" type="password" required>
  		</div>
		
		<button class="btn btn-lg btn-raised btn-primary btn-block" type="submit">Iniciar Sesi�n</button>
			
      </form>

	
    </div> <!-- /container -->


	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/ripples.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>

	<script src="//cdnjs.cloudflare.com/ajax/libs/noUiSlider/6.2.0/jquery.nouislider.min.js"></script>
	<script src="bootstrap/js/floating-label.js"></script>
	<script>
	
	function recuperarContrasenia(){	
		window.location.href = "/Server/recuperarPass";
	}
	
	
	</script>

  </body>
</html>

