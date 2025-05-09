document.addEventListener('DOMContentLoaded', function () {
    function abrirDropdown(inputId, listaId) {
        const input = document.getElementById(inputId);
        const dropdownMenu = document.getElementById(listaId);
        const dropdown = dropdownMenu.closest('.dropdown');
        const toggleBtn = dropdown.querySelector('[data-bs-toggle="dropdown"]');

        input.addEventListener('input', () => {
            dropdown.classList.add('show');
            toggleBtn.classList.add('show');
            dropdownMenu.classList.add('show');
        });

        // Opcional: cerrar si el input queda vacío
        input.addEventListener('blur', () => {
            setTimeout(() => {
                dropdown.classList.remove('show');
                toggleBtn.classList.remove('show');
                dropdownMenu.classList.remove('show');
            }, 200); // pequeño delay para permitir interacciones
        });
    }

    if (exist('filtroReportante') && exist('listaReportantes')) {
        abrirDropdown('filtroReportante', 'listaReportantes');
    }

    if (exist('filtroEstado') && exist('listaEstados')) {
        abrirDropdown('filtroEstado', 'listaEstados');
    }

    if (exist('filtroRol') && exist('listaRoles')) {
        abrirDropdown('filtroRol', 'listaRoles');
    }

});