DROP DATABASE IF EXISTS onskelistefilip;

CREATE SCHEMA onskelistefilip;

DROP TABLE IF EXISTS users;

CREATE TABLE onskelisteFILIP.users (
	`id` INT NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(20) NOT NULL,
    `password` VARCHAR(35) NOT NULL,
	PRIMARY KEY (`id`));