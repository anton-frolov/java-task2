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
import rt.task2.service.PersonService;

public class PersonsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	PersonService service = new PersonService();
	try {

	    List<Person> persons = service.getPersons();
	    String json = new Gson().toJson(new JsonContainer("success", persons));
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);

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
