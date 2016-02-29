<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		재고수 획득
		-
		2016.01.12 by Junseok

		1. Request
		product_code=6자리 상품코드
		product_size=옵션 사이즈
		product_color=옵션 색상

		2. Response
		{
		"stock":재고수
		}
		
	*/

	$product_code = $_POST[product_code];
	$product_size = $_POST[product_size];
	$product_color = $_POST[product_color];


	if($product_code != "" && $product_size != "" && $product_color == ""){ // 사이즈만 들어올 경우

		// Query 1 - 상품 재고 가져오기
		$query = sprintf("SELECT SUM(PrdStock) AS PrdStock FROM ProductOption WHERE PrdCode = '%s' AND PrdSize = '%s'",
		mysql_real_escape_string($product_code),
		mysql_real_escape_string($product_size));

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
			$stock = $row[PrdStock];
		}

	}else if($product_code != "" && $product_size == "" && $product_color != ""){ // 색상만 들어올 경우

		// Query 2 - 상품 재고 가져오기
		$query = sprintf("SELECT SUM(PrdStock) AS PrdStock FROM ProductOption WHERE PrdCode = '%s' AND PrdColor = '%s'",
		mysql_real_escape_string($product_code),
		mysql_real_escape_string($product_color));

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
			$stock = $row[PrdStock];
		}

	}else if($product_code != "" && $product_size != "" && $product_color != ""){ // 사이즈와 색상 모두 들어올 경우

		// Query 3 - 상품 재고 가져오기
		$query = sprintf("SELECT SUM(PrdStock) AS PrdStock FROM ProductOption WHERE PrdCode = '%s' AND PrdSize = '%s' AND PrdColor = '%s'",
		mysql_real_escape_string($product_code),
		mysql_real_escape_string($product_size),
		mysql_real_escape_string($product_color));

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
			$stock = $row[PrdStock];
		}

	}

		
	// JSON으로 반환
	$val = array("stock" => $stock);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);
?>
