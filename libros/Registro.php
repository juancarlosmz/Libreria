<?php

	//http://10.206.41.160:50/Libreria/libros/valida.php?usu=06013925&pas=1234567
	//include 'conn.php';
	$dni = $_REQUEST['dni'];
	$apater = $_REQUEST['apater'];
	$amater = $_REQUEST['amater'];
	$nombres = $_REQUEST['nombres'];
	$correo = $_REQUEST['correo'];
	$cel = $_REQUEST['cel'];
	$clave = $_REQUEST['clave'];

	$cnx = new PDO("mysql:host=localhost; dbname=libreria","root","");
	$res = $cnx->query("INSERT INTO usuario(idusuario,apater,amater,nombres,correo,cel,clave) 
						VALUES ('$dni','$apater','$amater','$nombres','$correo','$cel','$clave')";
	$datos = array();
	foreach ($res as $row) {
		$datos[]=$row;
	}
	echo json_encode($datos);

?>