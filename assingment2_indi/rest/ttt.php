<?php
    if($_SERVER['REQUEST_METHOD']=="GET"){
    }

        $server_name="localhost";
        $user_name="root";
        $password="";
        $dbname="meals";

        $conn=new mysqli($server_name,$user_name,$password,$dbname);
        if($conn->connect_error){
            die("Connection Failed".$conn->connect_error);
        }
	$sqlID= "SELECT MAX(`IDMeals`) FROM `meales` WHERE 1";
	
		$result = $conn->query($sqlID);
		$data = "";
			if ($result->num_rows > 0) {
		
		
				$data= $result->fetch_assoc()["MAX(`IDMeals`)"] ;
			
    	 if($conn->query($sqlID)==TRUE){
            echo $data;
        }
        else{
            echo "Error:";
        }
        $conn->close();
    }

?>