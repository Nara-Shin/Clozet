<?php
	header('Content-Type: text/html; charset=utf-8');

    include "GCMPushMessage.php";

	if($_GET[admin] == "true"){
		$apiKey = "AIzaSyBJufieLDsjAPgtMsiPbiw6VYzqlKoWdLM"; // 관리자
	}else{
		$apiKey = "AIzaSyD7rAhZs67uQ3I9E2obv6HX6x4CQZD1EfI"; // 고객
	}
	
	$devices = array($_GET[regid]);
	$message = $_GET[message];

	$gcpm = new GCMPushMessage($apiKey);
	$gcpm->setDevices($devices);
	$response = $gcpm->send($message);
	
	echo $response;
?>