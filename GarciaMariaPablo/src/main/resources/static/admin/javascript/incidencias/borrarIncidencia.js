// Escuchar cuando se abre el modal
document.getElementById('confirmarEliminacionModal').addEventListener('shown.bs.modal', function (event) {
    // Obtener el botón que activó el modal
    var button = event.relatedTarget;
    
    // Obtener el id de la incidencia desde el atributo data-id
    var incidenciaId = button.getAttribute('data-id');
    
    // Actualizar el action del formulario con el id de la incidencia
    var actionUrl = '/incidencias/' + incidenciaId;
    // Usar el DOM para actualizar el atributo 'action' del formulario
    document.getElementById('borrarIncidencia').action = actionUrl;
});
