<?
	header('Content-Type: application/json');

	include "../lib/dbconnect.php";

	/*
		직원 평가
		-
		2016.01.10 by Kyungrae

		1. Request
		member_code=6자리 회원코드
		star_count=별개수

		2. Response
		{
			"confirm_message""결과 메시지(success/fail)
		}

	*/

	$member_code = $_POST[member_code];
	$star_count = $_POST[star_count];

	if ($member_code == "" || $star_count == ""){
		$confirm_message = "fail";
	}else {
		$query = "SELECT * FROM ChangeRequest WHERE ReqMember = '$member_code' ORDER BY ReqCode DESC LIMIT 1";
		$result = mysql_query($query);
		$row = mysql_fetch_object($result);
		$clerk_code = $row->ReqClerkCode;
		
		$query = sprintf("INSERT INTO ClerkEvaluation(ClerkCode, StarCount, RegDate, ModDate) VALUES('%s', '%s', '%s', '%s')",
						 mysql_real_escape_string($clerk_code),
						 mysql_real_escape_string($star_count),
						 mysql_real_escape_string(date("Y-m-d H:i:s")),
						 mysql_real_escape_string(date("Y-m-d H:i:s")));
		$result = mysql_query($query);
		
		if($result){
			$confirm_message = "success";
		}else{
			$confirm_message = "fail";
		}
	}

	// JSON으로 반환
	$val = array("confirm_message" => $confirm_message);
	$output = json_encode($val);
	echo urldecode($output);

?>