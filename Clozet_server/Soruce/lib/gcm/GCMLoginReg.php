<?
$temp_id = $_POST['id'];
$temp_pw = $_POST['pw'];

$myServer = "localhost,포트(기본적으로 mssql은 1433포트 씀)";
$myUser = "아이디";
$myPass = "비밀번호";
$myDB = "DB명";

$db = mssql_connect($myServer, $myUser, $myPass) or die ("서버 연결 실패");
mssql_select_db($myDB,$db) or die ("DB 연결 실패");



$result=mssql_query("select reg_id from personinfo where id='$temp_id' and pw='$temp_pw'");

$reg_id=mssql_result($result, 0, "reg_id");
echo $reg_id;
	

?>

