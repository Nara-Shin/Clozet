<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		점원 회원가입 및 로그인
		-
		2016.01.06 by Junseok

		1. Request
		gcm_id=기기아이디
		android_id=폰 고유값

		2. Response
		"{
		"confirm_message":결과 메시지(success/fail)
		"clerk_code":6자리 회원코드
		}"

	*/

	$gcm_id = $_POST[gcm_id];
	$android_id = $_POST[android_id];

	// Request Value가 빈 값일 경우 무조건 fail
	if($gmc_id == "" || $android_id == ""){

		$confirm_message = "fail";

	}else{

		// Query 1 - 회원인지 체크하기
		$query = sprintf("SELECT * FROM ClerkInfo WHERE AndroidId = '%s'",
		mysql_real_escape_string($android_id));

		$result = mysql_query($query);

		$rows = mysql_num_rows($result);

		while($row = mysql_fetch_array($result)){
		   $clerk_code = $row[ClerkCode];
		}

		if($rows > 0){ // 이미 회원일 경우

			$confirm_message = "success";

		}else{ // 회원이 아닐 경우
			
			// Query 2 - 회원 코드 가져오기
			$query = sprintf("SELECT MAX(ClerkCode)+1 AS ClerkCode FROM ClerkInfo");

			$result = mysql_query($query);

			while($row = mysql_fetch_array($result)){
			   $clerk_code = $row[ClerkCode];
			}
			
			// Query 3 - 회원가입 하기
			$query = sprintf("INSERT INTO ClerkInfo(`ClerkCode`, `ClerkName`, `WorkingShop`, `GcmRegId`, `AndroidId`, `RegDate`, `ModDate`) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
			mysql_real_escape_string($clerk_code),
			mysql_real_escape_string(""),
			mysql_real_escape_string(""),
			mysql_real_escape_string($gcm_id),
			mysql_real_escape_string($android_id),
			mysql_real_escape_string(date("Y-m-d H:i:s")),
			mysql_real_escape_string(date("Y-m-d H:i:s")));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";
			}else{
				$confirm_message = "fail";
			}

		}

	}

	// JSON으로 반환
	$val = array("confirm_message" => $confirm_message, "clerk_code" => $clerk_code);
	$output = json_encode($val);
	echo urldecode($output);

?>
