USE lifesync_database;
INSERT IGNORE INTO `lifesync_database`.`LifeSyncUser` (`id`, `firstname`, `lastname`, `email`, `password`) VALUES (1, 'James', 'Hillyard', 'james.hillyard@payara.fish', 'test');

INSERT INTO `lifesync_database`.`NutritionData` (`id`, `userid`, `date`, `foodName`, `calories`, `fat`, `sugar`) VALUES ('1', '1', '2023-10-30', 'Chips', '350', '5.6', '10.4');
INSERT INTO `lifesync_database`.`NutritionData` (`id`, `userid`, `date`, `foodName`, `calories`, `fat`, `sugar`) VALUES ('2', '1', '2023-10-30', 'Cheese Burger', '1600', '20.8', '9.8');