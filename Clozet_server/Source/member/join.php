<?
	include "/www/clozet/lib/dbconnect.php";
	
	/*
		ȸ������ �� �α���
		-
		2016.01.06 by Junseok

		1. Request
		gcm_id=�����̵�
		android_id=�� ������

		2. Response
		{
		"confirm_message":��� �޽���(success/fail)
		"member_code":6�ڸ� ȸ���ڵ�
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
