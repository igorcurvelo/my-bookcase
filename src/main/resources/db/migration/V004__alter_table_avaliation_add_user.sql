ALTER TABLE `avaliation` ADD `user_id` int(11);
ALTER TABLE `avaliation` ADD CONSTRAINT `fk_avaliation_to_user_id` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);