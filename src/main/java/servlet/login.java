package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import dao.UserDAO;
import model.User;
import dao.UserDAO;
@WebServlet(urlPatterns = "/login")
public class login extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(req, resp);
		
		PrintWriter printWriter = resp.getWriter();
		printWriter.println("login.java");
		
		printWriter.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter printWriter = resp.getWriter();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		UserDAO uDao = new UserDAO();
		User user = uDao.checkLogin(username, password);

		if(user.getUser_id() != 0) {
			
			HttpSession httpSession = req.getSession();
			httpSession.setAttribute("user", user);
			UserDAO userdao = new UserDAO();
			userdao.setUserStatusOnline(user.getUser_id());
//			req.getSession().setAttribute("user", username);
			
			resp.sendRedirect("/ShareFile/dashboard.jsp");
		}else {
			printWriter.print("<p style='color: red;'>Tai khoan hoac mat khau khong chinh xac</p>");
			req.getServletContext().getRequestDispatcher("/login.jsp").include(req, resp);
			
		}
		
			
	}
}
