<?
	include "/www/clozet/lib/dbconnect.php";
	
	/*
		응답없을 시 교환 재요청
		-
		2016.01.06 by Junseok

		1. Request
		-

		2. Response
		-
	*/

	// Query 1
	$query = " select 'complete' as col from dual ";

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)) 
	{
	   echo $row['col'].'<br/>';
	}

?>
