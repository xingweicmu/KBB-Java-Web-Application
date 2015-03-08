CREATE TABLE IF NOT EXISTS `optionset_option` (
`optionset_id` int(11) NOT NULL,
`option_id` int(11) NOT NULL,
PRIMARY KEY (`optionset_id`,`option_id`),
CONSTRAINT `fk_optionset_option_1` FOREIGN KEY (`optionset_id`) REFERENCES `optionset` (`optionset_id`) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT `fk_optionset_option_2` FOREIGN KEY (`option_id`) REFERENCES `options` (`option_id`) ON DELETE CASCADE ON UPDATE CASCADE
);
