package servlet;

import java.io.IOException;
import dao.*;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/ignoreFriend")
public class ignorefriend extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String temp = req.getParameter("userid");
			int sender_id = Integer.parseInt(temp);
			User user = (User) req.getSession().getAttribute("user");
			int receiver_id = user.getUser_id();
			UserDAO userdao = new UserDAO();
			userdao.ignoreFriend(sender_id, receiver_id);
			resp.sendRedirect("/ShareFile/friend.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
