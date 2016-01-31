<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		프로모션 상품 리스트
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6자리 회원코드

		2. Response
		{
		"products":[{
			"prd_name":상품명
			"prd_brand":브랜드명
			"prd_barcode":상품바코드
			"prd_price":상품가격
			"prd_image":상품이미지(full)
			"prd_category":1 - 상의, 2 - 하의
			"prd_url":상품 쇼핑몰 URL
			"like_status":현재 좋아요상태
		},]
		}
	*/
	
	$member_code = $_POST[member_code];

	// Query 1 - 프로모션 상품 리스트 가져오기
	$query = sprintf("SELECT * FROM ProductInfo WHERE PrdCode IN(SELECT PrdCode FROM ProductPromotion WHERE StartDate <= NOW() AND EndDate >= NOW())");

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
		
		// Query 2 - 상품 브랜드 정보 가져오기
		$query = sprintf("SELECT BrandName FROM ShopBrand WHERE BrandCode = (SELECT BrandCode FROM ShopInfo WHERE ShopCode = (SELECT ShopCode FROM ProductInfo WHERE PrdCode = '%s'))",
		mysql_real_escape_string($row[PrdCode]));

		$result2 = mysql_query($query);

		while($row2 = mysql_fetch_array($result2)){
			$product_brand = $row2[BrandName];
		}

		// Query 3 - 상품 좋아요 상태 가져오기
		$query = sprintf("SELECT COUNT(*) AS LikeCount FROM MemberLike WHERE LikeMember = '%s' AND PrdCode = '%s' AND DeleteYN != 'Y'",
		mysql_real_escape_string($member_code),
		mysql_real_escape_string($row[PrdCode]));

		$result3 = mysql_query($query);

		while($row3 = mysql_fetch_array($result3)){
			if($row3[LikeCount] > 0){
				$like_status = "1";
			}else{
				$like_status = "0";
			}
		}


		$products[] = array("prd_name" => $row[PrdName], "prd_brand" => $product_brand, "prd_barcode" => $row[PrdBarcode], "prd_price" => $row[PrdPrice], "prd_image" => "http://godeung.woobi.co.kr/clozet/img/product/".$row[PrdImage], "prd_category" => $row[PrdCategory], "prd_url" => $row[PrdUrl], "like_status" => $like_status);
	}


	// JSON으로 반환
	$val = array("products" => $products);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);

?>
