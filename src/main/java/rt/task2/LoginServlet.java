package rt.task2;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rt.task2.data.PersistException;
import rt.task2.data.domain.User;


public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		String userId = req.getParameter("userId");   
		String password = req.getParameter("password");
		LoginService loginService = new LoginService();
		boolean remember = (req.getParameter("rememberMe")!=null) ? req.getParameter("rememberMe") != null : false;
		boolean result;
		try {
			result = loginService.authenticateUser(userId, password);
			User user = loginService.getUserByUserId(userId);
			if(result == true){
			    req.getSession().setAttribute("sid", user); 
			    if (!remember) {
			    	req.getSession().setMaxInactiveInterval(60*5); //5 мин
				}else{
					req.getSession().setMaxInactiveInterval(3600*24*30); //месяц
				}
			    resp.sendRedirect("index.jsp");
			}
			else{
				req.setAttribute("errorMessage", "Ошибка авторизации! Неправильное имя пользователя или пароль");
				req.getRequestDispatcher("error.jsp").forward(req, resp);
			}
			
		} catch (PersistException e) {
			e.printStackTrace();
			req.setAttribute("errorMessage", e.getLocalizedMessage());
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
			req.setAttribute("errorMessage", e.getLocalizedMessage());
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("login.jsp");
	}

}