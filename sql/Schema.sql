CREATE DATABASE IF NOT EXISTS gymmanager

USE gymmanager;

-- INSTRUCTOR
CREATE TABLE instructor (
    id_instructor INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_instructor PRIMARY KEY (id_instructor)
);

-- TIPO_MEMBRESIA
CREATE TABLE tipo_membresia (
    id_tipo INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    precio_mensual DECIMAL(8,2) NOT NULL CHECK (precio_mensual > 0),
    duracion_meses INT NOT NULL CHECK (duracion_meses > 0),
    descripcion VARCHAR(255),
    CONSTRAINT pk_tipo_membresia PRIMARY KEY (id_tipo)
);

-- SOCIO
CREATE TABLE socio (
    id_socio INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    fecha_nacimiento DATE,
    fecha_alta DATE NOT NULL DEFAULT (CURRENT_DATE),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_socio PRIMARY KEY (id_socio)
);

-- MEMBRESIA
CREATE TABLE membresia (
    id_membresia INT NOT NULL AUTO_INCREMENT,
    id_socio INT NOT NULL,
    id_tipo_membresia INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado ENUM('activa', 'expirada', 'cancelada') NOT NULL DEFAULT 'activa',
    CONSTRAINT pk_membresia PRIMARY KEY (id_membresia),
    CONSTRAINT fk_mem_socio FOREIGN KEY (id_socio) REFERENCES socio(id_socio) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_mem_tipo FOREIGN KEY (id_tipo_membresia) REFERENCES tipo_membresia(id_tipo) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_fechas CHECK (fecha_fin > fecha_inicio)
);

-- PAGO
CREATE TABLE pago (
    id_pago INT NOT NULL AUTO_INCREMENT,
    id_membresia INT NOT NULL,
    fecha_pago DATE NOT NULL DEFAULT (CURRENT_DATE),
    importe DECIMAL(8,2) NOT NULL CHECK (importe > 0),
    metodo_pago ENUM('efectivo', 'tarjeta', 'transferencia', 'domiciliacion') NOT NULL,
    observaciones VARCHAR(255),
    CONSTRAINT pk_pago PRIMARY KEY (id_pago),
    CONSTRAINT fk_pago_mem FOREIGN KEY (id_membresia) REFERENCES membresia(id_membresia) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ACTIVIDAD
CREATE TABLE actividad (
    id_actividad INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    aforo_maximo INT NOT NULL CHECK (aforo_maximo > 0),
    duracion_min INT NOT NULL DEFAULT 60 CHECK (duracion_min > 0),
    id_instructor INT NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_actividad PRIMARY KEY (id_actividad),
    CONSTRAINT fk_act_inst FOREIGN KEY (id_instructor) REFERENCES instructor(id_instructor) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- SESION
CREATE TABLE sesion (
    id_sesion INT NOT NULL AUTO_INCREMENT,
    id_actividad INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    plazas_disponibles INT NOT NULL CHECK (plazas_disponibles >= 0),
    CONSTRAINT pk_sesion PRIMARY KEY (id_sesion),
    CONSTRAINT fk_ses_act FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- RESERVA
CREATE TABLE reserva (
    id_reserva INT NOT NULL AUTO_INCREMENT,
    id_socio INT NOT NULL,
    id_sesion INT NOT NULL,
    fecha_reserva DATETIME NOT NULL DEFAULT NOW(),
    estado ENUM('confirmada', 'cancelada', 'pendiente') NOT NULL DEFAULT 'confirmada',
    CONSTRAINT pk_reserva PRIMARY KEY (id_reserva),
    CONSTRAINT fk_res_socio FOREIGN KEY (id_socio) REFERENCES socio(id_socio) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_res_sesion FOREIGN KEY (id_sesion) REFERENCES sesion(id_sesion) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT uq_reserva UNIQUE (id_socio, id_sesion)
);

-- ÍNDICES
CREATE INDEX idx_membresia_socio ON membresia(id_socio);
CREATE INDEX idx_membresia_estado ON membresia(estado);
CREATE INDEX idx_sesion_fecha ON sesion(fecha_hora);
CREATE INDEX idx_reserva_sesion ON reserva(id_sesion);
CREATE INDEX idx_reserva_estado ON reserva(estado);