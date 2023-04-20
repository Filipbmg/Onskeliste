DROP DATABASE IF EXISTS onskelistefilip;
CREATE SCHEMA onskelistefilip;

CREATE TABLE onskelistefilip.users (
	`id` INT NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(20) NOT NULL,
    `password` VARCHAR(35) NOT NULL,
	PRIMARY KEY (`id`));

CREATE TABLE onskelistefilip.links (
	`id`INT NOT NULL AUTO_INCREMENT,
	`user_id`INT NOT NULL,
    `link` VARCHAR(400) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES onskelistefilip.users(`id`));