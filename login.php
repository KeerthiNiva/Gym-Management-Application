<?php

include("connect.php");

	

	$login=$_POST['name'];
	$pass=$_POST['pass'];
	$action=$_POST['action'];

	if($action=="login")
     {
	 
	 
		   
	     $sql1="select * from `Customer` where `loginName` = '".$login."'";
	     $sqlcon1=mysqli_query($con,$sql1);
	      if($sqlcon1){
		   
	                       if(mysqli_num_rows($sqlcon1))
							{
							while($rows=mysqli_fetch_assoc($sqlcon1))
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
		 echo "id not fetched";
	 }
	 }
	 else
	 {
		 echo "not inserted";
	 }
	 
	 
	
		   
	
	 				
 
?>
