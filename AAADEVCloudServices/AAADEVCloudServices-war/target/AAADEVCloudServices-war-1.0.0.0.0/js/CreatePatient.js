/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


console.log("CreatePatient.js");
/*
 * Variables
 */
var absolutepath = getAbsolutePath();
console.log(absolutepath);
console.log(document.getElementById('responsablePatient').children);
console.log(document.getElementById('responsablePatient').id);
/*
 * Eventos
 */

document.getElementById('backButton').addEventListener('click', function (e) {
    e.preventDefault();
    window.location.replace(absolutepath);
});
document.getElementById('closeSessionBtn').addEventListener('click', function (e) {
    e.preventDefault();
    console.log("Close Session");
    var data = new FormData();
    data.append("action", "CloseSession");
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            console.log(this.responseText);
            window.location.reload();
        }
    });
    xhr.open("POST", absolutepath);
    xhr.send(data);
});
document.getElementById('submitCreateResponsable').addEventListener('click', function (e) {

    Swal.fire({
        title: '<strong>Enter the data of the medical staff</strong>',
        type: 'info',
        html: '<div class="row"><div class="col-sm-12"><form>' +
                '<div class="input-group col">' +
                '<span class="input-group-addon">' +
                '<i class="glyphicon glyphicon-user">' +
                '</i></span>' +
                '<input type="text" class="form-control" id="inputName" placeholder="Enter the name">' +
                '</div>' +
                '<hr>' +
                '<div class="input-group col">' +
                '<span class="input-group-addon">' +
                '<i class="glyphicon glyphicon-envelope">' +
                '</i></span>' +
                '<input type="text" class="form-control" id="correoElectronico" placeholder="Enter the email">' +
                '</div>' +
                '<hr>' +
                '<div class="form-group">' +
                ' <label for="sel1">Select the position of the personnel entered: </label> ' +
                '<select class="form-control" id="sel1"> ' +
                ' <option>Doctor</option> ' +
                ' <option>Nurse</option> ' +
                ' <option>Technical Service</option> ' +
                ' </select> ' +
                '</div>' +
                '</form>' +
                '</div>' +
                '</div>',
        showCancelButton: true,
        focusConfirm: false,
        confirmButtonText:
                '<i class="glyphicon glyphicon-thumbs-up" id="upload"></i> OK!',
        cancelButtonText:
                '<i class="glyphicon glyphicon-thumbs-down id="cancel"></i>',
        cancelButtonAriaLabel: 'Thumbs down'
    }).then((result) => {
        if (result.value) {
            validateNewPersonal();
        }
        ;
    });
    function validateNewPersonal() {
        if (validateInputValues()) {
            console.log("Validación exitosa");
            var puesto = document.getElementById('sel1').value;
            var nombre = document.getElementById('inputName').value;
            var data = new FormData();
            data.append("action", "CreateMedicalService");
            data.append("nombre", document.getElementById('inputName').value);
            data.append("puesto", document.getElementById('sel1').value);
            data.append("correoelEctronico", document.getElementById('correoElectronico').value);
            var xhr = new XMLHttpRequest();
            xhr.withCredentials = false;
            xhr.addEventListener("readystatechange", function () {
                if (this.readyState === 4) {
                    var jsonObject = JSON.parse(this.responseText);
                    console.log(jsonObject);
                    if (jsonObject.status === "ok") {
                        var selectForm;
                        if (puesto === "Doctor") {
                            selectForm = document.getElementById('responsablePatient');
                        }
                        if (puesto === "Nurse") {
                            selectForm = document.getElementById('nurseResponsable');
                        }
                        if (puesto === "Technical Service") {
                            selectForm = document.getElementById('technicalServiceResponsable');
                        }

                        var option = document.createElement('OPTION');
                        option.setAttribute("id", jsonObject.newId);
                        var optionTextNode = document.createTextNode(nombre);
                        option.appendChild(optionTextNode);
                        selectForm.appendChild(option);
                        Swal.fire(
                                'Good job!',
                                'The new ' + puesto + ' was created successfully!',
                                'success'
                                );
                    }
                    if (jsonObject.status === "error") {
                        Swal.fire(
                                'Opps!',
                                'Cannot create the new ' + puesto,
                                'error'
                                );
                    }
                }
            });
            xhr.open("POST", absolutepath);
            xhr.send(data);
        }
    }

    function validateInputValues() {
        if (document.getElementById('inputName').value === "" || document.getElementById('inputName').value === null) {
            alert("The name input cannot be empty...");
            return false;
        }
        if (document.getElementById('correoElectronico').value === "" || document.getElementById('inputName').value === null) {
            alert("The email input cannot be empty...");
            return false;
        }
        if (document.getElementById('sel1').value === "" || document.getElementById('sel1').value === null) {
            alert("The email input cannot be empty...");
            return false;
        }
        return true;
    }

});
document.getElementById('submitCreatePatient').addEventListener('click', function (e) {
    e.preventDefault();
    console.log("Submit Create Patient");
    document.getElementById('loaderDisplay').classList.add('is-active');
    document.getElementById("loaderDisplay").setAttribute("data-text", "Creating patient");
    if (validateInputs()) {
        var data = new FormData();
        data.append("action", "CreatePatient");
        data.append("nombre", document.getElementById('namePatient').value);
        data.append("address", document.getElementById('addressPatient').value);
        data.append("birthDay", document.getElementById('birthDayPatient').value);
        data.append("nss", document.getElementById('nssPatient').value);
        data.append("phone", document.getElementById('phonePatient').value);
        var inputsGenre = document.getElementById('inputGenre').childNodes;
        var name = document.getElementById('namePatient').value;
        var address = document.getElementById('addressPatient').value;
        var bithDay = document.getElementById('birthDayPatient').value;
        var nss = document.getElementById('nssPatient').value;
        var phone = document.getElementById('phonePatient').value;
        var genreValue;
        for (var i = 0; i < inputsGenre.length; i++) {
            if (inputsGenre[i].nodeName === "INPUT") {
                if (inputsGenre[i].checked === true) {
                    genreValue = inputsGenre[i].defaultValue;
                }
            }
        }
        data.append("genreValue", genreValue);
        var doctorId;
        var doctorName;
        var doctorsList = document.getElementById('responsablePatient');
        for (var i = 0; i < doctorsList.length; i++) {
            if (doctorsList[i].selected === true) {
                doctorId = doctorsList[i].id;
                doctorName = doctorsList[i].innerText;
            }
        }
        console.log(doctorId);
        data.append("doctor", doctorId);
        var nurseId;
        var nurseName;
        var nursesList = document.getElementById('nurseResponsable');
        for (var i = 0; i < nursesList.length; i++) {
            if (nursesList[i].selected === true) {
                nurseId = nursesList[i].id;
                nurseName = nursesList[i].innerText;
            }
        }
        data.append("nurse", nurseId);
        var technicalService;
        var technicalServiceName;
        var technicalServiceList = document.getElementById('technicalServiceResponsable');
        for (var i = 0; i < technicalServiceList.length; i++) {
            if (technicalServiceList[i].selected === true) {
                technicalService = technicalServiceList[i].id;
                technicalServiceName = technicalServiceList[i].innerText;
            }
        }
        data.append("technicalService", technicalService);
        var xhr = new XMLHttpRequest();
        xhr.withCredentials = false;
        xhr.addEventListener("readystatechange", function () {
            if (this.readyState === 4) {
                console.log(this.responseText);
                var jsonObject = JSON.parse(this.responseText);
                if (jsonObject.status === "ok") {

                    document.getElementById("loaderDisplay").classList.remove("is-active");
                    document.getElementById("loaderDisplay").setAttribute("data-text", "");
                    Swal.fire({
                        title: '<strong>Success creating the new patient</strong>',
                        type: 'success',
                        html: '<div class="row">' +
                                '<div class="col-md-6 col-xs-12">' +
                                '<h2 style="color: #111; font-family: \'Open Sans Condensed\', sans-serif; font-size: 13px; font-weight: 700; line-height: 64px; margin: 0 0 0; padding: 0px 30px; text-align: center; text-transform: uppercase;">Data of the new patient</h2>' +
                                '<h5 style="color: #111; font-family: \'Open Sans\', sans-serif; font-size: 16px; line-height: 28px; margin: 0 0 5px; text-align: left;"><span class="label label-primary">Name</span> ' + name + '</h5>' +
                                '<h5 style="color: #111; font-family: \'Open Sans\', sans-serif; font-size: 16px; line-height: 28px; margin: 0 0 5px; text-align: left;"><span class="label label-primary">Address</span> ' + address + '</h5>' +
                                '<h5 style="color: #111; font-family: \'Open Sans\', sans-serif; font-size: 16px; line-height: 28px; margin: 0 0 5px; text-align: left;"><span class="label label-primary">BirthDay</span> ' + bithDay + '</h5>' +
                                '<h5 style="color: #111; font-family: \'Open Sans\', sans-serif; font-size: 16px; line-height: 28px; margin: 0 0 5px; text-align: left;"><span class="label label-primary">Genre</span> ' + genreValue + '</h5>' +
                                '<h5 style="color: #111; font-family: \'Open Sans\', sans-serif; font-size: 16px; line-height: 28px; margin: 0 0 5px; text-align: left;"><span class="label label-primary">NSS</span> ' + nss + '</h5>' +
                                '<h5 style="color: #111; font-family: \'Open Sans\', sans-serif; font-size: 16px; line-height: 28px; margin: 0 0 5px; text-align: left;"><span class="label label-primary">Emergency Phone</span> ' + phone + '</h5>' +
                                '</div>' +
                                '<div class="col-md-6 col-xs-12">' +
                                '<h2 style="color: #111; font-family: \'Open Sans Condensed\', sans-serif; font-size: 13px; font-weight: 700; line-height: 64px; margin: 0 0 0; padding: 0px 30px; text-align: center; text-transform: uppercase;">Responsible medical staff</h2>' +
                                '<h5 style="color: #111; font-family: \'Open Sans\', sans-serif; font-size: 16px; line-height: 28px; margin: 0 0 5px; text-align: left;"><span class="label label-info">Doctor</span> ' + doctorName + '</h5>' +
                                '<h5 style="color: #111; font-family: \'Open Sans\', sans-serif; font-size: 16px; line-height: 28px; margin: 0 0 5px; text-align: left;"><span class="label label-info">Nurse</span> ' + nurseName + '</h5>' +
                                '<h5 style="color: #111; font-family: \'Open Sans\', sans-serif; font-size: 16px; line-height: 28px; margin: 0 0 5px; text-align: left;"><span class="label label-info">Technical service</span> ' + technicalServiceName + '</h5>' +
                                '</div>' +
                                '</div>',
                        showCancelButton: true,
                        focusConfirm: false,
                        confirmButtonText:
                                '<i class="glyphicon glyphicon-thumbs-up" id="Ok"></i> OK!',
                        cancelButtonText:
                                '<i class="glyphicon glyphicon-thumbs-down id="cancel"></i>',
                        cancelButtonAriaLabel: 'Thumbs down'
                    }).then((result) => {
                        if (result.value) {
                            window.location.replace(absolutepath);
                        }
                        ;
                    });
                }
                if (jsonObject.status === "error") {
                    alert("Error al crear Paciente");
                    document.getElementById("loaderDisplay").classList.remove("is-active");
                    document.getElementById("loaderDisplay").setAttribute("data-text", "");
                }
            }
        });
        xhr.open("POST", absolutepath);
        xhr.send(data);
    } else {
        document.getElementById("loaderDisplay").classList.remove("is-active");
        document.getElementById("loaderDisplay").setAttribute("data-text", "");
    }
});
/*
 * Funciones
 */

