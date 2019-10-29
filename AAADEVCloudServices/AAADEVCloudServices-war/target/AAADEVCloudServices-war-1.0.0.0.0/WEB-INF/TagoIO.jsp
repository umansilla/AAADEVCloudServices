<%-- 
    Document   : TagoIO.jsp
    Created on : Aug 28, 2019, 1:08:05 PM
    Author     : umansilla
--%>

<%@page import="service.AAADEVCloudServices.TagoIO.Bean.Paciente"%>
<%@page import="service.AAADEVCloudServices.TagoIO.Bean.UsuarioTagoIO"%>


<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Create Patient</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Use This for 
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css"> -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
<link rel="stylesheet" href="../../css/css-loader.css">
</head>

<%
	List<Paciente> listPacientes = (List<Paciente>) request
			.getAttribute("Pacientes");
	System.out.println(listPacientes.size());
	UsuarioTagoIO usuario = (UsuarioTagoIO) request
			.getAttribute("Usuario");

	String userName = usuario.getName();
	if (userName == null || userName.isEmpty()) {
		userName = usuario.getUsername();
	}
%>
<body>
	<div class="loader loader-default" data-blink id="loaderDisplay"></div>
	<nav class="navbar navbar-default text-center">
		<div class="container-fuid ">

			<div class="navbar-header text-center">
				<h2
					style="font-weight: bold; text-align: center; font-weight: 50px !important;"
					class="text-center navbar-text">America’s International PoC
					Development Team</h2>

			</div>

			<ul class="nav navbar-nav navbar-right">
				<p class="navbar-text">
					Signed in as <span style="font-weight: bold;"><%=userName%></span>
				</p>
				<p class="navbar-text">
					<a class="navbar-link" id="closeSessionBtn"
						style="cursor: pointer;">Close Session</a>
				</p>

			</ul>
		</div>
	</nav>
	<div class="jumbotron" style="background-color: #c72d1c">
		<img src="../../img/avaya-01-logo-black-and-white.png" width="190"
			height="60" style="display: inline;">
		<p style="display: inline; color: white; font-size: 40px;">|</p>
		<p
			style="display: inline; color: white; text-align: center; font-size: 30px;">Control
			of patient admission.</p>
	</div>
	<div class="container">
		<button type="button" class="btn btn-primary btn-block"
			id="createPatient">
			<span class="glyphicon glyphicon-user"></span> Create Patient
		</button>
	</div>
	<br>
	<hr>
	<div class="container-fluid">

		<table id="example" class="table table-striped table-bordered"
			style="width: 100%">
			<thead>
				<tr>
					<th>Patient Name</th>
					<th>Address</th>
					<th>BirthDate</th>
					<th>Age</th>
					<th>Genre</th>
					<th>NSS</th>
					<th>Contact Emergency Phone</th>
					<th>Patient Space</th>
					<th>Patient Dashboard</th>
					<th>Discharge patient</th>
				</tr>
			</thead>
			<tbody>

				<%
					for (Paciente paciente : listPacientes) {
				%>
				<tr id=<%=paciente.getIdpaciente()%>>
					<td><%=paciente.getNombrepaciente()%></td>
					<td><%=paciente.getDireccion()%></td>
					<td><%=paciente.getFechadenacimiento()%></td>
					<td><%=paciente.getEdad()%></td>
					<td><%=paciente.getSexo()%></td>
					<td><%=paciente.getNss()%></td>
					<td><%=paciente.getTelefono()%></td>
					<td><a
						href="https://spaces.zang.io/spaces/<%=paciente.getSpaceid()%>"
						target="_blank" style="">Go to Patient Space</a></td>
					<td><a
						href="https://admin.tago.io/dashboards/info/<%=paciente.getDashboardid()%>"
						target="_blank">Go to Patient Dashboard</a></td>
					<td>
						<button type="button" class="btn btn-danger btn-block" onclick="deletePatient(this)">
							<span class="glyphicon glyphicon-trash"></span>
						</button>
					</td>
				</tr>

				<%
					}
				%>

			</tbody>
			<tfoot>
				<tr>
					<th>Patient Name</th>
					<th>Address</th>
					<th>BirthDate</th>
					<th>Age</th>
					<th>Genre</th>
					<th>NSS</th>
					<th>Contact Emergency Phone</th>
					<th>Patient Space</th>
					<th>Patient Dashboard</th>
					<th>Discharge patient</th>
				</tr>
			</tfoot>
		</table>

	</div>

	<script src="../../js/sweetAlertmin.js"></script>
	<script src="../../js/jquery.canvasjs.min.js"></script>
	<script src="../../js/AdminPatients.js"></script>

</body>
</html>