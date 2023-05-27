<?php

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;




require 'vendor/autoload.php';


if(isset($_POST["signup"]))
{
    $mail=$_POST['mail'];
    $usrName=$_POST['usrName'];
    $fname=$_POST['fname'];
    $lname=$_POST['lname'];
    $password= $_POST['password'];
    $mNumber=$_POST['mNumber'];
   
    $successMessage = "Hurrah! ,otp sent to ";
    $unsuccessMessage = "This mail id is using for other account, try other mail";

    $conn = new mysqli('localhost', 'root', '', 'pblsignup');
    $checkEmailQuery = "SELECT * FROM registration WHERE mail = '" . $mail . "'";
    $checkEmailResult = mysqli_query($conn, $checkEmailQuery);

    if (mysqli_num_rows($checkEmailResult) > 0) {
        $user = mysqli_fetch_object($checkEmailResult);
        if ($user->verified_at != null) {
            $errorMessage = "This mail id is already associated with another account. Please try a different email.";
            header("Location: index.html?message=" . urlencode($mail . "  already exists"));
            exit();
        } else {
            $errorMessage = "OTP not verified";
            header("Location: otp.php?message=" . urlencode($fname . " please verify your OTP"));
            exit();
        }
    }
    
    
    $email= new PHPMailer(true);
    try

    {
        $email->SMTPDebug = 0;


        $email->isSMTP();

        $email->Host = "smtp.gmail.com";

        $email->SMTPAuth = true;

        $email->Username ="martinsmec6@gmail.com";

        $email->Password ="fbngquoyqjaivrey";

        $email->SMTPSecure  = PHPMailer::ENCRYPTION_STARTTLS;


        $email->Port=587;

        $email->setFrom('martinsmec6@gmail.com','NWMSU PBL');

        $email->addAddress($mail, $fname);

        $email->isHTML(true);   

        $otp= substr(number_format(time() * rand(), 0, '',''),0,6);

        $email->Subject = 'Email Verification for PBL';

        $email->Body    = 'Hello ' .$fname.'<p> your 6 digit <b>OTP</b> is <b style="font-family: ;">' . $otp . '</b></p><br><br><br><br><br><br><p></p><br><br>  <footer>
        <img src="https://www.nwmissouri.edu/marketing/images/design/logos/N-Horiz-Full.png" width="100%" height="auto">
    </footer>';


        $email->send();

        $encrypted_password=password_hash($password,PASSWORD_DEFAULT);


        $conn = new mysqli('localhost','root','','pblsignup');


        $sql= "INSERT  INTO registration(mail,usrName,fname,lname,password,mNumber,otp,verified_at) VALUES('".$mail."','".$usrName."','".$fname."','".$lname."','".$encrypted_password."','".$mNumber."','".$otp."',NULL)";

        mysqli_query($conn,$sql);

        header("Location: otp.php?message=". urlencode($successMessage) .$mail);
        exit();
    }
    catch(Exception $e)
    {
        echo "Mail was not sent. Mailer Error: {$email->ErrorInfo}";
    }


        

}














//DB Connection



?>