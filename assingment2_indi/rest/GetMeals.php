<?php
    if($_SERVER['REQUEST_METHOD']=="GET") {
        $IDMeals = 0;
        if (isset($_GET['IDMeals'])) {
            $IDMeals = $_GET['IDMeals'];
        }
    $server_name = "localhost";
    $user_name = "root";
    $password = "";
    $dbname = "meals";
    $conn = new mysqli($server_name, $user_name, $password, $dbname);
    if ($conn->connect_error) {
        die("Connection Failed" . $conn->connect_error);
    }
    if ($IDMeals != 0) {
        $sql = "select * from meales where IDMeals ='" . $IDMeals . "'";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            $data = $result->fetch_assoc();
            $myObject= new \stdClass();
            $myObject->IDMeals=(int)$data['IDMeals'];
            $myObject->NameMeals=$data['NameMeals'];
            $myObject->TypeMeals=$data['TypeMeals'];
            $myObject->countryMeals=$data['countryMeals'];
     
            $myJSON = json_encode($myObject);
            echo $myJSON;
        } else {
            echo "Not found data";
        }
    } else {
        $sql = "select * from meales";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            $data=array();
            while(($row=$result->fetch_assoc())!=NULL){
                $myObject= new \stdClass();
                $myObject->IDMeals=(int)$data['IDMeals'];
            $myObject->NameMeals=$data['NameMeals'];
            $myObject->TypeMeals=$data['TypeMeals'];
            $myObject->countryMeals=$data['countryMeals'];
     
                $data[]=$myObject;
            }
            $myJSON = json_encode($data);
            echo $myJSON;
        } else {
            echo "Not found data";
        }
    }
}else{
    echo "Error: Method Request";
}
$conn->close();
?>