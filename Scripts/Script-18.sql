INSERT INTO rating (user_id, song_id, rating)
			      SELECT 10, 'SOLOIYB12AB0185A6C', 10 FROM DUAL
				  WHERE NOT EXISTS (SELECT * FROM rating WHERE song_id = 'SOLOIYB12AB0185A6C')