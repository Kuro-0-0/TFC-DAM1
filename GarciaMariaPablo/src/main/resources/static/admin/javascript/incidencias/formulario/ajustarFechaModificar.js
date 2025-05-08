document.addEventListener("DOMContentLoaded", function () { // Cuando la pagina cargue
    const fecha = document.getElementById("fechaCreacion"); // obtenemos el input en el que se escribe la fecha.
    fecha.value = fecha.getAttribute("data-fecha") // metemos en el valor, el contenido del dataSet
    /**Tecnicamente esto no haria falta, pero como no me funcionaba de ninguna manera pa mostrar la fecha en el editar, pues acabe metiendole esto */
});