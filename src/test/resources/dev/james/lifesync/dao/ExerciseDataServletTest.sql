DELETE FROM ExerciseData;
DELETE FROM LifeSyncUser;

INSERT INTO `lifesync_database`.`LifeSyncUser` (`id`, `firstname`, `lastname`, `username`, `password`) VALUES (1, 'James', 'Hillyard', 'jhillyard', 'test');
INSERT INTO `lifesync_database`.`LifeSyncUser` (`id`, `firstname`, `lastname`, `username`, `password`) VALUES (2, 'Seb', 'North', 'snorth', 'HelloPassword321!');

INSERT INTO `lifesync_database`.`ExerciseData` (`id`, `userid`, `activityName`, `date`, `duration`, `caloriesBurnt`) VALUES ('1', '1', 'skiing', '2023-10-30', '80', '653');
INSERT INTO `lifesync_database`.`ExerciseData` (`id`, `userid`, `activityName`, `date`, `duration`, `caloriesBurnt`) VALUES ('2', '1', 'skiing', '2023-10-29', '80', '653');
INSERT INTO `lifesync_database`.`ExerciseData` (`id`, `userid`, `activityName`, `date`, `duration`, `caloriesBurnt`) VALUES ('3', '2', 'walking', '2023-10-29', '30', '123');