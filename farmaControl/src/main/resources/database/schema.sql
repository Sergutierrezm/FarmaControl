CREATE DATABASE FarmaControl;
USE FarmaControl;

-- =========================
-- TABLA: Rol
-- =========================
CREATE TABLE Rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- =========================
-- TABLA: Usuario
-- =========================
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES Rol(id_rol)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- =========================
-- TABLA: Proveedor
-- =========================
CREATE TABLE Proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255)
);

-- =========================
-- TABLA: Producto
-- =========================
CREATE TABLE Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    id_proveedor INT NOT NULL,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id_proveedor)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- =========================
-- TABLA: Cliente
-- =========================
CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion VARCHAR(255)
);

-- =========================
-- TABLA: Factura
-- =========================
CREATE TABLE Factura (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    id_cliente INT NOT NULL,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- =========================
-- TABLA: DetalleFactura
-- =========================
CREATE TABLE DetalleFactura (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_factura INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_factura) REFERENCES Factura(id_factura)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- =========================
-- TABLA: LogActividad
-- =========================
CREATE TABLE LogActividad (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_accion VARCHAR(100) NOT NULL,
    descripcion TEXT,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);
