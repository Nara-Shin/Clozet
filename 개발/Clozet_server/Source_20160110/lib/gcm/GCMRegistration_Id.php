<?
$temp_id = $_POST['id'];
$temp_pw = $_POST['pw'];
$temp_reg_id = $_POST['reg_id'];

$myServer = "localhost,포트(기본적으로 mssql은 1433씀)";
$myUser = "아이디";
$myPass = "비번";
$myDB = "DB명";

$db = mssql_connect($myServer, $myUser, $myPass) or die ("서버 연결 실패");
mssql_select_db($myDB,$db) or die ("DB 연결 실패");




$result=mssql_query("update personinfo set reg_id = '$temp_reg_id' where id='$temp_id' and pw='$temp_pw'");


echo "Success";
	

?>


