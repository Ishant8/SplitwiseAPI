CREATE TABLE `circle`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) DEFAULT NULL,
    `name`       varchar(100) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;