function getAbsolutePath() {
    var loc = window.location;
    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 15);
    return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

//Validación de input!
function validateInputs() {
    var nombre = document.getElementById('namePatient').value;
    var address = document.getElementById('addressPatient').value;
    var birthDay = document.getElementById('birthDayPatient').value;
    var responsable = document.getElementById('responsablePatient').value;
    var nss = document.getElementById('nssPatient').value;
    var phone = document.getElementById('phonePatient').value;
    var doctorResponsale = document.getElementById('responsablePatient').value;
    var nurseResponsale = document.getElementById('nurseResponsable').value;
    var technicalServiceResponsale = document.getElementById('technicalServiceResponsable').value;
    if (nombre === "" || nombre === null) {
        alert("Name canot be empty...");
        return false;
    }
    if (address === "" || address === null) {
        alert("Address canot be empty...");
        return false;
    }
    if (birthDay === "" || birthDay === null) {
        alert("birthDay canot be empty...");
        return false;
    }
    if (responsable === "" || responsable === null) {
        alert("responsable canot be empty...");
        return false;
    }
    if (nss === "" || nss === null) {
        alert("nss canot be empty...");
        return false;
    }
    if (phone === "" || phone === null) {
        alert("phone canot be empty...");
        return false;
    }
    if (doctorResponsale === "" || doctorResponsale === null) {
        alert("Doctor canot be empty...");
        return false;
    }
    if (nurseResponsale === "" || nurseResponsale === null) {
        alert("Nurse canot be empty...");
        return false;
    }
    if (technicalServiceResponsale === "" || technicalServiceResponsale === null) {
        alert("Technical canot be empty...");
        return false;
    }
    var inputsGenre = document.getElementById('inputGenre').childNodes;
    var genreValue;
    for (var i = 0; i < inputsGenre.length; i++) {
        if (inputsGenre[i].nodeName === "INPUT") {
            if (inputsGenre[i].checked === true) {
                genreValue = inputsGenre[i].defaultValue;
            }
        }
    }

    return true;
}
