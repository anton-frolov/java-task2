package rt.task2.test;

import java.sql.Connection;
import java.sql.SQLException;
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




public class PersonDataTest {
	
	private Connection connection;

    private GenericDao<Person, Long> dao;
    
    private static final DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
    
    public Connection context() {
        return connection;
    }
    
       
    @Before
    public void setUp() throws PersistException, SQLException {
        connection = factory.getContext();
        connection.setAutoCommit(false);
        dao = factory.getDao(connection, Person.class);
    }
    
    @After
    public void tearDown() throws SQLException {
        context().rollback();
        context().close();
    }
    
    
    @Test
    public void testCreatePerson() throws Exception {
        AbstractEntity<Long> dto = dao.create();
        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getId());
    }
    
    @Test
	public void testGetPersonById() throws Exception {
    	AbstractEntity<Long> dto = dao.getById(1L);
    	Assert.assertNotNull(dto);
    }
    
    @Test
	public void testGetAllPersons() throws Exception {
    	List<Person> list = dao.getAll();
    	Assert.assertNotNull(list);
    	Assert.assertTrue(list.size() > 0);
    }
    
    @Test
    public void testUpdatePerson() throws Exception{
    	
    	Person person = dao.getById(1L);
    	Assert.assertNotNull(person);
    	Assert.assertTrue(person.getFirstName().equals("test"));
    	Assert.assertTrue(person.getMiddleName().equals("test"));
    	Assert.assertTrue(person.getLastName().equals("test"));
    	person.setFirstName("newFirstName");
    	person.setMiddleName("newMiddleName");
    	person.setLastName("newLastName");
    	person.setEmail("newemail@test.ru");
    	dao.update(person);
    	person = null;
    	person = dao.getById(1L);
    	Assert.assertNotNull(person);
    	Assert.assertTrue(person.getFirstName().equals("newFirstName"));
    	Assert.assertTrue(person.getMiddleName().equals("newMiddleName")); 
    	Assert.assertTrue(person.getLastName().equals("newLastName")); 
    	Assert.assertTrue(person.getEmail().equals("newemail@test.ru")); 
    }
    
    @Test
    public void testDeletePerson() throws Exception{
    	Person person = dao.getById(2L);
    	Assert.assertNotNull(person);
    	dao.delete(person);
    	person = null;
    	person = dao.getById(2L);
    	Assert.assertNull(person);
    }

    @Test
   	public void testQuery() throws Exception {
    	String sql = dao.getSelectQuery()+ " WHERE first_name = ? and middle_name = ? and  coalesce(last_name,'null') = coalesce(?,'null') ORDER BY ID;";
    	Object[] params = {"test", "test", "test"};
       	List<Person> list = dao.query(sql, params);
       	Assert.assertNotNull(list);
       	Assert.assertTrue(list.size() > 0);
    }

}
