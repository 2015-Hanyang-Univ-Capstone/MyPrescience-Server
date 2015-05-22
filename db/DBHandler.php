<?php 
	class Dao
	{
	     
	    function getConnection(){
// 	        $connectionString = mysql_connect("192.168.0.99:8889", "root", "myp1234");
// 	    	$connectionString = mysql_connect("218.37.209.129:8889", "root", "root");
// 	    	$connectionString = mysql_connect("172.200.152.155:8889", "root", "root");
// 	    	$connectionString =mysql_connect("166.104.245.89:3306", "root", "root");

	    	$connectionString =mysql_connect("127.0.0.1:3306", "root", "root");
	        mysql_select_db("exercise_myp", $connectionString);
// 	    	mysql_select_db("myprescience", $connectionString);
	         
	        mysql_query("set session character_set_connection=utf8;");
	        mysql_query("set session character_set_results=utf8;");
	        mysql_query("set session character_set_client=utf8;");
	        
	        return $connectionString;  
	    }
	     
	    public function getResultSet($connection, $query){
	        $result = mysql_query($query);
	        mysql_close($connection);
	         
	        return $result;
	    }
	    
	    public function fetchQuery($query) {
	    	mysql_query($query);
	    }
	    
	    public function closeConnection($connection) {
	    	mysql_close($connection);
	    }
	    
	    /*  User  */
	    
	    public function selectUserId( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    
	    		$arrayMiddle = array(
	    				"user_id"=>$rs['id']
	    		);
	    
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    /*  Song  */
	    
	    public function selectTitleArtist( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    
	    		$arrayMiddle = array(
	    				"title"=>$rs['title'],
	    				"artist"=>$rs['artist']
	    		);
	    
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	     
	    public function selectSong( $resultSet  ){
	        $resultArray = array();
	        while( $rs = mysql_fetch_array( $resultSet ) ){
	        	
	            $arrayMiddle = array(
	            		 "track_spotify_id"=>$rs['track_spotify_id'],
	            		 "artist_spotify_id"=>$rs['artist_spotify_id'],
	            		 "album_spotify_id"=>$rs['album_spotify_id'],
	            		 "title"=>$rs['title'],
	            		 "artist"=>$rs['artist'],
	            		 "song_type"=>$rs['song_type'],
	            		 "tempo"=>$rs['tempo'],
	            		 "time_signature"=>$rs['time_signature'],
	            		 "duration"=>$rs['duration'],
	            		 "valence"=>$rs['valence'],
	            		 "loudness"=>$rs['loudness'],
	            	 	 "danceability"=>$rs['danceability'],
	            		 "energy"=>$rs['energy'],
	            		 "liveness"=>$rs['liveness'],
	            		 "speechiness"=>$rs['speechiness'],
	            		 "acousticness"=>$rs['acousticness'],
	            		 "instrumentalness"=>$rs['instrumentalness'],
	            		 "song_mode"=>$rs['song_mode'],
	            		 "song_key"=>$rs['song_key']
	               );
	             
	            array_push($resultArray, $arrayMiddle);
	        }
	        return $resultArray;
	    }
	    
	    public function selectTestSong( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    	  
	    		$arrayMiddle = array(
	    				"id"=>$rs['id'],
	    				"artist_spotify_id"=>$rs['artist_spotify_id'],
	    				"album_spotify_id"=>$rs['album_spotify_id']
	    		);
	    	  
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    public function selectSimpleSong( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    
	    		$arrayMiddle = array(
	    				"id"=>$rs['id'],
	    				"artist_spotify_id"=>$rs['artist_spotify_id'],
	    				"album_spotify_id"=>$rs['album_spotify_id'],
	    				"title"=>$rs['title'],
	    				"artist"=>$rs['artist'],
	    				"rating"=>$rs['rating']
	    		);
	    
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    public function selectMyPHotSong( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    	  
	    		$arrayMiddle = array(
	    				"id"=>$rs['id'],
	    				"artist_spotify_id"=>$rs['artist_spotify_id'],
	    				"artist_spotify_id"=>$rs['artist_spotify_id'],
	    				"title"=>$rs['title'],
	    				"artist"=>$rs['artist'],
	    				"avg"=>$rs['avg'],
	    				"rating_count"=>$rs['rating_count']
	    		);
	    	  
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    public function selectMyPTopSong( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    	  
	    		$arrayMiddle = array(
	    				"id"=>$rs['id'],
	    				"artist_spotify_id"=>$rs['artist_spotify_id'],
	    				"album_spotify_id"=>$rs['album_spotify_id'],
	    				"title"=>$rs['title'],
	    				"artist"=>$rs['artist'],
	    				"genres"=>$rs['genres'],
	    				"avg_rating"=>$rs['avg_rating'],
	    				"user_rating"=>$rs['user_rating'],
	    				"song_type"=>$rs['song_type'],
	    				"valence"=>$rs['valence'],
	    				"danceability"=>$rs['danceability'],
	    				"energy"=>$rs['energy'],
	    				"liveness"=>$rs['liveness'],
	    				"speechiness"=>$rs['speechiness'],
	    				"acousticness"=>$rs['acousticness'],
	    				"instrumentalness"=>$rs['instrumentalness']
	    		);
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    /*  Rating  */
	    
	    public function selectMySong( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    	  
	    		$arrayMiddle = array(
	    				"id"=>$rs['id'],
	    				"album_spotify_id"=>$rs['album_spotify_id'],
	    				"title"=>$rs['title'],
	    				"artist"=>$rs['artist'],
	    				"rating"=>$rs['rating']
	    		);
	    	  
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    public function selectSongCount( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    
	    		$arrayMiddle = array(
	    				"song_count"=>$rs['song_count']
	    		);
	    
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    public function selectSongRating( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    
	    		$arrayMiddle = array(
	    				"rating"=>$rs['rating']
	    		);
	    
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    public function selectSongAvgRating( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    	  
	    		$arrayMiddle = array(
	    				"avg"=>$rs['avg'],
	    				"rating_count"=>$rs['rating_count']
	    		);
	    	  
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    public function selectSongWithTrackId( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    	  
	    		$arrayMiddle = array(
	    				"id"=>$rs['id']
	    		);
	    	  
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    /*  Recommend  */
	     
	    public function selectRecommendSong( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    
	    		$arrayMiddle = array(
	    				"id"=>$rs['id'],
	    				"artist_spotify_id"=>$rs['artist_spotify_id'],
	    				"album_spotify_id"=>$rs['album_spotify_id'],
	    				"similar_song_id"=>$rs['similar_song_id'],
	    				"title"=>$rs['title'],
	    				"artist"=>$rs['artist'],
	    				"genres"=>$rs['genres'],
	    				"rating"=>$rs['rating'],
	    				"song_type"=>$rs['song_type'],
	    				"valence"=>$rs['valence'],
	    				"danceability"=>$rs['danceability'],
	    				"energy"=>$rs['energy'],
	    				"liveness"=>$rs['liveness'],
	    				"speechiness"=>$rs['speechiness'],
	    				"acousticness"=>$rs['acousticness'],
	    				"instrumentalness"=>$rs['instrumentalness']
	    		);
	    
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    /*  Genre  */
	    
	    public function selectGenre( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    	  
	    		$arrayMiddle = array(
	    				"genre"=>$rs['genre']
	    		);
	    	  
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    /*  Album  */
	     
	    public function selectMyAlbum( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    
	    		$arrayMiddle = array(
	    				"id"=>$rs['id'],
	    				"name"=>$rs['name'],
	    				"artist"=>$rs['artist'],
	    				"release_date"=>$rs['release_date'],
	    				"image_300"=>$rs['image_300']
	    		);
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    /*  Artist  */
	    
	    public function selectMyArtist( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    	  
	    		$arrayMiddle = array(
	    				"id"=>$rs['id'],
	    				"name"=>$rs['name'],
	    				"genres"=>$rs['genres'],
	    				"image_300"=>$rs['image_300']
	    		);
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    /*  GomLog  */
	    
	    public function selectMusic( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    
	    
	    		$arrayMiddle = array(
	    				"title"=>$rs['title'],
	    				"artist"=>$rs['artist'],
	    				"album"=>$rs['album'],
	    				"play_count"=>$rs['play_count'],
	    		);
	    
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    public function selectTitle( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    	  
	    	  
	    		$arrayMiddle = array(
	    				"title"=>$rs['title']
	    		);
	    	  
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    public function getMySQLUsage( $resultSet  ){
	    	$resultArray = array();
	    	while( $rs = mysql_fetch_array( $resultSet ) ){
	    
	    
	    		$arrayMiddle = array(
	    				"MAXSize"=>$rs['MAXSize']
	    		);
	    
	    		array_push($resultArray, $arrayMiddle);
	    	}
	    	return $resultArray;
	    }
	    
	    /*  Common  */
	    
	    public function Response( $resultSet  ){
	    
	    	$resultArray = array();
	    	if($resultSet) {
	    		$arrayMiddle = array(
	    				"result"=>true
	    		);
	    	} else {
	    		$arrayMiddle = array(
	    				"result"=>false
	    		);
	    	}
	    
	    	array_push($resultArray, $arrayMiddle);
	    	return $resultArray;
	    }
	         
	}

?>