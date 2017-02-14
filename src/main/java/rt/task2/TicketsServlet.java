package rt.task2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;

import rt.task2.data.PersistException;
import rt.task2.data.domain.Ticket;
import rt.task2.data.domain.User;
import rt.task2.service.TicketsService;

public class TicketsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	TicketsService service = new TicketsService();
	HttpSession session = request.getSession(true);
	User securityObject = (User) session.getAttribute("sid");

	String typeQuery = request.getParameter("type");
	if (typeQuery != null && typeQuery.equals("add")) {

	    String firstName = request.getParameter("firstname");
	    String middleName = request.getParameter("middlename");
	    String lastName = request.getParameter("lastname");
	    String recipients = request.getParameter("recipients");
	    String theme = request.getParameter("theme");
	    String messageBody = request.getParameter("message");
	    long[] recipientIds = null;
	    if (recipients != null) {
		recipientIds = Arrays.asList(recipients.split(";")).stream().map(Long::parseLong).mapToLong(i -> i)
			.toArray();
	    }
	    ;
	    try {
		Ticket ticket = service.saveTicket(firstName, middleName, lastName, recipientIds, theme, messageBody);
		List<Ticket> tiketList = new ArrayList<Ticket>(1);
		tiketList.add(ticket);
		String json = new Gson().toJson(new JsonContainer("success", tiketList));
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	    } catch (PersistException e) {
		response.getWriter().write(e.getMessage());
		response.setStatus(500);
		e.printStackTrace();
	    } catch (SQLException e) {
		response.getWriter().write(e.getMessage());
		response.setStatus(500);
		e.printStackTrace();
	    }

	} else {
	    if (securityObject != null && !securityObject.getUserId().equals("guest")) {
		try {
		    List<Ticket> tickets = service.getTickets();
		    String json = new Gson().toJson(new JsonContainer("success", tickets));
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(json);
		} catch (PersistException e) {
		    e.printStackTrace();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    } else {
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("Недостаточно прав для просмотра списка заявок (Пользователь: Гость)");
		response.setStatus(403);
	    }
	}

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.sendRedirect("tickets.jsp");
    }

    private class JsonContainer {
	String result;
	List<Ticket> tickets;

	public JsonContainer(String result, List<Ticket> tickets) {
	    this.result = result;
	    this.tickets = tickets;
	}
    }

}
