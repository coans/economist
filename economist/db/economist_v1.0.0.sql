-- MySQL Workbench Synchronization
-- Generated: 2015-12-16 00:47
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Milan

CREATE SCHEMA `alfaekonomist` DEFAULT CHARACTER SET utf8 ;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE TABLE IF NOT EXISTS `alfaekonomist`.`Agencija` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(100) NOT NULL,
  `created` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `alfaekonomist`.`Preduzece` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(80) NOT NULL,
  `adresa` VARCHAR(150) NOT NULL,
  `telefon` VARCHAR(45) NOT NULL,
  `mobilni` VARCHAR(45) NOT NULL,
  `ziroracun` VARCHAR(45) NOT NULL,
  `agencija_id` INT NOT NULL,
  `created` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `preduzece_agencija_idx` (`agencija_id` ASC),
  CONSTRAINT `preduzece_agencija`
  FOREIGN KEY (`agencija_id`)
  REFERENCES `alfaekonomist`.`Agencija` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;  

 CREATE TABLE IF NOT EXISTS `alfaekonomist`.`VrstaDokumenta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sifra` INT NOT NULL,
  `naziv` VARCHAR(45) NOT NULL,
  `agencija_id` INT NOT NULL,
  `created` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `vrstadokumenta_agencija_idx` (`agencija_id` ASC),
  CONSTRAINT `vrstadokumenta_agencija`
    FOREIGN KEY (`agencija_id`)
    REFERENCES `alfaekonomist`.`Agencija` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
 
  CREATE TABLE `alfaekonomist`.`Komitent` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(100) NOT NULL,
  `mesto` VARCHAR(100) NOT NULL,
  `adresa` VARCHAR(200) NOT NULL,
  `ziroracun` VARCHAR(50) NULL,
  `napomena` VARCHAR(200) NULL,
  `telefon` VARCHAR(45) NULL,
  `usistemupdv` TINYINT(1) NULL DEFAULT 0 ,
  `lokacija` ENUM('RS','FBIH', 'Brcko D.') NOT NULL,
  `agencija_id` INT NOT NULL,
  `created` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `komitent_agencija_idx` (`agencija_id` ASC),
  CONSTRAINT `komitent_agencija`
    FOREIGN KEY (`agencija_id`)
    REFERENCES `alfaekonomist`.`Agencija` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `alfaekonomist`.`User` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(80) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `uuid` VARCHAR(45) NULL DEFAULT NULL,
  `created` DATETIME NOT NULL,
  `status` ENUM('Active','Inactive') NOT NULL,
  `preduzece_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_preduzece_idx` (`preduzece_id` ASC),
  CONSTRAINT `user_preduzece`
  FOREIGN KEY (`preduzece_id`)
  REFERENCES `alfaekonomist`.`Preduzece` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `alfaekonomist`.`Konto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sifra` VARCHAR(6) NOT NULL,
  `naziv` VARCHAR(500) NOT NULL,
  `agencija_id` INT NOT NULL,
  `napomena` VARCHAR(50) NULL,
  `created` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `konto_agencija_idx` (`agencija_id` ASC),
  CONSTRAINT `konto_agencija`
    FOREIGN KEY (`agencija_id`)
    REFERENCES `alfaekonomist`.`Agencija` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `alfaekonomist`.`Nalog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `broj` VARCHAR(45) NOT NULL,
  `created` DATE NOT NULL,
  `modified` DATE NOT NULL,
  `opis` VARCHAR(50) NOT NULL,
  `napomena` VARCHAR(50) NULL,
  `preduzece_id` INT NOT NULL,
  `zakljucan` TINYINT(1) NULL DEFAULT '0',
  `vrstadokumenta_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `nalog_preduzece_idx` (`preduzece_id` ASC),
  INDEX `nalog_vrstadokumenta_idx` (`vrstadokumenta_id` ASC),
  CONSTRAINT `nalog_vrstadokumenta`
  FOREIGN KEY (`vrstadokumenta_id`)
  REFERENCES `alfaekonomist`.`VrstaDokumenta` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `nalog_preduzece`
  FOREIGN KEY (`preduzece_id`)
  REFERENCES `alfaekonomist`.`Preduzece` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION);
  
  CREATE TABLE IF NOT EXISTS `alfaekonomist`.`StavkaNaloga` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datum` DATE NOT NULL,
  `opis` VARCHAR(50) NOT NULL,
  `vrsta` ENUM('STAVKA','PROTIVSTAVKA','PDV') NOT NULL,
  `duguje` DECIMAL(10,2) NOT NULL,
  `potrazuje` DECIMAL(10,2) NOT NULL,
  `saldo` DECIMAL(10,2) NOT NULL,
  `nalog_id` INT NOT NULL,
  `konto_id` INT NOT NULL,
  `komitent_id` INT(11) NULL,
  `broj_fakture` VARCHAR(50) NULL,
  `identifikator` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  INDEX `stavkanaloga_nalog_idx` (`nalog_id` ASC),
  INDEX `stavkanaloga_konto_idx` (`konto_id` ASC),
  INDEX `stavkanaloga_komitent_idx` (`komitent_id` ASC),
  CONSTRAINT `stavkanaloga_nalog`
    FOREIGN KEY (`nalog_id`)
    REFERENCES `alfaekonomist`.`Nalog` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `stavkanaloga_konto`
    FOREIGN KEY (`konto_id`)
    REFERENCES `alfaekonomist`.`Konto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `stavkanaloga_komitent`
  FOREIGN KEY (`komitent_id`)
  REFERENCES `alfaekonomist`.`Komitent` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION);
   
  ALTER TABLE `alfaekonomist`.`VrstaDokumenta` ADD UNIQUE INDEX `sifra_agencija_unique` (`sifra` ASC, `agencija_id` ASC);
 
  INSERT INTO `alfaekonomist`.`Agencija` (`id`, `created`, `naziv`) VALUES ('1', '2018-03-16 11:06:25', 'IT-OROZ'); 
  INSERT INTO `alfaekonomist`.`Preduzece` (`id`, `naziv`, `adresa`, `telefon`, `mobilni`, `ziroracun`, `agencija_id`, `created`) VALUES ('1', 'Preduzece 1', 'Adresa 1', '021...', '064...', '321564', '1', '2018-03-16 11:06:25');
  INSERT INTO `alfaekonomist`.`User` (`email`, `password`, `first_name`, `last_name`, `created`, `status`, `preduzece_id`) VALUES ('coa', '89ec8da57db1f394906736ce9224fb9d99841c288572c3ada3018db668aff0d6fa5bf098b0a35072', 'admin', 'admin', '2018-03-16 11:06:25', 'Active', '1');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
