document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('crearIncidenciaForm');
    const reportanteInput = document.getElementById('reportante');
    const errorMessageDiv = document.querySelector('.alert.alert-danger');

    form.addEventListener('submit', function (event) {
        if (!reportanteInput || !reportanteInput.value.trim()) {
            event.preventDefault();
            const errorText = document.getElementById("errorTextMessage");
            errorText.textContent = 'El campo "Reportante" no puede estar vacío.';
            errorMessageDiv.classList.remove('d-none');
        }
    });
});