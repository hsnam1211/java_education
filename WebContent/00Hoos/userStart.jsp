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
#mask {
  position:absolute;
  z-index:2;
  background-color:rgb(239, 238, 226);
  display:none;
  left:0;
  top:0;
  text-align: center;
}
.window{
  display: none;
  position:absolute;
  right:55px;
  top:40px;
  z-index:3;
}
#close {
  color:gray;
  text-decoration:none;
  font-size: 30px;
}
#tb {
  position:absolute;
  width:500px;
  top: 200px;
  left: 400px;
  z-index:3;
}
#book_name {
  border-left-width: 0;
  border-right-width: 0;
  border-top-width: 0;
  border-bottom-width: 1;
  width: 60%;
  height: 30px;
  background: rgba(255, 255, 255, 0.3);
  text-align: center;
}
#search {
  text-align: center;
  position: relative;
  top: 383px;
}
<%
	String userid = (String)session.getAttribute("userid"); // 로그인 시 입력한 id를 login.jsp에서 전달해준다.
	String status = request.getParameter("status"); // 도서 대출 시 대출 상태를 가져온다.
	String bookno = request.getParameter("bookno"); // 도서 대출 시 도서 번호를 가져온다.
	
	if(userid == null) {
		userid = "";
	}
	if(status == null) {
		status = "";
	}
	if(bookno == null) {
		bookno = "";
	}
	out.print(bookno);
%>
</style>
<!-- <script>
	window.history.forward(); // 로그아웃 후 뒤로가기 막기
	function noBack(){window.history.forward();}
</script> -->
<script>
	function wrapWindowByMask(){
	    //화면의 높이와 너비를 구한다.
	    var maskHeight = $(document).height();
	    var maskWidth = $(window).width();  
	
	    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
	    $('#mask').css({'width':maskWidth,'height':maskHeight});  
	
	    //애니메이션 효과 - 일단 1초동안 까맣게 됐다가 80% 불투명도로 간다.
	    $('#mask').fadeTo("slow",0.8);    
	
	    //윈도우 같은 거 띄운다.
	    $('.window').show();
	}
	$(function() {
		// 클릭 시 전체도서 셀렉 및 contents배경 불투명하게 전환
		$("#book_search").on("click", function() {
			$.ajax({
				url:"bookselectall",
				success:function(responseData) {
					$("#mask").html(responseData);
				}
			});
		});
		
		// 도서제목 입력 후 검색버튼 누를 시 검색한 도서 셀렉
		$("#booksearch_btn").on("click", function() {
			$.ajax({
				url:"booksearch",
				data:{"bookname":$("#book_name").val()},
				success:function(responseData) {
					$("#mask").html(responseData);
				}
			});
		});
		
		// 원하는 도서 check후 도서대출 버튼 누를 시 대출
		$("#book_borrow").on("click", function(e) {
			e.preventDefault();
			<%-- alert("<%=bookno%>");
			alert("<%=status%>"); --%>
			$.ajax({
				url:"bookborrow",
				data:{
					"bookno":"<%=bookno%>",
					"status":"<%=status%>",
					"userid":"<%=userid%>"},
				success:function(responseData) {
					$("#mask").html(responseData);
				}
			});
		});
		
	    $(".btn").on("click",function() {
			//막 띄우기
			wrapWindowByMask();
	    });
        //닫기 버튼을 눌렀을 때
        $('.window #close').click(function (e) {
            //링크 기본동작은 작동하지 않도록 한다.
            e.preventDefault();
            $('#mask, .window').hide();
        });  
	});
</script>

</head>
<!-- <body onload="noBack();" onpageshow="if(event.persisted) noBack();" onunload=""> --> 
<body> 
	<div id="container">	
		<header>	
			<img src="/webProject(hoos)/00Hoos/images/스케치.png">
		</header>
		<main>
			<div id="contents">
				<div id="mask">
					여기!
				</div>
			</div>
			    <div class="window">
			    	<a id="close" href="#">X</a>
			    </div>
			</div>
			<aside id="sidebar_warpper">
				<div id="id_area">
					<%=userid %>님 환영합니다.
				</div>
				<div id="search">
					<input type="text" id="book_name" name="bookname" placeholder="도서 제목을 입력하세요.">
					<button type="button" id="booksearch_btn" class="booksearch_btn">검색</button>
				</div>
				<div id="sidebar_list">
					<a href="logout.jsp">로그아웃</a>
					<a class="btn" id="book_search" href="#">도서 검색</a>
					<a class="btn" id="book_borrow" href="#">도서 대출</a>
					<a class="btn" id="book_return" href="#">도서 반납</a>
					<a class="btn" id="book_extend" href="#">도서 연장</a>
					<a class="btn" id="book_reservation" href="#">도서 예약</a>
				</div>
			</aside>
		</main>
	</div>
</body>
</html>