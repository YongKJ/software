<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>登录</title>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.2,minimum-scale=1.2,maximum-scale=1.2,user-scalable=no">
	<link rel="stylesheet" type="text/css" href="css/index.css">
<!--     <link rel="icon" href="img/cropped-动漫-43-32x32.jpg" sizes="32x32">
    <link rel="icon" href="img/cropped-动漫-43-192x192.jpg" sizes="192x192"> -->
	<link rel="icon" href="img/club_icon.png" sizes="576x576">
    <!--<meta name="viewport" content="width=device-width, initial-scale=1.2">-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <script src="js/jquery.min.js"></script>
    <script src="js/ban.js"></script>
</head>
<body>

	<canvas class="canvas"></canvas>
  <!--  因为canvas长是window.innerWidth; canvas高为 window.innerHeight;（即刚打开时页面的长高）页面比例增大不影响canvas布局，页面比例缩小会有影响 -->
 

	<div id="container">
		<div class="top"><span>welcome to SHARING</span></div>
		
		<div class="form" >
         <form role="form" class="form-horizontal" οnsubmit="return Check()">
			<div class="formTop">
				<span>please login with your Usename and Password</span>
			</div>
			
           
			<div class="usename">
				<div class="usenameImg"><img src="images/usenameImg.png"></div>
				<div class="usenameText">
			
					<input id="userName" class="form-control" type="text" name="usenameText" placeholder="Usename" required="required">
					<!-- <input class="submit" type="submit" id="my_button" value="提交" /> -->
				   
				</div>
			</div>

     <!--  --------------密码-----------------密码----------密码----------密码-------- -->
			<div class="password">
				<div class="passwordImg"><img src="images/passwordImg.png"></div>
				<div class="passwordText">
					<input id="userPasswd" type="password" class="form-control" name="passwordText" placeholder="Password" required="required">
				</div>
			</div>
			<!-- ----------------验证码------------------- -->
			<div class="Code" style="">
					<input type="text" id="code_input" placeholder="请输入验证码">
					<span id="demo"></span>
					<div class="usenameText">
					<div id="v_container" style="width: 100px;height: 40px;"></div>
					</div>
					<div class="remember_Me">
				<!-- 		--------记住我---------- -->
            	<!-- <input type="checkbox" name="remember Me">
            	<span>Remember Me</span> -->
            </div>

			</div>

          <!--   <div class="remember_Me">
            	<input type="checkbox" name="remember Me">
            	<span>Remember Me</span>
            </div>
 -->
			

            <div class="button" style="margin-top:18px;">
<!--             	<input class="submit" id="my_button" value="login" /> -->
            	<button id="my_button" class="register"><a href="#">login</a></button>
            	<!-- <a href="./register.html">register</a> -->
            </div>
            </form>
		</div>
	</div>
</body>

	<script src="js/gVerify.js"></script>
	<script>
		var baseIP = "software.yongkj.cn";
         // function $(id){
         //      return document.getElementById(id);
         //  }
        var verifyCode = new GVerify("v_container");
        document.getElementById("my_button").onclick = function(){
            var res = verifyCode.validate(document.getElementById("code_input").value);
            var boo=document.getElementById('userName').value;
            var pwd=document.getElementById('userPasswd').value;
            
        if (boo) {
             if (pwd) {
              	  if(res){
//               	  	alert("登陆成功");
//                     getUserInfo();
              	  	return true;
              	  		 }else{
              	  		     document.getElementById("demo").innerHTML = "请再输入验证码";
              	  		     return false; 
              	  		 }
             }else{
                  alert('密码非法');
                  return false;
             	  }
		}else{
			alert("用户名非法");
		}

          
        }
        
        
        function getUserInfo() {
            var userNameValue = document.getElementById("userName").value;
            var confirmUserPasswdValue = document.getElementById("confirmUserPasswd").value;
            var url = "http://" + baseIP + "/software/login?";
            if(userNameValue != "" && confirmUserPasswdValue != "") {
                url += "userName=" + userNameValue + "&";
                url += "userPasswd=" + confirmUserPasswdValue;
            }
            console.log(url);
            window.location.href=url;
        }
        
        
    </script>
</html>