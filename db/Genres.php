<?php 
	header("Content-type: text/html; charset=utf-8");
	include('./DBHandler.php');

	$dao = new Dao();
	
	$query = $_REQUEST['query'];
	$user_id = $_REQUEST['user_id'];
	$genre = $_REQUEST['genre'];
	$detail = $_REQUEST['detail'];
	
// 	error_reporting(E_ALL);
// 	ini_set("display_errors", 1);

	if ($query == "InsertGenreDetail") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(),
			"  INSERT INTO genres
			   (genre, detail) 
			   values ( \"$genre\", \"$detail\")  "
		);
		
		print_r(  json_encode( $dao->Response($resultSet)) );
	} else if ($query == "SelectGenreWithDetail") {
	
		$resultSet = $dao->getResultSet( $dao->getConnection(),
			"  SELECT genre
				FROM genres 
				WHERE detail = \"$detail\" "
		);
		
		print_r(  json_encode( $dao->selectGenre($resultSet)) );
	} 
	
?>