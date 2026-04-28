USE gymmanager;

-- INSTRUCTORES
INSERT INTO instructor (nombre, apellidos, especialidad, email, telefono) VALUES
('Carlos',  'Ruiz Martínez',    'Crossfit y musculación',       'carlos.ruiz@gymmanager.com',   '612345678'),
('Laura',   'Gómez Sánchez',    'Yoga y pilates',               'laura.gomez@gymmanager.com',   '623456789'),
('Javier',  'López Torres',     'Spinning y cardio',            'javier.lopez@gymmanager.com',  '634567890'),
('María',   'Fernández Díaz',   'Zumba y baile fitness',        'maria.fernandez@gymmanager.com','645678901'),
('Pedro',   'Alonso García',    'Boxeo y artes marciales',      'pedro.alonso@gymmanager.com',  '656789012');

-- TIPOS DE MEMBRESÍA
INSERT INTO tipo_membresia (nombre, precio_mensual, duracion_meses, descripcion) VALUES
('Básica mensual',      29.99,  1,  'Acceso a sala de fitness en horario estándar'),
('Estándar trimestral', 24.99,  3,  'Acceso a sala de fitness + 2 actividades/semana'),
('Premium semestral',   19.99,  6,  'Acceso ilimitado a todas las instalaciones y actividades'),
('Anual completa',      17.99,  12, 'Acceso ilimitado + sesiones personales mensuales');

-- SOCIOS
INSERT INTO socio (nombre, apellidos, email, telefono, fecha_nacimiento, fecha_alta) VALUES
('Ana',         'Martínez López',       'ana.martinez@email.com',       '611000001', '1990-05-14', '2024-01-10'),
('Luis',        'García Hernández',     'luis.garcia@email.com',        '611000002', '1985-11-22', '2024-01-15'),
('Elena',       'Rodríguez Pérez',      'elena.rodriguez@email.com',    '611000003', '1995-03-08', '2024-02-01'),
('Miguel',      'Sánchez Torres',       'miguel.sanchez@email.com',     '611000004', '1988-07-30', '2024-02-10'),
('Sofía',       'López Díaz',           'sofia.lopez@email.com',        '611000005', '1992-12-19', '2024-03-05'),
('David',       'Fernández Ruiz',       'david.fernandez@email.com',    '611000006', '1997-09-02', '2024-03-20'),
('Carmen',      'Gómez Alonso',         'carmen.gomez@email.com',       '611000007', '1983-04-25', '2024-04-01'),
('Jorge',       'Díaz Martínez',        'jorge.diaz@email.com',         '611000008', '2000-08-11', '2024-04-15'),
('Isabel',      'Ruiz García',          'isabel.ruiz@email.com',        '611000009', '1975-01-03', '2024-05-01'),
('Alejandro',   'Torres Sánchez',       'alejandro.torres@email.com',   '611000010', '1993-06-17', '2024-05-20');

-- MEMBRESÍAS
INSERT INTO membresia (id_socio, id_tipo_membresia, fecha_inicio, fecha_fin, estado) VALUES
(1,  2, '2024-01-10', '2024-04-10', 'expirada'),
(1,  3, '2024-04-10', '2024-10-10', 'activa'),
(2,  1, '2024-01-15', '2024-02-15', 'expirada'),
(2,  4, '2024-02-15', '2025-02-15', 'activa'),
(3,  2, '2024-02-01', '2024-05-01', 'activa'),
(4,  1, '2024-02-10', '2024-03-10', 'expirada'),
(4,  1, '2024-03-10', '2024-04-10', 'expirada'),
(4,  2, '2024-04-10', '2024-07-10', 'activa'),
(5,  3, '2024-03-05', '2024-09-05', 'activa'),
(6,  1, '2024-03-20', '2024-04-20', 'activa'),
(7,  4, '2024-04-01', '2025-04-01', 'activa'),
(8,  1, '2024-04-15', '2024-05-15', 'activa'),
(9,  2, '2024-05-01', '2024-08-01', 'activa'),
(10, 3, '2024-05-20', '2024-11-20', 'activa');

