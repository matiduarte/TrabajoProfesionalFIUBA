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
	<%if(request.getAttribute("id") != null) {%>
  		<input type="hidden" name="id" id="id" value="${id}">
	<%} else{%>
		<input type="hidden" name="id" id="id" value="">
	<%} %>
	<div class="izquierda">
		<div>
			<img id="camaClonable" src="bootstrap/img/bed.png" class="cama">
		</div>
		<div>
			<input type="button" id="get_file" class="boton" value="Elegir imagen">
			<input type="file" name="archivoImagenPiso" id="archivoImagenPiso"/>
			
		</div>
	</div>
	<div class="cuadradoGrande" id="container">
		<div class="cuadrado" id="zonaArrastrable">
			<%if(request.getAttribute("id") != null) {%>
				<img id="imagen" class="absoluta" src="${pageContext.request.contextPath}/images?id=${id}">
					
			<%  } else { %>
				<img id="imagen">
			<%} %>
			
		</div>
		<div class="zonaBorrado" id="zonaBorrado">Arrastre camas aqui para borrarlas</div>
	</div>
	
	<br>
	<div class="alerta alert alert-danger" id="mensajeImagenIncorrectaError" style="display: none;">
	 	<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	 	<strong>Error!</strong> El archivo seleccionado no es una imagen. Por favor, introduzca otra.
	</div>
		
	<div class="alerta alert alert-danger" id="mensajeSinCamasError" style="display: none;">
	 	<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		<strong>Error!</strong> No se encontraron camas ingresadas. Por favor, introduzca por lo menos una.
	</div>
	<br>
	
	<div>
		<button class="btn btn-raised btn-danger pull-right" name="finalizar" onclick="guardarPiso()" type="button">Registrar</button>
		<button class="btn-back btn btn-danger pull-left" onclick="volver()" type="button">Volver</button>
	</div>
	<input id="posicionesCamas" name="posicionesCamas" type="hidden">
	<input id="imagenCambiada" name="imagenCambiada" type="hidden" value="0">
	
</form>
<script>
function volver(){	
	window.location.href = host + "/listaPisos";
}

$(document).ready(function() {
	$("#zonaArrastrable").droppable({
		accept: '.cama',
        drop: function(event, ui) {
            $(this).append($(ui.helper).clone());
            $("#zonaBorrado .cama").remove();
            $("#container .cama").addClass("item");
            
            $(".item").removeClass("ui-draggable cama");
            $(".item").draggable({
                containment: '#container'
            });
            
        }
	 });
	
	cargarCamas();
	
	$("#zonaBorrado").droppable({
		accept: '.item',
        drop: function(event, ui) {
           	$(ui.draggable).addClass("borrar");
            $(".borrar").draggable('destroy');
            $(".borrar").remove();
        }
	 });
	
	$( ".cama" ).draggable({
		helper: "clone"
    });
  
	document.getElementById('get_file').onclick = function() {
		document.getElementById('archivoImagenPiso').addEventListener('change', readURL, true);
		var fileButton = document.getElementById('archivoImagenPiso');
		fileButton.click();
	};
	
	$("#archivoImagenPiso").change(function() {

	    var val = $(this).val();

	    switch(val.substring(val.lastIndexOf('.') + 1).toLowerCase()){
	        case 'gif': case 'jpg': case 'png':
	        	document.getElementById("mensajeImagenIncorrectaError").style.display = 'none';
	        	break;
	        default:
	            $(this).val('');
				document.getElementById("mensajeImagenIncorrectaError").style.display = 'block';
				document.getElementById('archivoImagenPiso').value = "" ;
				document.getElementById('imagen').src = "" ;
				break;
	    }
	});
	
	function cargarCamas() {
		var alto = $('#zonaArrastrable').height();
		var ancho = $('#zonaArrastrable').width();
		var tamanioCamas = '${bedList.size()}';
		if (tamanioCamas != 0) {
			<c:forEach items="${bedList}" var="cama" >
				x = '${cama.getX()}';
				y = '${cama.getY()}';
				var cama = document.getElementById("camaClonable").cloneNode(true);				
				cama.id = '${cama.getId()}';
				cama.style.position = "absolute";
				cama.style.left = ( x * ancho / 100) + container.offsetLeft + "px";
				cama.style.top = ( y * alto / 100) + container.offsetTop + "px";
				cama.className = "";
				cama.className += "item";
				
				document.body.appendChild(cama);

			</c:forEach>

		$(".item").draggable({
               containment: '#container'
           });
		}
	}
});



function readURL(){
	document.getElementById("imagenCambiada").value = "1";
	var file = document.getElementById("archivoImagenPiso").files[0];
	var reader = new FileReader();
    reader.onloadend = function(){
		document.getElementById('imagen').src = reader.result ;        
		}
	if(file){
		reader.readAsDataURL(file);
	} else {
	}
}

function guardarPiso() {
	document.getElementById("mensajeSinCamasError").style.display = 'none';
	document.getElementById("mensajeImagenIncorrectaError").style.display = 'none';
	var alto = $('#zonaArrastrable').height();
	var ancho = $('#zonaArrastrable').width();
	var camas = document.querySelectorAll('.item');
	var posicionesCamas = "";
	if (camas.length == 0) {
		document.getElementById("mensajeSinCamasError").style.display = 'block';
		return;
	}
	for (var i = 0; i < camas.length; i++) {
		var x = camas[i].offsetLeft - container.offsetLeft;
		var y = camas[i].offsetTop - container.offsetTop;
		x = x * 100 / ancho;
		y = y * 100 / alto;
		var id = camas[i].id;
		posicionesCamas += x + "," + y + "," + id + ";";
	}
	var id = '${id}';		
	if (id == "" && document.getElementById('archivoImagenPiso').value == '') {
		document.getElementById("mensajeImagenIncorrectaError").style.display = 'block';
		return;
	}
	document.identicalForm.posicionesCamas.value = posicionesCamas;
	document.getElementById("identicalForm").submit();
}
</script>
</body>
</html>