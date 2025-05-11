// Función modificada para manejar solo los dropdowns específicos
function configurarDropdownsEspecificos() {
    // IDs de los menús desplegables que queremos manejar para evitar que se usen las del header.
    const menusIds = ['listaEstados', 'listaTecnicos', 'listaReportantes'];

    menusIds.forEach(menuId => { // iteramos
        const menu = document.getElementById(menuId); // obtenemos el elemento
        if (menu) {
            menu.querySelectorAll('.dropdown-item').forEach(item => { // iteramos sobre todos los dropdown item del menu
                item.addEventListener('click', function (e) { // escuchamos cuadndo se clickea sobre uno de los elementos.
                    e.preventDefault(); // evitamos la funcion default del elemento

                    const value = this.getAttribute('data-value'); // almacenamos el data-value en un variable
                    const text = this.textContent; // almacenamos el texto en una variable
                    const dropdown = this.closest('.dropdown'); // ubicamos y almacenamos el elemento class=dropwon

                    if (dropdown) { // si dropdown existe
                        const button = dropdown.querySelector('button'); // obtenemos los elementos tipo button que esten anidados en el dropdown
                        const input = dropdown.querySelector('input[type="hidden"]'); // obtenemos los elementos tipo input hidden anidados en el dropdown

                        if (button) button.textContent = text; // modificamos el texto del boton para que sea vea con el nombre de lo que queremos.
                        if (input) input.value = value; // modificamos el valor del input para que al mancdar la solicitud al back, esta se guarde ok.
                    }
                });
            });
        }
    });
}

document.addEventListener('DOMContentLoaded', function () {
    configurarDropdownsEspecificos(); // llamamos al metodo para que cargue los listeners y funcione segun lo esperado.
});