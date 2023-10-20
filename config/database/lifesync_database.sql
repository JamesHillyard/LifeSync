CREATE DATABASE IF NOT EXISTS `lifesync_database`;
USE lifesync_database;
CREATE TABLE IF NOT EXISTS `LifeSyncUser` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `firstname` varchar(45) NOT NULL,
                                `lastname` varchar(45) NOT NULL,
                                `username` varchar(45) NOT NULL,
                                `password` varchar(45) NOT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `username_UNIQUE` (`username`)
);
