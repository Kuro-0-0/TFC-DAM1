const inputBusqueda = document.getElementById('busqueda');

inputBusqueda.addEventListener('input', function () {
    const filtro = this.value.toLowerCase();
    const secciones = document.querySelectorAll('#adminAccordion .accordion-item');

    secciones.forEach(seccion => {
        const titulo = seccion.querySelector('.accordion-button').textContent.toLowerCase();
        if (titulo.includes(filtro)) {
            seccion.style.display = '';
        } else {
            seccion.style.display = 'none';
        }
    });
});