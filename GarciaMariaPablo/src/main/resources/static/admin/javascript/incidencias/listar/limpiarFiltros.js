function resetForm() {
    // Resetea el formulario
    document.getElementById("filtroForm").reset();

    // Colocamos los datos en defaultValue de manera individual por que con el .reset no funciona bien.
        // Con defaultValue me refiero a el valor que tendrian por defecto :)
    document.getElementById("filtroTitulo").value = '';
    document.getElementById("filtroUbicacion").value = '';
    document.getElementById("filtroReportante").value = '';
    document.getElementById("filtroEstado").value = '';
    document.getElementById("mostrarDesactivados").checked = false;
    
    
    // Iteramos por las listas y desmarcamos todos los checkboxes.
    const checkboxesReportantes = document.querySelectorAll('input[name="reportantesSeleccionados"]');
    checkboxesReportantes.forEach((checkbox) => checkbox.checked = false);
    
    const checkboxesEstados = document.querySelectorAll('input[name="estadosSeleccionados"]');
    checkboxesEstados.forEach((checkbox) => checkbox.checked = false);
    
    
    filtrarDropdown('filtroReportante', 'listaReportantes');
    filtrarDropdown("filtroEstado", "listaEstados");
}