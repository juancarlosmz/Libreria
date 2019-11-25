<?php

	//http://10.206.41.160:50/libros/valida.php?usu=06013925&pas=1234567
	//include 'conn.php';
	$cod = $_REQUEST['cod'];


	$cnx = new PDO("mysql:host=localhost; dbname=libreria","root","");
	//$res = $cnx->query("SELECT * FROM usuario WHERE correro='".$usu."' AND clave='".$pas."'");
	$res = $cnx->query("SELECT apater,amater,nombres,correo,cel FROM usuario WHERE idusuario='$cod'");
	$datos = array();
	foreach ($res as $row) {
		$datos[]=$row;
	}
	echo json_encode($datos);


?>