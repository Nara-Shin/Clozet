<?
	include "/www/clozet/lib/dbconnect.php";
	
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

	// Query 1
	$query = " select 'complete' as col from dual ";

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)) 
	{
	   echo $row['col'].'<br/>';
	}

?>
