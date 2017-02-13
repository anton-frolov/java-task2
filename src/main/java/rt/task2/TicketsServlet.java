package rt.task2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPBinding;

import com.google.gson.Gson;

import rt.task2.data.PersistException;
import rt.task2.data.domain.Ticket;
import rt.task2.data.domain.User;

public class TicketsServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		TicketsService service = new TicketsService();
		StringBuilder res = new StringBuilder();
		HttpSession session = req.getSession(true);
		User sid = (User) session.getAttribute("sid");
		
		
		String typeQuery = req.getParameter("type");   
		if(typeQuery!=null && typeQuery.equals("add")){
		
			String firstName = req.getParameter("firstname");
			String middleName = req.getParameter("middlename");
			String lastName = req.getParameter("lastname");
			String recipients = req.getParameter("recipients");
			String theme = req.getParameter("theme");
			String messageBody = req.getParameter("message");
			
			long[] recipientIds = null;
			
			if(recipients!=null){
				recipientIds = Arrays.asList( recipients.split(";")).stream().map(Long::parseLong).mapToLong(i->i).toArray();
			};
			
			try {
				Ticket ticket = service.saveTicket(firstName, middleName, lastName, recipientIds, theme, messageBody);
				List<Ticket> tl = new ArrayList<Ticket>(1);
				tl.add(ticket);
				String json = new Gson().toJson(new JsonContainer("success", tl));
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(json);
			} catch (PersistException e) {
				resp.getWriter().write(e.getMessage());
				resp.setStatus(500);
				e.printStackTrace();
			} catch (SQLException e) {
				resp.getWriter().write(e.getMessage());
				resp.setStatus(500);
				e.printStackTrace();
			}

			
		}else{
			if(sid!=null && !sid.getUserId().equals("guest")){
				try {
					
					List<Ticket> tickets = service.getTickets();
					String json = new Gson().toJson(new JsonContainer("success", tickets));
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(json);
					
					
				} catch (PersistException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write("Недостаточно прав для просмотра списка заявок (Пользователь: Гость)");	
				resp.setStatus(403);
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
