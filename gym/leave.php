<?php

include("connect.php");

	
	$id=$_POST['id'];
	$amt=$_POST['amt'];
	$date=$_POST['date'];
	$status=$_POST['status'];
	$name=$_POST['name'];
	$action=$_POST['action'];

	if($action=="fees")
     {
	   $sql="INSERT INTO `fee`(`id`,`amount`,`date`,`status`,`name`) VALUES ('".$id."','".$amt."','".$date."','".$status."','".$name."')";
	   $sqlcon=mysqli_query($con,$sql);
	   if($sqlcon){
		
		   
	               echo "connected";         
	
	 }
	  else
	 {
		 echo "not conn";
	 }
	 }
	  else if($action=="one"){
	     $sql="select * from fee where id='".$id."'";
	   $sqlcon=mysqli_query($con,$sql);
	   if($sqlcon){
		
		   
	             
	                       if(mysqli_num_rows($sqlcon))
							{
							while($rows=mysqli_fetch_assoc($sqlcon))
							{
								$outview=$rows;
								$status=array('status'=>'inserted');
									$output[]=array_merge($status,$outview);
							}
							echo json_encode($output); 
							}
							else
							{
							    echo "no data";
							}
							 
	
	 }
	  else
	 {
		 echo "not conn";
	 }
	 }
	 else{
	     $sql="select * from fee";
	   $sqlcon=mysqli_query($con,$sql);
	   if($sqlcon){
		
		   
	             
	                       if(mysqli_num_rows($sqlcon))
							{
							while($rows=mysqli_fetch_assoc($sqlcon))
							{
								$outview=$rows;
								$status=array('status'=>'inserted');
									$output[]=array_merge($status,$outview);
							}
							echo json_encode($output); 
							}
							else
							{
							    echo "no data";
							}
							 
	
	 }
	  else
	 {
		 echo "not conn";
	 }
	 }
	 
	
	
?>
