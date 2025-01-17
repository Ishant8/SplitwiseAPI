CREATE TABLE `user_circle` (
                               `circle_id` int NOT NULL,
                               `user_id` int NOT NULL,
                               PRIMARY KEY (`circle_id`,`user_id`),
                               KEY `FKptr7bgihj4apr0u4dl7hndkiw` (`user_id`),
                               CONSTRAINT `FKew6yhn3et33tb02mdsrox38fv` FOREIGN KEY (`circle_id`) REFERENCES `circle` (`id`),
                               CONSTRAINT `FKptr7bgihj4apr0u4dl7hndkiw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;