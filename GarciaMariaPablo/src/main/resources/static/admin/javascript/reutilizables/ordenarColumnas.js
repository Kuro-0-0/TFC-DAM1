function ordenarPor(campo) {
    const url = new URL(window.location.href); // obtenemos el url actual
    const actualCampo = url.searchParams.get("ordenarPor"); // almacenamos el valor del parametro ordenarPor
    const ordenActual = url.searchParams.get("ordenAsc") === "true"; // si el valor del parametro ordenAsc es un String("true"), entonces la variable guardara un true, si no, ps false.

    // Alternar orden si el campo que estamos tratando de modifcar es el mismo que se encuentra en la url.
        // Si no es actual, manda true, por ende, siempre empezaran siendo de mas a menos.
    const nuevoOrdenAsc = (actualCampo === campo) ? !ordenActual : true;

    // Manejamos los cambios de URL de manera manual para evitar que la pagina se actualize y perdamos el segundo campo, por eso no podemos usar aqui el updateUrlParam();
    url.searchParams.set("ordenarPor", campo); 
    url.searchParams.set("ordenAsc", nuevoOrdenAsc);
    window.location.href = url.toString();
}

// Mostrar ícono visual del campo ordenado actualmente
window.addEventListener("DOMContentLoaded", () => {

    document.getElementById("paginaSeleccionada").value = document.getElementById("paginaSeleccionada").dataset.paginaactual

    const url = new URL(window.location.href);
    const campo = url.searchParams.get("ordenarPor");
    const asc = url.searchParams.get("ordenAsc") === "true";
    if (campo) {
        const icono = document.getElementById(`icon-${campo}`);
        if (icono) {
            icono.textContent = asc ? "↑" : "↓";
        }
    }
});