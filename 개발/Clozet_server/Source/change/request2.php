<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		��ȯ��û(����)
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6�ڸ� ȸ���ڵ�(�߰��� ������ ���ǰ�)
		option_code=6�ڸ� �ɼ� �ڵ�
		fitroom_code=6�ڸ� ���÷� �ڵ�

		2. Response
		{
		"confirm_message":��û��� �޽���(success/fail)
		"request_code":6�ڸ� ��û�ڵ�
		"req_product_name":��û ��ǰ��
		"req_product_size":��û ��ǰ������
		"req_product_color":��û ��ǰ����
		"fitting_room":���÷� ��ȣ
		}

	*/

	$member_code = $_POST[member_code];
	$option_code = str_replace("#","",$_POST[option_code]);
	$fitroom_code = $_POST[fitroom_code];


	// Request Value�� �� ���� ��� ������ fail
	if($member_code == "" || $option_code == "" || $fitroom_code == ""){

		$confirm_message = "fail";

	}else{

		// Query 1 - ��û �ڵ� ��������
		$query = sprintf("SELECT MAX(ReqCode)+1 AS ReqCode FROM ChangeRequest");

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
		   $request_code = $row[ReqCode];
		}
			

		// Query 2 - ���� �ڵ� ��������
		$query = sprintf("SELECT ClerkCode FROM ClerkInfo WHERE ClerkCode NOT IN (SELECT ReqClerkCode FROM ChangeRequest WHERE TIMESTAMPDIFF(MINUTE, RegDate, NOW()) < 1)");

		$result = mysql_query($query);

		while($row = mysql_fetch_array($result)){
			$clerk_codes = $row[ClerkCode];
		}

		$clerk_code = $clerk_codes[rand(0,count($clerk_codes))];

		// Query 3 - ��û DB�� �Է��ϱ�
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

			// Query 4 - ��ǰ ���� ��������
			$query = sprintf("SELECT Info.PrdName, Opt.PrdSize, Opt.PrdColor FROM ProductInfo Info, ProductOption Opt WHERE Info.PrdCode = Opt.PrdCode AND Opt.OptionCode = '%s'",
			mysql_real_escape_string($option_code));

			$result = mysql_query($query);

			while($row = mysql_fetch_array($result)){
				$prd_name = $row[PrdName];
				$prd_size = $row[PrdSize];
				$prd_color = $row[PrdColor];
			}

			// GCM���� �������� �߼�


		}else{
			$confirm_message = "fail";
		}

	}
		
	// JSON���� ��ȯ
	$val = array("confirm_message" => $confirm_message, "request_code" => $request_code, "request_code" => $req_product_name, "req_product_name" => $prd_name, "req_product_size" => $prd_size, "req_product_color" => $prd_color, "fitting_room" => $fitroom_code);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);
?>
