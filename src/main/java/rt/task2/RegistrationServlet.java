package rt.task2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import rt.task2.data.PersistException;
import rt.task2.data.domain.AbstractEntity;
import rt.task2.data.domain.Ticket;
import rt.task2.data.domain.User;

public class RegistrationServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String userId = req.getParameter("userId");   
		String password = req.getParameter("password");
		String userEmail = req.getParameter("userEmail");
		User user = null;
		StringBuilder res = new StringBuilder();
		
		LoginService service = new LoginService();
		
		try {
			user = service.getUserByUserId(userId);
		} catch (PersistException | SQLException e) {
			res.append(e.getMessage());
			resp.setStatus(500);
			e.printStackTrace();
		}
		if(user!=null){
			res.append("Имя пользователя "+userId+" уже существует");
		}
		try {
			user = service.registrationUser(userId, password, userEmail);
	
			String json = new Gson().toJson(new JsonContainer("success", user));
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
			    
		} catch (PersistException | SQLException e) {
			res.append(e.getMessage());
			resp.setStatus(500);
			e.printStackTrace();
		}
		
		PrintWriter out = null;
		out = resp.getWriter();
		out.write(res.toString());
		out.flush();
		out.close();
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("registration.jsp");
	}

	private class JsonContainer {
		
		String result;
		User user;
		
		public JsonContainer(String result, User user) {

			this.result = result;
			this.user = user;
		}
	}
	
}
