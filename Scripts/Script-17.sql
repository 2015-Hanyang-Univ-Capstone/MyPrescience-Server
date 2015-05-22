SELECT DISTINCT id, album_spotify_id, title, artist, mysong.rating AS rating
				  FROM song LEFT OUTER JOIN (
						SELECT song_id, rating FROM rating WHERE user_id = 8
						) AS mysong
				  ON song.id = mysong.song_id
				  ORDER BY rand()
				  LIMIT 1000