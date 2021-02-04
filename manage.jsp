<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>后台管理</title>
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
  <style type="text/css">body{margin-top: 56px;margin-bottom: 62px;}</style>
  <style type="text/css">
	  .custom-file-input:lang(zh) ~ .custom-file-label::after {
		 content: "浏览";
	  }

	  .custom-file-label::after {
		 content: "浏览";
	  }
	  @media (min-width:100px) and (max-width:576px) {
		  table {
			  width:750px !important;
		  }
	  }
  </style> 
</head>
<body >
	
	<nav class="navbar navbar-expand-sm fixed-top bg-dark navbar-dark">
    	<ul class="navbar-nav">
    		<li class="nav-item active">
      	  	<a class="nav-link" href="#"><strong>软件俱乐部后台管理</strong></a>
    		</li>
  		</ul>
		
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
		    <span class="navbar-toggler-icon"></span>
	    </button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<ul class="navbar-nav ml-auto">
			  <li class="nav-item active">
				  <div class="input-group input-group-sm pt-1">
				    <input id="search" type="text" class="form-control" placeholder="请输入查找内容">
				    <div class="input-group-append">
					    <button id="searchButton" type="button" class="btn btn-secondary" onclick="getSearchStr(${search})">搜索</button>
				    </div>
				  </div>
			  </li>
			  <li class="nav-item active">
				  <div class="dropdown pl-3 pr-3">
						<div class="nav-link text-white dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
						admin
						</div>
						<div style="min-width:100px;" class="dropdown-menu position-absolute text-center">
						<a class="dropdown-item" href="home?userId=1" target="_blank">首页</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="home">退出</a>
						</div>
				  </div>
				</li>

			</ul>
		</div>
	</nav>
	
    <div class="container-fluid">
        <div class="row text-center">
            <div class="col-sm-12 col-md-2 p-0">
                
               <div class="card">
                <div class="card-header">
                        <a class="card-link" data-toggle="collapse" href="#collapseOne">
                        用户管理
                        </a>
                  </div>
                  <div id="collapseOne" class="collapse" data-parent="#accordion">
                        <div class="container p-0">
  					  	<div class="list-group">
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Users&tableOperation=mod" class="list-group-item list-group-item-warning">修改用户</a>
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Users&tableOperation=del" class="list-group-item list-group-item-danger">删除用户</a>
  						  </div>
					    </div>
                  </div>
              </div>

              <div class="card">
                <div class="card-header">
                        <a class="collapsed card-link" data-toggle="collapse" href="#collapseTwo">
                        评论管理
                        </a>
                  </div>
                  <div id="collapseTwo" class="collapse" data-parent="#accordion">
                        <div class="container p-0">
  					  	<div class="list-group">
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Comments&tableOperation=mod" class="list-group-item list-group-item-success">审核评论</a>
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Comments&tableOperation=del" class="list-group-item list-group-item-danger">删除评论</a>
  						  </div>
					    </div>
                  </div>
              </div>

              <div class="card">
                <div class="card-header">
                        <a class="collapsed card-link" data-toggle="collapse" href="#collapseThree">
                        文章管理
                        </a>
                  </div>
                  <div id="collapseThree" class="collapse" data-parent="#accordion">
                        <div class="container p-0">
  					  	<div class="list-group">
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Softwares&tableOperation=add" class="list-group-item list-group-item-primary">发布文章</a>
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Softwares&tableOperation=mod" class="list-group-item list-group-item-warning">修改文章</a>
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Softwares&tableOperation=del" class="list-group-item list-group-item-danger">删除文章</a>
  						  </div>
					    </div>      
                  </div>
               </div>
				
			   <div class="card">
                <div class="card-header">
                        <a class="collapsed card-link" data-toggle="collapse" href="#collapseFour">
                        图片管理
                        </a>
                  </div>
                  <div id="collapseFour" class="collapse" data-parent="#accordion">
                        <div class="container p-0">
  					  	<div class="list-group">
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Pictures&tableOperation=add" class="list-group-item list-group-item-primary">上传图片</a>
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Pictures&tableOperation=mod" class="list-group-item list-group-item-warning">修改图片</a>
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Pictures&tableOperation=del" class="list-group-item list-group-item-danger">删除图片</a>
  						  </div>
					    </div>      
                  </div>
               </div>
				
				<div class="card">
                <div class="card-header">
                        <a class="collapsed card-link" data-toggle="collapse" href="#collapseFive">
                        分类管理
                        </a>
                  </div>
                  <div id="collapseFive" class="collapse" data-parent="#accordion">
                        <div class="container p-0">
  					  	<div class="list-group">
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Windows" class="list-group-item list-group-item-primary">Windows软件</a>
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Android" class="list-group-item list-group-item-warning">Android软件</a>
    							<a href="manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Linux" class="list-group-item list-group-item-danger">Linux软件</a>
  						  </div>
					    </div>      
                  </div>
               </div>
				
            </div>
            <div class="col-sm-12 col-md-10 p-0">
            	<div class="container-fluid p-0">
					<div class="table-responsive">   
                		${displayStr}
                	</div>
                </div>
            </div>
        </div>
    </div>
	
	<nav class="navbar navbar-expand-sm fixed-bottom bg-light navbar-light">
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
		
// 		var onResize = function() {
//         $("body").css("padding-left", $(".side-navbar").width());
//     	};
//     	$(window).resize(onResize);
//     	$(function() {
//         	onResize();
//     	});
		
// 		$("#navMenu").resize(function () {
// 			$('#godown').height($("#navMenu").height() + 10);
//         });

// 		if ($("#navMenu").height() > $('#godown').height()) $('#godown').height($("#navMenu").height() + 10);
		
