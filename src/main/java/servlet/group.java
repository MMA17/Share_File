package servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Group;
import model.User;
import dao.GroupDAO;

@WebServlet(urlPatterns = "/group")
public class group extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/group.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Group group = new Group();
		group.setGroup_name(req.getParameter("groupName")); 
		group.setGroup_type(req.getParameter("groupType")); 
		User user = (User) req.getSession().getAttribute("user");
		int userId = user.getUser_id();
		GroupDAO gDAO = new GroupDAO();
		if(gDAO.createGroup(userId, group, req.getParameter("note"))) {
			System.out.println("truetrue");
		}else {
			System.out.println("falsefalse");
		}
		
		
		resp.sendRedirect("/ShareFile/group.jsp");
	}
}
