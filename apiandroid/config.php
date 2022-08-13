<?php
$con = mysqli_connect("localhost","root","","api");

if(!$con){
    echo mysqli_error($con);
}
?>