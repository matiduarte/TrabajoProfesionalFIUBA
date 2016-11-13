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
	
	<title></title>
</head>
<body>
<jsp:include page="admin.jsp">
		<jsp:param name="title" value="Alta Piso"/>
 </jsp:include>
<div class="register">
	<div class="cuadradoGrande" id="container">
		<div class="cuadrado" id="zonaPiso">
			<%if(request.getAttribute("id") != null) {%>
				<img id="imagen" class="absoluta" src="${pageContext.request.contextPath}/images?id=${id}">
			<%} %>
		</div>
	</div>
	
	<div>
		<button class="btn-back btn btn-danger pull-left" onclick="volver()" type="button">Volver</button>
	</div>
</div>

<script>
function volver(){	
	window.location.href = "/Server/vistaPisos";
}

$(document).ready(function() {
	cargarCamas();
	
	function cargarCamas() {
		var alto = $('#zonaPiso').height();
		var ancho = $('#zonaPiso').width();
		var tamanioCamas = '${bedList.size()}';
		if (tamanioCamas != 0) {
			<c:forEach items="${bedList}" var="cama" >
				x = '${cama.getX()}';
				y = '${cama.getY()}';
				var cama = document.createElement("img");				
				cama.id = '${cama.getId()}';
				cama.style.position = "absolute";
				var posX = ( x * ancho / 100) + container.offsetLeft;
				var posY = ( y * alto / 100) + container.offsetTop;
				cama.style.left = posX + "px";
				cama.style.top = posY + "px";
				cama.className = "cama";
				cama.src = "bootstrap/img/bed.png";
				cama.onclick = function() {
					alert("WOW");
				}
				<c:forEach items="${users}" var="user">
					if ('${user.getId()}' == '${cama.getPatientId()}') {
						var paciente = document.createElement("P");
						var text = document.createTextNode('${user.getFirstName()}' + " " + '${user.getLastName()}');
						paciente.style.position = "absolute";
						paciente.style.left = posX + "px";
						paciente.style.top = posY - 10 + "px";
						paciente.appendChild(text);
						paciente.style.fontSize = "10px";
						document.body.appendChild(paciente);
					}
				</c:forEach>
				document.body.appendChild(cama);

			</c:forEach>
		}
	}
});





</script>
</body>
</html>