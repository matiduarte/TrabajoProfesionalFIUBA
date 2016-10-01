<!doctype html>
<%@page import="java.util.List"%>
<%@page import="entities.User"%>
<html lang="en-us">
  <head>
        <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="bootstrap/img/icono.ico">
    
    
    
<!--     Bootstrap -->
<!--     <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"> -->

<!--     Bootstrap Material Design -->
<!--     <link href="bootstrap/css/bootstrap-material-design.css" rel="stylesheet"> -->
<!--     <link href="bootstrap/css/ripples.min.css" rel="stylesheet"> -->

<!-- 	<link href="//fezvrasta.github.io/snackbarjs/dist/snackbar.min.css" rel="stylesheet"> -->
 
 
    
    
    <!-- Bootstrap Material Design -->

	<!-- Custom styles for this template -->
	<link href="bootstrap/css/listar.css" rel="stylesheet">
	
	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/material.min.js"></script>
	<script src="bootstrap/js/bootbox.min.js"></script>

	<script src="bootstrap/js/floating-label.js"></script> 	
 	
</head>
<body>

<%-- <jsp:include page="admin.jsp"></jsp:include> --%>
<% List<User> listaMedicos = (List<User>) request.getAttribute("listaMedicos"); %>
<div class="container">
	<div class="alert alert-success" id="deleteSucces" style="display:none;">
		<button onclick="$('#deleteSucces').hide()" class="close" aria-label="close">&times;</button>
  		<span id="deleteSuccesMessage">Doctor borrado satisfactoriamente!</span>
	</div>
	<div class="tableContainer">
		<table class="tg">
			<thead>
				<tr>
					<th class="tg-zyzu">Nombre</th>
					<th class="tg-zyzu">Apellido</th>
					<th class="tg-zyzu"> </th>
				</tr>
			</thead>
			<tbody>
				<%for(User medico : listaMedicos) { %>
					<tr>
						<td class="tg-yw4l"><%=medico.getFirstName() %></td>
						<td class="tg-yw4l"><%=medico.getLastName() %></td>
						<td class="tg-yw4l">
							<button class="btn" type="submit" onclick="editDoctor(<%out.print(medico.getId());%>)">
								<img  src="bootstrap/img/edit_icon.png" class="actionButtonImage" alt="Editar" >
							</button>
							<button class="btn" onclick="showPopup(<%out.print(medico.getId());%>)">
								<img  src="bootstrap/img/delete_icon.png" class="actionButtonImage" alt="Borrar" >
							</button>
						</td>	
					</tr>
				<%} %>	
			</tbody>
		</table>
	</div>
	
	<div id="deletePopup">
		<label class="labelPopup" id="popupDeleteTitle">¿Está seguro que quiere eliminar el medico?</label>
		<br/>
		<hr>
		
		<div class="popupButtonsContainer">
			<button class="btn btnPopup" type="submit" onclick="hidePopup();">Cancelar</button>
			<button class="btn btnPopup" type="submit" onclick="deleteDoctor()" id="popupDeleteSubmit">Aceptar</button>
		</div>
	</div>
</div> <!-- Termina container -->
pepa23455selkfn
<div id="deleteId" class="hide"></div>

<script>
	function editDoctor(doctorId) {
// 		window.location.href = "../edit?id=" + Id;
		d = 9;
	}
    
	function showPopup(doctorId){
// 		$('#deleteId').text(doctorId);
//    		$("#deletePopup").show();
		var mensaje = "Estas seguro que lo desea borrar?";
		bootbox.confirm(mensaje, function (response) {
			if(response) {
				deleteDoctor(doctorId);
				$("#deleteSucces").show();
			}
		
		});
    }
	
	function hidePopup(){
		$("#deletePopup").hide();
// 		$("#deleteSucces").show();
	}

	function deleteDoctor(doctorId){
		e=8;
		
	}
	
</script>
</body>
</html>