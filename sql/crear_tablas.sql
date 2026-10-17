CREATE TABLE articulo (
    id_articulo  NUMBER GENERATED ALWAYS AS IDENTITY,
    codigo       VARCHAR2(20)  NOT NULL,
    nombre       VARCHAR2(100) NOT NULL,
    descripcion  VARCHAR2(200),
    precio       NUMBER(10,2)  NOT NULL,
    existencia   NUMBER(10)    DEFAULT 0 NOT NULL,
    CONSTRAINT pk_articulo PRIMARY KEY (id_articulo),
    CONSTRAINT uq_articulo_codigo UNIQUE (codigo),
    CONSTRAINT ck_articulo_precio CHECK (precio >= 0),
    CONSTRAINT ck_articulo_existencia CHECK (existencia >= 0)
);

INSERT INTO articulo (codigo, nombre, descripcion, precio, existencia) VALUES ('ART001', 'Teclado mecánico', 'Teclado mecánico con iluminación RGB', 450.00, 15);
INSERT INTO articulo (codigo, nombre, descripcion, precio, existencia) VALUES ('ART002', 'Mouse inalámbrico', 'Mouse óptico inalámbrico de 1600 DPI', 125.50, 30);
INSERT INTO articulo (codigo, nombre, descripcion, precio, existencia) VALUES ('ART003', 'Monitor 24 pulgadas', 'Monitor LED Full HD de 24 pulgadas', 1250.00, 8);
INSERT INTO articulo (codigo, nombre, descripcion, precio, existencia) VALUES ('ART004', 'Audífonos', 'Audífonos con micrófono y cancelación de ruido', 380.00, 12);
INSERT INTO articulo (codigo, nombre, descripcion, precio, existencia) VALUES ('ART005', 'Memoria USB 64 GB', 'Memoria USB 3.0 de 64 GB', 85.00, 50);
INSERT INTO articulo (codigo, nombre, descripcion, precio, existencia) VALUES ('ART006', 'Disco SSD 1 TB', 'Unidad de estado sólido de 1 TB', 720.00, 10);
INSERT INTO articulo (codigo, nombre, descripcion, precio, existencia) VALUES ('ART007', 'Cámara web', 'Cámara web HD de 1080p', 299.99, 6);
INSERT INTO articulo (codigo, nombre, descripcion, precio, existencia) VALUES ('ART008', 'Impresora multifuncional', 'Impresora de tinta con escáner', 1450.00, 4);

COMMIT;
