<?php
    if($_SERVER['REQUEST_METHOD']=="POST"){
        $NameMeals=isset($_POST['NameMeals'])?$_POST['NameMeals']:"";
        $TypeMeals=isset($_POST['TypeMeals'])?$_POST['TypeMeals']:"";
        $countryMeals=isset($_POST['countryMeals'])?$_POST['countryMeals']:"";
  

        $server_name="localhost";
        $user_name="root";
        $password="";
        $dbname="meals";

        $conn=new mysqli($server_name,$user_name,$password,$dbname);
        if($conn->connect_error){
            die("Connection Failed".$conn->connect_error);
        }
	//$sqlID= "SELECT MAX(`IDMeals`) FROM `meales` WHERE 1"
		
		//$result = $conn->query($sqlID);
		$data = "";
			if ($result->num_rows > 0) {
		
			$data= $result->fetch_assoc()["MAX(`IDMeals`)"] ;
		}
        $sql="INSERT INTO `meales`(`IDMeals`, `NameMeals`, `TypeMeals`, `countryMeals`) VALUES (null,NameMeals,TypeMeals,countryMeals)";
        if($conn->query($sql)==TRUE){
            echo "New record created successfully";
        }
        else{
            echo "Error: ".$sql."<br>".$conn->error;
        }
        $conn->close();
    }
?>