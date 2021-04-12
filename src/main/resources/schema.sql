CREATE TABLE IF NOT EXISTS PRESTAMOS
(
   id integer not null AUTO_INCREMENT,
   isbn varchar(10) not null,
   identificacionUsuario varchar(10),
   tipoUsuario integer(1) not null,
   fechaMaximaDevolucion varchar(10),

   primary key(id)
);
