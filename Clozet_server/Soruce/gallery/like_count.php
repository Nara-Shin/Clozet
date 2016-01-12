<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		갤러리 좋아요 개수
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6자리 회원코드

		2. Response
		{
		"like_count":좋아요 개수
		}

	*/

	$member_code = $_POST[member_code];

	// Query 1 - 이미 좋아요를 눌렀는지 체크
	$query = sprintf("SELECT COUNT(*) AS LikeCount FROM MemberLike WHERE LikeMember = '%s' AND DeleteYN != 'Y'",
	mysql_real_escape_string($member_code));

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)){
	   $like_count = $row[LikeCount];
	}

		
	// JSON으로 반환
	$val = array("like_count" => $like_count);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);
?>
