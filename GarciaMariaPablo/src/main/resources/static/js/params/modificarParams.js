// Función que se activa cuando se cambia el valor de "Elementos por página"
function handleInputChange() {
    const perPage = document.getElementById('perPage').value; // Obtenemos el valor del elemento con id perPage
    updateUrlParam('perPage', perPage); // Modiifcamos el parametro perPage y le metemos el valor encontrado antes.
}

// Función que se activa cuando se cambia la página
function changePage(element) {
    updateUrlParam('pagina', element.dataset.page); // Modificamos el parametro pagina y le metemos el dataset-page del elemento que llama a la funcion.
}

// Esta función maneja el envío del formulario, pero sin recargar la página
function handleFormSubmit(event) {
    event.preventDefault(); // Prevenimos el envío normal del formulario para que la pagina no se refresque y perdamos el resto de la url.
    handleInputChange() // Llamamos a la otra funcion para que modifique el perpage
}

function goToPage() {
    const input = document.getElementById('paginaSeleccionada'); // pillamos el input paginaSeleccionada.
    let page = parseInt(input.value); // almacenamos como int el valor del input anterior.
    const max = parseInt(input.getAttribute('max')); // obtenemos como int el atributo max del input.
    const min = parseInt(input.getAttribute('min')); // obtenemos como int el atributo max del input.

    // Comprobamos que no haya ningun valor extraño en la pagina y si lo hay lo modificamos a lo que se ajusta a nuestras necesidades.
    if (page < min) page = min; 
    if (page > max) page = max;

    updateUrlParam('pagina', page); // Llamamos a la funcion para que modifique la pagina de nuestro HTML.
}

// Actualiza o añade parámetros en la URL sin recargar la página
function updateUrlParam(param, value) {
    const url = new URL(window.location.href); // alamacenamos en una variable la url actual.
    url.searchParams.set(param, value); // Añadimos o modificamos el parametro que indicamos en los atributos de la funcion.
    window.history.pushState({}, '', url); // Actualiza la URL sin recargar
    window.location.reload(); // recargamos la pagina.
}

window.addEventListener("DOMContentLoaded", () => { // Cargamos esto aqui por que este js tiene la gestion de la paginacion.
    document.getElementById("paginaSeleccionada").value = document.getElementById("paginaSeleccionada").dataset.paginaactual // Basicamente, modificamos el valor de paginaSeleccionada, para que corresponda con la info enviada desde el back.
    }
)
