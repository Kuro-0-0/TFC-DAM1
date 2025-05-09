// Al cargar la pagina
document.addEventListener('DOMContentLoaded', function() {
    const menu = document.getElementById('listaRoles'); // almacenamos la lista de roles en una variable
    if (menu) { // si se ha alamacenado bien
        menu.querySelectorAll('.dropdown-item').forEach(item => { // Iteramos sobre todos los elementos que tengan la clase dropdown-item dentro del menu.
            item.addEventListener('click', function(e) { // creamos un listener para cuando se les haga click a cada uno de los elementos.
                e.preventDefault(); // evitamos el comportamiento normal

                const value = this.getAttribute('data-value'); // almacenamos los datos almacenamos en el data-value
                const text = this.textContent; // almacenamos el contenido de texto
                const dropdown = this.closest('.dropdown'); // obtenemos el elemento con clase dropdown mas cercano al elemento sobre el que estamos iterando.
                
                if (dropdown) { // si existe el dropdown
                    const button = dropdown.querySelector('button'); // pillamos los elementos button dentro del dropdown
                    const input = dropdown.querySelector('input[type="hidden"]'); // pillamos los inputs hiddens dentro del dropdown
                    
                    if (button) button.textContent = text; // le metemos al boton el texto del rol que seleccionamos
                    if (input) input.value = value; // le metemos al input el valor almacenado en data-value del elemento pa poder mandarlo a java.
                }
            });
        });
    }
});