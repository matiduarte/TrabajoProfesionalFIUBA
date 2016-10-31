<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="icon" href="bootstrap/img/icono.ico">
	
	<!-- Bootstrap -->
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- 	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"> -->
	<!-- Bootstrap Material Design -->
	<link href="bootstrap/css/bootstrap-material-design.css" rel="stylesheet">
	<link href="bootstrap/css/ripples.min.css" rel="stylesheet">
	
	
	<!-- Custom styles for this template -->
	<link href="bootstrap/css/addStyle.css" rel="stylesheet">
	
	<title>Nuevo piso</title>
</head>
<body>
<jsp:include page="admin.jsp">
		<jsp:param name="title" value="Alta Piso"/>
 </jsp:include>

<form id="identicalForm" name="identicalForm" class="register" method="post" action="addFloor" enctype="multipart/form-data">
	<div class="izquierda">
		<div id="piso" class="cama">
			<img src="bootstrap/img/bed.png" class="cama">
		</div>
		<div>
			<input type="button" id="get_file" class="boton" value="Elegir imagen">
			<input type="file" name="imagenPiso" id="imagenPiso"/>
		</div>
	</div>
	
	<div class="cuadrado" id="container">
		<img id="imagen">
	</div>
	<br>
	<div>
		<button class="btn btn-raised btn-danger pull-right" name="finalizar" onclick="guardarPiso()" type="button">Registrar</button>
	<!-- 	<button class="btn-back btn btn-danger pull-left" onclick="volver()" type="button">Volver</button> -->
	</div>
	<input id="posicionesCamas" name="posicionesCamas" type="hidden">
</form>
<script>
$(document).ready(function() {
	$("#container").droppable({
		accept: '.cama',
        drop: function(event, ui) {
            $(this).append($(ui.helper).clone());
            $("#container .cama").addClass("item");
            $(".item").removeClass("ui-draggable cama");
            $(".item").draggable({
                containment: 'parent'
            });
        }
	 });	
	
	$( ".cama" ).draggable({
		helper: "clone"
      
    });
  
	document.getElementById('get_file').onclick = function() {
		document.getElementById('imagenPiso').addEventListener('change', readURL, true);
		function readURL(){
			var file = document.getElementById("imagenPiso").files[0];
			var reader = new FileReader();
		    reader.onloadend = function(){
				document.getElementById('imagen').src = reader.result ;        
				}
			if(file){
				reader.readAsDataURL(file);
			} else {
			}
		}
		
		var fileButton = document.getElementById('imagenPiso');
		fileButton.click();
	};
});

function guardarPiso() {
	var container = document.getElementById("container");
	$('#container').height();
// 	alert($('#container').height() + " " + container.style.width);
	var camas = document.querySelectorAll('.item');
	var posicionesCamas = "";
	for (var i = 0; i < camas.length; i++) {
		var x = camas[i].offsetLeft - container.offsetLeft;
		var y = camas[i].offsetTop - container.offsetTop;
		posicionesCamas += x + "," + y + ";"; 
	}
	document.identicalForm.posicionesCamas.value = posicionesCamas;
	document.getElementById("identicalForm").submit();
// 	alert(posicionesCamas);
}
</script>
</body>
</html>