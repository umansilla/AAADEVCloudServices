/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

console.log("IoT");
var abdolutePath = getAbsolutePath();
getCurrentState();
setInterval(getCurrentState, 5000);
/*
 * Variables 
 */

let ledAzul;
let ledAmarillo;
let ledRojo;

/*
 * Eventos
 */

document.getElementById('blueLight').addEventListener('click', function (e) {
    e.preventDefault();
    console.log("Blue");
    var elementLed = document.getElementById('blueLight');
    var elementoImag = elementLed.children[0].children[0].currentSrc;
    var ultimoelemento = elementoImag.substr(elementoImag.lastIndexOf('/') + 1);
    console.log(ultimoelemento);
    if (ultimoelemento === "bombillaApagada.png") {
        var srcElement = elementLed.children[0].children[0];
        srcElement.src = "img/bombillaPrendidaAzul.png";
        ledAzul = "000";
        updateState(ledAzul, ledAmarillo, ledRojo);
    }
    if (ultimoelemento === "bombillaPrendidaAzul.png") {
        var srcElement = elementLed.children[0].children[0];
        srcElement.src = "img/bombillaApagada.png";
        ledAzul = "001";
        updateState(ledAzul, ledAmarillo, ledRojo);
    }

});

document.getElementById('yellowLight').addEventListener('click', function (e) {
    e.preventDefault();
    console.log("Yellow");
    var elementLed = document.getElementById('yellowLight');
    var elementoImag = elementLed.children[0].children[0].currentSrc;
    var ultimoelemento = elementoImag.substr(elementoImag.lastIndexOf('/') + 1);
    if (ultimoelemento === "bombillaApagada.png") {
        var srcElement = elementLed.children[0].children[0];
        srcElement.src = "img/bombillaPrendida.png";
        ledAmarillo = "000";
        updateState(ledAzul, ledAmarillo, ledRojo);
    }
    if (ultimoelemento === "bombillaPrendida.png") {
        var srcElement = elementLed.children[0].children[0];
        srcElement.src = "img/bombillaApagada.png";
        ledAmarillo = "001";
        updateState(ledAzul, ledAmarillo, ledRojo);
    }
});

document.getElementById('redLight').addEventListener('click', function (e) {
    e.preventDefault();
    console.log("Red");
    var elementLed = document.getElementById('redLight');
    var elementoImag = elementLed.children[0].children[0].currentSrc;
    var ultimoelemento = elementoImag.substr(elementoImag.lastIndexOf('/') + 1);
    if (ultimoelemento === "bombillaApagada.png") {
        var srcElement = elementLed.children[0].children[0];
        srcElement.src = "img/bombillaPrendidaRoja.png";
        ledRojo = "000";
        updateState(ledAzul, ledAmarillo, ledRojo);
    }
    if (ultimoelemento === "bombillaPrendidaRoja.png") {
        var srcElement = elementLed.children[0].children[0];
        srcElement.src = "img/bombillaApagada.png";
        ledRojo = "001";
        updateState(ledAzul, ledAmarillo, ledRojo);
    }
});


/*
 * Funciones
 */

function getCurrentState() {
    var data = null;
    var xhr = new XMLHttpRequest();
    xhr.withCredentials = false;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            console.log(this.responseText);
            var object = this.responseText;
            var split = object.split("\n");
            var valueLedAzul = split[0].split(",");
            var valueLedAmarillo = split[1].split(",");
            var valueLedRojo = split[2].split(",");
            ledAzul = valueLedAzul[1];
            ledAmarillo = valueLedAmarillo[1];
            ledRojo = valueLedRojo[1];
            var elementLedAzul = document.getElementById('blueLight');
            var srcElementAzul = elementLedAzul.children[0].children[0];
            if (ledAzul === "000") {
                srcElementAzul.src = "img/bombillaPrendidaAzul.png";
            }
            if (ledAzul === "001") {
                srcElementAzul.src = "img/bombillaApagada.png";
            }
            var elementLedAmarillo = document.getElementById('yellowLight');
            var srcElementAmarillo = elementLedAmarillo.children[0].children[0];
            if (ledAmarillo === "000") {
                srcElementAmarillo.src = "img/bombillaPrendida.png";
            }
            if (ledAmarillo === "001") {
                srcElementAmarillo.src = "img/bombillaApagada.png";
            }
            var elementLedRoja = document.getElementById('redLight');
            var srcElementRoja = elementLedRoja.children[0].children[0];
            if (ledRojo === "000") {
                srcElementRoja.src = "img/bombillaPrendidaRoja.png";
            }
            if (ledRojo === "001") {
                srcElementRoja.src = "img/bombillaApagada.png";
            }
        }
    });

    xhr.open("GET", abdolutePath + "ArduinoReadFile");
    xhr.send(data);
}

function updateState(ledAzul, ledAmarillo, ledRojo) {
    document.getElementById('loaderDisplay').classList.add('is-active');
    document.getElementById("loaderDisplay").setAttribute("data-text", "Mandando Datos");
    var data = null;

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = false;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            console.log(this.responseText);
            document.getElementById("loaderDisplay").classList.remove("is-active");
            document.getElementById("loaderDisplay").setAttribute("data-text", "");
        }
    });

    xhr.open("POST", abdolutePath + "ArduinoReadFile?Led05=" + ledAzul + "&Led06=" + ledAmarillo + "&Led07=" + ledRojo);

    xhr.send(data);
}

function getAbsolutePath() {
    var loc = window.location;
    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
    return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
