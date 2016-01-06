<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		교환요청수락 및 거절
		-
		2016.01.06 by Junseok

		1. Request
		request_code=6자리 교환요청 코드
		confirm=true/false (boolean)


		2. Response
		{
		"confirm_message":요청결과 메시지(success/fail)
		}

	*/

	// Query 1
	$query = " select 'complete' as col from dual ";

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)) 
	{
	   echo $row['col'].'<br/>';
	}

?>
