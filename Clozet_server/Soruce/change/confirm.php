<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		��ȯ��û���� �� ����
		-
		2016.01.06 by Junseok

		1. Request
		request_code=6�ڸ� ��ȯ��û �ڵ�
		confirm=true/false/delay/cancel/over


		2. Response
		{
		"confirm_message":��û��� �޽���(success/fail)
		}

	*/

	$request_code = $_POST[request_code];
	$confirm = $_POST[confirm];


	// Request Value�� �� ���� ��� ������ fail
	if($request_code == "" || $confirm == ""){

		$confirm_message = "fail";

	}else{

		if($confirm == "okay"){ // ���� - ���º��� -> ������ Ǫ��
			
			// Query 1 - ��ȯ ��û ���� ���� (���� - 205)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '205' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";

				// GCM���� ������ Ǫ�� �߼� ���� - ��û�� �����Ǿ����ϴ�.

				// ��û ��� GCM RegId�� ��������
				$query = sprintf("SELECT GcmRegId FROM MemberInfo WHERE MemberCode = (SELECT ReqMember FROM ChangeRequest WHERE ReqCode = '%s')",
				mysql_real_escape_string($request_code));

				$result = mysql_query($query);

				while($row = mysql_fetch_array($result)){
				   $devices[] = $row[GcmRegId];
				}

				$message = "okay";
				$admin = "false";

				include "../lib/gcm/sendPushMessageLib.php";

				// GCM���� ������ Ǫ�� �߼� ��

			}else{
				$confirm_message = "fail";
			}

		}else if($confirm == "refuse"){ // ���� - ���� ��û ���º��� -> ���ο� ���� ���� �� ��û��� -> ���ο� �������� Ǫ��

			// Query 2 - ��ȯ ��û ���� ���� (���� - 204)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '204' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";

				// Query 1 - ��û �ڵ� ��������
				$query = sprintf("SELECT MAX(ReqCode)+1 AS ReqCode FROM ChangeRequest");

				$result = mysql_query($query);

				while($row = mysql_fetch_array($result)){
				   $request_code = $row[ReqCode];
				}


				// Query 2 - ���� �ڵ� ��������
				$query = sprintf("SELECT ClerkCode FROM ClerkInfo WHERE ClerkCode NOT IN (SELECT ReqClerkCode FROM ChangeRequest WHERE TIMESTAMPDIFF(MINUTE, RegDate, NOW()) < 0)");

				$result = mysql_query($query);

				while($row = mysql_fetch_array($result)){
					$clerk_codes[] = $row[ClerkCode];
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

					// Query 3 - ���÷� ���ȣ ��������
					$query = sprintf("SELECT FitRoomNum FROM ShopFittingRoom WHERE FitRoomCode = (SELECT FitRoomCode FROM ChangeRequest WHERE ReqCode = '%s')",
					mysql_real_escape_string($request_code));

					$result = mysql_query($query);

					while($row = mysql_fetch_array($result)){
						$fitroom_number = $row[FitRoomNum];
					}

					// Query 4 - ��ǰ ���� ��������
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

					// GCM���� �������� Ǫ�� �߼� ����

					// ��û ���� GCM RegId�� ��������
					$query = sprintf("SELECT GcmRegId FROM ClerkInfo WHERE ClerkCode = (SELECT ReqClerkCode FROM ChangeRequest WHERE ReqCode = '%s')",
					mysql_real_escape_string($request_code));

					$result = mysql_query($query);

					while($row = mysql_fetch_array($result)){
						$devices[] = $row[GcmRegId];
					}

					$message = '{"request_code":"'.$request_code.'","room":"'.$fitroom_number.'","prdname":"'.$req_product_name.'","img":"http://godeung.woobi.co.kr/clozet/img/product/'.$req_product_image.'","size":"'.$req_product_size.'","color":"'.$req_product_color.'","count":"1","code":"'.$req_product_shopcode.'","price":"'.$req_product_price.'","stockURL":"'.$req_product_url.'"}';
					$admin = "true";

					include "../lib/gcm/sendPushMessageLib.php";

					// GCM���� �������� Ǫ�� �߼� ��

				}else{
					$confirm_message = "fail";
				}

			}else{
				$confirm_message = "fail";
			}

		}else if($confirm == "delay"){ // ���� - ���� ��û ���º��� -> ���ο� ���� ���� �� ��û��� -> ���ο� �������� Ǫ��
			
			// Query 3 - ��ȯ ��û ���� ���� (30�� �̻� ���� - 202)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '203' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";

				// Query 1 - ��û �ڵ� ��������
				$query = sprintf("SELECT MAX(ReqCode)+1 AS ReqCode FROM ChangeRequest");

				$result = mysql_query($query);

				while($row = mysql_fetch_array($result)){
				   $request_code = $row[ReqCode];
				}


				// Query 2 - ���� �ڵ� ��������
				$query = sprintf("SELECT ClerkCode FROM ClerkInfo WHERE ClerkCode NOT IN (SELECT ReqClerkCode FROM ChangeRequest WHERE TIMESTAMPDIFF(MINUTE, RegDate, NOW()) < 0)");

				$result = mysql_query($query);

				while($row = mysql_fetch_array($result)){
					$clerk_codes[] = $row[ClerkCode];
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

					// Query 3 - ���÷� ���ȣ ��������
					$query = sprintf("SELECT FitRoomNum FROM ShopFittingRoom WHERE FitRoomCode = (SELECT FitRoomCode FROM ChangeRequest WHERE ReqCode = '%s')",
					mysql_real_escape_string($request_code));

					$result = mysql_query($query);

					while($row = mysql_fetch_array($result)){
						$fitroom_number = $row[FitRoomNum];
					}

					// Query 4 - ��ǰ ���� ��������
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

					// GCM���� �������� Ǫ�� �߼� ����

					// ��û ���� GCM RegId�� ��������
					$query = sprintf("SELECT GcmRegId FROM ClerkInfo WHERE ClerkCode = (SELECT ReqClerkCode FROM ChangeRequest WHERE ReqCode = '%s')",
					mysql_real_escape_string($request_code));

					$result = mysql_query($query);

					while($row = mysql_fetch_array($result)){
						$devices[] = $row[GcmRegId];
					}

					$message = '{"request_code":"'.$request_code.'","room":"'.$fitroom_number.'","prdname":"'.$req_product_name.'","img":"http://godeung.woobi.co.kr/clozet/img/product/'.$req_product_image.'","size":"'.$req_product_size.'","color":"'.$req_product_color.'","count":"1","code":"'.$req_product_shopcode.'","price":"'.$req_product_price.'","stockURL":"'.$req_product_url.'"}';
					$admin = "true";

					include "../lib/gcm/sendPushMessageLib.php";

					// GCM���� �������� Ǫ�� �߼� ��

				}else{
					$confirm_message = "fail";
				}

			}else{
				$confirm_message = "fail";
			}

		}else if($confirm == "cancel"){ // ��� - ���� ��û ���� ���� -> �������� Ǫ��
			
			// Query 4 - ��ȯ ��û ���� ���� (��� - 203)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '203' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";
				
			}else{
				$confirm_message = "fail";
			}

		}else if($confirm == "over"){ // Ƚ���ʰ�
			

		}else if($confirm == "deliveryokay"){ // ��޿Ϸ� - ���� ��û ���º��� -> ������ Ǫ��
			
			// Query 5 - ��ȯ ��û ���� ���� (��޿Ϸ� - 206)
			$query = sprintf("UPDATE ChangeRequest SET ReqResult = '206' WHERE ReqCode = '%s'",
			mysql_real_escape_string($request_code));

			$result = mysql_query($query);

			if($result){
				$confirm_message = "success";

				// GCM���� ������ Ǫ�� - ����� �Ϸ�Ǿ����ϴ�.
				
				// ��û ��� GCM RegId�� ��������
				$query = sprintf("SELECT GcmRegId FROM MemberInfo WHERE MemberCode = (SELECT ReqMember FROM ChangeRequest WHERE ReqCode = '%s')",
				mysql_real_escape_string($request_code));

				$result = mysql_query($query);

				while($row = mysql_fetch_array($result)){
				   $devices[] = $row[GcmRegId];
				}

				$message = "deliveryokay";
				$admin = "false";

				include "../lib/gcm/sendPushMessageLib.php";

				// GCM���� ������ Ǫ�� �߼� ��

			}else{
				$confirm_message = "fail";
			}

		}

	}
		
	// JSON���� ��ȯ
	$val = array("confirm_message" => $confirm_message);
	$output = json_encode($val);
	echo urldecode($output);

	// $rows = mysql_num_rows($result);

?>
