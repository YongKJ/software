<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>${pageNum}</title>
  <meta charset="utf-8">
<!--   <meta name="viewport" content="width=device-width, initial-scale=1"> -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<!--   <link rel="icon" href="img/cropped-动漫-43-32x32.jpg" sizes="32x32"> -->
  <link rel="icon" href="img/club_icon.png" sizes="576x576">
  <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>
  <script src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<!--   <link rel="stylesheet" href="bootstrap-4.3.1/bootstrap.min.css">
  <script src="bootstrap-4.3.1/jquery.min.js"></script>
  <script src="bootstrap-4.3.1/popper.min.js"></script>
  <script src="bootstrap-4.3.1/bootstrap.min.js"></script> -->
  <style type="text/css">body{margin-top: 60px;margin-bottom: 60px;}</style>
</head>
<body>
    <nav id="nav_top" class="navbar navbar-expand-sm fixed-top bg-light navbar-light">
      <ul class="navbar-nav">
        <li class="nav-item active">
          <a class="nav-link" href="home?userId=${userId}${isMobilePhone}"><strong>软件俱乐部</strong></a>
        </li>
      </ul>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
          <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="collapsibleNavbar">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
                <div class="input-group input-group-sm pt-1">
                  <input id="search" type="text" class="form-control" placeholder="请输入文章标题或内容">
                  <div class="input-group-append">
                      <button id="searchButton" type="button" class="btn btn-secondary" onclick="getSearchStr(${userId}, '${classify}')">搜索</button>
                  </div>
                </div>
            </li>
            <li id="nav_android" class="nav-item ml-3 active">
            	<div class="dropdown">
					<a class="nav-link" target="_blank" href="http://software.yongkj.cn/software/%E8%BD%AF%E4%BB%B6%E4%BF%B1%E4%B9%90%E9%83%A8_21.0.apk"><strong>Android客户端</strong></a>
				</div>
			</li>
            <li id="nav_rss" class="nav-item ml-2 active">
            	<div class="dropdown">
					<a class="nav-link" target="_blank" href="http://software.yongkj.cn/software/club.xml"><strong>RSS订阅</strong></a>
				</div>
			</li>
            <li id="nav_download" class="nav-item ml-2 active">
            	<div class="dropdown">
					<a class="nav-link" target="_blank" href="http://yun.yongkj.cn/"><strong>网盘下载</strong></a>
				</div>
			</li>
			<li id="nav_browseChoose" class="nav-item ml-2 active">
                <div class="dropdown">
                    <div class="nav-link dropdown-toggle" data-toggle="dropdown">
                        <strong>${browseChoose}</strong>
<!-- 						<strong>分类浏览</strong> -->
                    </div>
                    <div style="min-width:100px;" class="dropdown-menu position-absolute">
                        <a class="dropdown-item" href="home?chooseClassification=Windows${isMobilePhone}">Windows软件</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="home?chooseClassification=Android${isMobilePhone}">Android软件</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="home?chooseClassification=Linux${isMobilePhone}">Linux软件</a>
                    </div>
                </div>
			</li>
            <li id="nav_user" class="nav-item ml-3 active">
                <div class="dropdown">
<!--                     <div class="nav-link dropdown-toggle" data-toggle="dropdown">
                        <strong>游客</strong>
                    </div>
                    <div class="dropdown-menu position-absolute">
                        <a class="dropdown-item" href="register">注册</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="login">登录</a>
                    </div> -->
                    ${userName}
                </div>
            </li>
          </ul>
          </div>
    </nav>
    
    <div class="container-fluid">
        <div class="row pt-0 mt-0 pl-3 pr-3">
<!--             <div class="col-sm-12 col-md-4 pt-3 pb-3">
                
                <div class="container-fluid pt-3 pb-3">
                  <div class="card m-auto">
                    <div class="card-header">完美埃菲尔铁塔！</div>
                    <div class="card-body text-center">
                        <img src="https://static.runoob.com/images/mix/paris.jpg" class="img-fluid">
                    </div> 
                    <div class="card-footer">YONGKJ&nbsp;&nbsp;&#47;&nbsp;&nbsp;11月 22, 2019</div>
                  </div>
                </div>
                
                <div class="container-fluid pt-3 pb-3">
                  <div class="card m-auto">
                    <div class="card-header">头部</div>
                    <div class="card-body">内容</div> 
                    <div class="card-footer">底部</div>
                  </div>
                </div>
                
            </div>
            <div class="col-sm-12 col-md-4 pt-3 pb-3">
                <div class="container-fluid pt-3 pb-3">
                  <div class="card w-100 m-auto">
                    <div class="card-header">头部</div>
                    <div class="card-body">内容</div> 
                    <div class="card-footer">底部</div>
                  </div>
                </div>
                
                <div class="container-fluid pt-3 pb-3">
                  <div class="card w-100 m-auto">
                    <div class="card-header">头部</div>
                    <div class="card-body">内容</div> 
                    <div class="card-footer">底部</div>
                  </div>
                </div>
            </div>
            <div class="col-sm-12 col-md-4 pt-3 pb-3">
                <div class="container-fluid pt-3 pb-3">
                  <div class="card w-100 m-auto">
                    <div class="card-header">头部</div>
                    <div class="card-body">内容</div> 
                    <div class="card-footer">底部</div>
                  </div>
                </div>
                
                <div class="container-fluid pt-3 pb-3">
                  <div class="card w-100 m-auto">
                    <div class="card-header">头部</div>
                    <div class="card-body">内容</div> 
                    <div class="card-footer">底部</div>
                  </div>
                </div>
            </div> -->
            ${SoftwaresDisplayStr}
        </div>
    </div>
    
    <nav id="nav_bottom" class="navbar navbar-expand-sm fixed-bottom bg-light navbar-light pl-0 pr-0">
      <div class="container-fluid pt-1 pb-1">
          <ul class="pagination m-auto">
