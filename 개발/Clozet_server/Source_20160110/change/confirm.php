<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		교환요청수락 및 거절
		-
		2016.01.06 by Junseok

		1. Request
		request_code=6자리 교환요청 코드
		confirm=true/false/delay/cancel/over


		2. Response
		{
		"confirm_message":요청결과 메시지(success/fail)
		}

	*/

	$request_code = $_POST[request_code];
	$confirm = $_POST[confirm];


	// Request Value가 빈 값일 경우 무조건 fail
	if($request_code == "" || $confirm == ""){

		$confirm_message = "fail";

	}else{

		if($confirm == "okay"){ // 수락 - 상태변경 -> 고객에게 푸쉬
			
			// Query 1 - 교환 요청 상태 변경 (수락 - 205)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '205' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";

				// GCM으로 고객에게 푸쉬 발송 시작 - 요청이 수락되었습니다.

				// 요청 멤버 GCM RegId값 가져오기
				$query = sprintf("SELECT GcmRegId FROM MemberInfo WHERE MemberCode = (SELECT ReqMember FROM ChangeRequest WHERE ReqCode = '%s')",
				mysql_real_escape_string($request_code));

				$result = mysql_query($query);

				while($row = mysql_fetch_array($result)){
				   $devices[] = $row[GcmRegId];
				}

				$message = "okay";
				$admin = "false";

				include "../lib/gcm/sendPushMessageLib.php";

				$gcpm = new GCMPushMessage($apiKey);
				$gcpm->setDevices($devices);
				$response = $gcpm->send($message);

				// GCM으로 고객에게 푸쉬 발송 끝

			}else{
				$confirm_message = "fail";
			}

		}else if($confirm == "refuse"){ // 거절 - 기존 요청 상태변경 -> 새로운 직원 배정 및 요청등록 -> 새로운 직원에게 푸쉬

			// Query 2 - 교환 요청 상태 변경 (거절 - 204)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '204' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";

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

			}else{
				$confirm_message = "fail";
			}

		}else if($confirm == "delay"){ // 지연 - 기존 요청 상태변경 -> 새로운 직원 배정 및 요청등록 -> 새로운 직원에게 푸쉬
			
			// Query 3 - 교환 요청 상태 변경 (30초 이상 지연 - 202)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '203' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";

				// GCM으로 고객에게 푸쉬 - 지연되어 재요청합니다.
			}else{
				$confirm_message = "fail";
			}

		}else if($confirm == "cancel"){ // 취소 - 기존 요청 상태 변경
			
			// Query 4 - 교환 요청 상태 변경 (취소 - 203)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '203' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";
				
			}else{
				$confirm_message = "fail";
			}

		}else if($confirm == "over"){ // 횟수초과
			
			// Query 5 - 교환 요청 상태 변경 (횟수초과 - 203)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '203' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";
			}else{
				$confirm_message = "fail";
			}

		}else if($confirm == "delivery"){ // 배달완료 - 기존 요청 상태변경 -> 고객에게 푸쉬
			
			// Query 5 - 교환 요청 상태 변경 (배달완료 - 203)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '203' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";

				// GCM으로 고객에게 푸쉬 - 배달이 완료되었습니다.
				

			}else{
				$confirm_message = "fail";
			}

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
	$val = array("confirm_message" => $confirm_message);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);

?>
