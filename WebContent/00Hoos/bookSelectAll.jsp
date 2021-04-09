<%@page import="model.LibBookVO"%>
<%@page import="java.util.List"%>
<%@page import="model.LibDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<aside id="sidebar_warpper">
		<a href="start.html"><img src="/webProject(hoos)/00Hoos/images/스케치.png"></a>
		<button class="sidebar_btn" type="button">> </button>
		<div id="sidebar_list">
			<a class="user_login" href="bookSelectAll.jsp">도서 검색</a>
			<p>|</p>
			<a class="admin_login" href="#">도서 대출</a>
			<p>|</p>
			<a class="main_page" href="#">도서 반납</a>
		</div>
	</aside>
	<div id="contents">			
		<%
			LibDAO dao = new LibDAO();
			List<LibBookVO> libList = dao.getBook(1);
			out.print("<table>");
			for(LibBookVO lib:libList) {
				out.print("<tr>");
				out.print("<td><div id='book_img'></div><td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>"+lib.getBook_no()+"</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>"+lib.getBook_name()+"</td>");
				out.print("</tr>");
			}
			out.print("</table>");
		%>		
	</div>
</body>
</html>
