<?
	include "/www/clozet/lib/dbconnect.php";
	
	/*
		회원가입 및 로그인
		-
		2016.01.06 by Junseok

		1. Request
		gcm_id=기기아이디
		android_id=폰 고유값

		2. Response
		{
		"confirm_message":결과 메시지(success/fail)
		"member_code":6자리 회원코드
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
