-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema CatalagoProductos
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema CatalagoProductos
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CatalagoProductos` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `CatalagoProductos` ;

-- -----------------------------------------------------
-- Table `CatalagoProductos`.`Categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CatalagoProductos`.`Categoria` (
  `idCategoria` INT NOT NULL,
  `NombreCategoria` VARCHAR(100) NULL,
  PRIMARY KEY (`idCategoria`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CatalagoProductos`.`Producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CatalagoProductos`.`Producto` (
  `idProducto` INT NOT NULL,
  `CodigoBERProducto` INT NULL,
  `DescripcionProducto` VARCHAR(200) NULL,
  `StockProducto` INT(3) NULL,
  `PrecioVentaProducto` FLOAT NULL,
  `CodigoQRProducto` VARCHAR(45) NULL,
  `ImagenProducto` VARCHAR(512) NULL,
  `Categoria_idCategoria` INT NOT NULL,
  PRIMARY KEY (`idProducto`),
  INDEX `fk_Producto_Categoria1_idx` (`Categoria_idCategoria` ASC),
  CONSTRAINT `fk_Producto_Categoria1`
    FOREIGN KEY (`Categoria_idCategoria`)
    REFERENCES `CatalagoProductos`.`Categoria` (`idCategoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CatalagoProductos`.`Almacen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CatalagoProductos`.`Almacen` (
  `idAlmacen` INT NOT NULL,
  `NombreAlmacen` VARCHAR(45) NULL,
  PRIMARY KEY (`idAlmacen`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `CatalagoProductos`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CatalagoProductos`.`Cliente` (
  `idCliente` INT NOT NULL,
  `NombreCliente` VARCHAR(45) NULL,
  `ApellidosCliente` VARCHAR(100) NULL,
  `CorreoCliente` VARCHAR(45) NULL,
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CatalagoProductos`.`Tiempo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CatalagoProductos`.`Tiempo` (
  `idTiempo` INT NOT NULL,
  `FechaTiempo` DATE NULL,
  `AnyoTiempo` INT NULL,
  `TrimestreTiempo` INT NULL,
  `MesTiempo` INT NULL,
  `SemanaTiempo` INT NULL,
  `DiaSemana` VARCHAR(10) NULL,
  PRIMARY KEY (`idTiempo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CatalagoProductos`.`Reserva`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CatalagoProductos`.`Reserva` (
  `idReserva` INT NOT NULL,
  `Almacen_idAlmacen` INT NOT NULL,
  `Tiempo_idTiempo` INT NOT NULL,
  `Cliente_idCliente` INT NOT NULL,
  PRIMARY KEY (`idReserva`),
  INDEX `fk_Reserva_Almacen1_idx` (`Almacen_idAlmacen` ASC),
  INDEX `fk_Reserva_Tiempo1_idx` (`Tiempo_idTiempo` ASC),
  INDEX `fk_Reserva_Cliente1_idx` (`Cliente_idCliente` ASC),
  CONSTRAINT `fk_Reserva_Almacen1`
    FOREIGN KEY (`Almacen_idAlmacen`)
    REFERENCES `CatalagoProductos`.`Almacen` (`idAlmacen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reserva_Tiempo1`
    FOREIGN KEY (`Tiempo_idTiempo`)
    REFERENCES `CatalagoProductos`.`Tiempo` (`idTiempo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reserva_Cliente1`
    FOREIGN KEY (`Cliente_idCliente`)
    REFERENCES `CatalagoProductos`.`Cliente` (`idCliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CatalagoProductos`.`DetalleReserva`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CatalagoProductos`.`DetalleReserva` (
  `Producto_idProducto` INT NOT NULL,
  `Reserva_idReserva` INT NOT NULL,
  `CantidadProducto` INT NULL,
  PRIMARY KEY (`Producto_idProducto`, `Reserva_idReserva`),
  INDEX `fk_Producto_has_Reserva_Reserva1_idx` (`Reserva_idReserva` ASC),
  INDEX `fk_Producto_has_Reserva_Producto1_idx` (`Producto_idProducto` ASC),
  CONSTRAINT `fk_Producto_has_Reserva_Producto1`
    FOREIGN KEY (`Producto_idProducto`)
    REFERENCES `CatalagoProductos`.`Producto` (`idProducto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Producto_has_Reserva_Reserva1`
    FOREIGN KEY (`Reserva_idReserva`)
    REFERENCES `CatalagoProductos`.`Reserva` (`idReserva`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `CatalagoProductos`.`Categoria`
-- -----------------------------------------------------
START TRANSACTION;
USE `CatalagoProductos`;
INSERT INTO `CatalagoProductos`.`Categoria` (`idCategoria`, `NombreCategoria`) VALUES (100, 'Electrodomesticos');
INSERT INTO `CatalagoProductos`.`Categoria` (`idCategoria`, `NombreCategoria`) VALUES (110, 'Perifericos');
INSERT INTO `CatalagoProductos`.`Categoria` (`idCategoria`, `NombreCategoria`) VALUES (111, 'Telefonia');

COMMIT;


-- -----------------------------------------------------
-- Data for table `CatalagoProductos`.`Producto`
-- -----------------------------------------------------
START TRANSACTION;
USE `CatalagoProductos`;
INSERT INTO `CatalagoProductos`.`Producto` (`idProducto`, `CodigoBERProducto`, `DescripcionProducto`, `StockProducto`, `PrecioVentaProducto`, `CodigoQRProducto`, `ImagenProducto`, `Categoria_idCategoria`) VALUES (050600004, 15645, 'CELULAR SMARTFONE BB Z10', 1, 25, NULL, NULL, 111);
INSERT INTO `CatalagoProductos`.`Producto` (`idProducto`, `CodigoBERProducto`, `DescripcionProducto`, `StockProducto`, `PrecioVentaProducto`, `CodigoQRProducto`, `ImagenProducto`, `Categoria_idCategoria`) VALUES (050100128, 15646, 'PARLANTES DELL', 1, 2, NULL, NULL, 110);
INSERT INTO `CatalagoProductos`.`Producto` (`idProducto`, `CodigoBERProducto`, `DescripcionProducto`, `StockProducto`, `PrecioVentaProducto`, `CodigoQRProducto`, `ImagenProducto`, `Categoria_idCategoria`) VALUES (090100014, 15647, 'ASPIRADORA DOMESTICA', 42, 10.35, NULL, NULL, 100);
INSERT INTO `CatalagoProductos`.`Producto` (`idProducto`, `CodigoBERProducto`, `DescripcionProducto`, `StockProducto`, `PrecioVentaProducto`, `CodigoQRProducto`, `ImagenProducto`, `Categoria_idCategoria`) VALUES (070300094, 15648, 'CAFETERA ELECTRICA PROFESIONAL', 1, 6, NULL, NULL, 100);
INSERT INTO `CatalagoProductos`.`Producto` (`idProducto`, `CodigoBERProducto`, `DescripcionProducto`, `StockProducto`, `PrecioVentaProducto`, `CodigoQRProducto`, `ImagenProducto`, `Categoria_idCategoria`) VALUES (090100089, 15648, 'COCINA A GAS C/HORNO', 3, 34.45, NULL, NULL, 100);
INSERT INTO `CatalagoProductos`.`Producto` (`idProducto`, `CodigoBERProducto`, `DescripcionProducto`, `StockProducto`, `PrecioVentaProducto`, `CodigoQRProducto`, `ImagenProducto`, `Categoria_idCategoria`) VALUES (050700002, 15649, 'HORNO ELECTRICO GRANDE', 0, 18, NULL, NULL, 100);
INSERT INTO `CatalagoProductos`.`Producto` (`idProducto`, `CodigoBERProducto`, `DescripcionProducto`, `StockProducto`, `PrecioVentaProducto`, `CodigoQRProducto`, `ImagenProducto`, `Categoria_idCategoria`) VALUES (050600005, 15650, 'CELULAR SMARTFONE BB Q10', 1, 90, NULL, NULL, 111);
INSERT INTO `CatalagoProductos`.`Producto` (`idProducto`, `CodigoBERProducto`, `DescripcionProducto`, `StockProducto`, `PrecioVentaProducto`, `CodigoQRProducto`, `ImagenProducto`, `Categoria_idCategoria`) VALUES (050600003, 15651, 'CELULAR SMARTFONE BB 9320/8520', 1, 25, NULL, NULL, 111);
INSERT INTO `CatalagoProductos`.`Producto` (`idProducto`, `CodigoBERProducto`, `DescripcionProducto`, `StockProducto`, `PrecioVentaProducto`, `CodigoQRProducto`, `ImagenProducto`, `Categoria_idCategoria`) VALUES (050100001, 15652, 'ESTABILIZADOR MEDIANO(1000 A 2000W)', 5, 30.40, NULL, NULL, 110);
INSERT INTO `CatalagoProductos`.`Producto` (`idProducto`, `CodigoBERProducto`, `DescripcionProducto`, `StockProducto`, `PrecioVentaProducto`, `CodigoQRProducto`, `ImagenProducto`, `Categoria_idCategoria`) VALUES (050100089, 15653, 'TECLADO USB', 15, 2, NULL, NULL, 110);

COMMIT;


-- -----------------------------------------------------
-- Data for table `CatalagoProductos`.`Almacen`
-- -----------------------------------------------------
START TRANSACTION;
USE `CatalagoProductos`;
INSERT INTO `CatalagoProductos`.`Almacen` (`idAlmacen`, `NombreAlmacen`) VALUES (001, 'La Paz');

COMMIT;


-- -----------------------------------------------------
-- Data for table `CatalagoProductos`.`Cliente`
-- -----------------------------------------------------
START TRANSACTION;
USE `CatalagoProductos`;
INSERT INTO `CatalagoProductos`.`Cliente` (`idCliente`, `NombreCliente`, `ApellidosCliente`, `CorreoCliente`) VALUES (0001, 'Eder', 'Cepeda Lopez', 'ederecl13@gmail.com');

COMMIT;

