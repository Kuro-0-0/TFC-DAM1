INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (0, 'Resuelto', 'resuelto', true, '#198754', '#000000', 2);
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (1, 'Pendiente', 'pendiente', true, '#ffc107', '#000000', 0);
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (2, 'En Proceso', 'en-proceso', true, '#0dcaf0', '#000000', 1);
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (3, 'Cancelado', 'cancelado', true, '#dc3545', '#ffffff', 2);
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (4, 'Bloqueado', 'bloqueado', true, '#6c757d', '#ffffff', 1);
INSERT INTO Estado (id, nombre, valor, activo, color_fondo, color_texto, tipo) VALUES (5, 'Sin Estado', 'sin-estado', true, '#04c7f5', '#ffffff', 0);

ALTER TABLE Estado ALTER COLUMN id RESTART WITH 6;

INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (0, 'admin', '$2a$12$fMCbeiD5ulJd0HVupQClOeUglGpw9z2UphGQPI3ANsiEtGP.TA67K', 'admin', '', 'ADMIN',true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (1, 'lar.gon', '$2a$12$V8iW5fcIYPKE59vfTwMHl.KhodvtPqwZdeq/dIup8giAnhBScLnzC', 'Laura', 'González', 'TECH', true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (2, 'mig.fer', '$2a$12$htpOsZyN7Xu9KvdW7aQ/4O0wyu2NDAtd9jlcC4uzTpTFR3nFzjX3e', 'Miguel', 'Fernández', 'TECH', true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (3, 'ana.mar', '$2a$12$htpOsZyN7Xu9KvdW7aQ/4O0wyu2NDAtd9jlcC4uzTpTFR3nFzjX3e', 'Ana', 'Martínez', 'USER', true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (4, 'jav.lop', '$2a$12$XvobC.0E48cH4UnJ82BrhOIwRgg9v1LSBI9TpsIW8w1ygL3I5x8Ui', 'Javier', 'López', 'USER', true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (5, 'sin-tecnico', '$2a$12$NmoIYUd8/skZvU3ZEp1uSe1Y8HRyM1ej0YUidAZi2tfzZr8cYv2Dm', 'Sin Tecnico', '', 'TECH',false);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (6, 'sin-reportante', '$2a$12$fFLk39rz2QSoKwvWfvnvsusutkUe7T9Gc1i0t/JaSg3J1I.xHUb3i', 'Sin Reportante', '','USER',false);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (7, 'car.paz', '$2a$12$StccD9RG/i.bInuEGfLGnewT6MSfKC557RHrvr29P0eqhhTQ1KJ3e', 'Carlos', 'Paz', 'TECH', true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (8, 'eli.rom', '$2a$12$C9nVZH4PSd86qyL0kgIsXeTPY8KjNjHjFAqOTomsJ.UvQgL4FcoQC', 'Elisa', 'Romero', 'TECH', true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (9, 'ale.nav', '$2a$12$hfqOybVbLm5yeQSIYtOwY.oDQnhL2JLNspNsjdaJvbVINCGJvyJ9W', 'Alejandro', 'Navarro', 'USER', true);
INSERT INTO usuario (id, username, password, nombre, apellidos, rol, editable) VALUES (10, 'mar.sol', '$2a$12$0SZTxvMtbZ/uD1A68V51OeckZBeN1fju0ncQvTfcQ//EBNsWv7nhC', 'Marina', 'Soler', 'USER', true);

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


INSERT INTO Usuario (username,password,rol,editable, nombre, apellidos) VALUES ('user','$2a$10$P.INQOjXA6Krg12MKDl3ne0jZKqQPwjdNd9LfzYl9m2GTwm1nfm3y','USER',true,'user','');