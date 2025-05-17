document.addEventListener('DOMContentLoaded', function() {
    // Todos los IDs son bastante descriptivos.
    const colorFondo = document.getElementById('colorFondo');
    const colorTexto = document.getElementById('colorTexto');
    const vistaPrevia = document.getElementById('vistaPreviaEstadoContenido');
    const hexFondo = document.getElementById('hexFondo');
    const hexTexto = document.getElementById('hexTexto');
    const colorFondoPreview = document.getElementById('colorFondoPreview');
    const colorTextoPreview = document.getElementById('colorTextoPreview');
    const nombreEstado = document.querySelector('input[name="nombre"]'); // Obtenemos los inputs con name "nombre"

    function actualizarVistaPrevia() {
        const fondo = colorFondo.value; // Almacenamos el color de fondo en una variable.
        const texto = colorTexto.value; // Almacenamos el color de texto en una variable.
        
        // Actualizar vista previa
        vistaPrevia.style.backgroundColor = fondo; // cambiamos el color de fondo de la vista previa.
        vistaPrevia.style.color = texto; // cambiamos el color del texto de la vista previa.
        
        // Actualizar códigos HEX
        hexFondo.value = fondo; // Cambiamos el valor del HEX al lado del color picker de fondo. 
        hexTexto.value = texto; // Cambiamos el valor del HEX al lado del color picker de texto.
        
        // Actualizar previsualizaciones de color
        colorFondoPreview.style.backgroundColor = fondo; // Actualizar el color del cuadradito del picker de fondo
        colorTextoPreview.style.backgroundColor = texto; // Actualizar el color del cuadradito del picker de texto
        colorTextoPreview.style.color = texto; // Actualizar el color del texto del cuadradito del picker de texto
    }

    // Actualizar cuando cambian los colores
    colorFondo.addEventListener('input', actualizarVistaPrevia); // colocamos el listener para que cada vez que se modifique el input color, se actualize la vista previa.
    colorTexto.addEventListener('input', actualizarVistaPrevia); // lo mismo de arriba
    
    // Actualizar cuando cambia el nombre
    nombreEstado.addEventListener('input', function() {
        vistaPrevia.textContent = this.value || 'Texto de ejemplo';
    });

    // Inicializar de primeras.
    actualizarVistaPrevia();
});