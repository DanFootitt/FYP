<?php
 
class GCM {
 

    function __construct() {
 
    }

    public function send_notification($registatoin_ids, $message) {

        include_once "./config.php";
 
        $url = "https://android.googleapis.com/gcm/send";
 
        $fields = array(
            "registration_ids" => $registatoin_ids,
            "data" => $message,
        );
 
        $headers = array(
            "Authorization: key=" .GOOGLE_API_KEY ,
            "Content-Type: application/json"
        );
  
        $ch = curl_init();
 
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
		curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
		curl_setopt ($ch, CURLOPT_SSL_VERIFYPEER, 0);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

		error_log(json_encode($data));
 
        $result = curl_exec($ch);

        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));

        }
 
        // Close connection
        curl_close($ch);

		error_log($result);
        echo $result;
    }
 
}
 
?>