<?
$temp_id = $_POST['id'];
$temp_pw = $_POST['pw'];

$myServer = "localhost,��Ʈ(�⺻������ mssql�� 1433��Ʈ ��)";
$myUser = "���̵�";
$myPass = "��й�ȣ";
$myDB = "DB��";

$db = mssql_connect($myServer, $myUser, $myPass) or die ("���� ���� ����");
mssql_select_db($myDB,$db) or die ("DB ���� ����");



$result=mssql_query("select reg_id from personinfo where id='$temp_id' and pw='$temp_pw'");

$reg_id=mssql_result($result, 0, "reg_id");
echo $reg_id;
	

?>

