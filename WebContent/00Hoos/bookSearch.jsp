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
	List<LibBookVO> booklist = (List<LibBookVO>)request.getAttribute("book1");
%>
	
<style>
	}
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


