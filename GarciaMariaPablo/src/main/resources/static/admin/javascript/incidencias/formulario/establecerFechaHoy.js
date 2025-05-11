// Espera a que el DOM esté completamente cargado
document.addEventListener("DOMContentLoaded", function() { // Cuando se cargue el HTML
    const fechaHoyInput = document.getElementById("fechaCreacion"); // Pillamos el input de fecha, que estara hidden.
    const hoy = new Date(); // almacenamos en una variable la fecha de hoy

    // Formato YYYY-MM-DD
    const fechaFormateada = hoy.toISOString().split('T')[0]; // Formateamos la fecha

    fechaHoyInput.value = fechaFormateada; // Almacenamos el valor.
    /**Todo esto podria hacerlo desde el back, pero me dio por hacerlo aqui. */
});
