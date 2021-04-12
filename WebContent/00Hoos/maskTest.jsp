<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    #mask {
      position:absolute;
      z-index:2;
      background-color:rgb(239, 238, 226);
      display:none;
      left:0;
      top:0;
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
    </style>
<!-- jQeury -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
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
 
    $(function(){
        //검은 막 띄우기
            wrapWindowByMask();
       /*  $('.openMask').click(function(e){
            e.preventDefault();
        });*/
 
        //닫기 버튼을 눌렀을 때
        $('.window #close').click(function (e) {
            //링크 기본동작은 작동하지 않도록 한다.
            e.preventDefault();
            $('#mask, .window').hide();
        });       
 
        //검은 막을 눌렀을 때
       /* $('#mask').click(function () {
            $(this).hide();
            $('.window').hide();
        }); */
    });
    </script>
</head>
<body>
    <div id="mask"></div>
    <div class="window">
    	<a id="close" href="#">X</a>
    </div>
    <a href="#" class="openMask">불투명 창 띄우기+select 결과 띄우기</a>
</body>
</html>


