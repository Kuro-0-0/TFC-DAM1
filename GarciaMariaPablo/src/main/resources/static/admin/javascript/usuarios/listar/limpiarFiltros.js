function resetForm() {
    // Resetea el formulario
    document.getElementById("filtroForm").reset();

    // Reinicia los valores predeterminados
    document.getElementById("filtroUsername").value = '';
    document.getElementById("filtroNombre").value = '';
    document.getElementById("filtroApellidos").value = '';
    document.getElementById("filtroRol").value = '';

    // Para los checkboxes de roles
    const checkboxesRoles = document.querySelectorAll('input[name="rolesSeleccionados"]');
    checkboxesRoles.forEach((checkbox) => checkbox.checked = false);

    // Desmarcar el switch de mostrar ocultos
    document.getElementById("mostrarOcultos").checked = false;

    setTimeout(() => {
        filtrarDropdown("filtroRol", "listaRoles");
    }, 200);
}