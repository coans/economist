ALTER TABLE `economist`.`agencija` 
ADD COLUMN `email` VARCHAR(100) NULL AFTER `created`;

ALTER TABLE `economist`.`agencija` ADD COLUMN `telefon` VARCHAR(100) NULL AFTER `email`;
ALTER TABLE `economist`.`agencija` ADD COLUMN `ziroRacun` VARCHAR(100) NULL AFTER `telefon`;
ALTER TABLE `economist`.`agencija` ADD COLUMN `jib` VARCHAR(100) NULL AFTER `ziroRacun`;
ALTER TABLE `economist`.`agencija` ADD COLUMN `ulica` VARCHAR(100) NULL AFTER `jib`;
ALTER TABLE `economist`.`agencija` ADD COLUMN `naselje` VARCHAR(100) NULL AFTER `ulica`;
ALTER TABLE `economist`.`agencija` ADD COLUMN `postanskiBroj` VARCHAR(100) NULL AFTER `naselje`;
ALTER TABLE `economist`.`agencija` ADD COLUMN `grad` VARCHAR(100) NULL AFTER `postanskiBroj`;
ALTER TABLE `economist`.`agencija` ADD COLUMN `odgovornoLice` VARCHAR(100) NULL AFTER `grad`;

ALTER TABLE `economist`.`preduzece` ADD COLUMN `jib` VARCHAR(100) NULL;
ALTER TABLE `economist`.`preduzece` ADD COLUMN `iznos` VARCHAR(100) NULL;
ALTER TABLE `economist`.`preduzece` ADD COLUMN `redniBrojFakture` int NULL;
ALTER TABLE `economist`.`preduzece` ADD COLUMN `godinaFakture` int NULL;
ALTER TABLE `economist`.`preduzece` ADD COLUMN `mjesecnaFaktura` TINYINT(1) NULL DEFAULT 0;