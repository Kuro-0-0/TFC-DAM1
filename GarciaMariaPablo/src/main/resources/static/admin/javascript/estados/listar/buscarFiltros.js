// Función para aplicar la búsqueda manteniendo otros parámetros
function aplicarBusqueda() {
    const buscar = document.getElementById('buscarInput').value;
    updateUrlParam('buscar', buscar);
}

// Función para limpiar la búsqueda manteniendo otros parámetros
function limpiarBusqueda() {
    updateUrlParam('buscar', '');
}

// Permitir búsqueda al presionar Enter
document.getElementById('buscarInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        aplicarBusqueda();
    }
});