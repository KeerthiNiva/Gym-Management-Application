<?php

include("connect.php");

	
	$id=$_POST['id'];
	$in=$_POST['in'];
	$out=$_POST['out'];
	$date=$_POST['date'];
	$action=$_POST['action'];

	if($action=="update")
     {
	 
	 
		   
	     $sql1="INSERT INTO `atten`(`in`, `out`, `date`, `id`) VALUES 
		 ('".$in."','".$out."','".$date."','".$id."')";
	     $sqlcon1=mysqli_query($con,$sql1);
	      if($sqlcon1){
		   
	                       
							    echo "update";
							
							
	
	 }
	  else
	 {
		 echo "not conn";
	 }
	 }
	 else
	 {
		$sql1="select * from atten where `id` = '".$id."'";
	     $sqlcon1=mysqli_query($con,$sql1);
	      if($sqlcon1){
		  
							if(mysqli_num_rows($sqlcon1))
							{
							while($rows=mysqli_fetch_assoc($sqlcon1))
							{
								$outview=$rows;
								$status=array('status'=>'view');
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
