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
CREATE TABLE IF NOT EXISTS `SleepData` (
                                 `sleepid` INT NOT NULL AUTO_INCREMENT,
                                 `userid` INT NOT NULL,
                                 `starttime` DATETIME NULL,
                                 `endtime` DATETIME NULL,
                                 PRIMARY KEY (`sleepid`),
                                 INDEX `userid_idx` (`userid` ASC) VISIBLE,
                                 CONSTRAINT `userid`
                                     FOREIGN KEY (`userid`)
                                         REFERENCES `lifesync_database`.`LifeSyncUser` (`id`)
                                         ON DELETE NO ACTION
                                         ON UPDATE NO ACTION
);
CREATE TABLE `lifesync_database`.`Article` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `name` LONGTEXT NOT NULL,
                                               `url` LONGTEXT NOT NULL,
                                               `section` SET("Sleep", "Exercise", "Nutrition") NOT NULL,
                                               `tags` MEDIUMTEXT NOT NULL,
                                               PRIMARY KEY (`id`)
);