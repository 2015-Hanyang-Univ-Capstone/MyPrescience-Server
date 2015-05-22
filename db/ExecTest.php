<?php 
	header("Content-type: text/html; charset=utf-8");
// 	include('./DBHandler.php');

// 	$dao = new Dao();
	
// 	$query = $_REQUEST['query'];
// 	$facebook_id = $_REQUEST['facebookId'];
	
	error_reporting(E_ALL);
	ini_set("display_errors", 1);

// 	if ($query == "insertUser") {
	
// 		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
// 				" INSERT INTO user (facebook_id)
// 			      SELECT '$facebook_id' FROM DUAL
// 				  WHERE NOT EXISTS (SELECT * FROM user WHERE facebook_id='$facebook_id') "
				
// 		);
// 		print_r(  json_encode( $dao->Response( $resultSet ) ) );
		
// 	} else if ($query == "searchUser") {
	
// 		$resultSet = $dao->getResultSet( $dao->getConnection(),
				
// 				" SELECT id
// 				  FROM user
// 				  WHERE facebook_id = '$facebook_id' "
				
// 		);
// 		print_r(  json_encode( $dao->selectUserId( $resultSet ) ) );
// 	} 

	exec('java -jar exec.jar test', $output);
	print_r($output);
// 	$result = array ('recommend'=>"true");
// 	echo json_encode($result);
?>