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

		// Query 1 - 이미 좋아요를 눌렀는지 체크
		$query = sprintf("SELECT * FROM MemberLike WHERE LikeMember = '%s' AND PrdCode = (SELECT PrdCode FROM ProductInfo WHERE PrdBarcode = '%s') AND DeleteYN != 'Y'",
		mysql_real_escape_string($member_code),
		mysql_real_escape_string($barcode));

		$result = mysql_query($query);

		$rows = mysql_num_rows($result);

		if($rows > 0){
			
			// Query 2 - 좋아요 상태를 삭제 상태로 업데이트
			$query = sprintf("UPDATE MemberLike SET DeleteYN = 'Y' WHERE LikeMember = '%s' AND PrdCode = (SELECT PrdCode FROM ProductInfo WHERE PrdBarcode = '%s')",
			mysql_real_escape_string($member_code),
			mysql_real_escape_string($barcode));

			$result = mysql_query($query);

		}else{

			// Query 3 - 좋아요 코드 가져오기
			$query = sprintf("SELECT MAX(LikeCode)+1 AS LikeCode FROM MemberLike");

			$result = mysql_query($query);

			while($row = mysql_fetch_array($result)){
			   $like_code = $row[LikeCode];
			}
			
			// Query 4 - 좋아요 입력
			$query = sprintf("INSERT INTO MemberLike(`LikeCode`, `PrdCode`, `LikeMember`, `DeleteYN`, `RegDate`, `ModDate`) VALUES('%s',(SELECT PrdCode FROM ProductInfo WHERE PrdBarcode = '%s'),'%s','%s','%s','%s')",
			mysql_real_escape_string($like_code),
			mysql_real_escape_string($barcode),
			mysql_real_escape_string($member_code),
			mysql_real_escape_string("N"),
			mysql_real_escape_string(date("Y-m-d H:i:s")),
			mysql_real_escape_string(date("Y-m-d H:i:s")));

			$result = mysql_query($query);

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
