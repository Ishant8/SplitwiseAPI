CREATE TABLE `user_friend_circle` (
                                      `bigger_id` int NOT NULL,
                                      `smaller_id` int NOT NULL,
                                      `circle_id` int NOT NULL,
                                      `owes_in_group` decimal(10,2) DEFAULT NULL,
                                      PRIMARY KEY (`circle_id`,`bigger_id`,`smaller_id`),
                                      KEY `FKj6q75y9byedt45gwu6te3ka7g` (`bigger_id`,`smaller_id`),
                                      CONSTRAINT `FK7c5or6h6fmkhpjdxxrsluay3f` FOREIGN KEY (`circle_id`) REFERENCES `circle` (`id`),
                                      CONSTRAINT `FKj6q75y9byedt45gwu6te3ka7g` FOREIGN KEY (`bigger_id`, `smaller_id`) REFERENCES `user_friend` (`bigger_id`, `smaller_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;