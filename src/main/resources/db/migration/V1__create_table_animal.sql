CREATE TABLE `animal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `species` varchar(255) NOT NULL,
  `breed` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `date_of_birth` varchar(255) NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
);