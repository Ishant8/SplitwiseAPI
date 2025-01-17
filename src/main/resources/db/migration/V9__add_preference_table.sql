CREATE TABLE `preference` (
                              `allow_authentication_with_biometrics` bit(1) DEFAULT NULL,
                              `allow_email_of_monthly_activity_summary` bit(1) DEFAULT NULL,
                              `allow_email_of_news_and_updates` bit(1) DEFAULT NULL,
                              `allow_email_when_added_as_friend` bit(1) DEFAULT NULL,
                              `allow_email_when_added_to_group` bit(1) DEFAULT NULL,
                              `allow_email_when_comments_on_expense` bit(1) DEFAULT NULL,
                              `allow_email_when_expense_added` bit(1) DEFAULT NULL,
                              `allow_email_when_expense_altered` bit(1) DEFAULT NULL,
                              `allow_email_when_expense_due` bit(1) DEFAULT NULL,
                              `allow_email_when_payment_received` bit(1) DEFAULT NULL,
                              `allow_suggestion_as_friend` bit(1) DEFAULT NULL,
                              `preference_id` int NOT NULL,
                              `timeout_before_authentication_expires` int DEFAULT NULL,
                              PRIMARY KEY (`preference_id`),
                              CONSTRAINT `FK1epx28w5g4sghq3q5pllksudb` FOREIGN KEY (`preference_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;