import random
from datetime import datetime, timedelta


# Datos reutilizables

incidencias_por_categoria = {
    "impresora": {
        "titulos": [
            "Impresora desconectada",
            "Error de impresión",
            "Fallo en impresora"
        ],
        "descripciones": [
            "La impresora no se encuentra disponible en red ni por cable.",
            "No responde al intentar imprimir.",
            "Falta papel en la impresora.",
            "La impresora muestra un error de conexión.",
            "El controlador de la impresora no está instalado correctamente."
        ]
    },
    "tablet": {
        "titulos": [
            "Tablet bloqueada",
            "Tablet no enciende",
            "Fallo de pantalla en tablet"
        ],
        "descripciones": [
            "La tablet no enciende, aunque esté cargada.",
            "La pantalla de la tablet no responde al tacto.",
            "No se puede acceder al sistema operativo.",
            "La batería de la tablet se agota rápidamente.",
            "La tablet se queda congelada en el arranque."
        ]
    },
    "proyector": {
        "titulos": [
            "Fallo en proyector",
            "Proyector fundido",
            "Proyector no responde"
        ],
        "descripciones": [
            "El proyector no responde al encenderlo.",
            "No se proyecta imagen, la lámpara está posiblemente fundida.",
            "El proyector se apaga solo a los pocos minutos.",
            "No detecta ninguna fuente de entrada.",
            "La imagen proyectada está distorsionada o borrosa."
        ]
    },
    "ordenador": {
        "titulos": [
            "Ordenador no enciende",
            "Pantalla azul",
            "Sistema operativo corrupto"
        ],
        "descripciones": [
            "El equipo no arranca, ni emite sonidos.",
            "Pantalla azul al iniciar el sistema.",
            "El sistema operativo no puede completar el arranque.",
            "Se reinicia continuamente sin cargar Windows.",
            "El disco duro no es detectado."
        ]
    },
    "red": {
        "titulos": [
            "Problemas de red",
            "Wi-Fi desconectado",
            "Fallo de conexión LAN"
        ],
        "descripciones": [
            "No hay acceso a internet en el aula.",
            "Los dispositivos no logran conectarse a la red Wi-Fi.",
            "La conexión por cable no funciona.",
            "Interrupciones constantes en la red.",
            "El punto de acceso Wi-Fi no aparece disponible."
        ]
    },
    "pantalla": {
        "titulos": [
            "Pantalla táctil fallando",
            "Monitor sin señal",
            "Pantalla negra"
        ],
        "descripciones": [
            "La pantalla táctil reacciona mal o no detecta toques.",
            "El monitor no recibe señal del equipo.",
            "La pantalla se queda en negro al iniciar.",
            "Flickering constante en la pantalla.",
            "Se observan líneas o parpadeos en la imagen."
        ]
    },
    "altavoces": {
        "titulos": [
            "Altavoces sin sonido",
            "Fallo de audio",
            "Audio distorsionado"
        ],
        "descripciones": [
            "No se escucha nada a través de los altavoces.",
            "El volumen es extremadamente bajo incluso al máximo.",
            "El audio suena entrecortado o distorsionado.",
            "Los altavoces emiten zumbidos o interferencias.",
            "No se detecta ningún dispositivo de audio."
        ]
    },
    "teclado": {
        "titulos": [
            "Teclado roto",
            "Teclas no funcionan",
            "Fallo de entrada de teclado"
        ],
        "descripciones": [
            "Algunas teclas del teclado no funcionan.",
            "El teclado no responde en absoluto.",
            "Las teclas responden con retraso.",
            "Teclas pulsadas producen caracteres incorrectos.",
            "El teclado se desconecta de forma intermitente."
        ]
    },
    "camara": {
        "titulos": [
            "Cámara sin imagen",
            "Fallo en cámara de seguridad",
            "Cámara no detectada"
        ],
        "descripciones": [
            "No hay señal desde la cámara de seguridad.",
            "La cámara aparece desconectada en el sistema.",
            "La imagen de la cámara se ve borrosa o congelada.",
            "La cámara no transmite video en directo.",
            "Fallo al acceder a la cámara desde el navegador."
        ]
    },
    "software": {
        "titulos": [
            "Software desactualizado",
            "Aplicación no se inicia",
            "Error al ejecutar programa"
        ],
        "descripciones": [
            "Se requiere actualizar el software instalado.",
            "La aplicación no se abre al hacer clic.",
            "Se genera un error al iniciar el programa.",
            "La aplicación se cierra inesperadamente.",
            "El software instalado no es compatible con el sistema."
        ]
    },
    "energia":{
        "titulos": [
            "Fallo de energía",
            "Cortocircuito",
            "Problemas eléctricos"
        ],
        "descripciones": [
            "El equipo no enciende por falta de energía.",
            "Se detecta un cortocircuito en el aula.",
            "Los enchufes no funcionan correctamente.",
            "La corriente eléctrica se corta intermitentemente.",
            "Se huele a quemado cerca de los enchufes."
        ]
    },
    "calor":{
        "titulos": [
            "Calor excesivo",
            "Fallo en ventilación",
        ],
        "descripciones": [
            "El aula está demasiado caliente.",
            "El aire acondicionado no enfría adecuadamente.",
            "Los ventiladores no funcionan.",
            "Se siente aire caliente en lugar de frío.",
            "El sistema de ventilación hace ruidos extraños.",
            "Hace mas calor que en la comunion de charmander",
            "Tanta  calor que hacia como David Pulido y me iba del grado"
        ]
    },
    "frio":{
        "titulos": [
            "Frío excesivo",
            "Fallo en calefacción",
        ],
        "descripciones": [
            "El aula está demasiado fría.",
            "La calefacción no calienta adecuadamente.",
            "Los radiadores no funcionan.",
            "Se siente aire frío en lugar de caliente.",
            "El sistema de calefacción hace ruidos extraños.",
            "Hace mas frio que en la comunion de pingu",
            "Mas frio que pescando pinguinos",
            "Aqui hace mucho frio, OTRO MES QUE NO COBRAS."
        ]
    },
    # "control_anti_dopping":{
    #     "titulos": [
    #         "Olor fuerte en los verdes",
    #         "Olor fuerte en el parque los naranjos"
    #     ],
    #     "descripciones": [
    #         "Creo que estan fumando porros.",
    #         "Me parece a mi, que lo des la basica estan desatados.",
    #         "Creo que los de informatica llevan sin ducharse desde el primer hijo de miguel.",
    #         "Me parece a mi, que el mauro se ha pasado con la colonia.",
    #         "No huele un poco raro aqui?",
    #         "Medio raros los amigos del abraham, no?",
    #     ]
    # },
    "ruido": {
        "titulos": [
            "Ruido infernal",
            "Sonido incontrolable",
            "Más ruido que en un concierto de motoras"
        ],
        "descripciones": [
            "El ventilador suena como un helicóptero despegando.",
            "El proyector hace más ruido que una olla express.",
            "Parece que alguien taladra constantemente en el aula.",
            "El zumbido es tan constante que se sincroniza con mis pensamientos.",
            "Hay más ruido que en una boda gitana con altavoces Bose.",
            "El german no se calla ni debajo del agua.",
            "Llevamos 3 horas en clase con luismi, no hay mas que decir."
        ]
    },
    "pantalla": {
        "titulos": [
            "Pantalla poseída",
            "Proyector en modo abstracto",
            "Colores estilo Windows 95"
        ],
        "descripciones": [
            "La pantalla cambia de color como una discoteca.",
            "El proyector proyecta el más allá en lugar del escritorio.",
            "Parece que ha entrado en modo arte moderno.",
            "La imagen está tan torcida que parece arte cubista.",
            "Se ve como si Gollum hubiese ajustado el brillo y contraste.",
            "Creo que algun gracioso esta apagando pantallas"
        ]
    },
    "ordenadores": {
        "titulos": [
            "PC con espíritu zen (no hace nada)",
            "Ordenador en huelga",
            "Pantalla congelada desde la Edad Media"
        ],
        "descripciones": [
            "El equipo medita al encenderse y no hace más.",
            "Tras 5 minutos sigue pensando... algo.",
            "Se reinicia más que mi motivación los lunes.",
            "El cursor parpadea... pero no ocurre nada más.",
            "Le he dado al botón y ha entrado en coma tecnológico."
        ]
    },
    "internet": {
        "titulos": [
            "Internet con pedal",
            "Red más lenta que un caracol cojo",
            "Conexión Wi-Fi de la Edad de Piedra"
        ],
        "descripciones": [
            "Cargar una web requiere pacto con fuerzas oscuras.",
            "Zoom parece stop motion del siglo pasado.",
            "El icono de carga se ha convertido en decoración permanente.",
            "Con esta velocidad, enviar un correo es una aventura épica.",
            "Ni el Internet Explorer de 2003 era tan lento."
        ]
    },
    "sillas": {
        "titulos": [
            "Silla con vida propia",
            "Asiento de tortura medieval",
            "Trono de la incomodidad"
        ],
        "descripciones": [
            "La silla gira sola cuando no debería.",
            "Crúje más que mis rodillas al levantarme.",
            "Se inclina como si quisiera invocar algo.",
            "Más incómoda que banco de parque en agosto.",
            "Parece una prueba de resistencia psicológica más que un asiento."
        ]
    }
}


