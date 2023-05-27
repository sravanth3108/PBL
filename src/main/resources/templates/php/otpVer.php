<?php

if(isset($_POST["verify"]))
{
    $mail=$_POST['mail'];
    $otp=$_POST['otp'];
    $successMessage = "Hurrah!, Registered Successfully";

    $conn = new mysqli('localhost','root','','pblsignup');

    
    $sql = "UPDATE registration SET verified_at=NOW() WHERE mail='".$mail."' AND otp='".$otp."'";
    $result=mysqli_query($conn,$sql);
    if(mysqli_affected_rows($conn)==0)
    {
        header("Location: otp.php?message=". urlencode("$mail.please verify your otp"));

    }
    header("Location: index.html?message=". urlencode($successMessage));
    exit();
}




?>
