CREATE TABLE `user_expense` (
                                `expense_share` decimal(10,2) DEFAULT NULL,
                                `expense_id` int NOT NULL,
                                `user_id` int NOT NULL,
                                PRIMARY KEY (`expense_id`,`user_id`),
                                KEY `FKl006xf0q0dc8aeex10cqran0q` (`user_id`),
                                CONSTRAINT `FKkk40759a6but9cug2lpcy3j7` FOREIGN KEY (`expense_id`) REFERENCES `expense` (`id`),
                                CONSTRAINT `FKl006xf0q0dc8aeex10cqran0q` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;