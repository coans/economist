-- MySQL Workbench Synchronization
-- Generated: 2015-12-16 00:47
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Milan

CREATE SCHEMA `economist` DEFAULT CHARACTER SET utf8 ;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE TABLE IF NOT EXISTS `economist`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(80) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `gender` ENUM('Female', 'Male') NOT NULL,
  `uuid` VARCHAR(45) NULL DEFAULT NULL,
  `created` DATETIME NOT NULL,
  `status` ENUM('Active','Inactive') NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `economist`.`preduzece` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(80) NOT NULL,
  `adresa` VARCHAR(150) NOT NULL,
  `telefon` VARCHAR(45) NOT NULL,
  `mobilni` VARCHAR(45) NOT NULL,
  `ziroracun` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `preduzece_user_idx` (`user_id` ASC),
  CONSTRAINT `preduzece_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `economist`.`user` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;  

CREATE TABLE IF NOT EXISTS `economist`.`konto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sifra` VARCHAR(6) NOT NULL,
  `naziv` VARCHAR(100) NOT NULL,
  `user_id` INT NOT NULL,
  `napomena` VARCHAR(50) NULL,
  PRIMARY KEY (`id`),
  INDEX `konto_user_idx` (`user_id` ASC),
  CONSTRAINT `konto_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `economist`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `economist`.`nalog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `broj` VARCHAR(45) NOT NULL,
  `datum` DATE NOT NULL,
  `opis` VARCHAR(50) NOT NULL,
  `duguje` DECIMAL(10,2) NOT NULL,
  `potrazuje` DECIMAL(10,2) NOT NULL,
  `saldo` DECIMAL(10,2) NOT NULL,
  `napomena` VARCHAR(50) NULL,
  `konto_id` INT NOT NULL,
  `preduzece_id` INT NOT NULL,
  `vrstadokumenta_id` INT NOT NULL,
  `komitent_id` INT(11) NULL,
  PRIMARY KEY (`id`),
  INDEX `nalog_konto_idx` (`konto_id` ASC),
  INDEX `nalog_preduzece_idx` (`preduzece_id` ASC),
  INDEX `nalog_vrstadokumenta_idx` (`vrstadokumenta_id` ASC),
  INDEX `nalog_komitent_idx` (`komitent_id` ASC),
  CONSTRAINT `nalog_konto`
    FOREIGN KEY (`konto_id`)
    REFERENCES `economist`.`konto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `nalog_preduzece`
  FOREIGN KEY (`preduzece_id`)
  REFERENCES `economist`.`preduzece` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `nalog_vrstadokumenta`
  FOREIGN KEY (`vrstadokumenta_id`)
  REFERENCES `economist`.`vrstadokumenta` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION);

  CREATE TABLE IF NOT EXISTS `economist`.`vrstadokumenta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sifra` INT NOT NULL,
  `naziv` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `vrstadokumenta_user_idx` (`user_id` ASC),
  CONSTRAINT `vrstadokumenta_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `economist`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
 
  CREATE TABLE `economist`.`komitent` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(100) NOT NULL,
  `mesto` VARCHAR(100) NOT NULL,
  `adresa` VARCHAR(200) NOT NULL,
  `ziroracun` VARCHAR(50) NOT NULL,
  `napomena` VARCHAR(200) NULL,
  `telefon` VARCHAR(45) NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `komitent_user_idx` (`user_id` ASC),
  CONSTRAINT `komitent_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `economist`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
  ALTER TABLE `economist`.`vrstadokumenta` ADD UNIQUE INDEX `sifra_user_unique` (`sifra` ASC, `user_id` ASC);
 
  INSERT INTO `economist`.`user` (`email`, `password`, `first_name`, `last_name`, `gender`, `created`, `status`) VALUES ('coa', '89ec8da57db1f394906736ce9224fb9d99841c288572c3ada3018db668aff0d6fa5bf098b0a35072', 'admin', 'admin', '1', '2018-03-16 11:06:25', 'Active');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
