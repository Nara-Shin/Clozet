<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		프로모션 상품 리스트
		-
		2016.01.06 by Junseok

		1. Request
		-

		2. Response
		{
		"products":[{
			   "prd_name":상품명
			   "prd_price":상품가격
			   "prd_image":상품이미지(full)
			   "prd_url":상품 쇼핑몰 URL
		},]
		}
	*/


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


		$products[] = array("prd_name" => $row[PrdName], "prd_brand" => $product_brand, "prd_price" => $row[PrdPrice], "prd_image" => "http://godeung.woobi.co.kr/clozet/img/product/".$row[PrdImage], "prd_category" => $row[PrdCategory], "prd_url" => $row[PrdUrl]);
	}




	// JSON으로 반환
	$val = array("products" => $products);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);

?>
