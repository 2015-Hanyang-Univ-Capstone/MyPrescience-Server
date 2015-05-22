SELECT DISTINCT id, title, artist, album_spotify_id, rating
FROM rating JOIN song
ON rating.song_id = song.id
WHERE user_id = 8