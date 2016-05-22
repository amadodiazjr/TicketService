DROP TABLE IF EXISTS `seat`;
DROP TABLE IF EXISTS `seats_on_hold`;
DROP TABLE IF EXISTS `seats_on_reserve`;
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

CREATE TABLE `seats_on_hold` ( 
  `seat_id` int(4) NOT null,
  `customer_id` int(4) NOT null, 
  PRIMARY KEY (`seat_id`, `customer_id`),
  FOREIGN KEY (seat_id)
      REFERENCES seat(id)
      ON DELETE CASCADE,
  FOREIGN KEY (customer_id)
      REFERENCES customer(id)
      ON DELETE CASCADE
);

CREATE TABLE `seat_on_reserve` ( 
  `seat_id` int(4) NOT null,
  `customer_id` int(4) NOT null, 
  PRIMARY KEY (`seat_id`, `customer_id`),
  FOREIGN KEY (seat_id)
      REFERENCES seat(id)
      ON DELETE CASCADE,
  FOREIGN KEY (customer_id)
      REFERENCES customer(id)
      ON DELETE CASCADE
);
