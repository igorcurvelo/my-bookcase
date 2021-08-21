CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `isbn` varchar(255) NOT NULL,
  `number_of_pages` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_book_isbn` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;