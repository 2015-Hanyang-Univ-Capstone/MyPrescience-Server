SELECT title, artist, album, COUNT(title) AS play 
FROM gomlog 
GROUP BY title 
ORDER BY COUNT(title) DESC
LIMIT 100;