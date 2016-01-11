<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		재고변경 (좋아요 푸쉬알림)
		-
		2016.01.06 by Junseok

		1. Request
		product_code=6자리 상품코드
		stock_count=재고수

		2. Response
		{
		"confirm_message":결과 메시지(success/fail)
		}

		3. GCM
		prd_name=상품명
		stock=재고수
		prd_url=상품 쇼핑몰 주소
		image_url=이미지 URL(full)

	*/

	$product_code = $_GET[product_code];
	$stock_count = $_GET[stock_count];


	// Request Value가 빈 값일 경우 무조건 fail
	if($product_code == "" || $stock_count == ""){

		$confirm_message = "fail";

	}else{

		// Query 2 - 재고 정보 업데이트
		$query = sprintf("UPDATE ProductInfo SET PrdStock = '%s' WHERE PrdCode = '%s'",
		mysql_real_escape_string($stock_count),
		mysql_real_escape_string($product_code));

		$result = mysql_query($query);

		if($result){
			$confirm_message = "success";
		}else{
			$confirm_message = "fail";
		}

		// Query 2 - 상품 정보 가져오기
		$query = sprintf("SELECT * FROM ProductInfo WHERE PrdCode = '%s'",
		mysql_real_escape_string($product_code));

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
			$prd_name = $row[PrdName];
			$stock = $row[PrdStock];
			$prd_url = $row[PrdUrl];
			$image_url = "http://godeung.woobi.co.kr/clozet/img/product/".$row[PrdImage];
		}


		// Query 3 - 좋아요 누른 고객 GCM ID 가져오기
		$query = sprintf("SELECT GcmRegId FROM MemberInfo WHERE MemberCode IN (SELECT LikeMember FROM MemberLike WHERE PrdCode = '%s')",
		mysql_real_escape_string($product_code));

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
			$devices[] = $row[GcmRegId];
		}
		
		// 좋아요 누른 고객에게 재고 정보 푸쉬
		$message = '{"prd_name":"'.$prd_name.'","stock":"'.$stock.'","prd_url":"'.$prd_url.'","image_url":"'.$image_url.'"}';
		$admin = "false";

		include "../lib/gcm/sendPushMessageLib.php";

	}

		
	// JSON으로 반환
	$val = array("confirm_message" => $confirm_message);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);
?>
