<?php
 
class DB_Functions {
 
    private $db;
 
    function __construct() {
        include_once './db_connect.php';
        $this->db = new DB_Connect();
        $this->db->connect();
    }
 
    // destructor
    function __destruct() {
 
    }
 
    public function storeUser($name, $email, $gcm_regid) {
        $result = mysql_query("INSERT INTO gcm_users(name, email, gcm_regid, created_at) VALUES('$name', '$email', '$gcm_regid', NOW())");
        if ($result) {
            $id = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM gcm_users WHERE id = $id") or die(mysql_error());
            if (mysql_num_rows($result) > 0) {
                return mysql_fetch_array($result);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
 
    public function getAllUsers() {
        $result = mysql_query("select * FROM gcm_users");
        return $result;
    }
 
}
 
?>