CREATE DATABASE `university_jdbc`;
CREATE TABLE `person` (
  `idperson` int(11) NOT NULL AUTO_INCREMENT,
  `person_name` varchar(45) DEFAULT NULL,
  `person_surname` varchar(45) DEFAULT NULL,
  `person_year_of_birth` year(4) DEFAULT NULL,
  `person_is_student` bit(1) DEFAULT NULL,
  PRIMARY KEY (`idperson`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
