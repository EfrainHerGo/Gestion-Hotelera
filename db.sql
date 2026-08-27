
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS huespedes;
DROP TABLE IF EXISTS habitaciones;
DROP TABLE IF EXISTS reservaciones;

CREATE TABLE IF NOT EXISTS usuarios (
                          id NUMBER GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_usuarios PRIMARY KEY,
                          username VARCHAR2(20) NOT NULL,
                          password VARCHAR2(255) NOT NULL,
                          rol VARCHAR2(30) NOT NULL,
                          estado_registro VARCHAR2(10) DEFAULT 'ACTIVO' NOT NULL,

    -- Validaciones
                          CONSTRAINT chk_usr_username_len CHECK (LENGTH(username) BETWEEN 5 AND 20),
                          CONSTRAINT chk_usr_pass_len CHECK (LENGTH(password) >= 8),
                          CONSTRAINT chk_usr_pass_alpha CHECK (REGEXP_LIKE(password, '[A-Za-z]')),
                          CONSTRAINT chk_usr_pass_num CHECK (REGEXP_LIKE(password, '[0-9]')),
                          CONSTRAINT chk_usr_estado CHECK (estado_registro IN ('ACTIVO', 'INACTIVO'))
);

-- Unicidad de Username entre registros ACTIVOS
CREATE UNIQUE INDEX uq_usr_username_activo ON usuarios (
                                                        CASE WHEN estado_registro = 'ACTIVO' THEN username ELSE NULL END
    );



CREATE TABLE IF NOT EXISTS huespedes (
                           id_huesped NUMBER GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_huespedes PRIMARY KEY,
                           nombre VARCHAR2(50) NOT NULL,
                           apellido_paterno VARCHAR2(50) NOT NULL,
                           apellido_materno VARCHAR2(50),
                           email VARCHAR2(100) NOT NULL,
                           telefono VARCHAR2(10) NOT NULL,
                           documento VARCHAR2(30) NOT NULL,
                           nacionalidad VARCHAR2(50) NOT NULL,
                           estado_registro VARCHAR2(10) DEFAULT 'ACTIVO' NOT NULL,

    -- Validaciones
                           CONSTRAINT chk_hues_nombre CHECK (LENGTH(nombre) BETWEEN 2 AND 50),
                           CONSTRAINT chk_hues_pat CHECK (LENGTH(apellido_paterno) BETWEEN 2 AND 50),
                           CONSTRAINT chk_hues_email_fmt CHECK (REGEXP_LIKE(email, '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')),
                           CONSTRAINT chk_hues_telefono_fmt CHECK (REGEXP_LIKE(telefono, '^[0-9]{10}$')),
                           CONSTRAINT chk_hues_estado CHECK (estado_registro IN ('ACTIVO', 'ELIMINADO'))
);

-- Unicidad condicional para Email, Teléfono y Documento entre registros ACTIVOS
CREATE UNIQUE INDEX uq_hues_email_activo ON huespedes (
                                                       CASE WHEN estado_registro = 'ACTIVO' THEN email ELSE NULL END
    );

CREATE UNIQUE INDEX uq_hues_telefono_activo ON huespedes (
                                                          CASE WHEN estado_registro = 'ACTIVO' THEN telefono ELSE NULL END
    );

CREATE UNIQUE INDEX uq_hues_doc_activo ON huespedes (
                                                     CASE WHEN estado_registro = 'ACTIVO' THEN documento ELSE NULL END
    );



CREATE TABLE IF NOT EXISTS habitaciones (
                              id_habitaciones NUMBER GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_habitaciones PRIMARY KEY,
                              numero_habitacion NUMBER(6) NOT NULL,
                              tipo VARCHAR2(50) NOT NULL,
                              precio NUMBER(10, 2) NOT NULL,
                              capacidad NUMBER(3) NOT NULL,
                              estado_habitacion VARCHAR2(20) DEFAULT 'DISPONIBLE' NOT NULL,
                              estado_registro VARCHAR2(10) DEFAULT 'ACTIVO' NOT NULL,

    -- Validaciones
                              CONSTRAINT chk_hab_numero CHECK (numero_habitacion > 0),
                              CONSTRAINT chk_hab_precio CHECK (precio > 0),
                              CONSTRAINT chk_hab_capacidad CHECK (capacidad >= 1),
                              CONSTRAINT chk_hab_estado CHECK (estado_registro IN ('ACTIVO', 'ELIMINADO'))
);

-- Unicidad del Número de Habitación entre registros ACTIVOS
CREATE UNIQUE INDEX uq_hab_numero_activo ON habitaciones (
                                                          CASE WHEN estado_registro = 'ACTIVO' THEN numero_habitacion ELSE NULL END
    );



CREATE TABLE IF NOT EXISTS reservaciones (
                               id_reservacion NUMBER GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_reservaciones PRIMARY KEY,
                               idhuesped NUMBER NOT NULL,
                               id_habitacion NUMBER NOT NULL,
                               fecha_entrada DATE NOT NULL,
                               fecha_salida DATE NOT NULL,
                               estado_reservacion VARCHAR2(20) DEFAULT 'CONFIRMADA' NOT NULL,

    -- Validaciones
                               CONSTRAINT chk_res_fechas CHECK (fecha_salida > fecha_entrada)
);