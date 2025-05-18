document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('confirmarEliminacionModal');

    modal.addEventListener('show.bs.modal', function (event) {
        console.log("Modal show event triggered");
        
        const button = event.relatedTarget;
        const incidenciaId = button.getAttribute('data-incidencia-id');
        const incidenciaTitulo = button.getAttribute('data-incidencia-titulo');

        document.getElementById('incidenciaTitulo').textContent = "Incidencia:" + incidenciaTitulo;
        document.getElementById('formEliminarIncidencia').action = `/user/incidencias/${incidenciaId}`;
    });
});