-- PAGOS
INSERT INTO pago (id_membresia, fecha_pago, importe, metodo_pago) VALUES
(1,  '2024-01-10', 74.97,  'domiciliacion'),
(2,  '2024-04-10', 119.94, 'tarjeta'),
(3,  '2024-01-15', 29.99,  'efectivo'),
(4,  '2024-02-15', 215.88, 'domiciliacion'),
(5,  '2024-02-01', 74.97,  'tarjeta'),
(6,  '2024-02-10', 29.99,  'efectivo'),
(7,  '2024-03-10', 29.99,  'efectivo'),
(8,  '2024-04-10', 74.97,  'tarjeta'),
(9,  '2024-03-05', 119.94, 'domiciliacion'),
(10, '2024-03-20', 29.99,  'efectivo'),
(11, '2024-04-01', 215.88, 'domiciliacion'),
(12, '2024-04-15', 29.99,  'tarjeta'),
(13, '2024-05-01', 74.97,  'domiciliacion'),
(14, '2024-05-20', 119.94, 'tarjeta');

-- ACTIVIDADES
INSERT INTO actividad (nombre, descripcion, aforo_maximo, duracion_min, id_instructor) VALUES
('Crossfit matutino',   'Entrenamiento funcional de alta intensidad',       15, 60, 1),
('Yoga relajante',      'Sesión de yoga para todos los niveles',            20, 75, 2),
('Spinning express',    'Clase de ciclismo indoor de alta intensidad',      25, 45, 3),
('Zumba fitness',       'Baile aeróbico con ritmos latinoamericanos',       30, 60, 4),
('Boxeo iniciación',    'Técnica y fundamentos del boxeo',                  12, 60, 5),
('Pilates core',        'Trabajo de suelo focalizado en zona core',         15, 60, 2),
('HIIT cardio',         'Entrenamiento interválico de alta intensidad',     20, 45, 1);

-- SESIONES
INSERT INTO sesion (id_actividad, fecha_hora, plazas_disponibles) VALUES
(1, '2024-06-03 07:00:00', 10),
(2, '2024-06-03 09:00:00', 18),
(3, '2024-06-03 18:30:00', 22),
(4, '2024-06-03 20:00:00', 25),
(1, '2024-06-04 07:00:00', 15),
(5, '2024-06-04 19:00:00', 8),
(6, '2024-06-05 10:00:00', 13),
(7, '2024-06-05 18:00:00', 17),
(2, '2024-06-06 09:00:00', 20),
(3, '2024-06-06 18:30:00', 25),
(4, '2024-06-07 20:00:00', 30),
(1, '2024-06-07 07:00:00', 15);

-- RESERVAS
INSERT INTO reserva (id_socio, id_sesion, fecha_reserva, estado) VALUES
(1,  1,  '2024-06-01 10:00:00', 'confirmada'),
(2,  1,  '2024-06-01 10:15:00', 'confirmada'),
(3,  2,  '2024-06-01 11:00:00', 'confirmada'),
(4,  3,  '2024-06-01 12:00:00', 'confirmada'),
(5,  3,  '2024-06-01 12:30:00', 'confirmada'),
(6,  4,  '2024-06-02 09:00:00', 'confirmada'),
(7,  2,  '2024-06-02 09:30:00', 'confirmada'),
(8,  5,  '2024-06-02 10:00:00', 'confirmada'),
(9,  6,  '2024-06-02 11:00:00', 'confirmada'),
(10, 7,  '2024-06-02 15:00:00', 'confirmada'),
(1,  9,  '2024-06-03 08:00:00', 'confirmada'),
(2,  10, '2024-06-03 08:30:00', 'confirmada'),
(3,  8,  '2024-06-03 09:00:00', 'pendiente'),
(4,  11, '2024-06-04 10:00:00', 'confirmada'),
(5,  12, '2024-06-04 10:30:00', 'cancelada');