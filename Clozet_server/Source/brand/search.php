<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		�귣�� �˻�
		NFC �±׸� ���� �귣�� �˻� �� ���� ��ȯ
		2016.01.06 by Junseok

		1. Request
		fitroom_code=6�ڸ� ���÷� �ڵ�

		2. Response
		{
		"brand_name":�귣���
		"branch_name":������
		"brand_img":���� �귣�� ��� �̹���
		}
	*/

	$fitroom_code = $_POST[fitroom_code];

	// Query 1 - ���� ���� ��������
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


	// JSON���� ��ȯ
	$val = array("brand_name" => $brand_name, "branch_name" => $branch_name, "brand_img" => $brand_img);
	$output = json_encode($val);
	echo urldecode($output);

?>
