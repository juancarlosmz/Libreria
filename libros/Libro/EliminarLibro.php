<?php

	//http://10.206.41.160:50/libros/valida.php?usu=06013925&pas=1234567
	//include 'conn.php';
	$idlibro = $_REQUEST['idlibro'];


	$cnx = new PDO("mysql:host=localhost; dbname=libreria","root","");
	//$res = $cnx->query("SELECT * FROM usuario WHERE correro='".$usu."' AND clave='".$pas."'");
	$res = $cnx->query("DELETE FROM libro WHERE idlibro='$idlibro'");
	if($res == TRUE) {
		echo "Libro Eliminado";
	}else {
	    echo "Error";
	}


?>