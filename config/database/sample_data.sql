USE lifesync_database;
INSERT INTO `lifesync_database`.`LifeSyncUser` (`firstname`, `lastname`, `username`, `password`) VALUES ('James', 'Hillyard', 'jhillyard', 'test');
INSERT INTO `lifesync_database`.`SleepData` (`userid`, `starttime`, `endtime`) VALUES ('1', '2023-10-23 22:00:00', '2023-10-24 08:00:00');
INSERT INTO `lifesync_database`.`SleepData` (`userid`, `starttime`, `endtime`) VALUES ('1', '2023-10-24 23:30:00', '2023-10-25 09:00:00');
INSERT INTO `lifesync_database`.`SleepData` (`userid`, `starttime`, `endtime`) VALUES ('1', '2023-10-25 22:00:00', '2023-10-26 10:25:00');
INSERT INTO `lifesync_database`.`SleepData` (`userid`, `starttime`, `endtime`) VALUES ('1', '2023-10-26 21:00:00', '2023-10-27 07:00:00');
INSERT INTO `lifesync_database`.`SleepData` (`userid`, `starttime`, `endtime`) VALUES ('1', '2023-10-28 23:55:00', '2023-10-29 08:00:00');
INSERT INTO `lifesync_database`.`SleepData` (`userid`, `starttime`, `endtime`) VALUES ('1', '2023-10-30 00:30:00', '2023-10-30 08:00:00');
INSERT INTO `lifesync_database`.`SleepData` (`userid`, `starttime`, `endtime`) VALUES ('1', '2023-10-30 22:00:00', '2023-10-31 08:00:00');

INSERT INTO `lifesync_database`.`Article` (`id`, `name`, `url`, `section`, `tags`) VALUES ('2', 'How can I get better quality sleep?', 'https://www.bbc.co.uk/programmes/articles/5wd7xyWbW5vqnYCZ2xdMFXB/how-can-i-get-better-quality-sleep', 'Sleep', 'quality');
INSERT INTO `lifesync_database`.`Article` (`id`, `name`, `url`, `section`, `tags`) VALUES ('3', 'My five tips to help improve your sleep', 'https://www.bbc.co.uk/programmes/articles/5vMxkF64jplCjgjzVhb7B5R/my-five-tips-to-help-improve-your-sleep', 'Sleep', 'improve');
INSERT INTO `lifesync_database`.`Article` (`id`, `name`, `url`, `section`, `tags`) VALUES ('4', 'How to fall asleep faster and sleep better', 'https://www.nhs.uk/every-mind-matters/mental-wellbeing-tips/how-to-fall-asleep-faster-and-sleep-better/', 'Sleep', 'faster');