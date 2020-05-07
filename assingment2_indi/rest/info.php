<?php

	
	
	$cat = "";
	if(isset($_GET['cat'])){
		$cat = $_GET['cat'];
	}
	if(!empty($cat)){
		$server_name = "localhost";
		$username = "root";
		$password = "";
		$dbname = "meals";
		
		$conn = new mysqli($server_name, $username, $password, $dbname);
		if ($conn->connect_error) {
			die("Connection failed: " . $conn->connect_error);
		} 
		$sql = "select * from meales where TypeMeals= '" . $cat . "'" ;
		
		$result = $conn->query($sql);
		$data = "";
		if ($result->num_rows > 0) {
		
			while($row = $result->fetch_assoc()) {
				$data.= $row["NameMeals"] . ",";
			}
			echo $data;
		} else {
			echo "0 results";
		}
		
		$conn->close();

		
		
		
	}
	
	




?>