/* 
 *  1. 유저 별로 플레이 카운트가 300이 넘는 노래.  
 */

SELECT title
FROM gomlog
GROUP BY ip, title
HAVING COUNT(title) > 300
ORDER BY COUNT(title) DESC;

/* 
 *  2. 유저 별로 아티스트 카운트가 300이 넘는 노래.  
 */

SELECT ip, artist, title, COUNT(artist)
FROM gomlog
GROUP BY ip, artist , title
HAVING COUNT(artist) > 300
ORDER BY COUNT(artist) DESC;


