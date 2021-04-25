package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.ReservationMessage;
import model.LibDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/00Hoos/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String userpass = request.getParameter("userpass");
		String login = "login";
		HttpSession session = request.getSession();
		
		LibDAO dao = new LibDAO();
		ReservationMessage msg = new ReservationMessage(); // 예약 메시지 가져오는 로직
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
			System.out.println("<p>관리자 입니다.</p>");
			response.sendRedirect("adminStart.jsp");

		} else {
			
			String message = msg.getMessage(userid);
			
			request.setAttribute("reservationMessage", message);
			session.setAttribute("userid", userid); // 세션에 "userid"를 키로 userid를 저장 
//			response.sendRedirect("userStart.jsp?reservationMessage="+message);
			RequestDispatcher rd = request.getRequestDispatcher("userStart.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
