<?php

	//http://10.206.41.160:50/libros/valida.php?usu=06013925&pas=1234567
	//include 'conn.php';
	$id = $_REQUEST['id'];


	$cnx = new PDO("mysql:host=localhost; dbname=libreria","root","");
	//$res = $cnx->query("SELECT * FROM usuario WHERE correro='".$usu."' AND clave='".$pas."'");
	$res = $cnx->query("SELECT idautor,apellidos,nombres,pais,correo FROM autor WHERE idautor='$id'");
	$datos = array();
	foreach ($res as $row) {
		$datos[]=$row;
	}
	echo json_encode($datos);


?>