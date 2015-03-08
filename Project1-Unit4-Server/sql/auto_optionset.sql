CREATE TABLE IF NOT EXISTS `auto_optionset` (
`auto_id` int(11),
`optionset_id` int(11),
PRIMARY KEY (`auto_id`,`optionset_id`),
CONSTRAINT `fk_auto_optionset_2` FOREIGN KEY (`optionset_id`) REFERENCES `optionset` (`optionset_id`) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT `fk_auto_optionset_1` FOREIGN KEY (`auto_id`) REFERENCES `auto` (`auto_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

