package rt.task2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import rt.task2.data.PersistException;
import rt.task2.data.domain.Person;

public class PersonsServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PersonService service = new PersonService();
		StringBuilder res = new StringBuilder();
		try {

			List<Person> persons = service.getPersons();
			String json = new Gson().toJson(new JsonContainer("success", persons));
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
			
			
		} catch (PersistException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private class JsonContainer {
		
		String result;
		List<Person> persons;
		
		public JsonContainer(String result, List<Person> persons) {

			this.result = result;
			this.persons = persons;
		}
	}

}
