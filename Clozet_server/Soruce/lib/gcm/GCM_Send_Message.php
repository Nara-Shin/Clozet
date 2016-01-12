<?
$API_key = "AIzaSyBLt7-7hs39ksg09qXwVIbDg5D8b6pjKos";
$headers = array(
 'Content-Type:application/json',
 'Authorization:key=AIzaSyBLt7-7hs39ksg09qXwVIbDg5D8b6pjKos'
);

$temp_msg = "push 메시지 테스트입니다.  push";
//$reg_id = "APA91bGU1q5LPh2tGWhGwap9O1p8DPEIWZMEuVi7NSzJNcX2GedwxfnUixvOxxw9_2MX5xcyt7K87piF6gH2ELSmxsSciZhjw6zNetibfvirL8XziTVAyIApLJidhfePh397oDZInGAv3pJwsdqgOEZUENgS7xDLaw";

$arr   = array();
$arr['data'] = array();
$arr['data']['message'] = $temp_msg; 
$arr['registration_ids'] = array();

	require_once("/home/mrts/URX_NEW/lib/DB.inc");
	date_default_timezone_set('Asia/Seoul');
			$Res_Miles = mysql_query(" SELECT RegID from PanelUP WHERE length(RegID) > 0 ");
			for( $k = 0; $row = mysql_fetch_assoc($Res_Miles); ++$k ) {
				$arr['registration_ids'][$k] = $row['RegID'];
			}

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL,    'https://android.googleapis.com/gcm/send');
curl_setopt($ch, CURLOPT_HTTPHEADER,  $headers);
curl_setopt($ch, CURLOPT_POST,    true);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
curl_setopt($ch, CURLOPT_POSTFIELDS,json_encode($arr));

$response = curl_exec($ch);
echo $response;
curl_close($ch);



?>