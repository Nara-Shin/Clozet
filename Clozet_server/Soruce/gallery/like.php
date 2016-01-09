<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		갤러리 좋아요 버튼
		-
		2016.01.06 by Junseok

		1. Request
		barcode=최대 20자리문자열
		member_code=6자리 회원코드

		2. Response
		{
		"confirm_message":결과 메시지(success/fail)
		}

	*/

	$barcode = $_POST[barcode];
	$member_code = $_POST[member_code];


	// Request Value가 빈 값일 경우 무조건 fail
	if($barcode == "" || $member_code == ""){

		$confirm_message = "fail";

	}else{

		// Query 1 - 값이 있는지 체크
		$query = sprintf("SELECT * FROM ChangeRequest");

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
		   $request_code = $row[ReqCode];
		}
			

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

	// $rows = mysql_num_rows($result);
?>
