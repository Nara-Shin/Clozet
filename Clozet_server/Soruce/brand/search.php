<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
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

	$fitroom_code = $_POST[fitroom_code];

	// Query 1 - 상점 정보 가져오기
	$query = "SELECT Brand.BrandName, Shop.ShopName, Shop.ShopImage FROM ShopBrand Brand, ShopInfo Shop WHERE Brand.BrandCode = Shop.BrandCode AND Shop.ShopCode = (SELECT ShopCode FROM ShopFittingRoom WHERE FitRoomcode = '%s')";
	
	$query = sprintf("SELECT Brand.BrandName, Shop.ShopName, Shop.ShopImage FROM ShopBrand Brand, ShopInfo Shop WHERE Brand.BrandCode = Shop.BrandCode AND Shop.ShopCode = (SELECT ShopCode FROM ShopFittingRoom WHERE FitRoomcode = '%s')",
		mysql_real_escape_string($fitroom_code));

	$result = mysql_query($query);

	if(mysql_num_rows($result) > 0){
		
		while($row = mysql_fetch_array($result)){

			$brand_name = $row[BrandName];
			$branch_name = $row[ShopName];
			$brand_img = "http://godeung.woobi.co.kr/clozet/img/view/main/" . $row[ShopImage];
		}

	}else{

		$brand_img = "http://godeung.woobi.co.kr/clozet/img/view/main/cont_nonfc.png";

	}


	// JSON으로 반환
	$val = array("brand_name" => $brand_name, "branch_name" => $branch_name, "brand_img" => $brand_img);
	$output = json_encode($val);
	echo urldecode($output);

?>
