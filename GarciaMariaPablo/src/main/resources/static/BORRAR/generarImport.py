import random
from datetime import datetime, timedelta


bloque_obligatorio = """
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (1, 'Resuelto', 'resuelto', true, '#198754', '#000000', 2);
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (2, 'En Proceso', 'en-proceso', true, '#0dcaf0', '#000000', 1);
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (3, 'Cancelado', 'cancelado', true, '#dc3545', '#ffffff', 2);
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (4, 'Bloqueado', 'bloqueado', true, '#6c757d', '#ffffff', 1);
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (6, 'Pendiente', 'pendiente', true, '#ffc107', '#000000', 0);
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (5, 'Sin Estado', 'sin-estado', true, '#04c7f5', '#ffffff', 0);

ALTER TABLE Estado ALTER COLUMN id RESTART WITH 7;

INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (1, 'user01', '$2a$12$7.MmeCKa9h.4u2uC31zOgOKFEyXQmIWKswTbNu4.MQedYyvQ2pgzW', 'USER', 'Ana', 'Martínez', 'ana.martinez@example.com', '612345678', '2024-11-03', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (2, 'user02', '$2a$12$Ur0Am.HjJ0WseHbtWuZLjuJ2yBbyQU5MJPORmweLEEd5ip2C05IYq', 'USER', 'Carlos', 'Jiménez', 'carlos.jimenez@example.com', '622345679', '2024-12-10', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (3, 'user03', '$2a$12$qiHV.kWZWYJXcpQWUwJME.c4o2bZxgINRb8jQCghvK5MrMRD0kNCy', 'USER', 'Lucía', 'Navarro', 'lucia.navarro@example.com', '632345680', '2025-01-05', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (4, 'user04', '$2a$12$Z9N.3Njv.bBXFfui5ysesu6Oc39oXfGs8n7V45ae/QIMunErTl3Fy', 'USER', 'David', 'Fernández', 'david.fernandez@example.com', '642345681', '2025-02-12', true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (5, 'sin-tecnico', 'sin-tecnico', 'Sin Tecnico', '', 'TECH',false);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (6, 'sin-reportante', 'sin-reportante', 'Sin Reportante', '','USER',false);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (7, 'user05', '$2a$12$BxOL/W6prcl10mPFM8sTlOudTdxmw/mNqYsINIkw5Ze1otkt58X0q', 'USER', 'Elena', 'Romero', 'elena.romero@example.com', '652345682', '2025-03-18', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (8, 'user06', '$2a$12$wcv3W3nrkedYQM1py43D5OyoxU6WOmPqrwDmnR7CnOtkzO2M44fhG', 'USER', 'Javier', 'Ortiz', 'javier.ortiz@example.com', '662345683', '2025-03-21', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (9, 'user07', '$2a$12$.Xv5w.qDf964rO.2x8TBJOu.Yq6xR7kXLOQUrwcHdpS8lYsfQB5yq', 'USER', 'Sara', 'Cano', 'sara.cano@example.com', '672345684', '2025-03-27', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (10, 'user08', '$2a$12$qL1JYDBiYRpCwyUp9/r/euX7gmLChdir8NAX7LJQBWS6DApOZxQ.i', 'USER', 'Mario', 'González', 'mario.gonzalez@example.com', '682345685', '2025-04-04', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (11, 'user09', '$2a$12$WL05lv72x9P6zeymFJzqre5smIW5t6L/v832MDKaZIyG4uTG7NsaK', 'USER', 'Nuria', 'Sanz', 'nuria.sanz@example.com', '692345686', '2025-04-11', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (12, 'user10', '$2a$12$smC6itF8JbqT83mXwnBPOu8ibh9aB/HryghmzLRB2B2/MJH1ImSom', 'USER', 'Raúl', 'Alonso', 'raul.alonso@example.com', '602345687', '2025-04-17', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (13, 'tech01', '$2a$12$mYl/eUWoje6/6cs0OoYGa.4IOH4iG5ziFr5go4c/V6Nr9bmyl.z/q', 'TECH', 'Isabel', 'Pérez', 'isabel.perez@example.com', '611111111', '2025-04-20', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (14, 'tech02', '$2a$12$JfN31Y95PREiph5/yuvBreFZhovgrjSzyGxdzC5EhdqPqMiJbdB8S', 'TECH', 'Pedro', 'Herrera', 'pedro.herrera@example.com', '622222222', '2025-04-21', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (15, 'tech03', '$2a$12$FePPC3xeTTHl8FsgK1APFuH8Sxdnh48BGoIq8k/HFkxG1RwyxOdlu', 'TECH', 'Claudia', 'Mora', 'claudia.mora@example.com', '633333333', '2025-04-22', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (16, 'tech04', '$2a$12$obaRrurahwkaCtabLacxg.rVgpJO9Bo2jaEPvmrK1EP/xiF/tNS1m', 'TECH', 'Alberto', 'Reyes', 'alberto.reyes@example.com', '644444444', '2025-04-23', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (17, 'tech05', '$2a$12$obaRrurahwkaCtabLacxg.rVgpJO9Bo2jaEPvmrK1EP/xiF/tNS1m', 'TECH', 'Patricia', 'Crespo', 'patricia.crespo@example.com', '655555555', '2025-04-24', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (18, 'tech06', '$2a$12$obaRrurahwkaCtabLacxg.rVgpJO9Bo2jaEPvmrK1EP/xiF/tNS1m', 'TECH', 'Hugo', 'López', 'hugo.lopez@example.com', '666666666', '2025-04-25', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (19, 'tech07', '$2a$12$obaRrurahwkaCtabLacxg.rVgpJO9Bo2jaEPvmrK1EP/xiF/tNS1m', 'TECH', 'Teresa', 'Delgado', 'teresa.delgado@example.com', '677777777', '2025-04-26', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (20, 'tech08', '$2a$12$obaRrurahwkaCtabLacxg.rVgpJO9Bo2jaEPvmrK1EP/xiF/tNS1m', 'TECH', 'Andrés', 'Ruiz', 'andres.ruiz@example.com', '688888888', '2025-04-27', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (21, 'tech09', '$2a$12$obaRrurahwkaCtabLacxg.rVgpJO9Bo2jaEPvmrK1EP/xiF/tNS1m', 'TECH', 'Laura', 'Suárez', 'laura.suarez@example.com', '699999999', '2025-04-28', true);
INSERT INTO Usuario (id, username, password, rol, nombre, apellidos, email, telefono, fecha_registro, editable) VALUES (22, 'tech10', '$2a$12$obaRrurahwkaCtabLacxg.rVgpJO9Bo2jaEPvmrK1EP/xiF/tNS1m', 'TECH', 'Sergio', 'Bermúdez', 'sergio.bermudez@example.com', '600000000', '2025-04-29', true);
INSERT INTO Usuario (id,username,password,rol,editable, nombre, apellidos) VALUES (23,'user','$2a$10$P.INQOjXA6Krg12MKDl3ne0jZKqQPwjdNd9LfzYl9m2GTwm1nfm3y','USER',false,'user','');
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (24, 'tech', '$2a$12$cVbhu2H9QtaWAA8zmXs7luqThZjcKrBTLd5upkgn.SFRPEVo76OKm', 'tech', '', 'TECH',false);
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 25;
INSERT INTO usuario (username, password, nombre, apellidos, rol, editable) VALUES ( 'admin', '$2a$12$fMCbeiD5ulJd0HVupQClOeUglGpw9z2UphGQPI3ANsiEtGP.TA67K', 'admin', '', 'ADMIN',false);
"""

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
            "El controlador de la impresora no está instalado correctamente.",
            "Es mas antigua que el puente del sillo"
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
            "Javi ha tumbado la red electrica nacional."
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
            "Aqui hace mucho frio, OTRO MES QUE NO COBRAS.",
            "Lleva todo el dia el aire puesto, me estoy muriendo"
        ]
    },
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
            "Llevamos 3 horas en clase con luismi, no hay mas que decir.",
            "Hay un chaval que no para de hacer ruido comiendo.",
            "German lleva desde las ocho de la mañana sin callarse.",
            "El pablo no para quieto, que se calle.",
            "Se escucha la moto de Fernando desde la otra punta del edificio.",
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
            "Creo que algun gracioso esta apagando pantallas",
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

