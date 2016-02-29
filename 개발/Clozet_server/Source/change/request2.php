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
		$query = sprintf("SELECT ClerkCode FROM ClerkInfo WHERE ClerkCode NOT IN (SELECT ReqClerkCode FROM ChangeRequest WHERE TIMESTAMPDIFF(MINUTE, RegDate, NOW()) < 1)");

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
			$clerk_codes = $row[ClerkCode];
		}

		$clerk_code = $clerk_codes[rand(0,count($clerk_codes))];

		// Query 3 - 요청 DB에 입력하기
		$query = sprintf("INSERT INTO ChangeRequest(`ReqCode`, `ReqMember`, `RequestPrdOption`, `ReqResult`, `ReqClerkCode`, `LimitTime`, `RegDate`, `ModDate`) VALUES('%s', '%s','%s', '%s','%s', '%s','%s', '%s')",
		mysql_real_escape_string($request_code),
		mysql_real_escape_string($member_code),
		mysql_real_escape_string($option_code),
		mysql_real_escape_string("201"),
		mysql_real_escape_string($clerk_code),
		mysql_real_escape_string(date("Y-m-d H:i:s",strtotime("+30 seconds"))),
		mysql_real_escape_string(date("Y-m-d H:i:s")),
		mysql_real_escape_string(date("Y-m-d H:i:s")));

		$result = mysql_query($query);

		if($result){
			$confirm_message = "success";

			// Query 4 - 상품 정보 가져오기
			$query = sprintf("SELECT Info.PrdName, Opt.PrdSize, Opt.PrdColor FROM ProductInfo Info, ProductOption Opt WHERE Info.PrdCode = Opt.PrdCode AND Opt.OptionCode = '%s'",
			mysql_real_escape_string($option_code));

			$result = mysql_query($query);

			while($row = mysql_fetch_array($result)){
				$prd_name = $row[PrdName];
				$prd_size = $row[PrdSize];
				$prd_color = $row[PrdColor];
			}

			// GCM으로 직원에게 발송


		}else{
			$confirm_message = "fail";
		}

	}
		
	// JSON으로 반환
	$val = array("confirm_message" => $confirm_message, "request_code" => $request_code, "request_code" => $req_product_name, "req_product_name" => $prd_name, "req_product_size" => $prd_size, "req_product_color" => $prd_color, "fitting_room" => $fitroom_code);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);
?>
