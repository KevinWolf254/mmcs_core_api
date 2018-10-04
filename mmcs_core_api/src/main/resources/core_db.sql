-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mmcs_proaktiv_db_v13
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mmcs_proaktiv_db_v13
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mmcs_proaktiv_db_v13` ;
USE `mmcs_proaktiv_db_v13` ;

-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`country` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `code` VARCHAR(4) NOT NULL,
  `currency` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `currency_UNIQUE` (`currency` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`product` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_product_country1_idx` (`country_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`service_provider`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`service_provider` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`service_provider` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_service_provider_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_service_provider_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`client` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `credit_amount` DOUBLE NOT NULL,
  `enabled` TINYINT NOT NULL,
  `created_on` DATETIME NULL,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_client_country1_idx` (`country_id` ASC) VISIBLE,
  CONSTRAINT `fk_client_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`sale`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`sale` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`sale` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  `type` TINYINT NOT NULL,
  `successful` TINYINT NOT NULL,
  `amount_info` VARCHAR(45) NOT NULL,
  `date` DATETIME NOT NULL,
  `product_id` INT NULL,
  `client_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sale_product1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_sale_client2_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_sale_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sale_client2`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`credit_disbursment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`credit_disbursment` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`credit_disbursment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pending` TINYINT NOT NULL,
  `date` DATETIME NULL,
  `sale_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_credit_disbursment_sale1_idx` (`sale_id` ASC) VISIBLE,
  CONSTRAINT `fk_credit_disbursment_sale1`
    FOREIGN KEY (`sale_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`sale` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`sender_id`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`sender_id` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`sender_id` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sender_id_client1_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_sender_id_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`client_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`client_user` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`client_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `phone_no` VARCHAR(13) NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_client_client1_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_client_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`token` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`token` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(255) NOT NULL,
  `expiry_date` DATETIME NOT NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_token_client1_idx` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_token_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`inventory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`inventory` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`inventory` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE NOT NULL,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_inventory_country1_idx` (`country_id` ASC) VISIBLE,
  CONSTRAINT `fk_inventory_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`cost_of_sale`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`cost_of_sale` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`cost_of_sale` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(20) NOT NULL,
  `type` TINYINT NULL,
  `currency` INT NOT NULL,
  `amount` DOUBLE NOT NULL,
  `date` DATETIME NOT NULL,
  `product_id` INT NULL,
  `client_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cost_product1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_cost_client1_idx` (`client_id` ASC) VISIBLE,
  INDEX `fk_cost_of_sale_country1_idx` (`currency` ASC) VISIBLE,
  CONSTRAINT `fk_cost_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cost_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cost_of_sale_country1`
    FOREIGN KEY (`currency`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`payment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`payment` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`payment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mode` VARCHAR(45) NOT NULL,
  `currency` INT NOT NULL,
  `amount` DOUBLE NOT NULL,
  `sale_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_payment_sale1_idx` (`sale_id` ASC) VISIBLE,
  INDEX `fk_payment_country1_idx` (`currency` ASC) VISIBLE,
  CONSTRAINT `fk_payment_sale1`
    FOREIGN KEY (`sale_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`sale` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_payment_country1`
    FOREIGN KEY (`currency`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`charge`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`charge` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`charge` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `type_UNIQUE` (`type` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`international_cost`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`international_cost` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`international_cost` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `currency` INT NOT NULL,
  `tax` DOUBLE NOT NULL,
  `amount` DOUBLE NOT NULL,
  `total` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_international_cost_country1_idx` (`currency` ASC) VISIBLE,
  CONSTRAINT `fk_international_cost_country1`
    FOREIGN KEY (`currency`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`cost`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`cost` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`cost` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `currency` INT NOT NULL,
  `tax` DOUBLE NOT NULL,
  `amount` DOUBLE NOT NULL,
  `total` DOUBLE NOT NULL,
  `product_id` INT NOT NULL,
  `charge_id` INT NOT NULL,
  `international_cost_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cost_product2_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_cost_charge1_idx` (`charge_id` ASC) VISIBLE,
  INDEX `fk_cost_international_cost1_idx` (`international_cost_id` ASC) VISIBLE,
  INDEX `fk_cost_country1_idx` (`currency` ASC) VISIBLE,
  CONSTRAINT `fk_cost_product2`
    FOREIGN KEY (`product_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cost_charge1`
    FOREIGN KEY (`charge_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`charge` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cost_international_cost1`
    FOREIGN KEY (`international_cost_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`international_cost` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cost_country1`
    FOREIGN KEY (`currency`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`international_price`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`international_price` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`international_price` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `currency` INT NOT NULL,
  `tax` DOUBLE NOT NULL,
  `margin` DOUBLE NOT NULL,
  `amount` DOUBLE NOT NULL,
  `total` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_international_price_country1_idx` (`currency` ASC) VISIBLE,
  CONSTRAINT `fk_international_price_country1`
    FOREIGN KEY (`currency`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`price`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`price` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`price` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `currency` INT NOT NULL,
  `tax` DOUBLE NOT NULL,
  `margin` DOUBLE NOT NULL,
  `amount` DOUBLE NOT NULL,
  `total` DOUBLE NOT NULL,
  `product_id` INT NOT NULL,
  `charge_id` INT NOT NULL,
  `international_price_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_price_product1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_price_charge1_idx` (`charge_id` ASC) VISIBLE,
  INDEX `fk_price_international_price1_idx` (`international_price_id` ASC) VISIBLE,
  INDEX `fk_price_country1_idx` (`currency` ASC) VISIBLE,
  CONSTRAINT `fk_price_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_price_charge1`
    FOREIGN KEY (`charge_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`charge` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_price_international_price1`
    FOREIGN KEY (`international_price_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`international_price` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_price_country1`
    FOREIGN KEY (`currency`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`sender_id_countries`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`sender_id_countries` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`sender_id_countries` (
  `sender_id_id` INT NOT NULL,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`sender_id_id`, `country_id`),
  INDEX `fk_sender_id_has_country_country1_idx` (`country_id` ASC) VISIBLE,
  INDEX `fk_sender_id_has_country_sender_id1_idx` (`sender_id_id` ASC) VISIBLE,
  CONSTRAINT `fk_sender_id_has_country_sender_id1`
    FOREIGN KEY (`sender_id_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`sender_id` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sender_id_has_country_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mmcs_proaktiv_db_v13`.`exchange_rate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mmcs_proaktiv_db_v13`.`exchange_rate` ;

CREATE TABLE IF NOT EXISTS `mmcs_proaktiv_db_v13`.`exchange_rate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `from` INT NOT NULL,
  `to` INT NOT NULL,
  `rate` DOUBLE NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_exchange_rate_country1_idx` (`from` ASC) VISIBLE,
  INDEX `fk_exchange_rate_country2_idx` (`to` ASC) VISIBLE,
  CONSTRAINT `fk_exchange_rate_country1`
    FOREIGN KEY (`from`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_exchange_rate_country2`
    FOREIGN KEY (`to`)
    REFERENCES `mmcs_proaktiv_db_v13`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
