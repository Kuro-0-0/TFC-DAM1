function filtrarDropdown(inputId, listaId) {
    const input = document.getElementById(inputId); // almacenamos el input en el que filtramos el contenido de las listas.
    const lista = document.getElementById(listaId); // almacenamos la lista con las opciones que tenemos.
    const filtro = input.value.toLowerCase(); // almacenamos como filtro el valor del input en minusculas.

    [...lista.querySelectorAll("li")].forEach(item => { // iteramos sobre todos los li que haya anidados dentro de la lista.
        const texto = item.dataset.label.toLowerCase(); // almacenamos el dataset-label de el LI actual en minusculas.
        item.style.display = texto.includes(filtro) ? "" : "none"; // mostramos u ocultamos elementos de la lista en funcion del resultado.
    });
}

if (exist("filtroReportante")) { // Añadimos esta linea para evitar que la web pete cuando no se encuentre el filtro Especificado.
    document.getElementById("filtroReportante").addEventListener("input", () => { // añadimos un listener para que siempre que se haga un input en el elemento filtroReportante, se llame a la funcion de filtrar.
        filtrarDropdown("filtroReportante", "listaReportantes");
    });
}

/** A partir de aqui todos son como el de la linea 12 - 17 */

if (exist(("filtroEstado"))) {
    document.getElementById("filtroEstado").addEventListener("input", () => {
        filtrarDropdown("filtroEstado", "listaEstados");
    });
}

if (exist("filtroTecnico")) {
    document.getElementById("filtroTecnico").addEventListener("input", () => {
        filtrarDropdown("filtroTecnico", "listaTecnicos");
    });
}

