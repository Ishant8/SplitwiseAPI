CREATE TABLE `user_friend` (
                               `bigger_id` int NOT NULL,
                               `smaller_id` int NOT NULL,
                               `money_owed` decimal(10,2) DEFAULT NULL,
                               PRIMARY KEY (`bigger_id`,`smaller_id`),
                               KEY `FKknnct1xggx0v2g4vhcjfv06ck` (`smaller_id`),
                               CONSTRAINT `FKen16dyn4xj7nvcis02bu57fvw` FOREIGN KEY (`bigger_id`) REFERENCES `user` (`id`),
                               CONSTRAINT `FKknnct1xggx0v2g4vhcjfv06ck` FOREIGN KEY (`smaller_id`) REFERENCES `user` (`id`),
                               CONSTRAINT `user_friend_chk_1` CHECK ((`smaller_id` < `bigger_id`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;