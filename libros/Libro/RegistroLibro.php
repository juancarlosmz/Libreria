<?php

	//http://10.206.41.160:50/Libreria/libros/valida.php?usu=06013925&pas=1234567
	//include 'conn.php';

	$idlibro = $_REQUEST['idlibro'];
	$titulo = $_REQUEST['titulo'];
	$idautor = $_REQUEST['idautor'];
	$edicion = $_REQUEST['edicion'];
	$editorial = $_REQUEST['editorial'];
	$numpaginas = $_REQUEST['numpaginas'];
	$year = $_REQUEST['year'];
	$estado = $_REQUEST['estado'];

	$cnx = new PDO("mysql:host=localhost; dbname=libreria","root","");
	$res = $cnx->query("INSERT INTO libro(idlibro,titulo,idautor,edicion,editorial,numpaginas,year,estado) VALUES ('$idlibro','$titulo','$idautor','$edicion','$editorial','$numpaginas','$year','$estado')");
	$datos = array();
	if($res == TRUE) {
		echo "Libro Creado";
	}else {
	    echo "Error";
	}




?>