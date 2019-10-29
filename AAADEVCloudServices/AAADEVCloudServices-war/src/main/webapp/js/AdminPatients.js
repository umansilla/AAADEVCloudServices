/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


console.log("AdminPatients.js");

/*
 * Variables
 */
var absolutepath = getAbsolutePath();
console.log(absolutepath);

/*
 * Eventos
 */
document.getElementById('createPatient').addEventListener('click', function (e) {
    e.preventDefault();
    console.log("Create Patient");
    window.location.replace(absolutepath + "?CreatePatient=go");
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

/*
 * Funciones
 */

function deletePatient(_this) {
    console.log(_this.parentNode.parentNode.id);
    console.log(_this.parentNode.parentNode.children[0].innerText);
    var idPatientToDelete = _this.parentNode.parentNode.id;
    var patientNameToDelete = _this.parentNode.parentNode.children[0].innerText;
    var tdObject = _this.parentNode.parentNode;
    Swal.fire({
        title: '<strong>Are you sure you want to discharge the next patient?</strong>',
        type: 'info',
        html: '<div class="row justify-content-center"><div class="col-md-12">' +
                '<h3> ' + patientNameToDelete + ' </h3>' +
                '</div>' +
                '</div>' +
                '<hr>',

        showCancelButton: true,
        focusConfirm: false,
        confirmButtonText:
                '<i class="glyphicon glyphicon-thumbs-up" id="upload"></i> OK!',
        cancelButtonText:
                '<i class="glyphicon glyphicon-thumbs-down id="cancel"></i>',
        cancelButtonAriaLabel: 'Thumbs down'
    }).then((result) => {
        if (result.value) {
            deletePatientConfirm();
        }
        ;
    });

    function deletePatientConfirm() {
        document.getElementById('loaderDisplay').classList.add('is-active');
        document.getElementById("loaderDisplay").setAttribute("data-text", "Deleting patient");
        var data = new FormData();
        data.append("action", "DeletePatient");
        data.append("idpatient", idPatientToDelete);

        var xhr = new XMLHttpRequest();
        xhr.withCredentials = true;

        xhr.addEventListener("readystatechange", function () {
            if (this.readyState === 4) {
                var jsonObject = JSON.parse(this.responseText);
                if (jsonObject.status === "ok") {
                    document.getElementById("loaderDisplay").classList.remove("is-active");
                    document.getElementById("loaderDisplay").setAttribute("data-text", "");
                    var elementToDelete = tdObject;
                    elementToDelete.parentNode.removeChild(elementToDelete);
                    Swal.fire(
                            'Good job!',
                            'The patient ' + patientNameToDelete + ' has been discharged correctly!',
                            'success'
                            );

                }
                if (jsonObject.status === "error") {
                    document.getElementById("loaderDisplay").classList.remove("is-active");
                    document.getElementById("loaderDisplay").setAttribute("data-text", "");
                    Swal({
                        type: 'error',
                        title: 'Error',
                        text: 'Error Deleting the Pacient'

                    });
                }
            }
        });

        xhr.open("POST", absolutepath);
        xhr.send(data);
    }
}

function getAbsolutePath() {
    var loc = window.location;
    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 15);
    return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

