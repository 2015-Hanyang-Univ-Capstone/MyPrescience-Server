<?php 
	header("Content-type: text/html; charset=utf-8");
	include('./DBHandler.php');

	$dao = new Dao();
	
	$query = $_REQUEST['query'];
	$user_id = $_REQUEST['user_id'];
	$genre = $_REQUEST['genre'];
	$clause = $_REQUEST['clause'];
	$song_id = $_REQUEST['song_id'];
	
	ini_set('max_execution_time', 100000);
	
// 	error_reporting(E_ALL);
// 	ini_set("display_errors", 1);

	if ($query == "selectRecommendSongs") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
				" SELECT song.id, artist_spotify_id, album_spotify_id, similar_song_id, title, artist, genres, recommend.rating AS rating, song_type,
						valence, danceability, energy, liveness, speechiness, 
						acousticness, instrumentalness
				  FROM song JOIN artist
				  ON artist_spotify_id = artist.id
				  INNER JOIN (SELECT song_id, rating, similar_song_id FROM recommend WHERE user_id = $user_id) AS recommend
				  ON song.id = recommend.song_id
				  WHERE song.id NOT IN (SELECT song_id FROM rating WHERE user_id = $user_id)
						AND (recommend.rating > 100 OR recommend.rating = 0)
				  ORDER BY rand()
				  LIMIT 1000;  "
		);
		
		print_r(  json_encode( $dao->selectRecommendSong( $resultSet ) ) );
	} else if ($query == "selectRecommendSearchSongs") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
				" SELECT song.id, artist_spotify_id, album_spotify_id, similar_song_id, title, artist, genres, recommend.rating AS rating, song_type,
						valence, danceability, energy, liveness, speechiness, 
						acousticness, instrumentalness
				  FROM song JOIN artist
				  ON artist_spotify_id = artist.id
				  INNER JOIN (SELECT song_id, rating FROM recommend WHERE user_id = $user_id) AS recommend
				  ON song.id = recommend.song_id
				  WHERE song.id NOT IN (SELECT song_id FROM rating WHERE user_id = $user_id) $clause
				  LIMIT 500;  "
		);
		
		print_r(  json_encode( $dao->selectRecommendSong( $resultSet ) ) );
	} else if ($query == "selectRecommendSearchSongsWithGenre") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
				" SELECT song.id, artist_spotify_id, album_spotify_id, similar_song_id, title, artist, genres, recommend.rating AS rating, song_type,
					valence, danceability, energy, liveness, speechiness,
					acousticness, instrumentalness
					FROM song
					JOIN artist ON artist_spotify_id = artist.id
					INNER JOIN (SELECT song_id, rating FROM recommend WHERE user_id = $user_id) AS recommend ON song.id = recommend.song_id
					WHERE song.id NOT IN (SELECT song_id FROM rating WHERE user_id = $user_id) 
					AND $genre $clause
					LIMIT 500;  "
		);
		
		print_r(  json_encode( $dao->selectRecommendSong( $resultSet ) ) );
		
	} else if ($query == "execRecommendAlgorithm") {
		
		if($user_id != null)
			exec("java -jar recommend.jar $user_id");
		else
			exec("java -jar recommend.jar");
		
		$ranNum = mt_rand(0, 100);
		$resultSet = $dao->getResultSet( $dao->getConnection(),
		
				" SELECT image_300
					FROM artist INNER JOIN (
						SELECT artist_spotify_id
						FROM song INNER JOIN (SELECT song_id, rating FROM recommend WHERE user_id = $user_id) AS recommend
						ON song.id = recommend.song_id
						WHERE song.id NOT IN (SELECT song_id FROM rating WHERE user_id = $user_id)
						LIMIT 1 OFFSET $ranNum ) as numberone
					ON artist.id = numberone.artist_spotify_id;  "
		);
		
		
		while( $rs = mysql_fetch_array( $resultSet ) ){
			$album_url = $rs['image_300'];
		}
		$result = array ('recommend'=>"true", "image_300"=>$album_url);
		echo json_encode($result);
		
	} else if ($query == "insertSimilarSong") {
		
		$resultSet = $dao->getResultSet( $dao->getConnection(),
		
				" SELECT tempo, valence, energy, acousticness, genres
					FROM song INNER JOIN artist
					ON song.artist_spotify_id = artist.id
				    WHERE song.id = '$song_id'  "
		);
		
		while( $rs = mysql_fetch_array( $resultSet ) ){
			$tempo = $rs['tempo'];
			$valence = $rs['valence'];
			$energy = $rs['energy'];
			$acousticness = $rs['acousticness'];
			$genres = $rs['genres'];
		}
		
		$tempo_degree = 1.0;
		$property_degree = 0.001;
		
		$tempo_min = $tempo - $tempo_degree;
		$tempo_max = $tempo + $tempo_degree;
		
		$valence_min = $valence - $property_degree;
		$valence_max = $valence + $property_degree;
		
		$energy_min = $energy - $property_degree;
		$energy_max = $energy + $property_degree;
		
		$acousticness_min = $acousticness - $property_degree;
		$acousticness_max = $acousticness + $property_degree;
		
		$genreArray = explode(",", $genres);
		foreach($genreArray as $index1 => $genreDetail) {
			
			$genreDetailArray = explode(" ", $genreDetail);
			foreach($genreDetailArray as $index2 => $genre) {
				$genreDetailArray[$index2] = "genres LIKE '%".$genre."%'";
			}
			
			$genreDetail = implode(" AND ", $genreDetailArray);
			
			$genreArray[$index1] = "(".$genreDetail.")";
		}
		$genreQuery = implode(" OR ", $genreArray);
		
		$resultSet = $dao->getResultSet( $dao->getConnection(),
		
				" INSERT INTO recommend (user_id, song_id, similar_song_id)
				  SELECT $user_id, song.id, '$song_id' 
					FROM song INNER JOIN artist
					ON song.artist_spotify_id = artist.id
					WHERE (tempo BETWEEN $tempo_min AND $tempo_max) AND (valence BETWEEN $valence_min AND $valence_max)
						AND (energy BETWEEN $energy_min AND $energy_max) AND (acousticness BETWEEN $acousticness_min AND $acousticness_max)
						AND $genreQuery AND song.id NOT IN ('$song_id')
				    ORDER BY rand()
				    LIMIT 1 ; "
		);
		
		print_r(  json_encode( $dao->Response( $resultSet ) ) );
		
// 		while( $rs = mysql_fetch_array( $resultSet ) ){
// 			$album_url = $rs['image_300'];
// 		}
// 		$result = array ('recommend'=>"true", "image_300"=>$album_url);
// 		echo json_encode($result);
	}
	
	
?>