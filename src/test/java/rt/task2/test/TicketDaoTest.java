package rt.task2.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rt.task2.data.DaoFactory;
import rt.task2.data.DaoFactoryImpl;
import rt.task2.data.GenericDao;
import rt.task2.data.PersistException;
import rt.task2.data.domain.AbstractEntity;
import rt.task2.data.domain.Person;
import rt.task2.data.domain.Ticket;

public class TicketDaoTest {

    private Connection connection;
    private GenericDao<Ticket, Long> dao;
    private GenericDao<Person, Long> personDao;

    private static final DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();

    public Connection context() {
	return connection;
    }

    @Before
    public void setUp() throws PersistException, SQLException {
	connection = factory.getContext();
	connection.setAutoCommit(false);
	personDao = factory.getDao(connection, Person.class);
	dao = factory.getDao(connection, Ticket.class);
    }

    @After
    public void tearDown() throws SQLException {
	context().rollback();
	context().close();
    }

    @Test
    public void testCreateTicket() throws Exception {
	AbstractEntity<Long> dto = dao.create();
	Assert.assertNotNull(dto);
	Assert.assertNotNull(dto.getId());
    }

    @Test
    public void testGetTicketById() throws Exception {
	AbstractEntity<Long> dto = dao.getById(1L);
	Assert.assertNotNull(dto);
    }

    @Test
    public void testGetAllTickets() throws Exception {
	List<Ticket> list = dao.getAll();
	Assert.assertNotNull(list);
	Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testUpdateTicket() throws Exception {

	// Person sender = personDao.getById(1L);
	Ticket ticket = dao.getById(1L);
	ticket.setTheme("theme2");
	ticket.setBody("testbody2");
	if (ticket.getRecipients() != null) {
	    ticket.setRecipients(new ArrayList<Person>(4));
	}
	for (int i = 0; i < 4; i++) {
	    Person person = personDao.create();
	    person.setFirstName("test" + i);
	    person.setMiddleName("test" + i);
	    person.setLastName("test" + i);
	    person.setEmail("test" + i + "@test.ru");
	    personDao.update(person);
	    ticket.getRecipients().add(person);
	}

	dao.update(ticket);
	ticket = null;
	ticket = dao.getById(1L);
	Assert.assertNotNull(ticket);
	Assert.assertTrue(ticket.getTheme().equals("theme2"));
	Assert.assertTrue(ticket.getBody().equals("testbody2"));
	Assert.assertTrue(ticket.getSender().getId() == 1L);
	Assert.assertNotNull(ticket.getRecipients());
	Assert.assertTrue(ticket.getRecipients().size() == 5);
    }

    @Test
    public void testDeleteTicket() throws Exception {
	Ticket ticket = dao.getById(1L);
	Assert.assertNotNull(ticket);
	dao.delete(ticket);
	ticket = null;
	ticket = dao.getById(1L);
	Assert.assertNull(ticket);
    }

    @Test
    public void testQuery() throws Exception {
	String sql = dao.getSelectQuery() + " WHERE id = ?  limit 1;";
	Object[] params = { 1L };
	List<Ticket> list = dao.query(sql, params);
	Assert.assertNotNull(list);
	Assert.assertTrue(list.size() > 0);
    }
}
