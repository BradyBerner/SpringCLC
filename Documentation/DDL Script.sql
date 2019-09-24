-- MySQL Workbench Synchronization
-- Generated: 2019-09-23 15:47
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Brady Berner

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

ALTER TABLE `laravelCLC`.`USERS` 
DROP COLUMN `IDUSERS`,
ADD COLUMN `ID` INT(11) NOT NULL AUTO_INCREMENT FIRST,
ADD COLUMN `PHONENUMBER` VARCHAR(10) NOT NULL AFTER `EMAIL`,
CHANGE COLUMN `FIRSTNAME` `FIRSTNAME` VARCHAR(45) NOT NULL AFTER `ID`,
CHANGE COLUMN `LASTNAME` `LASTNAME` VARCHAR(45) NOT NULL AFTER `FIRSTNAME`,
CHANGE COLUMN `ROLE` `ROLE` INT(11) NOT NULL DEFAULT 0 AFTER `PHONENUMBER`,
CHANGE COLUMN `USERNAME` `USERNAME` VARCHAR(45) NOT NULL ,
CHANGE COLUMN `PASSWORD` `PASSWORD` VARCHAR(45) NOT NULL ,
CHANGE COLUMN `EMAIL` `EMAIL` VARCHAR(45) NOT NULL ,
CHANGE COLUMN `STATUS` `STATUS` INT(11) NOT NULL DEFAULT 0 ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`ID`);
;

DROP TABLE IF EXISTS `laravelCLC`.`USER_INFO` ;

DROP TABLE IF EXISTS `laravelCLC`.`SKILLS` ;

DROP TABLE IF EXISTS `laravelCLC`.`JOBS` ;

DROP TABLE IF EXISTS `laravelCLC`.`JOBAPPLICANTS` ;

DROP TABLE IF EXISTS `laravelCLC`.`EXPERIENCE` ;

DROP TABLE IF EXISTS `laravelCLC`.`EDUCATION` ;

DROP TABLE IF EXISTS `laravelCLC`.`AFFINITYGROUPS` ;

DROP TABLE IF EXISTS `laravelCLC`.`AFFINITYGROUPMEMBER` ;

DROP TABLE IF EXISTS `laravelCLC`.`ADDRESS` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;