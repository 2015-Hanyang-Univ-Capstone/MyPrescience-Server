INSERT INTO song (id, artist_id, title, artist, song_type, tempo, time_signature, duration, valence, loudness, danceability, energy, liveness, speechiness, acousticness, instrumentalness, song_mode, song_key) "
				+ " values ('" +id+ "', '" +artist_id+ "', '" +title+ "', '" +artist + "','" +songType+ "', "
				+  audio_summary.get("tempo") + ", " + audio_summary.get("time_signature") + ", "
				+ audio_summary.get("duration") + ", " + audio_summary.get("valence") + ", "
				+ audio_summary.get("loudness") + ", " + audio_summary.get("danceability") + ", "
				+ audio_summary.get("energy") + ", " + audio_summary.get("liveness") + ", "
				+ audio_summary.get("speechiness") + ", " + audio_summary.get("acousticness") + ", "
				+ audio_summary.get("instrumentalness") + ", " + audio_summary.get("mode") + ", "
				+ audio_summary.get("key") + " )";
				
				