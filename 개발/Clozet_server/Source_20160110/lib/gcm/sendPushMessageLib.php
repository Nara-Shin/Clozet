<?php

    include "GCMPushMessage.php";

	if($_GET[admin] == "true"){
		$apiKey = "AIzaSyBJufieLDsjAPgtMsiPbiw6VYzqlKoWdLM"; // ������
	}else{
		$apiKey = "AIzaSyD7rAhZs67uQ3I9E2obv6HX6x4CQZD1EfI"; // ��
	}

	$gcpm = new GCMPushMessage($apiKey);
	$gcpm->setDevices($devices);
	$response = $gcpm->send($message);
	
?>