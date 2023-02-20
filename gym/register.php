<?php

include("connect.php");

	
	$name=$_POST['name'];
	$login_name=$_POST['lname'];
	$pass=$_POST['pass'];
	$phn=$_POST['phn'];
	$weight=$_POST['weight'];
	$height=$_POST['height'];
	$doj=$_POST['doj'];
	$brk=$_POST['break'];
	$lunch=$_POST['lunch'];
	$din=$_POST['dinner'];
	$due=$_POST['due'];
	$type=$_POST['type'];
	$action=$_POST['action'];

	if($action=="register")
     {
	   $sql="INSERT INTO `Customer`(`name`,`loginName`,`password`,`mobileNumber`,`weight`,`height`,`doj`,`breakfast`,`lunch`,`dinner`,`due`,`type`) VALUES ('".$name."','".$login_name."','".$pass."','".$phn."','".$weight."','".$height."','".$doj."','".$brk."','".$lunch."','".$din."','".$due."','".$type."')";
	   $sqlcon=mysqli_query($con,$sql);
	   if($sqlcon){
		
		   
	               echo "connected";         
	
	 }
	  else
	 {
		 echo "not conn";
	 }
	 }
	
	
?>
