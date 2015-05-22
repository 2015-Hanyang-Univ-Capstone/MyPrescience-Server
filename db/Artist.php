<?php 
	header("Content-type: text/html; charset=utf-8");
	include('./DBHandler.php');

	$dao = new Dao();
	
// 		error_reporting(E_ALL);
// 		ini_set("display_errors", 1);

	ini_set('max_execution_time', 1000);
	
	$query = $_REQUEST['query'];
	$user_id = $_REQUEST['user_id'];
	
	if ($query == "selectMyArtists") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
				" SELECT id, name, genres, image_300
					FROM artist 
					INNER JOIN 
						( SELECT DISTINCT artist_id
							FROM rating
							WHERE user_id = $user_id AND rating > 7 ) AS myartist
				    ON artist.id = myartist.artist_id; "
				
		);
		print_r(  json_encode( $dao->selectMyArtist( $resultSet ) ) );
	}
	
	
	
	
	
	
?>