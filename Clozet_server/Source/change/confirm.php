<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		��ȯ��û���� �� ����
		-
		2016.01.06 by Junseok

		1. Request
		request_code=6�ڸ� ��ȯ��û �ڵ�
		confirm=true/false (boolean)


		2. Response
		{
		"confirm_message":��û��� �޽���(success/fail)
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
