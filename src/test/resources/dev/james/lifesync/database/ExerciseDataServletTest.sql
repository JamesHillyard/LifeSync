DELETE FROM ExerciseData;
DELETE FROM LifeSyncUser;

INSERT IGNORE INTO `lifesync_database`.`LifeSyncUser` (`id`, `firstname`, `lastname`, `email`, `password`) VALUES (1, 'James', 'Hillyard', 'james.hillyard@payara.fish', 'test');
INSERT IGNORE INTO `lifesync_database`.`LifeSyncUser` (`id`, `firstname`, `lastname`, `email`, `password`) VALUES (2, 'Seb', 'North', 'seb.north@payara.fish', 'HelloPassword321!');

INSERT IGNORE INTO `lifesync_database`.`ExerciseData` (`id`, `userid`, `activityName`, `date`, `duration`, `caloriesBurnt`) VALUES ('1', '1', 'skiing', '2023-10-30', '80', '653');
INSERT IGNORE INTO `lifesync_database`.`ExerciseData` (`id`, `userid`, `activityName`, `date`, `duration`, `caloriesBurnt`) VALUES ('2', '1', 'skiing', '2023-10-29', '80', '653');
INSERT IGNORE INTO `lifesync_database`.`ExerciseData` (`id`, `userid`, `activityName`, `date`, `duration`, `caloriesBurnt`) VALUES ('3', '2', 'walking', '2023-10-29', '30', '123');