<!--             <li class="page-item disabled"><a class="page-link bg-light" href="#">Previous</a></li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link bg-light" href="#">2</a></li>
            <li class="page-item"><a class="page-link bg-light" href="#">3</a></li>
            <li class="page-item"><a class="page-link bg-light" href="#">Next</a></li> -->
            ${pageNumStr}
          </ul>
      </div>
    </nav>
    
    <script type="text/javascript">
		var baseIP = "software.yongkj.cn";
		
        var onResize = function() {
        	$("body").css("padding-left", $(".side-navbar").width());
    	};
    	$(window).resize(onResize);
    	$(function() {onResize();});
		
// 		var offsetWid = document.documentElement.clientWidth;
// 		console.log(offsetWid);
		var screenWid = window.screen.width;
// 		console.log(screenWid);
		if(screenWid < 768) {
			var one = document.getElementById('col_one');
			var two = document.getElementById('col_two');
			var three = document.getElementById('col_three');
			if(one != null) {
			 one.setAttribute("class", "col-sm-12 col-md-4 pt-3 pb-0 pl-0 pr-0");
			}
			if(two != null) {
			 two.setAttribute("class", "col-sm-12 col-md-4 pt-0 pb-0 pl-0 pr-0");
			}
			if(three != null) {
			 three.setAttribute("class", "col-sm-12 col-md-4 pt-0 pb-3 pl-0 pr-0");
			}
			
			var nav_browseChoose = document.getElementById('nav_browseChoose');
            var nav_android = document.getElementById('nav_android');
			var nav_rss = document.getElementById('nav_rss');
			var nav_download = document.getElementById('nav_download');
			var nav_user = document.getElementById('nav_user');
			if(nav_browseChoose != null) {
				nav_browseChoose.setAttribute("class", "nav-item active");
			}
            if(nav_android != null) {
				nav_android.setAttribute("class", "nav-item active");
			}
			if(nav_rss != null) {
				nav_rss.setAttribute("class", "nav-item active");
			}
			if(nav_download != null) {
				nav_download.setAttribute("class", "nav-item active");
			}
			if(nav_user != null) {
				nav_user.setAttribute("class", "nav-item active");
			}
		}
		
        function getPageNum(userId, pageNum) {
            var url = "http://" + baseIP + "/software/home?";
            if(userId != -1){
                url += "userId=" + userId + "&";
            }
            url += "pageNum=" + pageNum;
            console.log(userId);
            console.log(pageNum);
            window.location.href=url;
        }
		
        function getSoftwareId(userId, softwareId, chooseClassification) {
            var url = "http://" + baseIP + "/software/content?";
            if(userId != -1){
                url += "userId=" + userId + "&";
            }
			if(chooseClassification != "default") {
				url += "chooseClassification=" + chooseClassification + "&";
			}
            url += "softwareId=" + softwareId;
            console.log(userId);
            console.log(softwareId);
//             window.location.href=url;
            window.open(url);
        }
		
        function getSearchStr(userId, classify) {
        	var isMobilePhone = "${isMobilePhone}";
            var searchValue = document.getElementById("search").value;
            var url = "http://" + baseIP + "/software/home?";
            if(userId != -1){
                url += "userId=" + userId + "&";
            }
			if(classify != "default"){
                url += "chooseClassification=" + classify + "&";
            }
            if(searchValue != ""){
                url += "searchStr=" + encodeURIComponent(searchValue);
            }
            if(isMobilePhone != "") {
				url += isMobilePhone;
			}
            console.log(userId);
            console.log(searchValue);
//             window.location.href=url;
            window.open(url);
        }
		
        
        $(document).keypress(function(event) {
            var keynum = (event.keyCode ? event.keyCode : event.which);
            // console.log(keynum);
            if(keynum == '13'){
                $("#searchButton").click()
                event.preventDefault();
            }  
        });
    </script>
</body>
</html>