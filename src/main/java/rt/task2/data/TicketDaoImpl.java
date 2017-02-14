package rt.task2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import org.postgresql.util.PSQLException;

import rt.task2.data.domain.Person;
import rt.task2.data.domain.Ticket;

public class TicketDaoImpl extends AbstractDaoImpl<Ticket, Long> {

    private class PersistTicket extends Ticket {

	public void setId(Long id) {
	    super.setId(id);
	}

    }

    private class PersistPerson extends Person {

	public void setId(Long id) {
	    super.setId(id);
	}

    }

    public TicketDaoImpl(Connection connection) {
	super(connection);
    }

    @Override
    public Ticket create() throws PersistException {
	Ticket ticket = new Ticket();
	return persist(ticket);
    }

    @Override
    public String getSelectQuery() {
	return "SELECT * FROM public.ticket";
    }

    @Override
    public String getCreateQuery() {
	return "INSERT INTO public.ticket(person_id, theme, body) VALUES(?, ?, ?) RETURNING id;";
    }

    @Override
    public String getUpdateQuery() {
	return "UPDATE public.ticket SET person_id = ?, theme = ?, body = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
	return "DELETE FROM public.ticket WHERE id = ?;";
    }

    @Override
    protected List<Ticket> parseResultSet(ResultSet resultSet) throws PersistException {
	LinkedList<Ticket> result = new LinkedList<Ticket>();
	try {
	    while (resultSet.next()) {
		PersistTicket ticket = new PersistTicket();
		PersistPerson sender = new PersistPerson();
		ticket.setId(resultSet.getLong("id"));
		sender.setId(resultSet.getLong("person_id"));
		ticket.setSender(sender);
		ticket.setTheme(resultSet.getString("theme"));
		ticket.setBody(resultSet.getString("body"));
		result.add(ticket);
	    }
	} catch (Exception e) {
	    throw new PersistException(e);
	}
	return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Ticket ticket) throws PersistException {
	try {
	    if (ticket.getSender() != null) {
		statement.setLong(1, ticket.getSender().getId());
	    } else {
		statement.setNull(1, Types.NULL);
	    }
	    if (ticket.getTheme() != null) {
		statement.setString(2, ticket.getTheme());
	    } else {
		statement.setNull(2, Types.NULL);
	    }
	    if (ticket.getBody() != null) {
		statement.setString(3, ticket.getBody());
	    } else {
		statement.setNull(3, Types.NULL);
	    }
	} catch (Exception e) {
	    throw new PersistException(e);
	}
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Ticket ticket) throws PersistException {
	try {
	    if (ticket.getSender() != null) {
		statement.setLong(1, ticket.getSender().getId());
	    } else {
		statement.setNull(1, Types.NULL);
	    }
	    if (ticket.getTheme() != null) {
		statement.setString(2, ticket.getTheme());
	    } else {
		statement.setNull(2, Types.NULL);
	    }
	    if (ticket.getBody() != null) {
		statement.setString(3, ticket.getBody());
	    } else {
		statement.setNull(3, Types.NULL);
	    }
	    statement.setLong(4, ticket.getId());
	} catch (Exception e) {
	    throw new PersistException(e);
	}

    }

    @Override
    public void update(Ticket ticket) throws PersistException {
	super.update(ticket);
	if (ticket.getRecipients() != null && ticket.getRecipients().size() > 0) {
	    String sql = "INSERT INTO public.ticket_recipient (person_id, ticket_id)  VALUES(?, ?);";
	    try (PreparedStatement statement = connection.prepareStatement(sql);) {
		for (Person person : ticket.getRecipients()) {
		    try {
			statement.setLong(1, person.getId());
			statement.setLong(2, ticket.getId());
			statement.execute();
		    } catch (PSQLException e) {
			if (!e.getSQLState().equals("23505")) // дубликаты
							      // пропускаем
			{
			    throw new PersistException(e);
			}
		    } catch (Exception e) {
			throw new PersistException(e);
		    }
		}
		statement.close();
	    } catch (Exception e) {
		throw new PersistException(e);
	    }
	}
    }

    @Override
    public Ticket getById(Long id) throws PersistException {
	Ticket ticket = super.getById(id);
	if (ticket != null) {
	    DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
	    GenericDao<Person, Long> personDao = factory.getDao(connection, Person.class);
	    Person person = personDao.getById(ticket.getSender().getId());
	    ticket.setSender(person);
	    String sql = personDao.getSelectQuery()
		    + " WHERE id in(select person_id from public.ticket_recipient where ticket_id= ?) ORDER BY ID;";
	    Object[] params = { ticket.getId() };
	    List<Person> recipients = personDao.query(sql, params);
	    ticket.setRecipients(recipients);
	}
	return ticket;

    }

    @Override
    public List<Ticket> getAll() throws PersistException {
	List<Ticket> tickets = super.getAll();
	if (tickets != null && tickets.size() > 0) {
	    DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
	    GenericDao<Person, Long> personDao = factory.getDao(connection, Person.class);
	    for (Ticket ticket : tickets) {
		Person person = personDao.getById(ticket.getSender().getId());
		ticket.setSender(person);
		String sql = personDao.getSelectQuery()
			+ " WHERE id in(select person_id from public.ticket_recipient where ticket_id= ?) ORDER BY ID;";
		Object[] params = { ticket.getId() };
		List<Person> recipients = personDao.query(sql, params);
		ticket.setRecipients(recipients);
	    }
	}
	return tickets;
    }

    @Override
    public List<Ticket> query(String query, Object[] params) throws PersistException {
	List<Ticket> tickets = super.query(query, params);
	if (tickets != null && tickets.size() > 0) {
	    DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
	    GenericDao<Person, Long> personDao = factory.getDao(connection, Person.class);
	    for (Ticket ticket : tickets) {
		Person person = personDao.getById(ticket.getSender().getId());
		ticket.setSender(person);
		String sql = personDao.getSelectQuery()
			+ " WHERE id in(select person_id from public.ticket_recipient where ticket_id= ?) ORDER BY ID;";
		Object[] params2 = { ticket.getId() };
		List<Person> recipients = personDao.query(sql, params2);
		ticket.setRecipients(recipients);
	    }
	}
	return tickets;
    }

}
