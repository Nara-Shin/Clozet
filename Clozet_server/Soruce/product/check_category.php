<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		갤러리 좋아요 개수
		-
		2016.01.06 by Junseok

		1. Request
		barcode=최대 20자리문자열

		2. Response
		{
		"prd_category":상의 - 1, 하의 - 2
		}

	*/

	$barcode = $_POST[barcode];

	// Query 1 - 상품 종류 가져오기
	$query = sprintf("SELECT PrdCategory FROM ProductInfo WHERE PrdBarcode = '%s'",
	mysql_real_escape_string($barcode));

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
	   $prd_category = $row[prd_category];
	}


	// JSON으로 반환
	$val = array("prd_category" => $prd_category);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);
?>
