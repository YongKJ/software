<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>${softwareTitle}</title>
  <meta charset="utf-8">
<!--   <meta name="viewport" content="width=device-width, initial-scale=1"> -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
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
  <style type="text/css">body{margin-top: 56px;margin-bottom: 100px;}</style>
</head>
<body>
    <nav id="nav_top" class="navbar navbar-expand-sm fixed-top bg-light navbar-light">
      <ul class="navbar-nav">
        <li class="nav-item active">
          <a class="nav-link" href="home?userId=${userId}"><strong>软件俱乐部</strong></a>
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
                        <a class="dropdown-item" href="home?chooseClassification=Windows">Windows软件</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="home?chooseClassification=Android">Android软件</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="home?chooseClassification=Linux">Linux软件</a>
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
        <div id="row" class="row pt-5 mt-5">
            <div class="col-md-3"></div>
            <div id="col_content" class="col-md-6 col-sm-10 bg-light mt-3 ml-4 mr-4 pl-0 pr-0 pt-3">
                <h3 class="font-weight-bold mb-4">&nbsp;&nbsp;${softwareTitle}</h3>
                <div class="mb-4">
                    &nbsp;&nbsp;&nbsp;
                    <img src="img/user.png" class="img-fluid" style="border-radius:50%;" width="25" height="25">
<!--                    用户图像下载 http://2.gravatar.com/avatar/5c927c80d8efdf66c03e5bb66c5da683?s=100&d=mm&r=g -->
                    &nbsp;BY ${softwareAuthor}&nbsp;&nbsp;&#47;&nbsp;&nbsp;ON ${softwareDate}
                </div>
                <div class="container-fluid pt-2 pb-2 pr-0 pl-0 text-center">
                    <img src="${softwareImage}" class="img-fluid rounded">
                </div>
                <p class="mt-4 mb-5 p-2" style="font-size:18px;line-height:30px;">
<!--                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
                    ${softwareContent}
                </p>
                <br /><br />
                <div class="row p-3">
                    <div class="col-md-3 col-sm-6">
<!--                         <img src="http://software.yongkj.cn/software/img/01.jpg" class="img-fluid rounded"> -->
                        ${prevSoftwareImage}
                    </div>
                    <div class="col-md-3 col-sm-6 text-left p-3 mt-1">
<!--                         上一篇
                        <br />
                        <br />
                        <strong> Hello world!</strong> -->
                        ${prevSoftwareTitle}
                    </div>
                    <div class="col-md-3 col-sm-6 text-right p-3 mt-1">
<!--                         下一篇
                        <br />
                        <br />
                        <strong> Hello world!</strong> -->
                        ${nextSoftwareTitle}
                    </div>
                    <div class="col-md-3 col-sm-6">
<!--                         <img src="http://software.yongkj.cn/software/img/01.jpg" class="img-fluid rounded"> -->
                        ${nextSoftwareImage}
                    </div>
                </div>
                <div class="container-fluid pt-5 pr-5 pl-5 pb-2 mt-4">
                    <h3 class="p-3">${commentsSum}条评论</h3>
                    <div class="dropdown-divider"></div>
					
