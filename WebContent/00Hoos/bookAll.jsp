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
	}
	#tb {
	  position:absolute;
	  width:500px;
	  top: 200px;
	  right: 400px;
      z-index:3;
	}
</style>
<!-- jQeury -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>


</head>
<body>
   	<table id="tb">
   		<tr>
   			<td>도서 번호</td><td>도서 제목</td><td>저자</td>
   		</tr>
		<%for(LibBookVO book:booklist) { %>
		<tr>
			<td><%=book.getBook_no()%></td>
			<td><%=book.getBook_name()%></td>
			<td><%=book.getBook_writer()%></td>
		</tr>
		<%} %>
	</table>    
</body>
</html>


