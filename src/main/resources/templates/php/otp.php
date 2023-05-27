<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
</head>
<body>
    
    <header>
        <h1>Time to verify</h1>
    </header>
    <main>
        
        <form id="login_form" class="form_class" action="otpVer.php" method="post">
            <div class="form_div">
                <p id="success-message"></p><br>
                <label id="otp">otp</label>
                <span id="togglePassword" onclick="togglePasswordVisibility()">
                  <i class="fas fa-eye"></i>
                </span><br>
                
                <input class="field_class" name="mail" type="text" placeholder="Please Enter mail again" autofocus required>
                <input id="otpIP" class="field_class" name="otp" type="password" placeholder="6 digit otp" required>
                
    
                <button class="submit_class" name="verify" type="submit" form="login_form">verify</button>
                <button class="submit_class"  type="submit" form="login_form" onclick="return validarLogin()">Resend</button>
                
            </div>
        
            
        </form>
        
    </main>
    
    <footer>
        <p>Developed by Group2 <a href="#">NWMSU&trade;</a></p>
    </footer>
    <style>
        * {
    padding: 0px;
    margin: 0px;
}
#success-message
{
    text-transform:lowercase;
    margin-left: 2.7cm;
    color: #006A4E;
}

#otp
{
    margin-left: 220px;
}
#otpIP
{
    width: 32ch;
    margin-left: 125px;
}
body {
    background-color: #006A4E;
    color: white;
}
header {
    background-color: black;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 15vh;
    box-shadow: 5px 5px 10px rgb(0,0,0,0.3);
}
h1 {
    letter-spacing: 1.5vw;
    font-family: 'system-ui';
    text-transform: uppercase;
    text-align: center;
}

main {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 75vh;
    width: 100%;
    background: url(https://th.bing.com/th/id/R.c68d591d29885114f3ba7e0a2cdbf831?rik=jJ4o3LdNTmXXKA&riu=http%3a%2f%2fwww.nwmissouri.edu%2fmarketing%2fimages%2fdesign%2flogos%2fN-Horiz-G.png&ehk=tgHhZ%2ftigCAe0Pe4JMf%2f17BWEKc48P6%2f15ikBE68Z7E%3d&risl=&pid=ImgRaw&r=0) no-repeat center center;
    background-size:contain;
}
.form_class {

    margin-left: 6cm;
    width: 500px;
    padding: 40px;
    border-radius: 8px;
    background-color: white;
    border:1px solid #000;
    
    font-family: 'system-ui';
    box-shadow: 5px 5px 10px rgb(0,0,0,0.3);
    
}
.form_div {
    text-transform: uppercase;
    color: black;
}
.form_div > label {
    letter-spacing: 3px;
    font-size: 1rem;
}
.info_div {
    text-align: center;
    margin-top: 20px;
}
.info_div {
    letter-spacing: 1px;
}
.field_class {
    width: 100%;
    border-radius: 6px;
    border-style: solid;
    border-width: 1px;
    padding: 5px 0px;
    text-indent: 6px;
    margin-top: 10px;
    margin-bottom: 20px;
    font-family: 'system-ui';
    font-size: 0.9rem;
    letter-spacing: 2px;
}
.submit_class {

    border-style: none;
    border-radius: 5px;
    background-color:  #006A4E;
    color:white;
    padding: 8px 20px;
    font-family: 'system-ui';
    text-transform: uppercase;
    letter-spacing: .8px;
    display: block;
    margin: auto;
    margin-top: 10px;
    box-shadow: 2px 4px 5px rgb(0,0,0,0.4);
    cursor: pointer;
}
.submit_class:hover
{
background-color: white;
color: black;
}
footer {
    height: 10vh;
    background-color: black;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: -5px -5px 10px rgb(0,0,0,0.3);
}
footer > p {
    text-align: center;
    font-family: 'system-ui';
    letter-spacing: 3px;
}
footer > p > a {
    text-decoration: none;
    color: white;
    font-weight: bold;
}
    </style>
    <script>
        // const validarLogin = () => {
        // alert('Teste');
// }
    </script>
    <script>

function togglePasswordVisibility() {
  var passwordInput = document.getElementById("otpIP");
  var toggleIcon = document.getElementById("togglePassword");

  if (passwordInput.type === "password") {
    passwordInput.type = "text";
    toggleIcon.innerHTML = '<i class="fas fa-eye-slash"></i>';
  } else {
    passwordInput.type = "password";
    toggleIcon.innerHTML = '<i class="fas fa-eye"></i>';
  }
}

        // Retrieve the success message from the URL query parameter
        var urlParams = new URLSearchParams(window.location.search);
        var message = urlParams.get('message');
    
        // Display the success message on the page
        if (message) {
          var successMessageElement = document.getElementById('success-message');
          successMessageElement.textContent = message;
        }
      </script>
    
      
      
</body>
