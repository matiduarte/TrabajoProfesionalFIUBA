<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en-us">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="bootstrap/img/icono.ico">

    <!-- Bootstrap Material Design -->

	<!-- Custom styles for this template -->
	<link href="bootstrap/css/listar.css" rel="stylesheet">

	<script src="bootstrap/js/floating-label.js"></script> 	
 	
</head>
<body>

<jsp:include page="admin.jsp">
	<jsp:param name="title" value="Listar administradores"/>
</jsp:include>

<div class="container">
	<div class="tableContainer">
		<table class="tg" id="tableAdmins">
			<thead>
				<tr>
					<th class="tg-zyzu">Nombre</th>
					<th class="tg-zyzu">Apellido</th>
					<th class="tg-zyzu"> </th>
				</tr>
			</thead>
			<tbody>
					<c:forEach items="${listaAdministradores}" var="admin">
					<tr>
						<td class="tg-yw4l">${admin.getFirstName()} </td>
						<td class="tg-yw4l">${admin.getLastName()} </td>
						<td class="tg-yw4l">
							<button class="btn" type="submit" onclick="edit(${admin.getId()})">
								<img  src="bootstrap/img/edit_icon.png" class="actionButtonImage" alt="Editar">
							</button>
							<button class="btn" type="submit" onclick="showPopup(${admin.getId()})">
								<img  src="bootstrap/img/delete_icon.png" class="actionButtonImage" alt="Borrar">
							</button>
						</td>	
					</tr>
					</c:forEach>
			</tbody>
		</table>
	</div>

</div> <!-- Termina container -->

<form id="deleteForm" name="deleteForm" action="" method="post">
	<input type="hidden" id="deleteId" name="deleteId"/>
</form>

<script src="bootstrap/js/bootbox.min.js"></script>

<script>
	function edit(adminId) {
		window.location.href = host + '/addadmin?id=' + adminId;
	}
    
	function showPopup(adminId){
		var mensaje = "Estas seguro que lo desea borrar?";
		bootbox.confirm(mensaje, function (response) {
			if(response) {
				deleteAdmin(adminId);
			}
		});
    }

	function deleteAdmin(adminId){
		document.deleteForm.deleteId.value = adminId;
		document.getElementById("deleteForm").submit();
	}
	
	$('#tableAdmins').DataTable({
		 "language": {
	            "lengthMenu": "Mostrar _MENU_ registros por pagina",
	            "zeroRecords": "No se encontraron resultados",
	            "info": "Mostrando pagina _PAGE_ de _PAGES_",
	            "infoEmpty": "No hay registros disponibles",
	            "infoFiltered": "(filtrado de _MAX_ registros)",
	            "search" : "Buscar:",
	            paginate: {
	                first:      "Primer",
	                previous:   "Anterior",
	                next:       "Siguiente",
	                last:       "Ultimo"
	            },
	        }
	 });
	
</script>
</body>
</html>