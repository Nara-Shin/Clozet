<?
	include "/www/clozet/lib/dbconnect.php";
	
	/*
		�귣�� �˻�
		NFC �±׸� ���� �귣�� �˻� �� ���� ��ȯ
		2016.01.06 by Junseok

		1. Request
		fitroom_code=6�ڸ� ���÷� �ڵ�

		2. Response
		{
		"brand_name":�귣���
		"branch_name":������
		"brand_img":���� �귣�� ��� �̹���
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
