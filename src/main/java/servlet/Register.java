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

import dao.UserDAO;
import model.User;

@WebServlet(urlPatterns = "/register")
public class Register extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/register.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("username");
		String name = req.getParameter("name");
		String tel = req.getParameter("tel");
		String password = req.getParameter("password");
		
//		User user = new User(username, password, name, tel, 0, "");
		
		//Kiem tra xem username da ton tai chua???
		UserDAO uDao = new UserDAO();
		Boolean check = uDao.addUser(username, password, name, tel);
		
	
		PrintWriter printWriter = resp.getWriter();
		if(check) {
			printWriter.println("Thanh Cong");
		}else {
			System.out.println("Khong thanh cong");
		}
//		printWriter.println(username);
		resp.sendRedirect("/ShareFile/login.jsp");
		
		
	}

}
