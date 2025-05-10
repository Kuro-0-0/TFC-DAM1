INSERT INTO Estado (id,nombre, valor, activo, color_fondo, color_texto) VALUES (0,'Resuelto', 'resuelto',true,'#198754','#000')
    INSERT INTO Estado (id,nombre, valor, activo, color_fondo, color_texto) VALUES (1,'Pendiente', 'pendiente',true,'#ffc107','#000')
INSERT INTO Estado (id,nombre, valor, activo, color_fondo, color_texto) VALUES (2,'En Proceso', 'en-proceso',true,'#0dcaf0','#000')
INSERT INTO Estado (id,nombre, valor, activo, color_fondo, color_texto) VALUES (3,'Cancelado', 'cancelado',true,'#dc3545','#fff');
INSERT INTO Estado (id,nombre, valor, activo, color_fondo, color_texto) VALUES (4,'Bloqueado', 'bloqueado',true,'#6c757d','#fff');

ALTER TABLE Estado ALTER COLUMN id RESTART WITH 5;

INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (0, 'admin', 'admin', 'admin', '', 0,true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (1, 'lar.gon', '1234', 'Laura', 'González', 1, true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (2, 'mig.fer', '1234', 'Miguel', 'Fernández', 1, true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (3, 'ana.mar', '1234', 'Ana', 'Martínez', 2, true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (4, 'jav.lop', '1234', 'Javier', 'López', 2, true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (5, 'sin-tecnico', 'sin-tecnico', 'Sin Tecnico', '', 1,false);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (6, 'sin-reportante', 'sin-reportante', 'Sin Reportante', '',2,false);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (7, 'car.paz', '1234', 'Carlos', 'Paz', 1, true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (8, 'eli.rom', '1234', 'Elisa', 'Romero', 1, true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (9, 'ale.nav', '1234', 'Alejandro', 'Navarro', 2, true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (10, 'mar.sol', '1234', 'Marina', 'Soler', 2, true);

ALTER TABLE usuario ALTER COLUMN id RESTART WITH 11;


INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Actualización requerida', 'Oficina B', 'Computadora no enciende', 3, 1, 1, '2024-05-02');
INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Fallo de red', 'Sala de servidores', 'Sin conexión a Internet', 4, 1, 2, '2024-05-01');
INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Problema de impresora', 'Departamento Legal', 'La impresora no responde', 10, 8, 1, '2024-05-03');
INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Pantalla azul', 'Puesto 21', 'Error crítico de Windows', 9, 7, 0, '2024-05-04');
INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Actualización de software', 'Sala de formación', 'Actualizar a la última versión', 3, 7, 1, '2024-05-05');
INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Virus detectado', 'Puesto 5', 'Antivirus ha bloqueado amenazas', 10, 8, 0, '2024-05-06');
INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Correo no funciona', 'Planta 2', 'No se envían correos', 4, 2, 2, '2024-05-02');
INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Cambio de contraseña', 'Recepción', 'Usuario olvidó contraseña', 9, 1, 3, '2024-05-03');
INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Teclado dañado', 'Puesto 18', 'No funcionan varias teclas', 10, 7, 4, '2024-05-04');
INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Error en SAP', 'Contabilidad', 'Falla en login de SAP', 3, 8, 2, '2024-05-05');
INSERT INTO Incidencia (titulo, ubicacion, descripcion, reportante_id, tecnico_id, estado_id, fecha_creacion) VALUES ('Equipo bloqueado', 'Almacén', 'No se puede iniciar sesión', 8, 2, 4, '2024-05-06');