DROP TABLE IF EXISTS `seat`;
DROP TABLE IF EXISTS `seat_hold`;
DROP TABLE IF EXISTS `seat_reserved`;
DROP TABLE IF EXISTS `customer`;
DROP TABLE IF EXISTS `level`;

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
  `number` int(4) NOT null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (level_id) 
      REFERENCES level(id)
      ON DELETE CASCADE
);

CREATE TABLE `customer` (
  `id` int(4) NOT null AUTO_INCREMENT,
  `email` varchar(100) NOT null,
  PRIMARY KEY (`id`)
);

CREATE TABLE `seat_hold` (
  `id` int(4) NOT null AUTO_INCREMENT,
  `customer_id` int(4) NOT null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (customer_id)
      REFERENCES customer(id)
      ON DELETE CASCADE
);

CREATE TABLE `seat_reserved` (
  `id` int(4) NOT null AUTO_INCREMENT,
  `customer_id` int(4) NOT null,
  PRIMARY KEY (`id`),
  FOREIGN KEY (customer_id)
      REFERENCES customer(id)
      ON DELETE CASCADE
);
