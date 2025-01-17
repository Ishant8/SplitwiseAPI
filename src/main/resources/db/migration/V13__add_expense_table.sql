CREATE TABLE `expense` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `amount` decimal(10,2) DEFAULT NULL,
                           `expense_date` datetime(6) DEFAULT NULL,
                           `name` varchar(100) DEFAULT NULL,
                           `notes` varchar(1000) DEFAULT NULL,
                           `circle_id` int DEFAULT NULL,
                           `creator_id` int DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FKgwhsopr64yuf3o6l1ftvv1mqb` (`circle_id`),
                           KEY `FKe940h8bvdvgtfopxh2bm5t4t7` (`creator_id`),
                           CONSTRAINT `FKe940h8bvdvgtfopxh2bm5t4t7` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
                           CONSTRAINT `FKgwhsopr64yuf3o6l1ftvv1mqb` FOREIGN KEY (`circle_id`) REFERENCES `circle` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;