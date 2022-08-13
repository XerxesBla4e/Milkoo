<?php

include("config.php");

$name = $_POST['name'];
$email = $_POST['email'];
$password = $_POST['password'];

$query = mysqli_query($con, "INSERT INTO staff(name, email, password) VALUES ('$name','$email','$password')");

if($query)
{
    $json = array("response" => 'success',"status" => 0, "message" => "Submitted Successfully");
}else{
    $json = array("response" => 'error',"status" => 1, "message" => "Error");
}

header('Content-type : application/json');
echo json_encode($json);
?>