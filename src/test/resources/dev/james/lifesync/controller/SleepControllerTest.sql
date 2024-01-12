USE lifesync_database;
INSERT INTO `lifesync_database`.`LifeSyncUser` (`id`, `firstname`, `lastname`, `username`, `password`) VALUES (1, 'James', 'Hillyard', 'jhillyard', 'test');

INSERT INTO `lifesync_database`.`SleepData` (`userid`, `starttime`, `endtime`) VALUES ('1', '2023-10-23 22:00:00', '2023-10-24 08:00:00');
INSERT INTO `lifesync_database`.`SleepData` (`userid`, `starttime`, `endtime`) VALUES ('1', '2023-10-24 23:30:00', '2023-10-25 09:00:00');