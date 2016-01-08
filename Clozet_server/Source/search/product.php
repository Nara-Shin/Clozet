<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		��ǰ�˻�
		���ڵ�� ��ǰ �˻�
		2016.01.06 by Junseok

		1. Request
		barcode=�ִ� 20�ڸ����ڿ�
		member_code=6�ڸ� ȸ���ڵ�

		2. Response
		{
		"product_code":6�ڸ� �ڵ�
		"product_brand":�귣���
		"product_name":��ǰ��
		"product_detail":��ǰ����
		"product_price":��ǰ����
		"product_image":��ǰ�̹��� URL
		"product_sizes":��ǰ������(,�� ����)
		"product_colors":��ǰ����(,�� ����)
		"options"[{
			   "size":��ǰ������
			   "color":��ǰ����
			   "stock":����
			   "code":6�ڸ� �ɼ��ڵ�
		},]
		}
	*/

	$barcode = $_POST[barcode];
	$member_code = $_POST[member_code];


	// Query 1 - ��ǰ �귣�� ���� ��������
	$query = sprintf("SELECT BrandName FROM ShopBrand WHERE BrandCode = (SELECT BrandCode FROM ShopInfo WHERE ShopCode = (SELECT ShopCode FROM ProductInfo WHERE PrdBarcode = '%s'))",
	mysql_real_escape_string($barcode));

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
		$product_brand = $row[BrandName];
	}

	// Query 2 - ��ǰ �� ���� ��������
	$query = sprintf("SELECT * FROM ProductInfo Info, ProductOption Opt WHERE Info.PrdCode = Opt.PrdCode AND Info.PrdBarcode = '%s'",
	mysql_real_escape_string($barcode));

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
		$product_code = $row[PrdCode];
		$product_name = $row[PrdName];
		$product_detail = $row[PrdDetail];
		$product_price = $row[PrdPrice];
		$product_image = "http://godeung.woobi.co.kr/clozet/product/image/" . $row[PrdImage];
		$options[] = array("size" => $row[PrdSize], "color" => $row[PrdColor], "stock" => $row[PrdStock], "code" => $row[OptionCode]);
	}

	// Query 3 - ��ǰ ������ ���� ��������
	$query = sprintf("SELECT DISTINCT PrdSize FROM ProductInfo Info, ProductOption Opt WHERE Info.PrdCode = Opt.PrdCode AND Info.PrdBarcode = '%s'",
	mysql_real_escape_string($barcode));

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
		$product_sizes .= $row[PrdSize].",";
	}

	// Query 4 - ��ǰ ���� ���� ��������
	$query = sprintf("SELECT DISTINCT PrdColor FROM ProductInfo Info, ProductOption Opt WHERE Info.PrdCode = Opt.PrdCode AND Info.PrdBarcode = '%s'",
	mysql_real_escape_string($barcode));

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
		$product_colors .= $row[PrdColor].",";
	}

	// Query 5 - �α� �ڵ� ��������
	$query = sprintf("SELECT MAX(LogCode)+1 AS LogCode FROM ProductSearchLog");

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
	   $log_code = $row[LogCode];
	}
			

	// Query 6 - �˻� �α� �Է��ϱ�
	$query = sprintf("INSERT INTO ProductSearchLog(`LogCode`, `SearchMember`, `SearchProduct`, `RegDate`, `ModDate`) VALUES('%s', '%s', '%s', '%s', '%s')",
	mysql_real_escape_string($log_code),
	mysql_real_escape_string($member_code),
	mysql_real_escape_string($product_code),
	mysql_real_escape_string(date("Y-m-d H:i:s")),
	mysql_real_escape_string(date("Y-m-d H:i:s")));

	$result = mysql_query($query);

		
	// JSON���� ��ȯ
	$val = array("product_code" => $product_code, "product_brand" => $product_brand, "product_name" => $product_name, "product_detail" => $product_detail, "product_price" => $product_price, "product_image" => $product_image, "product_sizes" => $product_sizes, "product_colors" => $product_colors, "options" => $options);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);

?>
