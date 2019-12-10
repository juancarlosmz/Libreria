<?php

	//http://10.206.41.160:50/libros/valida.php?usu=06013925&pas=1234567
	//include 'conn.php';
	$idlibro = $_REQUEST['idlibro'];


	$cnx = new PDO("mysql:host=localhost; dbname=libreria","root","");
	//$res = $cnx->query("SELECT * FROM usuario WHERE correro='".$usu."' AND clave='".$pas."'");
	$res = $cnx->query("SELECT idlibro,titulo,idautor,edicion,editorial,numpaginas,year,estado FROM libro WHERE idlibro='$idlibro'");
	$datos = array();
	foreach ($res as $row) {
		$datos[]=$row;
	}
	echo json_encode($datos);


?>