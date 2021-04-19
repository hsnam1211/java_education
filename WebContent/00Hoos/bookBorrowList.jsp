<%@page import="model.BorrowListVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<% 	
	List<BorrowListVO> booklist = (List<BorrowListVO>)request.getAttribute("book");
	String userid = (String)session.getAttribute("userid");
%>
	
<style>
	
#tb {
  position:absolute;
  width:600px;
  top: 200px;
  right: 400px;
     z-index:3;
}
</style>
<!-- jQeury -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
function doOpenCheck(chk){
    var obj = document.getElementsByName("check");
    for(var i=0; i<obj.length; i++){
        if(obj[i] != chk){
            obj[i].checked = false;
        }
    }
 
	var checkbox = $("input[name='check']:checked");
	var col1 = "";
	var col2 = "";
	var col3 = "";
	var col4 = "";
	
	checkbox.each(function(i) {
		var tr = checkbox.parent().parent().eq(i); //checkbox 태그의 두 단계 상위 태그인 tr을 가리킴
		var td = tr.children(); //td 태그는 tr 태그의 하위
		
		col1 = td.eq(1).text(); //1번째 column(eq(0))은 체크박스 이므로 eq(1)부터 데이터를 받음
		col2 = td.eq(2).text();
		col3 = td.eq(3).text();
		col4 = td.eq(4).text();
	}); 
	
	$(".bookreturn").on("click", function() {
		
		$.ajax({
			url:"bookreturn",
			data:{
				"bookno":col1,
				"userid":"<%=userid%>"},
			success:function(responseData) {
				$("#mask").html(responseData);
			}
		});
	});
}

</script>
</head>
<body>
   	<table id="tb">
   		<tr>
   			<th>선택</th><th>도서번호</th><th>도서제목</th><th>대출일</th><th>반납일</th>
   		</tr>
		<%for(BorrowListVO book:booklist) {%>
		<tr>
			<td><input type="checkbox" name="check" class="check" onclick="doOpenCheck(this)"></td>
			<td><%=book.getBook_no()%></td>
			<td><%=book.getBook_name()%></td>
			<td><%=book.getBorrow_date()%></td>
			<td><%=book.getReturn_date()%></td>
			<td><button type="button" class="bookreturn">도서반납</td>			
		</tr>
		<%} %>
	</table>    
</body>
</html>