document.addEventListener('DOMContentLoaded', function () {
    // Función para abrir el dropdown personalizado al escribir en el input
    function abrirDropdown(inputId, listaId) {
        const input = document.getElementById(inputId); // Obtenemos el input de texto
        const dropdownMenu = document.getElementById(listaId); // Obtenemos el menú del dropdown
        const dropdown = dropdownMenu.closest('.dropdown'); // Buscamos el contenedor más cercano con la clase dropdown
        const toggleBtn = dropdown.querySelector('[data-bs-toggle="dropdown"]'); // Obtenemos el botón que abre el dropdown

        input.addEventListener('input', () => { //Añadimos un listener pa cuando el usuario empieza a escribir
            dropdown.classList.add('show'); // Mostramos el contenedor principal del dropdown
            toggleBtn.classList.add('show'); // Mostramos el botón toggle (por si Bootstrap lo necesita)
            dropdownMenu.classList.add('show'); // Mostramos el menú de opciones
        });

        // Opcional: cerramos el dropdown si el input queda vacío o pierde el foco
        input.addEventListener('blur', () => {
            setTimeout(() => { // Pequeño retraso para que dé tiempo a hacer clic en alguna opción
                dropdown.classList.remove('show'); // Ocultamos el contenedor principal
                toggleBtn.classList.remove('show'); // Ocultamos el botón toggle
                dropdownMenu.classList.remove('show'); // Ocultamos el menú de opciones
            }, 200); 
        });
    }

    // Si existen los elementos necesarios, activamos el dropdown de reportante
    if (exist('filtroReportante') && exist('listaReportantes')) {
        abrirDropdown('filtroReportante', 'listaReportantes');
    }

    // Lo mismo de antes, pero para todos.
    if (exist('filtroEstado') && exist('listaEstados')) {
        abrirDropdown('filtroEstado', 'listaEstados');
    }

    if (exist('filtroRol') && exist('listaRoles')) {
        abrirDropdown('filtroRol', 'listaRoles');
    }
});
