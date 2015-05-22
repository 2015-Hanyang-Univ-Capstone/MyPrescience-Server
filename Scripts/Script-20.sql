SELECT DISTINCT id, album_spotify_id, title, artist, rating
FROM song JOIN billboardHot100 
ON song.id = billboardHot100.song_id
LEFT OUTER JOIN ( SELECT song_id, rating FROM rating WHERE user_id = 8 ) AS mysong
ON song.id = mysong.song_id
ORDER BY billboardHot100.rank;

SELECT DISTINCT id, album_spotify_id, title, artist, rating
FROM song LEFT OUTER JOIN ( SELECT song_id, rating FROM rating WHERE user_id = 8 ) AS mysong
ON song.id = mysong.song_id
WHERE id IN (SELECT song_id FROM billboardGenreTop WHERE genre = 'pop' )
ORDER BY rand();

SELECT DISTINCT id, album_spotify_id, title, artist, mysong.rating AS rating, valence
				  FROM song LEFT OUTER JOIN (
						SELECT song_id, rating FROM rating WHERE user_id = 8
						) AS mysong
				  ON song.id = mysong.song_id
				  WHERE valence >= 0.9
				  ORDER BY rand()
				  LIMIT 300;
				  
SELECT DISTINCT id, album_spotify_id, title, artist, mysong.rating AS rating
				  FROM song LEFT OUTER JOIN (
						SELECT song_id, rating FROM rating WHERE user_id = 8
						) AS mysong
				  ON song.id = mysong.song_id
				  WHERE loudness >= 0
				  ORDER BY rand()
				  LIMIT 300;
				  
SELECT DISTINCT id, album_spotify_id, title, artist, mysong.rating AS ratin, danceability
				  FROM song LEFT OUTER JOIN (
						SELECT song_id, rating FROM rating WHERE user_id = 8
						) AS mysong
				  ON song.id = mysong.song_id
				  WHERE danceability >= 0.8
				  ORDER BY rand()
				  LIMIT 300;
				  
SELECT DISTINCT id, album_spotify_id, title, artist, mysong.rating AS rating
				  FROM song LEFT OUTER JOIN (
						SELECT song_id, rating FROM rating WHERE user_id = 8
						) AS mysong
				  ON song.id = mysong.song_id
				  WHERE energy >= 0.9
				  ORDER BY rand()
				  LIMIT 300;
				  
