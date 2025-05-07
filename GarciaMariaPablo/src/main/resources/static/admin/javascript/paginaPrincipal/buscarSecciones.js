/* Almacenar en una variable el input que se usara para filtar. */
const inputBusqueda = document.getElementById('busqueda');

/* Un listener para que cuando se mande el contenido del input se ejecute una funcion */
inputBusqueda.addEventListener('input', function () {
    /* Almacenar en una variable el valor buscado como minusculas */
    const filtro = this.value.toLowerCase();
    /* Almacenar en una variables todos los elementos del HTML que tengan alguno de los ID o las clases buscadas */
    const secciones = document.querySelectorAll('.accordion-item');

    /* Iteramos sobre las secciones encontradas en la linea 9*/
    secciones.forEach(seccion => {
        /*  Almacenamos en la variable titulo, el texto como minuscula de los elementos cuya clase sea accordion-button dentro de las secciones
            encontradas anteriormente. */
        const titulo = seccion.querySelector('.accordion-button').textContent.toLowerCase();
        
        // Si titulo tiene lo que se escribio en el input el display se borra para que se muestre, si no, se oculta el elemento.
        if (titulo.includes(filtro)) {
            seccion.style.display = '';
        } else {
            seccion.style.display = 'none';
        }
    });
});