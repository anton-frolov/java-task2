package rt.task2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	logout(request, response);
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logout(request, response);
    	
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("text/html");
    	Cookie[] cookies = req.getCookies();
    	if(cookies != null){
	    	for(Cookie cookie : cookies){
	    		if(cookie.getName().equals("JSESSIONID")){
	    			System.out.println("JSESSIONID="+cookie.getValue());
	    			break;
	    		}
	    	}
    	}
    	HttpSession session = req.getSession(false);
    	if(session != null){
    		session.invalidate();
    	}
    	resp.sendRedirect("login");
    }

}
