<?php 
	header("Content-type: text/html; charset=utf-8");
	include('./DBHandler.php');

	$dao = new Dao();
	
	$query = $_REQUEST['query'];
	$queryStr = $_REQUEST['q'];
	$user_id = $_REQUEST['user_id'];
// 	$title = $_REQUEST['t'];
// 	$artist = $_REQUEST['a'];
	$artists = $_REQUEST['artist'];
	$titles = $_REQUEST['title'];
	
// 	error_reporting(E_ALL);
// 	ini_set("display_errors", 1);
	ini_set('max_execution_time', 50000);

	if ($query == "makeIndexer") {
	
		exec("java -jar indexer.jar");
		
		$result = array ('index'=>"true");
		echo json_encode($result);
		
	} else if ($query == "searchSongs") {
		
		$queryStr = urlencode($queryStr);
		$command = 'java -Dfile.encoding=utf-8 -jar searcher.jar';
		exec($command . " \"$queryStr\" 0 2>&1", $output);
		echo json_encode($output);
		
	} else if ($query == "searchSongId") {
		
		
		// Version 1 . 검색하여 바로 입력하기.
// 		$artists = array("linkin park","linkin park","linkin park","linkin park","linkin park","linkin park","linkin park");
// 		$titles = array("Burn It Down", "Iridescent", "What I've Done", "New Divide", "Faint", "Leave Out All The Rest", "Papercut");
		
// 		$start = 0;
// 		$add = 5;
// 		$total = count($titles);
		
// 		while($start < $total) {
			
// 			if ($start + $add > $total)
// 				$add = $total - $start;
			
// 			unset($output);
// 			$now = $start;
			
// 			for($i = &$start; $i < $now + $add; $i++)  {
// 				$title = addslashes($titles[$i]);
// 				$artist = addslashes($artists[$i]);
// 				$command = 'java -jar searcher.jar';
// 				exec($command . " \"(+\'$title\' AND +\'$artist\')\" 1 2>&1", $output);
// 			}
			
// 			$connection = $dao->getConnection();
// 			mysql_query("BEGIN");
			
// 			$insert = " INSERT INTO rating (user_id, song_id, rating, artist_id, album_id) ";
// 			$selectArray = array();
// 			foreach($output as $song_id)  {
// 				array_push($selectArray, " SELECT $user_id, id, 11, artist_spotify_id, album_spotify_id FROM song WHERE id = '$song_id'");
// 			}
// 			$onDuplicate = " ON DUPLICATE KEY UPDATE rating = 11 ";
			
// 			$finalQuery = $insert.implode("\nUNION ALL\n", $selectArray).$onDuplicate;
// 			$resultSet = $dao->fetchQuery($finalQuery);
			
// 			mysql_query("COMMIT");
// 			$dao->closeConnection($connection);
// 		}
		
// 		$result = array ('sync'=>"true");
// 		echo json_encode($result);

		// Version 2. 검색 후 사용자에게 평가하도록 전송.
		
		for($i = 0; $i < count($titles); $i++)  {
			$title = addslashes($titles[$i]);
			$artist = addslashes($artists[$i]);
			$command = 'java -jar searcher.jar';
			exec($command . " \"(\'$title\' AND \'$artist\')\" 1 2>&1", $output);
		}
		
		$selectArray = array();
		foreach($output as $song_id) {
			array_push($selectArray, " SELECT id, artist_spotify_id, album_spotify_id, title, artist FROM song WHERE id = '$song_id'");
		}
		$finalQuery = implode("\nUNION ALL\n", $selectArray);
		
		$resultSet = $dao->getResultSet( $dao->getConnection(), $finalQuery );
		print_r(  json_encode( $dao->selectSimpleSong( $resultSet ) ) );
		
		// 		foreach($output as &$value)  {
		// 			if (strpos($value, 'S') != 0)
			// 				unset($value);
			// 			else
				// 				$value = "'".$value."'";
				// 		}
					
				// 		$song_ids = implode(",", $output);
		
				// 		print $song_ids;
		
// 		$resultSet = $dao->getResultSet( $dao->getConnection(),

// 				"SELECT song.id, artist_spotify_id, album_spotify_id, title, artist
// 						FROM song
// 						WHERE id IN ( $song_ids ) ;"

// 		);
// 		print_r(  json_encode( $dao->selectSimpleSong( $resultSet ) ) );
		
	} 
	 
?>