<!-- 					<div class="row p-3">
						<div class="col-md-1 p-0">
							<img src="http://2.gravatar.com/avatar/5c927c80d8efdf66c03e5bb66c5da683?s=100&d=mm&r=g" class="img-fluid" style="border-radius:50%;" width="60">
						</div>
						<div class="col-md-11 pr-0">
							<div class="container-fluid p-0 m-0">
								<strong>a</strong>
								<span class="float-right pt-1" style="font-size:12px;">
									11月 8, 2019 &#47; 下午5:21
								</span>
							</div>
							<br />
							<span>我是第一个发言,我是第一个发言,我是第一个发言,我是第一个发言</span>
						</div>
					</div>
                    <div class="dropdown-divider"></div>
                    <div class="row p-3">
                        <div class="col-md-1 p-0">
                            <img src="http://2.gravatar.com/avatar/5c927c80d8efdf66c03e5bb66c5da683?s=100&d=mm&r=g" class="img-fluid" style="border-radius:50%;" width="60">
                        </div>
                        <div class="col-md-7">
                            <strong>小王</strong>
                            <br />
                            <br />
                            我是第一个发言
                        </div>
                        <div class="col-md-4 pt-2 text-right" style="font-size:12px;">
                            11月 8, 2019 &#47; 下午5:21
                        </div>
                    </div>
                    <div class="dropdown-divider"></div>
                    <div class="row p-3">
                        <div class="col-md-1 p-0">
                            <img src="http://2.gravatar.com/avatar/5c927c80d8efdf66c03e5bb66c5da683?s=100&d=mm&r=g" class="img-fluid" style="border-radius:50%;" width="60">
                        </div>
                        <div class="col-md-7">
                            <strong>小王</strong>
                            <br />
                            <br />
                            我是第一个发言
                        </div>
                        <div class="col-md-4 pt-2 text-right" style="font-size:12px;">
                            11月 8, 2019 &#47; 下午5:21
                        </div>
                    </div>
                    <div class="dropdown-divider"></div> -->
                    ${commentStr}
                </div>
                <div class="container-fluid pl-5 pr-5">
                    <h4 class="p-3">留下你的评论</h4>
                    <div class="row mb-5">
                        <div class="col-md-6 p-3">
                            <input id="userName" type="text" class="form-control" placeholder="请输入用户名" value="${theUserName}">
                        </div>
                        <div class="col-md-6 p-3">
                            <input id="userEmail" type="text" class="form-control" placeholder="请输入电子邮箱">
                        </div>
                        <div class="col-md-12 p-3">
                            <textarea id="commentContent" class="form-control" rows="5" placeholder="请输入评论内容"></textarea>
                        </div>
                        <div class="col-md-3 p-3">
                            <button  onclick="getCommentStr(${userId}, ${softwareId})" type="button" class="btn btn-outline-info">发表评论</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
    </div>
    
     <script type="text/javascript">
		var baseIP = "software.yongkj.cn";
		 
        var onResize = function() {
        	$("body").css("padding-left", $(".side-navbar").width());
    	};
    	$(window).resize(onResize);
    	$(function() {onResize();});
		
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
        
        var screenWid = window.screen.width;
// 		console.log(screenWid);
		if(screenWid < 768) {
			var row = document.getElementById('row');
			if(row != null) {
				//row.setAttribute("class", "row pt-5 pl-0 pr-0 mt-5");
			}

			var content = document.getElementById('col_content');
			if(content != null) {
				//content.setAttribute("class", "col-md-6 col-sm-10 bg-light mt-3 ml-4 mr-4 mb-5 pr-0 pl-0 pt-3 pb-5");
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
		 
        function getSoftwareId(userId, softwareId) {
            var url = "http://" + baseIP + "/software/content?";
            if(userId != -1){
                url += "userId=" + userId + "&";
            }
            url += "softwareId=" + softwareId;
            console.log(userId);
            console.log(softwareId);
            window.location.href=url;
        }
		 
        function getSearchStr(userId, classify) {
            var searchValue = document.getElementById("search").value;
            var url = "http://" + baseIP + "/software/home?";
            if(userId != -1){
                url += "userId=" + userId + "&";
            }
			if(classify != "default"){
                url += "chooseClassification=" + classify + "&";
            }
            if(searchValue != ""){
                url += "searchStr=" + searchValue;
            }
            console.log(userId);
            console.log(searchValue);
//             window.location.href=url;
            window.open(url);
        }
		 
        function getCommentStr(userId, softwareId) {
            var url = "http://" + baseIP + "/software/content?";
            var userNameValue = document.getElementById("userName").value;
            var userEmailValue = document.getElementById("userEmail").value;
            var commentContentValue = document.getElementById("commentContent").value;
            if(userId != -1){
                url += "userId=" + userId + "&";
            }
            url += "softwareId=" + softwareId + "&";
            if(userEmailValue != "" && commentContentValue != "" && userNameValue != "") {
                url += "userName=" + userNameValue + "&";
                url += "userEmail=" + userEmailValue + "&";
                url += "commentContent=" + commentContentValue;
                console.log(url);
                window.location.href=url;
            }
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