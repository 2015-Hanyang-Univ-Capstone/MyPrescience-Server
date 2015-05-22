CREATE TABLE `rating` (
  `user_id` varchar(50) DEFAULT NULL,
  `song_id` varchar(255) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  PRIMARY KEY(user_id,song_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8