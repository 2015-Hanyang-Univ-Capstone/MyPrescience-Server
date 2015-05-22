<?php 
	header("Content-type: text/html; charset=utf-8");
	include('./DBHandler.php');

	$dao = new Dao();
	
// 	error_reporting(E_ALL);
// 	ini_set("display_errors", 1);

	ini_set('max_execution_time', 1000);
	
	$query = $_REQUEST['query'];
	$user_id = $_REQUEST['user_id'];
	
	if ($query == "selectMainLatestAlbums") {
		
		$time1 = date("Y-m-d",strtotime ("+0 days"));
		$time2 = date("Y-m-d",strtotime ("-1 days"));
		$time3 = date("Y-m-d",strtotime ("-2 days"));
		$time4 = date("Y-m-d",strtotime ("-3 days"));
		$time5 = date("Y-m-d",strtotime ("-4 days"));
	
		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
				" SELECT id, name, artist, release_date, image_300
				  FROM album 
				  WHERE release_date = '$time1' OR release_date = '$time2' OR
				 		release_date = '$time3' OR release_date = '$time4' OR
						release_date = '$time5' "
				
		);
		print_r(  json_encode( $dao->selectMyAlbum( $resultSet ) ) );
	} else if ($query == "selectLatestAlbums") {
		
		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
				" SELECT id, name, artist, release_date, image_300
				  FROM album 
				  ORDER BY release_date DESC 
				  LIMIT 1000 "
				
		);
		print_r(  json_encode( $dao->selectMyAlbum( $resultSet ) ) );
	} else if ($query == "selectMyAlbums") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
				" SELECT id, name, artist, release_date, image_300
					FROM album INNER JOIN
						( SELECT DISTINCT album_id
							FROM rating
							WHERE user_id = $user_id AND rating > 7 ) AS myalbum
				    ON album.id = myalbum.album_id "
				
		);
		print_r(  json_encode( $dao->selectMyAlbum( $resultSet ) ) );
	}
	
	
	
	
	
	
?>