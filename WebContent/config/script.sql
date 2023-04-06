CREATE SCHEMA mesero;

USE mesero;

CREATE TABLE Cliente (
   telefono VARCHAR(50) NOT NULL,
   email VARCHAR(256) DEFAULT NULL,
   direccion VARCHAR(256) NOT NULL,
   pass VARCHAR(256) NOT NULL,
   PRIMARY KEY (telefono)
);

CREATE TABLE Ingrediente (
   id INT NOT NULL auto_increment,
   nombre VARCHAR(256) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE Menu (
   nombre_menu VARCHAR(256) NOT NULL,
   PRIMARY KEY (nombre_menu)
);

CREATE TABLE MenuItem (
   nombre_menuItem VARCHAR(256) NOT NULL,
   nombre_menu VARCHAR(256) NOT NULL,
   precio DECIMAL(10,2) NOT NULL,
   imageName VARCHAR(256) DEFAULT NULL,
   hay INTEGER(1) DEFAULT 1, -- 1=true , 0=false
   FOREIGN KEY (nombre_menu) REFERENCES Menu(nombre_menu),
   PRIMARY KEY (nombre_menuItem)
);


CREATE TABLE Ingrediente_MenuItem (
   id_ingrediente INT NOT NULL,
   nombre_menuItem VARCHAR(256) NOT NULL,
   PRIMARY KEY (id_ingrediente,nombre_menuItem)
);

CREATE TABLE Pedido (
	id_pedido INT NOT NULL auto_increment,
	tipo_pedido INTEGER(1) NOT NULL,
	estado INTEGER(1) NOT NULL,
	numeroMesa INT DEFAULT NULL,
	comentario VARCHAR(256) DEFAULT NULL,
	fecha DATETIME NOT NULL,
	telefono_cliente VARCHAR(50) DEFAULT NULL,
	FOREIGN KEY (telefono_cliente) REFERENCES Cliente(telefono),
	PRIMARY KEY (id_pedido)
);

CREATE TABLE PedidoItem (
	id_pedidoItem INT NOT NULL auto_increment,
	cantidad INT NOT NULL,
	hay INTEGER(1) DEFAULT 1, -- 1=true , 0=false
	nombre_menuItem VARCHAR(256) NOT NULL,
	id_pedido INT NOT NULL,
	descuento INT DEFAULT 0,
	FOREIGN KEY (nombre_menuItem) REFERENCES MenuItem(nombre_menuItem),
	FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido),
	PRIMARY KEY (id_pedidoItem)
);