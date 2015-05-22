SELECT DISTINCT track_spotify_id, artist_spotify_id, album_spotify_id,
						title, artist, song_type, tempo, time_signature,
						duration, valence, loudness, danceability, energy,
						liveness, speechiness, acousticness, instrumentalness,
						song_mode, song_key
				  FROM song
				  WHERE id IN (SELECT song_id
								FROM billboardTop
								WHERE genre = 'pop')