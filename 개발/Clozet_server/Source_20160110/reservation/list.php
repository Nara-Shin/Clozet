<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		���� ����Ʈ ��������
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6�ڸ� ȸ���ڵ�

		2. Response
		{
		"products"[
			   "proudct_name":��ǰ��
			   "product_image":��ǰ�̹���
			   "product_size":��ǰ������
			   "product_color":��ǰ���� RGB�ڵ�
		]
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
