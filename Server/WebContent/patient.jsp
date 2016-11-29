<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entities.User" %>
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
    
    
    <link href="bootstrap/css/bootstrap-tagsinput.css" rel="stylesheet">
    
    
    
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="bootstrap/js/mySlider.js"></script>
 	
 	
 </head>
  <body>

<jsp:include page="admin.jsp">
		<jsp:param name="title" value="Alta Pacientes"/>
 </jsp:include>
<%

		if(request.getAttribute("errormsg") != null){
%>

	<div class="alerta alert alert-danger">
  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  		<strong>Error!</strong> DNI existente. Por favor, introduzca otro.
	</div>
 
 <%

		}
 %> 
 
<form id="identicalForm" class="register" method="post" action="patient">
<%if(request.getAttribute("id") != null) {%>
  <input type="hidden" name="id" id="id" value="${id}">
<%} else{%>
	<input type="hidden" name="id" id="id" value="">
<%} %>
  <div class="form-group label-floating">
    <label class="control-label" for="name">Nombre</label>
    <c:choose>
    	<c:when test="${name != NULL}">
 <input class="form-control" id="name" name="name" value="${name}" type="text" required>
  </c:when>
          <c:otherwise>
           <input class="form-control" id="name" name="name" type="text" required>
          </c:otherwise>
   </c:choose>
  
  </div>
  <div class="form-group label-floating">
    <label class="control-label" for="lastName">Apellido</label>
    <c:choose>
    	<c:when test="${lastName != NULL}">
  <input class="form-control" id="lastName" name="lastName" type="text" value="${lastName}" required>
  </c:when>
          <c:otherwise>
            <input class="form-control" id="lastName" name="lastName" type="text" required>
            </c:otherwise>
   </c:choose>
  </div>
   <div class="form-group label-floating">
    <label class="control-label" for="lastName">DNI</label>
    <c:choose>
    	<c:when test="${dni != NULL}">
  <input class="form-control" id="dni" name="dni" type="number" value="${dni}" required>
  </c:when>
          <c:otherwise>
            <input class="form-control" id="dni" name="dni" type="number" required>
            </c:otherwise>
   </c:choose>     
  </div>
  
  <div class="form-group label-floating">	
 	<label class="control-label" id="labelDoctors" for="doctors">Medicos</label>
 	<input type="text" id="doctors" name="doctors" class="form-control">
  </div>
  
  <button class="btn btn-raised btn-danger pull-right" name="finalizar" type="submit">Registrar</button>
  <button class="btn-back btn btn-danger pull-left" onclick="volver()" type="button">Volver</button>
	</form>

	
	
	<script type='text/javascript'>
	var data = [
	<%
	if(request.getAttribute("id") != null){
	    ArrayList<User> doctors = (java.util.ArrayList)request.getAttribute("doctors");
		 for (User doctor: doctors)
		 { 
			 out.print("{ 'value': " + doctor.getId() + " , 'text': '" + doctor.getFirstName() + " " + doctor.getLastName() + "'} ,");
		 }
	}
	%>
];

var citynames = new Bloodhound({
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    local: $.map(data, function (city) {
        return {
            name: city.text,
            id: city.value,
        };
    })
});
citynames.initialize();
var elt = $('#doctors');
elt.tagsinput({
	itemValue: 'id',
  	itemText: 'name',
    typeaheadjs: [{
          minLength: 1,
          highlight: true,
    },{
        minlength: 1,
        name: 'citynames',
        displayKey: 'name',
        //valueKey: 'name',
        source: citynames.ttAdapter()
    }],
    freeInput: true
});

<%

if(request.getAttribute("id") != null){

	ArrayList<User> currentDoctors = (java.util.ArrayList)request.getAttribute("currentDoctors");
	 for (User doctor: currentDoctors)
	 { 
		 out.print("elt.tagsinput('add',  { 'id': " + doctor.getId() + " , 'name': '" + doctor.getFirstName() + " " + doctor.getLastName()  + "'});\n");
	 }
}
%>


$(".bootstrap-tagsinput").addClass("form-control");

/* $(".tt-input").blur(function(){
	if($('#categories').val() != ""){
		$("#labelCategories").hide()
	}else{
		$("#labelCategories").show()
	}
}); */

$(".tt-input").focus(function(){
	$("#labelDoctors").show()
});

//$(".tt-input").attr("required",true);
	

	function volver(){	
			window.location.href = host + "/listaPacientes";
	}
	
	
	</script>

  </body>
</html>