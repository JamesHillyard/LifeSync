DELETE FROM NutritionData;
DELETE FROM LifeSyncUser;

INSERT IGNORE INTO `lifesync_database`.`LifeSyncUser` (`id`, `firstname`, `lastname`, `username`, `password`) VALUES (1, 'James', 'Hillyard', 'jhillyard', 'test');
INSERT IGNORE INTO `lifesync_database`.`LifeSyncUser` (`id`, `firstname`, `lastname`, `username`, `password`) VALUES (2, 'Seb', 'North', 'snorth', 'HelloPassword321!');

INSERT IGNORE INTO `lifesync_database`.`NutritionData` (`id`, `userid`, `date`, `foodName`, `calories`, `fat`, `sugar`) VALUES ('1', '1', '2023-10-30', 'Apple', '98', '1.3', '10.4');
INSERT IGNORE INTO `lifesync_database`.`NutritionData` (`id`, `userid`, `date`, `foodName`, `calories`, `fat`, `sugar`) VALUES ('2', '1', '2023-10-30', 'Apple', '98', '1.3', '10.4');
INSERT IGNORE INTO `lifesync_database`.`NutritionData` (`id`, `userid`, `date`, `foodName`, `calories`, `fat`, `sugar`) VALUES ('3', '2', '2023-10-31', 'Pumpkin', '286', '8.6', '2.2');