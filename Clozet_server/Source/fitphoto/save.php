<?
	header('Content-Type: application/json');
	
	include "../lib/dbconnect.php";
	
	/*
		���� ����
		-
		2016.01.06 by Junseok

		1. Request
		member_code=6�ڸ� ȸ���ڵ�
		brand_code=6�ڸ� �귣���ڵ�
		������ ���� �񵿱� ó��

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
