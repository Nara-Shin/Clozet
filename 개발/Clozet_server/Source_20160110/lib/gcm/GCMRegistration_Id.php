<?
$temp_id = $_POST['id'];
$temp_pw = $_POST['pw'];
$temp_reg_id = $_POST['reg_id'];

$myServer = "localhost,��Ʈ(�⺻������ mssql�� 1433��)";
$myUser = "���̵�";
$myPass = "���";
$myDB = "DB��";

$db = mssql_connect($myServer, $myUser, $myPass) or die ("���� ���� ����");
mssql_select_db($myDB,$db) or die ("DB ���� ����");




$result=mssql_query("update personinfo set reg_id = '$temp_reg_id' where id='$temp_id' and pw='$temp_pw'");


echo "Success";
	

?>


