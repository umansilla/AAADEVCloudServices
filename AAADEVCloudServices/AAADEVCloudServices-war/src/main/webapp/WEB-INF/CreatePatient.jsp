<%-- 
    Document   : CreatePatient
    Created on : Aug 28, 2019, 3:26:13 PM
    Author     : umansilla
--%>

<%@page import="service.AAADEVCloudServices.TagoIO.Bean.ServicioMedico"%>
<%@page import="service.AAADEVCloudServices.TagoIO.Bean.UsuarioTagoIO"%>
<%@page import="service.AAADEVCloudServices.TagoIO.Bean.Paciente"%>
<%@page import="java.util.List"%>
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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
        <link rel="stylesheet" href="../../css/css-loader.css">
    </head>
    <style>
        .swal2-popup {
            display: none;
            position: relative;
            flex-direction: column;
            justify-content: center;
            width: 66em !important;
            max-width: 100%;
            padding: 4.25em !important;;
            border-radius: 3.3125em !important;;
            background: #fff;
            font-family: inherit;
            font-size: 1rem;
            box-sizing: border-box;
        }

    </style>
    <%

        UsuarioTagoIO usuario = (UsuarioTagoIO) request.getAttribute("Usuario");
        List<ServicioMedico> servicioMedicoLista = (List<ServicioMedico>) request.getAttribute("ServicioMedico");
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
                    <h2 style="font-weight: bold; text-align: center; font-weight: 50px !important;" class="text-center navbar-text">America’s International PoC Development Team</h2>

                </div>

                <ul class="nav navbar-nav navbar-right">
                    <p class="navbar-text">Signed in as <span style="font-weight: bold;"><%= userName%></span></p>
                    <p class="navbar-text"><a class="navbar-link" id="closeSessionBtn" style="cursor:pointer;">Close Session</a></p>

                </ul>
            </div>
        </nav> 
        <div class="jumbotron" style="background-color: #c72d1c">
            <img src="../../img/avaya-01-logo-black-and-white.png" width="190" height= "60" style="display: inline;">
            <p style="display: inline; color: white; font-size: 40px;">|</p>
            <p style="display: inline; color: white; text-align: center; font-size: 30px;">Create Patient</p>
        </div>
        <div class="container">
            <a id="backButton" style="cursor: pointer;"> << Go Back</a>
            <div class="row">
                <div class="col-sm-6">
                    <form>
                        <h2>Enter patient information</h2>
                        <hr>
                        <div class="input-group col-sm-12">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span> 
                            <input type="text" class="form-control" placeholder="Enter the patient's name" id="namePatient">
                        </div>
                        <br>
                        <div class="input-group col-sm-12">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span> 
                            <input type="text" class="form-control" placeholder="Enter the patient's Adress" id="addressPatient">
                        </div>
                        <br>
                        <div class="input-group col-sm-2">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span> 
                            <input type="date" class="form-control" placeholder="Enter the patient's birthDay" id="birthDayPatient">
                        </div>
                        <br>
                        <div class="input-group col-sm-6" id="inputGenre">
                            <input type="radio" name="gender" value="Masculino" checked> Male<br>
                            <input type="radio" name="gender" value="Femenino"> Female<br>
                            <input type="radio" name="gender" value="Otro"> Other
                        </div>
                        <br>
                        <br>
                        <div class="input-group col-sm-12">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span> 
                            <input type="text" class="form-control" placeholder="Enter the patient's social security number" id="nssPatient">
                        </div>
                        <br>
                        <div class="input-group col-sm-12">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span> 
                            <input type="text" class="form-control" placeholder="Enter the patient's contat form emergency phone" id="phonePatient">
                        </div>
                        <hr>

                        <hr>
                        <div class="row">
                            <div class="col-sm-12" >
                                <button type="button" class="btn btn-primary btn-block" id="submitCreatePatient">Register patient</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-sm-6">

                    <h2>Add the responsible medical staff.</h2>
                    <hr>
                    <form>
                        <div class="form-group">
                            <label for="sel1">Select the responsible doctor:</label>
                            <select class="form-control" id="responsablePatient">
                                <% for (ServicioMedico responsable : servicioMedicoLista) {;%>
                                <% if (responsable.getPuesto().equals("Doctor")) {%>
                                <option id="<%= responsable.getIdpersonalmedico()%>"> <%= responsable.getNombre()%></option>
                                <% }%>
                                <% }%>
                            </select>
                        </div>
                    </form>
                    <form>
                        <div class="form-group">
                            <label for="sel1">Select the responsible nurse:</label>
                            <select class="form-control" id="nurseResponsable">
                                <% for (ServicioMedico responsable : servicioMedicoLista) {%>
                                <%if (responsable.getPuesto().equals("Nurse")) {%>
                                <option id="<%= responsable.getIdpersonalmedico()%>"><%= responsable.getNombre()%></option>
                                <% }%>
                                <% }%>
                            </select>
                        </div>
                    </form>
                    <form>
                        <div class="form-group">
                            <label for="sel1">Select the responsible technical service: </label>
                            <select class="form-control" id="technicalServiceResponsable">
                                <% for (ServicioMedico responsable : servicioMedicoLista) {%>
                                <%if (responsable.getPuesto().equals("Technical Service")) {%>
                                <option id="<%= responsable.getIdpersonalmedico()%>"><%= responsable.getNombre()%></option>
                                <% }%>
                                <% }%>
                            </select>
                        </div>
                    </form>

                    <br>
                    <div class="row">
                        <div class="col-sm-12" >
                            <button type="button" class="btn btn-success btn-block" id="submitCreateResponsable">Create Personal</button>
                        </div>
                    </div>

                </div>
            </div>

        </div>
        <script src="../../js/sweetAlertmin.js"></script>
        <script src="../../js/CreatePatient.js"></script>
    </body>
</html>
