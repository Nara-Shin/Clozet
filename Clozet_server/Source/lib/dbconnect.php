<?php

/**
 * Mysql Original 방식
 */

$mysql_hostname = 'localhost';
$mysql_username = 'godeung';
$mysql_password = 'appi2506';
$mysql_database = 'godeung';


//1. DB 연결
$connect = mysql_connect($mysql_hostname, $mysql_username, $mysql_password) or die("MySQL 서버에 연결할 수 없습니다.");
if(!$connect) echo "[연결성공]<br/>"; else echo "[연결성공]<br/>";
mysql_query(" SET NAMES UTF8 ");


//2. DB 선택
mysql_select_db($mysql_database, $connect);

?>