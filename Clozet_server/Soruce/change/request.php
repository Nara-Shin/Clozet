<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		교환요청(고객용)
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6자리 회원코드(중간평가 이전엔 임의값)
		option_code=6자리 옵션 코드
		fitroom_code=6자리 피팅룸 코드

		2. Response
		{
		"confirm_message":요청결과 메시지(success/fail)
		"request_code":6자리 요청코드
		"req_product_name":요청 상품명
		"req_product_size":요청 상품사이즈
		"req_product_color":요청 상품색상
		"fitting_room":피팅룸 번호
		}

	*/

	$member_code = $_POST[member_code];
	$option_code = str_replace("#","",$_POST[option_code]);
	$fitroom_code = $_POST[fitroom_code];


	// Request Value가 빈 값일 경우 무조건 fail
	if($member_code == "" || $option_code == "" || $fitroom_code == ""){

		$confirm_message = "fail";

	}else{

		// Query 1 - 요청 코드 가져오기
		$query = sprintf("SELECT MAX(ReqCode)+1 AS ReqCode FROM ChangeRequest");

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
		   $request_code = $row[ReqCode];
		}
			

		// Query 2 - 직원 코드 가져오기
		$query = sprintf("SELECT ClerkCode FROM ClerkInfo WHERE ClerkCode NOT IN (SELECT ReqClerkCode FROM ChangeRequest WHERE TIMESTAMPDIFF(MINUTE, RegDate, NOW()) < 0)");

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
			$clerk_codes[] = $row[ClerkCode];
		}

		// Query 3 - 피팅룸 방번호 가져오기
		$query = sprintf("SELECT FitRoomNum FROM ShopFittingRoom WHERE FitRoomCode = '%s'",
		mysql_real_escape_string($fitroom_code));

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
			$fitroom_number = $row[FitRoomNum];
		}

		$clerk_code = $clerk_codes[rand(0,count($clerk_codes))];

		// Query 4 - 요청 DB에 입력하기
		$query = sprintf("INSERT INTO ChangeRequest(`ReqCode`, `ReqMember`, `RequestPrdOption`, `RequestPrdOption2`, `RequestPrdOption3`, `FtiRoomCode`, `ReqResult`, `ReqClerkCode`, `LimitTime`, `RegDate`, `ModDate`) VALUES('%s','%s','%s','%s','%s','%s','%s','%s', '%s','%s', '%s')",
		mysql_real_escape_string($request_code),
		mysql_real_escape_string($member_code),
		mysql_real_escape_string($option_code),
		mysql_real_escape_string($option_code),
		mysql_real_escape_string($option_code),
		mysql_real_escape_string($fitroom_code),
		mysql_real_escape_string("201"),
		mysql_real_escape_string($clerk_code),
		mysql_real_escape_string(date("Y-m-d H:i:s",strtotime("+30 seconds"))),
		mysql_real_escape_string(date("Y-m-d H:i:s")),
		mysql_real_escape_string(date("Y-m-d H:i:s")));

		$result = mysql_query($query);

		if($result){
			$confirm_message = "success";

			// Query 5 - 상품 정보 가져오기
			$query = sprintf("SELECT Info.PrdName, Info.PrdImage, Info.PrdShopCode, Info.PrdPrice, Info.PrdUrl, Opt.PrdSize, Opt.PrdColor FROM ProductInfo Info, ProductOption Opt WHERE Info.PrdCode = Opt.PrdCode AND Opt.OptionCode = '%s'",
			mysql_real_escape_string($option_code));

			$result = mysql_query($query);

			while($row = mysql_fetch_array($result)){
				$req_product_name = $row[PrdName];
				$req_product_size = $row[PrdSize];
				$req_product_color = $row[PrdColor];
				$req_product_image = $row[PrdImage];
				$req_product_shopcode = $row[PrdShopCode];
				$req_product_price = $row[PrdPrice];
				$req_product_url = $row[PrdUrl];
			}

			// GCM으로 직원에게 푸쉬 발송 시작 

			// 요청 직원 GCM RegId값 가져오기
			$query = sprintf("SELECT GcmRegId FROM ClerkInfo WHERE ClerkCode = (SELECT ReqClerkCode FROM ChangeRequest WHERE ReqCode = '%s')",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			while($row = mysql_fetch_array($result)){
				$devices[] = $row[GcmRegId];
			}

			$message = '{"request_code":"'.$request_code.'","room":"'.$fitroom_number.'","prdname":"'.$req_product_name.'","img":"http://godeung.woobi.co.kr/clozet/img/product/'.$req_product_image.'","size":"'.$req_product_size.'","color":"'.$req_product_color.'","count":"1","code":"'.$req_product_shopcode.'","price":"'.$req_product_price.'","stockURL":"'.$req_product_url.'"}';
			$admin = "true";

			include "../lib/gcm/sendPushMessageLib.php";

			// GCM으로 직원에게 푸쉬 발송 끝

		}else{
			$confirm_message = "fail";
		}

	}
		
	// JSON으로 반환
	$val = array("confirm_message" => $confirm_message, "request_code" => $request_code, "req_product_name" => $req_product_name, "req_product_size" => $req_product_size, "req_product_color" => $req_product_color, "fitting_room" => $fitroom_code);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);
?>