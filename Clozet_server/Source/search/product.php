<?
	include "/www/clozet/lib/dbconnect.php";
	
	/*
		상품검색
		바코드로 상품 검색
		2016.01.06 by Junseok

		1. Request
		barcode=최대 20자리문자열
		member_code=6자리 회원코드

		2. Response
		{
		"product_code":6자리 코드
		"product_brand":브랜드명
		"product_name":상품명
		"product_detail":상품설명
		"product_price":상품가격
		"product_image":상품이미지 URL
		"product_sizes":상품사이즈(,로 구분)
		"product_colors":상품색상(,로 구분)
		"options"[{
			   "size":상품사이즈
			   "color":상품색상
			   "stock":재고수
			   "code":6자리 옵션코드
		},]
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
