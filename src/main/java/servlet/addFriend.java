package servlet;

import java.io.IOException;
import dao.*;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/addFriend")
public class addFriend extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String temp = req.getParameter("user_id");
			int receiver_id = Integer.parseInt(temp);
			User user = (User) req.getSession().getAttribute("user");
			int sender_id = user.getUser_id();
			UserDAO userdao = new UserDAO();
			userdao.sendFriendRequest(sender_id, receiver_id);
			req.setAttribute("message", "Tải file lên thành công!");
			resp.sendRedirect("/ShareFile/friend.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
