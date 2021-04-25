package logic;

import model.LibDAO;

public class ReservationMessage {
	public String getMessage(String userid) {
		LibDAO dao = new LibDAO();
		
		String message = ""; 
		if(dao.getReservationSave(userid) != null) {
			for(int i=0; i<dao.getReservationSave(userid).size();i++) {
				if(userid.equals(dao.getReservationSave(userid).get(i).getUser_id())) {
					message = "예약하신 도서 NO."+dao.getReservationSave(userid).get(i).getBook_no()+ "대출 가능합니다.";
					System.out.println(message);
				}
			}
		}
		return message;
	}
}
