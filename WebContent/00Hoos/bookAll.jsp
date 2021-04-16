<%@page import="model.LibBookVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<% 	
	List<LibBookVO> booklist = (List<LibBookVO>)request.getAttribute("book");
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
		
		if(col4 == "대출가능") {
			col4 = "Y";
		} else {
			col4 = "N";
		}
		
		var state = '{"bookno": '+col1+', "status": "'+col4+'"}';
		var title = '';
		var url = "userStart.jsp";
		
		history.pushState(state, title, url)
	}); 
}

</script>
</head>
<body>
   	<table id="tb">
   		<tr>
   			<th>선택</th><th>도서번호</th><th>도서제목</th><th>저자</th><th>도서상태</th>
   		</tr>
		<%for(LibBookVO book:booklist) { 
			if(book.getBook_borrow_status().equals("Y")) {
				book.setBook_borrow_status("대출가능");
			} else {
				book.setBook_borrow_status("대출중");
			}
		%>
		<tr>
			<td><input type="checkbox" name="check" class="check" onclick="doOpenCheck(this)"></td>
			<td><%=book.getBook_no()%></td>
			<td><%=book.getBook_name()%></td>
			<td><%=book.getBook_writer()%></td>
			<td><%=book.getBook_borrow_status()%></td>
		</tr>
		<%} %>
	</table>    
</body>
</html>


