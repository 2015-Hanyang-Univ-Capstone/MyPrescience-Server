DELETE FROM gomlog 
WHERE title IN (SELECT title
				FROM gomlog
				GROUP BY ip, title
				HAVING COUNT(title) > 300
				ORDER BY COUNT(title) DESC);