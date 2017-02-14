package rt.task2;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rt.task2.data.PersistException;
import rt.task2.data.domain.User;
import rt.task2.service.LoginService;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String userId = request.getParameter("userId");
	String password = request.getParameter("password");
	LoginService loginService = new LoginService();
	boolean remember = (request.getParameter("rememberMe") != null) ? request.getParameter("rememberMe") != null
		: false;
	boolean result;
	try {
	    result = loginService.authenticateUser(userId, password);
	    User user = loginService.getUserByUserId(userId);
	    if (result == true) {
		request.getSession().setAttribute("sid", user);
		if (!remember) {
		    request.getSession().setMaxInactiveInterval(60 * 5); // 5
									 // мин
		} else {
		    request.getSession().setMaxInactiveInterval(3600 * 24 * 30); // месяц
		}
		response.sendRedirect("index.jsp");
	    } else {
		request.setAttribute("errorMessage", "Ошибка авторизации! Неправильное имя пользователя или пароль");
		request.getRequestDispatcher("error.jsp").forward(request, response);
	    }

	} catch (PersistException e) {
	    e.printStackTrace();
	    request.setAttribute("errorMessage", e.getLocalizedMessage());
	    request.getRequestDispatcher("error.jsp").forward(request, response);
	} catch (SQLException e) {
	    e.printStackTrace();
	    request.setAttribute("errorMessage", e.getLocalizedMessage());
	    request.getRequestDispatcher("error.jsp").forward(request, response);
	}
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.sendRedirect("login.jsp");
    }

}