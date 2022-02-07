CREATE SCHEMA IF NOT EXISTS `shop_test` DEFAULT CHARACTER SET utf8;
USE `shop_test`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) DEFAULT NULL,
                         `email` varchar(255) DEFAULT NULL,
                         `account_status` tinyint DEFAULT '1',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `goods` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) DEFAULT NULL,
                         `quantity` int DEFAULT NULL,
                         `price_for_one` int DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `order_` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `order_number` int DEFAULT NULL,
                          `user_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `user_id_idx` (`user_id`),
                          CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `orders` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `order_id` bigint DEFAULT NULL,
                          `goods_id` bigint DEFAULT NULL,
                          `quantity` int DEFAULT NULL,
                          `total_price` int DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `goods_id_idx` (`goods_id`),
                          KEY `order_id_idx` (`order_id`),
                          CONSTRAINT `goods_id` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`),
                          CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `order_` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
