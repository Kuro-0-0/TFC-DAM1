document.addEventListener('DOMContentLoaded', function () {
    const editarEstadoModal = document.getElementById('editarEstadoModal');
    editarEstadoModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const incidenciaId = button.getAttribute('data-incidencia-id');
        document.getElementById('estado').value = button.getAttribute('data-estado-valor');
        document.getElementById('formEditarEstado').action = '/tech/incidencias/modificar/' + incidenciaId;
    });
});