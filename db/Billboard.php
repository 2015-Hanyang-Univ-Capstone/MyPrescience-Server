<?php 
	header("Content-type: text/html; charset=utf-8");
	include('./DBHandler.php');

	$dao = new Dao();
	
	$query = $_REQUEST['query'];
	$genres = $_REQUEST['genres'];
	$user_id = $_REQUEST['user_id'];
	
	$strTok =explode(',', $genres);
	$count = count($strTok);
	
	$genre = "";
	for($i = 0 ; $i < $count-1 ; $i++){
		$genre = $genre."genre = '".$strTok[$i]."' OR ";
	}
	$genre = $genre." genre = '".$strTok[$i]."' ";
	
// 	echo "$genre <br/>";
// 	error_reporting(E_ALL);
// 	ini_set("display_errors", 1);

	if ($query == "selectGenreTop") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
				" SELECT song.id, artist_spotify_id, album_spotify_id, title, artist
				  FROM song INNER JOIN (SELECT song_id FROM billboardGenreTop WHERE genre = $genre) AS billboard
				  ON song.id = billboard.song_id; "
	
		);
		print_r(  json_encode( $dao->selectSimpleSong( $resultSet ) ) );
	
	} else if ($query == "selectHot100") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
				" SELECT DISTINCT id, artist_spotify_id, album_spotify_id, title, artist, rating
				  FROM song JOIN billboardHot100 
				  ON song.id = billboardHot100.song_id
				  LEFT OUTER JOIN ( SELECT song_id, rating FROM rating WHERE user_id = $user_id ) AS mysong
				  ON song.id = mysong.song_id
				  ORDER BY billboardHot100.rank
				"
	
		);
		print_r(  json_encode( $dao->selectSimpleSong( $resultSet ) ) );
	
	} 
?>