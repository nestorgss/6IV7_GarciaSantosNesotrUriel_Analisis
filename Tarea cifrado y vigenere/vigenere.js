var vigenere = vigenere || (function () {
    var proceso = function (txt, desp, action) {
        var replace = (function () {
            var abc = [
                'a','b','c','d','e','f','g','h','i','j','k','l','m',
                'n','ñ','o','p','q','r','s','t','u','v','w','x','y','z'
            ];
            var longitud = abc.length;
            return function (c) {
                var i = abc.indexOf(c.toLowerCase());
                if (i !== -1) {
                    var pos = i;
                    if (action) {
                        pos += desp;
                        pos = pos % longitud;
                    } else {
                        pos -= desp;
                        if (pos < 0) {
                            pos += longitud;
                        }
                    }
                    return abc[pos];
                }
                return c;
            };
        })();
        var re = /([a-zñ])/ig;
        return String(txt).replace(re, function (match) {
            return replace(match);
        });
    };
    return {
        encode: function (txt, desp) {
            return proceso(txt, desp, true);
        },
        decode: function (txt, desp) {
            return proceso(txt, desp, false);
        }
    };
})();

function obindiceClave(letra) {
    var abc = [
        'a','b','c','d','e','f','g','h','i','j','k','l','m',
        'n','ñ','o','p','q','r','s','t','u','v','w','x','y','z'
    ];
    return abc.indexOf(letra.toLowerCase());
}

function codificar() {
    var texto = document.getElementById("texto").value;
    var clave = document.getElementById("clave").value;
    if (!texto.trim() || !clave.trim()) {
        alert("Por favor, rellena el texto y la clave antes de cifrar.");
        return;
    }
    if (clave.length > texto.length) {
        alert("La clave no puede ser más grande que el texto para cifrar.");
        return;
    }
    var resultado = "";
    var indiceClave = 0;
    var charsTexto = texto.split('');
    for (var i = 0; i < charsTexto.length; i++) {
        var desp = obindiceClave(clave.charAt(indiceClave));
        desp = (desp >= 27) ? desp % 27 : desp;
        resultado += vigenere.encode(charsTexto[i], desp);
        indiceClave++;
        if (indiceClave >= clave.length) {
            indiceClave = 0;
        }
    }
    var areaResultado = document.getElementById("resultado");
    if (areaResultado) {
        areaResultado.value = resultado;
    }
    return resultado;
}

function decodificar() {
    var texto = document.getElementById("texto").value;
    var clave = document.getElementById("clave").value;
    if (!texto.trim() || !clave.trim()) {
        alert("Por favor, rellena el texto y la clave antes de descifrar.");
        return;
    }
    if (clave.length > texto.length) {
        alert("La clave no puede ser más grande que el texto para descifrar.");
        return;
    }
    var resultado = "";
    var indiceClave = 0;
    var charsTexto = texto.split('');
    for (var i = 0; i < charsTexto.length; i++) {
        var desp = obindiceClave(clave.charAt(indiceClave));
        desp = (desp >= 27) ? desp % 27 : desp;
        resultado += vigenere.decode(charsTexto[i], desp);
        indiceClave++;
        if (indiceClave >= clave.length) {
            indiceClave = 0;
        }
    }
    var areaResultado = document.getElementById("resultado");
    if (areaResultado) {
        areaResultado.value = resultado;
    }
    return resultado;
}

function reiniciar() {
    var inputTexto = document.getElementById("texto");
    var inputClave = document.getElementById("clave");
    var areaResultado = document.getElementById("resultado");
    if (inputTexto) inputTexto.value = "";
    if (inputClave) inputClave.value = "";
    if (areaResultado) areaResultado.value = "";
}
