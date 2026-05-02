-- SOCIOS ------------------------------------------------------

-- Listado completo de socios activos
SELECT id_socio, nombre, apellidos, email, telefono, fecha_alta
FROM socio
WHERE activo = TRUE
ORDER BY apellidos, nombre;

-- Buscar socio por email
SELECT id_socio, nombre, apellidos, email, activo
FROM socio
WHERE email = 'test@email.com';

-- Socios dados de alta en un rango de fechas
SELECT nombre, apellidos, fecha_alta
FROM socio
WHERE fecha_alta BETWEEN '2024-01-01' AND '2024-03-31'
ORDER BY fecha_alta;

-- MEMBRESÍAS ------------------------------------------------------

-- Membresías activas con datos del socio y tipo
SELECT
    s.nombre AS socio_nombre,
    s.apellidos AS socio_apellidos,
    tm.nombre AS tipo_membresia,
    m.fecha_inicio,
    m.fecha_fin,
    m.estado
FROM membresia m
JOIN socio s ON m.id_socio = s.id_socio
JOIN tipo_membresia tm ON m.id_tipo_membresia = tm.id_tipo
WHERE m.estado = 'activa'
ORDER BY m.fecha_fin;

-- Membresías próximas a expirar (en los próximos 30 días)
SELECT
    s.nombre,
    s.apellidos,
    s.email,
    tm.nombre AS tipo_membresia,
    m.fecha_fin,
    (m.fecha_fin - CURRENT_DATE) AS dias_restantes
FROM membresia m
JOIN socio s ON m.id_socio = s.id_socio
JOIN tipo_membresia tm ON m.id_tipo_membresia = tm.id_tipo
WHERE m.estado = 'activa'
  AND m.fecha_fin BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '30 days'
ORDER BY m.fecha_fin;

-- Historial completo de membresías de un socio
SELECT
    tm.nombre AS tipo,
    m.fecha_inicio,
    m.fecha_fin,
    m.estado,
    p.importe,
    p.metodo_pago
FROM membresia m
JOIN tipo_membresia tm ON m.id_tipo_membresia = tm.id_tipo
LEFT JOIN pago p ON p.id_membresia = m.id_membresia
WHERE m.id_socio = 1
ORDER BY m.fecha_inicio DESC;

-- ACTIVIDADES /S ESIONES ------------------------------------------------------

-- Actividades disponibles con nombre del instructor
SELECT
    a.id_actividad,
    a.nombre AS actividad,
    a.aforo_maximo,
    a.duracion_min,
    i.nombre || ' ' || i.apellidos AS instructor
FROM actividad a
JOIN instructor i ON a.id_instructor = i.id_instructor
WHERE a.activa = TRUE
ORDER BY a.nombre;

-- Sesiones disponibles con plazas libres
SELECT
    s.id_sesion,
    a.nombre AS actividad,
    i.nombre || ' ' || i.apellidos AS instructor,
    s.fecha_hora,
    s.plazas_disponibles
FROM sesion s
JOIN actividad a ON s.id_actividad = a.id_actividad
JOIN instructor i ON a.id_instructor = i.id_instructor
WHERE s.fecha_hora >= CURRENT_TIMESTAMP
  AND s.plazas_disponibles > 0
ORDER BY s.fecha_hora;

-- RESERVAS ------------------------------------------------------

-- Reservas confirmadas
SELECT
    r.id_reserva,
    a.nombre AS actividad,
    s.fecha_hora,
    r.fecha_reserva,
    r.estado
FROM reserva r
JOIN sesion s ON r.id_sesion = s.id_sesion
JOIN actividad a ON s.id_actividad = a.id_actividad
WHERE r.id_socio = 1
  AND r.estado = 'confirmada'
ORDER BY s.fecha_hora;

-- Ocupación de una sesión concreta
SELECT
    a.nombre AS actividad,
    ses.fecha_hora,
    a.aforo_maximo,
    COUNT(r.id_reserva) AS reservas_confirmadas,
    a.aforo_maximo - COUNT(r.id_reserva) AS plazas_libres
FROM sesion ses
JOIN actividad a ON ses.id_actividad = a.id_actividad
LEFT JOIN reserva r
    ON r.id_sesion = ses.id_sesion
   AND r.estado = 'confirmada'
WHERE ses.id_sesion = 1
GROUP BY ses.id_sesion, a.nombre, ses.fecha_hora, a.aforo_maximo;

-- ESTADÍSTICAS ------------------------------------------------------

-- Total de ingresos por tipo de membresía
SELECT
    tm.nombre AS tipo_membresia,
    COUNT(p.id_pago) AS num_pagos,
    SUM(p.importe) AS total_ingresos
FROM pago p
JOIN membresia m ON p.id_membresia = m.id_membresia
JOIN tipo_membresia tm ON m.id_tipo_membresia = tm.id_tipo
GROUP BY tm.id_tipo, tm.nombre
ORDER BY total_ingresos DESC;

-- Actividades más populares por número de reservas
SELECT
    a.nombre AS actividad,
    COUNT(r.id_reserva) AS total_reservas
FROM reserva r
JOIN sesion s ON r.id_sesion = s.id_sesion
JOIN actividad a ON s.id_actividad = a.id_actividad
WHERE r.estado = 'confirmada'
GROUP BY a.id_actividad, a.nombre
ORDER BY total_reservas DESC;

-- Número de socios activos por mes de alta
SELECT
    TO_CHAR(fecha_alta, 'YYYY-MM') AS mes_alta,
    COUNT(*) AS nuevos_socios
FROM socio
WHERE activo = TRUE
GROUP BY mes_alta
ORDER BY mes_alta;