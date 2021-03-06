package rt.task2.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rt.task2.data.DaoFactory;
import rt.task2.data.DaoFactoryImpl;
import rt.task2.data.GenericDao;
import rt.task2.data.PersistException;
import rt.task2.data.domain.Person;
import rt.task2.data.domain.Ticket;

public class TicketsService {

	public List<Ticket> getTickets() throws PersistException, SQLException {

		DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
		Connection connection = factory.getContext();
		List<Ticket> list = null;
		try {
			connection.setAutoCommit(true);
			@SuppressWarnings("unchecked")
			GenericDao<Ticket, Long> dao = factory.getDao(connection, Ticket.class);
			list = dao.getAll();
		} catch (SQLException | PersistException e) {
			throw e;
		} finally {
			connection.close();
		}
		return list;
	}

	public Ticket saveTicket(String firstName, String middleName, String lastName, long[] recipients, String theme,
			String messageBody) throws PersistException, SQLException {

		DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
		Connection connection = factory.getContext();
		Ticket ticket = null;
		try {
			connection.setAutoCommit(true);
			@SuppressWarnings("unchecked")
			GenericDao<Ticket, Long> dao = factory.getDao(connection, Ticket.class);
			GenericDao<Person, Long> personDao = factory.getDao(connection, Person.class);
			String sql = personDao.getSelectQuery()
					+ " WHERE upper(first_name) = trim(upper(?)) and upper(middle_name) = trim(upper(?)) and  upper(coalesce(last_name,'null')) = trim(upper(coalesce(?,'null')));";
			Object[] params = { firstName, middleName, lastName };
			List<Person> persons = personDao.query(sql, params);
			Person person = null;
			if (persons == null || persons.isEmpty()) {
				person = personDao.create();
				person.setFirstName(firstName);
				person.setMiddleName(middleName);
				person.setLastName(lastName);
				personDao.update(person);
			} else {
				person = persons.get(0);
			}
			ticket = dao.create();
			ticket.setTheme(theme);
			ticket.setBody(messageBody);
			ticket.setSender(person);
			ticket.setRecipients(new ArrayList<Person>(recipients.length));
			for (int i = 0; i < recipients.length; i++) {
				ticket.getRecipients().add(personDao.getById(recipients[i]));
			}
			dao.update(ticket);
		} catch (SQLException | PersistException e) {
			throw e;
		} finally {
			connection.close();
		}
		return ticket;
	}
}
