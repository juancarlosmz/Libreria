<?php
	$HostName = "localhost"; 
	$UserName = "root"; 
	$Password = ""; 
	$dbname="libreria"; 

	$connection = new mysqli($HostName, $UserName, $Password, $dbname);      
	// Check connection 
	if ($connection->connect_error){
	    die("Connection failed: " . $connection->connect_error);
	}else{
		echo "correcto";
	}
?>