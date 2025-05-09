// Función para aplicar la búsqueda manteniendo otros parámetros
function aplicarBusqueda() {
    const buscar = document.getElementById('buscarInput').value; // Obtenemos el valor que el usuario escribió en el input de búsqueda
    updateUrlParam('buscar', buscar); // Actualizamos el parametro de buscar.
}

// Función para limpiar la búsqueda manteniendo otros parámetros
function limpiarBusqueda() {
    updateUrlParam('buscar', ''); // Vaciamos el valor del parametro buscar de la url
}

// Permitir búsqueda al presionar Enter
document.getElementById('buscarInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') { // Si el usuario pulsa Enter
        aplicarBusqueda(); // llamamos a la función que actualiza la búsqueda
    }
});