start_date = datetime(2025, 5, 1)
end_date = datetime(2025, 5, 19)

def rand_date(start=start_date, end=end_date):
    base = start + timedelta(days=random.randint(0, (end - start).days - 3))
    return base.date(), (base + timedelta(days=1)).date(), (base + timedelta(days=2)).date()

# Reportantes válidos
valid_reportantes = list(range(1, 5)) + list(range(7, 13)) + [23]
valid_tecnicos = list(range(13, 22)) + [5] + [24]

cont = 0
incidencias = []
historial = []

for _ in range(len(valid_reportantes) * 10):
    id_incidencia = cont
    cont += 1
    reportante_id = random.choice(valid_reportantes)
    tecnico_id = random.choice(valid_tecnicos)
    estado_id = random.randint(0, 5)
    fecha_creacion, fechaiea, fecha_modificacion = rand_date()
    categoria = random.choice(list(incidencias_por_categoria.keys()))
    titulo = random.choice(incidencias_por_categoria[categoria]["titulos"])
    descripcion = random.choice(incidencias_por_categoria[categoria]["descripciones"])
    ubicacion = f"Aula {random.randint(1, 20)}"

    incidencias.append(
        f"INSERT INTO Incidencia (id, titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion, fechaiea, fecha_modificacion) "
        f"VALUES ({id_incidencia}, '{titulo}', '{ubicacion}', '{descripcion}', {reportante_id}, {tecnico_id}, {estado_id}, "
        f"'{fecha_creacion}', '{fechaiea}', '{fecha_modificacion}');"
    )

    saltos = random.randint(2, 5)
    estado_inicial = 5
    fecha_comienzo = fecha_creacion

    for paso in range(saltos):
        if paso == saltos - 1:
            fecha_final = fecha_modificacion
            historial.append(
                f"INSERT INTO historial_estados (incidencia_id, estado_inicial_id, estado_actual_id, fecha_comienzo, fecha_final) "
                f"VALUES ({id_incidencia}, {estado_inicial}, {estado_id}, '{fecha_comienzo}', '{fecha_final}');"
            )
        else:
            nuevo_estado = random.randint(0, 5)
            while nuevo_estado == estado_inicial:
                nuevo_estado = random.randint(0, 5)
            nueva_fecha = fecha_comienzo + timedelta(days=random.randint(1, 2))
            historial.append(
                f"INSERT INTO historial_estados (incidencia_id, estado_inicial_id, estado_actual_id, fecha_comienzo, fecha_final) "
                f"VALUES (NULL, {estado_inicial}, {nuevo_estado}, '{fecha_comienzo}', '{nueva_fecha}');"
            )
            estado_inicial = nuevo_estado
            fecha_comienzo = nueva_fecha

resultado_final = incidencias
resultado_final += [f"ALTER TABLE Incidencia ALTER COLUMN id RESTART WITH {id_incidencia};"]
resultado_final += historial

# Guardar a archivo
path = "static\BORRAR\incidencias.sql"
with open(path, "w", encoding="utf-8") as f:
    f.write('\n'.join(resultado_final))

path
