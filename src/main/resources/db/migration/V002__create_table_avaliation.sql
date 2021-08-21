CREATE TABLE `avaliation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `score` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_avaliation_to_book_id` (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;