<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
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

	$barcode = $_POST[barcode];
	$member_code = $_POST[member_code];


	// Query 1 - 상품 브랜드 정보 가져오기
	$query = sprintf("SELECT BrandName FROM ShopBrand WHERE BrandCode = (SELECT BrandCode FROM ShopInfo WHERE ShopCode = (SELECT ShopCode FROM ProductInfo WHERE PrdBarcode = '%s'))",
	mysql_real_escape_string($barcode));

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
		$product_brand = $row[BrandName];
	}

	// Query 2 - 상품 상세 정보 가져오기
	$query = sprintf("SELECT * FROM ProductInfo Info, ProductOption Opt WHERE Info.PrdCode = Opt.PrdCode AND Info.PrdBarcode = '%s'",
	mysql_real_escape_string($barcode));

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
		$product_code = $row[PrdCode];
		$product_name = $row[PrdName];
		$product_detail = $row[PrdDetail];
		$product_price = $row[PrdPrice];
		$product_image = "http://godeung.woobi.co.kr/clozet/img/product/" . $row[PrdImage];
		$options[] = array("size" => $row[PrdSize], "color" => $row[PrdColor], "stock" => $row[PrdStock], "code" => $row[OptionCode]);
	}

	// Query 3 - 상품 사이즈 정보 가져오기
	$query = sprintf("SELECT DISTINCT PrdSize FROM ProductInfo Info, ProductOption Opt WHERE Info.PrdCode = Opt.PrdCode AND Info.PrdBarcode = '%s'",
	mysql_real_escape_string($barcode));

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
		$product_sizes .= $row[PrdSize].",";
	}

	// Query 4 - 상품 색상 정보 가져오기
	$query = sprintf("SELECT DISTINCT PrdColor FROM ProductInfo Info, ProductOption Opt WHERE Info.PrdCode = Opt.PrdCode AND Info.PrdBarcode = '%s'",
	mysql_real_escape_string($barcode));

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
		$product_colors .= $row[PrdColor].",";
	}

	// Query 5 - 로그 코드 가져오기
	$query = sprintf("SELECT MAX(LogCode)+1 AS LogCode FROM ProductSearchLog");

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
	   $log_code = $row[LogCode];
	}
			

	// Query 6 - 검색 로그 입력하기
	$query = sprintf("INSERT INTO ProductSearchLog(`LogCode`, `SearchMember`, `SearchProduct`, `RegDate`, `ModDate`) VALUES('%s', '%s', '%s', '%s', '%s')",
	mysql_real_escape_string($log_code),
	mysql_real_escape_string($member_code),
	mysql_real_escape_string($product_code),
	mysql_real_escape_string(date("Y-m-d H:i:s")),
	mysql_real_escape_string(date("Y-m-d H:i:s")));

	$result = mysql_query($query);

		
	// JSON으로 반환
	$val = array("product_code" => $product_code, "product_brand" => $product_brand, "product_name" => $product_name, "product_detail" => $product_detail, "product_price" => $product_price, "product_image" => $product_image, "product_sizes" => $product_sizes, "product_colors" => $product_colors, "options" => $options);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);

?>
