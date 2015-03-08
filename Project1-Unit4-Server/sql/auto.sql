CREATE TABLE IF NOT EXISTS `auto` (
`auto_id` int(11) NOT NULL AUTO_INCREMENT,
`model` varchar(45) NOT NULL,
`make` varchar(45) DEFAULT NULL,
`base_price` float DEFAULT NULL,
PRIMARY KEY (`auto_id`)
);
