CREATE DATABASE IF NOT EXISTS oficina_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

use oficina_db;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `admin` boolean default false,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `client` (
  `client_id` int(11) NOT NULL AUTO_INCREMENT,
  `client_name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cpf` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `cpf` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `car` (
  `car_id` int(11) NOT NULL AUTO_INCREMENT,
  `brand` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `plate` varchar(20) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `mileage` int(11) DEFAULT NULL,
  `client_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`car_id`),
  UNIQUE KEY `plate` (`plate`),
  KEY `car_ibfk_1` (`client_id`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `part` (
  `part_id` int(11) NOT NULL AUTO_INCREMENT,
  `part_name` varchar(255) NOT NULL,
  `part_price` double NOT NULL,
  `manufacturer` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`part_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(255) NOT NULL,
  `service_price` double NOT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `budget` (
  `budget_id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) DEFAULT NULL,
  `budget_price` double DEFAULT NULL,
  `budget_date_start` datetime DEFAULT NULL,
  `budget_date_finish` datetime DEFAULT NULL,
  PRIMARY KEY (`budget_id`),
  KEY `budget_ibfk_1` (`car_id`),
  CONSTRAINT `budget_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) DEFAULT NULL,
  `order_price` double DEFAULT NULL,
  `order_date_start` datetime DEFAULT NULL,
  `order_date_finish` datetime DEFAULT NULL,
  `completed` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`order_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `budget_parts` (
  `bp_id` int primary key auto_increment,
  `budget_id` INT NOT NULL,
  `part_id` INT NOT NULL,

  CONSTRAINT `fk_budget_parts_budget`
    FOREIGN KEY (`budget_id`)
    REFERENCES `budget` (`budget_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

  CONSTRAINT `fk_budget_parts_part`
    FOREIGN KEY (`part_id`)
    REFERENCES `part` (`part_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `budget_services` (
`bs_id` int primary key auto_increment,
  `budget_id` INT NOT NULL,
  `service_id` INT NOT NULL,

  CONSTRAINT `fk_budget_services_budget`
    FOREIGN KEY (`budget_id`)
    REFERENCES `budget` (`budget_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

  CONSTRAINT `fk_budget_services_service`
    FOREIGN KEY (`service_id`)
    REFERENCES `service` (`service_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `order_parts` (
`op_id` INT primary key auto_increment,
  `order_id` INT NOT NULL,
  `part_id` INT NOT NULL,

  CONSTRAINT `fk_order_parts_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `order` (`order_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

  CONSTRAINT `fk_order_parts_part`
    FOREIGN KEY (`part_id`)
    REFERENCES `part` (`part_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `order_services` (
`os_id` INT primary key auto_increment,
  `order_id` INT NOT NULL,
  `service_id` INT NOT NULL,


  CONSTRAINT `fk_order_services_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `order` (`order_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

  CONSTRAINT `fk_order_services_service`
    FOREIGN KEY (`service_id`)
    REFERENCES `service` (`service_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
