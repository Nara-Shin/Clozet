<?
	include "/www/clozet/lib/dbconnect.php";
	
	/*
		예약요청
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6자리 회원코드
		option_code=6자리 옵션 코드

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
