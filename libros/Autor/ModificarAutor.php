<?php

	//http://10.206.41.160:50/Libreria/libros/valida.php?usu=06013925&pas=1234567
	//include 'conn.php';
	$id = $_REQUEST['id'];
	$nombres = $_REQUEST['nombres'];
	$apellidos = $_REQUEST['apellidos'];
	$pais = $_REQUEST['pais'];
	$correo = $_REQUEST['correo'];


	$cnx = new PDO("mysql:host=localhost; dbname=libreria","root","");
	$res = $cnx->query("UPDATE autor SET apellidos='$apellidos',nombres='$nombres',pais='$pais',correo='$correo' WHERE idautor='$id'");

	if($res == TRUE) {
		echo "Autor Modificado";
	}else {
	    echo "Error";
	}






?>