// 		$(window).resize(function () { 
// 		   $('body').css('padding-top', parseInt($('#main-navbar').css("height"))+10);
// 		});
        
        function getAddSoftwareValue() {
//             var softwareAuthorValue = document.getElementById("softwareAuthor").value;
//             var softwareTitleValue = document.getElementById("softwareTitle").value;
//             var softwareContentValue = document.getElementById("softwareContent").value;
//             var softwareImageValue = document.getElementById("softwareImage").value;
//             var url="http://" + baseIP + "/software/manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&pageNum=" + pageNum + "&tableName=Softwares&tableOperation=add&softwareAuthor=" + softwareAuthorValue + "&softwareTitle=" + encodeURIComponent(softwareTitleValue) + "&softwareContent=" + encodeURIComponent(softwareContentValue) + "&softwareImage=" + encodeURIComponent(softwareImageValue);
//             console.log(softwareAuthorValue);
//             console.log(softwareTitleValue);
//             console.log(softwareContentValue);
//             console.log(softwareImageValue);
//             window.location.href=url;
			document.getElementById('addSoftware').submit();
        }
		
		function getAddClassificationValue(pageNum, tableName) {
			var softwareTitleValue = document.getElementById("softwareTitle").value;
			if(softwareTitleValue != "") {
				var url="http://" + baseIP + "/software/manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&pageNum=" + pageNum + "&tableName=" + tableName + "&tableOperation=add&softwareTitle=" + encodeURIComponent(softwareTitleValue);
				console.log(url);
				window.location.href=url;
			}
		}
        
        function getSoftwareVaule(id) {
//             var softwareTitle = "softwareTitle" + id;
//             var softwareContent = "softwareContent" + id;
//             var softwareImage = "softwareImage" + id;
//             var softwareTitleValue = document.getElementById(softwareTitle).value;
//             var softwareContentValue = document.getElementById(softwareContent).value;
//             var softwareImageValue = document.getElementById(softwareImage).value;
//             if(softwareTitleValue == "") {
//                 softwareTitleValue = document.getElementById(softwareTitle).placeholder;
//             }
//             if(softwareContentValue == "") {
//                 softwareContentValue = document.getElementById(softwareContent).placeholder;
//             }
//             if(softwareImageValue == "") {
//                 softwareImageValue = document.getElementById(softwareImage).placeholder;
//             }
//             var url="http://" + baseIP + "/software/manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Softwares&tableOperation=mod&pageNum=" + pageNum + "&id=" + id + "&softwareTitle=" + softwareTitleValue + "&softwareContent=" + encodeURIComponent(softwareContentValue) + "&softwareImage=" + softwareImageValue;
//             console.log(softwareTitleValue);
//             console.log(softwareContentValue);
//             console.log(softwareImageValue);
//             window.location.href=url;
			var formId = "modSoftware" + id;
			document.getElementById(formId).submit();
        }
        
        function getUserInputValue(id, pageNum) {
            var userName = "userName" + id;
            var userPasswd = "userPasswd" + id;
            var userNameValue = document.getElementById(userName).value;
            var userPasswdValue = document.getElementById(userPasswd).value;
            if(userNameValue == "") {
                userNameValue = document.getElementById(userName).placeholder;
            }
            if(userPasswdValue == "") {
               userPasswdValue = document.getElementById(userPasswd).placeholder;
            }
            var url = "http://" + baseIP + "/software/manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Users&tableOperation=mod&pageNum=" + pageNum + "&id=" + id + "&userName=" + userNameValue + "&userPasswd=" + userPasswdValue;
            console.log(userNameValue);
            console.log(userPasswdValue);
            window.location.href=url;
        }
		
		function getPictureVaule(id, pageNum) {
            var pictureName = "pictureName" + id;
			var pictureNameValue = document.getElementById(pictureName).value;
			if(pictureNameValue == "") {
                pictureNameValue = document.getElementById(pictureName).placeholder;
            }
            var url="http://" + baseIP + "/software/manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Pictures&tableOperation=mod&pageNum=" + pageNum + "&id=" + id + "&pictureName=" + pictureNameValue;
            console.log(pictureNameValue);
			console.log(url);
            window.location.href=url;
        }
		
		function getAddPictureContent(pageNum) {
			var pictureLinkValue = document.getElementById('pictureLink').value;
			if(pictureLinkValue != "") {
// 				var url="http://m.yongkj.cn/software/manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Pictures&tableOperation=add&pictureLink=" + encodeURIComponent(pictureLinkValue);
// 				console.log(url);
// 				window.location.href=url;
				document.getElementById('postPictureLink').submit();
			}else{
				document.getElementById('postPicture').submit();
			}
		}
		
		function getSearchStr(tableName, tableOperation) {
            var searchValue = document.getElementById("search").value;
            var url = "http://" + baseIP + "/software/manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0";
            if(searchValue != ""){
				url += "&tableName=" + tableName;
				url += "&tableOperation=" + tableOperation;
                url += "&searchStr=" + encodeURIComponent(searchValue);
				console.log(url);
				window.open(url);
            }
//             window.location.href=url;
        }
		
		$('#pictureName').on('change',function(){
            //get the file name
            var fileName = $(this).val();
            //replace the "Choose a file" label
            $(this).next('.custom-file-label').html(fileName.replace("C:\\fakepath\\", ''));
        })

        function readyNumber() { 

          $('textarea').each(function () {
             this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
          }).on('input', function () {
              this.style.height = 'auto';
              this.style.height = (this.scrollHeight) + 'px';
          })
        }

        readyNumber();
        
//         $(document).keypress(function(event) {
//             var keynum = (event.keyCode ? event.keyCode : event.which);
//             // console.log(keynum);
//             if(keynum == '13'){
//                 $("#searchButton").click()
//                 event.preventDefault();
//             }  
//         });
	</script>
</body>
</html>