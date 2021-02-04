<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>页面跳转</title>
  <meta charset="utf-8">
<!--   <meta name="viewport" content="width=device-width, initial-scale=1"> -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
  <meta http-equiv="refresh" content="5; url=${skipUrl}">
<!--   <link rel="icon" href="img/cropped-动漫-43-32x32.jpg" sizes="32x32">
  <link rel="icon" href="img/cropped-动漫-43-192x192.jpg" sizes="192x192"> -->
  <link rel="icon" href="img/club_icon.png" sizes="576x576">
  <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>
  <script src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<!--   <link rel="stylesheet" href="bootstrap-4.3.1/bootstrap.min.css">
  <script src="bootstrap-4.3.1/jquery.min.js"></script>
  <script src="bootstrap-4.3.1/popper.min.js"></script>
  <script src="bootstrap-4.3.1/bootstrap.min.js"></script> -->
<!--   <style type="text/css">body{margin-top: 60px;margin-bottom: 60px;}</style> -->
</head>
<body>
   <div class="container-fluid">
        <div class="row pt-0">
           <div class="col-md-3"></div>
           <div class="col-md-6 col-sm-12 bg-light pt-4 pb-4 text-center">
               <div class="container-fluid">
                   <img class="img-fluid img-thumbnail rounded" src="img/${image}">
               </div>
                <div class="container-fluid text-center pt-3">
                   <h4 class="font-weight-bold p-2">${displayStr1}</h4>
                   <h4 class="p-2">${displayStr2}</h4>
                   <h4 class="p-2">${displayStr3}</h4>
               </div>
            </div>
           <div class="col-md-3"></div>
       </div>
    </div>
</body>
</html>