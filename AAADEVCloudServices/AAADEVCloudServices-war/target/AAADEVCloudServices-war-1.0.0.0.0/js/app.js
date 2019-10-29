/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

console.log("Index");

/*
 * Variables
 */
var absolutepath = getAbsolutePath();
reloadAudio();

/*
 * Eventos
 */
document.getElementById('getAudio').addEventListener('click', function (e) {
    e.preventDefault();
    console.log("Push");
    var data = null;

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {

            var jsonObj = JSON.parse(this.responseText);
            console.log(jsonObj);
            if (jsonObj.hasOwnProperty("audioContent")) {

                var audioTag = document.getElementById('audioTag');
                audioTag.src = "data:audio/ogg;base64," + jsonObj.audioContent.toString();

                Swal.fire(
                        'Good job!',
                        'Se ha modificado el audio',
                        'success'
                        );
            }
        }
    });

    xhr.open("GET", absolutepath + "TTS");
    xhr.send(data);

});

document.getElementById('submitButton').addEventListener('click', function (e) {
    e.preventDefault();
    var data = new FormData();
    data.append("text", document.getElementById('text').value);
    data.append("voice", document.getElementById('voice').value);
    data.append("voiceName", document.getElementById('voiceName').value);

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            console.log(this.responseText);
            var jsonObj = JSON.parse(this.responseText);
            createAudio(jsonObj.apikey, jsonObj.text, jsonObj.voice, jsonObj.voiceName);
        }
    });

    xhr.open("POST", absolutepath + "/ExampleTTS");
    xhr.send(data);



});

document.getElementById('submitButtonDisk').addEventListener('click', function (e) {
    e.preventDefault();
    console.log("Disk");
    var data = new FormData();
    data.append("text", document.getElementById('textDisk').value);
    data.append("voice", document.getElementById('voiceDisk').value);
    data.append("voiceName", document.getElementById('voiceNameDisk').value);

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            var jsonObj = JSON.parse(this.responseText);
            console.log(jsonObj);
            writeOnDisk(jsonObj.apikey, jsonObj.text, jsonObj.voice, jsonObj.voiceName);
        }
    });

    xhr.open("POST", absolutepath + "/ExampleTTS");
    xhr.send(data);

});

/*
 * Funciones
 */

function getAbsolutePath() {
    var loc = window.location;
    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
    return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function reloadAudio() {
    var data = null;

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            console.log("Reload Audio");
            var audioTag = document.getElementById('audioTagSTTDisk');
            audioTag.src = "data:audio/ogg;base64," + this.responseText;
        }
    });

    xhr.open("GET", absolutepath + "WriteOnDiskTTS");
    xhr.send(data);
}

function writeOnDisk(apiCyp, textCyp, voiceCyp, voiceNameCyp) {
    var data = new FormData();

    data.append("apiKey", apiCyp);
    data.append("text", textCyp);
    data.append("voice", voiceCyp);
    data.append("voiceName", voiceNameCyp);

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            var jsonObj = JSON.parse(this.responseText);
            console.log(jsonObj);
            if (jsonObj.status === "ok") {
                reloadAudio();
                Swal.fire(
                        'Good job!',
                        'Se ha Grabado el audio',
                        'success'
                        );
            }
        }
    });
    xhr.open("POST", absolutepath + "WriteOnDiskTTS");
    xhr.send(data);
}

function createAudio(apiCyp, textCyp, voiceCyp, voiceNameCyp) {

    var data = new FormData();

    data.append("apiKey", apiCyp);
    data.append("text", textCyp);
    data.append("voice", voiceCyp);
    data.append("voiceName", voiceNameCyp);

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            var jsonObj = JSON.parse(this.responseText);
            console.log(jsonObj);
            if (jsonObj.hasOwnProperty("audioContent")) {

                var audioTag = document.getElementById('audioTagSTT');
                audioTag.src = "data:audio/ogg;base64," + jsonObj.audioContent.toString();

                Swal.fire(
                        'Good job!',
                        'Se ha modificado el audio',
                        'success'
                        );
            }
        }
    });
    xhr.open("POST", absolutepath + "TTS");
    xhr.send(data);

}