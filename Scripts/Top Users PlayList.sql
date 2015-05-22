

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '123.24.160.112' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '58.79.137.31' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '121.187.168.163' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '119.66.154.167' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '121.182.244.179' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '119.204.156.183' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '175.213.46.194' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '112.173.228.128' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '175.203.80.92' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '1.177.34.100' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '203.247.149.239' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

SELECT title, artist, album, COUNT(title) as play_count FROM gomlog WHERE ip = '184.152.101.88' 
GROUP BY title ORDER BY COUNT(title) DESC LIMIT 10;

 121.182.244.179 |      1327 |  - 이상
| 175.203.80.92   |       883 | - 이상
| 203.247.149.239 |       751 | -  정상 
| 121.50.21.24    |       744 | - 정상
| 152.149.12.254  |       741 | - 
| 221.153.143.146 |       690 |
| 175.205.188.74  |       599 |
| 183.104.201.228 |       542 |
| 58.143.65.9     |       530 |
| 121.170.249.251 |       518 |
| 175.119.193.187 |       506 |
| 113.130.235.34  |       485 |
| 183.102.172.238 |       478 |
| 112.186.164.85  |       461 |
| 220.94.104.223  |       444 







