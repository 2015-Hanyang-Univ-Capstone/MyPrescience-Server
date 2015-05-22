<?php 
	header("Content-type: text/html; charset=utf-8");
	include('./DBHandler.php');

	$dao = new Dao();
	
	$query = $_GET['query'];
// 	$id = $_GET['id'];
	
	if ($query == "selectUserTop10") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(), 
				
				" SELECT title, artist, album, COUNT(title) as play_count 
				  FROM gomlog 
				  WHERE ip = '175.213.46.194' 
				  GROUP BY title 
				  ORDER BY COUNT(title) DESC
				  LIMIT 15" );
				
		print_r(  json_encode( $dao->selectMusic( $resultSet ) ) );
	
	} else if ($query == "selectMusicTop100") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(), 
				
				" SELECT title, artist, album, COUNT(title) AS play_count 
				  FROM gomlog 
			      GROUP BY title 
				  ORDER BY COUNT(title) DESC
				  LIMIT 100" );
				
		print_r(  json_encode( $dao->selectMusic( $resultSet ) ) );
	
	} else if ($query == "selectMember") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(), "SELECT * FROM member WHERE id = '$id'" );
		print_r(  json_encode( $dao->selectMembers( $resultSet ) ) );
	
	} else if ($query == "DeleteUserTitleMore300") {
		$resultSet = $dao->getResultSet( $dao->getConnection(), "
				DELETE FROM gomlog 
				WHERE title IN (SELECT * FROM (SELECT title
								FROM gomlog
								GROUP BY ip, title
								HAVING COUNT(title) > 300
								ORDER BY COUNT(title) DESC) AS temp);
				" );
		print_r(  json_encode( $dao->Response( $resultSet ) ) );
	
	} else if ($query == "DeleteUserArtistMore300") {
		$resultSet = $dao->getResultSet( $dao->getConnection(), "
				DELETE FROM gomlog 
				WHERE artist AND title IN (SELECT * FROM (SELECT ip, artist, title, COUNT(artist)
												FROM gomlog
												GROUP BY ip, artist , title
												HAVING COUNT(artist) > 300
												ORDER BY COUNT(artist) DESC) AS temp);
				" );
		print_r(  json_encode( $dao->Response( $resultSet ) ) );
	
	} else if ($query == "getMySQLUsage") {
		$resultSet = $dao->getResultSet( $dao->getConnection(), "
				SELECT TABLE_SCHEMA 'myprescience', SUM(data_length + index_length) / 1024 / 1024 'MAXSize'
				FROM information_schema.TABLES;
				" );
		print_r(  json_encode( $dao->getMySQLUsage( $resultSet ) ) );
	}
	
	
?>