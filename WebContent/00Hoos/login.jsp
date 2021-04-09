<%@page import="model.LibDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>로그인을 체크합니다.</h1>
<hr>
<%
	String userid = request.getParameter("userid");
	String userpass = request.getParameter("userpass");
	LibDAO dao = new LibDAO();
	String id = dao.login(userid, userpass, 1);
%>

<p>아이디는 ${param.userid }</p>
<p>비밀번호는 ${param.userpass }</p>
<p><%=id %></p>


</body>
</html>