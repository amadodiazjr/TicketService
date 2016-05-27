DROP TABLE IF EXISTS `level`;
DROP TABLE IF EXISTS `seat`;
DROP TABLE IF EXISTS `status`;
DROP TABLE IF EXISTS `customer`;
DROP TABLE IF EXISTS `orders`;

CREATE TABLE `level` (
  `id` int(4) NOT null AUTO_INCREMENT,
  `name` varchar(40) NOT null,
  `price` decimal(6, 2) NOT null,
  `rows` int(4) NOT null,
  `seats_per_row` int(4) NOT null,
  PRIMARY KEY (`id`)
);

CREATE TABLE `seat` (
  `id` int(4) NOT null AUTO_INCREMENT,
  `level_id` int(4) NOT null,
  `status_id` int(4) NOT null,
  `number` int(4) NOT null,
  `customer_id` int(4), 
  PRIMARY KEY (`id`),
  FOREIGN KEY (level_id) 
      REFERENCES level(id)
      ON DELETE CASCADE,
  FOREIGN KEY (status_id)
      REFERENCES status(id)
      ON DELETE CASCADE,
  FOREIGN KEY (customer_id)
      REFERENCES customer(id)
      ON DELETE CASCADE
);

CREATE TABLE `status` ( 
  `id` int(4) NOT null,
  `name` varchar(100) NOT null,
  PRIMARY KEY (`id`)
);

CREATE TABLE `customer` (
  `id` int(4) NOT null AUTO_INCREMENT,
  `email` varchar(100) NOT null,
  PRIMARY KEY (`id`)
);

CREATE TABLE `orders` (
  `id` int(4) NOT null AUTO_INCREMENT,
  `customer_id` int(4) NOT null, 
  `confirmation` varchar(100),
  PRIMARY KEY (`id`),
  FOREIGN KEY (customer_id)
      REFERENCES customer(id)
      ON DELETE CASCADE
);
