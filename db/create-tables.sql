DROP TABLE IF EXISTS `level`;

CREATE TABLE `level` (
  `id` int(4) NOT null AUTO_INCREMENT,
  `name` varchar(40) NOT null,
  `price` int(4) NOT null,
  `rows` int(4) NOT null,
  `seats_per_row` int(4) NOT null,
  PRIMARY KEY (`id`)
);