CREATE TABLE `artist` (
  `id` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(255) DEFAULT NULL,
  `genres` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SELECT DISTINCT artist, artist_spotify_id
FROM song;

SELECT DISTINCT artist, country FROM song WHERE artist REGEXP '^[가- ㅎ|ㅎ]+$';
SELECT DISTINCT artist, country FROM song WHERE artist REGEXP '[가-힇]'

SELECT DISTINCT artist 
FROM song
WHERE artist NOT IN ( SELECT DISTINCT artist FROM song WHERE artist REGEXP '^[가- ㅎ|ㅎ]+$' );
블락비 (Block B) Dynamic Duo Winner 2NE1 Cool miss A Exo 포미닛(4minute) B1A4 f(x) BoA M.C The Max 2PM
mate(메이트) Dok2 J Rabbit 조PD EXO-K 10cm DJ DOC GD＆TOP （from BIGBANG） Illionaire Records YB, 박정현 Simon D
EXID

// 한글로된 가수 kor입력.

UPDATE song, ( SELECT DISTINCT artist FROM song WHERE artist REGEXP '[가-힇]' ) as other_song
SET country = "kor" 
WHERE song.artist = other_song.artist;

UPDATE song SET country = "kor" 
WHERE artist = "YB" OR artist = "Illionaire Records" OR artist = "GD＆TOP （from BIGBANG）" OR artist = "DJ DOC"
OR artist = "10cm" OR artist = "EXO-K" OR artist = "J Rabbit" OR artist = "Dok2" OR artist = "2PM" OR artist = "M.C The Max"
 OR artist = "BoA" OR artist = "f(x)" OR artist = "B1A4" OR artist = "Exo" OR artist = "miss A" OR artist = "Cool"
  OR artist = "2NE1" OR artist = "Winner" OR artist = "Dynamic Duo";
  
SELECT song.id, artist_spotify_id, title, artist, AVG(rating), COUNT(rating)
FROM (SELECT DISTINCT id, artist_spotify_id, title, artist FROM song) AS song JOIN rating
ON song.id = rating.song_id
WHERE song.id IN (SELECT DISTINCT song_id FROM rating )
GROUP BY song.id;

SELECT song.id, artist_spotify_id, title, artist, AVG(rating) AS avg, COUNT(rating) AS rating_count
  FROM (SELECT DISTINCT id, artist_spotify_id, title, artist FROM song) AS song JOIN rating
  ON song.id = rating.song_id
  WHERE song.id IN (SELECT DISTINCT song_id FROM rating ) 
  AND song.id NOT IN (SELECT song_id FROM rating WHERE user_id = 8)
  GROUP BY song.id
  ORDER BY avg DESC 
  LIMIT 300;
  
 SELECT id, album_spotify_id, title, artist, rating, AVG(rating) AS avg
  FROM (SELECT DISTINCT id, album_spotify_id, title, artist FROM song) AS song JOIN rating
  ON song.id = rating.song_id
  WHERE song.id IN (SELECT DISTINCT song_id FROM rating ) 
  AND song.id NOT IN (SELECT song_id FROM rating WHERE user_id = 8)
  GROUP BY song.id
  ORDER BY avg DESC 
  LIMIT 300;

  SELECT title, artist, COUNT(id) AS song_count
  FROM song
  GROUP BY id
  ORDER BY song_count DESC;

  
  CREATE TABLE test_song2 SELECT * FROM song ;
  
  ALTER TABLE test_song CHANGE id song_id VARCHAR;

  ALTER TABLE test_song ADD COLUMN id int(100) FIRST;
  
  
  DELETE FROM test_song WHERE id IN (SELECT id FROM test_song2 GROUP BY id HAVING COUNT(id) > 1);
  
  SELECT title, artist, COUNT(id) AS song_count
  FROM test_song
  GROUP BY id
  ORDER BY song_count DESC;
  
  DELETE FROM test_song 
  WHERE id IN (SELECT id
			  FROM test_song2
			  GROUP BY id
			  HAVING COUNT(id) > 1000);
			  
  DELETE FROM test_song 
  WHERE id = 'SOAEQXS148412DB732';
			  
			  
  SELECT id, COUNT(id)
  FROM test_song
  GROUP BY id
  HAVING COUNT(id) > 1
  ORDER BY COUNT(id) DESC;
  
  SELECT id, album_spotify_id, title, artist, recommend.rating
  FROM song JOIN (SELECT song_id, rating FROM recommend WHERE user_id = 8) AS recommend;
  
  
  SELECT song.id, album_spotify_id, title, artist, genres, recommend.rating AS rating, song_type,
						valence, loudness, danceability, energy, liveness, speechiness, 
						acousticness, instrumentalness
  FROM song JOIN artist
  ON artist_spotify_id = artist.id
  INNER JOIN (SELECT song_id, rating FROM recommend WHERE user_id = 8) AS recommend
  ON song.id = recommend.song_id
  WHERE song.id NOT IN (SELECT song_id FROM rating WHERE user_id = 8)
  ORDER BY recommend.rating DESC
  LIMIT 300;
  
  
  SELECT song_id, rating FROM recommend WHERE user_id = 8;
  
  SELECT artist, genres
  FROM song JOIN artist
  ON song.artist_spotify_id = artist.id
  
  SELECT name, genres
  FROM artist
  WHERE genres NOT IN (SELECT genres FROM artist WHERE name = 'Son Of Caesar')
  
  CREATE TABLE `genres` (
  `genre` varchar(100) DEFAULT NULL,
  `detail` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
  

SELECT genre 
FROM genre
WHERE detail IN (SELECT genres FROM artist)

SELECT ip, title, COUNT(song_id)
FROM gom_echo JOIN song
ON gom_echo.song_id = song.id
GROUP BY ip, title
ORDER BY COUNT(song_id) DESC

SELECT user.id, song.id, COUNT(song_id)
FROM gom_echo JOIN user
ON gom_echo.ip = user.facebook_id
JOIN song ON gom_echo.song_id = song.id
GROUP BY user.id, song.id

SELECT DISTINCT ip
FROM gom_echo

INSERT INTO user (facebook_id)
SELECT DISTINCT ip
FROM gom_echo;

SELECT user.id AS user_id, song.id AS song_id, COUNT(song_id) AS play_count
FROM gom_echo JOIN user
ON gom_echo.ip = user.facebook_id
JOIN song ON gom_echo.song_id = song.id
GROUP BY user.id, song.id;

CREATE TABLE dummy_data SELECT * FROM rating ;

SELECT song.id, artist_spotify_id, title, artist, AVG(rating) AS avg, COUNT(rating) AS rating_count
  FROM (SELECT DISTINCT id, artist_spotify_id, title, artist FROM song) AS song JOIN rating
  ON song.id = rating.song_id
  WHERE song.id IN (SELECT DISTINCT song_id FROM rating)
  GROUP BY song.id
  ORDER BY rating_count DESC, avg DESC 
  LIMIT 5;
  
SELECT song.id, artist_spotify_id, title, artist, avg, rating_count
FROM song
WHERE id IN (SELECT * FROM (
		            SELECT song_id, AVG(rating) AS avg, COUNT(rating) AS rating_count
					FROM rating
					GROUP BY song_id
					ORDER BY rating_count DESC, avg DESC 
					LIMIT 5
		      ) AS test)
	
SELECT s.id, artist_spotify_id, title, artist, avg, rating_count
FROM 
    song s INNER JOIN 
    (SELECT song_id, AVG(rating) AS avg, COUNT(rating) AS rating_count
		FROM rating
		GROUP BY song_id
		ORDER BY rating_count DESC, avg DESC 
		LIMIT 20
	) AS r
ON s.id = r.song_id;

DELETE FROM rating 
WHERE song_id = 'SOAEQXS148412DB732' OR song_id = 'SORBGXZ1466D9A1AA6' OR song_id = 'SOVWNQY144567C5BF6' OR
song_id = 'SOTUKKC147E991E1E4' OR song_id = 'SOODGQG1469846511C' OR song_id = 'SOIYHBY148412DB273' OR
song_id = 'SOYXYMI148412E5177' OR song_id = 'SOFMUTD14698520D67' OR song_id = 'SOJHYJE146982A9258';

SOAEQXS148412DB732
SORBGXZ1466D9A1AA6
SOVWNQY144567C5BF6
SOTUKKC147E991E1E4
SOODGQG1469846511C
SOIYHBY148412DB273
SOYXYMI148412E5177
SOFMUTD14698520D67
SOJHYJE146982A9258
				  
				  
DELETE FROM user 
WHERE NOT id = 8 AND NOT id = 9 AND NOT id = 10 AND NOT id = 11;

DELETE FROM rating;

ALTER TABLE user auto_increment = 5000;

SELECT id, album_spotify_id, title, artist, rating, AVG(rating) AS avg
  FROM (SELECT DISTINCT id, album_spotify_id, title, artist FROM song) AS song JOIN rating
  ON song.id = rating.song_id
  WHERE song.id IN (SELECT DISTINCT song_id FROM rating ) 
  AND song.id NOT IN (SELECT song_id FROM rating WHERE user_id = 8)
  GROUP BY song.id
  ORDER BY avg DESC 
  LIMIT 300
  
SELECT AVG(rating) AS avg, COUNT(song_id) AS rating_count
FROM rating
WHERE song_id = 'SOFZXXH14692FC0077';

SELECT DISTINCT user_id
FROM rating
ORDER BY user_id

SELECT title, country
FROM song
WHERE country is null; 

SELECT DISTINCT title, genres
FROM song 
JOIN artist ON artist_spotify_id = artist.id
JOIN genre ON genres = genre
WHERE detail IN (SELECT detail FROM genre WHERE genre = 'rock');


SELECT title, artist, rating
FROM SONG JOIN (SELECT song_id, rating 
				FROM recommend 
				WHERE user_id = 8 
				ORDER BY rating DESC) AS recommendSong
WHERE valence > 0.8;

SELECT song.id, album_spotify_id, title, artist, genres, recommend.rating AS rating, song_type,
						valence, danceability, energy, liveness, speechiness, 
						acousticness, instrumentalness
FROM (SELECT * FROM song WHERE song.id NOT IN (SELECT song_id FROM rating WHERE user_id = 8)) AS song JOIN artist
ON artist_spotify_id = artist.id
INNER JOIN (SELECT song_id, rating FROM recommend WHERE user_id = 8) AS recommend
ON song.id = recommend.song_id
ORDER BY recommend.rating DESC
LIMIT 300;

SELECT song.id, album_spotify_id, title, artist, genres, recommend.rating AS rating, song_type,
						valence, danceability, energy, liveness, speechiness, 
						acousticness, instrumentalness
  FROM song 
  JOIN artist ON artist_spotify_id = artist.id
  INNER JOIN (SELECT song_id, rating FROM recommend WHERE user_id = 8) AS recommend ON song.id = recommend.song_id
  WHERE song.id NOT IN (SELECT song_id FROM rating WHERE user_id = 8) 
  AND genres LIKE '%hip hop%'
  LIMIT 300;
  
  SELECT DISTINCT title, genres
FROM song 
JOIN artist ON artist_spotify_id = artist.id
JOIN genre ON genres = genre
WHERE detail LIKE '%rock%';

SELECT title, artist
FROM song
WHERE title = "Blues Bird";

SELECT COUNT(id)
FROM song;

SELECT DISTINCT song.id, album_spotify_id, title, artist, mysong.rating AS rating
  FROM song LEFT OUTER JOIN (
		SELECT song_id, rating FROM rating WHERE user_id = 8
		) AS mysong
  ON song.id = mysong.song_id
  JOIN artist ON artist_spotify_id = artist.id
  WHERE genres LIKE "%pop%"
  ORDER BY rand()
  LIMIT 300;



11713개.


CREATE TABLE `song_oversea` (
  `id` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `artist_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `track_spotify_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `album_spotify_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `artist_spotify_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `title` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `artist` varchar(150) CHARACTER SET utf8 DEFAULT NULL,
  `song_type` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
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
  `song_key` int(11) DEFAULT NULL,
  `country` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SELECT *
FROM song
WHERE title = 'uptown funk';

CREATE TABLE `album` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `release_date` varchar(50) DEFAULT NULL,
  `image_600` varchar(255) DEFAULT NULL,
  `image_300` varchar(255) DEFAULT NULL,
  `image_64` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


INSERT INTO album (id, name, release_date, image_600, image_300, image_64)
VALUES ( "1w0qEQyzI929DExQXX3dQ9", "The Best Of Act 1", "2010-12-03", "https://i.scdn.co/image/89400c51aaacbe8d6cd55029b995d23953c00e11", "https://i.scdn.co/image/997a84f723980cecb2da761136432c1fe041b6cd", "https://i.scdn.co/image/926bfc7e2d0677d012cb0383184602b7412ac391" ); 


SELECT s.id, album_spotify_id, title, artist, mysong.rating AS rating

SELECT * FROM rating WHERE user_id = 8;
  FROM song s INNER JOIN (
	  SELECT song_id, AVG(rating) AS avg, COUNT(rating) AS rating_count
	  FROM rating
	  GROUP BY song_id
	  ORDER BY rating_count DESC, avg DESC
	  LIMIT 500 ) AS r
  ON s.id = r.song_id
  LEFT OUTER JOIN (
		SELECT song_id, rating FROM rating WHERE user_id = 8
		) AS mysong
  ON s.id = mysong.song_id;

  
  SELECT s.id, album_spotify_id, title, artist, genres, r.avg AS rating, song_type,
						valence, danceability, energy, liveness, speechiness, 
						acousticness, instrumentalness
  FROM song s INNER JOIN (
	  SELECT song_id, AVG(rating) AS avg, COUNT(rating) AS rating_count
	  FROM rating
	  GROUP BY song_id
	  ORDER BY rating_count DESC, avg DESC
	  LIMIT 100 ) AS r
  ON s.id = r.song_id
  LEFT OUTER JOIN (
		SELECT song_id, rating FROM rating WHERE user_id = 8
		) AS mysong
  ON s.id = mysong.song_id
  JOIN artist
  ON artist_spotify_id = artist.id;
  
  SELECT album.id AS id, name, artist, release_date, image_600, image_300, image_64
	FROM (SELECT * FROM album ORDER BY release_date DESC LIMIT 11 ) AS album JOIN song
  	ON album.id = song.album_spotify_id;
  	
 SELECT id
 FROM song
 WHERE track_spotify_id = '7wBFjZMHsC6nfV0HOSd6uI';
 
 SELECT COUNT(id), album_spotify_id
 FROM song
 GROUP BY album_spotify_id
 ORDER BY COUNT(id) DESC
 LIMIT 30;
     
 
 ALTER TABLE album ADD COLUMN artist VARCHAR(255) AFTER name;
 
 INSERT INTO recommend SELECT * FROM recom WHERE user_id = ;
 
 SELECT COUNT(song.id), artist, genres
 FROM song JOIN artist
 ON song.artist_spotify_id = artist.id
 WHERE genres = ""
 GROUP BY artist
 ORDER BY COUNT(song.id) DESC
 LIMIT 100;
 
 SELECT artist, genres
 FROM song JOIN artist
 ON song.artist_spotify_id = artist.id
 WHERE genres = "";
 
 
 
 Richard Clayderman, orchestra pops  /
 Hillsong Worship, ccm  /
 Cliff Richard, pop  /
 Johnny Cash, country /
 Brenda Lee, country,pop  /
 Bob Marley, raggae /
 Santana, pop rock /
 Glee Cast, pop,drama /
 Nana Mouskouri, pop,chanson /
 Gilbert Becaud, chanson /
 Radiohead, rock,pop / 
 Pet Shop Boys, pop,pop rock /
 Lester Young, jazz /
 Celine Dion, pop /
 abba, pop,pop rock /
 Barbra Streisand, pop /
 Michael Jackson, pop /
 Bee Gees, pop, disco /
 Glenn Gould, orchestra /
 Muse, alternative rock /
 Norah Jones, jazz,vocal / 
 George Michael, pop,pop rock /
 Jean-Francois Maljean, new wage
 u2, pop rock,alternative rock /
 the moody Blues, art rock /
 Electric Light Orchestra, progressive,art rock /
 sum41, rock,alternative /
 Eminem, hip hop,rap,west /
 John Powell, movie ost /
 Judy Garland, pop /
 Keith Jarrett, jazz
 Michael Bolton, pop,pop rock /
 Glen Campbell, pop,country /
 Coldplay, alternative,rock /
 Simply Red,The Beach Boys,Tina Turner,Carpenters, pop,pop rock /
 Chris Brown, pop,r&b /
 Cher, pop,dance /
 Lady GaGa, dance /
 Jim Brickman, new wage /
 John Denver, country /
 Amy Winehouse, soul,jazz /
 Keane, rock,alternative /
 George Winston, new wage /
 The Script,Snow Patrol,Skillet,Green Day,Bryan Adams,Mark Knopfler,Oasis, rock,alternative /
 Sting, pop,alternative /
 Julio Iglesias, pop /
 Smokie, pop,brit pop /
 Murray Gold, pop /
 Trevor Jones, movie ost /
 Laura Fygi,Kenny G, jazz /
 John Ogdon, piano /
 Gloria Estefan, ratin,pop rock /
 Mr. Big, pop rock, hard /
 Lana Del Rey, pop,alternative /
 Dionne Warwick, pop,r&b /
 Owl City, electronica /
 Depapepe, J-jazz /
 Perfume, j-pop /
 Joe Loss & His Orchestra, swing /
 Hillsong, ccm
 Michael Learns To Rock, pop rock,alternative /
 Adam Lambert, pop,rock /
 Florence + The Machine, indie,pop/
 Sam Smith, r&b,pop /
 
 UPDATE artist SET genres = "ballad, rock" WHERE name LIKE "M.C The Max";
 
 
 ALTER TABLE rating ADD COLUMN artist_id VARCHAR(200);
 
 SELECT * FROM song WHERE artist = 'Anthony Warlow';
 
 SELECT name, album.artist
 FROM album JOIN song
 ON album.id = song.album_spotify_id
 WHERE song.id IN ( SELECT song_id
 FROM rating
 WHERE user_id = 8 AND rating > 8 );
 
 
 SELECT song_id
 FROM rating
 WHERE user_id = 8 AND rating > 8;
 
 artistData
 
 SELECT title, artist
 FROM song
 WHERE artist = 'linkin park';
 
  INSERT INTO rating (user_id, song_id, rating, artist_id, album_id)
  SELECT 8, id, 11, artist_spotify_id, album_spotify_id
  FROM song
  WHERE title LIKE '%Given Up%' AND artist LIKE '%Linkin Park%'
  ON DUPLICATE KEY UPDATE rating = 11 ;
  
  INSERT INTO courses (name, location, gid)
SELECT name, location, 1
FROM   courses
WHERE  cid = 2


INSERT INTO rating (user_id, song_id, rating, artist_id, album_id)
  SELECT 8, id, 11, artist_spotify_id, album_spotify_id
  FROM song
  WHERE title LIKE '%papercut%' AND artist LIKE '%linkin park%'
  UNION
  SELECT 8, id, 11, artist_spotify_id, album_spotify_id
  FROM song
  WHERE title LIKE '%numb%' AND artist LIKE '%linkin park%'
  ON DUPLICATE KEY UPDATE rating = 11;
  
  
  SELECT song.id, artist_spotify_id, album_spotify_id, title, artist, mysong.rating AS rating
  FROM song LEFT OUTER JOIN (
		SELECT song_id, rating FROM rating WHERE user_id = 8
		) AS mysong
  ON song.id = mysong.song_id
  JOIN artist ON song.artist_spotify_id = artist.id
  WHERE genres LIKE "%pop%"
  ORDER BY rand()
  LIMIT 500
  
  SELECT artist
  FROM song
  WHERE artist = 'linkin park';
  
  
  
  
  INSERT INTO rating (user_id, song_id, rating, artist_id, album_id)
  SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
  FROM song WHERE title = 'Hangouts Message' AND artist = '<unknown>'  
  UNION
  SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
  FROM song WHERE title = 'Hangouts video call' AND artist = '2131624086'
  UNION
  SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
  FROM song WHERE title = 'Hangouts Call' AND artist = '<unknown>'
  UNION  
  SELECT 8, id, 11, artist_spotify_id, album_spotify_id FROM song
  WHERE title = '밤샜지 (Prod. by ZICO)' AND artist = '육지담'
  UNION  
  SELECT 8, id, 11, artist_spotify_id, album_spotify_id
  FROM song WHERE title = 'On & On (Prod. by The Quiett)' AND artist = '육지담, 백예린 (15&)'
  ON DUPLICATE KEY UPDATE rating = 11;
  
INSERT INTO rating (user_id, song_id, rating, artist_id, album_id)  
SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
FROM song WHERE title = 'What Ive Done' AND artist = 'Linkin Park' 
UNION ALL
SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
FROM song WHERE title = 'New Divide' AND artist = 'Linkin Park' 
UNION ALL 
SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
FROM song WHERE title = 'Faint' AND artist = 'Linkin Park' 
UNION ALL 
SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
FROM song WHERE title = 'Leave Out All The Rest' AND artist = 'Linkin Park' 
UNION ALL 
SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
FROM song WHERE title = 'Papercut' AND artist = 'Linkin Park'  
ON DUPLICATE KEY UPDATE rating = 11;

INSERT INTO rating (user_id, song_id, rating, artist_id, album_id)  
SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
FROM song WHERE id = 'SOSAHVL137405DAD67'
UNION ALL
SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
FROM song WHERE id = 'SOBLLEW137759334A5'
UNION ALL 
SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
FROM song WHERE id = 'SOBUHQV1315CD4A404'
UNION ALL 
SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
FROM song WHERE id = 'SODCSIR12AF72A1BD9'
UNION ALL 
SELECT 8, id, 11, artist_spotify_id, album_spotify_id 
FROM song WHERE id = 'SOFMKWD13773EE0DFF' 
ON DUPLICATE KEY UPDATE rating = 11;

ALTER TABLE recommend ADD similar_song_id varchar(100);
ALTER TABLE artist ADD image_300 varchar(200);
ALTER TABLE artist ADD image_600 varchar(200);


SOSAHVL137405DAD67, SOBLLEW137759334A5, SOBUHQV1315CD4A404, SODCSIR12AF72A1BD9, SOFMKWD13773EE0DFF

ALTER TABLE billboardGenreTop ADD PRIMARY KEY(song_id);


SELECT id, album_spotify_id, title, artist, rating
  FROM song
  WHERE id IN (SELECT song_id FROM billboardGenreTop WHERE genre = 'pop' )
  ORDER BY rand();
  
  
  SELECT song.id, artist_spotify_id, album_spotify_id, title, artist
  FROM song INNER JOIN (SELECT song_id FROM billboardGenreTop WHERE genre = 'rock') AS billboard
  ON song.id = billboard.song_id;
  
  SELECT song_id FROM billboardGenreTop WHERE genre = 'rock';
  
  
  CREATE TABLE today_song 
  SELECT id FROM song ORDER BY rand() LIMIT 10000;
  
  SELECT song.id, artist_spotify_id, album_spotify_id, title, artist, mysong.rating AS rating
  FROM song INNER JOIN today_song
  ON song.id = today_song.id
  LEFT OUTER JOIN (
		SELECT song_id, rating FROM rating WHERE user_id = 8
		) AS mysong
  ON song.id = mysong.song_id
  ORDER BY rand();
  
  SELECT COUNT(song.id)
  FROM song INNER JOIN artist ON artist_spotify_id = artist.id
  WHERE genres LIKE '%pop%';
  
  SELECT id, artist_spotify_id, album_spotify_id, title, artist, mysong.rating AS rating
  FROM song LEFT OUTER JOIN (
		SELECT song_id, rating FROM rating WHERE user_id = 8
		) AS mysong
  ON song.id = mysong.song_id
  WHERE valence > 0.75
  ORDER BY rand()
  LIMIT 1000
  
  CREATE TABLE instrumentalness_song 
  SELECT id FROM song WHERE instrumentalness > 0.80 ORDER BY rand() LIMIT 3000;
  
   SELECT COUNT(id)
  FROM song
  WHERE instrumentalness > 0.80;
  
  SELECT COUNT(id)
  FROM song
  WHERE valence > 0.80;
  12만
  
  SELECT COUNT(id)
  FROM song
  WHERE energy > 0.80;
  23만
  
SELECT COUNT(id)
  FROM song
  WHERE danceability > 0.80;
 6만.
  
  SELECT COUNT(id)
  FROM song
  WHERE liveness > 0.80;
  3만.
  
  SELECT COUNT(id)
  FROM song
  WHERE speechiness > 0.80;
  6천.
  
  SELECT COUNT(id)
  FROM song
  WHERE acousticness > 0.80;
  16만.
  
  SELECT song.id, artist_spotify_id, album_spotify_id, title, artist, mysong.rating AS rating
  FROM song INNER JOIN valence_song
  ON song.id = valence_song.id
  LEFT OUTER JOIN (
		SELECT song_id, rating FROM rating WHERE user_id = 8
		) AS mysong
  ON song.id = mysong.song_id
  ORDER BY rand()
  
SELECT song.id, artist_spotify_id, album_spotify_id, title, artist, mysong.rating AS rating				FROM (SELECT song.id, artist_spotify_id, album_spotify_id, title, artist						FROM song						WHERE song id = SOCMZJE1390326EAF6 OR id = SOEMTZM13DB01B5912 OR id = SOEWUGJ1338A5D8ABA OR id = SOWKTLV135C344D9EE OR id = SOHOPVH137405DBBE3 OR id = SOESJSG135BCC3D3B9 OR id = SOAWHWM131343940FE) AS song				LEFT OUTER JOIN (					SELECT song_id, rating FROM rating WHERE user_id = 8				) AS mysong				ON song.id = mysong.song_id;


SELECT song.id, artist_spotify_id, album_spotify_id, title, artist 
FROM song 
WHERE id = 'SOCMZJE1390326EAF6' OR id = 'SOEMTZM13DB01B5912' OR id = 'SOEWUGJ1338A5D8ABA' OR id = 'SOWKTLV135C344D9EE' OR id = 'SOHOPVH137405DBBE3' OR id = 'SOESJSG135BCC3D3B9' OR id = 'SOAWHWM131343940FE';


CREATE TABLE domestic_song 
SELECT song.id
  FROM song 
  WHERE country = 'kor'
  ORDER BY rand()
  LIMIT 3000;


CREATE TABLE dance_song 
SELECT song.id
  FROM song INNER JOIN artist ON artist_spotify_id = artist.id
  WHERE genres LIKE '%dance%'
  ORDER BY rand()
  LIMIT 3000;
  
  
  
  34000
  
SELECT COUNT(song.id)
  FROM song INNER JOIN artist ON artist_spotify_id = artist.id
  WHERE genres LIKE '%hip hop%';

  5000
  
SELECT COUNT(song.id)
  FROM song INNER JOIN artist ON artist_spotify_id = artist.id
  WHERE genres LIKE '%r&b%';
  
  1705
  
 SELECT COUNT(song.id)
  FROM song INNER JOIN artist ON artist_spotify_id = artist.id
  WHERE genres LIKE '%rock%';
  
  41000
  
   SELECT COUNT(song.id)
  FROM song INNER JOIN artist ON artist_spotify_id = artist.id
  WHERE genres LIKE '%country%';
  
  6300
  
   SELECT COUNT(song.id)
  FROM song INNER JOIN artist ON artist_spotify_id = artist.id
  WHERE genres LIKE '%electro%';
  
  5000
  
  SELECT COUNT(song.id)
  FROM song INNER JOIN artist ON artist_spotify_id = artist.id
  WHERE genres LIKE '%jazz%';
  
  2000
  
    SELECT COUNT(song.id)
  FROM song INNER JOIN artist ON artist_spotify_id = artist.id
  WHERE genres LIKE '%dance%';
  
  8000
  
  SELECT artist FROM song WHERE country = 'kor';
  
SELECT image_300
FROM album INNER JOIN (
	SELECT album_spotify_id
	FROM song INNER JOIN (SELECT song_id, rating FROM recommend WHERE user_id = 8) AS recommend
	ON song.id = recommend.song_id
	WHERE song.id NOT IN (SELECT song_id FROM rating WHERE user_id = 8)
	LIMIT 1 ) as numberone
ON album.id = numberone.album_spotify_id;
  

SELECT image_300
FROM artist INNER JOIN (
	SELECT artist_spotify_id
	FROM song INNER JOIN (SELECT song_id, rating FROM recommend WHERE user_id = 8) AS recommend
	ON song.id = recommend.song_id
	WHERE song.id NOT IN (SELECT song_id FROM rating WHERE user_id = 8)
	LIMIT 1 ) as numberone
ON artist.id = numberone.artist_spotify_id;
  

INSERT INTO rating (user_id, song_id, rating)
SELECT 18597, id, 10 FROM song WHERE artist = 'linkin park';
  

SELECT tempo, valence, energy, acousticness, genres
FROM song INNER JOIN artist
ON song.artist_spotify_id = artist.id
WHERE title = "papercut";


SOSAHVL137405DAD67, 150, 0.32, 0.82, 048
tempo, valence, energy, acousticness, rap metal,rap rock
LIKE로 찾기.

SELECT title, artist
FROM song INNER JOIN artist
ON song.artist_spotify_id = artist.id
WHERE (tempo BETWEEN 147.5 AND 157.5) AND (valence BETWEEN 0.295 AND 0.345)
	AND (energy BETWEEN 0.795 AND 0.845) AND (acousticness BETWEEN 0.455 AND 0.505)
	AND (genres LIKE '%rock%' AND genres LIKE '%rap%') OR (genres LIKE '%rap%' AND genres LIKE '%metal%');
	

	HERE (tempo BETWEEN 147.7100067 AND 152.7100067) AND (valence BETWEEN 0.691781 AND 0.741781)
    AND (energy BETWEEN -0.025 AND 0.025) AND (acousticness BETWEEN -0.024758 AND 0.025242)

	SELECT tempo, valence, energy, acousticness, genres
		FROM song INNER JOIN artist
		ON song.artist_spotify_id = artist.id
	    WHERE song.id = 'SOSAHVL137405DAD67';
	    
	     CREATE TABLE test_song2 SELECT * FROM song ;
	    
CREATE TABLE mypTop100
SELECT s.id, artist_spotify_id, album_spotify_id, title, artist, avg, rating_count
				  FROM song s INNER JOIN (
					  SELECT song_id, AVG(rating) AS avg, COUNT(rating) AS rating_count
					  FROM rating
					  GROUP BY song_id
					  ORDER BY rating_count DESC, avg DESC
					  LIMIT 100 ) AS r
				  ON s.id = r.song_id;
				  
				  
				  SELECT s.id, s.artist_spotify_id, s.album_spotify_id, s.title, s.artist, genres, mypTop100.avg AS avg_rating, 0,
						 song_type, valence, danceability, energy, liveness, speechiness, acousticness, instrumentalness
				  FROM song s INNER JOIN mypTop100
				  ON s.id = mypTop100.id
				  JOIN artist
				  ON s.artist_spotify_id = artist.id;
				  
				  
				  
				  SELECT song.id, artist_spotify_id, album_spotify_id, similar_song_id, title, artist, genres, recommend.rating AS rating, song_type,
						valence, danceability, energy, liveness, speechiness, 
						acousticness, instrumentalness
				  FROM song JOIN artist
				  ON artist_spotify_id = artist.id
				  INNER JOIN (SELECT song_id, rating, similar_song_id FROM recommend WHERE user_id = 8) AS recommend
				  ON song.id = recommend.song_id
				  WHERE song.id NOT IN (SELECT song_id FROM rating WHERE user_id = 8)
						AND (recommend.rating > 100 OR recommend.rating = 0)
				  ORDER BY rand()
				  LIMIT 1000
				  
				  SELECT id FROM song
				  INNER JOIN (SELECT DISTINCT song_id FROM rating) AS rating
				  ON song.id = rating.song_id;
				  
				  DELETE FROM rating WHERE user_id > 18596;
 