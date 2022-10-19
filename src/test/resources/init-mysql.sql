DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(45) NOT NULL,
    `second_name` VARCHAR(45) DEFAULT NULL,
    `last_name` VARCHAR(45) NOT NULL,
    `birth_date` DATE NOT NULL,
    `personal_id` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (personal_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `family`;
CREATE TABLE `family` (
    `id` INT(11) NOT NULL,
    `first_name` VARCHAR(45) NOT NULL,
    `second_name` VARCHAR(45) DEFAULT NULL,
    `last_name` VARCHAR(45) NOT NULL,
    `kinship` ENUM('spouse', 'child') NOT NULL,
    `birth_date` DATE NOT NULL,
    FOREIGN KEY (id) REFERENCES employees(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `education`;
CREATE TABLE `education` (
    `id` INT(11) NOT NULL,
    `degree` VARCHAR(45) NOT NULL,
    `school_name` VARCHAR(45) NOT NULL,
    `address` VARCHAR(90) DEFAULT NULL,
    `start_date` DATE NOT NULL,
    `finish_date` DATE NOT NULL,
    FOREIGN KEY (id) REFERENCES employees(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `username` VARCHAR(50) NOT NULL PRIMARY KEY,
    `email` VARCHAR(254) NOT NULL UNIQUE,
    `password` VARCHAR (200) NOT NULL,
    `password_salt` VARCHAR (30) NOT NULL,
    `password_hash_algorithm` VARCHAR(15) NOT NULL,
    `enabled` BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY(username) REFERENCES users(username)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO employees (first_name, second_name, last_name, birth_date, personal_id)
VALUES ('Patrick', 'Wayne', 'Swayze', '1952-08-12', '52081212346'),
       ('John', null, 'Wayne', '1907-05-26', '07052612356'),
       ('Calvin', 'Cordozar', 'Broadus', '1971-10-20', '71102012346');

INSERT INTO education(id, degree, school_name, address, start_date, finish_date)
VALUES('1', 'Master', 'University of London', 'Gower Street, London, WC1E 6BT', '2013-10-01', '2015-06-29'),
      ('1', 'Bachelor', 'University of London', 'Gower Street, London, WC1E 6BT', '2010-10-01', '2013-07-02'),
      ('2', 'Secondary', 'College of Lake County', '19351 W Washington St., Grayslake, IL, 60030-1198', '1990-10-01', '1995-06-25');

INSERT INTO family (id, first_name, second_name, last_name, birth_date, kinship)
VALUES ('1', 'John', 'Joshua', 'Swayze', '1980-06-12', 'child'),
       ('1', 'Jane', null, 'Swayze', '1953-06-17', 'spouse'),
       ('2', 'Mary', 'Jane', 'Wayne', '1921-12-25', 'spouse');

INSERT INTO users (username, email, password, password_salt, password_hash_algorithm, enabled)
VALUES ('admin', 'john-wick@proton.me', '$2a$12$uY6DbnZMr8HSAlDvCXvsveOUcExJWRqIDdRCdSO5j3q7omDPcWASK', 'admiDummyS4lt', 'SHA256', '1'),
       ('user', 'hank_moore3@wp.pl', '$2a$12$dpMfEKdUkxfDDGs8O6VwMuegRxr1PDK5fZyFWuZT5t/X.5wKqN7oC', 'userDummyS4lt', 'SHA256', '1');

INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN'),
       ('user', 'ROLE_USER');
