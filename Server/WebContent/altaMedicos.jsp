
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
    <link href="bootstrap/css/altaMedicos.css" rel="stylesheet">
 	
 	
 </head>
  <body>

<jsp:include page="admin.jsp"></jsp:include>
 
<form id="identicalForm" class="register" method="post" action="signup">

  <div class="form-group label-floating">
    <label class="control-label" for="name">Nombre</label>
  <input class="form-control" id="name" name="name" type="text" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="lastName">Apellido</label>
  <input class="form-control" id="lastName" name="lastName" type="text" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="usuario">Nombre de Usuario</label>
  <input class="form-control" id="usuario" name="usuario" type="text" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="password">Password</label>
  <input class="pepito form-control" id="password" name="password" oninput="checkLenghtPass(this)" type="password" required>
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="passwordconf">Repita Contraseña *</label>
  <input class="form-control" id="passwordconf" name="passwordconf" oninput="checkSamePass(this)" type="password" required>
  </div>
  
 
  <button class="btn btn-raised btn-danger pull-right" name="finalizar" type="submit">Registrar</button>
  <button class="btn-back btn btn-danger pull-left" onclick="volver()" type="button">Volver</button>
	</form>

	
	
	<script type='text/javascript'>

	function volver(){	
			window.location.href = "/Server/admin.jsp";
	}
	
	function checkLenghtPass(input) {
	    if (input.value.length < 6) {
	        input.setCustomValidity('La contraseña debe tener al menos 6 caracteres.');
	    } else {
	        input.setCustomValidity('');
	   }
	}
	
	function checkSamePass(input) {
	    if (input.value != document.getElementById('password').value) {
	        input.setCustomValidity('Las contraseñas deben coincidir.');
	    } else {
	        input.setCustomValidity('');
	   }
	}
	
	
	</script>


  </body>
</html>