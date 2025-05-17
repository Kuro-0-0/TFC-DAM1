// Datos de ejemplo que vendrían del backend
var estadosData = {
    estados: [ // Lista de estados con sus datos
        {
            nombre: "Pendiente",
            colorFondo: "#fff3cd",
            colorTexto: "#856404",
            cantidad: 18,
            porcentaje: 43
        },
        {
            nombre: "En Proceso",
            colorFondo: "#cce5ff",
            colorTexto: "#004085",
            cantidad: 7,
            porcentaje: 17
        },
        {
            nombre: "Resuelto",
            colorFondo: "#d4edda",
            colorTexto: "#155724",
            cantidad: 17,
            porcentaje: 40
        },
        {
            nombre: "Cancelado",
            colorFondo: "#f8d7da",
            colorTexto: "#721c24",
            cantidad: 2,
            porcentaje: 5
        },
        {
            nombre: "Rechazado",
            colorFondo: "#e2e3e5",
            colorTexto: "#383d41",
            cantidad: 3,
            porcentaje: 7
        }
    ],
    total: 47 // Total de incidencias sumadas
};

// Convierte un color HEX a formato rgba con opacidad
function hexToRGBA(hex, alpha) {
    hex = hex.replace(/^#/, ''); // Quitamos el #
    let r = parseInt(hex.substring(0, 2), 16); // Rojo
    let g = parseInt(hex.substring(2, 4), 16); // Verde
    let b = parseInt(hex.substring(4, 6), 16); // Azul
    return `rgba(${r}, ${g}, ${b}, ${alpha})`; // Devolvemos el string en formato rgba
}

// Generamos las tarjetas con los estados y el total
function generarTarjetasEstados() {
    const container = document.getElementById('estadosContainer'); // Contenedor de tarjetas
    const scrollIndicator = document.getElementById('scrollIndicator'); // Indicador de scroll si hay muchas tarjetas

    container.innerHTML = ''; // Limpiamos el contenedor

    // Ocultamos el scroll si hay 4 o menos tarjetas
    scrollIndicator.style.display = estadosData.estados.length <= 4 ? 'none' : 'block';

    // Tarjeta de total
    const totalCard = document.createElement('div');
    totalCard.className = 'card stats-card flex-shrink-0 border-0';
    totalCard.style.backgroundColor = `rgba(20, 203, 97, 0.4)`; // Color verde con transparencia

    totalCard.innerHTML = `
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-start">
            <div>
                <h5 class="card-title text-dark">Total</h5>
                <p class="fs-3 fw-bold text-dark" id="TotalNum">${estadosData.total}</p>
            </div>
            <i class="bi bi-circle-half text-dark fs-4"></i>
        </div>
    </div>
`;
    container.appendChild(totalCard); // Añadimos al DOM

    // Tarjetas por cada estado
    estadosData.estados.forEach(estado => {
        const card = document.createElement('div');
        card.className = 'card stats-card flex-shrink-0 border-0';
        card.style.backgroundColor = hexToRGBA(estado.colorFondo, 0.4); // Color del fondo con opacidad

        card.innerHTML = `
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-start">
            <div>
                <h5 class="card-title" style="color: ${estado.colorTexto}">${estado.nombre}</h5>
                <p class="fs-3 fw-bold" style="color: ${estado.colorTexto}" id="${estado.nombre.replace(/\s+/g, '')}Num">${estado.cantidad}</p>
            </div>
            <i class="bi bi-circle-fill fs-4" style="color: ${estado.colorTexto}"></i>
        </div>
    </div>
`;
        container.appendChild(card); // Añadimos la tarjeta al contenedor
    });
}

// Generamos las barras de progreso según porcentaje de cada estado
function generarBarrasProgreso() {
    const container = document.getElementById('progressBarsContainer');
    container.innerHTML = ''; // Limpiamos el contenedor

    estadosData.estados.forEach(estado => {
        const progressDiv = document.createElement('div');
        progressDiv.className = 'mb-3';

        progressDiv.innerHTML = `
                <div class="d-flex justify-content-between mb-1">
                    <span class="fw-semibold text-dark" id="${estado.nombre.replace(/\s+/g, '')}">
                        ${estado.nombre}
                    </span>
                    <small class="text-muted">${estado.porcentaje}%</small>
                </div>
                <div class="progress" style="height: 10px;" role="progressbar">
                    <div class="progress-bar rounded" 
                        style="width: ${estado.porcentaje}%; background-color: ${estado.colorFondo}; color: ${estado.colorTexto}"
                        title="Progreso de ${estado.nombre}: ${estado.porcentaje}%">
                    </div>
                </div>
            `;


        container.appendChild(progressDiv); // Añadimos la barra al contenedor
    });
}

// Generamos la leyenda de colores con badges
function generarLeyendaEstados() {
    const container = document.getElementById('leyendaEstados');
    container.innerHTML = ''; // Limpiamos el contenedor

    estadosData.estados.forEach(estado => {
        const badge = document.createElement('span');
        badge.className = 'badge fw-semibold';
        badge.style.backgroundColor = estado.colorFondo;
        badge.style.color = estado.colorTexto;
        badge.textContent = estado.nombre;

        container.appendChild(badge); // Añadimos el badge
    });
}

// Actualiza el número total en el resumen
function actualizarTotal() {
    document.getElementById('totalIncidencias').textContent = estadosData.total;
}

// Al cargar la página generamos todo
document.addEventListener('DOMContentLoaded', function () {
    generarTarjetasEstados();
    generarBarrasProgreso();
    generarLeyendaEstados();
    actualizarTotal();
});

// Actualiza todo cuando llegan nuevos datos
function actualizarDatos(nuevosDatos) {
    estadosData = nuevosDatos;
    generarTarjetasEstados();
    generarBarrasProgreso();
    generarLeyendaEstados();
    actualizarTotal();
}

// Procesamos las incidencias que vienen del backend y calculamos cantidades y porcentajes
function procesarIncidencias(incidencias) {
    const estados = {}; // Objeto temporal para agrupar los estados
    let total = 0; // Contador de total

    // Recorremos todas las incidencias
    incidencias.forEach(element => {
        const item = element["estado"];
        const key = item.nombre;

        // Si el estado aún no está en el objeto, lo añadimos
        if (!estados[key]) {
            estados[key] = {
                nombre: item.nombre,
                colorFondo: item.colorFondo,
                colorTexto: item.colorTexto,
                cantidad: 0,
                porcentaje: 0,
            };
        }
        estados[key].cantidad += 1; // Sumamos una incidencia a ese estado
    });

    // Sumamos el total de todas las incidencias
    for (const [key, value] of Object.entries(estados)) {
        total += value.cantidad;
    }

    // 1. Calculamos los porcentajes (truncados)
    let totalPorcentaje = 0;
    let maxKey = null;
    let maxCantidad = 0;

    for (const [key, value] of Object.entries(estados)) {
        value.porcentaje = Math.floor((value.cantidad * 100) / total); // Porcentaje truncado
        totalPorcentaje += value.porcentaje; // Sumamos para después ajustar

        // Guardamos cuál es el estado con más incidencias
        if (value.cantidad > maxCantidad) {
            maxCantidad = value.cantidad;
            maxKey = key;
        }
    }

    // 2. Si falta algún punto por el redondeo, se lo damos al estado con más incidencias
    if (totalPorcentaje < 100 && maxKey) {
        estados[maxKey].porcentaje += 100 - totalPorcentaje;
    }

    // Creamos el objeto con los datos procesados
    let datosBack = {
        estados: Object.values(estados),
        total
    }

    actualizarDatos(datosBack); // Actualizamos todo con los nuevos datos
}
