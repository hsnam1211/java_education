<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- Font -->
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200&display=swap" rel="stylesheet">

<!-- jQeury -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<style>

html, body {
   
    background-color: #fff;
    height: 100%;
}
body
{	 
	animation: background_img 20s ease-in infinite;
}

 @keyframes background_img {
        
    0% {background: url("/webProject(hoos)/00Hoos/images/제주바다.jpg") no-repeat center center fixed;
    	background-position: 50% center;
    	background-size: cover;
    	width: 100%;
		margin: 0;
    }
    25% {background: url("/webProject(hoos)/00Hoos/images/제주바다2.jpg") no-repeat center center fixed;
    	background-position: 50% center;
    	background-size: cover;
    	width: 100%;
		margin: 0;
    }
    50% {background: url("/webProject(hoos)/00Hoos/images/소품샵.jpg") no-repeat center center fixed;
    	background-position: 50% center;
    	background-size: cover;
    	width: 100%;
		margin: 0;
    }
    75% {background: url("/webProject(hoos)/00Hoos/images/풍경.jpg") no-repeat center center fixed;
    	background-position: 50% center;
    	background-size: cover;
    	width: 100%;
		margin: 0;
    }
    100% {background: url("/webProject(hoos)/00Hoos/images/제주바다.jpg") no-repeat center center fixed;
    	background-position: 50% center;
    	background-size: cover;
    	width: 100%;
		margin: 0;
    }
 } 

#sidebar_warpper 
{
	background-color: rgb(239, 238, 226);
	float: left;
	width: 370px;
	height:100%;
	position: absolute;
	top:0;
	z-index: 4;
}
#sidebar_list {
	display:inline-flex;
	flex-direction: column;
	top: 430px;
	left: 153px;
	position: absolute;
}

#sidebar_list > a
{
	position: relative;
	font-family: 'Noto Serif KR', serif;
	color: black;
	text-decoration:none;
	text-align: center;
}

/* #sidebar .user_login { left: 150px; }
#sidebar .admin_login {	left: 144px; }
#sidebar .main_page { left: 160px; } */

#sidebar_list > a:hover 
{
	color: gray;
}
#id_area {
	top: 340px;
	width: 100%;
	text-align: center;
	position: absolute;
	
}

header img
{
	margin: 77px;
	width: 220px;
	position: relative;
	z-index: 5;
}
#wise_saying
{
	font-family: 'Noto Serif KR', serif;
	position: absolute;
	bottom: 37px;
	padding: 0 0 70px 60px;
	z-index: 5;
	mix-blend-mode: difference;
}

#wise_saying h2
{
	color: #808080;
}

#wise_saying .name 
{
	padding: 0 0 0 30px;
	color: #d3d3d3;
}

</style>
<script>
	window.history.forward(); // 로그아웃 후 뒤로가기 막기
	function noBack(){window.history.forward();}
</script>
<script>
	$(function() {
		$(".book_search").on("click", function() {
			$("#contents").load("maskTest.jsp");
		});
	});
</script>
<%
	String userid = (String)session.getAttribute("userid"); // 로그인 시 입력한 id를 login.jsp에서 전달해준다.
	
	if(userid == null) {
		userid = "";
	}
%>

</head>
<body onload="noBack();" onpageshow="if(event.persisted) noBack();" onunload=""> // 로그아웃 후 뒤로가기 막기
	<div id="container">	
		<header>	
			<img src="/webProject(hoos)/00Hoos/images/스케치.png">	
		</header>
		<main>
			<div id="contents">
			</div>
			<aside id="sidebar_warpper">
				<div id="id_area">
					<%=userid %>님 환영합니다.
				</div>
				<div id="sidebar_list">
					<a href="logout.jsp">로그아웃</a>
					<a class="book_search" href="#">도서 검색</a>
					<a class="book_borrow" href="#">도서 대출</a>
					<a class="book_return" href="#">도서 반납</a>
					<a class="book_extend" href="#">도서 연장</a>
					<a class="book_reservation" href="#">도서 예약</a>
					
				</div>
			</aside>
			<div id="wise_saying">
				<h2>남후승 도서관에 오신 것을 환영합니다.</h2>
				<p class="name"><b>좋은 양서를 읽는다는 것은 지난 몇 세기에 걸친 훌륭한 위인들과 대화를 하는 것과 같다.</b></p>
				<p class="name">- 데카르트 -</p>
			</div>
		</main>
	</div>
</body>
</html>