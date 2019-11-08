-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema springclc
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema springclc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `springclc` DEFAULT CHARACTER SET utf8 ;
USE `springclc` ;

-- -----------------------------------------------------
-- Table `springclc`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springclc`.`users` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `FIRSTNAME` VARCHAR(45) NOT NULL,
  `LASTNAME` VARCHAR(45) NOT NULL,
  `USERNAME` VARCHAR(45) NOT NULL,
  `PASSWORD` VARCHAR(45) NOT NULL,
  `EMAIL` VARCHAR(45) NOT NULL,
  `PHONENUMBER` VARCHAR(13) NOT NULL,
  `ROLE` INT(11) NOT NULL DEFAULT '0',
  `STATUS` INT(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `springclc`.`albums`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springclc`.`albums` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `USERS_ID` INT(11) NOT NULL,
  `NAME` VARCHAR(40) NOT NULL,
  `ARTIST` VARCHAR(40) NOT NULL,
  `DESCRIPTION` VARCHAR(255) NULL DEFAULT NULL,
  `GENRE` VARCHAR(14) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`, `USERS_ID`),
  INDEX `fk_products_users_idx` (`USERS_ID` ASC),
  CONSTRAINT `fk_products_users`
    FOREIGN KEY (`USERS_ID`)
    REFERENCES `springclc`.`users` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `springclc`.`songs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `springclc`.`songs` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `ALBUMS_ID` INT(11) NOT NULL,
  `NAME` VARCHAR(40) NOT NULL,
  `ARTIST` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`ID`, `ALBUMS_ID`),
  INDEX `fk_songs_albums1_idx` (`ALBUMS_ID` ASC),
  CONSTRAINT `fk_songs_albums1`
    FOREIGN KEY (`ALBUMS_ID`)
    REFERENCES `springclc`.`albums` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
