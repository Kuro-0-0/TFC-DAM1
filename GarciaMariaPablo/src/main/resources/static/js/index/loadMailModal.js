document.addEventListener('DOMContentLoaded', function () {
    const botonesContratar = document.querySelectorAll('[data-bs-target="#contactModal"]');

    botonesContratar.forEach(boton => {
        boton.addEventListener('click', function () {
            const card = this.closest('.card');
            const planTitulo = card.querySelector('.card-header h4').textContent.trim();
            document.getElementById('planSeleccionado').value = planTitulo;
        });
    });
});