<?php
	header('Content-Type: text/html; charset=utf-8');

    include "GCMPushMessage.php";
	
	$apiKey = "AIzaSyD7rAhZs67uQ3I9E2obv6HX6x4CQZD1EfI"; // ��
	//$apiKey = "AIzaSyBJufieLDsjAPgtMsiPbiw6VYzqlKoWdLM"; // ������

	$devices = array('cLIUNC9dT6k:APA91bEQqODT4GNxxlu8_3R6RT_QH44UudkGg38sGl27kN02wjTRqp5XzVxkpsIEIrZHyba3yDGAzs2ggn6U9w3zY5LcdkDSxuEo_xlpnb3ZRIdTXhrmqDmr-YveRAIlkrgj6OJOvlSo');
	$message = "The message to send";

	$gcpm = new GCMPushMessage($apiKey);
	$gcpm->setDevices($devices);
	$response = $gcpm->send($message);
	
	echo $response;
?>