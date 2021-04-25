<%@page import="model.LibDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String userid = request.getParameter("userid");
	String userpass = request.getParameter("userpass");
	String login = "login";
	
	LibDAO dao = new LibDAO();
	String id = null;
	int id_num = 1;
	
	if(userid.equals("admin1")) { id_num = 2; } // admin id일 경우 admin login모드로 들어가게 id_num부여
	
	id = dao.login(userid, userpass, id_num);
	
	if(id.equals("no_admin")) {
		request.setAttribute("errMsg", "관리자 승인이 필요합니다.");
		RequestDispatcher rd = request.getRequestDispatcher("userlogin.jsp");
		rd.forward(request, response);
 
	} else if(id.equals("no_id")) {
		request.setAttribute("errMsg", "계정이 올바르지 않습니다.");
		RequestDispatcher rd = request.getRequestDispatcher("userlogin.jsp");
		rd.forward(request, response); 

	} else if(id.equals("admin1")) {
		session.setAttribute("userid", userid); // 세션에 "userid"를 키로 userid를 저장
		out.print("<p>관리자 입니다.</p>");
		response.sendRedirect("adminStart.jsp");

	} else {
		String message = ""; 
		if(dao.getReservationSave(userid) != null) {
			for(int i=0; i<dao.getReservationSave(userid).size();i++) {
				if(userid.equals(dao.getReservationSave(userid).get(i).getUser_id())) {
					message = "예약하신 도서 NO."+dao.getReservationSave(userid).get(i).getBook_no()+ "대출 가능합니다.";
					out.print(message);
					out.print("gg");
					System.out.println(message);
				}					
			}
		}
		/* request.setAttribute("message", message); */
		session.setAttribute("userid", userid); // 세션에 "userid"를 키로 userid를 저장 
		response.sendRedirect("userStart.jsp?message="+message);
	}
	
%>
