<?php


	//http://192.168.0.13:50/Libreria/libros/CambioContra.php?usu=01010101&cantigua=ttt&cnueva=tetete&cconfirmar=tetete

	$usu = $_REQUEST['usu'];
	$cantigua = $_REQUEST['cantigua'];
	$cnueva = $_REQUEST['cnueva'];
	$cconfirmar = $_REQUEST['cconfirmar'];

	$cnx = new PDO("mysql:host=localhost; dbname=libreria","root","");

	if($cnueva == $cconfirmar){
		$res = $cnx->query("UPDATE usuario SET clave='$cnueva' WHERE clave='$cantigua' AND idusuario='$usu'  ");
		if($res == TRUE) {
			echo "Usuario modificado";
		}else {
		    echo "Error";
		}
	}else{
		echo "Usuario erroneo";
	}



?>