<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">
</head>
<body>
	<div title="欢迎使用" style="padding: 20px; overflow: hidden; color: red;">

		<div class="container">
			<div class="row">
				<div class="col">
					<div class="card" style="width: 50rem;">
						<img
							src="https://cdn.jsdelivr.net/gh/unluckynike/blogimg/images/student-849828_1280.jpg"
							class="img-thumbnail" alt="...">
						<div class="card-body">
							<h5 class="card-title" id="hitokoto">Hitokoto....</h5>
							<p class="card-text">
								开发人员：<a href="http://www.wulinzeng.vip">HaiLin&nbsp;Zhou</a>
							</p>
						</div>
						<ul class="list-group list-group-flush">
							<li class="list-group-item"><p class="card-text">
									参与项目：<a href="https://github.com/unluckynike/Student">issue&nbsp;&nbsp;&nbsp;&nbsp;fork</a>
								</p></li>
							<li class="list-group-item"><p class="card-text">
									联系作者：<a
										href="http://wpa.qq.com/msgrd?v=3&uin=2230432084&site=qq&menu=yes">QQ</a>
								</p></li>
							<li class="list-group-item"><p class="card-text">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
										href="Mailto:2230432084@qq.com">邮件</a>
								</p></li>

							<div>
								<iframe frameborder="no" border="0" marginwidth="0"
									marginheight="0" width="560" height="110" loading="lazy"
									sandbox="allow-popups allow-scripts allow-same-origin"
									src="https://www.xiami.com/webapp/embed-player?autoPlay=1&id=2121966"></iframe>
							</div>

						</ul>
					</div>
					<div class="col"></div>
				</div>
			</div>
		</div>
		<script>
      fetch('https://v1.hitokoto.cn/?c=i')
        .then(function (res){
          return res.json();   
       })
       .then(function (data) {      
         var hitokoto = document.getElementById('hitokoto');
         hitokoto.innerText = data.hitokoto + '           ——' + data.from_who+'《'+data.from + '》'; 
            })
        .catch(function (err) {      
          onsole.error(err);  
            })
    </script>
</body>
</html>
