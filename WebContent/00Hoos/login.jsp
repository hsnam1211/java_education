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
		
	session.setAttribute("userid", userid); // 세션에 "userid"를 키로 userid를 저장
	
	if(id.equals("no_admin")) {
		request.setAttribute("errMsg", "관리자 승인이 필요합니다.");
		RequestDispatcher rd = request.getRequestDispatcher("userlogin.jsp");
		rd.forward(request, response);
 		/* out.print("<p>관리자 승인이 필요합니다.</p>"); */
	} else if(id.equals("no_id")) {
		request.setAttribute("errMsg", "계정이 올바르지 않습니다.");
		/*RequestDispatcher rd = request.getRequestDispatcher("userlogin.jsp");
		rd.forward(request, response); */
		pageContext.forward("userlogin.jsp");
		/* out.print("<p>계정이 올바르지 않습니다.</p>");	 */	
	} else if(id.equals("admin1")) {
		out.print("<p>관리자 입니다.</p>");
		request.setAttribute("errMsg", "");
		response.sendRedirect("start.jsp");

	} else {
		response.sendRedirect("start.jsp");
	}
	
%>