def rand_datetime(start, end):
    delta = end - start
    random_seconds = random.randint(0, int(delta.total_seconds()))
    base = start + timedelta(seconds=random_seconds)
    f1 = base
    f2 = f1 + timedelta(seconds=random.randint(60, 3600*40))
    f3 = f2 + timedelta(seconds=random.randint(60, 3600*40))
    return f1, f2, f3

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
    if tecnico_id == 5:
        estado_id = 5
    else:
        estado_id = random.randint(1, 6)
    fecha_creacion, fechaiea, fecha_modificacion = rand_datetime(start_date, end_date)
    categoria = random.choice(list(incidencias_por_categoria.keys()))
    titulo = random.choice(incidencias_por_categoria[categoria]["titulos"])
    descripcion = random.choice(incidencias_por_categoria[categoria]["descripciones"])
    ubicacion = f"Aula {random.randint(1, 20)}"

    incidencias.append(
        f"INSERT INTO Incidencia (id, titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion, fechaiea, fecha_modificacion) "
        f"VALUES ({id_incidencia}, '{titulo}', '{ubicacion}', '{descripcion}', {reportante_id}, {tecnico_id}, {estado_id}, "
        f"'{fecha_creacion}', '{fechaiea}', '{fecha_modificacion}');"
    )

    saltos = random.randint(0, 3)
    estado_inicial = 5
    fecha_comienzo = fecha_creacion

    for paso in range(saltos):
        if paso == saltos - 1:
            fecha_final = fecha_modificacion
            historial.append(
                f"INSERT INTO historial_estados (incidencia_id, estado_inicial_id, estado_actual_id, fecha_comienzo, fecha_final) "
                f"VALUES ({id_incidencia}, {estado_inicial}, {estado_id}, '{fecha_comienzo.strftime('%Y-%m-%d %H:%M:%S')}', '{fecha_final.strftime('%Y-%m-%d %H:%M:%S')}');"
            )
        else:
            nuevo_estado = random.randint(1, 6)
            while nuevo_estado == estado_inicial:
                nuevo_estado = random.randint(1, 6)
            nueva_fecha = fecha_comienzo + timedelta(hours=random.randint(1, 24), minutes=random.randint(1, 59))
            while nueva_fecha > fecha_modificacion:
                print(fecha_comienzo, nueva_fecha, fecha_modificacion)
                nueva_fecha = fecha_comienzo + timedelta(hours=random.randint(1, 24), minutes=random.randint(1, 59))
            historial.append(
                f"INSERT INTO historial_estados (incidencia_id, estado_inicial_id, estado_actual_id, fecha_comienzo, fecha_final) "
                f"VALUES (NULL, {estado_inicial}, {nuevo_estado}, '{fecha_comienzo.strftime('%Y-%m-%d %H:%M:%S')}', '{nueva_fecha.strftime('%Y-%m-%d %H:%M:%S')}');"
            )
            estado_inicial = nuevo_estado
            fecha_comienzo = nueva_fecha


resultado_final = []
resultado_final += bloque_obligatorio.strip().splitlines()
resultado_final += incidencias
resultado_final += [f"ALTER TABLE Incidencia ALTER COLUMN id RESTART WITH {id_incidencia+1};"]
resultado_final += historial

# Guardar a archivo
path = "static/BORRAR/incidencias.sql"
with open(path, "w", encoding="utf-8") as f:
    f.write('\n'.join(resultado_final))

path
