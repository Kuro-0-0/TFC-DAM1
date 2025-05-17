document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('crearIncidenciaForm');
    const reportanteInput = document.getElementById('reportante');
    const reportanteInputMod = document.getElementById('reportante.id');
    const errorMessageDiv = document.querySelector('.alert.alert-danger');

    form.addEventListener('submit', function (event) {
        const reportanteVal = reportanteInput ? reportanteInput.value.trim() : '';
        const reportanteModVal = reportanteInputMod ? reportanteInputMod.value.trim() : '';

        console.log(reportanteVal);
        console.log(reportanteModVal);

        if (reportanteVal === '' && reportanteModVal === '') {
            event.preventDefault();
            const errorText = document.getElementById("errorTextMessage");
            errorText.textContent = 'El campo "Reportante" no puede estar vacío.';
            errorMessageDiv.classList.remove('d-none');
        }
    });
});