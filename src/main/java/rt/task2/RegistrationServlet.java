package rt.task2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import rt.task2.data.PersistException;
import rt.task2.data.domain.User;
import rt.task2.service.LoginService;

public class RegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String userId = request.getParameter("userId");
	String password = request.getParameter("password");
	String userEmail = request.getParameter("userEmail");
	User user = null;
	StringBuilder resultString = new StringBuilder();
	LoginService service = new LoginService();

	try {
	    user = service.getUserByUserId(userId);
	} catch (PersistException | SQLException e) {
	    resultString.append(e.getMessage());
	    response.setStatus(500);
	    e.printStackTrace();
	}
	if (user != null) {
	    resultString.append("Имя пользователя " + userId + " уже существует");
	}
	try {
	    user = service.registrationUser(userId, password, userEmail);

	    String json = new Gson().toJson(new JsonContainer("success", user));
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);

	} catch (PersistException | SQLException e) {
	    resultString.append(e.getMessage());
	    response.setStatus(500);
	    e.printStackTrace();
	}

	PrintWriter outWriter = null;
	outWriter = response.getWriter();
	outWriter.write(resultString.toString());
	outWriter.flush();
	outWriter.close();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.sendRedirect("registration.jsp");
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
