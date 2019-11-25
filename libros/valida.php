<?php

	//http://10.206.41.160:50/Libreria/libros/valida.php?usu=06013925&pas=1234567
	//include 'conn.php';
	$usu = $_REQUEST['usu'];
	$pas = $_REQUEST['pas'];

	$cnx = new PDO("mysql:host=localhost; dbname=libreria","root","");
	//$res = $cnx->query("SELECT * FROM usuario WHERE correro='".$usu."' AND clave='".$pas."'");
	$res = $cnx->query("SELECT * FROM usuario WHERE idusuario='$usu' AND clave='$pas'");
	$datos = array();
	foreach ($res as $row) {
		$datos[]=$row;
	}
	echo json_encode($datos);

/*
	if(!empty($_POST['usu']) && !empty($_POST['pass'])) {
	    $usuarioEmpleado=$_POST['usu'];
	    $claveEmpleado=$_POST['pass'];

	    $consulta =mysqli_query($con, "SELECT * FROM usuario WHERE correro='".$usuarioEmpleado."' AND clave='".$claveEmpleado."'");

	    $numeroFilas=mysqli_num_rows($consulta);
	    if($numeroFilas!=0)

	    {
	    while($fila=mysqli_fetch_assoc($consulta))
	    {
	    $bdUsuarioEmpleado=$fila['usu'];
	    $bdClaveEmpleado=$fila['pass'];
	    }

	    if($usuarioEmpleado == $bdUsuarioEmpleado && $claveEmpleado == $bdClaveEmpleado){
	        $_SESSION['session_username']=$usuarioEmpleado;
	        header("Location: introEmpleado.php");
	    }
	    } else {

	 $mensaje =  "Nombre de usuario o contraseña invalida!";
	    }

	} else {
	    $mensaje = "Todos los campos son requeridos!";
	}
*/


?>