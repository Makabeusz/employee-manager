DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT,
                                  `first_name` varchar(45) NOT NULL,
                                  `second_name` varchar(45) DEFAULT NULL,
                                  `last_name` varchar(45) NOT NULL,
                                  `birth_date` date NOT NULL,
                                  `personal_id` varchar(45) NOT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE (personal_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `family`;
CREATE TABLE `family` (
                               `id` int(11)  NOT NULL,
                               `first_name` varchar(45) NOT NULL,
                               `second_name` varchar(45) NOT NULL,
                               `kinship` varchar(45) NOT NULL,
                               `birth_date` date NOT NULL,
                               FOREIGN KEY (id) REFERENCES `employees`(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `education`;
CREATE TABLE `education` (
                                  `id` int(11)  NOT NULL,
                                  `degree` varchar(45) NOT NULL,
                                  `school_name` varchar(45) NOT NULL,
                                  `address` varchar(90) DEFAULT NULL,
                                  `start_date` date NOT NULL,
                                  `finish_date` date NOT NULL,
                                  FOREIGN KEY (id) REFERENCES `employees`(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `employees` (first_name, second_name, last_name, birth_date, personal_id)
VALUES ('Patrick', 'Wayne', 'Swayze', '1952-08-12', '52081212346'),
       ('John', null, 'Wayne', '1907-05-26', '07052612356'),
       ('Calvin', 'Cordozar', 'Broadus', '1971-10-20', '71102012346');

INSERT INTO education(id, degree, school_name, address, start_date, finish_date)
VALUES('1', 'Master', 'University of London', 'Gower Street, London, WC1E 6BT', '2013-10-01', '2015-06-29'),
      ('1', 'Bachelor', 'University of London', 'Gower Street, London, WC1E 6BT', '2010-10-01', '2013-07-02'),
      ('2', 'Secondary', 'College of Lake County', '19351 W Washington St., Grayslake, IL, 60030-1198', '1990-10-01', '1995-06-25');