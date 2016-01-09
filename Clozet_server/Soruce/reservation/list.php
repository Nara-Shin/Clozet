<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		예약 리스트 가져오기
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6자리 회원코드

		2. Response
		{
		"products"[
			   "proudct_name":상품명
			   "product_image":상품이미지
			   "product_size":상품사이즈
			   "product_color":상품색상 RGB코드
		]
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
