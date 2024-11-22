CREATE DATABASE db_crud_prueba;
USE db_crud_prueba;

CREATE TABLE `db_crud_prueba`.`marca` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `db_crud_prueba`.`bus` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `numero_bus` VARCHAR(45) NOT NULL,
  `placa` VARCHAR(45) NOT NULL,
  `fecha_creacion` DATETIME(6) NOT NULL,
  `caracteristicas` VARCHAR(45) NOT NULL,
  `marca_id` INT NOT NULL,
  `estado` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_marca_id_idx` (`marca_id` ASC) VISIBLE,
  CONSTRAINT `fk_marca_id`
    FOREIGN KEY (`marca_id`)
    REFERENCES `db_crud_prueba`.`marca` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

INSERT INTO `db_crud_prueba`.`marca` (`nombre`) VALUES ('Volvo');
INSERT INTO `db_crud_prueba`.`marca` (`nombre`) VALUES ('Scania');
INSERT INTO `db_crud_prueba`.`marca` (`nombre`) VALUES ('Fiat');


CREATE TABLE `db_crud_prueba`.`rol` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `db_crud_prueba`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre_usuario` VARCHAR(45) NOT NULL,
  `clave` VARCHAR(45) NOT NULL,
  `rol_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_rol_id_idx` (`rol_id` ASC) VISIBLE,
  CONSTRAINT `fk_rol_id`
    FOREIGN KEY (`rol_id`)
    REFERENCES `db_crud_prueba`.`rol` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

INSERT INTO `db_crud_prueba`.`rol` (`nombre`) VALUES ('ROLE_ADMIN');
INSERT INTO `db_crud_prueba`.`rol` (`nombre`) VALUES ('ROLE_USER');