<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>注册</title>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<link rel="stylesheet" type="text/css" href="css/register.css">
<!--     <link rel="icon" href="img/cropped-动漫-43-32x32.jpg" sizes="32x32">
    <link rel="icon" href="img/cropped-动漫-43-192x192.jpg" sizes="192x192"> -->
    <link rel="icon" href="img/club_icon.png" sizes="576x576">
<!-- 	<meta name="viewport" content="width=device-width, initial-scale=1"> -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<!--     <link rel="stylesheet" href="bootstrap-4.3.1/bootstrap.min.css"> -->
<!--     <script src="bootstrap-4.3.1/jquery.min.js"></script>
    <script src="bootstrap-4.3.1/popper.min.js"></script>
    <script src="bootstrap-4.3.1/bootstrap.min.js"></script> -->
    <script src="js/ban.js"></script>
</head>
<body>

	<canvas class="canvas"></canvas>
  <!--  因为canvas长是window.innerWidth; canvas高为 window.innerHeight;（即刚打开时页面的长高）页面比例增大不影响canvas布局，页面比例缩小会有影响 -->
    <div id="container">
        <div class="top"><span>welcome to Software sharing</span></div>		
        <div class="form">
            <div class="formTop">
                <span>please register with your Usename and password</span>
            </div>
            <form name="register_form" action="login"  method="post">


           <!-- --用户名--------------用户名-------------用户名---------用户名------------用户名-------->
            <div class="usename">
                <div class="usenameImg"><img src="images/usenameImg.png"></div>
                <div class="usenameText">
                    <input id="userName" type="text" name="usenameText" placeholder="Usename"  maxlength="8" >
                    <span id="error_tip" style="color:red;font-size: 13px"></span>
                </div>
            </div>
               <!-- --邮箱--------------邮箱----------邮箱---------邮箱-----------邮箱----------------->
    <!-- 		<div class="email">
                <div class="emailImg"><img src="images/emailImg.png"></div>
                <div class="emailText">
                    <input type="text" name="emailText" placeholder="email">
                </div>
            </div> -->
            <!-- 密码 -------------- 密码 ---------- 密码 -------- 密码 ------- 密码 ----- 密码 -->
            <div class="password">
                <div class="passwordImg"><img src="images/passwordImg.png"></div>
                <div class="passwordText">
                    <input id="userPasswd" type="password" name="passwordText" placeholder="Password">
                    <span id = "error_pwd" style="color:red; font-size: 13px"></span>
                    <!-- <input width=50px type="text" style= "color: red; border-style: solid; border-width:0" name="explain1" readonly/> -->
                </div>
            </div>
            <!-- 确认密码 --------- 确认密码 ------- 确认密码 ------- 确认密码 --------- 确认密码 ------->
            <div class="password">
                <div class="passwordImg"><img src="images/passwordImg.png"></div>
                <div class="passwordText">
                    <input id="confirmUserPasswd" type="password" name="confirmPasswordText" placeholder="Confirm password">
                    <span id="error_Cpwd" style="color:red; font-size: 13px"></span>
                    <!-- <input id="demo" width=50px type="text" style= "color: red; border-style: solid; border-width:0" name="explain2" readonly/> -->

                </div>
            </div>
         <!--    <div class="remember_Me">
                <input type="checkbox" name="remember Me">
                <span>Remember Me</span>
            </div> -->

            <div class="button">

                <input class="submit" id="my_button" value="register" style="text-align:center;"/>
            </div>
    </form>

        </div>
    </div>
 <!--    -------------- js内怜------------------ -->
<script>
	var baseIP = "software.yongkj.cn";
	
   // function $(id){
   //           return document.getElementById(id);
   //       }
 
          document.getElementById("my_button").onclick = function(){
          	
            var boo=document.getElementById('userName').value;
            var pwd=document.getElementById('userPasswd').value;
            var Cpwd = document.getElementById('confirmUserPasswd').value;
            
            if(boo){
            	if(pwd){
            		if (pwd==Cpwd) {
// 		              	  alert("注册成功");
                        getUserInfo();
		                  return true;
		             }else{
		                  document.getElementById("error_Cpwd").innerHTML = "两次密码不一致";
		                  return false;
		             }
		         }else{
		         	// alert("用户名违法");
		         	document.getElementById("error_pwd").innerHTML = "密码为空";

		         	return false;
		         	}
            	}else{

            		document.getElementById("error_tip").innerHTML = "用户名非法";
            		return false;
            		
            	}
		              
        }
//         function getUserInfo() {
//             var userNameValue = document.getElementById("userName").value;
//             var confirmUserPasswdValue = document.getElementById("confirmUserPasswd").value;
//             var url = "http://m.yongkj.cn/software/login?";
//             if(userNameValue != "" && confirmUserPasswdValue != "") {
//                 url += "userName=" + userNameValue + "&";
//                 url += "userPasswd=" + confirmUserPasswdValue;
//             }
//             console.log(url);
//             window.location.href=url;
//         }
          function getUserInfo() {
            var userNameValue = document.getElementById("userName").value;
            var userPasswdValue = document.getElementById("userPasswd").value;
            var url = "http://" + baseIP + "/software/register?";
            if(userNameValue != "" && userPasswdValue != "") {
                url += "userName=" + userNameValue + "&";
                url += "userPasswd=" + userPasswdValue;
            }
            console.log(url);
            window.location.href=url;
        }
  
</script>


</body>
</html>