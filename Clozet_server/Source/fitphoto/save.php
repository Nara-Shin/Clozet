<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		착샷 저장
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6자리 회원코드
		brand_code=6자리 브랜드코드
		사진은 따로 비동기 처리

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
