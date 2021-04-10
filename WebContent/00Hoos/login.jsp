<%@page import="model.LibDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String userid = request.getParameter("userid");
	String userpass = request.getParameter("userpass");
	LibDAO dao = new LibDAO();
	String id = null;
	int id_num = 1;
	
	if(userid.equals("admin1")) { id_num = 2; }
	
	id = dao.login(userid, userpass, id_num);
	
	if(id.equals("no_admin")) {
		out.print("<p>관리자 승인이 필요합니다.</p>");
	} else if(id.equals("no_id")) {
		out.print("<p>계정이 올바르지 않습니다.</p>");		
	} else if(id.equals("admin1")) {
		out.print("<p>관리자 입니다.</p>");		
		%>
		<script>
			location.href = "start.html"
		</script>
		<%
	} else {
		%>
		<script>
			location.href = "start.html"
		</script>
		<%
	}
%>

