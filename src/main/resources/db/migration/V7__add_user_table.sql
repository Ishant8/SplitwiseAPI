CREATE TABLE `user` (
                        `currency_id` int DEFAULT NULL,
                        `id` int NOT NULL AUTO_INCREMENT,
                        `timezone_id` bigint DEFAULT NULL,
                        `phone` varchar(20) DEFAULT NULL,
                        `password` varchar(60) NOT NULL,
                        `email` varchar(100) NOT NULL,
                        `full_name` varchar(100) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
                        KEY `FK85lk0lgcog7dyc2q87n8keku` (`currency_id`),
                        KEY `FKhfs8m1d9mkq1muwonij0bx2ak` (`timezone_id`),
                        CONSTRAINT `FK85lk0lgcog7dyc2q87n8keku` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`),
                        CONSTRAINT `FKhfs8m1d9mkq1muwonij0bx2ak` FOREIGN KEY (`timezone_id`) REFERENCES `timezone` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;