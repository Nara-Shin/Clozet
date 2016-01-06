<?
	include "/www/clozet/lib/dbconnect.php";
	
	/*
		브랜드 검색
		NFC 태그를 통해 브랜드 검색 후 정보 반환
		2016.01.06 by Junseok

		1. Request
		fitroom_code=6자리 피팅룸 코드

		2. Response
		{
		"brand_name":브랜드명
		"branch_name":지점명
		"brand_img":메인 브랜드 배너 이미지
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
