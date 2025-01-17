CREATE TABLE `payment` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `payment_amount` decimal(10,2) DEFAULT NULL,
                           `circle_id` int DEFAULT NULL,
                           `bigger_id` int DEFAULT NULL,
                           `smaller_id` int DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FK6jevu5exbg47hfeynfmdfv9es` (`circle_id`),
                           KEY `FK6mhg7qq0sdskkx490u5ebgm0v` (`bigger_id`,`smaller_id`),
                           CONSTRAINT `FK6jevu5exbg47hfeynfmdfv9es` FOREIGN KEY (`circle_id`) REFERENCES `circle` (`id`),
                           CONSTRAINT `FK6mhg7qq0sdskkx490u5ebgm0v` FOREIGN KEY (`bigger_id`, `smaller_id`) REFERENCES `user_friend` (`bigger_id`, `smaller_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;