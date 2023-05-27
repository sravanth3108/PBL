<?php
$otpMsg = "Please verify your email by entering the OTP";

if(isset($_POST["loginBtn"])) {
    $mail = $_POST['mail'];
    $password = $_POST['password'];
    $successMessage = "Hurrah! Login Successful";

    $conn = new mysqli('localhost','root','','pblsignup');

    $sql = "SELECT * FROM registration WHERE mail='".$mail."' ";
    $result = mysqli_query($conn, $sql);

    if(mysqli_num_rows($result) == 0) {
        header("Location: index.html?message=". urlencode("Email not found, please sign up"));
        exit();
    }

    $user = mysqli_fetch_assoc($result); // Fetch the user row as an associative array

    if(!password_verify($password, $user['password'])) {
        header("Location: index.html?message=". urlencode("Invalid password"));
        exit();
    }

    if($user['verified_at'] == null) {
        header("Location: otp.php?message=". urlencode($otpMsg));
        exit();
    }

    // Login successful
    header("Location: login.html?message=". urlencode("Hello, ".$mail));
    exit();
}




?>