CREATE TABLE `song` (
  `id` varchar(255) DEFAULT NULL,
  `artist_id` varchar(255) DEFAULT NULL,
  `track_spotify_id` varchar(255) DEFAULT NULL,
  `album_spotify_id` varchar(255) DEFAULT NULL,
  `artist_spotify_id` varchar(255) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `artist` varchar(150) DEFAULT NULL,
  `song_type` varchar(255) DEFAULT NULL,
  `tempo` float(10,7) DEFAULT NULL,
  `time_signature` int(20) DEFAULT NULL,
  `duration` float(11,7) DEFAULT NULL,
  `valence` float(8,7) DEFAULT NULL,
  `loudness` float(10,7) DEFAULT NULL,
  `danceability` float(8,7) DEFAULT NULL,
  `energy` float(8,7) DEFAULT NULL,
  `liveness` float(8,7) DEFAULT NULL,
  `speechiness` float(8,7) DEFAULT NULL,
  `acousticness` float(8,7) DEFAULT NULL,
  `instrumentalness` float(8,7) DEFAULT NULL,
  `song_mode` int(1) DEFAULT NULL,
  `song_key` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8

