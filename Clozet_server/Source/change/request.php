<?
	include "/www/clozet/lib/dbconnect.php";
	
	/*
		교환요청(고객용)
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6자리 회원코드(중간평가 이전엔 임의값)
		option_code=6자리 옵션 코드
		fitroom_code=6자리 피팅룸 코드



		2. Response
		{
		"confirm_message":요청결과 메시지(success/fail)
		"request_code":6자리 요청코드
		"req_product_name":요청 상품명
		"req_product_size":요청 상품사이즈
		"req_product_color":요청 상품색상
		"fitting_room":피팅룸 번호
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
