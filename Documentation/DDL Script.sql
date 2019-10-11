-- MySQL Script generated by MySQL Workbench
-- Thu Oct 10 22:35:13 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`USERS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`USERS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `FIRSTNAME` VARCHAR(45) NOT NULL,
  `LASTNAME` VARCHAR(45) NOT NULL,
  `USERNAME` VARCHAR(45) NOT NULL,
  `PASSWORD` VARCHAR(45) NOT NULL,
  `EMAIL` VARCHAR(45) NOT NULL,
  `PHONENUMBER` VARCHAR(10) NOT NULL,
  `ROLE` INT NOT NULL DEFAULT 0,
  `STATUS` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PRODUCTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PRODUCTS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USERS_ID` INT NOT NULL,
  `NAME` VARCHAR(40) NOT NULL,
  `DESCRIPTION` VARCHAR(255) NULL,
  `GENRE` VARCHAR(14) NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_PRODUCTS_USERS_idx` (`USERS_ID` ASC),
  CONSTRAINT `fk_PRODUCTS_USERS`
    FOREIGN KEY (`USERS_ID`)
    REFERENCES `mydb`.`USERS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
