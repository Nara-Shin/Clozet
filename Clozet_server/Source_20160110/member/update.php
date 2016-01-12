<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		회원정보 수정
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6자리 회원코드
		age=나이
		sex=성별(W/M)
		height=키

		2. Response
		{
		"confirm_message":결과 메시지(success/fail)
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
